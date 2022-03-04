function parseUrl() {
    var url = decodeURI(location.href);
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}
$(function () {
    laydate.render({
        elem: '#startTime',
        format: 'yyyy/MM/dd',
        done: function(value) {
            $(".startInput").val(value)
        }
    });
    laydate.render({
        elem: '#endTime',
        format: 'yyyy/MM/dd',
        done: function(value) {
            $(".endInput").val(value)
        }
    });
});


var v=parseUrl();
var param=getParam();
var vm =new Vue({
    el:'.all',
    data:{
        id:'',
        orgId:'',
        codList:[],
        crmList:[],
        updateTime:"",
        startDy:'',
        endDy:''
    },
    updated:function(){
        laydate.render({
            elem: '#startTime',
            value:vm.startDy,
            format: 'yyyy/MM/dd',
            done: function(value) {
                $(".startInput").val(value)
            }
        });
        laydate.render({
            elem: '#endTime',
            value:vm.endDy,
            format: 'yyyy/MM/dd',
            done: function(value) {
                $(".endInput").val(value)
            }
        });
    },
    mounted:function () {
        this.getInfo();
    },
    methods:{
        getInfo:function () {
            $.ajax({
                url: ctxPath + '/manager/F01002/init',
                data:{
                    id:param.id,
                    orgId:param.orgId,
                },
                dataType:'json',
                type:'GET',
                success:function (data) {
                    if(data.codList){
                        vm.codList = data.codList;
                    }
                    if (data.code == 0){
                        if(data.crmList){
                            vm.crmList = data.crmList;
                            vm.updateTime = data.updateTime;
                            if (data.crmList.useFlg == 0){
                                $("#check").prop("checked",false);
                            }
                        }
                        if(data.startDy){
                            $('.startInput').val(data.startDy);
                            vm.startDy=data.startDy;
                        }
                        if(data.endDy){
                            $('.endInput').val(data.endDy);
                            vm.endDy=data.endDy;
                        }
                    }else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
            if (v != null) {
                $('#orgNm').html(v["orgNm"]);
            }
        },
        //半角check
        isHalfAngle:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(48>str.charCodeAt(i)||str.charCodeAt(i)>57){
                    flg=false;
                    break;
                }
            }
            return flg
        },
        cancelFn:function () {
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        submitFn:function () {
            // 適用フラグ
            if($('#check').is(':checked')) {
                window.useFlg = 1;
                $("#check").prop("checked",true);

            }else {
                window.useFlg = 0;
                $("#check").prop("checked",false);
            }
            // 学年
            if($("#select1").val() == ''){
                parent.layer.alert($.format($.msg.MSGCOMD0001,"学年"));
                $("#select1").focus();
                return;
            }
            // 時期名
            if($(".getData").val()==''){
                parent.layer.alert($.format($.msg.MSGCOMD0001,"時期名"));
                $("#orgNm").focus();
                return;
            }
            if($(".getData").val().length > 60){
                parent.layer.alert($.format($.msg.MSGCOMD0011,"時期名","60"));
                $("#orgNm").focus();
                return;
            }
            // 時期開始日
            if($(".startInput").val()==''){
                parent.layer.alert($.format($.msg.MSGCOMD0001,"時期開始日"));
                $(".startInput").focus();
                return;
            }
            if($(".endInput").val()==''){
                parent.layer.alert($.format($.msg.MSGCOMD0001,"時期終了日"));
                $(".endInput").focus();
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/F01002/submit',
                data:{
                    id:param.id,
                    orgId:param.orgId,
                    useFlg:window.useFlg,
                    prdNm:$(".getData").val(),
                    schyDiv:$("#select1").val(),
                    prdStartDy:$(".startInput").val(),
                    prdEndDy:$(".endInput").val(),
                    updateTime:vm.updateTime
                },
                dataType:'json',
                type:'POST',
                async:true,
                success:function (data) {
                    if (data.code == 0) {
                        // layer.confirm(data.msg,{btn:['確認']},function(){
                            parent.location.reload(); // 父页面刷新
                            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);
                        // });
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        }
    }
});
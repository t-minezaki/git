var param =new getParam();
var vm =new Vue({
    el:'.content',
    data:{
        mentor:[],
        updateTime:"",
        brandCd:'',
        orgAddId:''
    },
    updated:function(){
    },
    mounted:function () {
        $.ajax({
            url: ctxPath + '/common/F40004/getOrg',
            type: 'GET',
            dataType: "json",
            success: function (data){
                vm.orgAddId = data.orgIdAdd;
            }
        });
        this.getInfo();
    },
    methods:{
        getInfo:function () {
            $.ajax({
                url: ctxPath + '/manager/f21001/init',
                type: 'GET',
                dataType: 'json',
                async: true,
                success:function (data) {
                    if (data.code == 0){
                        if (data.mentor){
                            $('#flnmNm').val(data.mentor.flnmNm);
                            $('#flnmLnm').val(data.mentor.flnmLnm);
                            $('#flnmKnNm').val(data.mentor.flnmKnNm);
                            $('#flnmKnLnm').val(data.mentor.flnmKnLnm);
                            $('#telnum').val(data.mentor.telnum);
                            $('#email').val(data.mentor.mailad);
                            window.id = data.mentor.id;
                            vm.updateTime = data.updateTime;
                            vm.brandCd = data.brandCd;
                        }
                    }else {
                        showMsg(data.msg);
                    }
                }
            });
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
        isAngle:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(!(12543 >str.charCodeAt(i)&&str.charCodeAt(i)> 12352)){
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
        backFn:function(){
            if(param.flag==1){
                window.location.href="../common/F40008.html";
            }else{
                window.history.back();
            }
        },
        submitFn:function () {
            formatCheck();
            if($("#f21001Form").valid() == true){
                $.ajax({
                    url: ctxPath + '/manager/f21001/submit',
                    data:{
                        id:window.id,
                        flnmNm:$('#flnmNm').val(),
                        flnmLnm:$('#flnmLnm').val(),
                        flnmKnNm:$('#flnmKnNm').val(),
                        flnmKnLnm:$('#flnmKnLnm').val(),
                        telnum:$('#telnum').val(),
                        mailad:$('#email').val(),
                        updateTime:vm.updateTime
                    },
                    dataType:'json',
                    type:'POST',
                    async:true,
                    success:function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0015,"基本情報"),{btn:["確認"]},function () {
                                if (param.flag == 1){
                                    if (vm.brandCd == vm.orgAddId){
                                        window.open("../manager/F21017.html","_self")
                                    } else {
                                        window.open("../manager/F00001.html","_self")
                                    }
                                }
                                else {
                                    window.location.href = document.referrer;
                                }
                            // })
                        }
                        else {
                            showMsg(data.msg);
                        }
                    }
                });
            }
        }
    }
});
function formatCheck() {
    $("#f21001Form").validate({
        rules: {
            flnmNm:{
                required: true,
                maxlength:10
            },
            flnmLnm: {
                required: true,
                maxlength:10
            },
            flnmKnNm:{
                required: true,
                maxlength:10,
                zenkaku:true
            },
            flnmKnLnm:{
                required: true,
                maxlength:10,
                zenkaku:true
            },
            telnum: {
                maxlength:20,
                digits:true
            },
            email: {
                required: true,
                email:true,
                maxlength:50
            }
        },
        debug:true,
        messages: {
            // 名前_姓
            flnmNm: {
                required:$.format($.msg.MSGCOMD0001,"名前_姓"),
                maxlength:$.format($.msg.MSGCOMD0011,"名前_姓","10")
            },
            // 名前_名
            flnmLnm: {
                required:$.format($.msg.MSGCOMD0001,"名前_名"),
                maxlength:$.format($.msg.MSGCOMD0011,"名前_名","10")
            },
            // 名前_カナ姓
            flnmKnNm: {
                required:$.format($.msg.MSGCOMD0001,"名前_カナ姓"),
                maxlength:$.format($.msg.MSGCOMD0011,"名前_カナ姓","10"),
                zenkaku:$.format($.msg.MSGCOMD0007, "名前_カナ姓")
            },
            // 名前_カナ名
            flnmKnLnm: {
                required:$.format($.msg.MSGCOMD0001,"名前_カナ名"),
                maxlength:$.format($.msg.MSGCOMD0011,"名前_カナ名","10"),
                zenkaku:$.format($.msg.MSGCOMD0007, "名前_カナ名")
            },
            // 電話番号
            telnum: {
                maxlength:$.format($.msg.MSGCOMD0011,"電話番号","20"),
                digits:$.format($.msg.MSGCOMD0006,"電話番号")
            },
            email: {
                required:$.format($.msg.MSGCOMD0001,"メール"),
                maxlength:$.format($.msg.MSGCOMD0011,"メール","50"),
                email:$.format($.msg.MSGCOMD0018,"メール")
            }
        },
        showErrors:function(errorMap,errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
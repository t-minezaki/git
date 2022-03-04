var index;
$(function () {
    $("#likeSearch").click(function () {
        $(this).addClass("hasborder");
        $("#popId").removeClass("disNone");
        //2021/01/07 liyuhuan add start
        $("#userId").val("");
        $("#userName").val("");
        $("#schyDiv").find("option:selected")[0].selected = false;
        $("#grpDiv1").find("option:selected")[0].selected = false;
        $("#color-input-red")[0].checked = false;
        //2021/01/07 liyuhuan add end
        index = layer.open({
            id:'f10002',
            type: 1,
            title: false,
            shade: 0.1,
            closeBtn: 0,
            fixed: true,
            area: ['20vw', 'auto'],
            content: $("#popId"),
            success:function () {
                $(".layui-layer").addClass("layui-chage");
            },
            end:function () {
                $("#popId").addClass("disNone");
            }
        });
    });
    $("#cancelBtn").click(function () {
        $("#popId").addClass("disNone");
        layer.close(index);
    });
});
var vm = new Vue({
    el: '#pageF20001',
    data: {
        list: [],
        schyList:[],
        groupsList:[],
        mentorNm:""
    },
    mounted: function () {
        var intervalID = setInterval(function () {
            if (window.parent.$("#orgList").val()) {
                clearInterval(intervalID);
                vm.showData();
            }
        }, 500);
    },
    updated:function(){
        $("a").click(function () {
            var url=$(this).attr("desc");
            var json=JSON.parse($(this).attr("target"));
            // var now = new Date();
            // if(json.id == null || now < new Date(Date.parse(json.planStartDy)) || now > new Date(Date.parse(json.planEndDy))){
            //     showMsg($.format($.msg.MSGCOMN0123,"塾時期"));
            //     return false;
            // }
            $.ajax({
                url: ctxPath + '/manager/F20001/saveStuId',
                type:'GET',
                data:{
                    stuId:json.stuId,
                    id:json.id,
                    mentorNm:vm.mentorNm
                },
                success:function (data) {
                    if (data.code == 0) {
                        window.location.href = ctxPath+url;
                    }else {
                        showMsg(data.msg);
                    }
                }
            });
        });
    },
    methods: {
        showData: function () {
            $.getJSON(ctxPath + "/manager/F20001/list",
                {
                    'orgId': window.parent.$("#orgList").val()
                },
                function (data) {
                    if (data.msg != "success") {
                        showMsg(data.msg);
                    } else {
                        vm.list = data.list;
                        if (data.groupsList) {
                            vm.groupsList = data.groupsList;
                        }
                        if (data.schyList) {
                            vm.schyList = data.schyList;
                        }
                        if (data.mentorNm) {
                            vm.mentorNm = data.mentorNm;
                        }
                    }
                });
        },
        isHalfAngle:function (str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(48>str.charCodeAt(i)||(str.charCodeAt(i)<65&&str.charCodeAt(i)>57)||(str.charCodeAt(i)<97&&str.charCodeAt(i)>90)||str.charCodeAt(i)>122){
                    flg=false;
                    break;
                }
            }
            return flg;
        },
        isAllAngle:function (str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(str.charCodeAt(i) <= 128){
                    flg=false;
                    break;
                }
            }

            return flg;
        },
        submitFn:function () {
            var termPlanCheck='0';
            //ユーザID
            if($("#userId").val()!=''&&!vm.isHalfAngle($("#userId").val())){
                showMsg($.format($.msg.MSGCOMD0004,"ユーザーID"));
                return;
            }
            //ユーザ名
            if($("#userName").val()!=''&&!vm.isAllAngle($("#userName").val())){
                showMsg($.format($.msg.MSGCOMD0002,"ユーザ名"));
                return;
            }
            if($("#color-input-red").get(0).checked){
                termPlanCheck='1';
            }
            $.ajax({
                url: ctxPath + '/manager/F20001/likeSearch',
                data:{
                    userId:$("#userId").val(),
                    userName:$("#userName").val(),
                    schyDiv:$("#schyDiv").val(),
                    grpDiv1:$("#grpDiv1").val(),
                    termPlanCheck:termPlanCheck
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    layer.close(index);
                    if (data.msg != "success") {
                        showMsg(data.msg);
                    } else {
                        vm.list = data.list;
                    }
                }
            });
        }
    }
});
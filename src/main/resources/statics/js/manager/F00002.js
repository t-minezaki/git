$(function () {
})
var param =new getParam();
var vm =new Vue({
    el:'.content',
    data:{
        manager:[],
        updateTime:""
    },
    updated:function(){
    },
    mounted:function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f00002/init',
                type: 'GET',
                dataType: 'json',
                async: true,
                success: function (data) {
                    if (data.code == 0) {
                        if (data.manager) {
                            $('#flnmNm').val(data.manager.flnmNm);
                            $('#flnmLnm').val(data.manager.flnmLnm);
                            $('#flnmKnNm').val(data.manager.flnmKnNm);
                            $('#flnmKnLnm').val(data.manager.flnmKnLnm);
                            $('#telnum').val(data.manager.telnum);
                            $('#email').val(data.manager.mailad);
                            window.id = data.manager.id;
                            vm.updateTime = data.updateTime;
                        }
                        if (data.saf_modify_flg =="0"){
                            $("#update").css("visibility","hidden");
                            $("#emailAdAuth").css("visibility","hidden");
                        }
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        //半角check
        isHalfAngle: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        isAngle: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (!(12543 > str.charCodeAt(i) && str.charCodeAt(i) > 12352)) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        cancelFn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        backFn: function () {
            if (param.flag == 1) {
                window.location.href = "../common/F40008.html";
            } else {
                window.history.back();
            }
        },
        //ページジャンプ
        sendEmailFn:function(){
            // 電子メール確認ページをポップモードで開きます
            layer.open({
                id:'F40017',
                type:2,
                title: ['', 'display:none;'],
                shade: 0.1,
                // button:null,
                closeBtn: 0,
                shadeClose: false,
                move: '.layui-layer-title',
                area: ["100%","100%"],
                fixed: true,
                resize: false,
                background:'#F0F0F0',
                content:["../common/F40017.html"],
                end: function(){
                    $.ajax({
                        url: ctxPath + '/manager/f00002/init',
                        type: 'GET',
                        dataType: 'json',
                        async: true,
                        success: function (data) {
                            if (data.code == 0) {
                                if (data.manager) {
                                    $('#email').val(data.manager.mailad);
                                    vm.updateTime = data.updateTime;
                                }
                            } else {
                                showMsg(data.msg);
                            }
                        }
                    });
                }
            })
        },
        submitFn: function () {
            formatCheck();
            if ($("#f00002Form").valid() == true) {
                $.ajax({
                    url: ctxPath + '/manager/f00002/submit',
                    data: {
                        id: window.id,
                        flnmNm: $('#flnmNm').val(),
                        flnmLnm: $('#flnmLnm').val(),
                        flnmKnNm: $('#flnmKnNm').val(),
                        flnmKnLnm: $('#flnmKnLnm').val(),
                        telnum: $('#telnum').val(),
                        mailad: $('#email').val(),
                        updateTime: vm.updateTime
                    },
                    dataType: 'json',
                    type: 'POST',
                    async: true,
                    success: function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0015, "基本情報"), {btn: ["確認"]}, function () {
                                // if (param.flag == 1) {
                                //     window.open("../manager/F00001.html", "_self")
                                // }
                                // else {
                                //     window.history.go(-1);
                                // }
                                window.open("../manager/F00001.html", "_self")
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
    $("#f00002Form").validate({
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
                minlength: 10,
                maxlength: 11,
                digits:true
            },
            email: {
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
                minlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                digits: $.format($.msg.MSGCOMD0006, "電話番号")
            },
            // メール
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
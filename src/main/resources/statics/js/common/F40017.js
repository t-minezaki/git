var flag = 0;
var roleDiv = '';
/*var fstLoginFlg = '';*/
var gidRuleFlg = '';
var manaRuleFlg= '';
var perlInfoRuleFlg= '';
var is_mobi = getCookie("equipment") === "phone";
$(function () {
    if (is_mobi) {
        $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/common/F40017_mobi.css"}).appendTo("head");
        $(".div_message_three").empty();
        $(".div_input_one").html("確認用メールを送信");
    } else {
        $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/common/F40017_pc.css"}).appendTo("head");
    }
    if (flag === 0){
        $.ajax({
            url: ctxPath + '/common/F40017/init',
            type: 'get',
            success: function (data) {
                if (data.code === 0) {
                    roleDiv = data.roleDiv;
                    fstLoginFlg = data.fstLoginFlg;
                    gidRuleFlg = data.gidRuleFlg;
                    manaRuleFlg = data.manaRuleFlg;
                    perlInfoRuleFlg = data.perlInfoRuleFlg;
                } else {
                    if (is_mobi) {
                        layer.alert(data.msg);
                    } else {
                        showMsg(data.msg)
                    }
                }
            }
        });
    }
});
$('#submit').click(function () {
    var inputVal = $("#input").val();
    formatCheck()
    if ($("#f40017Form").valid()){
        $.ajax({
            url: ctxPath + '/common/F40017/submit',
            type: 'POST',
            data: {
                mail: inputVal
            },
            success: function (data) {
                if (data.code === 0) {
                    window.location.href = ctxPath + "/common/F40018.html";
                } else {
                    if (is_mobi) {
                        layer.alert(data.msg);
                    } else {
                        showMsg(data.msg);
                    }
                }
            }
        });
    }
});
var validateFlag = true;
function formatCheck() {
    $("#f40017Form").validate({
        rules: {
            input: {
                required: true,
                email: true,
                maxlength:50
            },
        },
        debug:true,
        //トリガーイベント2020/11/24 modify yang start--
        onfocusout: false,
        onkeyup: false,
        messages: {
            input: {
                required:$.format($.msg.MSGCOMD0001,"メールアドレス"),
                email:$.format($.msg.MSGCOMD0018, "メールアドレス"),
                maxlength:$.format($.msg.MSGCOMD0011,"メールアドレス","50")
            }
        },
        showErrors:function(errorMap,errorList) {
            if (errorList.length != 0) {
                if (validateFlag ==true ) {
                    if (is_mobi) {
                        validateFlag = false;
                        layer.alert(errorList[0].message);
                        $(".layui-layer-btn0").click(function () {
                            validateFlag = true;
                        })
                    }else {
                        showMsg(errorList[0].message);
                    }
                }
            }
        }
    });
}

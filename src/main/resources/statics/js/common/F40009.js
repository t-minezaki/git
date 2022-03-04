//メールアドレスが一致するかどうかを確認する。
var param = new getParam()
function getInto() {
    var id = $("input[name='usrId']").val();
    var email = $("input[name='email']").val();
    //check
    formatCheck();
    if ($("#f40009Form").valid() == true){
        $.ajax({
            url: ctxPath + '/com/F40009/doPostEmail',
            type: 'POST',
            data: { "id":id,
                "email":email,
                "brandCd":param.brandCd
            },
            success: function (data) {
                if (data.code == 0) {
                    layer.alert($.msg.MSGCOMN0089);
                } else {
                    showMsg(data.msg);
                }
            }
        })
    }
}
function gotoLogin() {
    var url =  "/login";
    if (param.brandCd && param.brandCd != "") {
        url = url + "/" + param.brandCd;
    }
    window.open(ctxPath + url,'_self');
}
function formatCheck() {
    $("#f40009Form").validate({
        rules: {
            usrId:{
                required: true,
                alphaNumSymbol: true,
                minlength: 8,
                maxlength: 32
            },
            email: {
                required: true,
                email: true,
                maxlength:50
            },
        },
        debug:true,
        messages: {
            email: {
                required:$.format($.msg.MSGCOMD0001,"メールアドレス"),
                email:$.format($.msg.MSGCOMD0018, "メールアドレス"),
                maxlength:$.format($.msg.MSGCOMD0011,"メールアドレス","50")
            },
            userId: {
                required: $.format($.msg.MSGCOMD0001, "ＩＤ"),
                minlength: $.format($.msg.MSGCOMD0016, "ＩＤ", "8"),
                maxlength: $.format($.msg.MSGCOMD0017, "ＩＤ", "32"),
                // hjx : 2019/11/08 mod  hasAlphaNum  =>  alphaNumSymbol
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "ＩＤ")
            }
        },
        showErrors:function(errorMap,errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
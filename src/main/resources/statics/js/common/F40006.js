//メールアドレスが一致するかどうかを確認する。
var param = new getParam()
$(function () {
    if (getCookie("key") === 'PUSHAPI'){
        $("#back").css("display","none");
        $("#submit").css("margin-left","30%");
    }
});
function getInto() {
    var id = $("input[name='usrId']").val();
    var email = $("input[name='email']").val();
    //check
    formatCheck();
    if ($("#f40006Form").valid() == true){
        $.ajax({
            url: ctxPath + '/com/F40006/reset',
            type: 'POST',
            data: { "id":id,
                "email":email,
                "brandCd":param.brandCd
            },
            success: function (data) {
                /* 2021/03/09 manamiru4-37 add start */
                if (data.code == 0){
                    if (data.success == true) {
                        //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
                        // 2021/10/11　MANAMIRU1-776 cuikl　Edit　Start
                        // var userInfo = {
                        //     afterUserId:data.afterUserId,
                        //     gidFlg:data.gidFlg,
                        //     gidPk:data.gidPk
                        // }
                        window.open('./F40007.html?brandCd=' + param.brandCd + "&type=" + param.type, "_self");
                        // 2021/10/11　MANAMIRU1-776 cuikl　Edit　End
                        //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
                    } else {
                        showMsg($.format($.msg.MSGCOMN0080));
                    }
                } else {
                    showMsg(data.msg);
                }
                /* 2021/03/09 manamiru4-37 add end */
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
// 'min length 8
// max length 32
function formatCheck() {
    $("#f40006Form").validate({
        rules: {
            usrId:{
                required: true,
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
        //トリガーイベント2020/11/24 modify yang start--
        onfocusout: false,
        onkeyup: false,
        //トリガーイベント2020/11/24 modify yang end--
        messages: {

            email: {
                required:$.format($.msg.MSGCOMD0001,"メールアドレス"),
                email:$.format($.msg.MSGCOMD0018, "メールアドレス"),
                maxlength:$.format($.msg.MSGCOMD0011,"メールアドレス","50")
            },
            //最大長と最小長を設定します
            usrId: {
                required:$.format($.msg.MSGCOMD0001,"ID"),
                minlength: $.format($.msg.MSGCOMD0010, "ID", "8", "32"),
                maxlength: $.format($.msg.MSGCOMD0010, "ID", "8", "32")
            }
        },
        showErrors:function(errorMap,errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
// 'アプリ側から遷移した場合、非表示。
// ※URL中のtype="resetpwd"
$(function () {
    if (param.type && param.type == 'resetpwd') {
        $(".left").css('visibility', 'hidden');
    }
})
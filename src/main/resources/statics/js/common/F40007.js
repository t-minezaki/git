var param = new getParam();
$(function () {
    if (getCookie("key") === 'PUSHAPI'){
        $("#back").css("display","none");
        $("#submit").css("margin-left","30%");
    }
});
$(function () {
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
    // 2021/10/11　MANAMIRU1-776 cuikl　Edit　Start
    // if (param.userInfo == null) {
    // 2021/10/11　MANAMIRU1-776 cuikl　Edit　End
    //     if (param.sessionId == null || param.sessionId === "underfind") {
    //         window.open("./F40006.html", "_self");
    //     }
    // }
    $.ajax({
        url: ctxPath + '/com/F40007/init2',
        type: 'get',
        success: function (da) {
            if (da.code === 500) {
                window.location.href = da.url;
            }
        }
    })
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
});

$(function () {
    if (param.urlFlg === 1 || param.urlFlg === "1") {
        $.ajax({
            url: ctxPath + '/com/F40007/init',
            type: 'get',
            data: {
                sessionId: param.sessionId,
                brandCd: param.brandCd,
                token : param.token
            },
            success: function (da) {
                if (da.code === 500) {
                    window.location.href = da.url;
                }
            }
        })
    }
    if (param.type && param.type === 'resetpwd'){
        $('.left').css('visibility', 'hidden');
    }
});

// チェック
function getInto() {
    var password = $("input[name='password']").val();
    var ensurePwd = $("input[name='ensurePwd']").val();
    // check
    if (passwordCheck(password) && ensurePwdCheck(ensurePwd) && compareTwoPwd(password, ensurePwd)) {
        //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
        // 2021/10/11　MANAMIRU1-776 cuikl　Edit　Start
        // var userInfo = JSON.parse(decodeURI(param.userInfo));
        // userInfo.password = password;

        $.ajax({
            url: ctxPath + '/com/F40007/update',
            type: 'POST',
            data: JSON.stringify({password: password}),
            async: false,
            contentType: "application/json; charset=utf-8",
            // 2021/10/11　MANAMIRU1-776 cuikl　Edit　End
            //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
            success: function (data) {
                if (data.success) {
                    var index = layer.confirm("設定しました", {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: [ '確認'],
                        btn1: function () {
                            layer.close(index);
                            return;
                        }
                    })
                }else {
                    if (getEquipment() === 'otherEquip'){
                        showMsg(data.msg);
                    }else {
                        layer.alert(data.msg);
                    }
                }
            }
        })
    }
}

// パスワードチェック
function passwordCheck(password) {
    var count = 0;
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
    // nullCheck
    if (password.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "新パスワード（1回目）"));
        count = 1;
    }
    // lengthCheck
    else if (password.length > 32 || password.length < 8) {
        showMsg($.format($.msg.MSGCOMD0010, "新パスワード（1回目）", "8", "32"));
        count = 1;
    }
    // formatCheck
    else if (!reg.test(password)) {
        count = 1;
        showMsg($.format($.msg.MSGCOMD0022, "新パスワード"));
    }
    // angleCheck
    for (var i = 0; i < password.length; i++) {
        if (32 > password.charCodeAt(i) || password.charCodeAt(i) > 127) {
            count = 1;
            showMsg($.format($.msg.MSGCOMD0004, "新パスワード（1回目）"));
            break;
        }
    }
    if (count == 0) {
        return true;
    } else {
        return false;
    }
}

// 重複パスワードチェック
function ensurePwdCheck(ensurePwd) {
    var count = 0;
    // nullCheck
    if (ensurePwd.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "新パスワード（2回目）"));
        count = 1;
    }
    // lengthCheck
    else if (ensurePwd.length > 32 || ensurePwd.length < 8) {
        showMsg($.format($.msg.MSGCOMD0011, "新パスワード（2回目）", "8", "32"));
        count = 1;
    }
    // angleCheck
    for (var i = 0; i < ensurePwd.length; i++) {
        if (32 > ensurePwd.charCodeAt(i) || ensurePwd.charCodeAt(i) > 127) {
            count = 1;
            showMsg($.format($.msg.MSGCOMD0004, "新パスワード（2回目）"));
            break;
        }
    }
    if (count == 0) {
        return true;
    } else {
        return false;
    }
}

// パスワード比較チェック
function compareTwoPwd(password, ensurePwd) {
    if (password != ensurePwd) {
        showMsg($.format($.msg.MSGCOMN0059, "新しいパスワードとパスワード再入力"));
        return false;
    } else {
        return true;
    }
}

function gotoLogin() {
    var url = "/login";
    if (param.brandCd && param.brandCd != "") {
        url = url + "/" + param.brandCd;
    }
    window.open(ctxPath + url, '_self');
}
function getEquipment() {
    var equipment = 'phone';
    var os = function () {
        var ua = navigator.userAgent,
            isWindowsPhone = /(?:Windows Phone)/.test(ua),
            isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
            isAndroid = /(?:Android)/.test(ua),
            isFireFox = /(?:Firefox)/.test(ua),
            isChrome = /(?:Chrome|CriOS)/.test(ua),
            isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isChrome && /(?:Tablet)/.test(ua)),
            isPhone = /(?:iPhone)/.test(ua) && !isTablet,
            isPc = !isPhone && !isAndroid && !isSymbian;
        return {
            isTablet: isTablet,
            isPhone: isPhone,
            isAndroid: isAndroid,
            isPc: isPc
        };
    }();
    if (os.isAndroid || os.isPhone) {
        equipment = 'phone';
    }
    if (os.isTablet || (window.orientation == 90 || window.orientation == -90)){
        equipment = 'tablet';
    }else if(!os.isAndroid && !os.isPhone) {
        equipment = 'otherEquip';
    }
    return equipment;
}
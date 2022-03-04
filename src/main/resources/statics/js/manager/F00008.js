/**
 * ページ初期化
 */
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.25;
    $(".bottomDiv").css("height", srcHeight);
    getInfo();
});

/**
 * パスワード初期化
 */
function passWordInit() {
    if ($("input[name='inputId']").val().length == 0){
        showMsg($.format($.msg.MSGCOMD0001,"ログインID"));
    }else {
        $.ajax({
            url: ctxPath + '/manager/f00008upd',
            type: 'POST',
            data: {
                "userId":$("input[name='inputId']").val()
            },
            async: true,
            success: function (data) {
                if (data.code != 0){
                    showMsg(data.msg);
                }
                else {
                    $("#newPwd").text(data.newPwd);
                }
            }
        })
    }
}

/**
 * ページ初期化
 */
function getInfo() {

    $.ajax({
        url: ctxPath + '/manager/f00008init',
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                $("#orgId").text(data.upOrg.orgId);
                $("#orgNm").text(getOrgName(data.upOrg.orgNm));
            }

        }
    })
}


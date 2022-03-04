var is_mobi = getCookie("equipment") === "phone";
$(function () {
    if (is_mobi) {
        $("<link>").attr({
            rel: "stylesheet",
            type: "text/css",
            href: ctxPath + "/css/common/F40018_mobi.css"
        }).appendTo("head");
        $(".div_message_one").html("確認用のメールアドレス宛に、");
        $(".div_message_two").html("メールをお送りしました。");
        $(".div_message_three").html("メールに記載されたワンタイムパスワードを入力してください。");
        $(".div_message_four").html("docomo、au、softbankなど各キャリアのセキュリティ設定や、お客様が迷惑メール対策等で、ドメイン指定受信を設定されている場合に、メー ルが正しく届かないことがございます。以下のドメインを受信できるように設定してください。");
        /* 2021/4/16 manamiru1-623 cuikailin strat */
        $(".div_message_five").html("@manamiru.com");
        /* 2021/4/16 manamiru1-623 cuikailin end */
    } else {
        $("<link>").attr({
            rel: "stylesheet",
            type: "text/css",
            href: ctxPath + "/css/common/F40018_pc.css"
        }).appendTo("head");
    }
});
$('#confirm').click(function () {
    var inputVal = $("#input").val();
    if (inputVal === '') {
        if (is_mobi) {
            layer.alert($.format($.msg.MSGCOMD0001, 'ワンタイムパスワード'));
        } else {
            showMsg($.format($.msg.MSGCOMD0001, 'ワンタイムパスワード'));
        }
        return;
    }
    $.ajax({
        url: ctxPath + '/common/F40018/confirm',
        type: 'POST',
        data: {
            password: inputVal
        },
        success: function (data) {
            if (data.code === 0) {
                window.location.href = ctxPath + "/common/F40020.html";
            } else {
                if (data.flag == "0") {
                    window.location.href = ctxPath + "/common/F40019.html"
                }
                if (data.flag == "1") {
                    if (is_mobi) {
                        layer.alert(data.msg);
                    } else {
                        showMsg(data.msg)
                    }
                }
            }
        }
    });
});
$('#back').click(function () {
    window.location.href = ctxPath + "/common/F40017.html";
});

$(function() {
    /**
     * 全角文字（ひらがな・カタカナ・漢字 etc.）チェック
     */
    $.validator.addMethod("zenkaku", function (value, element, parameter) {
        // 空白の場合、何もしない
        if (value === '') return true;
        // 「\uFF61」〜「\uFF9F」の範囲はUnicodeで半角カナの範囲。
        // 2020/12/15 huangxinliang modify start
        return (/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test(value) && /[^　]+$/.test(value)) ? true : false;
        // 2020/12/15 huangxinliang modify end
    }, $.msg.MSGCOMD0007);

    /**
     * 半角文字チェック（半角英数符号と半角カナ）
     */
    $.validator.addMethod("hankaku", function (value, element, parameter) {
        // 空白の場合、何もしない
        if (value === '') return true;
        // 「\uFF61」〜「\uFF9F」の範囲はUnicodeで半角カナの範囲。
        return (/^[\x01-\x7E\uFF61-\uFF9F]+$/.test(value)) ? true : false;
    }, $.msg.MSGCOMD0003);

    /**
     * 半角英数字[a-zA-Z0-9]
     */
    $.validator.addMethod("alphaNumSymbol", function(value, element) {
        return this.optional(element) || /^[A-Za-z0-9]*$/.test(value);
    }, $.msg.MSGCOMD0004);

    /**
     * 半角英数字[a-zA-Z0-9]必須存在
     */
    $.validator.addMethod("hasAlphaNum", function(value, element) {
        // var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[])+$)([^(0-9a-zA-Z)]|[]|[a-zA-Z]|[0-9]){3,20}$/;
        var reg = /^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]+$/;
        return this.optional(element) || reg.test(value);
    }, $.msg.MSGCOMD0022);

    /**
     * パスワードフォーマットの検証
     */
    jQuery.validator.addMethod("pwd", function(value, element) {
        var reg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z\d]{8,32}$/;
        return this.optional(element) || reg.test(value);
    }, "パスワードはありますが、大文字と数字だけです。");
});

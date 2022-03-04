$(function () {

    // rightボタン押下後
    $("#right").click(function () {

        var inputs = $("#form_left").children().find("input[type='checkbox']:checked");
        $("#form_left").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_right");
        inputs.prop("checked", false);
    });

    // right_allボタン押下後
    $("#right_all").click(function () {

        var inputs = $("#form_left").children().find("input[type='checkbox']");
        $("#form_left").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_right");
        inputs.prop("checked", false);
    });

    // leftボタン押下後
    $("#left").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
                $(o).parent().appendTo("#form_left");
                $(o).prop("checked", false);
            }
        })
    });

    // left_allボタン押下後
    $("#left_all").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
                $(o).parent().appendTo("#form_left");
                $(o).prop("checked", false);
            }
        })
    });

    // 検索ボタン押下後
    $("#search_left_btn").click(function () {

        var txt = $("#search_left").val();
        var children = $("#form_left").children().find("input[type='checkbox']").parent();
        if (txt.length <= 0) {
            children.css("display", "block");
        } else {
            children.css("display", "none");
            $("#form_left").children().find("input[content*='" + txt + "']").parent().css("display", "block");
        }
    });

    // 検索ボタン押下後
    $("#search_right_btn").click(function () {

        var txt = $("#search_right").val();
        var children = $("#form_right").children().find("input[type='checkbox']").parent();
        if (txt.length <= 0) {
            children.css("display", "block");
        } else {
            children.css("display", "none");
            $("#form_right").children().find("input[content*='" + txt + "']").parent().css("display", "block");
        }
    });

    // upボタン押下後
    $("#up").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                var prev = $(o).first().parent().prev();
                if ($(prev.children()[0]).val() != parent.mustShow[parent.mustShow.length-1]) {
                    prev.before($(o).parent());
                }
            }
        });
    });

    // up_allボタン押下後
    $("#up_all").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                var after = $("#form_right").children()[parent.mustShow.length - 1];
                $(after).after($(o).parent());
            }
        });

    });

    // downボタン押下後
    $("#down").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                var prev = $(o).first().parent().next();
                if ($(prev.children()[0]).val() != parent.mustShow[parent.mustShow.length-1]) {
                    prev.after($(o).parent());
                }
            }
        });
    });

    // down_allボタン押下後
    $("#down_all").click(function () {
        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        inputs.each(function (e, o) {
            if (parent.mustShow.indexOf($(o).val()) == -1){
                var after = $("#form_right").children()[$("#form_right").children().length - 1];
                $(after).after($(o).parent());
            }
        });
    });

    // サブウィンドウインデックスを取得する
    var index = parent.layer.getFrameIndex(window.name);

    // confボタン押下後
    $("#conf").click(function () {

        var colList = [], hiddenList = [];
        var inputs = $("#form_left").children().find("input[type='checkbox']");
        for (var i = 0; i < inputs.length; i++) {
            hiddenList.push(inputs[i].value);
        }
        inputs = $("#form_right").children().find("input[type='checkbox']");
        for (var i = 0; i < inputs.length; i++) {
            colList.push(inputs[i].value);
        }
        $.get(ctxPath + "/manager/F08011/getDspCount",
            function (rsp) {
                if (rsp.dspcount <= 0) {
                    //若dsp表中不存在該用戶的設定
                    $.post(ctxPath + "/manager/F08011/saveDspItems", {
                        dspitems: JSON.stringify(colList)
                    }, function (){
                        parent.time = 1;
                        parent.reload(colList, hiddenList);
                        parent.layer.close(index);
                    });
                } else {
                    //若dsp表中存在該用戶的設定
                    $.post(ctxPath + "/manager/F08011/updDspItems", {
                        dspitems: JSON.stringify(colList)
                    }, function (){
                        parent.time = 1;
                        parent.reload(colList, hiddenList);
                        parent.layer.close(index);
                    });
                }
            });
    });
    // closeボタン押下後
    $("#close_btn").click(function () {

        parent.layer.close(index);
    });
});
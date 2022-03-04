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
        var able_inputs = [];
        for (var i = 0; i < inputs.length; i ++) {
            if (parent.notDspList.indexOf(inputs[i].value) < 0) {
                able_inputs.push(inputs[i]);
            }
        }
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        $(able_inputs).parent().appendTo("#form_left");
        $(able_inputs).prop("checked", false);
    });

    // left_allボタン押下後
    $("#left_all").click(function () {
        var inputs = $("#form_right").children().find("input[type='checkbox']");
        var able_inputs = [];
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        for (var i = 0; i < inputs.length; i ++) {
            if (parent.notDspList.indexOf(inputs[i].value) < 0) {
                able_inputs.push(inputs[i]);
            }
        }
        $(able_inputs).parent().appendTo("#form_left");
        $(able_inputs).prop("checked", false);
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
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        var prev = inputs.first().parent().prev();
        if (prev) {
            prev.before(inputs.parent());
        }
    });

    // up_allボタン押下後
    $("#up_all").click(function () {
        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        $("#form_right").prepend(inputs.parent());
    })  ;

    // downボタン押下後
    $("#down").click(function () {
        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        var prev = inputs.last().parent().next();
        if (prev) {
            prev.after(inputs.parent());
        }
    });

    // down_allボタン押下後
    $("#down_all").click(function () {
        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        $("#form_right").append(inputs.parent());
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
        $.get(ctxPath + "/manager/F08024/getDspCount",
            function (rsp) {
                if (rsp.dspcount <= 0) {
                    //若dsp表中不存在該用戶的設定
                    $.post(ctxPath + "/manager/F08024/saveDspItems", {
                        dspitems: JSON.stringify(colList)
                    });
                } else {
                    //若dsp表中存在該用戶的設定
                    $.post(ctxPath + "/manager/F08024/updDspItems", {
                        dspitems: JSON.stringify(colList)
                    });
                }
            });
        parent.reload(colList, hiddenList, null,null, null, null, null, null, null, null,null);
        parent.layer.close(index);
    });
    // closeボタン押下後
    $("#close_btn").click(function () {
        parent.layer.close(index);
    });
});
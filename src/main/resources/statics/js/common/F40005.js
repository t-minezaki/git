$().ready(function () {
    var param = getParam();
    $.ajax({
        url: ctxPath + '/common/F40005/init',
        data: {
            startDate:param.startDate,
            endDate:param.endDate
        },
        type: "GET",
        success: function (data) {
            $("#startInput").val(data.startDate);
            $("#endInput").val(data.endDate);
        }
    });
    laydate.render({
        elem: '#startTime',
        format: 'yyyy/MM/dd',
        done: function (value, date) {
            $("#startInput").val(value)
        }
    });
    laydate.render({
        elem: '#endTime',
        format: 'yyyy/MM/dd',
        done: function (value) {
            $("#endInput").val(value)
        }
    });

    $("#submitBtn").click(function () {
        if ($("#startInput").val() === ''){
            layer.alert($.format($.msg.MSGCOMD0001, "期間開始日"));
            return;
        }
        if ($("#endInput").val() === ''){
            layer.alert($.format($.msg.MSGCOMD0001, "期間終了日"));
            return;
        }
        $.ajax({
            url: ctxPath + '/common/F40005/submit',
            data: {
                start: $("#startInput").val(),
                end: $("#endInput").val()
            },
            type: "POST",
            success: function (data) {
                if (data.code == 0) {
                    if (param.id == 'f20008') {
                        var subjtDiv = $("#subjtDiv", window.parent.document).val();
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF20008Data('', subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    if (param.id == 'f20009') {
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF20009Data('', parent.param.subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    if (param.id == 'f10401') {
                        var subjtDiv = $("#subjtDiv", window.parent.document).val();
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF10401Data(0,'', subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    if (param.id == 'f10402') {
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF10402Data(0,'', parent.param.subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    if (param.id == 'f30104') {
                        var subjtDiv = $("#subjtDiv", window.parent.document).val();
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF30104Data(0,'', subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                    if (param.id == 'f30105') {
                        $(".active", window.parent.document).removeClass("active");
                        parent.showF30105Data(0,'', parent.param.subjtDiv, $("#startInput").val(), $("#endInput").val());
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                } else {
                    parent.layer.alert(data.msg);
                }
            }
        });
    });
    $("#cancelBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    })
});
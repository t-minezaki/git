var param = getParam();
var upDtTm = "";

$(function () {
    $.ajax({
        url: ctxPath + '/manager/F00052/org',
        type: 'GET',
        success: function (data) {
            $("#orgId").text(data.org.orgId);
            $("#orgNm").text(getOrgName(data.org.orgNm));
        }
    })
});

$(function () {
    if (param.id && param.id != 'underfined') {
        $.ajax({
            url: ctxPath + '/manager/F00052/init',
            type: 'GET',
            data: {
                id: param.id
            },
            success: function (data) {
                if (data.code === 0) {
                    if (data.grpNm) {
                        $("#grpNm").val(data.grpNm);
                    }
                    if (data.upDtTm) {
                        upDtTm = data.upDtTm;
                    }
                    var _days = [];
                    if (data.mstGrpEntity) {
                        _days = data.mstGrpEntity.dayweekDiv.split(",");
                        for (var i = 0; i < _days.length; i++) {
                            $("input[value = '"+_days[i].trim()+"']").attr("checked",true);
                        }
                    }
                } else {
                    showMsg(data.msg);
                }
            }
        })
    }
});

function updateBtn() {
    var id;
    if (param.id != 'underfined') {
        id = param.id;
    }
    var grpNm = $("#grpNm").val().trim();
    if (!grpNm || grpNm.length === 0) {
        showMsg($.format($.msg.MSGCOMD0001, "グループ名"));
        return;
    }
    if ($("#grpNm").val().length > 50){
        showMsg($.format($.msg.MSGCOMD0017, "グループ名","50"));
        return;
    }
    var dayweekDivArr = new Array();
    $('input[class="_days"]:checked').each(function () {
        dayweekDivArr.push($(this).val());
    })
    var dayweekDiv = dayweekDivArr.join(",");
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        closeBtn: 0,
        title: '確認',
        btn: ["キャンセル", "確認"],
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00052/update',
                type: 'GET',
                data: {
                    grpNm: grpNm,
                    dayweekDiv: dayweekDiv,
                    grpId: id,
                    updDtTm: upDtTm
                },
                success: function (data) {
                    if (data.code === 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                        //     closeBtn: 0,
                        //     title: '確認',
                        //     btn: ["確認"],
                        //     btn1: function () {
                                window.location.href = "F00051.html";
                        //     }
                        // });
                    } else {
                        index = layer.close(index);
                        showMsg(data.msg);
                    }
                }
            })
        },
        btn1: function () {
            index = layer.close(index);
        }
    });
}
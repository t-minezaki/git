$(function () {
    var srcHeight = $(window).height() - ($(window).width() / 100) * 7;
    $(".content").css("height", srcHeight)
});
var param = getParam();
var upDtTm = "";
var mentorId = "";
var stuId = "";

$(function () {
    $.ajax({
        url: ctxPath + '/manager/F00062/org',
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
            url: ctxPath + '/manager/F00062/init',
            type: 'GET',
            data: {
                id: param.id
            },
            success: function (data) {
                if (data.code === 0) {
                    if (data.showData) {
                        //メンター
                        $("#mentorId").val(data.showData.mentorId);
                        if (data.showData.mentor) {
                            mentorId = data.showData.mentor;
                        }
                        //メンター姓名
                        $("#mentorNm").val(data.showData.mentorSei + ' ' + data.showData.mentorMei);
                        //メンターカナ姓名
                        $("#mentorKnNm").val(data.showData.mentorKnSei + ' ' + data.showData.mentorKnMei);
                        $("#mailad").val(data.showData.mailad);
                        $("#telnum").val(data.showData.telnum);
                        //生徒
                        $("#stuId").val(data.showData.stuId);
                        if (data.showData.stu) {
                            stuId = data.showData.stu;
                        }
                        $("#stuNm").val(data.showData.stuSei + ' ' + data.showData.stuMei);
                        $("#stuKnNm").val(data.showData.stuKnSei + ' ' + data.showData.stuKnMei);
                        $("#birthd").val(data.showData.birth);
                        $("#sex").val(data.showData.sex);
                        $("#schy").val(data.showData.schy);
                        upDtTm = data.showData.updDatm;
                    }
                } else {
                    showMsg(data.msg);
                }
            }
        })
    }
});

function selectBtn(roleDiv) {
    layer.open({
        id: 'f00055',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F00055.html?roleDiv=" + roleDiv, 'no']
    })
}

function showF00055Data(usrId, roleDiv) {
    $.ajax({
        url: ctxPath + "/manager/F00062/select",
        type: "get",
        data: {
            usrId: usrId,
            roleDiv: roleDiv
        },
        success: function (data) {
            if (data.code === 0) {
                if (data.showData) {
                    if (roleDiv === "2") {
                        //メンター
                        $("#mentorId").val(data.showData.mentorId);
                        if (data.showData.mentor) {
                            mentorId = data.showData.mentor;
                        }
                        //メンター姓名
                        $("#mentorNm").val(data.showData.mentorSei + ' ' + data.showData.mentorMei);
                        //メンターカナ姓名
                        $("#mentorKnNm").val(data.showData.mentorKnSei + ' ' + data.showData.mentorKnMei);
                        $("#mailad").val(data.showData.mailad);
                        $("#telnum").val(data.showData.telnum);
                    }
                    if (roleDiv === "4") {
                        //生徒
                        $("#stuId").val(data.showData.stuId);
                        if (data.showData.stu) {
                            stuId = data.showData.stu;
                        }
                        $("#stuNm").val(data.showData.stuSei + ' ' + data.showData.stuMei);
                        $("#stuKnNm").val(data.showData.stuKnSei + ' ' + data.showData.stuKnMei);
                        $("#birthd").val(data.showData.birth);
                        $("#sex").val(data.showData.sex);
                        $("#schy").val(data.showData.schy);
                    }
                }
            }
        }
    })
}

function updateBtn() {
    var id;
    if (param.id != 'underfined') {
        id = param.id;
    }
    if (!mentorId || mentorId.trim().length === 0) {
        showMsg($.format($.msg.MSGCOMN0096, "先生", "検索"));
        return;
    }
    if (!stuId || stuId.trim().length === 0) {
        showMsg($.format($.msg.MSGCOMN0096, "生徒", "検索"));
        return;
    }
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        closeBtn: 0,
        title: '確認',
        btn: ["キャンセル", "確認"],
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00062/update',
                type: 'GET',
                data: {
                    stu: stuId,
                    mentor: mentorId,
                    updDatm: upDtTm,
                    id: id
                },
                success: function (data) {
                    if (data.code === 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                        //     closeBtn: 0,
                        //     title: '確認',
                        //     btn: ["確認"],
                        //     btn1: function () {
                                window.location.href = "F00061.html";
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
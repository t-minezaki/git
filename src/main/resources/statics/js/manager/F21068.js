//画面．検索条件．組織ＩＤ
var orgId;
//画面．検索条件．メッセージID
var id;
//画面．検索条件．件名
var messageTitle;
//画面．検索条件．掲載開始日時
var pubPlanStartDt;
//画面．検索条件．掲載終了日時
var pubPlanEndDt;

var times = 0;

$(function () {

    var width = $(window).width() * 0.99;
    var srcHeight = $(window).height() - $(window).width() * 0.21 - 62 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F21068/init',
            datatype: "json",
            postData: {},
            colModel: [
                {label: '組織ID', name: 'orgId', index: 'orgId', width: 40, key: true, sortable: false, align: "center"},
                {label: 'インフォメーションＩＤ', name: 'id', index: 'id', width: 60, sortable: false, align: "center"},
                {
                    label: '重要度',
                    name: 'messageLevelDiv',
                    index: 'messageLevelDiv',
                    width: 30,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {
                        if (object.messageLevelDiv == 2) {
                            return "★";
                        } else {
                            return "";
                        }
                    }
                },
                {label: '件名', name: 'messageTitle', index: 'messageTitle', width: 100, sortable: false, align: "center"},
                {
                    label: '掲載期間',
                    name: 'pubPlanStartToEndTime',
                    index: 'pubPlanStartToEndTime',
                    width: 110,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {
                        return moment(object.pubPlanStartDt).format("YYYY/MM/DD HH:mm") + "～" + moment(object.pubPlanEndDt).format("YYYY/MM/DD HH:mm");
                    }
                },
                {
                    label: '最新更新日時', name: 'updDatime', index: 'updDatime', width: 60, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return moment(object.updDatime).format("YYYY/MM/DD HH:mm");
                    }
                },
                {
                    label: '操作',
                    name: 'orgFlg',
                    index: 'orgFlg',
                    width: 210,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {

                        if (object.orgFlg == 1) {
                            return "<button onclick=window.location.href='./F21071.html?id=" + object.id + "'> 確認 </button>&nbsp;&nbsp;" + "<button" +
                                "  onclick=window.location.href='./F21073.html?id=" + object.id + "'>詳細</button>&nbsp;&nbsp;" + "<button  onclick=window.location.href='./F21070.html?id=" + object.id + "'>編集</button>&nbsp;&nbsp;" + "<button  onclick='deleteButton(\"" + object.id + "\",\"" + object.updDatimeStr + "\"," + object.orgFlg + ");'>削除</button>";
                        } else {
                            return "<button  onclick=window.location.href='./F21073.html?id=" + object.id + "'>詳細</button>";
                        }
                    }
                }
            ],
            viewrecords: true,
            height: srcHeight,
            width: width,
            rowNum: 30,
            rowList: [10, 30, 50],
            rownumbers: false,
            rownumWidth: 25,
            autowidth: false,
            multiselect: false,
            pager: "#jqGridPager",
            jsonReader:
                {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                }
            ,
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            }
            ,
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            }
            ,
            loadComplete: function (data) {
                if (times == 0) {
                    if (data.orgNm) {
                        $("#orgNm").text(data.orgNm);
                    }
                    if (data.sOrgId) {
                        $("#sOrgId").text(data.sOrgId);
                    }
                }
                if (data.code != 0) {
                    showMsg(data.msg);
                }
            }
        }
    );
});

//検索ボタン押下
function searchButton() {
    times = 1;
    if (!formatCheck()) {
        return;
    }
    if ($("#f21068Form").valid() == true) {
        $("#jqGrid").jqGrid('setGridParam', {
            url: ctxPath + '/manager/F21068/search',
            datatype: "json",
            postData: {
                orgId: $('#orgId').val(),
                id: $('#id').val(),
                messageTitle: $('#messageTitle').val(),
                pubPlanStartDt: $('#pubPlanStartDt').val(),
                pubPlanEndDt: $('#pubPlanEndDt').val()
            },
            page: 0
        }).trigger("reloadGrid");
    }
}

//削除リンク押下
function deleteButton(id, updDatimeStr) {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F21068/delete',
                type: 'POST',
                data: {
                    id: id,
                    updDatimeStr: updDatimeStr
                },
                success: function (data) {
                    if (data.code === 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     shadeClose: true,
                        //     btn: ['確認'],
                        //     btn1: function () {
                                location.reload();
                        //     }
                        // })
                    } else {
                        showMsg(data.msg);
                    }
                }
            });

            layer.close(index);
        }
    })
}

$(function () {
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#pubPlanStartDt").val(value)
        }
    });
    laydate.render({
        elem: '#timeTwo',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#pubPlanEndDt").val(value)
        }
    });
});

//Check
function formatCheck() {
    $("#f21068Form").validate({
        rules: {
            orgId: {
                alphaNumSymbol: true
            },
            id: {
                number: true
            }
        },
        debug: false,
        onfocusout: false,
        onkeyup: false,
        messages: {
            // 組織ＩＤ
            orgId: {
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "組織ＩＤ")
            },
            // メッセージID
            id: {
                number: $.format($.msg.MSGCOMD0006, "インフォメーションID")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
    if ($('#pubPlanStartDt').val() !== '' && $('#pubPlanEndDt').val() !== '') {
        if ($('#pubPlanStartDt').val() > $('#pubPlanEndDt').val()) {
            showMsg($.format($.msg.MSGCOMN0024, "掲載開始日時", "掲載終了日時"));
            return false;
        }
    }
    return true;
}
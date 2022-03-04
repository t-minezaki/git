var param = getParam();

var codCd;
var codValue;
var sort;
var times = 0;
//初期表示
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.18 - 62 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F07001/init',
            datatype: "json",
            postData: {
                codValue: codValue
            },
            colModel: [
                {label: '学年CD', name: 'codCd', index: 'codCd', width: 10, key: true, sortable: false, align: "center"},
                {label: '学年名', name: 'codValue', index: 'codValue', width: 10, sortable: false, align: "center"},
                {label: 'ソート', name: 'sort', index: 'sort', width: 10, sortable: false, align: "center"},
                {
                    label: ' ', name: ' ', index: ' ', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<button onclick='toF07002(" + object.codCd + ")'> 編集</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='deleteButton(" + object.codCd + ")'> 削除</button>"
                    }
                }
            ],
            viewrecords: true,
            height: srcHeight,
            rowNum: 30,
            rowList: [10, 30, 50],
            rownumbers: false,
            rownumWidth: 25,
            autowidth: true,
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
                rows: "limit"
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
                $("#orgNm").text(data.orgNm);
                $("#orgId").text(data.orgId);
                if (data.code == 0) {
                } else {
                    showMsg(data.msg);
                }

            }
        }
    );
});

//検索ボタン押下
$("#select").click(function () {
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F07001/search',
        datatype: "json",
        postData: {
            codValue: $("#CV_area").val()
        },
    }).trigger("reloadGrid");
});
//新規作成ボタン押下
$("#add").click(function () {
    layer.open({
        id: 'f07002Add',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['45%', '50%'],
        fixed: true,
        resize: false,
        content: ["../pop/F07002.html"],
        success: function (layero, index) {
        }
    })
})

//編集ボタン押下
function toF07002(codCd) {
    layer.open({
        id: 'f07002Edit',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F07002.html?codCd=" + codCd + ""],
        success: function (layero, index) {
        }
    })
}

// 削除リンク押下
function deleteButton(codCd) {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F07001/delete',
                type: 'POST',
                data: {
                    codCd: codCd
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var idx = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                                location.reload();
                            //     layer.close(idx);
                            // }
                        // })

                    }
                }
            });
        }
    })
}

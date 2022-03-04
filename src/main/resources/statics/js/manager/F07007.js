var param = getParam();
var times = 0;
var id;
var upid;
//初期表示
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.21 - 62 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F07007/init',
            datatype: "json",
            // type: 'GET',
            postData: {id: param.id},
            colModel: [
                {
                    label: '小分類名',
                    name: 'blockDispyNm',
                    index: 'blockDispyNm',
                    width: 20,
                    key: true,
                    sortable: false,
                    align: "center"
                },
                {
                    label: 'ソート', name: '', index: 'blockTypeDiv', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return object.blockTypeDiv.substring(1);
                    }
                },
                {
                    label: ' ', name: ' ', index: ' ', width: 100, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<button onclick='toF07008(" + object.id + ")'> 編集</button>"
                        // window.location.href='../pop/F07008.html?id=" + object.id + "'
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick=deleteButton(" + object.id + ")> 削除</button>"
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
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            }
            ,
            loadComplete: function (data) {
                if (times == 0) {
                    if (data.orgNm) {
                        $("#orgNm").text(data.orgNm);
                    }
                    if (data.orgId) {
                        $("#orgId").text(data.orgId);
                    }
                    $("#blockDispyNm").text("大分類名：" + data.blockDispyNm);

                }
                if (data.code == 0) {
                    if (data.blockNull) {
                        showMsg(data.blockNull);
                    }
                } else {
                    showMsg(data.msg);
                }

            }
        }
    );
});
// function addButton() {
//     window.location.href='./F07008.html?upid='+param.id
// }
//編集ボタン押下
function toF07008(id) {
    layer.open({
        id: 'f07008Edit',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F07008.html?id=" + id + ""],
        success: function (layero, index) {
        }
    })
}

//新規作成ボタン押下
$("#add").click(function () {
    layer.open({
        id: 'f07008Add',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F07008.html?upid=" + param.id + ""],
        success: function (layero, index) {
        }
    })
})
// 削除リンク押下
function deleteButton(id) {
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
                url: ctxPath + '/manager/F07007/delete',
                type: 'POST',
                data: {
                    deleteId: id
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
                        //         layer.close(idx);
                        //     }
                        // })

                    }
                }
            });
        }
    })
}

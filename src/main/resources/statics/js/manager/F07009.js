var param = getParam();

var blockDispyNm;
var id;
var times = 0;
//画像がロードの場合、関数を実行します。
function getErrorpath(path) {
    var img = event.srcElement;
    if(img.src = path){img.onerror = null}
    else {img.src = path}
}
//初期表示
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.19 - 62 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F07009/init',
            datatype: "json",
            postData: {},
            colModel: [
                {
                    label: '固定ブロック名', name: 'BlockName', index: 'BlockName', width: 20, key: true, sortable:false,align: "center",
                    formatter(cell, option, object) {
                        return object.blockDispyNm;
                    }
                },
                {
                    label: 'ブロック画像', name: 'BlockPic', index: 'BlockPic', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.blockPicDiv != null) {
                            // return '<img id="BlockPic" src="../img/' + object.blockPicDiv + '" />';
                            return '<img id="BlockPic" src="'+object.updUsrId+object.blockPicDiv+'" onerror=getErrorpath("../img/' + object.blockPicDiv +'")>';
                        }
                        else {
                            return
                        }

                    }

                },
                {label: 'ソート', name: 'sort', index: 'sort', width: 20, sortable:false, align: "center", formatter(cell, option, object) {
                        var arr = (object.blockTypeDiv).split('');
                        return arr[1];
                    }},
                {
                    label: ' ', name: ' ', index: ' ', width: 100, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<button onclick= onclick=window.location.href='./F07010.html?id=" + object.id + "';> 編集</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='deleteButton(" + object.id + ")'> 削除</button>"
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
                if (data.code != 0) {
                    showMsg(data.msg);
                }

            }
        }
    );
});
$("#select").click(function () {
    selectLike();
});

//検索ボタン押下
function selectLike() {
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F07009/search',
        datatype: "json",
        postData: {
            DispyNm: $("#inputArea").val()
        },
    }).trigger("reloadGrid");
}

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
                url: ctxPath + '/manager/F07009/delete',
                type: 'POST',
                data: {
                    id: id
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

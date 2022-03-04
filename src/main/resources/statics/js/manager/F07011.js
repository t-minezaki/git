var param = getParam();
var schy;
var codCd;
var subName;
var times = 0;

//初期表示
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.19 - 62 - ($(window).width() / 100) * 5.2;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F07011/init',
            datatype: "json",
            postData: {
            },
            colModel: [
                {label: '学年', name: 'schy', index: 'schy', width: 80, key: true, sortable:false,align: "center"},
                {label: '教科CD', name: 'codCd', index: 'codCd', width: 80, sortable: false, align: "center"},
                {label: '教科名', name: 'subName', index: 'subName', width: 80, sortable:false, align: "center"},
                {label: 'ソート', name: 'sort', index: 'sort', width: 80, sortable:false, align: "center"},
                {label: '表示用画像', name: 'codCont', index: 'codCont', width: 80, sortable:false, align: "center",
                    formatter:function(cell, option, object) {
                            return '<img id="codCont" style="background-color: '+ object.backGround +';width: 30px;height: 30px" src=' + object.codCont + ' />';
                    }
                },
                {label: '背景色', name: 'backGround', index: 'backGround', width: 80, sortable:false, align: "center",
                    formatter: function (cell, option, object) {
                        return '<img style="background-color: '+ object.backGround +';width: 30px;height: 30px"/>';
                    }},
                {label: ' ', name: ' ', index: ' ', width:80, sortable: false, align: "center",
                    formatter:function(cell, option, object) {
                        return "<button onclick= window.location.href='./F07012.html?codCd=" + object.codCd + "';> 編集</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='deleteButton(\"" + object.codCd + "\")'> 削除</button>"
                    }
                },
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
                    if (times == 0) {
                        var str = "<option value=''></option>";
                        if (data.schyList) {
                            var arr = data.schyList;
                            for (var i = 0; i < arr.length; i++) {
                                str += "<option value='" + arr[i].codCd + "'>" + arr[i].codValue + "</option>";
                            }
                            $("#schy").html(str);
                        }
                    }
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
        url: ctxPath + '/manager/F07011/search',
        datatype: "json",
        postData: {
            schy: $("#schy").val(),
            codCd:$("#codCd").val(),
            subName:$("#subName").val()
        },
    }).trigger("reloadGrid");
});
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
                url: ctxPath + '/manager/F07011/delete',
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
                        //         layer.close(idx);
                        //     }
                        // })

                    }
                }
            });
        }
    })
}

var orgIdList = [];

$(function () {

    $(window).resize(function(){
        $("#jqGrid").setGridWidth($(window).width() * 0.98);
        $("#jqGrid").setGridHeight($(window).height() * 0.55);
    });

    $("#org_select").bind("click", function () {

        var srcWidth = "500px";
        var srcHeight = "500px";
        layer.open({
            id: 'F09010-1',
            type: 2,
            title: '組織指定',
            area: ['45%', '40%'],
            fixed: true,
            resize: false,
            anim: 2,
            skin: "layui-layer-myskin",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            content: ["../pop/F09010.html", 'no'],
            success: function (layero, index) {
            },
            // end : function(layero, index) {
            //     orgIdList =layero.find("iframe")[0].contentWindow.orgIdList;
            // }
        });
    });

    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F00051/init',
        postData: {
            orgId: window.parent.$("#orgList").val(),
            orgIdListStr: JSON.stringify(orgIdList)
        },
        datatype: "json",
        colModel: [
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 10, sortable: false, key: true, align: "center"},
            {label: 'グループＩＤ', name: 'grpId', index: 'grp_id', width: 10, sortable: false, key: true, align: "center"},
            {label: 'グループ名', name: 'grpNm', index: 'grp_nm', width: 20, sortable: false, align: "center"},
            {
                label: '曜日', name: 'dayWeekStr', index: 'dayWeekStr', width: 30, sortable: false, align: "center",
                formatter: function (cell, option, object) {
                    if (object.dayWeekStr){
                        return object.dayWeekStr
                    } else {
                        return ""
                    }
                }
            },
            {
                label: '',
                name: '',
                index: 'upd_usr_id',
                sortable: false,
                width: 80,
                align: 'center',
                formatter: function (cell, option, object) {
                    return "<button class='jqgrid_btn' onclick='updateFn(" + object.grpId + ",\"" + object.orgId +"\")'>編集</button>" +
                            "<button class='jqgrid_btn' onclick='toObjSelect(" + object.grpId + ",\"" + object.orgId +"\")'>対象者を選択</button>"+
                            "<button class='jqgrid_btn' onclick='toCopy(" + object.grpId + ")'>コピーして作成</button>"+
                            "<button class='jqgrid_btn' onclick='deleteFn(" + object.grpId + ",\"" + object.updateTimeForCheck + "\")'>削除</button>";
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 30,
        rowList: [10, 30, 50],
        //rownumbers: false,
        rownumWidth: 25,
        autowidth: true,
        // width: 1920,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#jqGrid").setGridWidth($(window).width() * 0.99);
            $("#jqGrid").setGridHeight($(window).height() * 0.55);
        },
        loadComplete: function (data) {
            $("#orgId").text(data.mstOrgEntity.orgId);
            $("#orgNm").text(getOrgName(data.mstOrgEntity.orgNm));
            if (data.code != 0) {
                showMsg(data.msg);
            }
        }
    });
    var srcHeight = $(window).height() - ($(window).width() / 100) * 21.5 - ($(window).width() / 100) * 2.6;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
});

$("#select").click(function () {
    selectLike();
});

function selectLike() {
    var grpNm = $("#grp_nm_area").val();
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F00051/init',
        datatype: "json",
        postData: {
            grpNm: grpNm,
            orgId: window.parent.$("#orgList").val(),
            orgIdListStr: JSON.stringify(orgIdList)
        },
    }).trigger("reloadGrid");
}

$("#add").click(function () {
    window.location.href = "F00052.html";
});

function updateFn(grpId, orgId) {
    window.location.href = "./F00052.html?id=" + grpId + "&orgId=" + orgId;
}

function toObjSelect(grpId, orgId) {

    window.location.href = "./F00054.html?flg=1&groupId=" + grpId + "&orgId=" + orgId;
}

function toCopy(grpId) {

    window.location.href = "./F00054.html?flg=2&groupId=" + grpId ;
}

$("#add_or_export").click(function () {
    window.location.href = "F00005.html";
});

function deleteFn(grpId, updDatime) {
    var msg = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(msg);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00051/delete',
                data: {
                    grpId: grpId,
                    updateTm: updDatime,
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var idx = layer.alert($.format($.msg.MSGCOMN0022, "削除"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                                location.reload();
                        //         layer.close(idx);
                        //     }
                        // });
                    }
                }
            });
        }
    })
}



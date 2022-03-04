$(function () {
    var subjtCd = $("#subjt_cd_area").val();
    var subjtNm = $("#subjt_nm_area").val();
    var commonType = '';
    var reviewType = '';
    var commonFlg = document.getElementById('common_type').checked;
    if (commonFlg){
        commonType = '1'
    } else {
        commonType = '0'
    }
    var reviewFlg = document.getElementById('review_type').checked;
    if (reviewFlg){
        reviewType = '1'
    } else {
        reviewType = '0'
    }
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F07003/init',
        datatype: "json",
        postData: {
            subjtCd: subjtCd,
            subjtNm: subjtNm,
            commonType:commonType,
            reviewType:reviewType
        },
        colModel: [
            {label: '教科CD', name: 'subjtCd', index: 'subjtCd',  sortable:false,width: 20, key: true, align: "center"},
            {label: '教科名', name: 'subjtNm', index: 'subjtNm',  sortable:false,width: 20, align: "center"},
            {label: 'ソート', name: 'sort', index: 'sort',  sortable:false,width: 15, align: "center"},
            {
                label: '表示用画像',
                name: 'displayImg',
                index: 'displayImg',
                sortable:false,
                width: 30,
                align: "center",
                formatter: function (cell, option, object) {
                    return '<img class="display_color" src="' + object.displayImg + '" style="background-color: '+object.bgColor+'" />';
                }
            },
            {
                label: 'ボタン用画像',
                name: 'btnImg',
                index: 'btnImg',
                sortable: false,
                width: 30,
                align: "center",
                formatter: function (cell, option, object) {
                    return '<img src="' + object.btnImg + '" />';
                }
            },
            {
                label: '背景色',
                name: '',
                index: '',
                width: 20,
                sortable:false,
                align: "center",
                formatter: function (cell, option, object) {
                    return '<label class="bg_color" style="background-color: ' + object.bgColor + ';"></label>';
                }
            },
            {
                label: '復習系のみ',
                name: '',
                index: '',
                width: 25,
                sortable:false,
                align: "center",
                formatter: function (cell, option, object) {
                    if (object.reviewFlg != '0'){
                        return "&#10004";
                    }else{
                        return "";
                    }
                }
            },
            {
                label: '学年',
                name: 'schy',
                index: 'schy',
                width: 80,
                sortable:false,
                align: "center"
            },
            {
                label: '', name: '', index: 'upd_usr_id', sortable: false, width: 40, formatter: function (cell, option, object) {
                    return "<a class='edit' style='color: blue;text-decoration: underline' onclick='updateFn(\"" + object.subjtCd + "\")'>編集</a>" +
                        "&nbsp" + "&nbsp" + "&nbsp" +
                        "<a class='delete' style='color: blue;text-decoration: underline' onclick='deleteFn(\"" + object.subjtCd + "\",\"" + object.updateTmForCheck + "\")'>削除</a>";
                }
            },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
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
        },
        loadComplete: function (data) {
            $("#orgId").text(data.mstOrgEntity.orgId);
            $("#orgNm").text(data.mstOrgEntity.orgNm);
            if (data.code != 0) {
                showMsg(data.msg);
            }
        }
    });
    var srcHeight = $(window).height()  - ($(window).width()/100)*24.5;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
});

$("#select").click(function () {
    selectLike();
});

function selectLike() {
    var subjtCd = $("#subjt_cd_area").val();
    var subjtNm = $("#subjt_nm_area").val();
    var commonType = '';
    var reviewType = '';
    var commonFlg = document.getElementById('common_type').checked;
    if (commonFlg){
        commonType = '1'
    } else {
        commonType = '0'
    }
    var reviewFlg = document.getElementById('review_type').checked;
    if (reviewFlg){
        reviewType = '1'
    } else {
        reviewType = '0'
    }
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F07003/init',
        datatype: "json",
        postData: {
            subjtCd: subjtCd,
            subjtNm: subjtNm,
            commonType:commonType,
            reviewType:reviewType
        },
    }).trigger("reloadGrid");
}

$("#add").click(function () {
    window.location.href = "F07004.html";
});

function updateFn(subjtCd) {
    window.location.href = "./F07004.html?subCd=" + subjtCd;
}

function deleteFn(subjtCd, updDatime) {
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
                url: ctxPath + '/manager/F07003/delete',
                data: {
                    subjtCd: subjtCd,
                    updateTm: updDatime
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var index = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                                location.reload();
                        //         layer.close(index);
                        //     }
                        // })
                    }
                }
            });
        }
    })
}



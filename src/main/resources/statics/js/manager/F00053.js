var param = getParam();

//画面．検索条件．生徒ＩＤ
var stuId;
//画面．検索条件．グループ名
var groupName;

var times = 0;

var vm = new Vue({
    el: "#app",
    data: {

        mstOrgEntityList:[]
    }
});

/**
 * 初期化
 */
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.22 - ($(window).width() / 100) * 2.6;
    var width = $(window).width()*0.99;
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F00053/init',
        datatype: "json",
        postData: {},
        colModel: [
            {label: 'グループID', name: 'groupID', index: 'groupID', width: 20, sortable: false, align: 'center',key:true},
            {label: 'グループ名', name: 'tmp', index: 'groupName', sortable: false, align: 'center', width: 45},
            {
                label: '', name: '', index: 'cret_datime', align: 'center', sortable: false, width: 80
                , formatter: function (cell, option, object) {
                    return "<a class='to00054' href='./F00054.html?flg=1&groupId=" + object.groupID +"'>編集</a>" +
                        "<button style='color: #009944' onclick='deleteBtn(\"" + object.groupID + "\");'>削除</button>"+"  "+
                        "<a style='margin-left: 6vw;' class='to00054' href='./F00054.html?flg=2&groupId=" + object.groupID + "'>コピーして作成追加</a>"
                }
            }
        ],
        viewrecords: true,
        height: srcHeight,
        width:width,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: false,
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
            if (times == 0) {
                if (data.orgNm) {
                    $("#orgNm").text(data.orgNm);
                }
                if (data.sOrgId) {
                    $("#sOrgId").text(data.sOrgId);
                }
                if (data.mstOrgEntityList) {
                    vm.mstOrgEntityList = data.mstOrgEntityList;
                }
            }
            if (data.code != 0) {
                showMsg(data.msg);
            }
        }
    });
});

/**
 * 検索ボタン押下
 */
function searchFn() {
    times = 1;
    formatCheck();
    if ($("#f00053Form").valid() == true) {
        $("#jqGrid").jqGrid('setGridParam', {
            url: ctxPath + '/manager/F00053/search',
            datatype: "json",
            postData: {
                stuId: $("#stuId").val(),
                groupName: $("#groupName").val(),
                selectOrgId: $("#selectOrgId").val()
            },
            page: 1
        }).trigger("reloadGrid");
    }
}

function deleteBtn(groupId) {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', 'OK'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00053/delete',
                type: 'POST',
                data: {
                    groupId: groupId
                },
                success: function (data) {
                    if (data.code === 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     shadeClose: false,
                        //     btn: ['OK'],
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

//Check
function formatCheck() {
    $("#f00053Form").validate({
        rules: {
            stuId: {
                alphaNumSymbol: true
            }
        },
        debug: true,
        onfocusout: false,
        onkeyup: false,
        messages: {
            stuId: {
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "生徒ID")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
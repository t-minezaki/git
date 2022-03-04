var param = getParam();

//画面．検索条件．組織名
var orgName;
//画面．検索条件．ステータス
var usrStatus;
//画面．検索条件．異動ステータス
var crmSts;
//画面．検索条件．生徒ＩＤ
var stuId;
//画面．検索条件．生徒姓名
var stuName;
//画面．検索条件．異動年月日
var moveYmd;

var times = 0;
//初期化の画面．検索条件．組織名
$(function () {

    $(window).resize(function(){
        $("#jqGrid").setGridWidth($(window).width() * 0.99);
        $("#jqGrid").setGridHeight($(window).height() * 0.55);
    });

    $.ajax({
        url: ctxPath + '/manager/F00071/orgNameSearch',
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                var str;
                if (data.orgNameSearchList) {
                    var arr = data.orgNameSearchList;
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].orgFlg == 1) {
                            str += "<option value='" + arr[i].orgId1 + "' selected>" + arr[i].orgId1 + " " + " " + " " + getOrgName(arr[i].orgNm1) + "</option>";
                        } else {
                            str += "<option value='" + arr[i].orgId1 + "'>" + arr[i].orgId1 + " " + " " + " " + getOrgName(arr[i].orgNm1) + "</option>";
                        }

                    }
                    $("#orgName").html(str);
                }
            } else {
                showMsg(data.msg);
            }
        }
    });
    laydate.render({
        elem: '#time',
        type: 'date',
        format: 'yyyy/MM/dd',
        done: function (value, date) {
            $("#moveYmd").val(value)
        }
    });
});
//初期化の画面．検索条件．ステータス
$(function () {
    $.ajax({
        url: ctxPath + '/manager/F00071/usrStatusSearch',
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                var str = "<option value=''></option>";
                if (data.usrStatusSearchList) {
                    var arr = data.usrStatusSearchList;
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].codCd2 == "0") {
                            str += "<option value = 2>" + arr[i].codValue + "</option>";
                        }
                        if (arr[i].codCd2 == "1") {
                            str += "<option value = 1>" + arr[i].codValue + "</option>";
                        }
                    }
                    $("#usrStatus").html(str);
                }
            } else {
                showMsg(data.msg);
            }
        }
    })
});
//初期化の画面．検索条件．異動ステータス
$(function () {
    $.ajax({
        url: ctxPath + '/manager/F00071/crmStsSearch',
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                var str = "<option value=''></option>";
                if (data.crmStsSearchList) {
                    var arr = data.crmStsSearchList;
                    for (var i = 0; i < arr.length; i++) {
                        str += "<option value='" + arr[i].codCd + "'>" + arr[i].codValue + "</option>";
                    }
                    $("#crmSts").html(str);
                }
            } else {
                showMsg(data.msg);
            }
        }
    })
});
//初期化リスト
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.24 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F00071/init',
            datatype: "json",
            postData: {},
            colModel: [
                {
                    label: '組織ID', name: 'orgId', index: 'orgId', width: 30, key: true, sortable: false, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.orgId == null && object.orgId.trim().length === 0) {
                            return "";
                        } else {
                            return object.orgId;
                        }
                    }
                },
                {
                    label: '組織名', name: 'orgNm', index: 'orgNm', width: 30, sortable: false, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.orgNm == null && object.orgNm.trim().length === 0) {
                            return "";
                        } else {
                            return object.orgNm;
                        }
                    }
                },
                {
                    label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', sortable: false, width: 20, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.afterUsrId == null) {
                            return "";
                        } else {
                            return object.afterUsrId;
                        }
                    }
                },
                {
                    label: '姓名', name: 'stuName', index: 'stuName', width: 30, sortable: false, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.flnmNm == null && object.flnmLnm == null) {
                            return "";
                        } else {
                            return object.flnmNm + " " + object.flnmLnm;
                        }
                    }
                },
                {
                    label: 'ステータス', name: 'usrStatus', index: 'usrStatus', width: 40, sortable: false, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.usrStatus == null) {
                            return ""
                        } else {
                            if (object.usrStatus == "1") {
                                return "在塾";
                            } else if (object.usrStatus == "0") {
                                return "退塾";
                            } else {
                                return "";
                            }
                        }
                    }
                },
                {
                    label: '異動ステータス', name: 'crmSts', index: 'crmSts', width: 40, sortable: false, align: "center",
                    formatter(cell, opiton, object) {
                        if (object.crmSts == null) {
                            return ""
                        } else {
                            if (object.crmSts == "1") {
                                return "転入";
                            } else if (object.crmSts == "2") {
                                return "退塾";
                            }else if (object.crmSts == "3") {
                                return "休";
                            }else if (object.crmSts == "4"){
                                return "再入塾";
                            }
                            else {
                                return "";
                            }
                        }
                    }
                },

                {
                    label: '異動年月日', name: 'moveYmd', index: 'moveYmd', width: 45, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.moveYmd == null) {
                            return ""
                        } else {
                            return moment(object.moveYmd).format("YYYY/MM/DD");
                        }
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
                rows: "limit",
                order: "order"
            }
            ,
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                $("#jqGrid").setGridWidth($(window).width() * 0.99);
                $("#jqGrid").setGridHeight($(window).height() * 0.55);
            }
            ,
            loadComplete: function (data) {
                if (times == 0) {
                    if (data.orgNm) {
                        $("#orgNm").text(getOrgName(data.orgNm));
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
    formatCheck();
    if ($("#f00071Form").valid() == true) {
        $("#jqGrid").jqGrid('setGridParam', {
            url: ctxPath + '/manager/F00071/search',
            datatype: "json",
            postData: {
                orgName: $('#orgName').val(),
                usrStatus: $('#usrStatus').val(),
                crmSts: $('#crmSts').val(),
                stuId: $('#stuId').val(),
                stuName: $('#stuName').val(),
                moveYmd: $('#moveYmd').val()
            },
            page: 1
        }).trigger("reloadGrid");
    }
}

//Check
function formatCheck() {
    $("#f00071Form").validate({
        rules: {
            stuId: {
                alphaNumSymbol: true
            },
            stuName: {
                zenkaku: true
            }
        },
        debug: false,
        onfocusout: false,
        onkeyup: false,
        messages: {
            stuId: {
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "組織ＩＤ")
            },
            stuName: {
                zenkaku: $.format($.msg.MSGCOMD0007, "生徒姓名")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
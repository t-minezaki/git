var srcHeight = $(window).height() - ($(window).width() / 100) * 27 - ($(window).width() / 100) * 2.6;
var schySrcHeight = $(window).height() - ($(window).width() / 100) * 27 - ($(window).width() / 100) * 2.6;
// $(window).height() - ($(window).width() / 100) * 37.8;
var specAuthFlg = '0';
var orgIdList = [];
var showList = [
    {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, key: true, align: "center"},
    {label: '管理者ID', name: 'afterUsrId', index: 'afterUsrId', width: 50, sortable: false, key: true, align: "center"},
    // {label: '組織共用キー', name: 'orgCommKey', index: 'orgCommKey', width: 50, sortable: false, key: true, align: "center"},
    {label: 'パスワード', name: 'password', index: 'password', width: 80, sortable: false, align: "center"},
    {label: '姓名', name: 'name', index: 'name', width: 80, sortable: false, align: "center",
        formatter(cell, option, object) {
            return "<a href='F00043.html?usrId=" + object.userId + "&role=1&usrSts=1&div=" + object.systemKbn + "'>" + object.firstName + " " + object.lastName + "</a>"
        }
    },
    {label: 'カナ姓名', name: 'KnName', index: 'KnName', width: 80, sortable: false, align: "center",
        formatter(cell, option, object) {
            return object.firstKnName + " " + object.lastKnName
        }
    },
    {label: '電話番号', name: 'telnum', index: 'telnum', width: 80, sortable: false, align: "center"},
    {label: 'ステータス', name: 'usrVal', index: 'usrVal', sortable: false, width: 40},
    {label: '操作', name: '', index: '', sortable: false, width: 160,align:"center",
        formatter(cell, option, object) {
            if (object.delFlg == 0){
                return "<div style='text-align: center'><button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'>削除</button></div>"
            }else {
                return "<div style='text-align: center'><button> 削除済</button>"
                    + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                    "<button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'> 復元</button></div>"
            }

        }}
];
$(function () {
    $('#getPath1').change(function () {
        var str = $(this).val();
        $("#showPath1").attr("value", str);
    });
    var orgAddId = '';
    $.ajax({
        url: ctxPath + '/common/F40004/getOrg',
        type: 'GET',
        dataType: "json",
        success: function (data){
            orgAddId = data.orgIdAdd;
            if (window.getCookie('brandcd') === orgAddId) {
                document.getElementById("add").style.visibility="visible";
            }
            else {
                document.getElementById("add").style.visibility="hidden";
            }
        }
    });
});
function toF00042() {
    var usrRole = $('input[name="usrRole"]:checked').val();
    var usrSts = $('input[name="usrSts"]:checked').val();
    window.location.href = 'F00042.html?role=' + usrRole + '&usrSts=' + usrSts;

}
var param = getParam();
var height = $(window).height()*0.55;
var width = $(window).width()*0.96;
var vm = new Vue({
    el: '#vue',
    data: {
        orgId: "",
        orgNm: "",
        lowerLevList: [],
        usrCountList: [],
        schyList: [],
        specAuthFlg:'',
        usrRole: '1',
        schyList: []
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function (){
            $.ajax({
                url: ctxPath + '/manager/F00041/org',
                type: 'GET',
                success: function (data) {
                    $("#orgId").text(data.org.orgId);
                    $("#orgNm").text(getOrgName(data.org.orgNm));
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                    }
                    if (data.usrSts) {
                        $.each(data.usrSts, function (i, item) {
                            if (item.codCd === "1") {
                                // $("#labUse").html(item.codValue);
                                $("#use").val(item.codCd);
                                $("#use").attr("title",item.codValue);
                            }
                            if (item.codCd === "0") {
                                // $("#labStop").html(item.codValue);
                                $("#stop").val(item.codCd);
                                $("#stop").attr("title",item.codValue);
                            }
                        })
                    }
                    if (data.mstUsrEntity){
                        if (data.mstUsrEntity.specAuthFlg == '1'){
                            $("#specAuthFlg").show();
                            specAuthFlg = data.mstUsrEntity.specAuthFlg;
                            // 2020/12/15 huangxinliang modify start
                            sessionStorage.setItem('specAuthFlg', data.mstUsrEntity.specAuthFlg);
                            // 2020/12/15 huangxinliang modify end
                        }else {
                            showList.pop();
                        }
                    }
                    if (param.roleDiv != null && param.usrSts) {
                        vm.usrRole = param.roleDiv;
                        select(param.roleDiv, param.usrSts);
                        //ルール
                        if (param.roleDiv != null) {
                            var roles = $("input[name='usrRole']");
                            roles.each(function () {
                                if ($(this).val() == param.roleDiv) {
                                    $(this).attr("checked", true);
                                }
                            })
                        }
                        //ステータス
                        if (param.usrSts != null) {
                            var status = $("input[name='usrSts']");
                            status.each(function () {
                                if ($(this).val() == param.usrSts) {
                                    $(this).attr("checked", true);
                                }
                            })
                        }
                    }else {
                        vm.usrRole = '1';
                        vm.dataInit()
                    }
                }
            })
        },
        dataInit: function () {
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F00041/init',
                datatype: "json",
                postData: {
                    orgId: window.parent.$("#orgList").val()
                },
                colModel: showList ,
                viewrecords: true,
                height: 385,
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                },
                loadComplete: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                    }
                    // 2020/12/8 huangxinliang modify start
                    orgIdList.push(data.orgId);
                    // 2020/12/8 huangxinliang modify end
                }
            });
            $(".ui-jqgrid-bdiv").css("height", srcHeight);
        }
    }
});
var show = [];
function select(roleDiv, status) {
    formatCheck();
    if ($("#f00041Form").valid() != true) {
        return;
    }
    //ルール
    var usrRole;
    if (roleDiv == null) {
        usrRole = $('input[name="usrRole"]:checked').val();
    } else {
        usrRole = roleDiv;
    }
    // 2020/12/15 huangxinliang modify start
    if (sessionStorage.getItem("specAuthFlg")) {
        specAuthFlg = sessionStorage.getItem("specAuthFlg");
    }
    // 2020/12/15 huangxinliang modify end
    if (usrRole == '1'){
        show =  [
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, key: true, align: "center"},
            {label: '管理者ID', name: 'afterUsrId', index: 'afterUsrId', width: 50, sortable: false, key: true, align: "center"},
            // {label: '組織共用キー', name: 'orgCommKey', index: 'orgCommKey', width: 50, sortable: false, key: true, align: "center"},
            {label: 'パスワード', name: 'password', index: 'password', sortable: false, width: 80, align: "center"},
            {label: '姓名', name: 'name', index: 'name', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return "<a href='F00043.html?usrId=" + object.userId + "&role=" + usrRole + "&usrSts=" + usrSts + "&div=" + object.systemKbn + "'>" + object.firstName + " " + object.lastName + "</a>"
                }
            },
            {label: 'カナ姓名', name: 'KnName', index: 'KnName', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return object.firstKnName + " " + object.lastKnName
                }
            },
            {label: '電話番号', name: 'telnum', index: 'telnum', sortable: false, width: 80, align: "center"},
            {label: 'ステータス', name: 'usrVal', index: 'usrVal', sortable: false, width: 40},
            {label: '操作', name: '', index: '', sortable: false, width: 160,align:"center",
                formatter(cell, option, object) {
                    if (object.delFlg == 0){
                        return "<div style='text-align: center'><button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'>削除</button></div>"

                    }else {
                        return "<div style='text-align: center'><button> 削除済</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'> 復元</button></div>"
                    }

                }
            }
        ];
        if (specAuthFlg == '0') {
            show.pop();
        }
    }
    else if (usrRole == '2') {
        show =[
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, key: true, align: "center"},
            {label: '先生ID', name: 'afterUsrId', index: 'afterUsrId', width: 50, sortable: false, key: true, align: "center"},
            // {label: '組織共用キー', name: 'orgCommKey', index: 'orgCommKey', width: 50, sortable: false, key: true, align: "center"},
            {label: 'パスワード', name: 'password', index: 'password', sortable: false, width: 80, align: "center"},
            {label: '姓名', name: 'name',index: 'name', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return "<a href='F00043.html?usrId=" + object.userId + "&role=" + usrRole + "&usrSts=" + usrSts + "&div=" + object.systemKbn + "'>" + object.firstName + " " + object.lastName + "</a>"
                }
            },
            {label: 'カナ姓名', name: 'KnName', index: 'KnName', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return object.firstKnName + " " + object.lastKnName
                }
            },
            {label: '電話番号', name: 'telnum', index: 'telnum', sortable: false, width: 80, align: "center"},
            {label: 'ステータス', name: 'usrVal', index: 'usrVal', sortable: false, width: 40},
            {label: '操作', name: '', index: '', sortable: false, width: 160,align:"center",
                formatter(cell, option, object) {
                    if (object.delFlg == 0){
                        return "<div style='text-align: center'><button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'>削除</button></div>"

                    }else {
                        return "<div style='text-align: center'><button> 削除済</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'> 復元</button></div>"
                    }

                }
            }
        ];
        if (specAuthFlg == '0') {
            show.pop();
        }
    }
    else if (usrRole == '3') {
        show =[
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, key: true, align: "center"},
            {label: '保護者ID', name: 'afterUsrId', index: 'afterUsrId', sortable: false, width: 50, key: true, align: "center"},
            // {label: '組織共用キー', name: 'orgCommKey', index: 'orgCommKey', width: 50, sortable: false, key: true, align: "center"},
            {label: 'パスワード', name: 'password', index: 'password', sortable: false, width: 80, align: "center"},
            {label: '姓名', name: 'name', index: 'name', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return "<a href='F00043.html?usrId=" + object.userId + "&role=" + usrRole + "&usrSts=" + usrSts + "&div=" + object.systemKbn + "'>" + object.firstName + " " + object.lastName + "</a>"
                }
            },
            {label: 'カナ姓名', name: 'KnName', index: 'KnName', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return object.firstKnName + " " + object.lastKnName
                }
            },
            {label: '続柄', name: 'tuzukiGara', index: 'tuzukiGara', sortable: false, align: "center", width: 50},
            {label: '電話番号', name: 'telnum', index: 'telnum', sortable: false, align: "center", width: 80},
            {label: '住所', name: 'adr', index: 'adr', sortable: false, width: 120, align: "center"},
            {label: 'ステータス', name: 'usrVal', index: 'usrVal', sortable: false, width: 52},
            {label: '操作', name: '', index: '', sortable: false, width: 160,align:"center",
                formatter(cell, option, object) {
                    if (object.delFlg == 0){
                        return "<div style='text-align: center'><button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'>削除</button></div>"

                    }else {
                        return "<div style='text-align: center'><button> 削除済</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'> 復元</button></div>"
                    }

                }
            }
        ];
        if (specAuthFlg == '0') {
            show.pop();
        }
    }
    else if (usrRole == '4') {
        show =[
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, key: true, align: "center"},
            {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', sortable: false, width: 50, key: true, align: "center"},
            // {label: '組織共用キー', name: 'orgCommKey', index: 'orgCommKey', width: 50, sortable: false, key: true, align: "center"},
            {label: 'パスワード', name: 'password', index: 'password', sortable: false, width: 80, align: "center"},
            {label: '姓名', name: 'name', index: 'name', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return "<a href='F00043.html?usrId=" + object.userId + "&role=" + usrRole + "&usrSts=" + usrSts + "&div=" + object.systemKbn + "'>" + object.firstName + " " + object.lastName + "</a>"
                }
            },
            {label: 'カナ姓名', name: 'KnName', index: 'KnName', sortable: false, width: 80, align: "center",
                formatter(cell, option, object) {
                    return object.firstKnName + " " + object.lastKnName
                }
            },
            {label: '性別', name: 'gendrVal', index: 'gendrVal', sortable: false, width: 80},
            {label: '学年', name: 'schy', index: 'schy', sortable: false, width: 80},
            {label: '生年月日', name: 'birth', index: 'birth', sortable: false, width: 80},
            {label: '学校', name: 'schNm', index: 'schNm', sortable: false, width: 80, align: "center"},
            {label: 'ステータス', name: 'usrVal', index: 'usrVal', sortable: false, width: 49},
            {label: '操作', name: '', index: '', sortable: false, width: 160,align:"center",
                formatter(cell, option, object) {
                    if (object.delFlg == 0){
                        return "<div style='text-align: center'><button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'>削除</button></div>"
                    }else {
                        return "<div style='text-align: center'><button> 削除済</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button onclick='del(\""+object.afterUsrId+"\", \""+object.userId+"\", " + object.delFlg + ")'> 復元</button></div>"
                    }
                }
            }
        ];
        if (specAuthFlg == '0') {
            show.pop();
        }
    }
    var loginId = $('input[name="loginId"]').val();
    var usrName = $('input[name="usrName"]').val();
    var knName = $('input[name="knName"]').val();
    // ステータス
    var usrSts;
    if (status == null) {
        usrSts = $('input[name="usrSts"]:checked').val();
    } else {
        usrSts = status;
    }

    var schy = $("#schy").find("option:selected").val();
    $("#gbox_jqGrid").remove();
    $("body").append('<table id="jqGrid"></table>');
    $("body").append('<div id="jqGridPager"></div>');
    $.jgrid.gridUnload('jqGrid');
    switch (usrRole) {
        case "1":
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F00041/select',
                datatype: "json",
                page: 1,
                postData: {
                    orgId: window.parent.$("#orgList").val(),
                    usrRole: usrRole,
                    name: usrName,
                    knName: knName,
                    afterUsrId: loginId,
                    usrSts: usrSts,
                    specAuthFlg: specAuthFlg,
                    orgIdList: JSON.stringify(orgIdList)
                },
                colModel: show,
                viewrecords: true,
                height: 385,
                width: width,
                rowNum: 30,
                /* 2020/12/16 V9.0 modify start */
                rowList: [10, 30, 50],
                rownumbers: false,
                /* 2020/12/16 V9.0 modify end */
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                },
                loadComplete: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                    }
                }
            });
            // $(".main").css("height", "15vw");
            $(".ui-jqgrid-bdiv").css("height", schySrcHeight);
            break;
        case "2":
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F00041/select',
                datatype: "json",
                page: 1,
                postData: {
                    orgId: window.parent.$("#orgList").val(),
                    usrRole: usrRole,
                    name: usrName,
                    knName: knName,
                    afterUsrId: loginId,
                    usrSts: usrSts,
                    specAuthFlg: specAuthFlg,
                    orgIdList: JSON.stringify(orgIdList)
                },
                colModel: show,
                viewrecords: true,
                height: 385,
                width: width,
                rowNum: 30,
                /* 2020/12/16 V9.0 modify start */
                rowList: [10, 30, 50],
                rownumbers: false,
                /* 2020/12/16 V9.0 modify end */
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                },
                loadComplete: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                    }
                }
            });
            // $(".main").css("height", "15vw");
            $(".ui-jqgrid-bdiv").css("height", schySrcHeight);
            break;
        case "3":
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F00041/select',
                datatype: "json",
                page: 1,
                postData: {
                    orgId: window.parent.$("#orgList").val(),
                    usrRole: usrRole,
                    name: usrName,
                    knName: knName,
                    afterUsrId: loginId,
                    usrSts: usrSts,
                    specAuthFlg: specAuthFlg,
                    orgIdList: JSON.stringify(orgIdList)
                },
                colModel: show,
                viewrecords: true,
                height: 385,
                width: width,
                rowNum: 30,
                /* 2020/12/16 V9.0 modify start */
                rowList: [10, 30, 50],
                rownumbers: false,
                /* 2020/12/16 V9.0 modify end */
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                },
                loadComplete: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                    }
                }
            });
            // $(".main").css("height", "15vw");
            $(".ui-jqgrid-bdiv").css("height", schySrcHeight);
            break;
        case "4":
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F00041/select',
                datatype: "json",
                page: 1,
                postData: {
                    orgId: window.parent.$("#orgList").val(),
                    usrRole: usrRole,
                    name: usrName,
                    knName: knName,
                    afterUsrId: loginId,
                    usrSts: usrSts,
                    schy: schy,
                    specAuthFlg: specAuthFlg,
                    orgIdList: JSON.stringify(orgIdList)
                },
                colModel: show,
                viewrecords: true,
                height: 385,
                width: width,
                rowNum: 30,
                /* 2020/12/16 V9.0 modify start */
                rowList: [10, 30, 50],
                rownumbers: false,
                /* 2020/12/16 V9.0 modify end */
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
                },
                loadComplete: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                    }
                }
            });
            // $(".main").css("height", "17.2vw");
            $(".ui-jqgrid-bdiv").css("height", schySrcHeight);
            break;
    }
}

$('input[name="loginId"]').focusout(function () {
    formatCheck();
});
$('input[name="usrName"]').focusout(function () {
    formatCheck();
});
$('input[name="knName"]').focusout(function () {
    formatCheck();
});

function formatCheck() {
    $("#f00041Form").validate({
        rules: {
            loginId: {
                hankaku: true
            },
            usrName: {
                zenkaku: true
            },
            knName: {
                zenkaku: true
            }
        },
        debug: true,
        messages: {
            // 利用者ログインID
            loginId: {
                hankaku: $.format($.msg.MSGCOMD0003, "利用者ログインID"),
            },
            // 姓名
            usrName: {
                zenkaku: $.format($.msg.MSGCOMD0002, "姓名"),
            },
            // カナ姓名
            knName: {
                zenkaku: $.format($.msg.MSGCOMD0007, "カナ姓名"),
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}

function del(afterUsrId, userId, div) {
    // 2020/12/8 huangxinliang modify start
    var index = layer.confirm($.format(div === 0 ? $.msg.MSGCOMN0013 : $.msg.MSGCOMN0177, '当ユーザー'), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル','確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function (){
            $.ajax({
                url: ctxPath + '/manager/F00041/change',
                type: 'POST',
                data: {
                    afterUsrId: afterUsrId,
                    userId: userId
                },
                success: function (data) {
                    if (data.code == 0) {
                        $('#jqGrid').trigger('reloadGrid');
                        // location.reload();
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        }
    });
    // 2020/12/8 huangxinliang modify end
}

$("#org_select").click(function () {
    layer.open({
        id: 'F09010',
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
        content: ["../pop/F09010.html"],
        success: function (layero, index) {
        }
    });
});

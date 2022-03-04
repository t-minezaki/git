var param = getParam();
var width = $(window).width()*0.99;
$(function () {
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F03003/init/' + param.textbId,
        datatype: "json",
        colModel: [
            {label: '単元表示順', name: 'dispyOrder', index: 'cod_id', width: 40, key: true, align: "center"},
            {label: '章名', name: 'chaptNm', index: 'cod_key', width: 140,align:'center'},
            {label: '節名', name: 'sectnNm', index: 'cod_descrp', width: 140,align:'center'},
            {label: '項目表示名', name: 'unitDispyNm', index: 'cret_datime', width: 140,align:'center'},
            {label: '章NO', name: 'chaptNo', index: 'cret_usr_id', width: 40,align:'center'},
            {label: '節NO', name: 'sectnNo', index: 'upd_datime', width: 40,align:'center'},
            {label: '単元NO', name: 'unitNo', index: 'upd_usr_id', width: 40,align:'center'},
            {label: 'ページ', name: 'textbPage', index: 'del_flg', width: 40,align:'center'},
            {label: '学習時期', name: 'planLearnSeasn', index: 'upd_datime', width: 40,align:'center'},
            {label: '時期詳細', name: 'planLearnSeasnDisply', index: 'upd_usr_id', width: 40,align:'center'},
            {label: '目標学習時間', name: 'planLearnTm', index: 'del_flg', width: 60,align:'center',formatter:function (cell) {
                    return cell+"分";
                }}
        ],
        viewrecords: true,
        height: 480,
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
            if (data.code != 0) {
                showMsg(data.msg);
            } else {
                if (data.page.list != null) {
                    $("#schy").text(data.page.list[0].schy);
                    $("#subjt").text(data.page.list[0].subjt);
                    $("#publisher").text(data.page.list[0].publisher);
                    $("#textbNm").text(data.page.list[0].textbNm);
                }
            }
        }
    });
    var srcHeight = $(window).height() - ($(window).width() / 100) * 26.5;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
});
function returnFn() {
    window.location.href="./F03001.html";
}

var param = getParam();

function getInit() {
    var srcHeight = $(window).height() - $(window).width() * 0.13 - 130;
    var width = $(window).width() *0.9;

    $("#jqGrid").jqGrid({
        url: ctxPath + '/pop/F04007/init',
        postData: {
            paramOrgId: param.orgId,
            noticeId: param.noticeId,
            readFlg: param.readFlg
        },
        datatype: "json",
        colModel: [
            {label: '保護者ＩＤ', name: 'guardId',  key: true,sortable: false, align: "center"},
            {label: '保護者氏名', name: 'guardnm', index: '1',sortable: false, align: "center"},
            {label: '子供ＩＤ', name: 'stuId', index: '2',sortable: false, align: "center"},
            {label: '子供氏名', name: 'stunm', index: '3',sortable: false, align: "center"}
        ],
        viewrecords: true,
        height: srcHeight,
        width:width,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        // rownumWidth: 25,
        // autowidth: true,
        multiselect: false,
        shrinkToFit:false,
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
                $('#newsId').val(param.noticeId);
                $('#newsNm').val(data.mstNoticeEntity.noticeTitle);
                $('#planTime').val(data.pubPlanStartDt);
                $('#endTime').val(data.pubPlanEndDt);
                $('#org').val(param.orgId + '　' + data.guardNoticeOrgNm);
                $('#orgId').text(data.orgId);
                $('#orgNm').text(data.orgNm);
            }
        }
    });
}

$(function () {
    getInit();
});

function cancelFn() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
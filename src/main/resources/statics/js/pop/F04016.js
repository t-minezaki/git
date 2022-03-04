var param = getParam();

var vm = new Vue({
    el: '#app',
    data: {
        mstNoticeEntity: [],
        org: "",
        title:"",
        count:""
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            var srcHeight = $(window).height()*0.58;
            var width = $(window).width() * 0.91;
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F04016/init',
                    datatype: "json",
                    postData: {
                        noticeId: param.noticeId,
                        orgId:param.orgId,
                        flg:param.rsd
                    },
                    colModel: [
                        {label: '保護者ＩＤ', name: 'afterUserId', index: 'afterUserId', width: 80, key: true, sortable: false, align: "center"},
                        {label: '保護者氏名', name: 'guardName', index: 'guardName', width: 80, sortable: false, align: "center"},
                        {label: '生徒ＩＤ', name: 'stuId', index: 'stuId', width: 80, key: true, sortable: false, align: "center"},
                        {label: '生徒名', name: 'stuName', index: 'stuName', width: 80, sortable: false, align: "center"}
                    ],
                    viewrecords: true,
                    height: srcHeight,
                    width: width,
                    rowNum: 30,
                    rowList: [10, 30, 50],
                    rownumbers: false,
                    rownumWidth: 25,
                    autowidth: false,
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
                    }
                    ,
                    loadComplete: function (data) {
                        $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_readCount"]').css("text-indent","5.5vw");
                        $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_notReadCount"]').css("text-indent","5.5vw");
                        $(".ui-jqgrid-bdiv").css("width","99.2%");
                        vm.count = data.page.totalCount;
                        vm.title = data.title;
                        if (vm.count !== 0) {
                            vm.mstNoticeEntity = data.mstNoticeEntity;
                            vm.org = data.orgId + ' '+ data.orgNm;
                        } else {
                            showMsg($.format($.msg.MSGCOMN0017, 'マナミルチャンネル'));
                        }
                    }
                }
            );
        },
        bakBtn: function () {
            window.history.go(-1)
        }
    }
});
var params = getParam();

var vm = new Vue({
    el: '#app',
    data: {
        mstMessageEntity: [],
        org: ""
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            var srcHeight = $(window).height() * 0.58;
            var width = $(window).width() * 0.98;
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/pop/F21066/init',
                    datatype: "json",
                    postData: {
                        messageId: params.id,
                        orgId: params.orgId,
                        flg: params.rsd
                    },
                    colModel: [
                        {
                            label: '生徒ＩＤ',
                            name: 'stuId',
                            index: 'stuId',
                            width: 80,
                            key: true,
                            sortable: false,
                            align: "center"
                        },
                        {label: '生徒氏名', name: 'stunm', index: 'stunm', width: 80, sortable: false, align: "center"},
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
                        $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_readCount"]').css("text-indent", "5.5vw");
                        $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_notReadCount"]').css("text-indent", "5.5vw");
                        $(".ui-jqgrid-bdiv").css("width", "99.2%");
                        if (data.code === 0) {
                            vm.mstMessageEntity = data.mstMessageEntity;
                            vm.mstMessageEntity.pubPlanStartDt = vm.formatDateTime(vm.mstMessageEntity.pubPlanStartDt);
                            vm.mstMessageEntity.pubPlanEndDt = vm.formatDateTime(vm.mstMessageEntity.pubPlanEndDt);
                            vm.org = data.orgId + ' ' + data.orgNm;
                        } else {
                            showMsg(data.msg);
                        }
                    }
                }
            );
        },
        bakBtn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        formatDateTime: function (dateTime) {
            return dateTime.replace(/(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2}).?(\d{1,3})?/, '$1/$2/$3 $4:$5');
        }
    }
});
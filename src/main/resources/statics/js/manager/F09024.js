var page = 1;
var pageSize = 15;
var vm = new Vue({
    el: '#page',
    data: {
        currentDay: null,
        today: null,
        rangeStartDay: null
    },
    mounted: function () {
        if (getCookie("equipment") === "phone") {
            $("<link>").attr({rel: "stylesheet", type: "text/css", href: ctxPath + "/css/manager/F09024_phone.css"}).appendTo("head");
        } else {
            $("<link>").attr({rel: "stylesheet", type: "text/css", href: ctxPath + "/css/manager/F09024_tablet.css"}).appendTo("head");
        }
        this.dateSelect();
        this.init();
    },
    methods: {
        dateSelect: function () {
            this.today = new Date().format('Y-m-d');
            this.currentDay = new Date().format('Y/m/d');
            this.rangeStartDay = new Date();
            this.rangeStartDay.setDate(this.rangeStartDay.getDate() - 6);
            this.rangeStartDay = this.rangeStartDay.format('Y-m-d');
            laydate.render({
                elem: '.time_pick',
                type: 'date',
                format: 'yyyy/MM/dd',
                value: new Date(),
                min: this.rangeStartDay,
                max: this.today,
                eventElem: '.date_pic',
                trigger: 'click',
                done: function (value) {
                    if (vm.currentDay !== value) {
                        vm.currentDay = value;
                        pageSize = 15;
                        loadData();
                    }
                }
            });
        },
        init: function () {
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F09024/init',
                    datatype: "json",
                    postData: {
                        tgtYmd: this.currentDay
                    },
                colModel: [
                    {label: '会員名', name: 'stuName', index: 'stuName', sortable: false, align: "center"},
                    {label: '学年', name: 'schyDiv', index: 'schyDiv', sortable: false, align: "center"},
                    {label: '入室時間', name: 'entryTime', index: 'entryTime', sortable: false, align: "center"},
                    {label: '退室時間', name: 'exitTime', index: 'exitTime', sortable: false, align: "center"}
                ],
                    rowNum: pageSize,
                    shrinkToFit: true,
                    loadui: false,
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
                        if (vm.offset > 0) {
                            $(".ui-jqgrid-bdiv").scrollTop(vm.offset);
                        }
                    },
                    loadComplete: function (data) {
                        if (data.code !== 0) {
                            layer.alert(data.msg);
                            $(".insert").css("display", "none");
                            return;
                        }
                        if (pageSize >= data.page.totalCount) {
                            $(".insert").css("display", "none");
                        } else {
                            $(".insert").css("display", "block");
                        }
                    }
                }
            );
        },
        insert: function () {
            pageSize = pageSize + 15;
            vm.offset = $("#jqGrid").height() - $('.ui-jqgrid-bdiv').height() + $(".insert").outerHeight();
            $.jgrid.gridUnload("jqGrid");
            vm.init();
        }
    }
});

function loadData() {
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F09024/init',
        datatype: "json",
        postData: {
            tgtYmd: vm.currentDay
        },
        page: 1,
        loadComplete: function (data) {
            if (data.code !== 0) {
                layer.alert(data.msg);
                $(".insert").css("display", "none");
                return;
            }
            if (pageSize >= data.page.totalCount) {
                $(".insert").css("display", "none");
            } else {
                $(".insert").css("display", "block");
            }
        }
    }).trigger("reloadGrid");
}
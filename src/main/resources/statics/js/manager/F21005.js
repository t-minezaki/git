var colModel = [
    {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 200, key: true, sortable: false, align: "center"},
    {label: '学年', name: 'codValue', index: 'codValue', width: 200, key: true, sortable: false, align: "center"},
    {label: '保護者連絡日時', name: 'dateTime', index: 'dateTime', width: 200, key: true, sortable: false, align: "center"},
    {label: '保護者名', name: 'guardName', index: 'guardName', width: 200, sortable: false, align: "center"},
    {
        label: '操作', name: '', index: '', width: 300, sortable: false, align: "center",
        formatter(cell, option, object) {
            return "<button onclick='details(this, " + JSON.stringify(object) + ")'>詳細</button>"
        }
    }
];
var srcHeight = $(window).height() * 0.65;
var srcWidth = $(window).width() * 0.6;
var vm = new Vue({
    el: '#content',
    date: {
        currentHistory: {},
        currentDay: '',
        e: {}
    },
    mounted: function () {
        this.setUp();
    },
    methods: {
        setUp: function () {
            this.currentDay = new Date().format('Y/m/d');
            //laydate
            laydate.render({
                elem: '#day',
                format: 'yyyy/MM/dd',
                value: new Date(),
                eventElem: '#dayPic',
                trigger: 'click',
                done: function (value, date) {
                    if (vm.currentDay != value) {
                        vm.currentDay = value;
                        reload();
                    }
                }
            });
            //jqgrid
            $('#jqGrid').jqGrid({
                url: ctxPath + '/manager/F21005/select',
                datatype: 'json',
                postData: {
                    day: this.currentDay
                },
                colModel: colModel,
                /* 2021/01/14 UT-018 cuikailin modify start */
                viewrecords:
                /* 2021/01/14 UT-018 cuikailin modify end */
                    true,
                height:srcHeight,
                width:srcWidth,
                rowNum:
                    30,
                rowList:
                    [10, 30, 50],
                rownumbers:
                    false,
                rownumWidth:
                    25,
                autowidth:
                    true,
                multiselect:
                    false,
                pager:
                    "#jqGridPager",
                jsonReader: {
                    root: "page.list",
                    page:
                        "page.currPage",
                    total:
                        "page.totalPage",
                    records:
                        "page.totalCount"
                },
                prmNames: {
                    page:
                        "page",
                    rows:
                        "limit",
                    order:
                        "order"
                },
                gridComplete: function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                },
                loadComplete: function (data) {
                    if (data.code === 0) {
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        }
    }
});

function details(e, object) {
    vm.currentHistory = object;
    vm.e = e;
    var srcWidth = "480px";
    var srcHeight = "320px";
    var index = layer.open({
        id: 'chooseDateRange',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F21004.html?" + $.param(vm.currentHistory), 'no']
    });
}

function reload() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F21005/select',
        datatype: "json",
        postData: {
            day: vm.currentDay
        },
        page: 1,
        loadComplete: function (data) {
            if (data.code !== 0) {
                showMsg(data.msg);
            }
        }
    }).trigger("reloadGrid");
}

function toF21003() {
    window.location.href = 'F21003.html';
}
var colModel = [
    {   id:'id',
        label: '組織名',
        name: 'orgName',
        index: 'orgName',
        width: 150,
        key: true,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true
    },
    {
        label: '生徒ID',
        name: 'afterUsrId',
        index: 'afterUsrId',
        width: 150,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true
    },
    {
        label: '生徒名',
        name: 'stuName',
        index: 'stuName',
        width: 150,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true
    },
    {
        label: '学年',
        name: 'schyDiv',
        index: 'schyDiv',
        width: 150,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true
    },
    {
        label: '登校時間',
        name: 'entryTime',
        index: 'entryTime',
        width: 150,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true,
        formatter: function (cell, option, object) {
            if (cell) {
                return cell.match(/\d{2}:\d{2}/);
            } else {
                return '';
            }
        }
    },
    {
        label: '下校時間',
        name: 'exitTime',
        index: 'exitTime',
        width: 150,
        sortable: false,
        align: "center",
        resizable: false,
        fixed: true,
        formatter: function (cell, option, object) {
            if (cell) {
                return cell.match(/\d{2}:\d{2}/);
            } else {
                return '';
            }
        }
    }
];
var orgId = window.parent.$("#orgList").val();
var vm = new Vue({
    el: "#content",
    data: {
        orgList: [],
        currentDay: '',
        today: '',
        rangeStartDay: ''
    },
    mounted: function () {
        this.setUp();
        this.showData();
    },
    methods: {
        setUp: function () {
            this.today = new Date().format('Y-m-d');
            this.currentDay = new Date().format('Y/m/d');
            this.rangeStartDay = new Date();
            this.rangeStartDay.setMonth(this.rangeStartDay.getMonth() - 3);
            this.rangeStartDay = this.rangeStartDay.format('Y-m-d');
            laydate.render({
                elem: '#day',
                type: 'date',
                format: 'yyyy/MM/dd',
                value: new Date(),
                min: this.rangeStartDay,
                max: this.today,
                eventElem: '#dayPic',
                trigger: 'click',
                done: function (value, date) {
                    if (vm.currentDay != value) {
                        vm.currentDay = value;
                        reload();
                    }
                }
            });
        },
        showData: function () {
            $('#jqGrid').jqGrid({
                url: ctxPath + '/manager/F09001/select',
                datatype: 'json',
                postData: {
                    day: this.currentDay,
                    orgId: orgId
                },
                colModel: colModel,
                /* 2021/01/14 UT-156 cuikailin modify start */
                viewrecords:
                /* 2021/01/14 UT-156 cuikailin modify end */
                    true,
                height:
                    385,
                /* 2020/12/22 UT-41 cuikailin modify start */
                width: 950,
                /* 2020/12/22 UT-41 cuikailin modify end */
                rowNum:
                    30,
                rowList:
                    [10, 30, 50],
                rownumbers:
                    false,
                rownumWidth:
                    25,
                /* 2020/12/22 UT-41 cuikailin modify start */
                autowidth:
                    false,
                /* 2020/12/22 UT-41 cuikailin modify end */
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
                    resize();
                },
                loadComplete: function (data) {
                    if (data.code === 0) {
                    } else {
                        showMsg(data.msg);
                        resize();
                    }
                }
            });
            var srcHeight = $(window).height() - ($(window).width() / 100) * 25.6 - 36 - ($(window).width() / 100) * 2.6;
            $(".ui-jqgrid-bdiv").css("height", srcHeight);
        }
    }
});

$(function () {
    $(window).resize(function () {
        resize();
    });
});

function reload() {
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F09001/select',
        datatype: "json",
        postData: {
            day: vm.currentDay,
            orgId: vm.orgId
        },
        page: 1,
        loadComplete: function (data) {
            if (data.code !== 0) {
                showMsg(data.msg);
                resize();
            }
        }
    }).trigger("reloadGrid");
}

function chooseDateRange() {
    var param = {};
    param.rangeStartDay = vm.rangeStartDay;
    param.today = vm.today;
    var scrWidth = "40%";
    var scrHeight = "40%";
    // if (scrWidth > 540) {
    //     scrWidth = '360px'
    // } else {
    //     scrWidth = scrWidth + 'px'
    // }
    // if (scrHeight > 480) {
    //     scrHeight = '240px'
    // } else {
    //     scrHeight = scrHeight + 'px'
    // }
    var index = layer.open({
        id: 'chooseDateRange',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [scrWidth, scrHeight],
        fixed: true,
        resize: false,
        content: ["../pop/chooseDateRange.html?" + $.param(param), 'no'],
        btn: ['キャンセル', '確認'],
        btn2: function (index) {
            var row = window["layui-layer-iframe" + index].callbackdata();
            if (row) {
                var canDownload = true;
                if (row.rangeStartDay > row.today) {
                    canDownload = false;
                    var msg = layer.confirm($.format($.msg.MSGCOMN0124, "指定終了日", "指定開始日"), {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(msg);
                        }
                    });
                }
                var start = new Date(row.rangeStartDay);
                start.setMonth(start.getMonth() + 3);
                start = start.format('Y/m/d');
                if (start < row.today) {
                    canDownload = false;
                    var msg = layer.confirm($.msg.MSGCOMN0125, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(msg);
                        }
                    });
                }
                if (canDownload) {
                    $.ajax({
                        url: ctxPath + '/manager/F09001/Export',
                        type: 'GET',
                        data: {
                            start: row.rangeStartDay,
                            end: row.today,
                            orgId: orgId
                        },
                        success: function (data) {
                            if (data.code == 0) {
                                $("#downloadFrom").attr("action", ctxPath + "/manager/F09001/download");
                                $("#downloadFrom #fileName").val(data.fileNm);
                                $("#downloadFrom").submit();
                            } else {
                                showMsg(data.msg);
                                resize();
                            }
                        }
                    });
                }
            }
        },
        btn1: function (index) {
            layer.close(index);
        }
    });
}
function setHeight(objId, containerId) {
    var length = arguments.length;
    var height = 0;
    for (var i = 2; i < length; i++) {
        height += $("#" + arguments[i]).outerHeight(true);
    }
    var parentHeight = $(containerId).height();
    $("#" + objId).height(parentHeight - height);
}
function resize() {
    setHeight("content", window, "one", "message");
    setTargetControlHeight("listContent", "content", "selectContent", "downloadContent");
    $("#jqGrid").setGridHeight(getRemainingHeight("listContent", "jqGridPager") - ($(window).height * 0.1) - 50);
}
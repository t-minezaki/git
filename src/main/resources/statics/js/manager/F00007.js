$(function () {
    // $(window).resize(function(){
    //     $("#jqGrid").setGridWidth($(window).width() * 0.99);
    //     $("#jqGrid").setGridHeight($(window).height() * 0.55);
    // });

    $('#getPath1').change(function () {
        var str = $(this).val();
        $("#showPath1").attr("value", str);
    });
});

var vm = new Vue({
    el: '#vue',
    data: {
        orgId: "",
        orgNm: "",
        lowerLevList: [],
        usrCountList: []
    },
    updated:function(){
        var srcHeight = $(window).height() - ($(window).width() / 100) * 20.5 - 42 - ($(window).width() / 100) * 2.6;
        $(".ui-jqgrid-bdiv").css("height", srcHeight);
        $("select").change(function () {
            $("#message").hide();
        });
    },
    mounted: function () {
        this.dataInit();
    },
    methods: {
        dataInit: function () {
            $.ajax({
                url: ctxPath + '/manager/F00007/init',
                type: 'get',
                data:{
                    limit:30,
                    page:1
                },
                datatype: 'json',
                success: function (su) {
                    if (su.orgId) {
                        vm.orgId = su.orgId
                    }
                    if (su.orgNm) {
                        vm.orgNm = getOrgName(su.orgNm);
                    }
                    if (su.lowerLevList) {
                        vm.lowerLevList = su.lowerLevList
                    }
                }
            })
        },
        exportBtn: function () {
            var myselected = document.getElementById("org");
            var index = myselected.selectedIndex;
            var orgId = "";
            if (index !== 0) {
                orgId = vm.lowerLevList[index - 1].orgId;
            } else {
                orgId = "";
            }

            $.ajax({
                url: ctxPath + '/manager/F00007/export',
                type: 'post',
                data: {
                    currentOrgId: orgId
                },
                success: function (data) {
                    if (data.code === 0) {
                        $("#exportForm #exportInput").val(data.fileNm);
                        $("#exportForm").submit();
                    } else {
                        showMsg(data.msg)
                    }
                }
            })
        }
    }
});

function f00007select() {
    var myselected = document.getElementById("org");
    var index = myselected.selectedIndex;
    var orgId = "";
    if (index !== 0) {
        orgId = vm.lowerLevList[index - 1].orgId;
    } else {
        orgId = "null";
    }

    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F00007/selectUserCount/' + orgId,
        datatype: "json",
        page: 1
    }).trigger("reloadGrid");
}

$(function () {
    var width = $(window).width()*0.99;
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F00007/init',
        datatype: "json",
        colModel: [
            {label: '組織ID', name: 'orgId', index: 'orgId', width: 80, key: true, sortable: false, align: "center"},
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 80,sortable:false, align: "center"},
            {
                label: '在塾人数（人）',
                name: 'usrcount',
                index: 'usrcount',
                width: 50,
                sortable: false,
                align: "center",
                formatter(cell) {
                    return cell
                }
            },
            {
                label: '退塾人数（人）',
                name: 'exitcount',
                index: 'exitcount',
                width: 50,
                sortable: false,
                align: "center",
                formatter(cell) {
                    return cell
                }
            },
            {
                label: '転出人数（人）',
                name: 'outcount',
                index: 'outcount',
                width: 50,
                sortable: false,
                align: "center",
                formatter(cell) {
                    return cell
                }
            },
            {
                label: '転入人数（人）',
                name: 'incount',
                index: 'incount',
                width: 50,
                sortable: false,
                align: "center",
                formatter(cell) {
                    return cell
                }
            }
        ],
        viewrecords: true,
        height: 385,
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
            // $("#jqGrid").setGridWidth($(window).width() * 0.99);
            // $("#jqGrid").setGridHeight($(window).height() * 0.55);
        },
        loadComplete: function (data) {
            if (data.code !== 0) {
                showMsg(data.msg);
            }
        }
    });
});
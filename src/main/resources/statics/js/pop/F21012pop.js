var colModel = [
    {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 100, resizable : false, key: true, sortable: false, align: "center"},
    {label: '生徒名', name: 'stuName', index: 'stuName', width: 100, resizable : false, sortable: false, align: "center"},
    {label: '保護者名', name: 'guardName', index: 'guardName', width: 100, resizable : false, sortable: false, align: "center"},
    {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 100, resizable : false, sortable: false, align: "center"}
];
var vm = new Vue({
    el: '#content',
    data: {
        stuList: []
    },
    mounted: function(){
        this.setUp();
    },
    methods: {
        setUp: function () {
            this.stuList = parent.vm.misGuiRepStuList;
            //jqGrid
            $('#jqGrid').jqGrid({
                datatype: 'local',
                colModel: colModel,
                viewRecords:
                    true,
                width:
                    300,
                height:
                /* 2020/12/22 UT-159 cuikailin modify start */
                    235,
                /* 2020/12/22 UT-159 cuikailin modify end */
                rowNum:
                    10,
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
                gridComplete: function () {
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    $("#jqGrid").setGridWidth($(window).width() > 600 ? $(window).width() * 0.5 : $(window).width()*0.9);
                    /* 2021/01/25 cuikailin UT-159 add start */
                    $(".ui-jqgrid-htable").width($(window).width() > 600 ? $(window).width() * 0.5 : $(window).width()*0.9);
                    $("#jqGrid").width($(window).width() > 600 ? $(window).width() * 0.5-2 : $(window).width()*0.9-2);
                    /* 2021/01/25 cuikailin UT-159 add end */
                }
            });
            for (var i = 0; i <= this.stuList.length; i++){
                $('#jqGrid').jqGrid('addRowData', i + 1, this.stuList[i]);
            }
            //refresh jqGrid
            $('#jqGrid').trigger("reloadGrid");
        }
    }
});
var params = getParam();
$(function () {
    $("#selectAll").click(function () {
        var flag = document.getElementById("selectAll").checked;
        if (flag) {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = treeObj.getCheckedNodes(true);
            if (nodes.length != 1) {
                $(".select2-search__field").attr("placeholder", "");
            }
        } else {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
        }
        onCheck();
    });
});

function onCheck() {
    //ztree オブジェクトを取得
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    nodes = treeObj.getCheckedNodes(true);
    //find parents' nodes
    var node = treeObj.getNodes();
    // find all nodes
    unCheckNodes = treeObj.transformToArray(node);
    var flg = document.getElementById("selectAll");
    if (unCheckNodes.length != nodes.length) {
        flg.checked = false;
    } else {
        flg.checked = true;
    }
    //組織のいずれかチェックされた場合のみ 非活性
    if (nodes.length == 0) {
        $(".select2-search__field").attr("placeholder", "");
        $(".select2-selection__rendered").remove();
    } else {
        $(".select2-selection__rendered").remove();
        if (nodes.length != 1) {
            $(".select2-search__field").attr("placeholder", "");
        }
    }
};
var zNodes;
var nodes = [];
var unCheckNodes = [];
var stuIdList = [];
var selectTwoLength;
var allDeliverFlg = '';
var times = 0;
var vm = new Vue({
    el: '.main',
    data: {
        org: ''
    },
    mounted: function () {
        this.getInfo();
        $("#jqGrid").jqGrid(
            {
                datatype: "local",
                colModel: [
                    {label: '', name: 'userId', index: 'userId', width: 40, key: true, sortable: true, align: "center", hidden: true},
                    {label: '教室名', name: 'orgNm', index: 'orgNm', width: 40, sortable: true, align: "center"},
                    {label: 'ID', name: 'afterUserId', index: 'afterUserId', width: 40, sortable: true, align: "center"},
                    {label: '氏名', name: 'userName', index: 'userName', width: 40, sortable: true, align: "center"}
                ],
                multiselect: true,
                // loadonce: true,
                rowNum: -1,
                sortable: true,
                sortorder: 'asc',
                sortname: 'userName',
                loadComplete: function (data) {
                    $("#gview_jqGrid div.ui-jqgrid-sortable").each(function(index,element){
                        var asc = $(element).children().find("span[sort='asc']").first();
                        var span = $(element).children("span.s-ico").first();
                        var label = $(element).children("label");
                        if(label.length <= 0){
                            span.before("<label></label>");
                        }
                        if(asc.hasClass("ui-state-disabled")){
                            $(element).children("label").html("&#9660");
                        }else{
                            $(element).children("label").html("&#9650");
                        }
                    });

                    // ソートアイコン変換
                    $("#gview_jqGrid div.ui-jqgrid-sortable").bind("click",function () {
                        var asc = $(this).children().find("span[sort='asc']").first();
                        var span = $(this).children("span.s-ico").first();
                        var label = $(this).children("label");
                        if(label.length <= 0){
                            span.before("<label></label>");
                        }
                        if(asc.hasClass("ui-state-disabled")){
                            $(this).children("label").html("&#9660");
                        }else{
                            $(this).children("label").html("&#9650");
                        }
                    });
                }
            }
        );
    },
    updated: function() {
        $("#jqGrid2").jqGrid(
            {
                datatype: "local",
                colModel: [
                    {label: '', name: 'userId', index: 'userId', width: 40, key: true, sortable: true, align: "center", hidden: true},
                    {label: '教室名', name: 'orgNm', index: 'orgNm', width: 40, sortable: true, align: "center"},
                    {label: 'ID', name: 'afterUserId', index: 'afterUserId', width: 40, sortable: true, align: "center"},
                    {label: '氏名', name: 'userName', index: 'userName', width: 40, sortable: true, align: "center"}
                ],
                multiselect: true,
                // loadonce: true,
                rowNum: -1,
                sortable: true,
                sortorder: 'asc',
                sortname: 'userName',
                gridComplete: function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                    $("#jqGrid2").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                },
                loadComplete: function (data) {
                    $("#gview_jqGrid2 div.ui-jqgrid-sortable").each(function(index,element){
                        var asc = $(element).children().find("span[sort='asc']").first();
                        var span = $(element).children("span.s-ico").first();
                        var label = $(element).children("label");
                        if(label.length <= 0){
                            span.before("<label></label>");
                        }
                        if(asc.hasClass("ui-state-disabled")){
                            $(element).children("label").html("&#9660");
                        }else{
                            $(element).children("label").html("&#9650");
                        }
                    });

                    // ソートアイコン変換
                    $("#gview_jqGrid2 div.ui-jqgrid-sortable").bind("click",function () {
                        var asc = $(this).children().find("span[sort='asc']").first();
                        var span = $(this).children("span.s-ico").first();
                        var label = $(this).children("label");
                        if(label.length <= 0){
                            span.before("<label></label>");
                        }
                        if(asc.hasClass("ui-state-disabled")){
                            $(this).children("label").html("&#9660");
                        }else{
                            $(this).children("label").html("&#9650");
                        }
                    });
                }
            }
        );
        // jqgrid再読み込み
        $('#jqGrid2').trigger('reloadGrid');
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F21074/init',
                type: 'GET',
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.org) {
                            vm.org = data.org;
                        }
                        if (data.orgIdList) {

                            zNodes = data.orgIdList;
                            geneUnitTree(zNodes);
                            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                        }
                        onCheck();
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        },
        search: function () {
            $("#jqGrid").jqGrid('clearGridData');
            $("#jqGrid2").jqGrid('clearGridData');
            var formData = new FormData();
            var orgList = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
            var orgIdList = [];
            for (var i = 0; i < orgList.length; i++) {
                orgIdList.push(orgList[i].orgId);
            }
            formData.append('list', JSON.stringify(orgIdList));
            var searchName = $('#name').val();
            var loginId = $('#_id').val();
            formData.append('searchName', searchName === '' ? '' : searchName);
            formData.append('loginId', loginId === '' ? '' : loginId);
            formData.append('type', params.type);
            // リストをはずす
            $.ajax({
                url: ctxPath + '/pop/F21074/search',
                datatype: "json",
                data: formData,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (data) {
                    if (data.personList) {
                        var localData = {page: 1, total: 2, records: "2", rows: data.personList};
                        localData.rows = data.personList;
                        localData.records = data.personList.length;
                        localData.total = data.personList.length;
                        for (var i = 0; i < localData.rows.length; i++) {
                            $("#jqGrid").jqGrid('addRowData', localData.rows[i].stuId, localData.rows[i]);
                        }
                        $("#jqGrid").jqGrid('setGridParam', {
                            rowNum: data.personList.length
                        }).trigger('reloadGrid');
                    }
                }
            })
        },
        toRight: function () {
            var jqGrid = $("#jqGrid");
            var jqGrid2 = $("#jqGrid2");
            var ids2 = jqGrid2.getDataIDs();
            //選択した行を取得（複数行）
            var ids=jqGrid.jqGrid('getGridParam','selarrrow');
            for (var i = 0; i < ids.length; i++) {
                var rowData = jqGrid.jqGrid('getRowData', ids[i]);
                jqGrid2.jqGrid('addRowData',ids[i], rowData, 'last');
            }
            jqGrid2.jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
            for (var i = 0, l = ids.length; i < l; i++) {
                jqGrid.jqGrid('delRowData',ids[0])
            }
            allcheck()
        },
        allToRight: function () {
            var jqGrid = $("#jqGrid");
            var jqGrid2 = $("#jqGrid2");
            var ids2 = jqGrid2.getDataIDs();
            //すべての行のIDを取得する
            var ids = jqGrid.getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                var rowData = jqGrid.jqGrid('getRowData', ids[i]);
                jqGrid2.jqGrid('addRowData',ids[i], rowData, 'last');
            }
            jqGrid2.jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
            jqGrid.jqGrid('clearGridData');
            allcheck()
        },
        toLeft: function () {
            var jqGrid = $("#jqGrid");
            var jqGrid2 = $("#jqGrid2");
            //
            var ids = jqGrid.getDataIDs();
            //選択した行を取得（複数行）
            var ids2=jqGrid2.jqGrid('getGridParam','selarrrow');
            for (var i = 0; i < ids2.length; i++) {
                var rowData = jqGrid2.jqGrid('getRowData', ids2[i]);
                jqGrid.jqGrid('addRowData',ids2[i], rowData, 'last');
            }
            jqGrid.jqGrid('setGridParam', {rowNum: ids.length + ids2.length});
            for (var i = 0, l = ids2.length; i < l; i++) {
                jqGrid2.jqGrid('delRowData',ids2[0])
            }
            allcheck()
        },
        allToLeft: function () {
            var jqGrid = $("#jqGrid");
            var jqGrid2 = $("#jqGrid2");
            var ids2 = jqGrid2.getDataIDs();
            //すべての行のIDを取得する
            var ids = jqGrid.getDataIDs();
            for (var i = 0; i < ids2.length; i++) {
                var rowData = jqGrid2.jqGrid('getRowData', ids2[i]);
                jqGrid.jqGrid('addRowData',ids2[i], rowData, 'last');
            }
            jqGrid.jqGrid('setGridParam', {rowNum: ids.length + ids2.length});
            jqGrid2.jqGrid('clearGridData');
            allcheck()
        }
    }
});

function allcheck() {
    var dataLeft = $("#jqGrid").jqGrid("getRowData");
    var dataRight = $("#jqGrid2").jqGrid("getRowData");
    if (dataLeft == 0) {
        $("#cb_jqGrid")[0].checked = false;
    }
    if (dataRight == 0) {
        $("#cb_jqGrid2")[0].checked = false;
    }
}

// create ztree
function geneUnitTree(unitTrees) {
    $("#treeDemo").html(""); // clear
    var setting = {
        view: {
            selectedMulti: true,
            // expandAll: true,
            showIcon: false,
            expandSpeed: "fast"
            // open: true
        },
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "orgId",
                pIdKey: "upplevOrgId",
                rootPId: null
            },
            key: {
                name: "orgNmDisplay"
            }
        },
        callback: {
            onCheck: onCheck
        }
    };
    var treeNodes = unitTrees;
    $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.transformToArray(treeObj.getNodes());
    treeObj.expandNode(nodes[0],true);
}

$("#chooseBtn").click(function () {
    var personIdList = [];
    var checkedOrgList = [];

    var ids = $("#jqGrid2").jqGrid('getRowData');// すべての行のデータを取得すると、最後の行がいなくなりました
    //最後の行を処理する
    var allCountID = $("#jqGrid2").jqGrid('getDataIDs'); //すべての行のIDを取得する
    ids.push($("#jqGrid2").jqGrid('getRowData', allCountID[allCountID.length-1]));  //最後の行のデータを取得する。

    if (ids[0] && ids[0].length !== 0) {
        // ユーザーリストを取得
        for (var i = 0; i < ids.length; i++) {
            personIdList.push(ids[i].userId);
        }
    }

    // 画面．追加対象エリアで一件でもないの場合
    if (personIdList.length == 0) {
        var orgList = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
        for (var i = 0; i < orgList.length; i++) {
            checkedOrgList.push(orgList[i].orgId);
        }
        if (checkedOrgList.length === 0){
            personIdList = [];
        } else {
            var formData = new FormData();
            formData.append('list', JSON.stringify(checkedOrgList));
            formData.append('searchName', '');
            formData.append('loginId', '');
            formData.append('type', params.type);
            $.ajax({
                type: "POST",
                url: ctxPath + '/pop/F21074/getUsers',
                datatype: "json",
                data: formData,
                processData: false,
                contentType: false,
                async:false,
                success: function (data) {
                    if (data.code == 0) {
                        for (var i = 0; i < data.personList.length; i++) {
                            personIdList.push(data.personList[i].userId)
                        }
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        }
    }
    //全体配信フラグ
    if (document.getElementById("selectAll").checked) {
        allDeliverFlg = '1';
    } else {
        allDeliverFlg = '0';
    }
    parent.vm.allDeliverFlg = allDeliverFlg;
    if (params.type === '1') {
        parent.vm.stuIdList = personIdList;
    } else if (params.type === '2') {
        parent.vm.adminIdList = personIdList;
    }
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);

});

$("#closeBtn").click(function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});

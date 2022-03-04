$(function () {
    $("#jqGrid").jqGrid({
        url: '../../sys/role/list',
        datatype: "json",
        colModel: [
            { label: 'ロールID', name: 'roleId', index: "role_id", width: 45, key: true },
            { label: 'ロール名', name: 'roleName', index: "role_name", width: 75 },
//            { label: '所属部門', name: 'deptName', sortable: false, width: 75 },
            { label: '備考', name: 'remark', width: 100 },
            { label: '作成時間', name: 'createTime', index: "create_time", width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //gridのボトムスクロールバーを隠す
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

//メニューツリー
var menu_ztree;
var menu_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true
    }
};

//部門の構造ツリー
var dept_ztree;
var dept_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

//データツリー
var data_ztree;
var data_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    },
    check:{
        enable:true,
        nocheckInherit:true,
        chkboxType:{ "Y" : "", "N" : "" }
    }
};

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            roleName: null
        },
        showList: true,
        title:null,
        role:{
//            deptId:null,
//            deptName:null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新規";
            vm.role = {deptName:null, deptId:null};
            vm.getMenuTree(null);

//            vm.getDept();

//            vm.getDataTree();
        },
        update: function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "変更";
//            vm.getDataTree();
            vm.getMenuTree(roleId);

//            vm.getDept();
        },
        del: function () {
            var roleIds = getSelectedRows();
            if(roleIds == null){
                return ;
            }

            confirm('選択したレコードを削除してよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(roleIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('登録処理は完了しました。', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getRole: function(roleId){
            $.get("../../sys/role/info/"+roleId, function(r){
                vm.role = r.role;

                //チェックしたキャラが所有するメニュー
                var menuIds = vm.role.menuIdList;
                for(var i=0; i<menuIds.length; i++) {
                    var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
                    menu_ztree.checkNode(node, true, false);
                }

//                //チェックしたキャラが所有する部門のデータ権限
//                var deptIds = vm.role.deptIdList;
//                for(var i=0; i<deptIds.length; i++) {
//                    var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
//                    data_ztree.checkNode(node, true, false);
//                }
//
//                vm.getDept();
            });
        },
        saveOrUpdate: function () {
            //選択したメニューを取得する
            var nodes = menu_ztree.getCheckedNodes(true);
            var menuIdList = new Array();
            for(var i=0; i<nodes.length; i++) {
                menuIdList.push(nodes[i].menuId);
            }
            vm.role.menuIdList = menuIdList;

//            //選択したデータを取得する
//            var nodes = data_ztree.getCheckedNodes(true);
//            var deptIdList = new Array();
//            for(var i=0; i<nodes.length; i++) {
//                deptIdList.push(nodes[i].deptId);
//            }
//            vm.role.deptIdList = deptIdList;

            var url = vm.role.roleId == null ? "../../sys/role/save" : "../../sys/role/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.role),
                success: function(r){
                    if(r.code === 0){
                        alert('登録処理は完了しました。', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        getMenuTree: function(roleId) {
            //メニューツリーをロードする
            $.get("../../sys/menu/list", function(r){
                menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
                //すべてのノートを展開する
                menu_ztree.expandAll(true);

                if(roleId != null){
                    vm.getRole(roleId);
                }
            });
        },
//        getDataTree: function(roleId) {
//            //メニューツリーをロードする
//            $.get("../../sys/dept/list", function(r){
//                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
//                //すべてのノートを展開する
//                data_ztree.expandAll(true);
//            });
//        },
//        getDept: function(){
//            //部門ツリーをロードする
//            $.get("../../sys/dept/list", function(r){
//                dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
//                var node = dept_ztree.getNodeByParam("deptId", vm.role.deptId);
//                if(node != null){
//                    dept_ztree.selectNode(node);
//
//                    vm.role.deptName = node.name;
//                }
//            })
//        },
//        deptTree: function(){
//            layer.open({
//                type: 1,
//                offset: '50px',
//                skin: 'layui-layer-molv',
//                title: "部門を選択する",
//                area: ['300px', '450px'],
//                shade: 0,
//                shadeClose: false,
//                content: jQuery("#deptLayer"),
//                btn: ['確認', 'キャンセル'],
//                btn1: function (index) {
//                    var node = dept_ztree.getSelectedNodes();
//                    //选择上级部门
//                    vm.role.deptId = node[0].deptId;
//                    vm.role.deptName = node[0].name;
//
//                    layer.close(index);
//                }
//            });
//        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'roleName': vm.q.roleName},
                page:page
            }).trigger("reloadGrid");
        }
    }
});
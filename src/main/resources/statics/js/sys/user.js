$(function () {
    $("#jqGrid").jqGrid({
        url: '../../sys/user/list',
        datatype: "json",
        colModel: [
			{ label: 'ID', name: 'id', index: "id", width: 25, key: true },
			{ label: 'ユーザーID', name: 'usrId', width: 45},
			{ label: 'ユーザー名', name: 'usrNm', width: 75 },
			{ label: '権限', name: 'roleDiv', sortable: false, width: 75 },
            { label: '所属部門', name: 'orgId', sortable: false, width: 75 },
			{ label: '状態', name: 'lockFlg', width: 60, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">LOCK</span>' : 
					'<span class="label label-success">OK</span>';
			}},
			{ label: '作成時間', name: 'cretDatime', width: 85}
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
var setting = {
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
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            usrNm: null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新規";
            vm.roleList = {};
            vm.user = {deptName:null, deptId:null, status:1, roleIdList:[]};

            //キャラ情報を取得
            this.getRoleList();

            vm.getDept();
        },
        getDept: function(){
            //部門ツリーをロードする
            $.get("../../sys/dept/list", function(r){
                ztree = $.fn.zTree.init($("#deptTree"), setting, r);
                var node = ztree.getNodeByParam("deptId", vm.user.deptId);
                if(node != null){
                    ztree.selectNode(node);

                    vm.user.deptName = node.name;
                }
            })
        },
        update: function () {
            var userId = getSelectedRow();
            if(userId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "変更";

            vm.getUser(userId);
            //キャラ情報を取得する
            this.getRoleList();
        },
        del: function () {
            var userIds = getSelectedRows();
            if(userIds == null){
                return ;
            }

            confirm('選択したレコードを削除してよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
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
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "../../sys/user/save" : "../../sys/user/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
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
        getUser: function(userId){
            $.get("../../sys/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get("../../sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "部門を選択する",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['確認', 'キャンセル'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //上層部門を選択する
                    vm.user.deptId = node[0].deptId;
                    vm.user.deptName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'usrNm': vm.q.usrNm},
                page:page
            }).trigger("reloadGrid");
        }
    }
});
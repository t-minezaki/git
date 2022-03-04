var setting = {
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
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        menu:{
            parentName:null,
            parentId:0,
            type:1,
            orderNum:0
        }
    },
    methods: {
        getMenu: function(menuId){
            //メニューツリーをロードする
            $.get("../../sys/menu/select", function(r){
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
                ztree.selectNode(node);

                vm.menu.parentName = node.name;
            })
        },
        add: function(){
            vm.showList = false;
            vm.title = "新規";
            vm.menu = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getMenu();
        },
        update: function () {
            var menuId = getMenuId();
            if(menuId == null){
                return ;
            }

            $.get("../../sys/menu/info/"+menuId, function(r){
                vm.showList = false;
                vm.title = "変更";
                vm.menu = r.menu;

                vm.getMenu();
            });
        },
        del: function () {
            var menuId = getMenuId();
            if(menuId == null){
                return ;
            }

            confirm('選択したレコードを削除してよろしいですか', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/menu/delete/"+menuId,
                    //data: "menuId=" + menuId,
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
            });
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

            var url = vm.menu.menuId == null ? "../../sys/menu/save" : "../../sys/menu/update";
            $.ajax({
                type: "POST",
                url:  url,
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
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
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "メニューを選択する",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['確認', 'キャンセル'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //上層メニューを選択する
                    vm.menu.parentId = node[0].menuId;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Menu.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.menu.name)){
                alert("メニュー名は空にできません");
                return true;
            }

            //菜单
            if(vm.menu.type === 1 && isBlank(vm.menu.url)){
                alert("メニューURLは空にできません");
                return true;
            }
        }
    }
});


var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * テーブルの列を初期化する
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'メニューID', field: 'menuId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: 'メニュー名', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上層メニュー', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: 'アイコン', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
        }},
        {title: 'タイプ', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            if(item.type === 0){
                return '<span class="label label-primary">ディレクトリ</span>';
            }
            if(item.type === 1){
                return '<span class="label label-success">メニュー</span>';
            }
            if(item.type === 2){
                return '<span class="label label-warning">ボタン</span>';
            }
        }},
        {title: 'オーダー番号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: 'メニューURL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '320px'},
        {title: '権限マーク', field: 'perms', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};


function getMenuId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("1つのレコードを選択してください");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, "../../sys/menu/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("menuId");
    table.setCodeField("menuId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
});

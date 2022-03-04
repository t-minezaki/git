//メニューを作成する
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '    <a v-if="item.type === 0" href="javascript:;">',
        '        <i v-if="item.icon != null" :class="item.icon"></i>',
        '        <span>{{item.name}}</span>',
        '        <i class="fa fa-angle-left pull-right"></i>',
        '    </a>',
        '    <ul v-if="item.type === 0" class="treeview-menu">',
        '        <menu-item :item="item" v-for="item in item.list"></menu-item>',
        '    </ul>',

        '    <a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '        <i v-if="item.icon != null" :class="item.icon"></i>',
        '        <span>{{item.name}}</span>',
        '    </a>',

        '    <a v-if="item.type === 1 && item.parentId != 0" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframeのアダプティブ
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();

//登録メニューのコンポーネント
Vue.component('menuItem',menuItem);

var vm = new Vue({
    el:'#rrapp',
    data:{
        user:{},
        menuList:{},
        main:"main.html",
        password:'',
        newPassword:'',
        navTitle:"コンソール"
    },
    methods: {
        getMenuList: function (event) {
            $.getJSON("../sys/menu/nav?_"+$.now(), function(r){
                vm.menuList = r.menuList;
            });
        },
        getUser: function(){
            $.getJSON("../sys/user/info?_"+$.now(), function(r){
                vm.user = r.user;
            });
        },
        updatePassword: function(){
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "パスワード変更",
                area: ['600px', '300px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['確認','キャンセル'],
                btn1: function (index) {
                    if (vm.password == "") {
                        layer.alert($.format($.msg.MSGCOMD0001, "現在のパスワード"));
                        return false;
                    }
                    if (vm.newPassword == "") {
                        layer.alert($.format($.msg.MSGCOMD0001, "新しいパスワード"));
                        return false;
                    }
                    $.post("../sys/user/password",{password:vm.password, newPassword:vm.newPassword }, function(result){
                        if(result.code == 0){
                            layer.close(index);
                            layer.alert('登録処理は完了しました。', function(index){
                                location.reload();
                            });
                        } else {
                            layer.alert(result.msg);
                        }
                    });
//                    $.ajax({
//                        type: "POST",
//                        url: "../sys/user/password",
//                        data: {password:vm.password, newPassword:vm.newPassword },
//                        dataType: "json",
//                        success: function(result){
//                            if(result.code == 0){
//                                layer.close(index);
//                                layer.alert('登録処理は完了しました。', function(index){
//                                    location.reload();
//                                });
//                            } else {
//                                layer.alert(result.msg);
//                            }
//                        }
//                    });
                }
            });
        },
    },
    created: function(){
        this.getMenuList();
        this.getUser();
    },
    updated: function(){
        //ルーティング
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});



function routerList(router, menuList){
    for(var key in menuList){
        var menu = menuList[key];
        if(menu.type == 0){
            routerList(router, menu.list);
        }else if(menu.type == 1){
            router.add('#'+menu.url, function() {
                var url = window.location.hash;
                
                // iframeのurlを取り替える
                vm.main = "../" + url.replace('#', '');
                
                //ナビゲーションのメニューを展開する
                $(".treeview-menu li").removeClass("active");
                $("a[href='"+url+"']").parents("li").addClass("active");
                
                vm.navTitle = $("a[href='"+url+"']").text();
            });
        }
    }
}

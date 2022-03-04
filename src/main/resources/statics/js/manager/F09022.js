var equipment = getCookie("equipment");
if (equipment == 'tablet') {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09022-1.css"}).appendTo("head");
} else {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09022.css"}).appendTo("head");
}
param = getParam();
var limit = 15;
var flg = true;
$(function () {
    //「未読」
    $("#btn1").click(function () {
        flg = true;
        $(this).addClass("active");
        $("#btn2").removeClass("active").css("border-left", "0");
        limit = 15;
        $(".insert").css("display", "block");
        vm.init();
    });
    //全員」
    $("#btn2").click(function () {
        flg = false;
        $(this).addClass("active");
        $("#btn1").removeClass("active");
        limit = 15;
        $(".insert").css("display", "block");
        vm.init();
    });
});
var vm = new Vue({
    el: '.content',
    data: {
        stuList: [],
        showFlg:true
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            this.stuList = [];
            $.ajax({
                url: ctxPath + '/manager/F09022/init',
                type: 'GET',
                data: {
                    flag: flg,
                    noticeId: param.noticeId,
                    limit: limit
                },
                success: function (data) {
                    if (data.code != 0) {
                        vm.showFlg = false;
                        var index = layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                layer.close(index);
                            }
                        });
                    }
                    else {
                        //生徒
                        for (var i = 0; i < data.stuList.length; i++) {
                            data.stuList[i].codValue = data.stuList[i].codValue.substring(0, 1) + data.stuList[i].codValue.substring(2, 3);
                            // data.stuList[i].stuName = data.stuList[i].stuName +"    "+a;
                        }
                        vm.stuList = data.stuList;
                        if (data.lastPage) {
                            $(".insert").css("display", "none");
                        }
                    }

                }
            });
        },
        insert: function () {
            limit = limit + 15;
            vm.init();
        }
    }
});
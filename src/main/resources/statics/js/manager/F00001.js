//iframeのアダプティブ
$(window).on('resize', function () {
    var $content = $('.div_body');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();
var fold = "<img src=\"" + ctxPath + "/img/fold.png\" height=\"11\" width=\"9\"/>";
var unfold = "<img src=\"" + ctxPath + "/img/unfold.png\" height=\"11\" width=\"9\"/>";

var closeFlg = false;
$(".layui-nav-itemed").on('click','.a_main',function () {
    //サブメニューの非表示と表示
    $(this).next().toggle(200);
    if ($(this).find("i").css("transform") == "none") {
        //矢印アイコン回転
        $(this).find("i").css("transform", "rotate(180deg)");
    } else {
        //矢印アイコンの復元
        $(this).find("i").css("transform", "none");
    }
});
$(document).ready(function () {

    //メニュー縮小拡大ボタンをクリック
    $(".close_menu").click(function () {

        $("li.layui-nav-itemed dl").css("display", "none");

        closeFlg = !closeFlg;
        //メニューの非表示と表示
        if (closeFlg) {
            $(".div_right").css("width", "95%");
            $(".div_left").css("width", "5%");
            //矢印アイコンの復元
            $(this).css("width", "5%");
            $("#close_img").css("transform", "rotate(180deg)");
            $(".dis_label").hide();
            $(".layui-icon-triangle-r").css("display","none");
            $(".layui-icon-triangle-d").css("display", "none");
            $(".a_main").attr("disabled",true).css("pointer-events","none");
        } else {
            $(".div_left").css("width", "15%");
            $(".div_right").css("width", "85%");
            //矢印アイコン回転
            $(this).css("width", "15%");
            $("#close_img").css("transform", "rotate(0)");
            $(".dis_label").show();
            $(".layui-icon-triangle-r").css("display","block");
            $(".layui-icon-triangle-d").css("display", "block");
            $(".layui-icon-triangle-d").each(function () {
                var foldClass = $(this).eq(0);
                var name = foldClass.attr("class");
                var last = name.substring(name.lastIndexOf("-") + 1, name.length);
                if (last == "d") {
                    foldClass.removeClass(name);
                    foldClass.addClass(name.substring(0, name.length - 1) + "r");
                }
            });
            $(".layui-icon-triangle-r").each(function () {
                if ($(this).css("transform") != "none" || $(this).css("transform") != "undefined") {
                    $(this).css("transform", "none");
                }
            });
            $(".a_main").attr("disabled",false).css("pointer-events","unset");
        }
    });


});
var roleDiv = '';
// 2020/12/7 huangxinliang modify start
var orgSelectComponent = new Vue({
    el: '.header_operat',
    data: {
        orgIdList: [],
        sessionOrgId: ''
    },
    mounted: function () {
        this.loadOrg();
    },
    methods:{
        loadOrg: function (){
            $.ajax({
                url: ctxPath + '/manager/F00001/loadOrgList',
                datatype: "json",
                postData: {},
                success: function (data) {
                    if (data.code == 0) {
                        if (data.sessionOrgId){
                            orgSelectComponent.sessionOrgId = data.sessionOrgId;
                        }
                        if(data.orgIdList) {
                            orgSelectComponent.orgIdList = data.orgIdList;
                        }
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        }
    }
});
// 2020/12/7 huangxinliang modify end
var vm = new Vue({
    el: '.div_body',
    data: {
        userFunList: [],
        stuInfoMenu: [],
        stuMenuShowFlg: false,
        brandCd: ''
    },
    mounted: function () {
        this.menuInit();
    },
    updated: function () {
        $(".layui-nav-itemed").on('click', '.a_main', function () {
            //サブメニューの非表示と表示
            $(this).next().toggle(200);
            if ($(this).find("i").css("transform") == "none") {
                //矢印アイコン回転
                $(this).find("i").css("transform", "rotate(90deg)");
            } else {
                //矢印アイコンの復元
                $(this).find("i").css("transform", "none");
            }
        });
        $(".a_main").click(function () {
            var foldClass = $(this).find("i").eq(0);
            var name = foldClass.attr("class");
            var last = name.substring(name.lastIndexOf("-") + 1, name.length);
            if (last == "r") {
                foldClass.removeClass(name);
                foldClass.addClass(name.substring(0, name.length - 1) + "d");
                $(".thirdP").css("display", "block");
            } else {
                foldClass.removeClass(name);
                foldClass.addClass(name.substring(0, name.length - 1) + "r");
            }
        });
        if (roleDiv === '1') {
            $(".div_la").find("label").text("管理者ページ");
        } else {
            $(".div_la").find("label").text("先生ページ");
        }
    },
    methods:{
        //初期表示
        menuInit: function () {
            $.ajax({
                url: ctxPath + '/manager/F00001/init',
                datatype: "json",
                postData: {},
                success: function (data) {
                    if (data.code == 0) {
                        roleDiv = data.roleDiv.trim();
                        //取得したロール区分が「1：管理者」　且つ　取得した特殊権限フラグが「1：有」の場合、
                        if (data.specAuthFlg == '1' && data.roleDiv.trim() == '1') {
                            $("#specAuthArea").css("display", 'block');
                        }
                        if (data.brandCd) {
                            vm.brandCd = data.brandCd;
                        }
                        $(".changeOrg-div").css("display", data.disa?"none":"block");
                        if (data.userFunList) {
                            vm.userFunList = data.userFunList;
                            var firstLoad = false;
                            if (!firstLoad) {
                                var url = document.getElementById('ifr_main').contentWindow.location.href;
                                sessionStorage.setItem("url", url);
                            }
                            var funlist = [];
                            for (var i = 0; i < data.funList.length; i++) {
                                funlist.push(data.funList[i].funId);
                            }
                            if (funlist.indexOf("F21033") !== -1) {
                                var src = "F21033.html";
                                var iframe;
                                iframe = document.getElementById("ifr_main");
                                var url = sessionStorage.getItem("url");
                                if (sessionStorage.getItem("url").indexOf("blank") !== -1) {
                                    url = src;
                                    iframe.src = url;
                                    iframe.contentWindow.location.href = url;
                                }
                            }
                        }
                        vm.stuMenuShowFlg = data.stuMenuShowFlg;
                        if (data.stuInfoMenu) {
                            vm.stuInfoMenu = data.stuInfoMenu;
                        }
                    } else {
                        showMsg(data.msg);
                    }

                }
            });
        },
        //画面遷移URLの取得
        getUrl:function (funId) {
            if (funId != null){
                if (funId == 'F21003') {
                    $.ajax({
                        url: ctxPath + '/manager/F00001/getCount',
                        datatype: "json",
                        postData: {},
                        success: function (data) {
                            if (data.code == 0) {
                                if (data.count) {
                                    var count = data.count;
                                    // var count = Number(data.count) > 99?99:Number(data.count);
                                    $("a[href='F21003.html']").append('<div class="unCorrespond" style="position: absolute;\n' +
                                        '     width: 18px;\n' +
                                        '     height: 18px;\n' +
                                        '     border-radius: 50%;\n' +
                                        '     background: #cf2d28;\n' +
                                        '     color: #fff;\n' +
                                        '     font-size: 10px;\n' +
                                        '     right:4%;\n' +
                                        '     top: 30%;\n' +
                                        '     line-height: 18px;\n' +
                                        'text-align: center">' + count + '</div>');
                                }
                            }
                        }
                    })
                }
                if (funId == 'F09006') {
                    var orgId = window.parent.$("#orgList").val();
                    var url = funId + '.html?orgId=' + orgId;
                    // var url = funId + '.html';
                    return url;
                }
                var url = funId + '.html';
                return url;
            }
        }
    }
});
function loadFrame(obj) {
    if (!firstLoad) {
        var url = obj.contentWindow.location.href;
        sessionStorage.setItem("url", url);
    }
}
$(function () {
    $("#orgList").change(function () {
        var orgId = $("#orgList").val();
        $.ajax({
            url: ctxPath + '/manager/F00001/changeOrg',
            datatype: "json",
            type:'POST',
            data: {
                orgId: orgId
            },
            success: function (data) {
                if (data.code === 0){
                    document.getElementById('ifr_main').contentWindow.location.reload();
                }else {
                    layer.alert(data.msg);
                }
            }
        })
    })
})
$(document).on("click", ".menu_bar", function () {
    var url = $(this).find("a").attr("href");
    $("#html_content").attr("src", url.replace("#", ""));
});
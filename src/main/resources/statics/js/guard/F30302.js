$(function () {
    // 掲示板のタイトル
    $(".title").text(window.localStorage.getItem("title"));
    //掲示板情報の元サイト名
    $(".sourceName").text(window.localStorage.getItem("sourceName"));
    // 掲示板のコンテンツ
    if (window.localStorage.getItem("description").length > 100){
        $(".description").text(window.localStorage.getItem("description").substring(0,100)+"...");
    } else {
        $(".description").text(window.localStorage.getItem("description"));
    }
    // 掲示板の画像
    if (window.localStorage.getItem("img") == "undefined") {
        $(".img").hide();
    }else {
        $(".img").attr("src",window.localStorage.getItem("img"));
    }
    // 塾からの掲示板未読件数
    if (window.localStorage.getItem("count") > 0){
        $(".count").text(window.localStorage.getItem("count"));
        $(".count").show();
    }
    $.ajax({
        url: ctxPath + '/guard/F30302/init',
        data:{
            id : window.localStorage.getItem("id"),
            url : window.location.href
        },
        dataType:'json',
        type:'GET',
        async:true,
        success:function (data) {
        }
    });
})
function redirect() {
    // url
    window.location.href=window.localStorage.getItem("url");
}

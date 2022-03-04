if(getCookie("equipment") === "phone"){
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09019.css"}).appendTo("head");
    $("#hr").remove()
}else {
    $("#notice_add_btn").html("お知らせ作成");
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09019-1.css"}).appendTo("head");
}
var pageSize = 15;
var vm = new Vue({
    el: "#page",
    data: {
        f09019Dtos: []
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/manager/F09019/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.f09019Dtos) {
                            vm.f09019Dtos = vm.f09019Dtos.concat(da.f09019Dtos);
                        }
                        if (da.f09019Dtos.length < pageSize) {
                            $(".insert").hide();
                        }
                    } else {
                        layer.alert(da.msg);
                        $(".insert").css("display", "none");
                    }
                }
            });
        },
        pinkOrGrey: function (f09019Dto) {
            var date = new Date().Format('yyyy/MM/dd');
            return  (date>=f09019Dto.startTimeStr&&date<=f09019Dto.endTimeStr)?"background: rgb(255, 226, 234)":"background: #CCCCCC"
        },
        toEdit: function (noticeId) {
            window.location.href = 'F09020.html?noticeId=' + noticeId;
        },
        checkDeliver: function (noticeId) {
            window.location.href = 'F09022.html?noticeId=' + noticeId;
        },
        insert: function () {
            pageSize = pageSize + 15;
            this.showData();
        }
    }
});
// $(".list_ul").bind("scroll", function () {
//     if(getScrollHeight() == getDocumentTop() + getWindowHeight() + 1){
//         //当滚动条到底时,触发内容
//         //ajax_function()
//         alert("滑动到的底部");
//     }
// });
// //文档高度
// function getDocumentTop() {
//     var scrollTop =  0, bodyScrollTop = 0, documentScrollTop = 0;
//     // if (document.body) {
//     //     bodyScrollTop = document.body.scrollTop;
//     // }
//     if (document.getElementById("list_ul")) {
//         documentScrollTop = document.getElementById("list_ul").scrollTop;
//     }
//     scrollTop = documentScrollTop;
//     return scrollTop;
// }
// //可视窗口高度
// function getWindowHeight() {
//     var windowHeight = 0;
//     if (document.compatMode == "CSS1Compat") {
//         windowHeight = document.getElementById("list_ul").clientHeight;
//     } else {
//         windowHeight = document.body.clientHeight;
//     }
//     return windowHeight;
// }
// //滚动条滚动高度
// function getScrollHeight() {
//     var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
//     // if (document.body) {
//     //     bodyScrollHeight = document.body.scrollHeight;
//     // }
//     if (document.getElementById("list_ul")) {
//         documentScrollHeight = document.getElementById("list_ul").scrollHeight;
//     }
//     scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
//     return scrollHeight;
// }
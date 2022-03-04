var page = 1;
var pageSize = 30;
var vm = new Vue({
    el: "#page",
    data: {
        showList: [],
        total: "",
        count: "",
        count1:"",
        imgPath:""
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
        $(function () {
            var webHeight = document.body.clientHeight;
            var contHeight = webHeight - 145;
            document.getElementById("content").style.height = contHeight + "px";
        });
        $(function () {
            $(".title img").remove();
            $(".title video").remove();
            $(".title a").remove();
            var title = document.getElementsByClassName("title");
            var contentCut;
            var size = 12;
            $.each(title, function (i) {
                if (title[i].innerText.length > size) {
                    contentCut = title[i].innerText.substring(0, size) + "・・・";
                    title[i].innerText = contentCut;
                }
            })
        });
        if (page === 1 || page === 0) {
            $("#prev").addClass("gray");
        } else {
            $("#prev").removeClass("gray");
        }

        if (Math.ceil(vm.total / pageSize) === page) {
            $("#next").addClass("gray");
        } else {
            $("#next").removeClass("gray");
        }
        var cont = Math.ceil(vm.total / pageSize) + "　ページ中　" + page + "　 ページ目 ";
        $("#pageFoot").html(cont);
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/guard/F30401/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: 1
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.showList) {
                            vm.showList = da.showList;
                        }
                        if (da.total) {
                            vm.total = da.total;
                        }
                        if(da.total === 0){
                            page = 0;
                        }
                        if (da.count > 0) {
                            vm.count = da.count;
                            $(".count").show();
                        }
                        if (da.count1 > 0) {
                            vm.count1 = da.count1;
                            $(".count1").show();
                        }
                        if(da.imgPath){
                            vm.imgPath = da.imgPath;
                        }
                    }
                }
            })
        },
        pageChange: function (pageFlg) {
            var pg;
            if (pageFlg === 0) {
                if (page === 1 || page === 0) {
                    return;
                } else {
                    pg = page - 1;
                }
            } else {
                if (page === Math.ceil(vm.total / pageSize)) {
                    return;
                } else {
                    pg = page + 1;
                }
            }
            $.ajax({
                url: ctxPath + '/guard/F30401/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: pg
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.showList) {
                            vm.showList = da.showList;
                        }
                        if (pageFlg === 0) {
                            if (page > 1) {
                                page -= 1;
                            }
                        } else {
                            if (page < Math.ceil(vm.total / pageSize)) {
                                page += 1;
                            }
                        }
                    }
                }
            })
        },
        toF30402: function (id) {
            window.location.href = "F30402.html?eventId=" + id;
        },
        show: function (flg) {
            if ("1" === flg) {
                return "display: block!important;";
            }
        }
    }
});
//
// function showImport(levDiv) {
//     $.ajax({
//         url: ctxPath + '/guard/F30112/init',
//         type: 'get',
//         datatype: 'json',
//         data: {
//             limit: pageSize,
//             page: 1,
//             levDiv: levDiv
//         },
//         success: function (da) {
//             $("#titleUl").find("li").removeClass("active1");
//             $("#titleUl").find("li").eq(1).addClass("active1");
//             if (da.code === 0) {
//                 if (da.showList) {
//                     vm.showList = da.showList;
//                 }
//                 if (da.total) {
//                     vm.total = da.total;
//                 }
//             }
//         }
//     })
// }
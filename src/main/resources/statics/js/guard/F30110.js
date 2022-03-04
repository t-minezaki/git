var page = 1;
var pageSize = 30;
var url=window.location.href
var vm = new Vue({
    el: "#pageF30104",
    data: {
        showList: [],
        total: "",
        count: ""
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
        $(function () {
            // var webHeight = document.body.clientHeight;
            // var ulHeight = document.getElementsByTagName("ul")[0].offsetHeight;
            // var contHeight = webHeight - 145;
            // var contPadding = ulHeight;
            // document.getElementById("content").style.height = contHeight + "px";
            // document.getElementById("content").style.paddingTop = contPadding + "px";
        });
        // $(function () {
        //     var textArea = document.getElementsByTagName("textarea");
        //     var contentCut;
        //     var size = 77;
        //     $.each(textArea, function (i) {
        //         if (textArea[i].textLength > size) {
        //             contentCut = textArea[i].textContent.substring(0, size);
        //             textArea[i].textContent = contentCut;
        //         }
        //     })
        // });
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
                url: ctxPath + '/guard/F30110/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: 1,
                    url:url
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.showList) {
                            vm.showList = da.showList;
                        }
                        if (da.total) {
                            vm.total = da.total;
                        }
                        if(da.total == 0){
                            page = 0;
                        }
                        if (da.count > 0) {
                            vm.count = da.count;
                            $(".count").show();
                        }
                    }
                }
            })
        },
        pageChange: function (pageFlg) {
            var pg;
            if (pageFlg === 0) {
                if (page === 1 || page === 0) {
                    return false;
                } else {
                    pg = page - 1;
                }
            } else {
                if (page === Math.ceil(vm.total / pageSize)) {
                    return false;
                } else {
                    pg = page + 1;
                }
            }
            $.ajax({
                url: ctxPath + '/guard/F30110/init',
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
        toF30111: function (id) {
            window.location.href = "F30111.html?noticeId=" + id;
        },
        show:function (flg) {
            if ("2" === flg){
                return "display: block!important;";
            }
        }
    }
});

$("#topNews").on("click", function () {
    window.location.href = "F30301.html";
});
$("#schNews").on("click", function () {
    window.location.reload();
});
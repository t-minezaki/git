var param = getParam();
if (param.stuId !="undefined") {
    $("#_title").addClass("disPlayNone")
}
$(function () {
    var dom1 = $(".title_tab_one").find("li");
    var dom2 = $(".title_tab_two").find("li");
    dom1.click(function () {
        dom1.removeClass("active");
        $(this).addClass("active");
    });
    dom2.click(function () {
        dom2.removeClass("active");
        $(this).addClass("active");
    });
    $("#tomorrow").click(function () {
        // window.location.href = "/F10505.html";
    });
});

var vm = new Vue({
    el: "#el",
    data: {
        schyList: [],
        stuSchy: {},
        schyDiv: "1",
        testTypeDiv: "1",
        pointList: [],
        subjtTitle: []
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
        var height = $(window).height() - $("header").innerHeight()
            - $(".title_tab_one").innerHeight() - $(".title_tab_two").innerHeight() - $(".contentTitle").innerHeight() - 20;
        $(".contentT").css("height", height);
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + "/student/F10503/init",
                type: "get",
                data:{
                    "url":window.location.href,
                    stuId: param.stuId
                },
                datatype: "json",
                success: function (data) {
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                    }
                    if (data.stuSchy) {
                        vm.stuSchy = data.stuSchy;
                    }
                    if (data.code === 0) {
                        if (data.pointList) {
                            vm.pointList = data.pointList;
                        }
                        if (data.subjtTitle) {
                            vm.subjtTitle = data.subjtTitle;
                        }
                        $(".trTitle").show();
                    } else {
                        var height = $(window).height() - $("header").innerHeight()
                            - $(".title_tab_one").innerHeight() - $(".title_tab_two").innerHeight() - $(".contentTitle").innerHeight() - 20 - ($(window).width()/100)*2.6;
                        $(".contentT").css("height", height);
                        showMsg(data.msg);
                    }
                }
            })
        },
        goalBtn: function () {
            window.location.href = "F10507.html?stuId=" + param.stuId;
        },
        resultBtn: function () {
            window.location.reload();
        },
        typeBtn: function (type) {
            vm.testTypeDiv = type;
            var url=window.location.href;
            $.ajax({
                type: "get",
                url: ctxPath + "/student/F10503/init",
                contentType: "application/json",
                data: {
                    url:url,
                    schyDiv: vm.stuSchy.schyDiv,
                    testTypeDiv: type,
                    stuId: param.stuId
                },
                success: function (data) {
                    $("#message").removeClass("hasHeight");
                    $("#message").hide();
                    $(".trTitle").hide();
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                        vm.stuSchy = data.stuSchy;
                    } else {
                        vm.schyList = "";
                        vm.stuSchy = "";
                    }
                    if (data.msg === "success") {
                        if (data.pointList) {
                            vm.pointList = data.pointList;
                        }
                        if (data.subjtTitle) {
                            vm.subjtTitle = data.subjtTitle;
                        }
                        if (data.subjtList) {
                            vm.subjtList = data.subjtList;
                        }
                        $(".trTitle").show();
                    } else {
                        vm.pointList = "";
                        vm.subjtTitle = "";
                        vm.subjtList = "";
                        var height = $(window).height() - $("header").innerHeight()
                            - $(".title_tab_one").innerHeight() - $(".title_tab_two").innerHeight() - $(".contentTitle").innerHeight() - 20 - ($(window).width()/100)*2.6;
                        $(".contentT").css("height", height);
                        showMsg(data.msg);
                    }
                }
            })
        },
        schyChange: function (schyList) {
            var myselect = document.getElementById("schySelect");
            var index = myselect.selectedIndex;
            if (schyList[index]){
                var schyDiv = schyList[index].schyDiv;
            }
            vm.schyDiv = schyDiv;
            $.ajax({
                type: "get",
                url: ctxPath + "/student/F10503/init",
                contentType: "application/json",
                data: {
                    schyDiv: schyDiv,
                    testTypeDiv: vm.testTypeDiv,
                    stuId: param.stuId
                },
                success: function (data) {
                    $("#message").removeClass("hasHeight");
                    $("#message").hide();
                    $(".trTitle").hide();
                    if (data.msg === "success") {
                        vm.pointList = data.pointList;
                        vm.subjtTitle = data.subjtTitle;
                        vm.subjtList = data.subjtList;
                        $(".trTitle").show();
                    } else {
                        vm.pointList = "";
                        vm.subjtTitle = "";
                        vm.subjtList = "";
                        var height = $(window).height() - $("header").innerHeight()
                            - $(".title_tab_one").innerHeight() - $(".title_tab_two").innerHeight() - $(".contentTitle").innerHeight() - 20 - ($(window).width()/100)*2.6;
                        $(".contentT").css("height", height);
                        showMsg(data.msg);
                    }
                }
            })
        },
        getClass: function (i) {
            if (i === 6 || i === 11) {
                return "blue";
            }
        },
        setTitleClass: function (i) {
            if (i === 6 || i === 11) {
                return "green";
            }
        }
    }
});

window.onload = function (ev) {
    $("#iframe").contents().find("#grade_img").css('width','50%');
}
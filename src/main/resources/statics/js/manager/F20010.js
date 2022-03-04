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
        // window.location.href = "/F20012.html";
    });
});

var vm = new Vue({
    el: "#el",
    data: {
        schyList: [],
        stuSchy: {},
        schyDiv: "1",
        testTypeDiv: "1",
        pointList: '',
        subjtTitle: [],
        stuNm:"",
        mentorNm:""
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + "/manager/F20010/init",
                type: "get",
                datatype: "json",
                success: function (data) {
                    if (data.stuNm){
                        vm.stuNm = data.stuNm;
                    }
                    if (data.mentorNm){
                        vm.mentorNm = data.mentorNm;
                    }
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                        vm.stuSchy = data.stuSchy;
                    }
                    if (data.msg === "success") {
                        if (data.pointList) {
                            vm.pointList = data.pointList;
                        }
                        if (data.subjtTitle) {
                            vm.subjtTitle = data.subjtTitle;
                        }
                        $(".trTitle").show();
                        $(".contentT").css("display","block");
                    } else {
                        showMsg(data.msg);
                    }
                }
            })
        },
        typeBtn: function (type) {
            vm.testTypeDiv = type;
            $.ajax({
                type: "get",
                url: ctxPath + "/manager/F20010/init",
                contentType: "application/json",
                data: {
                    schyDiv: vm.stuSchy.schyDiv,
                    testTypeDiv: type
                },
                success: function (data) {
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
                        $(".contentT").css("display","block");
                        $(".trTitle").show();
                    } else {
                        vm.pointList = "";
                        vm.subjtTitle = "";
                        vm.subjtList = "";
                        $(".contentT").css("display","none");
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
                url: ctxPath + "/manager/F20010/init",
                contentType: "application/json",
                data: {
                    schyDiv: schyDiv,
                    testTypeDiv: vm.testTypeDiv
                },
                success: function (data) {
                    if (data.msg === "success") {
                        vm.pointList = data.pointList;
                        vm.subjtTitle = data.subjtTitle;
                        vm.subjtList = data.subjtList;
                        $(".contentT").css("display","block");
                    } else {
                        vm.pointList = "";
                        vm.subjtTitle = "";
                        vm.subjtList = "";
                        $(".contentT").css("display","none");
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
            if (i === 6 || i === 11){
                return "green";
            }
        }
    }
});
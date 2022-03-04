$("#tomorrow").click(function () {
    // window.location.href = "/F30108.html";
});

var vm = new Vue({
    el: "#el",
    data: {
        pointList1: [],
        pointList2: [],
        pointList3: [],
        subjtTitle1: [],
        subjtTitle2: [],
        subjtTitle3: [],
        schyList: [],
        stuSchy: {},
        tgtYMList: [],
        resultPointsNewUpDateTime: [],
        schyDiv: "1",
        testTypeDiv: "1"
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + "/guard/F30106/init",
                data:{
                    url:window.location.href,
                },
                type: "get",
                datatype: "json",
                success: function (data) {
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                        vm.stuSchy = data.stuSchy;
                    }
                    if (data.msg === "success") {
                        if (data.pointList1) {
                            vm.pointList1 = data.pointList1;
                        }
                        if (data.pointList2) {
                            vm.pointList2 = data.pointList2;
                        }
                        if (data.pointList3) {
                            vm.pointList3 = data.pointList3;
                        }
                        if (data.subjtTitle1) {
                            vm.subjtTitle1 = data.subjtTitle1;
                        }
                        if (data.subjtTitle2) {
                            vm.subjtTitle2 = data.subjtTitle2;
                        }
                        if (data.tgtYMList) {
                            vm.tgtYMList = data.tgtYMList;
                            vm.resultPointsNewUpDateTime = data.resultPointsNewUpDateTime;
                        }
                    } else {
                        var index = layer.alert(data.msg, {
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
                }
            })
        },
        typeBtn: function (type) {
            vm.testTypeDiv = type;
            $.ajax({
                type: "get",
                url: ctxPath + "/guard/F30106/init",
                contentType: "application/json",
                data: {
                    url:window.location.href,
                    schyDiv: vm.stuSchy.schyDiv,
                    testTypeDiv: type
                },
                success: function (data) {
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                        vm.stuSchy = data.stuSchy;
                    } else {
                        vm.schyList = "";
                    }
                    if (data.msg === "success") {
                        if (data.pointList1) {
                            vm.pointList1 = data.pointList1;
                        }
                        if (data.pointList2) {
                            vm.pointList2 = data.pointList2;
                        }
                        if (data.pointList3) {
                            vm.pointList3 = data.pointList3;
                        }
                        if (data.subjtTitle1) {
                            vm.subjtTitle1 = data.subjtTitle1;
                        }
                        if (data.subjtTitle2) {
                            vm.subjtTitle2 = data.subjtTitle2;
                        }
                        if (data.subjtTitle3) {
                            vm.subjtTitle3 = data.subjtTitle3;
                        }
                        if (data.tgtYMList) {
                            vm.tgtYMList = data.tgtYMList;
                            vm.resultPointsNewUpDateTime = data.resultPointsNewUpDateTime;
                        }
                    } else {
                        vm.pointList1 = "";
                        vm.pointList2 = "";
                        vm.pointList3 = "";
                        vm.subjtTitle1 = "";
                        vm.subjtTitle2 = "";
                        vm.subjtTitle3 = "";
                        vm.tgtYMList = "";
                        var index = layer.alert(data.msg, {
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
                }
            })
        },
        schyChange: function (schyList) {
            var myselect = document.getElementById("schySelect");
            var index = myselect.selectedIndex;
            var schyDiv = schyList[index].schyDiv;
            $.ajax({
                type: "get",
                url: ctxPath + "/guard/F30106/init",
                contentType: "application/json",
                data: {
                    url:window.location.href,
                    schyDiv: schyDiv,
                    testTypeDiv: vm.testTypeDiv
                },
                success: function (data) {
                    if (data.msg === "success") {
                        if (data.pointList1) {
                            vm.pointList1 = data.pointList1;
                        }
                        if (data.pointList2) {
                            vm.pointList2 = data.pointList2;
                        }
                        if (data.pointList3) {
                            vm.pointList3 = data.pointList3;
                        }
                        if (data.subjtTitle1) {
                            vm.subjtTitle1 = data.subjtTitle1;
                        }
                        if (data.subjtTitle2) {
                            vm.subjtTitle2 = data.subjtTitle2;
                        }
                        if (data.subjtTitle3) {
                            vm.subjtTitle3 = data.subjtTitle3;
                        }
                        if (data.tgtYMList) {
                            vm.tgtYMList = data.tgtYMList;
                            vm.resultPointsNewUpDateTime = data.resultPointsNewUpDateTime;
                        }
                    } else {
                        vm.pointList1 = "";
                        vm.pointList2 = "";
                        vm.pointList3 = "";
                        vm.subjtTitle1 = "";
                        vm.subjtTitle2 = "";
                        vm.subjtTitle3 = "";
                        var index = layer.alert(data.msg, {
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
                }
            })
        },
        timeChange: function (schyList, tgtYMList) {
            var myselect1 = document.getElementById("schySelect");
            var index1 = myselect1.selectedIndex;
            var schyDiv = schyList[index1].schyDiv;

            var myselect2 = document.getElementById("timeSelect");
            var index = myselect2.selectedIndex;
            var testTgtY = tgtYMList[index].testTgtY;
            var testTgtM = tgtYMList[index].testTgtM;
            var testKindDiv = tgtYMList[index].testKindDiv;
            $.ajax({
                type: "get",
                url: ctxPath + "/guard/F30106/init",
                contentType: "application/json",
                data: {
                    url:window.location.href,
                    schyDiv: schyDiv,
                    testTypeDiv: vm.testTypeDiv,
                    testTgtY: testTgtY,
                    testTgtM: testTgtM,
                    testKindDiv: testKindDiv
                },
                success: function (data) {
                    if (data.msg === "success") {
                        if (data.pointList1) {
                            vm.pointList1 = data.pointList1;
                        }
                        if (data.pointList2) {
                            vm.pointList2 = data.pointList2;
                        }
                        if (data.pointList3) {
                            vm.pointList3 = data.pointList3;
                        }
                        if (data.subjtTitle1) {
                            vm.subjtTitle1 = data.subjtTitle1;
                        }
                        if (data.subjtTitle2) {
                            vm.subjtTitle2 = data.subjtTitle2;
                        }
                        if (data.subjtTitle3) {
                            vm.subjtTitle3 = data.subjtTitle3;
                        }
                    } else {
                        vm.pointList1 = "";
                        vm.pointList2 = "";
                        vm.pointList3 = "";
                        vm.subjtTitle1 = "";
                        vm.subjtTitle2 = "";
                        vm.subjtTitle3 = "";
                        showMsg(data.msg);
                    }
                }
            })
        },
        getClass: function (i, length, flg) {
            // if (flg !== 1) {
            //     if (i === length) {
            //         return "blue";
            //     }
            // } else {
            //     if (i === length - 1) {
            //         return "blue";
            //     }
            //     if (i === length) {
            //         return "disNone";
            //     }
            // }
        },
        setTitleClass: function (i, length, flg) {
            // if (flg !== 1) {
            //     if (i === length - 1) {
            //         return "green";
            //     }
            // } else {
            //     if (i === length - 2) {
            //         return "green";
            //     }
            //     if (i === length - 1) {
            //         return "disNone";
            //     }
            // }
        }
    }
});
$(function () {
    var dom1 = $(".tab").find("li");
    dom1.click(function () {
        for (var i = 0; i < dom1.length; i++) {
            $(dom1).removeClass("active");
        }
        $(this).addClass("active");
    });
});
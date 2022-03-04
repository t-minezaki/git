$(function () {
    var windowH = $(window).height();
    $("body").css("max-height", windowH);
    $(".header_logo").click(function () {
        $(".topMessage").addClass("hasHeight");
        $(".topMessage").animate({opacity: '1'}, "500");
        setTimeout(function () {
            $(".topMessage").animate({opacity: '0'}, "500");
        }, 2000)
        setTimeout(function () {
            $(".topMessage").removeClass("hasHeight");
        }, 2500)
    })
    $("#tab_twothr").tabs();
    var item = $(".tab_subject").find("li")
    item.click(function () {
        item.removeClass("liactive")
        $(this).addClass("liactive")
    })
    $(".tbpop").click(function () {
        if ($(".info_subj_div").hasClass("disNone")) {
            $(".info_subj_div").removeClass("disNone")
            var index = $(this).parent().parent().find("tr").index($(this).parent()[0]) - 2;
            sessionStorage.setItem("index", JSON.parse(index))
        } else {
            $(".info_subj_div").addClass("disNone")
        }
    })
    var treelist = $("#treelist").children()
    treelist.click(function () {
        var index = JSON.parse(sessionStorage.getItem("index"));
        var value = $(this).text()
        $(".tbpop").eq(index).html(value)
        $(".info_subj_div").addClass("disNone")
    })
    $(".title_tab_two").on('tap', '.ST', function () {
        var attr = $(this).index()

    })

});
var formData = new Map();
var saveMap = new Map();
//changeCheck
var nowFormData = new Map();
//初期のSerialize
var oldSerialize;
//今のSerialize
var nowSerialize;

function getChangeStatus() {
    var flg = true;
    for (var i = 0; i <= parseInt(vm.listSize); i++) {
        if (nowFormData.get(i) != formData.get(i)) {
            flg = false;
        }
    }
    nowSerialize = $(".mySelect").serialize();
    if (flg && nowSerialize == oldSerialize) {
        return true;
    } else {
        return false;
    }
}

var vm = new Vue({
    el: "#app",
    data: {
        dtoMap: {},
        stuInfo: {},
        textchocList: {},
        learnSeasnList: {},
        termPlanList: {},
        subjtDiv: "",
        crmschId: "",
        listSize: "",
        defaultList: {},
        stuNm: "",
        mentorNm: ""
    },
    updated: function () {
        document.body.scrollTop = document.documentElement.scrollTop = 0;
        formData = new Map();
        nowFormData = new Map();
        for (var i = 0; i < parseInt(vm.listSize); i++) {
            var count = -1;
            for (var j = 0; j <= 7; j++) {
                if ($("#" + i + '_' + j)[0].style.background != '') {
                    count++;
                }
            }
            formData.set(i, count);
        }
        for (var i = 0; i < parseInt(vm.listSize); i++) {
            var count = -1;
            for (var j = 0; j <= 7; j++) {
                if ($("#" + i + '_' + j)[0].style.background != '') {
                    count++;
                }
            }
            nowFormData.set(i, count);
        }
        //selectのserialize
        oldSerialize = $(".mySelect").serialize();
        $(function () {
            $("#tab_twothr").tabs();
            //教科別メニュー
            var item = $(".title_tab_two").find("li");

            item.click(function () {
                if ($(this).hasClass("active")) {
                    return;
                }
                ;

                var backcolor = $(this).attr("acceptColor");
                var path2 = $(this).attr("acceptPath2");

                var _this = $(this);


                var subjtDiv = $(this).attr("desc");
                var textbId = $(this).attr("textbId")
                var newLi = $(this);
                var flg = true;
                for (var i = 0; i <= parseInt(vm.listSize); i++) {
                    if (nowFormData.get(i) != formData.get(i)) {
                        flg = false;
                    }
                }
                nowSerialize = $(".mySelect").serialize();
                if (flg && nowSerialize == oldSerialize) {

                    $(".title_tab_two").find("li").removeClass("active");
                    $(this).addClass("active");
                    for (var i = 0; i < $(".title_tab_two").find("li").length; i++) {
                        var back = $(".title_tab_two").find("li").eq(i).attr("acceptColor");
                        var path1 = $(".title_tab_two").find("li").eq(i).attr("acceptPath1");
                        $(".title_tab_two").find("li").eq(i).css("background", "");
                        $(".title_tab_two").find("li").eq(i).find("p").eq(0).css("color", back);
                        $(".title_tab_two").find("li").eq(i).find("img").eq(0).attr("src", path1);
                    }
                    _this.css("background", backcolor);
                    _this.find("img").eq(0).attr("src", path2);

                    var oldObj = $("#crrentLi");
                    oldObj.attr("id", "");
                    newLi.attr("id", "crrentLi");

                    var len = $(".ratio").children().length + 1;
                    for (var i = 1; i < len + 1; i++) {
                        $(".ratio").children().eq(i - 1).children().eq(0).attr('src', '../img/ratio' + i + '.png');
                    }
                    $(".ratio").children().eq(1).children().eq(0).attr('src', '../img/ratio2active.png');
                    $(".ratio").children().removeClass("active");
                    $(".ratio").children().eq(1).addClass("active");

                    //初期の位置に戻る
                    formData.forEach(function (value, key, map) {
                        for (var i = 0; i <= value; i++) {
                            if ($("#" + key + "_" + i).css("background-color") == "rgba(0, 0, 0, 0)") {
                                $("#" + key + "_" + i).css("background", "#d1d1d1");
                                $("#" + key + "_" + i).addClass("inCheck");
                            }
                        }
                        for (var i = value + 1; i < 8; i++) {
                            if ($("#" + key + "_" + i).css("background", "rgb(209, 209, 209)")) {
                                $("#" + key + "_" + i).css("background", "");
                                $("#" + key + "_" + i).removeClass("inCheck");
                            }
                        }
                    })
                    vm.showSubjtTimePlan(subjtDiv, textbId);
                } else {
                    var index = layer.confirm($.msg.MSGCOMN0051, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['キャンセル', '確認'],
                        btn1: function () {
                            layer.close(index);
                        },
                        btn2: function () {
                            $(".title_tab_two").find("li").removeClass("active");
                            $(this).addClass("active");
                            for (var i = 0; i < $(".title_tab_two").find("li").length; i++) {
                                var back = $(".title_tab_two").find("li").eq(i).attr("acceptColor");
                                var path1 = $(".title_tab_two").find("li").eq(i).attr("acceptPath1");
                                $(".title_tab_two").find("li").eq(i).css("background", "");
                                $(".title_tab_two").find("li").eq(i).find("p").eq(0).css("color", back);
                                $(".title_tab_two").find("li").eq(i).find("img").eq(0).attr("src", path1);
                            }
                            _this.css("background", backcolor);
                            _this.find("img").eq(0).attr("src", path2);
                            _this.find("p").eq(0).css("color", "white");

                            var oldObj = $("#crrentLi");
                            oldObj.attr("id", "");
                            newLi.attr("id", "crrentLi");

                            var len = $(".ratio").children().length + 1;
                            for (var i = 1; i < len + 1; i++) {
                                $(".ratio").children().eq(i - 1).children().eq(0).attr('src', '../img/ratio' + i + '.png');
                            }
                            $(".ratio").children().eq(1).children().eq(0).attr('src', '../img/ratio2active.png');
                            $(".ratio").children().removeClass("active");
                            $(".ratio").children().eq(1).addClass("active");

                            //初期の位置に戻る
                            formData.forEach(function (value, key, map) {
                                for (var i = 0; i <= value; i++) {
                                    if ($("#" + key + "_" + i).css("background-color") == "rgba(0, 0, 0, 0)") {
                                        $("#" + key + "_" + i).css("background", "#d1d1d1");
                                        $("#" + key + "_" + i).addClass("inCheck");
                                    }
                                }
                                for (var i = value + 1; i < 8; i++) {
                                    if ($("#" + key + "_" + i).css("background", "rgb(209, 209, 209)")) {
                                        $("#" + key + "_" + i).css("background", "");
                                        $("#" + key + "_" + i).removeClass("inCheck");
                                    }
                                }
                            })
                            vm.showSubjtTimePlan(subjtDiv, textbId);
                            layer.close(index);
                        }
                    });
                }
            })
        });

        autoRowSpan(textbTimeInfo, 2, 0);
    },
    mounted: function () {
        this.showSubjtTimePlanInit();
    },
    methods: {
        showF20004Data: function () {
            $.ajax({
                url: ctxPath + '/manager/F20004/init',
                type: 'get',
                datatype: 'json',
                async: true,
                success: function (data) {
                    vm.dtoMap = data.dtoMap;
                },
                error: function () {
                    alert("error");
                }
            })
        },
        showSubjtTimePlanInit: function () {
            $.ajax({
                url: ctxPath + '/manager/F20003/getSubjtPlanTimeInit',
                type: 'get',
                datatype: 'json',
                async: true,
                success: function (data) {
                    vm.stuNm = data.stuNm;
                    vm.mentorNm = data.mentorNm;
                    if (data.code == 0) {
                        vm.stuInfo = data.stuInfo;
                        vm.textchocList = data.textchocList;
                        vm.subjtDiv = data.textchocList[0].subjtDiv;
                        vm.textbId = data.textchocList[0].textbId;
                        vm.crmschId = data.stuInfo.orgId;
                        vm.showSubjtTimePlan(vm.subjtDiv, vm.textbId);
                    } else {
                        vm.stuInfo = data.stuInfo;
                        showMsg(data.msg);
                    }
                }
            });
        },
        showSubjtTimePlan: function (subjtDiv, textbId) {
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(1).addClass("checkCurrent");
            vm.subjtDiv = subjtDiv;
            vm.textbId = textbId;
            $.ajax({
                url: ctxPath + '/manager/F20003/getSubjtPlanTime',
                data: {
                    subjtDiv: vm.subjtDiv,
                    crmschId: vm.crmschId,
                    textbId: vm.textbId
                },
                type: 'get',
                datatype: 'json',
                async: true,
                success: function (data) {
                    if (data.code == 0) {
                        vm.learnSeasnList = data.learnSeasnList;
                        vm.termPlanList = data.termPlanList;
                        vm.listSize = data.listSize;
                        vm.defaultList = vm.termPlanList;
                    } else {
                        vm.learnSeasnList = data.learnSeasnList;
                        vm.termPlanList = data.termPlanList;
                        vm.listSize = data.listSize;
                        showMsg(data.msg);
                    }
                },
                error: function (data) {
                    showMsg(data.msg);
                }
            });
        },
        //「週別の学習時間を確認」を押下する時、週別タームプラン確認画面へ遷移する。
        migrate: function () {
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(1).addClass("checkCurrent");
            var flg = true;
            for (var i = 0; i <= parseInt(vm.listSize); i++) {
                if (nowFormData.get(i) != formData.get(i)) {
                    flg = false;
                }
            }
            nowSerialize = $("select").serialize();
            if (flg && nowSerialize == oldSerialize) {
                //遷移する
                if ($("#time_thre").hasClass('tab_one_active')) {
                    return;
                }
                $("#subject_thre").removeClass("tab_one_active");
                $("#time_thre").addClass("tab_one_active");
                $("#tab_twothr").addClass("disNone");
                $(".time_thre").removeClass("disNone");
                saveMap = formData;
                vm.showF20004Data();
                $("iframe").attr("src", "../common/F40003.html?id=F20003&type=2");
            } else {
                var index = layer.confirm($.msg.MSGCOMN0051, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['キャンセル', '確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                    btn2: function () {
                        //遷移する
                        if ($("#time_thre").hasClass('tab_one_active')) {
                            return;
                        }
                        $("#subject_thre").removeClass("tab_one_active");
                        $("#time_thre").addClass("tab_one_active");
                        $("#tab_twothr").addClass("disNone");
                        $(".time_thre").removeClass("disNone");
                        saveMap = formData;
                        vm.showF20004Data();
                        layer.close(index);
                        $("iframe").attr("src", "../common/F40003.html?id=F20003&type=2");
                    }
                });
            }
        },
        migrateToFirst: function () {
            if ($("#subject_thre").hasClass('tab_one_active')) {
                return;
            }
            $(".tab_subject").find("li").removeClass("liactive");
            $(".tab_subject").find("li").eq(0).addClass("liactive");
            // 2021/1/7 huangxinliang modify start
            // vm.showSubjtTimePlanInit();
            // 2021/1/7 huangxinliang modify end
            //初期の位置に戻る
            saveMap.forEach(function (value, key, map) {
                for (var i = 0; i <= value; i++) {
                    if ($("#" + key + "_" + i).css("background-color") == "rgba(0, 0, 0, 0)") {
                        $("#" + key + "_" + i).css("background", "#d1d1d1");
                        $("#" + key + "_" + i).addClass("inCheck");
                    }
                }
                for (var i = value + 1; i < 8; i++) {
                    if ($("#" + key + "_" + i).css("background", "rgb(209, 209, 209)")) {
                        $("#" + key + "_" + i).css("background", "");
                        $("#" + key + "_" + i).removeClass("inCheck");
                    }
                }
            })
            //遷移する
            $("#tab_twothr").removeClass("disNone");
            $(".time_thre").addClass("disNone");

            $("#subject_thre").addClass("tab_one_active");
            $("#time_thre").removeClass("tab_one_active");
            //初期化CSS
            var items = $(".title_tab_two").find("li");
            items.each(function (value) {
                var subjtDiv = $(this).attr("desc");
                $(this).removeClass(subjtDiv + "active");
                $(this).addClass(value == 0 ? subjtDiv + "active" : subjtDiv);
                $(this).attr("id", value == 0 ? "crrentLi" : "");
            })
            $("iframe").attr("src", "../common/F40003.html?id=F20003&type=1");

        },
        backBegin: function () {
            var li = $(".liactive");
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(0).addClass("checkCurrent");

            var index = layer.confirm($.msg.MSGCOMN0051, {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    //初期の位置に戻る
                    formData.forEach(function (value, key, map) {
                        for (var i = 0; i <= value; i++) {
                            if ($("#" + key + "_" + i).css("background-color") == "rgba(0, 0, 0, 0)") {
                                $("#" + key + "_" + i).css("background", "#d1d1d1");
                                $("#" + key + "_" + i).addClass("inCheck");
                            }
                        }
                        for (var i = value + 1; i < 8; i++) {
                            if ($("#" + key + "_" + i).css("background", "rgb(209, 209, 209)")) {
                                $("#" + key + "_" + i).css("background", "");
                                $("#" + key + "_" + i).removeClass("inCheck");
                            }
                        }
                    })

                    for (var i = 0; i < parseInt(vm.listSize); i++) {
                        var count = 0;
                        for (var j = 0; j <= 7; j++) {
                            if ($("#" + i + '_' + j)[0].style.background == '') {
                                count = j - 1;
                                break;
                            }
                        }
                        nowFormData.set(i, count);
                    }
                    var len = $(".ratio").children().length;
                    for (var i = 0; i < len; i++) {
                        $(".ratio").children().eq(i).children().eq(0).attr('src', '../img/ratio' + (i + 1) + '.png');
                    }
                    $(".ratio").children().eq(1).children().eq(0).attr('src', '../img/ratio2active.png');
                    $(".ratio").children().removeClass("active");
                    $(".ratio").children().eq(1).addClass("active");
                    layer.close(index);
                }
            });
        },
        allAddOne: function (listSize) {
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(0).addClass("checkCurrent")
            for (var i = 0; i < parseInt(listSize); i++) {
                for (var j = 0; j <= 7; j++) {
                    if ($("#" + i + "_" + j)[0].style.background == '') {
                        $("#" + i + "_" + j).css("background", "#d1d1d1");
                        $("#" + i + "_" + j).addClass("inCheck")
                        break;
                    }
                }
            }

            for (var i = 0; i < parseInt(vm.listSize); i++) {
                var count = 0;
                for (var j = 0; j <= 7; j++) {
                    if ($("#" + i + '_' + j)[0].style.background == '') {
                        count = j - 1;
                        break;
                    }
                }
                nowFormData.set(i, count);
            }
        },
        allLessOne: function (listSize) {
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(2).addClass("checkCurrent");
            for (var i = 0; i < parseInt(listSize); i++) {
                for (var j = 7; j > 0; j--) {
                    if ($("#" + i + "_" + j)[0].style.background == 'rgb(209, 209, 209)') {
                        $("#" + i + "_" + j).css("background", "");
                        $("#" + i + "_" + j).removeClass("inCheck")
                        break;
                    }
                }
            }

            for (var i = 0; i < parseInt(vm.listSize); i++) {
                var count = 0;
                for (var j = 0; j <= 7; j++) {
                    if ($("#" + i + '_' + j)[0].style.background == '') {
                        count = j - 1;
                        break;
                    }
                }
                nowFormData.set(i, count);
            }
        },
        allLessTwo: function (listSize) {
            $(".ratio").find("img").removeClass("checkCurrent");
            $(".ratio").find("img").eq(3).addClass("checkCurrent")
            vm.allLessOne(listSize);
            vm.allLessOne(listSize);
        },
        //styleの設定する
        getStyle: function (isEmpty, planRegFlg, learnLevUnds) {
            if (!isEmpty) {
                if (learnLevUnds == '4') {
                    return "background:yellow";
                } else if (learnLevUnds == '0') {
                    return "background:#009944";
                } else if (learnLevUnds == null) {
                    if (planRegFlg == '0' || planRegFlg == '2') {
                        return "background:#d1d1d1";
                    } else {
                        return "background:#009944";
                    }
                }else if(learnLevUnds=='-2'){
                    return "background:#d1d1d1";
                } else {
                    return "background:dodgerblue";
                }
            } else {
                return null;
            }
        },
        liGetStyle: function (index, codValue2) {
            var s = "";
            if (index == 0) {
                s += 'margin:0;border-color:' + codValue2 + ';background:' + codValue2 + ";";
            } else {
                if (index % 5 == 0) {
                    s += 'border-color:' + codValue2 + ";margin-left:0px;";
                } else {
                    s += 'border-color:' + codValue2 + ";";
                }
            }
            if (index > 4) {
                s += "margin-top:5px;";
            }
            return s;
        },
        //classの設定する
        getClass: function (isEmpty) {
            if (isEmpty) {
                return null;
            } else {
                return "inCheck";
            }
        },
        //登録
        submitFn: function () {

            var trs = [];
            var i = 0;
            $(".termTr").each(function () {
                var param = JSON.parse("{}");
                var jsonData = JSON.parse($(this).attr("data-json"));
                param.id = jsonData.id;
                //単元ID
                param.unitId = jsonData.unitId;
                var key = '' + jsonData.unitId + jsonData.planLearnSeasnId + jsonData.textbDefUnitId;
                var learn;
                if ($("#" + key + "  option:selected").attr("desc")==""){
                    learn = $("#" + key + "  option:selected").attr("desc");
                } else {
                    learn = JSON.parse($("#" + key + "  option:selected").attr("desc"));
                }
                //計画学習時期ID
                param.planLearnSeasnId = learn.id;
                //学習時期開始日
                param.learnSeasnStartDy = learn.learnSeasnStartDy;
                //学習時期終了日
                param.learnSeasnEndDy = learn.learnSeasnEndDy;
                //画面．選択マス数
                var n = 0;
                var arr = $(".termTr").eq(i).find("td");
                for (var j = 0; j < arr.length; j++) {
                    if (arr.eq(j).hasClass("inCheck")) {
                        n = n + 1;
                    }
                }
                param.subjtDiv = $("#crrentLi").attr("desc");
                param.dispyOrder = jsonData.dispyOrder;
                param.textbDefUnitId = jsonData.textbDefUnitId;
                param.cont = n;
                param.blockDispyNm = $("#crrentLi").attr("subjtNm") + " " + jsonData.sectnNo.trim() + "-" + jsonData.unitNo.trim() + " " + jsonData.chaptNm;
                //塾ID
                param.crmschId = vm.crmschId;
                //調整倍率
                param.adjustMnfct = $(".checkCurrent").attr("desc");
                trs.push(param);
                i = i + 1;
            });
            if (trs.length > 0){
                $.ajax({
                    url: ctxPath + '/manager/F20003/addPlanTime',
                    async: false,
                    data: JSON.stringify(trs),
                    traditional: true,
                    type: 'POST',
                    datatype: 'json',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    async: true,
                    success: function (data) {
                        if (data.code == 0) {
                            // //td位置を再設定
                            // for (var j = 0; j < parseInt(vm.listSize); j++) {
                            //     var count = 0;
                            //     for (var i = 0; i <= 7; i++) {
                            //         if ($("#" + j + '_' + i)[0].style.background == '') {
                            //             count = i - 1;
                            //             break;
                            //         }
                            //     }
                            //     formData.set(j, count);
                            // }
                            // oldSerialize = $(".mySelect").serialize();
                            var subjtDiv = $(".active").attr("desc");
                            var textbId = $(".active").attr("textbId");
                            vm.showSubjtTimePlan(subjtDiv, textbId);
                            // var index = parent.layer.alert($.format($.msg.MSGCOMN0014, "タームプラン"), {
                            //     skin: 'layui-layer-molv',
                            //     title: '確認',
                            //     closeBtn: 0,
                            //     anim: -1,
                            //     btn: ['確認'],
                            //     btn1: function () {
                            //         // location.reload();
                            //         parent.layer.close(index);
                            //     }
                            // });
                        } else {
                            showMsg(data.msg);
                        }
                    }
                });
            }else {
                var index = parent.layer.alert($.format($.msg.MSGCOMN0035, "生徒教科書"), {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    btn1: function () {
                        // location.reload();
                        parent.layer.close(index);
                    }
                });
            }
        }
    }
});

function changeColorClick(obj) {

    // TDのIDを取得する
    var id = obj.id;
    var idXy = id.split('_');
    // 行の位置
    var idx = idXy[0];
    // 列の位置
    var idy = idXy[1];

    var background = obj.style.background;
    if (background == '') {
        // 空白TDをクリックする

        // クリックところまで、グレー色を付く
        for (var i = 0; i <= parseInt(idy); i++) {
            if ($("#" + idx + '_' + i)[0].style.background == '') {
                $("#" + idx + '_' + i).css("background", "#d1d1d1");
                $("#" + idx + '_' + i).addClass("inCheck")
            }
        }
    } else {
        // 色付きTDクリックする

        // クリックところから、グレー色を消す、（青、黄除く）
        for (var i = parseInt(idy) + 1; i <= 7; i++) {
            if ($("#" + idx + '_' + i)[0].style.background == 'rgb(209, 209, 209)') {
                $("#" + idx + '_' + i).css("background", "");
                $("#" + idx + '_' + i).removeClass("inCheck")
            }
        }
    }

    for (var i = 0; i < parseInt(vm.listSize); i++) {
        var count = 0;
        for (var j = 0; j <= 7; j++) {
            if ($("#" + i + '_' + j)[0].style.background == '') {
                count = j - 1;
                break;
            }
        }
        nowFormData.set(i, count);
    }
}

var len = $(".ratio").children().length + 1
$(".ratio").children().click(function () {
    var index = $(this).index() + 1
    var indexx = $(this).index();
    if (indexx != 1) {
        for (var i = 1; i < len + 1; i++) {
            $(".ratio").children().eq(i - 1).children().eq(0).attr('src', '../img/ratio' + i + '.png')
        }
        $(".ratio").children().eq(indexx).children().eq(0).attr('src', '../img/ratio' + index + 'active.png')
        $(".ratio").children().removeClass("active")
        $(this).addClass("active")
    }
})

function autoRowSpan(tb, row, col) {
    //cellの値をバックアップ
    var lastValue = "";
    //該当cell的値
    var value = "";
    var pos = 1;
    //tb.rows.lenght総行数
    for (var i = row; i < tb.rows.length; i++) {
        //[row][col]から、データを取得する
        value = tb.rows[i].cells[col].innerText;
        //lastValueとvalueを比較する
        if (lastValue == value) {
            //該当値が同じの場合、該当cellを非表示する
            tb.rows[i].cells[col].style = "display:none";
            //rowSpanの値を＋１する
            tb.rows[i - pos].cells[col].rowSpan = tb.rows[i - pos].cells[col].rowSpan + 1;
            pos++;
        } else {
            //該当値が違いの場合、バックアップ値を更新する
            lastValue = value;

            // 該当cellを表示する
            tb.rows[i].cells[col].rowSpan = 1;
            tb.rows[i].cells[col].style = "display:";

            //pos
            pos = 1;
        }
    }
}

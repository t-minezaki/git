var params = getParam();

var vm = new Vue({
    el: "#page",
    data: {
        blockType: '',
        block: '',
        schyDiv: '',
        schy: '',
        blockTypeDivList: [],
        subjtlist: [],
        years: [],
        weeklyPlan: null,
        updDatime: '',
        thisYear:''
    },
    mounted: function () {
        var year = new Date().getFullYear();
        year = year - 30;
        for (var i = 0; i < 60; i++) {
            this.years.push(year);
            year = year + 1;
        }
        this.getInfo();
    },
    updated: function () {
        if (!params.id) {
            var thisYear = new Date().getFullYear();
            $(".selectBtn").val(thisYear);
        }
        $('#demo_select').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            formatResult: function (dateTime, event) {
                scrollSizeCommon("", $('#demo_select'))
                var hours = dateTime.toString().trim();
                var text = $("#time").html();
                text = text.replace(/\d{1,2}時間/, hours + "時間");
                $("#time").html(text);
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
                event.find('.dwwol').text('時間');
            }
        });
        $('#demo_select1').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#demo_select1'))
                var min = dateTime.toString().trim() % 60;
                var text = $("#time").html();
                text = text.replace(/\d{1,2}分/, min + "分");
                $("#time").html(text);
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
                event.find('.dwwol').text('分');
            },
            onValueTap: function (event, inst) {
                vm.showTime();
            }
        });
        $('#dom_sub').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            theme: 'mobiscroll',
            delay: 1,
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
            },
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#dom_sub'))
            },
            onValueTap: function (event, inst) {
                vm.showSubjt();
            }
        });
        $("#subjt").text($("#dom_sub").find("option:selected").text().trim());

        $("#dom_sub").change(function () {
            vm.schyDiv = $("#dom_sub").val().trim();
            vm.schy = $("#dom_sub").find("option:selected").text().trim();
            $("#subjt").text($("#dom_sub").find("option:selected").text().trim());
        });
    },
    methods: {
        //初期表示
        getInfo: function () {
            $.ajax({
                url: ctxPath + "/student/F11001/init",
                type: 'GET',
                data: {
                    weeklyPlanId: params.id ? params.id : -1
                },
                dataType: 'json',
                success: function (data) {
                    vm.blockTypeDivList = data.blockTypeDiv;
                    for (var i = 0; i < vm.blockTypeDivList.length; i++) {
                        vm.blockTypeDivList[i].blockTypeDiv = vm.blockTypeDivList[i].blockTypeDiv + ',' + vm.blockTypeDivList[i].id
                    }
                    vm.subjtlist = data.subjtDiv;
                    vm.subjtlist.unshift({subjtDiv: '', subjtNm: '選択する'});
                    if (data.weeklyPlan) {
                        vm.weeklyPlan = data.weeklyPlan;
                        for (var i = 0; i < data.blockTypeDiv.length; i++) {
                            if (data.blockTypeDiv[i].blockTypeDiv.split(',')[0] === vm.weeklyPlan.blockTypeDiv){
                                vm.blockType = data.blockTypeDiv[i].blockTypeDiv;
                                $("#init").text(data.blockTypeDiv[i].blockDispyNm);
                                break;
                            }
                        }
                        vm.schyDiv = data.weeklyPlan.subjtDiv;
                    }else {
                        vm.weeklyPlan = null;
                    }
                    if (data.updDatime){
                        vm.updDatime = data.updDatime;
                    }
                    vm.nowTime();
                    vm.timeSelect();
                    if (vm.weeklyPlan && vm.weeklyPlan.blockDispyNm.indexOf(' ') > 0) {
                        if (vm.weeklyPlan.blockDispyNm.indexOf(' ') > 0){
                            $('.memo').val(vm.weeklyPlan.blockDispyNm.substring(vm.weeklyPlan.blockDispyNm.indexOf(' ') + 1));
                        }
                    }
                }
            })
        },
        showSubjt: function () {
            $(".check_sub").toggleClass("disNone");
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        showCategory: function () {
            $(".check_category").toggleClass("disNone");
            if (!$(".check_sub").hasClass("disNone")) {
                $(".check_sub").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        showTime: function () {
            $(".check_time").toggleClass("disNone");
            if (!$(".check_sub").hasClass("disNone")) {
                $(".check_sub").addClass("disNone")
            }
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        starTime: function () {
            $(".check_starTime").toggleClass("disNone");
            if (!$(".check_sub").hasClass("disNone")) {
                $(".check_sub").addClass("disNone")
            }
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        show: function (value) {
            if (this.weeklyPlan){
                if (this.weeklyPlan.subjtDiv === value){
                    return true;
                }else {
                    return false;
                }
            }else {
                if (value === '') {
                    return true;
                } else {
                    return false
                }
            }
        },
        nowTime: function () {
            var date;
            if (params.id && this.weeklyPlan.planLearnStartTime) {
                date = new Date(this.weeklyPlan.planLearnStartTime.replace(/-/g, '/'));
                this.weeklyPlan.planLearnStartTime = null;
            } else {
                if ($(".selectBtn").val() == null || $(".selectBtn").val() == new Date().getFullYear().toString()) {
                    date = new Date();
                } else {
                    date = new Date($(".selectBtn").val());
                }
            }
            a = date.Format("MM" + "月" + "dd" + "日" + " " + "HH:mm");
            $("#starTime").html(a);
            createSelect(date);
            build();
        },
        timeSelect: function () {
            var time = "";
            var min = "";
            for (var i = 0; i < 60; i += 15) {
                var m = i % 60;
                min += "<option value='" + i + "'>" + m + "</option>";
            }
            for (var i = 0; i < 12; i++) {
                time += "<option value='" + i + "'>" + i + "</option>";
            }
            $('#demo_select').html(time);
            $('#demo_select1').html(min);
            $('#demo_select').find("option").each(function () {
                if (vm.weeklyPlan && $(this).val() === Math.floor(vm.weeklyPlan.stuPlanLearnTm/60) + '') {
                    $(this).attr("selected", true);
                }
            });
            $('#demo_select1').find("option").each(function () {
                if (vm.weeklyPlan){
                    if (vm.weeklyPlan.stuPlanLearnTm%60 + '' === $(this).val()){
                        $(this).attr("selected", true);
                    }
                }else {
                    if ($(this).val() == 30) {
                        $(this).attr("selected", true);
                    }
                }
            });
            $("#time").html("0時間30分");
        },
        update: function () {
            saveOrUpdate();
        },
        del: function () {
            //確認ダイアログをポップアップ表示する
            var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
                title: '確認',
                closeBtn: 0,
                shadeClose: false,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    $.ajax({
                        url: ctxPath + "/student/F11001/delete",
                        type: 'POST',
                        data: {
                            weeklyPlanId: params.id
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                            } else {
                                //登録完了確認メッセージを表示する。
                                // var index = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                //         layer.close(index);
                                        window.location.href = './F11008.html';
                                //     }
                                // });
                            }
                        }
                    });
                }
            });
        },
        chooseCategory: function (element, blockTypeDiv, blockDispyNm) {
            vm.blockType = blockTypeDiv;
            vm.block = blockDispyNm;
            $("#init").text(blockDispyNm);
            $(".check_category").addClass("disNone");
        },
        isEditMode: function () {
            return params.id ? true : false;
        },
        toF11004: function () {
            return params.id ? '' : 'window.location.href=\'F11004.html\'';
        }
    }
});

function createSelect(date) {
    var time = "";
    var hours = "";
    var min = "";
    //月日
    for (var i = 1; i < 13; i++) {
        var days = mGetDate(date.getFullYear(), i);
        var _year = date.getFullYear();
        for (var j = 1; j <= days; j++) {
            var month = i;
            var day = j;
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            var weekday = getDay(new Date(_year, (month - 1), day));
            var selected = '';
            var text = month + "月" + day + "日" + " " + weekday;
            if (date.getFullYear().toString() === new Date().getFullYear().toString() && new Date().getMonth() === (i - 1) && new Date().getDate() === j){
                text = "今日";
            }
            if (date.getMonth() === (i - 1) && date.getDate() === j){
                selected = 'selected';
            }
            time += "<option value='" + i + "/" + j + "' " + selected + ">" + text + "</option>";
        }
    }
    $('#dom_starTime').html("");
    $('#dom_starTime').html(time);
    //時
    for (var i = 0; i < 24; i++) {
        if (i < 10) {
            i = "0" + i;
        }
        if (date.getHours() == i) {
            hours += "<option value='" + i + " ' selected='true'>" + i + "</option>";
        } else {
            hours += "<option value='" + i + " '>" + i + "</option>";
        }
    }
    //分
    for (var i = 0; i < 60; i++) {
        if (i < 10) {
            i = "0" + i;
        }
        if (date.getMinutes() == i) {
            min += "<option value='" + i + " ' selected='true'>" + i + "</option>";
        } else {
            min += "<option value='" + i + " '>" + i + "</option>";
        }
    }
    $("#dom_starTime2").html(min);
    $("#dom_starTime1").html(hours);
}

function build() {
    $('#dom_starTime').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event,"");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime'));
            var date = dateTime.toString().split('/');
            var month = date[0].trim();
            var day = date[1].trim();
            var text = $("#starTime").html();
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            text = text.replace(/\d{1,2}月\d{1,2}日/, month + '月' + day + '日');
            $("#starTime").html(text);
        },
    });
    $('#dom_starTime1').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event,"");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime1'));
            var month = dateTime.toString().trim();
            var text = $("#starTime").html();
            text = text.replace(/\d{1,2}:/, month + ':');
            $("#starTime").html(text);
        },
    });
    $('#dom_starTime2').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event,"");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime2'));
            var day = dateTime.toString().trim();
            var text = $("#starTime").html();
            text = text.replace(/:\d{1,2}/, ':' + day);
            $("#starTime").html(text);
        },
        onValueTap: function (event, inst) {
            vm.starTime();
        }
    });
}

//登録を押下して、画面入力した項目より、DBに反映する。
$(".submit").click(function () {
    if (params.id) {
        window.location.href = ctxPath + '/student/F11008.html';
        return;
    }
    saveOrUpdate();
});

function saveOrUpdate() {
    var studyTime = $("#time").html().split("時間");
    var hours = studyTime[0];
    var min = studyTime[1].substring(0, studyTime[1].length - 1);
    if (vm.blockType === "") {
        var index = layer.confirm($.format($.msg.MSGCOMD0001, 'カテゴり'), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return ;
    }
    if (vm.schyDiv === ''){
        var index = layer.confirm($.format($.msg.MSGCOMD0001, '教科/科目'), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return ;
    }
    var memo = $(".memo").val().trim();
    var startTime = new Date($(".selectBtn").val() + "/" + $("#starTime").html().replace(/月/g, "/").replace(/日/g, ""));
    var startYmd = startTime.Format("yyyy-MM-dd");
    var nowTime = new Date();
    //画面選択した「開始時間」項目のチェック処理を行う
    // if (startTime < nowTime) {
    //     var index = layer.confirm($.format($.msg.MSGCOMN0048, '開始時間', 'システム時間'), {
    //         skin: 'layui-layer-molv',
    //         title: '確認',
    //         closeBtn: 0,
    //         anim: -1,
    //         btn: ['確認'],
    //         btn1: function () {
    //             layer.close(index);
    //         }
    //     });
    //     return false;
    // }
    //画面入力した「メモ」項目のチェックを行う
    if (memo.length > 50) {
        var index = layer.confirm($.format($.msg.MSGCOMD0017, 'メモ', '50'), {
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return false;

    }
    var text = '登録';
    if (params.id){
        text = '更新';
    }
    //確認ダイアログをポップアップ表示する
    var index = layer.confirm($.format($.msg.MSGCOMN0021, text), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + "/student/F11001/submit",
                type: 'POST',
                data: {
                    weeklyPlanId: params.id ? params.id : -1,
                    blockCd: vm.blockType,
                    subjCd: vm.schyDiv,
                    starTime: startTime,
                    startYmd: startYmd,
                    hours: hours,
                    min: min,
                    memo: memo,
                    schy: $("#subjt").html(),
                    block: $("#init").html(),
                    updateStr: vm.updDatime
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        //登録完了確認メッセージを表示する。
                        // var index = layer.confirm($.format($.msg.MSGCOMN0022, text), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     shadeClose: false,
                        //     btn: ['確認'],
                        //     btn1: function () {
                        //         layer.close(index);
                                window.location.reload();
                        //     }
                        // });
                    }
                }
            });
        },
    });
}

function mGetDate(year, month) {
    var d = new Date(year, month, 0);
    return d.getDate();
}

function  hidden() {
    if (!$(".check_starTime").hasClass("disNone")) {
        $(".check_starTime").addClass("disNone")
    }
    if (!$(".check_sub").hasClass("disNone")) {
        $(".check_sub").addClass("disNone")
    }
    if (!$(".check_category").hasClass("disNone")) {
        $(".check_category").addClass("disNone")
    }
    if (!$(".check_time").hasClass("disNone")) {
        $(".check_time").addClass("disNone")
    }
}

var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
    var height = $('.memo').offset().top - ($(window).height() - ($('.memo').outerHeight() * 2));
    $(window).scrollTop(height);
});


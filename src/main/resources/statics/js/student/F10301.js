// インポート時間
var eventDuration = 30;
// 一時保存インポートリスト
var chkEvents = [];
// 一時保存インポートリスト 保存フラグ
var chkEventsSaveFlg = true;
// 一時保存インポート
var tmpEvent = null;
// swiper 
var learnSeasn = null;
// popindex
var msgIndex = null;
var studyBlockList = ['S1', 'W1', 'R1', 'V1', 'P1'];
// 画面初期化
var vm = new Vue({
    el: '#pageF10301',
    data: {
        tgtYmd: "",
        dateTitle: "",
        remainBlock: [],
        learnBlock: [],
        reviewBlock: [],
        otherBlock: [],
        seasnList: [],
        //blockInfo:{},
        fristDay: "",
        lastDay: "",
        oldDay: "",
        weekStartDay: "",
        selectBlock: {},
        termPlan: {},
        planPerf: {},
        unPlanCnt: "",
        stuNm: '',
        stuTextbChocEntityListLength: ''
    },
    mounted: function () {
        // 画面表示データを取得する
        this.getInitData();
    },
    updated: function () {
        vm.$nextTick(function () {
            blockBind();
        });
    },
    methods: {
        // 画面を初期化
        getInitData: function () {
            // localstorage存在するの場合、
            if (storageTest(window.localStorage)) {

            } else {
                $.getJSON(ctxPath + "/student/F10301/blockInfo", {}, function (data) {
                    if (data.code != "0" && !data.blockInfo) {
                        layer.alert($.msg.MSGCOMN0020);
                        return;
                    }

                    // 学習週リスト
                    vm.seasnList = data.blockInfo.seasnList;
                    // 生徒タームプラン設定
                    if (data.blockInfo.termPlan != undefined) {
                        vm.termPlan = data.blockInfo.termPlan;
                    }
                    // 生徒ウィークリー計画実績設定
                    if (data.blockInfo.planPerf != undefined) {
                        vm.planPerf = data.blockInfo.planPerf;
                    }
                    // 今週の開始日
                    vm.weekStartDay = data.blockInfo.weekStartDay;
                    // 遅れている単元
                    vm.fristDay = data.blockInfo.fristDay;
                    // 進んでいる単元
                    vm.lastDay = data.blockInfo.lastDay;
                    // 対象日
                    vm.tgtYmd = data.blockInfo.weekStartDay;
                    // 復習ブロック
                    vm.reviewBlock = data.blockInfo.reviewBlock;
                    // その他ブロック
                    vm.otherBlock = data.blockInfo.otherBlock;
                    //生徒姓名
                    vm.stuNm = data.stuNm;
                    //生徒教科書データ有無の取得
                    vm.stuTextbChocEntityListLength = data.stuTextbChocEntityList.length;
                    if (vm.stuTextbChocEntityListLength <= 0){
                        document.getElementById("learn_events").setAttribute("style","margin-top:16.2%");
                        // 2020/12/7 huangxinliang modify start
                        $(".block-div").css("height", $(window).height() - $(window).width() * 0.12 - 34);
                        // 2020/12/7 huangxinliang modify end
                    }

                    $.each(data.blockInfo.planPerf, function (key, item) {
                        vm.selectBlock[item.stuTermPlanId] = key;
                    });

//                    if (data.blockInfo.unPlanCnt > 0) {
//                        vm.oldDay = data.blockInfo.oldDay;
//                        vm.unPlanCnt = data.blockInfo.unPlanCnt;
//                        var popHg = $(window).width() * 0.14 + 'px';
//                        var popWd = $(window).width() * 0.8;
//                        if(popWd > 320){
//                            popWd = '320px'
//                        }else{
//                            popWd = popWd+'px'
//                        }
//
//                        msgIndex = layer.open({
//                            type: 1,
//                            title: false,
//                            shade: 0.1,
//                            closeBtn: 0,
//                            shadeClose: false,
//                            area: [popWd, popHg],
//                            fixed: true,
//                            resize: false,
//                            content: $('.popNew')
//                        });
//                    }

                    vm.$nextTick(function () {
                        slideBind();
                    });
                });
            }
        },
        isRemain: function (item) {
            if (vm.weekStartDay == vm.tgtYmd) {
                // 生徒ウィークリー計画実績設定．積み残し対象フラグ　＝ 「null」「1：対象」  and 生徒ウィークリー計画実績設定．計画年月日 ＜ 画面．学習ブロックエリア．学習時期開始日
                if (item.planYmd < vm.weekStartDay && (item.remainDispFlg == "" || item.remainDispFlg == null || item.remainDispFlg == "1")) {
                    return true;
                }
                // 生徒ウィークリー計画実績設定．積み残し対象フラグ　= 「3：強制対象」
                if (item.remainDispFlg == "3") {
                    return true;
                }
            }
            return false;
        },
        getClass: function (item, remainFlg) {
            var strClass = "";
            var id = vm.selectBlock[item.stuTermPlanId];
            if (remainFlg == "0") {
                if (id != null) {
                    strClass += " hide";
                    strClass += " planned";
                } else {
                    strClass += " unplan";
                }
            } else {
                strClass += " unplan";
            }
            var perfInfo = vm.planPerf[id];
            if (perfInfo) {
                if (perfInfo.learnLevUnds == "1" || perfInfo.learnLevUnds == "2" || perfInfo.learnLevUnds == "3") {
                    strClass += " passEvent";
                } else if (perfInfo.learnLevUnds == "4") {
                    strClass += " failEvent";
                } else if (perfInfo.learnLevUnds == "0") {
                    strClass += " errEvent";
                }
            }
            return strClass;
        }
    }
});

$(function () {
    // 2020/12/7 huangxinliang modify start
    $(".block-div").css("height", $(window).height() - $(window).width() * 0.19 - 34);
    // 2020/12/7 huangxinliang modify end

//    // tab
//    $(".calendar").tabs();

    // 初期化カレンダー
    $("#calendar").fullCalendar({
        locale: "ja",
        now: new Date(),
        firstDay: 1,
        defaultView: "agendaWeek",
        editable: true,
        droppable: true,
        height: $(window).height() - $(window).height() * 0.16,
        header: {left: '', center: '', right: ''},
        columnHeaderHtml: function (mon) {
            var temp = '', tempDate = new Date();
            switch (mon.weekday()) {
                case 0:
                    temp += ('<p class="custom_col_header_date">' + mon.date() + '</p><p class="custom_col_header_day">月</p>');
                    break;
                case 1:
                    temp += ('<p class="custom_col_header_date">' + mon.date() + '</p><p class="custom_col_header_day">火</p>');
                    break;
                case 2:
                    temp += ('<p class="custom_col_header_date">' + mon.date() + '</p><p class="custom_col_header_day">水</p>');
                    break;
                case 3:
                    temp += ('<p class="custom_col_header_date">' + mon.date() + '</p><p class="custom_col_header_day">木</p>');
                    break;
                case 4:
                    temp += ('<p class="custom_col_header_date">' + mon.date() + '</p><p class="custom_col_header_day">金</p>');
                    break;
                case 5:
                    temp += ('<p class="custom_col_header_date satday">' + mon.date() + '</p><p class="custom_col_header_day satday">土</p>');
                    break;
                case 6:
                    temp += ('<p class="custom_col_header_date restday">' + mon.date() + '</p><span class="custom_col_header_day restday">日</span>');
                    break;
                default:
                    break;
            }
            return temp;
        },
        views: {
            agendaWeek: {
                titleFormat: "YYYY年MM月",
                slotDuration: '00:15:00',
                slotLabelInterval: {hours: 1},
                slotLabelFormat: 'H',
                allDaySlot: false,
                slotEventOverlap: false,
                minTime: "05:00:00",
                maxTime: "25:00:00"
            }
        },
        scrollTime: '17:00:00',
        // event setting
        dropAccept: '.fc-event',
        eventDurationEditable: false,
        defaultTimedEventDuration: "00:30:00",
        events: function (start, end, timezone, callback) {
            // VM
            vm.dateTitle = this.getDate().format('YYYY年MM月');
            // AJAX
            $.ajax({
                url: ctxPath + "/student/F10301/schdlList",
                dataType: 'json',
                data: {tgtYmd: this.getDate().format('YYYYMMDD')},
                success: function (data) {
                    if (!data.schdlList) {
                        return;
                    }
                    var events = [];
                    $(data.schdlList).each(function (i, schdl) {
                        var classNames = "";
                        if (schdl.id != null) {
                            if (schdl.learnLevUnds == "1" || schdl.learnLevUnds == "2" || schdl.learnLevUnds == "3") {
                                classNames = "passEvent customEvent";
                            } else if (schdl.learnLevUnds == "4") {
                                classNames = "failEvent customEvent";
                            } else if (schdl.learnLevUnds == "0") {
                                classNames = "errEvent customEvent";
                            } else {
                                classNames = "zeroEvent customEvent";
                            }
                        } else {
                            classNames = "schoolEvent loopEvent";
                        }
//                        var eventTitle = "";
                        // ブロック種類区分
//                        if (schdl.blockTypeDiv == "S1") {
//                            eventTitle = schdl.subjtNm;
//                        } else {
//                            eventTitle = schdl.blockDispyNm;
//                        }
                        // 生徒ウィークリー計画実績設定ID
                        schdl.planPerfId = schdl.id;
                        //その他の場合 ブロック表示名より、半角スペースを区切りで、前部内容（その他小分類名）を表示する。
                        if(schdl.blockTypeDiv.substring(0, 1) == 'O'){
                            var arr = schdl.blockDispyNm.split(' ');
                            schdl.blockDispyNm = arr[0];
                        }
                        var bgColor;
                        var foreColor;
                        if (schdl.colorId == null){
                            bgColor = "white";
                            foreColor = "black";
                        }
                        if (schdl.colorId) {
                            var rgbColor = colorRgb(schdl.colorId.toLowerCase()).split(",");
                            bgColor = schdl.colorId;
                            if (parseInt(rgbColor[0]) + parseInt(rgbColor[1]) + parseInt(rgbColor[2]) > 650){
                                foreColor = "black";
                            } else {
                                foreColor = "white";
                            }

                        }
                        // var color = data.map[schdl.blockId] == 'white'?'black':'white';
                        /****2020-11-11 cuikailin V9.0 modify start******/
                        var title = null;
                        if(schdl.blockTypeDiv == 'S1'){
                            title = '学－'+schdl.subjtNm;
                        }else if(schdl.blockTypeDiv == 'W1'){
                            title = '学宿－'+schdl.subjtNm;
                        }else if(schdl.blockTypeDiv == 'R1'){
                            title = '復－'+schdl.subjtNm;
                        }else if(schdl.blockTypeDiv == 'V1'){
                            title = '塾宿－'+schdl.subjtNm;
                        }else if(schdl.blockTypeDiv == 'P1'){
                            title = '予－'+schdl.subjtNm;
                        }else{
                            title = schdl.blockDispyNm;
                        }
                        // 2021/1/26 huangxinliang modify start
                        var memo = undefined;
                        if (schdl.memo){
                            if (studyBlockList.indexOf(schdl.blockTypeDiv) >= 0){
                                memo = schdl.memo;
                            }else {
                                const blockName = schdl.memo.split(' ')[0];
                                if (schdl.memo.length > blockName.length){
                                    memo = schdl.memo.substring(blockName.length + 1);
                                }
                            }
                        }
                        // 2021/1/26 huangxinliang modify end
                        events.push({
                            title: title,
                            /****2020-11-11 cuikailin V9.0 modify end******/
                            start: schdl.startTime,
                            end: schdl.endTime,
                            allDay: false,
                            editable: schdl.editable, // 編集可否
                            overlap: true, // 複数不可
                            planPrefId: schdl.id,
                            userParam: JSON.stringify(schdl),
                            backgroundColor:bgColor,
                            // borderColor:foreColor,
                            textColor:foreColor,
                            className: classNames,
                            memo: memo
                        });
                    });
                    callback(events);
                }
            });
        },
        eventReceive: function (event) { // called when a proper external event is dropped
            var param = JSON.parse(event.userParam);
            //塾の宿題  add
            /* 2020-11-24 cuikailin V9.0 modify start */
            if (param.blockTypeDiv == "R1" || param.blockTypeDiv == "P1" || param.blockTypeDiv == "W1" || param.blockTypeDiv == "V1" || (param.blockTypeDiv == "S1" && param.stuTermPlanId == null && !param.stuId)) {
                /* 2020-11-24 cuikailin V9.0 modify end */
                // イベント削除
                $("#calendar").fullCalendar("removeEvents", event._id);
                tmpEvent = event;
                // 復習教科選択画面
                openF10303(param.blockTypeDiv, param.blockDispyNm, param.subjtDiv, param.subjtNm);
            } else if (param.blockTypeDiv != null && param.blockTypeDiv.substring(0, 1) == 'O') {//ブロック種類区分 like 'O'%  0625
                // イベント削除
                $("#calendar").fullCalendar("removeEvents", event._id);
                tmpEvent = event;
                // F10305_計画時間調整画面
                openF10305(event, true);
            } else {
                // ブロック種類区分 =  学習
                if (param.blockTypeDiv == "S1") {
                    // イベントタイトル
                    event.title = param.subjtNm;
                }
                // 障害54
                if (param.planLearnTm != null && param.planLearnTm > 0) {
                    event.end = event.start.clone().add(param.planLearnTm * 60 * 1000);
                }
                // 更新処理
                updateEvent(event);
            }
        },
        eventClick: function (_event, _jsEvent, _view) {
            tmpEvent = _event;
            // 編集可能の判定
            if (!_event.editable) {
                return false;
            }
            // POPF10305
            openF10305(_event, false);
            return false;
        },
        eventDrop: function (event, dayDelta, minuteDelta, allDay, revertFunc) {
            // 更新処理
            updateEvent(event);
        }
    });

    // prevボタン
    $(".fc-prev-button").click(function () {
        $('#calendar').fullCalendar("removeEvents");
        $('#calendar').fullCalendar("prev");
    });

    // nextボタン
    $(".fc-next-button").click(function () {
        $('#calendar').fullCalendar("removeEvents");
        $('#calendar').fullCalendar("next");
    });

    // 学習のブロックタブ　と　その他のブロッククタブをクリックする
    $("#main_event_menu").on("click", "li", function () {
        if (!$(this).hasClass("active")) {
            $("#main_event_menu li").removeClass("active");
            $(this).addClass("active");
            if ($(this).hasClass("learn_events")) {
                $("#learn_events").show();
                $("#other_events").hide();
            } else {
                $("#learn_events").hide();
                $("#other_events").show();
            }
        }
    });

    // 当日～
    $("#todayPrintBtn").click(function () {
        $.post(ctxPath + "/student/F10301/todayPrint", {"url": window.location.href}, function (data) {
            if (data.code == '0') {
                $("#downloadPdf #fileName").val(data.fileName);
                $("#downloadPdf").submit();
            } else {
                layer.alert(data.msg);
            }
        }, "json");
        return false;
    });
    // 月曜～
    $("#printBtn").click(function () {
        $.post(ctxPath + "/student/F10301/print", {
            "tgtYmd": $("#calendar").fullCalendar("getDate").format("YYYYMMDD"),
            "url": window.location.href
        }, function (data) {
            if (data.code == '0') {
                $("#downloadPdf #fileName").val(data.fileName);
                $("#downloadPdf").submit();
            } else {
                layer.alert(data.msg);
            }
        }, "json");
        return false;
    });

    // 遅れている単元
    $("#gotoFrist").click(function () {
        var gotoIndex = -1;
        var fristDay = getFristDay();
        $(".learn_seasn .swiper-slide").each(function (i, item) {
            if ($(item).data("startdy") == fristDay) {
                gotoIndex = i;
                return;
            }
        });
        if (gotoIndex != -1) {
            setCurrentSlide($(".learn_seasn .swiper-slide").eq(gotoIndex), gotoIndex);
            learnSeasn.slideTo(gotoIndex, 0, false);
        }
    });

    // 進んでいる単元
    $("#gotoLast").click(function () {
        var gotoIndex = -1;
        var lastDay = getLastDay();
        $(".learn_seasn .swiper-slide").each(function (i, item) {
            if ($(item).data("startdy") == lastDay) {
                gotoIndex = i;
                return;
            }
        });
        if (gotoIndex != -1) {
            setCurrentSlide($(".learn_seasn .swiper-slide").eq(gotoIndex), gotoIndex);
            learnSeasn.slideTo(gotoIndex, 0, false);
        }
    });

    // pre Tab クリック
    $("#fixedAreaBtn").click(function () {
        $("#fixedAreaBtn").addClass("active");
        $("#planAreaBtn").removeClass("active");
        $("#sub_menu").addClass("disabled");
        window.location.href = "F10101.html";
    });

    $("#popBtnClose").click(function () {
        layer.close(msgIndex);
    });

    $("#popBtnGo").click(function () {
        var gotoIndex = -1;
        $(".learn_seasn .swiper-slide").each(function (i, item) {
            if ($(item).data("startdy") == vm.oldDay) {
                gotoIndex = i;
                return;
            }
        });
        if (gotoIndex != -1) {
            setCurrentSlide($(".learn_seasn .swiper-slide").eq(gotoIndex), gotoIndex);
            learnSeasn.slideTo(gotoIndex, 0, false);
        }
        layer.close(msgIndex);
    });
});

function slideBind() {
    // 学習週選択エリア
    learnSeasn = new Swiper('.learn_seasn', {
        slidesPerView: 3,
        centeredSlides: true,
        mousewheel: true,
        on: {
            tap: function (swiper) {
                var clickIndex = learnSeasn.clickedIndex;
                learnSeasn.slideTo(clickIndex);
                setCurrentSlide($(".learn_seasn .swiper-slide").eq(clickIndex), clickIndex);
            }
        }
    });

    learnSeasn.slideTo($(".learn_seasn .selected").index(), 0, false);
}

/**
 * bind events
 */
function blockBind() {
    var reviewBlock = new Swiper('.review_block', {
        slidesPerView: 3,
        spaceBetween: 10,
        slidesPerGroup: 3,
        touchMoveStopPropagation: false,
        pagination: {
            el: '.swiper-pagination',
            clickable: false
        }
    });
    $();
    // block event add
    $('#learn_events .fc-event, #other_events .userBlock').each(function (index, item) {
        // fullcalendarのイベントを設定する
        $(this).data('event', {
            title: $.trim($(this).text()),
            stick: true,
            allDay: false,
            editable: true,
            overlap: true,
            className: "zeroEvent customEvent",
            userParam: $(this).attr("data-json"),
            remainDispFlg: $(this).data("remain_flg"),
            planPrefId: $(this).data("plan_pref_id")
        });

        // fullcalendarのドラグを設定する
        $(this).draggable({
            start: function (_source, _helper) {
                // イベントドロップ時、mouse位置の設定
                // 2021/1/26 huangxinliang modify start
                _helper.helper.css("width", $(this).width()).css("height", $(this).height()).css("marginLeft", 0).css("marginRight", 0);
                var dataStr = _helper.helper.attr('data-json');
                var data = JSON.parse(dataStr);
                if(studyBlockList.indexOf(data.blockTypeDiv) >= 0){
                    _helper.helper.css("background","red");
                }else {
                    _helper.helper.css("border", "2px solid red");
                }
                // 2021/1/26 huangxinliang modify end
            },
            // stop: function(event, ui){
            //     window.location.reload()
            // },
            helper: "clone",
            zIndex: 999,
            revertDuration: 0,
            disabled: false,
            appendTo: "body",
            /* 2020/12/15 V9.0 cuikailin add start */
            scroll: false
            /* 2020/12/15 V9.0 cuikailin add end */
        });

        $(this).touchDraggable();
    });

    $("#learn_events .planned").draggable("option", "disabled", true);

    //F10304 POP
    $("#other_events .otherBlock").click(function () {
        var jsonData = JSON.parse($(this).attr("data-json"));
        var index = layer.open({
            id: 'f10304',
            type: 2,
            title: false,
            shade: 0.1,
            closeBtn: 0,
            shadeClose: false,
            area: ['240px', '430px'],
            fixed: true,
            resize: false,
            content: ["../pop/F10304.html?blockId=" + jsonData.blockId, 'no'],
            success: function (layero, index) {
            }
        });
    });

    // F10306 POP
    $(".learnBlock, .remainBlock").click(function () {
        var jsonData = JSON.parse($(this).attr("data-json"));

        var index = layer.open({
            id: 'f10306',
            type: 2,
            title: false,
            shade: 0.1,
            closeBtn: 0,
            shadeClose: false,
            area: ['240px', '240px'],
            fixed: true,
            resize: false,
            content: ["../pop/F10306.html?" + $.param({stuTermPlanId: jsonData.stuTermPlanId, frame: 'f10301'}), 'no'],
            success: function (layero, index) {
            }
        });
    });

    // careful POP
//         $(".header_logo").click(function () {
//             var index = layer.open({
//                 id:'careful',
//                 type: 2,
//                 title: false,
//                 shade: 0.1,
//                 closeBtn: 0,
//                 shadeClose: false,
//                 area: ['300px', '240px'],
//                 fixed: true,
//                 resize: false,
//                 content: ["../pop/careful.html?", 'no'],
//                 success: function (layero, index) {
//                 },
//             })
//         });
    //F10308 POP
    $("#other_events .userBlock").click(function () {
        var jsonData = JSON.parse($(this).attr("data-json"));
        var index = layer.open({
            id: 'f10308',
            type: 2,
            title: false,
            shade: 0.1,
            closeBtn: 0,
            shadeClose: false,
            area: ['240px', '370px'],
            fixed: true,
            resize: false,
            content: ["../pop/F10308.html?smallBlockId=" + jsonData.blockId + "&frame=f10301", 'no'],
            success: function (layero, index) {
            }
        });
    });
    return false;
}

// 学習週の選択状態を設定する
function setCurrentSlide(ele, index) {
    $(".learn_seasn .swiper-slide").removeClass("selected");
    ele.addClass("selected");

    // 今週の場合
    if ($(ele).data("thisweek") == "1") {
        $(".review_block").show();
        // 2020/12/7 huangxinliang modify start
        $(".block-div").css("height", $(window).height() - $(window).width() * 0.19 - 34);
        // 2020/12/7 huangxinliang modify end
    } else {
        $(".review_block").hide();
        $(".block-div").css("height", $(window).height() - $(window).width() * 0.12 - 34);

    }
    // ブロックを取得する
    vm.tgtYmd = $(ele).data("startdy");

    return false;
}

// POP F10305
function openF10305(_event, addFlg) {
    var jsonData = JSON.parse(_event.userParam);
    var param = {};
    param.eventid = _event._id;
    param.addFlg = addFlg;
    param.blockTypeDiv = jsonData.blockTypeDiv;
    param.blockId = jsonData.blockId;
    param.wppId = jsonData.planPerfId;
    param.startTime = _event.start.format('YYYYMMDDHHmmss');
    param.subjtNm = jsonData.subjtNm;
    if (_event.end != null) {
        param.endTime = _event.end.format('YYYYMMDDHHmmss');
    } else {
        param.endTime = _event.start.clone().add(eventDuration, "Minutes").format('YYYYMMDDHHmmss');
    }
    if (addFlg == true) {
        param.delBtnFlg = false;
    } else {
        // 実績学習時間（分）　IS NULL 又は、 実績学習時間（分） = 0
        if (jsonData.perfLearnTm > 0) {
            param.delBtnFlg = false;
        } else {
            param.delBtnFlg = true;
        }
    }
    /* 2020-11-11 cuikailin V9.0 add start */
    // for (var i = 0; i <vm.reviewBlock.length ; i++) {
    //     if (vm.reviewBlock[i]["blockTypeDiv"] ==jsonData.blockTypeDiv ) {
    param.blockDispyNm = _event.title;
    //     }
    // }
    /* 2020-11-11 cuikailin V9.0 add end */
    var index = layer.open({
        id: 'f10305',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['320px', '420px'],
        fixed: true,
        resize: false,
        content: ["../pop/F10305.html?" + $.param(param), 'no'],
        success: function (layero, index) {
        }
    });
    return false;
}

// openF10303
function openF10303(_blockTypeDiv, _blockTypeNm, _subjtDiv, _subjtNm) {
    //F10303POP 復習ブロックの場合、
    var index = layer.open({
        id: 'f10303',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['340px', '200px'],
        fixed: true,
        resize: false,
        content: ["../pop/F10303.html?" + $.param({blockTypeDiv: _blockTypeDiv, blockTypeNm: _blockTypeNm, subjtDiv: _subjtDiv, subjtNm: _subjtNm}), 'no'],
        success: function (layero, index) {
        }
    });
}

function updateEvent(event, popIndex) {

    var param = JSON.parse(event.userParam);

    //メモ F10305 改善要件管理no14
    if (event.memo != undefined) {
        //その他の場合
        if (param.blockTypeDiv == 'O1' || param.blockTypeDiv == 'O2' || param.blockTypeDiv == 'O3') {
            var arr = param.blockDispyNm.split(' ');
            //ブロック小分類名+半角スペース＋画面．メモ
            param.blockDispyNm = arr[0] + " " + event.memo;
            //ブロック表示名より、半角スペースを区切りで、前部内容（その他小分類名）を表示する。
            event.title = arr[0];
        }else{
            //「復習」「予習」「学校の宿題」「塾の宿題」の場合
            param.blockDispyNm =  event.memo;
        }
    }
    param.startTime = event.start.format('YYYY/MM/DD HH:mm');
    if (event.end != null) {
        param.endTime = event.end.format('YYYY/MM/DD HH:mm');
    } else {
        param.endTime = event.start.clone().add(eventDuration * 60000).format('YYYY/MM/DD HH:mm');
    }
    param.remainDispFlg = event.remainDispFlg;
    param.planLearnSeasnId = $(".learn_seasn .selected").data("seasn_id");

    $.post(ctxPath + "/student/F10301/update", param, function (data) {
        if (data.code == '0') {
//            layer.alert($.format($.msg.MSGCOMN0014, "ウィークリープラン"));
            //$('#calendar').fullCalendar("removeEvents");
            /* 2020/12/24 ITA-027 cuikailin modify start */
            if (param.blockTypeDiv == 'S1') {
                Vue.set(vm.planPerf, data.result.planPerfId, data.result);
            }
            /* 2020/12/24 ITA-027 cuikailin modify start */
            Vue.set(vm.selectBlock, data.result.stuTermPlanId, data.result.planPerfId);

            param.planPerfId = data.result.planPerfId;
            event.userParam = JSON.stringify(param);
            event.planPerfId = data.result.planPerfId;
            // 更新イベント
            $("#calendar").fullCalendar("updateEvent", event);

            // 積み残し学習ブロックの場合、
            if (event.remainDispFlg == "1") {
                $.each($("#calendar").fullCalendar("clientEvents"), function (i, item) {

                    if (item._id != event._id && param.planPerfId == item.planPerfId) {
                        // イベント削除
                        $("#calendar").fullCalendar("removeEvents", item._id);
                        return;
                    }
                });
            }
            if (popIndex) {
                layer.close(popIndex);
            }
            $('#calendar').fullCalendar("removeEvents");
            $("#calendar").fullCalendar("refetchEvents");
        } else {
            // イベント削除
            $("#calendar").fullCalendar("removeEvents", event._id);
            layer.alert(data.msg);
        }
        // window.location.reload();
    });

    return false;
}

function deleteEvent(event, popIndex) {
    var param = JSON.parse(event.userParam);
    $.post(ctxPath + "/student/F10301/delete", {'id': param.planPerfId}, function (data) {
        if (data.code == '0') {
//            layer.alert($.format($.msg.MSGCOMN0014, "ウィークリープラン"));
            Vue.delete(vm.planPerf, param.planPerfId);
            if (param.blockTypeDiv == "S1") {
                Vue.delete(vm.selectBlock, param.stuTermPlanId);
            }
            // イベント削除
            $("#calendar").fullCalendar("removeEvents", event._id);
            if (popIndex) {
                layer.close(popIndex);
            }
        } else {
            layer.alert(data.msg);
        }
    });
}

function getFristDay() {
    var result = "";
    for (var i = 0; i < vm.seasnList.length; i++) {
        var flg = false;
        if (vm.termPlan[vm.seasnList[i].startDy]) {
            $.each(vm.termPlan[vm.seasnList[i].startDy], function (j, termPlan) {
                if (vm.selectBlock[termPlan.stuTermPlanId] == null) {
                    result = vm.seasnList[i].startDy;
                    return false;
                }
            });
        }
        if (result != "") {
            break;
        }
    }
    return result;
}

function getLastDay() {
    var result = "";
    for (var i = vm.seasnList.length - 1; i >= 0; i--) {
        if (vm.termPlan[vm.seasnList[i].startDy]) {
            $.each(vm.termPlan[vm.seasnList[i].startDy], function (j, termPlan) {
                if (vm.selectBlock[termPlan.stuTermPlanId] == null) {
                    result = vm.seasnList[i].startDy;
                    return false;
                }
            });
        }
        if (result != "") {
            break;
        }
    }
    return result;
}

window.onload = function (ev) {
    $("#iframe").contents().find("#schedule_img").css('width','50%');
}
function playSound(){

    var borswer = window.navigator.userAgent.toLowerCase();
    if ( !!window.ActiveXObject || "ActiveXObject" in window )
    {
        //IE内核浏览器
        var OSPlayer = new ActiveXObject("WMPLayer.OCX");
        OSPlayer.url = "http://www.xmf119.cn/static/admin/sounds/notify.wav";
        OSPlayer.controls.play();
    } else
    {
        //非IE内核浏览器
        var strAudio = "<audio id='audioPlay' src='http://www.xmf119.cn/static/admin/sounds/notify.wav' hidden='true'>";
        if ( $( "body" ).find( "audio" ).length <= 0 ){
            $( "body" ).append( strAudio );
        }
        var audio = document.getElementById( "audioPlay" );
        //浏览器支持 audion
        audio.play();
    }
}
$(".reviewBlock").on("touchmove", function(e) {
    e.preventDefault();
    var moveEndX = e.originalEvent.changedTouches[0].pageX;
    var moveEndY = e.originalEvent.changedTouches[0].pageY;

    var xMM = moveEndX - $(this).width() / 2;
    var yMM = moveEndY - $(this).height() / 2;
    if(xMM < document.documentElement.clientWidth - $(this).width() && xMM > 0){
        $(this).css("left",xMM+"px");
    }

    if(yMM < document.documentElement.clientHeight - $(this).height() && yMM > 0){
        $(this).css("top",yMM+"px");
    }


});
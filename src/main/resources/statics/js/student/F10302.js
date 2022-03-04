// インポート時間
var eventDuration = 30;
// 一時保存インポート
var tmpEvent = null;
//2020/12/13 huangxinliang modify start
// popupFlg
// var popupFlg = false;
//2020/12/13 huangxinliang modify end
var perfLearnYmd = '';
var perfLearnTime = '';
var perfLearnEndTime = '';
$(function() {
    // 初期化カレンダー
    $("#calendar").fullCalendar({
        locale: "ja",
        now: new Date(),
        firstDay:1,
        defaultView: "agendaWeek",
        editable: false,
        droppable: true,
        height : $(window).height() - $(window).height() * 0.19,
        header: {left: '',center: '', right: ''},
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
                    temp += ('<p class="custom_col_header_date restday">' + mon.date() + '</p><p class="custom_col_header_day restday">日</p>');
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
                slotLabelInterval : {hours:1},
                slotLabelFormat : 'H',
                allDaySlot: false,
                slotEventOverlap: false,
                minTime : "05:00:00",
                maxTime : "25:00:00",
            }
        },
        scrollTime: '17:00:00',
        // event setting
        dropAccept: '.fc-event',
        eventDurationEditable: false,
        defaultTimedEventDuration : "00:30:00",
        events: function(start, end, timezone, callback){
            // VM
            vm.dateTitle = this.getDate().format('YYYY年MM月');
            // AJAX
            $.ajax({
                url: ctxPath + "/student/F10302/schdlList",
                dataType:'json',
                data: {tgtYmd:this.getDate().format('YYYYMMDD')},
                success: function(data) {
                    if (!data.schdlList) {
                        return;
                    }
                    var events = [];
                    $(data.schdlList).each(function(i, schdl) {
                        var classNames = "";
                        if (schdl.id != null) {
                            classNames = getEventClass(schdl.learnLevUnds) + " customEvent";
                        } else {
                            classNames = "schoolEvent loopEvent";
                        }
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
                        //2020-11-20 huangxinliang V9.0 modify start
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
                        events.push({
                            title: title,
                            //2020-11-20 huangxinliang V9.0 modify end
                            start: schdl.startTime,
                            end: schdl.endTime,
                            allDay: false,
                            editable:false, //編集可否
                            overlap:false, // 複数不可
                            //blockId:schdl.blockId,
                            userParam:JSON.stringify(schdl),
                            className: classNames,
                            weeklyPlanPerfId:schdl.id,
                            isFixed:schdl.isFixed,
                            blockTypeDiv:schdl.blockTypeDiv,
                            backgroundColor:bgColor,
                            // borderColor:foreColor,
                            textColor:foreColor
                        });
                    });
                    callback(events);
                }
            });
        },
        eventReceive: function (event) {
            perfLearnYmd = event.start.format('YYYY/MM/DD');
            perfLearnTime = event.start.format('HH:mm');
            var start = new Date(event.start.format('YYYY/MM/DD HH:mm'));
            start.setMinutes(start.getMinutes() + 30)
            perfLearnEndTime = start.Format('HH:mm')
            //2020/11/16 huangxinliang modify start
            // called when a proper external event is dropped
            openF10307(-1);
            $('#calendar').fullCalendar('removeEvents',event._id);
            //2020/11/16 huangxinliang modify end
        },
        eventClick: function(_event, _jsEvent, _view) {
            tmpEvent = _event;
            // クリックの判定
            if (_event.isFixed) {
                return false;
            }
            if (_event.blockTypeDiv == "S1" || _event.blockTypeDiv == "R1" || _event.blockTypeDiv == "P1" || _event.blockTypeDiv == "W1" || _event.blockTypeDiv == "V1" ||
                _event.blockTypeDiv == "NP") {
                // POPF10307
                openF10307(_event.weeklyPlanPerfId, true);
            }
            return false;
        }
    });

    // prevボタン
    $(".fc-prev-button").click(function(){
        $('#calendar').fullCalendar("prev");
    });

    // nextボタン
    $(".fc-next-button").click(function(){
        $('#calendar').fullCalendar("next");
    });

    // pre Tab クリック
    $("#fixedAreaBtn").click(function () {
        $("#fixedAreaBtn").addClass("active");
        $("#planAreaBtn").removeClass("active");
        $("#sub_menu").addClass("disabled");
        window.location.href = "F10101.html";
    });

    // 当日～
    $("#todayPrintBtn").click(function() {
        $.post(ctxPath + "/student/F10301/todayPrint", {}, function(data){
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
    $("#printBtn").click(function() {
        $.post(ctxPath + "/student/F10301/print", { "tgtYmd": $("#calendar").fullCalendar("getDate").format("YYYYMMDD")}, function(data){
            if (data.code == '0') {
                $("#downloadPdf #fileName").val(data.fileName);
                $("#downloadPdf").submit();
            } else {
                layer.alert(data.msg);
            }
        }, "json");
        return false;
    });

    $(".block-div").css("height", $(window).height() - $(window).width()*0.12 - 157);

});

// 画面初期化
var vm = new Vue({
    el: '#pageF10302',
    data: {
        tgtYmd: "",
        dateTitle: "",
        seasnList:[],
        weekStartDay:"",
        planPerf:{},
        notStartBlock:[],
        stuNm:''
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function (tgtYmd) {
            $.getJSON(ctxPath + "/student/F10302/blockInfo", {'tgtYmd' : tgtYmd}, function (data) {
                if (data.code != "0" && !data.blockInfo) {
                    layer.alert($.msg.MSGCOMN0020);
                    return;
                }

                // 学習週リスト
                vm.seasnList = data.blockInfo.seasnList;
                // 学習ブロック
                vm.planPerf = data.blockInfo.planPerf;
                // 実績ポップアップリスト
                // vm.notStartBlock = data.blockInfo.notStartBlock;
                // 対象日
                vm.tgtYmd = data.blockInfo.weekStartDay;
                //生徒姓名
                vm.stuNm=data.stuNm;

                vm.$nextTick(function(){
                    slideBind();
                    //2020/12/13 huangxinliang modify start
                    // if (popupFlg == false) {
                    //     if (vm.notStartBlock && vm.notStartBlock.length > 0) {
                    //         openF10307(vm.notStartBlock[0].planPerfId, false);
                    //         popupFlg = true;
                    //     }
                    // }
                    //2020/12/13 huangxinliang modify end
                });
                //2020/11/16 huangxinliang modify start
                $('.other-block').each(function (index, item) {
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
                            _helper.helper.css("width", $(this).width()).css("height", $(this).height()).css("marginLeft", 0).css("marginRight", 0).css("background","red");
                        },
                        // stop: function(event, ui){
                        //     window.location.reload()
                        // },
                        helper: "clone",
                        zIndex: 999,
                        revertDuration: 0,
                        disabled: false,
                        appendTo: "body",
                        // 2020/12/15 huangxinliang modify start
                        scroll: false
                        // 2020/12/15 huangxinliang modify end
                    });

                    $(this).touchDraggable();
                });
                //2020/11/16 huangxinliang modify end
            });
        },
        getClass: function (item) {
            var strClass = "";
            if (item.learnLevUnds == "1" || item.learnLevUnds == "2" || item.learnLevUnds == "3") {
                strClass+= " passEvent";
            } else if (item.learnLevUnds == "4") {
                strClass+= " failEvent";
            } else if (item.learnLevUnds == "0") {
                strClass+= " errEvent";
            }
            return strClass;
        },
        //2020/11/17 9.0 huangxinliang add start
        update: function (){
            $("#calendar").fullCalendar("refetchEvents");
        }
        //2020/11/17 9.0 huangxinliang add end
    }
});

// openF10307
function openF10307(id, closeFlg) {
    var scrWidth = $(window).width()*0.8;
    var scrHeight = $(window).height()*0.8;
    if(scrWidth > 360){
        scrWidth = '360px'
    }else{
        scrWidth = scrWidth + 'px'
    }
    if(scrHeight > 450){
        scrHeight = '450px'
    }else{
        scrHeight = scrHeight + 'px'
    }
    //F10307POP
    layer.open({
        id:'f10307',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [scrWidth, scrHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F10307.html?" + $.param({'id':id, 'closeFlg':closeFlg,'perfLearnYmd':perfLearnYmd,'perfLearnTime':perfLearnTime,'perfLearnEndTime':perfLearnEndTime}), 'no'],
        success: function (layero, index) {
        }
    });
}

function getEventClass(learnLevUnds) {
    var classNames;
    if (learnLevUnds == "1" || learnLevUnds == "2" || learnLevUnds == "3") {
        classNames = "passEvent";
    } else if (learnLevUnds == "4"){
        classNames = "failEvent";
    } else if (learnLevUnds == "0") {
        classNames = "errEvent";
    } else {
        classNames = "zeroEvent";
    }
    return classNames;
}

function slideBind() {
    // 学習週選択エリア
    learnSeasn = new Swiper('.learn_seasn', {
        slidesPerView: 3,
        centeredSlides: true,
        mousewheel: true,
        on:{
            tap: function(swiper){
                var clickIndex = learnSeasn.clickedIndex;
                learnSeasn.slideTo(clickIndex);
                setCurrentSlide($(".learn_seasn .swiper-slide").eq(clickIndex), clickIndex);
            }
        }
    });
    learnSeasn.slideTo($(".learn_seasn .selected").index(), 0, false);
}

// 学習週の選択状態を設定する
function setCurrentSlide(ele, index) {
    // 選択クラスをクリアする
    $(".learn_seasn .swiper-slide").removeClass("selected");
    // クラスを設定する
    ele.addClass("selected");
    // 対象日を取得する
    vm.tgtYmd = $(ele).data("startdy");

    return false;
}

window.onload = function (ev) {
    $("#iframe").contents().find("#schedule_img").css('width','50%');
}
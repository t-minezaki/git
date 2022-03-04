//iframe自适应
$(window).on('resize', function () {
    // var $content = $('.content');
    // $content.height($(this).height() - 140);
    // $content.find('iframe').each(function() {
    //     $(this).height($content.height());
    // });
}).resize();
var vm = new Vue({
    el: '#pageF20006',
    data: {
        currentYmd: "", //現在の年月日
        tgtYmd: "",
        remainBlock: [],// 積み残しブロック
        learnBlock: [], // 今週学習ブロック
        studentBlock: [],
        otherBlock: [],

        currentSeasn: "",
        prevSeasn: "",
        nextSeasn: "",
        prevYmd: "",
        nextYmd: "",
        currentFlg: "", // 今週
        currentLbl: "",
        mentorNm: "",
        stuNm: ""
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {

    },
    methods: {
        showData: function (data) {
            $.getJSON(ctxPath + "/manager/F20006/blockInfo", {'tgtYmd': data}, function (data) {
                vm.getDataInfo(data);
            });
        },
        getDataInfo: function (data) {
            if (data.code != "0" && !data.pageInfo) {
                showMsg($.format($.msg.MSGCOMN0017, "ブロック"))
                return;
            }
            vm.currentLbl = data.blockInfo.currentLbl;
            vm.currentFlg = data.blockInfo.currentFlg;
            vm.currentSeasn = data.blockInfo.currentSeasn;
            vm.prevSeasn = data.blockInfo.prevLbl;
            vm.nextSeasn = data.blockInfo.nextLbl;
            vm.currentYmd = data.blockInfo.currentYmd;
            vm.prevYmd = data.blockInfo.prevYmd;
            vm.nextYmd = data.blockInfo.nextYmd;
            vm.learnBlock = data.blockInfo.blockInfo;
            vm.mentorNm = data.mentorNm;
            vm.stuNm = data.stuNm;
            vm.$nextTick(function () {
                blockBind();
            });
        },
        setClass: function (learnLevUnds, remainDispFlg) {
            if (learnLevUnds != "削除") {
                if (learnLevUnds == "0") {
                    return "underRedNone";
                } else if (learnLevUnds == "未計画") {
                    return "underNoneRed";
                }
                if (remainDispFlg == "1" || remainDispFlg == "3") {
                    return "underYellowNone";
                }
                if (remainDispFlg == "0" || remainDispFlg == "4" || remainDispFlg == null || remainDispFlg == " ") {
                    switch (learnLevUnds) {
                        case "0":
                            return "underRedNone";
                        case "1":
                            return "underBlueNone";
                        case "2":
                            return "underBlueNone";
                        case "3":
                            return "underBlueNone";
                        case "4":
                            return "underYellowNone";
                        default:
                            return "underNoneGray";
                    }
                }
            }else{
                return "underNoneBlack";
            }
        },
        setLearnLevUnds: function (learnLevUnds) {
            switch (learnLevUnds) {
                case "0":
                    return "0%";
                case "1":
                    return "90%";
                case "2":
                    return "75%";
                case "3":
                    return "60%";
                case "4":
                    return "59%";
                default:
                    return learnLevUnds;
            }
        }
    }
});
var layerId = null;

$(function () {
    var topH = $(window).width() * 0.11 + 2;
    var topH1 = $(window).width() * 0.095 + 4;
    var openheight = $(window).height() - topH;
    var openheight1 = $(window).height() - topH1;
    $(".openPopInit").css('height', openheight);
    /* initialize the calendar
     -----------------------------------------------------------------*/
    // 初期化カレンダー
    $("#calendar").fullCalendar({
        locale: "ja",
        now: new Date(),
        firstDay: 1,
        defaultView: "agendaWeek",
        editable: false, // enable draggable events
        droppable: false, // this allows things to be dropped onto the calendar
        height: openheight1,
        header: {left: '', center: '', right: ''},
        columnHeaderHtml: function (mon) {
            var temp = '', tempDate = new Date();
            switch (mon.weekday()) {
                case 0:
                    temp += ('<span class="custom_col_header_date">' + mon.date() + '</span><span class="custom_col_header_day">月</span>');
                    break;
                case 1:
                    temp += ('<span class="custom_col_header_date">' + mon.date() + '</span><span class="custom_col_header_day">火</span>');
                    break;
                case 2:
                    temp += ('<span class="custom_col_header_date">' + mon.date() + '</span><span class="custom_col_header_day">水</span>');
                    break;
                case 3:
                    temp += ('<span class="custom_col_header_date">' + mon.date() + '</span><span class="custom_col_header_day">木</span>');
                    break;
                case 4:
                    temp += ('<span class="custom_col_header_date">' + mon.date() + '</span><span class="custom_col_header_day">金</span>');
                    break;
                case 5:
                    temp += ('<span class="custom_col_header_date satday">' + mon.date() + '</span><span class="custom_col_header_day satday">土</span>');
                    break;
                case 6:
                    temp += ('<span class="custom_col_header_date restday">' + mon.date() + '</span><span class="custom_col_header_day restday">日</span>');
                    break;
                default:
                    break;
            }
            if (mon.year() === tempDate.getFullYear() && mon.month() === tempDate.getMonth() && mon.date() === tempDate.getDate()) {
                temp = temp.replace(/custom_col_header_day/g, 'custom_col_header_day today').replace(/custom_col_header_date/g, 'custom_col_header_date today');
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
                maxTime: "25:00:00",
                columnFormat: "dd\r\nD",
            }
        },
        scrollTime: '17:00:00',
        // event setting
        dropAccept: '.fc-event',
        eventDurationEditable: false,
        defaultTimedEventDuration: "00:30:00",
        events: function (start, end, timezone, callback) {
            // VM
            vm.tgtYmd = this.getDate().format('YYYY年MM月');
            // AJAX
            $.ajax({
                url: ctxPath + "/manager/F20006/schdlList",
                dataType: 'json',
                data: {tgtYmd: this.getDate().format('YYYYMMDD')},
                async: true,
                success: function (data) {
                    if (!data.schdlList) {
                        showMsg($.format($.msg.MSGCOMN0017, "ブロック"))
                        return;
                    }
                    var events = [];
                    $(data.schdlList).each(function (i, schdl) {
                        var classNames = "";
                        //その他の場合 ブロック表示名より、半角スペースを区切りで、前部内容（その他小分類名）を表示する。 0704 tan
                        if(schdl.blockTypeDiv.substring(0, 1) == 'O'){
                            var arr = schdl.blockDispyNm.split(' ');
                            schdl.blockDispyNm = arr[0];
                        }
                        var dispyNm = schdl.blockDispyNm;
                        // if(schdl.blockTypeDiv=='S1'){
                        //     dispyNm=schdl.subjtNm;
                        // }
                        //  if(schdl.subjtDiv!=null) {
                        //      classNames = schdl.subjtDiv + " wordGrey loopEvent";
                        //  }else
                        if (schdl.id != null) {
                            classNames = getEventClass(schdl.learnLevUnds) + " customEvent loopEvent";
                        } else {
                            classNames = "schoolEvent loopEvent";
                        }
                        events.push({
                            id: schdl.id,
                            title: dispyNm,
                            start: schdl.startTime,
                            end: schdl.endTime,
                            allDay: false,
                            editable: false, //編集可否
                            overlap: false, // 複数不可
                            blockId: schdl.blockId,
                            userParam: JSON.stringify(schdl),
                            className: classNames
                        });
                    });
                    callback(events);
                }
            });
        }
    });

    // prevボタン
    $(".fc-prev-button").click(function () {
        $('#calendar').fullCalendar("prev");
    });

    // nextボタン
    $(".fc-next-button").click(function () {
        $('#calendar').fullCalendar("next");
    });

    // 当日～
    $("#todayPrintBtn").click(function () {
        $.post(ctxPath + "/manager/F20006/dayPrint", {"url": window.location.href}, function (data) {
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
        $.post(ctxPath + "/manager/F20006/weekPrint", {
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

});

function blockBind() {
    $('.openPopInit_Li').click(function (e) {
        var param = {};
        var obj = $(this);
        param.blocktypediv = obj.attr("blocktypediv")

        // // 生徒ID
        // param.stuId = obj.attr("stuId");
        // // 単元ID
        // param.unitId = obj.attr("unitId");
        // // 枝番
        // param.bnum = obj.attr("bnum");

        //isterm(t:(0：未計画、２：削除）f:1：計画済み)
        //理解度枠
        if (obj.attr("isTerm") == 'f') {
            param.id=obj.attr("id");
        }else{
            param.id=obj.attr("termId");
        }

        //stu_del_flg=1
        if(obj.attr("isTerm") == 'f' && obj.attr("learnLevUnds") == '削除'){
            param.stuDelFlg=0;
        }
        param.isTerm =obj.attr("isTerm") ;
        param.currentYmd = $("#current").attr("tgtymd");
        //514   wen    RLS3    NO.67
        openF20014(param);
    });
}

function blockDraggable() {
    $('#external-events .fc-event').each(function (index, item) {
        $(this).data('event', {
            title: $.trim($(this).text()),
            stick: true,
            allDay: false,
            editable: true,
        });
        $(this).draggable({
            zIndex: 999,
            revert: true,
            revertDuration: 200
        });
        $(this).touchDraggable();
    });
    return false;
}

// POP F20014
function openF20014(param) {
    var scrWidth = $(window).width() * 0.8;
    var scrHeight = $(window).height() * 0.8;
    if (scrWidth > 320) {
        scrWidth = '320px'
    } else {
        scrWidth = scrWidth + 'px'
    }
    if (scrHeight > 430) {
        scrHeight = '430px'
    } else {
        scrHeight = scrHeight + 'px'
    }
    var index = layer.open({
        id: 'f20014',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [scrWidth, scrHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F20014.html?" + $.param(param), 'no'],
        success: function (layero, index) {
        },
    });
    return false;
}

function getEventClass(learnLevUnds) {
    var classNames;
    if (learnLevUnds == "1" || learnLevUnds == "2" || learnLevUnds == "3") {
        classNames = "passEvent";
    } else if (learnLevUnds == "4") {
        classNames = "failEvent";
    } else if (learnLevUnds == "0") {
        classNames = "errEvent";
    } else {
        classNames = "zeroEvent";
    }
    return classNames;
}
// インポート時間
var eventDuration = 30;
// 一時保存インポート
var tmpEvent = null;
// 画面初期化
new Vue({el: 'pad-menu'});
var vm = new Vue({
    el: '#pageF10101',
    data: {
        tgtYmd: "",
        currentYmd: "",
        block: [],
        stuNm:''
    },
    mounted: function () {
        // this.showData();
    },
    updated: function () {
        vm.$nextTick(function () {
            blockBind();
        });
    },
    methods: {
        // showData: function (currenDate) {
        //     $.getJSON(ctxPath + "/student/F10101/getBlockInfo", {currenDate: currenDate}, function (data) {
        //         if (data.code != "0" && !data.block) {
        //             return;
        //         }
        //         //固定ブロックエリア情報の取得
        //         vm.block = data.block;
        //     });
        // }
    }
});

window.onload = function (ev) {
    $("#iframe").contents().find("#schedule_img").css('width','50%');
}

$(function () {
    // tab
    $(".calendar").tabs();

    // 初期化カレンダー
    $("#calendar").fullCalendar({
        locale: "ja",
        now: new Date(),
        firstDay: 1,
        defaultView: "agendaWeek",
        editable: true, // enable draggable events
        droppable: true, // this allows things to be dropped onto the calendar
        height: $(window).height() -$(window).width()*0.09,
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
            }
        },
        scrollTime: '06:00:00',
        // event setting
        dropAccept: '.fc-event',
        eventDurationEditable: false,
        defaultTimedEventDuration: "00:30:00",
        events: function (start, end, timezone, callback) {
            // VM
            vm.tgtYmd = this.getDate().format('YYYY年MM月');
            // AJAX
            $.ajax({
                url: ctxPath + "/student/F10101/init",
                dataType: 'json',
                data: {currenDate: this.getDate().format('YYYYMMDD')},
                success: function (data) {
                    if (data.code != "0" && !data.block) {
                        return;
                    }
                    //固定ブロックエリア情報の取得
                    vm.block = data.block;
                    if (!data.schdlList) {
                        return;
                    }
                    if(data.stuNm){
                        vm.stuNm=data.stuNm;
                    }
                    var events = [];
                    $(data.schdlList).each(function (i, schdl) {
                        var classNames = "schoolEvent loopEvent";
                        var color = data.map[schdl.blockId] == 'white'?'black':'white';
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
                        events.push({
                            title: schdl.blockDispyNm,
                            start: schdl.startTime,
                            end: schdl.endTime,
                            allDay: false,
                            editable: false, //編集可否
                            overlap: false, // 複数不可
                            //blockId:schdl.blockId,
                            userParam: JSON.stringify(schdl),
                            backgroundColor:bgColor,
                            textColor:foreColor,
                            className: classNames
                        });
                    });
                    callback(events);
                }
            });
        },
        eventReceive: function (event) { // called when a proper external event is dropped
            var param = JSON.parse(event.userParam);
            // イベント削除
            $("#calendar").fullCalendar("removeEvents", event._id);
            tmpEvent = event;
            openF10102(param.id, "1",null,event);
            // 更新イベント
            $("#calendar").fullCalendar("updateEvent", event);
            // ブロック種類区分
        },
        eventClick: function (_event, _jsEvent, _view) {
            var param = JSON.parse(_event.userParam);
            tmpEvent = _event;
            var index=layer.confirm('修正種類を選択してください', {
                title:'確認',
                closeBtn:0,
                shadeClose:true,
                btn:['個別修正','全体修正'],
                btn1:function () {
                    // POPF10102
                    openF10102(param.id, "2",'0',_event);
                    layer.close(index);
                },
                btn2:function () {
                    // POPF10102
                    openF10102(param.id, "2",'1',_event);
                    layer.close(index);
                }
            });
            return false;
        },
    });

    // prevボタン
    $(".fc-prev-button").click(function () {
        $('#calendar').fullCalendar("prev");
    });

    // nextボタン
    $(".fc-next-button").click(function () {
        $('#calendar').fullCalendar("next");
    });

    //ウィークリープラン
    $("#planAreaBtn").click(function () {
        $("#sub_menu").removeClass("disabled");
        $("#planAreaBtn").addClass("active");
        $("#fixedAreaBtn").removeClass("active");
        window.location.href = "F10301.html";
    });

    // pre Tab クリック
    $("#fixedAreaBtn").click(function () {
        $("#fixedAreaBtn").addClass("active");
        $("#planAreaBtn").removeClass("active");
        $("#sub_menu").addClass("disabled");
        window.location.reload();
        layer.close(index);
    });

    // // pre Tab クリック
    // $("#perfBtn").click(function () {
    //     window.location.href = "F10302.html";
    // });
    //
    // //計画
    // $("#planBtn").click(function () {
    //     $("#sub_menu").removeClass("disabled");
    //     $("#planAreaBtn").addClass("active");
    //     $("#fixedAreaBtn").removeClass("active");
    //     window.location.href = "F10301.html";
    // });
});

/**
 * bind events
 */
function blockBind() {
    // block event add
    $('.block').each(function (index, item) {
        $(this).data('event', {
            title: $.trim($(this).text()),
            stick: true,
            allDay: false,
            editable: true,
            overlap: false,
            className: "zeroEvent customEvent",
            userParam: $(this).attr("data-json")
        });
        $(this).draggable({
            start: function (_source, _helper) {
                _helper.helper.css("width", getComputedStyle(_source.target).getPropertyValue("width").replace(/px/g, '')).css("height", getComputedStyle(_source.target).getPropertyValue("height").replace(/px/g, ''));
            },
            helper: "clone",
            zIndex: 999,
            //revert: true,
            revertDuration: 0,
            disabled: false
        });
        $(this).touchDraggable();
    });

    return false;
}

// openF10102
function openF10102(id, key,singleOrAll,_event) {
    var param={};
    param.id=id;
    param.key=key;
    param.singleOrAll=singleOrAll;
    param.start=_event.start.format('YYYYMMDDHHmmss');
    if(singleOrAll=='0'){
        param.date1=_event.start.format('YYYY/MM/DD');
        param.startTime=_event.start.format('HH:mm');
        //param.endTime=_event.end.format('HH:mm');
        //param.end=_event.end.format('YYYYMMDDHHmmss');
    }
    param.fixdInitTime = _event.start.format('HH:mm');
    var index = layer.open({
        id: 'f10102',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['320px', '420px'],
        fixed: true,
        resize: false,
        content: ["../pop/F10102.html?" + $.param(param), 'no'],
        success: function (layero, index) {
        }
    });
}

var menuHeight = $(window).height() -$(window).width()*0.12;
$(".menu_container").height(menuHeight)
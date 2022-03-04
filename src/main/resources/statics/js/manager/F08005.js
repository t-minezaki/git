// インポート時間
var eventDuration = 30;
//セッションデータを取得
var param = getParam();
var map = [];
var successFlg = false;
//選択の時間
var selectDate = '';
// タイムフォーマット変換
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //時
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //四半期
        "S": this.getMilliseconds()             //ミリ秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};
var today = new Date().format('yyyyMMdd');

// 画面初期化
var vue = new Vue({
    el: '#pageF08005',
    data: {
        colorList: [],
        userCheckList: [],
        mstOrgEntity: '',
        eventScheduleEntity: '',
        mstEventEntity: '',
        map: {},
    },
    mounted: function () {
        laydate.render({
            elem: '#time',
            position: 'static',
            type: 'datetime',
            showBottom: false,
            change: function (value, date) {
                selectDate = value;
                $('#calendar').fullCalendar("gotoDate", selectDate);
                $.ajax({
                    url: ctxPath + "/manager/F08005/eventSchedule",
                    dataType: 'json',
                    type: 'GET',
                    data: {
                        eventId: param.eventId,
                        tgtYmd: new Date(selectDate).format('yyyyMMdd')
                    },
                    success: function (data) {
                        if (!data.eventScheduleEntityList) {
                            showMsg(data.msg);
                            return;
                        } else {
                            if ($("#message") != null) {
                                $(".topMessage").removeClass("hasHeight");
                                $(".topMessage").html("");
                                $(".topMessage").css("display", "none");
                            }
                        }
                    }
                });
            }
        });
        this.getInitData();
        this.event_schedule();
        $("#user_area").on('click', 'input', function () {
            var flg = $(this).val();
            if (!$(this).is(":checked")) {
                $(this).attr("checked", false);
                $("." + flg).css("visibility", "hidden");
            } else {
                $(this).attr("checked", true);
                $("." + flg).css("visibility", "visible");
            }
        });
    },
    updated: function () {
        // 画面表示データを取得する
        if (vue.mstEventEntity.refType == "0") {
            $("#user_input").attr("disabled", true);
            $("#user_area").find("input").eq(0).css("backgroundColor", vue.colorList[0].codValue);
        } else {
            if (vue.userCheckList.length > vue.colorList.length) {
                $("#user_area").find("input").each(function (index, element) {
                    if (index > vue.colorList.length - 1) {
                        index = index - vue.colorList.length;
                        $(this).css("backgroundColor", vue.colorList[index].codValue);
                    } else {
                        $(this).css("backgroundColor", vue.colorList[index].codValue);
                    }
                });
            } else {
                $("#user_area").find("input").each(function (index, element) {
                    $(this).css("backgroundColor", vue.colorList[index].codValue);
                });
            }
        }

    },
    methods: {
        event_schedule: function () {
            $(".block-div").css("height", $(window).height() - $(window).width() * 0.22 - 1);
            // 初期化カレンダー
            $("#calendar").fullCalendar({
                locale: "ja",
                now: new Date(),
                firstDay: 1,
                defaultView: "agendaWeek",
                editable: true,
                droppable: true,
                height: $(window).height() - $(window).height() * 0.19,
                header: {left: '', center: '', right: ''},
                columnHeaderHtml: function (mon) {
                    var temp = '', tempDate = new Date();
                    switch (mon.weekday()) {
                        case 0:
                            temp += ('<p class="custom_col_header_day">月</p><p class="custom_col_header_date">' + mon.date() + '</p>');
                            break;
                        case 1:
                            temp += ('<p class="custom_col_header_day">火</p><p class="custom_col_header_date">' + mon.date() + '</p>');
                            break;
                        case 2:
                            temp += ('<p class="custom_col_header_day">水</p><p class="custom_col_header_date">' + mon.date() + '</p>');
                            break;
                        case 3:
                            temp += ('<p class="custom_col_header_day">木</p><p class="custom_col_header_date">' + mon.date() + '</p>');
                            break;
                        case 4:
                            temp += ('<p class="custom_col_header_day">金</p><p class="custom_col_header_date">' + mon.date() + '</p>');
                            break;
                        case 5:
                            temp += ('<p class="custom_col_header_day satday">土</p><p class="custom_col_header_date satday">' + mon.date() + '</p>');
                            break;
                        case 6:
                            temp += ('<p class="custom_col_header_day restday">日</p><p class="custom_col_header_date restday">' + mon.date() + '</p>');
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
                        slotLabelFormat: 'HH:mm',
                        allDaySlot: false,
                        slotEventOverlap: false,
                        minTime: "00:00:00",
                        maxTime: "24:00:00"
                    }
                },
                scrollTime: '09:00:00',
                // event setting
                dropAccept: '.fc-event',
                eventDurationEditable: false,
                events: function (start, end, timezone, callback) {
                    // AJAX
                    $.ajax({
                        url: ctxPath + "/manager/F08005/eventSchedule",
                        dataType: 'json',
                        type: 'GET',
                        data: {
                            eventId: param.eventId,
                            tgtYmd: this.getDate().format('YYYYMMDD')
                        },
                        success: function (data) {
                            map = data.map;
                            if (!data.eventScheduleEntityList) {
                                showMsg(data.msg);
                                return;
                            }
                            var events = [];
                            $(data.eventScheduleEntityList).each(function (i, schdl) {
                                events.push({
                                    title: schdl.displayNm,
                                    start: schdl.sgdStartDatime,
                                    end: schdl.sgdEndDatime,
                                    allDay: false,
                                    overlap: true, // 複数不可
                                    className: schdl.refId,
                                    scheduleId: schdl.id,
                                    userParam: JSON.stringify(schdl),
                                    backgroundColor: data.map[schdl.refId],
                                    borderColor: data.map[schdl.refId]
                                });
                            });
                            callback(events);
                        }
                    });
                },
                //空白の日付エリアをクリック
                dayClick: function (date, jsEvent) {
                    //予約は当日以降です。1202
                    if (new Date().Format("yyyy/MM/dd") > date.format("YYYY/MM/DD")) {
                        parent.layer.alert($.format($.msg.MSGCOMN0105, "日程予定"));
                        return;
                    }
                    openF08006(jsEvent, date);
                },
                //イベントをクリック
                eventClick: function (_event) {
                    openF08006(_event);
                },
                //イベントをドラッグ＆ドロップ
                eventDrop: function (event, dayDelta) {
                    // 更新処理
                    updateEvent(event, dayDelta);
                }
            });
        },

        // 画面を初期化
        getInitData: function () {
            $.ajax({
                url: ctxPath + '/manager/F08005/init',
                type: "GET",
                data: {
                    eventId: param.eventId,
                    userName: $("#user_input").val()
                },
                async: true,
                datatype: "json",
                success: function (data) {
                    if (data.colorList) {
                        vue.colorList = data.colorList;
                    }
                    if (data.userCheckList) {
                        vue.userCheckList = data.userCheckList;
                    }
                    if (data.mstOrgEntity) {
                        vue.mstOrgEntity = data.mstOrgEntity;
                    }
                    if (data.mstEventEntity) {
                        vue.mstEventEntity = data.mstEventEntity;
                    }
                    if (data.minScheDate) {
                        //日程がある場合、一番近い日程の週を表示します。
                        $('#calendar').fullCalendar("gotoDate", data.minScheDate);
                        $.ajax({
                            url: ctxPath + "/manager/F08005/eventSchedule",
                            dataType: 'json',
                            type: 'GET',
                            data: {
                                eventId: param.eventId,
                                tgtYmd: new Date(data.minScheDate).format('yyyyMMdd')
                            },
                            success: function (data) {
                                if (!data.eventScheduleEntityList) {
                                    showMsg(data.msg);
                                    return;
                                } else {
                                    if ($("#message") != null) {
                                        $(".topMessage").removeClass("hasHeight");
                                        $(".topMessage").html("");
                                        $(".topMessage").css("display", "none");
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    },
});

$("#user_input").bind("input propertychange", function () {
    if ($("#message") != null) {
        $(".topMessage").removeClass("hasHeight");
        $(".topMessage").html("");
        $(".topMessage").css("display", "none");
    }
    var txt = $("#user_input").val();
    var children = $("#user_area").children().find("input[type='checkbox']").parent();
    if (txt.length <= 0) {
        children.css("display", "block");
    } else {
        children.css("display", "none");
        $("#user_area").children().find("label:contains('" + txt + "')").parent().css("display", "block");
        $("." + $("#user_area").children().find("label").not(":contains('" + txt + "')").parent().find("input").eq(0).val()).css("visibility", "hidden");
    }
});
var eventId = '';

// POP F08006
function openF08006(_event, selectDate) {
    eventId = _event._id;
    var object = {};
    object.eventId = param.eventId;
    object.refType = vue.mstEventEntity.refType;
    if (!_event.hasOwnProperty('userParam')) {
        object.date = selectDate.format('YYYY/MM/DD HH:mm:ss');
    } else {
        var jsonData = JSON.parse(_event.userParam);
        object.scheduleId = _event.scheduleId;
        object.startTime = _event.start.format("YYYY/MM/DD HH:mm:ss.SSS");
        object.endTime = _event.end.format("YYYY/MM/DD HH:mm:ss.SSS");
        object.mentorId = jsonData.refId;
        object.selectDate = new Date(jsonData.sgdPlanDate).format("yyyy/MM/dd hh:mm:ss");
    }
    var index = layer.open({
        id: 'F08006',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['370px', '470px'],
        fixed: true,
        resize: false,
        content: ["../pop/F08006.html?" + $.param(object), 'no'],
        success: function (layero, index) {
        },
        end: function () {
            if ($(".fc-time-grid-event").length === 0) {
                $(".topMessage").addClass("hasHeight");
                $(".topMessage").html($.format($.msg.MSGCOMN0017, "日程スケジュール情報"));
                $(".topMessage").css("display", "block");
            }
            if (successFlg) {
                $(".topMessage").removeClass("hasHeight");
                $(".topMessage").html("");
                $(".topMessage").css("display", "none");
            }

        }

    });
    return false;
}

function updateEvent(event, dayDelta) {
    var param = JSON.parse(event.userParam);
    param.varDays = dayDelta._days;
    param.startTime = event.start.format('YYYY-MM-DD HH:mm:ss');
    if (event.end != null) {
        param.endTime = event.end.format('YYYY-MM-DD HH:mm:ss');
    } else {
        param.endTime = event.start.clone().add(eventDuration * 60000).format('YYYY-MM-DD HH:mm:ss');
    }
    $.post(ctxPath + "/manager/F08005/updateEventSchedule", param, function (data) {
        if (data.code == '0') {
            Vue.set(data.eventScheduleEntity, data.eventScheduleEntity.id, data.eventScheduleEntity);

            param.id = data.eventScheduleEntity.id;
            event.userParam = JSON.stringify(param);
            event._id = data.eventScheduleEntity.id;
            // 更新イベント
            $("#calendar").fullCalendar("updateEvent", event);
        } else {
            layer.confirm(data.msg, {
                btn: ['確認'],
                cancel: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                }
            }, function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                parent.location.reload();
            });
        }
    });
    return false;
}
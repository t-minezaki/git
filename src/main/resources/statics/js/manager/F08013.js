// インポート時間
var eventDuration = 30;
//セッションデータを取得
var param = getParam();
//選択の時間
var selectDate = '';
//画面区分
var pageDiv = '';
var usersList = [];
var eventsList = [];
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
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
}
var layRender;
// 画面初期化
var vue = new Vue({
    el: '#pageF08013',
    data: {
        colorList: [],
        userCheckList: [],
        eventScheduleEntity: '',
        mstEventEntityList: '',
    },
    mounted: function () {
        // 画面表示データを取得する
        layRender = laydate.render({
            elem: '#time',
            position: 'static',
            type: 'datetime',
            // lang: 'jp',
            // showBottom: false,
            change: function (value, date) {
                selectDate = value;
                $('#calendar').fullCalendar("gotoDate", selectDate);
                $.ajax({
                    url: ctxPath + "/manager/F08013/eventSchedule",
                    dataType: 'json',
                    type: 'GET',
                    data: {
                        tgtYmd: new Date(selectDate).format("yyyyMMdd")
                    },
                    success: function (data) {
                        if (!data.eventScheduleEntityList) {
                            showMsg(data.msg)
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
        $("#event_area").on('click', 'input', function () {
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
        $(".layui-laydate-content").find("td:not(.laydate-day-next)").find("td:not(.laydate-day-prev)").prevObject.click(function () {
            if ($("#message") != null) {
                $(".topMessage").removeClass("hasHeight");
                $(".topMessage").html("");
                $(".topMessage").css("display", "none");
            }
        })
        $(".layui-icon").click(function () {
            $(".topMessage").removeClass("hasHeight");
            $(".topMessage").html("");
            $(".topMessage").css("display", "none");
        })
        if (vue.userCheckList.length > vue.colorList.length) {
            $("#user_area").find("input").each(function (index, element) {
                if (index > vue.colorList.length - 1) {
                    $(this).css("background", vue.colorList[index % vue.colorList.length].codValue);
                } else {
                    $(this).css("background", vue.colorList[index].codValue);
                }
            });
        } else {
            $("#user_area").find("input").each(function (index, element) {
                $(this).css("background", vue.colorList[index].codValue);
            });
        }
    },
    methods: {
        event_schedule: function () {
            // 初期化カレンダー
            $("#calendar").fullCalendar({
                locale: "ja",
                now: new Date(),
                firstDay: 1,
                defaultView: "agendaWeek",
                editable: false,
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
                        url: ctxPath + "/manager/F08013/eventSchedule",
                        dataType: 'json',
                        type: 'GET',
                        data: {
                            tgtYmd: this.getDate().format('YYYYMMDD')
                        },
                        success: function (data) {
                            if (!data.eventScheduleEntityList) {
                                showMsg(data.msg);
                                return;
                            }
                            if (!data.map) {
                                return;
                            }
                            var events = [];
                            $(data.eventScheduleEntityList).each(function (i, schdl) {
                                var colorFlg = null;
                                var nameFlg = null;
                                var backColor = data.map[schdl.refId];
                                var displayName = null;
                                if (schdl.refTypeDiv == "0") {
                                    nameFlg = '1';
                                    displayName = "予約人数/総人数：" + schdl.planedMember + "/" + schdl.personsLimt;
                                }
                                if (schdl.cancelFlg == '1') {
                                    backColor = "grey";
                                } else {
                                    if (schdl.planedMember < schdl.personsLimt) {
                                        colorFlg = '1';
                                        if (schdl.refTypeDiv == "0") {
                                            if (schdl.replyDiv != "1" || schdl.detailId == null) {
                                                var color = "black";
                                            }
                                        }
                                    }
                                }
                                events.push({
                                    title: nameFlg == null ? schdl.displayNm: displayName,
                                    start: schdl.sgdStartDatime,
                                    end: schdl.sgdEndDatime,
                                    allDay: false,
                                    overlap: true,
                                    userParam: JSON.stringify(schdl),
                                    backgroundColor: colorFlg == null ? backColor : 'white',
                                    textColor: color,
                                    borderColor:  data.map[schdl.refId]  ,
                                    className: 'detail ' + schdl.refId + ' ' + schdl.eventId + ' ' + "borderDash"
                                });
                            });
                            callback(events);
                        }
                    });
                },
                //イベントをクリック
                eventClick: function (_event) {
                    var userParam = JSON.parse(_event.userParam);
                    if (userParam.planedMember < userParam.personsLimt) {
                        pageDiv = '2';
                        openF08015(_event, pageDiv);
                    }
                    if (userParam.planedMember == userParam.personsLimt) {
                        openF08014(_event);
                    }
                },
                //イベントをドラッグ＆ドロップ
                // eventDrop: function (event) {
                //     // 更新処理
                //     updateEvent(event);
                // }
            });
        },
        // 画面を初期化
        getInitData: function () {
            $.ajax({
                url: ctxPath + '/manager/F08013/init',
                type: "GET",
                data: {
                    // eventId: param.eventId,
                    userName: $("#user_input").val(),
                    eventTitle: $("#event_input").val()
                },
                async: true,
                datatype: "json",
                success: function (data) {
                    if (data.colorList) {
                        vue.colorList = data.colorList;
                    }
                    if (data.userCheckList) {
                        vue.userCheckList = data.userCheckList;
                        usersList = data.userCheckList;
                    }
                    if (data.mstEventEntityList) {
                        vue.mstEventEntityList = data.mstEventEntityList;
                        eventsList = data.mstEventEntityList;
                    }
                }
            });
        }
    }
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
        $("#user_area").children().find("span").parent().css("display", "block");
    } else {
        // children.css("display", "none");
        $("#user_area").children().find("span:contains('" + txt + "')").parent().css("display", "block");
        $("#user_area").children().find("span").not(":contains('" + txt + "')").parent().css("display", "none");
    }
});

$("#event_input").bind("input propertychange", function () {
    if ($("#message") != null) {
        $(".topMessage").removeClass("hasHeight");
        $(".topMessage").html("");
        $(".topMessage").css("display", "none");
    }
    var txt = $("#event_input").val();
    var children = $("#event_area").children().find("input[type='checkbox']").parent();
    if (txt.length <= 0) {
        children.css("display", "block");
        $("#event_area").children().find("span").parent().css("display", "block");
    } else {
        // children.css("display", "none");
        $("#event_area").children().find("span:contains('" + txt + "')").parent().css("display", "block");
        $("#event_area").children().find("span").not(":contains('" + txt + "')").parent().css("display", "none");
    }
});

// POP F08015
function openF08015(_event, pageDiv) {
    var object = {};
    var userParam = JSON.parse(_event.userParam);
    if (userParam.detailId) {
        object.eventScheduleDelId = userParam.detailId;
    } else {
        object.eventScheduleDelId = userParam.id;
    }
    // object.date = selectDate.format('YYYY-MM-DD HH:mm:ss');
    object.pageDiv = pageDiv;
    var userFlag = true;
    if (userParam.userFlag === false) {
        userFlag = false;
    }
    object.userFlag = userFlag;
    var index = layer.open({
        id: 'F08015',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['400px', '350px'],
        fixed: true,
        resize: false,
        content: ["../pop/F08015.html?" + $.param(object), 'no'],
        success: function (layero, index) {
        }
    });
    return false;
}

// POP F08014
function openF08014(_event) {
    var userParam = JSON.parse(_event.userParam);
    var eventScheDelId = userParam.detailId;
    //デフォルトは保護者です
    var userFlag = true;
    if (userParam.userFlag === false) {
        userFlag = false;
    }
    var index = layer.open({
        id: 'F08014',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['400px', '350px'],
        fixed: true,
        resize: false,
        content: ["F08014.html?id=" + eventScheDelId + "&userFlag=" + userFlag, 'no'],
        success: function (layero, index) {
        }
    });
    return false;
}

// function updateEvent(event) {
//     var param = JSON.parse(event.userParam);
//     param.startTime = event.start.format('YYYY-MM-DD HH:mm:ss');
//     if (event.end != null) {
//         param.endTime = event.end.format('YYYY-MM-DD HH:mm:ss');
//     } else {
//         param.endTime = event.start.clone().add(eventDuration * 60000).format('YYYY-MM-DD HH:mm:ss');
//     }
//     $.post(ctxPath + "/manager/F08013/updateEventSchedule", param, function (data) {
//         if (!data.eventScheduleEntity) {
//             if (!data.eventSchePlanDelEntity) {
//                 return;
//             }
//             Vue.set(data.eventSchePlanDelEntity, data.eventSchePlanDelEntity.id, data.eventSchePlanDelEntity);
//
//             param.id = data.eventSchePlanDelEntity.id;
//             event.userParam = JSON.stringify(param);
//             event._id = data.eventSchePlanDelEntity.id;
//             // 更新イベント
//             $("#calendar").fullCalendar("updateEvent", event);
//         } else {
//             if (!data.eventScheduleEntity) {
//                 return;
//             }
//             Vue.set(data.eventScheduleEntity, data.eventScheduleEntity.id, data.eventScheduleEntity);
//
//             param.id = data.eventScheduleEntity.id;
//             event.userParam = JSON.stringify(param);
//             event._id = data.eventScheduleEntity.id;
//             // 更新イベント
//             $("#calendar").fullCalendar("updateEvent", event);
//         }
//     });
// }

function proxyLogin() {
    var index = layer.open({
        id: 'proxy',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['400px', '350px'],
        fixed: true,
        resize: false,
        content: ["../pop/F08015.html?pageDiv=1", 'no'],
        success: function (layero, index) {
        }
    });
}
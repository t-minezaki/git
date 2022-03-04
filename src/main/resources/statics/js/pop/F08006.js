var param = getParam();
var timeStart = '';
var timeEnd = '';
var da = '';
var vm = new Vue({
        el: '#info',
        data: {
            showFlg: '',
            mstMentorEntityList: [],
            weekdayList: [],
            delButton: '',
            applySts:'',
            mentorId: ''
        },
        mounted: function () {
            this.getInfo();
            this.show(true);
            this.getMentor();
        },
        updated: function () {
        },
        methods: {
            //初期化
            getInfo: function () {
                $.ajax({
                    url: ctxPath + '/pop/F08006/init',
                    type: 'GET',
                    data: {
                        //イベントID
                        scheduleId: param.scheduleId,
                        eventId: param.eventId,
                        mentorId:param.mentorId
                    },
                    success: function (data) {
                        if (data.code == 0) {
                            if (data.applySts){
                                vm.applySts = data.applySts;
                            }
                            if (param.date) {
                                    timeStart = new Date(Date.parse(decodeURIComponent(param.date)));
                                    timeEnd = new Date(Date.parse(decodeURIComponent(param.date)));
                                    timeEnd.setMinutes(timeStart.getMinutes()+30);
                            } else {
                                timeStart = new Date(Date.parse(decodeURIComponent(param.startTime)));
                                timeEnd = new Date(Date.parse(decodeURIComponent(param.endTime)));
                            }
                            $("#time_picker_begin").val(timeStart.format("hh:mm"));
                            $("#time_picker_end").val(timeEnd.format("hh:mm"));
                            if (param.refType == "0") {
                                $("#usernumber").removeAttr("disabled").css("background-color", "white");
                                if (param.scheduleId){
                                    $("#usernumber").val(data.eventScheduleEntity.personsLimt)
                                }
                            } else {
                                $("#searchUser").removeAttr("disabled").css("background-color", "white");
                                if (param.scheduleId){
                                    var mentorName = data.mstMentorEntity.flnmNm + data.mstMentorEntity.flnmLnm;
                                    var tag = "<span class=" + "' select2-selection__rendered '"  +  "id=" + "'select2-searchUser-container ' "  + "role=" + "'textbox'" +
                                        "aria-readonly=" + "true"  + "title=" + mentorName + ">" + mentorName + "</span>"
                                    $(".select2-selection").html(tag);
                                }
                            }
                        } else {
                            showMsg(data.msg)
                        }
                    },
                })
            },
            //画面切り替え
            show: function (flg) {
                if (param.scheduleId) {
                    this.showFlg = 'del';
                } else {
                    if (flg == true) {
                        this.showFlg = flg;
                        $("#weeksel").css('display', 'none');
                        this.weekdayList.length=0;
                        $("li").css({'color': 'green', 'background-color': 'white'});
                    } else {
                        $("#weeksel").css('display', 'block');
                        this.showFlg = flg;
                    }
                }

            },
            //保存ボダン押下
            save: function () {
                var rex = /^[0-9]+$/;
                if (param.refType == "0") {
                    if ( $("#usernumber").val()=="") {
                        parent.layer.alert($.format($.msg.MSGCOMD0001, "定員"));
                        return;
                    }
                    if (!rex.test($("#usernumber").val())) {
                        parent.layer.alert($.format($.msg.MSGCOMD0006, "定員"));
                        return;
                    }
                    if (($("#usernumber").val().substr(0, 1) == 0&&$("#usernumber").val().length == 1) || $("#usernumber").val() > 999 || $("#usernumber").val() < 1) {
                        parent.layer.alert($.format($.msg.MSGCOMD0010, "定員", "1", "999"));
                        return;
                    }
                }
                if ($("#time_picker_begin").val() >= $("#time_picker_end").val()) {
                    parent.layer.alert($.format($.msg.MSGCOMN0048, "日程設定終了時間", "日程設定開始時間"))
                    return;
                }
                if (vm.showFlg == true || vm.showFlg == 'del') {
                    var time = '';
                    if (param.date){
                        time = param.date;
                    } else{
                        time = param.start
                    }
                    $.ajax({
                        url: ctxPath + '/pop/F08006/saveSingle',
                        type: 'POST',
                        data: {
                            flg: vm.showFlg,
                            eventId: param.eventId,
                            scheduleId:param.scheduleId,
                            startTimedate: param.date,
                            selectDate: param.selectDate,
                            username:$(".select2-selection__rendered").attr("title"),
                            mentorId: param.mentorId?param.mentorId:vm.mentorId,
                            personlimit: $("#usernumber").val(),
                            SgdStartDatime: $("#time_picker_begin").val(),
                            SgdEndDatime: $("#time_picker_end").val(),
                            refType: param.refType
                        },
                        async: false,
                        success: function (data) {
                            da = data;
                            if (data.code == 0) {
                                // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                        var index = parent.layer.getFrameIndex(window.name);
                                        var event = {
                                            title:da.displayNm,
                                            start : timeStart,
                                            end : timeEnd,
                                            allDay: false,
                                            overlap: true, // 複数不可
                                            className:da.eventScheduleEntity.refId,
                                            scheduleId: da.eventScheduleEntity.id,
                                            userParam: JSON.stringify(da.eventScheduleEntity),
                                            backgroundColor: parent.map[da.eventScheduleEntity.refId],
                                            borderColor: parent.map[da.eventScheduleEntity.refId]
                                        };
                                        parent.$('#calendar').fullCalendar('renderEvent', event, false);
                                        parent.$('#calendar').fullCalendar('refetchEvents', event);
                                        parent.successFlg = true;
                                        parent.layer.close(index);
                                //     }
                                // })
                            } else {
                                parent.layer.alert(data.msg);
                            }
                        }
                    })
                } else {
                    if (vm.weekdayList.length == 0) {
                        parent.layer.alert($.format($.msg.MSGCOMN0050, "曜日"))
                        return;
                    }
                    $.ajax({
                        url: ctxPath + '/pop/F08006/saveGroup',
                        traditional: true,
                        dataType: 'json',
                        type: 'POST',
                        data: {
                            flg: vm.showFlg,
                            eventId: param.eventId,
                            startTimedate: param.date,
                            username: $(".select2-selection__rendered").attr("title"),
                            mentorId: vm.mentorId,
                            personlimit: $("#usernumber").val(),
                            SgdStartDatime: $("#time_picker_begin").val(),
                            SgdEndDatime: $("#time_picker_end").val(),
                            weekdayList: vm.weekdayList,
                            refType: param.refType
                        },
                        success: function (data) {
                            da = data;
                            if (data.code == 0) {
                                // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                        var index = parent.layer.getFrameIndex(window.name);
                                        var event = {
                                            title:da.displayNm,
                                            start : timeStart,
                                            end : timeEnd,
                                            allDay: false,
                                            overlap: true, // 複数不可
                                            className:da.eventScheduleEntity.refId,
                                            scheduleId: da.eventScheduleEntity.id,
                                            userParam: JSON.stringify(da.eventScheduleEntity),
                                            backgroundColor: parent.map[da.eventScheduleEntity.refId],
                                            borderColor: parent.map[da.eventScheduleEntity.refId]
                                        };
                                        parent.$('#calendar').fullCalendar('renderEvent', event, false);
                                        parent.$('#calendar').fullCalendar('refetchEvents', event);
                                        parent.successFlg = true;
                                        parent.layer.close(index);
                                        // parent.location.reload();
                                //     }
                                // })
                            } else {
                                parent.layer.alert(data.msg);
                            }
                        }
                    })
                }
            },
            close: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            getMentor:function () {
                $('#searchUser').select2({
                    ajax: {
                        url: ctxPath + '/pop/F08006/search',
                        type: 'POST',
                        data: function (params) {
                            return {
                                mentorname: params.term.trim()
                            };
                        },
                        delay: 250,
                        dataType: 'json',
                        processResults: function (data) {
                            var results = [];
                            var dataSet = data.mstMentorEntityList;
                            for (var i = 0; i < dataSet.length; i++) {
                                results.push({id: dataSet[i].usrId, text: dataSet[i].mentorName})
                            }
                            return {
                                results: results
                            };
                        },
                        cache: true
                    },
                    language: 'ja',
                    placeholder: '先生を検索',
                    minimumInputLength: 1,
                    templateSelection: stuFormatRepoSelection // omitted for brevity, see the source of this page
                });
            }
        }
    })
;
//対象日付リストを取得する
$(function () {
    $("li").on('click', function () {
        if ($(this).attr('flg') != 1) {
            $(this).css({'color': 'white', 'background-color': 'green'});
            $(this).attr('flg', 1);

            var time = new Date(Date.parse(decodeURIComponent(param.date)));
            time.setDate(time.getDate() + ($(this).val() - getweekDay(decodeURIComponent(param.date))));
            vm.weekdayList.push(time.format("yyyy/MM/dd"));
        } else {
            for (var i = 0; i < vm.weekdayList.length; i++) {
                var time = new Date(Date.parse(decodeURIComponent(param.date)));
                time.setDate(time.getDate() + ($(this).val() - getweekDay(decodeURIComponent(param.date))))
                if (vm.weekdayList[i] == time.format("yyyy/MM/dd")) {
                    vm.weekdayList.splice(i, 1);
                }
            }
            $(this).css({'color': 'green', 'background-color': 'white'});
            $(this).attr('flg', 0);

        }
    });
});
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

//取得された候補者リストを選択できること。
// function search() {
//     $.ajax({
//         url: ctxPath + '/pop/F08006/search',
//         type: 'POST',
//         data: {
//             mentorname: $("#searchUser").val(),
//
//         },
//         success: function (data) {
//             if (data.code == 0) {
//                 vm.mstMentorEntityList = data.mstMentorEntityList;
//             } else {
//                 showMsg(data.msg)
//             }
//         },
//     })
// }

//タイムセレクタ
laydate.render({
    elem: '#time_picker_begin',
    type: 'time',
    format: 'HH:mm',
    trigger: 'click',

    ready: function (date) {

            $("#layui-laydate1").find("ol").eq(1).find("li").each(function (index, element) {
                if ($(this).text() % 5 != 0) {
                    $(this).remove();
                }
            });
    }
});
//タイムセレクタ
laydate.render({
    elem: '#time_picker_end',
    type: 'time',
    format: 'HH:mm',
    trigger: 'click',
    ready: function (date) {
        $("#layui-laydate2").find("ol").eq(1).find("li").each(function (index, element) {
            if ($(this).text() % 5 != 0) {
                $(this).remove();
            }
        });
    }
});

//画面選択の曜日日付を取得します。
function getweekDay(date) {
    var day = new Date(Date.parse(date));
    var weeks = new Array(7, 1, 2, 3, 4, 5, 6);
    var week = weeks[day.getDay()];
    return week;
};

//削除ボダン押下
function del() {
    var index = layer.confirm($.format($.msg.MSGCOMN0114, "削除"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/pop/F08006/delete',
                type: 'POST',
                data: {
                    scheduleId: param.scheduleId
                },
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     shadeClose: false,
                        //     btn: ['確認'],
                        //     btn1: function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                // イベント削除
                                parent.$("#calendar").fullCalendar("removeEvents",parent.eventId);
                                parent.layer.close(index);
                                // parent.location.reload();
                        //     }
                        // })
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        }
    })
};

function stuFormatRepoSelection(repo) {
    if (repo.element) {
        vm.mentorId = repo.id;
    }
    return repo.text;
}
$(function () {
    $("select").change(function () {
        $("#usernumber").css("background-color", "darkgrey");
    });
})





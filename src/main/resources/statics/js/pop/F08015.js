var param = getParam();
//laydate時間のコントロール
$(function () {

    laydate.render({
        elem: '#date',
        type: 'date',
        format: 'yyyy-MM-dd',
        trigger: 'click',
        area: ['100px', '100px'],
        done: function (value, date) {
            $("#date").val(value)
        }
    });
    laydate.render({
        elem: '#startTime',
        type: 'time',
        format: 'HH:mm',
        area: ['100px', '100px'],
        trigger: 'click',
        done: function (value, date) {
            $("#startTime").val(value)
        },
        ready: function (date) {

            $("#layui-laydate2").find("ol").eq(1).find("li").each(function (index, element) {
                if ($(this).text() % 5 != 0) {
                    $(this).remove();
                }
            });
        }
    });
    laydate.render({
        elem: '#endTime',
        type: 'time',
        format: 'HH:mm',
        area: ['100px', '100px'],
        trigger: 'click',
        done: function (value, date) {
            $("#endTime").val(value)
        },
        ready: function (date) {

            $("#layui-laydate3").find("ol").eq(1).find("li").each(function (index, element) {
                if ($(this).text() % 5 != 0) {
                    $(this).remove();
                }
            });
        }
    });
});
var vm = new Vue({
    el: '.main',
    data: {
        pageDiv: '',
        planDate: '',
        startTime: '',
        endTime: '',
        stuList: [],
        eventList: [],
        mentorList: [],
        deliver: '',
        showFlg: '',
        eventId: '',
        eventScheduleId: '',
        eventTitle: ''
    },
    updated: function () {
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        //初期表示
        getInfo: function () {

            $(document).ready(function () {
                $("#stuList").select2({
                    placeholder: "生徒を検索"
                });
                $("#mentorList").select2({
                    placeholder: "先生を検索"
                });
                $("#eventList").select2({
                    placeholder: "イベントを検索"
                });
                $(".select2").css({"width": "60%", "font-size": "4vw", "margin-left": "3vw"});
                // $(".select2").eq(2).css({"height":"5vh"});
                $(".select2").eq(0).css({"margin-top": "4vw", "margin-left": "1vw"});
                $(".select2-selection").css("border", "none");
            });
            $.ajax({
                url: ctxPath + '/pop/F08015/init',
                type: 'GET',
                data: {
                    pageDiv: param.pageDiv,
                    id: param.eventScheduleDelId,
                    geasId: param.geasId,
                    stuName: null,
                    eventTitle: null,
                    mentorName: null,
                    userFlag: param.userFlag
                }, success: function (data) {
                    if (data.code == 0) {
                        if (data.f08015Dto) {
                            vm.eventScheduleId = data.f08015Dto.Id
                        }
                        //引渡データ．画面区分が「2：コマ非表示」「3：先生変更」の場合、
                        if (decodeURIComponent(param.pageDiv) != "1") {
                            if (param.pageDiv == "2") {
                                vm.eventId = data.f08015Dto.id;
                                if (data.f08015Dto.cancelFlg == "0") {
                                    vm.showFlg = 0;
                                } else {
                                    vm.showFlg = 1;
                                }
                            }
                            $("#eventList").attr("disabled", "disabled").css("background", "white");
                            $("#select2-eventList-container").removeAttr("aria-readonly").text(data.eventTitle).val(data.f08015Dto.eventId).trigger("change");
                            $("#date").val(data.f08015Dto.planDate.substring(0, 10));
                            $("#startTime").val(data.f08015Dto.startTime.substring(11, 16)).attr("disabled", "disabled").css("background", "white");
                            $("#endTime").val(data.f08015Dto.endTime.substring(11, 16)).attr("disabled", "disabled").css("background", "white");
                            if (param.pageDiv == "3") {
                                if (data.f08015Dto.cancelFlg == 0) {
                                    vm.showFlg = 0;
                                }
                                $("#stuList").attr("disabled", "disabled").css("background", "white");
                                $("#select2-eventList-container").removeAttr("aria-readonly").text(data.f08015Dto.eventTitle).val(data.f08015Dto.Id).trigger("change");
                                $("#select2-stuList-container").removeAttr("aria-readonly").text(data.f08015Dto.stuName).val(data.f08015Dto.stuId).trigger("change");
                                $("#select2-mentorList-container").removeAttr("aria-readonly").text(data.f08015Dto.displayNm).val(data.f08015Dto.refId).trigger("change");
                            }
                        } else {
                            $(".line").css("display", "block");
                            $(".select2").eq(0).css("margin-left", "10vw");
                        }
                        vm.pageDiv = param.pageDiv;
                        vm.stuList = data.stuList;
                        vm.eventList = data.eventList;
                        vm.mentorList = data.mentorList;
                    } else {
                        vm.showFlg = 4;
                        parent.layer.alert(data.msg);
                        return;
                    }


                }
            })
        },
        //「×」ボダン押下
        close: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    }

});
function eventChange(){
    var eventId = $("#eventList").val();
    $.ajax(
        {
            url: ctxPath + '/pop/F08015/getStuList',
            type: 'GET',
            data: {
                eventId: eventId,
                userFlag: param.userFlag
            },
            success: function (data) {
                vm.stuList = data.stuList;
            }
        }
    )
}
//代理登録ボダン押下
function log() {
    //イベント名（検索）が未入力の場合
    if ($("#select2-eventList-container").text() == null || $("#select2-eventList-container").text() == "" || $("#select2-eventList-container").text() == "イベントを検索") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "イベント名"));
        return;
    }
    //予約日が未入力の場合
    if ($("#date").val() == null || $("#date").val() == "") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "予約日"));
        return;
    }
    //開始時間が未入力の場合
    if ($("#startTime").val() == null || $("#startTime").val() == "") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "開始時間"));
        return;
    }
    //終了時間が未入力の場合
    if ($("#endTime").val() == null || $("#endTime").val() == "") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "終了時間"));
        return;
    }
    //画面．開始時間　＞＝　画面．終了時間の場合
    if ($("#startTime").val() >= $("#endTime").val()) {
        parent.layer.alert($.format($.msg.MSGCOMN0048, "終了時間", "開始時間"));
        return;
    }
    //生徒名（検索条件）が未入力の場合
    if ($("#select2-stuList-container").text() == null || $("#select2-stuList-container").text() == "" || $("#select2-stuList-container").text() == "生徒を検索") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "生徒名"));
        return;
    }
    //先生名（検索条件）が未入力の場合
    if ($("#select2-mentorList-container").text() == null || $("#select2-mentorList-container").text() == "" || $("#select2-mentorList-container").text() == "先生を検索") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "先生名"));
        return;
    }
    var eventId = '';
    var stuId = '';
    var mentorId = '';
    var displayName = '';
    var eventTitle = '';
    var stuName = '';
    if (param.pageDiv == '1') {
        eventId = $("#eventList").val();
        eventTitle = $("#eventList option:checked").text();
        stuId = $("#stuList").val();
        stuName = $("#stuList option:checked").text();
        mentorId = $("#mentorList").val();
        displayName = $("#mentorList option:checked").text();

    }
    if (param.pageDiv == '3') {
        eventId = $("#select2-eventList-container").val();
        eventTitle = $("#select2-eventList-container").text();
        stuId = $("#select2-stuList-container").val();
        stuName = $("#select2-stuList-container").text();
        mentorId = $("#mentorList").val();
        displayName = $("#select2-mentorList-container").text();
    }
    if (param.pageDiv == '2') {
        eventId = $("#select2-eventList-container").val();
        eventTitle = $("#select2-eventList-container").text();
        stuId = $("#stuList").val();
        stuName = $("#stuList option:checked").text();
        mentorId = $("#mentorList").val();
        displayName = $("#mentorList option:checked").text();

    }
    $.ajax({
        url: ctxPath + '/pop/F08015/log',
        type: 'GET',
        data: {
            eventTitle: eventTitle,
            stuName: stuName,
            displayName: displayName,
            planDate: $("#date").val(),
            startTime: $("#date").val() + ' ' + $("#startTime").val() + ":00",
            endTime: $("#date").val() + ' ' + $("#endTime").val() + ":00",
            pageDiv: param.pageDiv,
            eventId: eventId,
            stuId: stuId,
            refId: mentorId,
            geasId: param.geasId,
            eventScheId: param.eventScheduleDelId,
            userFlag: param.userFlag
        }, success: function (data) {
            if (data.code == 0) {
                // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                //     title: '確認',
                //     closeBtn: 0,
                //     shadeClose: false,
                //     btn: ['確認'],
                //     btn1: function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.location.reload();
                //     }
                // })
            } else {
                parent.layer.alert(data.msg);
            }
        }
    })

}

//コマ表示ボダン,コマ非表示ボダン押下の場合
function unit(flg) {
    $.ajax({
        url: ctxPath + '/pop/F08015/unit',
        type: 'GET',
        data: {
            id: vm.eventScheduleId,
            flg: flg
        },
        success: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.location.reload();
        }
    })
}
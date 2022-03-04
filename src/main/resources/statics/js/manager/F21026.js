var param = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        stu: '',
        goSchPoint: '',
        years:[],
        nowYear: new Date().getFullYear()
    },
    mounted: function () {
        if(getCookie("equipment") === "phone"){
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21026.css"}).appendTo("head");
        }else {
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21026-1.css"}).appendTo("head");
        }
        this.nowTime();
        this.timeSelect();
        this.init();
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/manager/F21026/init',
                type: 'get',
                data: {
                    stuId: param.stuId
                },
                success: function (data) {
                    data.stu.userSts = data.stu.userSts == '登校' ? '入室中' : '未入室';
                    data.stu.schy = (data.stu.schy).substring(0, 1) + (data.stu.schy).substring(2, 3);
                    vm.stu = data.stu;
                    if (data.mstVariousSetEntity) {
                        vm.goSchPoint = data.mstVariousSetEntity.goSchPoint;
                    }
                    var year = new Date().getFullYear();
                    year = year - 30;
                    for (var i = 0; i < 60; i++) {
                        vm.years.push(year);
                        year = year + 1;
                    }
                }
            });
        },
        submit: function () {
            var time = $('.selectBtn').val() + $('#starTime').text();
            // time = new Date(time);
            if (vm.stu.guardId == null) {
                var idx = layer.confirm($.msg.MSGCOMN0151, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['キャンセル', '確認'],
                    btn1: function () {
                        layer.close(idx);
                    },
                    btn2: function () {
                        var date = new Date().Format("yyyy/MM/dd HH:mm");
                        $.ajax({
                            url: ctxPath + '/manager/F21026/submit',
                            type: 'post',
                            data: {
                                stuId: param.stuId,
                                flg: $(".active").val(),
                                date: time === '' ? date : time,
                                guardId: vm.stu.guardId,
                                stuName: vm.stu.stuNm,
                                point: vm.stu.point,
                                goSchPoint: vm.goSchPoint
                            },
                            success: function (data) {
                                if (data.code != 0) {
                                    $('.td').css("background", 'red').css('color', 'white');
                                    var srcWidth = $(window).width() * 0.7;
                                    var srcHeight = $(window).height() * 0.4;
                                    var index = layer.confirm(data.msg, {
                                        skin: 'layui-layer-molv',
                                        title: '確認',
                                        area: [srcWidth, srcHeight],
                                        closeBtn: 0,
                                        anim: -1,
                                        btn: ['確認'],
                                        btn1: function () {
                                            layer.close(index);
                                        }
                                    });
                                } else {
                                    // var index = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                    //     skin: 'layui-layer-molv',
                                    //     title: '確認',
                                    //     area: [srcWidth, srcHeight],
                                    //     closeBtn: 0,
                                    //     anim: -1,
                                    //     btn: ['確認'],
                                    //     btn1: function () {
                                    //         layer.close(index);
                                            window.location.href = '../manager/F21017.html';
                                    //     }
                                    // });
                                }
                            }
                        });
                    }
                });
            } else {
                var date = new Date().Format("yyyy/MM/dd HH:mm");
                $.ajax({
                    url: ctxPath + '/manager/F21026/submit',
                    type: 'post',
                    data: {
                        stuId: param.stuId,
                        flg: $(".active").val(),
                        date: time === '' ? date : time,
                        guardId: vm.stu.guardId,
                        stuName: vm.stu.stuNm,
                        point: vm.stu.point,
                        goSchPoint: vm.goSchPoint
                    },
                    success: function (data) {
                        if (data.code != 0) {
                            $('.td').css("background", 'red').css('color', 'white');
                            var srcWidth = $(window).width() * 0.7;
                            var srcHeight = $(window).height() * 0.4;
                            var index = layer.confirm(data.msg, {
                                skin: 'layui-layer-molv',
                                title: '確認',
                                area: [srcWidth, srcHeight],
                                closeBtn: 0,
                                anim: -1,
                                btn: ['確認'],
                                btn1: function () {
                                    layer.close(index);
                                }
                            });
                        } else {
                            // var index = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                            //     skin: 'layui-layer-molv',
                            //     title: '確認',
                            //     area: [srcWidth, srcHeight],
                            //     closeBtn: 0,
                            //     anim: -1,
                            //     btn: ['確認'],
                            //     btn1: function () {
                            //         layer.close(index);
                                //put->メッセージ送信（messageSend）
                                window.location.href = 'F21017.html';
                            //     }
                            // });
                        }
                    }
                });
            }

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
        starTime: function () {
            $(".check_starTime").toggleClass("disNone");
            if (!$(".check_sub").hasClass("disNone")) {
                $(".check_sub").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
        },
        nowTime: function () {
            var date;
            if ($(".selectBtn").val()==null||$(".selectBtn").val()==new Date().getFullYear().toString()){
                date = new Date();
            }else {
                date = new Date($(".selectBtn").val());
            }
            a = date.Format('/' + "MM" + "/" + "dd" + " " + "HH:mm");
            $("#starTime").html(a);
            createSelect(date);
            build();
        }
    }
});
$(function () {
    //登校
    $("#logIn").click(function () {
        $(this).addClass("active");
        $("#logOut").removeClass("active");
    });
    //下校
    $("#logOut").click(function () {
        $(this).addClass("active");
        $("#logIn").removeClass("active");
    });
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
            // var weekday = getDay(new Date(_year, (month - 1), day));
            var selected = '';
            var text = month + "/" + day + " " ;
            // + weekday;
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

function mGetDate(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}

function toF21018() {
    window.location.href = '../manager/F21018.html?stuId=' + param.stuId;
}
function build(){
    $('#dom_starTime').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var date =dateTime.toString().split('/');
            var month = date[0].trim();
            var day = date[1].trim();
            var text = $("#starTime").html();
            if (month<10){
                month="0"+month;
            }
            if (day<10){
                day="0"+day;
            }
            text = text.replace(/\d{1,2}\/\d{1,2} /, month + '/' + day + " ");
            $("#starTime").html(text);
        },
    });
    $('#dom_starTime1').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
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
        rows: 3,
        formatResult:function(dateTime){
            var day = dateTime.toString().trim();
            var text = $("#starTime").html();
            text = text.replace(/:\d{1,2}/,  ':'+day);
            $("#starTime").html(text);
        },
        onValueTap: function (event, inst) {
            vm.starTime();
        }
    });
}
function  hidden() {
    if (!$(".check_starTime").hasClass("disNone")) {
        $(".check_starTime").addClass("disNone")
    }
    if (!$(".check_time").hasClass("disNone")) {
        $(".check_time").addClass("disNone")
    }
}
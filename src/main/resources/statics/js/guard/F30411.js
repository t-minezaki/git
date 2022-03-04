var param = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        content: '',
        reasons: [],
        contents: [],
        years: [],
        contentVal: '',
        dayVal: '',
        reasonVal: '',
        remarkVal: '',
        orgAddId:''
    },
    mounted: function () {
        $.ajax({
            url: ctxPath + '/common/F40004/getOrg',
            type: 'GET',
            dataType: "json",
            success: function (data){
                vm.orgAddId = data.orgIdAdd;
            }
        });
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
        this.nowTime();
        this.timeSelect();
        this.setUp();
    },
    methods: {
        //初期化
        setUp: function () {
            $.ajax({
                url: ctxPath + '/guard/F30411/init',
                type: 'GET',
                dataType: "json",
                success: function (data) {
                    if (data.reasons) {
                        vm.reasons = data.reasons;
                    }
                    if (data.contents) {
                        vm.contents = data.contents;
                    }
                    var year = new Date().getFullYear();
                    for (var i = 0; i < 31; i++) {
                        vm.years.push(year);
                        year = year + 1;
                    }
                }
            });
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
        },
        getId: function (index) {
            // 2021/02/25 manamiri1-526 cuikailin start
            return index == 0 ?  'change': 'absence';
            // 2021/02/25 manamiri1-526 cuikailin end
        },
        starTime: function () {
            $(".check_starTime").toggleClass("disNone");
            if ($('#change').hasClass('active') ? false : true){
                $(".dw-select")[0].style.visibility="hidden";
                $(".dw-select")[1].style.visibility="hidden";
            }else {
                $(".dw-select")[0].style.visibility="unset";
                $(".dw-select")[1].style.visibility="unset";
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        timeSelect: function () {
            var time = "";
            var min = "";
            for (var i = 15; i <= 720; i += 15) {
                var m = i % 60 + "分";

                min += "<option value='" + i + "'>" + m  +"</option>";
            }
            for (var i = 0; i <12 ; i++) {
                time += "<option value='" + i + "'>" + i + "時間" +"</option>";
            }
            $('#demo_select').html(time);
            $('#demo_select1').html(min);
            $('#demo_select').find("option").each(function () {
                if ($(this).val() == 30) {
                    $(this).attr("selected", true);
                }
            })
            $('#demo_select1').find("option").each(function () {
                if ($(this).val() == 30) {
                    $(this).attr("selected", true);
                }
            })
            $("#time").html("0時間30分");
        }
    }
});

//戻るボタン押下時
function back() {
    window.location.href = getCookie("brandcd") === vm.orgAddId?'F30421.html':'F30112.html';
}

// 登録ボタン押下時
function submit() {
    //NWT 李 MANAMIRU1-640 start
    vm.contentVal = $('#change').hasClass('active') ? '0' : '1';
    //NWT 李 MANAMIRU1-640 end
    var startTime = new Date($(".selectBtn").val() +  $("#starTime").html()).Format("yyyy/MM/dd HH:mm");
    if ($('#reason').val()==""||$('#reason').val()==null) {
        vm.reasonVal=""
    }else {
        vm.reasonVal = $('#reason').val();
    }
    if (vm.reasonVal == null || vm.reasonVal == ''){
        var idx = layer.confirm($.format($.msg.MSGCOMN0143, '理由'), {
            skin: 'layui-layer-molv out',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            yes: function () {
                layer.close(idx);  // 关闭layer
            }
        })
        return;
    }
    vm.remarkVal = $('#remark').val();
    $.ajax({
        url: ctxPath + '/guard/F30411/submit',
        type: 'GET',
        data: {
            type: vm.contentVal,
            lateDay: startTime,
            reason: vm.reasonVal,
            remark: vm.remarkVal
        },
        dataType: "json",
        success: function (data) {
            var idx;
            if (data.code != 0) {
                idx = layer.confirm(data.msg, {
                    skin: 'layui-layer-molv out',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        layer.close(idx);
                    }
                });
            } else {
                idx = layer.confirm('遅刻・欠席連絡情報の登録処理が完了しました。', {
                    skin: 'layui-layer-molv out info',
                    title: '登録完了',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認する'],
                    yes: function () {
                        layer.close(idx);
                        window.location.href = getCookie("brandcd") === vm.orgAddId?'F30421.html':'F30112.html';
                    }
                });
            }

        }
    });
}

function build(){
    $('#dom_starTime').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 1,
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
        rows: 1,
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
        rows: 1,
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

function mGetDate(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}



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
var earlyTime = '';
var earlyDate = '';
var frist = 0 ;
function  hidden(val) {
    var  flg = $('#change').hasClass('active') ? false : true;
    if (!$(".check_starTime").hasClass("disNone")) {
        $(".check_starTime").addClass("disNone")
    }
    if (frist==0){
        earlyTime = $("#starTime").text().split(" ")[1];
        frist=1;
    }
    earlyDate = $("#starTime").text().substring(0,7);
    if (val!=0){
        if (flg){
            earlyTime = $("#starTime").text().split(" ")[1];
            $("#starTime").html(earlyDate);
        }else {
            $("#starTime").html(earlyDate + earlyTime);
        }
    }
}

function choose(e) {
    //コンテンツの切り替え
    if ($(e).attr('id') == 'change') {
        $(e).addClass("active");
        $('#absence').removeClass("active");
    } else {
        $(e).addClass("active");
        $('#change').removeClass("active");
    }
}

function setHundredPercentHeight(objId, containerId) {
    var length = arguments.length;
    var height = 0;
    for (var i = 2; i < length; i++) {
        height += $("#" + arguments[i]).outerHeight(true);
    }
    var parentHeight = $("#" + containerId).height();
    $("#" + objId).height(parentHeight - height);
}
var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});
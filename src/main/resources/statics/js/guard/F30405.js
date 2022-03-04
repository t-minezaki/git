
var dataFlg = 0;
var url = window.location.href;
var param = getParam();
var weelkHtml = '';
var monthhtml = '';
var myDate = new Date();
var myYear = myDate.getFullYear(), myMonth = myDate.getMonth() + 1, myDay = myDate.getDate(),
    confimMonth = myDate.getMonth() + 1, confimYear = myDate.getFullYear();
var nowMonth = myDate.getMonth();
var nowWeek = new Date(myYear, nowMonth, myDay).getDay();

var weekday = getDay(new Date(myYear, myMonth-1, myDay));
var strss = myYear + '年' + myMonth + '月' + myDay + '日' + '(' + weekday + ')';
$(".time_line").find("p").eq(0).text(strss);


var closest = '';
var vm = new Vue({
    el: "#f30405",
    data: {
        eventTitle:'',
        applylist: [],
        f30405Dto: '',
        timeList: [],
        pageSts: "",
        minDate: '',
        askItems: [],
        talkRecordHEntity: "",
        talkRecordDEntities: [],
        base: {},
        refType: '1',
        entryDateTime: new Date()
    },
    updated:function() {
        if (/(?:iPhone)/.test(navigator.userAgent)) {
            $(".result_check").find("input[type = 'checkbox']").css("width","20px").css("height", "20px");
            $(".result_check").find("span").css("vertical-align","1%");
        }
    },
    mounted: function () {
        // 2020/12/1 huangxinliang modify start
        $(".calendar_div").css("pointer-events","none");
        this.showPage1();
        // 2020/12/1 huangxinliang modify end
        $.ajax({
            url: ctxPath + '/guard/F30405/getMin',
            type: 'GET',
            data: {
                eventId: param.eventId
            },
            dataType: "json",
            success: function (data) {
                if (data.minDate) {
                    vm.minDate = data.minDate
                    closest = data.minDate;
                    calendar(parseInt(closest.split("-")[0]), parseInt(closest.split("-")[1]));
                    var allP = $(".date_content").children();
                    allP.removeClass("Pback");
                    if (parseInt(myYear) === parseInt(closest.split("-")[0]) && (new Date().getMonth() + 1) === parseInt(closest.split("-")[1])) {
                        allP.eq(parseInt(myDay)).addClass("Pback");
                        var yobi = getDay(new Date(parseInt(closest.split("-")[0]), parseInt(closest.split("-")[1]) - 1, parseInt(myDay)));
                        var timeP = parseInt(closest.split("-")[0]) + '年' + parseInt(closest.split("-")[1]) + '月' + parseInt(myDay) + '日' + '(' + yobi + ')';
                        $(".time_line").find("p").eq(0).text(timeP);
                    } else {
                        allP.eq(1).addClass("Pback");
                        var yobi = getDay(new Date(parseInt(closest.split("-")[0]), parseInt(closest.split("-")[1]) - 1, 1));
                        var timeP = parseInt(closest.split("-")[0]) + '年' + parseInt(closest.split("-")[1]) + '月' + '1日' + '(' + yobi + ')';
                        $(".time_line").find("p").eq(0).text(timeP);
                    }
                    vm.showData(closest);
                } else {
                    calendar();
                    vm.showData();
                }
            }
        })
        //2020/11/12 huangxinliang modify start
        $('.image-container').click(function () {
            var img = $(".image-container").find('img')[0];
            var index = $(img).attr('id').substring(5);
            $(".image-container").fadeOut();
            $(img).addClass('photo-image');
            if (vm.pageSts === '3'){
                $(".result_cont").eq(index).html($(img))
            }else {
                $('#photoContainer' + index).html($(img));
            }
        });
        //2020/11/12 huangxinliang modify end
    },
    methods: {
        showData: function (closest) {
            var tgtYmd = '';
            if (closest){
                tgtYmd = closest.split(" ")[0].split("-")[0] + "-" + closest.split(" ")[0].split("-")[1] + '-' + "01"
            } else {
                tgtYmd = myYear + '-' + myMonth + '-' + myDay;
            }
            $.ajax({
                url: ctxPath + '/guard/F30405/init',
                type: 'GET',
                data: {
                    eventId: param.eventId,
                    applyId: param.applyId,
                    tgtYmd: tgtYmd,
                    firstFlag: true
                },
                // 2020/12/1 huangxinliang modify start
                async: false,
                // 2020/12/1 huangxinliang modify end
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        layer.alert(data.msg);
                    } else {
                        if (data.eventTitle) {
                            vm.eventTitle = data.eventTitle;
                        }
                        if (data.applylist) {
                            vm.applylist = data.applylist;
                            if (vm.applylist.length != 0) {
                                for (var i = 0; i < vm.applylist.length; i++) {
                                    if (vm.applylist[i].flag == '0') {
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events","auto");
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6 style='color: red'>〇</h6>");
                                    }
                                    if (vm.applylist[i].flag == '1') {
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6>✖</h6>");
                                    }
                                }
                            }
                        }
                        if (data.f30405Dto) {
                            vm.f30405Dto = data.f30405Dto;
                        }
                        if (data.timeList) {
                            vm.timeList = data.timeList;
                            var timeHtml = "";
                            for (var i = 0; i < vm.timeList.length; i++) {
                                timeHtml += '<li style="width: 30%" class="timelist">' + vm.timeList[i].timeLine + '</li>';
                            }
                            $(".time_line").find("ul").eq(0).html(timeHtml);
                        }
                        if (data.askItems) {
                            vm.askItems = data.askItems;
                        }
                        if (data.talkRecordHEntity) {
                            vm.talkRecordHEntity = data.talkRecordHEntity
                        }
                        if (data.talkRecordDEntities) {
                            vm.talkRecordDEntities = data.talkRecordDEntities
                        }
                        // 2020/12/1 huangxinliang modify start
                        if (data.refType){
                            vm.refType = data.refType;
                            if (data.refType === '2'){
                                vm.showPage2();
                            }
                        }
                        // 2020/12/1 huangxinliang modify end
                    }
                }
            });
        },
        //2020/11/12 huangxinliang modify start
        showImg: function (index) {
            var container = $("#imageContainer");
            container.html($('#image' + index));
            $('#image' + index).removeClass('photo-image');
            container.fadeIn();
        },
        //2020/11/12 huangxinliang modify end
        // 2020/12/1 huangxinliang modify start
        showPage1: function (){
            this.pageSts = '1';
            $(".time_tab").find("button").eq(0).addClass("active");
            $(".time_tab").find("button").eq(1).removeClass("active");
            $(".time_tab").find("button").eq(2).removeClass("active");
            $(".sche_select").show();
            $(".confirm_area").hide();
            $(".confirm_page").hide();
            $("#top_msg").text("日程を選択してください");
        },
        showPage2: function (){
            this.pageSts = '2';
            $(".time_tab").find("button").eq(0).removeClass("active");
            $(".time_tab").find("button").eq(1).addClass("active");
            $(".time_tab").find("button").eq(2).removeClass("active");
            $(".sche_select").hide();
            $(".confirm_area").show();
            $(".confirm_page").hide();
            $("#top_msg").html("ご要望ご不明点があれば入力してください。");
        },
        showPage3: function (){
            this.pageSts = "3";
            $(".time_tab").find("button").eq(0).removeClass("active");
            $(".time_tab").find("button").eq(1).removeClass("active");
            $(".time_tab").find("button").eq(2).addClass("active");
            $(".sche_select").hide();
            $(".confirm_area").hide();
            $(".confirm_page").show();
            $("#top_msg").html("申込内容");
        }
        // 2020/12/1 huangxinliang modify end
    }
});

/**
 * 日付クリック
 * @type {*|jQuery}
 */
var rcjk = /[\u2E80-\u2EFF\u2F00-\u2FDF\u3000-\u303F\u31C0-\u31EF\u3200-\u32FF\u3300-\u33FF\u3400-\u4DBF\u4DC0-\u4DFF\u4E00-\u9FBF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF]+/g;
$(".date_content").on('click', '.calendar_div', function () {
    var allP = $(".date_content").children();
    allP.removeClass("Pback");
    $(this).addClass("Pback");
    var sDay = $(this).find('p').text();
    var selectMonth = confimMonth + 1;
    var current = $(".date_title").find("p").html();
    if (selectMonth < 10){
        selectMonth = '0' + selectMonth
    }
    nowWeek = new Date(myYear, nowMonth, sDay).getDay();
    dataFlg = 1;
    $(".time_line").find("p").eq(0).text(current + sDay + '日'+ '(' + getDay(new Date(myYear, nowMonth, sDay)) + ')' );
    var selectss = current + sDay + '日';
    selectss = selectss.replace(rcjk, "-");
    selectss = selectss.substring(0, selectss.lastIndexOf("-"));
    $.ajax({
        url: ctxPath + '/guard/F30405/init',
        type: 'GET',
        data: {
            eventId: param.eventId,
            applyId: param.applyId,
            tgtYmd: selectss
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                if (data.timeList) {
                    vm.timeList = data.timeList;
                    $(".time_line").find("li").remove();
                    var timeHtml = "";
                    for (var i = 0; i < vm.timeList.length; i++) {
                        timeHtml += '<li style="width: 30%" class="timelist">' + vm.timeList[i].timeLine + '</li>';
                    }
                    $(".time_line").find("ul").eq(0).html(timeHtml);
                }
            }
        }
    });
})

var scheDel = '';
var scheDelId = '';
$(".time_line").on('click','.timelist',function () {
    $.ajax({
        url: ctxPath + '/guard/F30405/timeselect',
        type: 'GET',
        data: {
            eventId: param.eventId,
            applyId: param.applyId,
            tgtYmd: $(".time_line").find("p").html(),
            timeStr: $(this).html()
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                if (data.selectTime) {
                    scheDel = data.selectTime;
                    scheDelId = data.selectTime.id;
                    // 2020/12/1 huangxinliang modify start
                    vm.showPage2();
                    // 2020/12/1 huangxinliang modify end
                }
            }
        }
    });
})

// タイムフォーマット変換
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth()+3)/3),
        "S"  : this.getMilliseconds()
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
//内容を確認ボダン押下
$("#cnt_confirm").click(function () {
    // 2020/12/1 huangxinliang modify start
    vm.showPage3();
    // 2020/12/1 huangxinliang modify end
    $("#confirm_msg").html("確認事項");
    $("#stu_name").text(vm.f30405Dto.flnmNm + ' ' + vm.f30405Dto.flnmLnm);
    if (vm.refType !== '2') {
        $("#plan_time").text($(".time_line").find("p").html().split("年")[1] + new Date(scheDel.sgdStartDatime.replace(/\-/g, "/")).format('hh:mm') + '～');
    }else {
        $("#plan_time").text(vm.entryDateTime.format('yyyy/MM/dd hh:mm') + '～');
    }
    var num = 0;
    for (var i = 0; i < vm.askItems.length; i++) {
        var paramObj = '';
        if (vm.askItems[i].answerTypeDiv == '0') {
            paramObj = $(".result_div").eq(i).find("textarea").eq(0).val();
        }
        if (vm.askItems[i].answerTypeDiv == '1') {
            paramObj = $(".result_div").eq(i).find("select").eq(0).find("option:selected").html();
        }
        if (vm.askItems[i].answerTypeDiv == '2') {
            $(".result_check").eq(num).find("input:checked").each(function (index, element) {
                if (index == $(".result_check").eq(num).find("input:checked").length - 1) {
                    paramObj += $(this).next("span").html();
                } else {
                    paramObj += $(this).next("span").html() + ',';
                }
            });
            num++;
        }
        $(".result_cont").eq(i).text(paramObj);
        //2020/11/12 huangxinliang modify start
        if (vm.askItems[i].answerTypeDiv === '3'){
            $(".result_cont").eq(i).html($('#image' + i));
        }
        //2020/11/12 huangxinliang modify end
    }
    $("#remarks").text($("#remarkCnt").val());
})
//申込を確定ボダン押下
$("#apply_confirm").click(function () {
    var params = [];
    var formData = new FormData();
    var num = 0;
    for (var i = 0; i < vm.askItems.length; i++) {
        var paramObj = {};
        paramObj.askNum = vm.askItems[i].askNum;
        paramObj.answerTypeDiv = vm.askItems[i].answerTypeDiv;
        paramObj.questionName = vm.askItems[i].questionName;
        paramObj.content = '';
        if (vm.askItems[i].answerTypeDiv == '0') {
            paramObj.content = $(".result_div").eq(i).find("textarea").eq(0).val();
        }
        if (vm.askItems[i].answerTypeDiv == '1') {
            paramObj.content = $(".result_div").eq(i).find("select").eq(0).find("option:selected").html();
        }
        if (vm.askItems[i].answerTypeDiv == '2') {
            $(".result_check").eq(num).find("input:checked").each(function (index, element) {
                if (index == $(".result_check").eq(num).find("input:checked").length - 1) {
                    paramObj.content += $(this).next("span").html();
                } else {
                    paramObj.content += $(this).next("span").html() + ',';
                }
            });
            num++;
        }
        //2020/11/12 huangxinliang modify start
        if (vm.askItems[i].answerTypeDiv === '3') {
            paramObj.content = vm.base['image' + i] ? vm.base['image' + i] : $('#image' + i).attr('src');
        }
        //2020/11/12 huangxinliang modify end
        params.push(paramObj);
    }
    if (vm.talkRecordHEntity){
        formData.append("talkId",vm.talkRecordHEntity.id);
    }
    formData.append("applyId",param.applyId);
    formData.append("eventId",param.eventId);
    formData.append("scheDelId",scheDelId);
    formData.append("noteCont",$('#remarkCnt').val());
    var startTime = scheDel.sgdStartDatime;
    if (vm.refType === '2'){
        startTime = vm.entryDateTime.format('yyyy-MM-dd hh:mm:ss');
    }
    formData.append("sgdStartDatime",startTime);
    formData.append("resultList",JSON.stringify(params));
    $.ajax({
        url: ctxPath + '/guard/F30405/update',
        data: formData,
        type: "POST",
        datatype: "JSON",
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                window.location.href = 'F30408.html?eventId=' + param.eventId
            }
        }
    })
})

//戻るボダン押下
$(".returnBtn").click(function () {
    if (vm.pageSts == "3"){
        for (var i = 0; i <vm.askItems.length ; i++) {
            if (vm.askItems[i].answerTypeDiv === '3'){
                $("#photoContainer" + i).html($('#image' + i));
            }
        }
        // 2020/12/1 huangxinliang modify start
        vm.showPage2();
        // 2020/12/1 huangxinliang modify end
        return;
    }
    if (vm.pageSts == "2"){
        if (vm.refType === '2') {
            window.history.go(-1);
        }else {
            // 2020/12/1 huangxinliang modify start
            vm.showPage1();
            // 2020/12/1 huangxinliang modify end
        }
        return;
    }
})

function getDay(dateStr) {
    if (dateStr == "") {
        return;
    } else {
        var day = new Date(dateStr).getDay(),
            text = "";
        switch (day) {
            case 0:
                text = "日";
                break;
            case 1:
                text = "月";
                break;
            case 2:
                text = "火";
                break;
            case 3:
                text = "水";
                break;
            case 4:
                text = "木";
                break;
            case 5:
                text = "金";
                break;
            case 6:
                text = "土";
                break;
        }
        return text;
    }
}

//上側カレンダー
function calendar(initYear, initMonth) {
    if (initYear && initMonth) {
        myYear = initYear;
        myMonth = initMonth;
    }
    var weekMonth = myMonth - 1;
    var myMonths = myMonth + '月';
    $(".date_title").find("p").html(myYear + '年' + myMonths);
    var weekArray = ['月', '火', '水', '木', '金', '土', '日'];
    for (var i = 0; i < weekArray.length; i++) {
        weelkHtml += '<p>' + weekArray[i] + '</p>';
    }
    $(".date_week").html(weelkHtml);
    $(".date_week").children().eq(6).addClass("pColorGrey");
    $(".date_week").children().eq(5).addClass("pColorGrey");

    var firstWeek = new Date(myYear, weekMonth, '01').getDay() - 1;
    if (firstWeek < 0) {
        firstWeek += 7;
    }
    var lenwidth = firstWeek * 100 / 7 + '%';
    $(".control").css("width", lenwidth);
    var ssDay = new Date(myYear, weekMonth + 1, "01").getTime();
    var _day = new Date(ssDay - 1000 * 60 * 60 * 24).getDate();
    var lastweek = new Date(myYear, weekMonth, _day).getDay();
    var _lenwidth = (7 - lastweek) * 100 / 7 + '%';
    $(".last_days").css("width", _lenwidth);

    function getMonthLength(date) {
        var d = new Date(date);
        d.setMonth(d.getMonth() + 1);
        d.setDate('1');
        d.setDate(d.getDate() - 1);
        return d.getDate();
    }

    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    if (!isiOS) {
        var daytime = myYear + '-' + myMonth + '-' + '01';
        var alldays = getMonthLength(daytime) + 1;
    } else {
        var alldays = new Date(myYear, myMonth, 0).getDate() + 1;
    }
    for (var i = 1; i < alldays; i++) {
        monthhtml += '<div class="calendar_div">' +
            '<p>' + i + '</p>' +
            '<h6>―</h6>' +
            '</div>';
    }
    var myMontht = myMonth;
    if (myMontht < 10) {
        myMontht = '0' + myMontht;
    }
    $(".control").after(monthhtml);

    var allP = $(".date_content").children();
    if (myMonth == confimMonth && myYear == confimYear) {
        allP.eq(myDay).addClass("Pback");
    }
    var alsoDay = alldays - 8 + firstWeek;
    var allweekday = Math.ceil(alsoDay / 7);
    if (firstWeek == '0') {
        allP.eq(5).addClass("pColorGrey");
        allP.eq(6).addClass("pColorGrey");
    }
    var Dw = 1 - firstWeek - 1;
    for (var i = 0; i < allweekday; i++) {
        var Dw = Dw + 7;
        allP.eq(Dw).addClass("pColorGrey");
    }
    var allstaDay = Math.floor(alsoDay / 7);
    var fitststaDay = 7 - firstWeek - 1;
    allP.eq(fitststaDay).addClass("pColorGrey");
    for (var i = 0; i < allstaDay; i++) {
        var fitststaDay = fitststaDay + 7;
        allP.eq(fitststaDay).addClass("pColorGrey");
    }
    if ((7 - lastweek) == 1) {
        allP.eq(allP.length - (7 - lastweek) - 1).addClass("pColorGrey");
    }
    if (lastweek == 0) {
        allP.eq(allP.length - 1 - 1).addClass("pColorGrey");
    }
}

$(".t_past").click(function () {
    xRight();
    nowMonth = nowMonth - 1 === -1? 11 : nowMonth - 1;
});
$(".t_future").click(function () {
    xLeft();
    nowMonth = nowMonth + 1 === 12? 0: nowMonth + 1;
});

//先月
function xRight() {
    myMonth = myMonth - 1;
    weelkHtml = '';
    monthhtml = '';
    $(".date_content").find(".calendar_div").remove();
    $(".control").css("width", "0");
    if (myMonth == 0) {
        myMonth = 12;
        myYear = myYear - 1;
    }
    /**
     * 月変更処理
     * @type {string}
     */
    dataFlg = 1;
    calendar();
    $(".calendar_div").css("pointer-events","none");
    var allP = $(".date_content").children();
    allP.removeClass("Pback");
    allP.eq(1).addClass("Pback");
    nowWeek = getDay(new Date(myYear, myMonth - 1, 1));
    var current = $(".date_title").find("p").html();
    $(".time_line").find("p").eq(0).text(current + '1' + '日'+ '(' + nowWeek + ')');
    $.ajax({
        url: ctxPath + '/guard/F30405/init',
        type: 'GET',
        data: {
            eventId: param.eventId,
            applyId: param.applyId,
            tgtYmd: myYear + '-' + myMonth + '-' + '01'
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                if (data.applylist) {
                    vm.applylist = data.applylist;
                    if (vm.applylist.length != 0) {
                        for (var i = 0; i < vm.applylist.length; i++) {
                            if (vm.applylist[i].flag == '0') {
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events","auto");
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6 style='color: red'>〇</h6>");
                            }
                            if (vm.applylist[i].flag == '1') {
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6>✖</h6>");
                            }
                        }
                    }
                }
                if (data.timeList) {
                    vm.timeList = data.timeList;
                    $(".time_line").find("li").remove();
                    var timeHtml = "";
                    for (var i = 0; i < vm.timeList.length; i++) {
                        timeHtml += '<li style="width: 30%" class="timelist">' + vm.timeList[i].timeLine + '</li>';
                    }
                    $(".time_line").find("ul").eq(0).html(timeHtml);
                }
            }
        }
    });
}

//来月
function xLeft() {
    myMonth = myMonth + 1;
    weelkHtml = '';
    monthhtml = '';
    $(".date_content").find(".calendar_div").remove();
    $(".control").css("width", "0");
    if (myMonth == 13) {
        myMonth = 1;
        myYear = myYear + 1;
    }
    /**
     * 月変更処理
     * @type {string}
     */
    dataFlg = 1;
    calendar();
    $(".calendar_div").css("pointer-events","none");
    var allP = $(".date_content").children();
    allP.removeClass("Pback");
    allP.eq(1).addClass("Pback");
    nowWeek = getDay(new Date(myYear, myMonth - 1, 1));
    var current = $(".date_title").find("p").html();
    $(".time_line").find("p").eq(0).text(current + '1' + '日' +'('+ nowWeek+')');
    $.ajax({
        url: ctxPath + '/guard/F30405/init',
        type: 'GET',
        data: {
            eventId: param.eventId,
            applyId: param.applyId,
            tgtYmd: myYear + '-' + myMonth + '-' + '01'
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                if (data.applylist) {
                    vm.applylist = data.applylist;
                    if (vm.applylist.length != 0) {
                        for (var i = 0; i < vm.applylist.length; i++) {
                            if (vm.applylist[i].flag == '0') {
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events","auto");
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6 style='color: red'>〇</h6>");
                            }
                            if (vm.applylist[i].flag == '1') {
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6>✖</h6>");
                            }
                        }
                    }
                }
                if (data.timeList) {
                    vm.timeList = data.timeList;
                    $(".time_line").find("li").remove();
                    var timeHtml = "";
                    for (var i = 0; i < vm.timeList.length; i++) {
                        timeHtml += '<li style="width: 30%" class="timelist">' + vm.timeList[i].timeLine + '</li>';
                    }
                    $(".time_line").find("ul").eq(0).html(timeHtml);
                }
            }
        }
    });
}
function back() {
    window.history.go(-1);
    // if (param.flg == 0) {
    //     window.location.href = './F30402.html?eventId=' + param.eventId;
    // }
    // if (param.flg == 1) {
    //     window.location.href = './F30409.html';
    // }
}
//2020/11/12 huangxinliang modify start
//選択したアバターを表示
function changepic(index) {
    var f = document.getElementById('addPhoto' + index).files[0];
    if (f) {
        var src = window.URL.createObjectURL(new Blob([f], {type: 'image/*'}));
        // 读取图片元数据：方向
        var orient;
        var image = new Image();
        EXIF.getData(f, function () {
            EXIF.getAllTags(this);
            orient = EXIF.getTag(f, 'Orientation') ? EXIF.getTag(f, 'Orientation') : 1;
            var base64 = '';
            var reader = new FileReader();
            var ratio;
            reader.onload = function () {
                image.src = src;
                image.onload = function () {
                    var canvas = document.createElement("canvas");
                    var ctx = canvas.getContext("2d");
                    if ((ratio = image.width * image.height / 4000000)>1) {
                        ratio = ratio * 5;
                        image.width /= ratio;
                        image.height /= ratio;
                    }else {
                        ratio = 1;
                    }
                    // newImage変換された画像ですか：
                    // var newWidth=0,newHeight=0;
                    // var imgRatio;
                    // if (image.height>=image.width){
                    //     newHeight=image.width;
                    //     newWidth=image.width;
                    //     imgRatio= image.width / image.height;
                    // }else{
                    //     newHeight=image.height;
                    //     newWidth=image.height;
                    //     imgRatio=image.height/image.width;
                    // }
                    var newImage = cropImage(image, {
                        width:image.width,
                        height:image.height
                    });
                    changeCanvas(canvas, image.width, image.height, orient);
                    // 调整图片方向问题
                    adjustImgOrientation(ctx, this, orient, image.width, image.height);
                    base64 = newImage;
                    vm.base['image' + index] = base64.split(',')[1];
                    $('#image' + index).attr('src', base64).css("visibility","unset");
                };

            };
            reader.readAsDataURL(f);
        });
    }
}

// センタークロップ
function cropImage(img, ops){
    // 画像の元のサイズ；
    var imgOriginWidth = img.width,
        imgOriginHeight = img.height;
    // 画像が変形しないようにするための画像のアスペクト比；
    /*let imgRatio;

     if (imgOriginHeight>=imgOriginWidth){
     imgRatio= imgOriginWidth / imgOriginHeight;
     }else{
     imgRatio=imgOriginHeight/imgOriginWidth;
     }*/

    // トリミング後の画像の幅と高さ。デフォルト値は元の画像の幅と高さです。；
    var imgCropedWidth = ops.width || imgOriginWidth,
        imgCropedHeight = ops.height || imgOriginHeight;
    // 開始座標点のオフセットを計算し、, ；
    var dx = (imgCropedWidth - imgOriginWidth) / 2,
        dy = (imgCropedHeight - imgOriginHeight) / 2;
    // let dx = ( imgOriginWidth- imgCropedWidth) / 2,
    //     dy = (imgCropedHeight - imgOriginHeight) / 2;

    // キャンバスを作成し、トリミングされた幅と高さにキャンバスを設定します；
    var cvs = document.createElement('canvas');
    var ctx = cvs.getContext('2d');
    cvs.width = imgCropedWidth;
    cvs.height = imgCropedHeight;
    // 絵を描いてエクスポートする；

    var imgRatio;
    if (imgOriginHeight>=imgOriginWidth){
        imgRatio= imgOriginWidth / imgOriginHeight;
        ctx.drawImage(img, dx, dy, imgCropedWidth, imgCropedWidth / imgRatio);
    }else{
        imgRatio=imgOriginHeight/imgOriginWidth;
        ctx.drawImage(img, dx, dy,  imgCropedWidth / imgRatio,imgCropedWidth);
    }
    // ctx.drawImage(img, dx, dy, imgCropedWidth, imgCropedWidth / imgRatio);
    return cvs.toDataURL('image/jpeg', 0.9);
}

function adjustImgOrientation(ctx, img, orientation, width, height) {
    switch (orientation) {
        case 3:
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 6:
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 8:
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        case 2:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.drawImage(img, 0, 0, width, height);
            break;
        case 4:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 5:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 7:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        default:
            ctx.drawImage(img, 0, 0, width, height);
    }
}
function changeCanvas(canvas,width,height,orientation) {
    switch (orientation) {
        case 6:
            canvas.width = height;
            canvas.height = width;
            break;
        case 8:
            canvas.width = height;
            canvas.height = width;
            break;
        case 5:
            canvas.width = height;
            canvas.height = width;
            break;
        case 7:
            canvas.width = height;
            canvas.height = width;
            break;
        default:
            canvas.width = width;
            canvas.height = height;
    }
}
//2020/11/12 huangxinliang modify end
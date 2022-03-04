var dataFlg = 0;
var url = window.location.href;
var infoList; //
function ColorsClass() {
    this.GREEN = "green";
    this.PINK = "pink";
    this.LEVEL = "level"
}

function EventTypeDiv() {
    this.WEEKLY_PLAN = 'weeklyPlan';
    this.OTHER_PLAN = 'otherPlan';
    this.FIXED_SCHEDULE = 'fixedSchedule'
}

var colors_class = new ColorsClass();
var event_type_div = new EventTypeDiv();
/**
 * ブロック種類区分
 * @constructor
 */
function BlockTypeDiv() {
    this.TYPE_DIV_C1 = "C1"; // 「固定」の場合
    this.TYPE_DIV_O1 = "O1"; // 「その他」の場合
    this.TYPE_DIV_O2 = "O2"; // 「その他」の場合
    this.TYPE_DIV_O3 = "O3"; // 「その他」の場合
    this.TYPE_DIV_V1 = "V1"; // 「塾の宿題」の場合
    this.TYPE_DIV_R1 = "R1"; // 「復習」の場合
    this.TYPE_DIV_W1 = "W1"; // 「学校の宿題」
    this.TYPE_DIV_P1 = "P1"; // 「予習」の場合
    this.TYPE_DIV_S1 = "S1"; // 「学習」の場合
}

//ブロック種類区分
var type_div = new BlockTypeDiv();
/**
 * view data
 * @constructor
 */
function InfoData() {
    this.id = null;
    this.img = null;
    this.text_block = null;
    this.text_title = null;
    this.start = null;
    this.end = null;
    this.text_addtitle = null;
    this.time_min = null;
    this.memoflag = false;
    this.memo = null;
    this.level = null;
    this.className = null;
    this.compliment=null;
}

/**
 * view
 * @param id
 * @param data
 * @constructor
 */
function InfoList(id, data) {
    this.id = id;
    this.data = data;
    this.view = $(id);
    this.showAll = function () {
        for (var i = 0; i < data.length; i++) {
            this.show(data[i],i);
        }
    };
    this.show = function (adata,i) {
        // var text_block = $("<span></span>").text(adata.text_block).addClass(adata.className);
        var img = $("<img/>").prop('src',adata.img).addClass('title-image');
        var text_level = "";
        if(adata.level){
            switch (adata.level) {
                case '0':text_level = "未実施"; break;
                case '1':text_level = "理解度 >90%"; break;
                case '2':text_level = "理解度 75%"; break;
                case '3':text_level = "理解度 60%"; break;
                case '4':text_level = "理解度 <60%"; break;
                default: break;
            }
        }
        var text = adata.text_title+ " "+ adata.start
            + (adata.text_addtitle?" ":"~")
            + (adata.text_addtitle?adata.text_addtitle+" ":"") + adata.end + " 　" + text_level;
        if (adata.learnLevUnds && adata.learnLevUnds === '0'){
            text = text.replace(/\s\d{2}:\d{2}~\d{2}:\d{2}/g, '');
        }
        var text_title = $("<span></span>").text(text).attr("onclick","eventClick("+i+")");
        var cell = $("<div></div>") .addClass("newt-layout-line") .addClass(adata.className);

        // cell.append(text_block);
        cell.append(img);
        cell.append(text_title);
        this.view.append(cell);
    };
}

/**
 * hjx:2019/11/18 add
 *
 * @param date
 */
function fullList(date) {
    if (!date) {
        date = new Date();
    }
    // 2020/12/08 NWT文　クリック時に使う
    var tgtymd = date;
    // 2020/12/08 NWT文　固定計画用 何曜日だと判断します
    var initDate = date;
    date = date.Format("yyyyMMdd");
    $.ajax({
        url: ctxPath + "/student/F11008/init",
        datatype: "json",
        data: {
            tgtYmdStr: date,
            dataFlg: dataFlg,
            url: url
        },
        success: function (data) {
            if (data.msg == "success") {
                var eventObj = [];
                $("#calendar").text("");

                // 2020/12/08 NWT文　対象日付より、一ヶ月のデータを取得する。↓↓↓ start
                if (!data.schdlBlockList && data.planPerfList && data.entryExitHstList && data.stuComplimentMstEntityList) {
                    return;
                }
                $("#date-content").find(".calendar_div").not(".preMonth,.nextMonth").each(function () {
                    var date = $("#div_month").text() + $(this).find("span").eq(0).text() + '日';
                    var weekday = new Date(date.replace("年",'/').replace("月",'/').replace("日",'')).getDay();
                    var date = new Date(date.replace("年",'/').replace("月",'/').replace("日",'')).Format("yyyy-MM-dd");
                    weekday = getDayName(weekday);
                    if (data.schdlBlockList.length > 0) {
                        for (var i = 0; i < data.schdlBlockList.length; i++) {
                            if (date >= data.schdlBlockList[i].blockStartTime.substring(0,10) && data.schdlBlockList[i].blockEndTime.substring(0,10) >= date) {
                                if (data.schdlBlockList[i][weekday] === true && data.schdlBlockList[i].isAbolt.indexOf(date) < 0){
                                    $(this).addClass("plan");
                                    $(this).find("h6").empty();
                                    $(this).find("h6").append('<p class="point"></p>');
                                }
                            }
                        }
                    }
                    if (data.planPerfList.length > 0) {
                        for (var i = 0; i < data.planPerfList.length; i++) {
                            if (($(this).find("span").eq(0).text().length<2?('0' +$(this).find("span").eq(0).text()):$(this).find("span").eq(0).text()) === data.planPerfList[i].planYmd.split(" ")[0].split("-")[2]) {
                                if (!$(this).hasClass("pColorRed")&&!$(this).hasClass("pColorBlue")) {
                                    $(this).addClass("plan");
                                    $(this).find("h6").empty();
                                    $(this).find("h6").append('<p class="point" style=" background-color: #8CC134 !important;"></p>');
                                }else {
                                    $(this).find("h6").empty();
                                    $(this).find("h6").append('<p class="point" style=" background-color: #8CC134 !important;"></p>');
                                }

                            }
                        }
                    }
                    if (data.entryExitHstList.length > 0) {
                        for (var i = 0; i < data.entryExitHstList.length; i++) {
                            if (parseInt($(this).find("span").eq(0).text()) === parseInt(data.entryExitHstList[i].entryDt.split(" ")[0].split("-")[2])) {
                                $(this).addClass("plan");
                                $(this).find("h6").empty()
                                $(this).find("h6").append('<p class="point"></p>');
                            }
                        }
                    }
                });
                initDate = getDayName(initDate.getDay());
                // 固定スケージュール
                $.each(data.schdlBlockList, function (index, info) {
                    if (info.aboltFlg != '1' && tgtymd.Format("yyyy-MM-dd") >= info.blockStartTime.substring(0,10) && info.blockEndTime.substring(0,10) >= tgtymd.Format("yyyy-MM-dd")) {
                        if (info[initDate] === true) {
                            var aeventObj = new InfoData();
                            aeventObj.id = info.id;
                            //画面表示内容：「計画（背景色付け）」　＋　1．ブロック表示名　＋　1．ブロック開始時間　～　1．ブロック終了時間
                            aeventObj.text_block = "計画";
                            aeventObj.img = '../img/activity.png';
                            aeventObj.text_title = info.adjustNm ===null ? info.blockDispyNm : info.adjustNm;
                            aeventObj.start = info.adjustStartTime === null ? new Date(info.blockStartTime.replace(/\-/g, "/")).Format("HH:mm") : new Date(info.adjustStartTime.replace(/\-/g, "/")).Format("HH:mm");
                            aeventObj.end = info.adjustEndTime === null ? new Date(info.blockEndTime.replace(/\-/g, "/")).Format("HH:mm") : new Date(info.adjustEndTime.replace(/\-/g, "/")).Format("HH:mm");
                            aeventObj.className = colors_class.GREEN;
                            aeventObj.eventType = event_type_div.FIXED_SCHEDULE;
                            aeventObj.ymd = date;
                            eventObj.push(aeventObj);
                        }
                    }
                });
                // 計画ブロック
                $.each(data.planPerfList, function (index, info){
                    if (info.planYmd.substring(0, 10) === tgtymd.Format("yyyy-MM-dd")) {
                        var aeventObj = new InfoData();
                        aeventObj.id = info.id;
                        aeventObj.text_block = "計画";
                        aeventObj.img = '../img/activity.png';
                        if (info.learnLevUnds !== null){
                            aeventObj.learnLevUnds = info.learnLevUnds;
                            if (info.perfLearnStartTime === null){
                                aeventObj.start = '00:00';
                                aeventObj.end = '00:00';
                            }else {
                                if (info.timerTm === null){
                                    //タイマー時間なし実績時間あり：実績時間開始時間～開始＋実績時間
                                    aeventObj.start = new Date(info.perfLearnStartTime.replace(/-/g, "/")).Format("HH:mm");
                                    aeventObj.end = new Date(new Date(info.perfLearnStartTime.replace(/-/g, "/")).valueOf() + info.perfLearnTm * 60 * 1000).Format("HH:mm");
                                }else {
                                    //タイマー時間あり：実績時間開始時間～開始時間＋タイマー時間
                                    aeventObj.start = new Date(info.perfLearnStartTime.replace(/-/g, "/")).Format("HH:mm");
                                    aeventObj.end = new Date(new Date(info.perfLearnStartTime.replace(/-/g, "/")).valueOf() + info.timerTm * 1000).Format("HH:mm");
                                }
                            }
                        }else {
                            //上記以外：予定開始時間～予定開始時間＋予定時間
                            aeventObj.start = new Date(info.planLearnStartTime.replace(/-/g, "/")).Format("HH:mm");
                            aeventObj.end = new Date(new Date(info.planLearnStartTime.replace(/-/g, "/")).valueOf() + info.stuPlanLearnTm * 60 * 1000).Format("HH:mm");
                        }
                        aeventObj.time_min = info.stuPlanLearnTm;
                        aeventObj.memo = info.blockDispyNm;
                        aeventObj.level = info.learnLevUnds;
                        aeventObj.className = colors_class.GREEN;
                        aeventObj.perfFlag = info.perfFlag;
                        if(info.blockTypeDiv == type_div.TYPE_DIV_O1 || info.blockTypeDiv == type_div.TYPE_DIV_O2 || info.blockTypeDiv == type_div.TYPE_DIV_O3){
                            //画面表示内容：「計画（背景色付け）」＋2．ブロック表示名.front＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "その他 "+info.blockDispyNm.split(" ")[0];
                            aeventObj.memo = info.blockDispyNm.split(" ")[1];
                            aeventObj.eventType = event_type_div.OTHER_PLAN;
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_V1){
                            //画面表示内容：「計画（背景色付け）」＋「塾宿」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "塾宿 "+info.subjtNm;
                            aeventObj.eventType = event_type_div.WEEKLY_PLAN;
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_R1){
                            //画面表示内容：「計画（背景色付け）」＋「復習」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "復習 "+info.subjtNm;
                            aeventObj.eventType = event_type_div.WEEKLY_PLAN;
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_W1){
                            //画面表示内容：「計画（背景色付け）」＋「学宿」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "学宿 "+info.subjtNm;
                            aeventObj.eventType = event_type_div.WEEKLY_PLAN;
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_P1){
                            //画面表示内容：「計画（背景色付け）」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "予習 "+info.subjtNm;
                            aeventObj.eventType = event_type_div.WEEKLY_PLAN;
                            /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_S1 && info.stuTermPlanId == null){
                            //画面表示内容：「計画（背景色付け）」＋「学習」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋メモ表示（メモ表示とリンク有無判断を参照）
                            aeventObj.text_title = "学習 "+info.subjtNm;
                            aeventObj.eventType = event_type_div.WEEKLY_PLAN;
                            /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                        }else if(info.blockTypeDiv == type_div.TYPE_DIV_S1 && info.stuTermPlanId){
                            //画面表示内容：「計画（背景色付け）」＋「教科」＋2．計画学習開始時間　～ 2．計画学習開始時間＋2．生徒計画学習時間（分）＋2．学習理解度
                            aeventObj.text_title = info.subjtNm;
                        }
                        eventObj.push(aeventObj);
                    }
                });
                // 学生の通塾時間
                $.each(data.entryExitHstList, function (index, info){
                    if (info.entryDt.substring(0,10) === tgtymd.Format("yyyy-MM-dd")) {
                        //画面表示内容：「通塾（背景色付け）」　+　「登校」　＋　「該当リストIDの登録日時（時と分だけ残す）」（開始時間として利用）　＋「下校」　＋「該当リストID＋１の登録日時（時と分だけ残す）」（終了時間として利用）
                        if(info.entryFlg && info.entryFlg == '0'){
                            var aeventObj = new InfoData();
                            var info_next = data.entryExitHstList[index+1];
                            aeventObj.id = info.id;
                            aeventObj.text_block = "通塾";
                            aeventObj.img = '../img/school.png';
                            aeventObj.text_title = "登校";
                            aeventObj.start = new Date(info.entryDt.replace(/\-/g, "/")).Format("HH:mm");
                            aeventObj.end = info_next ? new Date(info_next.entryDt.replace(/\-/g, "/")).Format("HH:mm") : '--:--';
                            aeventObj.text_addtitle = "下校";
                            aeventObj.className = colors_class.PINK;
                            eventObj.push(aeventObj);
                        }
                    }
                });
                //↑↑↑ end

                // 時間順にソートする
                eventObj = sortByTime(eventObj);
                //生徒褒めスタンプと褒めコメントを取得
                infoList = new InfoList("#calendar", eventObj);
                infoList.showAll();
            }
        }
    });
}

/**
 * 時間順にソートする
 */
function sortByTime(dataList){
    if(!dataList){
        return null;
    }
    return dataList.sort(function (a,b) {
        return CompareDate(a.start , b.start) ? 1 : -1;
    });
}

/**
 * Compare Date 時分
 * data demo: "HH:mm"
 * @param t1
 * @param t2
 * @returns {boolean}
 * @constructor
 */
function CompareDate(t1,t2) {
    var date = new Date();
    var a = t1.split(":");
    var b = t2.split(":");
    return date.setHours(a[0],a[1]) > date.setHours(b[0],b[1]);
}

//上側カレンダー
$(function () {
    var weelkHtml = '';
    var monthhtml = '';
    var myDate = new Date();
    var myYear = myDate.getFullYear(),
        myMonth = myDate.getMonth() + 1,
        myDay = myDate.getDate(),
        confimMonth = myDate.getMonth() + 1,
        confimYear = myDate.getFullYear();
    var nowMonth = myDate.getMonth();
    $(".list_title").find("p").html(myMonth+"月"+myDay+"日");
    var nowWeek = new Date(myYear, nowMonth, myDay).getDay();

    function getWeek() {
        var sText = '';
        if (nowWeek == 1) {
            sText = '(月)';
        } else if (nowWeek == 2) {
            sText = '(火)';
        } else if (nowWeek == 3) {
            sText = '(水)';
        } else if (nowWeek == 4) {
            sText = '(木)';
        } else if (nowWeek == 5) {
            sText = '(金)';
        } else if (nowWeek == 6) {
            sText = '(土)';
        } else if (nowWeek == 0) {
            sText = '(日)';
        }
        $(".list_title").find("span").html(sText);
    }

    getWeek();

    function calendar() {
        var weekMonth = myMonth - 1;
        var myMonths = myMonth;
        var myYears = myYear;
        var preMonth = 0;
        if (myMonths - 1 > 0) {
            preMonth = myMonths - 1;
        } else {
            preMonth = 12;
            // myYears = myYears - 1;
        }
        var nextMonth = 12;
        if (myMonths + 1 < 13) {
            nextMonth = myMonths + 1;
        } else {
            nextMonth = 1;
            // myYears = myYears + 1;
        }
        var date = new Date((myMonths - 1) > 0 ? myYear : (myYear - 1), preMonth, 0);
        var daysOfPreMonth = date.getDate();
        $(".date_title").find("span").html(myYears + '年' + myMonths + '月');
        $("#div_month_left").find("span").html(((myMonths - 1) > 0 ? (myMonths - 1) : 12) + '月');
        $("#div_month_right").find("span").html(((myMonths + 1) < 13 ? (myMonths + 1) : 1) + '月');
        var weekArray = ['日', '月', '火', '水', '木', '金', '土'];
        for (var i = 0; i < weekArray.length; i++) {
            weelkHtml += '<p>' + weekArray[i] + '</p>';
        }
        $(".date_week").html(weelkHtml);
        $(".date_week").children().eq(0).addClass("pColorRed");
        $(".date_week").children().eq(6).addClass("pColorBlue");
        var firstWeek = new Date(myYear, weekMonth, '01').getDay();

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
            var alldays = getMonthLength(daytime);
        } else {
            var alldays = new Date(myYear, myMonth, 0).getDate();
        }
        var day = 0;
        var month = 0;
        var holidayList = getHoliday(myYear);
        for (var i = 1; i <= 42; i++) {
            var dayHtml = '';
            if (i - firstWeek <= 0){
                dayHtml = '<div class="calendar_div preMonth">' +
                    '<p><span>' + (daysOfPreMonth - firstWeek + i) + '</span></p>' +
                    '<h6></h6>' +
                    '</div>';
                day = daysOfPreMonth - firstWeek + i;
                month = preMonth;
            }else if(i - firstWeek <= alldays){
                dayHtml = '<div class="calendar_div">' +
                    '<p><span>' + (i - firstWeek) + '</span></p>' +
                    '<h6></h6>' +
                    '</div>';
                day = i - firstWeek;
                month = myMonths;
            }else if (i - firstWeek > alldays){
                dayHtml = '<div class="calendar_div nextMonth">' +
                    '<p><span>' + (i - alldays - firstWeek) + '</span></p>' +
                    '<h6></h6>' +
                    '</div>';
                day = i - alldays - firstWeek;
                month = nextMonth;
            }
            var holiday = "";
            try {
                holidayList.forEach(function (item, index) {
                    if (month != myMonths){
                        throw Error();
                    }
                    if (new Date(item.tgtYmd.replace(/-/g,"/")).Format("yyyy-MM-dd") == new Date(myYear, month - 1, day).Format("yyyy-MM-dd")) {
                        holiday = item.holidayNm;
                        throw Error();
                    }
                });
            }catch (e) {
            }
            if (holiday != '') {
                dayHtml = dayHtml.replace(/calendar_div/, 'calendar_div pColorRed ');
                dayHtml = dayHtml.replace(/<h6><\/h6>/, '<h6></h6>');
            }
            if (i <= 7){
                dayHtml = dayHtml.replace(/calendar_div/, 'calendar_div line-one ');
            }
            monthhtml += dayHtml;
        }
        $(".control").after(monthhtml);
        /**
         * 日付クリック
         * @type {*|jQuery}
         */
        var pclick = $(".date_content").find(".calendar_div");
        pclick.click(function () {
            if (!$(this).hasClass("Pback")) {
                pclick.removeClass("Pback");
                $(this).addClass("Pback");
                var sDay = $(this).find('p').text();
                var clickYear = 0;
                var clickMonth = 0;
                if ($(this).hasClass("preMonth")){
                    clickYear = (myMonths - 1) > 0 ? myYear : (myYear - 1);
                    clickMonth = preMonth;
                }else if ($(this).hasClass("nextMonth")){
                    clickYear = (myMonths + 1) <= 12 ? myYear : (myYear + 1);
                    clickMonth = nextMonth;
                }else {
                    clickYear = myYear;
                    clickMonth = myMonths;
                }
                dataFlg = 1;
                nowWeek = new Date(clickYear, clickMonth - 1, sDay).getDay();
                getWeek();
                $(".list_title").find("p").html(clickMonth+"月"+sDay+"日");
                fullList(new Date(clickYear, clickMonth - 1, sDay));
            }
        });

        var allP = $(".date_content").children();
        if (myMonth == confimMonth && myYear == confimYear) {
            allP.eq(myDay + firstWeek).addClass("Pback");
        }
        var Dw = 1;
        for (var i = 0; i < 6; i++) {
            allP.eq(Dw).addClass("pColorRed pBorderRed");
            Dw = Dw + 7;
        }
        Dw = 7;
        for (var i = 0; i < 6; i++) {
            allP.eq(Dw).addClass("pColorBlue");
            Dw = Dw + 7;
        }
    }

    calendar();
    var startx, starty;

    function getAngle(angx, angy) {
        return Math.atan2(angy, angx) * 180 / Math.PI;
    }

    function getDirection(startx, starty, endx, endy) {
        var angx = endx - startx;
        var angy = endy - starty;
        var result = 0;
        if (Math.abs(angx) < 30 && Math.abs(angy) < 30) {
            return result;
        }

        var angle = getAngle(angx, angy);
        if (angle >= -135 && angle <= -45) {
            result = 1;
        } else if (angle > 45 && angle < 135) {
            result = 2;
        } else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
            result = 3;
        } else if (angle >= -45 && angle <= 45) {
            result = 4;
        }

        return result;
    }

    document.getElementById("date-content").addEventListener("touchstart", function (e) {
        startx = e.touches[0].pageX;
        starty = e.touches[0].pageY;
    }, false);
    document.getElementById("date-content").addEventListener("touchend", function (e) {
        var endx, endy;
        endx = e.changedTouches[0].pageX;
        endy = e.changedTouches[0].pageY;
        var direction = getDirection(startx, starty, endx, endy);
        switch (direction) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                xLeft();
                break;
            case 4:
                xRight();
                break;
            default:
        }
    }, false);

    function xRight() {
        var nowYear = myDate.getFullYear(), nowMonth = myDate.getMonth() + 1;
        myMonth = myMonth - 1;
        weelkHtml = '';
        monthhtml = '';
        $(".date_content").find(".calendar_div").remove();
        $(".date_content").find(".calendar_div_empty").remove();
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
        var allP = $(".date_content").children();
        var thisMonth = [];
        allP.removeClass("Pback");
        nowWeek = new Date(myYear, myMonth - 1, 1).getDay();
        allP.each(function () {
            if (!$(this).hasClass("preMonth") && !$(this).hasClass("control")) {
                thisMonth.push($(this));
            }
        });
        $(thisMonth[0]).addClass("Pback");
        getWeek();
        $(".list_title").find("p").html(myMonth+"月1日");
        //reload data
        fullList(new Date(myYear, myMonth - 1, 1));
    }

    function xLeft() {
        var nowYear = myDate.getFullYear(), nowMonth = myDate.getMonth() + 1;
        myMonth = myMonth + 1;
        weelkHtml = '';
        monthhtml = '';
        $(".date_content").find(".calendar_div").remove();
        $(".date_content").find(".calendar_div_empty").remove();
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
        var allP = $(".date_content").children();
        var thisMonth = [];
        allP.removeClass("Pback");
        nowWeek = new Date(myYear, myMonth - 1, 1).getDay();
        allP.each(function () {
            if (!$(this).hasClass("preMonth") && !$(this).hasClass("control")) {
                thisMonth.push($(this));
            }
        });
        $(thisMonth[0]).addClass("Pback");
        getWeek();
        $(".list_title").find("p").html(myMonth+"月1日");
        //reload data
        fullList(new Date(myYear, myMonth - 1, 1));
    }

    $("#div_month_left").click(function () {
        xRight();
    });
    $("#div_month_right").click(function () {
        xLeft();
    });

    fullList();
});

function getDayName(weekday) {
    var dayName = '';
    switch (weekday) {
        case 1:
            dayName = "moDwChocFlg";
            break;
        case 2:
            dayName = "tuDwChocFlg";
            break;
        case 3:
            dayName = "weDwChocFlg";
            break;
        case 4:
            dayName = "thDwChocFlg";
            break;
        case 5:
            dayName = "frDwChocFlg";
            break;
        case 6:
            dayName = "saDwChocFlg";
            break;
        case 0:
            dayName = "suDwChocFlg";
            break;
    }
    return dayName;
}

function eventClick(index) {
    var event = infoList.data[index];
    if (event.eventType){
        if (event.perfFlag && event.perfFlag === true){
            parent.layer.confirm('実行結果を修正しますか？', {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 2,
                anim: -1,
                btn: ['手動', 'タイマー'],
                btn1: function () {
                    /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                    window.localStorage.setItem("submitType", "0");
                    /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                    window.location.href = ctxPath + '/student/F11005.html?id=' + event.id + '&editFlag=1';
                },
                btn2: function () {
                    window.location.href = ctxPath + '/student/F11006.html?id=' + event.id + '&editFlag=1';
                }
            });
        }else {
            if (event.eventType === event_type_div.WEEKLY_PLAN){
                parent.layer.confirm('計画を修正しますか？', {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 2,
                    anim: -1,
                    btn: ['修正'],
                    btn1: function () {
                        window.location.href = ctxPath + '/student/F11001.html?id=' + event.id;
                    }
                });
            }else if (event.eventType === event_type_div.FIXED_SCHEDULE){
                parent.layer.confirm('修正の種類を選択してください。', {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 2,
                    anim: -1,
                    btn: ['個別修正', '全体修正'],
                    btn1: function () {
                        window.location.href = ctxPath + '/student/F11002.html?id=' + event.id + '&typeDiv=1&ymd=' + event.ymd.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
                    },
                    btn2: function () {
                        window.location.href = ctxPath + '/student/F11002.html?id=' + event.id + '&typeDiv=0&ymd=' + event.ymd.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
                    }
                });
            }else if (event.eventType === event_type_div.OTHER_PLAN){
                parent.layer.confirm('計画を修正しますか？', {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 2,
                    anim: -1,
                    btn: ['修正'],
                    btn1: function () {
                        window.location.href = ctxPath + '/student/F11003.html?id=' + event.id;
                    }
                });
            }
        }
    }
}
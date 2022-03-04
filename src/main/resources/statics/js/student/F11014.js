var dataFlg = 0;
var url = window.location.href;
var param = getParam();
var weelkHtml = '';
var monthhtml = '';
var talkRecordH = '';
var talkRecordD = [];
var myDate = new Date();
var myYear = myDate.getFullYear(), myMonth = myDate.getMonth() + 1, myDay = myDate.getDate(),
    confimMonth = myDate.getMonth() + 1, confimYear = myDate.getFullYear();
var nowMonth = myDate.getMonth();
var nowWeek = new Date(myYear, nowMonth, myDay).getDay();
var question = [];
var weekday = getDay(new Date(myYear, myMonth - 1, myDay));
var strss = myYear + '年' + myMonth + '月' + myDay + '日' + '(' + weekday + ')';
$(".time_line").find("p").eq(0).text(strss);

function back() {
    if (param.flg == 0) {
        window.location.href = './F11013.html?eventId=' + param.eventId;
    }
    if (param.flg == 1) {
        window.location.href = './F11017.html';
    }
}

var closest = '';
var vm = new Vue({
    el: "#F11014",
    data: {
        eventTitle:'',
        applylist: [],
        askItems:[],
        timeList: [],
        pageSts: "",
        minDate: '',
        stuName: '',
        answerReltCont:'',
        base: {},
        refType: '1',
        entryDateTime: new Date()
    },
    mounted: function () {
        // 2020/12/1 huangxinliang modify start
        $(".calendar_div").css("pointer-events", "none");
        this.showPage1();
        // 2020/12/1 huangxinliang modify end
        $.ajax({
            url: ctxPath + '/student/F11014/getMin',
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
                    vm.showData();
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
            if (closest) {
                tgtYmd = closest.split(" ")[0].split("-")[0] + "-" + closest.split(" ")[0].split("-")[1] + '-' + "01"
            } else {
                tgtYmd = myYear + '-' + myMonth + '-' + myDay;
            }
            $.ajax({
                url: ctxPath + '/student/F11014/init',
                type: 'GET',
                data: {
                    eventId: param.eventId,
                    tgtYmd: tgtYmd
                },
                // 2020/12/1 huangxinliang modify start
                async: false,
                // 2020/12/1 huangxinliang modify end
                dataType: "json",
                success: function (data) {
                    vm.stuName = data.stuName;
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
                                    $(".timelist").val(vm.applylist[i].id);
                                    if (vm.applylist[i].flag == '0') {
                                        $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events", "auto");
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
                            var timeHtml = "";
                            for (var i = 0; i < vm.timeList.length; i++) {
                                timeHtml += '<li style="width: 30%" class="timelist">' + vm.timeList[i].timeLine + '</li>';
                            }
                            $(".time_line").find("ul").eq(0).html(timeHtml);
                            vm.getAskTalk()
                        }
                        // 2020/12/1 huangxinliang modify start
                        if (data.refType){
                            vm.refType = data.refType;
                            if (data.refType === '2'){
                                vm.showPage2();
                                $(".confirm_area").find('.returnBtn').eq(0).css("pointer-events","none");
                            }
                        }
                        // 2020/12/1 huangxinliang modify end
                    }
                }
            });
        },
        getAskTalk: function () {
            $.ajax({
                url: ctxPath + '/student/F11014/getAskTalk',
                type: 'Get',
                data: {
                    eventId: param.eventId
                },
                dataType: "json",
                success: function (data) {
                    vm.askItems = data.askTalk;
                    if (data.talkRecordH!=null){
                        $("#noteCont").html(data.talkRecordH.noteCont)
                    }
                    //質問名
                    //2020/11/12 huangxinliang modify start
                    var questionName = '';
                    for (var i = 0; i < data.askTalk.length; i++) {
                        question[i] = data.askTalk[i];
                        var className;
                        if (data.askTalk[i].answerTypeDiv == "0") {
                            className = 'inputText';
                        }
                        if (data.askTalk[i].answerTypeDiv == "1") {
                            className = 'selectInput';
                        }
                        if (data.askTalk[i].answerTypeDiv == "2") {
                            className = 'chooseBtn';
                        }
                        if (data.askTalk[i].answerTypeDiv == "3") {
                            className = 'imgSelect';
                        }

                        questionName += '<li style="width: 100%" class="name">' + data.askTalk[i].questionName + '</li><div class="' + className + '" id="' + data.askTalk[i].askNum + '"></div>';
                    }
                    $(".questionName").html(questionName);
                    //2020/11/12 huangxinliang modify end
                    if (data.talkRecordD != undefined) {
                        talkRecordH = data.talkRecordH;
                        talkRecordD = data.talkRecordD;
                        $('textarea[name="noteCont"]').val(talkRecordH.noteCont);
                        for (var i = 0; i <data.talkRecordD.length ; i++) {
                            if (data.talkRecordD[i].answerTypeDiv == "0") {
                                var text = '';
                                text += '<textarea class="inputArea" id="Text" maxlength="500">' + data.talkRecordD[i].answerReltCont + '</textarea>';
                                $("#" + data.talkRecordD[i].askNum + "").html(text);
                            } else if (data.talkRecordD[i].answerTypeDiv == "1") {
                                var select = '';
                                select += '<select  class="' + data.talkRecordD[i].askNum + '" value="' + data.talkRecordD[i].askNum + '"></select>';
                                $("#" + data.talkRecordD[i].askNum + "").html(select);
                            }
                        }
                        for (var i = 0; i <data.askTalk.length ; i++) {
                            if (data.askTalk[i].answerTypeDiv == "2"){
                                for (var j = 0; j < data.talkRecordD.length; j++) {
                                    if (data.talkRecordD[j].answerTypeDiv == "2") {
                                        var check = '';
                                        if (data.talkRecordD[j].askNum == data.askTalk[i].askNum){
                                            var answerReltCont = data.talkRecordD[j].answerReltCont.split(",");
                                            for (var m = 1; m < 10; m++) {
                                                if (data.askTalk[i]['optCont' + m] != "") {
                                                    if (stats(answerReltCont, data.askTalk[i]['optCont' + m])) {
                                                        check += '<li><input type="checkbox" class="check" checked="checked" value="' + data.askTalk[i]['optCont' + m] + '"/><span style="margin-left: 5px">' + data.askTalk[i]['optCont' + m] + '</span></li>'
                                                    } else {
                                                        check += '<li><input type="checkbox" class="check" value="' + data.askTalk[i]['optCont' + m] + '"/><span style="margin-left: 5px">' + data.askTalk[i]['optCont' + m] + '</span></li>'
                                                    }
                                                }
                                            }
                                            $("#" + data.talkRecordD[i].askNum + "").html(check);
                                        }

                                    }
                                }
                            }
                        }
                        for (var i = 0; i < data.askTalk.length; i++) {
                            for (var j = 0; j < data.talkRecordD.length; j++) {
                                if (data.talkRecordD[j].answerTypeDiv == "1") {
                                    if (data.askTalk[i].answerTypeDiv == "1") {
                                        if (data.askTalk[i].askNum ==data.talkRecordD[j].askNum ){
                                        $("." + data.talkRecordD[j].askNum + "").append("<option value=''></option>");
                                        for (var k = 1; k < 10; k++) {
                                            if (data.askTalk[i]['optCont' + k] != "") {

                                                    if (data.askTalk[i]['optCont' + k]==data.talkRecordD[j].answerReltCont) {
                                                        $("." + data.talkRecordD[j].askNum + "").append("<option selected = selected  value=" + data.askTalk[i]['optCont' + k] + ">" + data.askTalk[i]['optCont' + k] + "</option>");
                                                    }else {
                                                        $("." + data.talkRecordD[j].askNum + "").append("<option value=" + data.askTalk[i]['optCont' + k] + ">" + data.askTalk[i]['optCont' + k] + "</option>");
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        for (var i = 0; i < data.askTalk.length; i++) {
                            if (data.askTalk[i].answerTypeDiv == "0") {
                                var text = ''
                                text += '<textarea class="inputArea" id="Text" maxlength="500"></textarea>'
                                $("#" + data.askTalk[i].askNum + "").html(text);
                            } else if (data.askTalk[i].answerTypeDiv == "1") {
                                var select = ''
                                select += '<select  class="' + data.askTalk[i].askNum + '" value="' + data.askTalk[i].askNum + '"></select>';
                                $("#" + data.askTalk[i].askNum + "").html(select);
                            } else {
                                var check = ''
                                for (var j = 1; j < 10 ; j++) {
                                    if (data.askTalk[i]['optCont'+j] != "") {
                                            check += '<li><input type="checkbox" class="check" value="' + data.askTalk[i]['optCont'+j] + '"/><span style="margin-left: 5px">' + data.askTalk[i]['optCont'+j] + '</span></li>'
                                        }
                                    }
                                $("#" + data.askTalk[i].askNum + "").html(check);
                            }
                        }

                        for (var i = 0; i < data.askTalk.length; i++) {
                            if (data.askTalk[i].answerTypeDiv == "1") {
                                $("." + data.askTalk[i].askNum + "").append("<option value=''></option>");
                                if (data.askTalk[i].optCont1 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont1 + ">" + data.askTalk[i].optCont1 + "</option>");
                                }
                                if (data.askTalk[i].optCont2 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont2 + ">" + data.askTalk[i].optCont2 + "</option>");
                                }
                                if (data.askTalk[i].optCont3 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont3 + ">" + data.askTalk[i].optCont3 + "</option>");
                                }
                                if (data.askTalk[i].optCont4 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont4 + ">" + data.askTalk[i].optCont4 + "</option>");
                                }
                                if (data.askTalk[i].optCont5 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont5 + ">" + data.askTalk[i].optCont5 + "</option>");
                                }
                                //2020/11/12 huangxinliang modify start
                                if (data.askTalk[i].optCont6 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont6 + ">" + data.askTalk[i].optCont6 + "</option>");
                                }
                                if (data.askTalk[i].optCont7 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont7 + ">" + data.askTalk[i].optCont7 + "</option>");
                                }
                                if (data.askTalk[i].optCont8 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont8 + ">" + data.askTalk[i].optCont8 + "</option>");
                                }
                                if (data.askTalk[i].optCont9 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont9 + ">" + data.askTalk[i].optCont9 + "</option>");
                                }
                                if (data.askTalk[i].optCont10 != "") {
                                    $("." + data.askTalk[i].askNum + "").append("<option value=" + data.askTalk[i].optCont10 + ">" + data.askTalk[i].optCont10 + "</option>");
                                }
                                //2020/11/12 huangxinliang modify end
                            }
                        }
                    }

                    //写真
                    //2020/11/12 huangxinliang modify start
                    for (var i = 0; i <data.askTalk.length; i++) {
                        if (data.askTalk[i].answerTypeDiv == "3") {
                            // 2020/11/26 huangxinliang modify start
                            var image = '<div class="file-input-container">';
                            image += '<img src="../img/add.png" alt="" class="camera" id="camera"/>';
                            image += '<input type="file" id="addPhoto' + i + '" class="add-photo" onchange="changepic(' + i + ')" accept="image/*">';
                            image += '</div>';
                            // 2020/11/26 huangxinliang modify end
                            var src = data.talkRecordD && data.talkRecordD[i].answerReltCont ? data.talkRecordD[i].answerReltCont : '';
                            image += '<div id="photoContainer' + i + '" class="photo-container">\n' +
                                '       <img src="' + src + '" class="photo-image" onerror="$(this).css(\'visibility\',\'hidden\')" onclick="vm.showImg(' + i + ')" id="image' + i + '"/>\n' +
                                '     </div>'
                            $("#" + data.askTalk[i].askNum + "").html(image);
                        }
                    }
                    //2020/11/12 huangxinliang modify end
                }

            })
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



function stats(answerReltCont,optCont) {
   var cont = answerReltCont.indexOf(optCont);
       return cont==-1?false:true;
}

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
    if (selectMonth < 10) {
        selectMonth = '0' + selectMonth
    }
    nowWeek = new Date(myYear, nowMonth, sDay).getDay();
    dataFlg = 1;
    $(".time_line").find("p").eq(0).text(current + sDay + '日' + '(' + getDay(new Date(myYear, nowMonth, sDay)) + ')');
    var selectss = current + sDay + '日';
    selectss = selectss.replace(rcjk, "-");
    selectss = selectss.substring(0, selectss.lastIndexOf("-"));
    $.ajax({
        url: ctxPath + '/student/F11014/init',
        type: 'GET',
        data: {
            eventId: param.eventId,
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
$(".time_line").on('click', '.timelist', function () {
    $.ajax({
        url: ctxPath + '/student/F11014/timeselect',
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
                    vm.showPage2();
                }
            }
        }
    });
})

// タイムフォーマット変換
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
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
//内容を確認ボダン押下
$("#cnt_confirm").click(function () {
    // 2020/12/1 huangxinliang modify start
    vm.showPage3();
    // 2020/12/1 huangxinliang modify end
    $("#confirm_msg").html("確認事項");
    $("#stu_name").text(vm.stuName);
    if (vm.refType !== '2'){
        $("#plan_time").text($(".time_line").find("p").html().split("年")[1] + new Date(scheDel.sgdStartDatime.replace(/\-/g, "/")).format('hh:mm') + '～');
    }else {
        $("#plan_time").text(vm.entryDateTime.format('yyyy/MM/dd hh:mm') + '～');
    }
    for (var i = 0; i <vm.askItems.length ; i++) {
        var paramObj = '';
        if (vm.askItems[i].answerTypeDiv == "0") {
            paramObj= $("#" + vm.askItems[i].askNum + "").find("textArea").val()
        }
        if (vm.askItems[i].answerTypeDiv == '1') {
            paramObj = $("#" + vm.askItems[i].askNum + "").find("option:selected").html();
        }
        if (vm.askItems[i].answerTypeDiv == '2') {
            $("#" + vm.askItems[i].askNum + "").find("input:checked").each(function (index, element) {
                if (index == $("#" + vm.askItems[i].askNum + "").find("input:checked").length - 1) {
                    paramObj += $(this).next("input").prevObject.val();
                } else {
                    paramObj += $(this).next("input").prevObject.val() + ',';
                }
            });
        }
        $(".result_cont").eq(i).text(paramObj);
        //2020/11/12 huangxinliang modify start
        if (vm.askItems[i].answerTypeDiv === '3'){
            $(".result_cont").eq(i).html($('#image' + i));
        }
        //2020/11/12 huangxinliang modify end
    }



    $("#remarks").text($("#noteCont").val());
});
//申込を確定ボダン押下 $("#5").find("label").find("input")[1].checked
$("#apply_confirm").click(function () {
    var checkValue = '';
    // 2021/1/8 huangxinliang modify start
    var formData = new FormData();
    // 2021/1/8 huangxinliang modify end
    for (var i = 0; i < question.length; i++) {
        if (question[i].answerTypeDiv == "2") {
            for (var j = 0; j < $("#" + question[i].askNum + "").find("li").find("input").length; j++) {
                if ($("#" + question[i].askNum + "").find("li").find("input")[j].checked) {
                    if (checkValue == '' || checkValue.charAt(checkValue.length - 1) == "/") {
                        checkValue += $("#" + question[i].askNum + "").find("li").find("input")[j].value
                    } else {
                        checkValue += ',' + $("#" + question[i].askNum + "").find("li").find("input")[j].value
                    }
                }
            }
                checkValue += "" + ":" + question[i].askNum + "/";

        }

    }

    var selectValue = "";
    for (var i = 0; i < question.length; i++) {
        if (question[i].answerTypeDiv == "1") {
                selectValue += $("." + question[i].askNum + "")[0].value;
                selectValue += ":" + question[i].askNum + "/";
        }

    }
    var text = {};
    for (var i = 0; i < question.length; i++) {
        if (question[i].answerTypeDiv == "0") {
                text[question[i].askNum] = $("#" + question[i].askNum + "").find(".inputArea").val();
        }
    }
    //2020/11/12 huangxinliang modify start
    var img = {};
    for (var i = 0; i < question.length; i++) {
        if (question[i].answerTypeDiv == "3") {
            img[question[i].askNum] = vm.base['image' + i] ? vm.base['image' + i] : $('#image' + i).attr('src');
        }
    }
    //2020/11/12 huangxinliang modify end
    var startTime = scheDel.sgdStartDatime;
    if (vm.refType === '2'){
        startTime = vm.entryDateTime.format('yyyy-MM-dd hh:mm:ss');
    }
    // 2021/1/8 huangxinliang modify start
    formData.append('eventId', param.eventId);
    formData.append('scheDelId', scheDelId);
    formData.append('startTime', startTime);
    formData.append('checkValue', checkValue);
    formData.append('selectValue', selectValue);
    formData.append('inputArea', JSON.stringify(text));
    formData.append('imgArea', JSON.stringify(img));
    formData.append('replyCnt', $('textarea[name="noteCont"]').val());
    $.ajax({
        url: ctxPath + '/student/F11014/update',
        type: 'POST',
        data: formData,
        dataType: "json",
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.code != 0) {
                layer.alert(data.msg);
            } else {
                window.location.href = 'F11015.html?eventId=' + param.eventId
            }
        }
    })
    // 2021/1/8 huangxinliang modify end
})

//戻るボダン押下
$(".returnBtn").click(function () {
    if (vm.pageSts == "3") {
        //2020/11/12 huangxinliang modify start
        for (var i = 0; i <vm.askItems.length ; i++) {
            if (vm.askItems[i].answerTypeDiv === '3'){
                $("#photoContainer" + i).html($('#image' + i));
            }
        }
        //2020/11/12 huangxinliang modify end
        // 2020/12/1 huangxinliang modify start
        vm.showPage2();
        // 2020/12/1 huangxinliang modify end
        return;
    }
    if (vm.pageSts == "2") {
        // 2020/12/1 huangxinliang modify start
        vm.showPage1();
        // 2020/12/1 huangxinliang modify end
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
    $(".calendar_div").css("pointer-events", "none");
    var allP = $(".date_content").children();
    allP.removeClass("Pback");
    allP.eq(1).addClass("Pback");
    nowWeek = getDay(new Date(myYear, myMonth - 1, 1));
    var current = $(".date_title").find("p").html();
    $(".time_line").find("p").eq(0).text(current + '1' + '日' + '(' + nowWeek + ')');
    $.ajax({
        url: ctxPath + '/student/F11014/init',
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
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events", "auto");
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).nextAll().remove();
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).after("<h6 style=\"color: red;\">〇</h6>");
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
    $(".calendar_div").css("pointer-events", "none");
    var allP = $(".date_content").children();
    allP.removeClass("Pback");
    allP.eq(1).addClass("Pback");
    nowWeek = getDay(new Date(myYear, myMonth - 1, 1));
    var current = $(".date_title").find("p").html();
    $(".time_line").find("p").eq(0).text(current + '1' + '日' + '(' + nowWeek + ')');
    $.ajax({
        url: ctxPath + '/student/F11014/init',
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
                                $(".calendar_div").find("p").eq(vm.applylist[i].tgtDay - 1).css("pointer-events", "auto");
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
var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});
//2020/11/12 huangxinliang modify start
//選択したアバターを表示
function changepic(index) {
    var f = document.getElementById('addPhoto' + index).files[0];
    var max = 5 * 1024 * 1024;
    if (f) {
        if (f.size > max) {
            layer.alert($.format($.msg.MSGCOMN0088, "質問/面談", "写真", "5M"));
            return;
        }
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
                    if(navigator.userAgent.indexOf("iPhone") === -1 || navigator.userAgent.indexOf("iPad") || navigator.userAgent.indexOf("Mac")){
                        orient = 1;
                    }
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
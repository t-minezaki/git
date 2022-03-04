if(getCookie("equipment") === "phone"){
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09020.css"}).appendTo("head");
}else {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09020-1.css"}).appendTo("head");

}
var param = getParam();
var noticeId = param.noticeId;
var orgId = param.orgId;
var stuList;
var psd;
var ntd;
var ped;
var import1;
var stuIdList = [];
var orgIdList = [];
var mgrFlg = "";
//2020/11/17 LiYuHuan modifly start
var path="";
//NWT　楊　2021/07/30　MANAMIRU1-747　Edit　Start
$(function (){
    ue = UE.getEditor('con');
    // NWT　楊　2021/08/04　MANAMIRU1-754　Add　Start
    ue.ready(function (){
        ue.setHeight('30vw');
    })
    // NWT　楊　2021/08/04　MANAMIRU1-754　Add　End
    ue.placeholder("内容を入力");
})
//NWT　楊　2021/07/30　MANAMIRU1-747　Edit　End
var vm = new Vue({
    el: '#content',
    data: {
        stu: '',
        goSchPoint: '',
        // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　Start
        // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　End
        endYear:'',
        nowYear: new Date().getFullYear(),
        showTag : true
    },
    mounted: function () {
        $("#stuCount").text('0人');
        // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　Start
        // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　End
        if (noticeId == null || noticeId == "") {
            this.nowTime();
        }
        this.init();
    },
    methods: {
        init: function () {
            if (noticeId == null || noticeId == "") {
                $.ajax({
                    url: ctxPath + '/manager/F09020/init1',
                    type: 'GET',
                    dataType: 'json',
                    async: true,
                    success: function (data) {
                        var str = "<option value=''disabled selected>力テゴリを選択</option>";
                        if (data) {
                            if (data.cvl == null || data.cvl == "") {
                                var idx = layer.confirm($.format($.msg.MSGCOMN0017, "お知らせタイプ"), {
                                    skin: 'layui-layer-molv',
                                    title: '確認',
                                    closeBtn: 0,
                                    anim: -1,
                                    btn: ['確認'],
                                    yes: function () {
                                        layer.close(idx);
                                    }
                                });
                                return false;
                            }
                            if (data.cvl) {
                                var arr = data.cvl;
                                for (var i = 0; i < arr.length; i++) {
                                    str += "<option value='" + arr[i].codCd + "'>" + arr[i].codValue + "</option>";
                                }
                                $("#ntd").html(str);
                            }
                            $("#day1").text(data.dayS);
                            $("#day2").text(data.dayE + ' 00:00');
                        }

                    }
                });
            } else {
                this.showTag = false;
                $.ajax({
                    url: ctxPath + '/manager/F09020/init2',
                    type: 'GET',
                    dataType: 'json',
                    async: true,
                    data: {
                        noticeId: param.noticeId
                    },
                    success: function (data) {
                        if (data) {
                            if (data.cvl == null || data.cvl == "") {
                                var idx = layer.confirm($.format($.msg.MSGCOMN0017, "お知らせタイプ"), {
                                    skin: 'layui-layer-molv',
                                    title: '確認',
                                    closeBtn: 0,
                                    anim: -1,
                                    btn: ['確認'],
                                    yes: function () {
                                        layer.close(idx);
                                    }
                                });
                                return false;
                            }
                            if (data.cvl) {
                                var arr = data.cvl;
                                var str = "";
                                for (var i = 0; i < arr.length; i++) {
                                    if (data.dtos && data.dtos.codCd == arr[i].codCd) {
                                        str += "<option value='" + arr[i].codCd + "' selected>" + arr[i].codValue + "</option>";
                                    } else {
                                        str += "<option value='" + arr[i].codCd + "'>" + arr[i].codValue + "</option>";
                                    }
                                }
                                $("#ntd").html(str);
                            }
                            var dtos = data.dtos;
                            //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　Start
                            ue.ready(function (){
                                ue.setContent(decodeURIComponent(dtos.noticeCont));
                            })
                            //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　End
                            $("#name").val(dtos.noticeTitle);
                            if (data.dtos.attachFilePath) {
                                var str = data.dtos.attachFilePath.substring(data.dtos.attachFilePath.lastIndexOf('\\') + 1);
                                str = str.slice(str.lastIndexOf("/") + 1);
                                //2020/11/27 LiYuHuan modifly start
                                path = data.dtos.attachFilePath.replace(/\\/g,"/");
                                //2020/11/27 LiYuHuan modifly end
                                function handleFileName(fileName) {
                                    var filename = fileName.slice(fileName.lastIndexOf("\\") + 1);
                                    filename = filename.slice(filename.lastIndexOf("/") + 1);
                                    var newStr = "";
                                    for (var i = 0; i < filename.length; i++) {
                                        if (!(i >= filename.lastIndexOf(".") - 17 && i < filename.lastIndexOf("."))) {
                                            newStr += filename.charAt(i);
                                        }
                                    }
                                    return newStr;
                                }

                                $("#showPath1").text(handleFileName(data.dtos.attachFilePath));
                            }
                            if (dtos.noticeLevelDiv == '2') {
                                $("#import1").attr("checked", true);
                            }
                            if (dtos.noticeLevelDiv == '1') {
                                $("#selectAll").attr("checked", true);
                            }
                            // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　Start
                            // NWT　楊　2021/08/20　MANAMIRU1-765　Delete　End
                            mgrFlg = dtos.mgrFlg;
                            psd = data.pubPlanStartDt;
                            ped = data.pubPlanEndDt;
                            ntd = dtos.noticeTypeDiv;
                            import1 = dtos.noticeLevelDiv;
                            //配信数
                            if (data.count) {
                                $("#stuCount").text(data.count + '人');
                            }
                            vm.nowTime();
                        }
                    }
                });
            }
        },
        starTime: function () {
            $(".check_starTime").toggleClass("disNone");
            if (!$(".check_endTime").hasClass("disNone")) {
                $(".check_endTime").addClass("disNone")
            }
        },
        endTime: function () {
            $(".check_endTime").toggleClass("disNone");
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }

        },
        nowTime: function () {
            var date;
            var star;
            var end;
            if (noticeId==null||noticeId==""){
                date = new Date();
                end = new Date().setDate(date.getDate() + 60);
                this.endYear = new Date(end).getFullYear();

            }else {
                date = new Date(psd);
                end = new Date(ped);
                this.endYear = end.getFullYear()
            }
            //NWT　楊　2021/08/20　MANAMIRU1-765　Edit　Start
            star = date.Format("yyyy" + '/' + "MM" + "/" + "dd" + " " + "HH:mm");
            $("#starTime").html(star);
            createSelect(true ,date);
            $("#endTime").html(new Date(end).Format("yyyy" + '/' + "MM" + "/" + "dd" + " " + "HH:mm"));
            //NWT　楊　2021/08/20　MANAMIRU1-765　Edit　End
            createSelect(false , new Date(end));
            build();
        }
    }
});
//2020/11/17 LiYuHuan modifly end
function toF09021() {
    layer.open({
        id: 'f09021',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        fixed: true,
        resize: false,
        area: ['100%', '100%'],
        content: ["../manager/F09021.html"],
        success: function (layero, index) {
            stuList = [];
            stuIdList = [];
            orgIdList = [];
        },
        end: function () {
            for (var i = 0; i < stuList.length; i++) {
                stuIdList.push(stuList[i].stuId);
                orgIdList.push(stuList[i].orgId);
            }
            $("#stuCount").text(stuList.length + '人');
        }
    });
}

$("#go").click(function () {
    //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　Start
    var cont = ue.getContent();
    //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　End
    if ($("#name").val() == null || $("#name").val() == "") {
        layer.alert($.format($.msg.MSGCOMD0001, "件名"));
        return;
    }
    if (($("#name").val().replace(/^\s+|\s+$/g, "")).length > 50) {
        layer.alert($.format($.msg.MSGCOMD0017, '件名', '50'));
        return;
    }
    var conText = ue.getContentTxt().replace('内容を入力','');
    //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　Start
    if (conText == null || conText == "") {
        layer.alert($.format($.msg.MSGCOMD0001, "内容"));
        return;
    }
    if (conText.length > 1000) {
        layer.alert($.format($.msg.MSGCOMD0017, '内容', '1000'));
        return;
    }
    //NWT　楊　2021/07/30　MANAMIRU1-747　Edit　End
    //2020/11/17 LiYuHuan modifly start
    //NWT　楊　2021/08/20　MANAMIRU1-765　Edit　Start
    var start = $("#starTime").text();
    var end = $("#endTime").text();
    //NWT　楊　2021/08/20　MANAMIRU1-765　Edit　End
    if (start == null || start == "") {
        layer.alert($.format($.msg.MSGCOMN0136, "掲載開始"));
        return;
    }
    if (end == null || end == "") {
        layer.alert($.format($.msg.MSGCOMN0136, "掲載終了"));
        return;
    }

    if (start > end) {
        layer.alert($.format($.msg.MSGCOMN0124, "掲載終了", "掲載開始"));
        return;
    }
    //2020/11/17 LiYuHuan modifly end
    var formData = new FormData();
    var object = {};
    var ntitle = $("#name").val();
    if (noticeId == null) {
        var label = document.getElementById("import1").checked;
        if (label) {
            import1 = '2';
        } else {
            import1 = '1';
        }
    }
    var ntd = "2";
    //2020/11/17 LiYuHuan add start
    var ppsd = start;
    var pped = end;
    //2020/11/17 LiYuHuan add end
    var file = $("#btn_file")[0].files[0];
    // if (noticeId == null) {
    //     files = $("#btn_file")[0].files[0];
    // } else {
    //     files = $("#btn_file")[0].files[0];
    // }

    //alert(stuList);
    object.noticeTypeDiv = ntd;
    object.noticeTitle = encodeURIComponent(ntitle).replace(/\%20/g, " ");
    object.noticeCont = encodeURIComponent(cont).replace(/\%20/g, " ");
    object.noticeLevelDiv = import1;
    object.pubPlanStartDt = ppsd;
    object.pubPlanEndDt = pped;
    object.mgrFlg = mgrFlg;
    object.noticeId = noticeId;
    object.orgId = orgId;
    object.stuIdList = stuIdList;
    object.orgIdList = orgIdList;
    object.stu = stuList;
    object.psd = psd;
    object.ped = ped;
    formData.append("file", file);
    formData.append("f09020dto", JSON.stringify(object));
    $.ajax({
        url: ctxPath + '/manager/F09020/go',
        cache: false,
        data: formData,
        type: 'POST',
        processData: false,
        contentType: false,
        success: function (data) {
            if (data.code != 0) {
                var idx = layer.confirm(data.msg, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        layer.close(idx);
                    }
                })
            } else {
                // var idx = layer.confirm($.format($.msg.MSGCOMN0014, "お知らせ配信先"), {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     yes: function () {
                //         layer.close(idx);
                window.location.href = "F09019.html"
                //     }
                // })

            }
        }
    });
});
var maxSize = 5 * 1024 * 1024;//5M
$('#btn_file').change(function () {
    if ($("#btn_file")[0].files[0]!=undefined) {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        var file = $("#btn_file")[0].files[0];
        str = str.slice(str.lastIndexOf("/") + 1);
        var fileName = "";
        for (var i = 0; i < str.split(".").length -1; i++) {
            if (i === 0){
                fileName = fileName + str.split('.')[i];
            } else {
                fileName = fileName + "." + str.split('.')[i];
            }
        }
        var limitImg =[".png", ".jpg", ".jpeg", ".gif", ".bmp"];
        var size = (file.size/1000) < 1024 ? "（" + (file.size/1000).toFixed(2) + "KB）":"（" + (file.size/1024/1000).toFixed(2) + "MB）";
        var tail = '.' + str.split('.')[str.split(".").length -1];
        if (limitImg.indexOf(tail.toLowerCase()) === -1){
            var idx = layer.confirm($.msg.MSGCOMN0078, {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['確認'],
                yes: function () {
                    layer.close(idx);
                }
            })
        }else {
            if (file.size > maxSize) {
                var idx = layer.confirm($.format($.msg.MSGCOMN0077, 'ファイルの最大サイ'), {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        layer.close(idx);
                    }
                })
            }else {
                $("#showPath1").text((fileName.length >= 5?fileName.substr(0,5) + "..." + tail:fileName) + size );
            }
        }
    }else {
        $("#showPath1").html("")
    }
});


var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});
//2020/11/17 LiYuHuan add start
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
    $('#dom_starTime3').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var date =dateTime.toString().split('/');
            var month = date[0].trim();
            var day = date[1].trim();
            var text = $("#endTime").html();
            if (month<10){
                month="0"+month;
            }
            if (day<10){
                day="0"+day;
            }
            text = text.replace(/\d{1,2}\/\d{1,2} /, month + '/' + day + " ");
            $("#endTime").html(text);
        },
    });
    $('#dom_starTime4').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var month = dateTime.toString().trim();
            var text = $("#endTime").html();
            text = text.replace(/\d{1,2}:/, month + ':');
            $("#endTime").html(text);
        },
    });
    $('#dom_starTime5').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var day = dateTime.toString().trim();
            var text = $("#endTime").html();
            text = text.replace(/:\d{1,2}/,  ':'+day);
            $("#endTime").html(text);
        },
        onValueTap: function (event, inst) {
            vm.endTime();
        }
    });
    //NWT　楊　2021/08/20　MANAMIRU1-765　Add　Start
    $('#dom_starTime6').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var year = dateTime.toString().trim();
            var text = $("#starTime").html();
            text = text.replace(/\d{1,4}/,  year);
            $("#starTime").html(text);
        }
    });
    $('#dom_starTime7').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 3,
        formatResult:function(dateTime){
            var year = dateTime.toString().trim();
            var text = $("#endTime").html();
            text = text.replace(/\d{1,4}/,  year);
            $("#endTime").html(text);
        }
    });
    //NWT　楊　2021/08/20　MANAMIRU1-765　Add　End
}
function  hidden() {
    if (!$(".check_starTime").hasClass("disNone")) {
        $(".check_starTime").addClass("disNone")
    }
    if (!$(".check_endTime").hasClass("disNone")) {
        $(".check_endTime").addClass("disNone")
    }
}
function createSelect(flg ,date) {
    var time = "";
    var hours = "";
    var min = "";
    var year_select = "";
    //年選択の範囲
    var starYears = [];
    //今年
    var year = new Date().getFullYear();
    //今年
    var thisYear = new Date().getFullYear();
    //編集画面かどうか判断します
    if (noticeId){
        thisYear = date.getFullYear();
    }
    year = year - 30;
    for (var i = 0; i < 60; i++) {
        starYears.push(year);
        year = year + 1;
    }
    for (var i = 0; i < starYears.length; i++) {
        if (starYears[i] === thisYear){
            year_select += "<option value='" + starYears[i] + " ' selected='true'>" + starYears[i] + "</option>";
        }else {
            year_select += "<option value='" + starYears[i] + "'>" + starYears[i] + "</option>";
        }
    }
    if (!flg){
        $('#dom_starTime7').html("");
        $('#dom_starTime7').html(year_select);
    }else {
        $('#dom_starTime6').html("");
        $('#dom_starTime6').html(year_select);
    }
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
    if (!flg){
        $('#dom_starTime3').html("");
        $('#dom_starTime3').html(time);
    }else {
        $('#dom_starTime').html("");
        $('#dom_starTime').html(time);
    }
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
    if (!flg) {
        $("#dom_starTime5").html(min);
        $("#dom_starTime4").html(hours);
    }else {
        $("#dom_starTime2").html(min);
        $("#dom_starTime1").html(hours);
    }
}

function mGetDate(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}
function getPhotograph() {

    if (noticeId == null || noticeId == "") {
        if ($("#btn_file")[0].files[0]!=undefined) {
            var url = getObjectURL($("#btn_file")[0].files[0]);
            previewImg(url)
        }
    }else {
        if ($("#btn_file")[0].files[0]!=undefined) {
            var url = getObjectURL($("#btn_file")[0].files[0]);
            previewImg(url)
        }else {
            if ($("#showPath1").html()!=""){
                previewImg(path)
            }
        }
    }
}
function getObjectURL(file) {
    var url = null;
    /* window.URL = window.URL || window.webkitURL;*/

    if (window.createObjcectURL != undefined) {
        url = window.createOjcectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
function previewImg(src) {
    /* 2020/12/09 liguangxin add start*/
    var imgHtml = "<img id='preview' src='" + src + "' />";
    img = new Image();
    img.src= src;
    var loadingLoop = layer.load(1, {
        shade: [0.5, '#CCC']
    });
    img.onload = function () {
        if(img.complete){
            layer.close(loadingLoop);
        }
        var w = document.body.scrollWidth*0.8;
        var h = document.body.scrollHeight*0.8;
        var tempWidth;
        var tempHeight;
        if(img.naturalWidth/img.naturalHeight >= w/h){
            if (img.naturalWidth> w){
                tempWidth = w;
                tempHeight = (img.naturalHeight * w)/img.naturalWidth;
            } else{
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }else {
            if (img.naturalHeight>h){
                tempHeight = h;
                tempWidth = (img.naturalWidth * h)/img.naturalHeight;
            } else {
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }
        var height = tempHeight + "px";
        var width = tempWidth + "px";
        layer.open({
            type: 1,
            offset: 'auto',
            closeBtn:1,
            area: [width,height],
            shadeClose: false,
            scrollbar: false,
            title: "",
            content: imgHtml,
            cancel: function () {
            }
        });
    };
    return false;
}
/* 2020/12/09 liguangxin add end*/
//NWT　楊　2021/07/30　MANAMIRU1-747　Edit　Start
UE.Editor.prototype.placeholder = function (justPlainText) {
    var _editor = this;
    _editor.addListener("focus", function () {
        var localHtml = _editor.getPlainTxt();
        if ($.trim(localHtml) === $.trim(justPlainText)) {
            _editor.setContent("");
        }
    });
    _editor.addListener("blur", function () {
        var localHtml = _editor.getContent();
        if (!localHtml) {
            // NWT　楊　2021/08/03　MANAMIRU1-754　Edit　Start
            _editor.setContent('<p style="color: #CDCDCD">' + justPlainText + '</p>');
            // NWT　楊　2021/08/03　MANAMIRU1-754　Edit　End
        }
    });
    _editor.ready(function () {
        _editor.fireEvent("blur");
    });
};
//NWT　楊　2021/07/30　MANAMIRU1-747　Edit　End
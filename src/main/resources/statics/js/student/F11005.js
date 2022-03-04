var param = getParam();
var llu ='';
var b;
var vm = new Vue({
    el: "#page",
    data: {
        dto: '',
        cods:'',
        str: '',
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        submitType:"0",
        blockType:'',
        subjtDiv:'',
        block:'',
        blockNm:'',
        subjt:'',
        subjtNm:''
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
    },
    mounted: function () {
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        var temp = window.localStorage.getItem("submitType");
        this.submitType = temp ? temp : '0';
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
        this.getInfo();
    },
    updated: function () {
        $('#dom_category').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 3,
            onValueTap: function (event, inst) {
                vm.showCategory();
            },
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#dom_category'));
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
            }
        });

        $("#dom_category").change(function () {
            // vm.blockType = $("#dom_category").val();
            // vm.block = $("#dom_category").find("option:selected").text();
            $("#init").text($("#dom_category").find("option:selected").text());
        });
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        $('#block_type_div').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 3,
            onValueTap: function (event, inst) {
                vm.showBlockTypeDiv();
            },
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#block_type_div'));
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
            }
        });
        $("#block_type_div").change(function () {
            vm.block = $("#block_type_div").val().trim();
            vm.blockNm = $("#block_type_div").find("option:selected").text().trim();
            $("#blockType").text($("#block_type_div").find("option:selected").text());
        });

        $('#subjt_div').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            onValueTap: function (event, inst) {
                vm.showSubjtDiv();
            },
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#subjt_div'));
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
            }
        });
        $("#subjt_div").change(function () {
            vm.subjt = $("#subjt_div").val().trim();
            vm.subjtNm = $("#subjt_div").find("option:selected").text().trim();
            $("#subjtDiv").text($("#subjt_div").find("option:selected").text());
        });
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
    },
    methods: {
        //初期表示
        getInfo: function () {
            $.ajax({
                url: ctxPath + "/student/F11005/init",
                type: 'GET',
                data: {
                    id:param.id,
                    submitType:this.submitType,
                },
                dataType: 'json',
                success: function (data) {
                    if(data.code === 0){
                        if (vm.submitType == '0'){
                            if(data.dto){
                                vm.dto = data.dto;
                                if(data.dto.blockDispyNm){
                                    vm.str = data.dto.blockDispyNm;
                                    if (data.dto.blockDispyNm.indexOf(' ') > 0){
                                        var lastStr = data.dto.blockDispyNm.substring(data.dto.blockDispyNm.indexOf(' ') + 1);
                                        $(".memo").html(lastStr);
                                    }
                                }
                                if(data.dto.perfLearnStartTime){
                                    b=data.dto.perfLearnStartTime;
                                    b = new Date(b.replace(/-/g, '/'));
                                    b = b.Format("yyyy年MM月dd日 HH:mm");
                                    $("#starTime").html(b);
                                }
                                else {
                                    var date = new Date();
                                    b = date.Format("yyyy年MM月dd日 HH:mm");
                                    $("#starTime").html(b);
                                }
                                if(data.dto.perfLearnTm){
                                    var tm = data.dto.perfLearnTm;
                                    var str =  Math.floor(tm/60) + '時間' + tm%60 + '分';
                                    $("#time").html(str);
                                }
                                else {
                                    $("#time").html("0時間30分");
                                }
                                if(data.dto.codValue){
                                    $("#init").html(data.dto.codValue);
                                }
                                else {
                                    $("#init").html("60%理解できた");
                                }
                            }
                        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                        }else {
                            if (data.subjtDiv){
                                vm.subjtDiv = data.subjtDiv;
                            }
                            if (data.blockType){
                                vm.blockType = data.blockType;
                            }
                            var date = new Date();
                            b = date.Format("yyyy年MM月dd日 HH:mm");
                            $("#starTime").html(b);
                            $("#init").html("60%理解できた");
                        }
                        if (data.cods){
                            vm.cods = data.cods;
                        }
                        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                    }
                    vm.nowTime();
                    vm.timeSelect();
                    vm.build();
                }
            })
        },
        showCategory: function () {
            $(".check_category").toggleClass("disNone");
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
            if (!$(".block_type_div").hasClass("disNone")) {
                $(".block_type_div").addClass("disNone")
            }
            if (!$(".subjt_div").hasClass("disNone")) {
                $(".subjt_div").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        showTime: function () {
            $(".check_time").toggleClass("disNone");
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            if (!$(".block_type_div").hasClass("disNone")) {
                $(".block_type_div").addClass("disNone")
            }
            if (!$(".subjt_div").hasClass("disNone")) {
                $(".subjt_div").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        starTime: function () {
            $(".check_starTime").toggleClass("disNone");
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".block_type_div").hasClass("disNone")) {
                $(".block_type_div").addClass("disNone")
            }
            if (!$(".subjt_div").hasClass("disNone")) {
                $(".subjt_div").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        showBlockTypeDiv:function () {
            $(".block_type_div").toggleClass("disNone");
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".subjt_div").hasClass("disNone")) {
                $(".subjt_div").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        showSubjtDiv:function () {
            $(".subjt_div").toggleClass("disNone");
            if (!$(".check_category").hasClass("disNone")) {
                $(".check_category").addClass("disNone")
            }
            if (!$(".check_starTime").hasClass("disNone")) {
                $(".check_starTime").addClass("disNone")
            }
            if (!$(".check_time").hasClass("disNone")) {
                $(".check_time").addClass("disNone")
            }
            if (!$(".block_type_div").hasClass("disNone")) {
                $(".block_type_div").addClass("disNone")
            }
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
        showBlock: function(value){
            llu = value;
            if (param.editFlag && param.editFlag === '1'){
                if (value.split(",")[0] == this.dto.learnLevUnds){
                    return true;
                } else {
                    return false
                }
            }else{
                if (value.split(",")[0] == '3'){
                    return true;
                } else {
                    return false
                }
            }
        },
        nowTime: function () {
            var date = null;
            if (param.editFlag && param.editFlag === '1' && vm.dto.perfLearnStartTime != null){
                date = new Date(vm.dto.perfLearnStartTime.replace(/-/g, '/'));
            }else {
                date = new Date();
            }
            var time = "";
            var hours="";
            var min = "";
            for (var i = 1; i <13 ; i++) {
                var days =  mGetDate(date.getFullYear(),i);
                var _year = date.getFullYear();
                for (var j = 1; j <=days ; j++) {
                    var month = i;
                    var day = j;
                    if (month < 10) {
                        month = "0" + month;
                    }
                    if (day < 10) {
                        day = "0" + day;
                    }
                    var weekday = getDay(new Date(_year, (month - 1), day));
                    var selected = '';
                    var text = month + "月" + day + "日" + " " + weekday;
                    if (new Date().getMonth() === (i - 1) && new Date().getDate() === j){
                        text = "今日";
                    }
                    if (date.getMonth() === (i - 1) && date.getDate() === j){
                        selected = 'selected';
                    }
                    time += "<option value='" + i + "/" + j + "' " + selected + ">" + text + "</option>";
                }
            }
            $('#dom_starTime').html(time);

            for (var i = 0; i <24 ; i++) {
                if (i < 10) {
                    i = "0" + i;
                }
                if(date.getHours()==i){
                    hours += "<option value='"+i +" ' selected='true'>"+i+"</option>";
                }
                else {
                    hours += "<option value='" +i +" '>"+i+"</option>";
                }

            }
            for (var i = 0; i <60 ; i++) {
                if (i < 10) {
                    i = "0" + i;
                }
                if (date.getMinutes()==i){
                    min +="<option value='" +i +" ' selected='true'>"+i+"</option>";
                }else {
                    min +="<option value='" +i +" '>"+i+"</option>";
                }
            }
            $("#dom_starTime2").html(min);
            $("#dom_starTime1").html(hours);
        },
        timeSelect: function () {
            var time = "";
            var min = "";
            for (var i = 0 ; i < 60; i += 15) {
                var m = i % 60;

                min += "<option value='" + i + "'>" + m + "</option>";
            }
            for (var i = 0; i < 12; i++) {
                time += "<option value='" + i + "'>" + i + "</option>";
            }
            $('#demo_select').html(time);
            $('#demo_select1').html(min);
            /* 2021/01/28 cuikailin MANAMIRU1-393 start */
            $('#demo_select').find("option").each(function () {
                if (vm.submitType == '0'){
                    if (param.editFlag && param.editFlag === '1' && vm.dto.perfLearnTm !== null){
                        if($(this).val() === Math.floor(vm.dto.perfLearnTm/60) + ''){
                            $(this).attr("selected", true);
                        }
                    }else{
                        if ($(this).val() === Math.floor(vm.dto.stuPlanLearnTm / 60) + '') {
                            $(this).attr("selected", true);
                        }
                    }
                } else {
                    if ($(this).val() === 0 + '') {
                        $(this).attr("selected", true);
                    }
                }
            });
            $('#demo_select1').find("option").each(function () {
                if (vm.submitType == '0'){
                    if (param.editFlag && param.editFlag === '1' && vm.dto.perfLearnTm !== null){
                        if($(this).val() === vm.dto.perfLearnTm % 60 + ''){
                            $(this).attr("selected", true);
                        }
                    }else {
                        if ($(this).val() === vm.dto.stuPlanLearnTm % 60 + '') {
                            $(this).attr("selected", true);
                        }
                    }
                }else {
                    if ($(this).val() === 30 + '') {
                        $(this).attr("selected", true);
                    }
                }
            });
            /* 2021/01/28 cuikailin MANAMIRU1-393 end */
            $("#time").html("0時間30分");
        },
        toF11001: function () {
            return param.editFlag && param.editFlag === '1' ? '' : 'window.location.href=\'F11001.html\'';
        },
        update: function () {
            saveOrUpdate();
        },
        del: function () {
            //確認ダイアログをポップアップ表示する
            var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
                title: '確認',
                closeBtn: 0,
                shadeClose: false,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    $.ajax({
                        url: ctxPath + "/student/F11005/delete",
                        type: 'POST',
                        data: {
                            weeklyPlanId: param.id
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                            } else {
                                //登録完了確認メッセージを表示する。
                                // var index = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                //         layer.close(index);
                                        window.location.reload();
                                //     }
                                // });
                            }
                        }
                    });
                }
            });
        },
        build: function () {
            $('#dom_starTime').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 1,
                formatResult:function(dateTime){
                    scrollSizeCommon("", $('#dom_starTime'));
                    var date =dateTime.toString().split('/');
                    var month = date[0].trim();
                    var day = date[1].trim();
                    var text = $("#starTime").html();
                    text = text.replace(/\d{1,2}月\d{1,2}日/, month + '月' + day + '日');
                    $("#starTime").html(text);
                },
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                }
            });
            $('#dom_starTime1').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 1,
                formatResult:function(dateTime){
                    scrollSizeCommon("", $('#dom_starTime1'));
                    var month = dateTime.toString().trim();
                    var text = $("#starTime").html();
                    text = text.replace(/\d{1,2}:/, month + ':');
                    $("#starTime").html(text);
                },
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                }
            });
            $('#dom_starTime2').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 1,
                formatResult:function(dateTime){
                    scrollSizeCommon("", $('#dom_starTime2'));
                    var day = dateTime.toString().trim();
                    var text = $("#starTime").html();
                    text = text.replace(/:\d{1,2}/, ':' + day);
                    $("#starTime").html(text);
                },
                onValueTap: function (event, inst) {
                    vm.starTime();
                },
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                }
            });
            $('#demo_select').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 3,
                formatResult: function (dateTime) {
                    scrollSizeCommon("", $('#demo_select'));
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
            $('#demo_select1').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 3,
                formatResult: function (dateTime) {
                    scrollSizeCommon("", $('#demo_select1'));
                    var min = dateTime.toString().trim() % 60;

                    var text = $("#time").html();
                    text = text.replace(/\d{1,2}分/, min + "分");
                    $("#time").html(text);
                },
                onValueTap: function (event, inst) {
                    vm.showTime();
                },
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                    event.find('.dwwol').text('分');
                }
            });
            $('#dom_sub').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 3,
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                },
                formatResult: function (dateTime) {
                    scrollSizeCommon("", $('#dom_sub'));
                }
            });
        }
    }
});
//登録を押下して、画面入力した項目より、DBに反映する。
$(".submit").click(function () {
    if (param.editFlag && param.editFlag === '1') {
        window.location.href = ctxPath + '/student/F11008.html';
        return;
    }
    saveOrUpdate();
});
function saveOrUpdate() {
    /* 2021/01/28 cuikailin MANAMIRU1-393 start */
    if (vm.submitType == '1'){
        if (vm.block == "") {
            parent.layer.alert($.format($.msg.MSGCOMN0028, "カテゴリ"));
            return;
        }
        if (vm.subjt == "") {
            parent.layer.alert($.format($.msg.MSGCOMN0028, "教科"));
            return;
        }
    }
    /* 2021/01/28 cuikailin MANAMIRU1-393 end */
    var bdn;
    if(vm.str != '' && vm.str != null){
        var arr = vm.str.split(' ');
        var firstStr = arr[0];
        if($(".memo").val() == "" ||$(".memo").val() == null){
            bdn = firstStr;
        }
        else {
            bdn = firstStr+" "+($(".memo").val());
        }
    }else if(vm.submitType == '1'){
        if ($(".memo").val()!=''){
            bdn = vm.blockNm +' '+ $(".memo").val();
        } else {
            bdn = vm.blockNm;
        }
    }
    else {
        bdn = $(".memo").val();
    }
    var studyTime=$("#time").html().split("時間");
    var hours = studyTime[0];
    var min = studyTime[1].substring(0,studyTime[1].length-1);
    var startTime = new Date($("#starTime").html().replace(/年/g,"/").replace(/月/g,"/").replace(/日/g,""));
    var startYmd = startTime.Format("yyyy-MM-dd");
    if (bdn.length>50){
        var index = layer.confirm($.format($.msg.MSGCOMD0017,'メモ','50'),{
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: [ '確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return false;

    }
    var text = '登録';
    if (param.editFlag){
        text = '更新';
    }
    //確認ダイアログをポップアップ表示する
    var index = layer.confirm($.format($.msg.MSGCOMN0021, text), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + "/student/F11005/submit",
                type: 'GET',
                data: {
                    id:param.id,
                    starTime: startTime,
                    startYmd:startYmd,
                    hours:hours,
                    min:min,
                    memo:bdn,
                    llu:$("#dom_category").val(),
                    /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                    block:vm.block,
                    blockNm:vm.blockNm,
                    subjt:vm.subjt,
                    subjtNm:vm.subjtNm
                    /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                        window.location.href = 'F11004.html'
                        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                        //登録完了確認メッセージを表示する。
                        // var index = layer.confirm($.format($.msg.MSGCOMN0022, text), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     shadeClose: false,
                        //     btn: [ '確認'],
                        //     btn1: function () {
                        //         layer.close(index);
                        //         window.location.reload();
                        //     }
                        // });
                    }
                }
            });
        },
    });
}
function mGetDate(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}
var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});



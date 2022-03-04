function parseUrl() {
    var url = decodeURI(location.href);
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}
var param = getParam();
var v = parseUrl();
if (v['key'] == '1') {
    $("#deleteBtn").css("display", "none");
}
var vm = new Vue({
    el: "#info",
    data: {
        f10102Form: '',
        startDateTime: '',
        endDateTime: '',
        shortStartDateTime: '',
        shortEndDateTime: '',
        maxWeeklyPlanYmdAfter: '',
        shortStartDate: '',
        shortEndDate: '',
        key: v.key
    },
    updated: function () {
        var startTime0 = "";
        var endTimeT = "";
        if (vm.startDateTime == "") {
            $("#time_picker_begin").mobiscroll("setVal", param.fixdInitTime.replace("%3A",":"));
            $("#blockStartTime").text(param.fixdInitTime.replace("%3A",":"));
        } else {
            $("#time_picker_begin").mobiscroll("setVal", vm.shortStartDateTime);
            $("#blockStartTime").text(vm.shortStartDateTime);
        }
        if (vm.endDateTime == "") {
            var strs = param.fixdInitTime.split("%3A");
            var hours = parseInt(strs[0]);
            var minutes = parseInt(strs[1]) + 30;
            if (minutes >= 60){
                minutes = minutes - 60;
                hours = hours + 1;
            }
            var timeend = '';
            if (hours < 10){
                if (minutes < 10){
                    timeend = "0" + hours + ":" + "0" + minutes;
                } else {
                    timeend = "0" + hours + ":" + minutes;
                }
            } else {
                if (minutes < 10){
                    timeend = hours + ":" + "0" + minutes;
                } else {
                    timeend = hours + ":" + minutes;
                }
            }
            $("#time_picker_end").mobiscroll("setVal", timeend);
            $("#blockEndTime").text(timeend);

        } else {
            $("#time_picker_end").mobiscroll("setVal", vm.shortEndDateTime);
            $("#blockEndTime").text(vm.shortEndDateTime);
        }
        //登録
        if (v['singleOrAll'] == '') {
            laydate.render({
                elem: '.oneBtn',
                format: 'yyyy/MM/dd',
                value: vm.maxWeeklyPlanYmdAfter,
                trigger: 'click',
                done: function (date) {
                    $(".onetime").val(date)
                }
            })
            laydate.render({
                elem: '.twoBtn',
                format: 'yyyy/MM/dd',
                value:new Date(),
                trigger: 'click',
                done: function (date) {
                    $(".twotime").val(date)
                }
            })
        }
        //全体修正
        if (v['singleOrAll'] == '1') {
            laydate.render({
                elem: '.oneBtn',
                format: 'yyyy/MM/dd',
                value: vm.f10102Form.blockStartDate,
                trigger: 'click',
                done: function (date) {
                    $(".onetime").val(date)
                }
            })
            laydate.render({
                elem: '.twoBtn',
                format: 'yyyy/MM/dd',
                value:vm.f10102Form.blockEndDate,
                trigger: 'click',
                done: function (date) {
                    $(".twotime").val(date)
                }
            })
        }
        //個別修正
        if (v.singleOrAll == "0"){
            $(".divContent").find("li").removeClass("acive").css("background", "grey").css("color", "white").css("border-color", "white")
        }
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F10102/Init',
                type: 'GET',
                data: {
                    key: v['key'],
                    id: v['id'],
                    singleOrAll: v['singleOrAll'],
                    start: v['start'],
                },
                datatype: 'json',
                success: function (data) {
                    if (data.f10102Form) {
                        vm.f10102Form = data.f10102Form;
                    }
                    if (data.startDateTime) {
                        vm.startDateTime = data.startDateTime;
                    }
                    if (data.endDateTime) {
                        vm.endDateTime = data.endDateTime;
                    }
                    if (data.shortStartDateTime) {
                        vm.shortStartDateTime = data.shortStartDateTime;
                    }
                    if (data.shortEndDateTime) {
                        vm.shortEndDateTime = data.shortEndDateTime;
                    }
                    if (data.maxWeeklyPlanYmdAfter) {
                        vm.maxWeeklyPlanYmdAfter = data.maxWeeklyPlanYmdAfter;
                    }
                }
            });
        }
    }
});
$("#submitOrUpdate").click(function () {
    var blockStartDate = $("input[name=blockStartDate]").val();
    var blockEndDate = $("input[name=blockEndDate]").val();
    var moDwChocFlg = "";
    var tuDwChocFlg = "";
    var weDwChocFlg = "";
    var thDwChocFlg = "";
    var frDwChocFlg = "";
    var saDwChocFlg = "";
    var suDwChocFlg = "";
    var blockStartTime = $("#blockStartTime").text();
    var blockEndTime = $("#blockEndTime").text();
    for (var i = 0; i < $("li").length; i++) {
        if (i == 0) {
            if ($("li").eq(i).hasClass("active")) {
                moDwChocFlg = "1";
            } else {
                moDwChocFlg = "0";
            }
        }
        if (i == 1) {
            if ($("li").eq(i).hasClass("active")) {
                tuDwChocFlg = "1";
            } else {
                tuDwChocFlg = "0";
            }
        }
        if (i == 2) {
            if ($("li").eq(i).hasClass("active")) {
                weDwChocFlg = "1";
            } else {
                weDwChocFlg = "0";
            }
        }
        if (i == 3) {
            if ($("li").eq(i).hasClass("active")) {
                thDwChocFlg = "1";
            } else {
                thDwChocFlg = "0";
            }
        }
        if (i == 4) {
            if ($("li").eq(i).hasClass("active")) {
                frDwChocFlg = "1";
            } else {
                frDwChocFlg = "0";
            }
        }
        if (i == 5) {
            if ($("li").eq(i).hasClass("active")) {
                saDwChocFlg = "1";
            } else {
                saDwChocFlg = "0";
            }
        }
        if (i == 6) {
            if ($("li").eq(i).hasClass("active")) {
                suDwChocFlg = "1";
            } else {
                suDwChocFlg = "0";
            }
        }
    }
    if ($("#start_date").val() == "") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "開始日"));
        return
    }
    if ($("#end_date").val() == "") {
        parent.layer.alert($.format($.msg.MSGCOMD0001, "終了日"));
        return;
    }
    if (moDwChocFlg == "0" && tuDwChocFlg == "0" && weDwChocFlg == "0" && thDwChocFlg == "0" && frDwChocFlg == "0" && saDwChocFlg == "0" && suDwChocFlg == "0") {
        parent.layer.alert($.format($.msg.MSGCOMN0050, "曜日"))
        return;
    }
    var startTime = $("#blockStartTime").text();
    var endTime = $("#blockEndTime").text();
    $.ajax({
        url: ctxPath + '/pop/F10102/SubmitBtn',
        data: {
            start1: blockStartDate,
            end: blockEndDate,
            blockDispyNm: $("#blockDispyNm").text(),
            startTime:startTime,
            endTime: endTime,
            moDwChocFlg: moDwChocFlg,
            tuDwChocFlg: tuDwChocFlg,
            weDwChocFlg: weDwChocFlg,
            thDwChocFlg: thDwChocFlg,
            frDwChocFlg: frDwChocFlg,
            saDwChocFlg: saDwChocFlg,
            suDwChocFlg: suDwChocFlg,
            updDatimeString: vm.f10102Form.updateTime,
            key: v['key'],
            id: v['id'],
            singleOrAll: v['singleOrAll'],
            start: v['start'],
            blockId: vm.f10102Form.blockId
        },
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                parent.layer.alert(data.msg);
            } else {
                var index = parent.layer.getFrameIndex(window.name);
                parent.location.reload();
                parent.layer.close(index);
            }
        }
    });
})
$("#deleteBtn").click(function () {
    var start1 = $("input[name=blockStartDate]").val();
    var startTime = $("#blockStartTime").text();
    var endTime = $("#blockEndTime").text();
    $.ajax({
        url: ctxPath + '/pop/F10102/Delete',
        data: {
            start1: start1,
            startTime: startTime,
            endTime: endTime,
            id: v['id'],
            singleOrAll: v['singleOrAll'],
            start: v['start'],
        },
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                parent.layer.alert(data.msg);
            } else {
                var index = parent.layer.getFrameIndex(window.name);
                parent.location.reload();
                parent.layer.close(index);
            }
        }
    });
})

$("#cancelBtn").click(function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
})
$("#blockStartTime").click(function () {
    if ($(".time_itemO").find('.mbsc-mobiscroll').css('display') == 'block') {
        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none')
        $(".time_itemT").removeClass("borderTop")
        $(".dwwol").addClass("topborder")
        $(".time_itemT").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
        $(".time_itemT").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
    } else {
        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'block')
        $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'none')
        $(".time_itemT").addClass("borderTop")
        $(".dwwol").removeClass("topborder")
        $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
        $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
    }
});
$("#blockEndTime").click(function () {
    if ($(".time_itemT").find('.mbsc-mobiscroll').css('display') == 'block') {
        $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'none');
        $(".time_itemT").removeClass("borderTop");
        $(".dwwol").removeClass("topborder");
        $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)");
        $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)");
    } else {
        $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'block');
        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none');
        $(".time_itemT").removeClass("borderTop");
        $(".dwwol").addClass("topborder");
        $(".time_itemT").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)");
        $(".time_itemT").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)");
    }
});
$("#time_picker_begin").mobiscroll().time({
    theme: "mobiscroll",
    display: 'inline',
    timeFormat: "HH:ii",
    timeWheels: "|HH ii|",
    steps: {
        hour: 1,
        minute: 15
    },
    rows: 4,
    height: 30,
    onChange: function () {
        $(".checktimeO").text($("#time_picker_begin").mobiscroll().val());
    }
});
$("#time_picker_begin").mobiscroll("setVal", new Date());

$("#time_picker_end").mobiscroll().time({
    theme: "mobiscroll",
    display: 'inline',
    timeFormat: "HH:ii",
    timeWheels: "|HH ii|",
    steps: {
        hour: 1,
        minute: 15
    },
    rows: 4,
    height: 30,
    onChange: function () {
        $(".checktimeT").text($("#time_picker_end").mobiscroll().val());
    }
});
$("#time_picker_end").mobiscroll("setVal", new Date());
var divContent = $(".divContent").children()

divContent.click(function () {
    if (v.singleOrAll == "0") {
        return
    } else {
        if ($(this).hasClass("active")) {
            $(this).removeClass("active")
        } else {
            $(this).addClass("active")
        }
    }
})



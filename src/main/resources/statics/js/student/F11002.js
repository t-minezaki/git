var params = getParam();
var initFlag = false;
var vm = new Vue({
    el: "#page",
    data: {
        blockType: '',
        block: '',
        blockTypeDivList: [],
        planYmd: '',
        planEndYmd: '',
        scheduledDto: null,
        updStr: ''
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {
        if (!initFlag){
            $('#dom_category').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 5,
                height: 30,
                formatResult: function (dateTime, event) {
                    scrollSizeCommon("", $('#dom_category'))
                },
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                },
                onValueTap: function (event, inst) {
                    vm.showCategory();
                }
            });
            $("#init").text($("#dom_category").find("option:selected").text());
            $("#dom_category").change(function () {
                vm.blockType = parseInt($("#dom_category").val());
                vm.block = $("#dom_category").find("option:selected").text();
                $("#init").text($("#dom_category").find("option:selected").text());
            });
            initFlag = true;
        }
        $(".time_item time_item1").toggleClass("disNone");
        $(".time_item time_itemO").toggleClass("disNone");
    },
    methods: {
        getInfo: function () {
            //初期表示
            $.ajax({
                url: ctxPath + "/student/F11002/init",
                type: 'GET',
                data: {
                    stuFixedScheduleId: params.id ? params.id : -1,
                    typeDiv: params.typeDiv ? params.typeDiv : '9999',
                    ymd: params.ymd ? new Date(params.ymd.replace(/-/g, '/') + ' 00:00:00') : new Date()
                },
                dataType: 'json',
                success: function (data) {
                    if (data.scheduledDto){
                        vm.scheduledDto = data.scheduledDto;
                        //日付
                        vm.planYmd = vm.scheduledDto.blockStartDate;
                        vm.planEndYmd = vm.scheduledDto.blockEndDate;
                        //曜日
                        for (var i = 0; i < $("li").length; i++) {
                            if (i === 2) {
                                if(vm.scheduledDto.moDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 3) {
                                if(vm.scheduledDto.tuDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 4) {
                                if(vm.scheduledDto.weDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 5) {
                                if(vm.scheduledDto.thDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 6) {
                                if(vm.scheduledDto.frDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 7) {
                                if(vm.scheduledDto.saDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                            if (i === 8) {
                                if(vm.scheduledDto.suDwChocFlg === '1'){
                                    $("li").eq(i).addClass("active");
                                }
                            }
                        }
                        //開始時間
                        $("#blockStartTime").html(vm.scheduledDto.blockStartTime.substring(11, 16));
                        //終了時間
                        $("#blockEndTime").html(vm.scheduledDto.blockEndTime.substring(11, 16));
                        //ブロック表示名
                        vm.blockType = vm.scheduledDto.blockId;
                    }else {
                        //開始時間
                        $("#blockStartTime").html("07:00");
                        //終了時間
                        $("#blockEndTime").html("15:50");
                    }
                    if (data.updStr){
                        vm.updStr = data.updStr;
                    }
                    if (data.mstBlock){
                        vm.blockTypeDivList = data.mstBlock;
                        if (!data.scheduledDto){
                            for (var i = 0; i < vm.blockTypeDivList.length; i++) {
                                if (vm.blockTypeDivList[i].blockDispyNm === "学校") {
                                    vm.blockType = vm.blockTypeDivList[i].id;
                                }
                            }
                        }
                    }
                    vm.render();
                }
            })
        },
        showCategory: function () {
            if ($(".check_category").find('.mbsc-mobiscroll').css('display') == 'block') {
                $(".check_category").find('.mbsc-mobiscroll').css('display', 'none')
            } else {
                $(".check_category").find('.mbsc-mobiscroll').css('display', 'block')
            }
            if ($(".time_itemO").find('.mbsc-mobiscroll').css('display') == 'block') {
                $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none')
                $(".time_itemT").removeClass("borderTop");
                $(".dwwol").addClass("topborder");
                $(".time_itemT").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
                $(".time_itemT").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
            }
            if ($(".time_itemT").find('.mbsc-mobiscroll').css('display') == 'block') {
                $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'none');
                $(".time_itemT").removeClass("borderTop");
                $(".dwwol").removeClass("topborder");
                $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)");
                $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)");
            }
        },
        del: function () {
            //ブロック開始時間
            var startTime = $("#blockStartTime").text();
            //ブロック終了時間
            var endTime = $("#blockEndTime").text();
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
                        url: ctxPath + '/student/F11002/delete',
                        data: {
                            stuFixedScheduleId: params.id ? params.id : -1,
                            typeDiv: params.typeDiv ? params.typeDiv : '9999',
                            ymd: params.ymd ? new Date(params.ymd + ' 00:00:00') : new Date(),
                            blockDispyNm: $("#init").text(),
                            blockId: vm.blockType,
                            startTime: new Date(params.ymd + ' ' + startTime + ':00'),
                            endTime: new Date(params.ymd + ' ' + endTime + ':00')
                        },
                        type: "POST",
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
                    })
                }
            });
        },
        update: function () {
            saveOrUpdate();
        },
        render: function () {
            laydate.render({
                elem: '#start_date',
                format: 'yyyy/MM/dd',
                value: this.planYmd !== '' ? new Date(this.planYmd.replace(/-/g, '/')) : null,
                eventElem: '.oneBtn',
                trigger: 'click',
                done: function (date) {
                    // $(".onetime").val(date.replace(/(\d{4})\/(\d{2})\/(\d{2})\s\S*/, '$1/$2/$3'));
                    if ($(".check_category").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".check_category").find('.mbsc-mobiscroll').css('display', 'none')
                    }
                    if ($(".time_itemO").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none')
                        $(".time_itemT").removeClass("borderTop")
                        $(".dwwol").addClass("topborder")
                        $(".time_itemT").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
                        $(".time_itemT").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
                    }
                    if ($(".time_itemT").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'none');
                        $(".time_itemT").removeClass("borderTop");
                        $(".dwwol").removeClass("topborder");
                        $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)");
                        $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)");
                    }
                }
            });
            laydate.render({
                elem: '#end_date',
                format: 'yyyy/MM/dd',
                value: this.planEndYmd !== '' ? new Date(this.planEndYmd.replace(/-/g, '/')) : null,
                eventElem: '.twoBtn',
                trigger: 'click',
                done: function (date) {
                    // $(".twotime").val(date.replace(/(\d{4})\/(\d{2})\/(\d{2})\s\S*/, '$1/$2/$3'));
                    if ($(".check_category").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".check_category").find('.mbsc-mobiscroll').css('display', 'none')
                    }
                    if ($(".time_itemO").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none')
                        $(".time_itemT").removeClass("borderTop")
                        $(".dwwol").addClass("topborder")
                        $(".time_itemT").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
                        $(".time_itemT").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
                    }
                    if ($(".time_itemT").find('.mbsc-mobiscroll').css('display') == 'block') {
                        $(".time_itemT").find('.mbsc-mobiscroll').css('display', 'none');
                        $(".time_itemT").removeClass("borderTop");
                        $(".dwwol").removeClass("topborder");
                        $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)");
                        $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)");
                    }
                }
            });
            $("#time_picker_begin").mobiscroll().time({
                theme: "mobiscroll",
                display: 'inline',
                timeFormat: "HH:ii",
                timeWheels: "|HH ii|",
                steps: {
                    hour: 1,
                    minute: 5
                },
                rows: 5,
                height: 30,
                onChange: function () {
                    $(".checktimeO").text($("#time_picker_begin").mobiscroll().val());
                },
                onValueTap: function (event, inst) {
                    blockStartTime();
                }
            });
            $("#time_picker_begin").mobiscroll("setVal", $("#blockStartTime").text());

            $("#time_picker_end").mobiscroll().time({
                theme: "mobiscroll",
                display: 'inline',
                timeFormat: "HH:ii",
                timeWheels: "|HH ii|",
                steps: {
                    hour: 1,
                    minute: 5
                },
                rows: 5,
                height: 30,
                onChange: function () {
                    $(".checktimeT").text($("#time_picker_end").mobiscroll().val());
                },
                onValueTap: function (event, inst) {
                    blockEndTime();
                }
            });
            $("#time_picker_end").mobiscroll("setVal", $("#blockEndTime").text());

        },
        isEditMode: function () {
            return params.id ? true : false;
        },
        toF11004: function () {
            return params.id ? '' : 'window.location.href=\'F11004.html\'';
        }
    }
});
//登録を押下して
$(".submit").click(function () {
    if (params.id) {
        window.location.href = ctxPath + '/student/F11008.html';
        return;
    }
    saveOrUpdate();
});

function saveOrUpdate() {
    //ブロック開始日
    var blockStartDate = $("input[name=blockStartDate]").val();
    //ブロック終了日
    var blockEndDate = $("input[name=blockEndDate]").val();
    //月曜日選択フラグ
    var moDwChocFlg = "";
    //火曜日選択フラグ
    var tuDwChocFlg = "";
    //水曜日選択フラグ
    var weDwChocFlg = "";
    //木曜日選択フラグ
    var thDwChocFlg = "";
    //金曜日選択フラグ
    var frDwChocFlg = "";
    //土曜日選択フラグ
    var saDwChocFlg = "";
    //日曜日選択フラグ
    var suDwChocFlg = "";
    for (var i = 0; i < $("li").length; i++) {
        if (i == 2) {
            if ($("li").eq(i).hasClass("active")) {
                moDwChocFlg = "1";
            } else {
                moDwChocFlg = "0";
            }
        }
        if (i == 3) {
            if ($("li").eq(i).hasClass("active")) {
                tuDwChocFlg = "1";
            } else {
                tuDwChocFlg = "0";
            }
        }
        if (i == 4) {
            if ($("li").eq(i).hasClass("active")) {
                weDwChocFlg = "1";
            } else {
                weDwChocFlg = "0";
            }
        }
        if (i == 5) {
            if ($("li").eq(i).hasClass("active")) {
                thDwChocFlg = "1";
            } else {
                thDwChocFlg = "0";
            }
        }
        if (i == 6) {
            if ($("li").eq(i).hasClass("active")) {
                frDwChocFlg = "1";
            } else {
                frDwChocFlg = "0";
            }
        }
        if (i == 7) {
            if ($("li").eq(i).hasClass("active")) {
                saDwChocFlg = "1";
            } else {
                saDwChocFlg = "0";
            }
        }
        if (i == 8) {
            if ($("li").eq(i).hasClass("active")) {
                suDwChocFlg = "1";
            } else {
                suDwChocFlg = "0";
            }
        }
    }
    //画面選択した「開始時間」項目のチェック処理を行う
    if (blockStartDate > blockEndDate) {
        parent.layer.alert($.format($.msg.MSGCOMN0024, "ブロック終了日", "ブロック開始日"));
        return
    }
    //画面．曜日いずれも選択していない場合
    if (moDwChocFlg == "0" && tuDwChocFlg == "0" && weDwChocFlg == "0" && thDwChocFlg == "0" && frDwChocFlg == "0" && saDwChocFlg == "0" && suDwChocFlg == "0") {
        parent.layer.alert($.format($.msg.MSGCOMN0050, "曜日"));
        return;
    }
    //ブロック開始時間
    var startTime = $("#blockStartTime").text();
    //ブロック終了時間
    var endTime = $("#blockEndTime").text();
    //画面．ブロック終了時間＞画面．ブロック開始時間であること
    // if (startTime > endTime) {
    //     blockStartDate = new Date(blockStartDate + ' 00:00:00');
    //     blockStartDate = blockStartDate.setDate(blockStartDate.getDate() + 1);
    //     parent.layer.alert($.format($.msg.MSGCOMN0048, "ブロック終了時間", "ブロック開始時間"));
    //     return;
    // }
    var text = params.id ? '更新' : '登録';
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
                url: ctxPath + '/student/F11002/SubmitBtn',
                data: {
                    stuFixedScheduleId: params.id ? params.id : -1,
                    typeDiv: params.typeDiv ? params.typeDiv : '9999',
                    ymd: params.ymd ? new Date(params.ymd.replace(/-/g, '/') + ' 00:00:00') : new Date(),
                    startDate: blockStartDate,
                    endDate: blockEndDate,
                    blockDispyNm: $("#init").text(),
                    blockId: vm.blockType,
                    startTime: blockStartDate + " " + startTime,
                    endTime: blockEndDate + " " + endTime,
                    moDwChocFlg: moDwChocFlg,
                    tuDwChocFlg: tuDwChocFlg,
                    weDwChocFlg: weDwChocFlg,
                    thDwChocFlg: thDwChocFlg,
                    frDwChocFlg: frDwChocFlg,
                    saDwChocFlg: saDwChocFlg,
                    suDwChocFlg: suDwChocFlg,
                    updStr: vm.updStr
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        //登録完了確認メッセージを表示する。
                        // var index = layer.confirm($.format($.msg.MSGCOMN0022, text), {
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
            })
        }
    });
}

function blockStartTime() {
    if ($(".check_category").find('.mbsc-mobiscroll').css('display') == 'block') {
        $(".check_category").find('.mbsc-mobiscroll').css('display', 'none')
    }
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
};
function blockEndTime() {
    if ($(".check_category").find('.mbsc-mobiscroll').css('display') == 'block') {
        $(".check_category").find('.mbsc-mobiscroll').css('display', 'none').attr('margin-bottom', '50px')
    }
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
};

var divContent = $(".divContent").children();
divContent.click(function () {
    if ($(this).hasClass("active")) {
        $(this).removeClass("active")
    } else {
        $(this).addClass("active")
    }
});




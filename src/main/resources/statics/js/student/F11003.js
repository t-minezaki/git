var params = getParam();
//大分類初期化フラグ
var flg = true;
//その他の入力項目初期化フラグ
var initFlag = true;
//小分類初期化フラグ
var categoryInitFlag = true;

var vm = new Vue({
    el: "#page",
    data: {
        blockType: '',
        block: '',
        mstBlock: [],
        mstBlockList: [],
        id: '',
        years: [],
        blockPicDiv: '',
        updDatime: '',
        weeklyPlan: null,
        upplevBlockId: 0,
        diyBlockName: '',
        nowYear: new Date().getFullYear()
    },
    mounted: function () {
        //初期化
        this.getInfo();
    },
    updated: function () {
        if (flg) {
            flg = false;
            var buttons = $("#topLine button");
            for (var i = 0; i < vm.mstBlockList.length; i++) {
                //すべてのグレースケール表示
                $(buttons[i]).removeClass("green");
                $(buttons[i]).addClass("grey");
                if (vm.weeklyPlan != null && vm.weeklyPlan.blockTypeDiv) {
                    //編集する
                    if ($(buttons[i]).attr('category') === vm.weeklyPlan.blockTypeDiv) {
                        $(buttons[i]).removeClass("grey");
                        $(buttons[i]).addClass("green");
                    }
                } else if (i === 0) {
                    //新規作成時、最初のボタンはデフォルトで選択されています
                    $(buttons[i]).removeClass("grey");
                    $(buttons[i]).addClass("green");
                }
            }
        }

        $('#demo_select').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            formatResult: function (dateTime) {
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
        $('#demo_select1').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            formatResult: function (dateTime) {
                scrollSizeCommon("", $('#demo_select1'))
                var min = dateTime.toString().trim() % 60;
                var text = $("#time").html();
                text = text.replace(/\d{1,2}分/, min + "分");
                $("#time").html(text);
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
                event.find('.dwwol').text('分');
            },
            onValueTap: function (event, inst) {
                vm.showTime();
            }
        });

        $('#dom_category').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 5,
            height: 30,
            onInit: function (event, inst) {
                if (categoryInitFlag && categoryInitFlag === true && vm.weeklyPlan && vm.weeklyPlan.blockDispyNm) {
                    categoryInitFlag = false;
                    var blockName = vm.weeklyPlan.blockDispyNm.split(' ')[0];
                    var diyFlag = true;
                    for (var i = 0; i < vm.mstBlock.length; i++) {
                        if (vm.mstBlock[i].blockDispyNm === blockName) {
                            $('option[value="' + vm.mstBlock[i].blockTypeDiv + '"]').prop('selected', true);
                            diyFlag = false;
                            break;
                        }
                    }
                    $("#init").html(blockName);
                    if (diyFlag) {
                        $('option[value="' + vm.mstBlock[0].blockTypeDiv.split(',')[0] + ',-1"]').prop('selected', true);
                        vm.diyBlockName = blockName;
                    }
                } else {
                    $("#init").html($("#dom_category option:selected").html());
                    vm.diyBlockName = '';
                }
            },
            formatResult: function (dateTime, event) {
                scrollSizeCommon("", $('#dom_category'))
            },
            onMarkupReady: function (event, inst) {
                scrollSizeCommon(event, "");
                event.find('.other-input').val(vm.diyBlockName);
                event.find('.other-input').on('click', function () {
                    $(this).parent().click();
                })
            },
            onValueTap: function (event, inst) {
                if (!$($($(event[0]).find(".dw-i")).find("input")).hasClass("other-input")){
                    vm.showCategory();
                }
            }
        });

        $("#dom_category").change(function () {
            vm.blockType = $("#dom_category").val().trim();
            vm.block = $("#dom_category").find("option:selected").text().trim();
            var otherInput = $('.other-input');
            if (vm.blockType.split(',')[1] === '-1' && otherInput.length === 1) {
                if (otherInput.val() !== '') {
                    $("#init").text($('.other-input').val());
                } else {
                    $("#init").text(vm.block);
                }
                otherInput.on('input', function () {
                    if (otherInput.val() === '') {
                        $("#init").text(vm.block);
                    } else {
                        $("#init").text($('.other-input').val());
                    }
                });
                otherInput.attr('readonly', false);
            } else {
                $("#init").text(vm.block);
                otherInput.attr('readonly', true);
            }
        });
        $(".selectBtn").val(vm.nowYear);
    },
    methods: {
        //初期表示
        getInfo: function (newId) {
            $.ajax({
                url: ctxPath + "/student/F11003/init",
                type: 'GET',
                data: {
                    id: newId,
                    initFlag: initFlag,
                    weeklyPlanId: params.id ? params.id : -1
                },
                dataType: 'json',
                success: function (data) {
                    if (data.mstBlockList) {
                        vm.mstBlockList = data.mstBlockList;
                    }
                    if (data.mstBlock) {
                        vm.mstBlock = data.mstBlock;
                        var firstBlock = vm.mstBlock[0];
                        var otherBlock = {
                            blockDispyNm: 'その他',
                            blockPicDiv: firstBlock.blockPicDiv,
                            blockTypeDiv: firstBlock.blockTypeDiv,
                            cretDatime: firstBlock.cretDatime,
                            cretUsrId: firstBlock.cretUsrId,
                            delFlg: firstBlock.delFlg,
                            id: -1,
                            stuId: firstBlock.stuId,
                            updDatime: firstBlock.updDatime,
                            updUsrId: firstBlock.updUsrId,
                            upplevBlockId: firstBlock.upplevBlockId
                        };
                        //上層ブロックID
                        vm.upplevBlockId = firstBlock.upplevBlockId;
                        //ブロック画像
                        vm.blockPicDiv = firstBlock.blockPicDiv;
                        //その他のオプション
                        vm.mstBlock.push(otherBlock);
                        for (var i = 0; i < vm.mstBlock.length; i++) {
                            vm.mstBlock[i].blockTypeDiv = vm.mstBlock[i].blockTypeDiv + ',' + vm.mstBlock[i].id
                        }
                        vm.mstBlock.unshift({blockTypeDiv: '', blockDispyNm: '選択する'});
                    }
                    if (data.updDatime) {
                        vm.updDatime = data.updDatime;
                    }
                    if (data.weeklyPlan) {
                        vm.weeklyPlan = data.weeklyPlan;
                        vm.blockType = data.weeklyPlan.blockTypeDiv;
                        if (vm.weeklyPlan.blockDispyNm.indexOf(' ') > 0) {
                            $('.memo').val(vm.weeklyPlan.blockDispyNm.substring(vm.weeklyPlan.blockDispyNm.indexOf(' ') + 1));
                        }
                    } else {
                        vm.weeklyPlan = null;
                    }
                    var year = new Date().getFullYear() - 30;
                    for (var i = 0; i < 61; i++) {
                        vm.years.push(year);
                        year = year + 1;
                    }
                    if (initFlag) {
                        vm.nowTime();
                        vm.timeSelect();
                        initFlag = false;
                    }
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
            $(".layui-layer-iframe", parent.document).css("top", stop);
        },
        nowTime: function () {
            var date;
            if (params.id && this.weeklyPlan.planLearnStartTime) {
                date = new Date(this.weeklyPlan.planLearnStartTime.replace(/-/g, '/'));
                this.weeklyPlan.planLearnStartTime = null;
            } else {
                if ($(".selectBtn").val() == null || $(".selectBtn").val() == new Date().getFullYear().toString()) {
                    date = new Date();
                } else {
                    date = new Date($(".selectBtn").val());
                }
            }
            a = date.Format("MM" + "月" + "dd" + "日" + " " + "HH:mm");
            $("#starTime").html(a);
            createSelect(date);
            build();
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
                if (vm.weeklyPlan && $(this).val() === Math.floor(vm.weeklyPlan.stuPlanLearnTm / 60) + '') {
                    $(this).attr("selected", true);
                }
            });
            $('#demo_select1').find("option").each(function () {
                if (vm.weeklyPlan) {
                    if (vm.weeklyPlan.stuPlanLearnTm % 60 + '' === $(this).val()) {
                        $(this).attr("selected", true);
                    }
                } else {
                    if ($(this).val() == 30) {
                        $(this).attr("selected", true);
                    }
                }
            });
            $("#time").html("0時間30分");
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
                        url: ctxPath + '/student/F11003/delete',
                        data: {
                            weeklyPlanId: params.id ? params.id : -1
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
        isEditMode: function () {
            return params.id ? true : false;
        },
        toF11004: function () {
            return params.id ? '' : 'window.location.href=\'F11004.html\'';
        }
    }
});

function createSelect(date) {
    var time = "";
    var hours = "";
    var min = "";
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
            var index = new Date(date.getFullYear(), i - 1, j);
            var selected = '';
            var weekday = getDay(new Date(_year, (month - 1), day));
            var text = month + "月" + day + "日" + " " + weekday;
            if (date.getFullYear().toString() === new Date().getFullYear().toString()
                && new Date().getMonth() === (i - 1) && new Date().getDate() === j) {
                text = "今日";
            }
            if (date.getMonth() === (i - 1) && date.getDate() === j) {
                selected = 'selected';
            }
            time += "<option value='" + i + "/" + j + "' " + selected + ">" + text + "</option>";
        }
    }
    $('#dom_starTime').html("");
    $('#dom_starTime').html(time);

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

function build() {
    $('#dom_starTime').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event, "");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime'));
            var date = dateTime.toString().split('/');
            var month = date[0].trim();
            var day = date[1].trim();
            var text = $("#starTime").html();
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            text = text.replace(/\d{1,2}月\d{1,2}日/, month + '月' + day + '日');
            $("#starTime").html(text);
        },
    });
    $('#dom_starTime1').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event, "");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime1'));
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
        rows: 5,
        height: 30,
        onMarkupReady: function (event, inst) {
            scrollSizeCommon(event, "");
        },
        formatResult: function (dateTime) {
            scrollSizeCommon("", $('#dom_starTime2'));
            var day = dateTime.toString().trim();
            var text = $("#starTime").html();
            text = text.replace(/:\d{1,2}/, ':' + day);
            $("#starTime").html(text);
        },
        onValueTap: function (event, inst) {
            vm.starTime();
        }
    });
}

//登録を押下して、画面入力した項目より、DBに反映する。
$(".submit").click(function () {
    if (params.id) {
        window.location.href = ctxPath + '/student/F11008.html';
        return;
    }
    saveOrUpdate();
});

function saveOrUpdate() {
    var studyTime = $("#time").html().split("時間");
    var hours = studyTime[0];
    var min = studyTime[1].substring(0, studyTime[1].length - 1);
    if (vm.blockType === "") {
        var index = layer.confirm($.format($.msg.MSGCOMD0001, '項目'), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return;
    }
    if (vm.blockType.split(',')[1] === '-1') {
        if ($('.other-input').val().trim() === '') {
            var index = layer.alert($.format($.msg.MSGCOMD0001, "枠で"), {
                title: '確認',
                closeBtn: 0,
                btn: ["確認"],
                btn1: function () {
                    index = layer.close(index);
                }
            });
            return;
        }
    }
    var memo = $(".memo").val().trim();
    var startTime = new Date($(".selectBtn").val() + "/" + $("#starTime").html().replace(/月/g, "/").replace(/日/g, ""));
    var startYmd = startTime.Format("yyyy-MM-dd");
    var nowTime = new Date();
    //画面選択した「開始時間」項目のチェック処理を行う
    // if (startTime < nowTime) {
    //     var index = layer.confirm($.format($.msg.MSGCOMN0048, '開始時間', 'システム時間'), {
    //         title: '確認',
    //         closeBtn: 0,
    //         shadeClose: false,
    //         btn: ['確認'],
    //         btn1: function () {
    //             layer.close(index);
    //         }
    //     });
    //     return false;
    // }
    //画面入力した「メモ」項目のチェックを行う
    if (memo.length > 50) {
        var index = layer.confirm($.format($.msg.MSGCOMD0017, 'メモ', '50'), {
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return false;

    }
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
                url: ctxPath + "/student/F11003/submit",
                type: 'POST',
                data: {
                    weeklyPlanId: params.id ? params.id : -1,
                    blockCd: vm.blockType,
                    starTime: startTime,
                    startYmd: startYmd,
                    hours: hours,
                    min: min,
                    memo: memo,
                    blockNm: $("#init").html().replace(/ /g, '　'),
                    updDatime: vm.updDatime,
                    upplevBlockId: vm.upplevBlockId,
                    blockPicDiv: vm.blockPicDiv
                },
                dataType: "json",
                success: function (data) {
                    if (data.code !== 0) {
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
            });
        }
    });
}

function change(newId) {
    if ($("#category" + newId).hasClass('green')) {
        return;
    }
    for (var i = 0; i < vm.mstBlockList.length; i++) {
        if ($("#topLine button")[i].value != newId) {
            $($("#topLine button")[i]).removeClass("green");
            $($("#topLine button")[i]).addClass("grey");
        } else {
            $($("#topLine button")[i]).removeClass("grey");
            $($("#topLine button")[i]).addClass("green");
        }
    }
    if (!$(".check_category").hasClass("disNone")) {
        $(".check_category").addClass("disNone")
    }
    vm.blockType = '';
    vm.getInfo(newId)
}

function mGetDate(year, month) {
    var d = new Date(year, month, 0);
    return d.getDate();
}

function hidden() {
    if (!$(".check_starTime").hasClass("disNone")) {
        $(".check_starTime").addClass("disNone")
    }
    if (!$(".check_category").hasClass("disNone")) {
        $(".check_category").addClass("disNone")
    }
    if (!$(".check_time").hasClass("disNone")) {
        $(".check_time").addClass("disNone")
    }
}

var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
    var height = $('.memo').offset().top - ($(window).height() - ($('.memo').outerHeight() * 2));
    $(window).scrollTop(height);
});
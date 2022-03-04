var wppId = null;
var param = getParam();
var vm = new Vue({
    el: '#info',
    data: {
        wpp: '',
        list: [],
        texDff: '',
        weekday: '',
        planLearnStartTime: '',
        planLearnEndTime: '',
        learnLevUnds: '',
        updateTime: '',
        perfLearnYmd: '',
        perfLearnTime: '',
        perfLearnEndTime: '',
        shortTime: '',
        preEndTimeCheck:'',
        /* 2020/11/12 V9.0 cuikailin add start */
        flag :1,
        blockList:[],
        eduList:[],
        block:'',
        blockNm:'',
        subjt:'',
        subjtNm:'',
        memoText:'',
        subjtList: []
        /* 2020/11/12 V9.0 cuikailin add end */
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {
        laydate.render({
            elem: '#startInput',
            format: 'yyyy/MM/dd',
            done: function (value, date) {
                $("#startInput").val(value);
            }
        });

        $(".time_st_con").find("li").click(function () {
            var lis = $(".time_st_con").find("li");
            $(".time_st_con").find("li").removeClass("active");
            for (var i = 0; i < lis.length; i++) {
                var acceptColor = lis.eq(i).attr("acceptColor");
                lis.eq(i).css("background", "");
                lis.eq(i).css("color", acceptColor);
            }
            $(this).css("background", $(this).attr("acceptColor"));
            $(this).css("color", "#fff");
            $(this).addClass("active");
        });
        /* 2020/11/13 V9.0 cuikailin add start */
        if(vm.flag == 0){
            $('#dom_sub').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 5,
                theme: 'mobiscroll',
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                },
                formatResult: function (dateTime) {
                    scrollSizeCommon("", $('#dom_sub'))
                },
                onValueTap: function (event, inst) {
                    vm.showSubjt();
                }
            });
            /* 2020/11/20 cuikailin V9.0 modify start */
            //$("#subjt").text($("#dom_sub").find("option:selected").text().trim());
            /* 2020/11/20 cuikailin V9.0 modify end */

            $("#dom_sub").change(function () {
                vm.subjt = $("#dom_sub").val().trim();
                vm.subjtNm = $("#dom_sub").find("option:selected").text().trim();
                $("#subjt").text($("#dom_sub").find("option:selected").text().trim());
            });

            $('#dom_block').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 3,
                theme: 'mobiscroll',
                onMarkupReady: function (event, inst) {
                    scrollSizeCommon(event, "");
                },
                formatResult: function (dateTime) {
                    scrollSizeCommon("", $('#dom_block'))
                },
                onValueTap: function (event, inst) {
                    vm.showBlock();
                    vm.showSubjt();
                }
            });
            /* 2020/11/20 cuikailin V9.0 modify start */
            //$("#block").text($("#dom_block").find("option:selected").text().trim());
            /* 2020/11/20 cuikailin V9.0 modify end */

            $("#dom_block").change(function () {
                vm.block = $("#dom_block").val().trim();
                vm.blockNm = $("#dom_block").find("option:selected").text().trim();
                $("#block").text($("#dom_block").find("option:selected").text().trim());
            });
            /* 2020/11/13 V9.0 cuikailin end */
        }

    },
    methods: {
        getInfo: function (id) {
            if (wppId == null) {
                wppId = param.id;
            } else {
                wppId = id;
            }
            /* 2020/11/13 V9.0 cuikailin modify start */
            var flag = null;
            if (param.flag){
                flag = param.flag;
            } else if(param.id == -1){
                flag = 0;
            } else {
                flag = 1;
            }
            /* 2020/11/13 V9.0 cuikailin modify end */
            $.ajax({
                url: ctxPath + '/pop/F10307/init',
                data:{
                    'id':wppId,
                    'flag':flag
                },
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    vm.flag = data.flag;
                    if (data.flag == 1) {
                        if (data.msg != "success") {
                            parent.layer.alert(data.msg);
                        }
                        if (data.wpp) {
                            vm.wpp = data.wpp;
                            if (data.wpp.perfYmd) {
                                $("#perfDiv").show();
                            } else {
                                $("#planDiv").show();
                            }
                            var text = data.wpp.blockDispyNm+data.wpp.subjtNm;
                            $("#smallBlockRPWV").html(text);
                        }
                        if (data.subjtList){
                            vm.subjtList = data.subjtList;
                        }
                        if (data.texDff) {
                            vm.texDff = data.texDff;
                        }
                        //曜日
                        if (data.weekday) {
                            vm.weekday = data.weekday;
                        }
                        //計画学習開始時間
                        if (data.planLearnStartTime) {
                            vm.planLearnStartTime = data.planLearnStartTime;
                        }
                        //実績年月日
                        if (data.perfLearnYmd) {
                            vm.perfLearnYmd = data.perfLearnYmd;
                        }
                        //実績開始時間
                        if (data.perfLearnTime) {
                            vm.perfLearnTime = data.perfLearnTime;
                        }
                        //計画学習終了時間
                        if (data.planLearnEndTime) {
                            vm.planLearnEndTime = data.planLearnEndTime;
                        }
                        // 学習理解度
                        vm.learnLevUnds = data.wpp.learnLevUnds;
                        if (data.list) {
                            vm.list = data.list;
                        }
                        //実績終了時間
                        if (data.perfLearnEndTime) {
                            vm.perfLearnEndTime = data.perfLearnEndTime;
                        }
                        //更新日時
                        vm.updateTime = data.updateTime;
                    /* 2020/11/12 V9.0 cuikailin add start */
                    }else {
                        if (data.list) {
                            vm.list = data.list;
                        }
                        if (data.blockList) {
                            vm.blockList = data.blockList;
                        }
                        if (data.eduList) {
                            vm.eduList = data.eduList;
                        }
                        //実績年月日
                        if (param.perfLearnYmd) {
                            vm.perfLearnYmd = decodeURIComponent(param.perfLearnYmd);
                        }
                        //実績開始時間
                        if (param.perfLearnTime) {
                            vm.perfLearnTime = decodeURIComponent(param.perfLearnTime);
                        }
                        //実績終了時間
                        if (param.perfLearnEndTime) {
                            vm.perfLearnEndTime = decodeURIComponent(param.perfLearnEndTime);
                        }
                    }
                    /* 2020/11/12 V9.0 cuikailin add end */
                    /* 2020/11/12 V9.0 cuikailin modify start */
                    var str1 = "";
                    var s = "";
                    var str2 = "";
                    for (var i = 0; i < 24; i++) {
                        for (var j = 0; j < 60; j += 15) {
                            if (i < 10) {
                                if (j < 10) {
                                    s = "0" + i + ":0" + j;
                                    if (vm.perfLearnTime == s) {
                                        str1 += "<option value='0" + i + ":0" + j + "' selected>0" + i + ":0" + j + "</option>";
                                    } else {
                                        str1 += "<option value='0" + i + ":0" + j + "'>0" + i + ":0" + j + "</option>";
                                    }

                                    if (vm.perfLearnEndTime == s) {
                                        str2 += "<option value='0" + i + ":0" + j + "' selected>0" + i + ":0" + j + "</option>";
                                    } else {
                                        str2 += "<option value='0" + i + ":0" + j + "'>0" + i + ":0" + j + "</option>";
                                    }

                                } else {
                                    s = "0" + i + ":" + j;
                                    if (vm.perfLearnTime == s) {
                                        str1 += "<option value='0" + i + ":" + j + "' selected>0" + i + ":" + j + "</option>";
                                    } else {
                                        str1 += "<option value='0" + i + ":" + j + "'>0" + i + ":" + j + "</option>";
                                    }
                                    if (vm.perfLearnEndTime == s) {
                                        str2 += "<option value='0" + i + ":" + j + "' selected>0" + i + ":" + j + "</option>";
                                    } else {
                                        str2 += "<option value='0" + i + ":" + j + "'>0" + i + ":" + j + "</option>";
                                    }
                                }
                            } else {
                                if (j < 10) {
                                    s = i + ":0" + j;
                                    if (vm.perfLearnTime == s) {
                                        str1 += "<option value='" + i + ":0" + j + "' selected>" + i + ":0" + j + "</option>";
                                    } else {
                                        str1 += "<option value='" + i + ":0" + j + "'>" + i + ":0" + j + "</option>";
                                    }
                                    if (vm.perfLearnEndTime == s) {
                                        str2 += "<option value='" + i + ":0" + j + "' selected>" + i + ":0" + j + "</option>";
                                    } else {
                                        str2 += "<option value='" + i + ":0" + j + "'>" + i + ":0" + j + "</option>";
                                    }
                                } else {
                                    s = i + ":" + j;
                                    if (vm.perfLearnTime == s) {
                                        str1 += "<option value='" + i + ":" + j + "' selected>" + i + ":" + j + "</option>";
                                    } else {
                                        str1 += "<option value='" + i + ":" + j + "'>" + i + ":" + j + "</option>";
                                    }
                                    if (vm.perfLearnEndTime == s) {
                                        str2 += "<option value='" + i + ":" + j + "' selected>" + i + ":" + j + "</option>";
                                    } else {
                                        str2 += "<option value='" + i + ":" + j + "'>" + i + ":" + j + "</option>";
                                    }
                                }
                            }
                        }
                    }
                    $("#time_picker").html(str1);
                    $("#end_time").html(str2);
                    $("#startInput").html(vm.perfLearnYmd);
                    /* 2020/11/12 V9.0 cuikailin modfiy start */
                },
                error: function () {
                }
            })
        },
        submitFn: function () {
            vm.learnLevUnds = $(".time_st_con .active").attr("desc");
            if (vm.learnLevUnds==null){
                var idx = layer.alert($.format($.msg.MSGCOMN0028,"理解度"),{
                    yes:function () {
                        layer.close(idx);
                    }
                });
                return;
            }
        /* 2020/11/13 V9.0 cuikailin add start */
            var data  = null;
            if (vm.flag == 1){
                data = {
                    "learnLevUnds": vm.learnLevUnds,
                    "updateTime": vm.updateTime,
                    "id": vm.wpp.id,
                    "perfLearnYmd": $("#startInput").text(),
                    "perfLearnTime": $("#time_picker").val(),
                    "perfLearnEndTime": $("#end_time").val(),
                    "subjtDiv": $('#subject_select').find("option:selected").val(),
                    "subjtNm": $('#subject_select').find("option:selected").text(),
                    // endChecked: document.getElementById("in_check").checked
                    "flag":vm.flag
                }
            } else {
                if($("#memoText").val().length>50){
                    parent.layer.alert($.format($.msg.MSGCOMD0011, "メモ","50"));
                    return;
                }
                if (vm.block == "") {
                    parent.layer.alert($.format($.msg.MSGCOMN0028, "カテゴリ"));
                    return;
                }
                if (vm.subjt == "") {
                    parent.layer.alert($.format($.msg.MSGCOMN0028, "教科"));
                    return;
                }
                if(vm.memoText!=null && vm.memoText!= ""){
                    vm.blockNm = vm.memoText;
                }
                data = {
                    "blockDispyNm":vm.subjtNm,
                    "blockTypeDiv":vm.block,
                    "subjtDiv":vm.subjt,
                    "subjtNm":vm.subjtNm,
                    "learnLevUnds": vm.learnLevUnds,
                    "perfLearnYmd": $("#startInput").text(),
                    "perfLearnTime": $("#time_picker").val(),
                    "perfLearnEndTime": $("#end_time").val(),
                    "flag":vm.flag,
                }
            }
        /* 2020/11/31 UT-089 cuikailin add start */
            // var checked = vm.timeCheck();
            // if (!checked) {return;}
        /* 2020/11/31 UT-089 cuikailin add end */
        /* 2020/11/13 V9.0 cuikailin add end */
            $.ajax({
                url: ctxPath + '/pop/F10307/submit',
                data: data,
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg);
                        return false;
                    }
                    // if (parent.tmpEvent != null) {
                    //     parent.tmpEvent.className = ["customEvent", parent.getEventClass(vm.learnLevUnds)];
                    //     parent.$('#calendar').fullCalendar('updateEvent', parent.tmpEvent);
                    //     parent.tmpEvent = null;
                    // } else {
                    //     // 設定イベントクラス
                    //     var events = parent.$("#calendar").fullCalendar("clientEvents");
                    //     $(events).each(function (i, event) {
                    //         if (event.weeklyPlanPerfId == wppId) {
                    //             event.className = ["customEvent", parent.getEventClass(vm.learnLevUnds)];
                    //             parent.$('#calendar').fullCalendar('updateEvent', event);
                    //             return false;
                    //         }
                    //     });
                    // }
                    // // 更新block
                    // $.each(parent.vm.planPerf, function (key, item) {
                    //     $.each(parent.vm.planPerf[key], function (i, block) {
                    //         if (block.planPerfId == wppId) {
                    //             parent.vm.planPerf[key][i].learnLevUnds = vm.learnLevUnds;
                    //             return false;
                    //         }
                    //     });
                    // });
                    // $(parent.vm.planPerf[parent.vm.tgtYmd]).each(function (i, block) {
                    //     if (block.planPerfId == wppId) {
                    //         parent.vm.planPerf[parent.vm.tgtYmd][i].learnLevUnds = vm.learnLevUnds;
                    //         return false;
                    //     }
                    // });
                    // if (param.closeFlg == "true") {
                    //     var index = parent.layer.getFrameIndex(window.name);
                    //     parent.layer.close(index);
                    // } else {
                    //     $(parent.vm.notStartBlock).each(function (i, plan) {
                    //         if (plan.planPerfId == wppId) {
                    //             parent.vm.notStartBlock.splice(i, 1);
                    //             return false;
                    //         }
                    //     });
                    //     if (parent.vm.notStartBlock.length == 0) {
                    //         var index = parent.layer.getFrameIndex(window.name);
                    //         parent.layer.close(index);
                    //     } else {
                    //         // 学習理解度
                    //         vm.learnLevUnds = "";
                    //         $(".time_st_con").find("li").removeClass("active");
                    //         vm.getInfo(parent.vm.notStartBlock[0].planPerfId);
                    //     }
                    // }
                    if(vm.flag == 1){
                        vm.updateBlock();
                        parent.vm.update();
                    }else {
                        parent.vm.update();
                        //2020/11/17 9.0 huangxinliang add start
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        //2020/11/17 9.0 huangxinliang add end
                    }
                    parent.document.getElementById('iframe').contentWindow.location.reload();
                },
                error: function () {
                }
            })
        },
        cancelFn: function () {
            if (param.closeFlg == "true") {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                $(parent.vm.notStartBlock).each(function (i, plan) {
                    if (plan.planPerfId == wppId) {
                        parent.vm.notStartBlock.splice(i, 1);
                        return false;
                    }
                });
                if (parent.vm.notStartBlock.length == 0) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                } else {
                    // 学習理解度
                    vm.learnLevUnds = "";
                    $(".time_st_con").find("li").removeClass("active");
                    vm.getInfo(parent.vm.notStartBlock[0].planPerfId);
                }
            }
        },
        getStyle: function (learnLevUnds, item) {
            var strClass = "";
            if (learnLevUnds == item.codCd) {
                strClass = "background:" + item.codValue2 + ";border: 2px solid " + item.codValue2 + ";color:#fff;";
            } else {
                strClass = "border: 2px solid " + item.codValue2 + ";color:" + item.codValue2;
            }
            return strClass;
        },
        /* 2020/11/12 V9.0 cuikailin add start */
        delFn: function () {
            var index = layer.confirm($.format($.msg.MSGCOMN0013, "実績データ"), {
                title: '確認',
                closeBtn: 0,
                shadeClose: false,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    $.ajax({
                        url: ctxPath + '/pop/F10307/del',
                        data: {
                            'id':param.id,
                            'updateTime':vm.updateTime
                        },
                        type: 'POST',
                        datatype: "json",
                        success: function (data) {
                            if (data.code !== 0) {
                                popMsg(data.msg);
                                return false;
                            }
                            vm.learnLevUnds = "";
                            vm.updateBlock();
                        }
                    });
                }
            });
        },
        /* 2020/11/12 V9.0 cuikailin add end */
        /* 2020/11/13 V9.0 cuikailin add start */
        showBlock:function(){
            $(".check_block").toggleClass("disNone");
            if(!$(".check_block").hasClass("disNone")) {
                if(!$(".check_sub").hasClass("disNone")) {
                    $(".check_sub").addClass("disNone");
                }
                $("#showSubjt").hide();
                $(".mainDiv").hide();
                $(".content_them").hide();
                $("#memo").hide();
            }else{
                $("#showSubjt").show();
                $(".mainDiv").show();
                $(".content_them").show();
                $("#memo").show();
            }
        },
        showSubjt:function(){
            $(".check_sub").toggleClass("disNone");
            if(!$(".check_sub").hasClass("disNone")) {
                $("#memo").hide();
                $(".mainDiv").hide();
                $(".content_them").hide();
            }else{
                $("#memo").show();
                $(".mainDiv").show();
                $(".content_them").show();
            }
        },

        updateBlock:function () {
            if (parent.tmpEvent != null) {
                parent.tmpEvent.className = ["customEvent", parent.getEventClass(vm.learnLevUnds)];
                parent.$('#calendar').fullCalendar('updateEvent', parent.tmpEvent);
                parent.tmpEvent = null;
            } else {
                // 設定イベントクラス
                var events = parent.$("#calendar").fullCalendar("clientEvents");
                $(events).each(function (i, event) {
                    if (event.weeklyPlanPerfId == wppId) {
                        event.className = ["customEvent", parent.getEventClass(vm.learnLevUnds)];
                        parent.$('#calendar').fullCalendar('updateEvent', event);
                        return false;
                    }
                });
            }
            // 更新block
            $.each(parent.vm.planPerf, function (key, item) {
                $.each(parent.vm.planPerf[key], function (i, block) {
                    if (block.planPerfId == wppId) {
                        parent.vm.planPerf[key][i].learnLevUnds = vm.learnLevUnds;
                        return false;
                    }
                });
            });
            $(parent.vm.planPerf[parent.vm.tgtYmd]).each(function (i, block) {
                if (block.planPerfId == wppId) {
                    parent.vm.planPerf[parent.vm.tgtYmd][i].learnLevUnds = vm.learnLevUnds;
                    return false;
                }
            });
            if (param.closeFlg == "true") {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                $(parent.vm.notStartBlock).each(function (i, plan) {
                    if (plan.planPerfId == wppId) {
                        parent.vm.notStartBlock.splice(i, 1);
                        return false;
                    }
                });
                if (parent.vm.notStartBlock.length == 0) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                } else {
                    // 学習理解度
                    vm.learnLevUnds = "";
                    $(".time_st_con").find("li").removeClass("active");
                    vm.getInfo(parent.vm.notStartBlock[0].planPerfId);
                }
            }
        },
        /* 2020/11/13 V9.0 cuikailin add end */
        /* 2020/11/31 UT-089 cuikailin add start */
        // timeCheck:function () {
        //     var start = $("#time_picker").val();
        //     var end = $("#end_time").val();
        //     if (start > end) {
        //         layer.alert($.format($.msg.MSGCOMN0124, "実績学習終了時間", "実績学習開始時間"));
        //         return false;
        //     }else {
        //         return true;
        //     }
        // }
        /* 2020/11/31 UT-089 cuikailin add end */
    }

});
$("#perfTime").click(function () {
    if ($(".time_itemO").find('.mbsc-mobiscroll').css('display') == 'block') {
        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'none')
    } else {
        $(".time_itemO").find('.mbsc-mobiscroll').css('display', 'block')
        $(".time_itemO").find(".dwwc").children().eq(0).find(".dwww").css("padding-left", "calc(100% - 70px)")
        $(".time_itemO").find(".dwwc").children().eq(1).find(".dwww").css("padding-right", "calc(100% - 70px)")
    }
})
$("#perfTime").blur(function () {
    $(".mbsc-mobiscroll").hide();
})
/* 2020/11/31 UT-089 cuikailin add start */
// $("#time_picker").change(function () {
//     vm.timeCheck();
// })
// $("#end_time").change(function () {
//     vm.timeCheck();
// })
/* 2020/11/31 UT-089 cuikailin add end */
// 期日フォーマット
function dateFmt(val) {

    if (!val) {
        return "";
    }

    var date = new Date(val);
    Y = date.getFullYear() + '/';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
    D = date.getDate() > 9 ? date.getDate() + ' ' : '0' + date.getDate() + ' ';
    h = date.getHours() > 9 ? date.getHours() + ':' : '0' + date.getHours() + ":";
    m = date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes();
    s = date.getSeconds() > 9 ? ':' + date.getSeconds() : ':' + '0' + date.getSeconds();
    return Y + M + D + h + m;
}

var param = getParam();
var eventId = param.id;
var selectData = [];

function selectLoad() {
    var width = $(window).width() * 0.56;
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid(
        {
            datatype: "local",
            // colNames: ['保護者ID', '保護者名', '生徒ID', '生徒名', '学年'],
            colModel: [
                {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 10, sortable: false, align: "center"},
                {label: '生徒名', name: 'stuName', index: 'stuName', width: 10, sortable: false, align: "center"},
                {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 10, sortable: false, align: "center"},
                {label: 'hidden', name: 'stuId', index: 'stuId', width: 0, sortable: false, key: true}
            ],
            multiselect: false,
            rowNum: 30,
            width: width,
            rowList: [10, 20, 30],
            viewrecords: true,
            pager: "#jqGridPager",
            loadComplete: function () {
                var jqGridList = $("#jqGrid").jqGrid("getRowData");
                var allCountID = $("#jqGrid").jqGrid('getDataIDs');
                jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
                logData = [];
                for (var i = 0; i < vm.guardAndStudentList.length; i++) {
                    if (!vm.guardAndStudentList[i].readFlg || vm.guardAndStudentList[i].readFlg === '1') {
                        $("#" + vm.guardAndStudentList[i].stuId).css("background", "red");
                    }
                    logData.push(vm.guardAndStudentList[i]);
                }
                $("#jqGrid").setGridParam().hideCol("stuId");
                $('td#jqGridPager_center').css('width', $(window).width() * 0.33);
            },
            beforeSelectRow: function (rowid, e) {
                var $myGrid = $(this),
                    i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
                    cm = $myGrid.jqGrid('getGridParam', 'colModel');
                return (cm[i].name === 'cb');
            }
        }
    );
    var localData = {page: 1, total: 2, records: "2", rows: selectData};
    localData.rows = selectData;
    localData.records = selectData.length;
    localData.total = (selectData.length % 30 == 0) ? (selectData.length / 30) : (Math.floor(selectData.length / 30) + 1);
    var reader = {
        root: function (obj) {
            return localData.rows;
        },
        page: function (obj) {
            return localData.page;
        },
        total: function (obj) {
            return localData.total;
        },
        records: function (obj) {
            return localData.records;
        }, repeatitems: false
    };
    $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
}

var vm = new Vue({
    el: '#app',
    data: {
        eventStsDivList: [],
        guardAndStudentList: [],
        mstEventEntity: {},
        eventStsDiv: 2,
        noFocus: false,
        deliverTargetDiv: 0,
        replyFlg:''
    },
    computed: {},
    methods: {
        timeFormat: function (time) {
            return dateFmt(time)
        }
    },
    mounted: function () {

    },
    updated: function () {
    }
});

function numFormat(num) {
    if (num < 9 && num > 0) {
        num = "0" + num;
    }
    return num;
}

function randomColor() {
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    var color = '#' + r.toString(16) + g.toString(16) + b.toString(16);
    return color;
}

var changeTimeMax = 30;
var changeTimeMin = 0;

function save() {
    // 確認ダイアログをポップアップ表示する
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {

            var eventStsDiv = vm.eventStsDiv;
            var pubStartDt = $("#pubStartDt").val();
            var pubEndDt = $("#pubEndDt").val();
            var applyStartDt = $("#applyStartDt").val();
            var applyEndDt = $("#applyEndDt").val();
            var noitceFlg = vm.mstEventEntity.noitceFlg;
            var remandFlg = vm.mstEventEntity.remandFlg;
            var chgLimtDays = $("#changeTime").val();
            var deliverTargetDiv = vm.deliverTargetDiv + '';
            var reSentFlg=param.reSentFlg;
            var eventParams = {
                eventStsDiv: eventStsDiv
                , pubStartDt: pubStartDt
                , pubEndDt: pubEndDt
                , applyStartDt: applyStartDt
                , applyEndDt: applyEndDt
                , noitceFlg: noitceFlg
                , remandFlg: remandFlg
                , chgLimtDays: chgLimtDays
                , deliverTargetDiv: deliverTargetDiv
                , reSentFlg: reSentFlg
            };

            var stuId = [];
            var guardId = [];
            var orgId = [];
            for (var i = 0; i < logData.length; i++) {

                var guardAndStudent = logData[i];
                stuId.push(guardAndStudent.stuId);
                guardId.push(guardAndStudent.guardId);
                // NWT崔 manmiru1-726 2021/07/07 add start
                orgId.push(guardAndStudent.orgId);
                // NWT崔 manmiru1-726 2021/07/07 add end
            }
            var stuIdAll=[];
            var guardIdAll=[];
            for (var j = 0; j < vm.guardAndStudentList.length; j++) {
                var allStudentId=vm.guardAndStudentList[j];
                stuIdAll.push(allStudentId.stuId);
                guardIdAll.push(allStudentId.guardId);
            }
            var guardParamsAll={
                stuIdAll:JSON.stringify(stuIdAll),
                guardIdAll:JSON.stringify(guardIdAll)

            };
            var guardParams = {
                stuId: JSON.stringify(stuId),
                guardId: JSON.stringify(guardId),
                // NWT崔 manmiru1-726 2021/07/07 add start
                orgId: JSON.stringify(orgId)
                // NWT崔 manmiru1-726 2021/07/07 add end
            };

            if (logData.length < vm.guardAndStudentList.length) {
                var idx = layer.confirm($.msg.MSGCOMN0158, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['キャンセル', '確認'],
                    btn1: function () {
                        layer.close(idx);
                        return;
                    },
                    btn2: function () {
                        $.post({
                            url: ctxPath + '/manager/F08010/save',
                            type: 'POST',
                            data: {
                                eventParams: JSON.stringify(eventParams),
                                guardParams: JSON.stringify(guardParams),
                                eventId: vm.mstEventEntity.id,
                                orgId: vm.mstEventEntity.orgId,
                                guardParamsAll:JSON.stringify(guardParamsAll)
                            },
                            dataType: "json",
                            success: function (data) {
                                if (data.code != 0) {
                                    showMsg(data.msg);
                                } else {
                                    // var idx = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                    //     skin: 'layui-layer-molv',
                                    //     title: '確認',
                                    //     closeBtn: 0,
                                    //     anim: -1,
                                    //     btn: ['確認'],
                                    //     yes: function () {
                                    location.href = "F08009.html";
                                    //         layer.close(idx);
                                    //     }
                                    // })

                                }
                            }
                        });
                    },
                })
            } else {
                $.post({
                    url: ctxPath + '/manager/F08010/save',
                    type: 'POST',
                    data: {
                        eventParams: JSON.stringify(eventParams),
                        guardParams: JSON.stringify(guardParams),
                        eventId: vm.mstEventEntity.id,
                        orgId: vm.mstEventEntity.orgId,
                        guardParamsAll: JSON.stringify(guardParamsAll)
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code != 0) {
                            showMsg(data.msg);
                        } else {
                            // var idx = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                            //     skin: 'layui-layer-molv',
                            //     title: '確認',
                            //     closeBtn: 0,
                            //     anim: -1,
                            //     btn: ['確認'],
                            //     yes: function () {
                            location.href = "F08009.html";
                            // layer.close(idx);
                            // }
                            // })

                        }
                    }
                });
            }
        }
    })
}

function formatminutes(date) {
    var aa = $(".laydate-time-list li ol")[1];
    var showtime = $($(".laydate-time-list li ol")[1]).find("li");
    for (var i = 0; i < showtime.length; i++) {
        var t00 = showtime[i].innerText;
        if (t00 != "00" && t00 != "20" && t00 != "30" && t00 != "40" && t00 != "50") {
            showtime[i].remove()
        }
    }
    $($(".laydate-time-list li ol")[2]).find("li").remove();
}

$(function () {
    $("#changeTime").bind("input propertychange", function () {
        var num = $(this).val();
        vm.mstEventEntity.chgLimtDays = num;
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#pubStartDt").val(value);
            vm.mstEventEntity.pubStartDt = value;
        },
        ready: formatminutes
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeTwo',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#pubEndDt").val(value);
            vm.mstEventEntity.pubEndDt = value;
        },
        ready: formatminutes
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeThree',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#applyStartDt").val(value);
            vm.mstEventEntity.applyStartDt = value;
        },
        ready: formatminutes
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeFour',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#applyEndDt").val(value);
            vm.mstEventEntity.applyEndDt = value;
        },
        ready: formatminutes
    });

    $.get(ctxPath + "/manager/F08010/init",
        {
            eventId: param.id,
            reSentFlg: param.reSentFlg
        },
        function (data) {
            if (data.code == 0) {
                // 保護者予約後も公開期間、申込可能期間を延長できるようにする 2020/01/04 modify yang start--
                // if (data.activeFlg === false) {
                //     $(".time_div").addClass("noFocus");
                // }
                // 保護者予約後も公開期間、申込可能期間を延長できるようにする 2020/01/04 modify yang end--
                if (param.reSentFlg === "1") {
                    $(".changeTime").addClass("noFocus");
                    $(".my_radio").addClass("noFocusS");
                    // $(".inputMessage").addClass("noFocusS");
                }
                if (data.replyFlg == "1") {
                    vm.replyFlg = data.replyFlg;
                }
                vm.eventStsDivList = data.eventStsDivList;
                vm.SchyDivList = data.SchyDivList;
                vm.mstGrpEntityList = data.mstGrpEntityList;
                vm.mstEventEntity = data.eventEntity;
                // vm.guardAndStudentList = data.guardAndStudentList;
                vm.guardAndStudentList = window.localStorage.getItem("guardAndStudentList") ? JSON.parse(decodeURIComponent(window.localStorage.getItem("guardAndStudentList"))) : data.guardAndStudentList;
                if (window.localStorage.getItem("guardAndStudentList")!=null||window.localStorage.getItem("guardAndStudentList")!=undefined) {
                    window.localStorage.removeItem("guardAndStudentList")
                }
                if (vm.guardAndStudentList.length > 0) {
                    $("#deliverTarget .my_radio").addClass("noFocusS")
                }
                vm.eventStsDiv = vm.mstEventEntity.eventStsDiv;
                vm.mstEventEntity.chgLimtDays = numFormat(data.eventEntity.chgLimtDays);
                var orgId = data.orgId;
                $(".time_div").css("background", "white");
                selectData = vm.guardAndStudentList;
                selectLoad();
                if (data.deliverTargetDiv) {
                    vm.deliverTargetDiv = data.deliverTargetDiv;
                }
            } else {
                $("#btn_ok").addClass("noFocus");
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
        }
    );
    // 再通知かどうかを判断します
    if (param.reSentFlg !== "1") {
        $("#search").click(function () {
            if ($("#message") != null) {
                $("#message").hide();
            }
            var stuIdList =[];
            var stuIdMap = new Map();
            for (var i = 0; i <vm.guardAndStudentList.length ; i++) {
                stuIdList[i] =vm.guardAndStudentList[i]["stuId"];
                stuIdMap.set(vm.guardAndStudentList[i]["stuId"],vm.guardAndStudentList[i]["readFlg"]);
            }
            sessionStorage.setItem("stuIdList",stuIdList.toString());
            var srcWidth = $(window).width() * 0.6 + "px";
            var srcHeight = $(window).height() * 0.9 + "px";
            layer.open({
                id: 'F08012',
                type: 2,
                anim: 2,
                skin: "layui-layer-myskin",
                title: false,
                shade: 0.2,
                closeBtn: 0,
                shadeClose: false,
                move: '.layui-layer-title',
                area: ['80%', '70%'],
                fixed: true,
                resize: false,
                // 組織表示区分0：非表示を連れて、F04004_配信先選択画面（POP）をポップアップする。
                content: ["../pop/F04004.html?eventId=" + eventId + "&orgSelectDiv=0", 'no'],
                success: function (layero, index) {
                    var child = $(layero).find("iframe")[0].contentWindow;
                    // selectLoad(selectData)
                },
                end: function () {
                    selectData = [];
                    for (var i = 0; i < vm.guardAndStudentList.length ; i++) {
                        if ((stuIdList.indexOf(vm.guardAndStudentList[i].stuId)!=-1)&&(stuIdMap.get(vm.guardAndStudentList[i].stuId)) === false) {
                            vm.guardAndStudentList[i].readFlg = false;
                        }
                    }
                    for (var i = 0; i < vm.guardAndStudentList.length; i++) {
                        selectData.push(vm.guardAndStudentList[i]);
                    }
                    selectLoad();
                }
            });
        });
    }
    // 戻るボダン押下
    $("#btn_cancel").click(function () {

        location.href = "F08009.html";
    });

    // 登録ボダン押下
    $("#btn_ok").click(function () {
        if ($("#message") != null) {
            $("#message").hide();
        }

        if (vm.guardAndStudentList.length === 0) {
            showMsg($.format($.msg.MSGCOMN0096, "配信先設定", "「＞　または　＞＞」"));
            return;
        }
        var pubStartDt = $("#pubStartDt").val();
        var pubEndDt = $("#pubEndDt").val();
        var applyStartDt = $("#applyStartDt").val();
        var applyEndDt = $("#applyEndDt").val();
        var chgLimtDays = $("#changeTime").val();

        if ((pubEndDt == "" && pubStartDt != "") || (pubEndDt != "" && pubStartDt == "")) {
            showMsg($.format($.msg.MSGCOMN0039, "公開開始日時", "公開終了日時"));
            return;
        }

        if (pubEndDt != "" && pubStartDt != "" && pubStartDt > pubEndDt) {
            showMsg($.format($.msg.MSGCOMN0048, "公開終了日時", "公開開始日時"));
            return;
        }

        if ((applyEndDt == "" && applyStartDt != "") || (applyEndDt != "" && applyStartDt == "")) {
            showMsg($.format($.msg.MSGCOMN0039, "申込可能開始日時", "申込可能終了日時"));
            return;
        }

        if (applyEndDt != "" && applyStartDt != "" && applyStartDt > applyEndDt) {
            showMsg($.format($.msg.MSGCOMN0048, "申込可能終了日時", "申込可能開始日時"));
            return;
        }

        if (Number(chgLimtDays) > 30 || chgLimtDays.indexOf('-') !== -1) {
            showMsg($.format($.msg.MSGCOMD0014, "変更可能期間", "0", "30"));
            return;
        }
        if (!/^\d+$/.test(chgLimtDays))
        {
            showMsg($.format($.msg.MSGCOMD0006, "変更可能期間"));
            return;
        }


        //  画面．公開有無が「公開」の場合
        if (vm.eventStsDiv == '0') {
            // 画面．公開開始日時、画面．公開終了日時、画面．申込可能開始日時、画面．申込可能終了日時、変更可能期間がすべて必須であること
            if (pubStartDt == "" || pubEndDt == "" || applyStartDt == "" || applyEndDt == "" || chgLimtDays == "") {
                showMsg($.format($.msg.MSGCOMD0001, "公開開始日時、公開終了日時、申込可能開始日時、申込可能終了日時、変更可能期間"));
                return;
            }
        }
        save();
    })
});
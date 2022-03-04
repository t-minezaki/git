var param = getParam();
var data;
var flg;
var mustShow = [];
let selectedStu = [];
// add at 2021/08/12 for V9.02 by NWT wen START
var selectData = [];
// add at 2021/08/12 for V9.02 by NWT wen END
//状態集計一覧部情報を表示するため
var vm = new Vue({
    el: '#divinfo',
    data: {
        allCount: '',
        unreadCount: '',
        readCount: '',
        repliedCount: '',
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F08011/info',
                type: 'GET',
                data: {
                    eventId: param.id
                },
                dataType: 'json',
                success: function (data) {
                    vm.org = data.org;
                    if (data.code == 0) {
                        vm.allCount = data.getInfo.deleverSum,
                            vm.unreadCount = data.getInfo.notReadingSum,
                            vm.readCount = data.getInfo.readingSum,
                            vm.repliedCount = data.getInfo.replySum
                    } else {
                        showMsg(data.msg)
                    }
                }
            })
        },
        download: function () {
            var checked = $('.stu_check:checked');
            if (checked.length <= 0) {
                // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "添付ダウンロード対象", "対象"));
                return;
            }
            // modify at 2021/08/20 for V9.02 by NWT wen START
            for (let i = 0; i < selectData.length; i++) {
                let data = selectData[i];
                let stuId = data['stuId'];
                let stuName = data['stuName'];
                for (let j = 1; j <= data.talkRecordDEntityList.length; j++){
                    var _obj = data.talkRecordDEntityList[j-1]
                    var qNum = _obj.askNum;
                    var answer = _obj.answerReltCont;
                    var answerTypeDiv = _obj.answerTypeDiv;
                    if (answerTypeDiv === '3') {
                        let link = answer.substring(2);
                        if (link !== ''){
                            let a = document.createElement('a');
                            a.download = stuName + '_' + stuId + '_質問' + qNum + '.png'
                            a.href = ctxPath + link;
                            a.click();
                            $(a).remove();
                        }
                    }
                }
            }
            // for (let i = 0; i < checked.length; i++) {
            //     let data = JSON.parse($(checked[i]).attr("json-data"));
            //     let afterId = data['eventFlag'] === 'guard' ? data['guardLoginId'] : data['stuLoginId'];
            //     for (let j = 1; j <= 10; j++){
            //         const answer = data['answerReltCont' + j];
            //         if (!answer){
            //             continue;
            //         }
            //         const answerTypeDiv = answer.split(',')[0];
            //         if (['0', '1', '2'].indexOf(answerTypeDiv) >= 0) {
            //             continue;
            //         }
            //         if (answerTypeDiv === '3') {
            //             let link = answer.substring(2);
            //             let questionName = data['questionName' + j];
            //             if (link !== ''){
            //                 let a = document.createElement('a');
            //                 a.download = afterId + '_' + questionName + '.png';
            //                 a.href = ctxPath + link.substring(2);
            //                 a.click();
            //                 $(a).remove();
            //             }
            //         }
            //     }
            // }
            // modify at 2021/08/20 for V9.02 by NWT wen END
        },
        // add at 2021/08/12 for V9.02 by NWT wen START
        downloadCSV: function () {
            let downloadData = {};
            if (selectData && selectData.length > 0) {
                downloadData = {
                    param: selectData
                }
            }
            let str = JSON.stringify(downloadData);
            $.ajax({
                url: ctxPath + '/manager/F08011/getFile',
                type: 'POST',
                data: str,
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if(data.code==0){
                        $("#downloadFrom #fileName").val(data.filename);
                        $("#downloadFrom").submit();
                    } else {
                        showMsg(data.msg);
                    }
                }
            })
        },
        // add at 2021/08/12 for V9.02 by NWT wen END
        showImg: function (imgUrl) {
            var imgHtml = "<img id='preview' src='" + decodeURIComponent(imgUrl).replace(/\%20/g, " ") + "' />";
            var img = new Image();
            img.src = decodeURIComponent(imgUrl).replace(/\%20/g, " ");
            var loadingLoop = layer.load(1, {
                shade: [0.5, '#CCC']
            });
            img.onload = function () {
                if (img.complete) {
                    layer.close(loadingLoop);
                }
                var w = window.innerWidth * 0.95;
                var h = window.innerHeight * 0.8;
                var tempWidth;
                var tempHeight;
                if (img.naturalWidth / img.naturalHeight >= w / h) {
                    if (img.naturalWidth > w) {
                        tempWidth = w;
                        tempHeight = (img.naturalHeight * w) / img.naturalWidth;
                    } else {
                        tempWidth = img.naturalWidth;
                        tempHeight = img.naturalHeight;
                    }
                } else {
                    if (img.naturalHeight > h) {
                        tempHeight = h;
                        tempWidth = (img.naturalWidth * h) / img.naturalHeight;
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
                    closeBtn: 1,
                    area: [width, height],
                    shadeClose: false,
                    scrollbar: false,
                    title: "",
                    content: imgHtml,
                    cancel: function () {
                    }
                });
            };
            // return false;
        },
        resend: function (){
            var checked = $('.stu_check:checked');
            if (checked.length <= 0) {
                // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "対象"));
                return;
            }
            var guardAndStudentList = [];
            for (var i = 0; i < checked.length; i++) {
                let data = JSON.parse($(checked[i]).attr("json-data"));
                if(data.readingStatusDiv === "0"){
                    var guardAndStudent = {};
                    guardAndStudent.afterUsrId = data.stuLoginId;
                    guardAndStudent.stuName = data.stuName;
                    guardAndStudent.schyDiv = data.schy;
                    guardAndStudent.stuId = data.stuId;
                    guardAndStudent.guardId = data.guardId;
                    guardAndStudent.readFlg = data.readingStatusDiv;
                    guardAndStudent.replyFlg = data.replyStatusDiv;
                    guardAndStudentList.push(guardAndStudent);
                }
            }
            if (guardAndStudentList.length <= 0){
                // 画面．未開封対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "未読対象"));
                return;
            }
            var object = {};
            object.guardAndStudentList = JSON.stringify(guardAndStudentList);
            object.id = param.id;
            object.reSentFlg = '1';
            window.location.href = "./F08010.html?" + $.param(object);
        }
    }
})
var data = {};


// 表示項目辞书
var dist = {
    'guardName': {
        label: '保護者名',
        name: 'guardName',
        index: 'guardName',
        width: 100,
        height: 100,
        sortable: true,
        align: "center"
    },
    'stuName': {
        label: '生徒名',
        name: 'stuName',
        index: 'stuName',
        width: 100,
        height: 100,
        sortable: true,
        align: "center"
    },
    'status': {
        label: 'ステータス',
        name: 'status',
        index: 'status',
        width: 100,
        height: 100,
        sortable: true,
        align: "center"
    },
    'readTime': {
        label: '既読日', name: 'readTime', index: 'readTime', width: 100, height: 100, sortable: true, align: "center",
        formatter(cell, option, object) {
            if (object.readTime == null) {
                return ''
            }
            var date = new Date(Date.parse(object.readTime));
            return date.format('yyyy/MM/dd');
            // if (object.status == '未読') {
            //
            // }

        }
    },
    'replyTime': {
        label: '回答日', name: 'replyTime', index: 'replyTime', width: 100, height: 100, sortable: true, align: "center",
        formatter(cell, option, object) {
            if (object.replyTime == null) {
                return ''
            }
            var date = new Date(Date.parse(object.replyTime));
            return date.format('yyyy/MM/dd');

        }
    },
    'startTime': {
        label: '申込日程', name: 'startTime', index: 'startTime', width: 150, height: 100, sortable: true, align: "center",
        formatter(cell, option, object) {
            if (object.startTime == null) {
                return ''
            }
            var date = new Date(Date.parse(object.startTime));
            return date.format('yyyy/MM/dd hh:mm');

        }
    },
    'classRoom': {
        label: '教室 ',
        name: 'teacherName',
        index: 'classRoom',
        width: 100,
        height: 100,
        sortable: true,
        align: "center"
    },
    'teacherName': {
        label: '担当講師 ',
        name: 'teacherName',
        index: 'teacherName',
        width: 100,
        height: 100,
        sortable: true,
        align: "center"
    },
    'reply1': {label: '備考', name: 'reply1', index: 'reply1', width: 100, height: 100, sortable: true, align: "center"},
    'answerReltCont1': {
        label: '質問１',
        name: 'answerReltCont1',
        index: 'answerReltCont1',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont2': {
        label: '質問２',
        name: 'answerReltCont2',
        index: 'answerReltCont2',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont3': {
        label: '質問３',
        name: 'answerReltCont3',
        index: 'answerReltCont3',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont4': {
        label: '質問４',
        name: 'answerReltCont4',
        index: 'answerReltCont4',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont5': {
        label: '質問５',
        name: 'answerReltCont5',
        index: 'answerReltCont5',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont6': {
        label: '質問６',
        name: 'answerReltCont6',
        index: 'answerReltCont6',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont7': {
        label: '質問７',
        name: 'answerReltCont7',
        index: 'answerReltCont7',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont8': {
        label: '質問８',
        name: 'answerReltCont8',
        index: 'answerReltCont8',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont9': {
        label: '質問９',
        name: 'answerReltCont9',
        index: 'answerReltCont9',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
    ,
    'answerReltCont10': {
        label: '質問１０',
        name: 'answerReltCont10',
        index: 'answerReltCont10',
        width: 100,
        height: 100,
        sortable: true,
        align: "center",
        formatter: function (cell, option, object) {
            return getContent(cell);
        }
    }
};
// 表示項目全集合
var wholeList = ['guardName', 'status', 'stuName', 'readTime', 'replyTime', 'startTime', 'teacherName', 'reply1', 'classRoom', 'answerReltCont1', 'answerReltCont2', 'answerReltCont3', 'answerReltCont4',
    'answerReltCont5', 'answerReltCont6', 'answerReltCont7', 'answerReltCont8', 'answerReltCont9', 'answerReltCont10'];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
var time = 1;

function getIndex(list, val) {
    for (var i = 0; i < list.length; i++) {
        if (list[i] == val) return i;
    }
    return -1;
};

var dataList = [];
// リスト重載
function reload(col, hidden) {
    $(".topMessage").css("display", "None")
    //POP幅
    var width = $(window).width() * 0.95;
    //POP高さ
    var height = $(window).height() * 0.5;
    if (col != null)
        colList = col;
    if (hidden != null)
        hiddenList = hidden;
    var list = [];
    // 表示項目
    for (var i = 0; i < colList.length; i++) {
        list.push(dist[colList[i]]);
    }
    list.unshift({
        label: '<input id="allCheck" type="checkbox" onclick="allSelect(this)"/>',
        name: 'stuId',
        index: 'stuId',
        width: 50,
        sortable: false,
        align: "center",
        formatter(cell, option, object) {
            var key = {
                id: object.id,
                talkId: object.talkId,
                guardId: object.guardId,
                stuId: object.stuId,
                stuName: object.stuName,
                eventScheDelId: object.eventScheDelId,
                afterUsrId: object.stuLoginId,
                schyDiv: object.schy,
                readFlg: object.readingStatusDiv,
                replyFlg: object.replyStatusDiv
            };
            if (selectedStu.indexOf(object.usrId) !== -1) {
                return "<input type='checkbox' checked onclick='isCheckBox(this)' class='stu_check' value='" + object.stuId + "' json-data='" + JSON.stringify(key) + "'/>";
            }
            return "<input type='checkbox' onclick='isCheckBox(this)' class='stu_check' value='" + object.stuId + "' json-data='" + JSON.stringify(key) + "'/>";
        }
    })
    $.jgrid.gridUnload("jqGrid");

    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F08011/init',
        datatype: 'JSON',
        postData: {
            eventId: param.id
        },
        colModel: list,
        viewrecords: true,
        // modify at 2021/08/25 for V9.02 by NWT wen START
        regional: 'ja',
        // modify at 2021/08/25 for V9.02 by NWT wen END
        // width: width,
        height: height,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        sortable: true,
        sortorder: 'desc',
        loadonce: true,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader:
            {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            }
        ,
        prmNames: {
            page: "page",
            rows: "limit"
        }
        ,
        gridComplete: function () {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "auto"});
        }
        ,
        loadComplete: function (data) {
            if (!data.code || data.code == 0) {
                dataList = data.page.list;
                if (time === 1) {
                    setHeader(data.page.list);
                }
                time = time + 1;
                $("div.ui-jqgrid-sortable").each(function (index, element) {

                    if (element.id == "jqgh_jqGrid_stuId") {

                        return;
                    }

                    var asc = $(element).children().find("span[sort='asc']").first();
                    var span = $(element).children("span.s-ico").first();
                    var label = $(element).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(element).children("label").html("&#9650");
                    } else {
                        $(element).children("label").html("&#9660");
                    }
                });
                // ソートアイコン変換

                $("div.ui-jqgrid-sortable").bind("click", function () {

                    if (this.id == "jqgh_jqGrid_stuId") {

                        return;
                    }

                    var asc = $(this).children().find("span[sort='asc']").first();
                    var span = $(this).children("span.s-ico").first();
                    var label = $(this).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(this).children("label").html("&#9660");
                    } else {
                        $(this).children("label").html("&#9650");
                    }
                });
                $(".ui-jqgrid-hdiv").bind("resize", function () {

                    $(this).parent(".ui-jqgrid").width($(this).width());
                });

                $(".topMessage").css("display", "none");
            } else {
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }

        }
    });
    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}

function removeDisplayItem(rsp, diplayItem) {
    delete dist[diplayItem];
    var index = getIndex(rsp.dspitems.dspItemslist, diplayItem);
    if (index >= 0) {
        rsp.dspitems.dspItemslist.splice(index, 1);
    }
    index = wholeList.indexOf(diplayItem);
    if (index >= 0) {
        wholeList.splice(index, 1);
    }
}

$(function () {
    // 検索リンク押下
    $("#search").click(function () {
        var srcWidth = $(window).width() * 0.4 + "px";
        var srcHeight = $(window).height() * 0.55 + "px";
        layer.open({
            id: 'F08011-1',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "検索項目",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            fixed: true,
            resize: false,
            content: ["../pop/F08011-1.html?eventId=" + param.id, 'no'],
            success: function (layero, index) {
            },
            end: function () {
                if (flg == 0) {
                    $("#jqGrid").jqGrid('setGridParam', {
                        url: ctxPath + '/manager/F08011/search',
                        datatype: "json",
                        postData: {
                            searchpage: 1,
                            searchlimit: 30,
                            eventId: param.id,
                            data: JSON.stringify(data)
                        },
                        loadComplete: function (data) {
                            if (data.code == 0) {
                            } else {
                                showMsg(data.msg);
                            }

                        }
                    }).trigger("reloadGrid");
                }
            }
        });
    });

    $("#updateitem").click(function () {
        var srcWidth = $(window).width() * 0.7 + "px";
        var srcHeight = $(window).height() * 0.55 + "px";
        layer.open({
            id: 'F08011-1',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "表示可能項目",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            fixed: true,
            resize: false,
            content: ["../pop/F08011-2.html", 'no'],
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var form_left = body.find('#form_left');
                for (var j = 0; j < hiddenList.length; j++) {
                    var str = "<div class='item_div'><input type='checkbox' value='" + hiddenList[j] + "' content='" + dist[hiddenList[j]].label + "'/><span class='cont'>" + dist[hiddenList[j]].label + "</span></div>";
                    $(form_left).append(str);
                }
                var form_right = body.find('#form_right');
                for (var j = 0; j < colList.length; j++) {
                    var str = "<div class='item_div'><input type='checkbox' value='" + colList[j] + "' content='" + dist[colList[j]].label + "'/><span class='cont'>" + dist[colList[j]].label + "</span></div>";
                    $(form_right).append(str);
                }
            },
        });
    })
    $.ajax({
        url: ctxPath + "/manager/F08011/getDspItems",
        datatype: 'JSON',
        data: {
            eventId: param.id
        },
        success: function (rsp) {
            if (rsp.dspitems) {
                mustShow = rsp.dspitems.mustItems;
                if (rsp.refType !== "0") {
                    removeDisplayItem(rsp, 'classRoom');
                }
                if (rsp.refType !== "1") {
                    removeDisplayItem(rsp, 'teacherName');
                }
                if (rsp.refType === '2') {
                    removeDisplayItem(rsp, 'startTime');
                }
                for (var i = 0; i < rsp.dspitems.dspItemslist.length; i++) {
                    colList.push(rsp.dspitems.dspItemslist[i]);
                }
                for (var i = 0; i < wholeList.length; i++) {
                    if (colList.indexOf(wholeList[i]) < 0) {
                        //表示可能項目
                        hiddenList.push(wholeList[i]);
                    }
                }
                reload(colList, hiddenList);
            }
        }
    })
})


//戻るボダン押下
function back() {
    window.location.href = "./F08009.html";
}

//時間の書式設定
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
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

function setHeader(list) {
    // 2021/1/28 huangxinliang modify start
    for (var i = 0; i < 1; i++) {
        for (var j = 0; (j < 10 && i === 0); j++) {
            if (list[i]['questionName' + (j + 1)] && list[i]['questionName' + (j + 1)] !== '') {
                var newLabel = list[i]['questionName' + (j + 1)].length > 5 ?
                    list[i]['questionName' + (j + 1)].substring(0, 4) + '...' :
                    list[i]['questionName' + (j + 1)]
                newLabel = '<p title="' + list[i]['questionName' + (j + 1)] + '" style="display: inline-block;vertical-align: middle;height: inherit;">' + newLabel + '</p>'
                $("#jqGrid").jqGrid('setLabel', 'answerReltCont' + (j + 1), newLabel);
            }
        }
    }
    // 2021/1/28 huangxinliang modify end
}

function allSelect(e) {
    $(".topMessage").css("display", "None")
    if (e && e.stopPropagation) {
        e.stopPropagation();
    } else {
        window.event.cancelBubble = true;
    }
    var allBox = $(".stu_check");
    // add at 2021/08/26 for V9.02 by NWT wen START
    selectData = [];
    // add at 2021/08/26 for V9.02 by NWT wen END
    if (selectedStu.length !== 0 && allBox.length === selectedStu.length) {
        for (var i = 0; i < $(".stu_check").length; i++) {
            $(".stu_check")[i].checked = false
        }
        selectedStu = [];
    } else {
        $(".stu_check").prop("checked", true);
        selectedStu = [];
        for (var i = 0; i < $(".stu_check").length; i++) {
            selectedStu.push($(".stu_check")[i].value);
            // add at 2021/08/26 for V9.02 by NWT wen START
            selectData = dataList;
            // add at 2021/08/26 for V9.02 by NWT wen END
        }
    }
}

function isCheckBox(dom) {
    $(".topMessage").css("display", "None")
    // // add at 2021/08/12 for V9.02 by NWT wen START
    // let dataStr = $(dom).attr("json-data")
    // // add at 2021/08/12 for V9.02 by NWT wen END
    if ($(dom).prop("checked")) {
        // add at 2021/08/12 for V9.02 by NWT wen START
        var chkStr = $(dom).attr("json-data");
        var chk = JSON.parse(chkStr);
        var chkList = dataList.filter(item => item.id === chk.id && item.guardId === chk.guardId && item.stuId === chk.stuId && item.eventScheDelId === chk.eventScheDelId)
        selectData = selectData.concat(chkList);
        // selectData.push(JSON.parse(dataStr));
        // add at 2021/08/12 for V9.02 by NWT wen END
        selectedStu.push(dom.value);
        if (selectedStu.length !== 0 && selectedStu.length === $(".stu_check").length) {
            $("#allCheck")[0].checked = true;
        }
    } else {
        // add at 2021/08/12 for V9.02 by NWT wen START
        var chkStr = $(dom).attr("json-data");
        var chk = JSON.parse(chkStr);
        var unchkList = selectData.filter(item => item.id !== chk.id || item.guardId !== chk.guardId || item.stuId !== chk.stuId || item.eventScheDelId !== chk.eventScheDelId)
        selectData = unchkList;
        // add at 2021/08/12 for V9.02 by NWT wen END
        selectedStu.pop(dom.value);
        if (selectedStu.length !== $(".stu_check").length) {
            $("#allCheck")[0].checked = false;
        }
    }
}

function getContent(original) {
    if (!original){
        return '';
    }
    const answerTypeDiv = original.split(',')[0];
    if (['0', '1', '2'].indexOf(answerTypeDiv) >= 0) {
        return original.substring(2);
    }
    if (answerTypeDiv === '3') {
        var link = original.substring(2);
        if (link !== ''){
            link = '<a target="_self" class="hyper_link" style="text-decoration: underline; color: blue;" ' +
                'onclick="vm.showImg(\'' + encodeURIComponent(link) + '\')">添付</a>';
        }
        return link;
    }
    return '';
}
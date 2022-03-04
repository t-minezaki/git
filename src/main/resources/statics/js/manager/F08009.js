// 表示項目辞书
var params = getParam();
var dist = {
    // mounted: function () {
    //     this.showData();
    // },
    // methods: {
    //     showData: function () {
    //         $.ajax({
    //             url: ctxPath + '/manager/F21065/init',
    //             type: 'get',
    //             data: {
    //                 noticeId: params.id
    //             },
    //             datatype: 'json',
    //             success: function (data) {
    //                 if (data.code === 0) {
    //                     if (data.mstNoticeEntity) {
    //                         vm.mstNoticeEntity = data.mstNoticeEntity;
    //                     }
    //                     if (data.orgNm) {
    //                         vm.orgNm = data.orgNm;
    //                     }
    //                     if (data.pubPlanStartDt) {
    //                         vm.pubPlanStartDt = data.pubPlanStartDt;
    //                     }
    //                     if (data.pubPlanEndDt) {
    //                         vm.pubPlanEndDt = data.pubPlanEndDt;
    //                     }
    //                     getErrorData(data.pubPlanStartDt, data.pubPlanEndDt, params.id);
    //                 }
    //             }
    //         });
    //     }},
    'eventCd': {
        label: 'イベントコード',
        name: 'eventCd',
        index: 'eventCd',
        width: 150,
        key: true,
        sortable: true,
        align: "center"
    },
    'cretDatime': {
        label: '配信設定日', name: 'cretDatime', index: 'cretDatime', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.cretDatime;
            date = date.substring(0, 19);
            date = date.replace(/-/g, '/');
            var timestamp = new Date(date).getTime();
            // 根据毫秒数构建 Date 对象
            var date = new Date(timestamp);
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'updDatime': {
        label: '最終更新日', name: 'updDatime', index: 'updDatime', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.updDatime;
            date = date.substring(0, 19);
            date = date.replace(/-/g, '/');
            var timestamp = new Date(date).getTime();
            // ミリ秒数に応じてDateオブジェクトが構築されている
            var date = new Date(timestamp);
            // 書式期日
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'tmplateTitle': {
        label: 'テンプレート',
        name: 'tmplateTitle',
        index: 'tmplateTitle',
        width: 150,
        sortable: true,
        align: "center"
    }
    ,
    'ctgyNm': {label: 'カテゴリ', name: 'ctgyNm', index: 'ctgyNm', width: 150, sortable: true, align: "center"}
    ,
    'status': {label: '状態', name: 'status', index: 'status', width: 100, sortable: true, align: "center"}
    ,
    'deleverSum': {label: '配信数', name: 'deleverSum', index: 'deleverSum', width: 80, sortable: true, align: "center",
        formatter(cell, option, object) {
            return "<a href ='F08019.html?id=" + object.id + "&div="+ 1 + "&flg=" + object.usrFlg +"'>" + object.deleverSum + "</a>";
        }}
    ,
    'errorDataList': { label: '失敗件数',
        name: 'errorDataList',
        index: 'errorDataList',
        width: 80,
        sortable: true,
        align: "center",
        formatter(cell, option, object) {
            return "<a onclick=toF40026('" + object.id + "',1,3)>" + cell + "</a>";
    }}
    ,
    'notReadingSum': {label: '未読数', name: 'notReadingSum', index: 'notReadingSum', width: 80, sortable: true, align: "center",
        formatter(cell, option, object) {
            return "<a href ='F08019.html?id=" + object.id + "&div="+ 2 + "&flg=" + object.usrFlg +"'>" + object.notReadingSum + "</a>";
        }}
    ,
    'readingSum': {label: '既読数', name: 'readingSum', index: 'readingSum', width: 80, sortable: true, align: "center",
        formatter(cell, option, object) {
            return "<a href ='F08019.html?id=" + object.id + "&div="+ 3 + "&flg=" + object.usrFlg +"'>" + object.readingSum + "</a>";
        }}
    ,
    'notReplySum': {label: '未回答数', name: 'notReplySum', index: 'notReplySum', width: 80, sortable: true, align: "center",
        formatter(cell, option, object) {
            return "<a href ='F08019.html?id=" + object.id + "&div="+ 4 + "&flg=" + object.usrFlg +"'>" + object.notReplySum + "</a>";
        }}
    ,
    'replySum': {label: '回答数', name: 'replySum', index: 'replySum', width: 80, sortable: true, align: "center",
        formatter(cell, option, object) {
            return "<a href ='F08019.html?id=" + object.id + "&div="+ 5 + "&flg=" + object.usrFlg + "'>" + object.replySum + "</a>";
        }}
    ,
    'eventTitle': {label: 'タイトル', name: 'eventTitle', index: 'eventTitle', width: 300, sortable: true, align: "center"}
    ,
    'pubStartDt': {
        label: '公開開始日', name: 'pubStartDt', index: 'pubStartDt', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.pubStartDt;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'pubEndDt': {
        label: '公開終了日', name: 'pubEndDt', index: 'pubEndDt', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.pubEndDt;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'applyStartDt': {
        label: '申請開始日', name: 'applyStartDt', index: 'applyStartDt', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.applyStartDt;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'applyEndDt': {
        label: '申請終了日', name: 'applyEndDt', index: 'applyEndDt', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.applyEndDt;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'againSendDt': {
        label: 'プッシュ通知連絡日', name: 'againSendDt', index: 'againSendDt', width: 200, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.againSendDt;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'cretUserName': {
        label: '作成者名',
        name: 'cretUserName',
        index: 'cretUserName',
        width: 200,
        sortable: true,
        align: "center"
    }
    ,
    'updUserName': {
        label: '最終更新者名',
        name: 'updUserName',
        index: 'updUserName',
        width: 200,
        sortable: true,
        align: "center"
    },
    'refType': {
        label: 'refType',
        name: 'refType',
        index: 'refType',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'orgId': {
        label: '組織ID',
        name: 'orgId',
        index: 'orgId',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'scheduleCount': {
        label: 'イベント日程数',
        name: 'scheduleCount',
        index: 'scheduleCount',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'id': {
        label: 'id',
        name: 'id',
        index: 'id',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'operation': {
        label: '操作', name: 'operation', index: 'operation',
        width: 300,
        sortable: false,
        align: "center",
        formatter(cell, option, object) {
            // //セッションデータ．組織ID
            // var orgId = getCookie("brandcd");
            // /*
            //     'イベント．組織ID＝セッションデータ．組織ID
            //     　活性、
            //     以外の場合、
            //     　非活性
            //  */
            // var visible = orgId != object.orgId ? "hidden" : "visible";
            //戻るボタン

            var button = "<li><button onclick='editButton(" + object.id + ")' class='tbl_btn'>設定</button></li>";
            if (object.scheduleCount <= 0 && object.refType!='2') {
                button = "<li><button onclick='editButton(" + object.id + ")' class='tbl_btn noFocus'>設定</button></li>";
            }
            return "<ul>" +
                 button +
                "<li><button onclick = 'detailButton(" + object.id + ")' class='tbl_btn detail_btn'>詳細</button></li>"+
                "</ul>";
        }
    }
};

// 表示項目全集合
var wholeList = ['eventCd', 'cretDatime', 'updDatime', 'ctgyNm', 'cretUserName','errorDataList',
    'updUserName' ,'pubStartDt', 'pubEndDt', 'applyStartDt', 'applyEndDt', 'againSendDt','refType'];

var notDspList = ['eventTitle', 'status', 'deleverSum','errorDataList', 'notReadingSum',
    'readingSum', 'notReplySum', 'replySum', 'operation'];

var disableList = ['scheduleCount', 'id', 'orgId','refType'];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
// 条件を調べる
var ctgyNm = null, eventTitle = null, cretDatimeStart = null, cretDatimeEnd = null,
    updDatimeStart = null, updDatimeEnd = null, cretUserName = null, updUserName = null, eventStsDiv = null,
    pubStartDtStart = null, pubStartDtEnd = null, pubEndDtStart = null, pubEndDtEnd = null,
    applyStartDtStart = null, applyStartDtEnd = null, applyEndDtStart = null, applyEndDtEnd = null;

// リスト重載
function reload(col, hidden, ctgyNm_d, eventTitle_d, cretDatimeStart_d, cretDatimeEnd_d, updDatimeStart_d, updDatimeEnd_d, cretUserName_d, updUserName_d, eventStsDiv_d,
                pubStartDtStart_d, pubStartDtEnd_d, pubEndDtStart_d, pubEndDtEnd_d, applyStartDtStart_d, applyStartDtEnd_d, applyEndDtStart_d, applyEndDtEnd_d) {

    // 存储条件を調べる
    if (eventStsDiv_d != null)
        eventStsDiv = eventStsDiv_d;

    if (ctgyNm_d != null)
        ctgyNm = ctgyNm_d;

    if (eventTitle_d != null)
        eventTitle = eventTitle_d;

    if (cretDatimeStart_d != null)
        cretDatimeStart = cretDatimeStart_d;

    if (cretDatimeEnd_d != null)
        cretDatimeEnd = cretDatimeEnd_d;

    if (updDatimeStart_d != null)
        updDatimeStart = updDatimeStart_d;

    if (updDatimeEnd_d != null)
        updDatimeEnd = updDatimeEnd_d;

    if (cretUserName_d != null)
        cretUserName = cretUserName_d;

    if (updUserName_d != null)
        updUserName = updUserName_d;

    if (pubStartDtStart_d != null)
        pubStartDtStart = pubStartDtStart_d;

    if (pubStartDtEnd_d != null)
        pubStartDtEnd = pubStartDtEnd_d;

    if (pubEndDtStart_d != null)
        pubEndDtStart = pubEndDtStart_d;

    if (pubEndDtEnd_d != null)
        pubEndDtEnd = pubEndDtEnd_d;

    if (applyStartDtStart_d != null)
        applyStartDtStart = applyStartDtStart_d;

    if (applyStartDtEnd_d != null)
        applyStartDtEnd = applyStartDtEnd_d;

    if (applyEndDtStart_d != null)
        applyEndDtStart = applyEndDtStart_d;

    if (applyEndDtEnd_d != null)
        applyEndDtEnd = applyEndDtEnd_d;

    if (col != null)
        colList = col;

    if (hidden != null)
        hiddenList = hidden;

    var list = [];

    // 表示項目
    for (var i = 0; i < colList.length; i++) {
        if (dist[colList[i]]) {
            list.push(dist[colList[i]]);
        }
    }

    for (var i = 0; i < notDspList.length; i++) {
        if (dist[notDspList[i]] && colList.indexOf(notDspList[i]) < 0) {
            list.push(dist[notDspList[i]]);
        }
    }

    for (var i = 0; i < disableList.length; i++) {
        if (dist[disableList[i]]) {
            list.push(dist[disableList[i]]);
        }
    }

    var params= {
        ctgyNm: ctgyNm,
        eventTitle: eventTitle,
        cretDatimeStart: cretDatimeStart,
        cretDatimeEnd: cretDatimeEnd,
        updDatimeStart: updDatimeStart,
        updDatimeEnd: updDatimeEnd,
        cretUserName: cretUserName,
        updUserName: updUserName,
        eventStsDiv: eventStsDiv,
        pubStartDtStart: pubStartDtStart,
        pubStartDtEnd: pubStartDtEnd,
        pubEndDtStart: pubEndDtStart,
        pubEndDtEnd: pubEndDtEnd,
        applyStartDtStart: applyStartDtStart,
        applyStartDtEnd: applyStartDtEnd,
        applyEndDtStart: applyEndDtStart,
        applyEndDtEnd: applyEndDtEnd
    };
    var startTime = new Date();
    var endTime = new Date();
    startTime.setDate(startTime.getDate() - 30);
    getErrorData(startTime,endTime);
    // リストをはずす
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F08009/init',
        datatype: "json",
        postData: {
            params: JSON.stringify(params)
        },
        colModel:
        list,
        viewrecords: true,
        regional: 'jp',
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        sortable:true,
        sortorder:'desc',
        loadonce: true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader:
            {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount",
                orgId: "orgId",
                orgNm: "orgNm"
            }
        ,
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function (data) {
        }
        ,
        loadComplete: function (data) {

            $("#jqGrid").setGridHeight($(window).height() * 0.7);
            if (!data.code || data.code == 0) {

                $("div.ui-jqgrid-sortable").each(function(index,element){

                    if (element.id == "jqgh_jqGrid_operation") {

                        return ;
                    }

                    var asc = $(element).children().find("span[sort='asc']").first();
                    var span = $(element).children("span.s-ico").first();
                    var label = $(element).children("label");
                    if(label.length <= 0){
                        span.before("<label></label>");
                    }
                    if(asc.hasClass("ui-state-disabled")){
                        $(element).children("label").html("&#9650");
                    }else{
                        $(element).children("label").html("&#9660");
                    }
                });

                // ソートアイコン変換
                $("div.ui-jqgrid-sortable").bind("click",function () {

                    if (this.id == "jqgh_jqGrid_operation") {

                        return ;
                    }

                    var asc = $(this).children().find("span[sort='asc']").first();
                    var span = $(this).children("span.s-ico").first();
                    var label = $(this).children("label");
                    if(label.length <= 0){
                        span.before("<label></label>");
                    }
                    if(asc.hasClass("ui-state-disabled")){
                        $(this).children("label").html("&#9660");
                    }else{
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

// 編集リンク押下
function editButton(id) {
    $(location).attr("href", "F08010.html?id=" + id);
}

// プレビューボダン押下
function detailButton(id) {
    $(location).attr("href", "F08011.html?id=" + id);
}

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
    m = date.getMinutes() > 9 ? date.getMinutes() + ':' : '0' + date.getMinutes() + ':';
    s = date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds();
    return Y + M + D;
}

$(function () {

    $("button").click(function () {
        $("#message").hide();
    });

    // jqGridはウィンドウサイズに合わせて適応する
    $(window).resize(function () {
        $("#jqGrid").setGridHeight($(window).height() * 0.7);
    });

    // 検索リンク押下
    $("#search").click(function () {
        var srcWidth = $(window).width() * 0.4 + "px";
        var srcHeight = $(window).height() * 0.75 + "px";
        layer.open({
            id: 'F08009-1',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "検索項目",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F08009-1.html", 'no'],
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);//親子の連絡をとる
                var template = body.find('#template');
                var ctgy_nm = body.find('#ctgy_nm');
                var event_title = body.find('#event_title');
                var cret_TimeStart = body.find('#cretTimeStart');
                var cret_TimeEnd = body.find('#cretTimeEnd');
                var upd_DatimeStart = body.find('#updDatimeStart');
                var upd_DatimeEnd = body.find('#updDatimeEnd');
                var pub_StartDtStart = body.find('#pubStartDtStart');
                var pub_StartDtEnd = body.find('#pubStartDtEnd');
                var pub_EndDtStart = body.find('#pubEndDtStart');
                var pub_EndDtEnd = body.find('#pubEndDtEnd');
                var apply_StartDtStart = body.find('#applyStartDtStart');
                var apply_StartDtEnd = body.find('#applyStartDtEnd');
                var apply_EndDtStart = body.find('#applyEndDtStart');
                var apply_EndDtEnd = body.find('#applyEndDtEnd');
                var cret_UserName = body.find('#cretUserName');
                var upd_UserName = body.find('#updUserName');

                if (ctgyNm != null)
                    ctgy_nm.val(ctgyNm);

                if (eventTitle != null)
                    event_title.val(eventTitle);

                if (cretDatimeStart != null)
                    cret_TimeStart.val(cretDatimeStart);

                if (cretDatimeEnd != null)
                    cret_TimeEnd.val(cretDatimeEnd);

                if (updDatimeStart != null)
                    upd_DatimeStart.val(updDatimeStart);

                if (updDatimeEnd != null)
                    upd_DatimeEnd.val(updDatimeEnd);

                if (pubStartDtStart != null)
                    pub_StartDtStart.val(pubStartDtStart);

                if (pubStartDtEnd != null)
                    pub_StartDtEnd.val(pubStartDtEnd);

                if (pubEndDtStart != null)
                    pub_EndDtStart.val(pubEndDtStart);

                if (pubEndDtEnd != null)
                    pub_EndDtEnd.val(pubEndDtEnd);

                if (applyStartDtStart != null)
                    apply_StartDtStart.val(applyStartDtStart);

                if (applyStartDtEnd != null)
                    apply_StartDtEnd.val(applyStartDtEnd);

                if (applyEndDtStart != null)
                    apply_EndDtStart.val(applyEndDtStart);

                if (applyEndDtEnd != null)
                    apply_EndDtEnd.val(applyEndDtEnd);

                if (cretUserName != null)
                    cret_UserName.val(cretUserName);

                if (updUserName != null)
                    upd_UserName.val(updUserName);

            }
        });
    });

    // 表示項目変更ボタン押下
    $("#updateitem").click(function () {
        // var srcWidth = $(window).width() * 0.8;
        // if (srcWidth > 800) {
        //     var srcH = 500;
        //     srcWidth = '800px';
        // } else {
        //     var srcH = srcWidth;
        //     srcWidth = srcWidth + 'px'
        // }
        // var srcHeight = srcH * 0.78 + 'px';

        var srcWidth = $(window).width() * 0.7 + "px";
        var srcHeight = $(window).height() * 0.55 + "px";
        layer.open({
            id: 'F08009-2',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "表示可能項目",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F08009-2.html", 'no'],
            success: function (layero, index) {
                var cols = $(".ui-jqgrid-hbox table tr th");
                colList=[];
                for (var i =0 ;i< cols.length-3 ; i++){
                    var colName = cols[i].id.split("_")[1];
                    if (disableList.indexOf(colName) < 0){
                        colList.push(colName);
                    }
                }
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
            }
        });
    });

    // 新規作成ボタン押下
    $("#additem").click(function () {
        $(location).attr("href", "F08002.html");
    });

    // 表示項目初期化
    $.get(ctxPath + "/manager/F08009/getDspItems", function (rsp) {
        if (rsp.F08009DspDto) {
            wholeList = rsp.F08009DspDto.allItemslist;
            notDspList = rsp.F08009DspDto.mustItemslist;
            for (var i = 0; i < rsp.F08009DspDto.dspItemslist.length; i++) {
                colList.push(rsp.F08009DspDto.dspItemslist[i]);
            }
            for (var i = 0; i < wholeList.length; i++) {
                if (colList.indexOf(wholeList[i]) < 0) {
                    //表示可能項目
                    hiddenList.push(wholeList[i]);
                }
            }
            reload(colList, hiddenList, null, null, null, null, null, null, null, null, null);
        }
    });
});
function toF40026(noticeId,msgType,msgTypeDiv) {
    //2021/02/02 liyuhuan add start
    window.location.href = "../pop/F40026.html?noticeId=" + noticeId + "&msgType=" + msgType + "&msgTypeDiv=" + msgTypeDiv;
    //2021/02/02 liyuhuan add end
}
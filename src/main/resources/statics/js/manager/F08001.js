// 表示項目辞书
var dist = {
    'eventCd': {
        label: 'イベントコード',
        name: 'eventCd',
        index: 'eventCd',
        width: 150,
        key: true,
        sortable: true,
        align: "center"
    }
    ,
    'cretDatime': {
        label: '作成日', name: 'cretDatime', index: 'cretDatime', width: 150, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.cretDatime;
            // 格式化日期
            var dateTime = dateFmt(date);
            return dateTime;
        }
    }
    ,
    'updDatime': {
        label: '最終更新日', name: 'updDatime', index: 'updDatime', width: 150, sortable: true, align: "center",
        formatter(cell, option, object) {
            var date = object.updDatime;
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
        width: 200,
        sortable: true,
        align: "center"
    }
    ,
    'orgNm': {
        label: '作成組織',
        name: 'orgNm',
        index: 'orgNm',
        width: 120,
        sortable: true,
        align: "center"
    }
    ,
    'ctgyNm': {label: 'カテゴリ', name: 'ctgyNm', index: 'ctgyNm', width: 100, sortable: true, align: "center"}
    ,
    'status': {label: '状態', name: 'status', index: 'status', width: 100, sortable: true, align: "center"}
    ,
    'object': {label: '対象', name: 'object', index: 'object', width: 100, sortable: true, align: "center"}
    ,
    'eventTitle': {label: 'タイトル', name: 'eventTitle', index: 'eventTitle', width: 200, sortable: true, align: "center"}
    ,
    'attachFilePath': {
        label: '添付ファイル', name: 'attachFilePath', index: 'attachFilePath', width: 400, sortable: true, align: "center",
        formatter(cell, option, object) {
            // 2021/1/12 huangxinliang modify start
            var temp = '';
            if(object.attachFilePath) {
                var filePath = object.attachFilePath;
                var filename = '';
                for (var i = 0; i < filePath.split(",").length; i++) {
                    filename = filePath.split(",")[i];
                    if (temp !== ''){
                        temp += ',';
                    }
                    temp += "<a title = '"+ handleFileName(filename) +"' href ='" + filename + "'>" + handleFileName(filename) + "</a>";
                }
            }
            return temp;
            // 2021/1/12 huangxinliang modify end
        }
    }
    ,
    'cretUserName': {
        label: '作成者名',
        name: 'cretUserName',
        index: 'cretUserName',
        width: 120,
        sortable: true,
        align: "center"
    }
    ,
    'updUserName': {
        label: '最終更新者名',
        name: 'updUserName',
        index: 'updUserName',
        width: 120,
        sortable: true,
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
    'id': {
        label: 'id',
        name: 'id',
        index: 'id',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'refType': {
        label: 'refType',
        name: 'refType',
        index: 'refType',
        hidden: true,
        sortable: false,
        align: "center"
    }
    ,
    'eventCont': {label: '本文', name: 'eventCont', index: 'eventCont', width: 200, sortable: true, align: "center",formatter(cell, option, object) {
            var cont = decodeURIComponent(object.eventCont);
            while (cont){
                var sub1 = cont.indexOf('<img');
                if(sub1 != -1){
                    var sub2 = cont.substring(sub1).indexOf('/>');
                    cont = cont.replace(cont.substring(sub1 , sub2 + sub1 + 2),'');
                }else{
                    break;
                }
            }
            cont = cont.replace(/%/g, "%25")
            return "<div class='eventCont'>" + decodeURIComponent(cont) + "</div>";
        }}
    ,
    'operation': {
        label: '操作', name: 'operation', index: 'operation',
        width: 600,
        sortable: false,
        align: "center",
        formatter(cell, option, object) {
            //セッションデータ．組織ID
            var orgId = getCookie("orgId");
            /*
                'イベント．組織ID＝セッションデータ．組織ID
                　活性、
                以外の場合、
                　非活性
             */
            var visible = orgId != object.orgId ? "disabled"+" "+ 'style="background-color: #ccc !important"' : "visible";
            var visible1 = orgId == object.orgId && object.refType!='2' ? "visible" : "disabled"+" "+ 'style="background-color: #ccc !important"';
            //戻るボタン
            return "<ul>" +
                "<li><button onclick = 'detailButton(" + object.id + ")' class='tbl_btn detail_btn'>プレビュー</button></li>" +
                "<li><button onclick='editButton(" + object.id + ")' class='tbl_btn'"+ visible+">編集</button></li>" +
                "<li><button onclick='scheduleButton(" + object.id + ")' class='tbl_btn' "+visible1+">日程</button></li>" +
                "<li><button onclick='deleteButton(" + object.id + ",\"" + object.updateStrForCheck + "\")' class='tbl_btn' "+visible+">削除</button></li>" +
                "</ul>";
        }
    }
};

// 表示項目全集合
var wholeList = ['eventCd', 'cretDatime', 'updDatime', 'tmplateTitle', 'ctgyNm', 'attachFilePath', 'cretUserName', 'updUserName', 'eventCont' , 'orgNm','refType'];

var notDspList = ['eventTitle', 'status', 'object', 'operation'];

var disableList = ['id', 'orgId','refType'];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
// 条件を調べる
var tmplateId = null, ctgyNm = null, eventTitle = null, cretDatimeStart = null, cretDatimeEnd = null,
    updDatimeStart = null, updDatimeEnd = null, cretUserName = null, updUserName = null, eventStsDiv = null, object = null;

// リスト重載
function reload(col, hidden, tmplateId_d, ctgyNm_d, eventTitle_d, cretDatimeStart_d, cretDatimeEnd_d, updDatimeStart_d, updDatimeEnd_d, cretUserName_d, updUserName_d, eventStsDiv_d, object_d) {

    // 存储条件を調べる
    if (tmplateId_d != null)
        tmplateId = tmplateId_d;
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
    if (eventStsDiv_d != null)
        eventStsDiv = eventStsDiv_d;
    if (object_d != null)
        object = object_d;
    if (col != null)
        colList = col;
    if (hidden != null)
        hiddenList = hidden;
    var list = [];

    var params= {
        tmplateId: tmplateId,
        ctgyNm: ctgyNm,
        eventTitle: eventTitle,
        cretDatimeStart: cretDatimeStart,
        cretDatimeEnd: cretDatimeEnd,
        updDatimeStart: updDatimeStart,
        updDatimeEnd: updDatimeEnd,
        cretUserName: cretUserName,
        updUserName: updUserName,
        eventStsDiv: eventStsDiv,
        object: object
    };

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
    // リストをはずす
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F08001/init',
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
        /* 2020/12/23 UT-40 cuikailin modify start */
        loadonce: true,
        /* 2020/12/23 UT-40 cuikailin modify end */
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
            } else {

                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
        }
    });

    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}

// 削除リンク押下
function deleteButton(id, updateStrForCheck) {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.post({
                url: ctxPath + '/manager/F08001/del',
                type: 'POST',
                data: {
                    id: id,
                    updateStrForCheck: updateStrForCheck
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var idx = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                                location.reload();
                        //         layer.close(idx);
                        //     }
                        // })

                    }
                }
            });
        }
    })
}

// 編集リンク押下
function editButton(id) {
    $(location).attr("href", "F08002.html?id=" + id);
}

function getFileIcon(url){
    var ext = url.substr(url.lastIndexOf('.') + 1).toLowerCase(),
        maps = {
            "rar":"icon_rar.gif",
            "zip":"icon_rar.gif",
            "tar":"icon_rar.gif",
            "gz":"icon_rar.gif",
            "bz2":"icon_rar.gif",
            "doc":"icon_doc.gif",
            "docx":"icon_doc.gif",
            "pdf":"icon_pdf.gif",
            "mp3":"icon_mp3.gif",
            "xls":"icon_xls.gif",
            "chm":"icon_chm.gif",
            "ppt":"icon_ppt.gif",
            "pptx":"icon_ppt.gif",
            "avi":"icon_mv.gif",
            "rmvb":"icon_mv.gif",
            "wmv":"icon_mv.gif",
            "flv":"icon_mv.gif",
            "swf":"icon_mv.gif",
            "rm":"icon_mv.gif",
            "exe":"icon_exe.gif",
            "psd":"icon_psd.gif",
            "txt":"icon_txt.gif",
            "jpg":"icon_jpg.gif",
            "png":"icon_jpg.gif",
            "jpeg":"icon_jpg.gif",
            "gif":"icon_jpg.gif",
            "ico":"icon_jpg.gif",
            "bmp":"icon_jpg.gif",
            "xlsx":"icon_xls.gif",
            "csv":"icon_xls.gif"
        };
    return maps[ext] ? maps[ext]:maps['txt'];
}

// プレビューボダン押下
function detailButton(id) {

    $.get(ctxPath + "/manager/F08001/getEventCont",
        {
            eventId: id
        }, function (data) {
            if (data.code == 0) {
                var eventCont = data.event.eventCont;
                var attachFilePath = decodeURIComponent(data.attachFilePath);

                var srcWidth = $(window).width() + "px";
                var srcHeight = $(window).height() + "px";
                layer.open({
                    id: 'preview',
                    type: 2,
                    anim: 2,
                    skin: "layui-layer-myskin",
                    title: "プレビュー",
                    shade: 0.2,
                    closeBtn: 1,
                    shadeClose: false,
                    move: '.layui-layer-title',
                    area: [srcWidth, srcHeight],
                    resize: false,
                    content: ["../pop/my_preview.html", 'no'],
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);

                        var preview = body.find('#preview');
                        $(preview).html(decodeURIComponent(eventCont));
// 2021/1/12 huangxinliang modify start
                        var pathss = attachFilePath;
                        var icons = [];
                        var preUrl = "";
                        var paths = [];
                        var displayNms = [];
                        for (var i = 0; i < pathss.split(",").length; i++) {
                            var filepath = pathss.split(',')[i];
                            displayNms.push(handleFileName(filepath));
                            icons.push(getFileIcon(filepath));
                            paths.push(filepath.split('..')[1]);
                        }
                        preUrl = window.location.origin + ctxPath;
                        for(var j = 0; j < paths.length; j++){
                            paths[j] = preUrl + paths[j];
                        }
                        if (pathss && pathss != null && pathss != undefined && pathss != "undefined" && pathss != "null"){
                            var links = '';
                            for (j = 0; j < paths.length; j++){
                                links = links +
                                    '<img style="vertical-align: middle; margin-right: 2px;" src="' + preUrl + '/plugins/ueditor-1.4.3.3/dialogs/attachment/fileTypeImages/' + icons[j] + '">' +
                                    '<a id="fileNm" style="font-size:12px; color:#0066cc;" href="' + paths[j] + '" title="' + displayNms[j] + '">'+displayNms[j]+'</a></br>';
                            }
                            $(preview).append('' +
                                '<p style="line-height: 16px;">' +
                                links +
                                '</p>');
                        }
// 2021/1/12 huangxinliang modify end
                        $(preview).prev().css("width", "600px", "text-align", "center");
                        $(preview).height("90%");
                    }

                });
            } else {
                showMsg(data.msg);
            }
        });
}

// 日程リンク押下
function scheduleButton(id) {
    $(location).attr("href", "F08005.html?eventId=" + id);
}

// 期日フォーマット
function dateFmt(val) {
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
        var srcWidth = $(window).width() * 0.8;
        // if (srcWidth > 400) {
        //     var srcH = 700;
        //     srcWidth = '550px';
        // } else {
        //     var srcH = srcWidth;
        //     srcWidth = srcWidth + 'px'
        // }
        // var srcHeight = srcH * 0.78 + 'px';

        var srcWidth = $(window).width() * 0.45 + "px";
        var srcHeight = $(window).height() * 0.6 + "px";
        layer.open({
            id: 'F08001-1',
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
            content: ["../pop/F08001-1.html", 'no'],
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var template = body.find('#template');
                var ctgy_nm = body.find('#ctgy_nm');
                var event_title = body.find('#event_title');
                var cret_TimeStart = body.find('#cretTimeStart');
                var cret_TimeEnd = body.find('#cretTimeEnd');
                var upd_DatimeStart = body.find('#updDatimeStart');
                var upd_DatimeEnd = body.find('#updDatimeEnd');
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
            id: 'F08001-2',
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
            content: ["../pop/F08001-2.html", 'no'],
            success: function (layero, index) {
                var cols = $(".ui-jqgrid-hbox table tr th");
                colList=[];
                for (var i =0 ;i< cols.length; i++){
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
    $.get(ctxPath + "/manager/F08001/getDspItems", function (rsp) {
        if (rsp.f08001DspDto) {
            notDspList = rsp.f08001DspDto.mustItemslist;
            wholeList = rsp.f08001DspDto.allItemslist;
            for (var i = 0; i < rsp.f08001DspDto.dspItemslist.length; i++) {
                colList.push(rsp.f08001DspDto.dspItemslist[i]);
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
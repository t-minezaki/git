var param = getParam();
var height;
//項目と教科の列
var colModel = [
    {id:'id',label: '項目', name: 'content', index: 'content', fixed: true, width: 150, resizable : false, sortable: true, sorttype: 'text', align: "center"},
    {label: '教科', name: 'subject', index: 'subject', fixed: true, width: 80, resizable : false, sortable: true, sorttype: 'text', align: "center"}
];
var vm = new Vue({
    el: '#content',
    data: {
        month: '',
        stuId: '',
        stuName: '',
        data: []
    },
    mounted: function(){
        this.setUp();
    },
    updated: function(){
        getResult();
    },
    methods: {
        setUp: function () {
            setTargetControlHeight("content", "body", "one");
            height = getRemainingHeight("content", "div-select", "div-download", "div-button") - $(window).height() * 0.18;
            //param
            this.stuName = decodeURIComponent(param.stuNm);
            this.stuId = decodeURIComponent(param.stuId);
            this.month = new Date().format('Y-m');
            laydate.render({
                elem: '#monthSelect',
                type: 'month',
                format: 'yyyy年MM月',
                value: new Date(),
                max: new Date().format('Y-m'),
                eventElem: '#dayPic',
                trigger: 'click',
                done: function (value, date) {
                    if (vm.month != value) {
                        vm.month = value.replace(/(\d{4})年(\d{2})月/, '$1-$2');
                        getResult();
                    }
                }
            });
        }
    }
});
//テーブルをリセットしてデータを取得する
function getResult() {
    if ($("#message") != null) {
        $("#message").html('');
    }
    //colModelを生成
    var columns = [];
    columns.push(colModel[0]);
    columns.push(colModel[1]);
    //月の最大日
    var maxDay = getDaysOfMonth(vm.month);
    //選択した月
    var month = vm.month.match(/-\d{1,2}/).toString().replace(/-/, '');
    for (var i = 0; i < maxDay; i++){
        var day = (i + 1) < 10 ? '0' + (i + 1) : (i + 1);
        vm.index = i;
        //colModel生成する
        columns.push({
            label: month + '/' + day,
            name: 'day' + i,
            index: 'day' + i,
            width: 100,
            resizable : false,
            key: false,
            sortable: false,
            align: "center",
            fixed: true,
            formatter: function (cell, option, object) {
                //使用テキスト「USE_CONT」、指導内容「GUID_CONT」、宿題内容「HWK_CONT」、小テスト単元名
                if (object.content == '使用テキスト' || object.content == '指導内容' || object.content == '宿題内容' || object.content == '小テスト単元名' || object.content == '連絡事項内容'){
                    if (cell){
                        return "入力済";
                    }
                    else {
                        return "";
                    }
                }else {
                    if (cell){
                        return cell;
                    }else {
                        return "";
                    }
                }
            }
        })
    }

    //jqGrid re-init
    $.jgrid.gridUnload("jqGrid");
    $('#jqGrid').jqGrid({
        datatype: 'local',
        colModel: columns,
        /* 2020/12/21 liguangxin add start*/
        multiselect: false,
        rowNum: 30,
        rowList: [10, 30, 50],
        viewrecords: true,
        pager: "#jqGridPager",
        width: 600,
        height:height,
        /* 2020/12/21 liguangxin add end*/
        gridComplete: function () {
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            $("#jqGrid").setGridWidth($(window).width()*0.96);
        }
    });

    //データを取得する
    $.ajax({
        url: ctxPath + '/manager/F21014/getResult',
        type: 'GET',
        dataType: "json",
        data: {
            stuId: vm.stuId,
            month: vm.month
        },
        success: function (data) {
            if (data.code != 0){
                showMsg(data.msg);
                return;
            }
            if (data.result) {
                vm.data = data.result;
                reload(data.result)
            }else {
                vm.data = [];
            }
        }
    });
}
//データの平坦化とデータ入力
function reload(result) {
    for (var i = 0; i < result.length; i++){
        var r = {
            content: result[i].content,
            subject: result[i].subject
        };
        for (var j = 0; j < getDaysOfMonth(vm.month); j++){
            var column = 'day' + j;
            r["day" + j] = result[i].day[j];
        }
        $('#jqGrid').jqGrid('addRowData', i + 1, r);
    }
    /* 2020/12/21 liguangxin add start*/
    //refresh jqGrid
    // $('#jqGrid').trigger("reloadGrid");
    var localData = {page: 1, total: 2, records: "2", rows: result};
    localData.rows = result;
    localData.records = result.length;
    localData.total = (result.length % 30 == 0) ? (result.length / 30) : (Math.floor(result.length / 30) + 1);
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
    $('#jqGrid').setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
    /* 2020/12/21 liguangxin add end*/
    //並べ替えアイコン
    $("div.ui-jqgrid-sortable").each(function(index,element){

        if (index > 1){
            return;
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

        if ((this.id != "jqgh_jqGrid_content") && (this.id != "jqgh_jqGrid_subject")) {

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
}
//イベントのサイズを変更する
$(function () {
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(window).width()*0.96);
    });
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $(".stuNameDiv").css("font-size","1.8vh")
                    $(".label").css("font-size","1.8vh")
                    $(".input").css("width","10vw").css("height","3vh").css("font-size","1.8vh")
                }
                else if (/iPad/i.test(navigator.userAgent)) {
                    $(".stuNameDiv").css("font-size","1.8vh")
                    $(".label").css("font-size","1.8vh")
                    $(".input").css("width","10vw").css("height","2.5vh").css("font-size","1.8vh")
                }
                else {
                    // alert("other")
                }
            }
            catch (e) {
            }
        } else {
            // alert("456")
        }
    } else {
        if (/Macintosh/i.test(navigator.userAgent)){
            $(".stuNameDiv").css("font-size","1.8vh")
            $(".label").css("font-size","1.8vh")
            $(".input").css("width","10vw").css("height","2.5vh").css("font-size","1.8vh")
        }
    }
});
//年と月に従って月の最大日数を取得する
function getDaysOfMonth(yearMonth){
    var date=new Date(yearMonth.match(/\d{4}/),yearMonth.match(/-\d{1,2}/).toString().replace(/-/, ''),0);
    var days=date.getDate();
    return days;
}
//「戻る」を押す
function toF21013() {
    window.location.href = 'F21013.html';
}
//「ダウンロード」を押す
function exportExcel() {
    saveAttendBook()
    // if ($("#message") != null) {
    //     $("#message").hide();
    // }
    // if (vm.data == null || vm.data.length == 0){
    //     showMsg($.format($.msg.MSGCOMN0017, "個人の出席簿と指導報告情報"));
    //     return;
    // }
    // $.ajax({
    //     url: ctxPath + '/manager/F21014/export',
    //     type: 'POST',
    //     dataType: "json",
    //     data: {
    //         data: JSON.stringify(vm.data),
    //         days: getDaysOfMonth(vm.month),
    //         month: vm.month.match(/-\d{1,2}/).toString().replace(/-/,'')
    //     },
    //     success: function (data) {
    //         if (data.code != 0){
    //             showMsg(data.msg);
    //             return;
    //         }
    //         if (data.fileName) {
    //             $("#exportForm #fileName").val(data.fileName);
    //             $("#exportForm #month").val(vm.month.replace(/-/, '年'));
    //             $("#exportForm #stuId").val(vm.stuId);
    //             $("#exportForm").submit();
    //         }
    //     }
    // });
}

function saveAttendBook() {
    const data = dataFormat();
    const sheet = XLSX.utils.json_to_sheet(data);
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, sheet, "sheet1");
    const workbookBlob = workbook2blob(wb);
    const fileName = vm.month.replace(/-/, '年') + '月_' + vm.stuName + '_指導報告書_' + new Date().format('Ymd') + '.xlsx';
    openDownloadDialog(workbookBlob, fileName);
}

//データの平坦化
function dataFormat(){
    let result = [];
    for (let i in vm.data) {
        let data = "";
        const content = vm.data[i];
        const daysContent = content.day;
        const days = getDaysOfMonth(vm.month);
        const month = vm.month.match(/-\d{1,2}/).toString().replace(/-/, '');
        data += "{\"項目\":\"" + content.content + "\"";
        data += ",\"教科\":\"" + content.subject + "\"";
        for (let j = 0; j < days; j++){
            data += ",\"" + month + "月" + (j + 1) + "日\":";
            if (daysContent[j]){
                data += "\"" + daysContent[j] + "\"";
            }else {
                data += "null";
            }
        }
        data += "}";
        data = JSON.parse(data);
        result.push(data);
    }
    return result;
}
// blobオブジェクトへのワークブック
function workbook2blob(workbook) {
    // Excel構成アイテムを生成する
    var wopts = {
        // 生成されるファイルのタイプ
        bookType: "xlsx",
        // // 共有文字列テーブルを生成するかどうか、公式の説明では、生成をオンにすると速度は低下しますが、IOSデバイスの下位バージョンではより良い互換性があります
        bookSST: false,
        type: "binary"
    };
    var wbout = XLSX.write(workbook, wopts);
    // 文字列をArrayBufferに変換します
    function s2ab(s) {
        var buf = new ArrayBuffer(s.length);
        var view = new Uint8Array(buf);
        for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xff;
        return buf;
    }
    var blob = new Blob([s2ab(wbout)], {
        type: "application/octet-stream"
    });
    return blob;
}

// blobオブジェクトのblob URLを作成し、aタグを使用してポップアップダウンロードボックスを実装します
function openDownloadDialog(blob, fileName) {
    if (typeof blob == "object" && blob instanceof Blob) {
        blob = URL.createObjectURL(blob); // BLOBアドレスを作成する
    }
    var aLink = document.createElement("a");
    aLink.href = blob;
    // 新しいHTML5属性、保存ファイル名を指定、接尾辞は不要、ファイル：///モードが有効にならない場合がある
    aLink.download = fileName || "";
    var event;
    if (window.MouseEvent) event = new MouseEvent("click");
    //   モバイル
    else {
        event = document.createEvent("MouseEvents");
        event.initMouseEvent( "click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null );
    }
    aLink.dispatchEvent(event);
}
function mGetDate(){
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var d = new Date(year, month, 0);
    return d.getDate();
}
var year="";
var month="";
var closeFlg= false;
var stuIdStr ="";
var show = 0;


var dist = [
    {
        label: '对象日期',
        name: 'tgtYmd',
        index: 'tgtYmd',
        width: 150,
        // key: true,
        sortable: false,
        align: "center",
        hidden:true
    },
    {
        label: '对象',
        name: 'codValue',
        index: 'codValue',
        width: 150,
        // key: true,
        sortable: false,
        align: "center",
        hidden:true
    },
    {
        label: '生徒ID',
        name: 'afterUsrId',
        index: 'afterUsrId',
        width: 150,
        // key: true,
        sortable: true,
        align: "center"
    }
    ,
    {
        label: '生徒名',
        name: 'stuNm',
        index: 'stuNm',
        width: 150,
        // key: true,
        sortable: true,
        align: "center",
        formatter(cell, option, object){
            var params={};
            params.stuId = object.stuId;
            params.afterUsrId = object.afterUsrId;
            params.subjtDiv = object.subjtDiv;
            params.stuNm = object.stuNm;
            return "<a  href='F21014.html?"+ $.param(params) +"' class='link'>"+object.stuNm+"</a>"
        }
    }
    ,
    {
        label: '教科',
        name: 'subjtDiv',
        index: 'subjtDiv',
        width: 150,
        // key: true,
        sortable: false,
        align: "center"
    },
    {
        label: 'stuId',
        name: 'stuId',
        index: 'stuId',
        width: 150,
        // key: true,
        sortable: false,
        align: "center",
        hidden:true
    }

];
var list = [];
var stuIdList = [];
var date = new Date();

if ((date.getMonth() + 1)<10 ){
     month = "0"+ (date.getMonth() + 1);
}
else {
    month =  date.getMonth() + 1;
}
var height = $(window).height()*0.6;
var vm = new Vue({
    el: "#app",
    data: {
        mstCodDEntityList: "",
        month: month,
        d:"",
        a:""
        // data:[]
    },
    mounted: function () {
        this.setUp();
        this.getInfo();
    },
    updated: function() {
      $(".layui-laydate-list.laydate-month-list").find("li").each(function (element,index) {
          $(this).innerText = "0" + index + "月"
      })
    },
    methods: {
        setUp: function(){
            setTargetControlHeight("div-main", "content", "one", "div-select");
            height = getRemainingHeight("div-main", "div-head") - $(window).height() * 0.15;
            laydate.render({
                elem: '#monthChoose'
                ,type: 'month'
                ,format:'yyyy年MM月'
                ,value: new Date()
                ,max: new Date().Format('Y-m')
                ,eventElem: '#timeOne'
                ,trigger: 'click'
                ,ready: function () {
                    $(".layui-laydate-list.laydate-month-list").find("li").each(function (index,element) {
                        $(this).text((index + 1) + "月")
                    })
                }
                ,done: function (value) {
                    $(".time_input").val(value);
                    year = $("#monthChoose").val().split('年')[0];
                    month =  $("#monthChoose").val().split('年')[1].substring(0,2);
                    stuListFirst = stuIdList;
                    vm.getInfo(show,month,stuListFirst,stuIdStr,year);
                }
            });
        },
        getInfo: function (show,month,stuListFirst,stuIdStr,year) {
            $.ajax({
                url:ctxPath + '/manager/F21013/getMstCod',
                datatype:'json',
                type:'GET',
                success:function (data) {
                    vm.mstCodDEntityList = data.mstCodDEntityList;
                }
            })
            if (month == undefined) {
                if ((date.getMonth() + 1)<10 ){
                    month = "0"+ (date.getMonth() + 1);
                }
                else {
                    month =  date.getMonth() + 1;
                }
            }
            var list =  getShowItems(show,month,stuListFirst,stuIdStr,year);
            $.jgrid.gridUnload("jqGrid");
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F21013/init',
                datatype: "json",
                postData: {
                    month: month,
                    year:year,
                    cd:show,
                    d:mGetDate(),
                    stuIdListStr:JSON.stringify(stuListFirst),
                    stuIdStr:stuIdStr
                },
                colModel:
                list,
                viewrecords: true,
                regional: 'jp',
                height:height,
                rowNum: 30,
                rowList: [10, 30, 50],
                rownumbers: false,
                rownumWidth: 25,
                sortable: false,
                sortorder: 'desc',
                multiselect:false,
                loadonce: true,
                pager: "#jqGridPager",
                jsonReader:
                    {
                        root: "page.list",
                        page: "page.currPage",
                        total: "page.totalPage",
                        records: "page.totalCount",

                    }
                ,
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                gridComplete: function (data) {
                    // 2020/12/28 huangxinliang modify start
                    $('#choose').attr('disabled', false);
                    // 2020/12/28 huangxinliang modify end
                }
                ,
                loadComplete: function (data) {
                    stuIdList = [];
                    // vm.data = data.page.list;
                    if (data.page){
                        if (data.rows != undefined){
                            for (var i = 0; i < data.rows.length; i ++) {

                                stuIdList.push(data.rows[i].stuId);
                            }
                        }else {
                            for (var i = 0; i < data.page.list.length; i ++) {

                                stuIdList.push(data.page.list[i].stuId);
                            }
                        }
                    }

                    $("#jqGrid").setGridHeight(height);
                    if (!data.code || data.code == 0) {

                        $("div.ui-jqgrid-sortable").each(function(index,element){

                            if (element.id != "jqgh_jqGrid_afterUsrId" && element.id != "jqgh_jqGrid_stuNm" ) {

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
                            if (this.id != "jqgh_jqGrid_afterUsrId" && this.id != "jqgh_jqGrid_stuNm") {

                                return;
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
                    else {

                        // 状態コードが0でない場合は警告情報が表示される
                        showMsg(data.msg);
                    }
                }

            });

            // jqgrid重載
            $('#jqGrid').trigger('reloadGrid');

        },
        // 2020/12/28 huangxinliang modify start
        search: function (){
            var srcWidth = "750px";
            var srcHeight = "400px";
            layer.open({
                id: 'F09003',
                type: 2,
                anim: 2,
                skin: "layui-layer-myskin",
                title: [" ",'height:3vh'],
                shade: 0.2,
                closeBtn: 1,
                shadeClose: false,
                move: '.layui-layer-title',
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                background:'#F0F0F0',
                content: ["../pop/F09003.html?", 'no'],
                success: function (layero, index) {
                },
                end: function () {
                    if (closeFlg) {
                        stuListFirst = stuIdList;
                        stuIdStr = JSON.stringify(stuList);
                        vm.getInfo(show,month,stuListFirst,stuIdStr,year)
                    }
                }
            });
        },
        download: function (){
            var rowData = getJQAllData();
            for (var i = 0; i < rowData.length; i++) {
                var result= JSON.stringify(rowData[i]).replace("入力済",rowData[i].codValue);
                rowData[i] = JSON.parse(result);
            }
            saveAttendBook(rowData);
        },
        show: function (){
            $('#choose').attr('disabled',true);
            show =  $("#choose").val();
            stuListFirst = stuIdList;
            vm.getInfo(show,month,stuListFirst,stuIdStr,year);
        }
        // 2020/12/28 huangxinliang modify end
    }

});

function getJQAllData() {

    $("#jqGrid").jqGrid('getGridParam','colNames').splice(3,1);
    var obj = $("#jqGrid");
    var rowIds = obj.getDataIDs();
    var arrayData = new Array();
    if (rowIds.length > 0) {
        for (var i = 0; i < rowIds.length; i++) {
            arrayData.push(obj.getRowData(rowIds[i]));
        }
    }
    return arrayData;
}
function getMonthLength(date) {
    var d = new Date(date);
    d.setMonth(month);
    d.setDate('1');
    d.setDate(d.getDate() - 1);
    return d.getDate();
}
function getShowItems(show,month) {

    var list = [];

    for (var i = 0; i < dist.length; i ++) {

        list.push(dist[i]);
    }
    var dayDate = new Date().Format("yyyy-MM-dd");
    var allDays = getMonthLength(dayDate);

        for (var i = 1; i <= allDays; i++) {
            var mon = "";
            mon = month ;
            var day = i < 10 ? '0' + i : i;
            var date = {
                label: mon + '/' + day,
                name: mon + '/' + day,
                index: mon + '/' + day,
                width: 150,
                key: true,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    if (object.tgtYmd != undefined) {
                        var List = object.tgtYmd.split(",");
                        var status = '';
                        for (var j = 0; j < List.length; j++) {
                            var d = List[j].substring(8, 10);
                            if (option.colModel.label.substring(3) == d) {
                                if (show == 4 || show == 11 ||show == 5 ||show == 6 || show == 7) {
                                    if (!object.codValue) {
                                        status ="";
                                    }else {
                                        status = "入力済";
                                    }
                                }else {
                                    if (show == 10){
                                        status = object.codValue;
                                    }else {
                                        if (show == 2 || show == 8 || show == 9){
                                            if (object.codValue==null){
                                                object.codValue == "";
                                            }else {
                                                status = object.codValue;

                                            }
                                        } else {
                                            status = object.codValue.split(",")[j];

                                        }

                                    }
                                }

                            }
                        }
                        return status;
                    }
                }
            };
            list.push(date)

    }

        return list;
}


$(function(){
    var date = new Date();
    year = date.getFullYear();
    var a = date.getMonth() + 1;
    var b = a < 10? "0" + a: a;
    var nowDate = year + "年" + b + "月" ;
    $(".time_input").val(nowDate);
});

function saveAttendBook(oldData) {
    var data = dataFormat(oldData);
    var sheet = XLSX.utils.json_to_sheet(data);
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, sheet, "sheet1");
    var workbookBlob = workbook2blob(wb);
    var a = new Date();
    var fileName = a.Format("yyyy") + '年_' + month+ '月_'  + '指導報告書_' +new Date().Format('yyyyMMdd') + '.xlsx';
    openDownloadDialog(workbookBlob, fileName);
}

//データの平坦化
function dataFormat(data){
    for (var i = 0; i <data.length ; i++) {
        delete data[i].stuId;
        delete data[i].tgtYmd;
        delete data[i].codValue;
    }
    var dataStr = JSON.stringify(data);
    dataStr = dataStr.replace(/\"afterUsrId\"/g,'"生徒ID"');
    dataStr = dataStr.replace(/<\/?a[^>]*>/g,'');
    dataStr = dataStr.replace(/\"stuNm\"/g,'"生徒名"');
    dataStr = dataStr.replace(/\"subjtDiv\"/g,'"教科"');
    dataStr = dataStr.replace(/\"stuId\"/g,'"生徒"');
    dataStr = dataStr.replace(/\"(\d{2})\/(\d{2})\"/g, '"$1月$2日"');
    // dataStr = dataStr.replace(/\//g,"月");
    // dataStr = dataStr.replace(/stuId)
    data = JSON.parse(dataStr);

    return data;
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
        blob = URL.createObjectURL(blob); // 创建blob地址
    }
    var aLink = document.createElement("a");
    aLink.href = blob;
    // 新しいHTML5属性、保存ファイル名を指定、接尾辞は不要、ファイル：///モードが有効にならない場合がある
    aLink.download = fileName || "";
    var event;
    if (window.MouseEvent) event = new MouseEvent("click");
    //  モバイル
    else {
        event = document.createEvent("MouseEvents");
        event.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
    }
    aLink.dispatchEvent(event);
}



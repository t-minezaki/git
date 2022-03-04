var srcHeight = $(window).height() * 0.45;
var width = $(window).width() * 0.98;
var menuId = 'F21008';
var oldValues = [];
var newValues = [];
var addStuList = [];
var closeFlg;
var submitFlg = false;
/* 2020/12/14 V9.0 cuikailin add start */
var isChecked = [];
/* 2020/12/14 V9.0 cuikailin add end */
// 表示項目全集合
var wholeList = {
    'stuId': '生徒ID',
    'stuNm': '生徒名',
    'absSts': '内容',
    'absReason': '理由',
    'subjtDiv': '教科',
    'absStsDiv': '出欠',
    'homeWkDiv': '宿題',
    'testPoints': '小テスト',
    'careDiv': 'ケア'
    // add at 2021/08/11 for V9.02 by NWT wen START
    , 'testPassKbn': '小テスト合否'
    // add at 2021/08/11 for V9.02 by NWT wen END
};
var dist = {
    'stuId': {label: '生徒ID', name: 'afterUsrId', index: 'stuId', width: 30, sortable: false, align: "center"},
    'stuNm': {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 30, sortable: false, align: "center"},
    'absSts': {label: '内容', name: 'absSts', index: 'absSts', width: 30, sortable: false, align: "center"},
    'absReason': {
        label: '理由', name: 'absReason', index: 'absReason', width: 30, sortable: false, align: "center"
    },
    // 'lateTm': {label: '時間（分）', name: 'lateTm', index: 'lateTm', width: 30, sortable: false, align: "center"},
    'subjtDiv': {
        label: getButton(2, 1), name: 'subjtDiv', index: 'subjtDiv', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 1, object.stuId + ',' + object.schyDiv)

        }
    },
    'absStsDiv': {
        label: getButton(2, 2), name: 'absStsDiv', index: 'absStsDiv', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 2, object.stuId + ',' + object.schyDiv)
        }
    },
    'homeWkDiv': {
        label: getButton(2, 3), name: 'homeWkDiv', index: 'homeWkDiv', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 3, object.stuId + ',' + object.schyDiv)
        }
    },
    'testPoints': {
        label: getButton(2, 4), name: 'testPoints', index: 'testPoints', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 4, object.stuId + ',' + object.schyDiv)
        }
    },
    'careDiv': {
        label: getButton(2, 5), name: 'careDiv', index: 'careDiv', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 5, object.stuId + ',' + object.schyDiv)
        }
    },
    // add at 2021/08/11 for V9.02 by NWT wen START
    'testPassKbn': {
        label: getButton(2, 9), name: 'testPassKbn', index: 'testPassKbn', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 9, object.stuId + ',' + object.schyDiv)
        }
    }
    // add at 2021/08/11 for V9.02 by NWT wen END
}
var dspItems = [];
var list = [];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
//表示しなければならない項目
var disabledItems = [];

$(function () {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $("#stuCount").css("margin-left", "3vw")
                    $("#absDiv").css("line-height", "2.5vh")
                    $(".headButton").css("font-size", "9px").css("line-height", "2vh")
                } else if (/iPad/i.test(navigator.userAgent)) {
                }
            }
            catch (e) {
            }
        }
    } else {

    }
})

function getShowItems() {
    $.ajax({
        url: ctxPath + '/manager/F21008/getshowItems',
        type: 'GET',
        data: {},
        async: false,
        success: function (data) {
            hiddenList = data.displayItems.allItems.split(",");
            dspItems = data.displayItems.dspItems.split(",");
            disabledItems = data.displayItems.mustItems.split(",");
            for (var i = 0; i < dspItems.length; i++) {
                list.push(dist[dspItems[i]]);
            }
            return list;
        }
    });
}

//画面ボタンの取得
function getButton(buttonFlg, pageDiv, stuId, headName) {
    if (buttonFlg == 1) {
        return "<p class='head_P'>" + headName + "</p>" + "<div><button id='divChoose" + pageDiv + "'  class='headButton' schyDiv= '' onclick=to21006(this," + buttonFlg + ',' + pageDiv + ") value='' >まとめ入力</button></div>";
    } else if (buttonFlg == 2) {
        return "<button id='allSet" + pageDiv + "' class='allSet' onclick='setAll(" + pageDiv + ")' class = 'gridButton' style='background: #BFBFBF' disabled>ALL</button>";
    } else {
        return "<button id='singleSet" + pageDiv + "" + stuId.split(",")[0] + "' schyDiv='" + stuId.split(",")[1].trim() + "' class='singleSet" + pageDiv + "' value=''" + " onclick='to21006(this," + buttonFlg + ',' + pageDiv + ")'></button>";
    }
}

var param = getParam();
var params1 = param;

var vm = new Vue({
    el: '#main',
    data: {
        absDiv: [],
        addStu: [],
        selectData: '',
        codKey: '',
        codCd: '',
        score: '',
        tgtYmd: decodeURIComponent(param.tgtYmd),
        grpNm: param.grpNm==undefined?'':decodeURIComponent(param.grpNm),
        timesNum: param.timesNum == '' ? 0 : decodeURIComponent(param.timesNum),
        stuCount: '',
        endFlg: '',
        allItems: '',
        attendBookCd: '',
        pageDiv: param.pageDiv,
        style: {
            background: 'white',
            color: 'grey'
        },
        subjectName:'',
        jqGridList:[],
        allCountID:[]
    },
    mounted: function () {
        this.setUp();
        getShowItems();
        this.getInfo(null);
    },
    methods: {
        setUp: function(){
            setTargetControlHeight("div_body", "main", "one", "div_footer");
            srcHeight = getRemainingHeight("div_body", "div_head") - $(window).height() * 0.15;
        },
        getInfo: function (absDiv) {
            $.jgrid.gridUnload("jqGrid");
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F21008/init',
                datatype: "json",
                postData: {
                    tgtYmd: decodeURIComponent(param.tgtYmd),
                    grpId: param.grpId,
                    absDiv: absDiv,
                    attendBookCd: param.attendBookCd,
                    pageDiv: param.pageDiv,
                    attendBookId: param.id,
                },
                colModel: list,
                viewrecords: true,
                height: srcHeight,
                width: width,
                rowNum: 9999,
                rownumbers: false,
                rownumWidth: 25,
                autowidth: false,
                multiselect: true,
                multiboxonly: false,
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
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                },
                loadComplete: function (data) {
                    colList = $("#jqGrid").jqGrid('getGridParam', 'colModel');
                    for (var i = 0; i < colList.length; i++) {
                        for (var j = 0; j < hiddenList.length; j++) {
                            if (hiddenList[j] == colList[i].index) {
                                hiddenList.splice(j, 1);
                            }
                        }
                    }
                    vm.absDiv = data.mstCodDEntityList;
                    vm.absDiv.push({
                        codCd: 4,
                        codValue: 'チェック済'
                    });
                    if (data.code != 0) {
                        vm.stuCount = 0 ;
                        showMsg(data.msg)
                    } else {
                        vm.stuCount = data.page.list.length;
                        var rowIds = $("#jqGrid").jqGrid('getDataIDs');
                        if (param.pageDiv == 1) {
                            for (var i = 0; i < data.page.list.length; i++) {
                                //各プロジェクトの割り当て
                                $("#singleSet2" + data.page.list[i].stuId + "").text(data.page.list[i].absStsDiv);
                                $("#singleSet2" + data.page.list[i].stuId + "").val(data.page.list[i].absStsCod);
                                var curRowData = $("#jqGrid").jqGrid('getRowData', rowIds[i]);
                                var curChk = $("#"+rowIds[i]+"").find(":checkbox");
                                curChk.attr('name', 'checkboxname'); //给每一个checkbox赋名字
                                curChk.attr('value', data.page.list[i].stuId); //给checkbox赋值
                            }
                        }
                        if (param.pageDiv == 2) {
                            for (var i = 0; i < data.page.list.length; i++) {
                                //各プロジェクトの割り当て
                                $("#singleSet1" + data.page.list[i].stuId + "").text(data.page.list[i].subjtDiv);
                                $("#singleSet2" + data.page.list[i].stuId + "").text(data.page.list[i].absStsDiv);
                                $("#singleSet3" + data.page.list[i].stuId + "").text(data.page.list[i].homeWkDiv);
                                $("#singleSet4" + data.page.list[i].stuId + "").text(data.page.list[i].testPoints);
                                $("#singleSet5" + data.page.list[i].stuId + "").text(data.page.list[i].careDiv);
                                // add at 2021/08/11 for V9.02 by NWT wen START
                                $("#singleSet9" + data.page.list[i].stuId + "").text(data.page.list[i].testPassKbn);
                                // add at 2021/08/11 for V9.02 by NWT wen END
                                $("#singleSet1" + data.page.list[i].stuId + "").val(data.page.list[i].subjectCod);
                                $("#singleSet2" + data.page.list[i].stuId + "").val(data.page.list[i].absStsCod);
                                $("#singleSet3" + data.page.list[i].stuId + "").val(data.page.list[i].homeWkCod);
                                $("#singleSet5" + data.page.list[i].stuId + "").val(data.page.list[i].careCod);
                                $("#singleSet9" + data.page.list[i].stuId + "").val(data.page.list[i].testCod);
                                var curRowData = $("#jqGrid").jqGrid('getRowData', rowIds[i]);
                                var curChk = $("#"+rowIds[i]+"").find(":checkbox");
                                curChk.attr('name', 'checkboxname'); //给每一个checkbox赋名字
                                curChk.attr('value', data.page.list[i].stuId); //给checkbox赋值
                            }
                        }
                        if (absDiv === null){
                            vm.jqGridList = $("#jqGrid").jqGrid("getRowData");
                            vm.allCountID = $("#jqGrid").jqGrid('getDataIDs');
                            vm.jqGridList.push($("#jqGrid").jqGrid('getRowData', vm.allCountID[vm.allCountID.length - 1]));
                            var checkboxs = document.getElementsByName("checkboxname");
                            for (var i = 0; i < vm.jqGridList.length; i++) {
                                stuId = $(checkboxs[i]).val();
                                vm.jqGridList[i].stuId = stuId;
                                vm.jqGridList[i].subjtDiv = $("#singleSet1" + stuId + "").val();
                                vm.jqGridList[i].subjtName = $("#singleSet1" + stuId + "").text();
                                vm.jqGridList[i].absStsDiv = $("#singleSet2" + stuId + "").val();
                                vm.jqGridList[i].homeWkDiv = $("#singleSet3" + stuId + "").val();
                                vm.jqGridList[i].testPoints = $("#singleSet4" + stuId + "").text();
                                vm.jqGridList[i].careDiv = $("#singleSet5" + stuId + "").val();
                                // add at 2021/08/11 for V9.02 by NWT wen START
                                vm.jqGridList[i].testPassKbn = $("#singleSet9" + stuId + "").val();
                                // add at 2021/08/11 for V9.02 by NWT wen END
                            }
                        }
                    }
                    oldValues = [];
                    $("#gview_jqGrid").find("button").each(function () {
                        oldValues.push($(this).val());
                    })
                    if ($("#jqGrid").jqGrid('getDataIDs').length == 0){
                        $("#login").css("background", "grey").css("color", "white").attr("disabled", "disabled");
                    }else {
                        $("#login").css("background", "#00a65a").css("color", "white").removeAttr("disabled", "disabled");
                    }
                    /* 2020/12/14 V9.0 cuikailin add start */
                    var allCheckVal = $("input:checkbox[name='checkboxname']");
                    if(isChecked && allCheckVal){
                        for(let checkVal of isChecked){
                            for(let allVal of allCheckVal){
                                if($(checkVal)[0].value === $(allVal)[0].value){
                                    $($(allVal)[0].parentNode.parentNode).click();
                                }
                            }
                        }
                    }
                    /* 2020/12/14 V9.0 cuikailin add end */
                }
            })
            //レベル2を追加
            $("#jqGrid").jqGrid('setGroupHeaders', {
                useColSpanStyle: true,
                groupHeaders: [
                    {startColumnName: 'absSts', numberOfColumns: 2, titleText: '保護者からの連絡'},
                    {startColumnName: 'subjtDiv', numberOfColumns: 1, titleText: getButton(1, 1, null, '教科')},
                    {startColumnName: 'absStsDiv', numberOfColumns: 1, titleText: getButton(1, 2, null, '出欠')},
                    {startColumnName: 'homeWkDiv', numberOfColumns: 1, titleText: getButton(1, 3, null, '宿題')},
                    {startColumnName: 'testPoints', numberOfColumns: 1, titleText: getButton(1, 4, null, '小テスト')},
                    {startColumnName: 'careDiv', numberOfColumns: 1, titleText: getButton(1, 5, null, 'ケア')},
                    // add at 2021/08/11 for V9.02 by NWT wen START
                    {startColumnName: 'testPassKbn', numberOfColumns: 1, titleText: getButton(1, 9, null, '小テスト合否')}
                    // add at 2021/08/11 for V9.02 by NWT wen END
                ]
            });
        },
        //「登録」ボタン押下
        submit: function () {
            if (closeFlg){
                vm.jqGridList = $("#jqGrid").jqGrid("getRowData");
                vm.allCountID = $("#jqGrid").jqGrid('getDataIDs');
                vm.jqGridList.push($("#jqGrid").jqGrid('getRowData', vm.allCountID[vm.allCountID.length - 1]));
                var checkboxs = document.getElementsByName("checkboxname");
                for (var i = 0; i < vm.jqGridList.length; i++) {
                    stuId = $(checkboxs[i]).val();
                    vm.jqGridList[i].stuId = stuId;
                    vm.jqGridList[i].subjtDiv = $("#singleSet1" + stuId + "").val();
                    vm.jqGridList[i].subjtName = $("#singleSet1" + stuId + "").text();
                    vm.jqGridList[i].absStsDiv = $("#singleSet2" + stuId + "").val();
                    vm.jqGridList[i].homeWkDiv = $("#singleSet3" + stuId + "").val();
                    vm.jqGridList[i].testPoints = $("#singleSet4" + stuId + "").text();
                    vm.jqGridList[i].careDiv = $("#singleSet5" + stuId + "").val();
                    // add at 2021/08/11 for V9.02 by NWT wen START
                    vm.jqGridList[i].testPassKbn = $("#singleSet9" + stuId + "").val();
                    // add at 2021/08/11 for V9.02 by NWT wen END
                }
            }
            var jqGridList = $("#jqGrid").jqGrid("getRowData");
            var allCountID = $("#jqGrid").jqGrid('getDataIDs');
            jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
            var checkboxs = document.getElementsByName("checkboxname");
            var stuIds = [];
            for (var i = 0; i < jqGridList.length; i++) {
                stuId = $(checkboxs[i]).val();
                stuIds.push(stuId);
                jqGridList[i].stuId = stuId;
                jqGridList[i].subjtDiv = $("#singleSet1" + stuId + "").val();
                jqGridList[i].subjtName = $("#singleSet1" + stuId + "").text();
                jqGridList[i].absStsDiv = $("#singleSet2" + stuId + "").val();
                jqGridList[i].homeWkDiv = $("#singleSet3" + stuId + "").val();
                jqGridList[i].testPoints = $("#singleSet4" + stuId + "").text();
                jqGridList[i].careDiv = $("#singleSet5" + stuId + "").val();
                // add at 2021/08/11 for V9.02 by NWT wen START
                jqGridList[i].testPassKbn = $("#singleSet9" + stuId + "").val();
                // add at 2021/08/11 for V9.02 by NWT wen END
            }
            for (var i = 0; i < vm.jqGridList.length; i++) {
                if (stuIds.indexOf(vm.jqGridList[i].stuId) !== -1){
                    var index = stuIds.indexOf(vm.jqGridList[i].stuId);
                    vm.jqGridList[i] = jqGridList[index];
                }
            }
            var flg = false;
            if (jqGridList.length > 0) {
                for (var i = 0; i < jqGridList.length; i++) {
                    if (jqGridList[i].subjtDiv == null || jqGridList[i].subjtDiv == '') {
                        flg = true;
                        if (flg) {
                            var index = layer.confirm($.format($.msg.MSGCOMD0001, "教科"), {
                                title: '確認',
                                closeBtn: 0,
                                shadeClose: false,
                                btn: ['確認'],
                                btn1: function () {
                                    layer.close(index);
                                }
                            })
                            return;
                        }
                        return;
                    }
                }
                var index = layer.confirm($.format($.msg.MSGCOMN0011, "出席簿明細"), {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['キャンセル', '確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                    btn2: function () {
                        $.ajax(
                            {
                                url: ctxPath + '/manager/F21008/submit',
                                data: {
                                    jqGridList: JSON.stringify(jqGridList),
                                    tgtYmd: decodeURIComponent(param.tgtYmd),
                                    grpId: param.grpId,
                                    pageDiv: param.pageDiv,
                                    attendBookId: param.id,
                                    addStuList:JSON.stringify(addStuList)
                                },
                                type: 'POST',
                                success: function (data) {
                                    if (data.code === 0) {
                                        if (param.pageDiv == 1) {
                                            var num = 1;
                                            window.location.href = window.location.href.replace("pageDiv=1", "pageDiv=2")
                                                .replace("id=", "id=" + data.attendBookId)
                                                .replace("attendBookCd=", "attendBookCd=" + data.attendBookCd)
                                                .replace("timesNum=","timesNum=  " + num);
                                        } else {
                                            location.reload();
                                        }
                                    } else {
                                        showMsg(data.msg);
                                    }
                                }
                            }
                        )
                    }
                })
            }
        },
        hideRow: function (rowId){
            $("#jqGrid").setRowData(rowId,null,{display: 'none'});
        },
        showRow: function (rowId){
            $("#jqGrid").setRowData(rowId,null,{display: ''});
        },
        switchSts: function (type, absState){
            let ids = $("#jqGrid").jqGrid('getDataIDs');
            if (type === 'state'){
                for(let i = 0; i < ids.length; i ++){
                    let rowData = $('#jqGrid').jqGrid('getRowData', ids[i]);
                    if (rowData.absStsDiv && $(rowData.absStsDiv)) {
                        if (absState.indexOf($(rowData.absStsDiv).val()) >= 0) {
                            vm.showRow(ids[i]);
                            continue;
                        }
                    }
                    vm.hideRow(ids[i]);
                }
            }else if (type === 'select'){
                let selectedIds = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
                for(let i = 0; i < ids.length; i ++){
                    if (selectedIds.indexOf(ids[i]) >= 0) {
                        vm.showRow(ids[i]);
                        continue;
                    }
                    vm.hideRow(ids[i]);
                }
            }
        }
    }
});

var absDivList = [];
//「出欠フラグ」リスト選択
$("#absDiv").change(function () {
    var flg = '2';
    newValues = [];
    var absDiv = $("#absDiv option:selected").val();
    absDivList.push(absDiv);
    // 2020/11/17 wyh modify start
    // 選択されていないアイテムを取得する
    let notChecked = $("input:checkbox[name='checkboxname']").not("input:checked");

    // [チェック済]以外に切り替えると、未選択の項目が表示されます
    for (let inputDom of notChecked) {
        $(inputDom).parentsUntil('tbody').show();
    }
    // 2020/11/17 wyh modify end
    /* 2020/12/14 V9.0 cuikailin add start */
    isChecked = $("input:checkbox[name='checkboxname']:checked");
    /* 2020/12/14 V9.0 cuikailin add end */

    $("#gview_jqGrid").find("button").each(function () {
        newValues.push($(this).val());
    });
    for (var i = 0; i < oldValues.length; i++) {
        if (oldValues[i] != newValues[i]) {
            flg = '1';
        }
    }

    var type = 'state';
    var showStateList = [];
    if (absDiv === "0"){
        showStateList = [' ', '', null, '0', '1', '2', '3', '4'];
    }else if(absDiv === "1"){
        showStateList = ['1', '2'];
    }else if(absDiv === "2"){
        showStateList = ['3', '4'];
    }else if(absDiv === "3"){
        showStateList = ['0']
    }else if(absDiv === "4"){
        type = 'select';
    }
    vm.switchSts(type, showStateList);
});


//対象者追加
function toStuChoose() {
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
        content: ["../pop/F09003.html"],
        success: function (layero, index) {
        },
        end: function () {
            var gridStuList = [];
            $("input[name='checkboxname']").each(function () {
                gridStuList.push($(this).val());
            });
            stuList = stuList.reduce(function (carry, item) {
                //  check if the item is actually an object and does contain the field
                if (gridStuList.indexOf(item) === -1) {
                    carry.push(item);
                }
                //  return the 'carry' (which is the list of matched field values)
                return carry;
            }, []);
            addStuList = stuList;
            if (closeFlg) {
                if (stuList.length > 0) {
                    $.ajax({
                        url: ctxPath + '/manager/F21008/stuSelect',
                        type: 'GET',
                        data: {
                            tgtYmd: decodeURIComponent(param.tgtYmd),
                            stuIdListStr: JSON.stringify(stuList)
                        },
                        datatype: 'json',
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                            } else {
                                vm.addStu = data.addStu;
                                vm.addStu.forEach(function (v) {
                                    $("#jqGrid").jqGrid('addRowData', v.stuId, v, 'last');
                                });
                                $("#login").css("background", "#00a65a").css("color", "white").removeAttr("disabled", "disabled");
                                for (var i = 0; i < data.addStu.length; i++) {
                                    //各プロジェクトの割り当て
                                    $("#singleSet2" + data.addStu[i].stuId + "").text(data.addStu[i].absStsDiv);
                                    $("#singleSet2" + data.addStu[i].stuId + "").val(data.addStu[i].absStsCod);
                                    var curChk = $("#jqg_jqGrid_" + data.addStu[i].stuId + "");
                                    curChk.attr('name', 'checkboxname');
                                    curChk.attr('value', data.addStu[i].stuId);
                                }
                                var jqGridList = $("#jqGrid").jqGrid("getRowData");
                                var allCountID = $("#jqGrid").jqGrid('getDataIDs');
                                jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
                                var count = '対象者：' + jqGridList.length + '名';
                                $("#stuCount").text(count);
                                vm.addStu = [];
                            }
                        }
                    });
                }

            }
        }
    })
}

//対象者削除
function stuDelete() {
    var selectedItems = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
    if (selectedItems.length == 0) {
        var index = layer.confirm($.format($.msg.MSGCOMN0028, "削除対象者"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        })
    } else {
        var rows = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
        var allCountID = $("#jqGrid").jqGrid('getDataIDs');
        var flg = false;
        if (rows.length == allCountID.length) {
            flg = true;
        }
        var index = layer.confirm($.format($.msg.MSGCOMN0013, "選択した対象者"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                layer.close(index);
            },
            btn2: function () {
                var rowid = $('#jqGrid').jqGrid('getGridParam', 'selarrrow');
                var stuIdlist = [];
                for (var i = 0; i < rowid.length; i++) {
                    stuIdlist.push($("#"+rowid[i]+"").find(":checkbox").val());
                }

                if (vm.pageDiv == 2) {
                    $.ajax({
                        url: ctxPath + '/manager/F21008/stuDel',
                        type: 'POST',
                        data: {
                            flg: flg,
                            attendBookId: param.id,
                            stuIdListStr: JSON.stringify(stuIdlist)
                        },
                        success: function () {
                            if (flg){
                                window.location.reload();
                            }
                        }
                    })
                }
                var len = rowid.length;
                for (var i = 0; i < len; i++) {
                    $("#jqGrid").jqGrid('delRowData', rowid[0]);
                }
                if ($("#jqGrid").jqGrid('getDataIDs').length == 0){
                    $("#login").css("background", "grey").css("color", "white").attr("disabled", "disabled");
                }
                var jqGridList = $("#jqGrid").jqGrid("getRowData");
                var allCountID = $("#jqGrid").jqGrid('getDataIDs');
                jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
                var count = '対象者：' + allCountID.length + '名';
                $("#stuCount").text(count);
            }
        })
    }
}

//「まとめ入力」と「各明細行」テキストボックスクリック
function to21006(obj, buttonFlg, pageDiv) {
    var srcWidth = "500px";
    var srcHeight = "350px";
    window.event.stopPropagation();
    var headValue =  $("#divChoose" + pageDiv + "").val();
    var headText =  $("#divChoose" + pageDiv + "").text();
    if (buttonFlg != 1){
        if (headValue != '' && headValue != null){
            if (headValue ==='m1' && (4 <= (Number)($(obj).attr("schyDiv")) && (Number)($(obj).attr("schyDiv")) <= 9)) {
                $(obj).text('算数');
                $(obj).val('m2');
            }else if (headValue ==='m1' && (4 > (Number)($(obj).attr("schyDiv")) || (Number)($(obj).attr("schyDiv")) > 9)) {
                $(obj).text('数学');
                $(obj).val('m1');
            }else {
                $(obj).text(headText);
                $(obj).val(headValue);
            }
        }else {
            layer.open({
                id: 'F21006',
                type: 2,
                shade: 0.1,
                closeBtn: 1,
                shadeClose: false,
                title: ['', 'display:none;'],
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                content: ["../pop/F21006.html?grpId=" + param.grpId + '&pageDiv=' + pageDiv],
                success: function (layero, index) {
                    if (pageDiv == 1) {
                        vm.codKey = 'SUBJT_DIV';
                    } else if (pageDiv == 2) {
                        vm.codKey = 'ABS_STS_DIV';
                    } else if (pageDiv == 3) {
                        vm.codKey = 'HOME_WK_DIV';
                    // modify at 2021/08/11 for V9.02 by NWT wen START
                    } else if (pageDiv == 5) {
                        vm.codKey = 'CARE_DIV';
                    } else if (pageDiv == 9) {
                        vm.codKey = 'TEST_PASS_KBN';
                    }
                    // modify at 2021/08/11 for V9.02 by NWT wen END
                },
                end: function () {
                    if (vm.endFlg) {
                        if (submitFlg){
                            $(obj).text(vm.subjectName);
                            $(obj).val(vm.codCd);
                            if (vm.codCd !== ''){
                                $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                            }else {
                                $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                            }
                        } else {
                            $.ajax({
                                url: ctxPath + '/manager/F21008/getSelectedItems',
                                type: 'GET',
                                data: {
                                    selectData: vm.codCd,
                                    codKey: vm.codKey
                                },
                                success: function (data) {
                                    if (pageDiv != 4) {
                                        if (data.mstCodDEntity) {
                                            if (data.mstCodDEntity.codCd === 'm1' && $(obj).attr("schyDiv") === ''){
                                                $(obj).text("数学/算数");
                                                $(obj).val(data.mstCodDEntity.codCd);
                                            }else if (data.mstCodDEntity.codCd === 'm1' && (4 <= (Number)($(obj).attr("schyDiv")) && (Number)($(obj).attr("schyDiv")) <= 9)){
                                                $(obj).text("算数");
                                                $(obj).val("m2");
                                            } else if (data.mstCodDEntity.codCd === 'm1' && (4 > (Number)($(obj).attr("schyDiv")) || (Number)($(obj).attr("schyDiv")) > 9)) {
                                                $(obj).text("数学");
                                                $(obj).val("m1");
                                            }
                                            else {
                                                $(obj).text(data.mstCodDEntity.codValue);
                                                $(obj).val(data.mstCodDEntity.codCd);
                                            }
                                        } else {
                                            if ($(obj).hasClass("headButton")) {
                                                $(obj).text("まとめ入力");
                                                $(obj).val('');
                                            } else {
                                                $(obj).text("");
                                                $(obj).val('');
                                            }
                                            $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                                        }
                                    } else {
                                        if (vm.score > 0) {
                                            $(obj).text(vm.score);
                                            $(obj).val(vm.score);
                                        } else {
                                            $(obj).text("");
                                            $(obj).val('');
                                            $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                                        }
                                    }
                                    if ($("#divChoose" + pageDiv + "").val() != '') {
                                        $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                                    }
                                }
                            });
                        }
                        vm.endFlg = false;
                        submitFlg = false;
                    }
                }
            })
        }
    }else {
        layer.open({
            id: 'F21006',
            type: 2,
            shade: 0.1,
            closeBtn: 1,
            shadeClose: false,
            title: ['', 'display:none;'],
            area: [srcWidth, srcHeight],
            fixed: true,
            resize: false,
            content: ["../pop/F21006.html?grpId=" + param.grpId + '&pageDiv=' + pageDiv],
            success: function (layero, index) {
                if (pageDiv == 1) {
                    vm.codKey = 'SUBJT_DIV';
                } else if (pageDiv == 2) {
                    vm.codKey = 'ABS_STS_DIV';
                } else if (pageDiv == 3) {
                    vm.codKey = 'HOME_WK_DIV';
                // modify at 2021/08/11 for V9.02 by NWT wen START
                } else if (pageDiv == 5) {
                    vm.codKey = 'CARE_DIV';
                } else if (pageDiv == 9) {
                    vm.codKey = 'TEST_PASS_KBN';
                }
                // modify at 2021/08/11 for V9.02 by NWT wen END
            },
            end: function () {
                if (vm.endFlg) {
                    if (submitFlg){
                        $(obj).text(vm.subjectName == ''?'まとめ入力':vm.subjectName);
                        $(obj).val(vm.codCd);
                        if (vm.codCd !== ''){
                            $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                        }else {
                            $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                        }
                    } else {
                        $.ajax({
                            url: ctxPath + '/manager/F21008/getSelectedItems',
                            type: 'GET',
                            data: {
                                selectData: vm.codCd,
                                codKey: vm.codKey
                            },
                            success: function (data) {
                                if (pageDiv != 4) {
                                    if (data.mstCodDEntity) {
                                        if (data.mstCodDEntity.codCd === 'm1' && $(obj).attr("schyDiv") === ''){
                                            $(obj).text("数学/算数");
                                            $(obj).val(data.mstCodDEntity.codCd);
                                        }else if (data.mstCodDEntity.codCd === 'm1' && (4 <= (Number)($(obj).attr("schyDiv")) && (Number)($(obj).attr("schyDiv")) <= 9)){
                                            $(obj).text("算数");
                                            $(obj).val("m2");
                                        } else if (data.mstCodDEntity.codCd === 'm1' && (4 > (Number)($(obj).attr("schyDiv")) || (Number)($(obj).attr("schyDiv")) > 9)) {
                                            $(obj).text("数学");
                                            $(obj).val("m1");
                                        }
                                        else {
                                            $(obj).text(data.mstCodDEntity.codValue);
                                            $(obj).val(data.mstCodDEntity.codCd);
                                        }
                                    } else {
                                        if ($(obj).hasClass("headButton")) {
                                            $(obj).text("まとめ入力");
                                            $(obj).val('');
                                        } else {
                                            $(obj).text("");
                                            $(obj).val('');
                                        }
                                        $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                                    }
                                } else {
                                    if (vm.score > 0) {
                                        $(obj).text(vm.score);
                                        $(obj).val(vm.score);
                                    } else {
                                        $(obj).text("");
                                        $(obj).val('');
                                        $("#allSet" + pageDiv + "").attr("disabled", "disabled").css("background", "#BFBFBF").css("color", "black");
                                    }
                                }
                                if ($("#divChoose" + pageDiv + "").val() != '') {
                                    $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                                }
                            }
                        })
                    }
                    vm.endFlg = false;
                    submitFlg = false;
                }
            }
        })
    }
}

//「ALL」ボタン押下
function setAll(pageDiv) {
    var msgText = '';
    if (pageDiv == 1) {
        msgText = '教科';
    } else if (pageDiv == 2) {
        msgText = '出欠';
    } else if (pageDiv == 3) {
        msgText = '宿題';
    } else if (pageDiv == 4) {
        msgText = '小テスト';
    // modify at 2021/08/11 for V9.02 by NWT wen START
    } else if (pageDiv == 5) {
        msgText = 'ケア';
    } else if (pageDiv == 9) {
        msgText = '小テスト合否';
    }
    // modify at 2021/08/11 for V9.02 by NWT wen END
    var msg1 = $.format($.msg.MSGCOMN0129, msgText);
    for (var i = 0; i < $(".singleSet" + pageDiv + "").length; i++) {
        if ($("#divChoose" + pageDiv + "").val() === 'm1' && (4 <= (Number)($($(".singleSet" + pageDiv + "")[i]).attr("schyDiv")) && (Number)($($(".singleSet" + pageDiv + "")[i]).attr("schyDiv")) <= 9)){
            $($(".singleSet" + pageDiv + "")[i]).text('算数');
            $($(".singleSet" + pageDiv + "")[i]).val('m2');
        } else if ($("#divChoose" + pageDiv + "").val() === 'm1' && (4 > (Number)($($(".singleSet" + pageDiv + "")[i]).attr("schyDiv"))|| (Number)($($(".singleSet" + pageDiv + "")[i]).attr("schyDiv")) > 9)){
            $($(".singleSet" + pageDiv + "")[i]).text('数学');
            $($(".singleSet" + pageDiv + "")[i]).val('m1');
        } else {
            $($(".singleSet" + pageDiv + "")[i]).text($("#divChoose" + pageDiv + "").text());
            $($(".singleSet" + pageDiv + "")[i]).val($("#divChoose" + pageDiv + "").val());
        }
    }
}

function reloadControl(btnFlg) {
    params1.grpNm = decodeURIComponent(param.grpNm);
    params1.tgtYmd = decodeURIComponent(param.tgtYmd);
    params1.timesNum = decodeURIComponent(param.timesNum);
    var flg = '2';
    newValues = [];
    $("#gview_jqGrid").find("button").each(function () {
        newValues.push($(this).val());
    });
    for (var i = 0; i < oldValues.length; i++) {
        if (oldValues[i] != newValues[i]) {
            flg = '1';
        }
    }
    if (flg === '1') {
        var index = layer.confirm($.format($.msg.MSGCOMN0128, "選択した対象者"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['保存せず進む', '戻る'],
            btn2: function () {
                layer.close(index);
            },
            btn1: function () {
                layer.close(index);
                if (btnFlg == 1) {
                    window.location.href = 'F21007.html'
                } else if (btnFlg == 2) {
                    window.location.href = 'F21009.html?' + $.param(params1);
                } else {
                    var srcWidth = $(window).width() * 0.7 + "px";
                    var srcHeight = $(window).height() * 0.7 + "px";
                    layer.open({
                        id: 'F21008-2',
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
                        content: ["../pop/F21008-2.html", 'no'],
                        success: function (layero, index) {
                            var body = layer.getChildFrame('body', index);
                            var form_left = body.find('#form_left');
                            for (var j = 0; j < hiddenList.length; j++) {
                                var str = "<div class='item_div'><input type='checkbox' value='" + hiddenList[j] + "' content='" + wholeList[hiddenList[j]] + "'/><span class='cont'>" + wholeList[hiddenList[j]] + "</span></div>";
                                $(form_left).append(str);
                            }
                            var form_right = body.find('#form_right');
                            for (var j = 1; j < colList.length; j++) {
                                var str = "<div class='item_div'><input type='checkbox' value='" + colList[j].index + "' content='" + wholeList[colList[j].index] + "'/><span class='cont'>" + wholeList[colList[j].index] + "</span></div>";
                                $(form_right).append(str);
                            }
                        },
                        end:function () {
                            window.location.reload();
                        }
                    });
                }
            }
        })
    } else {
        newValues = [];
        if (btnFlg == 1) {
            window.location.href = 'F21007.html';
        } else if (btnFlg == 2) {
            window.location.href = 'F21009.html?' + $.param(params1);
        } else {
            var srcWidth = $(window).width() * 0.7 + "px";
            var srcHeight = $(window).height() * 0.7 + "px";
            layer.open({
                id: 'F21008-2',
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
                content: ["../pop/F21008-2.html", 'no'],
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    var form_left = body.find('#form_left');
                    for (var j = 0; j < hiddenList.length; j++) {
                        var str = "<div class='item_div'><input type='checkbox' value='" + hiddenList[j] + "' content='" + wholeList[hiddenList[j]] + "'/><span class='cont'>" + wholeList[hiddenList[j]] + "</span></div>";
                        $(form_left).append(str);
                    }
                    var form_right = body.find('#form_right');
                    for (var j = 1; j < colList.length; j++) {
                        var str = "<div class='item_div'><input type='checkbox' value='" + colList[j].index + "' content='" + wholeList[colList[j].index] + "'/><span class='cont'>" + wholeList[colList[j].index] + "</span></div>";
                        $(form_right).append(str);
                    }
                },
                /* 2020/12/23 UT-045 cuikailin add start */
                end:function () {
                    window.location.reload();
                }
                /* 2020/12/23 UT-045 cuikailin add end */
            });
        }
    }
}





var srcHeight = $(window).height() * 0.5;
var width = $(window).width() * 0.98;
var oldValues = [];
var newValues = [];
var menuId = 'F21009';
var param = getParam();

//画面ボタンの取得
function getButton(buttonFlg, pageDiv, stuId, headName) {
    var flg = 'unset';
    var color = 'none'
    if (pageDiv == 2){
        flg = 'none';
        color = '#BFBFBF';
    }
    if (buttonFlg == 1) {
        return "<p class='head_P'>" + headName + "</p>" + "<div><button style='pointer-events:"+ flg +";background:" + color + " ' id='divChoose" + pageDiv + "' class='headButton' onclick=to21006(this," + buttonFlg + ',' + pageDiv + ") value='' >まとめ入力</button></div>";
    } else if (buttonFlg == 2) {
        return "<button id='allSet" + pageDiv + "' class='allSet' onclick='setAll(" + pageDiv + ")' class = 'gridButton' style='background: #BFBFBF' disabled>ALL</button>";
    } else {
        return "<button style='pointer-events:"+ flg +";background:" + color + " 'id='singleSet" + pageDiv + "" + stuId + "' class='singleSet" + pageDiv + "' value='' onclick='to21006(this," + buttonFlg + ',' + pageDiv + ")'></button>";
    }
}

var dist = {
    'stuId': {label: '生徒ID', name: 'afterUsrId', index: 'stuId', width: 30, sortable: false, align: "center"},
    'stuNm': {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 30, sortable: false, align: "center"},
    'absStsDiv': {
        label: getButton(2, 2), name: 'absStsDiv', index: 'absStsDiv', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 2, object.stuId)
        }
    },
    'useCont': {
        label: getButton(2, 9), name: 'useCont', index: 'useCont', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 9, object.stuId)
        }
    },
    'guidCont': {
        label: getButton(2, 10), name: 'guidCont', index: 'guidCont', width: 30, sortable: false, align: "center",
        formatter(cell, option, object) {
            return getButton(3, 10, object.stuId)
        }
    },
    'hwkCont': {
        label: getButton(2, 11), name: 'hwkCont', index: 'hwkCont', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 11, object.stuId)
        }
    },
    'testUnitNm': {
        label: getButton(2, 12), name: 'testUnitNm', index: 'testUnitNm', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 12, object.stuId)
        }
    },
    'lastTimeHwkDiv': {
        label: getButton(2, 6), name: 'lastTimeHwkDiv', index: 'lastTimeHwkDiv', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 6, object.stuId)
        }
    },
    'schStsDiv': {
        label: getButton(2, 7), name: 'schStsDiv', index: 'schStsDiv', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 7, object.stuId)
        }
    },
    'lectShapeDiv': {
        label: getButton(2, 8), name: 'lectShapeDiv', index: 'lectShapeDiv', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 8, object.stuId)
        }
    },
    'concItemCont': {
        label: getButton(2, 13), name: 'concItemCont', index: 'concItemCont', width: 30, sortable: false, align: "center", formatter(cell, option, object) {
            return getButton(3, 13, object.stuId)
        }
    }
};
// 表示項目全集合
var wholeList = {
    'stuId': '生徒ID',
    'stuNm': '生徒名',
    'absStsDiv': '出欠',
    'useCont': '使用テキスト',
    'guidCont': '本日の授業内容',
    'hwkCont': '本日の宿題内容',
    'testUnitNm': '小テスト単元名',
    'lastTimeHwkDiv': '前回の宿題',
    'schStsDiv': '進捗の状況',
    'lectShapeDiv': '授業の様子',
    'concItemCont': '連絡事項'
};
var dspItems = [];
var list = [];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
//表示しなければならない項目
var disabledItems = [];

function getShowItems() {
    $.ajax({
        url: ctxPath + '/manager/F21009/getshowItems',
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

var vm = new Vue({
    el: '#main',
    data: {
        absDiv: [],
        pageDiff: '',
        id: '',
        allStuIdList:[]
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
                url: ctxPath + '/manager/F21009/init',
                datatype: "json",
                postData: {
                    absDiv: absDiv,
                    attendBookCd: param.attendBookCd
                },
                colModel: list,
                viewrecords: true,
                height: srcHeight,
                width: width,
                rowNum: 30,
                rowList: [10, 30, 50],
                rownumbers: false,
                rownumWidth: 25,
                autowidth: false,
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
                }
                ,
                loadComplete: function (data) {
                    colList = $("#jqGrid").jqGrid('getGridParam', 'colModel');
                    for (var i = 0; i < colList.length; i++) {
                        for (var j = 0; j < hiddenList.length; j++) {
                            if (hiddenList[j] == colList[i].index) {
                                hiddenList.splice(j, 1);
                            }
                        }
                    }
                    for (var i = 0; i < colList.length; i++) {
                        if (colList[i].name == 'cb') {
                            colList.splice(i, 1);
                        }
                    }
                    vm.absDiv = data.mstCodDEntityList;
                    if (data.code != 0) {
                        vm.stuCount = 0
                        showMsg(data.msg)
                    } else {
                        vm.id = data.page.list[0].id;
                        vm.pageDiff = data.pageDiff;
                        var rows = $('td[aria-describedby=jqGrid_afterUsrId]');
                        for (var i = 0; i < data.page.list.length; i++) {
                            //各プロジェクトの割り当て
                            $(rows[i]).attr('id',data.page.list[i].stuId).addClass("getStuId");
                            $("#singleSet2" + data.page.list[i].stuId + "").text(data.page.list[i].absStsDiv).val(data.page.list[i].absStsCod);
                            $("#singleSet6" + data.page.list[i].stuId + "").text(data.page.list[i].lastTimeHwkDiv).val(data.page.list[i].lastTimeHwkCod);
                            $("#singleSet7" + data.page.list[i].stuId + "").text(data.page.list[i].schStsDiv).val(data.page.list[i].schStsCod);
                            $("#singleSet8" + data.page.list[i].stuId + "").text(data.page.list[i].lectShapeDiv).val(data.page.list[i].lectShapeCod);
                            $("#singleSet9" + data.page.list[i].stuId + "").text((data.page.list[i].useCont == '' || data.page.list[i].useCont == null) ? data.page.list[i].useCont : '入力済').val(data.page.list[i].useCont);
                            $("#singleSet10" + data.page.list[i].stuId + "").text((data.page.list[i].guidCont == '' || data.page.list[i].guidCont == null) ? data.page.list[i].guidCont : '入力済').val(data.page.list[i].guidCont);
                            $("#singleSet11" + data.page.list[i].stuId + "").text((data.page.list[i].hwkCont == '' || data.page.list[i].hwkCont == null) ? data.page.list[i].hwkCont : '入力済').val(data.page.list[i].hwkCont);
                            $("#singleSet12" + data.page.list[i].stuId + "").text((data.page.list[i].testUnitNm == '' || data.page.list[i].testUnitNm == null) ? data.page.list[i].testUnitNm : '入力済').val(data.page.list[i].testUnitNm);
                            $("#singleSet13" + data.page.list[i].stuId + "").text((data.page.list[i].concItemCont == '' || data.page.list[i].concItemCont == null) ? data.page.list[i].concItemCont : '入力済').val(data.page.list[i].concItemCont);
                        }
                    }
                    oldValues = [];
                    $("#gview_jqGrid").find("button").each(function () {
                        oldValues.push($(this).val());
                    });
                    if (absDiv === null){
                        vm.jqGridList = $("#jqGrid").jqGrid("getRowData");
                        vm.allCountID = $("#jqGrid").jqGrid('getDataIDs');
                        vm.jqGridList.push($("#jqGrid").jqGrid('getRowData', vm.allCountID[vm.allCountID.length - 1]));
                        var rows = $('td[aria-describedby=jqGrid_afterUsrId]');
                        for (var i = 0; i < vm.jqGridList.length; i++) {
                            stuId = $(rows[i]).attr('id');
                            vm.allStuIdList.push(stuId);
                            vm.jqGridList[i].stuId = stuId;
                            vm.jqGridList[i].absStsDiv = $("#singleSet2" + stuId + "").val();
                            vm.jqGridList[i].lastTimeHwkDiv = $("#singleSet6" + stuId + "").val();
                            vm.jqGridList[i].schStsDiv = $("#singleSet7" + stuId + "").val();
                            vm.jqGridList[i].lectShapeDiv = $("#singleSet8" + stuId + "").val();
                            vm.jqGridList[i].useCont = $("#singleSet9" + stuId + "").val();
                            vm.jqGridList[i].guidCont = $("#singleSet10" + stuId + "").val();
                            vm.jqGridList[i].hwkCont = $("#singleSet11" + stuId + "").val();
                            vm.jqGridList[i].testUnitNm = $("#singleSet12" + stuId + "").val();
                            vm.jqGridList[i].concItemCont = $("#singleSet13" + stuId + "").val();
                        }
                    }
                }
            }),
                // //レベル2を追加
                $("#jqGrid").jqGrid('setGroupHeaders', {
                    useColSpanStyle: true,
                    groupHeaders: [
                        {startColumnName: 'absStsDiv', numberOfColumns: 1, titleText: getButton(1, 2, null, '出欠')},
                        {startColumnName: 'useCont', numberOfColumns: 1, titleText: getButton(1, 9, null, '使用テキスト')},
                        {startColumnName: 'guidCont', numberOfColumns: 1, titleText: getButton(1, 10, null, '本日の授業内容')},
                        {startColumnName: 'hwkCont', numberOfColumns: 1, titleText: getButton(1, 11, null, '本日の宿題内容')},
                        {startColumnName: 'testUnitNm', numberOfColumns: 1, titleText: getButton(1, 12, null, '小テスト単元名')},
                        {startColumnName: 'lastTimeHwkDiv', numberOfColumns: 1, titleText: getButton(1, 6, null, '前回の宿題')},
                        {startColumnName: 'schStsDiv', numberOfColumns: 1, titleText: getButton(1, 7, null, '進捗の状況')},
                        {startColumnName: 'lectShapeDiv', numberOfColumns: 1, titleText: getButton(1, 8, null, '授業の様子')},
                        {startColumnName: 'concItemCont', numberOfColumns: 1, titleText: getButton(1, 13, null, '連絡事項')},
                    ]
                })
        },
        submit: function () {
            var jqGridList = $("#jqGrid").jqGrid("getRowData");
            var allCountID = $("#jqGrid").jqGrid('getDataIDs');
            jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
            var stuIds = $(".getStuId");
            var stuIdList = [];
            for (var i = 0; i < jqGridList.length; i++) {
                var stuId = $(stuIds[i]).attr('id');
                jqGridList[i].stuId = stuId;
                stuIdList.push(stuId);
                jqGridList[i].absStsDiv = $("#singleSet2" + stuId + "").val();
                jqGridList[i].lastTimeHwkDiv = $("#singleSet6" + stuId + "").val();
                jqGridList[i].schStsDiv = $("#singleSet7" + stuId + "").val();
                jqGridList[i].lectShapeDiv = $("#singleSet8" + stuId + "").val();
                jqGridList[i].useCont = $("#singleSet9" + stuId + "").val();
                jqGridList[i].guidCont = $("#singleSet10" + stuId + "").val();
                jqGridList[i].hwkCont = $("#singleSet11" + stuId + "").val();
                jqGridList[i].testUnitNm = $("#singleSet12" + stuId + "").val();
                jqGridList[i].concItemCont = $("#singleSet13" + stuId + "").val();
            }
            for (var i = 0; i < jqGridList.length; i++) {
                var index = vm.allStuIdList.indexOf(stuIdList[i]);
                vm.jqGridList[index] = jqGridList[i];
            }
            var index = layer.confirm($.format($.msg.MSGCOMN0011, "指導報告書"), {
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
                            url: ctxPath + '/manager/F21009/submit',
                            data: {
                                id: vm.id,
                                jqGridList: JSON.stringify(vm.jqGridList),
                                pageDiff: vm.pageDiff,
                                attendBookCd: param.attendBookCd
                            },
                            type: 'POST',
                            success: function (data) {
                                if (data.code === 0) {
                                    // layer.confirm($.format($.msg.MSGCOMN0014, "指導報告書情報"), {
                                    //     title: '確認',
                                    //     closeBtn: 0,
                                    //     shadeClose: false,
                                    //     btn: ['確認'],
                                    //     btn1: function () {
                                            location.reload();
                                    //     }
                                    // })
                                } else {
                                    showMsg(data.msg);
                                }
                            }
                        }
                    )
                }
            })
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
//「出欠フラグ」リスト選択
$("#absDiv").change(function () {
    var flg = '2';
    newValues = [];
    var absDiv = $("#absDiv option:selected").val();
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
    }
    vm.switchSts(type, showStateList);
})
$(function () {

})

function reloadControl(btnFlg) {
    newValues = [];
    var params1 = param;
    params1.grpNm = decodeURIComponent(param.grpNm);
    params1.tgtYmd = decodeURIComponent(param.tgtYmd);
    params1.timesNum = decodeURIComponent(param.timesNum);
    var flg = '2';
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
                    window.location.href = 'F21008.html?' + $.param(params1);
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
                            for (var j = 0; j < colList.length; j++) {
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
            window.location.href = 'F21008.html?' + $.param(params1);
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
                    for (var j = 0; j < colList.length; j++) {
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
}

//「まとめ入力」と「各明細行」テキストボックスクリック
function to21006(obj, buttonFlg, pageDiv) {
    var headValue =  $("#divChoose" + pageDiv + "").val();
    var headText =  $("#divChoose" + pageDiv + "").text();
    if (buttonFlg != 1) {
        if (headValue != '' && headValue != null){
            $(obj).text(headText);
            $(obj).val(headValue);
        }else {
            if (pageDiv <= 8) {
                layer.open({
                    id: 'F21006',
                    type: 2,
                    title: ['', 'display:none;'],
                    shade: 0.1,
                    closeBtn: 1,
                    shadeClose: false,
                    area: ['45%', '50%'],
                    fixed: true,
                    resize: false,
                    content: ["../pop/F21006.html?grpId=" + param.grpId + '&pageDiv=' + pageDiv + '&codCd=' + $(obj).val()],
                    success: function (layero, index) {
                        if (pageDiv == 6) {
                            vm.codKey = 'LAST_TIME_HWK_DIV';
                        } else if (pageDiv == 2) {
                            vm.codKey = 'ABS_STS_DIV';
                        } else if (pageDiv == 7) {
                            vm.codKey = 'SCH_STS_DIV';
                        } else if (pageDiv == 8) {
                            vm.codKey = 'LECT_SHAPE_DIV';
                        }
                    },
                    end: function () {
                        if (vm.endFlg) {
                            $.ajax({
                                url: ctxPath + '/manager/F21008/getSelectedItems',
                                type: 'GET',
                                data: {
                                    selectData: pageDiv == 8 ? JSON.stringify(vm.codCd) : vm.codCd,
                                    codKey: vm.codKey,
                                    pageDiv: pageDiv
                                },
                                success: function (data) {
                                    if (data.mstCodDEntity) {
                                        if (pageDiv == 2) {
                                            $(obj).text(data.mstCodDEntity.codValue).val(data.mstCodDEntity.codValue);
                                        } else {
                                            $(obj).text(data.mstCodDEntity.codValue2).val(data.mstCodDEntity.codCd);
                                        }
                                        $(obj).val(data.mstCodDEntity.codCd);
                                        vm.codKey = '';
                                    }else {
                                        if ($(obj).hasClass("headButton")){
                                            $(obj).text('まとめ入力').val('');
                                        } else {
                                            $(obj).text('').val('');
                                        }
                                        $("#allSet" + pageDiv + "").attr("disabled","disabled").css("background", "#BFBFBF").css("color", "black");
                                    }
                                    if ($("#divChoose" + pageDiv + "").val() != '') {
                                        $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                                    }
                                }
                            })
                            vm.endFlg = false;
                        }
                    }
                })
            } else {
                layer.open({
                    id: 'F21015',
                    type: 2,
                    title: ['', 'display:none;'],
                    shade: 0.1,
                    closeBtn: 1,
                    shadeClose: false,
                    area: ['45%', '50%'],
                    fixed: true,
                    resize: false,
                    content: ["../pop/F21015.html?pageDiv=" + pageDiv + '&text=' + $(obj).val()],
                    success: function (layero, index) {
                    },
                    end: function () {
                        if (vm.endFlg) {
                            if (vm.codKey != null && vm.codKey != '') {
                                $(obj).text('入力済').val(vm.codKey);
                                vm.codKey = '';
                            }else {
                                if ($(obj).hasClass("headButton")) {
                                    $(obj).text('まとめ入力').val('');
                                }else {
                                    $(obj).text('').val('');
                                }
                                $("#allSet" + pageDiv + "").attr("disabled","disabled").css("background", "#BFBFBF").css("color", "black");
                            }
                            if ($("#divChoose" + pageDiv + "").val() != '') {
                                $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                            }
                            vm.endFlg = false;
                        }
                    }
                })
            }
        }
    }else {
        if (pageDiv <= 8) {
            layer.open({
                id: 'F21006',
                type: 2,
                title: ['', 'display:none;'],
                shade: 0.1,
                closeBtn: 1,
                shadeClose: false,
                area: ['45%', '50%'],
                fixed: true,
                resize: false,
                content: ["../pop/F21006.html?grpId=" + param.grpId + '&pageDiv=' + pageDiv + '&codCd=' + $(obj).val()],
                success: function (layero, index) {
                    if (pageDiv == 6) {
                        vm.codKey = 'LAST_TIME_HWK_DIV';
                    } else if (pageDiv == 2) {
                        vm.codKey = 'ABS_STS_DIV';
                    } else if (pageDiv == 7) {
                        vm.codKey = 'SCH_STS_DIV';
                    } else if (pageDiv == 8) {
                        vm.codKey = 'LECT_SHAPE_DIV';
                    }
                },
                end: function () {
                    if (vm.endFlg) {
                        $.ajax({
                            url: ctxPath + '/manager/F21008/getSelectedItems',
                            type: 'GET',
                            data: {
                                selectData: pageDiv == 8 ? JSON.stringify(vm.codCd) : vm.codCd,
                                codKey: vm.codKey,
                                pageDiv: pageDiv
                            },
                            success: function (data) {
                                if (data.mstCodDEntity) {
                                    if (pageDiv == 2) {
                                        $(obj).text(data.mstCodDEntity.codValue).val(data.mstCodDEntity.codValue);
                                    } else {
                                        $(obj).text(data.mstCodDEntity.codValue2).val(data.mstCodDEntity.codCd);
                                    }
                                    $(obj).val(data.mstCodDEntity.codCd);
                                    vm.codKey = '';
                                }else {
                                    if ($(obj).hasClass("headButton")){
                                        $(obj).text('まとめ入力').val('');
                                    } else {
                                        $(obj).text('').val('');
                                    }
                                    $("#allSet" + pageDiv + "").attr("disabled","disabled").css("background", "#BFBFBF").css("color", "black");
                                }
                                if ($("#divChoose" + pageDiv + "").val() != '') {
                                    $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                                }
                            }
                        })
                        vm.endFlg = false;
                    }
                }
            })
        } else {
            layer.open({
                id: 'F21015',
                type: 2,
                title: ['', 'display:none;'],
                shade: 0.1,
                closeBtn: 1,
                shadeClose: false,
                area: ['45%', '50%'],
                fixed: true,
                resize: false,
                content: ["../pop/F21015.html?pageDiv=" + pageDiv + '&text=' + $(obj).val()],
                success: function (layero, index) {
                },
                end: function () {
                    if (vm.endFlg) {
                        if (vm.codKey != null && vm.codKey != '') {
                            $(obj).text('入力済').val(vm.codKey);
                            vm.codKey = '';
                        }else {
                            if ($(obj).hasClass("headButton")) {
                                $(obj).text('まとめ入力').val('');
                            }else {
                                $(obj).text('').val('');
                            }
                            $("#allSet" + pageDiv + "").attr("disabled","disabled").css("background", "#BFBFBF").css("color", "black");
                        }
                        if ($("#divChoose" + pageDiv + "").val() != '') {
                            $("#allSet" + pageDiv + "").removeAttr("disabled").css("background", "#00a65a").css("color", "#ffffff");
                        }
                        vm.endFlg = false;
                    }
                }
            })
        }
    }
};

//「ALL」ボタン押下
function setAll(pageDiv) {
    var msgText = '';
    if (pageDiv == 6) {
        msgText = '前回の宿題';
    } else if (pageDiv == 2) {
        msgText = '出欠';
    } else if (pageDiv == 7) {
        msgText = '進捗の状況';
    } else if (pageDiv == 8) {
        msgText = '授業の様子';
    } else if (pageDiv == 9) {
        msgText = '使用テキスト';
    } else if (pageDiv == 10) {
        msgText = '本日の授業内容';
    } else if (pageDiv == 11) {
        msgText = '本日の宿題内容';
    } else if (pageDiv == 12) {
        msgText = '小テスト単元名';
    } else if (pageDiv == 13) {
        msgText = '連絡事項';
    }

    $(".singleSet" + pageDiv + "").text($("#divChoose" + pageDiv + "").text());
    $(".singleSet" + pageDiv + "").val($("#divChoose" + pageDiv + "").val());
}





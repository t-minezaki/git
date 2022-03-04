let param = getParam();
let orgId = param.orgId;
let selectedStu = [];
var selectPoint = [];

var stuList = param.stuIdList ? JSON.parse(decodeURIComponent(param.stuIdList)) : [];
let isFirst = !param.isFirst;

let jqGridModel = [{
    // 2020/12/28 9.0 cuikailin modify start
    label: '<input id="allCheck" type="checkbox" onclick="allSelect(this)"/>', name: 'usrId', index: 'usrId', width: 3, sortable: false, align: "center",
        formatter(cell, option, object) {
            if(selectedStu.indexOf(object.usrId) !== -1){
                return "<input type='checkbox' checked onclick='isCheckBox(this)' class='stu_check' value='" + object.usrId + "' data-point='"+object.point+"'/>";
            }
            return "<input type='checkbox' onclick='isCheckBox(this)' class='stu_check' value='" + object.usrId + "' data-point='"+object.point+"'/>";
        }
    // 2020/12/28 9.0 cuikailin modify end
    },
    {label: '組織名', name: 'orgNm', index: 'orgNm', width: 8, sortable: false, align: "center"},
    {label: '生徒ID', name: 'stuId', index: 'stuId', width: 8, key: true, sortable: true, align: "center"},
    {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 8, sortable: true, align: "center"},
    {label: '学年', name: 'schy', index: 'schy', width: 10, sortable: false, align: "center"},
    {label: '現在ポイント', name: 'point', index: 'point', width: 7, sortable: false, align: "center"},
    {label: '累計ポイント',name:'allPoint',index: 'allPoint',width: 7,sortable: false,align: "center"}];

function load() {
    if (isFirst) {
        jqGridInit();
        isFirst = false
    }else {
        jqGridReload()
    }

    // jqGrid重載
    $('#jqGrid').trigger('reloadGrid');
}

function loadJqGrid(url, data) {
    $.jgrid.gridUnload("jqGrid");
    let width = $(window).width() * 0.99;
    // let srcHeight = $(window).height() * 0.5;
    let srcHeight = $(window).height() - $(window).width() * 0.25 - 10 - ($(window).width() / 100) * 3;
    $("#jqGrid").jqGrid({
        url: ctxPath + url,
        datatype: "json",
        postData: data,
        colModel: jqGridModel,
        viewrecords: true,
        height: srcHeight,
        width: width,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        pager: "#jqGridPager",
        sortable: true,
        sortname: 'userId',
        sortorder: 'asc',
        jsonReader:
            {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
        },
        loadComplete: function (data) {
            let jqGridDom = $("#jqGrid");
            jqGridDom.setGridHeight($(window).height() * 0.55);
            jqGridDom.setGridWidth($(window).width() * 0.9);
            if (data.code !== 0) {
                showMsg(data.msg);
            }
        }
    })
}

function jqGridInit() {
    loadJqGrid('/manager/F09006/init', {});
}

function jqGridReload() {
    loadJqGrid('/manager/F09006/check_after', {
        stuList: JSON.stringify(stuList)
    });
}

$("#check_d").click(function () {
    let srcWidth = "750px";
    let srcHeight = "350px";
    layer.open({
        id: 'F09003',
        type: 2,
        anim: 2,
        skin: "layui-layer-myskin",
        title: [" ", 'height:3vh'],
        shade: 0.2,
        closeBtn: 1,
        shadeClose: false,
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        background: '#F0F0F0',
        content: ["../pop/F09003.html"],
        end: function () {
            if (stuList == null || stuList.length === 0) {
                // reload(null,null);
                layer.close();
            }
            else {
                jqGridReload();
                // jqgrid重載
                $('#jqGrid').trigger('reloadGrid');
                $(".topMessage").css("display", "None")
            }
        }
    });
});

// 「確認」ボタン押下
function submit() {
    // 選択された生徒なしの場合
    if (selectedStu.length === 0) {
        showMsg($.format($.msg.MSGCOMN0028, "生徒"));
        return;
    }

    let point = $('#point').val();
    if (point.indexOf('.') !== -1){
        showMsg($.format($.msg.MSGCOMN0180, "付与ポイント"));
        return;
    }
    if (!/^\d+$/.test( point.replace('-','') ))
    {
        showMsg($.format($.msg.MSGCOMN0170, "付与ポイント"));
        return;
    }
    // 付与ポイントが未設定　または　0の場合
    if (!point ||Number(point)== 0 ||point==='-') {
        showMsg($.format($.msg.MSGCOMN0170, "付与ポイント"));
        return;
    }
    // 2020/12/28 9.0 cuikailin modify start
    for (var i=0;i<selectPoint.length;i++) {
        if ((parseInt(selectPoint[i]) + parseInt(point)) <0 ){
            showMsg($.format($.msg.MSGCOMN0179));
            return;
        }
    }
    // 2020/12/28 9.0 cuikailin modify end

    window.location.href = './F09011.html?' + $.param({
        point: Number(point),
        stuIdList: JSON.stringify(selectedStu)
    })
}

// 「リセット」ボタン押下
function reset() {
    // 選択された生徒なしの場合、なにもしない。
    if (selectedStu.length === 0) {
        return;
    }

    // アラートメッセージを出す
    let index = layer.confirm($.msg.MSGCOMN0171, {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                type: "POST",
                url: ctxPath + '/manager/F09006/resetPoint',
                contentType: "application/json",
                data: JSON.stringify({
                    stuIdList: selectedStu
                }),
                /* modify at 2021/09/01 for V9.02 by NWT wen START */
                success(data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                        return;
                    }
                    /* modify at 2021/09/01 for V9.02 by NWT wen END */
                    /* 2021/01/13 UT-117 cuikailin modify start */
                    // allSelect();
                    // 画面データを再取得する
                    if (stuList.length > 0){
                        jqGridReload();
                    } else {
                        jqGridInit();
                    }
                    /* 2021/01/13 UT-117 cuikailin modify end */
                }
            })
        }
    })
}

// 付与ポイントと生徒IDリストを引渡データとして、付与ポイント確認画面遷移を行う
function isCheckBox(dom){
    if ($(dom).prop("checked")) {
        selectedStu.push(dom.value);
        // 2020/12/28 9.0 cuikailin add start
        selectPoint.push(dom.getAttribute("data-point"));
        if (selectedStu.length!=0 && selectedStu.length==$(".stu_check").length){
            $("#allCheck")[0].checked=true;
        }
        // 2020/12/28 9.0 cuikailin add end
    } else {
        selectedStu.pop(dom.value);
        // 2020/12/28 9.0 cuikailin add start
        selectPoint.pop(dom.getAttribute("data-point"));
        if (selectedStu.length!=$(".stu_check").length){
            $("#allCheck")[0].checked=false;
        }
        // 2020/12/28 9.0 cuikailin add end
    }
}

/**
 *   //2020/11/25 9.0 wyh new start
 * 入力の長さを決定します
 */
function t() {
    let pointInput = $('#point');
    let val = pointInput.val();
     if (val>99999){
        if (val.indexOf("0")===0){
            pointInput.val(val.slice(0,6));
        }else {
            pointInput.val(val.slice(0, 5));
        }
    }else if (val<0){
        pointInput.val(val.slice(0,6));
    }
}
//2020/11/25 9.0 wyh new end
// 2020/12/28 9.0 cuikailin add start
function allSelect(e) {
    if ( e && e.stopPropagation ) {
        e.stopPropagation();
    }else{
        window.event.cancelBubble = true;
    }
    var allBox = $(".stu_check");
    if (selectedStu.length !=0 && allBox.length == selectedStu.length) {
        for(var i=0; i<$(".stu_check").length; i++){
            $(".stu_check")[i].checked=false
        }
        selectedStu = [];
        selectPoint = [];
    }else {
        $(".stu_check").prop("checked",true);
        selectedStu = [];
        for(var i=0; i<$(".stu_check").length; i++){
            selectedStu.push($(".stu_check")[i].value);
            selectPoint.push($(".stu_check")[i].getAttribute("data-point"));
        }
    }
}
// 2020/12/28 9.0 cuikailin add end
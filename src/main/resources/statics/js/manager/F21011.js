var startDt = "";
var endDt = "";
var closeFlg = false;
var stuId = [];
var gridStuList = [];
var param = JSON.parse(window.sessionStorage.getItem("params")) === null?getParam():JSON.parse(window.sessionStorage.getItem("params"));
var a ="";
var schyDiv = "";
var height = 0;

function reload() {
    if ((param.stuList)){
        var stuList = JSON.parse(decodeURIComponent(decodeURIComponent(param.stuList)));
        for (var i = 0; i < stuList.length; i++) {
            gridStuList.push(stuList[i].stuId);
        }
        vm.selectStuCount = gridStuList.length;
        //2020/11/23 huangxinliang modify start
        delete param.stuList;
        //2020/11/23 huangxinliang modify end
    }
    var srcWidth = $(window).width() * 0.85;
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    srcWidth = $(window).width();
                    $("#jqGridPager").css("width", $(window).width());
                } else if (/iPad/i.test(navigator.userAgent)) {
                    srcWidth = $(window).width() * 0.7;
                    $("#jqGridPager").css("width", $(window).width() * 0.7);
                } else {
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
            srcWidth = $(window).width() * 0.7;
            $("#jqGridPager").css("width", $(window).width() * 0.7);
        }
    }
    // laydate日時を設ける
    laydate.render({
        elem: '#dayPic',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        done: function (value) {
            vm.attendanceBookDate = value;
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#pubStartDt',
        eventElem: '#timeOne',
        trigger: 'click',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            vm.startTime = value;
        }
    });

// laydate日時を設ける
    laydate.render({
        elem: '#pubEndDt',
        eventElem: '#timeTwo',
        trigger: 'click',
        type: 'datetime',
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            vm.endTime = value;
        }
    });
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F21011/init',
            datatype: "json",
            postData: {
                guidReprCd: param.guidReprDeliverCd,
                stuIdList: JSON.stringify(gridStuList)
            },
            colModel: [
                {label: '', name: 'stuId', index: 'stuId', width: 5, sortable: false, align: "center", hidden: true},
                {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 5, sortable: false, align: "center"},
                {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 5, sortable: false, align: "center"},
                {
                    label: '生徒名', name: 'name', index: 'name', width: 5, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var name = '';
                        if (object.stuNm != null || object.stuLnm != null){
                            name = object.stuNm + "　" + object.stuLnm;
                        }
                        return name;
                    }
                },
                {
                    label: '保護者名', name: 'guardName', index: 'guardName', width: 5, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var guardName = '';
                        if (object.flnmNm != null || object.flnmLnm != null){
                            guardName = object.flnmNm + "　" + object.flnmLnm;
                        }
                        return guardName;
                    }
                },
                {
                    label: '教科', name: 'codValue', index: 'codValue', width: 5, sortable: false, align: "center", hidden: true
                }

            ],
            viewrecords: true,
            height: height,
            width: srcWidth,
            rowNum: 30,
            rowList: [10, 30, 50],
            rownumbers: false,
            rownumWidth: 25,

            // loadonce: true,
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
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            }
            ,
            loadComplete: function (data) {
                if (!param.guidReprDeliverCd){
                    a=1;
                } else {
                    a=initial;
                }
                if (data.code == 0) {
                    /* 2020/12/7 V9.0 cuikailin add start */
                    vm.selectStuCount = data.page.totalCount;
                    /* 2020/12/7 V9.0 cuikailin add end */
                    if (param.add == 1){
                        if (initial == '0') {
                            $(".choose").attr("disabled", "disabled");
                            $("#toF09003").attr("disabled", "disabled");
                            $("#timeOne").css("pointer-events", "none");
                            $("#timeTwo").css("pointer-events", "none");
                        }
                    }
                    if (data.page.list.length > 0 && data.page.list[0].noticeLevelDiv === '1'){
                        $("#noticeLevelDiv").attr("checked",true);
                    }
                } else {
                    showMsg(data.msg);
                }

            }

        }
    );
    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
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
    m = date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes();
    s = date.getSeconds() > 9 ? ':' + date.getSeconds() : ':' + '0' + date.getSeconds();
    return Y + M + D + h + m;
}

function getJQAllData() {
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
var initial;
if (param.a !=undefined ){
    initial = param.a;
}else{
    initial = param.pick
}
var vm = new Vue({
    el: "#app",
    data:{
        attendanceBookDate:"",
        startTime: "",
        endTime: "",
        pick:initial,
        mstCodDEntityList:[],
        codCd:"",
        status:"",
        selectStuCount: 0,
        guardAndStudentList: [],
        /* 2020/12/7 V9.0 cuikailin add start */
        againFlag:param.againFlag,
        /* 2020/12/7 V9.0 cuikailin add end */
    },
    mounted: function () {
        this.init();

    },
    updated: function () {
        if (param.add == 1){
            if (param.pick == '0'){
                $("input[type='radio'][name='a']").attr("disabled",true);
            }
        }

        if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
            if (window.location.href.indexOf("?mobile") < 0) {
                try {
                    if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                        $("input[type = \"radio\"].newtui-radio-one").append('<style>input[type = "radio"].newtui-radio-one:checked::after{border: 2px solid' +
                            ' #AAA;}</style>');
                        $(".chooseBtn").css("width", "18vw").css("font-size", "1.2vw");
                    } else if (/iPad/i.test(navigator.userAgent)) {
                        $(".nolabel:disabled").css("background", "none");
                    } else {
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
                $(".nolabel:disabled").css("background", "none");
            }
        }
    },
    methods: {
        timeFormat: function (time) {
            return dateFmt(time)

        },
        init:function () {
            height = getRemainingHeight('body', 'statusTitle', 'choose', 'jqGridPager', 'timeChoose', 'bottomBtn') - $(window).height() * 0.15 - 30;
            $.ajax({
                url: ctxPath + '/manager/F21011/getCod',
                type: "GET",
                data: {},
                success:function (data) {
                    vm.mstCodDEntityList = data.mstCodDEntityList;
                    if (param.guidReprDeliverCd == null){
                        param.eventStsDiv = "1";
                        vm.pick = param.eventStsDiv
                    }
                }
            })
        }
    },
});
var pubStartDt = (decodeURIComponent(param.startDt));
var pubEndDt = (decodeURIComponent(param.endTime));
$(function () {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $(".one").css("height", "18px");
                    $("#bottomBtn").css("margin-left", "unset").css("width", "95%").css("text-align", "center");
                } else if (/iPad/i.test(navigator.userAgent)) {
                    $("#bottomBtn").css("margin-left", "unset").css("width", "70%").css("text-align", "center");
                } else {
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
            $("#bottomBtn").css("margin-left", "unset").css("width", "70%").css("text-align", "center");
        }
    }
    if (param.startDt == null){
        reload();
    } else {
        if (param.flag){
            reload();
            vm.attendanceBookDate = decodeURIComponent(decodeURIComponent(param.attendanceBookDate));
            vm.startTime= decodeURIComponent(decodeURIComponent(param.startDt));
            vm.endTime = decodeURIComponent(decodeURIComponent(param.endTime));
        } else {
            vm.attendanceBookDate = decodeURIComponent(decodeURIComponent(param.attendanceBookDate));
            vm.startTime=(decodeURIComponent(param.startDt));
            vm.endTime = (decodeURIComponent(param.endTime));
            reload();
        }
    }

    $("#btn_ok").click(function () {



        if (getJQAllData().size ==0&&vm.pick == '0') {
            showMsg($.format($.msg.MSGCOMN0028, "送信対象者"));
            return;
        }
        if (vm.startTime.trim() == "" || vm.endTime.trim() == "") {
            showMsg($.format($.msg.MSGCOMD0001, "公開開始日時と公開終了日時"));
            return;
        }
        if (vm.startTime.trim() == "" && vm.endTime.trim() == "") {
            showMsg($.format($.msg.MSGCOMD0001, "公開の開始と終了日時"));
            return;
        }
        var date = new Date();
        if (vm.endTime.trim() < vm.startTime.trim() || vm.endTime.trim() < date.Format("yyyy/MM/dd HH:mm")) {
            showMsg($.format($.msg.MSGCOMN0048, "公開終了日時","公開開始日時と本日")) ;
            return;
        }
        var dataList = [];
        var jqGridList = $("#jqGrid").jqGrid("getRowData");
        var allCountID = $("#jqGrid").jqGrid('getDataIDs');
        jqGridList.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length - 1]));
        for (var i = 0; i < jqGridList.length; i++) {
            if (jqGridList[i].guardName == ''){
                dataList.push(jqGridList[i]);
                $("#" + (i+1)).css('background','red').css('color','white');
            }
        }
        if (dataList.length > 0){
            showMsg($.format($.msg.MSGCOMN0150)) ;
            return;
        }
        var params ={};
        params.pick = vm.pick;
        $(".eventStsDiv").find("input[value="+ vm.pick +"]").text();
        var chooseTxt = "";
        for (var i = 0; i < vm.mstCodDEntityList.length; i ++) {
            if (vm.pick == vm.mstCodDEntityList[i].codCd) {
                chooseTxt = vm.mstCodDEntityList[i].codValue;
                // if (i == 1){
                //     chooseTxt += "中";
                // }
                break;
            }
        }
        //2020/11/19 9.0 huangxinliang modify start
        params.noticeLevelDiv = $("#noticeLevelDiv")[0].checked === true?'2':'1';
        //2020/11/19 9.0 huangxinliang modify end
        params.add = param.add;
        params.guidReprDeliverCd= param.guidReprDeliverCd;
        params.chooseText = chooseTxt;
        params.attendanceBookDate = $("#attendanceBookDate").val(),
        params.startDt = vm.startTime;
        params.endTime = vm.endTime;
        params.mstCodDEntityList = vm.mstCodDEntityList;
        params.stuList = JSON.stringify(getJQAllData());
        params.a = a;
        /* 2020/12/7 V9.0 cuikailin add start */
        params.againFlag = vm.againFlag;
        /* 2020/12/7 V9.0 cuikailin add end */
        window.sessionStorage.setItem("params",JSON.stringify(params));
        location.href="F21012.html";
    });

    $("#backBtn").click(function () {
        if ((vm.pick == a || vm.pick == "") && (vm.startTime == pubStartDt || vm.startTime == "") && (vm.endTime == pubEndDt || vm.endTime == "") && getJQAllData().length == 0) {
            location.href = "F21010.html";
        } else {
            var idx = layer.confirm($.msg.MSGCOMN0135, {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(idx);
                },
                btn2: function () {
                    window.sessionStorage.removeItem("params");
                    location.href = "F21010.html";
                }
            });
        }
    });
});


// 2020/11/11 huangxinliang modify start
$("#toF04004").click(function () {
    layer.open({
        id: 'F04004',
        type: 2,
        anim: 2,
        skin: "layui-layer-myskin",
        title: false,
        shade: 0.2,
        closeBtn: 0,
        shadeClose: false,
        move: '.layui-layer-title',
        area: ['80%', '75%'],
        fixed: true,
        resize: false,
        content: ["../pop/F04004.html?orgSelectDiv=0", 'no'],
        success: function (layero, index) {
        },
        end: function () {
            if (closeFlg){
                closeFlg = false;
                gridStuList=[];
                //2020/11/23 9.0 huangxinliang modify start
                vm.selectStuCount = vm.guardAndStudentList.length;
                for (var i = 0; i <vm.guardAndStudentList.length ; i++) {
                    gridStuList.push(vm.guardAndStudentList[i].stuId);
                }
                //2020/11/23 9.0 huangxinliang modify end
                reload();
            }
        }
    });
});
// 2020/11/11 huangxinliang modify end

var chooseText;
var choose;
$('input[type=radio][name=a]').change(function () {
    vm.pick = this.value;
    $(".eventStsDiv span").each(function () {
        if ($(this).id == vm.pick){
            chooseText = this.text();
        }
    })
    reload();
});

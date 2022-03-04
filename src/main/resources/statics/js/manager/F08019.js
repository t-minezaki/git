var params = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        eventId: '',
        menuStatus: 1,
        title: "",
        objSum: 20,
        div: 4,
        flg: '',
        headMsg: "",
        objList: [],
        sendObjList: [],
        sortList: [
            {name: "guardAfterId", rule: "asc"},
            {name: "guardNm", rule: "asc"},
            {name: "stuAfterId", rule: "asc"},
            {name: "stuNm", rule: "asc"},
            {name: "status", rule: "asc"},
            {name: "statusDiv", rule: "asc"}
        ],
        sortkey: "guardId"
    },
    computed: {},
    methods: {
        dataChange: function (menuStatus, objList, sendObjList) {
            if (menuStatus == 1)
                return objList;
            if (menuStatus == 2)
                return sendObjList;
        },
        /* 2020/12/23 UT-38 cuikailin add start */
        check: function (){
            if ($("#message") != null) {
                $("#message").hide();
            }
        },
        /* 2020/12/23 UT-38 cuikailin add start */
    },
    mounted: function () {
    },
    updated: function () {
        vm.objSum = vm.menuStatus == 1 ? vm.objList.length : vm.sendObjList.length;
    }
});

vm.eventId = params.id;
vm.div = params.div;
vm.flg = params.flg;
var srcHeight = $(window).height() * 0.55;
var width = $(window).width() * 0.95;

$(function () {
    // 全テーマ検索
    $("#tbl_title").on("click", "#all_select", function () {
        var checked = $(this).prop("checked");
        $(".child_select").prop("checked", checked);
    });
    // 画面．ステータスが「1：新規」の場合、メール送信押下ボダン押下
    $(".div_head").on("click", "#sendMsg", function () {
        if (vm.menuStatus == 1) {
            var checked = $(".child_select:checked");
            var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
            if (ids.length <= 0) {
                // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "対象"));
                return;
            }
            var list = [];
            for (var i = 0; i < ids.length; i++) {
                // if (vm.objList[$(checked[i]).attr("content")].mailAddress)
                if(vm.objList[parseInt(ids[i])-1].replyStsDiv==="0"){
                    list.push(vm.objList[parseInt(ids[i])-1]);
                }
            }
            //2020/11/19 9.0 huangxinliang add start
            if (list.length <= 0){
                // 画面．未開封対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "未読対象"));
                return;
            }
            //2020/11/19 9.0 huangxinliang add end
            vm.sendObjList = list;
            vm.menuStatus = 2;
            //2020/11/19 9.0 huangxinliang modify start
            // 確認メッセージ（MSGCOMN0173）を画面上部に表示する。
            vm.headMsg = $.msg.MSGCOMN0174;
            //2020/11/19 9.0 huangxinliang modify end
        }
    });
    // 戻るボタン押下
    $("#btn_cancel").click(function () {

        if (vm.menuStatus == 1) {
            // 画面．ステータスが「1：新規」の場合
            location.href = "F08009.html";
        } else if (vm.menuStatus == 2) {
            // 画面．ステータスが「2：確認」の場合
            vm.menuStatus = 1;
        }
    });
    // 画面．ステータスが「2：確認」の場合、メール送信押下ボダン押下
    $(".bottomBtn").on("click", "#sendMsg_down", function () {
        // 画面．ステータスが「2：確認」の場合
        if (vm.menuStatus == 2) {
            var guardAndStudentList = [];
            for (var i = 0; i < vm.sendObjList.length; i++) {
                var guardAndStudent = {};
                guardAndStudent.afterUsrId = vm.sendObjList[i].stuAfterId;
                guardAndStudent.stuName = vm.sendObjList[i].stuNm;
                guardAndStudent.schyDiv = vm.sendObjList[i].schyDiv;
                guardAndStudent.stuId = vm.sendObjList[i].stuId;
                guardAndStudent.guardId=vm.sendObjList[i].guardId;
                guardAndStudent.readFlg = vm.sendObjList[i].readingStsDiv;
                guardAndStudent.replyFlg = vm.sendObjList[i].replyStsDiv;
                guardAndStudentList.push(guardAndStudent);
            }
            var object = {};
            // object.guardAndStudentList = JSON.stringify(guardAndStudentList);
            object.id = params.id;
            object.reSentFlg = '1';
            window.localStorage.setItem("guardAndStudentList",JSON.stringify(guardAndStudentList));
            window.location.href = "./F08010.html?" + $.param(object);
        }
    });
    // 対象者ダウンロードボダン押下
    $(".div_head").on("click", "#objDownload", function () {
        // 2021/08/31 manamiru1-730 cuikl edit start
        var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
        if (ids.length <= 0) {
            // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
            showMsg($.format($.msg.MSGCOMN0087, "ダウンロード対象", "対象"));
            return;
        }
        var stuIdList = [];
        for (var i = 0; i < ids.length; i++) {
            stuIdList.push(vm.objList[parseInt(ids[i])-1].stuId);
        }
        // 2021/08/31 manamiru1-730 cuikl edit end
        // 対象を選ばなければ
        if (stuIdList.length <= 0) {
            return null;
        }
        params.stuIdListStr = JSON.stringify(stuIdList);
        params.eventId = vm.eventId;
        params.div = vm.div;
        params.flg = vm.flg;
        // チェックされたユーザに対して、下記条件で、イベント、保護者イベント申込状況を元に、対象者CSVファイルを出力する。
        window.location.href = './F08019/getDownloadInfo?'+ $.param(params);

    });
    var colModel = [
        {label: '保護者ID', name: 'guardAfterId', index: 'guardAfterId', width: 200, sortable: false, align: "center"},
        {label: '保護者名', name: 'guardNm', index: 'guardNm', width: 200, sortable: false, align: "center"},
        {label: '生徒ID', name: 'stuAfterId', index: 'stuAfterId', width: 200, sortable: false, align: "center"},
        {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 200, sortable: false, align: "center"},
        {label: 'ステータス', name: 'status', index: 'status', width: 80, sortable: false, align: "center"},

    ];
    if (vm.menuStatus == 1 && vm.div == 5){
        colModel.push({label: '面談実施状況', name: '', index: '', width: 120, sortable: false, align: "center",
            formatter(cell, option, object){
            if (object.statusDiv) {
                return object.statusDiv;
            }else {
                return "";
            }
            }},
            {label: '操作', name: '', index: '', width: 100, sortable: false, align: "center",
            formatter(cell, option, object){
                if((object.talkApplyStsDiv == '0') && object.talkId){
                    return "<button  class='record_btn' onclick=toF21075('"+ object.talkId+"')>記録する</button>";
                }else {
                    return "";
                }
        }})
    }
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F08019/init',
        datatype: 'json',
        postData: {
            eventId: vm.eventId,
            div: vm.div,
            flg: vm.flg
        },
        colModel:colModel,
        viewrecords: true,
        regional: 'jp',
        rownumbers: false,
        height:srcHeight,
        width:width,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumWidth: 25,
        sortable:true,
        sortorder:'desc',
        multiselect: true,
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
        }
        ,
        loadComplete: function (data) {
            if (vm.div == 1) {
                vm.title = data.title + "総件数";
            } else if (vm.div == 2) {
                vm.title = data.title;
            }else if (vm.div == 3){
                vm.title = data.title;
            } else if (vm.div == 4){
                vm.title = data.title;
            }else if (vm.div == 5){
                vm.title = "予約";
            } else {
                vm.title = data.title + "対象者一覧";
            }
            if (!data.code || data.code == 0) {
                vm.objList = data.page.list;
            } else {
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
        },
        beforeSelectRow: function (rowid, e) {
            var $myGrid = $(this),
                i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
                cm = $myGrid.jqGrid('getGridParam','colModel');
            return (cm[i].name ==='cb');
        }
    });
});

function toF21075(talkId) {
    layer.open({
        id: 'f08019',
        type: 2,
        title: [" ", "background:none !important;border-bottom:none !important"],
        shade: 0.3,
        closeBtn: 1,
        shadeClose: false,
        area: ['60%', '75%'],
        content: ["../pop/F21075.html?talkId=" + talkId + "&eventId=" + vm.eventId],
        /* 2021/1/4 UT-047 add start */
        resize:false,
        /* 2021/1/4 UT-047 add end */
        success: function (layero, index) {
        }
    });
}

$(".div_head").on("click", "#deliver", function () {
    var object = {};
    var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
    if (ids.length <= 0) {
        // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
        showMsg($.format($.msg.MSGCOMN0087, "送信対象", "対象"));
        return;
    }
    var list = [];
    for (var i = 0; i < ids.length; i++) {
        var obj = vm.objList[parseInt(ids[i])-1];
        if (obj.talkApplyStsDiv === '0' || obj.talkApplyStsDiv === '3'){
            showMsg($.format($.msg.MSGCOMN0182, "未実施　または　配信済の面談記録"));
            return;
        }
        if (obj.talkApplyStsDiv === '2' && obj.talkId) {
            var param = {};
            param.guardId = obj.guardId;
            param.gId = obj.guardAfterId;
            param.guardNm = obj.guardNm;
            param.stuId = obj.stuId;
            param.sId = obj.stuAfterId;
            param.stuNm = obj.stuNm;
            param.schyDiv = obj.schyDiv;
            list.push(param);
        }
    }
    object.userList = JSON.stringify(list);
    object.eventId = vm.eventId;
    object.flg = vm.flg;
    var index = layer.confirm('面談記録を配信します。よろしいですか。', {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            window.location.href = "F21076.html?" + $.param(object);
        }
    })
})
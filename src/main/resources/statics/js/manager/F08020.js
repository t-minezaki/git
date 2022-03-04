var params = getParam();
var data = params;
var status = 1;
var sendObjList = [];
if (params.getData!=undefined){
   data = JSON.parse(decodeURIComponent(params.getData));
   status = data.menuStatus;
   sendObjList = data.sendObjList;
}
var vm = new Vue({
    el: '#app',
    data: {
        id: '',
        menuStatus: status,
        openDiv: data.openDiv,
        title: "",
        topTitle:"",
        objSum: 20,
        readDiv: data.readDiv,
        headMsg: "",
        objList: [],
        sendObjList: sendObjList,
        sortList: [
            {name: "guardAfterId", rule: "asc"},
            {name: "guardNm", rule: "asc"},
            {name: "stuAfterId", rule: "asc"},
            {name: "stuNm", rule: "asc"},
            {name: "status", rule: "asc"},
            {name: "statusDiv", rule: "asc"}
        ],
        sortkey: "guardId",
        //2020/11/19 9.0 huangxinliang modify start
        orgId: ''
        //2020/11/19 9.0 huangxinliang modify end
    },
    methods: {
        dataChange: function (menuStatus, objList, sendObjList) {
            if (menuStatus == 1)
                return objList;
            if (menuStatus == 2)
                return sendObjList;
        },
        display: function () {
            return ($('#objTable').height() - $(window).height() * 0.04 - 4) > $('.tbl_div').height() ? '' : 'display:none;';
        },
        init: function () {
            $.get(ctxPath + "/manager/F08020/init",
                {
                    noticeId: data.id,
                    openDiv: data.openDiv,
                    readDiv: data.readDiv,
                    //2020/11/19 9.0 huangxinliang modify start
                    orgId: data.orgId
                    //2020/11/19 9.0 huangxinliang modify end
                },
                function (data) {
                    if (data.code == 0) {
                        vm.objList = data.objList;
                        vm.title = data.title;
                    } else {
                        vm.title = data.title;
                        showMsg(data.msg);
                    }
                }
            );
        },
        getStatus: function (readingStsDiv, openedFlg){
            if (readingStsDiv === '0'){
                return '未読';
            }else {
                if (openedFlg === '0'){
                    return '既読未開封';
                }else if (openedFlg === '1'){
                    return '既読開封済';
                }
            }
        },
        check: function (){
            if ($("#message") != null) {
                $("#message").hide();
            }
        },
        isChecked: function (obj){
            return this.sendObjList.indexOf(obj) >= 0
        }
    },
    mounted: function () {
        //2020/11/19 9.0 huangxinliang modify start
        this.orgId = params.orgId;
        //2020/11/19 9.0 huangxinliang modify end
        this.id = params.id;
        //2021/02/02 liyuhuan add start
        if (params.msgType ==="0" ) {
            this.topTitle = "お知らせ管理";
        }else if (params.msgType ==="1") {
            this.topTitle = "イベント管理";
        }else {
            this.topTitle = "メッセージ管理";
        }
        //2021/02/02 liyuhuan add end
        if (params.readDiv){
            this.readDiv = params.readDiv;
        }
        if (params.openDiv){
            this.openDiv = params.openDiv;
        }
        this.init();
    },
    updated: function () {
        vm.objSum = vm.menuStatus == 1 ? vm.objList.length : vm.sendObjList.length;
    }
});

$(function () {
    // 全テーマ検索
    $("#tbl_title").on("click", "#all_select", function () {
        if ($("#message") != null) {
            $("#message").hide();
        }
        var checked = $(this).prop("checked");
        $(".child_select").prop("checked", checked);
    });
    // 画面．ステータスが「1：新規」の場合、再通知ボダン押下
    $(".div_head").on("click", "#deliver", function () {
        if ($("#message") != null) {
            $("#message").hide();
        }
        if (vm.menuStatus == 1) {
            var checked = $(".child_select:checked");
            if (checked.length <= 0) {
                // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "対象"));
                return;
            }
            var list = [];
            for (var i = 0; i < checked.length; i++) {
                var obj = vm.objList[$(checked[i]).attr("content")]
                if (obj.openedFlg === '0')
                    list.push(obj);
            }
            //2020/11/19 9.0 huangxinliang add start
            if (list.length <= 0){
                // 画面．未開封対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
                showMsg($.format($.msg.MSGCOMN0087, "送信対象", "未開封対象"));
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
        if ($("#message") != null) {
            $("#message").hide();
        }
        if (vm.menuStatus == 1) {
            // 画面．ステータスが「1：新規」の場合
            var params = {};
            params.id = data.id;
            window.location.href = "../manager/F05005.html?" + $.param(params);
        } else if (vm.menuStatus == 2) {
            // 画面．ステータスが「2：確認」の場合
            vm.menuStatus = 1;
        }
    });
    // 画面．ステータスが「2：確認」の場合、再通知ボダン押下
    $(".first_div").on("click", "#sendMsg_down", function () {
        if ($("#message") != null) {
            $("#message").hide();
        }
        // var index_load = parent.layer.load(2);
        // 画面．ステータスが「2：確認」の場合
        if (vm.menuStatus == 2) {
            var stuIds = [];
            for (var i = 0; i < vm.sendObjList.length; i++) {
                stuIds.push(vm.sendObjList[i].stuUsrId);
            }
            data.menuStatus = 2;
            data.sendObjList = vm.sendObjList;
            var params = {};
            params.id = vm.id==undefined?data.id:vm.id;
            params.stuIds = JSON.stringify(stuIds);
            params.reSentFlg=1;
            params.getData=JSON.stringify(data);
            // チェックされた未開封対象者ユーザを連れて、F05003_お知らせ編集画面（修正）_V9.0を遷移する
            window.location.href = "./F05003.html?" + $.param(params);
        }
    });
    // 対象者ダウンロードボダン押下
    $(".div_head").on("click", "#objDownload", function () {
        if ($("#message") != null) {
            $("#message").hide();
        }
        var checked = $(".child_select:checked");
        if (checked.length <= 0) {
            // 画面．対象者一覧が一件でもチェックされない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
            showMsg($.format($.msg.MSGCOMN0087, "送信対象", "対象"));
            return;
        }
        var stuIdList = [];
        var params = {};
        for (var i = 0; i < checked.length; i++) {
            stuIdList.push(vm.objList[$(checked[i]).attr("content")].stuUsrId);
        }
        // 対象を選ばなければ
        if (stuIdList.length <= 0) {
            return null;
        }
        params.stuIdListStr = JSON.stringify(stuIdList);
        params.noticeId = vm.id;
        params.div = vm.div;
        params.modelDiv = vm.modelDiv;
        //2020/11/19 9.0 huangxinliang modify start
        params.orgId = vm.orgId;
        //2020/11/19 9.0 huangxinliang modify end
        // チェックされたユーザに対して、下記条件で、イベント、保護者イベント申込状況を元に、対象者CSVファイルを出力する。
        var url = "./F08020/getDownloadInfo?" + $.param(params);
        window.location.href = url;

    });
});
var params = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        messageId: '',
        menuStatus: 1,
        title: "",
        objSum: 20,
        readDiv: null,
        div: 4,
        headMsg: "",
        objList: [],
        sendObjList: [],
        sortkey: "guardId"
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
            $.get(ctxPath + "/manager/F08022/init",
                {
                    messageId: this.messageId,
                    readDiv: this.readDiv,
                    orgId: this.orgId
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
        this.messageId = params.messageId;
        if (params.readDiv && params.readDiv !== 'undefined'){
            this.readDiv = params.readDiv;
        }
        this.orgId=params.orgId;
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
                if (obj.readingStsDiv==="0"){
                    list.push(obj);
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
        if ($("#message") != null) {
            $("#message").hide();
        }
        if (vm.menuStatus == 1) {
            // 画面．ステータスが「1：新規」の場合
            location.href = "F21071.html?id=" + params.messageId;
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
            var usrId = [];
            for (var i = 0; i < vm.sendObjList.length; i++) {
                usrId.push(vm.sendObjList[i].usrId);
            }
            var params = {};
            params.id = vm.messageId;
            params.usrId = JSON.stringify(usrId);
            // チェックされた未開封対象者ユーザを連れて、F21070_メッセージ編集画面（修正）_V9.0を遷移する
            window.location.href = "./F21070.html?" + $.param(params);
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
        var usrIdList = [];
        var params = {};
        for (var i = 0; i < checked.length; i++) {
            usrIdList.push(vm.objList[$(checked[i]).attr("content")].usrId);
        }
        // 対象を選ばなければ
        if (usrIdList.length <= 0) {
            return null;
        }
        params.usrIdListStr = JSON.stringify(usrIdList);
        params.messageId = vm.messageId;
        params.orgId=vm.orgId;
        params.readDiv = vm.readDiv;
        // チェックされたユーザに対して、下記条件で、イベント、保護者イベント申込状況を元に、対象者CSVファイルを出力する。
        var url = "./F08022/getDownloadInfo?" + $.param(params);
        window.location.href = url;

    });
});
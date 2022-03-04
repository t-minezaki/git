var param = getParam();
var users = JSON.parse(decodeURIComponent(param.userList));
$(function () {
    $("#jqGrid").jqGrid(
        {
            datatype: "local",
            colNames: ['保護者ID', '保護者名', '生徒ID', '生徒名', '学年'],
            colModel: [
                {name: 'gId', index: 'gId', align: "center"},
                {name: 'guardNm', index: 'guardNm', align: "center"},
                {name: 'sId', index: 'sId', align: "center"},
                {name: 'stuNm', index: 'stuNm', align: "center"},
                {name: 'schyDiv', index: 'schyDiv', align: "center"}
            ],
            multiselect: false,
            rowNum: 15,
            viewrecords: true,
            pager: "#jqGridPager"
        }
    );
    var localData = {page: 1, total: 2, records: "2", rows: users};
    localData.rows = users;
    localData.records = users.length;
    localData.total = (users.length % 15 == 0) ? (users.length / 15) : (Math.floor(users.length / 15) + 1);
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
    $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
});

function deliver() {
    var formData = new FormData();
    formData.append("eventId", param.eventId);
    formData.append("flg", $("input[name = 'object']:checked").val());
    formData.append("questionFlg", $("input[name = '_question']:checked").val());
    formData.append("interviewFlg", $("input[name = '_interview']:checked").val());
    formData.append("userList", JSON.stringify(users));
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "メッセージ登録"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + "/manager/F21076/deliver",
                data: formData,
                type: "POST",
                datatype: "JSON",
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        // var idx = layer.alert($.format($.msg.MSGCOMN0022, "メッセージ登録"), {
                        //     yes: function () {
                                window.location.href = "./F08019.html?id=" + param.eventId + "&flg=" + param.flg + "&div=5";
                        //         layer.close(idx);
                        //     }
                        // });
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        }
    });
}

//戻るボタン押下時
function back() {
    window.history.back();
}
let params = getParam();
let stuIdList = [];
let stuPoint = [];
let point = 0;
let srcHeight = $(window).height() * 0.6;
let width = $(window).width() * 0.48;

$(function(){
    if (params.stuIdList){
        stuIdList = JSON.parse(decodeURIComponent(params.stuIdList))
    }
    if (params.point){
        point = params.point
    }

    reload();

    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
});

// リスト重載
function reload() {
    // リストをはずす
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F09011/init',
        datatype: "json",
        postData: {
            stuIdList: JSON.stringify(stuIdList)
        },
        colModel: [
            {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 50, sortable: false, align: "center"},
            {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 50, sortable: false, align: "center"},
            {label: '学年', name: 'schy', index: 'schy', width: 25, sortable: false, align: "center"},
            {label: '付与ポイント', name: 'orgNm', index: 'orgNm', width: 50, sortable: false, align: "center",
                formatter() {
                    return point;
                }
            }
        ],
        viewrecords: true,
        regional: 'jp',
        rownumbers: false,
        height: srcHeight,
        width: width,
        rowNum: 30,
        rowList: [10, 20, 30],
        rownumWidth: 25,
        sortable: true,
        sortorder: 'desc',
        multiselect: false,
        pager: "#jqGridPager",
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
        loadComplete(data) {
            if (data.code !== 0) {
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }

            // 学生ポイント情報の更新時刻
            stuPoint = data.stuList;
        }
    });
}

$('#btn_submit').click(function (){
    let index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            let data = {
                stuIdList: stuIdList,
                point: point,
                oldStuPoint: stuPoint
            }
            $.ajax({
                url: ctxPath + '/manager/F09011/save',
                data: JSON.stringify(data),
                type: 'POST',
                datatype: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.code !== 0) {
                        showMsg(data.msg);
                        layer.close(index);
                    }

                    toF09006()
                }
            });
        }
    });
})

function toF09006() {
    // 2020/12/7 huangxinliang modify start
    window.location.href = "../manager/F09006.html?";
    // 2020/12/7 huangxinliang modify end
}
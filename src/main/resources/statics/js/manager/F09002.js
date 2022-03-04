var orgId = window.parent.$("#orgList").val();

var stuList = [];
var srcHeight = $(window).height() * 0.65;
var width = $(window).width() * 0.98;

// リスト重載
function reload() {
    // リストをはずす
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F09002/init',
        datatype: "json",
        postData: {
            orgId: orgId,
            stuIdListStr: JSON.stringify(stuList)
        },
        colModel:[
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 200, sortable: false, align: "center"},
            {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 200, sortable: false, align: "center"},
            {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 200, sortable: false, align: "center",fixed:true},
            {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 150, sortable: false, align: "center"},
            {label: '二次元コード', name: 'qrCod', index: 'qrCod', width: 300, sortable: false, align: "center",
                formatter(cell, option, object) {

                    var url = ctxPath + "/manager/F09002/qrcode?content=" + object.qrCod;
                    return "<img class='qr_img' src='" + url + "'><span class='qr_span'>" + object.orgId + "_" + object.afterUsrId + "</span>";
                }
            }

        ],
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
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader:
            {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount",
                orgId: "orgId",
                orgNm: "orgNm"
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
            if (!data.code || data.code == 0) {
            } else {
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
            if (data.orgId){
                orgId = data.orgId;
            }
        }
    });

    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}

$(function () {

    // 「印刷」ボタン押下
    $("#btn_print").bind("click", function () {

        var srcWidth = "200px";
        var srcHeight = "200px";
        layer.open({
            id: 'F09002-1',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "開始位置選択",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F09002-1.html?orgId=" + orgId + "&stuIdListStr=" + encodeURIComponent(JSON.stringify(stuList)), 'no'],
            success: function (layero, index) {

            },
            end:function () {
                if (closeFlg) {
                    reload();
                }
            }
        });
    });

    // 「対象者を選択する」ボタン押下
    $("#obj_select").bind("click", function () {

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
                content: ["../pop/F09003.html" , 'no'],
                success: function (layero, index) {
                }
                ,end : function() {
                    if (closeFlg){
                        reload();
                        closeFlg =false;
                    }

                }
            });
    });

    // 「ダウンロード」ボタン押下
    $("#btn_download").bind("click", function () {

        window.location.href = ctxPath + "/manager/F09002/getDownloadInfo?orgId=" + orgId + "&stuIdListStr=" + encodeURIComponent(JSON.stringify(stuList));
        reload();
    });

    reload();
});
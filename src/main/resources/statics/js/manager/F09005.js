var param = getParam();
var time = decodeURI(param.time);
var status = decodeURI(param.status);
var stuidlist = decodeURI(param.stuIdList);
$(function () {
    $(window).resize(function () {
        $("#jqGrid").setGridHeight($(window).height() * 0.7);
        $("#jqGrid").jqGrid('setGridParam',{
            colModel: getCols()
        }).trigger("reloadGrid");
        $("#jqGrid").setGridWidth($(window).width() * 0.85 + 5);
    });
    $("#back").click(function (){
        window.location.href = 'F09004.html';
    });
    $("#conf").click(function () {
        var lock = false;
        var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: ['キャンセル', '確認'],
            btn1: function () {

                layer.close(index);
            },
            btn2: function () {
                if (!lock){
                    lock = true;
                    var formData = new FormData();
                    formData.append("stuidlistStr", stuidlist);
                    formData.append("time", new Date(time));
                    formData.append("status", status);
                    $.ajax({
                        url: ctxPath + '/manager/F09005/save',
                        cache: false,
                        data:formData,
                        datatype: 'json',
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        async:false,
                        success: function (data) {
                            if (data.code == 0) {
                                window.location.href = 'F09004.html'
                            } else {
                                showMsg(data.msg);
                            }
                        }
                    });
                }

            },
        });


    });
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F09005/init',
        datatype: "json",
        postData: {
            stuidlistStr: stuidlist,
            status:status
        },
        colModel: getCols(),
        viewrecords: true,
        rowNum: -1,
        shrinkToFit: true,
        regional: 'jp',
        rownumbers: false,
        sortable: true,
        sortorder: 'desc',
        loadonce: true,
        multiselect: false,
        //pager: "#jqGridPager",
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
        gridComplete: function () {
        }
        ,
        loadComplete: function (data) {

            $("#jqGrid").setGridHeight($(window).height() * 0.7);
            $("#jqGrid").setGridWidth($(window).width() * 0.85 + 5);
            if (!data.code || data.code == 0) {
            } else {
                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
        }
    });
});

function getCols(){
    return [
        {label: '組織名', name: 'orgCd', index: 'orgCd', width: $(window).width() * 0.15, sortable: false, align: "center"},
        {label: '生徒ID', name: 'stuId', index: 'stuId', width: $(window).width() * 0.15, sortable: false, align: "center"},
        {
            label: '生徒名',
            name: 'stuNm',
            index: 'stuNm',
            width: $(window).width() * 0.15,
            sortable: false,
            align: "center"
        },
        {
            label: '学年',
            name: 'schyDiv',
            index: 'schyDiv',
            width: $(window).width() * 0.15,
            sortable: false,
            align: "center"
        },
        {
            label: 'ステータス', name: 'status', index: 'status', width: $(window).width() * 0.1, sortable: false, align: "center",
            formatter(cell, option, object) {
                if (status == "0")
                    return "登校 ";
                else if (status == "1")
                    return "下校";
                else
                    return "";
            }
        },

        {
            label: '時間', name: 'time', index: 'time',

            width: $(window).width() * 0.15,
            sortable: false,
            fixed: true,
            align: "center",
            formatter(cell, option, object) {

                return time;
            }
        },
    ];
}
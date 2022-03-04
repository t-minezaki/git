var param = getParam();

//画面．検索条件．組織ＩＤ
var orgId = $("input[name='orgId']").val();
//画面．検索条件．掲示板ID
var id = $("input[name='id']").val();
//画面．検索条件．件名
var noticeTitle = $("input[name='noticeTitle']").val();
//画面．検索条件．掲載開始日時
var pubPlanStartDt = $("input[name='pubPlanStartDt']").val();
//画面．検索条件．掲載終了日時
var pubPlanEndDt = $("input[name='pubPlanEndDt']").val();

var times = 0;

var vm = new Vue({
    el: '#app',
    data: {
        mstNoticeEntity: [],
        orgNm: "",
        pubPlanStartDt: "",
        pubPlanEndDt: ""
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/manager/F05005/init',
                type: 'get',
                data: {
                    noticeId: param.id
                },
                datatype: 'json',
                success: function (data) {
                    if (data.code === 0) {
                        if (data.mstNoticeEntity) {
                            vm.mstNoticeEntity = data.mstNoticeEntity;
                        }
                        if (data.orgNm) {
                            vm.orgNm = data.orgNm;
                        }
                        if (data.pubPlanStartDt) {
                            vm.pubPlanStartDt = data.pubPlanStartDt;
                        }
                        if (data.pubPlanEndDt) {
                            vm.pubPlanEndDt = data.pubPlanEndDt;
                        }
                        getErrorData(data.pubPlanStartDt,data.pubPlanEndDt,param.id);
                        loadJqGrid();
                    }
                }
            })
        },
        bakBtn: function () {
            window.location.href = "F05001.html";
        }
    }
});

function toF05006(orgId, readFlg) {
    window.location.href = "../pop/F05006.html?orgId=" + orgId + "&readFlg=" + readFlg + "&noticeId=" + param.id, 'no';
}
function toF40026(orgId, msgTypeDiv,msgType) {
    window.location.href = "../pop/F40026.html?orgId=" + orgId + "&msgTypeDiv=" + msgTypeDiv + "&noticeId=" + param.id + "&msgType=" + msgType, 'no';
}
function toF08020(openFlg,orgId,orgNm, readDiv) {
    window.location.href = "../manager/F08020.html?orgId=" + orgId + "&openDiv=" + openFlg + "&orgNm=" + orgNm + "&id=" + param.id + "&readDiv=" + readDiv + "&msgType=" + 0;
}

function loadJqGrid() {
    var srcHeight = $(window).height() - $(window).width() * 0.235 - 62;
    var width = $(window).width() * 0.99;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F05005/select',
            datatype: "json",
            postData: {
                orgId: vm.mstNoticeEntity.orgId,
                noticeId: vm.mstNoticeEntity.id
            },
            colModel: [
                {
                    label: '配信先組織', name: 'orgId', index: 'orgId', width: 80, key: true, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return object.orgId + "  " + object.orgNm;
                    }
                },
                {
                    label: '配信総件数', name: 'allCount', index: 'allCount', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<a onclick=toF08020('','" + object.orgId + "','" + object.orgId + "','')>" + cell + "</a>";
                    }
                },
                {
                    label: '通知プッシュ失敗件数', name: 'errorDataList', index: 'errorDataList', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<a onclick=toF40026('" + object.orgId + "',1,0)>" + cell + "</a>";
                    }
                },
                {
                    label: '既読件数', name: 'readCount', index: 'readCount', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<a onclick=toF08020('','" + object.orgId + "','','1')>" + cell + "</a>";
                    }
                },
                {
                    label: '未読件数', name: 'notReadCount', index: 'notReadCount', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return "<a onclick=toF08020('','" + object.orgId + "','','0')>" + cell + "</a>";
                    }
                },
                {
                    /* 2020/12/17 V9.0 cuikailin modify start */
                    label: '未確認件数', name: 'notOpenedCount', index: 'notOpenedCount', width: 80, sortable: false, align: "center",
                    /* 2020/12/17 V9.0 cuikailin modify end */
                    formatter(cell, option, object) {
                        return "<a onclick=toF08020(0,'" + object.orgId + "','" + object.orgNm + "','')>" + cell + "</a>";
                    }
                },
                {
                    /* 2020/12/17 V9.0 cuikailin modify start */
                    label: '確認済件数', name: 'openedCount', index: 'openedCount', width: 80, sortable: false, align: "center",
                    /* 2020/12/17 V9.0 cuikailin modify end */
                    formatter(cell, option, object) {
                        return "<a onclick=toF08020(1,'" + object.orgId + "','" + object.orgNm + "','')>" + cell + "</a>";
                    }
                }
            ],
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
                rows: "limit",
                order: "order"
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
                $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_readCount"]').css("line-height","4vh");
                $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_notReadCount"]').css("line-height","4vh");
                $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_errorDataList"]').css("line-height","4vh");
                if (data.code === 0) {
                } else {
                    showMsg(data.msg);
                }
            }
        }
    );
}
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
    updated: function () {
        loadJqGrid();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/manager/F04006/init',
                type: 'get',
                data: {
                    noticeId: param.noticeId
                },
                datatype: 'json',
                success: function (da) {
                    if (da.code === 0) {
                        if (da.mstNoticeEntity) {
                            vm.mstNoticeEntity = da.mstNoticeEntity;
                        }
                        if (da.orgNm) {
                            vm.orgNm = da.orgNm;
                        }
                        if (da.pubPlanStartDt) {
                            vm.pubPlanStartDt = da.pubPlanStartDt;
                        }
                        if (da.pubPlanEndDt) {
                            vm.pubPlanEndDt = da.pubPlanEndDt;
                        }
                    }
                }
            })
        },
        bakBtn: function () {
            window.location.href = "F04001.html";
        }
    }
});

function toF04007(orgId, readFlg) {
    layer.open({
        id: 'f04007',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F04007.html?orgId=" + orgId + "&readFlg=" + readFlg + "&noticeId=" + param.noticeId, 'no'],
        success: function (layero, index) {
        },
    })
}

function loadJqGrid() {
    var srcHeight = $(window).height() - $(window).width() * 0.235 - 62;
    var width = $(window).width() * 0.99;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F04006/select',
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
                {label: '配信総件数', name: 'allCount', index: 'allCount', width: 80, sortable: false, align: "center"},
                {
                    label: '既読件数', name: 'readCount', index: 'readCount', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return cell + "<button onclick=toF04007('" + object.orgId + "',1)>詳細</button>";
                    }
                },
                {
                    label: '未読件数', name: 'notReadCount', index: 'notReadCount', width: 80, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return cell + "<button onclick=toF04007('" + object.orgId + "',0)>詳細</button>";
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
                $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_readCount"]').css("text-indent","5.5vw").css("line-height","4vh");
                $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_notReadCount"]').css("text-indent","5.5vw").css("line-height","4vh");
                if (data.code === 0) {
                } else {
                    showMsg(data.msg);
                }
            }
        }
    );
}
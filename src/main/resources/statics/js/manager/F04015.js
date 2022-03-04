var param = getParam();
//引渡データ．ID
var noticeId = param.noticeId;
// var noticeId = decodeURI(param.noticeId);
var vm = new Vue({
    el: '#app',
    data: {
        mstNoticeEntity: [],
        start:'',
        end:''
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            var srcHeight = $(window).height() - $(window).width() * 0.235 - 62;
            var width = $(window).width() * 0.99;
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F04015/init',
                    datatype: "json",
                    postData: {
                        noticeId: noticeId
                    },
                    colModel: [
                        {
                            label: '配信先組織',
                            name: 'orgIdNm',
                            index: 'orgIdNm',
                            width: 110,
                            key: true,
                            sortable: false,
                            align: "center"
                        },
                        {
                            label: '組織Nm',
                            name: 'orgNm',
                            index: 'orgNm',
                            width: 110,
                            hidden:true,
                            key: true,
                            sortable: false,
                            align: "center"
                        },
                        {
                            label: '組織Id',
                            name: 'orgId',
                            index: 'orgId',
                            width: 110,
                            hidden:true,
                            key: true,
                            sortable: false,
                            align: "center"
                        },
                        {
                            label: '配信総件数',
                            name: 'countSend',
                            index: 'countSend',
                            width: 110,
                            sortable: false,
                            align: "center",
                            formatter(cell, option, object) {
                                return object.countSend == null ? 0 : object.countSend;
                            }
                        },
                        {
                            label: '既読件数',
                            name: 'countRead',
                            index: 'countRead',
                            width: 110,
                            sortable: false,
                            align: "center",
                            formatter(cell, option, object) {
                                var a = object.countRead == null ? 0 : object.countRead;
                                var b = object.orgId;
                                var c = object.orgNm;
                               return "<a onclick=toF04016('"+b + "','"+ '1' +"')>"+ a + "</a>";
                            }
                        },
                        {
                            label: '未読件数',
                            name: 'countNotRead',
                            index: 'countNotRead',
                            width: 110,
                            sortable: false,
                            align: "center",
                            formatter(cell, option, object) {
                                var b = object.orgId;
                                var c = object.orgNm;
                                var a = object.countNotRead == null ? 0 : object.countNotRead;
                                return "<a onclick=toF04016('"+b + "','"+ '0' + "')>" + a + "</a>"
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
                        // $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_countRead"]').css("text-indent", "5.5vw").css("line-height","4vh");
                        // $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_countNotRead"]').css("text-indent", "5.5vw").css("line-height","4vh");
                        if (data.code === 0) {
                            vm.mstNoticeEntity = data.mstNoticeEntity;
                            vm.start = data.mstNoticeEntity.pubPlanStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/,'$1/$2/$3 $4:$5');
                            vm.end = data.mstNoticeEntity.pubPlanEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/,'$1/$2/$3 $4:$5');
                            if (data.orgNm) {
                                $("#orgNm").text(data.orgNm);
                            }
                            if (data.sOrgId) {
                                $("#sOrgId").text(data.sOrgId);
                            }
                        }
                        else {
                            showMsg(data.msg);
                        }
                    }
                }
            );
        },
        backBtn: function () {
            window.location.href = "./F04011.html";
        }
    }
})
function toF04016(orgId,rsd) {
    window.location.href = "../pop/F04016.html?orgId=" + orgId + "&rsd=" + rsd + "&noticeId=" + param.noticeId;
    // layer.open({
    //     id: 'f04016',
    //     type: 2,
    //     title: false,
    //     shade: 0.1,
    //     closeBtn: 0,
    //     shadeClose: false,
    //     area: ['60%', '65%'],
    //     fixed: true,
    //     resize: false,
    //     content: ["../pop/F04016.html?orgId=" + orgId + "&rsd=" + rsd + "&noticeId=" + param.noticeId, 'no'],
    //     success: function (layero, index) {
    //     },
    // })
}



var params = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        mstMessageEntity: [],
        start:'',
        end:''
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/manager/F21065/init',
                type: 'get',
                data: {
                    messageId: params.id
                },
                datatype: 'json',
                success: function (data) {
                    if (data.code === 0) {
                        if (data.mstNoticeEntity) {
                            vm.mstNoticeEntity = data.mstNoticeEntity;
                        }
                        if (data.sOrgId) {
                            vm.sOrgId = data.sOrgId;
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
                        getErrorData(data.pubPlanStartDt,data.pubPlanEndDt,params.id);
                        loadJqGrid();
                    }
                }
            })

        },
        backBtn: function () {
            window.location.href = "./F21062.html";
        }
    }
});
function loadJqGrid(){
    var srcHeight = $(window).height() - $(window).width() * 0.235 - 62;
    var width = $(window).width() * 0.99;
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F21065/select',
        datatype: "json",
        postData: {
            messageId: params.id
        },
        colModel: [
            {
                label: '配信先組織',
                name: 'orgIdNm',
                index: 'orgIdNm',
                width: 95,
                key: true,
                sortable: false,
                align: "center"
            },
            {
                label: '組織Nm',
                name: 'orgNm',
                index: 'orgNm',
                width: 95,
                hidden: true,
                key: true,
                sortable: false,
                align: "center"
            },
            {
                label: '組織Id',
                name: 'orgId',
                index: 'orgId',
                width: 95,
                hidden: true,
                key: true,
                sortable: false,
                align: "center"
            },
            {
                label: '配信総件数',
                name: 'countSend',
                index: 'countSend',
                width: 95,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    //2020/11/4 zhangminghao modify start
                    let a = object.countSend == null ? 0 : object.countSend;
                    let b = object.orgId;
                    let c = object.orgNm;
                    return "<a onclick=toF08021('" + b + "','" + c + "','','')>" + a + "</a>";
                    //2020/11/4 zhangminghao modify end
                }
            },
            {
                label: '通知プッシュ失敗件数',
                name: 'errorDataList',
                index: 'errorDataList',
                width: 95,
                sortable: false,
                align: "center",
                // formatter(cell, option, object) {
                //     return "<a onclick=toF40026('" + object.orgId + "',0)>" + cell + "</a>";
                // }
                formatter(cell, option, object) {
                    return "<a onclick=toF40026('" + object.orgId + "',14,2)>" + cell + "</a>";
                }
            },

            {
                label: '既読件数',
                name: 'countRead',
                index: 'countRead',
                width: 95,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    var a = object.countRead == null ? 0 : object.countRead;
                    var b = object.orgId;
                    // 2020/11/4 zhangminghao modify start
                    let c = object.orgNm;
                    return "<a onclick=toF08021('" + b + "','" + c + "','','1')>" + a + "</a>";
                    // 2020/11/4 zhangminghao modify end
                }
            },
            {
                label: '未読件数',
                name: 'countNotRead',
                index: 'countNotRead',
                width: 95,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    var a = object.countNotRead == null ? 0 : object.countNotRead;
                    var b = object.orgId;
                    // 2020/11/4 zhangminghao modify start
                    let c = object.orgNm;
                    return "<a onclick=toF08021('" + b + "','" + c + "','','0')>" + a + "</a>";
                    // 2020/11/4 zhangminghao modify end
                }
            },
            {
                label: '未確認件数',
                name: 'notOpened',
                index: 'notOpened',
                width: 95,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    let a = object.notOpened == null ? 0 : object.notOpened;
                    let b = object.orgId;
                    let c = object.orgNm;
                    return "<a onclick=toF08021('" + b + "','" + c + "','0','')>" + a + "</a>";
                }
            },
            // 2020/11/4 zhangminghao modify start
            {
                label: '確認済件数',
                name: 'opened',
                index: 'opened',
                width: 95,
                sortable: false,
                align: "center",
                formatter(cell, option, object) {
                    let a = object.opened == null ? 0 : object.opened;
                    let b = object.orgId;
                    let c = object.orgNm;
                    return "<a onclick=toF08021('" + b + "','" + c + "','1')>" + a + "</a>";
                }
            }
            // 2020/11/4 zhangminghao modify end
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
        },
        loadComplete: function (data) {
            // $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_countRead"]').css("text-indent", "6.5vw");
            // $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_countNotRead"]').css("text-indent", "6.5vw");
            if (data.code === 0) {
                vm.mstMessageEntity = data.mstMessageEntity;
                vm.start = data.mstMessageEntity.pubPlanStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3 $4:$5');
                vm.end = data.mstMessageEntity.pubPlanEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3 $4:$5');
            } else {
                showMsg(data.msg);
            }
        }
})
}
function toF40026(orgId,msgTypeDiv,msgType) {
    window.location.href="../pop/F40026.html?orgId="  + orgId + "&msgTypeDiv=" + msgTypeDiv + "&noticeId=" + params.id + "&msgType=" + msgType;
    // layer.open({
    //     id: 'f40026',
    //     type: 2,
    //     title: false,
    //     shade: 0.1,
    //     closeBtn: 0,
    //     shadeClose: false,
    //     area: ['60%', '65%'],
    //     fixed: true,
    //     resize: false,
    //     content: ["../pop/F40026.html?orgId="  + orgId + "&msgTypeDiv=" + msgTypeDiv + "&noticeId=" + params.id + "&msgType=" + msgType, 'no'],
    //     success: function (layero, index) {
    //     }
    // })
}
function toF21066(orgId,rsd) {
    layer.open({
        id: 'f04016',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['60%', '65%'],
        fixed: true,
        resize: false,
        content: ["../pop/F21066.html?orgId=" + orgId + "&rsd=" + rsd + "&id=" + params.id, 'no'],
        success: function (layero, index) {
        }
    })
}
// 2020/11/4 zhangminghao modify start
function toF08021(orgId, orgNm, openDiv, readDiv) {
    window.location.href = "../manager/F08021.html?orgId=" + orgId + "&orgNm=" + orgNm + "&openDiv=" + openDiv+ "&id=" + params.id + "&readDiv=" + readDiv +  "&msgType=" + 2;
}
// 2020/11/4 zhangminghao modify end



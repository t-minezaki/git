var param=getParam();
var vue = new Vue({
    el: "#page",
    data: {
        info: '',
        failedUserList: [],
        objSum:'',
        title:'',
        orgId: '',
        orgNm: '',
        messageTypeDiv: '',
        roleDiv: '3',
        deviceIdList:[],
        message: '',
        roleNm:'',
        msgType:param.msgType
    },
    mounted: function () {
        //2021/02/02 liyuhuan add start
        if (param.msgType ==="0" ) {
            this.title = "お知らせ管理";
        }else if (param.msgType ==="1") {
            this.title = "イベント管理";
        }else {
            this.title = "メッセージ管理";
        }
        //2021/02/02 liyuhuan add end
        this.showInfo();
        this.showList();
        },
    methods: {
        showInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F40026/init',
                dataType: 'json',
                type: 'GET',
                data: {
                    msgId:param.noticeId,
                    messageTypeDiv:param.msgType
                },
                async: true,
                success: function (data) {
                    if (data.code == 0) {
                        if (data.info) {
                            vue.info = data.info
                        }
                        if (data.orgId) {
                            vue.orgId = data.orgId
                        }
                        if (data.orgNm) {
                            vue.orgNm = data.orgNm
                        }
                        if(data.messageTypeDiv){
                            vue.messageTypeDiv=data.messageTypeDiv
                        }

                    }
                }
            });

            },
        showList: function () {
            var width = $(window).width()*0.91;
            var srcHeight = $(window).height()*0.6;
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F40026/getFailedUserList',
                    datatype: "json",
                    postData: {
                        msgTypeDiv:param.msgTypeDiv,
                        msgId:param.noticeId,
                        orgId:param.orgId,
                        roleDel:param.roleDiv
                    },
                    colModel: [
                        {label:  '',id:'id', name: 'id', index: 'id', width: 70, sortable: false, key: true,align: "center",hidden:true},
                        {label:  '保護者ID', name: 'afterUsrId', index: 'afterUsrId', width: 70, sortable: false, align: "center"},
                        {label:  '保護者名', name: 'userName', index: 'userName', width: 70, sortable: false, align: "center"},
                        {label:  '生徒ID', name: 'stuAfterId', index: 'stuAfterId', width: 70, sortable: false, align: "center"},
                        {label:  '生徒名', name: 'stuNm', index: 'stuNm', width: 70, sortable: false, align: "center"},
                        // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang START
                        // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang eND
                        {label:  '', name: 'eventPushError', index: 'eventPushError', width: 70, sortable: false, align: "center",
                            hidden:param.msgTypeDiv !== '3' && param.msgTypeDiv !== '7',
                            formatter(){
                                return param.msgTypeDiv === '3' ? 'X' : ''
                            }},
                        {label:  '', name: 'unreadPushError', index: 'unreadPushError', width: 70, sortable: false, align: "center",
                            hidden:param.msgTypeDiv !== '3' && param.msgTypeDiv !== '7',
                            formatter(){
                                return param.msgTypeDiv === '7' ? 'X' : ''
                            }}
                    ],
                    viewrecords: true,
                    height: srcHeight,
                    width:width,
                    rowNum: 30,
                    rowList: [10, 30, 50],
                    rownumbers: false,
                    rownumWidth: 25,
                    autowidth: false,
                    multiselect: true,
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
                    },
                    gridComplete: function() {
                        var rowIds = $("#jqGrid").jqGrid('getDataIDs');
                        for (var k = 0; k < rowIds.length; k++) {
                            var curRowData = jQuery("#jqGrid").jqGrid('getRowData', rowIds[k]);
                            var curChk = $("#" + rowIds[k] + "").find(":checkbox");
                            curChk.attr('name', 'checkboxname');
                            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang START
                            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang END
                            curChk.attr('pushErrDatId', curRowData['id']);
                        }
                    },
                loadComplete: function (data) {
                        vue.objSum = data.page.totalCount;
                    // メッセージ区分が 3 または 7 の場合, 非表示の列を表示
                    if (param.msgTypeDiv === '3' || param.msgTypeDiv === '7'){
                        $("#jqgh_jqGrid_eventPushError").html('イベント通知プッシュ失敗');
                        $("#jqgh_jqGrid_unreadPushError").html('未読再送通知プッシュ失敗');
                    }
                    if (vue.objSum != 0) {
                        if(data.deviceIdList) {
                            vue.deviceIdList=data.deviceIdList;
                        }
                        if (data.page.list.length > 0) {
                            vue.roleDiv = data.page.list[0].roleDiv.trim();
                        }
                    } else {
                        if(param.msgType=="0"){
                            showMsg($.format($.msg.MSGCOMN0017, 'お知らせ'));
                        }else if (param.msgType=="2") {
                            showMsg($.format($.msg.MSGCOMN0017, 'メッセージ'));
                        }else {
                            showMsg($.format($.msg.MSGCOMN0017, 'イベント'));
                        }
                    }

                },
                    beforeSelectRow: function (rowid, e) {
                        var $myGrid = $(this),
                            i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
                            cm = $myGrid.jqGrid('getGridParam','colModel');
                        return (cm[i].name ==='cb');
                    }
            }
            )
        },

    }
});
function deliver(){
    var userIdList = [];
    var pushErrDatIdList = [];
    $("input[name='checkboxname']").each(function () {
        if ($(this).is(":checked")){
            userIdList.push($(this).val());
            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang START
            pushErrDatIdList.push(Number($(this).attr("pushErrDatId")));
            // modify at 2021/09/17 for V9.02 UT-0029 by NWT yang END
        }
    });
    if(userIdList.length === 0){
        var index = layer.confirm($.format($.msg.MSGCOMN0138, "再送信対象"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: [ '確認'],
            btn1: function () {
                layer.close(index);
                return;
            }
        })
    }else {
        var sendTime = new Date(vue.info.pubStartDt);
        $.ajax({
            url: ctxPath + '/manager/F40026/sendMessage',
            dataType: 'json',
            type: 'GET',
            data: {
                userIdListStr:JSON.stringify(userIdList),
                pushErrDatIdList:JSON.stringify(pushErrDatIdList),
                msgId:vue.info.id,
                imgUrl:vue.info.titleImgPath == null?"":vue.info.titleImgPath,
                sendTime:sendTime.getTime().toString(),
                title:vue.info.title,
                messageTypeDiv:param.msgTypeDiv
            },
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    if (data.info) {
                        vue.info = data.info
                    }
                    if (data.orgId) {
                        vue.orgId = data.orgId
                    }
                    if (data.orgNm) {
                        vue.orgNm = data.orgNm
                    }
                    if(data.messageTypeDiv){
                        vue.messageTypeDiv=data.messageTypeDiv
                    }
                    if(data.deviceList){
                        vue.deviceList=data.deviceList
                    }
                    if(data.message)
                    {
                        vue.message=data.message
                    }
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                }else {
                    parent.layer.alert(data.msg);
                }
            }
        });
    }
}
// $("#closeBtn").click(function () {
//     var index = parent.layer.getFrameIndex(window.name);
//     parent.layer.close(index);
// });

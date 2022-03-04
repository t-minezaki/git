var pageSize = 15;
var url = window.location.href;
var vm = new Vue({
    el: "#page",
    data: {
        noticeList: [],
        channelList: [],
        total: "",
        noticeCount: "",
        channelCount: "",
        orgId: ''
    },
    mounted: function () {
        this.showNotice();
    },
    methods: {
        showNotice: function () {
            $.ajax({
                url: ctxPath + '/guard/F30421/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (pageSize >= da.dataCount) {
                            $(".nextpage").css("display","none");
                        }
                        if (da.f30421Dtos) {
                            vm.noticeList = vm.noticeList.concat(da.f30421Dtos);
                            vm.channelList = [];
                            vm.noticeCount = da.f30421Dtos.length;
                            if (da.f30421Dtos.length == 0) {
                                layer.alert($.format($.msg.MSGCOMN0017, "お知らせ"));
                            }
                            $("#notice_tab").addClass("active1");
                            $("#channel_tab").removeClass("active1");
                        }
                        if (da.noticeCount > 0) {
                            vm.noticeCount = da.noticeCount;
                            $(".noticeCount").show();
                        }
                        if (da.channelCount > 0) {
                            vm.channelCount = da.channelCount;
                            $(".channelCount").show();
                        }
                        if (da.orgId){
                            vm.orgId = da.orgId;
                        }
                    }
                }
            });
        },
        pageChange: function () {
            pageSize = pageSize + 15;
            vm.showNotice();
        },
        toF30113: function (obj) {
            if (obj.eventReadingStsDiv === '0' || obj.noticeReadingStsDiv === '0'){
                $.ajax({
                    url: ctxPath + '/guard/F30421/sendMessage',
                    type: 'POST',
                    datatype: 'json',
                    data:{
                        msgId:obj.eventId === null?obj.noticeId:obj.eventId
                    },
                    success:function (data) {
                        vm.toDetailPage(obj);
                    }
                })
            }else {
                this.toDetailPage(obj);
            }
        },
        toDetailPage:function(obj){
            var url = '';
            if (obj.eventCd != null) {
                url = "F30402.html?eventId=" + obj.eventId;
            } else {
                if (obj.noticeTypeDiv == '4') {
                    url = "F30413.html?noticeId=" + obj.noticeId + "&deliverCd=" + obj.contents.split(",")[1] + "&guidReprId=" + obj.contents.split(",")[0];
                } else if (obj.noticeTypeDiv == '7') {
                    url = "F30423.html?noticeId=" + obj.noticeId;
                } else {
                    url = "F30113.html?noticeId=" + obj.noticeId;
                }
            }
            window.location.href = url;
        },
        show: function (flg) {
            if ("2" === flg) {
                return "display: block!important;";
            }
        },
        showStartDt: function (startDt) {
            if (startDt) {
                if (startDt.length > 19){
                    startDt = startDt.substring(0,19);
                }
                var nowDate = new Date();
                var startDate = new Date(startDt.replaceAll("-", "/"));
                if (nowDate.Format('yyyyMMdd') === startDate.Format('yyyyMMdd')){
                    return startDate.Format('HH:mm');
                }else {
                    return startDate.Format('M月d日')
                }
            } else {
                return ''
            }
        }
    }
});

window.addEventListener('pageshow', function(event) {
    if(window.localStorage.getItem('refresh') === 'true') {
        window.localStorage.removeItem('refresh');
        window.location.reload();
    }
});
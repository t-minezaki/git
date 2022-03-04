var page = 1;
var pageSize = 15;
var url = window.location.href;
var param = getParam();
var tabFlg = getParam().tabFlg;
var vm = new Vue({
    el: "#page",
    data: {
        showList: [],
        total: "",
        noticeCount: "",
        imgPath: "",
        channelCount:"",
        orgId: ''
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
        // $(function () {
        //     var webHeight = document.body.clientHeight;
        //     var contHeight = webHeight - 145;
        //     document.getElementById("content").style.height = contHeight + "px";
        // });
        if (page === 1 || page === 0) {
            $("#prev").addClass("gray");
        } else {
            $("#prev").removeClass("gray");
        }
        if (Math.ceil(vm.total / pageSize) === page) {
            $("#next").addClass("gray");
        } else {
            $("#next").removeClass("gray");
        }
        var cont = Math.ceil(vm.total / pageSize) + "　ページ中　" + page + "　 ページ目 ";
        $("#pageFoot").html(cont);
    },
    methods: {
        showData: function () {
            $(".insert").hide();
            $(".pageChange").show();
            $("#pageFoot").show();
            $.ajax({
                url: ctxPath + '/guard/F30112/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: 1,
                    url: url,
                    stuId: param.stuId
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.imgPath) {
                            vm.imgPath = da.imgPath;
                        }
                        if (da.showList) {
                            for (var i = 0, len = da.showList.length; i < len; i++){
                                da.showList[i].style = 'height:42px;transform:translate(-50%, 0);left:50%;position: relative';
                                (function (arg) {
                                    var img = new Image();
                                    img.onload = function () {
                                        if (img.width >= img.height) {
                                            da.showList[arg].style = 'height:42px;transform:translate(-50%, 0);left:50%;position: relative';
                                        } else {
                                            da.showList[arg].style = 'width:42px;transform:translate(0, -50%);top:50%;position: relative';
                                        }
                                    };
                                    img.src = da.showList[i].noticeImgPath;
                                })(i);
                            }
                            vm.showList = da.showList;
                            $("#notice_tab").addClass("active1");
                        }
                        if (da.total) {
                            vm.total = da.total;
                        }
                        if (da.total === 0) {
                            page = 0;
                        }
                        if (da.noticeCount > 0) {
                            vm.noticeCount = da.noticeCount;
                            $(".count").prop('style', 'display: inline-block');
                        }
                        if (da.channelCount > 0) {
                            vm.channelCount = da.channelCount;
                            $(".count1").prop('style', 'display: inline-block');
                        }
                        if (da.orgId){
                            vm.orgId = da.orgId;
                        }
                    }
                }
            });
        },
        // カメリオＡＰＩを呼び出し、次の３０件を追加し再表示する。
        pageChange: function (pageFlg) {
            var pg;
            if (pageFlg === 0) {
                if (page === 1 || page === 0) {
                    return;
                } else {
                    pg = page - 1;
                }
            } else {
                if (page === Math.ceil(vm.total / pageSize)) {
                    return;
                } else {
                    pg = page + 1;
                }
            }
            $.ajax({
                url: ctxPath + '/guard/F30112/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: pg
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.showList) {
                            for (var i = 0, len = da.showList.length; i < len; i++){
                                da.showList[i].style = 'height:42px;transform:translate(-50%, 0);left:50%;position: relative';
                                (function (arg) {
                                    var img = new Image();
                                    img.onload = function () {
                                        if (img.width >= img.height) {
                                            da.showList[arg].style = 'height:42px;transform:translate(-50%, 0);left:50%;position: relative';
                                        } else {
                                            da.showList[arg].style = 'width:42px;transform:translate(0, -50%);top:50%;position: relative';
                                        }
                                    };
                                    img.src = da.showList[i].noticeImgPath;
                                })(i);
                            }
                            vm.showList = da.showList;
                        }
                        if (pageFlg === 0) {
                            if (page > 1) {
                                page -= 1;
                            }
                        } else {
                            if (page < Math.ceil(vm.total / pageSize)) {
                                page += 1;
                            }
                        }
                    }
                }
            });
        },
        toDetail: function (obj) {
            if (obj.readStsDiv === '0'){
                $.ajax({
                    url: ctxPath + '/guard/F30421/sendMessage',
                    type: 'POST',
                    datatype: 'json',
                    data:{
                        msgId:obj.id
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
            switch (obj.type) {
                case 'message':
                    url = ctxPath + '/guard/F30424.html?messageId=' + obj.id;
                    break;
                case 'notice':
                    if (obj.noticeTypeDiv === '4'){
                        url = ctxPath + '/guard/F30413.html?noticeId=' + obj.id + "&deliverCd=" + obj.noticeContent.split(",")[1] + "&guidReprId=" + obj.noticeContent.split(",")[0];
                    }
                    else if (obj.noticeTypeDiv === '7'){
                        url = ctxPath + '/guard/F30423.html?noticeId=' + obj.id;
                    }
                    else{
                        url = ctxPath + '/guard/F30113.html?noticeId=' + obj.id;
                    }

                    break;
                case 'event':
                    url = ctxPath + '/guard/F30402.html?eventId=' + obj.id;
                    break;
                default:
            }
            window.location.href = url;
        },
        show: function (flg) {
            if ("2" === flg) {
                return "display: block!important;";
            }
        },
        isCorrspd: function (flg) {
            if (flg.corrspdSts) {
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
//1ページに表示されるチャネルの数
var pageSize = 15;
//現在のページのURL
var url = window.location.href;
//vue
var vm = new Vue({
    el: "#page",
    data: {
        //1ページのチャネルデータ
        showList: [],
        //公開期間中のチャンネルの総数
        total: 0,
        //お知らせ未読カウント
        count: "",
        //チャネル未読カウント
        count1: "",
        //デフォルトのアイコンURL
        imgPath: "",
        orgAddId:''
    },
    mounted: function () {
        $.ajax({
            url: ctxPath + '/common/F40004/getOrg',
            type: 'GET',
            dataType: "json",
            success: function (data){
                vm.orgAddId = data.orgIdAdd;
            }
        });
        //初期化
        this.getNews();
    },
    methods: {
        toF30421: function () {
            if (window.getCookie('brandcd') === vm.orgAddId){
                window.location.href = './F30421.html'
            } else {
                window.location.href = './F30112.html'
            }

        },
        getNews: function () {
            $.ajax({
                url: ctxPath + '/guard/F30419/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    url: url
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (pageSize >= da.dataCount) {
                            $(".nextpage").css("display","none");
                        }
                        if (da.showList) {
                            for (var i = 0, len = da.showList.length; i < len; i++){
                                da.showList[i].noticeTitle = decodeURIComponent(da.showList[i].noticeTitle);
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
                            vm.showList = vm.showList.concat(da.showList);
                        }
                        if (da.total) {
                            vm.total = da.total;
                        }
                        if (vm.total == 0) {
                            page = 0;
                        }
                        if (da.count > 0) {
                            vm.count = da.count;
                            $(".count").show();
                        }
                        if (da.count1 > 0) {
                            vm.count1 = da.count1;
                            $(".count1").show();
                        }
                        if (da.imgPath){
                            vm.imgPath = da.imgPath;
                        }
                    } else {
                        idx = layer.confirm(da.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            yes: function () {
                                layer.close(idx);
                            }
                        });
                    }
                }
            });
        },
        pageChange: function () {
            pageSize = pageSize + 15;
            vm.getNews();
        },
        toF30420: function (id) {
            window.location.href = "F30420.html?noticeId=" + id;
        },
        show: function (flg) {
            if ("2" === flg) {
                return "display: block!important;";
            }
        },
        getStatus: function (flg) {
            if (flg == '0'){
                return '確認中'
            }else{
                return '確認済'
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
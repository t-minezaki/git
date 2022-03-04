var page = 1;
var pageSize = 15;
var vm = new Vue({
    el: "#page",
    data: {
        noticeList: [],
        total: "",
        numCount: ""
    },
    mounted: function () {
        this.showMessage();
    },
    methods: {
        showMessage: function () {
            $.ajax({
                url: ctxPath + '/student/F11010/init',
                type: 'get',
                datatype: 'json',
                data: {
                    limit: pageSize,
                    page: page
                },
                success: function (da) {
                    if (da.code === 0) {
                        if (da.f11010Dtos) {
                            vm.noticeList = vm.noticeList.concat(da.f11010Dtos);
                            vm.numCount = da.numCount;
                            if (da.f11010Dtos.length == 0) {
                                layer.alert($.format($.msg.MSGCOMN0017, "メッセージ"));
                            }
                        }
                        if (da.count){
                            vm.total = da.count;
                            if (vm.total <= pageSize){
                                $(".pageChange").css("display","none");
                            }else {
                                $(".pageChange").css("display","block");
                            }
                        }
                    }
                }
            });
        },
        toDetailPage: function (obj) {
            if (obj.readingStsDiv === "0"){
                $.ajax({
                    url: ctxPath + '/student/F11010/sendMessage',
                    type: 'POST',
                    datatype: 'json',
                    data:{
                        typeDiv:obj.typeDiv,
                        id:obj.id
                    },
                    success:function (data) {
                        vm.toDetail(obj);
                    }
                })
            }else {
                this.toDetail(obj);
            }
        },
        toDetail: function (obj){
            switch (obj.typeDiv) {
                case '1':
                    window.location.href='F11011.html?messageId='+obj.id;
                    break;
                case '4':
                case '5':
                    window.location.href='F11020.html?messageId='+obj.id;
                    break;
                default:
                    window.location.href='F11013.html?eventId='+obj.id;
            }
        },
        show: function (flg) {
            if ("2" === flg) {
                return "display: block!important;";
            }
        },
        getStartDt: function (time) {
            if (time) {
                return time.replace(/(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/, '$1/$2/$3 $4:$5');
            } else {
                return ''
            }
        },
        pageChange: function () {
            pageSize = pageSize + 15;
            this.showMessage();
        }
    }
});
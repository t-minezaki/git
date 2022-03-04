var param = getParam();
var vm = new Vue({
    el:'#app',
    data: {
        detail:[],
        title:''
    },
    mounted:function(){
        this.getInit()
    },
    methods:{
        getInit:function() {
            $.ajax(
                {
                    url: ctxPath + '/manager/F21010/getDetail',
                    data: {
                        guidReprDeliverCd: param.guidReprDeliverCd,
                        readFlg: param.readFlg
                    },
                    success:function (data) {
                        vm.detail = data.detail;
                        if (param.readFlg) {
                            if (param.readFlg === '0'){
                                vm.title = '未読';
                            } else if (param.readFlg === '1'){
                                vm.title = '既読';
                            } else {
                                vm.title = '配信';
                            }
                        }
                    }
                }
            );
        }
    }
});

function cancelFn() {
    window.location.href = "../manager/F21010.html"
}
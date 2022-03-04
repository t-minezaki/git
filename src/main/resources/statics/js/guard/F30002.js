var vm = new Vue({
    el: '.content',
    data: {
        stuList: [],
        cou: 0,
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
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/guard/F30002/init',
                type: 'get',
                dataType: 'json',
                data: {},
                success: function (data) {
                    if (data.stuList) {
                        vm.stuList = data.stuList;
                    }
                }
            })
        },
        toF30101: function (stuId, orgId) {
            $.ajax({
                url: ctxPath + '/guard/F30002/save',
                type: 'get',
                dataType: 'json',
                data: {
                    stuId: stuId,
                    orgId: orgId
                },
                success: function (data) {
                    if (data.code == 0) {
                        if (window.getCookie('brandcd') === vm.orgAddId) {
                            window.location.href = './F30421.html'
                        } else {
                            //GKGC以外の保護者画面トップページ「ホーム」に修正して下さい。2020/11/09 Modify--yang
                            window.location.href = "./F30112.html";
                        }
                    } else {
                        layer.alert(data.msg);
                    }
                }
            })

        },
        displayCount: function (data) {
            if (data > 0) {
                return false;
            } else {
                return 'display: none;'
            }
        }
    }
});
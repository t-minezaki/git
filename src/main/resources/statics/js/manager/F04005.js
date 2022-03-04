var param = getParam();
var vm = new Vue({
    el: ".page",
    data: {
        mstOrgEntity: '',
        mstNoticeEntity: '',
        noticeCont: '',
        stuList: '',
        listLength: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F04005/init',
                type: 'GET',
                data: {
                    id: param.id
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.mstOrgEntity) {
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    if (data.mstNoticeEntity) {
                        vm.mstNoticeEntity = data.mstNoticeEntity;
                        vm.noticeCont = vm.mstNoticeEntity.noticeCont;
                        if (vm.noticeCont==null){
                            $("#noticeCont").html("");
                        } else {
                            $("#noticeCont").html(decodeURIComponent(vm.noticeCont));
                            //背景色
                            uParse('#noticeCont', {
                                rootPath: '../plugins/ueditor-1.4.3.3'
                            })
                        }
                    }
                    if (data.stuList) {
                        vm.stuList = data.stuList;
                    }
                    if (data.listLength) {
                        vm.listLength = data.listLength;
                    }
                }
            })
        }
    }
});

//戻るボタン押下
$("#backBtn").click(function () {
    window.location.href = './F04001.html'
});
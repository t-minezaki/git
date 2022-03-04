var index = parent.layer.getFrameIndex(window.name);
var params = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        messageTitle: '',
        askItemName: '',
        talkItemName: '',
        askAndTalkList: [],
        askDisplayFlg: false,
        talkDisplayFlg: false,
        noteCont: '',
        subeContFlg: false
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            var messageId = params.messageId;
            $.ajax({
                url: ctxPath + '/manager/F21041/getPop',
                data: {
                    id: params.id
                },
                type: 'get',
                datatype: 'json',
                success: function (data) {
                    /* 2021/1/18 UT-176 cuikailin modify start */
                    if (data.code == 0) {
                        if (data.f21041Dtos && data.f21041Dtos.length > 0 ) {
                            vm.askAndTalkList = data.f21041Dtos;
                            if (vm.askAndTalkList[0] && vm.askAndTalkList[0].noteCont != null && vm.askAndTalkList[0].noteCont.trim() != "") {
                                vm.subeContFlg = true;
                                vm.noteCont = vm.askAndTalkList[0].noteCont;
                            }
                            for (var i = 0; i < vm.askAndTalkList.length; i++) {
                                if (vm.askAndTalkList[i]){
                                    if (vm.askItemName === '' && vm.askAndTalkList[i].itemTypeDiv === '0') {
                                        vm.askItemName = vm.askAndTalkList[i].codValue;
                                    }
                                    if (vm.talkItemName === '' && vm.askAndTalkList[i].itemTypeDiv === '1') {
                                        vm.talkItemName = vm.askAndTalkList[i].codValue;
                                    }
                                    if (!vm.askDisplayFlg && vm.askAndTalkList[i].itemTypeDiv === '1') {
                                        vm.askDisplayFlg = true;
                                    }
                                    if (!vm.talkDisplayFlg && vm.askAndTalkList[i].itemTypeDiv === '0') {
                                        vm.talkDisplayFlg = true;
                                    }
                                }
                            }
                            if (!vm.subeContFlg && !vm.askDisplayFlg && !vm.talkDisplayFlg){
                                showMsg($.format($.msg.MSGCOMN0017, "面談記録"))
                            }
                        }else {
                            showMsg($.format($.msg.MSGCOMN0017, "面談記録"))
                        }
                    }else {
                        showMsg(data.msg);
                    }
                    /* 2021/1/18 UT-176 cuikailin modify end */
                }
            });
        }
    }
});
/*

function btn() {
    parent.layer.close(index);
}*/

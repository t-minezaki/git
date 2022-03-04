var param = getParam();
var vm = new Vue({
    el: "#page",
    data: {
        f30424Dto: "",
        mstCodDEntities: [],
        talkRecordDEntityAskList: [],
        talkRecordDEntityTalkList: []
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/guard/F30424/init',
                type: 'get',
                datatype: 'json',
                data: {
                    messageId: param.messageId
                },
                success: function (data) {
                    if (data.code == 0) {
                        if (data.f30424Dto) {
                            vm.f30424Dto = data.f30424Dto;
                        }
                        if (data.mstCodDEntities) {
                            vm.mstCodDEntities = data.mstCodDEntities;
                        }
                        if (data.talkRecordDEntityAskList) {
                            vm.talkRecordDEntityAskList = data.talkRecordDEntityAskList;
                        }
                        if (data.talkRecordDEntityTalkList) {
                            vm.talkRecordDEntityTalkList = data.talkRecordDEntityTalkList;
                        }
                    } else {
                        layer.alert(data.msg);
                    }
                }
            });
        }
    }
});

//戻るボタン押下時
function back() {
    window.history.back();
}
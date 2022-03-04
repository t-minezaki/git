
var param = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        stuId: '',
        contentVal: '',
        lateTimeVal:'',
        dayVal:'',
        reasonVal:'',
        remarkVal:''
    },
    mounted: function () {
        this.setUp();
    },
    methods: {
        //初期化
        setUp: function () {
            this.stuId = decodeURI(param['stuId']);
            this.contentVal = decodeURI(param['contentVal']);
            this.lateTimeVal = decodeURI(param['lateTimeVal']);
            this.dayVal = decodeURI(param['dayVal']);
            this.reasonVal = decodeURI(param['reasonVal']);
            this.remarkVal = decodeURI(param['remarkVal']);
        }
    }
});
// 登録ボタン押下時
function submit() {
    $.ajax({
        url: ctxPath + '/guard/F30412/submit',
        type: 'GET',
        data: {
            stuId: vm.stuId,
            type: vm.contentVal == '時間変更'?0:1,
            lateTime: vm.lateTimeVal,
            lateDay: vm.dayVal,
            reason: vm.reasonVal,
            remark: vm.remarkVal
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                showMsg(data.msg);
            } else {
                // var idx = layer.confirm($.format($.msg.MSGCOMN0014, '遅刻・欠席連絡情報'), {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     yes: function () {
                //         layer.close(idx);
                //     }
                // })

            }
        }
    });
}
//戻るボタン押下時
function back(){
    var herf = 'F30411.html?stuId=' + vm.stuId + '&contentVal=' + vm.contentVal + '&lateTimeVal=' + vm.lateTimeVal
        + '&dayVal=' + vm.dayVal + '&reasonVal=' + vm.reasonVal + '&remarkVal=' + vm.remarkVal;
    window.location.href = herf;
}

function toNotice() {
    if (common_bottom.window.toNotice){
        common_bottom.window.toNotice();
    }
    // window.location.href = 'F30112.html'
}
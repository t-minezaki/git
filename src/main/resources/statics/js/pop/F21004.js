var param = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        guardName: '',
        content: '',
        reason: '',
        remark: '',
        reload: false
    },
    mounted: function () {
        this.setUp();
    },
    methods: {
        setUp: function () {
            if (param.guardName){
                this.guardName = decodeURIComponent(param.guardName);
            }else{
                this.guardName = decodeURIComponent(param.flnmNm) + ' ' + decodeURIComponent(param.flnmLnm);
            }

            $.ajax({
                url: ctxPath + '/pop/F21004/detail',
                type: 'GET',
                data: {
                    id: param.id
                },
                success: function (data) {
                    if (data.code == 0){
                        var detail = data.detail;
                        vm.content = detail.content;
                        vm.reason = detail.reason;
                        vm.remark = detail.remark;
                    }else {
                        var msg = parent.layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                parent.layer.close(msg);
                                back();
                            }
                        });
                    }
                }
            });

            setTargetControlHeight('remarkDiv', 'content', 'dayDiv', 'contentDiv', 'reasonDiv', 'btnDiv', 'line1', 'line3', 'line4', 'line5');
        }
    }
});

//戻るボタン押下時
function back() {
    if (vm.reload){
        parent.reload();
    }
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

// 登録ボタン押下時
function submit() {
    //対応済
    // if (param.corrspdSts == '3') {
    //     var msg = parent.layer.confirm($.msg.MSGCOMN0133, {
    //         skin: 'layui-layer-molv',
    //         title: '確認',
    //         closeBtn: 0,
    //         anim: -1,
    //         btn: ['確認'],
    //         btn1: function () {
    //             parent.layer.close(msg);
    //             back();
    //         }
    //     });
    // } else {
        //対応状態更新する
        $.ajax({
            url: ctxPath + '/pop/F21004/update',
            type: 'GET',
            data: {
                id: param.id
            },
            success: function () {
                if (param.guardName){
                    param.guardName = vm.guardName;
                    param.dateTime = decodeURIComponent(param.dateTime);
                    param.corrspdSts = '1';
                    if (parent.vm && parent.vm.e) {
                        $(parent.vm.e).attr('onclick', 'details(this, ' + JSON.stringify(param) + ')');
                    }
                }else{
                    vm.reload = true;
                }
            }
        });
        var msg = parent.layer.confirm($.msg.MSGCOMN0132, {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                parent.layer.close(msg);
                back();
            },
            btn2: function () {
                //お知らせ
                $.ajax({
                    url: ctxPath + '/pop/F21004/notice',
                    type: 'GET',
                    data: {
                        id: param.id,
                        guardName: param.guardName
                    },
                    success : function (data) {
                        parent.layer.close(msg);
                        back();
                    }
                });
            }
        });
    // }
}
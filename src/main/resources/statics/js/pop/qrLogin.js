var qrUsers = parent.qrUsers;
var aftUsrIds = parent.aftUsrIds;
var vm = new Vue({
    el: '#app',
    data: {
        orgIdList: [],
        aftUsrIds: [],
        checkFlg:false
    },
    mounted: function () {
        this.init();
    },
    updated: function () {
        $(".radio").eq(0).attr("checked", true);
        $("input[name=org]").click(function () {
            if ($(this).hasClass("others")) {
                $(".text").removeAttr("disabled");
                vm.checkFlg = true;
            } else {
                $(".text").attr("disabled", true);
                vm.checkFlg = false;
            }
        });
    },
    methods: {
        init: function () {
            this.qrUsers = qrUsers;
            this.aftUsrIds = aftUsrIds;
        },
        //「キャンセル」押下時、該当画面（POP）が閉じる。
        cancelFn: function () {
            orgIdList = [];
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        //「登録」押下時、ＱＲ統合処理を行う。
        submit: function () {
            var afterUsrId = '';
            //　その他ラジオボタンが選択された場合、且つ　その他内容が未入力していない場合、
            if ($("input[type='radio']:checked").hasClass("others")) {
                if ($(".text").val() == '' || $(".text").val() == null) {
                    var index = layer.confirm($.format($.msg.MSGCOMD0001, "ＱＲ統合ユーザー"), {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(index);
                        }
                    });
                    return;
                } else {
                    afterUsrId = $(".text").val();
                    vm.checkFlg = true;
                }
            } else {
                afterUsrId = $("input[type='radio']:checked").val();
            }
            //確認メッセージはPOP画面に表示する。
            var index = layer.confirm($.format($.msg.MSGCOMN0021, "ＱＲ統合ユーザー"), {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['戻る', '確認'],
                //「戻る」ボタン押下の場合
                btn1: function () {
                    //処理を中止する。
                    layer.close(index);
                },
                // 「確認」ボタン押下の場合
                btn2: function () {
                    //処理を継続する。
                    $.ajax({
                        url: ctxPath + '/manager/F00009/submit',
                        type: 'GET',
                        data: {
                            qrUsersStr: JSON.stringify(qrUsers),
                            afterUsrId: afterUsrId,
                            checkFlg:vm.checkFlg
                        },
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                            } else {
                                // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                        var index = parent.layer.getFrameIndex(window.name);
                                        parent.layer.close(index);
                                        parent.location.reload();
                                    // }
                                // });
                            }
                        }
                    });
                }
            });

        }
    }
});

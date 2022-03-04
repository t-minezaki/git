var param = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        guardEventApplySts: '',
        msgTop: '',
        msgBottom: '',
        answerResult: {}
    },
    mounted: function () {
        this.getInfo();
        // 2020/12/3 huangxinliang modify start
        $('.image-container').click(function () {
            var img = $(".image-container").find('img')[0];
            var index = $(img).attr('id').substring(5);
            $(".image-container").fadeOut();
            $(img).addClass('photo-image');
            $('#photoContainer' + index).html($(img));
        });
        // 2020/12/3 huangxinliang modify end
    },
    methods: {
        //初期化
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/guard/F30410/init',
                type: 'GET',
                data: {
                    eventId: param.eventId,
                    stuId: param.stuId,
                    guardId: param.guardId,
                    /* 2020/12/21 V9.0 cuikailin add start */
                    refType: param.refType
                    /* 2020/12/21 V9.0 cuikailin add end */
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        vm.guardEventApplySts = data.guardEventApplySts;
                        vm.msgTop = data.msg.split("。")[0];
                        vm.msgBottom = data.msg.split("。")[1];
                        if (data.answerResult) {
                            vm.answerResult = data.answerResult;
                        } else {
                            vm.answerResult = {talkRecordDEntityList: []};
                            var index = layer.alert($.format($.msg.MSGCOMN0017, '面談記録'), {
                                skin: 'layui-layer-molv',
                                title: '確認',
                                closeBtn: 0,
                                anim: -1,
                                btn: ['確認'],
                                btn1: function () {
                                    layer.close(index);
                                }
                            });
                        }
                    }
                }
            })
        },
        // 2020/12/3 huangxinliang modify start
        showImg: function (index) {
            var container = $("#imageContainer");
            container.html($('#image' + index));
            $('#image' + index).removeClass('photo-image');
            container.fadeIn();
        }
        // 2020/12/3 huangxinliang modify end
    }
});

//戻るボタン押下時
function back() {
    window.location.href = "./F30409.html";
}
// 申込をキャンセルボタン押下時
function remove() {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "キャンセル"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/guard/F30410/delete',
                type: 'GET',
                data: {
                    talkRecordId: vm.answerResult.id ? vm.answerResult.id : null,
                    eventId: param.eventId,
                    stuId: param.stuId,
                    guardId: param.guardId,
                    /* 2020/12/21 V9.0 cuikailin add start */
                    refType: param.refType
                    /* 2020/12/21 V9.0 cuikailin add end */
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var idx = layer.confirm($.format($.msg.MSGCOMN0014, "キャンセル"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                        //         layer.close(idx);
                                location.href = "F30407.html?brandCd=" + data.brandCd;
                        //     }
                        // })

                    }
                }
            });
        }
    })
}


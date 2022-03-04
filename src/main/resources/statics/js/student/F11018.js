var param = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        f11018Dto: '',
        f11018Dtos: [],
        msgTop: '',
        msgBottom: ''
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
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/student/F11018/init',
                type: 'GET',
                data: {
                    applyId: param.applyId,
                    /* 2020/12/30 V9.0 cuikailin add start */
                    refType: param.refType
                    /* 2020/12/30 V9.0 cuikailin add end */
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.f11018Dto) {
                            vm.f11018Dto = data.f11018Dto;
                        } else {
                            return;
                        }
                        if (data.f11018Dtos) {
                            vm.f11018Dtos = data.f11018Dtos;
                        } else {
                            return;
                        }
                        vm.msgTop = data.msg.split("。")[0];
                        vm.msgBottom = data.msg.split("。")[1];
                    }
                }
            });
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
    window.location.href = "./F11017.html";
};

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
                url: ctxPath + '/student/F11018/delete',
                type: 'GET',
                data: {
                    talkId: vm.f11018Dto.trhId,
                    applyId: param.applyId,
                    /* 2020/12/30 V9.0 cuikailin add start */
                    refType: param.refType
                    /* 2020/12/30 V9.0 cuikailin add end */
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        // showMsg(data.msg);
                    } else {
                        // var idx = layer.confirm($.format($.msg.MSGCOMN0014, "キャンセル"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                        //         layer.close(idx);
                                location.href = "F11019.html";
                        //     }
                        // });

                    }
                }
            });
        }
    });
}


var param = getParam();
var maxSize = 5 * 1024 * 1024;//5M
var vm = new Vue({
    el: '.main',
    data: {
        org: [],
        mstBlockEntity: [],
        id: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/f07008/init',
                type: 'GET',
                data: {
                    id: param.id
                },
                dataType: 'json',
                success: function (data) {
                    vm.org = data.org;
                    if (data.code == 0) {
                        if (data.mstBlockEntity) {
                            $(".blockDispyNm").val(data.mstBlockEntity.blockDispyNm);
                            var arr = (data.mstBlockEntity.blockTypeDiv).split('');
                            $(".blockTypeDiv").val(arr[1]);
                        }
                        else {
                            $("#star").text("*");
                            document.getElementById("blockDispyNm").disabled = false;
                        }
                    }
                    else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        },
        submitFn: function () {
            // this.formatCheck();
            if (this.formatCheck()) {
                var formData = new FormData();
                // 編集
                if (param.id) {
                    formData.append('id', param.id);
                    formData.append('flg', 0);
                }
                // 追加
                else {
                    formData.append('upid', param.upid);
                    formData.append('flg', 1);
                }
                formData.append('blockDispyNm', encodeURIComponent($('.blockDispyNm').val()).replace(/\%20/g," "));
                formData.append('blockTypeDiv', $(".blockTypeDiv").val());
                if ($("#f07008Form").valid() == true) {
                    $.ajax({
                        url: ctxPath + '/pop/f07008/submit',
                        cache: false,
                        data: formData,
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            if (data.code == 0) {
                                // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                        parent.location.reload();
                                        var index = parent.layer.getFrameIndex(window.name);
                                        parent.layer.close(index);
                                //     }
                                // });
                            }
                            else {
                                parent.layer.alert(data.msg);
                            }
                        }
                    });
                }
            }
        },
        bakBtn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        formatCheck: function () {
            $("#f07008Form").validate({
                onfocusout: false,
                onkeyup:false,
                rules: {
                    // 教科CD
                    blockDispyNm: {
                        required: true,
                        zenkaku: true
                    },
                    // 教科名
                    blockTypeDiv: {
                        required: true,
                        digits: true
                    }
                },
                debug: true,
                messages: {
                    // 小分類名
                    blockDispyNm: {
                        required: $.format($.msg.MSGCOMD0001, "小分類名"),
                        zenkaku: $.format($.msg.MSGCOMD0002, "小分類名")
                    },
                    // ソート
                    blockTypeDiv: {
                        required: $.format($.msg.MSGCOMD0001, "ソート"),
                        digits: $.format($.msg.MSGCOMD0006, "ソート")
                    }
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        parent.layer.alert(errorList[0].message);
                    }
                }
            });
            return true;
        }
    }
});

// function formatCheck() {
//     $("#f07008Form").validate({
//         onfocusout: false,
//         rules: {
//             // 教科CD
//             blockDispyNm: {
//                 required: true,
//                 zenkaku: true
//             },
//             // 教科名
//             blockTypeDiv: {
//                 required: true,
//                 digits: true
//             }
//         },
//         debug:true,
//         messages: {
//             // 小分類名
//             blockDispyNm: {
//                 required: $.format($.msg.MSGCOMD0001, "小分類名"),
//                 zenkaku: $.format($.msg.MSGCOMD0002, "小分類名")
//             },
//             // ソート
//             blockTypeDiv: {
//                 required: $.format($.msg.MSGCOMD0001, "ソート"),
//                 digits: $.format($.msg.MSGCOMD0006, "ソート")
//             },
//         },
//         showErrors: function (errorMap, errorList) {
//             if (errorList.length != 0) {
//                 parent.layer.alert(errorList[0].message);
//             }
//         }
//     });
// }
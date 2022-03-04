var param = getParam();
var vm = new Vue({
        el: '.main',
        data: {
            schy: '',
            sortValue: '',
            org: ''
        },
        mounted: function () {
            this.getInfo();
        },
        methods: {
            //初期化
            getInfo: function () {
                $.ajax({
                    url: ctxPath + '/pop/F07002/init',
                    type: 'get',
                    data: {
                        //セッションデータ．学年CD
                        codCd: param.codCd
                    },
                    success: function (data) {
                        vm.org = data.org;
                        if (data.code == 0) {
                            vm.schy = data.schy;
                            vm.sortValue = data.sort;
                        } else {
                            showMsg(data.msg)
                        }
                    }
                })
            },
            //登録ボタン押下時
            submitButton: function () {
                this.formatCheck();
                if ($("#f07002Form").valid()) {
                    $.ajax({
                        url: ctxPath + '/pop/F07002/submit',
                        type: 'get',
                        data: {
                            schy: $('#schy').val(),
                            sort: $('#sortValue').val(),
                            codCd: param.codCd
                        },
                        success: function (data) {
                            if (data.code == 0) {
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
                                // })
                            } else {
                                parent.layer.alert(data.msg);
                            }
                        }
                    })
                }
            },
            //キャンセルボタン押下時
            cancel: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
            },
            //Check
            formatCheck: function () {
                $("#f07002Form").validate({
                    rules: {
                        //学年名
                        schy: {
                            required: true,
                            maxlength: 20
                        },
                        //ソート
                        sortValue: {
                            required: true,
                            number: true,
                            maxlength: 3
                        }
                    },
                    debug: true,
                    onfocusout: false,
                    onkeyup: false,
                    messages: {
                        //学年名
                        schy: {
                            required: $.format($.msg.MSGCOMD0001, "学年名"),
                            maxlength: $.format($.msg.MSGCOMD0017, "学年名", "20")
                        },
                        //ソート
                        sortValue: {
                            required: $.format($.msg.MSGCOMD0001, "ソート"),
                            number: $.format($.msg.MSGCOMD0006, "ソート"),
                            maxlength: $.format($.msg.MSGCOMD0017, "ソート", "3")
                        }
                    },
                    showErrors: function (errorMap, errorList) {
                        if (errorList.length != 0) {
                            parent.layer.alert(errorList[0].message);
                        }
                    }
                });
            }
        }
    })
;



var param = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        orgId: "",
        orgNm: "",
        arr:[],
        schy_div: [],
        gender_div: [],
        reltnsp_div: [],
        mstOrgEntity: [],
        role_div: [],
        information: [],
        headMsg: "",
        objList: [],
        sendObjList: [],
        sortList: [
            {name: "orgNm", rule: "asc"},
            {name: "orgId", rule: "asc"},
            {name: "status", rule: "asc"}
        ],
        sortkey: "orgId",
        returnList: [],
        orgList: []
    },
    watch: {
        returnList: {
            handler(arr1) {
                //最初の選択の後、2番目の選択を行うと、重複排除後にディスプレイが表示されます
                let dataList = arr1.concat(vm.orgList);
                $.ajax({
                    url: ctxPath + '/manager/f00042/retainTopOrg',
                    type: "POST",
                    data: JSON.stringify(dataList),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                        if (result.code !== 0) {
                            parent.layer.alert(result.msg);
                            return;
                        }
                        vm.orgList = result.orgList;
                    }
                })
            },
            deep: true
        }
    },
    updated: function () {
        $(".parent").find("option").each(function () {
            if ($(this).val() === '1') {
                $(this).attr("selected", true);
            }
        })
    },
    mounted: function () {
        $('#birthDay').mobiscroll().date({
            theme: "mobiscroll",
            preset: 'date',
            display: 'modal',
            dateFormat: 'yy/mm/dd',
            mode: "scroller",
            setText: '確認',
            cancelText: "キャンセル",
            dayText: '日',
            monthText: '月',
            yearText: '年',
            dateOrder: 'yymmdd',
            defaultValue: new Date('2005-01-01'),
            onShow: function () {
                $(".dwwl0").find(".dw-i").each(function () {
                    $(this).text($(this).text() + '年');
                });
                $(".dwwl1").find(".dw-i").each(function () {
                    $(this).text($(this).text() + '月');
                });
                $(".dwwl2").find(".dw-i").each(function () {
                    $(this).text($(this).text() + '日');
                });
            }
        });
        $("#time_picker_begin").mobiscroll("setVal", '2005-01-01');
        this.getInfo();
        if (param.role == "1" || param.role == "2") {
            $("#manager").show();
            $("#guard").remove();
            $("#student").remove();
        }
        if (param.role == "3") {
            $("#guard").show();
            $("#manager").remove();
            $("#student").remove();
        }
        if (param.role == "4") {
            $("#student").show();
            $("#manager").remove();
            $("#guard").remove();
        }
        formatCheck();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f00042/init',
                data: {
                    role: param.role
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        vm.orgId = data.orgId;
                        vm.orgNm = data.orgNm;
                        vm.mstOrgEntity = data.mstOrgEntity;
                        vm.mstOrgEntity.orgNm = getOrgName(vm.mstOrgEntity.orgNm)
                        vm.schy_div = data.schy_div;
                        vm.gender_div = data.gender_div;
                        vm.reltnsp_div = data.reltnsp_div;
                        vm.role_div = data.role_div;
                        vm.objList = data.objList;
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        dataChange: function (objList) {
            return objList;
        },
        //半角check
        isHalfAngle: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        searchPostCd: function () {
            // 郵便番号
            // 郵便番号が未入力の場合、エラーとする
            if ($(".postCd").val() == '') {
                showMsg($.format($.msg.MSGCOMD0001, "郵便番号"));
                $("#postcd").focus();
                return;
            }
            // 郵便番号が７文字を超える場合、エラーとする
            if ($(".postCd").val().length > 7) {
                showMsg($.format($.msg.MSGCOMD0017, "郵便番号", "7"));
                return;
            }
            // 郵便番号が５文字不足場合、エラーとする
            if ($(".postCd").val().length < 7) {
                showMsg($.format($.msg.MSGCOMD0016, "郵便番号", "7"));
                return;
            }
            // 郵便番号が半角数字以外で入力した場合、エラーとする
            if (this.isHalfAngle($(".postCd").val()) == false) {
                showMsg($.format($.msg.MSGCOMD0006, "郵便番号"));
                $(".postCd").focus();
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/f00042/searchPostCd',
                data: {
                    postcd: $(".postCd").val()
                },
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data.code == 0) {
                        $(".addr1").val(data.adr1);
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        submitFn: function () {
            if (vm.orgList.length===0){
                showMsg($.format($.msg.MSGCOMD0001,"利用組織"));
                return ;
            }
            if (this.role_div.codCd == '3' && $(".telNum").val() == '') {
                showMsg($.format($.msg.MSGCOMD0001, "電話番号"));
                return ;
            }
            if ($("#f00042Form").valid() == true) {
                $.ajax({
                    url: ctxPath + '/manager/f00042/checkAfterUserId',
                    data: {
                        userId: $(".userId").val()
                    },
                    dataType: 'json',
                    type: 'POST',
                    success: function (data) {
                        if (data.code == 0) {
                            var msg = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
                                skin: 'layui-layer-molv',
                                title: '確認',
                                closeBtn: 0,
                                anim: -1,
                                btn: ['キャンセル', '確認'],
                                btn2: function () {
                                    var data = {
                                        role: param.role,
                                        userId: $(".userId").val(),
                                        password: $(".password").val(),
                                        flnmNm: $(".flnmNm").val(),
                                        flnmLnm: $(".flnmLnm").val(),
                                        flnmKnNm: $(".flnmKnNm").val(),
                                        flnmKnLnm: $(".flnmKnLnm").val(),
                                        email: $(".email").val(),
                                        telNum: $(".telNum").val(),
                                        postCd: $(".postCd").val(),
                                        parent: $(".parent").val(),
                                        urgTelNum: $(".urgent").val(),
                                        addr1: $(".addr1").val(),
                                        addr2: $(".addr2").val(),
                                        schoolYear: $(".schoolYear").val(),
                                        schoolName: $(".schoolName").val(),
                                        birthDay: $(".birthDay").val(),
                                        gender: $(".gender").val(),
                                        qrCod: $(".userId").val(),
                                        oriaCd: '',
                                        orgList: vm.orgList
                                    };
                                    $.ajax({
                                        url: ctxPath + '/manager/f00042/getInformation',
                                        data: JSON.stringify(data),
                                        dataType: 'json',
                                        contentType: "application/json; charset=utf-8",
                                        type: 'POST',
                                        success: function (data) {
                                            if (data.code == 0) {
                                                window.location.href = "./F00041.html?roleDiv=" + param.role + '&usrSts=' + param.usrSts;
                                            } else {
                                                layer.close(msg);
                                                showMsg(data.msg);
                                            }
                                        }
                                    });
                                },
                                btn1: function () {
                                    layer.close(msg);
                                }
                            });
                        } else {
                            showMsg(data.msg);
                        }
                    }
                });
            }
        },
        bakBtn: function () {
            window.location.href = "F00041.html?roleDiv=" + param.role + '&usrSts=' + param.usrSts;
        },
        //組織選択BTN
        chooseBtn: function () {
            var srcWidth = $(window).width() * 0.8;
            if (srcWidth > 280) {
                var srcH = 800;
                srcWidth = '800px';
            } else {
                var srcH = srcWidth;
                srcWidth = srcWidth + 'px'
            }
            var srcHeight = srcH * 0.4 + 'px';
            layer.open({
                id: 'f00042',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                content: ["../pop/F00046.html", 'no']
            })
        },
        //削除
        delOrgBtn: function (arr,index) {
            arr.splice(index,1);
        }
    }
});

function formatCheck() {
    $("#f00042Form").validate({
        onfocusout: false,
        rules: {
            // 利用者ＩＤ
            userId: {
                required: true,
                alphaNumSymbol: true,
                minlength: 8,
                maxlength: 32
            },
            // パスワード
            password: {
                minlength: 8,
                maxlength: 32,
                // hasAlphaNum: true,
                pwd: true
            },
            // 名前_姓
            flnmNm: {
                required: true,
                <!--2021/02/24 manamiru1-524 cuikailin start -->
                maxlength: 30,
                /* 2020/11/30 V8.0 cuikailin add start */
                zenkaku: true
                /* 2020/11/30 V8.0 cuikailin add end */
            },
            // 名前_名
            flnmLnm: {
                required: true,
                maxlength: 30,
                /* 2020/11/30 V8.0 cuikailin add start */
                zenkaku: true
                /* 2020/11/30 V8.0 cuikailin add end */
            },
            // 名前_カナ姓
            flnmKnNm: {
                required: true,
                maxlength: 30,
                zenkaku: true
            },
            // 名前_カナ名
            flnmKnLnm: {
                required: true,
                maxlength: 30,
                <!--2021/02/24 manamiru1-524 cuikailin end -->
                zenkaku: true
            },
            // 電話番号
            telNum: {
                minlength: 10,
                maxlength: 11,
                digits: true
            },
            // メール
            email: {
                // required:true,
                email:true,
                maxlength:60
            },
            // 郵便番号
            postCd: {
                required: true,
                minlength: 7,
                maxlength: 7,
                digits: true
            },
            // 住所1
            addr1: {
                required: true,
                maxlength: 60
            },
            // 住所2
            addr2: {
                maxlength: 60
            },
            // 学校
            schoolName: {
                maxlength: 30
            },
            //生年月日
            birthDay: {
                required: true
            },
            //性別
            gender: {
                required: true
            },
            // 学年
            schoolYear: {
                required: true
            },
            //緊急電話番号
            urgent: {
                minlength: 10,
                maxlength: 11,
                digits: true
            }
        },
        debug: true,
        messages: {
            // 利用者ＩＤ
            userId: {
                required: $.format($.msg.MSGCOMD0001, "利用者ＩＤ"),
                minlength: $.format($.msg.MSGCOMD0016, "利用者ＩＤ", "8"),
                maxlength: $.format($.msg.MSGCOMD0017, "利用者ＩＤ", "32"),
                // hjx : 2019/11/08 mod  hasAlphaNum  =>  alphaNumSymbol
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "利用者ＩＤ")
            },
            // パスワード
            password: {
                minlength: $.format($.msg.MSGCOMD0016, "パスワード", "8"),
                maxlength: $.format($.msg.MSGCOMD0017, "パスワード", "32"),
                // hasAlphaNum: $.format($.msg.MSGCOMD0004, "パスワード"),
                pwd: $.format($.msg.MSGCOMN0162, "パスワード")
            },
            // 名前_姓
            flnmNm: {
                required: $.format($.msg.MSGCOMD0001, "姓名_姓"),
                <!--2021/02/24 manamiru1-524 cuikailin start -->
                maxlength: $.format($.msg.MSGCOMD0017, "姓名_姓", "30"),
            /* 2020/11/30 V8.0 cuikailin add start */
                zenkaku: $.format($.msg.MSGCOMD0002, "姓名_姓")
            /* 2020/11/30 V8.0 cuikailin add end */
            },
            // 名前_名
            flnmLnm: {
                required: $.format($.msg.MSGCOMD0001, "姓名_名"),
                maxlength: $.format($.msg.MSGCOMD0017, "姓名_名", "30"),
                /* 2020/11/30 V8.0 cuikailin add start */
                zenkaku: $.format($.msg.MSGCOMD0002, "姓名_名")
                /* 2020/11/30 V8.0 cuikailin add end */
            },
            // 名前_カナ姓
            flnmKnNm: {
                required: $.format($.msg.MSGCOMD0001, "カナ姓名_姓"),
                maxlength: $.format($.msg.MSGCOMD0017, "カナ姓名_姓", "30"),
                /* 2020/11/30 V8.0 cuikailin modify start */
                zenkaku: $.format($.msg.MSGCOMD0007, "カナ姓名_姓")
                /* 2020/11/30 V8.0 cuikailin modify end */
            },
            // 名前_カナ名
            flnmKnLnm: {
                required: $.format($.msg.MSGCOMD0001, "カナ姓名_名"),
                maxlength: $.format($.msg.MSGCOMD0017, "カナ姓名_名", "30"),
                <!--2021/02/24 manamiru1-524 cuikailin end -->
                /* 2020/11/30 V8.0 cuikailin modify start */
                zenkaku: $.format($.msg.MSGCOMD0007, "カナ姓名_名")
                /* 2020/11/30 V8.0 cuikailin modify end */
            },
            // 電話番号
            telNum: {
                minlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                digits: $.format($.msg.MSGCOMD0006, "電話番号")
            },
            // メール
            email: {
                // required:$.format($.msg.MSGCOMD0001,"メールアドレス"),
                maxlength:$.format($.msg.MSGCOMD0017,"メールアドレス","60"),
                email:$.format($.msg.MSGCOMD0018,"メールアドレス")
            },
            // 郵便番号
            postCd: {
                required: $.format($.msg.MSGCOMD0001, "郵便番号"),
                minlength: $.format($.msg.MSGCOMD0016, "郵便番号", "7"),
                maxlength: $.format($.msg.MSGCOMD0017, "郵便番号", "7"),
                digits: $.format($.msg.MSGCOMD0006, "郵便番号")
            },
            // 住所1
            addr1: {
                required: $.format($.msg.MSGCOMD0001, "住所1"),
                maxlength: $.format($.msg.MSGCOMD0017, "住所1", "60")
            },
            // 住所2
            addr2: {
                maxlength: $.format($.msg.MSGCOMD0017, "住所2", "60")
            },
            // 学校
            schoolName: {
                maxlength: $.format($.msg.MSGCOMD0017, "学校", "30")
            },
            //生年月日
            birthDay: {
                required: $.format($.msg.MSGCOMD0001, "生年月日")
            },
            //性別
            gender: {
                required: $.format($.msg.MSGCOMD0001, "性別")
            },
            // 学年
            schoolYear: {
                required: $.format($.msg.MSGCOMD0001, "学年")
            },
            // 緊急電話番号
            urgent: {
                minlength: $.format($.msg.MSGCOMD0010, "緊急電話番号", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "緊急電話番号", "10", "11"),
                digits: $.format($.msg.MSGCOMD0006, "緊急電話番号")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
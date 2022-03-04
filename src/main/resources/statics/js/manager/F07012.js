var param = getParam();
var maxSize = 5 * 1024 * 1024;//5M
var vm = new Vue({
    el: ".content",
    data: {
        org: [],
        mstcodEntity: [],
        schyList: []
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            //初期表示
            $.ajax({
                url: ctxPath + '/manager/f07012/init',
                type: 'GET',
                data: {
                    codCd: param.codCd,
                },
                dataType: 'json',
                success: function (data) {
                    vm.org = data.org;
                    if (data.code == 0) {
                        if (data.schyList){
                            vm.schyList = data.schyList;
                        }
                        if (data.mstcodEntity) {
                            vm.mstcodEntity = data.mstcodEntity;
                            $("#star").text("");
                            //非活性
                            document.getElementById("codCd").disabled = true;
                            $(".sort").val(data.mstcodEntity.sort);
                            if(data.mstcodEntity.backGround){
                                $('.showImg1').css("background-color",data.mstcodEntity.backGround);
                            }
                        }
                        else {
                            vm.mstcodEntity.sort = "";
                            $("#star").text("*");
                            document.getElementById("codCd").disabled = false;
                        }
                    } else {
                        showMsg(data.msg);
                    }
                }
            })
        },
        submitFn: function () {
            if(vm.formatCheck()){
                var formData = new FormData();
                formData.append('codCd', $('.codCd').val());
                formData.append('schy', $('.schy').val());
                formData.append('sort', $('.sort').val());
                formData.append('subName', encodeURIComponent($('.subName').val()).replace(/\%20/g," "));
                if ($(".img1")[0].files[0]) {
                    formData.append('file', $(".img1")[0].files[0]);
                } else {
                    formData.append('file', null);
                }
                formData.append('backGround', $(".background").val());
                if (param.codCd) {
                    // 編集
                    formData.append('flg', 0);
                } else {
                    // 追加
                    formData.append('flg', 1);
                }
                $.ajax({
                    url: ctxPath + '/manager/f07012/submit',
                    cache: false,
                    data: formData,
                    type: 'POST',
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {btn: ['確認']}, function () {
                                window.location.href = "./F07011.html";
                            // });
                        } else {
                            showMsg(data.msg);
                        }
                    }
                })
            }
        },
        bakBtn: function () {
            window.location.href = "./F07011.html"
        },
        btnUploadFile:function (e,type) {
            // 画像を取得する
            var files = e.target.files;
            var file = files[0];
            var reader = new FileReader();
            reader.onload = function () {
                var result = this.result;  // 画像base64の文字列
                $(".showImg1").attr("src", result);  // 写真を表示
            };
            reader.readAsDataURL(file);    // Base64
        },
        formatCheck:function ()  {
            $("#f07012Form").validate({
                onfocusout: false,
                rules: {
                    //学年
                    schy:{
                        required:true
                    },
                    // 教科CD
                    codCd: {
                        required: true,
                        alphaNumSymbol: true
                    },
                    // 教科名
                    subName: {
                        required: true,
                        zenkaku: true
                    },
                    // ソート
                    sort: {
                        required: true,
                        digits: true
                    },
                    // 背景色
                    background: {
                        required: true,
                        hankaku: true
                    }
                },
                debug: true,
                messages: {
                    //学年
                    schy:{
                        required:$.format($.msg.MSGCOMD0001, "学年"),
                    },
                    // 教科CD
                    codCd: {
                        required: $.format($.msg.MSGCOMD0001, "教科ＣＤ"),
                        alphaNumSymbol: $.format($.msg.MSGCOMD0004, "教科ＣＤ")
                    },
                    // 教科名
                    subName: {
                        required: $.format($.msg.MSGCOMD0001, "教科名"),
                        zenkaku: $.format($.msg.MSGCOMD0007, "教科名")
                    },
                    // ソート
                    sort: {
                        required: $.format($.msg.MSGCOMD0001, "ソート"),
                        digits: $.format($.msg.MSGCOMD0006, "ソート")
                    },
                    // 背景色
                    background: {
                        required: $.format($.msg.MSGCOMD0001, "背景色"),
                        hankaku: $.format($.msg.MSGCOMD0003, "背景色")
                    }
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        showMsg(errorList[0].message);
                    }
                }
            });
            if (!$("#f07012Form").valid()) {
                return false;
            }
            if (!/^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/.test($(".background").val())) {
                showMsg($.format($.msg.MSGCOMD0022,"背景色"));
                return false;
            }
            if(!param.codCd){
                if (!$(".img1")[0].files[0]) {
                    showMsg($.format($.msg.MSGCOMD0001, "表示用画像"));
                    return false;
                }
            }
            if ($(".img1")[0].files[0]) {
                if ($(".img1")[0].files[0].size >= maxSize) {
                    showMsg($.format($.msg.MSGCOMN0088, "表示用画像", "画像", "5M"))
                    return false;
                }
                // タイプの画像を受け入れる
                if (!/\/(?:jpg|bmp|eps|gif|mif|miff|png|tif|tiff|svg|wmf|jpe|jpeg|dib|ico|tga|cut|pic)/i.test($(".img1")[0].files[0].type)) {
                    showMsg($.format($.msg.MSGCOMN0076, 'jpg,bmp,eps,gif,mif,miff,png,tif,tiff,svg,wmf,jpe,jpeg,dib,ico,tga,cut,pic'))
                    return false;
                }
            }
            return true;
        },
        //色のマッチング
        colorChange: function (type1,type2) {
            $("."+type1).val($("."+type2).val());
            $('.showImg1').css("background-color", $("."+type2).val());
        }
    }
})













var param = getParam();
var formData = new FormData();
var maxSize = 5 * 1024 * 1024;//5M

$(document).ready(function () {

});
var vm = new Vue({
    el: '.content',
    data: {
        mstOrgEntity: [],
        subjt: [],
        subjtList:[],
        checksubjtList:[]
    },
    updated: function () {
        if(this.checksubjtList){//コード値５表示
            for(var i = 0; i < vm.checksubjtList.length ;i++){
                var id = "codCd"+vm.checksubjtList[i].codCd.toLocaleString();
                document.getElementById(id).checked = true;
            }
        }
        $(".subCd4").click(function () {
            //復習系のみ場合
            if(document.getElementById("subCd4_2").checked){
                document.getElementById("backgroundColor").disabled = true;
                document.getElementById("backgroundColor1").disabled = true;
                document.getElementById("img1").disabled = true;
                document.getElementById("img2").disabled = true;
                $(".backgroundColor").val(null);
            }else{
                //以外の場合
                document.getElementById("backgroundColor").disabled = false;
                document.getElementById("backgroundColor1").disabled = false;
                document.getElementById("img1").disabled = false;
                document.getElementById("img2").disabled = false;
            }
        });
        //checkboxのクリックの結合
        $(".div_schy_div_list span").click(function () {
            $(this).parent("p").find("input").click();
        });
        //全選択ボタンをクリックします
        $("#checkall").click(function () {
            if(document.getElementById("checkall").checked){
                var checks = document.getElementsByName("schy_div");
                for(var i = 0; i < checks.length ;i++){
                    checks[i].checked = true;
                }
            }else{
                var checks = document.getElementsByName("schy_div");
                for(var i = 0; i < checks.length ;i++){
                    checks[i].checked = false;
                }
            }
        });
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f07004/init',
                data: {
                    subCd: param.subCd
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.mstOrgEntity) {
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    if (data.code == 0) {
                        if (data.subjt) {
                            vm.subjt = data.subjt;
                            $("#star").text("");
                            document.getElementById("subCd").disabled = true;
                            $(".sort").val(data.subjt.sort);
                            if (data.subjt.codValue2) {
                                $('.showImg1').css("background-color", data.subjt.codValue2);
                                // $('.showImg2').css("background-color", data.subjt.codValue2);
                            }
                            if(data.subjt.codValue4 == '0'){
                                document.getElementById("subCd4_1").checked = true;
                            }else if(data.subjt.codValue4 == '1'){
                                document.getElementById("subCd4_2").checked = true;
                                document.getElementById("backgroundColor").disabled = true;
                                document.getElementById("backgroundColor1").disabled = true;
                                document.getElementById("img1").disabled = true;
                                document.getElementById("img2").disabled = true;
                            }
                        } else {
                            $("#star").text("*");
                            document.getElementById("subCd").disabled = false;
                        }
                    }
                    else {
                        showMsg(data.msg);
                    }
                    if(data.subjtList){
                        vm.subjtList = data.subjtList;
                    }
                    if(data.checksubjtList){
                        vm.checksubjtList = data.checksubjtList;
                        if(vm.checksubjtList.length == vm.subjtList.length){
                            document.getElementById("checkall").checked = true;
                        }
                    }
                }
            });
        },
        submitFn: function () {
            if (this.formatCheck() != false) {
                var formData = new FormData();
                formData.append('subCd', $(".subCd").val());
                formData.append('subNm', encodeURIComponent($('.subNm').val()).replace(/\%20/g," "));
                formData.append('sort', $(".sort").val());
                formData.append('backgroudColor', $(".backgroundColor").val());
                if ($(".img1")[0].files[0]) {
                    formData.append('file1', $(".img1")[0].files[0]);
                } else {
                    formData.append('file1', null);
                }
                if ($(".img2")[0].files[0]) {
                    formData.append('file2', $(".img2")[0].files[0]);
                } else {
                    formData.append('file2', null);
                }
                if (param.subCd) {
                    // 編集
                    formData.append('flg', 0);
                } else {
                    // 追加
                    formData.append('flg', 1);
                }
                if(document.getElementById("subCd4_1").checked){
                    formData.append('codValue4', '0');
                }else{
                    formData.append('codValue4', '1');
                }
                var codValue5 = "";
                var checks = document.getElementsByName('schy_div');
                for(var i = 0; i < checks.length ;i++){
                    if(checks[i].checked){
                        codValue5 += checks[i].value;
                        codValue5+= ',';
                    }
                }
                if(codValue5 != ""){
                    codValue5 = codValue5.substring(0,codValue5.length-1);
                    formData.append('codValue5', codValue5);
                }

                $.ajax({
                    url: ctxPath + '/manager/f07004/submit',
                    cache: false,
                    data: formData,
                    type: 'POST',
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {btn: ['確認']}, function () {
                                window.location.href = "./F07003.html";
                            // });
                        }
                        else {
                            showMsg(data.msg);
                        }
                    }
                });
            }
        },
        bakBtn: function () {
            window.location.href = "./F07003.html"
        },
        btnUploadFile: function (e, type) {
            // 画像を取得する
            var files = e.target.files;
            var file = files[0];
            var reader = new FileReader();
            reader.onload = function () {
                var result = this.result;  // 画像base64の文字列
                 $("."+type).attr("src", result);  // 写真を表示
            };
            reader.readAsDataURL(file);    // Base64
        },
        formatCheck: function () {
            $("#f07004Form").validate({
                onfocusout: false,
                rules: {
                    // 教科CD
                    subCd: {
                        required: true,
                        alphaNumSymbol: true
                    },
                    // 教科名
                    subNm: {
                        required: true,
                        zenkaku: true
                    },
                    // ソート
                    sort: {
                        required: true,
                        digits: true
                    },
                    // 背景色
                    // backgroundColor: {
                    //     required: true,
                    //     hankaku: true
                    // },
                    //学年区分
                    schy_div:{
                        required: true,
                        hankaku: true
                    },
                    //ブロックタイプ
                    subCd4:{
                        required: true,
                        hankaku: true
                    }
                },
                debug: true,
                messages: {
                    // 教科CD
                    subCd: {
                        required: $.format($.msg.MSGCOMD0001, "教科ＣＤ"),
                        alphaNumSymbol: $.format($.msg.MSGCOMD0004, "教科ＣＤ")
                    },
                    // 教科名
                    subNm: {
                        required: $.format($.msg.MSGCOMD0001, "教科名"),
                        zenkaku: $.format($.msg.MSGCOMD0007, "教科名")
                    },
                    // ソート
                    sort: {
                        required: $.format($.msg.MSGCOMD0001, "ソート"),
                        digits: $.format($.msg.MSGCOMD0006, "ソート")
                    },
                    // 背景色
                    // backgroundColor: {
                    //     required: $.format($.msg.MSGCOMN0107,"ブロックタイプ（共通）","背景色"),
                    //     hankaku: $.format($.msg.MSGCOMD0003, "背景色")
                    // },
                    //学年区分
                    schy_div:{
                        required: $.format($.msg.MSGCOMD0001, "学年"),
                        hankaku: $.format($.msg.MSGCOMD0003, "学年")
                    },
                    //ブロックタイプ
                    subCd4:{
                        required: $.format($.msg.MSGCOMD0001, "ブロックタイプ"),
                        hankaku: $.format($.msg.MSGCOMD0003, "ブロックタイプ")
                    }
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        showMsg(errorList[0].message);
                    }
                }
            });
            if (!$("#f07004Form").valid()) {
                return false;
            }
            // // 編集
            // if (param.subCd) {
            //     formData.append('flg', 0);
            // }
            // 追加
            if (document.getElementById("subCd4_1").checked) {
                if(!$("input[name='backgroundColor']").val()){
                    showMsg($.format($.msg.MSGCOMN0107, "ブロックタイプ（共通）","背景色"));
                    return false;
                }
                if (!/^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/.test($(".backgroundColor").val())) {
                    showMsg($.format($.msg.MSGCOMD0022, "背景色"));
                    return false;
                }
                if (!$(".img1")[0].files[0]) {
                    if(!$(".showImg1").attr("src")){//更新の場合
                        showMsg($.format($.msg.MSGCOMN0107, "ブロックタイプ（共通）","表示用画像"));
                        return false;
                    }
                }
                if (!$(".img2")[0].files[0]) {
                    if(!$(".showImg2").attr("src")){//更新の場合
                        showMsg($.format($.msg.MSGCOMN0107, "ブロックタイプ（共通）" ,"ボタン用画像"));
                        return false;
                    }
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
            if ($(".img2")[0].files[0]) {
                if ($(".img2")[0].files[0].size >= maxSize) {
                    showMsg($.format($.msg.MSGCOMN0088, "ボタン用画像", "画像", "5M"))
                    return false;
                }
                // タイプの画像を受け入れる
                if (!/\/(?:jpg|bmp|eps|gif|mif|miff|png|tif|tiff|svg|wmf|jpe|jpeg|dib|ico|tga|cut|pic)/i.test($(".img2")[0].files[0].type)) {
                    showMsg($.format($.msg.MSGCOMN0076, 'jpg,bmp,eps,gif,mif,miff,png,tif,tiff,svg,wmf,jpe,jpeg,dib,ico,tga,cut,pic'))
                    return false;
                }
            }
            return true;
        },
        colorChange: function (type1,type2) {
            $("."+type1).val($("."+type2).val());
            $('.showImg1').css("background-color", $("."+type2).val());
            $('.showImg2').css("background-color", $("."+type2).val());
        }
    }
});



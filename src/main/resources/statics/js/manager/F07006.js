var param = getParam();
var maxSize = 5 * 1024 * 1024;//5M
var path;
window.errorFlg = 0;
var vm = new Vue({
    el: '.main',
    data: {
        org: [],
        mstBlockEntity: [],
        imgId1: "",
        imgId2: "",
        imgId3: "",
        imgId4: "",
        path: ""
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f07006/init',
                type: 'GET',
                data: {
                    id: param.id,
                },
                dataType: 'json',
                success: function (data) {
                    vm.org = data.org;
                    if (data.code == 0) {
                        if (data.path) {
                            vm.path = data.path;
                        }
                        if (data.mstBlockEntity) {
                            vm.mstBlockEntity = data.mstBlockEntity;
                            vm.imgId1 = vm.path + data.mstBlockEntity.blockPicDiv.replace('.png', '_grey.png');
                            vm.imgId2 = "../img/" + data.mstBlockEntity.blockPicDiv.replace('.png', '_grey.png');
                            vm.imgId3 = vm.path + data.mstBlockEntity.blockPicDiv;
                            vm.imgId4 = "../img/" + data.mstBlockEntity.blockPicDiv;
                            var arr = (data.mstBlockEntity.blockTypeDiv).split('');
                            $(".blockTypeDiv").val(arr[1]);
                        } else {
                            $("#star").text("*");
                            document.getElementById("blockDispyNm").disabled = false;
                        }
                    }
                    else {
                        showMsg(data.msg)
                    }
                }
            })
        },
        submitFn: function () {
            if (vm.formatCheck()) {
                var formData = new FormData();
                formData.append('blockDispyNm', encodeURIComponent($('.blockDispyNm').val()).replace(/\%20/g," "));
                formData.append('blockTypeDiv', $(".blockTypeDiv").val());
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
                // ??????
                if (param.id) {
                    formData.append('id', param.id);
                    formData.append('flg', 0)
                }
                // ??????
                else {
                    formData.append('flg', 1)
                }

                $.ajax({
                    url: ctxPath + '/manager/f07006/submit',
                    cache: false,
                    data: formData,
                    type: 'POST',
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0022, "??????"), {btn: ['??????']}, function () {
                                window.location.href = "./F07005.html";
                            // });
                        }
                        else {
                            showMsg(data.msg);
                        }
                    }
                })
            }
        },
        bakBtn: function () {
            window.location.href = "F07005.html";
        },
        btnUploadFile:function (e,type) {
            // ?????????????????????
            var files = e.target.files;
            var file = files[0];
            var reader = new FileReader();
            reader.onload = function () {
                var result = this.result;  // ??????base64????????????
                $("."+type).attr("src", result);  // ???????????????
                $("."+type).css("opacity",1);
                $(".img").css("border",0);
            };
            reader.readAsDataURL(file);    // Base64
        },
        formatCheck:function () {
            $("#f07006Form").validate({
                onfocusout: false,
                rules: {
                    // ????????????
                    blockDispyNm: {
                        required: true,
                        zenkaku: true
                    },
                    // ?????????
                    blockTypeDiv: {
                        required: true,
                        digits:true
                    }
                },
                debug: true,
                messages: {
                    // ????????????
                    blockDispyNm: {
                        required: $.format($.msg.MSGCOMD0001, "????????????"),
                        zenkaku: $.format($.msg.MSGCOMD0002, "????????????")
                    },
                    // ?????????
                    blockTypeDiv: {
                        required: $.format($.msg.MSGCOMD0001, "?????????"),
                        digits: $.format($.msg.MSGCOMD0006, "?????????")
                    },
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        showMsg(errorList[0].message);
                    }
                }
            });
            if (!$("#f07006Form").valid()){
                return false;
            }
            if(!param.id){
                if (!$(".img1")[0].files[0]) {
                    showMsg($.format($.msg.MSGCOMD0001, "???????????????"));
                    return false;
                }
                if (!$(".img2")[0].files[0]) {
                    showMsg($.format($.msg.MSGCOMD0001, "???????????????"));
                    return false;
                }
            }
            if ($(".img1")[0].files[0]) {
                if ($(".img1")[0].files[0].size >= maxSize) {
                    showMsg($.format($.msg.MSGCOMN0088, "???????????????", "??????", "5M"))
                    return false;
                }
                // ????????????????????????????????????
                if (!/\/(?:jpg|bmp|eps|gif|mif|miff|png|tif|tiff|svg|wmf|jpe|jpeg|dib|ico|tga|cut|pic)/i.test($(".img1")[0].files[0].type)) {
                    showMsg($.format($.msg.MSGCOMN0076, 'jpg,bmp,eps,gif,mif,miff,png,tif,tiff,svg,wmf,jpe,jpeg,dib,ico,tga,cut,pic'))
                    return false;
                }
            }
            if ($(".img2")[0].files[0]) {
                if ($(".img2")[0].files[0].size >= maxSize) {
                    showMsg($.format($.msg.MSGCOMN0088, "???????????????", "??????", "5M"))
                    return false;
                }
                // ????????????????????????????????????
                if (!/\/(?:jpg|bmp|eps|gif|mif|miff|png|tif|tiff|svg|wmf|jpe|jpeg|dib|ico|tga|cut|pic)/i.test($(".img2")[0].files[0].type)) {
                    showMsg($.format($.msg.MSGCOMN0076, 'jpg,bmp,eps,gif,mif,miff,png,tif,tiff,svg,wmf,jpe,jpeg,dib,ico,tga,cut,pic'))
                    return false;
                }
            }
            return true;
        },
        //???????????????????????????????????????????????????????????????
        changeImg:function (path) {
            if(param.id){
                var img=event.srcElement;
                if (path == "imgId2") {
                    if (img.src = vm.imgId2) {img.onerror=null;}
                    else{img.src = vm.imgId2;}
                }
                if (path == "imgId4") {
                    if (img.src = vm.imgId4) {img.onerror=null;}
                    else{img.src = vm.imgId4;}
                }
            }
            else {
                if (path == "imgId2") {
                    $(".showImg1").css("opacity",0);
                }
                if (path == "imgId4") {
                    $(".showImg2").css("opacity",0);
                }
                $(".img").css("border","1px solid #333").css("background-color","#A0A0A0");
                // $('input[type="file"]').css("margin-top",0);
            }
        }
    }
})

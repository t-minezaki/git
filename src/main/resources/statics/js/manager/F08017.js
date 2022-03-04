var ue;
var param = getParam();
var filePath = "";
var FILE_TYPE = ["png", "jpg", "jpeg", "gif", "bmp", "pdf", "xlsx", "xls", "csv"];
var previewUrl;
var updateModeFlag = false;
var firstClickFileButtonFlag = true;
var IMG_TYPE = ["png", "jpg", "jpeg", "gif", "bmp"];
var vm =new Vue({
    el:'#div_body',
    data(){
        return {
            tmplate:"",
            mstAskTalkTmplateEntities:'',
            mstCodDEntityList:[],
            talkEntityList: [],
            askEntityList: [],
            useDiv1: '1',
            useDiv2: '1',
            updateTm: '',
            newFiles:[]
        }
    },
    updated: function () {
        if (this.useDiv1 === '0'){
            toBeDisabled('askItems');
        } else {
            toBeAlive('askItems');
        }
        if (this.useDiv2 === '0'){
            toBeDisabled('talkItems');
        } else {
            toBeAlive('talkItems');
        }
    },
    mounted:function () {
    },
    methods: {
        //textarea高度適応
        input: function (e){
            $(e).css("height", 'auto');
            $(e).css("height",$(e).scrollHeight);
        },
        focus: function (e){
            $(e).css("z-index",999).css("height",$(e).scrollHeight);
        },
        blur: function (e){
            $(e).css("height",$(window).height() * 0.03).css("overflow-x","hidden").css("z-index",0);
        }
    }
});
$(document).ready(function () {
    $("#showPath1").css("background", "white");
    ue = UE.getEditor('editor');
    ue.ready(function () {
        var tag = "<div id='img-mail-div'><img id='img-mail' src='../img/addMailAddr.png'></div>" + "<div style='display: none' id='mail-div'><input type='text' id='mail-input'> <button type='submit' id='mail-submit'>確認</button></div>";
        document.getElementById("edui2").insertAdjacentHTML("beforeEnd",tag);
        var time = 0;
        $("#img-mail-div").click(function () {
            time++;
            if (time % 2 === 0){
                $("#mail-div").css("display","none");
            } else {
                $("#mail-div").css("display","block");
            }
        });
        $("#mail-submit").click(function () {
            var text = ue.getContent() == ""?"<p></p>":ue.getContent();
            var mail = "mailto:" + $("#mail-input").val();
            text = text.replace("</p>",'&nbsp' + '<a class="mail" style="color: blue;text-decoration: underline;" href="' + mail + '">'+ $("#mail-input").val() + '</a>' + '&nbsp' + "</p>")
            ue.setContent(text);
        });
        ue.addListener('imgoversize', function (t, arg) {
            showMsg($.format($.msg.MSGCOMN0088, "テンプレート内容", "画像", "5M"));
        });
        //背景色
        var bgcolor;
        if ($("#ueditor_0").contents().find("p[data-background]").length > 1) {
            bgcolor = $("#ueditor_0").contents().find("p[data-background]").eq($("#ueditor_0").contents().find("p[data-background]").length - 1).attr("data-background").split(";")[2].split(":")[1].split(" ")[0];
            $("#ueditor_0").contents().find("body").css("background", bgcolor);
        } else if ($("#ueditor_0").contents().find("p[data-background]").length > 0) {
            bgcolor = $("#ueditor_0").contents().find("p[data-background]").eq(0).attr("data-background").split(";")[2].split(":")[1].split(" ")[0];
            $("#ueditor_0").contents().find("body").css("background", bgcolor);
        }

        $.ajax({
            url: ctxPath + '/manager/F08017/init',
            data:{
                id:param.id
            },
            datatype: 'json',
            type: 'GET',
            async: true,
            success: function (data) {
                if (data.code == 0) {
                    if (data.tmplate) {
                        updateModeFlag = true;
                        vm.tmplate = data.tmplate;
                        //カテゴリ名
                        $("#in_nm").val(vm.tmplate.ctgyNm);
                        //タイトル
                        $("#in_title").val(vm.tmplate.tmplateTitle);
                        var temp_cnt = decodeURIComponent(vm.tmplate.tmplateCnt);
                        if (temp_cnt == 'undefined') {
                            ue.setContent('');
                        } else {
                            ue.setContent(temp_cnt);
                        }
                        //添付ファイル
                        if(vm.tmplate.attachFilePath){
                            filePath = vm.tmplate.attachFilePath;
                            previewUrl = [];
                            for (var i = 0; i < filePath.split(",").length; i++) {
                                previewUrl.push(filePath.split(",")[i]);
                            }
                            let temp = filePath.split(',')
                            let filesName = "";
                            for (let item of temp) {
                                if (!!filesName){
                                    filesName += ', '
                                }
                                filesName += handleFileName(item)
                            }
                            $("#la_name").text(filesName);
                            firstClickFileButtonFlag = true;
                        }
                    }
                    if (data.updateTm) {
                        vm.updateTm = data.updateTm;
                    }
                    if (data.mstAskTalkTmplateEntities && data.mstAskTalkTmplateEntities.length > 0) {
                        data.mstAskTalkTmplateEntities.sort(function (a, b){
                            var itemType1 = a.itemTypeDiv;
                            var itemType2 = b.itemTypeDiv;
                            if (itemType1 === itemType2){
                                return a.askNum - b.askNum;
                            }else {
                                return itemType1 - itemType2;
                            }
                        })
                        vm.mstAskTalkTmplateEntities = data.mstAskTalkTmplateEntities;
                        for (var i = 0; i < vm.mstAskTalkTmplateEntities.length; i++) {
                            var askTalkEntity = vm.mstAskTalkTmplateEntities[i];
                            if (askTalkEntity.itemTypeDiv === '0'){
                                vm.askEntityList.push(askTalkEntity)
                                vm.useDiv1 = askTalkEntity.useDiv;
                            } else {
                                vm.talkEntityList.push(askTalkEntity)
                                vm.useDiv2 = askTalkEntity.useDiv;
                            }
                        }
                    }else {
                        for (var i = 0; i < 15; i++) {
                            var list = Math.floor(i / 10) === 0 ? vm.askEntityList : vm.talkEntityList;
                            list.push({
                                itemTypeDiv: Math.floor(i / 10) + '',
                                useDiv: '0',
                                askNum: (i % 10) + 1,
                                questionName: '',
                                answerTypeDiv: '',
                                optCont1: '',
                                optCont2: '',
                                optCont3: '',
                                optCont4: '',
                                optCont5: '',
                                optCont6: '',
                                optCont7: '',
                                optCont8: '',
                                optCont9: '',
                                optCont10: ''
                            })
                        }
                    }
                    if (data.mstCodDEntityList){
                        vm.mstCodDEntityList = data.mstCodDEntityList;
                    }
                } else {
                    showMsg(data.msg);
                    setTimeout(function () {
                        $(".topMessage").css("display","none");
                        $(".topMessage").removeClass("hasHeight");
                        $(".topMessage").html("");
                    },10000);
                }
            }
        });
    });
    $('#in_file').bind('click', function (){
        if (updateModeFlag && firstClickFileButtonFlag){
            filePath = "";
            $("#la_name").text("ドラッグ＆ドロッブ");
            firstClickFileButtonFlag = false;
        }
    })
    $("#in_file").bind('change',function (e) {
        // イベントが発生すると、後続の伝播を切断します
        e.stopPropagation();
        e.stopImmediatePropagation();
        previewUrl = [];
        vm.newFiles=[];
        if (this.files.length === 0){
            $("#la_name").text('ドラッグ＆ドロッブ');
            return;
        }
        // 添付したファイルは最大３件であること。
        if (this.files.length > 3){
            showErrorAndDeleteData(this, $.msg.MSGCOMN0086, "3");
            return;
        }
        let filesName = "";
        let recordSize;
        for (let file of this.files) {
            previewUrl.push(file.name);
            recordSize = file.size;
            // 容量は「250M」以上の場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
            if (recordSize > 250 * 1024 * 1024){
                showErrorAndDeleteData(this, $.msg.MSGCOMN0088,['お知らせ内容','添付ファイル','250M']);
                return;
            }
            if (recordSize.size === 0) {
                showErrorAndDeleteData(this, $.msg.MSGCOMN0075, [file.name]);
                return;
            }
            let ext = getFileSuffix(file);
            // ファイルの拡張子が「png」「jpg」「jpeg」「gif」「bmp」「pdf」「xlsx」「xls」「csv」以外の場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
            if (FILE_TYPE.indexOf(ext) === -1){
                showErrorAndDeleteData(this, $.msg.MSGCOMN0076, "「png/jpg/jpeg/gif/bmp/pdf/xlsx/xls/csv」");
                return;
            }
            if (!!filesName){
                filesName += ', '
            }
            filesName += file.name;
        }
        $("#la_name").text(" " + filesName);
        $(".topMessage").css("display", "None");
        /* 2021/3/3 manamiru1-557 cuikailin start */
        var file = $("#in_file")[0].files;
        for(var i=0;i<file.length;i++){
            (function (num){
                idx = file[num].name.lastIndexOf(".");
                if (idx != -1) {
                    ext = file[num].name.substr(idx + 1).toUpperCase();
                    ext = ext.toLowerCase();
                }
                if (IMG_TYPE.indexOf(ext)>-1) {
                    var f = file[num];
                    var src = window.URL.createObjectURL(new Blob([f], {type: 'image/*'}));
                    // 画像メタデータの読み取り：方向
                    var orient;
                    var image = new Image();
                    EXIF.getData(f, function () {
                        EXIF.getAllTags(this);
                        orient = EXIF.getTag(f, 'Orientation') ? EXIF.getTag(f, 'Orientation') : 1;
                        var reader = new FileReader();
                        reader.onload = function () {
                            image.src = src;
                            image.onload = function () {
                                var canvas = document.createElement("canvas");
                                var ctx = canvas.getContext("2d");
                                if(navigator.userAgent.indexOf("iPhone") === -1 || navigator.userAgent.indexOf("iPad") || navigator.userAgent.indexOf("Mac")){
                                    orient = 1;
                                }

                                changeCanvas(canvas, image.width, image.height, orient);
                                // 画像の向きの調整に関する問題
                                adjustImgOrientation(ctx, this, orient, image.width, image.height);
                                let newImage = canvas.toDataURL("image/jpeg", 0.1);

                                var arr = newImage.split(','), mime = arr[0].match(/:(.*?);/)[1];
                                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
                                while(n--){
                                    u8arr[n] = bstr.charCodeAt(n);
                                }
                                vm.newFiles.push(new File([u8arr], file[num].name, {type:mime}));
                            };
                        };
                        reader.readAsDataURL(f);
                    });
                }else {
                    vm.newFiles.push(file[num]);
                }
            })(i);
        }
        /* 2021/3/3 manamiru1-557 cuikailin end */
    })
    $("textarea").keyup(function () {
        $(".topMessage").hide();
    });
});

//プレビュー
function viewopen(){
    ue.ui._dialogs['previewDialog'].open();
}
var maxSize = 250 * 1024 * 1024;//250M
//登録
function save() {
    var askTalkEntityList = [];
    for(var askNumber = 0; askNumber < 15; askNumber++){
        var askTalkEntity = vm.$refs[Math.floor(askNumber / 10) + '.' + (askNumber % 10 + 1)][0].entity;
        if ((askTalkEntity.answerTypeDiv === '0' || askTalkEntity.answerTypeDiv === '3') && askTalkEntity.questionName === ''){
            showMsg($.format($.msg.MSGCOMD0001, "設問名"));
            return false;
        }
        if (askTalkEntity.answerTypeDiv === '1' || askTalkEntity.answerTypeDiv === '2'){
            var flg = false;
            for (var j = 1; j <= 10; j++){
                if (askTalkEntity['optCont' + j] !== null && askTalkEntity['optCont' + j] !== '' ){
                    flg = true;
                    break;
                }
            }
            if (!flg){
                showMsg($.format($.msg.MSGCOMN0050, "選択肢"));
                return false;
            }
            if (askTalkEntity.questionName === null || askTalkEntity.questionName === '') {
                showMsg($.format($.msg.MSGCOMD0001, "設問名"));
                return false;
            }
        }
        askTalkEntity.useDiv = Math.floor(askNumber / 10) === 0 ? vm.useDiv1 : vm.useDiv2;
        askTalkEntityList.push(askTalkEntity);
    }
    //カテゴリ名
    var nm = $("#in_nm").val();
    //タイトル
    var title = $("#in_title").val();
    //本文
    var cont = ue.getContent();
    //添付ファイル
    var files = vm.newFiles;
    if (nm.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "カテゴリ名"));
        return false;
    }
    if (nm.length > 60) {
        showMsg($.format($.msg.MSGCOMD0011, "カテゴリ名", "60"));
        return false;
    }
    if (title.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "タイトル"));
        return false;
    }
    if (nm.length > 100) {
        showMsg($.format($.msg.MSGCOMD0011, "タイトル", "100"));
        return false;
    }
    if (ue.getContentTxt().length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "本文"));
        return false;
    }
    if (ue.getContentTxt().length > 10000) {
        showMsg($.format($.msg.MSGCOMD0011, "本文", "10000"));
        return false;
    }
    for (let file of files) {
        //添付ファイル
        if (file != null) {
            if (file.size > maxSize) {
                showMsg($.format($.msg.MSGCOMN0077, 'ファイルの最大サイ'));
                return false;
            }
            if (file.size === 0) {
                showMsg($.format($.msg.MSGCOMN0075, file.name));
                return false;
            }
            let ext = getFileSuffix(file);
            if (FILE_TYPE.indexOf(ext) === -1) {
                showMsg($.format($.msg.MSGCOMN0076, '「png/jpg/jpeg/gif/bmp/pdf/xlsx/xls/csv」'));
                return false;
            }
        }
    }
    var formData = new FormData();
    if(param.id){
        formData.append('id',param.id);
        formData.append('updDatime', vm.updateTm);
    }
    formData.append('ctgyNm', encodeURIComponent(nm).replace(/\%20/g," "));
    formData.append('tmplateTitle', encodeURIComponent(title).replace(/\%20/g," "));
    formData.append('tmplateCnt', encodeURIComponent(cont).replace(/\%20/g," "));
    formData.append('filePaths', filePath);
    for (let item of files) {
        formData.append("file", item);
    }
    formData.append("mstAskTalkTmplateList",JSON.stringify(askTalkEntityList));
    //登録の場合
    if (param.id == null) {
        var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                layer.close(index);
            },
            btn2: function () {
                $.ajax({
                    url: ctxPath + '/manager/F08017/add',
                    type: 'POST',
                    datatype: 'json',
                    data: formData,
                    async: false,
                    cache:false,
                    contentType:false,
                    processData: false,
                    success: function (data) {
                        if (data.code != 0) {
                            showMsg(data.msg);
                            layer.close(index);
                        } else {
                            // var idx = layer.alert($.format($.msg.MSGCOMN0022, "登録"), {
                            //     yes: function () {
                                    window.location.href = "./F08016.html";
                            //         layer.close(idx);
                            //     }
                            // });
                        }
                    }
                });
            }
        });
    } else {
        //更新の場合
        var index = layer.confirm($.format($.msg.MSGCOMN0021, "更新"), {
            title: '確認',
            closeBtn: 0,
            shadeClose: false,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                layer.close(index);
            },
            btn2: function () {
                $.ajax({
                    url: ctxPath + '/manager/F08017/update',
                    type: 'POST',
                    datatype: 'json',
                    data: formData,
                    async: false,
                    cache:false,
                    contentType:false,
                    processData: false,
                    success: function (data) {
                        if (data.code != 0) {
                            showMsg(data.msg);
                            layer.close(index);
                        } else {
                            // var idx = layer.alert($.format($.msg.MSGCOMN0022, "更新"), {
                            //     yes: function () {
                                    window.location.href = "./F08016.html";
                            //         layer.close(idx);
                            //     }
                            // });
                        }
                    }
                });
            }
        });
    }
}
function showErrorAndDeleteData(dom, msg, params) {
    $("#la_name").text("ドラッグ＆ドロッブ");
    showMsg($.format(msg, params));
    dom.value = ''
}
function F_Open_dialog() {
    document.getElementById("in_file").click();
}
/* 2021/3/3 manamiru1-557 cuikailin start */
function adjustImgOrientation(ctx, img, orientation, width, height) {
    switch (orientation) {
        case 3:
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 6:
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 8:
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        case 2:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.drawImage(img, 0, 0, width, height);
            break;
        case 4:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 5:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 7:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        default:
            ctx.drawImage(img, 0, 0, width, height);
    }
}

function changeCanvas(canvas,width,height,orientation) {
    switch (orientation) {
        case 6:
            canvas.width = height;
            canvas.height = width;
            break;
        case 8:
            canvas.width = height;updateGakkenIDBulk
            canvas.height = width;
            break;
        case 5:
            canvas.width = height;
            canvas.height = width;
            break;
        case 7:
            canvas.width = height;
            canvas.height = width;
            break;
        default:
            canvas.width = width;
            canvas.height = height;
    }
}
/* 2021/3/3 manamiru1-557 cuikailin end */
function toBeDisabled(tag) {
    $('.' + tag + '').find('.questionName').attr("disabled",true).css("background","rgb(170, 170, 170)");
    $('.' + tag + '').find('.textarea').attr("disabled",true).css("background","rgb(170, 170, 170)");
    $('.' + tag + '').find('.answerTypeDiv').attr("disabled",true).css("background","grey");
    $('.' + tag + '').find('.add-option').attr("disabled",true).css("background","rgb(170, 170, 170)");
}
function toBeAlive(tag) {
    $('.' + tag + '').find('.questionName').removeAttr("disabled").css("background","white");
    $('.' + tag + '').find('.textarea').removeAttr("disabled").css("background","white");
    $('.' + tag + '').find('.answerTypeDiv').removeAttr("disabled").css("background","white");
    $('.' + tag + '').find('.add-option').removeAttr("disabled").css("background","white");
}
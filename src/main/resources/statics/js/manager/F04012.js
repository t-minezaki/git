//UEditor
var ue;
//組織ノード
var nodes = [];
//最大ファイルメモリ
var maxSize = 250 * 1024 * 1024;//250M
//一致文字列
var rgx= "(.png|.jpg|.jpeg|.gif|.bmp|.pdf|.xlsx|.xls|.csv|.pptx|.docx)$";
var re = new RegExp(rgx);
//前の画面からパラメーターを取得する
var params = getParam();
var previewUrl;
var updateModeFlag = false;
var firstClickFileButtonFlag = true;
var filePath;
var IMG_TYPE = ["png", "jpg", "jpeg", "gif", "bmp"];
//vue
var vm = new Vue({
    el: ".page",
    data: {
        org: '',
        noticeEntity: '',
        content:null,
        mstTmplateEntityList: [],
        newFiles:[]
    },
    mounted: function () {
        $("#temp_nm").select2();
        //初期化
        this.setUp();
    },
    methods: {
        setUp: function () {
            //「ファイル選択」ボタンクリックイベント
            $('#btn_file').change(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                var fileList = $(this)[0].files;
                var totalSize = 0;
                var str = '';
                var len = fileList.length;
                //選択したファイルの数が3を超えています
                if (len > 3){
                    var intervalID = setInterval(function () {
                        if ($("#message") != null) {
                            clearInterval(intervalID);
                        }
                        showMsg($.format($.msg.MSGCOMN0086, "3"));
                    }, 500);
                    $("#showPath1").attr("value", '');
                    return;
                }
                previewUrl = [];
                vm.newFiles = [];
                //配列を反復処理する
                for (var i = 0; i < len; i++){
                    previewUrl.push(fileList[i].name);
                    //選択したファイルのサフィックスが正しくありません
                    if (!re.test(fileList[i].name)){
                        var intervalID = setInterval(function () {
                            if ($("#message") != null) {
                                clearInterval(intervalID);
                            }
                            showMsg($.format($.msg.MSGCOMN0076, "「png/jpg/jpeg/gif/bmp/pdf/xlsx/xls/csv/pptx/docx」"));
                        }, 500);
                        $("#showPath1").attr("value", '');
                        return;
                    }else {
                        //ファイルサイズは0です
                        if (fileList[i].size == 0){
                            var intervalID = setInterval(function () {
                                if ($("#message") != null) {
                                    clearInterval(intervalID);
                                }
                                showMsg($.format($.msg.MSGCOMN0075, fileList[i].name));
                            }, 500);
                            $("#showPath1").attr("value", '');
                            return;
                        }
                        totalSize += fileList[i].size;
                        //ファイルサイズが250Mを超えています
                        if (totalSize > maxSize){
                            var intervalID = setInterval(function () {
                                if ($("#message") != null) {
                                    clearInterval(intervalID);
                                }
                                showMsg($.format($.msg.MSGCOMN0088, "教育コラム内容", "添付ファイル", "250M"));
                            }, 500);
                            $("#showPath1").attr("value", '');
                            return;
                        }
                        //ステッチングファイル名
                        str = str + fileList[i].name + ',';
                    }
                }
                //選択したファイル名を表示
                $("#showPath1").val(str.substring(0, str.length - 1));
                /* 2021/3/3 manamiru1-557 cuikailin start */
                var file = $("#btn_file")[0].files;
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
                                var ratio;
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
                                        // let newImage = cropImage(image, {
                                        //     width:image.width,
                                        //     height:image.height,
                                        // });
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
            });
            //ueエディターを構成する
            ue = UE.getEditor('editor');
            $("#editor").click(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
            //ueエディターを構成する
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
                //画像サイズが5Mを超える
                ue.addListener("imgoversize", function () {
                    showMsg($.format($.msg.MSGCOMN0088, "掲示板内容", "画像", "5M"));
                    return;
                });
                //他のイベント発生する時、エラーメッセージをクリアしてください。
                ue.addListener("click", function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                });
                //ueコンテンツ変更イベント
                ue.addListener("contentChange", function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                });
                var timer = setInterval(function(){
                    if (vm.content !== null){
                        ue.setContent(vm.content);
                        clearInterval(timer);
                    }
                }, 500);
            });
            //「プレビュー」ボタンのクリックイベント
            $("#cpop").click(function () {
                $("#message").hide();
                // ue.execCommand('preview');
                ue.ui._dialogs['previewDialog'].open();
            });
            //laydate
            laydate.render({
                elem: '#start_date',
                eventElem: '#timeOne',
                trigger: 'click',
                type: 'datetime',
                format: 'yyyy/MM/dd HH:mm',
                done: function (value, date) {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                    $("#start_date").val(value);
                }
            });
            //laydate
            laydate.render({
                elem: '#end_date',
                eventElem: '#timeTwo',
                trigger: 'click',
                type: 'datetime',
                format: 'yyyy/MM/dd HH:mm',
                done: function (value, date) {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                    $("#end_date").val(value);
                }
            });
            //「すべて選択」クリックイベント
            $("#selectAll").click(function () {
                var flag = document.getElementById("selectAll").checked;
                if (flag) {
                    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    nodes = treeObj.getCheckedNodes(true);
                } else {
                    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
                }
                vm.onCheck();
            });
            //戻るボタン押下
            $("#backBtn").click(function () {
                window.location.href = './F04011.html';
            });
            //登録ボタン押下
            $("#saveBtn").click(function () {
                var formData = new FormData();
                var object = {};
                //ファイル
                var fileList = vm.newFiles;
                //画面入力した項目をチェックし、エラーがある場合
                if ($("#mail_name").val().length == 0) {
                    showMsg($.format($.msg.MSGCOMD0001, "タイトル"));
                    return;
                }
                //タイトルが50より長い
                if ($("#mail_name").val().length > 50) {
                    showMsg($.format($.msg.MSGCOMD0011, "件名", "50"));
                    return;
                }
                //コンテンツの長さは0です
                if (ue.getContentTxt().length == 0) {
                    showMsg($.format($.msg.MSGCOMD0001, "本文"));
                    return;
                }
                //コンテンツの長さが10000を超えています
                if (ue.getContentTxt().length > 10000) {
                    showMsg($.format($.msg.MSGCOMD0011, "内容", "10000"));
                    return;
                }
                var orgList = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
                //配信先未指定チェック
                if (orgList.length == 0){
                    showMsg($.format($.msg.MSGCOMN0096, "配信先対象", "配信先設定"));
                    return;
                }
                var orgIdList = [];
                for (var i = 0; i < orgList.length; i++) {
                    orgIdList.push(orgList[i].orgId);
                }
                //お知らせタイトル
                var noticeTitle = $("#mail_name").val();
                //お知らせ内容
                var noticeCont = ue.getContent();
                //お知らせレベル区分
                var noticeLevelDiv = '';
                var tempId = $("#temp_nm").val();
                var label = document.getElementById("checkbox").checked;
                if (label) {
                    noticeLevelDiv = '2';
                } else {
                    noticeLevelDiv = '1';
                }
                //掲載開始日時
                var pubPlanStartDt = $("input[name=blockStartDate]").val();
                if (pubPlanStartDt == '') {
                    showMsg($.format($.msg.MSGCOMD0001, "掲載開始日時"));
                    return;
                }
                //掲載終了日時
                var pubPlanEndDt = $("input[name=blockEndDate]").val();
                if (pubPlanEndDt == '') {
                    showMsg($.format($.msg.MSGCOMD0001, "掲載終了日時"));
                    return;
                }

                if (pubPlanEndDt < pubPlanStartDt) {
                    showMsg($.format($.msg.MSGCOMN0048, "掲載終了日時", "掲載開始日時"));
                    return;
                }
                var allDeliverFlg = '0';
                if (document.getElementById("selectAll").checked) {
                    allDeliverFlg = '1';
                } else {
                    allDeliverFlg = '0';
                }
                //title
                object.title = encodeURIComponent(noticeTitle).replace(/\%20/g, " ");
                //content
                object.content = encodeURIComponent(noticeCont).replace(/\%20/g, " ");
                //files
                for (var index = 0,len = fileList.length; index < len; index++) {
                    formData.append("files", fileList[index], fileList[index].name);
                }
                //notice level
                object.noticeLevel = noticeLevelDiv;
                //public plan start date
                object.startDate = pubPlanStartDt;
                //public plan end date
                object.endDate = pubPlanEndDt;
                //allDeliverFlg
                object.allDeliverFlg = allDeliverFlg;
                //org id list
                object.orgIdList = orgIdList;
                object.filePaths = filePath;
                object.tempId = tempId;
                formData.append("f04012Dto", JSON.stringify(object));
                //create msg
                var msg = '';
                if (params.id){
                    msg = $.format($.msg.MSGCOMN0021, "編集");
                }else {
                    msg = $.format($.msg.MSGCOMN0021, "登録");
                }
                var index = layer.confirm(msg, {
                    title: '確認',
                    closeBtn: 0,
                    shadeClose: false,
                    btn: ['キャンセル', '確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                    btn2: function () {
                        var id = -1;
                        if (params.id){
                            id = params.id;
                            formData.append("lastUpdateTime", vm.noticeEntity.updDatime);
                        }
                        formData.append("id", id);
                        $.ajax({
                            url: ctxPath + '/manager/F04012/saveOrUpdate',
                            cache: false,
                            data: formData,
                            type: 'POST',
                            processData: false,
                            contentType: false,
                            success: function (data) {
                                if (data.code != 0) {
                                    showMsg(data.msg);
                                    layer.close(index);
                                } else {
                                    //create msg
                                    var msg = '';
                                    // if (params.id){
                                    //     msg = $.format($.msg.MSGCOMN0022, "編集");
                                    // }else {
                                    //     msg = $.format($.msg.MSGCOMN0022, "登録");
                                    // }
                                    // var idx = layer.alert(msg, {
                                    //     yes: function () {
                                            window.location.href = "./F04011.html";
                                    //         layer.close(idx);
                                    //     }
                                    // });
                                }
                            }
                        });
                    }
                });
            });

            var id = -1;
            //編集
            if (params.id){
                id = params.id;
                $('#saveBtn').html('登録');
            }

            $.ajax({
                url: ctxPath + '/manager/F04012/init',
                type: 'GET',
                async: true,
                data: {
                    id: id
                },
                datatype: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.org) {
                            vm.org = data.org;
                        }
                        if (data.orgIdList) {
                            zNodes = data.orgIdList;
                            if (zNodes != 1){
                                $("#group").attr("disabled", "disabled").css("background-color", "#EEEEEE");
                            }
                            vm.createUnitTree(zNodes);
                            // 2021/1/4 huangxinliang modify start
                            if (id !== -1 && data.chosenOrgIdList){
                                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                var flg = document.getElementById("selectAll");
                                for (var i = 0; i < data.chosenOrgIdList.length; i++) {
                                    var nodess = treeObj.getNodeByParam("orgId", data.chosenOrgIdList[i]);
                                    // NWT　楊　2021/08/11　MANAMIRU1-761　Edit　Start
                                    if(nodess != null){
                                        treeObj.checkNode(nodess);
                                    }
                                    // NWT　楊　2021/08/11　MANAMIRU1-761　Edit　End
                                }
                                flg.checked = data.orgIdList.length === data.chosenOrgIdList.length;
                            }else {
                                $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                            }
                            // 2021/1/4 huangxinliang modify end
                        }
                        vm.onCheck();
                        if (data.notice){
                            vm.noticeEntity = data.notice;
                            //title
                            $("#mail_name").val(decodeURIComponent(data.notice.noticeTitle));
                            //level
                            document.getElementById("checkbox").checked = data.notice.noticeLevelDiv === '2';
                            //content
                            vm.content = decodeURIComponent(data.notice.noticeCont);
                            //start date
                            $("input[name=blockStartDate]").val(new Date(data.notice.pubPlanStartDt).format('Y/m/d H:i'));
                            //end date
                            $("input[name=blockEndDate]").val(new Date(data.notice.pubPlanEndDt).format('Y/m/d H:i'));
                            //select all
                            document.getElementById("selectAll").checked = data.notice.allDeliverFlg === '1';
                            //update time
                            $('#latest_update_time').html(new Date(data.notice.updDatime).format('Y/m/d H:i'));
                            $('#update_time_area').css('visibility', 'visible');
                            //file
                            $("#showPath1").attr("value", vm.handleFileName(data.notice.attachFilePath));
                            if (data.notice.attachFilePath) {
                                previewUrl = [];
                                filePath = data.notice.attachFilePath;
                                for (var i = 0; i < data.notice.attachFilePath.split(",").length; i++) {
                                    previewUrl.push(data.notice.attachFilePath.split(",")[i]);
                                }
                            }
                        }
                        if (data.mstTmplateEntityList){
                            vm.mstTmplateEntityList = data.mstTmplateEntityList;
                        }
                        updateModeFlag = true;
                        firstClickFileButtonFlag = true;
                    } else {
                        layer.alert(data.msg);
                    }
                }
            });
        },
        // create ztree
        createUnitTree: function (unitTrees) {
            $("#treeDemo").html(""); // clear
            var setting = {
                view: {
                    selectedMulti: true,
                    // expandAll: true,
                    showIcon: false,
                    expandSpeed: "fast"
                    // open: true
                },
                check: {
                    enable: true,
                    chkboxType: {"Y": "s", "N": "s"}
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "orgId",
                        pIdKey: "upplevOrgId",
                        rootPId: null
                    },
                    key: {
                        name: "orgNmDisplay"
                    }
                },
                callback: {
                    onCheck: vm.onCheck
                }
            };
            var treeNodes = unitTrees;
            $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.transformToArray(treeObj.getNodes());
            treeObj.expandNode(nodes[0],true);
        },
        onCheck: function () {
            //ztree オブジェクトを取得
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = treeObj.getCheckedNodes(true);
            //find parents' nodes
            var node = treeObj.getNodes();
            // find all nodes
            unCheckNodes = treeObj.transformToArray(node);
            var flg = document.getElementById("selectAll");
            if (unCheckNodes.length != nodes.length) {
                flg.checked = false;
            } else {
                flg.checked = true;
            }
        },
        handleFileName: function (fileName) {
            var newStr = "";
            //分割ファイル名
            var files = fileName.split(',../');
            for (var i = 0, len = files.length; i < len; i++){
                files[i] = files[i].slice(files[i].lastIndexOf('\\') + 1);
                files[i] = files[i].slice(files[i].lastIndexOf('/') + 1);
                files[i] = files[i].replace(/(\d{17})\./, '.');
                newStr += files[i];
                if (i != len - 1){
                    newStr = newStr + ',';
                }
            }
            return newStr;
        }
    }
});

function F_Open_dialog() {
    document.getElementById("btn_file").click();
}
//適用ボタン押下
$("#apply_btn").click(function () {
    ue = UE.getEditor('editor');
    var tempTitle = $("#select2-temp_nm-container").text();
    var tempId = $("#temp_nm").val();
    if (tempTitle.length <= 0) {
        showMsg($.format($.msg.MSGCOMN0099, "適用", "テンプレート"));
        return;
    }
    $.ajax({
        url: ctxPath + '/manager/F05002/tempFind',
        type: "GET",
        data: {
            tempId: tempId
        },
        async: true,
        datatype: "json",
        success: function (data) {
            if (data.mstTmplateEntity){
                var entity = data.mstTmplateEntity;
                $("#mail_name").val(entity.tmplateTitle);
                var temp_cnt = decodeURIComponent(entity.tmplateCnt);
                if (temp_cnt == 'undefined') {
                    ue.setContent('');
                } else {
                    ue.setContent(temp_cnt);
                }
                //添付ファイル
                if(entity.attachFilePath){
                    filePath = entity.attachFilePath;
                    previewUrl = [];
                    for (var i = 0; i < filePath.split(",").length; i++) {
                        previewUrl.push(filePath.split(",")[i]);
                    }
                    var temp = filePath.split(',')
                    var filesName = "";
                    for (var item of temp) {
                        if (!!filesName){
                            filesName += ', '
                        }
                        filesName += handleFileName(item)
                    }
                    $("#showPath1").val(filesName);
                    firstClickFileButtonFlag = true;
                }else {
                    $("#showPath1").val("ドラッグ＆ドロッブ");
                    filePath = "";
                }
            } else {
                showMsg(data.msg);
            }
        }
    })
});
$('#file_btn').bind('click', function (){
    if (updateModeFlag && firstClickFileButtonFlag){
        filePath = "";
        $("#showPath1").val("ドラッグ＆ドロッブ");
        firstClickFileButtonFlag = false;
    }
});
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
            canvas.width = height;
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
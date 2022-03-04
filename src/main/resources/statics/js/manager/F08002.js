var ue;
var param = getParam();
var filePath = "";
var previewUrl;
var FILE_TYPE = ["png", "jpg", "jpeg", "gif", "bmp", "pdf","pptx","docx", "xlsx", "xls", "csv"];
var updateModeFlag = false;
var firstClickFileButtonFlag = true;
var IMG_TYPE = ["png", "jpg", "jpeg", "gif", "bmp"];
// create ztree
function geneUnitTree(unitTrees) {
    $("#treeDemo").html(""); // clear
    var setting = {
        view: {
            selectedMulti: true,
            expandAll: true,
            showIcon: false,
            expandSpeed: "fast"
            // open: true
        },
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
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
            onCheck: onCheck
        }
    };
    var treeNodes = unitTrees;
    $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(true);
}

function onCheck() {
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
}

var vm = new Vue({
    el: ".main",
    data: {
        mstEventEntity: '',
        mstTmplateEntity: '',
        updateStrForCheck: '',
        mstTmplateEntityList: [],
        orgIdList: [],
        mstEventDeliverEntityList: [],
        mstAskTalkEntities: '',
        mstCodDEntityList: [],
        selectObject: '0',
        unitTime: 30,
        talkEntityList: [],
        askEntityList: [],
        useDiv1: '1',
        useDiv2: '1',
        activeFlg: true,
        unitTimeDisabled: true,
        activeFlg: true,
        newFiles:[]
    },
    updated: function () {
        $("textarea").keyup(function () {
            $("#message").hide();
        });
        $("input").keyup(function () {
            $("#message").hide();
        });
        $("input").change(function () {
            $("#message").hide();
        });
        $("select").change(function () {
            $("#message").hide();
        });
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
        ue = UE.getEditor('editor');
        ue.ready(function () {
            ue.addListener('imgoversize', function (t, arg) {
                showMsg($.format($.msg.MSGCOMN0088, "イベント内容", "画像", "5M"));
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
        });
        $("#cpop").click(function () {
            $("#message").hide();
            ue.ui._dialogs['previewDialog'].open();
        });
    },
    mounted: function () {
        this.init();
        $("#temp_nm").select2();
        this.getInfo();
    },
    methods: {
        init: function (){
            $("#showPath1").css("background", "white");
            $('#btn_file').bind('click', function (){
                if (updateModeFlag && firstClickFileButtonFlag){
                    filePath = "";
                    $("#showPath1").val("");
                    firstClickFileButtonFlag = false;
                }
            })
            $('#btn_file').change(function (e) {
                // イベントが発生すると、後続の伝播を切断します
                e.stopPropagation();
                e.stopImmediatePropagation();
                previewUrl = [];
                vm.newFiles=[];
                if (this.files.length === 0){
                    $("#showPath1").val('');
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
                        showErrorAndDeleteData(this, $.msg.MSGCOMN0076, "「png/jpg/jpeg/gif/bmp/pdf/xlsx/xls/csv/pptx/docx」");
                        return;
                    }
                    if (!!filesName){
                        filesName += ', '
                    }
                    filesName += file.name;
                }
                $("#showPath1").val(" " + filesName);
                $(".topMessage").css("display", "None");
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
            });
            ue = UE.getEditor('editor');
            $("#editor").click(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
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
                    showMsg($.format($.msg.MSGCOMN0088, "イベント内容", "画像", "5M"));
                });
                //他のイベント発生する時、エラーメッセージをクリアしてください。
                ue.addListener("click", function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                });
                ue.addListener("contentChange", function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                });
            });
            $("#selectAll").click(function () {
                var flag = document.getElementById("selectAll").checked;
                if (flag) {
                    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                } else {
                    $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
                }
                onCheck();
            });
        },
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F08002/init',
                type: "GET",
                data: {
                    id: param.id,
                    type: param.type,
                    templateNm: $("#temp_nm").val()
                },
                async: true,
                datatype: "json",
                success: function (data) {
                    if (data.mstEventEntity) {
                        updateModeFlag = true;
                        vm.mstEventEntity = data.mstEventEntity;
                        if (param.id != null && param.id != 'undefined') {
                            $("#event_category").val(vm.mstEventEntity.ctgyNm);
                            $("#event_title").val(vm.mstEventEntity.eventTitle);
                            var temp_cnt = decodeURIComponent(vm.mstEventEntity.eventCont);
                            ue = UE.getEditor('editor');
                            ue.ready(function () {
                                if (temp_cnt == 'undefined') {
                                    ue.setContent('');
                                } else {
                                    ue.setContent(temp_cnt);
                                }
                            })
                            vm.selectObject = vm.mstEventEntity.refType;
                            vm.unitTime = vm.mstEventEntity.unitTime;
                            if (vm.mstEventEntity.attachFilePath) {
                                filePath = vm.mstEventEntity.attachFilePath;
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
                                $("#showPath1").val(filesName);
                                firstClickFileButtonFlag = true;
                            }
                        }
                    }
                    if (data.updateStrForCheck) {
                        vm.updateStrForCheck = data.updateStrForCheck;
                    }
                    if (data.mstTmplateEntityList) {
                        vm.mstTmplateEntityList = data.mstTmplateEntityList;
                    }
                    if (data.orgIdList) {
                        vm.orgIdList = data.orgIdList;
                        //ztreeの初期化
                        geneUnitTree(vm.orgIdList);
                    }
                    if (data.mstEventDeliverEntityList) {
                        vm.mstEventDeliverEntityList = data.mstEventDeliverEntityList;
                    }
                    // 2020/11/24 huangxinliang modify start
                    if (data.mstAskTalkEntities && data.mstAskTalkEntities.length > 0) {
                        vm.mstAskTalkEntities = data.mstAskTalkEntities;
                        vm.initAskTalk();
                    }else {
                        vm.generateAskTalkItem();
                    }
                    // 2020/11/24 huangxinliang modify end
                    if (data.mstCodDEntityList) {
                        vm.mstCodDEntityList = data.mstCodDEntityList;
                    }
                    if (data.activeFlg === false){
                        vm.activeFlg = data.activeFlg;
                        $("#selectAll").addClass("noFocus");
                        $("#treeDemo_1").addClass("noFocus");
                        $("input[name='dan']").addClass("noFocus");
                        $(".select2-selection").addClass("noFocus");
                        $(".edui-editor").addClass("noFocus");
                        $("#saveBtn").addClass("noFocus");
                    }
                    //新規の場合
                    if (param.id == null) {
                        $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                    } else {
                        //編集の場合
                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                        var flg = document.getElementById("selectAll");
                        for (var i = 0; i < data.mstEventDeliverEntityList.length; i++) {
                            var nodess = treeObj.getNodeByParam("orgId", data.mstEventDeliverEntityList[i].orgId);
                            // NWT　楊　2021/08/12　MANAMIRU1-761　Edit　Start
                            if (nodess != null){
                                treeObj.checkNode(nodess);
                            }
                            // NWT　楊　2021/08/12　MANAMIRU1-761　Edit　End
                        }
                        if (data.orgIdList.length == data.mstEventDeliverEntityList.length) {
                            flg.checked = true;
                        } else {
                            flg.checked = false;
                        }
                    }
                }
            });
        },
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
        },
        initAskTalk: function () {
            vm.mstAskTalkEntities.sort(function (a, b){
                var itemType1 = a.itemTypeDiv;
                var itemType2 = b.itemTypeDiv;
                if (itemType1 === itemType2){
                    return a.askNum - b.askNum;
                }else {
                    return itemType1 - itemType2;
                }
            });
            vm.askEntityList = [];
            vm.talkEntityList = [];
            for (var i = 0; i < vm.mstAskTalkEntities.length; i++) {
                var askTalkEntity = vm.mstAskTalkEntities[i];
                if (askTalkEntity.itemTypeDiv === '0'){
                    vm.askEntityList.push(askTalkEntity);
                    vm.useDiv1 = askTalkEntity.useDiv;
                } else {
                    vm.talkEntityList.push(askTalkEntity);
                    vm.useDiv2 = askTalkEntity.useDiv;
                }
            }
        },
        getMstAskTalkList: function (number, type, useDiv) {
            var ask = $("." + type + number);
            var itemTypeDiv = type === 'talk' ? '1' : '0';
            var mstAskTalk = {
                id: ask.find(".hidden").val().split(",")[0],
                questionName:  ask.find("select").val()==""?"":ask.find(".questionName").val().trim(),
                answerTypeDiv: ask.find("select").val(),
                optCont1: ask.find("select").val()==""?"":$(ask.find("textarea")[1]).val().trim(),
                optCont2: ask.find("select").val()==""?"":$(ask.find("textarea")[2]).val().trim(),
                optCont3: ask.find("select").val()==""?"":$(ask.find("textarea")[3]).val().trim(),
                optCont4: ask.find("select").val()==""?"":$(ask.find("textarea")[4]).val().trim(),
                optCont5: ask.find("select").val()==""?"":$(ask.find("textarea")[5]).val().trim(),
                askNum: number,
                useDiv: useDiv,
                itemTypeDiv: itemTypeDiv,
                updDatime: ask.find(".hidden").val().split(",")[1]
            };
            return mstAskTalk;
        },
        // 2020/11/24 huangxinliang modify start
        generateAskTalkItem: function (){
            vm.askEntityList = [];
            vm.talkEntityList = [];
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
        // 2020/11/24 huangxinliang modify end
    },
    computed:{
        classOfUnitTime: {
            get(){
                // 2021/1/26 huangxinliang modify start
                return (this.selectObject === '2' || this.selectObject === '0' || this.activeFlg === false) ? 'disabled' : 'active';
                // 2021/1/26 huangxinliang modify end
            }
        },
        isBanned:{
            get(){
                // 2020/12/2 huangxinliang modify start
                return (this.selectObject === '2' || this.activeFlg === false) ? true : false;
                // 2020/12/2 huangxinliang modify end
            }
        }
    },
    watch: {
        selectObject: function (newValue, oldValue){
            // 2021/1/26 huangxinliang modify start
            this.unitTimeDisabled = (newValue === '0' || newValue === '2');
            // 2021/1/26 huangxinliang modify end
            this.selectObject = newValue;
        }
    }
});

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
        url: ctxPath + '/manager/F08002/tempFind',
        type: "GET",
        data: {
            tempId: tempId
        },
        async: true,
        datatype: "json",
        success: function (data) {
            if (data.mstTmplateEntity) {
                $('#btn_file').val('');
                $('#showPath1').val('');
                updateModeFlag = true;
                vm.mstTmplateEntity = data.mstTmplateEntity;
                $("#event_category").val(vm.mstTmplateEntity.ctgyNm);
                $("#event_title").val(vm.mstTmplateEntity.tmplateTitle);
                var temp_cnt = decodeURIComponent(vm.mstTmplateEntity.tmplateCnt);
                ue.ready(function () {
                    if (temp_cnt == 'undefined') {
                        ue.setContent('');
                    } else {
                        ue.setContent(temp_cnt);
                    }
                })
                if (vm.mstTmplateEntity.attachFilePath) {
                    filePath = vm.mstTmplateEntity.attachFilePath;
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
                    $("#showPath1").val(filesName);
                    firstClickFileButtonFlag = true;
                } else {
                    sessionStorage.removeItem("paths")
                }
                // 2020/11/24 huangxinliang modify start
                if (data.mstAskTalkEntities && data.mstAskTalkEntities.length > 0) {
                    vm.mstAskTalkEntities = data.mstAskTalkEntities;
                    vm.initAskTalk();
                }else {
                    vm.generateAskTalkItem();
                }
                // 2020/11/24 huangxinliang modify end
            } else {
                showMsg(data.msg);
            }
        }
    });
});

var maxSize = 250 * 1024 * 1024;//250M
//登録ボタン押下
$("#saveBtn").click(function () {
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
    /* 2020/12/29 V9.0 cuikailin add start */
    if(vm.selectObject == '2'){
        var flag = 0;
        for(var i=0;i<askTalkEntityList.length;i++){
            if(askTalkEntityList[i].questionName == ""){
                flag++;
            }
        }
        if (flag == 15) {
            showMsg($.format($.msg.MSGCOMN0050, "アンケートの質問"));
            return;
        }
    }
    /* 2020/12/29 V9.0 cuikailin add end */
    var formData = new FormData();
    var object = {};

    //カテゴリ
    var categoryNm = $("#event_category").val();
    //イベントタイトル
    var eventTitle = $("#event_title").val();
    //イベント内容
    var eventCont = ue.getContent();
    //ファイル
    var files = vm.newFiles;

    //画面入力した項目をチェックし、エラーがある場合
    if (categoryNm.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "カテゴリ"));
        return;
    }
    if (categoryNm.length > 60) {
        showMsg($.format($.msg.MSGCOMD0011, "カテゴリ", "60"));
        return;
    }
    if (eventTitle.length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "タイトル"));
        return;
    }
    if (eventTitle.length > 100) {
        showMsg($.format($.msg.MSGCOMD0011, "タイトル", "100"));
        return;
    }
    if (ue.getContentTxt().length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "本文"));
        return;
    }
    if (ue.getContentTxt().length > 10000) {
        showMsg($.format($.msg.MSGCOMD0011, "本文", "10000"));
        return;
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
                showMsg($.format($.msg.MSGCOMN0076, '「png/jpg/jpeg/gif/bmp/pdf/xlsx/xls/csv/pptx/docx」'));
                return false;
            }
        }
    }
    //配信先組織リスト
    var selectOrgList = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
    //配信先未指定チェック
    if (selectOrgList.length === 0) {
        showMsg($.format($.msg.MSGCOMN0087, "配信先組織", "配信先組織"));
        return;
    }

    object.id = param.id;
    object.tempId = vm.mstTmplateEntity.id;
    object.ctgyNm = encodeURIComponent(categoryNm).replace(/\%20/g, " ");
    object.eventTitle = encodeURIComponent(eventTitle).replace(/\%20/g, " ");
    object.eventCont = encodeURIComponent(eventCont).replace(/\%20/g, " ");
    object.refType = vm.selectObject;
    object.unitTime = vm.unitTime;
    object.updDatime = vm.mstEventEntity.updDatime;
    object.updateStrForCheck = vm.updateStrForCheck;
    object.orgIdList = selectOrgList;
    formData.append("mstAskTalkList", JSON.stringify(askTalkEntityList));
    formData.append("f08002Dto", JSON.stringify(object));
    formData.append("filePaths", filePath);
    for (let item of files) {
        formData.append("file", item);
    }
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
                    url: ctxPath + '/manager/F08002/edit',
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
                            window.location.href = "./F08001.html";
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
                    url: ctxPath + '/manager/F08002/edit',
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
                            window.location.href = "./F08001.html";
                        }
                    }
                });
            }
        });
    }
});
$("#turn_back").click(function () {
    window.location.href = "./F08001.html";
});
//textarea高度適応
$('.textarea').each(function () {
    this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
}).on('input', function () {
    this.style.height = 'auto';
    this.style.height = (this.scrollHeight) + 'px';
}).focus(function () {
    $(this).css("z-index",999).css("height",this.scrollHeight);
}).blur(function () {
    $(this).css("height","3vh").css("overflow-x","hidden").css("z-index",0);
});
function showErrorAndDeleteData(dom, msg, params) {
    $("#showPath1").val('');
    showMsg($.format(msg, params));
    dom.value = ''
}
function F_Open_dialog() {
    document.getElementById("btn_file").click();
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
function getMsg() {
    var val = $('input[name="dan"]:checked').val();
    if (val=='2'){
        $(".getMsg").css("display","block");
    } else {
        $(".getMsg").css("display","none");
    }
}
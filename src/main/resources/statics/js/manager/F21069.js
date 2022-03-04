var ue;
var maxSize = 250 * 1024 * 1024;//250M
var previewUrl;
var filePath = "";
var FILE_TYPE = ["png", "jpg", "jpeg", "gif", "bmp", "pdf","pptx","docx", "xlsx", "xls", "csv"];
var updateModeFlag = false;
var firstClickFileButtonFlag = true;
var IMG_TYPE = ["png", "jpg", "jpeg", "gif", "bmp"];
var vm = new Vue({
    el: ".page",
    data: {
        org: '',
        allDeliverFlg: '',
        adminList: [],
        mstTmplateEntityList: [],
        adminIdList: [],
        newFiles:[]
    },
    mounted: function () {
        this.getInfo();
        this.setUp();
        this.selectLoad();
        this.getTemplate();
        $("#temp_nm").select2();
    },
    methods: {
        setUp: function () {
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
                vm.newFiles = [];
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
            //ueditor
            ue = UE.getEditor('editor');
            //他のイベント発生する時、エラーメッセージをクリアしてください。
            $("#editor").click(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
            ue.ready(function () {
                ue.addListener('imgoversize', function (t, arg) {
                    showMsg($.format($.msg.MSGCOMN0088, "本文", "画像", "5M"));
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
            $("#cpop").click(function () {
                $("#message").hide();
                // ue.execCommand('preview');
                ue.ui._dialogs['previewDialog'].open();
            });
            laydate.render({
                elem: '#start_date',
                eventElem: '#timeOne',
                trigger: 'click',
                type: 'datetime',
                format: 'yyyy/MM/dd HH:mm',
                done: function (value, date) {
                    $("#start_date").val(value);
                }
            });
            laydate.render({
                elem: '#end_date',
                eventElem: '#timeTwo',
                trigger: 'click',
                type: 'datetime',
                format: 'yyyy/MM/dd HH:mm',
                done: function (value, date) {
                    $("#end_date").val(value);
                }
            });
        },
        selectAdmin: function () {
            //配信先設定ボタン押下
            layer.open({
                id: 'f21069Add',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['60%', '75%'],
                fixed: true,
                resize: false,
                content: ["../pop/F21074.html?type=2"],
                success: function (layero, index) {
                },
                end: function () {
                    // if (vm.adminIdList == null || vm.adminIdList.length === 0) {
                    //     return;
                    // }
                    // vm.adminIdList = [];
                    vm.selectLoad();
                }
            });
        },
        selectLoad: function () {
            var width = $(window).width() * 0.5;
            var srcHeight = $(window).height() * 0.5 - $(window).width() * 0.15 - 62 - ($(window).width() / 100) * 2.6;
            $.jgrid.gridUnload("jqGrid");
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F21069/getPage',
                    datatype: "json",
                    mtype: 'POST',
                    postData: {
                        selectData: JSON.stringify(this.adminIdList)
                    },
                    colModel: [
                        {label: '管理者ID（先生ID）', name: 'aid', index: 'aid', sortable: false, align: "center"},
                        {label: '管理者名（先生名）', name: 'aflnm', index: 'aflnm', sortable: false, align: "center"}
                    ],
                    viewrecords: true,
                    regional: 'jp',
                    height: srcHeight,
                    width: width,
                    rowNum: 30,
                    rowList: [10, 30, 50],
                    rownumbers: false,
                    rownumWidth: 25,
                    autowidth: false,
                    multiselect: false,
                    pager: "#jqGridPager",
                    jsonReader:
                        {
                            root: "page.list",
                            page: "page.currPage",
                            total: "page.totalPage",
                            records: "page.totalCount"
                        }
                    ,
                    prmNames: {
                        page: "page",
                        rows: "limit",
                        order: "order"
                    }
                    ,
                    gridComplete: function () {
                        if ($("#message") != null) {
                            $("#message").hide();
                        }
                        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    }
                    ,
                    loadComplete: function (data) {
                        if (data.total) {
                            /* 2020/01/06 liguangxin add start*/
                            vm.adminList = data.total;
                            /* 2020/01/06 liguangxin add end*/
                        }
                        // if (data.roleDiv){
                        //     $("#jqgh_jqGrid_aid").text(data.roleDiv === '1'?'管理者ID':'先生ID');
                        //     $("#jqgh_jqGrid_aflnm").text(data.roleDiv === '1'?'管理者名':'先生名');
                        // }
                    }
                }
            );
            $('#jqGrid').trigger('reloadGrid');
        },
        submit: function () {
            //登録ボタン押下
            var formData = new FormData();
            var object = {};
            //ファイル
            var files = vm.newFiles;
            //画面入力した項目をチェックし、エラーがある場合
            if ($("#mail_name").val().length == 0) {
                showMsg($.format($.msg.MSGCOMD0001, "件名"));
                return;
            }
            if ($("#mail_name").val().length > 50) {
                showMsg($.format($.msg.MSGCOMD0011, "件名", "50"));
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
            //配信先未指定チェック
            if (this.adminList.length == 0) {
                showMsg($.format($.msg.MSGCOMN0096, "配信先対象", "配信先設定"));
                return;
            }
            //お知らせタイトル
            var messageTitle = $("#mail_name").val();
            //お知らせ内容
            var messageCont = ue.getContent();
            //お知らせレベル区分
            var messageLevelDiv = '';
            var tempId = $("#temp_nm").val();
            var label = document.getElementById("checkbox").checked;
            if (label) {
                messageLevelDiv = '2';
            } else {
                messageLevelDiv = '1';
            }
            //掲載予定開始日時
            var pubPlanStartDt = $("input[name=blockStartDate]").val();
            if (pubPlanStartDt == '') {
                showMsg($.format($.msg.MSGCOMD0001, "掲載開始日時"));
                return;
            }
            //掲載予定終了日時
            var pubPlanEndDt = $("input[name=blockEndDate]").val();
            if (pubPlanEndDt == '') {
                showMsg($.format($.msg.MSGCOMD0001, "掲載終了日時"));
                return;
            }
            object.messageTitle = encodeURIComponent(messageTitle).replace(/\%20/g, " ");
            object.messageCont = encodeURIComponent(messageCont).replace(/\%20/g, " ");
            object.messageLevelDiv = messageLevelDiv;
            object.pubPlanStartDtStr = pubPlanStartDt;
            object.pubPlanEndDtStr = pubPlanEndDt;
            object.allDeliverFlg = vm.allDeliverFlg;
            object.adminList = this.adminList;
            object.filePaths = filePath;
            object.tempId = tempId;
            formData.append("f21069Dto", JSON.stringify(object));
            for (let item of files) {
                formData.append("file", item);
            }
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
                        url: ctxPath + '/manager/F21069/save',
                        cache: false,
                        data: formData,
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        datatype: "json",
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                                layer.close(index);
                            } else {
                                // var idx = layer.alert($.format($.msg.MSGCOMN0014, "インフォメーション"), {
                                //     yes: function () {
                                        window.location.href = "./F21068.html";
                                    //     layer.close(idx);
                                    // }
                                // });
                            }
                        }
                    });
                }
            });
        },
        back: function () {
            //戻るボタン押下
            window.location.href = './F21068.html';
        },
        getTemplate:function () {
            $.ajax({
                url: ctxPath + '/manager/F21069/getTemplate',
                async: true,
                datatype: "json",
                success: function (data) {
                    if (data.mstTmplateEntityList) {
                        vm.mstTmplateEntityList = data.mstTmplateEntityList;
                    }
                }
            });
        },
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F21069/init',
                type: 'GET',
                data: {},
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.org) {
                            vm.org = data.org;
                        }
                        updateModeFlag = true;
                    }
                }
            });
        }
    }
});

function showErrorAndDeleteData(dom, msg, params) {
    $("#showPath1").val('');
    showMsg($.format(msg, params));
    dom.value = ''
}
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
                $("#mail_name").val(vm.mstTmplateEntity.tmplateTitle);
                var temp_cnt = decodeURIComponent(vm.mstTmplateEntity.tmplateCnt);
                ue.ready(function () {
                    if (temp_cnt == 'undefined') {
                        ue.setContent('');
                    } else {
                        ue.setContent(temp_cnt);
                    }
                });
                if (vm.mstTmplateEntity.attachFilePath) {
                    filePath = vm.mstTmplateEntity.attachFilePath;
                    previewUrl = [];
                    for (var i = 0; i < filePath.split(",").length; i++) {
                        previewUrl.push(filePath.split(",")[i]);
                    }
                    let temp = filePath.split(',');
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
                    filePath = "";
                    sessionStorage.removeItem("paths")
                }
            } else {
                showMsg(data.msg);
            }
        }
    });
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
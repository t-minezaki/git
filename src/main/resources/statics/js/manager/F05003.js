var ue;
var param = getParam();
var selectData = [];
var returList = [];
let stuIds = param.stuIds ? JSON.parse(decodeURIComponent(param.stuIds)) : [];
var previewUrl;
var updateModeFlag = false;
var firstClickFileButtonFlag = true;
var filePath;
$(function () {
    $("#showPath1").css("background", "white");
    //ueditor
    ue = UE.getEditor('editor');
    //他のイベント発生する時、エラーメッセージをクリアしてください。
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
            showMsg($.format($.msg.MSGCOMN0088, "お知らせ内容", "画像", "5M"));
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
        ue.ui._dialogs['previewDialog'].open();
    });

    $("#selectAll").click(function () {
        var flag = document.getElementById("selectAll").checked;
        if (flag) {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
        } else {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
        }
    });
});
var zNodes;
var nodes = [];
var unCheckNodes = [];
var vm = new Vue({
    el: ".page",
    data: {
        org: '',
        notice: '',
        stuList: [],
        dto: '',
        stuIdList: [],
        allDeliverFlg: '',
        mstTmplateEntityList: [],
        newFiles:[]
    },
    updated: function () {
        $("#start_date").val(vm.dto.pubPlanStartDtStr);
        laydate.render({
            /* 2021/1/4 UT-093 modify start */
            elem: '#start_date',
            eventElem: '#timeOne',
            trigger: 'click',
            /* 2021/1/4 UT-093 modify end */
            type: 'datetime',
            value: vm.dto.pubPlanStartDtStr,
            format: 'yyyy/MM/dd HH:mm',
            done: function (value, date) {
                $("#start_date").val(value);
            }
        });
        ue = UE.getEditor('editor');
        ue.ready(function () {
            ue.addListener('imgoversize', function (t, arg) {
                showMsg($.format($.msg.MSGCOMN0088, "お知らせ内容", "画像", "5M"));
            });
            ue.setContent(decodeURIComponent(vm.notice.noticeCont));
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
        $("#end_date").val(vm.dto.pubPlanEndDtStr);
        laydate.render({
            /* 2021/1/4 UT-093 modify start */
            elem: '#end_date',
            eventElem: '#timeTwo',
            trigger: 'click',
            /* 2021/1/4 UT-093 modify end */
            type: 'datetime',
            value: vm.dto.pubPlanEndDtStr,
            format: 'yyyy/MM/dd HH:mm',
            done: function (value, date) {
                $("#end_date").val(value);
            }
        });
    },
    mounted: function () {
        this.getInfo();
        $("#temp_nm").select2();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F05003/init',
                type: 'GET',
                data: {
                    noticeId: param.id,
                    stuIds: JSON.stringify(stuIds)
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if (data.mstTmplateEntityList){
                        vm.mstTmplateEntityList = data.mstTmplateEntityList;
                    }
                    updateModeFlag = true;
                    firstClickFileButtonFlag = true;
                    if (data.orgIdList) {
                        zNodes = data.orgIdList;
                    }
                    if (data.notice) {
                        $("#mail_name").val(data.notice.noticeTitle);
                        filePath = data.notice.attachFilePath;
                        vm.notice = data.notice;
                        if (vm.notice.noticeLevelDiv != null && vm.notice.noticeLevelDiv == '2') {
                            $("#checkbox").attr("checked", "checked");
                        }
                        if (vm.notice.attachFilePath) {
                            previewUrl = [];
                            for (var i = 0; i < vm.notice.attachFilePath.split(",").length; i++) {
                                previewUrl.push(vm.notice.attachFilePath.split(",")[i]);
                            }
                            // 2020/11/2 zhangminghao modify start
                            function handleFileName(fileName) {
                                let filename = fileName.slice(fileName.lastIndexOf("\\") + 1);
                                filename = filename.slice(filename.lastIndexOf("/") + 1);
                                let newStr = "";
                                for (let i = 0; i < filename.length; i++) {
                                    if (!(i >= filename.lastIndexOf(".") - 17 && i < filename.lastIndexOf("."))) {
                                        newStr += filename.charAt(i);
                                    }
                                }
                                return newStr;
                            }
                            let temp = vm.notice.attachFilePath.split(',')
                            let filesName = "";
                            for (let item of temp) {
                                if (!!filesName){
                                    filesName += ', '
                                }
                                filesName += handleFileName(item)
                            }

                            $("#showPath1").attr("value", filesName);
                        }
                    }
                    // 2020/11/2 zhangminghao modify end
                    if (data.stuList) {
                        returList = data.stuList;
                        for (var i = 0; i < returList.length; i++) {
                            selectData.push(returList[i].usrId)
                        }
                        selectLoad();
                    }
                    if (data.dto) {
                        vm.dto = data.dto;
                    }
                    vm.isResent();
                }
            });
        },
        isResent:function (){
            var reSentFlg=param.reSentFlg;
            if (reSentFlg=="1"){
                document.getElementById("mail_name").style.pointerEvents="none";
                document.getElementById("mail_name").style.backgroundColor="#E5E5E5";
                document.getElementById("checkbox").style.pointerEvents="none";
                document.getElementById("cpop").style.pointerEvents="none";
                // document.getElementById("cpop").style.backgroundColor="#E5E5E5";
                document.getElementById("edui1").style.pointerEvents="none";
                document.getElementById("edui1").style.backgroundColor="#E5E5E5";
                document.getElementById("edui2").style.pointerEvents="none";
                document.getElementById("edui1_iframeholder").style.backgroundColor="#E5E5E5";
                document.getElementById("showPath1").style.pointerEvents="none";
                document.getElementById("showPath1").style.backgroundColor="#E5E5E5";
                document.getElementById("file_btn").style.pointerEvents="none";
                document.getElementById("file_btn").style.disabled="true";
                // document.getElementById("file_btn").style.backgroundColor="#E5E5E5";
                document.getElementById("btn_file").style.pointerEvents="none";
                document.getElementById("btn_haishin").style.pointerEvents="none";
                // document.getElementById("btn_haishin").style.backgroundColor="#E5E5E5";
                document.getElementById("comCheck").style.pointerEvents="none";
                document.getElementById("comCheck").click();
                document.getElementById("timeOne").style.pointerEvents="none";
                // document.getElementById("timeOne").style.backgroundColor="#E5E5E5";
                document.getElementById("timeTwo").style.pointerEvents="none";
                // document.getElementById("timeTwo").style.backgroundColor="#E5E5E5";
            }
        }
    }
});

// var stuList = [];
function getStuList(stuId_List) {
    if (stuId_List == null || stuId_List.length == 0) {
        return false;
    }
    selectData = vm.stuIdList;
    selectLoad();
}

function onCheck() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    nodes = treeObj.getCheckedNodes(true);
    var node = treeObj.getNodes();
    unCheckNodes = treeObj.transformToArray(node);
    var flg = document.getElementById("selectAll");
    if (unCheckNodes.length != nodes.length) {
        flg.checked = false;
    } else {
        flg.checked = true;
    }
}

function selectLoad() {
    var list = [
        {label: '保護者ID', name: 'guardAfterId', index: 'guardAfterId', sortable: false, align: "center"},
        {label: '保護者名', name: 'guardName', index: 'guardName', sortable: false, align: "center"},
        {label: '生徒ID', name: 'stuAfterId', index: 'stuAfterId', sortable: false, align: "center"},
        {label: '生徒名', name: 'stuName', index: 'stuName', sortable: false, align: "center"}
    ]
    var width = $(window).width() * 0.964 * 0.64;
    var srcHeight = $(window).height() * 0.5 - $(window).width() * 0.15 - 62 - ($(window).width() / 100) * 2.6;
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid(
        {
            datatype: "local",
            colModel: list,
            multiselect: false,
            rowNum: 30,
            rowList: [10, 20, 30],
            viewrecords: true,
            pager: "#jqGridPager",
            width: width,
            loadComplete: function () {
                $('td#jqGridPager_center').css('width', $(window).width() * 0.33);
            }
        }
    );
    var localData = {page: 1, total: 2, records: "2", rows: returList};
    localData.rows = returList;
    localData.records = returList.length;
    localData.total = (returList.length % 30 == 0) ? (returList.length / 30) : (Math.floor(returList.length / 30) + 1);
    var reader = {
        root: function (obj) {
            return localData.rows;
        },
        page: function (obj) {
            return localData.page;
        },
        total: function (obj) {
            return localData.total;
        },
        records: function (obj) {
            return localData.records;
        }, repeatitems: false
    };
    $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
}

//戻るボタン押下
$("#backBtn").click(function () {
    if (param.getData!=undefined){
        var data = JSON.parse(decodeURIComponent(param.getData));
        if (data!=null&&data!=undefined){
            var params = {};
            params.getData=JSON.stringify(data);
            window.location.href = './F08020.html?'+ $.param(params);
        }
    }else {
        window.location.href = './F05001.html';
    }
});

var maxSize = 250 * 1024 * 1024;//250M
//登録ボタン押下
$("#saveBtn").click(function () {
    var formData = new FormData();
    var tempId = $("#temp_nm").val();
    var object = {};
    //ファイル
    // 2020/11/2 zhangminghao modify start
    var file = vm.newFiles;
    // 2020/11/2 zhangminghao modify end
    //画面入力した項目をチェックし、エラーがある場合
    if ($("#mail_name").val().length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "お知らせ名"));
        return;
    }
    if ($("#mail_name").val().length > 50) {
        showMsg($.format($.msg.MSGCOMD0011, "お知らせ名", "50"));
        return;
    }

    if (ue.getContentTxt().length == 0) {
        showMsg($.format($.msg.MSGCOMD0001, "内容"));
        return;
    }
    if (ue.getContentTxt().length > 10000) {
        showMsg($.format($.msg.MSGCOMD0011, "内容", "10000"));
        return;
    }
    //配信先未指定チェック
    if (returList.length == 0) {
        showMsg($.format($.msg.MSGCOMN0096, "配信先対象", "配信先設定"));
        return;
    }
    //お知らせタイトル
    var noticeTitle = $("#mail_name").val();
    //お知らせ内容
    var noticeCont = ue.getContent();
    //お知らせレベル区分
    var noticeLevelDiv = '';
    var label = document.getElementById("checkbox").checked;
    if (label) {
        noticeLevelDiv = '2';
    } else {
        noticeLevelDiv = '1';
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
    object.id = param.id;
    object.noticeTitle = encodeURIComponent(noticeTitle).replace(/\%20/g, " ");
    object.noticeCont = encodeURIComponent(noticeCont).replace(/\%20/g, " ");
    // 2020/11/2 zhangminghao modify start
    for (let item of file) {
        formData.append("file", item);
    }
    // 2020/11/2 zhangminghao modify end
    object.noticeLevelDiv = noticeLevelDiv;
    object.pubPlanStartDtStr = pubPlanStartDt;
    object.pubPlanEndDtStr = pubPlanEndDt;
    object.allDeliverFlg = vm.allDeliverFlg;
    object.stuList = returList;
    object.updateStrCheck = vm.dto.updateStrCheck;
    object.tempId = tempId;
    object.reSentFlg=param.reSentFlg?"1":null;
    object.filePaths = filePath;
    formData.append("filePaths",filePath);
    formData.append("f05003Dto", JSON.stringify(object));
    formData.append("pushFlg",$("#comCheck").is(":checked"));
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
                url: ctxPath + '/manager/F05003/update',
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
                        // var idx = layer.alert($.format($.msg.MSGCOMN0014, "お知らせ"), {
                        //     yes: function () {
                                window.location.href = "./F05001.html";
                        //         layer.close(idx);
                        //     }
                        // });
                    }
                }
            });
        }
    });
});

//配信先設定ボタン押下
$("#btn_haishin").click(function () {
    layer.open({
        id: 'f05003mod',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['80%', '90%'],
        fixed: true,
        resize: false,
        // 2020/11/9 zhangminghao modify start
        content: ["../pop/F04004.html?orgSelectDiv=1" + "&notice=0"],
        // 2020/11/9 zhangminghao modify end
        success: function (layero, index) {
        },
        end: function () {
            getStuList(vm.stuIdList);
        }
    });
});

// 2020/11/2 zhangminghao modify start
// nwt文　2020/12/02 ファイルの種類を制限する .png,.jpg,.jpeg,.gif,.bmp,.pdf,.xlsx,.xls,.csv　

// let SUPPORT_TYPE = ['application/pdf',
//     'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
//     'application/vnd.openxmlformats-officedocument.presentationml.presentation']
var FILE_TYPE = ["png", "jpg", "jpeg", "gif", "bmp", "pdf","pptx","docx", "xlsx", "xls", "csv"];
var IMG_TYPE = ["png", "jpg", "jpeg", "gif", "bmp"];
$("input[type='file']").change(function (e) {
    // イベントが発生すると、後続の伝播を切断します
    e.stopPropagation();
    e.stopImmediatePropagation();

    if (this.files.length === 0){
        $("#showPath1").attr("value", '');
        return;
    }

    // 添付したファイルは最大３件であること。
    if (this.files.length > 3){
        showErrorAndDeleteData(this, $.msg.MSGCOMN0086, "3");
        return;
    }

    let filesName = "";
    let recordSize;
    let ext;
    let idx;
    previewUrl = [];
    vm.newFiles = [];
    for (let file of this.files) {
        previewUrl.push(file.name);
        recordSize = file.size;
        idx = file.name.lastIndexOf(".");
        if (idx != -1) {
            ext = file.name.substr(idx + 1).toUpperCase();
            ext = ext.toLowerCase();
        }
        // 容量は「250M」以上の場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
        if (recordSize > 250 * 1024 * 1024){
            showErrorAndDeleteData(this, $.msg.MSGCOMN0088,['お知らせ内容','添付ファイル','250M']);
            return;
        }
        if (recordSize.size == 0) {
            showErrorAndDeleteData(this, $.msg.MSGCOMN0075, [file.name]);
            return;
        }

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

    $("#showPath1").attr("value", ' ' + filesName);
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

function showErrorAndDeleteData(dom, msg, params) {
    showMsg($.format(msg, params));
    dom.value = '';
    $("#showPath1").attr("value", ' 添付ファイル');
}
// 2020/11/2 zhangminghao modify end
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
        $("#showPath1").text("ドラッグ＆ドロッブ");
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
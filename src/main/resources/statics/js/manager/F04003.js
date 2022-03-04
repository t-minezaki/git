var ue;
var selectData = [];
$(function () {
    $("#showPath1").css("background", "white");
    $('#btn_file').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        str = str.slice(str.lastIndexOf("/") + 1);
        $("#showPath1").attr("value", str);
    });
    //ueditor
    ue = UE.getEditor('editor');
    $("#editor").click(function () {
        if ($("#message") != null) {
            $("#message").hide();
        }
    });
    ue.ready(function () {
        ue.addListener('imgoversize', function (t, arg) {
            showMsg($.format($.msg.MSGCOMN0088, "掲示板内容", "画像", "5M"));
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
    });
});

var zNodes;
var nodes = [];
var param = getParam();
var unCheckNodes = [];

// create ztree
function geneUnitTree(unitTrees) {
    $("#treeDemo").html(""); // clear
    var setting = {
        view: {
            selectedMulti: true,
            showIcon: false,
            expandAll: true,
            expandSpeed: "fast"
            // open: true
        },
        check: {
            enable: true
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
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    nodes = treeObj.getCheckedNodes(true);
    var node = treeObj.getNodes();
    //all Nodes
    unCheckNodes = treeObj.transformToArray(node);
    var flg = document.getElementById("selectAll");
    if (unCheckNodes.length != nodes.length) {
        flg.checked = false;
    } else {
        flg.checked = true;
    }
}

var vm = new Vue({
    el: ".page",
    data: {
        org: '',
        mstNoticeEntity: '',
        noticeDeliverOrgIdList: '',
        dto: '',
        stuIdList: [],
        stuList: [],
        allDeliverFlg: ''
    },
    updated: function () {
        laydate.render({
            elem: '#timeOne',
            type: 'datetime',
            format: 'yyyy/MM/dd HH:mm',
            done: function (value, date) {
                $("#start_date").val(value);
            }
        });
        ue = UE.getEditor('editor');
        ue.ready(function () {
            ue.addListener('imgoversize', function (t, arg) {
                showMsg($.format($.msg.MSGCOMN0088, "掲示板内容", "画像", "5M"));
            });
            ue.setContent(decodeURIComponent(vm.mstNoticeEntity.noticeCont));
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
            // ue.execCommand('preview');
            ue.ui._dialogs['previewDialog'].open();
        });
        laydate.render({
            elem: '#timeTwo',
            type: 'datetime',
            format: 'yyyy/MM/dd HH:mm',
            done: function (value, date) {
                $("#end_date").val(value);
            }
        });
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F04003/init',
                type: 'GET',
                data: {
                    id: param.id
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if (data.mstNoticeEntity) {
                        vm.mstNoticeEntity = data.mstNoticeEntity;
                        $("#latest_update_time").text(vm.mstNoticeEntity.updDatime.substr(0, 16));
                        $("#mail_name").val(vm.mstNoticeEntity.noticeTitle);
                        if (vm.mstNoticeEntity.noticeLevelDiv == '2') {
                            $("#checkbox").attr("checked", true);
                        }
                        if (vm.mstNoticeEntity.allDeliverFlg == '1') {
                            $("#selectAll").attr("checked", true);
                        }
                        $("#start_date").val(vm.mstNoticeEntity.pubPlanStartDt.substr(0, 16));
                        $("#end_date").val(vm.mstNoticeEntity.pubPlanEndDt.substr(0, 16));
                        if (vm.mstNoticeEntity.attachFilePath) {
                            var str = vm.mstNoticeEntity.attachFilePath.substring(vm.mstNoticeEntity.attachFilePath.lastIndexOf('\\') + 1);
                            str = str.slice(str.lastIndexOf("/") + 1);

                            function handleFileName(fileName) {
                                var filename = fileName.slice(fileName.lastIndexOf("\\") + 1);
                                filename = filename.slice(filename.lastIndexOf("/") + 1);
                                var newStr = "";
                                for (var i = 0; i < filename.length; i++) {
                                    if (!(i >= filename.lastIndexOf(".") - 17 && i < filename.lastIndexOf("."))) {
                                        newStr += filename.charAt(i);
                                    }
                                }
                                return newStr;
                            }

                            $("#showPath1").attr("value", handleFileName(vm.mstNoticeEntity.attachFilePath));
                        }
                    }
                    if (data.stuList) {
                        vm.stuList = data.stuList;
                        for (var i = 0; i < vm.stuList.length; i++) {
                            selectData.push(vm.stuList[i].usrId)
                        }
                        selectLoad();
                    }

                    if (data.dto) {
                        vm.dto = data.dto;
                    }
                }
            });
        }
    }
});

//配信先設定押下
function toF04004() {
    layer.open({
        id: 'f04004',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['80%', '75%'],
        fixed: true,
        resize: false,
        content: ["../pop/F04004.html?orgSelectDiv=0"],
        success: function (layero, index) {
        },
        end: function () {
            if (vm.stuIdList.length == 0) {
                return;
            }
            selectData = vm.stuIdList;
            selectLoad();
            // $.ajax({
            //     url: ctxPath + '/manager/F04003/getStu',
            //     type: 'POST',
            //     data: formData,
            //     async: true,
            //     datatype: 'json',
            //     processData: false,
            //     contentType: false,
            //     success: function (data) {
            //         if (data.code == 0) {
            //             vm.stuList = data.stuList;
            //         }
            //     }
            // });
        }
    });
}

//戻るボタン押下
$("#backBtn").click(function () {
    window.location.href = './F04001.html';
});

function selectLoad() {
    var width = $(window).width() * 0.5;
    var srcHeight = $(window).height() * 0.5 - $(window).width() * 0.15 - 62 - ($(window).width() / 100) * 2.6;
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F04003/getPage',
            datatype: "json",
            postData: {
                selectData: JSON.stringify(selectData)
            },
            colModel: [
                {label: '保護者ID', name: 'gId', index: 'gId', sortable: false, align: "center"},
                {label: '保護者名', name: 'guardName', index: 'guardName', sortable: false, align: "center"},
                {label: '生徒ID', name: 'sId', index: 'sId', sortable: false, align: "center"},
                {label: '生徒名', name: 'stuName', index: 'stuName', sortable: false, align: "center"}
            ],
            viewrecords: true,
            regional: 'jp',
            height: srcHeight,
            width: width,
            // rowNum: 30,
            // rowList: [10, 30, 50],
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
                // $(".ui-jqgrid-labels").find("th").eq(3).css("border-right", "0 none");
            }
            ,
            loadComplete: function (data) {
                if (data.page) {
                    if (data.page.list) {
                        vm.stuList = data.page.list;
                    }
                }
                // $(".stamp").click(function () {
                //     $(".qrcode").fadeIn();
                // })
                // $(".qrcode").click(function() {
                //     $(".qrcode").fadeOut();
                // })
            }
        }
    );
    $('#jqGrid').trigger('reloadGrid');
}

var maxSize = 250 * 1024 * 1024;//250M
//登録ボタン押下
$("#saveBtn").click(function () {
    var formData = new FormData();
    var object = {};
    //ファイル
    var file = $("#btn_file")[0].files[0];
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
        showMsg($.format($.msg.MSGCOMD0001, "内容"));
        return;
    }
    if (ue.getContentTxt().length > 10000) {
        showMsg($.format($.msg.MSGCOMD0011, "内容", "10000"));
        return;
    }
    if (file != '' && file != undefined) {
        if (file.size > maxSize) {
            showMsg($.format($.msg.MSGCOMN0077, 'ファイルの最大サイ'));
            return;
        }
        if (file.size == 0) {
            showMsg($.format($.msg.MSGCOMN0075, file.name));
            return;
        }
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
    object.updDatime = vm.mstNoticeEntity.updDatime;
    object.noticeTitle = encodeURIComponent(noticeTitle).replace(/\%20/g, " ");
    object.noticeCont = encodeURIComponent(noticeCont).replace(/\%20/g, " ");
    formData.append("file", file);
    object.noticeLevelDiv = noticeLevelDiv;
    object.pubPlanStartDtStr = pubPlanStartDt;
    object.pubPlanEndDtStr = pubPlanEndDt;
    object.allDeliverFlg = vm.allDeliverFlg;
    object.updateStrCheck = vm.dto.updateStrCheck;
    object.stuList = vm.stuList;
    formData.append("f04003Dto", JSON.stringify(object));
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
                url: ctxPath + '/manager/F04003/edit',
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
                        // var idx = layer.alert($.format($.msg.MSGCOMN0022, "登録"), {
                        //     yes: function () {
                                window.location.href = "./F04001.html";
                        //         layer.close(idx);
                        //     }
                        // });
                    }
                }
            });
        }
    });
});




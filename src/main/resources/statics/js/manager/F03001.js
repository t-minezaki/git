var maxSize = 5 * 1024 * 1024;//5M
var index1 = 0;
var index2 = 0;
var index3 = 0;
var schyDiv = "";
var subjtDiv = "";
var publisherDiv = "";
var textbNm = "";
var orgId = "";
$(function () {

    $(window).resize(function(){
        $("#jqGrid").setGridWidth($(window).width() * 0.99)
        /* 2020/11/26 V9.0 cuikailin modify start */;
        // $("#jqGrid").setGridHeight($(window).height() * 0.6);
        /* 2020/11/26 V9.0 cuikailin modify end */
    });

    $('#getPath1').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        $("#showPath1").attr("value", str);
    });
});
var vm = new Vue({
    el: "#app",
    data: {
        schyList: [],
        subjtList: [],
        publisherList: [], textbList: [],
        orgId: "",
        orgNm: "",
        total: "",
        orgList: [],
        org: ''
    },
    mounted: function () {
        this.showData();
    },
    updated: function () {
        $("input").keyup(function () {
            $("#message").hide();
        });
        $("input").change(function () {
            $("#message").hide();
        });
        $("select").change(function () {
            $("#message").hide();
        });
        var publisherDiv = $("#org option:selected").val();
        if (publisherDiv == vm.org.orgId) {
            document.getElementById("add").disabled = false;
            document.getElementById("chooseBtn").disabled = false;
            document.getElementById("import").disabled = false;
            document.getElementById("getPath1").disabled = false;
            $("#add").css("border-color", "grey").css("background-color", "#00a65a").css("color", "white");
            $("#chooseBtn").css("border-color", "grey").css("background-color", "#00a65a").css("color", "white");
            $("#import").css("border-color", "grey").css("background-color", "#00a65a").css("color", "white");
        } else {
            document.getElementById("add").disabled = true;
            document.getElementById("chooseBtn").disabled = true;
            document.getElementById("import").disabled = true;
            document.getElementById("getPath1").disabled = true;
            $("#add").css("border-color", "grey").css("background-color", "white").css("color", "grey");
            $("#chooseBtn").css("border-color", "grey").css("background-color", "white").css("color", "grey");
            $("#import").css("border-color", "grey").css("background-color", "white").css("color", "grey");
        }
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + "/manager/F03001/init",
                type: 'get',
                datatype: "json",
                success: function (data) {
                    if (data.orgId) {
                        vm.orgId = data.orgId;
                        orgId = data.orgId;
                        detaOnload();
                    }
                    if (data.org) {
                        vm.org = data.org;
                        $("#orgId").text(data.org.orgId);
                        $("#orgNm").text(getOrgName(data.org.orgNm));
                    }
                    if (data.orgList.length != 0) {
                        vm.orgList = data.orgList;
                    }else {
                        showMsg($.format($.msg.MSGCOMN0017, "組織マスタ"));
                        return false;
                    }
                    if (data.schyList.length != 0) {
                        vm.schyList = data.schyList;
                    } else {
                        showMsg($.format($.msg.MSGCOMN0017, "学年"));
                        return false;
                    }
                    if (data.subjtList.length != 0) {
                        vm.subjtList = data.subjtList;
                    }else {
                        showMsg($.format($.msg.MSGCOMN0017, "教科"));
                        return false;
                    }
                    if (data.publisherList.length != 0) {
                        vm.publisherList = data.publisherList;
                    }else {
                        showMsg($.format($.msg.MSGCOMN0017, "出版社"));
                        return false;
                    }
                }
            })
        },
        importBtn: function (schyList, subjtList, publisherList) {
            index1 = document.getElementById("schy").selectedIndex;
            index2 = document.getElementById("subjt").selectedIndex;
            index3 = document.getElementById("pubulisher").selectedIndex;
            if (index1 === 0) {
                showMsg($.format($.msg.MSGCOMD0001, '学年'));
                return;
            }
            if (index2 === 0) {
                showMsg($.format($.msg.MSGCOMD0001, '教科'));
                return;
            }
            if (index3 === 0) {
                showMsg($.format($.msg.MSGCOMD0001, '出版社名'));
                return;
            }
            if ($("#textbNm").val() === '') {
                showMsg($.format($.msg.MSGCOMD0001, '教科書名'));
                return;
            }
            if ($("#showPath1").val() === '') {
                showMsg($.format($.msg.MSGCOMN0074, 'インポートファイル'));
                return;
            }

            var fileSize = $("#getPath1")[0].files[0].size;
            if (fileSize > maxSize) {
                showMsg($.format($.msg.MSGCOMN0077, 'ファイルの最大サイ'));
                return;
            }
            if ($("#showPath1").val().substr($("#showPath1").val().lastIndexOf(".") + 1) !== 'xlsx') {
                showMsg($.format($.msg.MSGCOMN0076, 'xlsx'));
                return;
            }
            if (document.querySelector("input[type=file]").files[0].size === 0) {
                showMsg($.format($.msg.MSGCOMN0075, document.querySelector("input[type=file]").files[0].name));
                return;
            }

            schyDiv = schyList[index1 - 1].codCd;
            subjtDiv = subjtList[index2 - 1].codCd;
            publisherDiv = publisherList[index3 - 1].codCd;
            textbNm = encodeURI($("#textbNm").val());

            var formData = new FormData();
            formData.append('file', $("#getPath1")[0].files[0]);
            formData.append('schyDiv', schyDiv);
            formData.append('subjtDiv', subjtDiv);
            formData.append('publisherDiv', publisherDiv);
            formData.append('textbNm', textbNm);

            $.ajax({
                url: ctxPath + '/manager/F03001/import',
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code === 0) {
                        // var successMsg = layer.alert($.format($.msg.MSGCOMN0014, "インポート"), {
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     btn: ["確認"],
                        //     btn1: function () {
                        //         successMsg = layer.close(successMsg);
                                location.reload();
                        //     }
                        // });
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        }
    }
});

function toF03006() {
    if ($("#schy").val()) {
        window.location.href = 'F03006.html?schy=' + $("#schy").val();
    }
    else {
        showMsg($.format($.msg.MSGCOMD0001, '学年'));
    }
}

function detaOnload() {
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F03001/select',
            datatype: "json",
            postData: {
                orgId: "null",
                schyDiv: "null",
                subjtDiv: "null",
                publisherDiv: "null",
                textbNm: "null"
            },
            colModel: [
                {
                    label: '組織名',
                    name: 'orgNm',
                    index: 'orgNm',
                    width: 120,
                    key: true,
                    align: "center",
                    sortable: false,
                    formatter(cell, option, object) {
                        return object.orgId + " " + object.orgNm;
                    }
                },
                {label: '学年', name: 'schyVal', index: 'schyVal', width: 80, sortable: false, align: "center"},
                {label: '教科', name: 'subjtVal', index: 'subjtVal', width: 80, sortable: false, align: "center"},
                {label: '出版社名', name: 'publisherVal', index: 'publisherVal', sortable: false, width: 80, align: "center"},
                {
                    label: '教科書名',
                    name: 'textbNm',
                    index: 'textbNm',
                    width: 80,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {
                        return "<a style='color: blue' onclick='f03003(" + object.textbId + "," + object.orgFlg + ");'>" + cell + "</a>";
                    }
                },
                {
                    label: '',
                    name: '',
                    index: 'orgFlg',
                    width: 100,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {
                        if (object.orgId === orgId) {
                            return "<button onclick='f03002(" + object.textbId + "," + "1);'>編集</button>&nbsp;&nbsp;" + "<button onclick='delBtn(\"" + object.textbNm + "\",\"" + object.textbId + "\",\"" + object.mtUpdTm + "\",\"" + object.orgId + "\",\"" + object.schyDiv + "\",\"" + object.subjtDiv + "\",\"" + object.publisherDiv + "\"," + object.orgFlg + ");'>削除</button>";
                        } else {
                            return "<button style=' width: 10vw !important' onclick='f03002(" + object.textbId + "," + "0);'>コピーして作成</button>";
                        }
                    }
                },
                {
                    label: '',
                    name: '',
                    index: 'export',
                    width: 80,
                    sortable: false,
                    align: "center",
                    formatter(cell, option, object) {
                        return "<button style=' width: 10vw !important' onclick='exportBtn(" + object.textbId + ")'>エクスポート</button>";
                    }
                }
            ],
            viewrecords: true,
            height:
                385,
            rowNum:
                30,
            rowList:
                [10, 30, 50],
            rownumbers:
                false,
            rownumWidth:
                25,
            autowidth:
                true,
            multiselect:
                false,
            pager:
                "#jqGridPager",
            jsonReader:
                {
                    root: "page.list",
                    page:
                        "page.currPage",
                    total:
                        "page.totalPage",
                    records:
                        "page.totalCount"
                }
            ,
            prmNames: {
                page: "page",
                rows:
                    "limit",
                order:
                    "order"
            }
            ,
            gridComplete: function () {
                // if ($("#message") != null) {
                //     $("#message").hide();
                // }
                $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                $("#jqGrid").setGridWidth($(window).width() * 0.99);
                /* 2020/11/26 V9.0 cuikailin modify start */
                // $("#jqGrid").setGridHeight($(window).height() * 0.6);
                /* 2020/11/26 V9.0 cuikailin modify end */
            }
            ,
            loadComplete: function (data) {
                if (data.code === 0) {
                } else {
                    showMsg(data.msg);
                }
            }
        }
    );
    var srcHeight = $(window).height() - ($(window).width() / 100) * 25.6 - 36 - ($(window).width() / 100) * 2.6;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
}

function selectBtn() {
    index1 = document.getElementById("schy").selectedIndex;
    index2 = document.getElementById("subjt").selectedIndex;
    index3 = document.getElementById("pubulisher").selectedIndex;
    if (index1 === 0) {
        schyDiv = "null";
    } else {
        schyDiv = vm.schyList[index1 - 1].codCd;
    }
    if (index2 === 0) {
        subjtDiv = "null";
    } else {
        subjtDiv = vm.subjtList[index2 - 1].codCd;
    }
    if (index3 === 0) {
        publisherDiv = "null";
    } else {
        publisherDiv = vm.publisherList[index3 - 1].codCd;
    }
    if ($("#textbNm").val() === '') {
        textbNm = "null"
    } else {
        textbNm = $("#textbNm").val()
    }

    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F03001/select',
        datatype: "json",
        postData: {
            orgId: $("#org").val(),
            schyDiv: schyDiv,
            subjtDiv: subjtDiv,
            publisherDiv: publisherDiv,
            textbNm: textbNm
        },
        page: 1,
        loadComplete: function (data) {
            if (data.code !== 0) {
                showMsg(data.msg);
            }
        }
    }).trigger("reloadGrid");
}

function f03002(textbId, orgFlg) {
    $.ajax({
        url: ctxPath + '/manager/F03001/selectMstCrmschId',
        type: 'get',
        dataType: 'json',
        data: {
            textbId: textbId
        },
        success: function (data) {
            if (data.code === 0) {
                window.location.href = "F03002.html?textbId=" + textbId + "&orgFlg=" + orgFlg;
            } else {
                showMsg(data.msg);
            }
        }
    });
}

function f03003(textbId, orgFlg) {
    $.ajax({
        url: ctxPath + '/manager/F03001/selectMstCrmschId',
        type: 'get',
        dataType: 'json',
        data: {
            textbId: textbId
        },
        success: function (data) {
            if (data.code === 0) {
                window.location.href = "F03003.html?textbId=" + textbId + "&orgFlg=" + orgFlg;
            } else {
                showMsg(data.msg);
            }
        }
    });
}

function delBtn(textbNm, textbId, mtUpdTm, orgId, schyDiv, subjtDiv, publisherDiv, orgFlg) {
    var msg = layer.confirm($.format($.msg.MSGCOMN0026, textbNm), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(msg);
        },
        btn2: function () {
            $.ajax({
                type: 'post',
                url: ctxPath + "/manager/F03001/delete",
                dataType: "json",
                data: {
                    textbId: textbId,
                    mtUpdTm: mtUpdTm,
                    orgId: orgId,
                    schyDiv: schyDiv,
                    subjtDiv: subjtDiv,
                    publisherDiv: publisherDiv,
                    orgFlg: orgFlg,
                    textbNm: textbNm
                },
                success: function (data) {
                    layer.close(msg);
                    if (data.code === 0) {
                        location.reload();
                    } else {
                        showMsg(data.msg);
                    }
                }
            })
        }
    });
}

function exportBtn(textbId) {
    $.ajax({
        url: ctxPath + '/manager/F03001/export',
        type: 'post',
        data: {
            textbId: textbId
        },
        success: function (data) {
            if (data.code === 0) {
                // var successMsg = layer.alert($.format($.msg.MSGCOMN0014, "エクスポート"), {
                //     closeBtn: 0,
                //     btn1: function () {
                //         successMsg = layer.close(successMsg);
                //     }
                // });
                $("#exportForm #exportInput").val(data.fileNm);
                $("#exportForm").submit();
            } else {
                showMsg(data.msg);
            }
        }
    });
}

function getTemplate() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate").submit();
}
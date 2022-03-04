var param = getParam();

//画面．検索条件．生徒ID
var stuId;
//画面．検索条件．保護者ID
var guardId;
//画面．検索条件．生徒姓名
var stuName;
//画面．検索条件．保護者姓名
var guardName;
//画面．検索条件．学年
var schy;
//画面．検索条件．組織
var orgId;

var times = 0;

var orgIdList = [];
//初期化学年区分を組織ID区分取得
$(function () {
    // $(window).resize(function(){
    //     $("#jqGrid").setGridWidth($(window).width() * 0.99);
    //     $("#jqGrid").setGridHeight($(window).height() * 0.55);
    // });

    $.ajax({
        url: ctxPath + '/manager/F00044/schySearch',
        type: 'GET',
        dataType: 'json',
        async: true,
        success: function (data) {
            var str = "<option value=''></option>";
            if (data.schySearchList) {
                var arr = data.schySearchList;
                for (var i = 0; i < arr.length; i++) {
                    str += "<option value='" + arr[i].codCd + "'>" + arr[i].codValue + "</option>";
                }
                $("#schy").html(str);
                str = "<option value=''></option>";
                arr = data.orgIdList;
                for (var i = 0; i < arr.length; i++) {
                    str += "<option value='" + arr[i].orgId + "'>" + arr[i].orgNm + "</option>";
                }
                 $("#orgId").html(str);
            }
        }
    })
});
//初期化リスト
$(function () {
    var width = $(window).width()*0.99;
    var srcHeight = $(window).height() - $(window).width() * 0.25 - 10 - ($(window).width() / 100) * 2.6;
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F00044/init',
            datatype: "json",
            postData: {
                orgId: window.parent.$("#orgList").val(),
                stuId: stuId,
                guardId: guardId,
                stuName: stuName,
                guardName: guardName,
                schy: schy,
                orgIdList: JSON.stringify(orgIdList)
            },
            colModel: [
                {
                    label: '組織名',
                    name: 'orgNm',
                    index: 'orgNm',
                    width: 8,
                    sortable: false,
                    key: true,
                    align: "center"
                },
                {
                    label: '生徒ID', name: 'stuId', index: 'stuId', width: 8, key: true, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.stuId == null) {
                            return "";
                        } else {
                            return object.stuId;
                        }
                    }
                },
                {
                    label: '生徒姓名', name: 'stuName', index: 'stuName', width: 8, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.stuNm == null && object.stuLnm == null) {
                            return "";
                        } else {
                            return object.stuNm + " " + object.stuLnm;
                        }
                    }
                },
                {
                    label: '性別', name: 'gendr', index: 'gendr', width: 10, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.gendr == null) {
                            return "";
                        } else {
                            return object.gendr;
                        }
                    }
                },
                {
                    label: '生年月日', name: 'birthd', index: 'birthd', width: 8, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.birthd == null) {
                            return "";
                        } else {
                            return moment(object.birthd).format("YYYY/M/D");
                        }
                    }
                },
                {
                    label: '学年', name: 'schy', index: 'schy', width: 10, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.schy == null) {
                            return "";
                        } else {
                            return object.schy;
                        }
                    }
                },
                {
                    label: '保護者ID', name: 'guardId', index: 'guardId', width: 8, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.stuGuardId == null || object.stuGuardId == "") {
                            return "<a href='./F00045.html?stuId=" + object.stuUi + "'>+</a>";
                        } else {
                            return "<a style='color: blue; border-bottom:1px solid' href='./F00045.html?stuId=" + object.stuUi + "'>" + object.guardId + "</a>";
                        }
                    }
                },
                {
                    label: '保護者姓名', name: 'guardName', index: 'guardName', width: 8, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.guardNm == null && object.guardLnm == null) {
                            return "";
                        } else {
                            return object.guardNm + " " + object.guardLnm;
                        }
                    }
                },
                {label: '続柄', name: 'reltnspDiv', index: 'reltnspDiv', width: 8, sortable: false, align: "center"},
                {
                    label: 'メールアドレス', name: 'mailad', index: 'mailad', width: 10, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.mailad == null) {
                            return "";
                        } else {
                            return object.mailad;
                        }
                    }
                },
                {
                    label: '住所', name: 'address', index: 'address', width: 30, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.adr1 == null) {
                            object.adr1 = " ";
                        }
                        if (object.adr2 == null) {
                            object.adr2 = " ";
                        }
                        if (object.postcdMnum == null || object.postcdMnum.trim().length === 0 || object.postcdBnum == null || object.postcdBnum.trim().length === 0) {
                            return object.adr1 + " " + object.adr2;
                        } else {
                            return object.postcdMnum + "-" + object.postcdBnum + " " + object.adr1 + " " + object.adr2;
                        }
                    }
                },
                {
                    label: '操作', name: 'del', index: 'del', width: 10, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        if (object.guardUi != object.firstGuard&&object.firstGuard!=""){
                            return "<button value='" + object.guardUi + ',' + object.stuUi + "' id='del' onclick='del(value)'>削除<button>"
                        }else{
                            return ""
                        }
                    }
                }
            ],
            viewrecords: true,
            height: srcHeight,
            width:width,
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
                if (times == 0) {
                    if (data.orgNm) {
                        $("#orgNm").text(getOrgName(data.orgNm));
                    }
                    if (data.sOrgId) {
                        $("#sOrgId").text(data.sOrgId);
                    }
                }
                if (data.code != 0) {
                    showMsg(data.msg);
                }
            }
        }
    );
});

//検索ボタン押下
function searchButton() {
    times = 1;
    formatCheck();
    if ($("#f00044Form").valid() == true) {
        $("#jqGrid").jqGrid('setGridParam', {
            url: ctxPath + '/manager/F00044/search',
            datatype: "json",
            postData: {
                orgId: window.parent.$("#orgList").val(),
                stuId: $('#stuId').val(),
                guardId: $('#guardId').val(),
                stuName: $('#stuName').val(),
                guardName: $('#guardName').val(),
                schy: $('#schy').val(),
                orgIdList: JSON.stringify(orgIdList)
            },
            page: 1
        }).trigger("reloadGrid");
    }
}

//Check
function formatCheck() {
    $("#f00044Form").validate({
        rules: {
            stuId: {
                hankaku: true
            },
            guardId: {
                hankaku: true
            },
            stuName: {
                zenkaku: true
            },
            guardName: {
                zenkaku: true
            }
        },
        debug: false,
        onfocusout: false,
        onkeyup: false,
        messages: {
            stuId: {
                hankaku: $.format($.msg.MSGCOMD0003, "生徒ID")
            },
            guardId: {
                hankaku: $.format($.msg.MSGCOMD0003, "保護者ID")
            },
            stuName: {
                zenkaku: $.format($.msg.MSGCOMD0007, "生徒姓名")
            },
            guardName: {
                zenkaku: $.format($.msg.MSGCOMD0007, "保護者姓名")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
$("#setOrg").bind('click', function () {
    layer.open({
        id: 'F09010-1',
        type: 2,
        title: '組織指定',
        area: ['45%', '40%'],
        fixed: true,
        resize: false,
        anim: 2,
        skin: "layui-layer-myskin",
        shade: 0.2,
        closeBtn: 1,
        shadeClose: false,
        move: '.layui-layer-title',
        content: ["../pop/F09010.html"],
        success: function (layero, index) {
        },
    })
});
function del(value) {
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00044/del',
                type:'GET',
                data:{
                    id:value
                },
                success:function () {
                    $("#jqGrid").trigger("reloadGrid");
                }
            })
        }
    })
}
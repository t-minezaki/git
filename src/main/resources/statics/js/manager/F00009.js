var currentLevel = 0;
var param = getParam();
var index;
var flg = 0;
//半角check
function isHalfAngle(str) {
    var flg = true;
    for (var i = 0; i < str.length; i++) {
        if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
            flg = false;
            break;
        }
    }
    return flg;
}

function searchFn() {
    if (!isHalfAngle($("#level").val())) {
        showMsg($.format($.msg.MSGCOMD0004, "階層"));
        $("#level").focus();
        return;
    }
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F00009/init',
        datatype: "json",
        postData: {
            currentOrgId: $("input[name = 'orgId']").val(),
            orgNm: $("input[name = 'orgNm']").val(),
            upLevOrgId: $("#uplevList").val(),
            level: $("#level").val(),
            mgrFlg: $('input[name="mgrFlg"]:checked').val()
        },
        page: 1
    }).trigger("reloadGrid");
}

function updateFn(id) {
    var srcWidth = $(window).width() * 0.8;
    if (srcWidth > 280) {
        var srcH = 280;
        srcWidth = '280px';
    } else {
        var srcH = srcWidth;
        srcWidth = srcWidth + 'px'
    }
    var srcHeight = srcH * 0.68 + 'px';
    layer.open({
        id: 'f00003',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F00003.html?id=" + id, 'no']
    });
}

function addFn() {
    var srcWidth = $(window).width() * 0.8;
    if (srcWidth > 280) {
        var srcH = 280;
        srcWidth = '280px';
    } else {
        var srcH = srcWidth;
        srcWidth = srcWidth + 'px'
    }
    var srcHeight = srcH * 0.68 + 'px';
    layer.open({
        id: 'f00003',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F00003.html", 'no'],
        /* 2020/12/21 liguangxin add start*/
        end:function() {
            location.reload();
        }
        /* 2020/12/21 liguangxin add end*/
    });
}

function deleteFn(id, orgId) {
    var msg = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
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
                url: ctxPath + '/manager/F00009/delete',
                data: {
                    id: id
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    layer.close(msg);
                    if (data.code != 0) {
                        showMsg(data.msg)
                    }else {
                        // 2020/12/7 huangxinliang modify start
                        top.orgSelectComponent.loadOrg();
                        // 2020/12/7 huangxinliang modify end
                        $("#jqGrid").jqGrid('setGridParam', {
                            url: ctxPath + '/manager/F00009/init',
                            datatype: "json",
                            postData: {
                                currentOrgId: $("input[name = 'orgId']").val(),
                                orgNm: $("input[name = 'orgNm']").val(),
                                upLevOrgId: $("#uplevList").val(),
                                level: $("#level").val(),
                                mgrFlg: $('input[name="mgrFlg"]:checked').val()
                            },
                            page: 1
                        }).trigger("reloadGrid");
                    }
                }
            });
        }
    });
}
var width = $(window).width()*0.99;
$(function () {
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F00009/init',
        async:true,
        datatype: "json",
        colModel: [
            {label: '組織ID', name: 'orgId', index: 'cod_id', width: 50, key: true,sortable:false, align: "center"},
            {label: '組織名', name: 'orgNm', index: 'cod_key', width: 80,sortable:false,align: "center"},
            {
                label: '上位組織',
                name: 'upplevOrgId',
                index: 'cret_datime',
                width: 80,
                align: "center",
                sortable:false,
                formatter: function (cell, option, object) {
                    return object.upplevOrgId + " " + object.upLevOrgNm;
                }
            },
            {label: '階層', name: 'level', index: 'cod_descrp', width: 80,sortable:false,align: "center"},
            {
                label: '管理組織',
                name: 'upplevOrgId',
                index: 'cret_datime',
                width: 80,
                sortable:false,
                align: "center",
                formatter: function (cell, option, object) {
                    return object.mgrVal;
                }
            },
            // {
            //     label: 'ＱＲログインID統合', name: '', index: 'qrLoginId', width: 80,sortable:false,
            //     formatter: function (cell, option, object) {
            //         if (object.mgrVal == '非管理') {
            //             return "<input onclick='getOrgList(this)' type='checkbox' value='"+object.orgId + "'  style='width: 1vw;height: 3vh'>";
            //         }else {
            //             return "";
            //         }
            //     }
            // },
            {
                label: '', name: '', index: 'upd_usr_id', width: 80,sortable:false, formatter: function (cell, option, object) {
                    return "<button onclick='updateFn(" + object.id + ")'>編集</button>";
                }
            },
            {
                label: '', name: '', index: 'del_flg', width: 80,sortable:false, formatter: function (cell, option, object) {
                    return "<button onclick=deleteFn(" + object.id + ",'" + object.orgId + "')>削除</button>";
                }
            }
        ],
        viewrecords: true,
        height: 385,
        width:width,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        loadComplete: function (data) {
            $("#orgId").text(data.upOrg.orgId);
            $("#orgNm").text(getOrgName(data.upOrg.orgNm));
            $("#levelShow").text(data.upOrg.level);
            if (flg === 0) {
                var str = "<option selected></option>";
                $.each(data.upLevOrg, function (i, item) {
                    str += "<option value='" + item.orgId + "'>" + item.orgId + " " + item.orgNm + "</option>";
                });
                $("#uplevList").html(str);
                flg += 1;
            }
            if (data.code !== 0) {
                showMsg(data.msg);
            } else {
                $("input").keyup(function () {
                    $("#message").hide();
                });
                $("input").change(function () {
                    $("#message").hide();
                });
                $("select").change(function () {
                    $("#message").hide();
                });
            }
            if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
                if (window.location.href.indexOf("?mobile") < 0) {
                    try {
                        if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                            $(".ui-jqgrid tr.ui-row-ltr td").css("font-size", "1vw");
                        } else if (/iPad/i.test(navigator.userAgent)) {

                        } else {
                            // alert("other")
                        }
                    }
                    catch (e) {
                    }
                } else {
                    // alert("456")
                }
            }
            else {

            }
            $("input:checkbox").each(function () {
                if (orgIdList.indexOf($(this).val()) != -1){
                    $(this).attr("checked","checked");
                }
            })
        }
    });
    var srcHeight = $(window).height() - ($(window).width() / 100) * 27.5 - ($(window).width() / 100) * 2.6;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $(".topSessionTitle div").css("font-size", "1.2vw");
                    $(".objectName").css("font-size", "1.2vw");
                    $("input[type = radio].newtui-radio-one").css("font-size", "1.2vw");
                    $(".buttonDiv button").css("font-size", "1.2vw");
                    $(".listTitle").css("font-size", "1.2vw");
                    $(".ui-th-ltr, .ui-jqgrid .ui-jqgrid-htable th.ui-th-ltr").css("font-size", "1.2vw").css("line-height", "6vh");
                }
                else if (/iPad/i.test(navigator.userAgent)) {

                }
                else {
                    // alert("other")
                }
            }
            catch (e) {
            }
        } else {
            // alert("456")
        }
    }
    else {

    }
});
var orgIdList = [];
var qrUsers = [];
var aftUsrIds = [];
function getOrgList(obj){
    if (!$(obj).is(':checked')) {
        var index = orgIdList.indexOf($(obj).val());
        orgIdList.splice(index,1);
    }else {
        orgIdList.push($(obj).val());
    }
}
function toQrLogin() {
    //一覧の１レコードのみ　又は　全て未選択の場合
    if (orgIdList.length < 2){
        //エラーメッセージ画面（POP）が表示
        var index = layer.confirm($.format($.msg.MSGCOMN0152, "二つ以上のデータ"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(index);
            }
        });
        return;
    }
    //ＱＲログインデータを取得
    $.ajax({
        url:ctxPath + '/manager/F00009/qrLogin',
        type: 'GET',
        data:{
            orgListStr:JSON.stringify(orgIdList)
        },
        success:function (data) {
            if (data.code != 0){
                showMsg(data.msg);
                return;
            }else {
                qrUsers = data.qrDatas;
                aftUsrIds = data.aftUsrIds;
                var srcWidth = $(window).width() * 0.8;
                if (srcWidth > 400) {
                    var srcH = 400;
                    srcWidth = '400px';
                } else {
                    var srcH = srcWidth;
                    srcWidth = srcWidth + 'px'
                }
                var srcHeight = srcH * 0.68 + 'px';
                layer.open({
                    id: 'f00009',
                    type: 2,
                    title: false,
                    shade: 0.1,
                    closeBtn: 0,
                    shadeClose: false,
                    area: [srcWidth, srcHeight],
                    fixed: true,
                    resize: false,
                    content: ["../pop/qrLogin.html", 'no'],
                });
            }
        }
    })
}
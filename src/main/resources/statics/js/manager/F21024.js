var srcHeight = $(window).height() * 0.5;
var srcWidth;
var flag = getCookie("equipment") === "phone";
if(flag){
    /*srcHeight = $('.content').height() * 0.93 - $(window).height() * 0.01 - 43;*/
    srcWidth = $(window).width() * 0.98;
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21024.css"}).appendTo("head");
}else {
    srcWidth = $(window).width() * 0.74;
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21024-1.css"}).appendTo("head");
}
$(function () {
    reload();
});
var corrspdSts ='0';
var offset;
var pageSize = 15;
function pageChange(pgFlg){
    if(pgFlg == 0){
        $("#prev").addClass("active");
        $("#next").removeClass("active").css("border-left","0");
    }
    else {
        $("#next").addClass("active");
        $("#prev").removeClass("active");
    }
    pageSize = 15;
    corrspdSts = pgFlg;
    offset = 0;
    reload();
}
function reload(){
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F21024/init',
            datatype: "json",
            postData: {
                corrspdSts: corrspdSts,
                limit: pageSize
            },
            colModel: [
                {
                    label: '遅刻・欠席希望日時', name: 'tgtYmd', index: 'tgtYmd', width: 30, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var s = new Date(object.tgtYmd.replace(/-/g, '/')).Format("yy/MM/dd HH:mm");
                        return '<p class="grid-date">' + s + '</p>';
                    }
                },

                {
                    label: '送信日', name: 'cretDatime', index: 'cretDatime', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        return '<p class="grid-date">' + object.cretDatime + '</p>';
                    }
                },

                {
                    label: '会員名', name: 'name', index: 'name', width: 30, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var name = object.stuFN + " " + object.stuFLn;
                        return name;
                    }
                },
                {
                    label: '内容', name: 'operate', index: 'operate', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var params = {};
                        params.id = object.id;
                        params.tgtYmd = object.tgtYmd.substring(0, 10);
                        // 2022/01/06 MANAMIRU1-873 del start
                        // params.mailad = object.mailad;
                        // 2022/01/06 MANAMIRU1-873 del end
                        params.stuFN = object.stuFN;
                        params.stuFLn = object.stuFLn;
                        params.stuId = object.stuId;
                        params.telnum = object.telnum;
                        params.corrspdSts = object.corrspdSts;
                        params.noticeId = object.noticeId;
                        params.flnmNm = object.flnmNm;
                        params.flnmLnm = object.flnmLnm;
                        return "<button onclick=toF21025('" + encodeURIComponent(JSON.stringify(params)) + "',corrspdSts)> 確認する</button>"
                    }
                }
            ],
            viewrecords: true,
            height: srcHeight,
            width: srcWidth,
            // rowList: [10, 30, 50],
            rowNum: -1,
            rownumWidth: 25,
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
                // rows: "limit"
            }
            ,
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            }
            ,
            loadComplete: function (data) {
                if (data.code == 0) {
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    $(".ui-jqgrid-bdiv").append("<button class=\"insert\" onclick=\"insert()\">更に読み込む</button>");
                    $(".ui-jqgrid-bdiv").append('<div style="height: 1px;"></div>');
                    if (offset > 0) {
                        $(".ui-jqgrid-bdiv").scrollTop(offset);
                    }
                    if (pageSize >= data.page.totalCount) {
                        $(".insert").remove();
                    }
                    for (var i = 0; i < data.page.list.length; i++) {
                        if (data.page.list[i].corrspdSts == "2" || data.page.list[i].corrspdSts == "3") {
                            // $("#"+data.page.list[i].id+"").css("background","#E5E5E5");
                        }
                    }
                    if (data.page.list == "" || data.page.list.length == 0) {
                        var idx = layer.confirm($.format($.msg.MSGCOMN0017, "遅刻欠席連絡"), {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            yes: function () {
                                layer.close(idx);
                            }
                        })
                    }
                }else {
                    showMsg(data.msg);
                    layer.close(index);
                }
            },

        }
    );
    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}

function change(id, corrspdSts) {
    // 確認ダイアログをポップアップ表示する
    if (corrspdSts == "1" || corrspdSts == "2") {
        var index = layer.confirm($.format($.msg.MSGCOMN0131, "対応済を"), {
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
                    url: ctxPath + '/manager/F21003/change',
                    type: 'GET',
                    data: {
                        id: id,
                        corrspdSts: corrspdSts
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.code != 0) {
                            showMsg(data.msg);
                        } else {
                            reload();
                        }
                    }
                });
            }
        })
    } else {
        $.ajax({
            url: ctxPath + '/manager/F21003/change',
            type: 'GET',
            data: {
                id: id,
                corrspdSts: corrspdSts
            },
            success: function (data) {
                if (data.code != 0) {
                    showMsg(data.msg);
                } else {
                    reload();
                }
            }
        })
    }
}
function insert() {
    pageSize = pageSize + 15;
    offset = $("#jqGrid").height() - $('.ui-jqgrid-bdiv').height() + $(".insert").outerHeight();
    // $.jgrid.gridUnload("jqGrid");
    $.ajax({
        url: ctxPath + '/manager/F21024/init',
        type:'GET',
        data:{
            limit:pageSize,
            page:pageSize / 15,
            corrspdSts: corrspdSts
        },
        success:function (data) {
            if (data.code !== 0){
                layer.alert(data.msg);
                return;
            }
            data.page.list.forEach(function (v) {
                $("#jqGrid").jqGrid('addRowData', v.id, v, 'last');
            });
            if (pageSize >= data.page.totalCount) {
                $(".insert").remove();
            }
        }
    })
}
function toF21025(params,corrspdSts) {
    var params1 = JSON.parse(decodeURIComponent(params));
    params1.stuName = params1.stuFN + ' ' + params1.stuFLn;
    params1.guardName = params1.flnmNm + ' ' + params1.flnmLnm;
    var srcWidth = "315px";
    var srcHeight = "370px";
    layer.open({
        id: 'F21025',
        type: 2,
        title: '',
        shade: 0.1,
        closeBtn: 1,
        shadeClose: false,
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F21025.html?" + $.param(params1) + '&corrspdSts=' + corrspdSts],
        success: function (layero, index) {
        }
    })
}

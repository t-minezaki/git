var srcHeight = $(window).height() * 0.65;
var srcWidth = $(window).width() * 0.98;
$(function () {
    laydate.render({
        elem: '#startTime',
        type: 'date',
        format: 'yyyy/MM/dd',
        eventElem: '#time',
        trigger: 'click',
        btns: ['clear'],
        done: function (value) {
            if(value ==''){
                value = new Date().Format("yyyy/MM/dd");
            }
            $("#message").hide();
            $(".timeInput").val(value);
        },
        ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $("#date").css("font-size", "1.8vw");
                    $(".timeInput").css("font-size", "1.2vw");
                } else if (/iPad/i.test(navigator.userAgent)) {
                    $("#time").css("margin-top", "0.3%");
                }
            } catch (e) {
            }
        }
    } else {
        if (/Macintosh/i.test(navigator.userAgent)){
            $("#time").css("margin-top", "0.3%");
        }
    }
    reload();
});


function reload() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var nowDate = year + "/" + month + "/" + day;
    $("#startTime").val(nowDate);
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F21007/init',
        datatype: "json",
        postData: {
            tgtYmd: $("#startTime").val()
        },
        colModel: [
            {label: '出席簿コード', name: 'attendBookCd', index: 'attendBookCd', width: 10, key: true, sortable: true, align: "center"},
            {label: 'グループ名', name: 'grpNm', index: 'grpNm', width: 10, key: true, sortable: true, align: "center"},
            {label: '回数', name: 'timesNum', index: 'timesNum', width: 10, key: true, sortable: false, align: "center"},
            {label: '操作 ', name: ' ', index: ' ', width: 15, sortable: false, align: "center",
                formatter(cell, option, object) {
                    var params={}
                    params.id = object.id;
                    params.tgtYmd = $("#startTime").val();
                    params.attendBookCd = object.attendBookCd;
                    params.grpNm = encodeURIComponent(getGrpNm(object.tgtYmd,object.grpNm));
                    params.timesNum = object.timesNum;
                    params.grpId = object.grpId;
                    if (object.attendBookCd) {
                        params.pageDiv = 2;
                        return "<div style='text-align: center;'><button  class='tbl_btn' onclick=add('" + object.orgId + "','" + object.id + "'," + object.grpId + ",'" + object.tgtYmd + "')> 新しい出席簿を表示</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button  class='tbl_btn' onclick=toF21008('"+JSON.stringify(params)+"')  >修正</button></div>"
                    } else {
                        params.pageDiv = 1;
                        return "<div style='text-align: center;'><button onclick=toF21008('"+JSON.stringify(params)+"')  class='tbl_btn'> 新しい出席簿を表示</button></div>"
                    }
                }
            }
        ],
        viewrecords: true,
        height: srcHeight,
        width: srcWidth,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        sortable: true,
        sortorder: 'desc',
        shrinkToFit: true,
        loadonce: true,
        multiselect: false,
        autowidth: false,
        pager: "#jqGridPager",
        jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit"
        },
        loadComplete: function (data) {
            if (data.code == 0) {
            } else {
                showMsg(data.msg);
            }

            if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
                if (window.location.href.indexOf("?mobile") < 0) {
                    try {
                        if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                            $(".ui-jqgrid tr.ui-row-ltr td").css("font-size", "1.3vw");
                        }
                    } catch (e) {
                    }
                }
            }
        }
    });
    $("div.ui-jqgrid-sortable").each(function (index, element) {
        if (element.id == "jqgh_jqGrid_timesNum") {
            return;
        }
        if (this.id == "jqgh_jqGrid_ ") {
            return;
        }
        var asc = $(element).children().find("span[sort='asc']").first();
        var span = $(element).children("span.s-ico").first();
        var label = $(element).children("label");
        if (label.length <= 0) {
            span.before("<label></label>");
        }
        if (asc.hasClass("ui-state-disabled")) {
            $(element).children("label").html("&#9650");
        } else {
            $(element).children("label").html("&#9660");
        }
    });
    $("div.ui-jqgrid-sortable").bind("click", function () {
        if (this.id == "jqgh_jqGrid_timesNum") {
            return;
        }
        if (this.id == "jqgh_jqGrid_ ") {
            return;
        }

        var asc = $(this).children().find("span[sort='asc']").first();
        var span = $(this).children("span.s-ico").first();
        var label = $(this).children("label");
        if (label.length <= 0) {
            span.before("<label></label>");
        }
        if (asc.hasClass("ui-state-disabled")) {
            $(this).children("label").html("&#9660");
        } else {
            $(this).children("label").html("&#9650");
        }
    });
    $('#jqGrid').trigger('reloadGrid');
}

$("#find").click(function () {
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F21007/init',
        datatype: "json",
        postData: {
            tgtYmd: $("#startTime").val(),
            grpNm: $("#groupInput").val()
        }
    }).trigger("reloadGrid");
});

function toF21008(params) {
    var params1 = JSON.parse(params)
    params1.grpNm = decodeURIComponent(JSON.parse(params).grpNm);
    window.location.href = 'F21008.html?' + $.param(params1);
}

function add(orgId, id, grpId, tgtYmd) {
    // 確認ダイアログをポップアップ表示する
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "出席簿追加作成"), {
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
                url: ctxPath + '/manager/F21007/add',
                type: 'GET',
                data: {
                    orgId: orgId,
                    id: id,
                    grpId: grpId,
                    tgtYmd: tgtYmd,
                    dayweekDiv: ""
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
}
function getGrpNm(tgtYmd,grpNm) {
    var weekDay = ["日曜", "月曜", "火曜", "水曜", "木曜", "金曜", "土曜"];
    if (!tgtYmd) {
        var myDate = new Date(Date.parse($("#startTime").val()));
    } else {
        var myDate = new Date(Date.parse(tgtYmd));
    }
    return grpNm + " " + weekDay[myDate.getDay()];
}
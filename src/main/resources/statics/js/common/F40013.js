var flg = 0;
var srcHeight = $(window).height() * 0.2;
var srcWidth = $(window).width() * 0.315;
var count = 0;
var msg = false;
var vue = new Vue({
    el: "#vue",
    data: {
        f40013DtoList: [],
        orgNm: '',
        orgId: '',
        flg: flg,
        orgNow: ''
    },
    mounted: function () {
        this.init();
    },
    updated: function () {
        if (vue.orgNow != '' && vue.orgNow != null) {
            for (var i = 0; i < $(".div_select input").length; i++) {
                if ($(".div_select input")[i].className == vue.orgNow) {
                    $($(".div_select input")[i]).prop("checked", true);
                }
            }
        } else {
            $(".div_select input:first").prop("checked", true);
        }
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/common/F40013/init',
                datatype: 'json',
                type: 'GET',
                async: true,
                success: function (data) {
                    vue.orgNow = data.orgNow;
                    if (data.code == 0) {
                        if (data.f40013DtoList.length < 10) {
                            vue.f40013DtoList = data.f40013DtoList;
                            $(".hiddenDiv_box2").css("display", "none");
                        } else {
                            msg = true;
                            $(".hiddenDiv_box").css("display", "none");
                            getOrg();
                        }
                    }
                }
            });
        }
    }
});

function getOrg() {
    msg = true;
    var orgId = $("#orgId").val();
    var orgNm = $("#orgNm").val();
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
        url: ctxPath + '/common/F40013/init',
        datatype: "json",
        postData: {
            orgId: orgId,
            orgNm: orgNm
        },
        // traditional: false,
        colModel: [
            {
                label: '組織コード',
                name: 'orgId',
                index: 'orgId',
                width: 7,
                key: true,
                sortable: true,
                align: "left",
                formatter(cell, option, object) {
                    return "<a onclick=choose('" + encodeURIComponent(object.orgId) + "') " + " class='link'>" + object.orgId + "</a>"
                }
            },
            {
                label: '組織名',
                name: 'orgNm',
                index: 'orgNm',
                width: 10,
                key: true,
                sortable: true,
                align: "left"
            }
        ],
        viewrecords: true,
        height: srcHeight,
        width: srcWidth,
        rowNum: 50,
        rowList: [50, 100, 150],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: false,
        multiselect: false,
        shrinkToFit: true,
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
        }
        ,
        loadComplete: function (data) {
            count = data.f40013DtoList;
            if (msg) {
                if (count > 50) {
                    var idx = layer.confirm($.msg.MSGCOMN0178, {
                        skin: 'layui-layer-molv123',
                        title: '',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['戻る'],
                        yes: function () {
                            layer.close(idx);
                        }
                    })
                }
                msg = false;
            }
        }
    });
    $("#jqGrid").trigger("reloadGrid");
}

function choose(orgId) {
    orgId = decodeURIComponent(orgId);
    $.ajax({
        url: ctxPath + '/sys/chooseOrg',
        type: 'POST',
        data: {
            // "orgNm": orgNm,
            "orgId": orgId
        },
        success: function (data) {
            if (data.code == 0) {
                // window.location.href = ctxPath + "/manager/F00001.html";
                window.location.href = ctxPath + data.url;
            } else {
                layer.alert(data.msg);
            }
        }
    })
}

// function back() {
//     if (vue.orgNow){
//         window.location.href = ctxPath + "/manager/F00001.html";
//     } else {
//         window.location.href = history.go(-1)
//     }
//
// }

function select() {
    var orgNm = $("input[name='orgNm']:checked").val();
    var orgId = $("input[name='orgNm']:checked").attr('class');
    $.ajax({
        url: ctxPath + '/sys/chooseOrg',
        type: 'POST',
        data: {
            // "orgNm": orgNm,
            "orgId": orgId
        },
        success: function (data) {
            if (data.code == 0) {
                // window.location.href = ctxPath + "/manager/F00001.html";
                window.location.href = ctxPath + data.url;
            } else {
                layer.alert(data.msg);
            }
        }
    })

}

var WinHeight = $(window).height();

$(window).resize(function () {
    $('body').height(WinHeight);

});

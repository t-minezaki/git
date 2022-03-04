window.onload=function() {
    var srcHeight = $(window).height() - $(window).width() * 0.13 - 62;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
};
var param = getParam();
var width = $(window).width()*0.98;
function getInit(){
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F01001/init',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', width: 50 , key: true, align: "center", hidden:true},
            {label: '組織名', name: 'orgNm', index: '1', width: 80,  align: "center"},
            {label: '時期名', name: 'prdNm', index: '2', width: 80 , align: "center"},
            {label: '学年', name: 'codValue', index: '3', width: 80},
            {label: '時期開始日', name: 'planStartDy', index: '4', width: 100},
            {label: '時期終了日', name: 'planEndDy', index: '5', width: 100},
            {label: '適用', name: 'useFlg', index: '', width: 50,formatter:function (cell) {
                    if (cell == 1){
                        return '適用中'
                    } else {
                        return '未適用'
                    }
                }},
            {
                label: '', name: '', index: 'upd_usr_id', width: 50, formatter: function (cell,option, object) {
                    if (object.flg == "1") {
                        return "<button onclick='updateFn(" + object.id + ", \"" + object.orgId + "\",\"" + object.orgNm + "\")'>編集</button>";
                    }else {
                        return '';
                    }
                }
            },
            {
                label: '', name: '', index: 'del_flg', width: 50, formatter: function (cell, option, object) {
                    if (object.flg == "1") {
                        return "<button onclick='deleteFn(" + object.id + ")'>削除</button>"
                    }else {
                        return '';
                    }
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
            records: "page.totalCount",
            orgId:"orgId"
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
            $('#orgId').text(data.orgId);
            $('#orgNm').text(getOrgName(data.orgNm));
            if (data.code != 0) {
                showMsg(data.msg);
            }
        }
    });
    var srcHeight = $(window).height() - ($(window).width() / 100) * 17  - ($(window).width()/100)*2.6;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
}
$(function () {
    $(window).resize(function(){
        $("#jqGrid").setGridWidth($(window).width() * 0.99);
        /* 2020/11/26 V9.0 cuikailin modify start */
        // $("#jqGrid").setGridHeight($(window).height() * 0.75);
        /* 2020/11/26 V9.0 cuikailin modify end */
    });
    getInit();
});
function addFn(){
    var index = layer.open({
        id: 'f01002',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['340px', '370px'],
        fixed: true,
        resize: false,
        content: ["../manager/F01002.html", 'no'],
        success: function (layero, index) {
        }
    });
}
function updateFn(id,orgId,orgNm) {
    var index = layer.open({
        id: 'f01002',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['340px', '370px'],
        fixed: true,
        resize: false,
        content: ["../manager/F01002.html?id="+id+"&orgNm="+orgNm+"&orgId="+orgId, 'no'],
        success: function (layero, index) {
        }
    });
}
function deleteFn(id) {
    var msg = layer.confirm($.format($.msg.MSGCOMN0013, "塾学習"), {
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
                url: ctxPath + '/manager/F01001/delete',
                data: {
                    id: id
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        layer.close(msg);
                        window.location.reload();
                    }else {
                        layer.close(msg);
                        showMsg(data.msg);
                    }
                }
            });
        }
    });
}

// タイムフォーマット変換
Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

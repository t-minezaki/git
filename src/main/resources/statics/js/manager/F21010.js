var srcHeight = $(window).height() * 0.4;
var width = $(window).width() * 0.98;
var menuId = 'F21010';
var param = getParam();
var list = [];
var closeFlg = false;
var wholeList = {
    'guidReprDeliverCd': '指導報告書配信コード',
    'pubStartDt': '公開開始日時',
    'pubEndDt': '公開終了日時',
    'eventStsDiv': '公開有無',
    'countSend': '配信数',
    'countNotRead': '未読数',
    'countRead': '既読数',
    'tgtYmd': '対象年月日',
    'cretDatime': '作成日',
    'updDatime': '更新日',
    'cretUsrId': '作成者',
    'updUsrId': '更新者',
    'operation':'操作'
}
// 表示項目辞书
var dist =
    {
        'guidReprDeliverCd': {label: '指導報告書配信コード', name: 'guidReprDeliverCd', index: 'guidReprDeliverCd', width: 200, key: true, sortable: false, align: "center"
        }

        ,
        'cretDatime': {label: '作成日', name: 'cretDatime', index: 'cretDatime', width: 150, sortable: false, align: "center",
            formatter(cell, option, object){
                var date = new Date(object.cretDatime.replace(/-/g, '/'));
                return date.Format("yyyy/MM/dd");
            }
        }
        ,
        'pubStartDt': {
            label: '公開開始日', name: 'pubStartDt', index: 'pubStartDt', width: 150, sortable: false, align: "center",
            formatter(cell, option, object){
               var date = new Date(object.pubStartDt.replace(/-/g, '/'));
               return date.Format("yyyy/MM/dd");
            }
        }
        ,
        'pubEndDt': {
            label: '公開終了日', name: 'pubEndDt', index: 'pubEndDt', width: 150, sortable: false, align: "center",
            formatter(cell, option, object){
                var date = new Date(object.pubEndDt.replace(/-/g, '/'));
                return date.Format("yyyy/MM/dd");
            }
        }
        ,
        // 'managerNm': {
        //     label: '管理者姓名', name: 'managerNm', index: 'managerNm', width: 150, sortable: false, align: "center"
        // }
        // ,
        // 'mentorNm': {
        //     label: 'メンタ姓名', name: 'mentorNm', index: 'mentorNm', width: 150, sortable: false, align: "center"
        // },
        'eventStsDiv': {label: '公開有無', name: 'eventStsDiv', index: 'eventStsDiv', width: 150, sortable: false, align: "center"
        }
        // ,
        // 'codValue': {label: '公開有無', name: 'codValue', index: 'codValue', width: 150, sortable: false, align: "center"
        // }
        ,
        /* 2020/12/07 V9.0 cuikailin modify start */
        'countSend': {label: '配信数', name: 'countSend', index: 'countSend', width: 70, sortable: false, align: "center",
            formatter(cell, option, object) {
                var count = object.countSend==null?0:object.countSend;
                var params={};
                params.guidReprDeliverCd = object.guidReprDeliverCd;
                params.pick = object.codCd;
                params.attendanceBookDate = object.tgtYmd.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.eventStsDiv = object.eventStsDiv;
                params.add = 1;
                params.startDt = object.pubStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.endTime = object.pubEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                return "<a onclick='showDetail(\"" +  object.guidReprDeliverCd  + "\"" + "," + (-1) + "," +JSON.stringify(params) + ")'>" + count + "</a>";
            }
        }
        ,
        'countNotRead': {label: '未読数', name: 'countNotRead', index: 'countNotRead', width: 70,sortable: false, align: "center",
            formatter(cell, option, object) {
                var count = object.countNotRead==null?0:object.countNotRead;
                var params={};
                params.guidReprDeliverCd = object.guidReprDeliverCd;
                params.pick = object.codCd;
                params.attendanceBookDate = object.tgtYmd.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.eventStsDiv = object.eventStsDiv;
                params.add = 1;
                params.startDt = object.pubStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.endTime = object.pubEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                return "<a onclick='showDetail(\"" +  object.guidReprDeliverCd  + "\"" + "," + 0 + "," + JSON.stringify(params) + ")'>" + count + "</a>";
            }
        }
        ,
        'countRead': {label: '既読数', name: 'countRead', index: 'countRead', width: 70, sortable: false, align: "center",
            formatter(cell, option, object) {
                var count = object.countRead==null?0:object.countRead;
                var params={};
                params.guidReprDeliverCd = object.guidReprDeliverCd;
                params.pick = object.codCd;
                params.attendanceBookDate = object.tgtYmd.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.eventStsDiv = object.eventStsDiv;
                params.add = 1;
                params.startDt = object.pubStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.endTime = object.pubEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                return "<a onclick='showDetail(\"" +  object.guidReprDeliverCd  + "\"" + "," + 1 + "," + JSON.stringify(params) + ")'>" + count + "</a>";
            }

        }
        /* 2020/12/07 V9.0 cuikailin modify end */
        ,
        'tgtYmd': {label: '対象年月日', name: 'tgtYmd', index: 'tgtYmd', width: 150, sortable: false, align: "center",
            formatter(cell, option, object){
                var date = new Date(object.tgtYmd.replace(/-/g, '/'));
                return date.Format("yyyy/MM/dd");
            }
        }
        ,

        'updDatime': {label: '更新日', name: 'updDatime', index: 'updDatime', width: 150, sortable: false, align: "center",
            formatter(cell, option, object){
                var date = new Date(object.updDatime.replace(/-/g, '/'));
                return date.Format("yyyy/MM/dd");
            }
        },
        'cretUsrId': {label: '作成者', name: 'cretUsrId', index: 'cretUsrId', width: 150, sortable: false, align: "center"
        },
        'updUsrId': {label: '更新者', name: 'updUsrId', index: 'updUsrId', width: 150, sortable: false, align: "center"
        }
        ,

        'operation': {label: '操作', name: 'operation', index: 'operation', width: 300, sortable: false, align: "center",
            formatter(cell, option, object) {
                var params={};
                params.guidReprDeliverCd = object.guidReprDeliverCd;
                params.pick = object.codCd;
                params.attendanceBookDate = object.tgtYmd.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.eventStsDiv = object.eventStsDiv;
                params.add = 1;
                params.startDt = object.pubStartDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                params.endTime = object.pubEndDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3%20$4:$5');
                //2021/01/15 liyuhuan 8.0 modify start
                if (object.codCd=="1"){
                    return "<button  class='tbl_btn' onclick=toF21011('"+JSON.stringify(params)+"')> 編集</button>"
                        + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                        "<button  class='tbl_btn' onclick=deleteButton('"+object.guidReprDeliverCd + "','"+ object.codCd + "')  >削除</button>"
                } else {
                    return "<button  class='tbl_btn' style='background-color: #ccc;pointer-events: none;' > 編集</button>"
                        + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                        "<button  class='tbl_btn' style='background-color: #ccc;pointer-events: none;'  >削除</button>"
                }
                //2021/01/15 liyuhuan 8.0 modify end
            }

        }
    }

var dspItems = [];
// 表示項目
var colList = [];
// 表示可能項目
var hiddenList = [];
//表示しなければならない項目
var disabledItems = [];
function getShowItems() {
    $.ajax({
        url: ctxPath + '/manager/F21010/getshowItems',
        type: 'GET',
        data: {},
        async: false,
        success: function (data) {
            hiddenList = data.displayItems.allItems.split(",");
            dspItems = data.displayItems.dspItems.split(",");
            disabledItems = data.displayItems.mustItems.split(",");
            for (var i = 0; i < dspItems.length; i++) {
                list.push(dist[dspItems[i]]);
            }
            return list;
        }
    })

}



var vm = new Vue({
    el: '#main',
    data: {},
    mounted: function () {
        getShowItems();
        var params = {
            eventStsDiv:null,
            pubStartDt:null,
            pubEndDt:null,
            cretTimeStart:null,
            cretTimeEnd:null,
            updDatimeStart:null,
            updDatimeEnd:null,
            cretUserName:null,
            updUserName:null
        };
        this.getInfo(params);
    },
    methods: {
        getInfo: function (params) {
            $.jgrid.gridUnload("jqGrid");
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F21010/init',
                datatype: "json",
                postData: {
                    params:JSON.stringify(params)
                },
                colModel: list,
                viewrecords: true,
                regional: 'jp',
                rowNum: 10,
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
                        records: "page.totalCount",
                    }
                ,
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                gridComplete: function (data) {
                }
                ,
                loadComplete: function (data) {
                    colList = $("#jqGrid").jqGrid('getGridParam', 'colModel');
                    for (var i = 0; i < colList.length; i++) {
                        for (var j = 0; j < hiddenList.length; j++) {
                            if (hiddenList[j] == colList[i].name) {
                                hiddenList.splice(j, 1);
                            }
                        }
                    }
                    if (data.code!=0){
                        showMsg(data.msg);
                    }


                    $("#jqGrid").setGridHeight($(window).height() * 0.7);
                }
            })
        }
    }
});

function toF21011(params){
    var params1 = JSON.parse(params);
    var date = new Date( params1.attendanceBookDate.replace(/%20/, ' '));
    params1.attendanceBookDate = date.Format("yyyy/MM/dd");
    params1.startDt = params1.startDt.replace(/%20/, ' ');
    params1.endTime = params1.endTime.replace(/%20/, ' ');
    window.location.href = 'F21011.html?' + $.param(params1);
}

$("#additem").click(function (guidReprDeliverCd,pick) {
    window.sessionStorage.removeItem("params");
    window.location.href = 'F21011.html?';
});
var flag = false;
// 検索リンク押下
$("#search").click(function () {
    if ($("#message") != null) {
        $(".topMessage").removeClass("hasHeight");
        $(".topMessage").html("");
        $(".topMessage").css("display","none");
    }
    var srcWidth = $(window).width() * 0.35 + "px";
    var srcHeight = $(window).height() * 0.45 + "px";
    layer.open({
        id: 'F21010-1',
        type: 2,
        anim: 2,
        skin: "layui-layer-myskin",
        title: "検索項目",
        shade: 0.2,
        closeBtn: 1,
        shadeClose: false,
        move: '.layui-layer-title',
        area: [srcWidth, srcHeight],
        resize: false,
        content: ["../pop/F21010-1.html", 'no'],
        success: function (layero, index) {
        },
        end:function () {
            if (flag){
                vm.getInfo(vm.params);
            }
        }


    });
});

$(function () {
    // 表示項目変更ボタン押下
    $("#updateitem").click(function () {
        if ($("#message") != null) {
            $(".topMessage").removeClass("hasHeight");
            $(".topMessage").html("");
            $(".topMessage").css("display","none");
        }
        var srcWidth = $(window).width() * 0.7 + "px";
        var srcHeight = $(window).height() * 0.7 + "px";
        layer.open({
            id: 'F21010-2',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "表示可能項目",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F21010-2.html", 'no'],
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var form_left = body.find('#form_left');
                for (var j = 0; j < hiddenList.length; j++) {
                    var str = "<div class='item_div'><input type='checkbox' value='" + hiddenList[j] + "' content='" + wholeList[hiddenList[j]] + "'/><span class='cont'>" + wholeList[hiddenList[j]] + "</span></div>";
                    $(form_left).append(str);
                }
                var form_right = body.find('#form_right');
                for (var i = 0; i < colList.length; i++) {
                    if (colList[i].name == 'cb') {
                        colList.splice(i, 1);
                    }
                }
                for (var j = 0; j < colList.length; j++) {
                    var str = "<div class='item_div'><input type='checkbox' value='" + colList[j].name + "' content='" + wholeList[colList[j].name] + "'/><span class='cont'>" + wholeList[colList[j].name] + "</span></div>";
                    $(form_right).append(str);
                }
            },
            end:function () {
                if (closeFlg) {
                    window.location.reload();
                }
            }
        });
    });
});
// 削除リンク押下
function deleteButton(guidReprDeliverCd,codCd) {
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
            $.post({
                url: ctxPath + '/manager/F21010/del',
                type: 'POST',
                data: {
                    guidReprDeliverCd: guidReprDeliverCd,
                    eventStsDiv:codCd
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        // var idx = layer.confirm($.format($.msg.MSGCOMN0022,"削除"), {
                        //     skin: 'layui-layer-molv',
                        //     title: '確認',
                        //     closeBtn: 0,
                        //     anim: -1,
                        //     btn: ['確認'],
                        //     yes: function () {
                                location.reload();
                            //     layer.close(idx);
                            // }
                        // })

                    }
                }
            });
        }
    })
}
/* 2020/12/07 V9.0 cuikailin modify start */
function showDetail(guidReprDeliverCd,readFlg,params) {
    var date = new Date( params.attendanceBookDate.replace(/%20/, ' '));
    params.attendanceBookDate = date.Format("yyyy/MM/dd");
    params.startDt = params.startDt.replace(/%20/, ' ');
    params.endTime = params.endTime.replace(/%20/, ' ');
    params.guidReprDeliverCd = guidReprDeliverCd;
    params.readFlg = readFlg;
    //20201111 huangxinliang modify start
    window.location.href = "../manager/F08023.html?" + $.param(params);
    //20201111 huangxinliang modify end
}
/* 2020/12/07 V9.0 cuikailin modify end */
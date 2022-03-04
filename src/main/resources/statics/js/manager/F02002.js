
$(function () {
    $(window).resize(function(){
        // $("#jqGrid").setGridWidth($(window).width() * 0.99);
        $("#jqGrid").setGridHeight($(window).height() * 0.6);
    });
});

var vm = new Vue({
    el: '#vue',
    data: {
        // 本組織及び上下層組織リストの取得
        orgList:[],
        // 学年区分を取得
        schyDiv:[],
        // 教科区分を取得
        subjtDiv:[],
        // 組織
        mstOrgEntity:[],
        // 本組織及全部の下層組織、上層組織リストを取得する
        orgAll:[]
    },
    mounted: function () {
        this.getInfo();
    },
    updated:function(){
        $("input").keyup(function () {
            $("#message").hide();
        });
        $("input").change(function () {
            $("#message").hide();
        });
        $("select").change(function () {
            $("#message").hide();
        });
    },
    methods: {
        getInfo: function () {
            var width = $(window).width()*0.99;
            $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/f02002/init',
                datatype: "json",
                colModel: [
                    {label: '単元ＩＤ', name: 'id', index: 'id', width: 40, key: true,sortable:false, align: "center"},
                    {label: '組織名', name: '', index: 'orgId'+' '+'orgNm', width: 100,sortable:false,align: "center", formatter: function (cell,option, object) {
                            return object.orgId + ' ' + object.orgNm;
                        }},
                    {label: '学年', name: 'codValue1', index: 'codValue1',sortable:false, width: 50 ,align: "center"},
                    {label: '教科', name: 'codValue2', index: 'codValue2',sortable:false, width: 30, align: "center"},
                    {label: '単元管理CD', name: 'unitMstCd', index: 'unitMstCd',sortable:false, width: 50, align: "center"},
                    {label: '章名', name: 'chaptNm', index: 'chaptNm', width: 100 ,sortable:false,align: "center"},
                    {label: '節名', name: 'sectnNm', index: 'sectnNm', width: 100 ,sortable:false,align: "center"},
                    {label: '単元名', name: 'unitNm', index: 'unitNm', width: 100 ,sortable:false,align: "center"},
                    {
                        label: '', name: '', index: 'upd_usr_id', width: 50,sortable:false, formatter: function (cell,option, object) {
                            if (object.flg == 1) {
                                return "<button onclick=openF03005(" + object.id + ")>編集</button>";
                            }else {
                                return '';
                            }
                        }
                    },
                    {
                        label: '', name: '', index: 'del_flg', width: 50,sortable:false, formatter: function (cell, option, object) {
                            if (object.flg == 1) {
                                return "<button onclick=deleteFn(" + object.id + ",'" + object.updDatimeFormat + "')>削除</button>";
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
                    // $("#jqGrid").setGridWidth($(window).width() * 0.99);
                    // $("#jqGrid").setGridHeight($(window).height() * 0.6);
                },
                loadComplete: function (data) {
                    if (data.schyDiv) {
                        vm.schyDiv = data.schyDiv;
                    }
                    if (data.subjtDiv){
                        vm.subjtDiv = data.subjtDiv;
                    }
                    if (data.mstOrgEntity){
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    if (data.orgAll){
                        vm.orgAll = data.orgAll;
                    }
                    if (data.unitList){
                        showMsg(data.unitList);
                    }
                    if (data.code != 0) {
                        showMsg(data.msg);
                    }else {
                        if (data.orgList){
                            vm.orgList = data.orgList;
                        }
                    }
                }
            });
            var srcHeight = $(window).height() - ($(window).width() / 100) * 28;
            $(".ui-jqgrid-bdiv").css("height", srcHeight);
        },
        select: function (){
            $("#jqGrid").jqGrid('setGridParam',{
                url: ctxPath + '/manager/f02002/search',
                datatype:"json",
                postData:{
                    orgId:$("#orgId").val(),
                    schyDiv:$("#schyDiv").val(),
                    subjtDiv:$("#subjtDiv").val(),
                    unitNm:$("#unitNm").val()
                },
                page:0
            }).trigger("reloadGrid");
        }
    }
});
// IDを引き渡し、F03005_単元追加・編集画面へ遷移する。
function openF03005 (unitId) {
    var index = layer.open({
        id: 'f03005',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['1000px', '400px'],
        fixed: true,
        resize: false,
        content: ["../pop/F03005.html?unitId="+unitId, 'no'],
        success: function (layero, index) {
        }
    });
}
// F03005_単元追加・編集画面へ遷移する。
function newF03005 () {
    var index = layer.open({
        id: 'f03005',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['1000px', '400px'],
        fixed: true,
        resize: false,
        content: ["../pop/F03005.html", 'no'],
        success: function (layero, index) {
        }
    });
}
// ・確認ダイアログをポップアップし、ＯＫ押下後、単元マスタから削除する。
function deleteFn(unitId,updateTime) {
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
                url: ctxPath + '/manager/f02002/delete',
                datatype:"json",
                data:{
                    unitId:unitId,
                    updateTime:updateTime
                },
                type:'POST',
                success:function (data) {
                    if (data.code == 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022,"削除"),{btn:['確認']},function(){
                        //     layer.close(msg);
                            parent.location.reload();
                        // });
                    } else {
                        layer.close(msg);
                        showMsg(data.msg);
                    }
                }
            });
        }
    });

}
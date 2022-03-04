var param=getParam();
var orgId;
var times=0;
window.onload=function() {
    var srcHeight = $(window).height() - $(window).width() * 0.12 - 62;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
};
$(function () {
    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F03004/init',
        datatype: "json",
        postData:{
            schyDiv:param.schyDiv,
            subjtDiv:param.subjtDiv,
        },
        colModel: [
            {label: '単元ID', name: 'id', index: 'cod_id', width: 25, key: true, sortable: false, align: "center"},
            {label: '組織名', name: '', index: 'cod_key', width: 80, sortable: false,align:'center',
                formatter:function (cell,option,ob) {
                    return ob.orgId+" "+ob.orgNm;
                }},
            {label: '単元管理CD', name: 'unitMstCd', index: 'cod_key', width: 40, sortable: false,align:'center'},
            {label: '章名', name: 'chaptNm', index: 'cod_key', width: 80, sortable: false,align:'center'},
            {label: '節名', name: 'sectnNm', index: 'cod_descrp', width: 80, sortable: false,align:'center'},
            {label: '単元名', name: 'unitNm', index: 'cret_datime', sortable: false,align:'center',width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 30,
        rowList: [10, 30, 50],
        rownumbers: false,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
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
            if (data.code != 0) {
                layer.alert(data.msg);
            }
            if (times == 0) {
                if (data.orgList != null) {
                    var str = "";
                    for (var i = 0; i < data.orgList.length; i++) {
                        var org = data.orgList[i];
                        if (org.orgId == data.org.orgId) {
                            str += "<option value='" + org.orgId + "' selected>" + org.orgId + " " + org.orgNm + "</option>";
                        } else {
                            str += "<option value='" + org.orgId + "'>" + org.orgId + " " + org.orgNm + "</option>";
                        }
                    }
                    $("#orgId").html(str);
                }
                if (data.org.orgId != null) {
                    $("#showOrgId").text(data.org.orgId);
                }
                if (data.org.orgNm != null) {
                    $("#orgNm").text(getOrgName(data.org.orgNm));
                }
                if (data.schy != null) {
                    $("#schy").text(data.schy);
                }
                if (data.subjt != null) {
                    $("#subjt").text(data.subjt);
                }
            }
        }
    });
});
function cancelFn(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function searchFn() {
    times=1;
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F03004/init',
        datatype: "json",
        postData:{
            schyDiv:param.schyDiv,
            subjtDiv:param.subjtDiv,
            orgId:$("#orgId option:selected").val(),
            unitNm:$("#unitNm").val(),
            sectnNm:$("#sectnNm").val(),
            chaptNm:$("#chaptNm").val()
        },
        page: 1
    }).trigger("reloadGrid");
}
function addFn() {
    var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
    if(ids.length==0){
        layer.alert($.format($.msg.MSGCOMN0028,"教科書単元情報"));
        return;
    }
    for(var i=0;i<ids.length;i++) {
        var rowData = $("#jqGrid").jqGrid('getRowData', ids[i]);
        var addItem={};
        addItem.unitId=rowData.id;
        addItem.unitDispyNm=rowData.unitNm;
        addItem.chaptNm=rowData.chaptNm;
        addItem.sectnNm=rowData.sectnNm;
        addItem.planLearnSeasn=parent.vm.mstLearnSeasnEntities[0].planLearnSeasn;
        addItem.planLearnTm=parent.vm.planTmList[1];
        parent.vm.list.push(addItem);
    }
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
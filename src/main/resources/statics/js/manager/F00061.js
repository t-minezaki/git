var param=getParam();
var orgId;
var times=0;
var orgIdList = [];
window.onload=function() {
    var srcHeight = $(window).height() - $(window).width() * 0.12 - 62 -($(window).width()/100)*2.6;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
};
var height = $(window).height()*0.55;
var width = $(window).width()*0.99;
$(function () {

    // $(window).resize(function(){
    //     $("#jqGrid").setGridWidth($(window).width() * 0.98);
    //     $("#jqGrid").setGridHeight($(window).height() * 0.45);
    // });

    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F00061/init',
        datatype: "json",
        postData:{
            orgId: window.parent.$("#orgList").val()
        },
        colModel: [
            {label: '組織名', name: 'orgNm', index: 'orgNm', width: 50, sortable:false, align: "center"},
            {label: '生徒ID', name: 'stuId', index: 'cod_id', width: 50, sortable:false, align: "center"},
            {label: '生徒姓名', name: 'stuNm', index: 'cod_key',  sortable:false,width: 80,align:'center'},
            {label: '性別', name: 'gendr', index: 'cod_descrp', sortable:false, width: 80,align:'center'},
            {label: '生年月日', name: 'birthdStr', index: 'cret_datime', sortable:false,align:'center',width: 80},
            {label: '学年', name: 'schy', index: 'cod_descrp', width: 80, sortable:false,align:'center'},
            {label: '先生ID', name: 'mentorId', index: 'cret_datime', sortable:false,align:'center',width: 40},
            {label: '先生姓名', name: 'mentorNm', index: 'cret_datime', sortable:false,align:'center',width: 80},
            {label: '', name: '', index: 'cret_datime',align:'center', sortable:false,align:'center',width: 100
                ,formatter:function (cell,option,object) {
                    return "<button onclick='to00062(\"" + object.id + "\")'>編集</button>" +
                        "&nbsp" + "&nbsp" +
                        "<button onclick='deteleFn(\"" + object.id + "\",\"" + object.updDatimeStr + "\")'>削除</button>";
                }
            },
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
            // $("#jqGrid").setGridWidth($(window).width() * 0.98);
            // $("#jqGrid").setGridHeight($(window).height() * 0.45);
        },
        loadComplete: function (data) {
            if(times==0){
                var str="<option value=''></option>";
                for(var i=0;i<data.schyList.length;i++){
                    var schy=data.schyList[i];
                    str+="<option value='"+schy.codCd+"'>"+schy.codValue+"</option>";
                }
                $("#schy").html(str);
                str="<option value=''></option>";
                for(var i=0;i<data.orgList.length;i++){
                    var orgIdi=data.orgList[i];
                    str+="<option value='"+orgIdi.orgId+"'>"+orgIdi.orgNm+"</option>";
                }
                $("#orgIdi").html(str);
                if(data.org){
                    $("#orgId").text(data.org.orgId);
                    $("#orgNm").text(getOrgName(data.org.orgNm));
                }
            }
            if (data.code != 0) {
                showMsg(data.msg);
            }
        }
    });
    /* 2020/11/26 V9.0 cuikailin modify start */
    // var srcHeight = $(window).height() - 62 -($(window).width()/100) * 23.6;
    // $(".ui-jqgrid-bdiv").css("height", srcHeight);
    /* 2020/11/26 V9.0 cuikailin modify end */
});
function deteleFn(id,updDatimeStr){
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00061/delete',
                type: 'POST',
                data: {
                    id: id,
                    updDatimeStr: updDatimeStr
                },
                success: function (data) {
                    if (data.code === 0) {
                        // var alert = layer.alert($.format($.msg.MSGCOMN0022, "削除"),{
                        //     btn:['OK'],
                        //     yes:function () {
                        //         layer.close(alert);
                                location.reload();

                        //     }
                        // });
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
            layer.close(index);
        }
    })
}
function to00062(id) {
    window.location.href='./F00062.html?id=' + id;
}
function searchFn() {
    times=1;
    $("#jqGrid").jqGrid('setGridParam', {
        url: ctxPath + '/manager/F00061/init',
        datatype: "json",
        postData:{
            orgId: window.parent.$("#orgList").val(),
            stuId:$("#stuId").val(),
            mentorId:$("#mentorId").val(),
            stuNm:$("#stuNm").val(),
            mentorNm:$("#mentorNm").val(),
            schyDiv:$("#schy option:selected").val(),
            orgIdStringList:JSON.stringify(orgIdList)
        },
        page: 1
    }).trigger("reloadGrid");
}
function toF00062() {
    window.location.href="./F00062.html";
}
$(".inputMessage button").click(function () {
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
        content: ["../pop/F09010.html", 'no'],
        success: function (layero, index) {
        },
    })
})
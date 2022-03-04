var page = 1;
var pageSize = 15;
var param  = getParam();
var stuId ;
var complimentDiv='';
var codValue;
var content="";
var params ={};
var flag=false;
list=[
    {
        //日付_タイトル
        label: '日付',
        name: 'complimentDt',
        index: 'complimentDt',
        width: 150,
        // key: true,
        sortable: false,
        resizable : false,
        align: "center",
        formatter(cell,option,object){
            return object.complimentDt.substring(0,10);

        }
    },
    {
        //コメント_タイトル
        label: 'コメント',
        name: 'complimentCont',
        index: 'complimentCont',
        resizable : false,
        width: 500,
        // key: true,
        sortable: false,
        align: "center",
        formatter(cell,option,object){
            var codValue2 = object.compliment === null? '' : '［' + object.compliment + '］' + '<br>';
            return '<p class="comment">' + codValue2  + cell + '</p>';
        }
    },
    // {
    //     label: '保護者ID',
    //     name: 'guardId',
    //     index: 'complimentCont',
    //     width: 500,
    //     // key: true,
    //     sortable: false,
    //     align: "center",
    //     hidden:true
    // },
    {
        //未読・既読_タイトル
        label: 'スタンブ',
        name: 'operation',
        index: 'operation',
        resizable : false,
        width: 150,
        // key: true,
        sortable: false,
        align: "center",
        formatter(cell, option, object) {
            if (object.codValue2) {
                params.guardId=object.guardId;
                codValue = object.codValue2;
                return "<button  class='tbl_btn' onclick=toPop('" + codValue + "')  ></button></div>"
            }else {
                return "";
            }

        }
    },
    {
        label: '未読・既読',
        name: 'readingStsDiv',
        index: 'readingStsDiv',
        width: 150,
        // key: true,
        sortable: false,
        align: "center",
        formatter(cell,option,object){
            if (object.readingStsDiv==0){
                object.readingStsDiv = '未読';
                return object.readingStsDiv;
            } else {
                object.readingStsDiv = '-';
                return object.readingStsDiv;
            }

        }
    }
];
var width = $(window).width()*0.9;
var srcHeight = $(window).height() * 0.6;
var guardId='';
function reload() {
    var vm =new Vue({
        el:"main",
        data:{
            guardId:''
        },
        mounted: function () {
            this.goInfo(stuId);
        },
        methods:{
            goInfo:function (stuId) {
                $.jgrid.gridUnload("jqGrid");
                $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F21022/init',
                    datatype: "json",
                    postData: {
                        stuId:stuId
                    },
                    colModel: list,
                    viewrecords: true,
                    regional: 'jp',
                    height: srcHeight,
                    width:width,
                    forceFit:true,
                    rowNum: 15,
                    // rowList: [10, 30, 50],
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
                        if (data.code!=0){
                            showMsg(data.msg);
                        }else {
                            guardId = data.guardId;
                        }
                        // jqGridはウィンドウサイズに合わせて適応する
                        $(window).resize(function () {
                            $("#jqGrid").setGridHeight($(window).height() * 0.6);
                            $("#jqGrid").setGridWidth($(window).width() * 0.9);
                        });
                    }

                })
            }
        }
    })
};

$(function () {
    reload();
});

function toPop(codValue){
    //alert(url);
    var img_infor = "<img style='width:170px !important;' src='" + codValue + "' />";
    // this.css("width","200px");
    layer.open({
        type: 1,
        closeBtn: 0,
        shade: 0.1,
        title: false,
        //skin: 'layui-layer-nobg',
        shadeClose: true,
        resize: false,
        // area:['auto','auto'],
        area: [170+'px', 126+'px'],
        // area: [img.width + '100' + 'px', img.height + '100' + 'px'],    
        content: img_infor  
    });

}

var closeFlg=false;
//F21032生徒指定画面（POP）へ遷移する。
$("#toF21032").click(function () {
    var srcWidth = "750px";
    var srcHeight = "400px";
    layer.open({
        id: 'F21032',
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
        content: ["../pop/F21032.html", 'no'],
        success: function (layero, index) {
        },
        end: function () {
            try{
                stuId = stuList[0];
                $.ajax({
                    url: ctxPath + '/manager/F21020/init2',
                    type: 'get',
                    datatype: 'json',
                    data: {
                        stuId: stuId
                    },
                    success: function (data) {
                        if (data.stuNm) {
                            $(".stuNm").val(data.stuNm);
                        }
                    }
                });
                reload()
            }catch(e){
            }
        }
    });
});
//F21023_ ほめポイントPOP画面（PC）
$("#toF21023").click(function () {
    var srcWidth = "450px";
    var srcHeight = "250px";
    layer.open({
        id: 'F21032',
        type: 2,
        // anim: 2,
        skin: "layui-layer-myskin",
        shade: 0.2,
        closeBtn: 1,
        shadeClose: false,
        title: [" ","background: #EFEFEF;background-color:#EFEFEF !important;border-bottom:1px solid #EFEFEF!important"],
        move: '.layui-layer-title',
        area: [srcWidth, srcHeight],
        resize: false,
        fixed: true,
        content: ["../pop/F21023.html", 'no'],
        success: function (layero, index) {
        },
        end: function () {
        }
    });
});
function submit() {
    if (stuId == null || stuId === ''){
        showMsg($.format($.msg.MSGCOMN0152, '生徒'));
        return ;
    }
    if ($("#content").val() === '' && (complimentDiv == null || complimentDiv === '')){
        showMsg($.format($.msg.MSGCOMN0153, 'コメント', 'スタンプ'));
        return;
    }
    if (guardId == null || guardId === ''){
        showMsg($.format($.msg.MSGCOMN0093));
        return ;
    }
    $.ajax({
        url: ctxPath + '/manager/F21022/submit',
        type: 'GET',
        data: {
            stuId:stuId,
            complimentDiv:complimentDiv,
            content:$("#content").val()
        },
        success: function (data) {
            if (data.code != 0) {
                var idx = layer.confirm(data.msg, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    btn1: function () {
                        layer.close(idx);
                    }
                });
            }else {
                // var idx = layer.confirm($.format($.msg.MSGCOMN0022,'送信'),{
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     btn1: function () {
                //         layer.close(idx);
                        reload();
                //     }
                // });
            }
            $("#content").val("");
            $(".stamp_img_div img").remove();
            complimentDiv='';
        },
        end:function () {

        }

    });
};
// サブウィンドウインデックスを取得する
var idx = parent.layer.getFrameIndex(window.name);

var vm = new Vue({
    el:"#app",
    data:{
        mstCodDEntityList:[],
        pick: 0
    },
    mounted: function () {
        this.init();
    },
    updated: function () {
    },
    methods: {
        init:function () {
            $.ajax({
                url: ctxPath + '/manager/F21011/getCod',
                type: "GET",
                data: {},
                success:function (data) {
                    vm.mstCodDEntityList = data.mstCodDEntityList;
                    for (index in vm.mstCodDEntityList){
                        if (vm.mstCodDEntityList[index].codCd == '0'){
                            vm.pick = parseInt(vm.mstCodDEntityList[index].codCd);
                        }
                    }
                }
            })
        }
    },

});
$(function () {
    // laydate日時を設ける
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#cretTimeStart").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeTwo',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#cretTimeEnd").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeThree',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#updDatimeStart").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeFour',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#updDatimeEnd").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeFive',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#pubStartDt").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeSix',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#pubEndDt").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });
    });





    // 検索ボタン押下後
    $("#submitbtn").click(function () {
        // var tmplateId = $("#template").val();
        // var ctgyNm = $("#ctgy_nm").val();
        // var eventTitle = $("#event_title").val();
        // var cretUserName = $("#cretUserName").val();
        // var updUserName = $("#updUserName").val();
        // var eventStsDiv = $("#status").val();
        // var object = $("#object").val();
        var pubStartDt = $("#pubStartDt").val();
        var pubEndDt = $("#pubEndDt").val();
        var cretTimeStart = $("#cretTimeStart").val();
        var cretTimeEnd = $("#cretTimeEnd").val();
        var updDatimeStart = $("#updDatimeStart").val();
        var updDatimeEnd = $("#updDatimeEnd").val();
        var cretUserName = $("#cretUserName").val();
        var updUserName = $("#updUserName").val();
        var params = {
            eventStsDiv:vm.pick,
            pubStartDt:pubStartDt,
            pubEndDt:pubEndDt,
            cretTimeStart:cretTimeStart,
            cretTimeEnd:cretTimeEnd,
            updDatimeStart:updDatimeStart,
            updDatimeEnd:updDatimeEnd,
            cretUserName:cretUserName,
            updUserName:updUserName
        };
        parent.vm.params = params;
        parent.flag = true;
        parent.layer.close(idx);

    });

    // closeボタン押下後
    $("#close_btn").click(function () {

        parent.layer.close(index);
    });

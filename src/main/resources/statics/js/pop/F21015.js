var param = getParam();
var vue = new Vue({
    el: ".v_If",
    data: {
        pageDiv : '',
    },
    mounted: function(){
        this.pageDiv = param.pageDiv;
        this.pd();
    },
    methods:{
        pd: function () {
            $.ajax({
                data:{
                },
                async: true,
                datatype: 'json',
                success: function () {
                    if(param.pageDiv == 9){
                            $("#use_cont").text(decodeURI(param.text));
                    }
                    if(param.pageDiv == 10){
                            $("#guid_cont").text(decodeURI(param.text));
                    }
                    if(param.pageDiv == 11){
                            $("#hwk_cont").text(decodeURI(param.text));
                    }
                    if(param.pageDiv == 12){
                            $("#test_unit_nm").text(decodeURI(param.text));
                    }
                    if(param.pageDiv == 13){
                            $("#conc_item_cont").text(decodeURI(param.text));
                    }
                }
            })
        }
    }
});
$("#btn_okk").click(function () {
    var use_cont = $("#use_cont").val();
    var guid_cont = $("#guid_cont").val();
    var hwk_cont = $("#hwk_cont").val();
    var test_unit_nm = $("#test_unit_nm").val();
    var conc_item_cont = $("#conc_item_cont").val();
    if(param.pageDiv == 9)
    {
        parent.vm.endFlg = true;
        var a = use_cont.replace(/^\s+|\s+$/g,"");
        if(a.length>300)
        {
            showMsg($.format($.msg.MSGCOMD0017,'入力文字','300'));
            return false;
        }
        parent.vm.codKey = a;
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    if(param.pageDiv == 10)
    {
        parent.vm.endFlg = true;
        var b = guid_cont.replace(/^\s+|\s+$/g,"");
        if(b.length>300)
        {
            showMsg($.format($.msg.MSGCOMD0017,'入力文字','300'));
            return false;
        }
        parent.vm.codKey = b;
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    if(param.pageDiv == 11)
    {
        parent.vm.endFlg = true;
        var c = hwk_cont.replace(/^\s+|\s+$/g,"");
        if(c.length>300)
        {
            showMsg($.format($.msg.MSGCOMD0017,'入力文字','300'));
            return false;
        }
        parent.vm.codKey = c;
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    if(param.pageDiv == 12)
    {
        parent.vm.endFlg = true;
        var d = test_unit_nm.replace(/^\s+|\s+$/g,"");
        if(d.length>300)
        {
            showMsg($.format($.msg.MSGCOMD0017,'入力文字','300'));
            return false;
        }
        parent.vm.codKey = d;
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    if(param.pageDiv == 13)
    {
        parent.vm.endFlg = true;
        var e = conc_item_cont.replace(/^\s+|\s+$/g,"");
        if(e.length>300)
        {
            showMsg($.format($.msg.MSGCOMD0017,'入力文字','300'));
            return false;
        }
        parent.vm.codKey = e;
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
});


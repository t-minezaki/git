$(function () {
    $.get(ctxPath + "/manager/F08009/getTmplateAndStatusAndObject", function (rsp) {

        if (rsp.code == 0) {
            var tmplates = rsp.tmplates;
            if (tmplates) {
                for (var i = 0; i < tmplates.length; i++) {
                    var tmplate = tmplates[i];
                    $("#template").append("<option value='" + tmplate.id + "'>" + tmplate.tmplateTitle + "</option>");
                }
                var tmplateId = parent.tmplateId;
                if (tmplateId != null) {
                    $("#template").val(tmplateId);
                }
            }
            var statuslist = rsp.status;
            if (statuslist) {
                for (var i = 0; i < statuslist.length; i++) {
                    var status = statuslist[i];
                    $("#status").append("<option value='" + status.codCd + "'>" + status.codValue + "</option>");
                }
                var eventStsDiv = parent.eventStsDiv;
                if (eventStsDiv != null) {
                    $("#status").val(eventStsDiv);
                }
            }
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#pubStartDtStart").val(value)
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
            $("#pubStartDtEnd").val(value)
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
            $("#pubEndDtStart").val(value)
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
            $("#pubEndDtEnd").val(value)
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
            $("#applyStartDtStart").val(value)
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
            $("#applyStartDtEnd").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeSeven',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#applyEndDtStart").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeEight',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#applyEndDtEnd").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeNine',
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
        elem: '#timeTen',
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
        elem: '#timeEleven',
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
        elem: '#timeTwelve',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        btns: ['clear', 'confirm'],
        done: function (value) {
            $("#updDatimeEnd").val(value)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });

    // サブウィンドウインデックスを取得する
    var index = parent.layer.getFrameIndex(window.name);

    // 検索ボタン押下後
    $("#submitbtn").click(function () {
        var eventStsDiv = $("#status").val();
        var ctgyNm = $("#ctgy_nm").val();
        var eventTitle = $("#event_title").val();
        var cretDatimeStart = $("#cretTimeStart").val();
        var cretDatimeEnd = $("#cretTimeEnd").val();
        var updDatimeStart = $("#updDatimeStart").val();
        var updDatimeEnd = $("#updDatimeEnd").val();
        var cretUserName = $("#cretUserName").val();
        var updUserName = $("#updUserName").val();
        var pubStartDtStart = $("#pubStartDtStart").val();
        var pubStartDtEnd = $("#pubStartDtEnd").val();
        var pubEndDtStart = $("#pubEndDtStart").val();
        var pubEndDtEnd = $("#pubEndDtEnd").val();
        var applyStartDtStart = $("#applyStartDtStart").val();
        var applyStartDtEnd = $("#applyStartDtEnd").val();
        var applyEndDtStart = $("#applyEndDtStart").val();
        var applyEndDtEnd = $("#applyEndDtEnd").val();

        if(cretDatimeStart && cretDatimeEnd && cretDatimeStart != "" && cretDatimeEnd != "" && cretDatimeStart > cretDatimeEnd){

            var msg = $.format($.msg.MSGCOMN0024, "作成日終了","作成日開始");
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }
        if(updDatimeStart && updDatimeEnd && updDatimeStart != "" && updDatimeEnd != "" && updDatimeStart > updDatimeEnd){

            var msg = $.format($.msg.MSGCOMN0024, "最终更新日終了","最终更新日開始");
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }
        if(pubStartDtStart && pubStartDtEnd && pubStartDtStart != "" && pubStartDtEnd != "" && pubStartDtStart > pubStartDtEnd){

            var msg = $.format($.msg.MSGCOMN0024, "公開開始日終了","公開開始日開始");
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }
        if(pubEndDtStart && pubEndDtEnd && pubEndDtStart != "" && pubEndDtEnd != "" && pubEndDtStart > pubEndDtEnd){

            var msg = $.format($.msg.MSGCOMN0024, "公開終了日終了","公開終了日開始");
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }
        if(applyStartDtStart && applyStartDtEnd && applyStartDtStart != "" && applyStartDtEnd != "" && applyStartDtStart > applyStartDtEnd){

            var msg = $.format($.msg.MSGCOMN0024, "申請開始日終了","申請開始日開始");
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }
        if(applyEndDtStart && applyEndDtEnd && applyEndDtStart != "" && applyEndDtEnd != "" && applyEndDtStart > applyEndDtEnd){

            var msg = $.format($.msg.MSGCOMN0024, "申請終了日終了","申請終了日開始");;
            parent.layer.msg( msg,{
                icon:5
            });
            return ;
        }

        parent.layer.close(index);
        parent.reload(null, null, ctgyNm, eventTitle, cretDatimeStart, cretDatimeEnd, updDatimeStart, updDatimeEnd, cretUserName, updUserName, eventStsDiv,
            pubStartDtStart, pubStartDtEnd, pubEndDtStart, pubEndDtEnd, applyStartDtStart, applyStartDtEnd, applyEndDtStart, applyEndDtEnd);
    });

    // closeボタン押下後
    $("#close_btn").click(function () {
        parent.layer.close(index);
    });
});
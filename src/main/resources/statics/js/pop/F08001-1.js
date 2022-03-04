
$(function () {
    $.get(ctxPath + "/manager/F08001/getTmplateAndStatusAndObject", function (rsp) {

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
            var objectlist = rsp.objects;
            if (objectlist) {
                for (var i = 0; i < objectlist.length; i++) {
                    var object = objectlist[i];
                    $("#object").append("<option value='" + object.codCd + "'>" + object.codValue + "</option>");
                }
                var object = parent.object;
                if (object != null) {
                    $("#object").val(object);
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

    // サブウィンドウインデックスを取得する
    var index = parent.layer.getFrameIndex(window.name);

    // 検索ボタン押下後
    $("#submitbtn").click(function () {
        var tmplateId = $("#template").val();
        var ctgyNm = $("#ctgy_nm").val();
        var eventTitle = $("#event_title").val();
        var cretDatimeStart = $("#cretTimeStart").val();
        var cretDatimeEnd = $("#cretTimeEnd").val();
        var updDatimeStart = $("#updDatimeStart").val();
        var updDatimeEnd = $("#updDatimeEnd").val();
        var cretUserName = $("#cretUserName").val();
        var updUserName = $("#updUserName").val();
        var eventStsDiv = $("#status").val();
        var object = $("#object").val();

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

        parent.layer.close(index);
        parent.reload(null, null, tmplateId, ctgyNm, eventTitle, cretDatimeStart, cretDatimeEnd, updDatimeStart, updDatimeEnd, cretUserName, updUserName, eventStsDiv, object);
    });

    // closeボタン押下後
    $("#close_btn").click(function () {
        parent.layer.close(index);
    });
});
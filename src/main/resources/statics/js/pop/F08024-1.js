$(function () {
    $.get(ctxPath + "/manager/F08024/getTmplate", {
        tmplateTypeDiv: parent.tmplateTypeDiv
    }, function (rsp) {
        if (rsp.code == 0) {
            var tmplates = rsp.tmplates;
            if (tmplates) {
                for (var i = 0; i < tmplates.length; i++) {
                    var tmplate = tmplates[i];
                    var newTitle = subString(tmplate.tmplateTitle,24);
                    $("#template").append("<option value='" + tmplate.id + "' title='" + tmplate.tmplateTitle + "'>" + newTitle + "</option>");
                }
                var tmplateId = parent.tmplateId;
                var tmplateTypeDiv = parent.tmplateTypeDiv;
                if (tmplateId != null) {
                    $("#template").val(tmplateId);
                }
                if (tmplateTypeDiv != null) {
                    $("#tmplateType").val(tmplateTypeDiv);
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
        done: function (date) {
            $("#cretTimeStart").val(date)
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
        done: function (date) {
            $("#cretTimeEnd").val(date)
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
        done: function (date) {
            $("#updDatimeStart").val(date)
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
        done: function (date) {
            $("#updDatimeEnd").val(date)
        },ready: function(){
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });
    // サブウィンドウインデックスを取得する
    var index = parent.layer.getFrameIndex(window.name);
    // 検索ボタン押下後
    $("#submitbtn").click(function () {
        var tmplateType = $("#tmplateType").val();
        var tmplateId = $("#template").val();
        var ctgyNm = $("#ctgy_nm").val();
        var cretDatimeStart = $("#cretTimeStart").val();
        var cretDatimeEnd = $("#cretTimeEnd").val();
        var updDatimeStart = $("#updDatimeStart").val();
        var updDatimeEnd = $("#updDatimeEnd").val();
        var cretUserName = $("#cretUserName").val();
        var updUserName = $("#updUserName").val();
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
        parent.reload(null, null, tmplateId,tmplateType,ctgyNm, cretDatimeStart, cretDatimeEnd, updDatimeStart, updDatimeEnd, cretUserName, updUserName);
    });
    // closeボタン押下後
    $("#close_btn").click(function () {
        parent.layer.close(index);
    });
});
function subString(str, len) {
    var newLength = 0;
    var newStr = "";
    var chineseRegex = /[^\x00-\xff]/g;
    var singleChar = "";
    var strLength = str.replace(chineseRegex, "**").length;
    if (strLength > len) {
        for (var i = 0; i < strLength; i++) {
            singleChar = str.charAt(i).toString();
            if (singleChar.match(chineseRegex) != null) {
                newLength += 2;
            }
            else {
                newLength++;
            }
            if (newLength > len) {
                break;
            }
            newStr += singleChar;
        }
        if (strLength > len) {
            newStr += "...";
        }
    } else {
        newStr = str;
    }
    return newStr;
}
function changeTemplate(str) {
    var tmplateDiv= str;
    $("#template").empty();
    $("#template").append("<option></option>");
    $.get(ctxPath + "/manager/F08024/getOrderTmplate",
        {
            tmplateDiv: tmplateDiv
        },function (rsp) {
        if (rsp.code == 0) {
            var tmplates = rsp.tmplates;
            if (tmplates) {
                for (var i = 0; i < tmplates.length; i++) {
                    var tmplate = tmplates[i];
                    var newTitle = subString(tmplate.tmplateTitle,24);
                    $("#template").append("<option value='" + tmplate.id + "' title='" + tmplate.tmplateTitle + "'>" + newTitle + "</option>");
                }
            }
        }
    });
}
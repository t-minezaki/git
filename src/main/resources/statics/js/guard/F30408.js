
var param = getParam();
var applyId = '';
$(function () {
    $.get(ctxPath + '/guard/F30408/init',
        {
            eventId: param.eventId
        }, function (data) {
            if (data.code == 0) {
                $("#btn_ok").css("display", data.display);
                applyId = data.applyId;
            } else {
                showMsg(data.msg);
            }
        });
});

function toF30405() {
    window.location.href = "F30405.html?eventId=" + param.eventId +'&applyId=' + applyId;
}
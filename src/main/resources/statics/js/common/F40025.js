$('#no').click(function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});
$('#yes').click(function () {
    window.top.location.href = ctxPath + "/logout";
    return false;
});

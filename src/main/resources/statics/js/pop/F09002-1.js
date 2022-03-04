var param = getParam();

$(function () {

    var index = parent.layer.getFrameIndex(window.name);

    $("#btn_ok").bind("click", function () {

        var itime = 0;
        $(this).attr("disabled", "disabled");
        $(this).css("background-color", "#ccc");
        $("#span_info").html('ダウンロード中<i>' + itime + '</i>');
        var hurl = ctxPath + "/manager/F09002/getPrintFile?startRow=" + $("#row_select").val() + "&startCol=" + $("#col_select").val() + "&orgId=" + param.orgId + "&stuIdListStr=" + param.stuIdListStr;
        var xhr = new XMLHttpRequest();
        xhr.open('get', hurl, true);
        xhr.responseType = "blob";
        xhr.onload = function() {
            if (this.status === 200) {
                var blob = this.response;
                var reader = new FileReader;
                reader.readAsDataURL(blob);
                reader.onload = function(e) {
                    var headerName = xhr.getResponseHeader("Content-disposition");
                    var str = decodeURIComponent(headerName.substring(21));
                    var fileName = str.substring(0, str.indexOf(";"));
                    var a = document.createElement("a");
                    a.download = fileName;
                    a.href = e.target.result;
                    $("body").append(a);
                    a.click();
                    $(a).remove();

                    clearTimeout(downloadTimer);
                    $("#span_info").html("ダウンロード済み");
                    parent.layer.close(index);
                    parent.closeFlg = true;
                }
            }
        };
        xhr.send();

        var downloadTimer = setInterval(function(){
            $("#span_info").children("i").text(++itime);
    }, 1000);
    });
});
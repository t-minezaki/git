var ue;
$(function () {
    ue = UE.getEditor('editor');
    $("#editor").click(function () {
        if($("#message")!=null){
            $("#message").hide();
        }
    })
})
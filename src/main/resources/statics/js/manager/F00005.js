$(function () {
    var maxSize=5*1024*1024;//5M
   var srcHeight =  $(window).height() - $(window).width()*0.074;
    $(".content").css("height",srcHeight);
    $('#getPath1').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath1").attr("value", str);
    });
    $('#getPath2').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath2").attr("value", str);
    });
    $('#getPath3').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath3").attr("value", str);
    });
    $('#getPath4').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath4").attr("value", str);
    });
    $.ajax({
        url: ctxPath + '/manager/F00005/init',
        type:'GET',
        success:function (data) {
            if(data.code==0){
                $("#orgId").text(data.org.orgId);
                $("#orgNm").text(getOrgName(data.org.orgNm));
            }
        }
    });
    $("#importBtn").click(function () {
        if($("#showPath1").val()==''){
            showMsg($.format($.msg.MSGCOMN0074,'インポートファイル'));
            return;
        }
        var fileSize=$("#getPath1")[0].files[0].size;
        if(fileSize>maxSize){
            showMsg($.format($.msg.MSGCOMN0077,'ファイルの最大サイ'));
            return;
        }
        if($("#showPath1").val().substr($("#showPath1").val().lastIndexOf(".")+1)!='xlsx'){
            showMsg($.format($.msg.MSGCOMN0076,'xlsx'));
            return;
        }
        var formData=new FormData();
        if($("#getPath1")[0].files[0].size==0){
            showMsg($.format($.msg.MSGCOMN0075,$("#getPath1")[0].files[0].name))
            return;
        }
        formData.append('file',$("#getPath1")[0].files[0]);
        formData.append('type',$("input[name='dan']:checked").val());
        $.ajax({
            url: ctxPath + '/manager/F00005/import',
            type:'POST',
            cache: false,
            data:formData,
            processData: false,
            contentType: false,
            success:function (data) {
                $("#getPath1")[0].value = '';
                if(data.code!=0){
                    showMsg(data.msg);
                }else{
                    /*2021/02/01 liyuhuan add start */
                    var index = layer.confirm("インポートが完了しました", {
                        skin: 'layui-layer-molv4',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(index);
                        }
                    });
                    /*2021/02/01 liyuhuan add end */
                    // layer.alert($.format($.msg.MSGCOMN0014,"インポート"));
                }
            }
        });
    });
    $("#importBtn1").click(function () {
        if($("#showPath3").val()==''){
            showMsg($.format($.msg.MSGCOMN0074,'インポートファイル'));
            return;
        }
        var fileSize=$("#getPath3")[0].files[0].size;
        if(fileSize>maxSize){
            showMsg($.format($.msg.MSGCOMN0077,'ファイルの最大サイ'));
            return;
        }
        if($("#showPath3").val().substr($("#showPath3").val().lastIndexOf(".")+1)!='xlsx'){
            showMsg($.format($.msg.MSGCOMN0076,'xlsx'));
            return;
        }
        var formData=new FormData();
        if($("#getPath3")[0].files[0].size==0){
            showMsg($.format($.msg.MSGCOMN0075,$("#getPath3")[0].files[0].name))
            return;
        }
        formData.append('file',$("#getPath3")[0].files[0]);
        formData.append('type',$("input[name='dan1']:checked").val());
        $.ajax({
            url: ctxPath + '/manager/F00005/importStuGrp',
            type:'POST',
            cache: false,
            data:formData,
            processData: false,
            contentType: false,
            success:function (data) {
                if(data.code!=0){
                    showMsg(data.msg);
                }else{
                    /*2021/02/01 liyuhuan add start */
                    var index = layer.confirm("インポートが完了しました", {
                        skin: 'layui-layer-molv4',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(index);
                        }
                    });
                    /*2021/02/01 liyuhuan add end */
                    // layer.alert($.format($.msg.MSGCOMN0014,"インポート"));
                }
            }
        });
    });
    $("#exportBtn1").click(function () {
        $.ajax({
            url: ctxPath + '/manager/F00005/export',
            type:'POST',
            data:{

            },
            success:function (data) {
                if(data.code==0){
                    $("#exportForm #fileNm").val(data.fileNm);
                    $("#exportForm #div").val(0);
                    $("#exportForm").submit();
                }
            }
        })
    });
    $("#exportBtn2").click(function () {
        $.ajax({
            url: ctxPath + '/manager/F00005/exportStuGrp',
            type:'POST',
            data:{

            },
            success:function (data) {
                if(data.code==0){
                    $("#exportForm #fileNm").val(data.fileNm);
                    $("#exportForm #div").val(1);
                    $("#exportForm").submit();
                }
            }
        })
    });
});
function getTemplate(div) {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate input[name='div']").val(div);
    $("#getTemplate").submit();
}
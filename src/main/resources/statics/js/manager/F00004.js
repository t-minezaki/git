$(function () {
    var maxSize=5*1024*1024;//5M
    // 2021/02/26 liyuhuan modify start
    $('#getPath1').click(function () {
        $("#showPath1")[0].value = "";
        document.getElementById('getPath1').value = null;
    });
    $('#getPath2').click(function () {
        $("#showPath2")[0].value = "";
        document.getElementById('getPath2').value = null;
    });
    $('#getPath3').click(function () {
        $("#showPath3")[0].value = "";
        document.getElementById('getPath3').value = null;
    });
    $('#getPath4').click(function () {
        $("#showPath4")[0].value = "";
        document.getElementById('getPath4').value = null;
    });
    $('#getPath1').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath1").removeAttr("value");
        $("#showPath1")[0].value = "";
        $("#showPath1").val(str);
    });
    // 2021/02/26 huangxinliang modify end
    $('#getPath2').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath2").removeAttr("value");
        $("#showPath2")[0].value = "";
        $("#showPath2").val(str);
    });
    $('#getPath3').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath3").removeAttr("value");
        $("#showPath3")[0].value = "";
        $("#showPath3").val(str);
    });
    $('#getPath4').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\')+1);
        $("#showPath4").removeAttr("value");
        $("#showPath4")[0].value = "";
        $("#showPath4").val(str);
    });
   var srcHeight =  $(window).height() - $(window).width()*0.074;
    $(".content").css("height",srcHeight)
    $.ajax({
        url: ctxPath + '/manager/F00004/init',
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
        formData.append('type',$("input[name='one']:checked").val());
        formData.append('div',$("input[name='two']:checked").val());
        $.ajax({
            url: ctxPath + '/manager/F00004/import',
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
                    // layer.alert($.format($.msg.MSGCOMN0022,"組織の追加"));
                }
            }
        });
    });
    $("#stuWithGuardImportBtn").click(function () {
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
        $.ajax({
            url: ctxPath + '/manager/F00004/stuWithGuardImport',
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
                    // layer.alert($.format($.msg.MSGCOMN0022,"インポート"));
                }
            }
        });
    });
    $("#exportBtn").click(function () {
        $.ajax({
            url: ctxPath + '/manager/F00004/export/'+$("input[name='one']:checked").val(),
            type:'POST',
            data:{

            },
            success:function (data) {
                if(data.code==0){
                    $("#exportForm #fileNm").val(data.fileNm);
                    $("#exportForm #div").val($("input[name='one']:checked").val());
                    $("#exportForm").submit();
                }else {
                    layer.alert(data.msg);
                }
            }
        })
    });
    $("#stuWithGuardBtn").click(function () {
        $.ajax({
            url: ctxPath + '/manager/F00004/stuWithGuardCreate',
            type:'POST',
            data:{

            },
            success:function (data) {
                if(data.code==0){
                    $("#exportForm #fileNm").val(data.fileNm);
                    $("#exportForm #div").val(4);
                    $("#exportForm").submit();
                }
            }
        })
    });
});
function getTemplate() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate input[name='div']").val($("input[name='one']:checked").val());
    $("#getTemplate").submit();
}
function getTemplateOfStuWithGuard() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate input[name='div']").val(4);
    $("#getTemplate").submit();
}

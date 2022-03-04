var maxSize = 5 * 1024 * 1024;//5M
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.074;
    $(".content").css("height", srcHeight)

    $('#getPath1').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        $("#showPath1").attr("value", str);
    });
    $('#getPath2').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        $("#showPath2").attr("value", str);
    });
});

var vm = new Vue({
    el: ".content",
    data: {
        org: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F00006/Init',
                type: 'GET',
                data: {},
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                }
            })
        }
    }
});

$("#import").click(function () {
    if ($("#showPath1").val() == '') {
        showMsg($.format($.msg.MSGCOMN0074, 'インポートファイル'));
        return;
    }
    var fileSize = $("#getPath1")[0].files[0].size;
    if (fileSize > maxSize) {
        showMsg($.format($.msg.MSGCOMN0077, 'ファイルの最大サイ'));
        return;
    }
    if ($("#showPath1").val().substr($("#showPath1").val().lastIndexOf(".") + 1) != 'xlsx') {
        showMsg($.format($.msg.MSGCOMN0076, 'xlsx'));
        return;
    }
    var formData = new FormData();
    if ($("#getPath1")[0].files[0].size == 0) {
        showMsg($.format($.msg.MSGCOMN0075, $("#getPath1")[0].files[0].name))
        return;
    }
    formData.append('file', $("#getPath1")[0].files[0]);
    formData.append('type', $("input[name='dan']:checked").val());
    $.ajax({
        url: ctxPath + '/manager/F00006/Import',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType:
            false,
        success:
            function (data) {
                $("#getPath1")[0].value = '';
                if (data.code != 0) {
                    showMsg(data.msg);
                } else {
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
                    // parent.layer.alert($.format($.msg.MSGCOMN0014, "インポート"));
                }
            }
    })
})
$("#export").click(function () {
    $.ajax({
        url: ctxPath + '/manager/F00006/Export',
        type: 'GET',
        data: {},
        success: function (data) {
            if (data.code == 0) {
                $("#exportForm").attr("action",ctxPath+"/manager/F00006/Download");
                $("#exportForm #fileNm").val(data.fileNm);
                $("#exportForm").submit();
            } else {
                showMsg(data.msg)
            }
        }
    })
});
function getTemplate() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate").attr("action",ctxPath+"/manager/F00006/getTemplate");
    $("#getTemplate").submit();
}


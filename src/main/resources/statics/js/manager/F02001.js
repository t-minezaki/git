var maxSize = 5 * 1024 * 1024;//5M
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.074;
    $(".content").css("height", srcHeight)

    $('#getPath1').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        $("#showPath1").attr("value", str);
    });
});
var index1 = 0;
var index2 = 0;
var vm = new Vue({
    el: ".content",
    data: {
        org: '',
        f02001SchyDivList: '',
        f02001SubjtDivList: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F02001/Init',
                type: 'GET',
                data: {},
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if (data.f02001SchyDivList) {
                        vm.f02001SchyDivList = data.f02001SchyDivList;
                        if (index1 == 0) {
                            var schyDivList = data.f02001SchyDivList;
                            for (var i = 0; i < schyDivList.length; i++) {
                                $("#schyDiv").append("<option value='" + schyDivList[i].codCd + "'>" + schyDivList[i].codValue + "</option>");
                            }
                            index1++;
                        }
                    }
                    if (data.code == 0) {
                        if (data.f02001SubjtDivList) {
                            vm.f02001SubjtDivList = data.f02001SubjtDivList;
                            if (index2 == 0) {
                                var subjtDivList = data.f02001SubjtDivList;
                                for (var i = 0; i < subjtDivList.length; i++) {
                                    $("#subjtDiv").append("<option value='" + subjtDivList[i].codCd + "'>" + subjtDivList[i].codValue + "</option>");
                                }
                                index2++;
                            }
                        }
                    } else {
                        showMsg(data.msg);
                    }
                },
            })
        }
    }
});

$("#import").click(function () {
    //指定ファイル未入力チェック
    if ($("#showPath1").val() == '') {
        showMsg($.format($.msg.MSGCOMN0074, 'インポートファイル'));
        return;
    }
    //インポートファイル存在チェック
    if ($("#getPath1")[0].files[0].size == 0) {
        showMsg($.format($.msg.MSGCOMN0075, $("#getPath1")[0].files[0].name))
        return;
    }
    //インポートファイル拡張子不正チェック
    if ($("#showPath1").val().substr($("#showPath1").val().lastIndexOf(".") + 1) != 'xlsx') {
        showMsg($.format($.msg.MSGCOMN0076, 'xlsx'));
        return;
    }
    //インポートファイルサイズチェック
    var fileSize = $("#getPath1")[0].files[0].size;
    if (fileSize > maxSize) {
        showMsg($.format($.msg.MSGCOMN0077, 'ファイルの最大サイズ'));
        return;
    }
    var formData = new FormData();
    formData.append('file', $("#getPath1")[0].files[0]);
    formData.append('type', $("input[name='dan']:checked").val());
    $.ajax({
        url: ctxPath + '/manager/F02001/Import',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType:
            false,
        success: function (data) {
            if (data.code != 0) {
                showMsg(data.msg);
            } else {
                // var index = parent.layer.alert($.format($.msg.MSGCOMN0014, "インポート"), {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     btn1: function () {
                //         parent.layer.close(index);
                    // }
                // });
            }
        },
        error: function () {
        }
    })
});
$("#export").click(function () {
    $.ajax({
        url: ctxPath + '/manager/F02001/Export',
        type: 'GET',
        data: {
            schyDiv: $("#schyDiv option:selected").val(),
            subjtDiv: $("#subjtDiv option:selected").val()
        },
        success: function (data) {
            if (data.code == 0) {
                $("#exportForm").attr("action", ctxPath+"/manager/F02001/Download");
                $("#exportForm #fileNm").val(data.fileNm);
                $("#exportForm").submit();
            } else {
                showMsg(data.msg);
            }
        }
    })
});

function openF03005() {
    var index = layer.open({
        id: 'f03005',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['1000px', '400px'],
        fixed: true,
        resize: false,
        content: ["../pop/F03005.html", 'no'],
        success: function (layero, index) {
        }
    });
}

$("#addMstUnit").click(function () {
    // var schyDiv = $("#schyDiv").val();
    // var subjtDiv = $("#subjtDiv").val();
    openF03005();
})

function getTemplate() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#exportForm").attr("action", ctxPath+"/manager/F02001/getTemplate");
    $("#exportForm").submit();
}


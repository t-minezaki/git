var param = getParam();
$(function () {
    $("#moreColor").val(decodeURIComponent(param.color));
    var arr = [
        "#AD1457", "#F4511E", "#4C441", "#0B8043", "#F51B5", "#8E24AA",
        "#D81B60", "#EF6C00", "#C0CA33", "#009688", "#7986CB", "#795548",
        "#D50000", "#F09300", "#7CB342", "#039BE5", "#B39DDB", "#616161",
        "#E67C73", "#F6BF26", "#33B679", "#4285F4", "#9E69AF", "#A79B8E"
    ];
    var divColor = '';
    for (var i = 1; i <= 24; i++) {
        if ((i % 6) == 0) {
            divColor += '<div class="color"></div><br>';
        } else {
            divColor += '<div class="color"></div>';
        }
    }
    $(".arrColor").append(divColor);
    $.each(arr, function (index, element) {
        $(".color").eq(index).css("background", arr[index]);
    });

    // $(".colorMenu").bind("click", function (e) {
    //     $.each(arr, function (index, element) {
    //         $(".color").eq(index).css("background", arr[index]);
    //     });
    //     $(".container").show();
    //     // e.stopPropagation();
    // });
    // $(document).click(function () {
    //     $(".container").hide();
    // });
    $(".color").bind("click", function () {
        $(".selectedColor").css("background", $(this).css("background-color"));
    })
    $("#setColor").click(function () {
        var color = $(".selectedColor").css("background-color");
        color = rgb2hex(color);
        $.post(ctxPath + "/student/F10004/save",
            {
                id: param.id,
                color: color,
                blockId: param.blockId,
                subjtDiv: param.cod
            }, function (data) {
                if (data.code == 0) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                } else {
                    showMsg(data.msg);
                }
            });
    });
    $("#moreColor").change(function () {
        var color = document.getElementById("moreColor").value;
        // color = rgb2hex(color);
        $.post(ctxPath + "/student/F10004/save",
            {
                id: param.id,
                color: color,
                blockId: param.blockId,
                subjtDiv: param.cod
            }, function (data) {
                if (data.code == 0) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                } else {
                    showMsg(data.msg);
                }
            });
    })
    // function colorSelect() {
    //     var color = $(".selectedColor").css("background-color");
    //     color = rgb2hex(color);
    //     $.post(ctxPath + "/student/F10004/save",
    //         {
    //             id: param.id,
    //             color: color,
    //             blockId: param.blockId,
    //             subjtDiv: param.cod
    //         }, function (data) {
    //             if (data.code == 0) {
    //                 var index = parent.layer.getFrameIndex(window.name);
    //                 parent.layer.close(index);
    //                 parent.location.reload();
    //             } else {
    //                 showMsg(data.msg);
    //             }
    //         });
    // }

    function rgb2hex(rgb) {
        rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
        function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
        }
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }
})
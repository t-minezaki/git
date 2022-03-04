function blurFn() {
    $(".xbottom").addClass("disNone");
}
var vm = new Vue({
    el: "#mypage",
    data: {
        stuDto: {},
        stuId: "",
        birthd: ""
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/student/F10002/getStuMyPage',
                type: 'get',
                datatype: 'json',
                success: function (data) {
                    if (data.msg != "success") {
                        showMsg(data.msg);
                    } else {
                        if (data.stuDto.stuId != "") {
                            vm.stuId = data.stuDto.stuId;
                        }
                        vm.birthd = data.birthd;
                        vm.stuDto = data.stuDto;
                    }
                },
                error: function (data) {
                    showMsg(data.msg);
                }
            })
        },
        f10101: function () {
            window.location.href = "F10301.html";
        },
        f40008: function () {
            window.location.href = "../common/F40008.html";
        },
        f10003: function () {
            window.location.href = "F10003.html?id=f10002";
        },
        f10001: function () {
            window.location.href = "F10001.html?id=f10002";
        },
        updateFn:function () {
            $(".xbottom").removeClass("disNone");
            $("#updateMenu").addClass("active");
        },
        f10004: function () {
            window.location.href = "F10004.html?id=f10002";
        }

    }
})

window.onload = function (ev) {
    $("#iframe").contents().find("#mypage_img").css('width','50%');
}
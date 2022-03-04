var index = 1;
var pageSize = 50;
var srcHeight = $(window).height() * 0.2;
var srcWidth = $(window).width() * 0.315;
var count = 0;
var vu = new Vue({
    el: "#vue",
    data: {
        f40013DtoList: [],
        orgNm: '',
        orgId: '',
        tableList: [],
        orgName: "",
        orgIdCheck: "",
        firstCount: 0,
        orgNow: ""

    },
    mounted: function () {
        if (getCookie("key") === 'PUSHAPI') {
            $(".img_loginout").css("display", "none");
        }
        this.init();
    },
    updated: function () {
        // if(vu.firstCount <= 50) {
        //     $(".text").css("visibility", "hidden");
        // }
        if (vu.orgNow != "") {
            var spans = $("input[type='radio']");
            $.each(spans, function (index, singleSpan) {
                var text = $(singleSpan).val();
                if (text == vu.orgNow) {
                    $(singleSpan).prop("checked", true);

                }
            })
        }
        else {
            $(".div_select input:first").prop("checked", true);
        }
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/common/F40015/init',
                dataType: 'json',
                data: {
                    pageSize: pageSize,
                    orgId: this.orgId,
                    orgNm: this.orgName
                },
                type: 'GET',
                success: function (data) {
                    if (data.code === 0) {
                        if (count === 0) {
                            vu.firstCount = data.count.length;
                            count++;
                        }
                        vu.f40013DtoList = data.f40013DtoList;
                        vu.orgNow = data.orgNow;
                        if (vu.firstCount < 10) {
                            $(".mode2").css("display", "none");
                        } else {
                            $("#mode1").css("display", "none");
                            if (data.count.length > 50) {
                                var idx = layer.confirm($.msg.MSGCOMN0178, {
                                    skin: 'layui-layer-molv123',
                                    title: '',
                                    closeBtn: 0,
                                    anim: -1,
                                    btn: ['戻る'],
                                    yes: function () {
                                        layer.close(idx);  // 关闭layer
                                    }
                                })
                            }
                        }
                    }
                }
            });
        }, bindCheck: function ($event) {
            var pdiv = $event.target;
            var radio = $(pdiv).prev();
            $(radio).prop("checked", !$(radio).prop("checked"));
        },
        refind: function () {
            vu.f40013DtoList = [];
            pageSize = 50;
            this.init();
        },
        find: function () {
            $.ajax({
                url: ctxPath + '/common/F40016/find',
                type: "GET",
                datatype: "json",
                data: {
                    pageSize: pageSize,
                    orgId: vu.orgIdCheck,
                    orgName: vu.orgName
                },
                success: function (data) {
                    if (data.code === 0) {
                        vu.tableList = data.tableList;
                    } else {
                        layer.alert(data.msg);
                    }
                }
            });
        },
        findMore: function () {
            vu.tableList = [];
            pageSize = pageSize + 50;
            this.init();
        }
    }
});

function select() {

    // var orgNm = $("input[name='orgNm']:checked").val();
    var orgId = $("input[name='orgNm']:checked").attr('class');
    $.ajax({
        url: ctxPath + '/sys/chooseOrg',
        type: 'POST',
        data: {
            // "orgNm": orgNm,
            "orgId": orgId
        },
        success: function (data) {
            if (data.code === 0) {
                // window.location.href = ctxPath + "/manager/F21017.html";
                window.location.href = ctxPath + data.url;
            } else {
                layer.alert(data.msg);
            }
        }
    })

}


function reload() {
    location.reload();
}


function toF21017mode2(orgId) {
    orgId = decodeURIComponent(orgId);
    //組織名
    // var orgNm = $(orgId).next().val();
    $.ajax({
        url: ctxPath + '/sys/chooseOrg',
        type: 'POST',
        data: {
            // "orgNm": orgNm,
            "orgId": orgId
        },
        success: function (data) {
            if (data.code === 0) {
                // window.location.href = ctxPath + "/manager/F21017.html";
                window.location.href = ctxPath + data.url;
            } else {
                layer.alert(data.msg);
            }
        }
    })
}

var WinHeight = $(window).height();

$(window).resize(function () {
    $('body').height(WinHeight);

});

function ifraLoad(event) {
    var str = $("#frame").contents().find("#changeOrg");
    $(str).remove();
}
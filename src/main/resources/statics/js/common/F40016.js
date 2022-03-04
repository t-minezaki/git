var pageSize = 50;
var srcHeight = $(window).height() * 0.2;
var srcWidth = $(window).width() * 0.315;
var count = 0;
var vu = new Vue({
    el: "#classroom",
    data: {
        //組織情報データを取得する
        f40013DtoList: [],
        orgNm: '',
        orgId: '',
        tableList: [],
        orgName: "",
        orgIdCheck: "",
        firstCount: 0,
        orgNow: ""
    },
    methods: {
        //取得した組織名が画面に表示する。
        search: function () {
            $.ajax({
                url: ctxPath + '/common/F40016/init',
                type: "GET",
                data: {
                    "pageSize": pageSize,
                    "orgId": this.orgId,
                    "orgNm": this.orgName
                },
                success: function (data) {
                    vu.f40013DtoList = [];
                    if (data.code === 0) {
                        if (count === 0) {
                            vu.firstCount = data.count.length;
                            count++;
                        }
                        vu.orgNow = data.orgNow;
                        vu.f40013DtoList = data.f40013DtoList;
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
                            // getOrg();
                        }
                    }
                }
            });
        },
        //「F11004_スマホ_学習情報登録一覧」へ遷移する
        toF11004: function () {

            //選ばれたradio
            var orgId = $("input[type='radio']:checked").val();
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
                        // window.location.href = ctxPath + "/student/F11004.html";
                        window.location.href = ctxPath + data.url;
                    } else {
                        layer.alert(data.msg);
                    }
                }
            })
            //「F11004_スマホ_学習情報登録一覧」へ遷移する
            // window.location.href= '../student/F11004.html?orgId='+$(orgId).val()+'&orgNm='+$(orgNm).text();
        },
        bindCheck: function ($event) {
            var pdiv = $event.target;
            var radio = $(pdiv).prev();
            $(radio).prop("checked", !$(radio).prop("checked"));
        },
        refind: function () {
            pageSize = 50;
            this.search();
        },
        find: function () {
            $.ajax({
                url: ctxPath + '/common/F40016/find',
                type: "GET",
                datatype: "json",
                data: {
                    pageSize: pageSize,
                    orgId: vu.orgId,
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
            this.search();
        },
        toF11004mode2: function (orgId) {
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
                        // window.location.href = ctxPath + "/student/F11004.html";
                        window.location.href = ctxPath + data.url;
                    } else {
                        layer.alert(data.msg);
                    }
                }
            })
        }
    },
    mounted: function () {
        this.search();
        // this.search();
        // this.findMore();
    },
    updated: function () {

        if (vu.orgNow != null) {
            var divs = $("input[type='radio']");
            $.each(divs, function (index, single) {
                if ($(single).val() === vu.orgNow) {
                    $(single).prop("checked", true);
                }
            })
        }
    }
});

var WinHeight = $(window).height();

$(window).resize(function () {
    $('body').height(WinHeight);

});




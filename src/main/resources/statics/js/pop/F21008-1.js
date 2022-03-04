var param = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        // 組織
        mstOrgEntity: [],
        searchStuList: [],
        stuList: [],
        groupList: [],
        schyList: [],
        mstCodDEntityList: [],
        flg: '',
        groupName: '',
        searchDiv: '',
        grpEntityList: []
    },
    updated: function () {
        $('input[name="select"]').change(function () {
            var searchDiv = $('input[name="select"]:checked').val();
            $("#stuName").val('');
            $("#stuName").attr("disabled", "disabled").css("background", "none");
            if (searchDiv == 'all') {
                $("#typeDiv").attr("disabled", "disabled").css("background", "none");
            } else if (searchDiv == 'someone') {
                $("#typeDiv").attr("disabled", "disabled").css("background", "none");
                $("#stuName").removeAttr("disabled", "disabled").css("background", "white");
            } else {
                $("#typeDiv").removeAttr("disabled", "disabled").css("background", "white");
            }

            vm.searchDiv = searchDiv;
        });
    },
    mounted: function () {
        $("#toright").click(function () {
            $("#select2 option:selected").attr("selected", false);
            $("#select1 option:selected").appendTo("#select2");
        });
        $("#toleft").click(function () {
            $("#select1 option:selected").attr("selected", false);
            $("#select2 option:selected").appendTo("#select1");
        });
        this.getInfo();
    },
    methods: {

        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f00054/init',
                data: {
                    flg: 0,
                    groupId: ''
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        $("#typeDiv").attr("disabled", "disabled");
                        vm.flg = param.flg;
                        if (data.groupList) {
                            vm.groupList = data.groupList;
                        }
                        if (data.grpEntityList) {
                            vm.grpEntityList = data.grpEntityList;
                        }
                        vm.groupName = data.groupName;
                        if (vm.flg == 2) {
                            $("#group").text("コピー元グループ名_タイトル");
                            $(".box").css("margin-top", "12vh");
                        }
                        if (data.mstCodDEntityList) {
                            vm.mstCodDEntityList = data.mstCodDEntityList;
                        }
                        if (data.mstOrgEntity) {
                            vm.mstOrgEntity = data.mstOrgEntity;
                        }
                        if (data.groupStuInfoList) {
                            vm.stuList = data.groupStuInfoList;
                        }
                    }
                }
            });

        },
        search: function () {
            vm.searchStuList = [];
            $("#select2 option").remove();
            var params = {};
            var searchDiv = $('input[name="select"]:checked').val();
            if (searchDiv == 'schy') {
                var schyDiv = $("#typeDiv").val();
                params.schyDiv = schyDiv;
            }
            if (searchDiv == 'group') {
                var groupDiv = $("#typeDiv").val();
                params.groupDiv = groupDiv;
            }
            if (searchDiv == 'someone') {
                var stuName = $("#stuName").val();
                params.stuName = stuName;
            }
            params.searchDiv = searchDiv;

            $.ajax({
                url: ctxPath + '/manager/f00054/search',
                data: {
                    params: JSON.stringify(params)
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.searchStuList) {
                            vm.searchStuList = data.searchStuList;
                        }
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        submit: function () {
            var formData = new FormData();
            if (param.flg == 0 ||param.flg == 2){
                if(param.flg == 0){
                    if ($("#grpName").val() == null || $("#grpName").val() == '') {
                        showMsg($.format($.msg.MSGCOMN0028, "グループ名"));
                        return;
                    }
                }
                if (param.flg == 2) {
                    if ($("#newGroup").val() == null || $("#newGroup").val() == '') {
                        showMsg($.format($.msg.MSGCOMN0028, "グループ名"));
                        return;
                    }
                }
                if ($('#select2 option').length == 0) {
                    showMsg($.format($.msg.MSGCOMN0087, "生徒", "対象者とするエリアへ移動"));
                    return;
                }
            }

            var stuIdListRight = []
            $('#select2 option').each(function () {

                stuIdListRight.push($(this).val())

            });
            formData.append("stuIdListRight", stuIdListRight);
            formData.append("tgtYmd", '2019-11-15');
            var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    $.ajax({
                        url: ctxPath + '/manager/F21008/stuSelect',
                        type: 'POST',
                        data: formData,
                        async: true,
                        datatype: 'json',
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            if (data.code != 0) {
                                showMsg(data.msg);
                            } else {
                                // var idx = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                                //     skin: 'layui-layer-molv',
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     anim: -1,
                                //     btn: ['確認'],
                                //     yes: function () {
                                        parent.vm.addStu = data.addStu;
                                        // layer.close(idx);
                                        var index = parent.layer.getFrameIndex(window.name);
                                        parent.layer.close(index);
                                //     }
                                // })

                            }
                        }
                    });
                }
            })
        }
    }
});

function cancle() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
};
$(function () {
    /**
     * メッセージをクリアする
     */
    $("select").change(function () {
        $("#stuName").css("background-color", "transparent");
    });
})
$(".left li").click(function () {
    $(".left li").removeClass("active");
    if ($(this).hasClass("active")) {
        $(this).removeClass("active");
    } else {
        $(this).addClass("active");
    }
});

$(".right li").click(function () {
    $(".right li").removeClass("active");
    if ($(this).hasClass("active")) {
        $(this).removeClass("active");
    } else {
        $(this).addClass("active");
    }
});

$("#toright").click(function () {
    // var sort=$(".left").find(".active").attr("sort");
    // if($(".right").find("li[sort*='"+sort+"']").length!=0){
    // 	return;
    // }
    $(".right ul").append($(".left").find(".active"));

})

$("#toleft").click(function () {
    $(".left ul").append($(".right").find(".active"));
})

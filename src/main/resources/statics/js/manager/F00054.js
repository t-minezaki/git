var param = getParam();
var flg = 0;
Array.prototype.remove = function (val) {
    for (var i = 0; i < this.length; i++) {

        if (this[i].stuId == val.stuId) {

            this.splice(i, 1);
        }
    }
};
var vm = new Vue({
    el: '.content',
    data: {
        // 組織
        mstOrgEntity: [],
        searchStuList: [],
        judStuList: [],
        stuList: [],
        groupList: [],
        schyList: [],
        mstCodDEntityList: [],
        flg: '',
        groupName: '',
        searchDiv:'',
        grpEntityList:[]
    },
    updated: function () {
        $('input[name="select"]').change(function () {
            var searchDiv = $('input[name="select"]:checked').val();
            $("#stuName").val('');
            $("#stuName").attr("disabled","disabled").css("background","none");
            if (searchDiv == 'all'){
                $("#typeDiv").attr("disabled","disabled").css("background","none");
            }else if (searchDiv == 'someone'){
                $("#typeDiv").attr("disabled","disabled").css("background","none");
                $("#stuName").removeAttr("disabled","disabled").css("background","white");
            }
            else {
                $("#typeDiv").removeAttr("disabled","disabled").css("background","white");
            }

            vm.searchDiv = searchDiv;
        });
    },
    mounted: function () {
        // ＞＞ボタン押下時
        $("#allToRight").click(function () {
            var options = $("#select1 option");
            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                var stuAndGuard = {
                    stuId: $(option).val(),
                    stuName: $(option).text()
                };
                vm.judStuList.push(stuAndGuard);
                vm.searchStuList.remove(stuAndGuard);
            }
        });

        // ＞ボタン押下時
        $("#toright").click(function () {
            var options = $("#select1 option:selected");
            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                var stuAndGuard = {
                    stuId: $(option).val(),
                    stuName: $(option).text()
                };
                vm.judStuList.push(stuAndGuard);
                vm.searchStuList.remove(stuAndGuard);
            }
        });

        // ＜＜ボタン押下時
        $("#allToLeft").click(function () {
            var options = $("#select2 option");
            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                var stuAndGuard = {
                    stuId: $(option).val(),
                    stuName: $(option).text()
                };
                vm.judStuList.remove(stuAndGuard);
                vm.searchStuList.push(stuAndGuard);
            }
        });

        // ＜ボタン押下時
        $("#toleft").click(function () {

            var options = $("#select2 option:selected");
            for (var i = 0; i < options.length; i++) {
                var option = options[i];
                var stuAndGuard = {
                    stuId: $(option).val(),
                    stuName: $(option).text()
                };
                vm.judStuList.remove(stuAndGuard);
                vm.searchStuList.push(stuAndGuard);
            }
        });
        this.getInfo();
    },
    methods: {

        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f00054/init',
                data: {
                    flg: param.flg,
                    groupId: param.groupId
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        $("#typeDiv").attr("disabled","disabled");
                        vm.flg = param.flg;
                        if (data.groupList) {
                            vm.groupList = data.groupList;
                        }
                        if (data.grpEntityList){
                            vm.grpEntityList = data.grpEntityList;
                        }
                        vm.groupName = data.groupName;
                        if (vm.flg == 2) {
                            $("#group").text("コピー元グループ名");
                            $(".box").css("margin-top","12vh");
                        }
                        if (data.mstCodDEntityList) {
                            vm.mstCodDEntityList = data.mstCodDEntityList;
                        }
                        if (data.mstOrgEntity) {
                            data.mstOrgEntity.orgNm = getOrgName(data.mstOrgEntity.orgNm);
                            vm.mstOrgEntity = data.mstOrgEntity;
                        }
                        if (data.groupStuInfoList) {
                            vm.stuList = data.groupStuInfoList;
                            for (var i = 0; i < vm.stuList.length; i++) {
                                var stuAndGuard = {
                                    stuId: vm.stuList[i].stuId,
                                    stuName: vm.stuList[i].stuName
                                };
                                vm.judStuList.push(stuAndGuard);
                            }
                        }
                    }
                }
            });

        },
        search: function () {
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
                        vm.searchStuList = [];
                        var list = [];
                        for (var i = 0; i < data.searchStuList.length; i++) {
                            var item = data.searchStuList[i];
                            var flag = false;
                            for (var j = 0; j < vm.judStuList.length; j++) {
                                var item2 = vm.judStuList[j];
                                if (item.stuId == item2.stuId) {
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                list.push(item);
                            }
                        }
                        if (list.length <= 0) {

                            var msg = $.format($.msg.MSGCOMN0017, "生徒");
                            showMsg(msg);
                        }
                        vm.searchStuList = list;
                    } else {
                        vm.searchStuList = [];
                        showMsg(data.msg);
                    }
                }
            });
        },
        submit: function () {

            if (param.flg == 0 || param.flg == 2) {
                if (param.flg == 0) {
                    if ($("#grpName").val() == null || $("#grpName").val() == '') {
                        showMsg($.format($.msg.MSGCOMN0028, "グループ名"));
                        return;
                    }
                }
                if (param.flg == 2) {
                    if ($("#newGroup").val() == null || $("#newGroup").val() == '') {
                        showMsg($.format($.msg.MSGCOMD0001, "グループ名"));
                        return;
                    }
                }
                if ($('#select2 option').length == 0) {
                    showMsg('対象者が選択されていません。');
                    return;
                }
            }

            var stuIdListLeft = [];
            $('#select1 option').each(function () {

                stuIdListLeft.push($(this).val());

            });

            var stuIdListRight = [];
            $('#select2 option').each(function () {

                stuIdListRight.push($(this).val());

            });
            var formData = new FormData();

            if (param.flg == 0) {
                formData.append("groupId", $("#grpName").val());
            }
            if (param.flg == 1 || param.flg == 2) {
                formData.append("groupId", param.groupId);
            }

            formData.append("stuIdListRight", stuIdListRight);
            formData.append("stuIdListLeft", stuIdListLeft);
            formData.append("flg", param.flg);
            formData.append("newgroupName", encodeURIComponent($("#newGroup").val()));
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
                        url: ctxPath + '/manager/f00054/submit',
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
                                        window.location.href = "F00051.html";
                                //         layer.close(idx);
                                //     }
                                // });

                            }
                        }
                    });
                }
            });
        }
    }
});

$(function () {
    /**
     * メッセージをクリアする
     */
    // $("select").change(function () {
    //     $("#stuName").css("background-color", "transparent");
    // });
});
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

});

$("#toleft").click(function () {
    $(".left ul").append($(".right").find(".active"));
});

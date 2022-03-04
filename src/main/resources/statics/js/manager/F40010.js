var vm = new Vue({
    el: '#app',
    data: {
        orgId: "",
        orgNm: "",
        managerTypeList: [],
        CheckedOrgIdList: [],
        rightList: [],
        managerOrMentor: 0,
        pageDiv: 0
    },
    computed: {},
    methods: {},
    mounted: function () {
    },
    updated: function () {
    }
});

var params = getParam();
vm.pageDiv = params.pageDiv;

$(function () {

    $("#manager_select_all").bind("click", function () {
        $(".manager").prop("checked", $(this).prop("checked"));
    });

    $("#mentor_select_all").bind("click", function () {
        $(".mentor").prop("checked", $(this).prop("checked"));
    });

    $.get(ctxPath + "/manager/F40010/init"
        , function (data) {
            vm.orgId = data.orgId;
            vm.orgNm = data.orgNm;
            if (data.code == 0) {

                vm.managerTypeList = data.managerTypeList;
            } else {
                showMsg(data.msg);
            }
        });

    $("#btn_org_select").click(function () {

        var srcWidth = $(window).width() * 0.4 + "px";
        var srcHeight = $(window).height() * 0.45 + "px";
        layer.open({
            id: 'F40010-1',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: ' ',
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            fixed: true,
            resize: false,
            content: ["../pop/F40010-1.html", 'no'],
            success: function (layero, index) {
            }
        });
    });

    $("#btn_user_select").click(function () {

        var srcWidth = $(window).width() * 0.7 + "px";
        var srcHeight = $(window).height() * 0.65 + "px";
        layer.open({
            id: 'F40010-2',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin2",
            title: "指定者検索",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F40010-2.html", 'no'],
            success: function (layero, index) {
            }
        });
    });

    $("#btn_ok").bind("click", function () {

        var funcIdList = [];
        $(".function_check:checked").each(function () {
            funcIdList.push($(this).val());
        });
        // 画面にいずれか機能が選択されていない場合
        if (funcIdList <= 0) {
            showMsg($.msg.MSGCOMN0126);
            return;
        }
        var mentorFunIdrList = [];
        $(".mentor:checked").each(function () {
            mentorFunIdrList.push($(this).val());
        });
        var wholeItems = [];
        $(".function_check:checked").each(function () {
            wholeItems.push($(this).val());
        });
        var mentorItems = [];
        $(".mentor:checked").each(function () {
            mentorItems.push($(this).val());
        });
        var managerFunIdrList = [];
        managerFunIdrList = wholeItems.reduce(function(carry, item) {
            if (mentorItems.indexOf(item) == -1) {
                carry.push(item);
            }
            return carry;
        }, []);
        //ロール指定が「先生」の場合、且つ　先生機能既定値にはなにも機能が選択されていない場合、
        var roleType = $("input:radio:checked").val();
        if (roleType == 1) {
            if (mentorFunIdrList.length <= 0) {
                showMsg($.format($.msg.MSGCOMN0138, "先生機能"));
                return;
            }
            if (managerFunIdrList.length > 0) {
                var index = layer.confirm($.msg.MSGCOMN0139,  {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                });
                return;
            }
        }
        if (vm.pageDiv == 2){
            if (vm.rightList.length <= 0){
                var index = layer.confirm($.format($.msg.MSGCOMN0137, "個人"),  {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                });
                return;
            }
        }
        // 確認ダイアログをポップアップ表示する
        var index = layer.confirm($.format($.msg.MSGCOMN0021, "権限付与"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                layer.close(index);
            },
            btn2: function () {

                $.post(ctxPath + "/manager/F40010/save",
                    {
                        funcIdListStr: JSON.stringify(funcIdList),
                        orgIdListStr: JSON.stringify(vm.CheckedOrgIdList),
                        userIdListStr: JSON.stringify(vm.rightList),
                        managerOrMentor: vm.managerOrMentor,
                        pageDiv: vm.pageDiv
                    }, function (data) {

                        if (data.code == 0) {

                            if (vm.pageDiv == 1) {
                                // var idx = layer.confirm($.format($.msg.MSGCOMN0015, "ロール別機能権限"), {
                                //     skin: 'layui-layer-molv',
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     anim: -1,
                                //     btn: ['確認'],
                                //     yes: function () {
                                        location.reload();
                                //         layer.close(idx);
                                //     }
                                // })
                            } else if (vm.pageDiv == 2) {
                                // var idx = layer.confirm($.format($.msg.MSGCOMN0015, "個人別機能権限設定"), {
                                //     skin: 'layui-layer-molv',
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     anim: -1,
                                //     btn: ['確認'],
                                //     yes: function () {
                                        location.reload();
                                //         layer.close(idx);
                                //     }
                                // })
                            }
                        } else {
                            // var idx = layer.confirm(data.msg, {
                            //     skin: 'layui-layer-molv',
                            //     title: '確認',
                            //     closeBtn: 0,
                            //     anim: -1,
                            //     btn: ['確認'],
                            //     yes: function () {
                                    location.reload();
                            //         layer.close(idx);
                            //     }
                            // });
                        }
                    });
            }
        });
    });
});
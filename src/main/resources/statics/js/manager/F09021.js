var equipment = $.cookie("equipment");
if (equipment == 'tablet') {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09021-1.css"}).appendTo("head");
} else {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F09021.css"}).appendTo("head");
}
var params = getParam();
var isFirst = true;
var changeCondition = false;
var vm = new Vue({
    el: '#content',
    data: {
        orgList: [],
        classifyList: [],
        schyList: [],
        grpList: [],
        conditionList: [],
        stuList: []
    },
    mounted: function () {
        this.setUp();
        this.init();
    },
    updated: function () {
        if (isFirst) {
            $('#classRoomChoose').fSelect({
                placeholder: '配信教室を選択',
                numDisplayed: 999,
                overflowText: '',
                showSearch: false
            });
            $('#categoryChoose').fSelect({
                placeholder: 'カテゴリを選択',
                numDisplayed: 999,
                overflowText: '',
                showSearch: false,
                onSelected: function () {
                    var category = $('#categoryChoose').val();
                    if (category == '0') {
                        vm.conditionList = [];
                        changeCondition = true;
                    } else if (category == '1') {
                        vm.conditionList = vm.schyList;
                        changeCondition = true;
                    } else if (category == '2') {
                        vm.conditionList = vm.grpList;
                        changeCondition = true;
                    }
                }
            });
            $('#conditionChoose').fSelect({
                placeholder: '条件を選択',
                numDisplayed: 999,
                overflowText: '',
                showSearch: false
            });
            isFirst = false;
        }
        if (changeCondition) {
            $('#conditionChoose').val('');
            $('#conditionChoose').fSelect("destroy");
            $('#conditionChoose').fSelect("create");
            changeCondition = false;
        }
    },
    methods: {
        setUp: function () {
            $("#checkAll").click(function () {
                var flag = document.getElementById("checkAll").checked;
                if (flag) {
                    $("input[name='student']").each(function (i) {
                        $(this).prop("checked", true);
                    });
                } else {
                    $("input[name='student']").each(function (i) {
                        $(this).prop("checked", false);
                    });
                }
                $(".stu-div").each(function () {
                    $(this).css('background', 'white');
                });
            });
        },
        init: function () {
            var orgIds = params.orgIds ? params.orgIds : '';
            $.ajax({
                type: "GET",
                url: ctxPath + '/manager/F09021/init',
                dataType: 'json',
                data: {
                    orgIds: orgIds
                },
                success: function (r) {
                    if (r.code === 0) {
                        if (r.orgList) {
                            vm.orgList = r.orgList;
                        }
                        if (r.classifyList) {
                            vm.classifyList = r.classifyList;
                        }
                        if (r.schyList) {
                            for (var i = 0, len = r.schyList.length; i < len; i++) {
                                vm.schyList.push({"code": r.schyList[i].codCd, "value": r.schyList[i].codValue});
                            }
                        }
                        if (r.grpList) {
                            for (var i = 0, len = r.grpList.length; i < len; i++) {
                                vm.grpList.push({"code": r.grpList[i].grpId, "value": r.grpList[i].grpNm});
                            }
                        }
                    } else {
                        layer.confirm(r.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認']
                        });
                    }
                }
            });
        },
        search: function () {
            var orgIds = $('#classRoomChoose').val();
            var category = $('#categoryChoose').val();
            var grpId = '';
            var schyDiv = '';
            if (category == '1') {
                schyDiv = $('#conditionChoose').val();
            } else if (category == '2') {
                grpId = $('#conditionChoose').val();
            }
            $.ajax({
                type: "GET",
                url: ctxPath + '/manager/F09021/getStuList',
                dataType: 'json',
                data: {
                    orgIds: JSON.stringify(orgIds),
                    grpId: grpId,
                    schyDiv: schyDiv
                },
                success: function (r) {
                    if (r.code === 0) {
                        if (r.stuList) {
                            vm.stuList = r.stuList;
                            $('#checkAll').prop("checked", false);
                        }
                    } else {
                        layer.confirm(r.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認']
                        });
                    }
                }
            });
        },
        check: function (obj) {
            var count = 0;
            $("input[name='student']").each(function (i) {
                if ($(this).prop("checked")) {
                    count++;
                }
            });
            if (!obj.checked) {
                $(obj).parent().css('background', 'white');
            } else {
                $(obj).parent().css('background', '#FFE2EA');
            }
            if (count === 0) {
                $('#checkAll').prop("checked", false);
            } else if (count === this.stuList.length) {
                $('#checkAll').prop("checked", true);
                $(".stu-div").css('background', 'white');
            } else {
                if ($('#checkAll').prop("checked")) {
                    $("input[name='student']").each(function (i) {
                        if ($(this).prop("checked")) {
                            $(this).parent().css('background', '#FFE2EA');
                        }
                    });
                    $('#checkAll').prop("checked", false);
                }
            }
        },
        submit: function () {
            var param = {};
            param.orgIds = $('#classRoomChoose').val();
            param.category = $('#categoryChoose').val();
            param.condition = $('#conditionChoose').val();
            var stuList = [];
            $("input[name='student']").each(function (i) {
                if ($(this).prop("checked")) {
                    stuList.push(vm.stuList[parseInt($(this).val())]);
                }
            });
            // param.stuList = stuList;
            // //todo
            parent.stuList = stuList;
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        /* 2021/03/05 cuikailin add start */
        back: function () {
            parent.layer.closeAll();
        }
        /* 2021/03/05 cuikailin add end */
    }
});

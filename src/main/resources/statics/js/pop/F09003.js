// サブウィンドウインデックスを取得する
var index = parent.layer.getFrameIndex(window.name);
var vm = new Vue({
    el: '.content',
    data: {
        searchDiv: 'all',
        // 2020/11/9 zhangminghao modify start
        schyList: [],
        absList: [],
        // 2020/11/9 zhangminghao modify end
        mstGrpEntityList: [],
        searchStuList:[]
    },
    updated: function () {
        if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
            if (window.location.href.indexOf("?mobile") < 0) {
                try {
                    if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                        $('.item_div input[type="checkbox"]').append("<style>.stu_chk:checked::after{top: -2.1vh !important;}</style>")
                    } else if (/iPad/i.test(navigator.userAgent)) {
                        $('.item_div input[type="checkbox"]').css("height", "6vh").css("width", "3vw")
                    } else {
                        // alert("other")
                    }
                } catch (e) {
                }
            } else {
                // alert("456")
            }
        } else {
            if (/Macintosh/i.test(navigator.userAgent)){
                $('.item_div input[type="checkbox"]').css("height", "6vh").css("width", "3vw")
            }
        }
        // laydate日時を設ける
        laydate.render({
            elem: '#timeOne',
            type: 'datetime',
            format: 'yyyy/MM/dd',
            done: function (value) {
                $("#readTimeStart").val(value)
            }
        });

        // laydate日時を設ける
        laydate.render({
            elem: '#timeTwo',
            type: 'datetime',
            format: 'yyyy/MM/dd ',
            done: function (value) {
                $("#readTimeEnd").val(value)
            }
        });
    },
    mounted: function () {
        this.init();
    },
    methods: {
        //初期表示
        init: function () {
            $.ajax({
                url: ctxPath + '/pop/F09003/init',
                data: {},
                type: 'GET',
                success: function (data) {
                    // 2020/11/9 zhangminghao modify start
                    vm.schyList = data.schyList;
                    vm.absList = data.absList;
                    // 2020/11/9 zhangminghao modify end
                    vm.mstGrpEntityList = data.mstGrpEntityList;
                }
            })
        },
        //検索
        search: function () {
            var params={};
            params.searchDiv = vm.searchDiv;
            params.value = '';
            // 2020/11/9 zhangminghao modify start
            if (vm.searchDiv == 'schy' || vm.searchDiv == 'group' || vm.searchDiv == 'absSts') {
            // 2020/11/9 zhangminghao modify end
                params.value = $("#typeDiv").val();
            }else if (vm.searchDiv == 'someone') {
                params.value = $("#stuName").val();
            }else if (vm.searchDiv == 'loginDate') {
                params.startDate = $("#readTimeStart").val();
                params.endDate = '';
                if ($("#readTimeEnd").val() !== '' && $("#readTimeEnd").val() !== null) {
                    params.endDate = new Date($("#readTimeEnd").val());
                    params.endDate.setDate(params.endDate.getDate() + 1);
                    params.endDate = params.endDate.Format("yyyy/MM/dd");
                }
                if ($("#readTimeStart").val() =='' && $("#readTimeEnd").val() ==''){
                    var index = layer.confirm($.format($.msg.MSGCOMN0136, "登録日"), {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(index);
                        }
                    });
                    return false;
                }
                if ($("#readTimeStart").val() !='' && $("#readTimeEnd").val() !='') {
                    if ($("#readTimeStart").val() >  $("#readTimeEnd").val() ) {
                        var index = layer.confirm($.format($.msg.MSGCOMN0124, "登録日(To)","登録日(From)"), {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                layer.close(index);
                            }
                        });
                        return false;
                    }
                }
            }
            // 2020/11/16 zhangminghao modify start
            // 必須チェック追加
            if((vm.searchDiv === 'schy' || vm.searchDiv === 'group' || vm.searchDiv === 'absSts'
                || vm.searchDiv === 'someone') && !params.value){
                // showMsg($.format($.msg.MSGCOMN0143, $('#' + vm.searchDiv).html()));
                let msg = $.format($.msg.MSGCOMN0143, $('#' + vm.searchDiv).html());
                let index = layer.confirm(msg, {
                    title: '確認',
                    closeBtn: 0,
                    shadeClose: false,
                    btn: ['確認'],
                    btn1: function () {
                        layer.close(index);
                    },
                });
                return;
            }
            // 2020/11/16 zhangminghao modify end
            // 2020/01/12 liyuhuan add start
            vm.searchStuList = [];
            // 2021/01/12 liyuhuan add end
            $.ajax({
                url: ctxPath + '/pop/F09003/search',
                data: {
                    params:JSON.stringify(params)
                },
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.stuList) {
                            vm.searchStuList = data.stuList;
                        }
                    }else {
                        showMsg(data.msg);
                    }
                }
            })
        },
        //選択
        submit:function () {
            var stuList = [];
            var stuIdList = $("#form_right").children().find("input[type='checkbox']");
            for (var i = 0; i < stuIdList.length; i++) {
                stuList.push(stuIdList[i].value);
            }
            parent.stuList = stuList;
            parent.closeFlg = true;
            parent.layer.close(index);
        }
    }
});
$('input[name="select"]').change(function () {
    var searchDiv = $('input[name="select"]:checked').val();
    vm.searchDiv = searchDiv;
});
$(function () {
    // rightボタン押下後
    $("#right").click(function () {

        var inputs = $("#form_left").children().find("input[type='checkbox']:checked");
        $("#form_left").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_right");
        inputs.prop("checked", false);
    });

    // right_allボタン押下後
    $("#right_all").click(function () {

        var inputs = $("#form_left").children().find("input[type='checkbox']");
        $("#form_left").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_right");
        inputs.prop("checked", false);
    });

    // leftボタン押下後
    $("#left").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']:checked");
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_left");
        inputs.prop("checked", false);
    });

    // left_allボタン押下後
    $("#left_all").click(function () {

        var inputs = $("#form_right").children().find("input[type='checkbox']");
        $("#form_right").children().find("input[type='checkbox']").parent().css("display", "block");
        inputs.parent().appendTo("#form_left");
        inputs.prop("checked", false);
    });
})


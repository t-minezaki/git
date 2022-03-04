var params = getParam();
var vm = new Vue({
    el: '#main',
    data: {
        f21031Dto: ''
    },
    mounted: function () {
        this.init();
    },
    updated:function(){

    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/manager/F21031/init',
                type: 'GET',
                data: {
                    orgId: params.orgId,
                    usrId: params.userId
                },
                success: function (data) {
                    if (data.f21031Dto) {
                        vm.f21031Dto = data.f21031Dto;
                    }
                }
            })
        },
        submit:function () {
            if ((Number($(".dayUnpassLine").val()*60) + Number($("#dayUnpassLine").val())) < (Number($(".dayMidLine").val()*60) + Number($("#dayMidLine").val())) && (Number($(".dayMidLine").val()*60) + Number($("#dayMidLine").val())) < (Number($(".dayPassLine").val()*60) + Number($("#dayPassLine").val()))){
                if ((Number($(".weekUnpassLine").val()*60) + Number($("#weekUnpassLine").val())) < (Number($(".weekMidLine").val()*60) + Number($("#weekMidLine").val())) && (Number($(".weekMidLine").val()*60) + Number($("#weekMidLine").val())) < (Number($(".weekPassLine").val()*60) + Number($("#weekPassLine").val()))) {
                    if ((Number($(".monthUnpassLine").val()*60) + Number($("#monthUnpassLine").val())) < (Number($(".monthMidLine").val()*60) + Number($("#monthMidLine").val())) && (Number($(".monthMidLine").val()*60) + Number($("#monthMidLine").val())) < (Number($(".monthPassLine").val()*60) + Number($("#monthPassLine").val()))) {
                        // しきい値登録
                        $.ajax({
                            url: ctxPath + '/manager/F21031/submit',
                            type: 'GET',
                            data: {
                                orgId: params.orgId,
                                usrId: params.userId,
                                perfStandDay1:Number($(".dayUnpassLine").val()*60) + Number($("#dayUnpassLine").val()),
                                perfStandDay2:Number($(".dayMidLine").val()*60) + Number($("#dayMidLine").val()),
                                perfStandDay3:Number($(".dayPassLine").val()*60) + Number($("#dayPassLine").val()),
                                perfStandWeek1:Number($(".weekUnpassLine").val()*60) + Number($("#weekUnpassLine").val()),
                                perfStandWeek2:Number($(".weekMidLine").val()*60) + Number($("#weekMidLine").val()),
                                perfStandWeek3:Number($(".weekPassLine").val()*60) + Number($("#weekPassLine").val()),
                                perfStandMonth1:Number($(".monthUnpassLine").val()*60) + Number($("#monthUnpassLine").val()),
                                perfStandMonth2:Number($(".monthMidLine").val()*60) + Number($("#monthMidLine").val()),
                                perfStandMonth3:Number($(".monthPassLine").val()*60) + Number($("#monthPassLine").val())
                            },
                            success: function (data) {
                                if (data.code == 0) {
                                    var index = parent.layer.getFrameIndex(window.name);
                                    parent.closeFlg = true;
                                    parent.layer.close(index);
                                }
                            }
                        })
                    }else {
                        var index = layer.confirm($.msg.MSGCOMN0148, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                layer.close(index);
                            }
                        })
                    }

                }else {
                    var index = layer.confirm($.msg.MSGCOMN0148, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(index);
                        }
                    })
                }
            }else {
                var index = layer.confirm($.msg.MSGCOMN0148, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    btn1: function () {
                        layer.close(index);
                    }
                })
            }
        }
    }
});
var cls = '';
function getDisabled(obj) {
    getIndex($(".day"));
    getIndex($(".week"));
    getIndex($(".month"));
    var txt = '';
    switch (cls) {
        case 'dayPassLine':
            txt = '日別．合格ライン';
            break;
        case 'dayMidLine':
            txt = '日別．中間ライン';
            break;
        case 'dayUnpassLine':
            txt = '日別．不合格ライン';
            break;
        case 'weekPassLine':
            txt = '週別．合格ライン';
            break;
        case 'weekMidLine':
            txt = '週別．中間ライン';
            break;
        case 'weekUnpassLine':
            txt = '週別．不合格ライン';
            break;
        case 'monthPassLine':
            txt = '月別．合格ライン';
            break;
        case 'monthMidLine':
            txt = '月別．中間ライン';
            break;
        case 'monthUnpassLine':
            txt = '月別．不合格ライン';
            break;
    }
    if (cls.substring(0,3) == 'day'){
        if ($(".dayPassLine").val()*60 > 1440 || $(".dayMidLine").val()*60 > 1440 || $(".dayUnpassLine").val()*60 > 1440){
            $("#" + cls).attr("disabled","disabled");
            $("#submit").attr("disabled","disabled").css("background", "grey");
            showEr(txt);
        }else {
            $(".topMessage").removeClass("hasHeight").css("display","none");
            $("#" + cls).removeAttr("disabled","disabled");
            $("#submit").removeAttr("disabled","disabled").css("background", "#00a65a");
        }
    }else if (cls.substring(0,4) == 'week'){
        if ($(".weekPassLine").val()*60 > 10080 || $(".weekMidLine").val()*60 > 10080 || $(".weekUnpassLine").val()*60 > 10080 ){
            $("#" + cls).attr("disabled","disabled");
            $("#submit").attr("disabled","disabled").css("background", "grey");
            showEr(txt);
        }
        else {
            $(".topMessage").removeClass("hasHeight").css("display","none");
            $("#" + cls).removeAttr("disabled","disabled");
            $("#submit").removeAttr("disabled","disabled").css("background", "#00a65a");
        }
    }else if (cls.substring(0,5) == 'month'){
        if ($(".monthPassLine").val()*60 > 43200 || $(".monthMidLine").val()*60 > 43200 || $(".monthUnpassLine").val()*60 > 43200  ){
            $("#" + cls).attr("disabled","disabled");
            $("#submit").attr("disabled","disabled").css("background", "grey");
            showEr(txt);
        }
        else {
            $(".topMessage").removeClass("hasHeight").css("display","none");
            $("#" + cls).removeAttr("disabled","disabled");
            $("#submit").removeAttr("disabled","disabled").css("background", "#00a65a");
        }
    }
};
function showEr(txt) {
    showMsg($.format($.msg.MSGCOMN0147, txt));
}
function getIndex(objs) {
    for (var i = 0; i < objs.length; i++) {
        if ($(objs[i]).hasClass("day")) {
            if ($(objs[i]).val()*60 > 1440) {
                cls = objs[i].className.substring(0,objs[i].className.length-4);
                break;
            }
        } else if ($(objs[i]).hasClass("week")) {
            if ($(objs[i]).val()*60 > 10080) {
                cls = objs[i].className.substring(0,objs[i].className.length-5);
                break;
            }
        } else if ($(objs[i]).hasClass("month")){
            if ($(objs[i]).val()*60 > 43200) {
                cls = objs[i].className.substring(0,objs[i].className.length-6);
                break;
            }
        }
    }
}
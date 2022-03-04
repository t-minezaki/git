var vm = new Vue({
    el: '#app',
    data: {
        eventEntity: {
            eventTitle: "XXXXX",
            sgdPlanDateStr: "XX/XX(X)",
            sgdStartDatimeStr: "XX:XX",
            sgdEndDatimeStr: "XX:XX",
            refType: "0",
            mentorNm: "XXXX",
            stuNm: "XXXX",
            orgNm:"XXXX"
        },
        orgNm: "XXX",
        mailTitle: "",
        mailCnt: "",
        stuList:[]
    },
    computed: {},
    methods: {
    },
    mounted: function () {
    },
    updated: function () {
        $("#btn_change").on("click",function () {
            var object = {};
            object.pageDiv = '3';
            object.geasId = $("#geasId").val();
            object.userFlag = userFlag;
            location.href = "../pop/F08015.html?" + $.param(object);
        });
    }
});

var params = getParam();
var scheduleId = params.id;
var userFlag = params.userFlag;

// サブウィンドウインデックスを取得する
var index = parent.layer.getFrameIndex(window.name);


function btnOrder(){
    var index_load = parent.layer.load(2);
    var geasId = $("#geasId").val();

    $.get(ctxPath + "/manager/F08014/updGuardEventApplySts",
        {
            guardEventApplyStsId: geasId,
            userFlag: userFlag
        }
        ,function (data) {
            if (data.code == 0) {

                // メールパラメータ
                var params = {
                    orgNm: vm.orgNm,
                    ctgyNm: vm.eventEntity.ctgyNm,
                    sgdPlanDateStr: vm.eventEntity.timeStr,
                    orgId: vm.eventEntity.orgId
                };
                if (userFlag === false || userFlag === 'false') {
                    parent.location.reload();
                    parent.layer.close(index);
                    return;
                }
                $.post(ctxPath + "/manager/F08014/postMail",
                    {
                        params: JSON.stringify(params),
                        geasId: $("#geasId").val()
                    }
                    , function (data) {
                        parent.layer.close(index_load);
                        if (data.code == 0) {
                            // var idx = parent.layer.confirm($.format($.msg.MSGCOMN0118, "予約キャンセル時"), {
                            //  skin: 'layui-layer-molv',
                            //  title: '確認',
                            //  closeBtn: 0,
                            //  anim: -1,
                            //  btn: ['確認'],
                            //  yes: function () {
                                 parent.location.reload();
                                 // parent.layer.close(idx);
                                 parent.layer.close(index);
                         //     }
                         // });
                     } else{
                         var idx = parent.layer.confirm(data.msg, {
                             skin: 'layui-layer-molv',
                             title: '確認',
                             closeBtn: 0,
                             anim: -1,
                             btn: ['確認'],
                             yes: function () {
                                 parent.location.reload();
                                 parent.layer.close(idx);
                                 parent.layer.close(index);
                             }
                         });
                     }
                 });

            }else{
                var idx = parent.layer.confirm(data.msg, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        parent.location.reload();
                        parent.layer.close(idx);
                        parent.layer.close(index);
                    }
                });
            }
        });
}

$(function () {

    $.get(ctxPath + "/manager/F08014/init",
        {
            id: scheduleId,
            userFlag: userFlag
        }
        ,function (data) {
            if (data.code == 0){

                vm.eventEntity = data.eventEntity;
                vm.orgNm = data.orgNm;
                vm.stuList = data.stuList;
            } else{
                parent.layer.alert(data.msg);
            }
        });

    $("#btn_close").click(function () {

        parent.layer.close(index);
    });

    $("#btn_order").click(function () {


    });
});
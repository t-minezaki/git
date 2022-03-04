var v = getParam();
var vm = new Vue({
    el: '#info',
    data: {
        dto: '',
        weekday: '',
        planLearnStartTime: '',
        planLearnEndTime: '',
        learnLev: '',
        isTerm: '',
        dateFlg: '',
        blockTypeDiv:v.blockTypeDiv,
        time:''
    },
    mounted: function () {
        this.getInfo();

    },
    updated: function () {
        //積み残しから削除する
        if (vm.dto.blockTypeDiv == 'S1'&&(vm.dto.remainDispFlg == '3'||((vm.dto.remainDispFlg == null || vm.dto.remainDispFlg == '1')&&vm.dateFlg))) {
            $("#delBtn p").css("color", "#00b050");
            $("#delBtn").attr("disabled", false);
            $("#upBtn").attr("disabled", "disabled");
        }

        //積み残しにする
        if (vm.dto.blockTypeDiv=='S1'&&((vm.dto.remainDispFlg == '0'||vm.dto.remainDispFlg == '4')||((vm.dto.remainDispFlg == null||vm.dto.remainDispFlg == '1')&&!vm.dateFlg))){
            $("#upBtn p").css("color", "#00b050");
            $("#delBtn").attr("disabled", "disabled");
        }

        if (vm.dto.planRegFlg == '2') {
            $("#replyBtn p").css("color", "#00b050");
            $("#deleteBtn").attr("disabled", "disabled");
        } else {
            $("#deleteBtn p").css("color", "#00b050");
            $("#replyBtn").attr("disabled", "disabled");
        }
        //引渡データの区分
        if (v['isTerm'] == 't') {
            vm.isTerm = 't';
        } else {
            vm.isTerm = 'f';
        }
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F20014/init',
                data: {
                    id: v['id'],
                    isTerm: v['isTerm'],
                    blockTypeDiv: v['blocktypediv'],
                    currentYmd: v['currentYmd'],
                    // planRegFlg:v['planRegFlg']
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg, {
                            title: '警告',
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            anim: 1,
                            btn: ['確認']
                        });
                    } else {
                        vm.dto = data.dto;
                        //曜日
                        vm.weekday = data.weekday;
                        //計画学習開始時間
                        vm.planLearnStartTime = data.planLearnStartTime;
                        //計画学習終了時間<計画学習開始時間
                        if (data.planLearnStartTime>data.planLearnEndTime){

                            //計画学習終了時間
                            vm.planLearnEndTime = data.planLearnEndTime+"（翌日）";
                        }
                        //算出された終了時間> 23:45の場合
                        else if (data.planLearnStartTime<data.planLearnEndTime&&data.planLearnEndTime>"23:45"){
                            //計画学習終了時間
                            vm.planLearnEndTime = data.planLearnEndTime+"（翌日）";
                        } else{
                            //計画学習終了時間
                            vm.planLearnEndTime = data.planLearnEndTime;
                        }
                        vm.dateFlg = data.dateFlg;
                    }
                },
                error: function () {
                }
            })
        },
        delFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F20014/delete',
                data: {
                    'id': vm.dto.weeklyId,
                    'stuDelFlg':v.stuDelFlg
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg, {
                            title: '警告',
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            anim: 1,
                            btn: ['確認']
                        });
                    }
                    //success
                    else {
                        $(parent.vm.learnBlock).each(function (i, item) {
                            if (item.id == vm.dto.weeklyId) {
                                parent.vm.learnBlock[i].remainDispFlg = data.remainDispFlg;
                                if(v.stuDelFlg!=null){
                                    parent.vm.learnBlock[i].learnLevUnds = data.learnLevUnds;
                                }
                            }
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                error: function () {
                }
            })
        },
        upFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F20014/update',
                data: {
                    'id': vm.dto.weeklyId,
                    'stuDelFlg':v.stuDelFlg
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg, {
                            title: '警告',
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            anim: 1,
                            btn: ['確認']
                        });
                    }
                    //success
                    else {
                        $(parent.vm.learnBlock).each(function (i, item) {
                            if (item.id == vm.dto.weeklyId) {
                                parent.vm.learnBlock[i].remainDispFlg = data.remainDispFlg;
                                if(v.stuDelFlg!=null){
                                    parent.vm.learnBlock[i].learnLevUnds = data.learnLevUnds;
                                }
                            }
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                error: function () {
                }
            })
        },
        cancelFn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        replyFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F20014/reply',
                data: {
                    'id': vm.dto.termPlanId
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg, {
                            title: '警告',
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            anim: 1,
                            btn: ['確認']
                        });
                    }
                    //success
                    else {
                        $(parent.vm.learnBlock).each(function (i, item) {
                            if (item.termId == vm.dto.termPlanId) {
                                parent.vm.learnBlock[i].learnLevUnds = '未計画';
                            }
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                error: function () {
                }
            })
        },
        deleteFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F20014/deleteTermPlan',
                data: {
                    'id': vm.dto.termPlanId
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg, {
                            title: '警告',
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            anim: 1,
                            btn: ['確認']
                        });
                    }
                    //success
                    else {
                        $(parent.vm.learnBlock).each(function (i, item) {
                            if (item.termId == vm.dto.termPlanId) {
                                parent.vm.learnBlock[i].learnLevUnds = '削除';
                            }
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                error: function () {
                }
            })
        }
    }

});
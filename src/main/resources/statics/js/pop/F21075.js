var param = getParam();
var vm = new Vue({
    el: "#page",
    data: {
        f21075AskDtos: [],
        f21075Dtos: [],
        mstCodDEntities: [],
        talkRecordDEntityAskList: [],
        talkRecordHEntity: "",
        talkRecordDEntityTalkList: [],
        proxyFlg: false
    },
    mounted: function () {
        this.init();
    },
    updated: function () {
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/pop/F21075/init',
                type: 'get',
                datatype: 'json',
                data: {
                    talkId: param.talkId,
                    eventId: param.eventId
                },
                success: function (data) {
                    if (data.f21075AskDtos) {
                        vm.f21075AskDtos = data.f21075AskDtos;
                    }
                    if (data.f21075Dtos) {
                        vm.f21075Dtos = data.f21075Dtos;
                    }
                    if (data.mstCodDEntities) {
                        vm.mstCodDEntities = data.mstCodDEntities;
                    }
                    if (data.talkRecordDEntityAskList) {
                        vm.talkRecordDEntityAskList = data.talkRecordDEntityAskList;
                        for (let i = 0; i < vm.talkRecordDEntityAskList.length; i++) {
                            var talkRecordDEntity = vm.talkRecordDEntityAskList[i];
                            if (talkRecordDEntity.answerTypeDiv === '3' && talkRecordDEntity.answerReltCont && talkRecordDEntity.answerReltCont.lastIndexOf('../') >= 0){
                                var link = '<a target="_self" class="hyper_link" style="text-decoration: underline; color: blue;" ' +
                                    'onclick="vm.showImg(\'' + encodeURIComponent(talkRecordDEntity.answerReltCont) + '\')">添付</a>';
                                talkRecordDEntity.answerReltCont = link;
                            }
                        }
                    }
                    if (data.talkRecordHEntity) {
                        vm.talkRecordHEntity = data.talkRecordHEntity;
                    }
                    if (data.talkRecordDEntityTalkList) {
                        vm.talkRecordDEntityTalkList = data.talkRecordDEntityTalkList;
                    }
                    vm.proxyFlg = data.proxyFlg;
                }
            });
        },
        cancel: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        commit: function () {
            var params = [];
            var formData = new FormData();
            var flg = "";
            if (vm.talkRecordHEntity.talkApplyStsDiv == '0') {
                flg = 0;
            } else {
                flg = 1;
            }
            var num = 0;
            if (vm.proxyFlg) {
                for (var i = 0; i < vm.f21075AskDtos.length; i++) {
                    var paramObj = {};
                    paramObj.itemTypeDiv = vm.f21075AskDtos[i].itemTypeDiv;
                    paramObj.askNum = vm.f21075AskDtos[i].askNum;
                    paramObj.answerTypeDiv = vm.f21075AskDtos[i].answerTypeDiv;
                    paramObj.questionName = vm.f21075AskDtos[i].questionName;
                    paramObj.content = '';
                    if (vm.f21075AskDtos[i].answerTypeDiv == '0') {
                        paramObj.content = $(".proxy").eq(i).find("textarea").eq(0).val();
                    }
                    if (vm.f21075AskDtos[i].answerTypeDiv == '1') {
                        paramObj.content = $(".proxy").eq(i).find("select").eq(0).find("option:selected").html();
                    }
                    if (vm.f21075AskDtos[i].answerTypeDiv == '2') {
                        $(".proxyCheck").eq(num).find("input:checked").each(function (index, element) {
                            if (index == $(".proxyCheck").eq(num).find("input:checked").length - 1) {
                                paramObj.content += $(this).next("span").html();
                            } else {
                                paramObj.content += $(this).next("span").html() + ',';
                            }
                        });

                        num++;
                    }
                    params.push(paramObj);
                }
            }
            num = 0;
            for (var i = 0; i < vm.f21075Dtos.length; i++) {
                var paramObj = {};
                paramObj.itemTypeDiv = vm.f21075Dtos[i].itemTypeDiv;
                paramObj.askNum = vm.f21075Dtos[i].askNum;
                paramObj.answerTypeDiv = vm.f21075Dtos[i].answerTypeDiv;
                paramObj.questionName = vm.f21075Dtos[i].questionName;
                paramObj.content = '';
                if (vm.f21075Dtos[i].answerTypeDiv == '0') {
                    paramObj.content = $(".normal").eq(i).find("textarea").eq(0).val();
                }
                if (vm.f21075Dtos[i].answerTypeDiv == '1') {
                    paramObj.content = $(".normal").eq(i).find("select").eq(0).find("option:selected").html();
                }
                if (vm.f21075Dtos[i].answerTypeDiv == '2') {
                    $(".normalCheck").eq(num).find("input:checked").each(function (index, element) {
                        if (index == $(".normalCheck").eq(num).find("input:checked").length - 1) {
                            paramObj.content += $(this).next("span").html();
                        } else {
                            paramObj.content += $(this).next("span").html() + ',';
                        }
                    });

                    num++;
                }
                params.push(paramObj);
            }
            formData.append("talkId", param.talkId);
            formData.append("flg", flg);
            formData.append("isProxy", vm.proxyFlg);
            for (var i = 0; i < params.length; i++) {
                if (params[i].content === null || params[i].content === "") {
                    var idx = layer.alert($.format($.msg.MSGCOMD0001, "入力可能の項目"), {
                        yes: function () {
                            layer.close(idx);
                        }
                    });
                    return;
                }
            }
            formData.append("resultList", JSON.stringify(params));
            $.ajax({
                url: ctxPath + "/pop/F21075/commit",
                data: formData,
                type: "POST",
                datatype: "JSON",
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        // var idx = layer.alert($.format($.msg.MSGCOMN0022, "更新"), {
                        //     yes: function () {
                                parent.location.reload();
                        //         layer.close(idx);
                        //     }
                        // });
                    } else {
                        layer.alert(data.msg);
                    }
                }
            });
        },
        showImg: function (imgUrl){
            var imgHtml = "<img id='preview' src='" + decodeURIComponent(imgUrl).replace(/\%20/g," ") + "' />";
            var img = new Image();
            img.src= decodeURIComponent(imgUrl).replace(/\%20/g," ");
            var loadingLoop = layer.load(1, {
                shade: [0.5, '#CCC']
            });
            img.onload = function () {
                if(img.complete){
                    layer.close(loadingLoop);
                }
                var w = window.innerWidth*0.95;
                var h = window.innerHeight*0.8;
                var tempWidth;
                var tempHeight;
                if(img.naturalWidth/img.naturalHeight >= w/h){
                    if (img.naturalWidth> w){
                        tempWidth = w;
                        tempHeight = (img.naturalHeight * w)/img.naturalWidth;
                    } else{
                        tempWidth = img.naturalWidth;
                        tempHeight = img.naturalHeight;
                    }
                }else {
                    if (img.naturalHeight>h){
                        tempHeight = h;
                        tempWidth = (img.naturalWidth * h)/img.naturalHeight;
                    } else {
                        tempWidth = img.naturalWidth;
                        tempHeight = img.naturalHeight;
                    }
                }
                var height = tempHeight + "px";
                var width = tempWidth + "px";
                layer.open({
                    type: 1,
                    offset: 'auto',
                    closeBtn:1,
                    area: [width,height],
                    shadeClose: false,
                    scrollbar: false,
                    title: "",
                    content: imgHtml,
                    cancel: function () {
                    }
                });
            };
            // return false;
        }
    }
});
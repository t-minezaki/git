var v = getParam();
if (v['frame'] == 'f10301') {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.iframeAuto(index);
}else{
    var index = parent.parent.layer.getFrameIndex(window.name);
    parent.parent.layer.iframeAuto(index);
}
var bigBlockId = 0;
var smallBlockId = 0;
if (v['bigBlockId']) {
    $("#delBtn").css("display", "none");
    bigBlockId = v['bigBlockId'];
}
if (v['smallBlockId']) {
    $("#insertBtn").css("display", "none");
    $("input[name='smallBlockNm']").attr("disabled", "disabled");
    smallBlockId = v['smallBlockId'];
}
var vm = new Vue({
    el: '#app',
    data: {
        bigBlock: '',
        smallBlock: '',
        flag: false,
        blockPicDiv:'schedule2.png'
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F10308/init',
                data: {
                    bigBlockId: bigBlockId,
                    smallBlockId: smallBlockId
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.parent.layer.alert(data.msg);
                    }
                    if (data.bigBlock) {
                        vm.bigBlock = data.bigBlock;
                        vm.blockPicDiv=data.bigBlock.blockPicDiv;
                    }
                    if (data.smallBlock) {
                        vm.smallBlock = data.smallBlock;
                        vm.flag = true;
                        $(" input[ name='smallBlockNm' ] ").attr("disabled", "disabled");
                    }
                },
                error: function () {
                }
            })
        },
        submitFn: function () {
            if($(" input[ name='smallBlockNm' ] ").val()==""){
                parent.parent.layer.alert($.format($.msg.MSGCOMD0001, "その他ブロック名"));
                return
            }
            $.ajax({
                url: ctxPath + '/pop/F10308/submit',
                data: {
                    'blockId': bigBlockId,
                    'smallBlockNm': $(" input[ name='smallBlockNm' ] ").val().replace(/ /g, "　")
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    var parentPage;
                    if (v['frame'] == 'f10301') {
                        parentPage = parent;
                    } else {
                        parentPage = parent.parent;
                    }
                    if (data.code != 0) {
                        parentPage.layer.alert(data.msg);
                    } else {
                        var index = parentPage.layer.getFrameIndex(window.name);
                        // その他ブロックの登録した後、画面更新処理
                        parent.vm.otherBlock.push(data.blockDto);

                        parentPage.layer.close(index);
                    }
                },
                error: function () {
                }
            });
        },
        cancelFn: function () {
            /*if (v['bigBlockId']) {
                window.location.href = './F10304.html';
            }
            if (v['smallBlockId']) {
                window.location.href = './F10301.html';
            }*/
            var index = parent.parent.layer.getFrameIndex(window.name);
            parent.parent.layer.close(index);
        },
        delFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F10308/delete',
                data: {
                    'blockId': v['smallBlockId']
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.parent.layer.alert(data.msg);
                    } else {
                        var parentPage;
                        if (v['frame'] == 'f10301') {
                            parentPage = parent;
                        } else {
                            parentPage = parent.parent;
                        }
                        var index = parentPage.layer.getFrameIndex(window.name);

                        // その他ブロックの削除した後、画面更新処理
                        $(parentPage.vm.otherBlock).each(function (i, block){
                            if (block.blockId == smallBlockId) {
                                parentPage.vm.otherBlock.splice(i, 1);
                                return false
                            }
                        });

                        parentPage.layer.close(index);
                    }
                },
                error: function () {
                }
            });
        }
    }

});

function changeValue() {
    $("#showright").html($(" input[ name='smallBlockNm' ] ").val());
}
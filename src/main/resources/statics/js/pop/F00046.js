let selectTwoLength;
let vm = new Vue({
    el: '.main',
    data: {
        org: ''
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
        //all add to right
        $("#allToRight").click(function () {
            $("#select1 *").appendTo("#select2");
            //length of rightarea
            selectTwoLength = $("#select2 option").length;
        });
        //all add to left
        $("#allToLeft").click(function () {
            $("#select2 *").appendTo("#select1");
        });
    },
    methods: {
        search: function () {
            $("#select1").find("option").remove();
            $("#select2").find("option").remove();

            let data = {
                orgId: $('#orgId').val(),
                orgName: $('#orgName').val()
            }

            $.get(ctxPath + '/pop/f00046/search', data, function(result){
                if (result.code !== 0) {
                    let msg = layer.confirm(result.msg, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            layer.close(msg);
                        }
                    })
                    return;
                }
                if (!!result.orgList) {
                    for(let i = 0; i < result.orgList.length ;i++){
                        $("#select1").append($("<option>"+getOrgName(result.orgList[i].orgNm)+"</option>").attr(
                            {
                                "orgId": result.orgList[i].orgId,
                                "orgNm": result.orgList[i].orgNm,
                                "level": result.orgList[i].level,
                                "upplevOrgId": result.orgList[i].upplevOrgId,
                            }));
                    }
                }
            });
        }
    }
});

// 選択押す
$("#chooseBtn").click(function () {
    let selectedOrg = [];

    // 組織情報の取得
    $("#select2 option").each(function () {
        selectedOrg.push({
            "orgId":$(this).attr("orgId"),
            "orgNm":$(this).attr("orgNm"),
            "level":$(this).attr("level"),
            "upplevOrgId": $(this).attr("upplevOrgId"),
        })
    });
    // 画面．追加対象エリアで一件でもないの場合
    if (selectedOrg.length === 0) {
        let msgBox = layer.confirm($.format($.msg.MSGCOMN0096, "組織対象", "「＞　または　＞＞」"), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                layer.close(msgBox);
            }
        })
        return;
    }

    // 取得したの最上位組織保留する
    $.ajax({
        url: ctxPath + '/pop/f00046/retainTopOrg',
        type: "POST",
        data: JSON.stringify(selectedOrg),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(result){
            if (result.code !== 0){
                parent.layer.alert(result.msg);
                return;
            }

            parent.vm.lowerOrgList = getSubtract(selectedOrg, result.orgList);
            // 親ページにデータを返す
            parent.vm.returnList = result.orgList;
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }})
});

$("#closeBtn").click(function () {
    let index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});

function getSubtract(arr1, arr2) {
    for (let i = arr1.length - 1; i >= 0; i--) {
        for (let j = 0; j  < arr2.length; j++) {
            if (arr1[i].orgId === arr2[j].orgId) {
                arr1.splice(i, 1);
                break;
            }
        }
    }
    return arr1;
}

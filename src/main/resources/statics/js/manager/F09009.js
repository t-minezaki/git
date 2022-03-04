// 2020/11/9 zhangminghao modify start
let param = getParam();
let updt;
let orgId;
let id;
// 2020/11/9 zhangminghao modify end
// 2020/11/13 zhangminghao modify start
/**
 * 自動ポイント付与設定
 */
let pointGrantSetting = [
    {title: 'ログイン時', field: 'goSchPoint'},
    {title: '実行登録時', field: 'doLoginPoint'},
    // 2020/12/2 huangxinliang modify start
    {title: '実行時間累計', field: 'doTotalPoint'},
    {title: '入室時', field: 'inRoomPoint'}
    // 2020/12/2 huangxinliang modify end
    <!-- add at 2021/08/11 for V9.02 by NWT wen START -->
    , {title: '合格時', field: 'paddPoint'}
    , {title: '満点時', field: 'fullMarksPoint'}
    , {title: '宿題提出時', field: 'workOutPoint'}
    , {title: '出席登録時', field: 'attentOutPoint'}
    , {title: '誕生日時', field: 'birthdayTimePoint'}
    <!-- add at 2021/08/11 for V9.02 by NWT wen END -->
];
let optional = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
// 2020/11/13 zhangminghao modify end
function getInformation(){
    $.ajax({
        url: ctxPath + '/manager/F09009/init',
        type: 'GET',
        datatype: 'json',
        success: function (data) {
            if(data.code === 0){
                let org = data.org;
                $("#reRecoTime").val(org.reRecoTime);
                // 2020/11/13 zhangminghao modify start
                for (let item of pointGrantSetting) {
                    let field = item.field
                    // modify at 2021/08/23 for V9.02 by NWT wen START
                    $("#" + field).val(org[field] ? org[field] : 0);
                    // modify at 2021/08/23 for V9.02 by NWT wen END
                }
                // 2020/11/13 zhangminghao modify end
                updt = data.updateStrCheck;
                orgId = data.org.orgId;
                id = data.org.id;
            }
        }
    });
}
$("#btn_ok").click(function () {
    // 2020/11/13 zhangminghao modify start
    let reRecoTime = $("#reRecoTime").val();
    let data = {
        reRecoTime: reRecoTime,
        id: id,
        updateStrCheck: updt,
        orgId: orgId
    }
    for (let item of pointGrantSetting) {
        let field = item.field
        data[field] = $("#" + field).val()
    }
    // 2020/11/13 zhangminghao modify end
    let index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        title: '確認',
        closeBtn: 0,
        shadeClose: false,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        // 2020/11/9 zhangminghao modify start
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F09009/edit',
                cache: false,
                data: JSON.stringify(data),
                type: 'POST',
                contentType: "application/json",
                processData: false,
                success: function (data) {
                    if (data.code === 0) {
                        getInformation();
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        // 2020/11/9 zhangminghao modify end
    });
});
// 2020/11/13 zhangminghao modify start
new Vue({
    el: '#app',
    mounted(){
        getInformation();
    }
})
// 2020/11/13 zhangminghao modify end
var param = getParam();
var userId = '';
var loginFlg = '';
var roleDiv = '';
var systemKbn = '';
var webUseFlg = '';
var pwUpFlg = '';
var brandCd = getCookie("brandcd").toUpperCase();
//データを確認する
$(function () {
    // var id = $("input[name='userId']").val();
    //check
    $.ajax({
        url: ctxPath + '/common/F40020/reset',
        type: 'POST',
        // data: {
        //     "id": id
        // },
        success: function (data) {
            if (data.userId) {
                userId = data.userId;
            }
            if (data.f40020Dto) {
                loginFlg = data.f40020Dto.fstLoginFlg;
                roleDiv = data.f40020Dto.roleDiv;
                systemKbn = data.f40020Dto.systemKbn;
                webUseFlg = data.f40020Dto.webUseFlg;
                pwUpFlg = data.f40020Dto.pwUpFlg;
            }
        }
    })
});

var vm = new Vue({
    el: '.content',
    data: {
        stuList: [],
        cou: 0
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/guard/F30002/init',
                type: 'get',
                dataType: 'json',
                data: {},
                success: function (data) {
                    if (data.stuList) {
                        vm.stuList = data.stuList;
                    }
                }
            })
            $.get(ctxPath + "/common/F40020/getUpdateTime",
                function (data) {
                    updDatime = data.updDatime;
                })
        }
    }
});
var updDatime='';
var gidFlg='';
// FST_LOGIN_FLG 第一次登陆值为0 F40021
//  ROLE_DIV
// ロール区分が1：管理者、2：メンターの場合、
// ロール区分が3：保護者の場合、
function onClick() {
    //ポップモードかどうかを確認する
    if (window.location.href === parent.location.href) {
        // 初回登録フラグが0：初回、
        if (loginFlg == 0) {
            // Web申込から導入した保護者、導入した組織情報　の　Web申込利用フラグ＝利用済の場合、
            if (webUseFlg == 1) {
                $.ajax({
                    url: ctxPath + '/common/F40020/update',
                    type: 'POST',
                    data: {
                        "updDatime":updDatime,
                        'gidFlg': vm.gidFlg == '1'
                    },
                    success: function (data) {
                        if (data.code != 0) {
                            showMsg($.format($.msg.MSGCOMN0019, "対象データが他ユーザーによって更新されています。対象データを再取得してから更新処理を行ってください。"));
                            return;
                        }

                        //子供１個
                        if (vm.stuList.length == 1) {
                            window.location.href = ctxPath + brandCd=='GKGC'?'/guard/F30421.html':'/guard/F30112.html';
                        }
                        //子供個数が２個以降の場合、
                        else if (vm.stuList.length > 1) {
                            window.location.href = ctxPath + '/guard/F30002.html'
                        }

                    }
                })}
            //上記以外の場合、
            else
                window.location.href = ctxPath + '/common/F40008.html'
        }
        //初回登録フラグが1：初回以外の場合、
        else {
            //　ロール区分が1：管理者、2：メンターの場合、
            if (roleDiv == 1 || roleDiv == 2) {
                window.location.href = ctxPath + '/manager/F00002.html?flag=1'
            }
            //　ロール区分が3：保護者の場合、
            else if (roleDiv == 3) {
                if (pwUpFlg === '0'){
                    window.location.href = ctxPath + '/common/F40008.html'
                } else{
                    //子供１個
                    if (vm.stuList.length == 1) {
                        window.location.href = ctxPath + brandCd=='GKGC'?'/guard/F30421.html':'/guard/F30112.html';
                    }
                    //子供個数が２個以降の場合、
                    else if (vm.stuList.length > 1) {
                        window.location.href = ctxPath + '/guard/F30002.html'
                    }
                }
            }
        }
    } else {
        //ポップモードを閉じる
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
}

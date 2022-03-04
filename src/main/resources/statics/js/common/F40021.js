var vm = new Vue({
    el: '#all',
    data: {
        gidFlg: '',
        gidRuleFlg: '',
        manaRuleFlg: '',
        roleDiv: ''
    },
    mounted: function (){
        //check
        $.ajax({
            url: ctxPath + '/common/F40021/init',
            type: 'POST',
            success: function (data) {
                if (data.updDatime) {
                    updDatime = data.updDatime;
                    vm.gidFlg = data.gidFlg;
                    vm.gidRuleFlg = data.gidRuleFlg;
                    vm.manaRuleFlg = data.manaRuleFlg;
                    vm.roleDiv = data.roleDiv;
                }
            }
        })
    }
});

// ・確認チェックボックスがチェックする場合、
// 　非活性。
// ・確認チェックボックスがチェックしない場合、
// 　活性。
function chkClick() {
    if (document.getElementById("selectMe").checked == true) {
        document.getElementById("next").disabled = false;
        $("#next").css("background-color","#189746","border","1px solid green");
        return true;
    } else {
        document.getElementById("next").disabled = true;
        $("#next").css("background-color","#bfbfbf","border","1px solid #bfbfbf");
        return false;
    }
}
$(function(){
    chkClick()
})


//      排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
//  	MSGCOMN0019：｛対象データが他ユーザーによって更新されています。対象データを再取得してから更新処理を行ってください。｝｝

var param = getParam();
var userId = '';
var updDatime='';
var brandCd = getCookie("brandcd").toUpperCase();
function clickIt() {
    $.ajax({
        url: ctxPath + '/common/F40021/reset',
        type:'POST',
        data:{
            "updDatime":updDatime,
            'gidFlg': vm.gidFlg === '1' && vm.gidRuleFlg === '0',
            'manaFlg': vm.manaRuleFlg === '0'
        },
        success: function (data) {
            if(data.code!=0){
                var index = layer.confirm($.msg.MSGCOMN0019, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    btn: ['確認'],
                    btn1: function () {
                        parent.layer.close(index);
                    },
                });
            }else {
                if (vm.roleDiv === '3' || vm.roleDiv === '4'){
                    if (data.emptyMail){
                        window.location.href = ctxPath + '/common/F40017.html';
                        return ;
                    }if (data.pwUpFlg == '0'){
                        window.location.href = ctxPath + '/common/F40008.html';
                        return;
                    } else {
                       if (data.count == 1){
                           window.location.href = ctxPath + brandCd=='GKGC'?'/guard/F30421.html':'/guard/F30112.html';
                       } else {
                           window.location.href = ctxPath + '/guard/F30002.html'
                       }
                    }

                }else {
                    $.ajax({
                        url: ctxPath + '/sys/getUrl',
                        type:'GET',
                        success: function (data) {
                            if(data.code!=0){
                                var index = layer.confirm(data.msg, {
                                    skin: 'layui-layer-molv',
                                    title: '確認',
                                    closeBtn: 0,
                                    btn: ['確認'],
                                    btn1: function () {
                                        parent.layer.close(index);
                                    },
                                });
                            }else {
                                window.location.href = ctxPath + data.url;
                            }
                        }
                    })
                }
            }
        }
    })
}

// 戻るボタン押下
// F40025_ログイン画面に戻る確認画面（POP）を表示する。
function toF40025() {
    var srcWidth = '';
    var srcHeight= '';
    if(getCookie("equipment") === "phone"){
        srcWidth = "80%";
        srcHeight = "30%";
    }else {

        srcWidth = "402px";
        srcHeight = "212px";
    }
    layer.open({
        id:'F40025',
        type:2,
        title: ['', 'display:none;'],
        shade: 0.1,
        // button:null,
        closeBtn: 0,
        shadeClose: false,
        move: '.layui-layer-title',
        area: [srcWidth,srcHeight],
        fixed: true,
        resize: false,
        background:'#F0F0F0',
        content:["../common/F40025.html"]
        // end:function () {
        // }
    })
}
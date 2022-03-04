var afterId = '';
var roleDiv='';
var mstUsrEntity = '';
var gidFlg='';
var is_mobi = getCookie("equipment");
var vm = new Vue({
    el: '#app',
    data: {
        roleDiv: '1',
        flag: '0',
        afterId: '',
        tchCod:0
    },
    mounted: function (){
        this.init();
    },
    methods: {
        init: function (){
            //初期化
            if (getCookie("key") === 'PUSHAPI'){
                $(".header_operat").css("display","none");
            }
            if (is_mobi=="phone") {
                $(".ulbottom").css("margin-top","16vw");
            }
            $.ajax({
                url: ctxPath + '/com/F40008/init',
                type: 'GET',
                async: true,
                success:function (data) {
                    if(data.code != 0){
                        if (is_mobi) {
                            layer.alert(data.msg);
                        }else {
                            showMsg(data.msg);
                        }
                    }else {
                        mstUsrEntity = data.mstUsrEntity;
                        roleDiv = mstUsrEntity.roleDiv;
                        vm.roleDiv = mstUsrEntity.roleDiv;
                        gidFlg= mstUsrEntity.gidFlg;
                        //初回登録フラグ　が「0：初回」の場合、
                        if(data.first == true){
                            vm.flag = '0';
                            // 初回登録フラグが「0：初回」場合、非表示。
                            $("#pro").hide();
                            $("#retu").hide();
                            $("#no-first").hide();
                            window.flag = "0";
                        }else {
                            vm.flag = '1';
                            $('#id').val(data.afterId);
                            window.flag = "1";
                            $("#first").hide();
                            $(".modifyPsw").css("visibility","hidden");
                        }
                        window.updDatime = data.updDatime;
                        //生徒の場合のみ、IDが変更できないため、生徒の場合、IDを非活性になる。
                        if (data.display == true){
                            $("input[name='id']").attr("disabled",true);
                            $('#p1').text('');
                        }
                        $('#id').val(data.afterId);
                        vm.afterId = data.afterId;
                        afterId = data.afterId;
                        if (data.tchCod){
                            vm.tchCod =data.tchCod;
                        }
                    }
                }
            })
        }
    }
});

//メールアドレスが一致するかどうかを確認する。
function getInto() {
    var id = afterId;
    var oldPwd = $("input[name='oldPwd']").val();
    var newPwd1 = $("input[name='newPwd1']").val();
    var newPwd2 = $("input[name='newPwd2']").val();
    /* 2021/03/09 manamiru4-33 add start */
    if (gidFlg == 1 && vm.roleDiv==1 && (vm.tchCod > 0 || !mstUsrEntity.gidpk)){
        parent.layer.alert($.msg.MSGCOMN0187);
        return ;
    }
    /* 2021/03/09 manamiru4-33 add end */
    if (idCheck($("#id").val())) {
        // 初回登録フラグが「0：初回」場合
        if (window.flag == "0") {
            //check
            if (passwordCheck(newPwd1) && passwordCheck(newPwd2) && compareTwoPwd(newPwd1, newPwd2)) {
                $.ajax({
                    url: ctxPath + '/com/F40008/update',
                    type: 'POST',
                    data: {
                        "imageId": $("#id").val(),
                        "oldPwd": null,
                        "newPwd": newPwd1,
                        "updDatime": window.updDatime,
                        "gidFlg":gidFlg
                    },
                    async: false,
                    success: function (data) {
                        if (data.code != 0) {
                            if (is_mobi) {
                                layer.alert(data.msg);
                            }else {
                                showMsg(data.msg);
                            }
                        } else {
                            layer.confirm($.format($.msg.MSGCOMN0015, $("#id").val()), {btn: ['確認']}, function () {
                                toHtml();
                            });
                        }
                    }
                })
            }
        } else {
            if (compareTwoPwd(newPwd1, newPwd2) && (newPwd1 === '' || (passwordCheck(newPwd1) && passwordCheck(newPwd2)))) {
                if (newPwd1.length === 0 && afterId === $("#id").val()){
                    parent.layer.alert($.format($.msg.MSGCOMN0186, "パスワード"));
                    return ;
                }
                $.ajax({
                    url: ctxPath + '/com/F40008/update',
                    type: 'POST',
                    data: {
                        "imageId": $("#id").val(),
                        "oldPwd": oldPwd,
                        "newPwd": newPwd1,
                        "updDatime": window.updDatime,
                        "gidFlg":gidFlg
                    },
                    async: false,
                    success: function (data) {
                        if (data.code != 0) {
                            if (is_mobi) {
                                layer.alert(data.msg);
                            }else {
                                showMsg(data.msg);
                            }
                        } else {
                            layer.confirm($.format($.msg.MSGCOMN0015, $("#id").val()), {btn: ['確認']}, function () {
                                toHtml();
                            });
                        }
                    }
                })
            }
        }
    }
}


// パスワードチェック
function passwordCheck(password){
    var count = 0;
    var reg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8,32}$/;

    // nullCheck
    if (password.length == 0 && window.flag == "1"){
        if (is_mobi) {
            layer.alert('パスワードが入力されていません');
        }else {
            showMsg('パスワードが入力されていません');
        }
        count = 1;
    }else if(password.length == 0 && window.flag == "0"){
        if (is_mobi) {
            layer.alert($.format($.msg.MSGCOMD0001,"新パスワード"));
        }else {
            showMsg($.format($.msg.MSGCOMD0001,"新パスワード"));
        }
        count = 1;
    }
    // formatCheck
    else if (!reg.test(password)||password.length > 32||password.length < 8)
    {
        count = 1;
        if (is_mobi) {
            layer.alert($.format($.msg.MSGCOMN0184, 'パスワードは大文字・小文字・数字を含む8文字以上(記号は使えません)で'));
        }else {
            showMsg($.format($.msg.MSGCOMN0184, 'パスワードは大文字・小文字・数字を含む8文字以上(記号は使えません)で'));
        }
    }
    if (count == 0){
        return true;
    } else {
        return false;
    }
}

//idCheck
function idCheck(id){
    var count = 0;
    // formatCheckelse
    if (!test(id)||$("#id").val().length<8||$("#id").val().length>32)
    {
        count = 1;
        if (is_mobi) {
            layer.alert($.format($.msg.MSGCOMD0022,"ユーザＩＤ"));
        }else {
            showMsg($.format($.msg.MSGCOMD0022,"ユーザＩＤ"));
        }
    }
    if (count == 0){
        return true;
    } else {
        return false;
    }
}
function test(str)//Trueには全幅がなく、Falseには全幅があります
{
    for (var i = 0; i < str.length; i++)
    {
        strCode = str.charCodeAt(i);
        if ((strCode > 65248) || (strCode == 12288))
        {
            return false;
        }
    }
    return true;
}

// パスワード比較チェック
function compareTwoPwd(newPwd1,newPwd2) {
    if (newPwd1 != newPwd2){
        if (is_mobi) {
            layer.alert($.format($.msg.MSGCOMN0059,"新しいパスワードとパスワード再入力"));
        }else {
            showMsg($.format($.msg.MSGCOMN0059,"新しいパスワードとパスワード再入力"));
        }
        return false;
    }
    else {
        return true;
    }
}

function Jump() {
    $.ajax({
        url: ctxPath + "/com/F40008/updateStatus",
        type: "GET",
        data: {
            "afterId": afterId,
            "updDatime":updDatime
        },
        success: function (data) {
            toHtml();
        }
    });
}
function toHtml() {
    if (roleDiv == 4) {
        if (is_mobi == 'phone') {
            window.open("../student/F11008.html", "_self");
        } else {
            window.open("../student/F10301.html", "_self");
        }
    }
    if (roleDiv == 3) {
        $.ajax({
            url:ctxPath + "/com/F40008/getStuNumber",
            type:"GET",
            data:{
                "afterId":afterId
            },
            success: function (data) {
                if (data.f30001Dto.count <= 1){
                    window.open(data.brandCd === 'GKGC'?"../guard/F30421.html":"../guard/F30112.html","_self");
                }else {
                    window.open("../guard/F30002.html", "_self");
                }
            }
        });

    }
}

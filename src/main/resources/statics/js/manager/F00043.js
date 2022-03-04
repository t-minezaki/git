var param = getParam();

var vm = new Vue({
    el:'.content',
    data:{
        // ユーザー初期基本情報
        f00043Dto:[],
        // 更新日時
        updDatime:[],
        // ユーザー更新日時
        usrUpdDatime:"",
        // それぞれの基本情報
        userInformation:[],
        // 学年区分
        schy_div:[],
        // 性別を取得
        gender_div:[],
        // 続柄を取得
        reltnsp_div:[],
        // 組織を取得
        mstOrgEntity:[],
        //
        Div:[],
        // birth
        birth:[],
        returnList: [],
        orgList: [],
        lowerOrgList: []
    },
    watch: {
        returnList: {
            handler(arr1) {
                //最初の選択の後、2番目の選択を行うと、重複排除後にディスプレイが表示されます
                let dataList = arr1.concat(vm.orgList);
                $.ajax({
                    url: ctxPath + '/manager/f00043/retainTopOrg',
                    type: "POST",
                    data: JSON.stringify(dataList),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                        if (result.code !== 0) {
                            let msgBox = layer.confirm(result.msg, {
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

                        // 追加したい組織は反映済組織の下組織の場合、アラートで下記メッセージを出す。
                        let selectedLowerOrg = vm.lowerOrgList.concat(getOrgSubtract(dataList, result.orgList));

                        if (!!selectedLowerOrg && selectedLowerOrg.length !== 0){
                            let msg = $.format($.msg.MSGCOMN0169, joinObj(selectedLowerOrg, 'orgId'));

                            let msgBox = layer.confirm(msg, {
                                skin: 'layui-layer-molv',
                                title: '確認',
                                closeBtn: 0,
                                anim: -1,
                                btn: ['確認'],
                                btn1: function () {
                                    layer.close(msgBox);
                                }
                            })
                        }

                        vm.orgList = result.orgList;
                    }
                })
            },
            deep: true
        }
    },
    computed:{
        postCode: {
            get:function (){
                return this.userInformation.postcdMnum + this.userInformation.postcdBnum;
            },
            set:function (val){
                this.userInformation.postcdMnum = val.substring(0, 3);
                this.userInformation.postcdBnum = val.substring(3);
            }
        }
    },
    updated:function(){
        if (this.userInformation.reltnspDiv !== undefined){
            if (this.userInformation.reltnspDiv === null || this.userInformation.reltnspDiv.trim() === ''){
                $(".parent").find("option").each(function () {
                    if ($(this).val() === '1'){
                        $( this).attr("selected",true);
                        return;
                    }
                })
            }
        }
    },
    mounted:function () {
        this.getInfo();
        formatCheck();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f00043/init',
                data:{
                    usrId:param.usrId
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.mstOrgEntity){
                            vm.mstOrgEntity = data.mstOrgEntity;
                        }
                        if (data.schy_div){
                            vm.schy_div = data.schy_div;
                        }
                        if (data.gender_div){
                            vm.gender_div = data.gender_div;
                        }
                        if (data.reltnsp_div){
                            vm.reltnsp_div = data.reltnsp_div;
                        }
                        if (data.f00043Dto){
                            vm.f00043Dto = data.f00043Dto;
                        }
                        if (data.userInformation){
                            vm.userInformation = data.userInformation;
                        }
                        if (vm.userInformation.postcdBnum){
                            vm.userInformation.postcdBnum = vm.userInformation.postcdBnum.trim("");
                        }
                        if (data.usrUpdDatime){
                            vm.usrUpdDatime = data.usrUpdDatime;
                        }
                        if (data.updDatime){
                            vm.updDatime = data.updDatime;
                        }
                        if (data.birth){
                            vm.birth = data.birth;
                        }
                        $("#birthDay").val(data.birth);
                        $('#birthDay').mobiscroll().date({
                            theme: "mobiscroll",
                            preset : 'date',
                            display: 'modal',
                            dateFormat : 'yy/mm/dd',
                            mode:"scroller",
                            setText:'確認',
                            cancelText:"キャンセル",
                            dayText:'日',
                            monthText:'月',
                            yearText:'年',
                            dateOrder : 'yymmdd',
                            defaultValue: new Date(vm.birth),
                            onShow:function () {
                                $(".dwwl0").find(".dw-i").each(function () {
                                    $(this).text($(this).text()+'年');
                                });
                                $(".dwwl1").find(".dw-i").each(function () {
                                    $(this).text($(this).text()+'月');
                                });
                                $(".dwwl2").find(".dw-i").each(function () {
                                    $(this).text($(this).text()+'日');
                                });
                            }
                        });
                        $("#time_picker_begin").mobiscroll("setVal",vm.birth);
                        if(data.orgList){
                            vm.orgList = data.orgList;
                        }
                    }
                    if (vm.schy_div) {
                        for(var i = 0;i< vm.schy_div.length;i++){
                            if (vm.userInformation.schyDiv == vm.schy_div[i].codCd) {
                                vm.Div.schyDiv = vm.schy_div[i].codValue;
                                break;
                            }
                        }
                    }
                    if (vm.gender_div) {
                        for(var i = 0;i< vm.gender_div.length;i++){
                            if (vm.userInformation.gendrDiv == vm.gender_div[i].codCd) {
                                vm.Div.gendrDiv = vm.gender_div[i].codValue;
                                break;
                            }
                        }
                    }
                    if (vm.reltnsp_div) {
                        for(var i = 0;i< vm.reltnsp_div.length;i++){
                            if (vm.userInformation.reltnspDiv == vm.reltnsp_div[i].codCd) {
                                vm.Div.reltnspDiv = vm.reltnsp_div[i].codValue;
                                break;
                            }
                        }
                    }
                    if (vm.f00043Dto.codCd == "1" || vm.f00043Dto.codCd == "2"){
                        $("#manager").show();
                        $("#guard").remove();
                        $("#student").remove();
                    }
                    if (vm.f00043Dto.codCd == "3"){
                        $("#guard").show();
                        $("#manager").remove();
                        $("#student").remove();
                    }
                    if (vm.f00043Dto.codCd == "4"){
                        $("#student").show();
                        $("#manager").remove();
                        $("#guard").remove();
                    }
                    if (data.mgrModifyFlg == "0"){
                        $(".flnmNm").attr("readonly","readonly");
                        $(".flnmLnm").attr("readonly","readonly");
                        $(".flnmKnNm").attr("readonly","readonly");
                        $(".flnmKnLnm").attr("readonly","readonly");
                        $(".telNum").attr("readonly","readonly");
                        $(".parent").attr("disabled","disabled");
                        $(".postCd").attr("readonly","readonly");
                        $(".addr1").attr("readonly","readonly");
                        $(".addr2").attr("readonly","readonly");
                        $(".gender").attr("disabled","disabled");
                        $(".schoolYear").attr("disabled","disabled");
                        $("#searchButton").attr("disabled","disabled");
                        $(".birthDay").attr("readonly","readonly");
                        $(".schoolName").attr("readonly","readonly");
                        $(".urgent").attr("readonly","readonly");
                        $("#submit").css("visibility","hidden");
                    }
                }
            });
        },
        //半角check
        isHalfAngle:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(48>str.charCodeAt(i)||str.charCodeAt(i)>57){
                    flg=false;
                    break;
                }
            }
            return flg
        },
        searchPostCd: function () {
            // 郵便番号
            // 郵便番号が未入力の場合、エラーとする
            if($(".postCd").val() == ''){
                showMsg($.format($.msg.MSGCOMD0001,"郵便番号"));
                $("#postcd").focus();
                return;
            }
            // 郵便番号が７文字を超える場合、エラーとする
            if ($(".postCd").val().length > 7 ){
                showMsg($.format($.msg.MSGCOMD0017,"郵便番号","7"));
                return;
            }
            // 郵便番号が５文字不足場合、エラーとする
            if ($(".postCd").val().length < 7){
                showMsg($.format($.msg.MSGCOMD0016,"郵便番号","7"));
                return;
            }
            // 郵便番号が半角数字以外で入力した場合、エラーとする
            if(this.isHalfAngle($(".postCd").val()) == false){
                showMsg($.format($.msg.MSGCOMD0006,"郵便番号"));
                $(".postCd").focus();
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/f00042/searchPostCd',
                data:{
                    postcd:$(".postCd").val()
                },
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data.code == 0) {
                        $(".addr1").val(data.adr1);
                        vm.userInformation.adr1 = data.adr1;
                    }else{
                        showMsg(data.msg);
                    }
                }
            });
        },
        submitFn:function () {
            if (this.f00043Dto.codCd == '3' && $(".telNum").val() == '') {
                showMsg($.format($.msg.MSGCOMD0001, "電話番号"));
                return ;
            }
            if($("#f00042Form").valid() == true) {
                // 生徒学年選択チェック
                var flg = 0;
                if($(".schoolYear").val() != vm.userInformation.schyDiv){
                    flg = 1;
                }
                if (vm.orgList.length === 0){
                    showMsg($.format($.msg.MSGCOMD0001,"利用組織"));
                    return
                }
                $.ajax({
                    url: ctxPath + '/manager/f00043/checkAfterUserId',
                    data: {
                        userId:param.usrId,
                        newUserId: $(".userId").val(),
                        flg:flg
                    },
                    dataType: 'json',
                    type: 'POST',
                    success: function (data) {
                        if (data.code == 0) {
                            var msg = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
                                skin: 'layui-layer-molv',
                                title: '確認',
                                closeBtn: 0,
                                anim: -1,
                                btn: ['キャンセル','確認'],
                                btn2: function () {
                                    let data = {
                                        oldLoginId: vm.f00043Dto.afterUsrId,
                                        oldPwd: vm.f00043Dto.usrPassword,
                                        gidFlg: vm.f00043Dto.gidFlg,
                                        gidpk: vm.f00043Dto.gidpk,
                                        usrId: param.usrId,
                                        userId: $(".userId").val(),
                                        password: $(".password").val(),
                                        flnmNm: $(".flnmNm").val(),
                                        flnmLnm: $(".flnmLnm").val(),
                                        flnmKnNm: $(".flnmKnNm").val(),
                                        flnmKnLnm: $(".flnmKnLnm").val(),
                                        email: $(".email").val(),
                                        telNum: $(".telNum").val(),
                                        postCd: $(".postCd").val(),
                                        parent: $(".parent").val(),
                                        urgTelNum: $(".urgent").val(),
                                        addr1: $(".addr1").val(),
                                        addr2: $(".addr2").val(),
                                        schoolYear: $(".schoolYear").val(),
                                        schoolName: $(".schoolName").val(),
                                        birthDay: $(".birthDay").val(),
                                        gender: $(".gender").val(),
                                        usrSts:$('input:radio:checked').val(),
                                        updateTime:vm.usrUpdDatime,
                                        updDatime:vm.updDatime,
                                        orgList: vm.orgList
                                    }
                                    $.ajax({
                                        url: ctxPath + '/manager/f00043/setInformation',
                                        data: JSON.stringify(data),
                                        dataType: 'json',
                                        contentType: "application/json; charset=utf-8",
                                        type: 'POST',
                                        success: function (data) {
                                            if (data.code == 0) {
                                                // layer.confirm($.format($.msg.MSGCOMN0022,"登録"),{btn:['確認']},function(){
                                                //     layer.close(msg);
                                                    window.location.href="./F00041.html";
                                                // });
                                            } else {
                                                layer.close(msg);
                                                showMsg(data.msg);
                                            }
                                        }
                                    });
                                },
                                btn1: function () {
                                    layer.close(msg);
                                }
                            });
                        }
                        else {
                            showMsg(data.msg);
                        }
                    }
                });
            }
        },
        bakBtn: function () {
            window.location.href = "F00041.html?roleDiv="+param.role+'&usrSts='+param.usrSts;
        },
        //組織選択BTN
        chooseBtn: function () {
            var srcWidth = $(window).width() * 0.8;
            if (srcWidth > 280) {
                var srcH = 800;
                srcWidth = '800px';
            } else {
                var srcH = srcWidth;
                srcWidth = srcWidth + 'px'
            }
            var srcHeight = srcH * 0.4 + 'px';
            layer.open({
                id: 'f00042',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                content: ["../pop/F00046.html", 'no']
            })
        },
        //削除
        delOrgBtn: function (arr,index) {
            arr.splice(index,1);
        }
    }
});

function formatCheck() {
    $("#f00042Form").validate({
        onfocusout: false,
        rules: {
            // 利用者ＩＤ
            userId: {
                required: true,
                alphaNumSymbol: true,
                minlength: 8,
                maxlength: 32
            },
            // パスワード
            password: {
                minlength: 8,
                maxlength: 32,
                // hasAlphaNum: true,
                pwd: true
            },
            // 名前_姓
            flnmNm: {
                required: true,
                <!--2021/02/24 manamiru1-524 cuikailin start -->
                maxlength: 30,
                zenkaku: true
            },
            // 名前_名
            flnmLnm: {
                required: true,
                maxlength: 30,
                zenkaku: true
            },
            // 名前_カナ姓
            flnmKnNm: {
                required: true,
                maxlength: 30,
                zenkaku: true
            },
            // 名前_カナ名
            flnmKnLnm: {
                required: true,
                maxlength: 30,
                <!--2021/02/24 manamiru1-524 cuikailin end -->
                zenkaku: true
            },
            // メール
            email: {
                // required:true,
                email:true,
                maxlength:60
            },
            // 電話番号
            telNum: {
                minlength: 10,
                maxlength: 11,
                digits: true
            },
            // 郵便番号
            postCd: {
                required: true,
                minlength: 7,
                maxlength: 7,
                digits: true
            },
            // 住所1
            addr1: {
                required: true,
                maxlength: 60
            },
            // 住所2
            addr2: {
                maxlength: 60
            },
            // 学校
            schoolName: {
                maxlength: 30
            },
            //生年月日
            birthDay: {
                required: true
            },
            //性別
            gender: {
                required: true
            },
            // 学年
            schoolYear: {
                required: true
            },
            //緊急電話番号
            urgent: {
                minlength: 10,
                maxlength: 11,
                digits: true
            }
        },
        debug: true,
        messages: {
            // 利用者ＩＤ
            userId: {
                required: $.format($.msg.MSGCOMD0001, "利用者ＩＤ"),
                minlength: $.format($.msg.MSGCOMD0016, "利用者ＩＤ", "8"),
                maxlength: $.format($.msg.MSGCOMD0017, "利用者ＩＤ", "32"),
                alphaNumSymbol: $.format($.msg.MSGCOMD0004, "利用者ＩＤ")
            },
            // 新パスワード
            password: {
                minlength: $.format($.msg.MSGCOMD0016, "新パスワード", "8"),
                maxlength: $.format($.msg.MSGCOMD0017, "新パスワード", "32"),
                // hasAlphaNum: $.format($.msg.MSGCOMD0004, "新パスワード"),
                pwd: $.format($.msg.MSGCOMN0162, "新パスワード")
            },
            // 名前_姓
            flnmNm: {
                required: $.format($.msg.MSGCOMD0001, "姓名_姓"),
                <!--2021/02/24 manamiru1-524 cuikailin start -->
                maxlength: $.format($.msg.MSGCOMD0017, "姓名_姓", "30"),
                zenkaku: $.format($.msg.MSGCOMD0007, "姓名_姓")
            },
            // 名前_名
            flnmLnm: {
                required: $.format($.msg.MSGCOMD0001, "姓名_名"),
                maxlength: $.format($.msg.MSGCOMD0017, "姓名_名", "30"),
                zenkaku: $.format($.msg.MSGCOMD0007, "姓名_名")
            },
            // 名前_カナ姓
            flnmKnNm: {
                required: $.format($.msg.MSGCOMD0001, "カナ姓名_姓"),
                maxlength: $.format($.msg.MSGCOMD0017, "カナ姓名_姓", "30"),
                zenkaku: $.format($.msg.MSGCOMD0007, "カナ姓名_姓")
            },
            // 名前_カナ名
            flnmKnLnm: {
                required: $.format($.msg.MSGCOMD0001, "カナ姓名_名"),
                maxlength: $.format($.msg.MSGCOMD0017, "カナ姓名_名", "30"),
                <!--2021/02/24 manamiru1-524 cuikailin end -->
                zenkaku: $.format($.msg.MSGCOMD0007, "カナ姓名_名")
            },
            // メール
            email: {
                // required:$.format($.msg.MSGCOMD0001,"メールアドレス"),
                maxlength:$.format($.msg.MSGCOMD0017,"メールアドレス","60"),
                email:$.format($.msg.MSGCOMD0018,"メールアドレス")
            },
            // 電話番号
            telNum: {
                minlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                digits: $.format($.msg.MSGCOMD0006, "電話番号")
            },
            // 郵便番号
            postCd: {
                required: $.format($.msg.MSGCOMD0001, "郵便番号"),
                minlength: $.format($.msg.MSGCOMD0016, "郵便番号", "7"),
                maxlength: $.format($.msg.MSGCOMD0017, "郵便番号", "7"),
                digits: $.format($.msg.MSGCOMD0006, "郵便番号")
            },
            // 住所1
            addr1: {
                required: $.format($.msg.MSGCOMD0001, "住所1"),
                maxlength: $.format($.msg.MSGCOMD0017, "住所1", "60")
            },
            // 住所2
            addr2: {
                maxlength: $.format($.msg.MSGCOMD0017, "住所2", "60")
            },
            // 学校
            schoolName: {
                maxlength: $.format($.msg.MSGCOMD0017, "学校", "30")
            },
            //生年月日
            birthDay: {
                required: $.format($.msg.MSGCOMD0001, "生年月日")
            },
            //性別
            gender: {
                required: $.format($.msg.MSGCOMD0001, "性別")
            },
            // 学年
            schoolYear: {
                required: $.format($.msg.MSGCOMD0001, "学年")
            },
            // 緊急電話番号
            urgent: {
                minlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "電話番号", "10", "11"),
                digits: $.format($.msg.MSGCOMD0006, "緊急電話番号")
            }
        },
        showErrors: function (errorMap, errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}

function getOrgSubtract(arr1, arr2) {
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

function joinObj(a, attr) {
    let out = [];
    for (let i = 0; i < a.length; i++) {
        out.push(a[i][attr]);
    }
    return out.join(", ");
}
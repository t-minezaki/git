var menuHeight= $(window).height() -  $(window).width()*0.09;
var menuHeightt= $(window).height() - $(window).width()*0.114 ;
$(".contentback").css("height",menuHeight);
var number = 0;
$(function () {
    $(".popTitle").click(function () {
        var index=$(this).attr("data")
        layer.open({
            id:'p10502',
            type: 2,
            title: false,
            shade: 0.1,
            closeBtn: 0,
            shadeClose: false,
            area: ['310px', '315px'],
            fixed: true,
            resize: false,
            content: ["../pop/F10502.html?id="+index+"&codCd="+$("#testPrd").attr("desc")+"&schy="+$("#schyDiv").attr("desc"), 'no'],
            success: function (layero, index) {
            },
        })
    });
})
var param=getParam();
if (param.id !=undefined){
    $("#subject").css("margin-top","3%");
}
if (param.stuId && !param.stuId == 'undefined') {
    $("#_title").addClass("disPlayNone");
}
var vm = new Vue({
    el: "#app",
    data: {
        schyList:[],
        testPrdList:[],
        testDivList:[],
        subjtList:[],
        subjt_num1: '',
        stuTextH:'',
        stuTextDList:'',
        updateTimeString:'',
        schy:'',
        testType:'',
        testKind:'',
        schyDiv:''
    },
    updated:function(){
        $("input").keyup(function () {
            $(this).css("background-color","white");
        });
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + "/student/F10502/init",
                type: "get",
                data:{
                    id:　param.id,
                    stuId: param.stuId
                },
                datatype: "json",
                success: function (data) {
                    if(data.code!=0){
                        layer.alert(data.msg);
                    }else{
                        //1学年リスト
                        if(data.schyList){
                            vm.schyList=data.schyList;
                        }
                        //2テスト時期リスト
                        if(data.testPrdList){
                            vm.testPrdList=data.testPrdList;
                        }
                        //3テスト種別リスト
                        if(data.testDivList){
                            vm.testDivList=data.testDivList;
                        }
                        //4教科リストを取得し
                        if(data.subjtList){
                            vm.subjtList=data.subjtList;
                            if (vm.subjtList[0]) {
                                vm.subjt_num1 = vm.subjtList[0];
                                var str = '';
                                for (var i = 0; i < vm.subjtList.length; i++) {
                                    if (i==0) {
                                        str = str + '<option value="'+vm.subjtList[i].codCd+'" _color="'+vm.subjtList[i].codValue2+'" _src="'+vm.subjtList[i].codValue3+'"' +
                                            ' style="-webkit-appearance: none;background: '+vm.subjtList[i].codValue2+'" selected>'+vm.subjtList[i].codValue+'</option>';
                                    } else {
                                        str = str + '<option value="'+vm.subjtList[i].codCd+'" _color="'+vm.subjtList[i].codValue2+'" _src="'+vm.subjtList[i].codValue3+'"' +
                                            ' style="-webkit-appearance: none;background: '+vm.subjtList[i].codValue2+'">'+vm.subjtList[i].codValue+'</option>';
                                    }
                                }
                                $("#goalSelect_0").append(str);
                                $("#goalSelect_0").css("background", vm.subjt_num1.codValue2);
                                $("#goalSelect_0").prev("img").attr("src", vm.subjt_num1.codValue3);
                            }
                        }
                        //生徒テスト目標結果_ヘッダ情報
                        if(data.stuTextH){
                            vm.stuTextH=data.stuTextH;
                        }
                        //生徒テスト目標結果_明細情報List
                        if(data.stuTextDList){
                            vm.stuTextDList=data.stuTextDList;
                        }
                        //更新日時String
                        if(data.updateTimeString){
                            vm.updateTimeString=data.updateTimeString;
                        }
                        //学年
                        if(data.schy){
                            vm.schy=data.schy;
                        }
                        //学年
                        if(data.schyDiv){
                            vm.schyDiv=data.schyDiv
                        }
                        //テスト分類
                        if(data.testType){
                            vm.testType=data.testType;
                        }
                        //テスト種別
                        if(data.testKind){
                            vm.testKind=data.testKind;
                        }
                    }
                }
            })
        },
        toF10507:function(){
            window.location.href="./F10507.html?stuId=" + param.stuId;
        },
        toF10503:function(){
            window.location.href="./F10503.html?stuId=" + param.stuId;
        },
        cancelFn:function(){
            window.location.href="./F10507.html?stuId=" + param.stuId;
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
        //半角AND point のcheck
        isFloat:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if((48>str.charCodeAt(i)||str.charCodeAt(i)>57)&&str.charCodeAt(i)!=46){
                    flg=false;
                    break;
                }
            }
            return flg
        },
        submitFn:function () {
            //学年区分のチェック
            if($("#schyDiv").attr("desc") == ""){
                showMsg($.format($.msg.MSGCOMD0001, "学年"));
                return;
            }

            //テスト分類のチェック
            if($("#testPrd").attr("desc") == ""){
                showMsg($.format($.msg.MSGCOMD0001, "テスト分類"));
                return;
            }

            if(!$("#triggleDiv").hasClass("disNone")){
                //テスト種類区分のチェック
                if($("#testDiv").attr("desc") == ""){
                    showMsg($.format($.msg.MSGCOMD0001, "テスト種別"));
                    return;
                }
            }

            //年のチェック
            if ($("#testTgtY").attr("desc") == "") {
                showMsg($.format($.msg.MSGCOMD0001, "年"));
                $(".contentback").css("height",menuHeightt);
                return;
            }

            if ($("#testTgtY").attr("desc").length>4) {
                showMsg($.format($.msg.MSGCOMD0009, "年",'4'));
                return;
            }
            //年は半角数字以外
            if ($("#testTgtY").attr("desc")!=="" && !vm.isHalfAngle($("#testTgtY").attr("desc"))) {
                showMsg($.format($.msg.MSGCOMD0006, "年"));
                return;
            }

            //月のチェック
            if ($("#testTgtM").attr("desc") == "") {
                showMsg($.format($.msg.MSGCOMD0001, "月"));
                return;
            }
            if ($("#testTgtM").attr("desc").length >2) {
                showMsg($.format($.msg.MSGCOMD0009, "月",'2'));
                return;
            }
            //月は半角数字以外
            if($("#testTgtM").attr("desc")!="" && !vm.isHalfAngle($("#testTgtM").attr("desc"))){
                showMsg($.format($.msg.MSGCOMD0006, "月"));
                return;
            }


            var paramList=[];
            //教科ごとの目標点数はすべて未入力のflg
            var goalPointsFlg=true;
            //教科ごとの結果点数はすべて未入力のflg
            var resultPointsFlg=true;
            var formatFlg = true;
            var isReturn = false;
            $('.grade').eq(0).find("div.goal").each(function (index1, ele1) {
                $('.grade').eq(0).find("div.goal").each(function (index2, ele2) {
                    if ($(ele1).attr("subjtDiv") === $(ele2).attr("subjtDiv") && index1 != index2) {
                        isReturn = true;
                        return false;
                    }
                })
                if (isReturn) {
                    showMsg("重複した教科書を選ばないでください。");
                    return false;
                }
            });
            if (isReturn) {
                return;
            }
            $(".goal").find("input").each(function (i, dom) {
                if ($(this).val()) {
                    var para=JSON.parse("{}");
                    //id
                    para.id = param.id;
                    //生徒ID
                    para.stuId = param.stuId;
                    //学年区分
                    para.schyDiv=$("#schyDiv").attr("desc");
                    //テスト分類区分
                    para.testTypeDiv=$("#testPrd").attr("desc");
                    //テスト種別区分
                    if(!$("#triggleDiv").hasClass("disNone")) {
                        para.testKindDiv = $("#testDiv").attr("desc");
                    }else{
                        para.testKindDiv=null;
                    }
                    //テスト対象年
                    para.testTgtY=$("#testTgtY").attr("desc");
                    //テスト対象月
                    para.testTgtM=$("#testTgtM").attr("desc");
                    //排他チェックの更新日時
                    para.updateTimeString=vm.updateTimeString;
                    //教科区分
                    para.subjtDiv = $(".goal").eq(i).attr("subjtDiv");

                    //目標点数は半角数字以外で入力した場合
                    if($(".goal").eq(i).find("input").eq(0).val()!="" && !vm.isHalfAngle($(".goal").eq(i).find("input").eq(0).val())){
                        $(".goal").eq(i).find("input").eq(0).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0006, "目標点数"));
                        formatFlg = false;
                        return false;
                    }
                    //教科ごとの目標点数はすべて未入力のflg
                    if($(".goal").eq(i).find("input").eq(0).val()!=""){
                        goalPointsFlg=false;
                        //目標点数
                        para.goalPoints = $(".goal").eq(i).find("input").eq(0).val();
                    }

                    //結果満点は半角数字以外で入力した場合
                    if($(".result").eq(i).find("input").eq(0).val()!="" && !vm.isHalfAngle($(".result").eq(i).find("input").eq(0).val())){
                        showMsg($.format($.msg.MSGCOMD0006, "満点"));
                        $(".result").eq(i).find("input").eq(0).css("background-color",'red');
                        formatFlg = false;
                        return false;
                    }
                    //教科ごとの結果満点はすべて未入力のflg
                    if($(".result").eq(i).find("input").eq(0).val()!=""){
                        //結果満点
                        para.resultPerfectPoints = $(".result").eq(i).find("input").eq(0).val();
                    }

                    //結果点数は半角数字以外で入力した場合
                    if($(".result").eq(i).find("input").eq(1).val()!="" && !vm.isHalfAngle($(".result").eq(i).find("input").eq(1).val())){
                        $(".result").eq(i).find("input").eq(1).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0006, "結果点数"));
                        formatFlg = false;
                        return false;
                    }
                    //教科ごとの結果点数はすべて未入力のflg
                    if($(".result").eq(i).find("input").eq(1).val()!=""){
                        resultPointsFlg=false;
                        //結果点数
                        para.resultPoints = $(".result").eq(i).find("input").eq(1).val();
                    }

                    //結果順位は半角数字以外で入力した場合
                    if($(".result").eq(i).find("input").eq(2).val()!="" && !vm.isHalfAngle($(".result").eq(i).find("input").eq(2).val())){
                        $(".result").eq(i).find("input").eq(2).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0006, "順位"));
                        formatFlg = false;
                        return false;
                    }
                    if($(".result").eq(i).find("input").eq(2).val()!=""){
                        //結果順位
                        para.resultRank = $(".result").eq(i).find("input").eq(2).val();
                    }

                    //結果平均点は半角数字以外で入力した場合
                    if($(".result").eq(i).find("input").eq(3).val()!="" && !vm.isFloat($(".result").eq(i).find("input").eq(3).val())){
                        $(".result").eq(i).find("input").eq(3).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0006, "平均点"));
                        formatFlg = false;
                        return false;
                    }
                    //入力した平均点は整数5桁、少数１桁を超える場合
                    if($(".result").eq(i).find("input").eq(3).val() != "" && !/^([0-9]{1}\d{0,4})(?:\.\d)?$/.test($(".result").eq(i).find("input").eq(3).val())){
                        $(".result").eq(i).find("input").eq(3).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0015, "平均点","5","1"));
                        formatFlg = false;
                        return false;
                    }
                    if($(".result").eq(i).find("input").eq(3).val()!=""){
                        //結果平均点
                        para.resultAvg = $(".result").eq(i).find("input").eq(3).val();
                    }

                    //入力した偏差値は整数2桁、少数１桁を超える場合
                    if($(".result").eq(i).find("input").eq(4).val() != "" && !/^([0-9]{1}\d{0,1})(?:\.\d)?$/.test($(".result").eq(i).find("input").eq(4).val())){
                        $(".result").eq(i).find("input").eq(4).css("background-color",'red');
                        showMsg($.format($.msg.MSGCOMD0015, "平均点","2","1"));
                        formatFlg = false;
                        return false;
                    }
                    if($(".result").eq(i).find("input").eq(4).val()!=""){
                        //偏差値
                        para.devionValue = $(".result").eq(i).find("input").eq(4).val();
                    }

                    //5.2.3　当教科の結果点数、目標点数いずれかに入力する場合、当教科の両方とも入力必須です。
                    if($(".goal").eq(i).find("input").eq(0).val()!=""&&$(".result").eq(i).find("input").eq(1).val()=="" && param.id!=undefined){
                        showMsg($.format($.msg.MSGCOMN0055, $(".goal").eq(i).attr("subjtNm"),"結果点数","目標点数"));
                        $(".result").eq(i).find("input").eq(1).css("background-color",'red');
                        formatFlg = false;
                        return false;
                    }
                    if($(".goal").eq(i).find("input").eq(0).val()==""&&$(".result").eq(i).find("input").eq(1).val()!="" && param.id!=undefined){
                        showMsg($.format($.msg.MSGCOMN0055, $(".goal").eq(i).attr("subjtNm"),"結果点数","目標点数"));
                        $(".goal").eq(i).find("input").eq(0).css("background-color",'red');
                        formatFlg = false;
                        return false;
                    }

                    //結果点数、結果満点の任意入力する場合、両方とも入力必須です。
                    if($(".result").eq(i).find("input").eq(0).val()==""&&$(".result").eq(i).find("input").eq(1).val()!="" && param.id!=undefined){
                        showMsg($.format($.msg.MSGCOMN0055, $(".goal").eq(i).attr("subjtNm"),"結果点数","結果満点"));
                        $(".result").eq(i).find("input").eq(0).css("background-color",'red');
                        formatFlg = false;
                        return false;
                    }
                    if($(".result").eq(i).find("input").eq(0).val()!=""&&$(".result").eq(i).find("input").eq(1).val()=="" && param.id!=undefined){
                        showMsg($.format($.msg.MSGCOMN0055, $(".goal").eq(i).attr("subjtNm"),"結果点数","結果満点"));
                        $(".result").eq(i).find("input").eq(1).css("background-color",'red');
                        formatFlg = false;
                        return false;
                    }
                    paramList.push(para);
                }
            })

            if (!formatFlg) {
                return false;
            }

            //教科ごとの結果点数はすべて未入力 and 教科ごとの目標点数はすべて未入力
            if(resultPointsFlg && goalPointsFlg){
                showMsg($.format($.msg.MSGCOMN0050, "目標点数と結果点数"));
                return false;
            }
            $.ajax({
                url: ctxPath + "/student/F10502/update",
                data:JSON.stringify(paramList),
                traditional: true,
                type: 'POST',
                datatype: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                success: function (data) {
                    if(data.code!=0){
                        showMsg(data.msg);
                    }else{
                        window.location.href='./F10507.html?stuId=' + param.stuId;
                    }
                }
            })
        },
        getGoalClass:function (subjt_cd) {
            return subjt_cd + "_goalInput"
        },
        getActualClass: function (subjt_cd) {
            return subjt_cd + "_actualInput"
        },
        addResult: function (flg) {
            if ($("#resultSelect_" + number).find("option:selected").val() == 'blank') {
                showMsg("先に教科と目標点数を設定してください。");
                return;
            }
            number++;
            var optionArr = [];
            for(var key in vm.subjtList) {
                var count = 0;
                $(".goal").each(function () {
                    if ($(this).attr("subjtDiv") == vm.subjtList[key].codCd) {
                        count++;
                    }
                })
                if (count === 0) {
                    optionArr.push(vm.subjtList[key]);
                }
            }
            if (optionArr.length == 0) {
                showMsg("すべての教科書を追加しました。");
                return;
            }
            var num = "";
            for (var i = 0; i < vm.subjtList.length; i++) {
                for (var j = 0; j < optionArr.length; j++) {
                    if (vm.subjtList[i] === optionArr[j]) {
                        num = optionArr[j];
                        break;
                    }
                }
                if (num) {
                    break;
                }
            }
            var str = '';
            for (var i = 0; i < vm.subjtList.length; i++) {
                if (num && num === vm.subjtList[i]) {
                    str = str + '<option value="'+vm.subjtList[i].codCd+'" _color="'+vm.subjtList[i].codValue2+'" _src="'+vm.subjtList[i].codValue3+'"' +
                        ' style="-webkit-appearance: none;background: '+vm.subjtList[i].codValue2+'" selected>'+vm.subjtList[i].codValue+'</option>';
                } else {
                    str = str + '<option value="'+vm.subjtList[i].codCd+'" _color="'+vm.subjtList[i].codValue2+'" _src="'+vm.subjtList[i].codValue3+'"' +
                        ' style="-webkit-appearance: none;background: '+vm.subjtList[i].codValue2+'">'+vm.subjtList[i].codValue+'</option>';
                }
            }
            $('.grade').eq(0).append('<div class="contentDiv contentDivT goal">' +
                '<img src="" style="position: relative;left: -36%;width: 1.6vw;height: 1.6vw;margin-top: 0.4vw;">' +
                '<select name="subjt_list" id="goalSelect_'+number+'" class="sub_select" style="color: white;border: 0 !important;text-align:' +
                ' center;text-align-last: center;padding-left: 5%"' +
                ' onchange="selectChange(this)">'+str+'</select>' +
                '<input type="number" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" oninput="if(value.length>5)value=value.slice(0,5)" maxlength="5" placeholder="目標得点">'
                + '<p>点</p></div>');
            var color = $("#goalSelect_"+number).find("option:selected").attr("_color");
            var src = $("#goalSelect_"+number).find("option:selected").attr("_src");
            var text = $("#goalSelect_"+number).find("option:selected").text();
            $("#goalSelect_"+number).css("background", color);
            $("#goalSelect_"+number).prev("img").attr("src", src);
            $('.grade').eq(1).append('<div class="contentDiv contentDivT result">' +
                // '<select name="subjt_list" id="resultSelect_'+number+'" class="sub_select">'+str+'</select>' +
                '<div id="resultSelect_'+number+'" class="sub_select" style="background: '+$("#goalSelect_"+number).find("option:selected").attr("_color")+'"><img src="'+src+'" alt=""><span>'+text+'</span></div>' +
                '<input type="number" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" oninput="if(value.length>5)value=value.slice(0,5)" maxlength="5"' + ' placeholder="満点">' +
                '<p>点</p>' +
                '<input type="number" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" oninput="if(value.length>5)value=value.slice(0,5)" maxlength="5" placeholder="あなたの得点">' +
                '<p>点</p>' +
                '<input type="number" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" oninput="if(value.length>6)value=value.slice(0,6)" maxlength="6" placeholder="順位">' +
                '<p>位</p>' + '<input type="number" maxlength="7" placeholder="平均点">' +
                '<p>点</p>' +
                '<input type="number" maxlength="4"' + ' placeholder="偏差値"></div>');
            var subDiv = $("#goalSelect_" + number).val();
            var subNm = $("#goalSelect_" + number).find("option:selected").html();
            $("#goalSelect_" + number).parent().attr("subjtDiv", subDiv);
            $("#goalSelect_" + number).parent().attr("subjtNm", subNm);
            $("#resultSelect_" + number).find("option[value='"+subDiv+"']").attr("selected",true);
            $("#goalSelect_" + number).next("input").attr("id",subDiv+"_goalInput")
            $("#resultSelect_" + number).nextAll().eq(2).attr("id", subDiv+"_actualInput");
        }
    }
});

function selectChange(obj) {
    $("#message").removeClass("hasHeight");
    $("#message").css("display", "none");
    var subjtDiv = $(obj).val();
    var subjtNm = $(obj).find("option:selected").html();
    var color = $(obj).find("option:selected").attr("_color");
    var src = $(obj).find("option:selected").attr("_src");
    var text = $(obj).find("option:selected").text();
    var num = $(obj).attr("id").split("_")[1];
    $(obj).parent().attr("subjtDiv", subjtDiv);
    $(obj).parent().attr("subjtNm", subjtNm);
    $("#goalSelect_"+num).css("background", color);
    $("#goalSelect_"+num).prev("img").attr("src", src);
    $("#goalSelect_" + num).find("option").attr("selected",false);
    $("#goalSelect_" + num).find("option[value='"+subjtDiv+"']").attr("selected",true);
    $("#resultSelect_"+num).css("background", color);
    $("#resultSelect_"+num).find("img").attr("src", src);
    $("#resultSelect_"+num).find("span").text(text);
    // $("#resultSelect_" + num).find("option").attr("selected",false);
    // $("#resultSelect_" + num).find("option[value='"+subjtDiv+"']").attr("selected",true);
    $(obj).next("input").attr("id",subjtDiv+"_goalInput")
    $("#resultSelect_" + num).nextAll().eq(2).attr("id", subjtDiv+"_actualInput");
    var isReturn = false;
    $('.grade').eq(0).find("div.goal").each(function (index1, ele1) {
        $('.grade').eq(0).find("div.goal").each(function (index2, ele2) {
            if ($(ele1).attr("subjtDiv") === $(ele2).attr("subjtDiv") && index1 != index2) {
                isReturn = true;
                return false;
            }
        })
        if (isReturn) {
            showMsg($(ele1).attr("subjtNm") + "はもう選択されました。他の教科を選んでください。");
            return false;
        }
    });
    if (isReturn) {
        return;
    }
}

// function syncUp(id,value) {
//     var actual = id.split("_")[0] + "_actualInput";
//     $("#"+actual).val(value)
// }

window.onload = function (ev) {
    $("#iframe").contents().find("#grade_img").css('width','50%');
}
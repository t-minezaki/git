var v = getParam();
var vm = new Vue({
    el: "#rrapp",
    data: {
        stu: '',
        gendrList: '',
        updateStr: '',
        orgId: ''
    },
    mounted: function () {
        this.init();
        this.showData();
    },
    methods: {
        init: function () {
            var height = $(window).height();
            $('#retu').height(height * 0.045);
            $('#update').height(height * 0.045);

            $('.content-list').height(getRemainingHeight('infoMain', 'title', 'notice'));
        },
        isAngle: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (!(12543 > str.charCodeAt(i) && str.charCodeAt(i) > 12352)) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        showData: function () {
            $.ajax({
                url: ctxPath + "/student/F10000/init",
                type: "get",
                datatype: "json",
                success: function (data) {
                    if (data.code != 0) {
                        popMsg(data.msg);
                        return;
                    }
                    if (data.stuInfo) {
                        vm.stu = data.stuInfo;
                        if (data.stuInfo.birthdayString != undefined) {
                            var y = parseInt(data.stuInfo.birthdayString.split("-")[0]);
                            var m = parseInt(data.stuInfo.birthdayString.split("-")[1]);
                            var d = parseInt(data.stuInfo.birthdayString.split("-")[2]);
                            if (document.attachEvent) {
                                window.attachEvent("onload", dateStart(y, m, d));
                            } else
                                window.addEventListener('load', dateStart(y, m, d), false);
                        } else {
                            if (document.attachEvent) {
                                window.attachEvent("onload", dateStart(undefined, undefined, undefined));
                            } else
                                window.addEventListener('load', dateStart(undefined, undefined, undefined), false);
                        }

                    }
                    if (data.gendrList) {
                        vm.gendrList = data.gendrList;
                    }
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                    }
                    if (data.updateStr) {
                        vm.updateStr = data.updateStr;
                    }
                    if (data.orgId) {
                        vm.orgId = data.orgId;
                    }
                }
            })
        },
        saveOrUpdate: function () {
            //名前_姓のチェック
            if ($("input[name='stuFnm']").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "名前_姓"));
                return;
            }

            //入力した名前_姓は全角カナ以外の場合、エラーとする
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='stuFnm']").val())) {
                popMsg($.format($.msg.MSGCOMD0007, "名前_姓"));
                $("#stuFnm").focus();
                return;
            }

            //入力した名前_名は全角カナ以外の場合、エラーとする
            if ($("input[name='stuLnm']").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "名前_名"));
                return;
            }

            //名前_名の全角チェック
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='stuLnm']").val())) {
                popMsg($.format($.msg.MSGCOMD0007, "名前_名"))
                $("#stuLnm").focus();
                return;
            }

            //カナ_姓のチェック
            if ($("input[name='flnmKnNm']").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "名前_カナ姓"));
                return;
            }

            //入力したカナ_姓は全角カナ以外の場合、エラーとする
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='flnmKnNm']").val())) {
                popMsg($.format($.msg.MSGCOMD0007, "名前_カナ名"));
                $("#flnmKnNm").focus();
                return;
            }

            //カナ_名のチェック
            if ($("input[name='flnmKnLnm']").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "名前_カナ名"));
                return;
            }

            //入力したカナ_名は全角カナ以外の場合、エラーとする
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='flnmKnLnm']").val())) {
                popMsg($.format($.msg.MSGCOMD0007, "名前_カナ名"));
                $("#flnmKnLnm").focus();
                return;
            }

            //生年月日のチェック
            if ($("#year option:selected").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "生年月日_年"));
                return;
            }
            //生年月日のチェック
            if ($("#month option:selected").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "生年月日_月"));
                return;
            }
            //生年月日のチェック
            if ($("#day option:selected").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "生年月日_日"));
                return;
            }

            // //郵便番号_主番のチェック
            // if($("input[name='postcdMnum']").val()==""){
            //     popMsg($.format($.msg.MSGCOMD0001, "郵便番号_主番"));
            //     return;
            // }
            // if($("input[name='postcdMnum']").val().length<3){
            //     popMsg($.format($.msg.MSGCOMD0016, "郵便番号_主番","3"));
            //     return;
            // }
            // if(!/^\d{3}$/.test($("input[name='postcdMnum']").val())){
            //     popMsg($.format($.msg.MSGCOMD0006, "郵便番号_主番"));
            //     return;
            // }
            //
            // //郵便番号_枝番のチェック
            // if($("input[name='postcdBnum']").val()==""){
            //     popMsg($.format($.msg.MSGCOMD0001, "郵便番号_枝番"));
            //     return;
            // }
            // if($("input[name='postcdBnum']").val().length<4){
            //     popMsg($.format($.msg.MSGCOMD0016, "郵便番号_枝番","4"));
            //     return;
            // }
            // if(!/^\d{4}$/.test($("input[name='postcdBnum']").val())){
            //     popMsg($.format($.msg.MSGCOMD0006, "郵便番号_枝番"));
            //     return;
            // }
            //
            // //住所のチェック
            // if($("input[name='adr']").val()==""){
            //     popMsg($.format($.msg.MSGCOMD0001, "住所"));
            //     return;
            // }

            // //学校のチェック
            // if ($("#sch").val() == "") {
            //     popMsg($.format($.msg.MSGCOMD0001, "学校"));
            //     return;
            // }

            //性別のチェック
            if ($("#gendr option:selected").val() == "") {
                popMsg($.format($.msg.MSGCOMD0001, "性別"));
                return;
            }


            // //学年のチェック
            // if ($("#schy option:selected").val() == "") {
            //     popMsg($.format($.msg.MSGCOMD0001, "学年"));
            //     return;
            // }

            //パラメータ生成
            var param = {};
            //生徒基本マスタid
            param.id = vm.stu.id;
            param.updateStr = vm.updateStr;
            //名前_姓
            param.stuFnm = $("input[name='stuFnm']").val();
            //名前_名
            param.stuLnm = $("input[name='stuLnm']").val();
            //名前_カナ姓
            param.flnmKnNm = $("input[name='flnmKnNm']").val();
            //名前_カナ名
            param.flnmKnLnm = $("input[name='flnmKnLnm']").val();
            //生年月日
            param.birthdayString = $("#year option:selected").val() + '-' + $("#month option:selected").val() + '-' + $("#day option:selected").val();
            //性別
            param.gendrDiv = $("#gendr option:selected").val();
            //学校名
            param.schNm = $('#sch').val();
            $.ajax({
                type: "POST",
                url: ctxPath + "/student/F10000/submit",
                contentType: "application/json",
                data: JSON.stringify(param),
                success: function (r) {
                    if (r.code == 0) {
                        // layer.alert($.format($.msg.MSGCOMN0015, "基本情報"), {
                        //     closeBtn: 0,
                        //     btn: ["確認"],
                        //     btn1: function () {
                                window.location.href = "./F11004.html";
                        //     }
                        // });
                    } else {
                        popMsg(r.msg);
                    }
                }
            });
        },
        returnFn: function () {
            window.history.go(-1);
        }
    }

});
// F10002POP

var index = '';
$(".save_btn_left").click(function () {
    $(this).addClass("hasborder")
    $("#popId").removeClass("disNone")
    index = layer.open({
        id: 'f10002',
        type: 1,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        fixed: true,
        area: ['270px', '150px'],
        content: $("#popId"),
        success: function () {
            $(".layui-layer").addClass("layui-chage");
        },
        end: function () {
            $("#popId").addClass("disNone");
        }
    });
});
$(".cancelFn").click(function () {
    $(".save_btn_left").removeClass("hasborder");
    $("#popId").addClass("disNone");
    layer.close(index);
})

function uploadpImg(file, imgNum, select) {
    selectflg = select;
    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            if (reader.readyState === 2) {
                document.getElementById("headImg").src = evt.target.result;
                $(".save_btn_left").removeClass("hasborder");
                $("#popId").addClass("disNone");
                layer.close(index);
            }
        }
        reader.readAsDataURL(file.files[0]);
        document.getElementsByName("pic")[0].files = file.files;
        document.getElementsByName("pic").value = file.value;
    }
}

function dateStart(y, n, d) {
    MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    //年の下回りをする
    if (y == undefined) {
        y = new Date().getFullYear();
    }
    for (var i = (y - 50); i < (y + 50); i++) //今年は準、前50年、後50年
        document.date.year.options.add(new Option(" " + i, i));

    //月下に枠を引っ張る
    for (var i = 1; i < 13; i++)
        document.date.month.options.add(new Option(" " + i, i));

    document.date.year.value = y;
    if (n == undefined) {
        n = MonHead[new Date().getMonth()]
    }
    document.date.month.value = n;

    if (new Date().getMonth() == 1 && IsPinYear(y))
        n++;
    writeDay(MonHead[n - 1]); //期日が枠を引く
    if (d == undefined) {
        document.date.day.value = new Date().getDate();
    } else {
        document.date.day.value = d;
    }
}


function selectYear(str) //年の変化時に日付が変わった（主に閏平年を判断する）
{
    var monthvalue = document.date.month.options[document.date.month.selectedIndex].value;
    if (monthvalue == "") {
        var e = document.date.day;
        optionsClear(e);
        return;
    }
    var n = MonHead[monthvalue - 1];
    if (monthvalue == 2 && IsPinYear(str))
        n++;
    writeDay(n);
}

function selectMonth(str)   //月の変化が発生したときは
{
    var yearvalue = document.date.year.options[document.date.year.selectedIndex].value;
    if (yearvalue == "") {
        var e = document.date.day;
        optionsClear(e);
        return;
    }
    var n = MonHead[str - 1];
    if (str == 2 && IsPinYear(yearvalue))
        n++;
    writeDay(n);
}

function writeDay(n)   //条件によると日付の引台
{
    var e = document.date.day;
    optionsClear(e);
    for (var i = 1; i < (n + 1); i++)
        e.options.add(new Option(" " + i, i));
}

function IsPinYear(year)//閏の平年を判断する
{
    return (0 == year % 4 && (year % 100 != 0 || year % 400 == 0));
}

function optionsClear(e) {
    e.options.length = 1;
}

var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});
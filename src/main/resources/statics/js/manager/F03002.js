$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.18 - ($(window).width() / 100) * 2.6;
    $(".bottomDiv").css("height", srcHeight);
});
var oldSerialize;
var oldSerializeOfList;

function clickAll() {
    var flag = document.getElementById("all").checked;
    var allCheck = $(".clickOne");
    if (flag) {
        for (var i = 0; i < allCheck.length; i++) {
            allCheck[i].checked = true;
        }
        $('.one').parent().parent().css("background-color", 'white');
        $('.one').removeClass("back");
        $('.two').removeClass("back");
        $('.long').removeClass("back");
        $('.four').removeClass("back");
        $('.five').removeClass("back");
        $(".list").find('select').removeClass("back");
        $('.six').removeClass("back");

        $('.one').removeAttr("disabled");
        $('.two').removeAttr("disabled");
        $('.long').removeAttr("disabled");
        $('.four').removeAttr("disabled");
        $('.five').removeAttr("disabled");
        $(".list").find('select').removeAttr("disabled");
        $('.six').removeAttr("disabled");

    } else {
        for (var i = 0; i < allCheck.length; i++) {
            allCheck[i].checked = false;
        }
        $('.one').parent().parent().css("background-color", 'white');
        $('.one').addClass("back");
        $('.two').addClass("back");
        $('.long').addClass("back");
        $('.four').addClass("back");
        $('.five').addClass("back");
        $(".list").find('select').addClass("back");
        $('.six').addClass("back");

        $('.one').attr("disabled", "true");
        $('.two').attr("disabled", "true");
        $('.long').attr("disabled", "true");
        $('.four').attr("disabled", "true");
        $('.five').attr("disabled", "true");
        $(".list").find('select').attr("disabled", "true");
        $('.six').attr("disabled", "true");
    }
}
function isChecked() {
    var check = document.getElementById("all");
    var checkAll = true;
    var isAllCheck = $(".clickOne");
    for (var i = 0; i < isAllCheck.length; i++) {
        if (!isAllCheck[i].checked) {
            checkAll = false
        }
    }
    check.checked = checkAll;
}

//半角check
function isHalfAngle(str) {
    var flg = true;
    for (var i = 0; i < str.length; i++) {
        if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
            flg = false;
            break;
        }
    }
    return flg
}

var param = getParam();
//教科書単元編集画面
if (param.orgFlg == 1) {
    $(".popTitle").text("教科書単元編集画面");
    $("#create").hide();
}
//コピー作成して作成
else {
    $("#edit").hide();
    $(".popTitle").text("教科書コピー作成して作成画面");
}
var textId = param.textbId;
var vm = new Vue({
    el: '#app',
    data: {
        list: [],
        publisherList: '',
        mstLearnSeasnEntities: [],
        planTmList: [],
        schy: '',
        subjt: '',
        publisher: '',
        textbNm: '',
        schyDiv: '',
        publisherDiv: '',
        org: '',
        textbUpdatime: ''
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {
        $("select[name='planLearnSeasn']").each(function (i) {
            var a = $(this).attr("desc");
            var planLearnSeasnDisply = $(this).find("option:selected").attr("desc");
            $("#" + a).text(planLearnSeasnDisply);
        });
        $("select[name='planLearnSeasn']").change(function () {
            var a = $(this).attr("desc");
            var planLearnSeasnDisply = $(this).find("option:selected").attr("desc");
            $("#" + a).text(planLearnSeasnDisply);
        })
        $("input").keyup(function () {
            $("input").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
            $("#message").hide();
        })
        $("input").change(function () {
            $("input").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
            $("#message").hide();
        })
        $("select").change(function () {
            $("#message").hide();
            $("input").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
        })
        $(".clickOne").click(function () {
            var flag = $(this).is(':checked');
            if (!flag) {
                $(this).parent().parent().css("background-color", 'white');
                $(this).next().addClass("back");
                $(this).parent().parent().find('select').addClass("back");
                $(this).parent().parent().find('input').addClass("back");
                $(this).next().attr("disabled", "true");
                $(this).parent().parent().find('select').attr("disabled", "true");
                $(this).parent().parent().find('input[type=text]').attr("disabled", "true");
            } else {
                $(this).parent().parent().css("background-color", 'white');
                $(this).next().removeClass("back");
                $(this).parent().parent().find('select').removeClass("back");
                $(this).parent().parent().find('input').removeClass("back");
                $(this).next().removeAttr("disabled");
                $(this).parent().parent().find('select').removeAttr("disabled");
                $(this).parent().parent().find('input[type=text]').removeAttr("disabled");
            }
            isChecked();
        });
        oldSerialize = $("#form1").serialize();
        oldSerializeOfList = $("#seriForm").serialize();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F03002/init',
                type: 'GET',
                data: {
                    textbId: param.textbId
                },
                datatype: 'json',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        if (data.list) {
                            vm.list = data.list;
                            for (var i = 0; i < vm.list.length; i++) {
                                vm.list[i].textbPage1 = vm.list[i].textbPage.split('-')[0];
                                vm.list[i].textbPage2 = vm.list[i].textbPage.split('-')[1];
                            }
                            vm.schy = data.list[0].schy;
                            vm.schyDiv = data.list[0].schyDiv.replace(/\s+/g,"");
                            vm.subjt = data.list[0].subjt;
                            vm.subjtDiv = data.list[0].subjtDiv;
                            vm.publisher = data.list[0].publisher;
                            vm.publisherDiv = data.list[0].publisherDiv;
                            vm.textbNm = data.list[0].textbNm;
                        }
                        if (data.publisherList) {
                            vm.publisherList = data.publisherList;
                        }
                        if (data.mstLearnSeasnEntities) {
                            vm.mstLearnSeasnEntities = data.mstLearnSeasnEntities;
                        }
                        if (data.planTmList) {
                            vm.planTmList = data.planTmList;
                        }
                        if (data.textbUpdatime) {
                            vm.textbUpdatime = data.textbUpdatime;
                        }
                    }
                },
                error: function () {
                }
            })
        },
        toF03005: function () {
            var par = {};
            par.subjtDiv = vm.subjtDiv;
            if (param.orgFlg == 1) {
                par.schyDiv = vm.schyDiv;
            } else {
                par.schyDiv = $("#schy").val()
            }
            var index = layer.open({
                id: 'f10102',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['48vw', '40vh'],
                fixed: true,
                resize: false,
                content: ["../pop/F03005.html?" + $.param(par), 'no'],
                success: function (layero, index) {
                }
            });
        },
        toF03004: function () {
            var par = {};
            par.subjtDiv = vm.subjtDiv;
            //教科書単元編集画面
            if (param.orgFlg == 1) {
                par.schyDiv = vm.schyDiv;
            } else {
                par.schyDiv = $("#schy").val()
            }
            var index = layer.open({
                id: 'f10102',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['60%', '65%'],
                fixed: true,
                resize: false,
                content: ["../pop/F03004.html?" + $.param(par), 'no'],
                success: function (layero, index) {
                }
            });
        },
        orderCreate: function () {
            var n = 1;
            $("input[name='dispyOrder']").each(function (i, t) {
                if (!$(this).hasClass("back")) {
                    $(this).val(n);
                    vm.list[i].dispyOrder=n;
                    n++;
                }
            })
        },
        editFn: function () {
            var result = true;
            vm.formCheck();
            if ($("#form1").valid() == false) {
                return;
            }
            var list = [];
            if ($(".clickOne:checked").length == 0) {
                showMsg($.format($.msg.MSGCOMN0028, "単元"));
                return;
            }
            $("#submitTable").find("tr").each(function () {
                var param = {};
                //教科書ID
                param.textbId = textId;
                //教科区分
                param.subjtDiv = vm.subjtDiv;
                param.subjt = vm.subjt;
                param.schyDiv = vm.schyDiv;
                param.schy = vm.schy;
                //単元マスタId
                param.unitId = $(this).attr("unitId");
                if ($(this).find("input[name='tdtiId']")[0].checked){
                    //表示順番
                    if ($(this).find("input[name='dispyOrder']").eq(0).val() == '') {
                        showMsg($.format($.msg.MSGCOMD0001, "単元表示順"));
                        $(this).find("input[name='dispyOrder']").eq(0).css("background-color", 'red');
                        result = false;
                        return;
                    }
                    if (!isHalfAngle($(this).find("input[name='dispyOrder']").eq(0).val())) {
                        showMsg($.format($.msg.MSGCOMD0006, "単元表示順"));
                        $(this).find("input[name='dispyOrder']").eq(0).css("background-color", 'red');
                        result = false;
                        return;
                    }
                    //単元表示名
                    if ($(this).find("input[name='unitDispyNm']").val() == '') {
                        showMsg($.format($.msg.MSGCOMD0001, "項目表示名"));
                        $(this).find("input[name='unitDispyNm']").css("background-color", 'red');
                        result = false;
                        return;
                    }
                    //教科書ページ
                    if ($(this).find("input[name='textbPage']").eq(0).val() == '') {
                        showMsg($.format($.msg.MSGCOMD0001, "ページ前"));
                        $(this).find("input[name='textbPage']").eq(0).css("background-color", 'red');
                        result = false;
                        return;
                    }
                }
                param.dispyOrder = $(this).find("input[name='dispyOrder']").eq(0).val();
                //更新日時Str
                param.updateStr = $(this).attr("desc");
                //選択Flg
                if ($(this).find("input[name='tdtiId']").eq(0).is(':checked')) {
                    param.isCheck = 1;
                } else {
                    param.isCheck = 0;
                }
                param.tdtiId = $(this).find("input[name='tdtiId']").eq(0).val();
                param.unitDispyNm = $(this).find("input[name='unitDispyNm']").val();
                param.chaptNo = $(this).find("input[name='chaptNo']").val();
                param.sectnNo = $(this).find("input[name='sectnNo']").val();
                param.unitNo = $(this).find("input[name='unitNo']").val();
                param.textbNm = $("#textbNm").val();
                param.publisherDiv = $("#pub").val();
                param.textbUpdatimeStr = vm.textbUpdatime;
                param.chaptNm = $(this).find(".chaptNm").text();
                if ($(this).find("input[name='textbPage']").eq(1).val() == '') {
                    param.textbPage = $(this).find("input[name='textbPage']").eq(0).val();
                } else {
                    param.textbPage = $(this).find("input[name='textbPage']").eq(0).val() + "-" + $(this).find("input[name='textbPage']").eq(1).val();
                }
                //計画学習時期
                param.planLearnSeasn = $(this).find("select[name='planLearnSeasn']").val();
                param.planLearnSeasnId = $(this).find("select[name='planLearnSeasn']").find("option:selected").attr("planlearnseasnid");
                //計画学習時間（分）
                param.planLearnTm = $(this).find("select[name='planLearnTm']").val();
                list.push(param);
            });
            if (!result) {
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/F03002/edit',
                type: 'POST',
                data: JSON.stringify(list),
                datatype: 'json',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                traditional: true,
                success: function (data) {
                    if (data.code == 0) {
                        // var index = layer.alert($.format($.msg.MSGCOMN0014, "教科書"), {
                        //     closeBtn: 0,
                        //     yes: function () {
                        //         layer.close(index);
                                window.location.href = "./F03001.html";
                        //     }
                        // });
                    } else {
                        showMsg(data.msg.split("@")[0]);
                        $("#submitTable").find("tr").each(function () {
                            if (data.msg.split("@")[2]) {
                                if (data.msg.split("@")[1] == $(this).attr("unitId") && data.msg.split("@")[2] == $(this).find("select[name='planLearnSeasn']").eq(0).val()) {
                                    $(this).css("background-color", 'red');
                                }
                                ;
                            } else {
                                if (data.msg.split("@")[1] == $(this).find("input[name='tdtiId']").eq(0).val()) {
                                    $(this).css("background-color", 'red');
                                }
                                ;
                            }

                        });
                    }
                }
            });
        },
        createFn: function () {
            var result = true;
            vm.formCheck();
            if ($("#form1").valid() == false) {
                return;
            }

            var list = [];
            if ($(".clickOne:checked").length == 0) {
                showMsg($.format($.msg.MSGCOMN0028, "単元"));
                return;
            }
            $("#submitTable").find("tr").each(function () {
                var param = {};
                param.schyDiv = vm.schyDiv;
                param.subjtDiv = vm.subjtDiv;
                param.publisherDiv = $("#pub").val();
                param.textbNm = $("#textbNm").val();
                //単元マスタId
                param.unitId = $(this).attr("unitId");
                //表示順番
                if ($(this).find("input[name='dispyOrder']").eq(0).val() == '') {
                    showMsg($.format($.msg.MSGCOMD0001, "単元表示順"));
                    $(this).find("input[name='dispyOrder']").eq(0).css("background-color", 'red');
                    result = false;
                    return;
                }
                if (!isHalfAngle($(this).find("input[name='dispyOrder']").eq(0).val())) {
                    showMsg($.format($.msg.MSGCOMD0006, "単元表示順"));
                    $(this).find("input[name='dispyOrder']").eq(0).css("background-color", 'red');
                    result = false;
                    return;
                }
                param.dispyOrder = $(this).find("input[name='dispyOrder']").eq(0).val();
                //更新日時Str
                param.updateStr = $(this).attr("desc");
                //選択Flg
                if (!$(this).find("input[name='tdtiId']").eq(0).is(':checked')) {
                    return true;
                }
                //単元表示名
                if ($(this).find("input[name='unitDispyNm']").val() == '') {
                    showMsg($.format($.msg.MSGCOMD0001, "項目表示名"));
                    $(this).find("input[name='unitDispyNm']").css("background-color", 'red');
                    result = false;
                    return;
                }
                param.unitDispyNm = $(this).find("input[name='unitDispyNm']").val();
                param.chaptNo = $(this).find("input[name='chaptNo']").val();
                param.sectnNo = $(this).find("input[name='sectnNo']").val();
                param.unitNo = $(this).find("input[name='unitNo']").val();
                //教科書ページ
                if ($(this).find("input[name='textbPage']").eq(0).val() == '') {
                    showMsg($.format($.msg.MSGCOMD0001, "ページ前"));
                    $(this).find("input[name='textbPage']").eq(0).css("background-color", 'red');
                    result = false;
                    return;
                }
                if ($(this).find("input[name='textbPage']").eq(1).val() == '') {
                    param.textbPage = $(this).find("input[name='textbPage']").eq(0).val();
                } else {
                    param.textbPage = $(this).find("input[name='textbPage']").eq(0).val() + "-" + $(this).find("input[name='textbPage']").eq(1).val();
                }
                //計画学習時期
                param.planLearnSeasn = $(this).find("select[name='planLearnSeasn']").val();
                //計画学習時間（分）
                param.planLearnTm = $(this).find("select[name='planLearnTm']").val();
                list.push(param);
            });
            if (!result) {
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/F03002/create',
                type: 'POST',
                data: JSON.stringify(list),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                traditional: true,
                datatype: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        // var index = layer.alert($.format($.msg.MSGCOMN0014, "教科書"), {
                        //     closeBtn: 0,
                        //     yes: function () {
                        //         layer.close(index);
                                window.location.href = "./F03001.html";
                        //     }
                        // });
                    } else {
                        showMsg(data.msg);
                    }
                }
            });
        },
        formCheck: function () {
            $("#form1").validate({
                rules: {
                    pub: {
                        required: true
                    },
                    textbNm: {
                        required: true,
                        maxlength: 60
                    }
                },
                debug: true,
                onfocusout: false,
                onkeyup: false,
                messages: {
                    pub: {
                        required: $.format($.msg.MSGCOMD0001, "出版社名")
                    },
                    textbNm: {
                        required: $.format($.msg.MSGCOMD0001, "教科書名"),
                        maxlength: $.format($.msg.MSGCOMD0011, "教科書名", "60")
                    }
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        errorList[0].element.style.backgroundColor = "red";
                        showMsg(errorList[0].message);
                    }
                }
            })
        }
    }
});

function returnFn() {
    var nowSerialize = $("#form1").serialize();
    var nowSerializeOfList = $("#seriForm").serialize();
    if (nowSerialize != oldSerialize || nowSerializeOfList != oldSerializeOfList) {
        var index = layer.confirm($.msg.MSGCOMN0095, {
            btn: ["キャンセル", "確認"],
            btn1: function () {
                layer.close(index);
            },
            btn2: function () {
                window.location.href = "./F03001.html";
            }
        })
    } else {
        window.location.href = "./F03001.html";
    }
}
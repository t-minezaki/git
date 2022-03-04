var oldForm1Ser;
var pa = new getParam();
$(function () {
    var srcHeight = $(window).height() - $(window).width() * 0.26 - ($(window).width() / 100) * 2.6;
    $(".bottomDiv").css("height", srcHeight);
});
$(function () {
    oldForm1Ser = $("#form1").serialize();
});
var vm = new Vue({
    el: '.content',
    data: {
        // 単元List
        list: [],
        // 学年区分を取得
        schyDiv: [],
        // 教科区分を取得
        subjtDiv: [],
        // 出版社区分を取得
        publisherDiv: [],
        // 組織
        mstOrgEntity: [],
        mstLearnSeasnEntities: [],
        planTmList: [],
        StringList: [],
        form1Ser: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/f03006/init',
                data: {
                    schy: pa.schy
                },
                datatype: "json",
                success: function (data) {
                    if (data.mstOrgEntity) {
                        vm.mstOrgEntity = data.mstOrgEntity;
                    }
                    if (data.schyDiv.length != 0) {
                        for (var i = 0; i < data.schyDiv.length; i++) {
                            if (pa.schy == data.schyDiv[i].codCd) {
                                $("#schy").text(data.schyDiv[i].codValue);
                                break;
                            }
                        }
                    } else {
                        showMsg($.format($.msg.MSGCOMN0017, "学年"));
                        return false;
                    }
                    if (data.subjtDiv.length != 0) {
                        vm.subjtDiv = data.subjtDiv;
                    } else {
                        showMsg($.format($.msg.MSGCOMN0017, "教科"));
                        return false;
                    }
                    if (data.publisherDiv.length != 0) {
                        vm.publisherDiv = data.publisherDiv;
                    } else {
                        showMsg($.format($.msg.MSGCOMN0017, "出版社"));
                        return false;
                    }
                    if (data.crmLearnPrdIdNull) {
                        showMsg(data.crmLearnPrdIdNull);
                        $("#searchUnit").attr("disabled",true);
                        return false;
                    }
                    if (data.mstLearnSeasnEntitiesNull) {
                        showMsg(data.mstLearnSeasnEntitiesNull);
                        $("#searchUnit").attr("disabled",true);
                        return false;
                    }
                    if (data.mstLearnSeasnEntities) {
                        vm.mstLearnSeasnEntities = data.mstLearnSeasnEntities;
                    }
                    if (data.planTmList) {
                        vm.planTmList = data.planTmList;
                    }
                }
            });
        },
        orderCreate: function () {
            var n = 1;
            $("input[name='dispyOrder']").each(function (i, t) {
                if (!$(this).hasClass("back")) {
                    $(this).val(n);
                    vm.list[i].dispyOrder = n;
                    n++;
                }
            });
        },
        createFn: function () {
            var result = true;
            var list = [];
            if ($(".clickOne:checked").length == 0) {
                showMsg($.format($.msg.MSGCOMN0028, "単元"));
                return;
            }
            // 教科
            if ($("#subjt").val() == "") {
                $("#subjt").css("background-color", 'red');
                showMsg($.format($.msg.MSGCOMD0001, "教科"));
                return;
            }
            // 出版社名
            if ($("#pub").val() == "") {
                $("#pub").css("background-color", 'red');
                showMsg($.format($.msg.MSGCOMD0001, "出版社名"));
                return;
            }
            // 教科書名
            if ($("#textbNm").val() == "") {
                $("#textbNm").css("background-color", 'red');
                showMsg($.format($.msg.MSGCOMD0001, "教科書名"));
                return;
            }
            $("#submitTable").find("tr").each(function () {
                if ($(this).find("input[name='tdtiId']").eq(0).is(':checked')) {
                    var param = {};
                    param.schyDiv = pa.schy;
                    param.subjtDiv = $("#subjt").val();
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
                }
            });
            if (!result) {
                return;
            }
            $.ajax({
                url: ctxPath + '/manager/f03006/create',
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
        toF03005: function () {
            var index = layer.open({
                id: 'f03006',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['1000px', '400px'],
                fixed: true,
                resize: false,
                content: ["../pop/F03005.html", 'no'],
                success: function (layero, index) {
                }
            });
        },
        toF03004: function () {
            // 教科
            if ($("#subjt").val() == "") {
                $("#subjt").css("background-color", 'red');
                showMsg($.format($.msg.MSGCOMD0001, "教科"));
                return;
            }
            //教科書単元編集画面
            var index = layer.open({
                id: 'f03004',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['60%', '65%'],
                fixed: true,
                resize: false,
                content: ["../pop/F03004.html?" + "schyDiv=" + pa.schy + "&subjtDiv=" + $("#subjt").val()
                + "&pub=" + $("#pub").val() + "&textbNm=" + $("#textbNm").val(), 'no'],
                success: function (layero, index) {
                }
            });
        },
        toF03001: function () {
            if (oldForm1Ser == $("#form1").serialize()) {
                window.location.href = "./F03001.html";
            } else {
                var msg = layer.confirm($.msg.MSGCOMN0095, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['キャンセル', '確認'],
                    btn2: function () {
                        layer.close(msg);
                        window.location.href = "./F03001.html";
                    },
                    btn1: function () {
                        layer.close(msg);
                    }
                });
            }
        }
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
        });
        $("input").keyup(function () {
            $("input").css("background-color", "white");
            $("select").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
            $("#message").hide();
        });
        $("input").change(function () {
            $("input").css("background-color", "white");
            $("select").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
            $("#message").hide();
        });
        $("select").change(function () {
            $("input").css("background-color", "white");
            $("select").css("background-color", "white");
            $(".clickOne").parent().parent().css("background-color", 'white');
            $("#message").hide();
        });
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
        });
    }
});

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

//半角check
function isHalfAngle(str) {
    var flg = true;
    for (var i = 0; i < str.length; i++) {
        if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
            flg = false;
            break;
        }
    }
    return flg;
}

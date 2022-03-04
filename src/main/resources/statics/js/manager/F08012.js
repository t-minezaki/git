Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


Array.prototype.unique3 = function(){
    var res = [];
    var json = {};
    for(var i = 0; i < this.length; i++){
        if(!json[this[i]]){
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};

// サブウィンドウインデックスを取得する
var index = parent.layer.getFrameIndex(window.name);
var params = getParam();
var eventId = params.eventId;
var vm = new Vue({
    el: '#app',
    data: {
        SchyDivList: [],
        mstGrpEntityList: [],
        guardAndStudentList: [],
        searchGuardAndStudentList: [],
        type_select: 0
    },
    computed: {},
    methods: {
        timeFormat: function (time) {
            return dateFmt(time)
        }
    },
    mounted: function () {
        $("#schy_boxs").attr("disabled", true);
        // $("#grp_form_down li").css("display","none");
        $("#grp_form_down ul").on("click", "li", function () {

            $("#grp_form_up ul").first().append("<li content='" + $(this).attr("content") + "'>" + "<span onclick='grp_remove($(this))' class='btn_del'>&#10006</span>" + $(this).html() + "</li>");
            $(this).remove();
        });

        // $("#gender_form_down ul").on("click", "li", function () {
        //
        //     $("#gender_form_up ul").first().append("<li content='" + $(this).attr("content") + "'>" + "<span onclick='gender_remove($(this))' class='btn_del'>&#10006</span>" + $(this).html() + "</li>");
        //     $(this).remove();
        // });
    },
    updated: function () {
    }
});

function grp_remove(obj) {

    var parent = obj.parent();
    obj.remove();
    $("#grp_form_down ul").first().append("<li content='" + parent.attr("content") + "'>" + parent.text() + "</li>");
    parent.remove();
}

function gender_remove(obj) {

    var parent = obj.parent();
    obj.remove();
    $("#gender_form_down ul").first().append("<li content='" + parent.attr("content") + "'>" + parent.text() + "</li>");
    parent.remove();
}

// function randomColor()
// {
//     var r = Math.floor(Math.random() * 256);
//     var g = Math.floor(Math.random() * 256);
//     var b = Math.floor(Math.random() * 256);
//     var color = '#' + r.toString(16) + g.toString(16) + b.toString(16);
//     return color;
// }

$(function () {

    Array.prototype.remove = function(val) {
        for (var i = 0; i < this.length; i ++) {

            if (this[i].stuId == val.stuId && this[i].guardId == val.guardId) {

                this.splice(i, 1);
            }
        }
    };

    // ＞＞ボタン押下時
    $("#right_all").click(function () {

        var options = $("#select_left option");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                stuId: $(option).val(),
                afterUsrId: $(option).text().split(",")[0],
                guardId: $(option).attr("content"),
                stuNm: $(option).text().trim(),
                stuName: $(option).text().split(",")[1].trim(),
                schyDiv: $(option).attr("schy"),
                readFlg: $(option).attr("read_flg")
            };
            vm.guardAndStudentList.push(stuAndGuard);
            vm.searchGuardAndStudentList.remove(stuAndGuard);
        }
    });

    // ＞ボタン押下時
    $("#right").click(function () {

        var options = $("#select_left option:selected");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                stuId: $(option).val(),
                afterUsrId: $(option).text().split(",")[0],
                guardId: $(option).attr("content"),
                stuNm: $(option).text().trim(),
                stuName: $(option).text().split(",")[1].trim(),
                schyDiv: $(option).attr("schy"),
                readFlg: $(option).attr("read_flg")
            };
            vm.guardAndStudentList.push(stuAndGuard);
            vm.searchGuardAndStudentList.remove(stuAndGuard);
        }
    });

    // ＜＜ボタン押下時
    $("#left_all").click(function () {

        var options = $("#select_right option");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                stuId: $(option).val(),
                afterUsrId: $(option).text().split(",")[0],
                guardId: $(option).attr("content"),
                stuNm: $(option).text().trim(),
                stuName: $(option).text().split(",")[1].trim(),
                schyDiv: $(option).attr("schy"),
                readFlg: $(option).attr("read_flg")
            };
            vm.guardAndStudentList.remove(stuAndGuard);
            vm.searchGuardAndStudentList.push(stuAndGuard);
        }
    });

    // ＜ボタン押下時
    $("#left").click(function () {

        var options = $("#select_right option:selected");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                stuId: $(option).val(),
                afterUsrId: $(option).text().split(",")[0],
                guardId: $(option).attr("content"),
                stuNm: $(option).text().trim(),
                stuName: $(option).text().split(",")[1].trim(),
                schyDiv: $(option).attr("schy"),
                readFlg: $(option).attr("read_flg")
            };
            vm.guardAndStudentList.remove(stuAndGuard);
            vm.searchGuardAndStudentList.push(stuAndGuard);
        }
    });

    // 種類のラジオを切替る
    $("#r_classroom").click(function () {
        $("#schy_boxs").val("");
        // $("#gender_form_up").addClass("noFocus");
        // $("#gender_form_down").addClass("noFocus");
        $("#grp_form_up").addClass("noFocus");
        $("#grp_form_down").addClass("noFocus");

        // $("#gender_form_up ul li").addClass("noFocus");
        // $("#gender_form_down ul li").addClass("noFocus");

        $("#grp_form_down ul li").addClass("noFocus");
        $("#grp_form_up ul li").addClass("noFocus");
        // $("#schy_boxs").find("option:first").attr("selected", true);
        // $("#_blank").attr("selected", true);
        $("#schy_boxs").attr("disabled", true);
        $("#grp_form_up li").css("display", "none");
    });

    // 種類のラジオを切替る
    $("#r_gender").click(function () {

        $("#schy_boxs").val("");
        // $("#gender_form_up").removeClass("noFocus");
        // $("#gender_form_down").removeClass("noFocus");
        $("#grp_form_up").addClass("noFocus");
        $("#grp_form_down").addClass("noFocus");

        // $("#gender_form_up ul li").removeClass("noFocus");
        // $("#gender_form_down ul li").removeClass("noFocus");

        $("#grp_form_down ul li").addClass("noFocus");
        $("#grp_form_up ul li").addClass("noFocus");
        // $("#schy_boxs").find("option:first").attr("selected", true);
        // $("#_blank").attr("selected", true);
        $("#schy_boxs").attr("disabled", false);
        $("#grp_form_up li").css("display", "none");
    });

    // 種類のラジオを切替る
    $("#r_group").click(function () {

        $("#schy_boxs").val("");
        // $("#gender_form_up").addClass("noFocus");
        // $("#gender_form_down").addClass("noFocus");
        $("#grp_form_up").removeClass("noFocus");
        $("#grp_form_down").removeClass("noFocus");

        // $("#gender_form_up ul li").addClass("noFocus");
        // $("#gender_form_down ul li").addClass("noFocus");

        $("#grp_form_down ul li").removeClass("noFocus");
        $("#grp_form_up ul li").removeClass("noFocus");
        // $("#schy_boxs").find("option:first").attr("selected", true);
        // $("#_blank").attr("selected", true);
        $("#schy_boxs").attr("disabled", true);
        $("#grp_form_up li").css("display", "unset");
    });

    $("#close_btn").click(function () {

        parent.layer.close(index);
    });

    $.get(ctxPath + "/manager/F08012/init",
        {
            eventId: eventId
        },
        function (data) {

            vm.SchyDivList = data.SchyDivList;
            vm.mstGrpEntityList = data.mstGrpEntityList;
            if (data.code == 0) {

            } else {
                // 状態コードが0でない場合は警告情報が表示される
                parent.layer.alert(data.msg);
            }
        }
    );

    // 検索ボタン押下
    $("#search").click(function () {
        var grpIdList = [];
        var schyDivList = [];
        var list = $("#grp_form_up ul li");
        for (var i = 0; i < list.length; i++) {
            var li = list[i];
            grpIdList.push(parseInt($(li).attr("content")));
        }

        // list = $("#gender_form_up ul li");
        // for (var i = 0; i < list.length; i++) {
        //     var li = list[i];
        //     schyDivList.push($(li).attr("content"));
        // }
        var schyDiv = $("#schy_boxs").val();
        var radioType = vm.type_select;

        // 学年すでに選択した
        // if (radioType == '1' && schyDivList.length <= 0) {
        //
        //     var msg = $.format($.msg.MSGCOMN0017, "生徒");
        //     parent.layer.alert(msg);
        //     vm.searchGuardAndStudentList = [];
        //     return;
        // }
        // グループすでに選択した
        // if (radioType == '2' && grpIdList.length <= 0) {
        //
        //     var msg = $.format($.msg.MSGCOMN0017, "生徒");
        //     parent.layer.alert(msg);
        //     vm.searchGuardAndStudentList = [];
        //     return;
        // }

        // 検索
        var params = {
            radioType: radioType,
            schyDivList: schyDiv,
            eventId: eventId,
            grpIdList: JSON.stringify(grpIdList)
        };

        $.get(ctxPath + "/manager/F08012/search",
            {
                params: JSON.stringify(params)
            },
            function (data) {
                if (data.code == 0) {
                    vm.searchGuardAndStudentList = [];
                    var list = [];
                    for (var i = 0; i < data.searchGuardAndStudentList.length; i++) {
                        var item = data.searchGuardAndStudentList[i];
                        var flag = false;
                        for (var j = 0; j < vm.guardAndStudentList.length; j++) {
                            var item2 = vm.guardAndStudentList[j];
                            if (item.stuId == item2.stuId && item.guardId == item2.guardId) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            list.push(item);
                        }
                    }
                    if (list.length <= 0) {

                        var msg = $.format($.msg.MSGCOMN0017, "生徒");
                        parent.layer.alert(msg);
                    }
                    vm.searchGuardAndStudentList = list;
                } else {

                    vm.searchGuardAndStudentList = [];
                    // 状態コードが0でない場合は警告情報が表示される
                    parent.layer.alert(data.msg);
                }
            });
    });

    $("#btn_ok").click(function () {

        var stuAndGuardList = [];
        var options = $("#select_right option");
        for (var i = 0; i < options.length; i++) {

            var option = options[i];
            var stuAndGuard = {
                stuId: $(option).val(),
                afterUsrId: $(option).text().split(",")[0],
                guardId: $(option).attr("content"),
                stuNm: $(option).text().trim(),
                stuName: $(option).text().split(",")[1].trim(),
                schyDiv: $(option).attr("schy"),
                readFlg:$(option).attr("read_flg")
            };
            stuAndGuardList.push(stuAndGuard);
        }
        parent.layer.close(index);
        parent.vm.guardAndStudentList = [];
        parent.vm.guardAndStudentList = stuAndGuardList;
    });
});
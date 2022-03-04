window.onload = function () {
    var srcHeight = $(window).height() - $(window).width() * 0.13 - 62;
    $(".ui-jqgrid-bdiv").css("height", srcHeight);
};
var param = getParam();
var vm={};
if (!param.page) {
    param.page = 1;
}
if (param.stuId && param.stuId != "undefined") {
    $("#_title").addClass("disPlayNone")
}

function updateFn(id) {
    window.location.href = "./F10502.html?id=" + id + "&stuId=" + param.stuId;
}

function addFn() {
    window.location.href = "./F10502.html?stuId=" + param.stuId;
}

function deleteFn(id, updateTm) {
    var msg = layer.confirm($.format($.msg.MSGCOMN0013, "テスト目標結果"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            // var index = layer.getFrameIndex(window.name);
            layer.close(msg);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/student/F10507/delete',
                data: {
                    id: id,
                    updateTm: updateTm
                },
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        location.reload();
                        layer.close(msg);
                    }
                }
            });
        }
    })
}
$(function () {
    $("#jqGrid").jqGrid({
        url: ctxPath + '/student/F10507/init',
        datatype: "json",
        postData: {
            stuId: param.stuId
        },
        colModel: [
            {label: '学年', name: 'schyVal', index: 'cod_id', width: 80, key: true, sortable: false, align: "center"},
            {label: '分類', name: 'testTypeVal', index: 'cod_key', width: 80, sortable: false, align: "center"},
            {label: '種別', name: 'testKindVal', index: 'cod_descrp', width: 80, sortable: false, align: "center"},
            {
                label: '時期', name: '', index: 'cret_datime', width: 80, sortable: false,
                formatter: function (cell, option, object) {
                    return object.testTgtY + "年" + object.testTgtM + '月';
                }
            },
            {
                label: '操作', name: '', index: 'upd_usr_id', width: 80,sortable:false, formatter: function (cell, option, object) {
                    return "<button class='edit' onclick='updateFn(" + object.id + ")'>編集</button><button class='delete' onclick='deleteFn(" + object.id + ",\"" + object.updateTm + "\")'>削除</button>";
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 30,
        // rowList: [10, 30, 50],
        //rownumbers: false,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            if ($("#message") != null) {
                $("#message").hide();
            }
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        loadComplete: function (data) {
            if (data.code != 0) {
                showMsg(data.msg);
            } else {
                // $("#orgId").text(data.upOrg.orgId);
                // $("#managerId").text(data.managerId);
                // $("#levelShow").text(data.upOrg.level);
                // upLevel=data.upOrg.level;
                vm.stuNm=data.stuNm;
                $(".prev_jqGridPager").text(" ＜ 前へ");
                $(".first_jqGridPager").text(" ｜＜ 1");
                $(".next_jqGridPager").text("次へ ＞ ");
                $(".last_jqGridPager").text(data.page.totalCount+" ＞｜ ");
            }
        }
    });
});

function isshow(scr) {
    var menuHeight = window.screen.availHeight - 340;
    var allH = scr * 40
    if (allH < menuHeight) {
        $(".list").find('thead').find('div').css("padding-right", "0")
    } else {
        $(".list").find('thead').find('div').css("padding-right", "4px")
    }
}

// var scrWid = $(window).width() * 0.155;
// var menuHeight = $(window).height() - scrWid - 50;
// $(".list").find('tbody').find('div').css("height", menuHeight);
$(".title_li_one").click(function () {
    //var currentPage = parseInt($(".activP").html());
    $(".pageTest").find("a").removeClass("activP");
    $(".pageTest").find("a").eq(0).addClass("activP");
    vue.getInfo('1');
});
$(".title_li_end").click(function () {
    //window.location.href = "./F10503.html";
});
$("#add").click(function () {
    window.location.href = "./F10502.html?stuId=" + param.stuId;
});
$("#result").click(function () {
    window.location.href = "F10503.html?stuId=" + param.stuId;
});
$(".prev_jqGridPager").click(function () {
    $("#prev_jqGridPager").click();
});
$(".first_jqGridPager").click(function () {
    $("#first_jqGridPager").click();
});
$(".next_jqGridPager").click(function () {
    $("#next_jqGridPager").click();
});
$(".last_jqGridPager").click(function () {
    $("#last_jqGridPager").click();
});

window.onload = function (ev) {
    $("#iframe").contents().find("#grade_img").css('width','50%');
}

if(getCookie("equipment") !== "phone"){
    $("#head").css("height","100%");
    $(".li").css("width","auto");
    $("#li").css("position","absolute").css("margin-top","31vh").css("margin-left","21vw");
    $("#head > li").css("width","auto").css("float","left");
    $("#classRoom").css("width","100%");
    $("#classRoom_p").css("font-size","15px").css("margin-left","1vw").css("margin-top","10vh");
    $("#orgName").css("display","inline-block").css("font-size","2vw").css("font-weight","bold").css("margin-left","1vw").css("margin-top","20vh");
    $("#time").css("font-size","2vw").css("font-weight","bold").css("margin-top","20vh");
    $("#p").css("width","auto").css("float","left").css("font-size","15px").css("margin-top","20vh");
    $("#reload_container").css("position","absolute").css("top","50%").css("transform","translateY(-50%)").css("right","55px");
    $("#logout").css("position","absolute").css("top","50%").css("transform","translateY(-50%)");
}
var param = getParam();

function gettime() {
    var d = new Date();
    var day = new Date().getDay();
    if (day == 1) {
        day = '（月）';
    } else if (day == 2) {
        day = '（火）';
    } else if (day == 3) {
        day = '（水）';
    } else if (day == 4) {
        day = '（木）';
    } else if (day == 5) {
        day = '（金）';
    } else if (day == 6) {
        day = '（土）';
    } else if (day == 0) {
        day = '（日）';
    }
    var hms = d.Format('M/d') + day + ' ' + d.Format("HH:mm") ;
    $("#time").text(hms);
    // setTimeout('gettime()', 1000);
}

var roleDiv;
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "S+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "s": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
$(function () {
    if (getCookie("key") === 'PUSHAPI'){
        $("#logout").css("display","none");
    }
    $.ajax({
        url: ctxPath + '/common/F40011/init',
        dataType: 'json',
        type: 'GET',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                roleDiv = data.roleDiv;
                $("#hidden").val(data.roleDiv);
                if (data.orgName) {
                    $("#orgName").text(getOrgName(data.orgName));
                }
                if (data.unreadCount != 0) {
                    $(".haveMess").text(data.unreadCount);
                    $(".haveMess").show();
                } else {
                    $(".haveMess").hide();
                }
                if (data.roleDiv == 2) {
                    $("#other_three").css("pointer-events", "none").removeAttr('onclick');
                }
                $("#changeOrg").css("display", data.disa?"none":"block");
            }
        }
    });
    $("li").click(function () {
        var index = $(this).index();
        var indexx = $(this).index() + 1;
        if (roleDiv == 1) {
            var srcpng = '../img/bottomPngs6.0/bottompng' + indexx + 'fill.png';
            for (var i = 0; i < $("li").length; i++) {
                var j = i + 1;
                $("li").eq(i).find("img").attr("src", "../img/bottomPngs6.0/bottompng" + j + ".png");
            }
            $("li").eq(index).find('img').attr("src", srcpng);
            $("li").find("p").removeClass("green");
            $(this).find("p").addClass("green");
        }
    });
    gettime();
    isActive();
});

function isActive() {

    $("li").find("p").removeClass("green");
    if (param['id'] == 'F21017') {
        for (var i = 0; i < $("li").length; i++) {
            if (i != 0) {
                $("li").eq(i).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + ".png");
            } else {
                $("li").eq(0).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + "fill.png");
                $("li").eq(0).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if (param['id'] == 'F21024') {
        for (var i = 0; i < $("li").length; i++) {
            if (i != 1) {
                $("li").eq(i).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + ".png");
            } else {
                $("li").eq(1).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + "fill.png");
                $("li").eq(1).find("p").eq(0).addClass("green");
            }
        }
        return;
    }

    if (param['id'] == 'F09018') {
        for (var i = 0; i < $("li").length; i++) {
            if (i != 2) {
                $("li").eq(i).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + ".png");
            } else {
                $("li").eq(2).find("img").eq(0).attr("src", "../img/bottomPngs6.0/bottompng" + (i + 1) + "fill.png");
                $("li").eq(2).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
}

function reload() {
    window.parent.location.reload();
}

$("#other_three").click(function () {
    if ($("#hidden").val() != 1) {
        var index = parent.layer.confirm($.msg.MSGCOMN0149, {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            btn1: function () {
                parent.layer.close(index);
            }
        });
    } else {
        window.parent.location.href = '../manager/F09018.html';
    }
});

function openUrl(url) {
    if (url == "logout") {
        parent.layer.confirm("ログアウトしますか？", {
            btn: ['キャンセル', '確認'], title: "確認", closeBtn: false, btn2: function () {
                window.top.location.href = ctxPath + "/logout";
                return false;
            }
        });
    } else {
        parent.layer.confirm($.msg.MSGCOMN0004, {}, function () {
            window.top.location.href = ctxPath + "/student/" + url;
        });
    }
}
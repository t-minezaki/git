var param = getParam();
var startPageDate = param.startDate;
var endPageDate = param.endDate;
var aclick = $(".plan_time").find('a');
aclick.removeClass("active");
if(startPageDate == undefined || endPageDate == '' || startPageDate == 'null' || startPageDate == 'undefined'){
    for (var i = 0; i < aclick.length; i++) {
        if (aclick.eq(i).attr("id") == param.flag) {
            aclick.eq(i).addClass("active");
        }
    }
    if (param.flag == 'undefined') {
        aclick.eq(0).addClass("active");
    }
}
$(function () {
    var winH = $(window).height();
    $(".content").css("height", winH);
    $("#week").click(function () {
        window.location.href = "#tab-1";
    });
    $("#month").click(function () {
        window.location.href = "#tab-2";
    });
    $("#year").click(function () {
        window.location.href = "#tab-3";
    });
    $(".renturnBtn").click(function () {
        window.location.href = "./F10401.html?type=" + $(".active").attr("id") + "&startDay=" + startPageDate + "&endDay=" + endPageDate;
    });
    var aclick = $(".plan_time").find('a');
    aclick.click(function () {
        aclick.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).attr("id");
        vm.getInfo(0,dateType, param.subjtDiv);
    });
});

var type = param.type;

function showF10402Data(init,type, subjtDiv, startDate, endDate) {
    var data;
    var url=window.location.href;
    if (startDate == null || endDate == null) {
        data = {
            init:init,
            type: type,
            subjtDiv: subjtDiv,
            url:url
        };
    } else {
        startPageDate=startDate;
        endPageDate=endDate;
        data = {
            init:init,
            type: type,
            subjtDiv: subjtDiv,
            getStartDate: startDate,
            getEndDate: endDate,
            url:url
        };
    }
    $.ajax({
        url: ctxPath + '/student/F10402/init',
        data: data,
        type: 'get',
        datatype: 'json',
        success: function (data) {
            if (data.code == 0) {
                vm.f10402DtoList = data.f10402DtoList;
            }
        }
    })
}

function getPrd() {
    var index = layer.open({
        id: 'f40005',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['340px', '340px'],
        fixed: true,
        resize: false,
        content: ["../common/F40005.html?id=f10402&startDate="+startPageDate+"&endDate="+endPageDate, 'no'],
        success: function (layero, index) {
        },
    });
}

var vm = new Vue({
    el: "#pageF10402",
    data: {
        subjtNm: '',
        f10402DtoList: [],
    },
    mounted: function () {
        if (param.flag == 'undefined') {
            this.getInfo(1,'week', param.subjtDiv, param.startDate, param.endDate);
        } else {
            this.getInfo(1,param.flag, param.subjtDiv, param.startDate, param.endDate);
        }
    },
    methods: {
        getInfo: function (init,flag, subjtDiv, startDate, endDate) {
            var url=window.location.href;
            $.ajax({
                url: ctxPath + '/student/F10402/init',
                type: 'GET',
                data: {
                    init:init,
                    subjtDiv: subjtDiv,
                    flag: flag,
                    getStartDate: startDate,
                    getEndDate: endDate,
                    url:url
                },
                datatype: 'json',
                success: function (data) {
                    if (data.subjtNm) {
                        vm.subjtNm = data.subjtNm;
                    }
                    if (data.f10402DtoList) {
                        vm.f10402DtoList = data.f10402DtoList;
                    }
                }
            })
        }
    }
});

$(function () {
    var menlwidth = $(document).width() / 10 * 6;
    $(".border_div").css('width', menlwidth)
    var html = '';
    for (var i = 0; i < 10; i++) {
        var deg = i * 36;
        html += '<div class="border_s" style="transform: rotate(' + deg + 'deg)">' +
            '<div class="border_y"></div>' +
            '</div>'
    }
    $(".border_con").html(html)
});

window.onload = function (ev) {
    $("#iframe").contents().find("#status_img").css('width','50%');
}
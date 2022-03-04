var param = getParam();

var startPageDate = param.startDate;
var endPageDate = param.endDate;
var type = param.type;
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
        window.location.href = "./F20008.html?type=" + $(".active").attr("id") + "&startDay=" + startPageDate + "&endDay=" + endPageDate;
    });
    var aclick = $(".plan_time").find('a');
    aclick.click(function () {
        aclick.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).attr("id");
        vm.getInfo(dateType, param.subjtDiv);
    });
});

function showF20009Data(type, subjtDiv, startDate, endDate) {
    var data;
    if (startDate == null || endDate == null) {
        data = {
            type: type,
            subjtDiv: subjtDiv
        };
    } else {
        startPageDate = startDate;
        endPageDate = endDate;
        data = {
            type: type,
            subjtDiv: subjtDiv,
            getStartDate: startDate,
            getEndDate: endDate
        };
    }
    $.ajax({
        url: ctxPath + '/manager/F20009/init',
        data: data,
        type: 'get',
        datatype: 'json',
        success: function (data) {
            if (data.code == 0) {
                vm.f20009DtoList = data.f20009DtoList;
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
        content: ["../common/F40005.html?id=f20009&startDate=" + startPageDate + "&endDate=" + endPageDate, 'no'],
        success: function (layero, index) {
        },
    });
}

var vm = new Vue({
    el: "#pageF20009",
    data: {
        subjtNm: '',
        f20009DtoList: [],
    },
    updated: function () {
    },
    mounted: function () {
        if (param.flag == 'undefined') {
            this.getInfo('week', param.subjtDiv, param.startDate, param.endDate);
        } else {
            this.getInfo(param.flag, param.subjtDiv, param.startDate, param.endDate);
        }
    },
    methods: {
        getInfo: function (flag, subjtDiv, startDate, endDate) {
            $.ajax({
                url: ctxPath + '/manager/F20009/init',
                type: 'GET',
                data: {
                    subjtDiv: subjtDiv,
                    flag: flag,
                    getStartDate: startDate,
                    getEndDate: endDate
                },
                datatype: 'json',
                success: function (data) {
                    if (data.subjtNm) {
                        vm.subjtNm = data.subjtNm;
                    }
                    if (data.f20009DtoList) {
                        vm.f20009DtoList = data.f20009DtoList;
                    }
                    if (data.mentorNm) {
                        $("#mentorNm").text(data.mentorNm);
                    }
                    if (data.stuNm){
                        $("#studentNm").text(data.stuNm);
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
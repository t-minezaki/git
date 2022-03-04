var url = window.location.href;
var param = getParam();
var startPageDate = param.startDate;
var endPageDate = param.endDate;
var aclick = $(".time_tab").find('li');
aclick.removeClass("active");
$(function () {
    var docHeight = $('body').height();
    $(".content").css("height", docHeight);
    var aclick = $(".time_tab").find('li');
    $("#week").click(function () {
        window.location.href = "#tab-1";
    });
    $("#month").click(function () {
        window.location.href = "#tab-2";
    });
    $("#year").click(function () {
        window.location.href = "#tab-3";
    });
    if (startPageDate != null && endPageDate != null && startPageDate != 'null' && endPageDate != 'null') {
        aclick.removeClass("active");
    }
    $(".return").click(function () {
        window.location.href = "./F30104.html?type=" + $(".active").find("a").eq(0).attr("id") + "&startDay=" + startPageDate + "&endDay=" + endPageDate;
    });
    if (startPageDate == null || endPageDate == 'null' || startPageDate == '') {
        for (var i = 0; i < aclick.length; i++) {
            if (aclick.eq(i).find("a").eq(0).attr("id") == param.flag) {
                aclick.eq(i).addClass("active");
            }
        }
        if (param.flag == 'undefined') {
            aclick.eq(0).addClass("active");
        }
    }
    aclick.click(function () {
        aclick.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).find("a").eq(0).attr("id");
        // vu.getInfo(0,dateType, param.subjtNm);
        vu.getInfo(0,dateType, param.subjtDiv);
    });
    $("#learnStatus").click(function () {
        window.location.href = "./F30104.html";
    })
});

var type = param.type;

function showF30105Data(dataFlg,type, subjtDiv, startDate, endDate) {
    var data;
    if (startDate == null || endDate == null) {
        data = {
            url:url,
            dataFlg:dataFlg,
            type: type,
            subjtDiv: param.subjtDiv
        };
    } else {
        startPageDate = startDate;
        endPageDate = endDate;
        data = {
            url:url,
            dataFlg:dataFlg,
            type: type,
            subjtDiv: subjtDiv,
            getStartDate: startDate,
            getEndDate: endDate
        };
    }
    $.ajax({
        url: ctxPath + '/guard/F30105/Init',
        data: data,
        type: 'get',
        datatype: 'json',
        success: function (data) {
            if (data.code == 0) {
                vu.f30105DtoList = data.f30105DtoList;
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
        content: ["../common/F40005.html?id=f30105&startDate=" + startPageDate + "&endDate=" + endPageDate, 'no'],
        success: function (layero, index) {
        },
    });
}

var vu = new Vue({
    el: ".content",
    data: {
        subjtDiv: '',
        f30105DtoList: [],
        subjtNm:''
    },
    mounted: function () {
        if (param.flag == 'undefined') {
            this.getInfo(1,'week', param.subjtDiv, param.startDate, param.endDate);
        } else {
            this.getInfo(1,param.flag, param.subjtDiv, param.startDate, param.endDate);
        }
    },
    methods: {
        getInfo: function (dataFlg,flag, subjtDiv, startDate, endDate) {
            $.ajax({
                url: ctxPath + '/guard/F30105/Init',
                type: 'GET',
                data: {
                    url:url,
                    dataFlg:dataFlg,
                    subjtDiv: subjtDiv,
                    subjtNm:param.subjtNm,
                    flag: flag,
                    getStartDate: startDate,
                    getEndDate: endDate
                },
                datatype: 'json',
                success: function (data) {
                    if (data.subjtNm) {
                        vu.subjtNm = data.subjtNm;
                    }
                    if (data.f30105DtoList) {
                        vu.f30105DtoList = data.f30105DtoList;
                    }
                }
            })
        },
    }
});

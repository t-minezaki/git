var param = getParam();
var degree = {};
var myChart1;
var myChart2;
var myChart3;
var timeTwo = [];
var timeOne = [];
var subjtNm = '';
var dataIndex = 0;
var subjtDiv = [];
var index_flag = 0;//重複修正
var type = param.type;
var vm = {};
//時期指定
var startPageDate = param.startDay;
var endPageDate = param.endDay;
var colors = [];
$(function () {
    var winH = $(window).height();
    $(".content").css("height", winH);
    var aclick = $(".plan_time").find('a');
    if (startPageDate == undefined || endPageDate == '' || startPageDate == 'null' || startPageDate == 'undefined') {
        if (type != null && type != 'undefined') {
            aclick.removeClass("active");
            for (var i = 0; i < aclick.length; i++) {
                if (aclick.eq(i).attr("id") == type) {
                    aclick.eq(i).addClass("active");
                }
            }
            showF10401Data(1,type);
        } else {
            aclick.eq(0).addClass("active");
            showF10401Data(1,"day");
        }
    } else {
        showF10401Data(1,"day", '', startPageDate, endPageDate);
    }

    aclick.click(function () {
        aclick.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).attr("id");
        showF10401Data(0,dateType);
        $("#subjtDiv").find("option").eq(0).attr("selected", "selected");
        startPageDate = null;
        endPageDate = null;
    });
    $("#subjtDiv").change(function () {
        if (startPageDate != null && endPageDate != null) {
            showF10401Data(0,'', $("#subjtDiv").val(), startPageDate, endPageDate);
        } else {
            showF10401Data(0,$(".active").attr("id"), $("#subjtDiv").val());
        }
    })
});

/*
* init:0--非初期，1--初期
* */
function showF10401Data(init,type, subjtDiv, startDate, endDate) {
    var data;
    if(!subjtDiv){//教科未指定の場合、フラッグがリセットされます
        index_flag = 0;
    }
    var url=window.location.href;
    if (startDate == null || endDate == null) {
        data = {
            init:init,
            type: type,
            subjtDiv: subjtDiv,
            url:url
        };
    } else {
        data = {
            init:init,
            type: type,
            subjtDiv: subjtDiv,
            startDate: startDate,
            endDate: endDate,
            url:url
        };
    }
    $.ajax({
        url: ctxPath + '/student/F10401',
        data: data,
        type: 'get',
        datatype: 'json',
        success: function (data) {
            degree = data.degree;
            timeOne = data.timeCircle;
            timeTwo = data.timeHorizontal;
            type = data.type;
            $("#stuplantm").text((timeOne.stuplantm / 60).toFixed(2));
            $("#perftm").text((timeOne.perftm / 60).toFixed(2));
            // if (subjtDiv == null || subjtDiv == "") {
            //     getDegree();
            // }
            if (data.timeCircle2){
                var timeCircle2 = data.timeCircle2;
                $('#change_time_container').show();
                var change = (timeOne.perftm - timeCircle2.perftm) / 60;
                if (change >= 0){
                    $("#comparetm").text(change.toFixed(2));
                    $("#updown").text('UP');
                }else {
                    $("#comparetm").text((-change).toFixed(2));
                    $("#updown").text('DOWN');
                }
            }else {
                $('#change_time_container').hide();
            }
            if (data.startDate != null && data.startDate != undefined) {
                startPageDate = data.startDate;
            }
            if (data.endDate != null && data.endDate != undefined) {
                endPageDate = data.endDate;
            }
            if (data.stuNm) {
                vm.stuNm = data.stuNm;
            }
            if (data.colors){
                colors = data.colors;
            }
            getDegree();
            getTimeOne();
            getTimeTwo();
            if (data.degree) {
                // subjtDiv = data.degree;
                // if (index_flag == 0) {
                $("#subjtDiv option").remove();
                $("#subjtDiv").append("<option value=''>選択してください。</option>");
                var subjtDivList = data.degree;
                for (var i = 0; i < subjtDivList.length; i++) {
                    if (subjtDiv && subjtDiv === subjtDivList[i].subjtdiv){
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "' selected>" + subjtDivList[i].subjtn + "</option>");
                    }else {
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "'>" + subjtDivList[i].subjtn + "</option>");
                    }
                }
            }
        }
    })
}

function getDatePrd() {
    var index = layer.open({
        id: 'f10401',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['340px', '400px'],
        fixed: true,
        resize: false,
        content: ["../common/F40005.html?id=f10401&startDate=" + startPageDate + "&endDate=" + endPageDate, 'no'],
        success: function (layero, index) {
        },
    });
}

function getDegree() {
    if (myChart1 != null && myChart1 != "" && myChart1 != undefined) {
        myChart1.dispose();
    }
    myChart1 = echarts.init(document.getElementById('degree'));
    var xAxisData = [];
    var titleData = [];
    var seriesData = [];
    var seriesData0 = [];
    var seriesData1 = [];
    var seriesData2 = [];
    var seriesData3 = [];
    var seriesData4 = [];
    var flag = 'day';
    var maxValue = 600;
    var allLev = '';

    degree.forEach(function (item) {
        xAxisData.push(item.subjtn);
        seriesData0.push(item.lev0);
        seriesData1.push(item.lev1);
        seriesData2.push(item.lev2);
        seriesData3.push(item.lev3);
        seriesData4.push(item.lev4);
        subjtDiv.push(item.subjtdiv);
        allLev = item.lev0 + item.lev1 + item.lev2 + item.lev3 + item.lev4;
        maxValue = maxValue <= allLev ? allLev : maxValue;
    });
    var seriesDatas = {
        '0': seriesData0,
        '1': seriesData1,
        '2': seriesData2,
        '3': seriesData3,
        '4': seriesData4
    };
    colors.forEach(function (item) {
        titleData.push(item.codValue);
        seriesData.push({
            name: item.codValue,
            type: 'bar',
            stack: 'lev',
            barMaxWidth: '40',
            itemStyle: {
                normal: {color: item.codValue2, label: {show: false}}
            },
            data: seriesDatas[item.codCd],
        });
    });
    subjtNm = xAxisData;
    interval = Math.ceil(maxValue / 300) * 60;
    var option = {
        legend: {
            data: titleData
        },
        calculable: true,
        grid: {y: 80, y2: 30, x: 55, x2: 20},
        xAxis: [
            {
                type: 'category',
                data: xAxisData,
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#CCC'
                    }
                },
                axisLabel: {
                    textStyle: {
                        color: '#333',
                        fontSize: 10
                    },
                    rotate: -20
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: function (value) {
                        return value / 60 + '時間';
                    }
                },
                min: 0,
                max: interval * 5,
                splitNumber: 5,
                interval: interval,
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                }
            }
        ],
        // dataZoom: {
        //     show: true,
        //     realtime: true,
        //     bottom: -1,
        //     type:'inside',
        //     start: 0,
        //     end: degree.length > 5 ? (5/degree.length) * 100 : 100
        // },
        series: seriesData
    };
    myChart1.setOption(option);
    if (option && typeof option === "object") {
        myChart1.setOption(option, true);
        //myChartクリック事件
        myChart1.on('click', function (params) {
            dataIndex = params.dataIndex;
            flag = $(".active").attr("id");
            window.location.href = "./F10402.html?flag=" + flag + "&subjtDiv=" + subjtDiv[dataIndex] + "&startDate=" + startPageDate + "&endDate=" + endPageDate;
        });
    }
}

function getTimeOne() {
    if (myChart2 != null && myChart2 != "" && myChart2 != undefined) {
        myChart2.dispose();
    }
    myChart2 = echarts.init(document.getElementById('st_time_one'));
    var labelTop = {
        normal: {
            color: '#b9cfff',
            label: {
                show: false,
                position: 'center',
                formatter: '{b}',
                textStyle: {
                    baseline: 'top'
                }
            },
            labelLine: {
                show: false
            }
        }
    };
    var labelBottom = {
        normal: {
            color: '#2D6AF2',
            label: {
                show: false,
                position: 'center'
            },
            labelLine: {
                show: false
            }
        },
    };
    option = {
        title: {
            text: timeOne.proportion.toFixed(2) + '%',
            left: 'center',
            top: '45%',
            // padding:[24,0],
            textStyle: {
                color: '#031f2d',
                align: 'center'
            }
        },
        series: [
            {
                type: 'pie',
                center: ['50%', '50%'],
                radius: ['60%', '90%'],
                x: '60%', // for funnel
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        lablelLine: {
                            show: false
                        }
                    }
                },
                data: [
                    {name: '計画', value: timeOne.stuplantm >= timeOne.perftm ? (timeOne.stuplantm - timeOne.perftm) : 0, itemStyle: labelTop},
                    {name: '実績', value: timeOne.perftm, itemStyle: labelBottom}
                ],
                animation: false
            }
        ]
    };
    myChart2.setOption(option);
}

function getTimeTwo() {
    if (myChart3 != null && myChart3 != "" && myChart3 != undefined) {
        myChart3.dispose();
    }
    myChart3 = echarts.init(document.getElementById('st_time_two'));
    var maxValue = 20;
    var interval = 2;
    var plantm = (timeTwo.plantm / 60).toFixed(2);
    var stuplantm = (timeTwo.stuplantm / 60).toFixed(2);
    var perftm = (timeTwo.perftm / 60).toFixed(2);

    maxValue = maxValue <= plantm ? plantm : maxValue;
    maxValue = maxValue <= stuplantm ? stuplantm : maxValue;
    maxValue = maxValue <= perftm ? perftm : maxValue;
    interval = Math.ceil(maxValue / 10);
    var option = {
        tooltip: {
            show: false,
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            max: interval * 10,
            min: 0,
            splitNumber: 9,
            interval: interval,
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#333'
                }
            }
        },
        yAxis: {
            type: 'category',
            data: ['実績', '計画', 'タームプラン'],
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#CCC'
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#333'
                }
            }
        },
        series: [
            {
                name: '',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var colorList = ['#2D6AF2', '#2DB7F2', '#D6D6D6'];
                            return colorList[params.dataIndex]
                        }
                    }
                },
                barCategoryGap: '20px',
                data: [perftm, stuplantm, plantm]
            }
        ]
    };
    myChart3.setOption(option);
}

$(function () {
    var menlwidth = $(document).width() / 10 * 6;
    $(".border_div").css('width', menlwidth);
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
};
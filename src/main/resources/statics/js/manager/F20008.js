var param = getParam();
var degree = {};
var myChart1;
var myChart2;
var myChart3;
var timeOne = [];
var timeTwo = [];
var stuId = '';
var subjtDiv = [];
var dataIndex = 0;
var subjtNm = '';
var index_flag = 0; //重複修正
var type = param.type;

//時期指定
var startPageDate = param.startDay;
var endPageDate = param.endDay;
var colors = [];
$(function () {
    var scrHeight = $(window).height() - $(window).width() * 0.156;
    var scrHeight1 = ($(window).height() - $(window).width() * 0.196) / 2;
    $("#degree").css("height", scrHeight);
    $(".st_time_one_div").css("height", scrHeight1);
    $("#st_time_one").css("height", scrHeight1);
    $("#st_time_two").css("height", scrHeight1);
    var aclick = $(".plan_time").find('a');
    if (startPageDate == undefined || endPageDate == '' || startPageDate == 'null' || startPageDate == 'undefined') {
        if (type != null && type != 'undefined') {
            aclick.removeClass("active");
            for (var i = 0; i < aclick.length; i++) {
                if (aclick.eq(i).attr("id") == type) {
                    aclick.eq(i).addClass("active");
                }
            }
            showF20008Data(type);
        } else {
            aclick.eq(0).addClass("active");
            showF20008Data("day");
        }
    } else {
        showF20008Data("day", '', startPageDate, endPageDate);
    }

    aclick.click(function () {
        aclick.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).attr("id");
        showF20008Data(dateType, "");
        $("#subjtDiv").find("option").eq(0).attr("selected", "selected");
        startPageDate = null;
        endPageDate = null;
    });
    $("#subjtDiv").change(function () {
        if (startPageDate != null && endPageDate != null) {
            showF20008Data('', $("#subjtDiv").val(), startPageDate, endPageDate);
        } else {
            showF20008Data($(".active").attr("id"), $("#subjtDiv").val());
        }
    })
});

function showF20008Data(type, subjtDiv, startDate, endDate) {
    var data;
    if(!subjtDiv){//教科未指定の場合、フラッグがリセットされます
        index_flag = 0;
    }
    if (startDate == null || endDate == null) {
        data = {
            type: type,
            subjtDiv: subjtDiv
        };
    } else {
        data = {
            type: type,
            subjtDiv: subjtDiv,
            startDate: startDate,
            endDate: endDate
        };
    }
    $.ajax({
        url: ctxPath + '/manager/F20008',
        data: data,
        type: 'get',
        datatype: 'json',
        success: function (data) {
            degree = data.degree;
            timeOne = data.timeCircle;
            timeTwo = data.timeHorizontal;
            $("#stuplantm").text((timeOne.stuplantm / 60).toFixed(2));
            $("#perftm").text((timeOne.perftm / 60).toFixed(2));
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
            if (data.colors){
                colors = data.colors;
            }
            if (data.mentorNm) {
                $("#mentorNm").text(data.mentorNm);
            }
            if (data.stuNm){
                $("#studentNm").text(data.stuNm);
            }
            getDegree();
            getTimeOne();
            // getTimeTwo();
            if (data.degree) {
                // if (index_flag == 0) {
                $("#subjtDiv option").remove();
                $("#subjtDiv").append("<option value=''>選択してください。</option>");
                var subjtDivList = data.degree;
                for (var i = 0; i < subjtDivList.length; i++) {
                    if (subjtDiv && subjtDiv === subjtDivList[i].subjtdiv){
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "' selected>" + subjtDivList[i].subjtnm + "</option>");
                    }else {
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "'>" + subjtDivList[i].subjtnm + "</option>");
                    }
                }
                    // index_flag++;
                // }
            }
        }
    })
}

function getDatePrd() {
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
        content: ["../common/F40005.html?id=f20008&startDate=" + startPageDate + "&endDate=" + endPageDate, 'no'],
        success: function (layero, index) {
        },
    });
}

// function getInfo(flag, subjtDiv, stuId) {
//     $.ajax({
//         url: ctxPath + '/manager/F20008',
//         type: 'GET',
//         data: {
//             subjtDiv: subjtDiv,
//             flag: flag,
//             stuId: stuId
//         },
//         datatype: 'json',
//         success: function (data) {
//             if (data.subjtNm) {
//                 subjtNm = data.subjtNm;
//             }
//             if (data.f10402DtoList) {
//                 f10402DtoList = data.f10402DtoList;
//             }
//         }
//     })
// }

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
        xAxisData.push(item.subjtnm);
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
            data: seriesDatas[item.codCd]
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
                    /* 2021/01/04 UT-077 cuikailin add start */
                    interval:0,
                    /* 2021/01/04 UT-077 cuikailin add end */
                    textStyle: {
                        color: '#333',
                        fontSize: 10
                    },
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
        series: seriesData
    };

    myChart1.setOption(option);
    if (option && typeof option === "object") {
        myChart1.setOption(option, true);
        //myChartクリック事件
        myChart1.on('click', function (params) {
            dataIndex = params.dataIndex;
            flag = $(".active").attr("id");
            myChart1.clear();
            myChart1.dispose();
            myChart2.clear();
            myChart2.dispose();
            // myChart3.clear();
            // myChart3.dispose();
            window.location.href = "./F20009.html?flag=" + flag + "&subjtDiv=" + subjtDiv[dataIndex] + "&startDate=" + startPageDate + "&endDate=" + endPageDate;
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
                    {name: '実行', value: timeOne.perftm, itemStyle: labelBottom}
                ],
                animation: false
            }
        ]
    };
    myChart2.setOption(option);
}

// function getTimeTwo() {
//     var maxValue = 20;
//     var interval = 2;
//     var plantm = (timeTwo.plantm / 60).toFixed(2);
//     var stuplantm = (timeTwo.stuplantm / 60).toFixed(2);
//     var perftm = (timeTwo.perftm / 60).toFixed(2);
//
//     maxValue = maxValue <= plantm ? plantm : maxValue;
//     maxValue = maxValue <= stuplantm ? stuplantm : maxValue;
//     maxValue = maxValue <= perftm ? perftm : maxValue;
//     interval = Math.ceil(maxValue / 10);
//     if (myChart3 != null && myChart3 != "" && myChart3 != undefined) {
//         myChart3.dispose();
//     }
//     myChart3 = echarts.init(document.getElementById('st_time_two'));
//     var option = {
//         tooltip: {
//             show: false,
//             trigger: 'axis',
//             axisPointer: {
//                 type: 'shadow'
//             }
//         },
//         grid: {
//             left: '3%',
//             right: '4%',
//             bottom: '3%',
//             containLabel: true
//         },
//         xAxis: {
//             type: 'value',
//             boundaryGap: [0, 0.01],
//             max: interval * 10,
//             min: 0,
//             splitNumber: 9,
//             interval: interval,
//             axisTick: {
//                 show: false
//             },
//             axisLine: {
//                 show: false
//             },
//             axisLabel: {
//                 textStyle: {
//                     color: '#333'
//                 }
//             }
//         },
//         yAxis: {
//             type: 'category',
//             data: ['実績', '計画', 'タームプラン',],
//             axisTick: {
//                 show: false
//             },
//             axisLine: {
//                 lineStyle: {
//                     color: '#CCC'
//                 }
//             },
//             axisLabel: {
//                 textStyle: {
//                     color: '#333'
//                 }
//             }
//         },
//         series: [
//             {
//                 name: '',
//                 type: 'bar',
//                 itemStyle: {
//                     normal: {
//                         color: function (params) {
//                             var colorList = ['#2D6AF2', '#2DB7F2', '#D6D6D6'];
//                             return colorList[params.dataIndex]
//                         }
//                     }
//                 },
//                 barCategoryGap: '20px',
//                 data: [perftm, stuplantm, plantm]
//             }
//         ]
//     };
//     myChart3.setOption(option);
// }

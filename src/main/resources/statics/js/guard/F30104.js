var param = getParam();
var degree = {};
var mychart1;
var mychart2;
var mychart3;
var timeOne = [];
var timeTwo = [];
var stuId = '';
var subjtDiv = [];
var subjtNm = [];
var dataIndex = 0;
var index_flag = 0;//重複修正
var type = param.type;
//時期指定
var startPageDate = '';
var endPageDate = '';
var colors = [];
if (param.startDay != undefined) {
    startPageDate = param.startDay;
}
if (param.endDay != undefined) {
    endPageDate = param.endDay;
}

$(function () {
    var aclick = $(".time_tab").find('li');
    aclick.removeClass("active");//应该是去掉底部颜色，下面的循环是加上颜色
    if (startPageDate == undefined || endPageDate == '' || startPageDate == 'null') {
        if (type != null && type != 'undefined') {
            for (var i = 0; i < aclick.length; i++) {
                if (aclick.eq(i).find("a").eq(0).attr("target") == type) {
                    aclick.eq(i).addClass("active");
                }
            }
            showF30104Data(1,type);
        } else {
            aclick.eq(0).addClass("active");
            showF30104Data(1,"day");
        }
    } else {
        showF30104Data(1,"day", '', startPageDate, endPageDate);
    }
    var aclick1 = $(".time_tab").find('li');
    aclick1.click(function () {
        aclick1.removeClass("active");
        $(this).addClass("active");
        var dateType = $(this).find("a").eq(0).attr("target");
        showF30104Data(0,dateType);
        $("#subjtDiv").find("option").eq(0).attr("selected", "selected");
        startPageDate = null;
        endPageDate = null;
    });
    $("#subjtDiv").change(function () {
        if (startPageDate != null && endPageDate != null && startPageDate != '' && endPageDate != '') {
            showF30104Data(0,'', $("#subjtDiv").val(), startPageDate, endPageDate);
        } else {
            showF30104Data(0,$(".active").attr("id"), $("#subjtDiv").val());
        }
    });

    var headAclick = $(".tab").find('li');
    $("#learnStatus").click(function () {
        showF30104Data(0,"day");
        headAclick.removeClass("liactive");
        headAclick.eq(0).addClass("liactive");
        $("#subjtDiv").find("option").eq(0).attr("selected", "selected");
        $(".time_tab").find('li').removeClass("active");
        $(".time_tab").find('li').eq(0).addClass("active");
    });
});

function getDatePrd() {
    var index = layer.open({
        id: 'f30104',
        type: 2,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: ['80%', '340px'],
        fixed: true,
        resize: false,
        content: ["../common/F40005.html?id=f30104&startDate=" + startPageDate + "&endDate=" + endPageDate, 'no'],
        success: function (layero, index) {
        }
    });
}
/*
 * dataFlg:0--非初期，1--初期
 * */
function showF30104Data(dataFlg,type, subjtDiv, startDate, endDate) {
    var data;
    if(!subjtDiv){//教科未指定の場合、フラッグがリセットされます
        index_flag = 0;
    }
    var url = window.location.href;
    if (startDate == null || endDate == null) {
        data = {
            url:url,
            dataFlg:dataFlg,
            type: type,
            subjtDiv: subjtDiv
        };
    } else {
        data = {
            url:url,
            dataFlg:dataFlg,
            type: type,
            subjtDiv: subjtDiv,
            startDate: startDate,
            endDate: endDate
        };
    }
    $.ajax({
        url: ctxPath + '/guard/F30104',
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
            //if (subjtDiv == null || subjtDiv == "") {

            //}
            if (data.startDate != null && data.startDate != undefined) {
                startPageDate = data.startDate;
            }
            if (data.endDate != null && data.endDate != undefined) {
                endPageDate = data.endDate;
            }
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
            if (data.colors){
                colors = data.colors;
            }
            getDegree();
            getTimeOne();
            getTimeTwo();
            if (data.degree) {
                // if (index_flag == 0) {
                $("#subjtDiv option").remove();
                $("#subjtDiv").append("<option value=''>すべて</option>");
                var subjtDivList = data.degree;
                for (var i = 0; i < subjtDivList.length; i++) {
                    if (subjtDiv && subjtDiv === subjtDivList[i].subjtdiv){
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "' selected>" + subjtDivList[i].subjtnm + "</option>");
                    }else {
                        $("#subjtDiv").append("<option value='" + subjtDivList[i].subjtdiv + "'>" + subjtDivList[i].subjtnm + "</option>");
                    }
                }
                //     index_flag++;
                // }
            }
        }
    })
}

function getDegree() {
    if (mychart1 != null && mychart1 != "" && mychart1 != undefined) {
        mychart1.dispose();
    }
    mychart1 = echarts.init(document.getElementById('degree'));
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
                    textStyle: {
                        color: '#333',
                        fontSize: 10
                    },
                    rotate: -20,
                    padding:[10, 0, 0, -10]
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
        dataZoom: {
            show: true,
            realtime: true,
            bottom: -1,
            type:'inside',
            start: 0,
            end: degree.length > 5 ? (5/degree.length) * 100 : 100
        },
        series: seriesData
    };
    mychart1.setOption(option);
    if (option && typeof option === "object") {
        mychart1.setOption(option, true);
        //myChartクリック事件
        mychart1.on('click', function (params) {
            dataIndex = params.dataIndex;
            flag = $(".active").find("a").attr("target");
            window.location.href = "F30105.html?flag=" + flag + "&subjtNm=" + subjtNm[params.dataIndex] + "&startDate=" + startPageDate + "&endDate=" + endPageDate+"&subjtDiv="+subjtDiv[params.dataIndex];
        });
    }
}

function getTimeOne() {
    if (mychart2 != null && mychart2 != "" && mychart2 != undefined) {
        mychart2.dispose();
    }
    mychart2 = echarts.init(document.getElementById('time_one'));
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
    var labelFromatter = {
        normal: {
            label: {
                textStyle: {
                    baseline: 'bottom'
                }
            }
        },
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
            top: 'center',
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
                radius: ['55%', '85%'],
                x: '60%', // for funnel
                itemStyle: labelFromatter,
                data: [
                    {name: '計画', value: timeOne.stuplantm >= timeOne.perftm ? (timeOne.stuplantm - timeOne.perftm) : 0, itemStyle: labelTop},
                    {name: '実績', value: timeOne.perftm, itemStyle: labelBottom}
                ],
                animation: false,
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                label: {
                    normal: {
                        show: false
                    }
                }
            }
        ]
    };
    mychart2.setOption(option);
}

function getTimeTwo() {
    if (mychart3 != null && mychart3 != "" && mychart3 != undefined) {
        mychart3.dispose();
    }
    mychart3 = echarts.init(document.getElementById('time_two'));
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
            data: ['実績', '計画', 'タームプラン',],
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
                            var colorList = ['#2D6AF2', '#b9cfff', '#D6D6D6'];
                            return colorList[params.dataIndex]
                        }
                    }
                },
                barCategoryGap: '5px',
                data: [perftm, stuplantm, plantm]
            }
        ]
    };
    mychart3.setOption(option);
}
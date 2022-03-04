var page = 0;
var pageSize = 30;
var weekArray = ['日', '月', '火', '水', '木', '金', '土'];
var category = [];
var vm = new Vue({
    el: '.main',
    data: {
        mstGrpEntityList: [],
        schyList: [],
        today: '',
        schoolday: [],
        groupday: [],
        schyday: [],
        type: '',
        thisweek: '',
        maxValue: '',
        flg: '',
        informationList: [],
        total: 1
    },
    mounted: function () {
        this.init();
        this.loadGrid();
    },
    methods: {
        //初期表示
        init: function () {
            //タブの取得
            // this.type = type;
            $.ajax({
                url: ctxPath + '/manager/F21033/init',
                type: 'POST',
                data: {},
                success: function (data) {
                    //登録者所属組織全グループを抽出して、画面中セレクトボックス中のに表示する
                    vm.mstGrpEntityList = data.mstGrpEntityList;
                    //コードマスタ_明細から学年リストの値を抽出して
                    vm.schyList = data.schyList;
                    //echartsの結合
                    var dayDatas = echarts.init(document.getElementById('map1'));
                    //echartsのクリア
                    dayDatas.clear();
                    initMap1('day');
                    initMap2('daySchy');
                    $("#groupSel").val(null);
                }
            });
        },
        getDayDatas: function (type) {
            $(".body_head").find(".map1").each(function () {
                if ($(this).hasClass("active")) {
                    vm.flg = type === undefined ? true : false;
                    type = type === undefined ? $(this).attr('id') : type;
                    $(this).removeClass("active");
                }
            });
            //タブ日押下
            if (type === 'day') {
                $("#day").addClass("active");
            }
            //タブ週押下
            else if (type === 'week') {
                $("#week").addClass("active");
            }
            //タブ月押下
            else {
                $("#month").addClass("active");
            }
            if ($("#groupSel").val() === ''){
                initMap1(type);
                return;
            }
            //背景色の設定
            // //echartsの結合
            var dayDatas = echarts.init(document.getElementById('map1'));
            //echartsのクリア
            dayDatas.clear();
            var sqlDays = [];
            var dateSchoolData = [];
            var dateGroupData = [];
            this.today = new Date();
            //タブ日
            if (type === 'day') {
                category = [];
                for (var i = 0; i < 7; i++) {
                    var date = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate());
                    //日数の計算
                    var index = 6 - i;
                    var day = new Date(date.setDate(date.getDate() - index));
                    sqlDays.push(day.Format("yyyy-MM-dd"));
                    category.push(day.Format("M/d") + '(' + getDay(day.getDay()) + ')');
                }
            }
            //タブ週押下
            else if (type === 'week') {
                category = [];
                var now = new Date(); //当前日期
                this.thisweek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay() + 1);
                for (var i = 0; i < 5; i++) {
                    var date = new Date(this.thisweek.getFullYear(), this.thisweek.getMonth(), this.thisweek.getDate());
                    var index = 4 - i;
                    var preWeek = new Date(date.setDate(date.getDate() - 7 * index));
                    sqlDays.push(preWeek.Format("yyyy-MM-dd"));
                    category.push(preWeek.Format("M/d") + "(週)");
                }
            }
            //タブ月押下
            else {
                category = [];
                for (var i = 0; i < 6; i++) {
                    var date = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate());
                    //データのインデックスは、トラバーサルのインデックスの反対です
                    var index = 5 - i;
                    //日数の計算
                    var day = new Date(date.setMonth(date.getMonth() - index));
                    var firstDay = new Date(day.getFullYear(), day.getMonth(), 1);
                    sqlDays.push(firstDay.Format("yyyy-MM-dd"));
                    category.push(day.format("Y/m"));
                }
            }
            //データの取得
            $.ajax({
                url: ctxPath + '/manager/F21033/init',
                type: 'POST',
                data: {
                    daysStr: JSON.stringify(sqlDays),
                    type: type,
                    grpId: $("#groupSel").val()
                },
                async: false,
                success: function (data) {
                    //校舎のデータ
                    vm.schoolday = data.schoolMap;
                    //担当グループのデータ
                    vm.groupday = data.groupMap;
                }
            });
            for (var i = 0; i < sqlDays.length; i++) {
                //校舎平均の折れ線グラフが表示するため
                dateSchoolData.push(vm.schoolday[sqlDays[i]] * 100);
                //担当グループ平均の折れ線グラフが表示するため
                dateGroupData.push(vm.groupday[sqlDays[i]] * 100);
            }
            var option = {
                title: {
                    text: '実績入力割合推移',
                    left: '3%',
                    textStyle: {
                        color: 'red',
                        // 2020/11/27 modify zmh start
                        fontSize:15,
                        fontFamily: "Meiryo"
                        // 2020/11/27 modify zmh end
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params)//数据格式
                    {
                        var result = '';
                        for (var i = 0; i < params.length; i++) {
                            if (i === 0) {
                                result += params[i].axisValue + '</br>';
                            }
                            var percent = params[i].data % 1 > 0 ? params[i].data.toFixed(2) : params[i].data;
                            result += params[i].seriesName + ':' + percent + '%';
                            if (i < params.length - 1) {
                                result += '</br>';
                            }
                        }
                        return result;
                    }
                },
                legend: {
                    x: 200,
                    data: ['校舎平均', '担当グループ平均']
                },
                grid: {
                    left: '3%',
                    right: '6%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: category
                },
                yAxis: {
                    type: 'value',
                    scale: true,
                    max: 100,
                    min: 0,
                    splitNumber: 5,
                    axisLabel: {
                        show: true,
                        interval: 'auto',
                        formatter: function (value) {
                            return value + '%';
                        }
                    },
                    show: true
                },
                series: [
                    {
                        name: '校舎平均',
                        type: 'line',
                        stack: '校舎平均',
                        itemStyle: {
                            normal: {
                                color: '#6EAB45',
                                lineStyle: {
                                    color: '#6EAB45'
                                }
                            }
                        },
                        data: dateSchoolData
                    },
                    {
                        name: '担当グループ平均',
                        type: 'line',
                        stack: '担当グループ平均',
                        itemStyle: {
                            normal: {
                                color: '#FC0000',
                                lineStyle: {
                                    color: '#FC0000'
                                }
                            }
                        },
                        data: dateGroupData
                    }
                ]
            };
            var dayDatas = echarts.init(document.getElementById('map1'));
            if ($("#groupSel").val() !== '') {
                dayDatas.setOption(option);
            }
            if ($("#schySel").val() !== '' && this.flg == true) {
                this.getMap2();
            }
        },
        getMap2: function (type) {
            $(".body_head").find(".map2").each(function () {
                if ($(this).hasClass("active")) {
                    type = type === undefined ? $(this).attr('id') : type;
                    $(this).removeClass("active");
                }
            });
            //タブ日押下
            if (type === 'daySchy') {
                $("#daySchy").addClass("active");
            }
            //タブ週押下
            else if (type === 'weekSchy') {
                $("#weekSchy").addClass("active");
            }
            //タブ月押下
            else {
                $("#monthSchy").addClass("active");
            }
            if ($("#schySel").val() === ''){
                initMap2(type);
                return;
            }
            //背景色の設定
            // //echartsの結合
            var dayDatas = echarts.init(document.getElementById('map2'));
            //echartsのクリア
            dayDatas.clear();
            var sqlDays = [];
            var dateSchoolData = [];
            var dateAvgData = [];
            var dateGroupData = [];
            this.today = new Date();
            //タブ日
            if (type === 'daySchy') {
                category = [];
                for (var i = 0; i < 7; i++) {
                    var date = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate());
                    //日数の計算
                    var index = 6 - i;
                    var day = new Date(date.setDate(date.getDate() - index));
                    sqlDays.push(day.Format("yyyy-MM-dd"));
                    category.push(day.Format("M/d") + '(' + getDay(day.getDay()) + ')');
                }
            }
            //タブ週押下
            else if (type === 'weekSchy') {
                category = [];
                var now = new Date(); //当前日期
                this.thisweek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay() + 1);
                for (var i = 0; i < 5; i++) {
                    var date = new Date(this.thisweek.getFullYear(), this.thisweek.getMonth(), this.thisweek.getDate());
                    var index = 4 - i;
                    var preWeek = new Date(date.setDate(date.getDate() - 7 * index));
                    sqlDays.push(preWeek.Format("yyyy-MM-dd"));
                    category.push(preWeek.Format("M/d") + "(週)");
                }
            }
            //タブ月押下
            else {
                category = [];
                for (var i = 0; i < 6; i++) {
                    var date = new Date(this.today.getFullYear(), this.today.getMonth(), this.today.getDate());
                    //データのインデックスは、トラバーサルのインデックスの反対です
                    var index = 5 - i;
                    //日数の計算
                    var day = new Date(date.setMonth(date.getMonth() - index));
                    var firstDay = new Date(day.getFullYear(), day.getMonth(), 1);
                    sqlDays.push(firstDay.Format("yyyy-MM-dd"));
                    category.push(day.format("Y/m"));
                }
            }
            //データの取得
            $.ajax({
                url: ctxPath + '/manager/F21033/getMap2',
                type: 'POST',
                data: {
                    daysStr: JSON.stringify(sqlDays),
                    type: type,
                    grpId: $("#groupSel").val(),
                    schyDiv: $("#schySel").val()
                },
                async: false,
                success: function (data) {
                    //校舎のデータ
                    vm.schoolday = data.schoolMap;
                    //担当グループのデータ
                    vm.groupday = data.avgMap;
                    vm.schyday = data.groupMap;
                    vm.maxValue = data.maxValue;
                }
            });
            for (var i = 0; i < sqlDays.length; i++) {
                //校舎平均の折れ線グラフが表示するため
                dateSchoolData.push(vm.schoolday[sqlDays[i]]);
                //担当グループ平均の折れ線グラフが表示するため
                dateAvgData.push(vm.schyday[sqlDays[i]]);
                dateGroupData.push(vm.groupday[sqlDays[i]]);
            }
            var option2 = {
                title: {
                    text: '学年別平均学習時間推移 ',
                    left: '3%',
                    textStyle: {
                        color: 'red',
                        // 2020/11/27 modify zmh start
                        fontSize:15,
                        fontFamily: "Meiryo"
                        // 2020/11/27 modify zmh end
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params)//数据格式
                    {
                        var result = '';
                        for (var i = 0; i < params.length; i++) {
                            if (i === 0) {
                                result += params[i].axisValue + '</br>';
                            }
                            var percent = params[i].data % 1 > 0 ? params[i].data : params[i].data;
                            result += params[i].seriesName + ':' + (percent / 60).toFixed(2) + '時間';
                            if (i < params.length - 1) {
                                result += '</br>';
                            }
                        }
                        return result;
                    }
                },
                legend: {
                    x: 200,
                    data: ['校舎平均', '担当グループ平均', '全体平均']
                },
                grid: {
                    left: '3%',
                    right: '6%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: category
                },
                yAxis: {
                    type: 'value',
                    max: vm.maxValue,
                    min: 0,
                    splitNumber: 5,
                    interval: Math.ceil(vm.maxValue / 5),
                    axisLabel: {
                        formatter: function (value) {
                            var num = (Number)((value / 60));
                            var result = Math.ceil(num * 4)/4;
                            return result.toFixed(2) + '時間';
                        }
                    },
                },
                series: [
                    {
                        name: '校舎平均',
                        type: 'line',
                        stack: '校舎平均',
                        itemStyle: {
                            normal: {
                                color: '#6EAB45',
                                lineStyle: {
                                    color: '#6EAB45'
                                }
                            }
                        },
                        data: dateSchoolData
                    },
                    {
                        name: '担当グループ平均',
                        type: 'line',
                        stack: '担当グループ平均',
                        itemStyle: {
                            normal: {
                                color: '#FC0000',
                                lineStyle: {
                                    color: '#FC0000'
                                }
                            }
                        },
                        data: dateAvgData
                    },
                    {
                        name: '全体平均',
                        type: 'line',
                        stack: '全体平均',
                        itemStyle: {
                            normal: {
                                color: 'blue',
                                lineStyle: {
                                    color: 'blue'
                                }
                            }
                        },
                        data: dateGroupData
                    }
                ]
            };
            var dayDatas = echarts.init(document.getElementById('map2'));
            if ($("#groupSel").val() !== '') {
                dayDatas.setOption(option2);
            }
        },
        openPop: function (id, e) {
            $(e).css('font-weight', 'normal');
            layer.open({
                id: 'F21057',
                type: 2,
                anim: 2,
                skin: 'layui-layer-myskin',
                title: " ",
                shade: 0.2,
                closeBtn: 1,
                shadeClose: false,
                move: '.layui-layer-title',
                area: ['680px', '510px'],
                fixed: true,
                resize: false,
                content: ["../pop/F21057.html?id=" + id, 'no']
            });
        },
        loadGrid: function () {
            var srcHeight = $(window).height() * 0.6;
            var srcWidth = $('#information-container').width() * 0.99;
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F21033/getInformation',
                    datatype: "json",
                    postData: {},
                    colModel: [
                        {
                            label: ' '
                            , name: 'pubPlanStartDt'
                            , index: 'pubPlanStartDt'
                            , width: 15
                            , sortable: false
                            , align: "center"
                            , hidden: false
                            , formatter: function (cell, option, object) {
                                var match = cell.match(/(\d{4})-(\d{2})-(\d{2})/);
                                var datetime = new Date(match[1], parseInt(match[2]) - 1, match[3]);
                                var date = parseInt(match[2]) + '/' + parseInt(match[3]) + '（' + getDay(datetime.getDay()) + '）';
                                return '<span class="information-date">' + date + '</span>';
                            }
                        },
                        {
                            label: ' '
                            , name: 'usrNm'
                            , index: 'usrNm'
                            , width: 20
                            , sortable: false
                            , align: "left"
                            , formatter(cell, option, object) {
                                return '<span class="send-user">From：' + cell + '　</span>'
                            }
                        },
                        {
                            label: ' '
                            , name: 'messageTitle'
                            , index: 'messageTitle'
                            , width: 55
                            , sortable: false
                            , align: "left"
                            , formatter(cell, option, object) {
                                var style = object.readingStsDiv === '0' ? 'font-weight:bold;' : 'font-weight: normal;';
                                return '<a style="' + style + '" onclick="vm.openPop(' + object.id + ', this)" class="information-item">' + cell + '</a>'
                            }
                        },
                        {
                            label: ' '
                            , name: 'messageLevelDiv'
                            , index: 'messageLevelDiv'
                            , width: 10
                            , sortable: false
                            , align: "center"
                            , hidden: false
                            , formatter(cell, option, object) {
                                var style = cell === '2' ? 'display: block!important;' : '';
                                return '<div style="' + style + '" class="important">重要</div>'
                            }
                        }
                    ],
                    viewrecords: true,
                    height: srcHeight,
                    width: srcWidth,
                    rowNum: 10,
                    rowList: [10, 30, 50],
                    rownumbers: false,
                    rownumWidth: 25,
                    multiselect: false,
                    pager: "#jqGridPager",
                    jsonReader:
                        {
                            root: "page.list",
                            page: "page.currPage",
                            total: "page.totalPage",
                            records: "page.totalCount"
                        }
                    ,
                    prmNames: {
                        page: "page",
                        rows: "limit"
                    }
                    ,
                    gridComplete: function () {
                        if ($("#message") != null) {
                            $("#message").hide();
                        }
                        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    },
                    loadComplete: function (data) {
                        if (data.code != 0) {
                            showMsg(data.msg);
                        }
                    },

                }
            );
        }
    }
});

//曜日の取得
function getDay(day) {
    var weekday = '';
    switch (day) {
        case 1:
            weekday = "月";
            break;
        case 2:
            weekday = "火";
            break;
        case 3:
            weekday = "水";
            break;
        case 4:
            weekday = "木";
            break;
        case 5:
            weekday = "金";
            break;
        case 6:
            weekday = "土";
            break;
        case 0:
            weekday = "日";
            break;
    }
    return weekday;
}
function initMap1(type){
    vm.today = new Date();
    if (type === 'day') {
        category = [];
        for (var i = 0; i < 7; i++) {
            var date = new Date(vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate());
            //日数の計算
            var index = 6 - i;
            var day = new Date(date.setDate(date.getDate() - index));
            category.push(day.Format("M/d") + '(' + getDay(day.getDay()) + ')');
        }
    }
    //タブ週押下
    else if (type === 'week') {
        category = [];
        var now = new Date(); //当前日期
        vm.thisweek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay() + 1);
        for (var i = 0; i < 5; i++) {
            var date = new Date(vm.thisweek.getFullYear(), vm.thisweek.getMonth(), vm.thisweek.getDate());
            var index = 4 - i;
            var preWeek = new Date(date.setDate(date.getDate() - 7 * index));
            category.push(preWeek.Format("M/d") + "(週)");
        }
    }
    //タブ月押下
    else {
        category = [];
        for (var i = 0; i < 6; i++) {
            var date = new Date(vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate());
            //データのインデックスは、トラバーサルのインデックスの反対です
            var index = 5 - i;
            //日数の計算
            var day = new Date(date.setMonth(date.getMonth() - index));
            category.push(day.format("Y/m"));
        }
    }
    var option = {
        title: {
            text: '実績入力割合推移',
            left: '3%',
            textStyle: {
                color: 'red',
                // 2020/11/27 modify zmh start
                fontSize:15,
                fontFamily: "Meiryo"
                // 2020/11/27 modify zmh end
            }
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params)//数据格式
            {
                var result = '';
                for (var i = 0; i < params.length; i++) {
                    if (i === 0) {
                        result += params[i].axisValue + '</br>';
                    }
                    var percent = params[i].data % 1 > 0 ? params[i].data.toFixed(2) : params[i].data;
                    result += params[i].seriesName + ':' + percent + '%';
                    if (i < params.length - 1) {
                        result += '</br>';
                    }
                }
                return result;
            }
        },
        legend: {
            x: 200,
            data: ['校舎平均', '担当グループ平均']
        },
        grid: {
            left: '3%',
            right: '6%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: category
        },
        yAxis: {
            type: 'value',
            scale: true,
            max: 100,
            min: 0,
            splitNumber: 5,
            axisLabel: {
                show: true,
                interval: 'auto',
                formatter: function (value) {
                    return value + '%';
                }
            },
            show: true
        },
        series: [
            {
                name: '校舎平均',
                type: 'line',
                stack: '校舎平均',
                itemStyle: {
                    normal: {
                        color: '#6EAB45',
                        lineStyle: {
                            color: '#6EAB45'
                        }
                    }
                },
                data: ''
            },
            {
                name: '担当グループ平均',
                type: 'line',
                stack: '担当グループ平均',
                itemStyle: {
                    normal: {
                        color: '#FC0000',
                        lineStyle: {
                            color: '#FC0000'
                        }
                    }
                },
                data: ''
            }
        ]
    };
    var dayDatas = echarts.init(document.getElementById('map1'));
    dayDatas.setOption(option);
}
function initMap2(type){
    vm.today = new Date();
    if (type === 'daySchy') {
        category = [];
        for (var i = 0; i < 7; i++) {
            var date = new Date(vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate());
            //日数の計算
            var index = 6 - i;
            var day = new Date(date.setDate(date.getDate() - index));
            category.push(day.Format("M/d") + '(' + getDay(day.getDay()) + ')');
        }
    }
    //タブ週押下
    else if (type === 'weekSchy') {
        category = [];
        var now = new Date(); //当前日期
        vm.thisweek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay() + 1);
        for (var i = 0; i < 5; i++) {
            var date = new Date(vm.thisweek.getFullYear(), vm.thisweek.getMonth(), vm.thisweek.getDate());
            var index = 4 - i;
            var preWeek = new Date(date.setDate(date.getDate() - 7 * index));
            category.push(preWeek.Format("M/d") + "(週)");
        }
    }
    //タブ月押下
    else {
        category = [];
        for (var i = 0; i < 6; i++) {
            var date = new Date(vm.today.getFullYear(), vm.today.getMonth(), vm.today.getDate());
            //データのインデックスは、トラバーサルのインデックスの反対です
            var index = 5 - i;
            //日数の計算
            var day = new Date(date.setMonth(date.getMonth() - index));
            category.push(day.format("Y/m"));
        }
    }
    var option2 = {
        title: {
            text: '学年別平均学習時間推移 ',
            left: '3%',
            textStyle: {
                color: 'red',
                // 2020/11/27 modify zmh start
                fontSize:15,
                fontFamily: "Meiryo"
                // 2020/11/27 modify zmh end
            }
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params)//数据格式
            {
                var result = '';
                for (var i = 0; i < params.length; i++) {
                    if (i === 0) {
                        result += params[i].axisValue + '</br>';
                    }
                    var percent = params[i].data % 1 > 0 ? params[i].data.toFixed(2) : params[i].data;
                    result += params[i].seriesName + ':' + (percent / 60) + '時間';
                    if (i < params.length - 1) {
                        result += '</br>';
                    }
                }
                return result;
            }
        },
        legend: {
            x: 200,
            data: ['校舎平均', '担当グループ平均', '全体平均']
        },
        grid: {
            left: '3%',
            right: '6%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: category
        },
        yAxis: {
            type: 'value',
            max: 10,
            min: 0,
            splitNumber: 5,
            interval: Math.ceil(10 / 5),
            axisLabel: {
                formatter: function (value) {
                    return value.toFixed(2) + '時間';
                }
            },
        },
        series: [
            {
                name: '校舎平均',
                type: 'line',
                stack: '校舎平均',
                itemStyle: {
                    normal: {
                        color: '#6EAB45',
                        lineStyle: {
                            color: '#6EAB45'
                        }
                    }
                },
                data: ''
            },
            {
                name: '担当グループ平均',
                type: 'line',
                stack: '担当グループ平均',
                itemStyle: {
                    normal: {
                        color: '#FC0000',
                        lineStyle: {
                            color: '#FC0000'
                        }
                    }
                },
                data: ''
            },
            {
                name: '全体平均',
                type: 'line',
                stack: '全体平均',
                itemStyle: {
                    normal: {
                        color: 'blue',
                        lineStyle: {
                            color: 'blue'
                        }
                    }
                },
                data: ''
            }
        ]
    };
    var dayDatas = echarts.init(document.getElementById('map2'));
    dayDatas.setOption(option2);
}
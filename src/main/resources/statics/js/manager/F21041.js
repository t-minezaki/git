var param = getParam();
var weekArray = ['日', '月', '火', '水', '木', '金', '土'];
var degree = {};
var mychart1;
var mychart2;
var timeOne = [];
var timeTwo = [];
var stuId = param.stuId;
var subjtDiv = [];
var subjtNm = [];
var dataIndex = 0;
var index_flag = 0;//重複修正
var frist= true;
var getTalk =[];
var type = param.type;
//時期指定
var startPageDate = '';
var endPageDate = '';
var colors = [];
var datas ={};
var stats = 1;
var flg=1;
if (param.startDay != undefined) {
    startPageDate = param.startDay;
}
if (param.endDay != undefined) {
    endPageDate = param.endDay;
}



/*
 * dataFlg:0--非初期，1--初期
 * */

var vm = new Vue({
    el:'#page',
    type:'json',
    data:{
        stuNm:'',
        date:'',
        dateRange:'',
        talkDate:[],
        getTalk:[]
    },
    mounted: function(){

        this.getInfo();
        this.getInit();
    },
    methods:{
        getInit:function(){
            var srcHeight = $(window).height() * 0.62;
            var srcWidth = $(window).width() * 0.39;
            $.jgrid.gridUnload("jqGrid");
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F21041/talk',
                    datatype: "json",
                    postData: {
                        nowYear:new Date().getFullYear(),
                        flg:flg,
                        stuId:stuId
                    },
                    colModel: [
                        {label: ' ', name: 'talkDatime', index: 'talkDatime', width: 50, sortable: false, align: "left"
                            ,formatter(cell, option, object){
                                var getTime = new Date(object.talkDatime==null?object.askDatime:object.talkDatime);
                                var a = weekArray[getTime.getDay()];
                                var time =getTime.getMonth()+1 +"/" +getTime.getDate()+ " " +"(" + a + ")";
                                return '<div style="border-bottom:1px #C6C6C6 !important;margin-left: 2vw;font-weight: bolder;">'+time+'</div>'
                            }
                        },
                        {label: '面談タイトル', name: 'eventTitle', index: 'eventTitle', width: 150, sortable: false, align: "left"
                            ,formatter(cell, option, object){
                            if (object.talkDatime==null) {
                                return '<div onclick="infoPop('+object.id+')"  style="border-bottom:1px #C6C6C6  !important;font-weight: bolder;cursor: pointer;">'+object.askTitle+'</div>'
                            }else {
                                return '<div onclick="infoPop('+object.id+')" style="border-bottom:1px #C6C6C6  !important;font-weight: bolder;cursor: pointer;">'+object.eventTitle+'</div>'
                            }
                                }}
                    ],
                    viewrecords: true,
                    height: srcHeight,
                    width:srcWidth,
                    rowNum: flg==1?10:30,
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
                    }
                    ,
                    loadComplete: function (data) {
                            if (data.code !=0){
                                showMsg(data.msg);
                            }
                    },

                }
            );
        },
        getInfo:function () {
            if (stats ==1){
                if (frist) {
                    this.date=new Date();
                    startDay= this.date;
                    this.date = new Date(this.date.setDate(this.date.getDate() - 6));

                }else {
                    this.date=vm.date;
                    startDay= this.date;
                    this.date = new Date( this.date.setDate( this.date.getDate()));
                }
                var date = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());
                //日数の計算
                var day = new Date(date.setDate(date.getDate() + 6));
                //日付範囲の設定
                this.dateRange = this.date.format('Y/m/d') + ' ~ ' + day.format('Y/m/d');
                $("#monthView").removeClass("this-view");
                $("#weekView").removeClass("this-view");
                $("#dayView").addClass("this-view");
            }else if(stats ==2){
                var startDay ='';
                    if (frist) {
                        this.date=new Date();
                        startDay= new Date();

                        this.date =startDay;
                    }else {
                        var date = this.date;
                        startDay = this.date;
                    }
                var date = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());
                var day = new Date(date.setDate(date.getDate() - 35));
                this.dateRange =day.format('Y/m/d') + ' ~ ' + this.date.format('Y/m/d') ;
                $("#dayView").removeClass("this-view");
                $("#monthView").removeClass("this-view");
                $("#weekView").addClass("this-view");
        }else{
                if (frist) {
                    this.date=new Date();
                    startDay= new Date();

                    this.date =startDay;
                }else {
                    var date = this.date;
                    startDay = this.date;
                }
                var date = new Date(this.date.getFullYear(), this.date.getMonth());
                var day = new Date(date.setMonth(date.getMonth()-5));
                    this.dateRange = day.format('Y/m') + ' ~ ' + this.date.format('Y/m');
                $("#dayView").removeClass("this-view");
                $("#weekView").removeClass("this-view");
                $("#monthView").addClass("this-view");
            }

            $.ajax({
                url: ctxPath + '/manager/F21041/init',
                data:{
                    stats:stats,
                    stuId:stuId,
                    startDate:new Date(new Date(startDay.Format("yyyy-MM-dd")).setHours(0)),
                    nowYear:new Date().getFullYear()

                },
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    datas = {};
                    degree = data.degree;
                    vm.stuNm=data.stuNm;
                    colors=[];
                        for (var i = 0; i <data.degree.length ; i++) {
                            if (!datas[data.degree[i].planYmd.substring(0,10)]){
                                datas[data.degree[i].planYmd.substring(0,10)] = {}
                            }
                            datas[data.degree[i].planYmd.substring(0,10)][data.degree[i].codValue] = data.degree[i].perfLearnTm;
                            var exits = false;
                            for (var j = 0; j < colors.length; j++) {
                                if (colors[j].subJet === data.degree[i].codValue) {
                                    exits = true;
                                    break;
                                }
                            }
                            if (!exits){
                                colors.push({subJet: data.degree[i].codValue,color: data.degree[i].codValue2});
                            }
                        }
                    getDegree();

                }
            })
        }

    }

});

//「来週」アイコンのイベントをクリック
$('#nextWeek').click(function () {
    vm.isFirst = true;
    vm.pageTurning = true;
    if (stats == 1){
        vm.date.setDate(vm.date.getDate() + 7);
    }else if (stats ==2) {
        vm.date.setDate(vm.date.getDate() + 36);
    }else if(stats==3){
       vm.date.setMonth(vm.date.getMonth() +6);
    }
    frist=false;
    vm.getInfo();
});
//「先週」アイコンのイベントをクリック
$('#preWeek').click(function () {
    vm.isFirst = true;
    vm.pageTurning = true;
    if (stats == 1){
        vm.date.setDate(vm.date.getDate() - 7);
    }else if (stats ==2) {
        vm.date.setDate(vm.date.getDate() - 36);
    }else if(stats==3){
        vm.date.setMonth(vm.date.getMonth() -6);
    }
    frist=false;
    vm.getInfo();
});

function getDay() {
    var now = new Date();
    vm.date = new Date(now.setDate(now.getDate() - 6));
    stats = 1;
    vm.getInfo(stats)
}
function getWeek() {
    vm.date = new Date();
    stats = 2;
    this.date=new Date();
    vm.getInfo(stats)
}
function getMonth() {
    vm.date = new Date();
    stats = 3;
    vm.getInfo(stats)
}

function getDate(getTalk) {
    for (var i = 0; i <getTalk.length ; i++) {
        var getTime =  new Date(getTalk[i].talkDatime);
        var a = weekArray[getTime.getDay()];
        vm.talkDate[i] =getTime.getMonth()+1 +"/" +getTime.getDate()+ " " +"(" + a + ")";
        // vm.eventTitle[i] = getTalk[i].eventTitle;
    }
}
function F21060() {
    flg = 1;
    $(".ui-jqgrid .ui-jqgrid-btable").css("table-layout", "auto !important");
    $("#add").css("display", "none");
    $("#score").css("display", "contents");
    $("#askTalk").css("color", "#1B9848");
    $("#askAbout").css("color", "#C6C6C6");

    vm.getInit();
}

function toF10507() {
    window.location.href = ctxPath + '/student/F10507.html?stuId=' + param.stuId;
}

function F21049() {
    flg = 2;
    $("#askAbout").css("color", "#1B9848");
    $("#askTalk").css("color", "#C6C6C6");
    $(".ui-jqgrid .ui-jqgrid-btable").css("table-layout", "fixed !important");
    $("#score").css("display", "none");
    $("#add").css("display", "inline");
    vm.getInit();
}
function add() {
    var srcWidth = $(window).width() * 0.35 + "px";
    var srcHeight = $(window).height() * 0.75 + "px";
    layer.open({
        id: 'F21051',
        title:"問合わせ記録",
        type: 2,
        anim: 2,
        skin: "layui-layer-myskin",
        shade: 0.2,
        closeBtn: 0,
        shadeClose: false,
        move: '.layui-layer-title',
        area: [srcWidth, srcHeight],
        resize: false,
        content: ["../pop/F21050.html", 'no'],
        success: function (layero, index) {
        },
        end:function () {
        }
    })
}

function infoPop(id) {
    if(flg==1){
        layer.open({
            id: 'F21045-1',
            title:"面談記録",
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: ['60%', '75%'],
            resize: false,
            content: ["../pop/F21045.html?id=" + id, 'no'],
            success: function (layero, index) {
            },
            end:function () {
                /* 2020/12/23 UT-39 cuikailin modify start */
                //reload();
                /* 2020/12/23 UT-39 cuikailin modify end */
            }
        })
    }else {
        var srcWidth = $(window).width() * 0.5 + "px";
        var srcHeight = $(window).height() * 0.5 + "px";
        layer.open({
            id: 'F210051',
            title:"問い合わせ記録",
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F21050.html?id=" + id  + "&stuId=" +stuId, 'no'],
            success: function (layero, index) {
            },
            end:function () {
            }
        })
    }

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
    var maxValue = 0;
    var interval = 120;
    var allLev = '';
    var seriesDatas = {};
    for (var i = 0; i <7 ; i++) {
        allLev = 0;
        if (stats == 1){
            var date=''
            var nowDate = new Date(vm.date.getFullYear(), vm.date.getMonth(), vm.date.getDate());
            date=new Date(nowDate.setDate(nowDate.getDate()+i));
            var str = weekArray[date.getDay()];
            var ymd = date.Format("yyyy-MM-dd")
            xAxisData.push(date.Format("MM/dd")+"("+str+")");
        } else if (stats == 2 &&i<5) {
            var da='';
            if (vm.date ==''){
                da = new Date()
            }else {
                da=vm.date;
            }

            var date = new Date(da.getFullYear(), da.getMonth(), da.getDate() - da.getDay()+1);
            // var date = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());
            //データのインデックスは、トラバーサルのインデックスの反対です
            var index = 4-i;
            //曜日を計算する
            var day = new Date(date.setDate(date.getDate() - index * 7));
            var ymd = day.Format("yyyy-MM-dd");
            xAxisData.push(date.Format("MM/dd")+"(週)");
        }else if ( stats == 3 && i<6) {
            if (vm.date ==''){
                da = new Date()
            }else {
                da=vm.date;
            }
            var date = new Date(da.getFullYear(), da.getMonth(), +1);
            // var date = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());
            //データのインデックスは、トラバーサルのインデックスの反対です
            var index = 5-i;
            //曜日を計算する
            var month = new Date(date.setMonth(date.getMonth() - index));
            var ymd = month.Format("yyyy-MM-dd");
            xAxisData.push(date.Format("yyyy/MM"));
        }
        for (var j = 0; j <colors.length ; j++) {
            if (!seriesDatas[colors[j].subJet]){
                seriesDatas[colors[j].subJet] = [];
            }
            if (datas[ymd]){
                if (!datas[ymd][colors[j].subJet]) {
                    seriesDatas[colors[j].subJet].push(0);
                }else {
                    allLev += parseInt(datas[ymd][colors[j].subJet]);
                    seriesDatas[colors[j].subJet].push(parseInt(datas[ymd][colors[j].subJet]));
                }
            }else {
                seriesDatas[colors[j].subJet].push(0);
            }
        }
        maxValue = maxValue <= allLev ? allLev : maxValue;
    }
    if (maxValue==0&&stats==1){
        maxValue=600;
    }
    if (maxValue==0&&stats==2){
        maxValue=4200;
    }
    if (maxValue==0&&stats==3){
        maxValue=18000;
    }
    colors.forEach(function (item) {
        titleData.push(item.subJet);
        seriesData.push({
            name: item.subJet,
            type: 'bar',
            stack: 'lev',
            barMaxWidth: '40',
            itemStyle: {
                normal: {color: item.color, label: {show: false}}
            },
            data: seriesDatas[item.subJet]
        });
    });

    subjtNm = xAxisData;
    interval = Math.ceil(maxValue / 5);
    interval = Math.ceil(interval / 6) * 6;
    var option = {
        legend: {
            orient: 'horizontal',
            x:'center',      //凡例は、左、右、中央に設定できます
            y:'bottom',     //凡例は、上下左右に設定できます
            padding:[0,0,0,0],   //凡例を設定できます[上からの距離、右からの距離、下からの距離、左からの距離]
            data: titleData
        },
        calculable: true,
        grid: {y: 20, y2: 70, x2: 10},
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
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {formatter: function (time) {
                        var hours = time/60;
                        var min =time%60;
                        if (min==0){
                            return hours+"時間";
                        }else {
                            return hours.toFixed(1) +"時間"
                        }

                    }},
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
    mychart1.setOption(option);
    if (option && typeof option === "object") {
        mychart1.setOption(option, true);

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
function back() {
    window.location.href = "F21028.html"
}
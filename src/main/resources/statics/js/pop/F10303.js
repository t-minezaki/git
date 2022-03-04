function getParam() {
    var url = decodeURI(location.href);
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = {};
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}
$(window).on('resize', function () {
    // var $content = $('.content');
    // $content.height($(this).height() - 140);
    // $content.find('iframe').each(function() {
    //     $(this).height($content.height());
    // });
}).resize();
var param = getParam();
$(function () {
    var index = parent.layer.getFrameIndex(window.name);// //高度自適
    parent.layer.iframeAuto(index);
    $(".showtF").click(function(){
        $(".check_sub").toggleClass("disNone");
        if(!$(".check_time").hasClass("disNone")){
            $(".check_time").addClass("disNone")
        }
        var index = parent.layer.getFrameIndex(window.name);// //高度自適
        parent.layer.iframeAuto(index);
        var smallH = $(window).height()/2;
        var bigH = $(parent.document).height()/2;
        var stop = bigH - smallH;
        $(".layui-layer-iframe" , parent.document).css("top",stop);
    })
    //'・復習ブロックの場合
    if (param.blockTypeDiv == 'R1') {
        $("h2").html("復習する教科を選択してください");
        // $(".showIput").show();
    }
    if (param.blockTypeDiv == 'P1') {
        $("h2").html("予習する教科を選択してください");
    }
    //・予習ブロックの場合
    // if (param.blockTypeDiv == 'P1') {
    //     $("h2").html( param.blockTypeNm + "の予習計画時間を入力してください");
    //     $("ul").css("display", "none");
    //     // $(".showIput").hide();
    //     $(".showtF").hide();
    //     $(".check_sub").hide();
    //     var index = parent.layer.getFrameIndex(window.name);// //高度自適
    //     parent.layer.iframeAuto(index);
    //    // $(".popDiv").css("height","170px");
    //
    //     $('#demo_select').mobiscroll().select({
    //         mode: "scroller",
    //         display: "inline",
    //         lang: "en",
    //         rows: 5
    //     });
    //     $("#demo_select").change(function () {
    //         var time=Math.floor($("#demo_select").val()/60)+"時間"+$("#demo_select").val()%60+"分";
    //         $(".showTimeRight").html(time);
    //     })
    // }
    if(param.blockTypeDiv=='W1'){
        $("h2").html("学校の宿題教科を選択してください");
        // $(".showIput").show();
    }
    //塾の宿題
    if(param.blockTypeDiv=='V1'){
        $("h2").html("塾の宿題教科を選択してください");
        // $(".showIput").show();
    }
    //学習
    if(param.blockTypeDiv=='S1'){
        $("h2").html("学習教科を選択してください");
        // $(".showIput").show();
    }
    $('#dom_sub').mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5
    });
    var smallH = $(window).height()/2;
    var bigH = $(parent.document).height()/2;
    var stop = bigH - smallH;
    $(".layui-layer-iframe" , parent.document).css("top",stop);
});
var index = parent.layer.getFrameIndex(window.name);
var vm = new Vue({
    el: '#app',
    data: {
        subjtlist: [],
        schy: '',
        // 2020/11/12 zhangminghao modify start
        schyDiv: '',
        defaultSubject: {
            codValue: '選択してください'
        }
        // 2020/11/12 zhangminghao modify end
    },
    mounted: function () {
        this.timeSelect();
        //・予習ブロックの場合
        // if (param.blockTypeDiv == 'P1') {
        //     return;
        // } else {
            //'・復習ブロックの場合
            this.getInfo();
        // }
    },
    updated: function () {
        $('#demo_select').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 3
        });
        $('#dom_sub').mobiscroll().select({
            mode: "scroller",
            display: "inline",
            lang: "en",
            rows: 3
        });

        $("#demo_select").change(function () {
            var time=Math.floor($("#demo_select").val()/60)+"時間"+$("#demo_select").val()%60+"分";
            $(".showTimeRight").html(time);
        });
        $("#dom_sub").change(function () {
            vm.schyDiv = $("#dom_sub").val();
            vm.schy = $("#dom_sub").find("option:selected").text();
            $("#subjt").text($("#dom_sub").find("option:selected").text());
        });

    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F10303/init',
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    if (data.msg != "success") {
                        vm.subjtlist=[];
                        parent.layer.alert(data.msg);
                    }
                    if (data.subjtList) {
                        vm.subjtlist = data.subjtList;
                    }
                    // 2020/11/12 zhangminghao modify start
                    if (param.blockTypeDiv=='S1' && data.defaultSubject) {
                        vm.defaultSubject = data.defaultSubject;
                    }
                    // 2020/11/12 zhangminghao modify end
                },
                error: function () {
                }
            })
        },
        showTime: function () {
            $(".check_time").toggleClass("disNone");
            if(!$(".check_sub").hasClass("disNone")){
                $(".check_sub").addClass("disNone")
            }
            var index = parent.layer.getFrameIndex(window.name);// //高度自適
            parent.layer.iframeAuto(index);
            var smallH = $(window).height()/2;
            var bigH = $(parent.document).height()/2;
            var stop = bigH - smallH;
            $(".layui-layer-iframe" , parent.document).css("top",stop);
        },
        submitFn: function () {
            //checkdemo_select
            // 2020/11/20 zhangminghao modify start
            // 学習教科のデフォルト値を表示する
            if (vm.schyDiv == "" && param.blockTypeDiv == "S1"){
                vm.schyDiv = vm.defaultSubject.codCd;
                vm.schy = vm.defaultSubject.codValue;
            }
            if (vm.schyDiv == "" && (param.blockTypeDiv == 'R1' || param.blockTypeDiv == 'W1' || param.blockTypeDiv == 'V1' || param.blockTypeDiv == 'P1') ) {
                parent.layer.alert($.format($.msg.MSGCOMN0028, "教科"));
                return;
            }
            // 2020/11/20 zhangminghao modify end
            if($("#memo").val().length>50){
                parent.layer.alert($.format($.msg.MSGCOMD0011, "メモ","50"));
                return;
            }
            var userParam = JSON.parse(parent.tmpEvent.userParam);
            // 2020/11/13 zhangminghao modify start
            if (param.blockTypeDiv == 'R1' || param.blockTypeDiv == 'W1' || param.blockTypeDiv == 'V1'
                || param.blockTypeDiv == 'P1' || param.blockTypeDiv == 'S1') {
            // 2020/11/13 zhangminghao modify end
                userParam.subjtDiv = vm.schyDiv;
                userParam.subjtNm = vm.schy;
                userParam.blockDispyNm = $("#memo").val()?$("#memo").val():vm.schy;
                parent.tmpEvent.title = vm.schy;
            }
            //予習ブロックの場合
            // if(param.blockTypeDiv == 'P1'){
            //     userParam.subjtNm = param.subjtNm;
            //     userParam.subjtDiv = param.subjtDiv;
            //     userParam.blockDispyNm = $("#memo").val();
            //     parent.tmpEvent.title = param.subjtNm;
            // }
            // 終了時間
            parent.tmpEvent.end = parent.tmpEvent.start.clone().add($("#demo_select").val() * 60 * 1000);
            parent.tmpEvent.userParam = JSON.stringify(userParam);
            // 更新処理
            parent.$('#calendar').fullCalendar('renderEvent', parent.tmpEvent, false);
            parent.updateEvent(parent.tmpEvent, index);
            parent.layer.close(index);
            // window.parent.location.reload();
        },
        cancelFn: function () {
            parent.layer.close(index);
        },
        //最大「720」分選択可
        timeSelect: function () {
            var time = "";
            for (var i = 15; i <= 720; i += 15) {
                var t=Math.floor(i/60)+"時間"+i%60+"分";
                time += "<option value='" + i + "'>" + t +"</option>";
            }
            $('#demo_select').html(time);
            $('#demo_select').find("option").each(function () {
                if ($(this).val() == 30){
                    $(this).attr("selected",true);
                }
            })
            $(".showTimeRight").html("0時間30分");
        }
    }

});

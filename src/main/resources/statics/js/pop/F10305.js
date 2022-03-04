function parseUrl() {
    var url = decodeURI(location.href);
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}
var v=parseUrl();
var vm = new Vue({
    el: "#changePlTm",
    data: {
        text:'',
        smallBlock:'',
        smallBlockNm:'',
        bigBlock:'',
        weekday: v['weekday'],
        btDiv: v['blockTypeDiv'],
        planLearnStartTime: v['planLearnStartTime'],
        planLearnEndTime: '',
        minutes:'',
        delBtnFlg:v['delBtnFlg'],
        /* 2020-11-18 liyuhuan V9.0 modify start */
        subjtNm:v['blockDispyNm'],
        /* 2020-11-18 liyuhuan V9.0 modify end */
        memo:v['memo']
    },
    updated: function () {
        this.timeSelect();
        //「学習」の場合、
        if(vm.minutes!=null){
            var arr=$('#demo_select').find("option");
            for(var i=0;i<arr.length;i++){
                if(arr.eq(i).val()==vm.minutes){
                    arr.eq(i).attr("selected","selected");
                    var t=Math.floor($("#demo_select").val()/60)+"時間"+$("#demo_select").val()%60+"分";
                    $(".showTimeRight").html(t);
                }
            }
        }else{
            $("#thirty").attr("selected","selected");
        }
        $(function () {
            $('#demo_select').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "en",
                rows: 3
            });
        })

    },
    mounted: function () {
        this.showData();
    },
    methods: {
        showData: function () {
            $.ajax({
                url: ctxPath + '/pop/F10305/info',
                data:{
                    'type':v['type'],
                    'blockTypeDiv':v['blockTypeDiv'],
                    'blockId':v['blockId'],
                    'wppId':v['wppId'],
                    'startTime':v['startTime'],
                    // 2020/11/11 modify LiYuHuan start
                    'endTime':v['endTime'],
                    'blockDispyNm':v['blockDispyNm']
                    // 2020/11/11 modify LiYuHuan end
                },
                type: 'GET',
                success: function (data) {
                    //教科書デフォルトターム情報
                    vm.text=data.text;
                    if(data.minutes){
                        vm.minutes=data.minutes;
                    }
                    //小分類ブロック
                    if(data.smallBlock){
                        vm.smallBlock=data.smallBlock;
                    }
                    //大分類ブロック
                    if(data.bigBlock){
                        vm.bigBlock=data.bigBlock;
                    }
                    // 2020/11/11 modify LiYuHuan start
                    // if(vm.btDiv != 'S1'){
                    　　//｢その他｣の場合、
                        if(vm.btDiv =='O1' || vm.btDiv =='O2' || vm.btDiv =='O3'){
                            if(data.smallBlockNm){
                                vm.smallBlockNm = data.smallBlockNm;
                                if(data.smallBlockNm.blockDispyNm != null){
                                    if (vm.smallBlockNm.subjtNm!=null){
                                        vm.smallBlockNm.blockDispyNm = vm.smallBlockNm.subjtNm + ' ' + vm.smallBlockNm.blockDispyNm;
                                        vm.memo = data.smallBlockNm.blockDispyNm.replace(data.smallBlockNm.blockDispyNm.split(' ')[0]+' ','');
                                    } else {
                                        vm.smallBlockNm.blockDispyNm =vm.smallBlockNm.blockDispyNm;
                                        vm.memo = data.smallBlockNm.blockDispyNm.replace(data.smallBlockNm.blockDispyNm.split(' ')[0]+' ','');
                                    }
                                }
                            }
                        }else{
                            //「復習」「予習」「学校の宿題」「塾の宿題」の場合
                            if(data.smallBlockNm){
                                vm.smallBlockNm = data.smallBlockNm;
                                vm.memo = data.smallBlockNm.blockDispyNm;
                                }
                            }
                        // }
                    // 2020/11/11 modify LiYuHuan end
                    //曜日
                    if (data.weekday) {
                        vm.weekday = data.weekday;
                    }
                    //計画学習開始時間
                    if (data.planLearnStartTime) {
                        vm.planLearnStartTime = data.planLearnStartTime;
                    }
                    //算出された終了時間> 23:45の場合、（翌日）の文字を後ろで表示される
                    if (data.planLearnEndTime) {
                        //計画学習終了時間 <　計画学習開始時間の場合、（翌日）の文字を後ろで表示される
                        if(data.planLearnEndTime < data.planLearnStartTime){
                            data.planLearnEndTime = data.planLearnEndTime + "(翌日)";
                        }
                        //終了時間> 23:45の場合、（翌日）の文字を後ろで表示される
                        if(data.planLearnStartTime < data.planLearnEndTime && data.planLearnEndTime > "23:45"){
                            data.planLearnEndTime = data.planLearnEndTime + "(翌日)";
                        }
                        //計画学習終了時間
                        vm.planLearnEndTime = data.planLearnEndTime;
                    }
                    //生徒計画学習時間
                    if (data.stuPlanLearnTm) {
                        vm.stuPlanLearnTm = data.stuPlanLearnTm;
                    }
                },
                error: function () {
                    alert('load error');
                }
            })
        },
        showTime:function(){
            $(".check_time").toggleClass("disNone");
            if(!$(".check_time").hasClass("disNone")) {
                $(".showIput").hide();
                // $(".showIput").toggleClass("disNone");
            }else{
                $(".showIput").show();
            }
        },
        cancelBtn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        updateBtn: function () {
            // イベントを取得する
            var events = parent.$("#calendar").fullCalendar("clientEvents");
//            var checkFlg = false;
            parent.tmpEvent.end = parent.tmpEvent.start.clone().add($("#demo_select").val() * 60 * 1000);

            //メモ
            parent.tmpEvent.memo=$('#memo').val();
            // 開始時間
            var start = Date.parse(parent.tmpEvent.start);
            // 終了時間
            var end = Date.parse(parent.tmpEvent.end);

            if (v['addFlg'] == "true") {
                parent.$('#calendar').fullCalendar('renderEvent', parent.tmpEvent, false);
            } else {
                parent.$('#calendar').fullCalendar('updateEvent', parent.tmpEvent);
            }
            // 更新処理
            var index = parent.layer.getFrameIndex(window.name);
            parent.updateEvent(parent.tmpEvent, index);
//            parent.layer.close(index);
        },
        delBtn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.$("#calendar").fullCalendar("removeEvents", function(_event, _index, _events) {
                if (_event._id == v['eventid']) {
                    // 削除処理
                    parent.deleteEvent(_event, index);
                    return true;
                }
                return false;
            });
//            parent.layer.close(index);
         },
        //最大「720」分選択可
        timeSelect: function () {
            var time = "";
            for (var i = 15; i <= 720; i += 15) {
                var t=Math.floor(i/60)+"時間"+i%60+"分";
                time += "<option value=' " + i + "'>" + t +"</option>";
            }
            $('#demo_select').html(time);
        }

    }
})

function selectChange() {
    var t=Math.floor($("#demo_select").val()/60)+"時間"+$("#demo_select").val()%60+"分";
    $(".showTimeRight").html(t);
}
var param = getParam();

$(function () {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $(".content").css("padding-top", "10px");
                    $(".input").css("font-size", "4vw").css("height", "18px");
                    $("span").css("font-size", "4vw").css("height", "18px").css("line-height", "18px");
                    $(".float").css("font-size", "11px");
                    $("#remarkDiv").css("height", "25vh")
                } else if (/iPad/i.test(navigator.userAgent)) {
                    $(".input").css("font-size", "3vw");
                    $("span").css("font-size", "3vw");
                } else {
                    // alert("other")
                }
            } catch (e) {
            }
        } else {
            // alert("456")
        }
    } else {
        if (/Macintosh/i.test(navigator.userAgent)){
            $(".input").css("font-size", "3vw");
            $("span").css("font-size", "3vw");
        }
    }
})
var vm = new Vue({
    el: '#content',
    data: {
        reasons: [],
        schys: [],
        contentVal: '',
        dayVal: '',
        reasonVal: '',
        remarkVal: '',
        lateTimeTemp: 0,
        stuId: '',
        contents: []
    },
    mounted: function () {
        this.setUp();
    },
    updated: function() {
        if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
            if (window.location.href.indexOf("?mobile") < 0) {
                try {
                    if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                        $(".btn").css("font-size", "4vw").css("height", "15px").css("line-height", "13px");
                        $("#back-btn").css("width", "40%").css("height", "10vh")
                        $("#submit-btn").css("width", "40%").css("height", "10vh")
                    } else if (/iPad/i.test(navigator.userAgent)) {
                        $(".btn").css("font-size", "3vw");
                    } else {
                        // alert("other")
                    }
                } catch (e) {
                }
            } else {
                // alert("456")
            }
        } else {
            if (/Macintosh/i.test(navigator.userAgent)){
                $(".btn").css("font-size", "3vw");
            }
        }
    },
    methods: {
        //初期化
        setUp: function () {
            //アイコンの位置を調整する
            $('#dayPic').css("top", -((($('#day').outerHeight() - 16) / 2) + 17)).css('right', -($('#day').outerWidth() - 20));
            $.ajax({
                url: ctxPath + '/pop/F21016/init',
                type: 'GET',
                dataType: "json",
                success: function (data) {
                    if (data.reasons) {
                        vm.reasons = data.reasons;
                        vm.schys = data.schys;
                        vm.contents = data.contents;
                    }
                }
            });
            laydate.render({
                elem: '#day',
                min: new Date().format('Y-m-d'),
                format: 'yyyy/MM/dd',
                value: new Date(),
                eventElem: '#dayPic',
                btns:['clear'],
                trigger: 'click',
                // done: function (value, date) {
                //     if (vm.currentDay != value) {
                //         vm.currentDay = value;
                //     }
                // }
            });
            $('#stuName').select2({
                ajax: {
                    url: ctxPath + '/pop/F21016/getStudentList',
                    type: 'GET',
                    data: function (params) {
                        return {
                            stuName: params.term.trim(),
                            schy: $('#schy').val()
                        };
                    },
                    delay: 250,
                    dataType: 'json',
                    processResults: function (data) {
                        var results = [];
                        var dataSet = data.studentList;
                        for (var i = 0; i < dataSet.length; i++) {
                            results.push({id: dataSet[i].stuId, text: dataSet[i].stuName})
                        }
                        return {
                            results: results
                        };
                    },
                    cache: true
                },
                language: 'ja',
                placeholder: '生徒名を入力してください',
                minimumInputLength: 1,
                templateSelection: stuFormatRepoSelection // omitted for brevity, see the source of this page
            });
            $('#stuNameDiv').change(function () {
                $('#stuNameDiv span.select2-selection.select2-selection--single').css('border', '1px solid #aaa');
            });
            //スペースの高さを調整する
            setTargetControlHeight('content', 'body'
                , 'header');
            setTargetControlHeight('remarkDiv', 'content'
                , 'contentDiv', 'dayDiv', 'reasonDiv'
                , 'btnDiv', 'schyDiv', 'stuNameDiv');
        },
        getId: function(index){
            return index == 0 ? 'change' : 'absence';
        }
    }
});

//戻るボタン押下時
function back() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

// 登録ボタン押下時
function submit() {
    if (vm.stuId == '') {
        $('#stuNameDiv span.select2-selection.select2-selection--single').css('border', '1px solid red');
        var idx = parent.layer.confirm($.msg.MSGCOMN0134, {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            yes: function () {
                parent.layer.close(idx);
            }
        });
        return;
    }
    vm.contentVal = $('#change').hasClass('active') ? '0' : '1';
    if($('#day').val() ==""){
        var date = new Date();
         vm.dayVal = date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " +date.getHours()+ ":" + date.getMinutes()
        // var year = now.getFullYear();
        // var month = now.getMonth()+1;
        // var day = now.getDate();
        // if (month<10){
        //     month = "0"+month;
        // }
        // if (day<10){
        //     day = "0" + day;
        // }
        // vm.dayVal = year +"/"+month+"/"+day;
        // vm.dayVal = now.Format("yyyy/MM/dd HH:mm");
    }else {
        vm.dayVal = $('#day').val();
    }
    vm.reasonVal = $('#reason').val();
    if (vm.reasonVal == null || vm.reasonVal == ''){
        var idx = layer.confirm($.format($.msg.MSGCOMN0143, '理由'), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            yes: function () {
                layer.close(idx);  // 关闭layer
            }
        })
        return;
    }
    vm.remarkVal = $('#remark').val();
    $.ajax({
        url: ctxPath + '/pop/F21016/submit',
        type: 'GET',
        data: {
            stuId: vm.stuId,
            type: vm.contentVal,
            lateDay: vm.dayVal,
            reason: vm.reasonVal,
            remark: vm.remarkVal
        },
        dataType: "json",
        success: function (data) {
            if (data.code != 0) {
                var idx = layer.confirm(data.msg, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        layer.close(idx);  // 关闭layer
                    }
                })
            } else {
                // var idx = layer.confirm($.format($.msg.MSGCOMN0014, '遅刻・欠席連絡情報'), {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     yes: function () {
                        parent.reload();
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);  // 关闭layer
                //     }
                // })
            }
        }
    });
}


function stuFormatRepoSelection(repo) {
    if (repo.element) {
        vm.stuId = repo.id;
    }
    return repo.text;
}

function choose(e) {
    //コンテンツの切り替え
    if ($(e).attr('id') == 'change') {
        $(e).addClass("active");
        $('#absence').removeClass("active");
    } else {
        $(e).addClass("active");
        $('#change').removeClass("active");
    }
}
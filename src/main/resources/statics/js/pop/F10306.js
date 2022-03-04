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
var v = parseUrl();
var vm = new Vue({
    el: '#app',
    data: {
        term: '',
        wpp: '',
        updateTime: '',
        textDff:'',
        remainDispFlg:false,
        dataFlg:''
    },
    mounted: function () {
        this.getInfo();

    },
    updated: function () {
        if (vm.wpp == null) {
            $(".thru_s_close").css("margin-left", "25%");
        } else {
            if (((vm.wpp.remainDispFlg == null||vm.wpp.remainDispFlg == '1')&&vm.dataFlg)||vm.wpp.remainDispFlg=='3') {
                $(".thru_s_first").css("display", "block");
                $(".thru_div").css("display", "block");
                //}
            } else {
                $(".thru_s_close").css("margin-left", "70px");
            }
        }
    },
    methods: {
        getInfo: function () {
            var v = parseUrl();
            $.ajax({
                url: ctxPath + '/pop/F10306/init',
                data: {
                    'termId': v['stuTermPlanId'],
                },
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg);
                    }else {
                        vm.term = data.term;
                        vm.wpp = data.wpp;
                        vm.updateTime = data.updateTime;
                        vm.remainDispFlg = data.remainDispFlg;
                        vm.textDff = data.textDff;
                        vm.dataFlg=data.dataFlg;
                    }
                },
                error: function () {
                }
            })
        },
        submitFn: function () {
            $.ajax({
                url: ctxPath + '/pop/F10306/del',
                data: {
                    'id': vm.wpp.id,
                    'updateTime': vm.updateTime
                },
                type: 'POST',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg);
                    }
                    else {
                        /*if(v['frame']=='f10301'){
                            window.location.href = "../student/F10301.html";
                        }*/
                        var perfInfo = parent.vm.planPerf[vm.wpp.id];
                        // 生徒削除フラグ
                        perfInfo.stuDelFlg = data.stuDelFlg;
                        // 積み残し対象フラグ
                        perfInfo.remainDispFlg = data.remainDispFlg;
                        Vue.set(parent.vm.planPerf, vm.wpp.id, perfInfo);
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                },
                error: function () {
                }
            })
        },
        cancelFn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    }

});
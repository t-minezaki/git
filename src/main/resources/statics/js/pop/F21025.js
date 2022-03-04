var os = function() {
    var ua = navigator.userAgent,
        isWindowsPhone = /(?:Windows Phone)/.test(ua),
        isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
        isAndroid = /(?:Android)/.test(ua),
        isFireFox = /(?:Firefox)/.test(ua),
        isChrome = /(?:Chrome|CriOS)/.test(ua),
        isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isFireFox && /(?:Tablet)/.test(ua)),
        isPhone = /(?:iPhone)/.test(ua) && !isTablet,
        isPc = !isPhone && !isAndroid && !isSymbian;
    return {
        isTablet: isTablet,
        isPhone: isPhone,
        isAndroid: isAndroid,
        isPc: isPc
    };
}();

if (os.isTablet) {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/pop/F21025-1.css"}).appendTo("head");
} else {
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/pop/F21025.css"}).appendTo("head");
}
var param = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        stuName: '',
        stuId:'',
        telnum:'',
        content: '',
        dataTime:'',
        reason: '',
        bikou: '',
        cretDatime:'',
        reload: false
    },
    mounted: function () {
        this.setUp();
    },
    methods: {
        setUp: function () {
            if (param.corrspdSts !== "0" && param.corrspdSts !== "3") {
                $(".submit-btn").css("display", "none");
            }
            if (param.stuName){
                this.stuName = decodeURIComponent(param.stuName);
            }else{
                this.stuName = decodeURIComponent(param.stuFN) + ' ' + decodeURIComponent(param.stuFLn);
            }
            this.telnum = decodeURIComponent(param.telnum);
            this.dataTime = param.tgtYmd;

            $.ajax({
                url: ctxPath + '/pop/F21025/detail',
                type: 'GET',
                data: {
                    id: param.id
                },
                success: function (data) {
                    if (data.code == 0) {
                        var detail = data.detail;
                        vm.content = detail.content;
                        vm.reason = detail.reason;
                        vm.bikou = detail.bikou;
                        vm.cretDatime = data.detail.cretDatime;
                        vm.stuId = detail.afterUsrId;
                    } else {
                        var msg = parent.layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                parent.layer.close(msg);
                            }
                        });
                    }
                }
            });

            // setTargetControlHeight('remarkDiv', 'content', 'dayDiv', 'contentDiv', 'reasonDiv', 'btnDiv', 'line1', 'line3', 'line4', 'line5');
        },
        //電話のURLを作成します
        getHref: function (tel) {
            parent.window.location.href='tel://' + tel;
        }
    }
});
function submit() {
    var index = parent.layer.getFrameIndex(window.name);
    //対応済
    // if (param.corrspdSts == '3') {
    //     var msg = parent.layer.confirm($.msg.MSGCOMN0133, {
    //         skin: 'layui-layer-molv',
    //         title: '確認',
    //         closeBtn: 0,
    //         anim: -1,
    //         btn: ['確認'],
    //         btn1: function () {
    //             parent.layer.close(msg);
    //             parent.layer.close(index);
    //             // parent.location.reload();
    //         }
    //     });
    // } else {
        //対応状態更新する
        $.ajax({
            url: ctxPath + '/pop/F21025/update',
            type: 'GET',
            data: {
                id: param.id
            },
            success: function (data) {
                if (param.stuName){
                    param.stuName = vm.stuName;
                    param.stuId = vm.stuId;
                    param.telnum = vm.telnum;
                    param.dateTime = decodeURIComponent(param.dateTime);
                    param.corrspdSts = '1';
                    if (parent.vm && parent.vm.e) {
                        $(parent.vm.e).attr('onclick', 'details(this, ' + JSON.stringify(param) + ')');
                    }
                }else{
                    vm.reload = true;
                }
                if(data.code != 0){
                    var idx = layer.confirm(data.msg, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        yes: function () {
                            layer.close(idx);
                            parent.location.reload();
                        }
                    })
                } else {
                    //お知らせ
                    $.ajax({
                        url: ctxPath + '/pop/F21025/notice',
                        type: 'GET',
                        data: {
                            id: param.id,
                            guardName: decodeURIComponent(param.guardName)
                        },
                        success: function (data) {
                            parent.location.reload();
                        }
                    });
                }
            }
        });
    // }
}
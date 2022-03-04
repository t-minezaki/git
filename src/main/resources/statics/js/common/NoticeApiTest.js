var vm = new Vue({
    el: '#content',
    data: {
        json1: '',
        json2: '',
        json3: '',
        json4: '',
        deviceToken:'',
        url_one: '',
        url_two: '',
        url_three: '',
        url_four: '',
        userId:'',
        key:''
    },
    methods: {
        sendMsg: function (index) {
            var params ;
            if (index === 1){
                params= $('#sendMsg1').val();
            } else if (index === 2){
                params = $('#sendMsg2').val();
            } else if (index === 3){
                params = $('#sendMsg3').val();
            } else if (index === 4){
                params = $('#sendMsg4').val();
                param = JSON.parse($('#sendMsg4').val());
                msgId = param.msgId;
                deviceToken = param.deviceToken;
                stuid = param.stuid;
                this.url_four = window.location.protocol + '//' + location.host + ctxPath + "/msgLogin?msgId=" + msgId + "&deviceToken=" + deviceToken + "&stuid=" + stuid + "&equipment=" + 'phone';
                this.json4 = this.url_four;
                return ;
            }
            $.ajax({
                type: "POST",
                url: '../PUSHAPI',
                contentType: "application/json",
                data: params,
                success: function(data){
                    if (index === 1){
                        vm.json1 = vm.format(JSON.stringify(data));
                        if (data.code === 200){
                            userId = data.userId;
                            vm.url_one = window.location.protocol + '//' + location.host + ctxPath + "/pLogin?userid=" + userId + "&key=" + $.md5(userId + "appmsg") + "&equipment=phone";
                        }
                    } else if (index === 2){
                        vm.json2 = vm.format(JSON.stringify(data));
                        if (data.code === 200){
                            vm.url_two = '..' + data.url;
                        }
                    } else if (index === 3){
                        vm.url_three = window.location.protocol + '//' + location.host + ctxPath + "/logout";
                        vm.json3 = vm.format(JSON.stringify(data));
                    }
                }
            });
        },
        format: function (text) {
            text = text.replace(/"([^"]*?)":/g, '\n     "$1":');
            return text.replace(/}/g, '\n}');
        },
        toMainPage: function () {
            if (vm.url_one !== '') {
                var srcHeight = "736px";
                var srcWidth = "414px";
                layer.open({
                    id: 'F30002',
                    type: 2,
                    title: ['', 'display:none;'],
                    shade: 0.1,
                    closeBtn: 2,
                    shadeClose: false,
                    move: '.layui-layer-title',
                    area: [srcWidth, srcHeight],
                    fixed: true,
                    resize: false,
                    background: '#F0F0F0',
                    content: [vm.url_one],
                    end: function () {
                    }
                })
            }
        },
        toNotice:function(){
            if (vm.url_four !== '') {
                var srcHeight = "736px";
                var srcWidth = "414px";
                layer.open({
                    id: 'F30002',
                    type: 2,
                    title: ['', 'display:none;'],
                    shade: 0.1,
                    closeBtn: 2,
                    shadeClose: false,
                    move: '.layui-layer-title',
                    area: [srcWidth, srcHeight],
                    fixed: true,
                    resize: false,
                    background: '#F0F0F0',
                    content: [vm.url_four],
                    end: function () {
                    }
                })
            }
        },
        toF40006: function () {
            if (vm.url_two !== '') {
                var srcHeight = "736px";
                var srcWidth = "414px";
                layer.open({
                    id: 'F30002',
                    type: 2,
                    title: ['', 'display:none;'],
                    shade: 0.1,
                    closeBtn: 2,
                    shadeClose: false,
                    move: '.layui-layer-title',
                    area: [srcWidth, srcHeight],
                    fixed: true,
                    resize: false,
                    background: '#F0F0F0',
                    content: [vm.url_two],
                    end: function () {
                    }
                })
            }
        },
        logout:function () {
            layer.confirm("ログアウトしますか？", {
                btn: ['キャンセル', '確認'], title: "確認", btn2: function () {
                    window.top.location.href = vm.url_three;
                    return false;
                }
            });
        }
    }
});
function getEquipment() {
    var isHWPad = false;
    var hh = window.screen.height;
    var ww = window.screen.width;
    if (new Number(parseFloat(hh/ww) - parseFloat(16/10)).toFixed(2) == 0 || new Number(parseFloat(ww/hh) - parseFloat(16/10)).toFixed(2) == 0) {
        isHWPad = true;
    }
    var os = function () {
        var ua = navigator.userAgent,
            isWindowsPhone = /(?:Windows Phone)/.test(ua),
            isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
            isAndroid = /(?:Android)/.test(ua),
            isFireFox = /(?:Firefox)/.test(ua),
            isChrome = /(?:Chrome|CriOS)/.test(ua),
            isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isChrome && /(?:Tablet)/.test(ua)),
            isPhone = /(?:iPhone)/.test(ua) && !isTablet,
            isTosh = /Macintosh/i.test(ua),
            isIPad = /iPad/i.test(ua),
            isPc = !isPhone && !isAndroid && !isSymbian;
        return {
            isTablet: isTablet,
            isPhone: isPhone,
            isAndroid: isAndroid,
            isPc: isPc,
            isTosh: isTosh,
            isIPad: isIPad
        };
    }();
    if (os.isTosh || isHWPad || os.isIPad) {
        return 'tablet'
    }
    if (os.isAndroid || os.isPhone) {
        return 'phone';
    }
    return 'otherEquip';
}
$("#BTGKA1001").click(function () {
    $.ajax({
        url:ctxPath + '/common/BTGKA1001',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1002").click(function () {
    $.ajax({
        url:ctxPath + '/common/BTGKA1002',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1004").click(function () {
    $.ajax({
        url:ctxPath + '/manager/BTGKA1004/importCsvFile',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1005").click(function () {
    $.ajax({
        url:ctxPath + '/manager/BTGKA1005/prepare',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1006").click(function () {
    $.ajax({
        url:ctxPath + '/manager/BTGKA1006/importDateFromGoogleSheet',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1012").click(function () {
    $.ajax({
        url:ctxPath + '/common/BTGKA1012',
        type:'GET',
        success:function (data) {
        }
    })
});
$("#BTGKA1013").click(function () {
    $.ajax({
        url:ctxPath + '/manager/BTGKA1013/updateDelFlgByDeviceId',
        type:'GET',
        success:function (data) {
            alert(data.msg);
        }
    })
});
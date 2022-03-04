var params = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        message: {}
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/pop/F21057/detail',
                type: 'GET',
                data: {
                    messageId: params.id
                },
                success: function (data) {
                    if (data.code == 0) {
                        if (data.message) {
                            data.message.messageTitle = decodeURIComponent(data.message.messageTitle);
                            data.message.usrNm = decodeURIComponent(data.message.usrNm);
                            data.message.messageCont = decodeURIComponent(data.message.messageCont);
                            vm.message = data.message;
                            parent.$('.layui-layer-title').html(vm.message.messageTitle);
                            parent.$('.layui-layer-title').prop('style', 'font-weight:bold;text-align:left !important;padding-left:4%;');
                            var conTent;
                            if (data.message.attachFilePath) {
                                var files = data.message.attachFilePath.split(',');
                                var tag = '';
                                for(let i = 0; i < files.length; i++){
                                    var str = files[i].substring(files[i].lastIndexOf('\\') + 1);
                                    str = str.slice(str.lastIndexOf("/") + 1);
                                    tag += "<a href='"+ files[i] +"' style='color:red;text-decoration: none;border-bottom: 0.5vh solid red;' download='"+ str +"'>"+ handleFileName(files[i]) +"</a><br>";
                                }
                            /* 2020/12/8  V9.0 cuikailin modify start */
                                conTent = vm.message.messageCont + tag;
                            }else {
                                conTent = vm.message.messageCont;
                            }
                            $('.content').html(conTent);
                            /* 2020/12/8  V9.0 cuikailin modify end */
                            // 2021/1/27 huangxinliang modify start
                            $(".hyper_link").click(function (e) {
                                window.open($(this).text(), 'target', '');
                            });
                            // 2021/1/27 huangxinliang modify end
                        }
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
        }
    }
});

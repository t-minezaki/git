var params = getParam();
var vm = new Vue({
    el: '#content',
    data: {
        messageTitle: '',
        askItemName: '',
        talkItemName: '',
        askAndTalkList: [],
        noteCont: '',
        askDisplayFlg: false,
        talkDisplayFlg: false,
        /* 2021/1/13 UT-116 cuikailin modify start */
        noteTitle:''
        /* 2021/1/13 UT-116 cuikailin modify end */
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            var messageId = params.messageId;
            $.ajax({
                url: ctxPath + '/student/F11020/init',
                data: {
                    messageId: messageId
                },
                type: 'get',
                datatype: 'json',
                success: function (data) {
                    /* 2021/1/13 UT-116 cuikailin modify start */
                    if (data.askAndTalkList && data.askAndTalkList.length > 0 ) {
                        vm.noteTitle = "備考";
                    /* 2021/1/13 UT-116 cuikailin modify end */
                        vm.askAndTalkList = data.askAndTalkList;
                        vm.noteCont = vm.askAndTalkList[0].noteCont;
                        var askCount = 0;
                        var talkCount = 0;
                        for (var i = 0; i < vm.askAndTalkList.length; i++) {
                            if (vm.messageTitle === '') {
                                vm.messageTitle = vm.askAndTalkList[i].messageTitle;
                            }
                            if (vm.askItemName === '' && vm.askAndTalkList[i].itemTypeDiv === '0') {
                                vm.askItemName = vm.askAndTalkList[i].codValue;
                                askCount++;
                            }
                            if (vm.talkItemName === '' && vm.askAndTalkList[i].itemTypeDiv === '1') {
                                vm.talkItemName = vm.askAndTalkList[i].codValue;
                                talkCount++;
                            }
                            if (!vm.askDisplayFlg && vm.askAndTalkList[i].askDispFlg === '1' && askCount > 0) {
                                vm.askDisplayFlg = true;
                            }
                            if (!vm.talkDisplayFlg && vm.askAndTalkList[i].talkDispFlg === '1' && talkCount > 0) {
                                vm.talkDisplayFlg = true;
                            }
                        }
                    }
                }
            });
        }
    }
});
function previewImg(src) {
    /* 2021/02/01 liyuhuan add start*/
    var imgHtml = "<img id='preview' src='" + decodeURIComponent(src).replace(/\%20/g," ") + "' />";
    img = new Image();
    img.src= decodeURIComponent(src).replace(/\%20/g," ");
    var loadingLoop = layer.load(1, {
        shade: [0.5, '#CCC']
    });
    img.onload = function () {
        if(img.complete){
            layer.close(loadingLoop);
        }
        var w = document.body.scrollWidth*0.8;
        var h = document.body.scrollHeight*0.8;
        var tempWidth;
        var tempHeight;
        if(img.naturalWidth/img.naturalHeight >= w/h){
            if (img.naturalWidth> w){
                tempWidth = w;
                tempHeight = (img.naturalHeight * w)/img.naturalWidth;
            } else{
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }else {
            if (img.naturalHeight>h){
                tempHeight = h;
                tempWidth = (img.naturalWidth * h)/img.naturalHeight;
            } else {
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }
        var height = tempHeight + "px";
        var width = tempWidth + "px";
        layer.open({
            type: 1,
            offset: 'auto',
            closeBtn:1,
            area: [width,height],
            shadeClose: false,
            scrollbar: false,
            title: "",
            content: imgHtml,
            cancel: function () {
            }
        });
    };
    return false;
    /* 2021/02/01 liyuhuan add end*/
}
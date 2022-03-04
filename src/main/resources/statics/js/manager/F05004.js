var ue;
var param=getParam();
var vm = new Vue({
    el: ".page",
    data: {
        org: '',
        notice:'',
        noticeCont:'',
        stuList:[],
        dto:'',
        eachCount:0,
        path:''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F05004/init',
                type: 'GET',
                data: {
                    noticeId:param.id
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    /*2020/11/19 liyuhuan add start */
                    if (data.notice.attachFilePath!="" && data.notice.attachFilePath!=null) {
                        //一致文字列
                        var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                        var re = new RegExp(rgx);
                        var fileList = data.notice.attachFilePath.split(',');
                        var tag = '';
                        for (var i=0;i<fileList.length;i++ ){
                            if (IsPicture(fileList[i])) {
                                var src = encodeURIComponent(fileList[i].replace(/\\/g,"/"));
                                tag  +="<a id='showFile' onclick=previewImg('"+src+"')>"+ handleFileName(fileList[i]) +"</a>";
                            }else if (!re.test(fileList[i].replace(/\\/g,"/"))) {
                                tag +="<a id='showFile' onclick='pdfClick()' title='"+ fileList[i] +"' >"+ handleFileName(fileList[i]) +"</a>";
                            }else {
                                tag +="<a onclick='pdfClick()' href='"+ fileList[i]+"' >"+ handleFileName(fileList[i]) +"</a>";
                            }
                            tag +="<br>"
                        }
                        $('.drag_area').append(tag);
                        // for (var i = 0; i < fileList.length; i++) {
                        //     var str = fileList[i];
                        //     var path = str.slice(str.lastIndexOf("/") + 1);
                        //     var tag = '<a href="' + str + '">' + path + '</a><br>';
                        //     $('.drag_area').append(tag);
                        // }
                    }
                    /*2020/11/19 liyuhuan add end */
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if(data.notice){
                        vm.notice=data.notice;
                        vm.noticeCont = vm.notice.noticeCont;
                        if (vm.noticeCont==null){
                            $("#noticeCont").html("");
                        } else {
                            $("#noticeCont").html(decodeURIComponent(vm.noticeCont));
                            //背景色
                            uParse('#noticeCont', {
                                rootPath: '../plugins/ueditor-1.4.3.3'
                            })
                        }
                    }
                    if(data.stuList){
                        vm.stuList=data.stuList;
                    }
                    if(data.dto){
                        vm.dto=data.dto;
                    }
                    if(data.eachCount){
                        vm.eachCount=data.eachCount;
                    }
                }
            })
        }
    }
});



//戻るボタン押下
$("#backBtn").click(function () {
    window.location.href = './F05001.html'
});
//2020/11/26 liyuhuan add start
function IsPicture(path) {
    {
        var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|.svg|"
        if(path.indexOf(".")>-1)
        {
            var p = path.lastIndexOf(".");
            var strPostfix=path.substring(p,path.length) + '|';
            strPostfix = strPostfix.toLowerCase();
            if(strFilter.indexOf(strPostfix)>-1)
            {
                return true;
            }
        }
        return false;
    }
}

function previewImg(src) {
    /* 2020/12/09 liguangxin add start*/
    var imgHtml = "<img id='preview' style='max-height: 100%;min-width: 50%' src='" + decodeURIComponent(src).replace(/\%20/g," ") + "' />";
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
    /* 2020/12/09 liguangxin add end*/
}
//2020/11/26 liyuhuan add end
function pdfClick() {
    // var path = decodeURIComponent($("#showFile").attr("href"));
    var path = window.location.protocol + '//' + location.host + ctxPath + $("#showFile").attr("title").substring(2,$("#showFile").attr("title").length).replace(/\\/g,"/");
    // window.location.href = path;
    path = encodeURIComponent(path).replace(/\%20/g, " ");
    layer.open({
        id: 'pdfpreview',
        type: 2,
        title: false,
        skin: 'layui-layer-molv123',
        shade: 0.1,
        closeBtn: 1,
        shadeClose: false,
        area: ['100%', '100%'],
        fixed: true,
        resize: false,
        content: [ctxPath + "/plugins/pdfjs-2.5.207-es5-dist/web/viewer.html?file=" + path],
        success: function (layero, index) {
            layero[0].childNodes[1].childNodes[0].removeAttribute('href');
            layero[0].childNodes[1].classList.add('cursorStyle');
            layero[0].childNodes[1].childNodes[0].classList.remove('layui-layer-close2');
            layero[0].childNodes[1].childNodes[0].classList.add('layui-layer-close1');
        },
        end: function () {
            $("#button#secondaryToolbarToggle").css("margin-right","7vw");
        }
    });
}
//PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang end--

// 「確認しました」ボタン押下
function noticeConfirm() {
    $.ajax({
        url: ctxPath + '/guard/F30113/noticeConfirm?guardReadingId=' + guardReadingId,
        type: 'POST',
        datatype: "json",
        success: function () {
            let idx = layer.confirm('配信情報を確認したことを送信しました。', {
                skin: 'layui-layer-molv out',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['確認'],
                yes: function () {
                    // 2020/11/25 modify zmh start
                    // POP画面で「確認」押下時、親画面へ戻る。
                    $('#btn_confirm').hide()
                    layer.close(idx);
                    // 2020/11/25 modify zmh end
                }
            })
        }
    });
}



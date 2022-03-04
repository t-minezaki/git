let param = getParam();
let vm = new Vue({
    el: '#page',
    data:{
        numCount: 0
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/student/F11011/init',
                type: 'GET',
                data: {
                    messageId: param.messageId
                },
                async: true,
                datatype: 'json',
                success: function (data){
                    if (data.code === 0) {
                        let f11011Dto = data.dto;
                        if(f11011Dto){
                            // メッセージ．メッセージタイトル
                            let title = decodeURIComponent(f11011Dto.messageTitle);
                            $(".title").text(title);
                            //メッセージ．メッセージ内容
                            let cont = decodeURIComponent(f11011Dto.messageCont);
                            // while (cont) {
                            //     let sub1 = cont.indexOf('<img');
                            //     if (sub1 != -1) {
                            //         let sub2 = cont.substring(sub1).indexOf('/>');
                            //         cont = cont.replace(cont.substring(sub1, sub2 + sub1 + 2), '');
                            //     } else {
                            //         break;
                            //     }
                            // }
                            $(".description").html(cont);
                            //背景色
                            uParse('.content_div', {
                                rootPath: '../plugins/ueditor-1.4.3.3'
                            });
                            // メッセージ．タイトル画像パス
                            // if (f11011Dto.titleImgPath) {
                            //     $(".img").attr("src", f11011Dto.titleImgPath);
                            // }
                            // 2020/11/12 zhangminghao modify start
                            if (f11011Dto.attachFilePath) {
                                //PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang start--
                                //2020/11/26 liyuhuan add start
                                //一致文字列
                                var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                                var re = new RegExp(rgx);
                                var filePaths = f11011Dto.attachFilePath.split(",");
                                var tag = "";
                                for (var i=0;i<filePaths.length;i++ ){
                                    if (IsPicture(filePaths[i])) {
                                        var src = encodeURIComponent(filePaths[i].replace(/\\/g,"/"));
                                        tag  +="<a id='showFile' onclick=previewImg('"+src+"')>"+ handleFileName(filePaths[i]) +"</a>";
                                    }else if (!re.test(filePaths[i].replace(/\\/g,"/"))) {
                                        tag +="<a id='showFile' onclick='pdfClick(this)' title='"+ filePaths[i] +"' >"+ handleFileName(filePaths[i]) +"</a>";
                                    }else {
                                        tag +="<a onclick='pdfClick(this)' href='"+ filePaths[i]+"' >"+ handleFileName(filePaths[i]) +"</a>";
                                    }
                                    tag +="<br>"
                                }
                                //2020/11/26 liyuhuan add end
                                $(".div_files").html(tag);
                            }

                            // 開封済区分が0の場合：
                            // 活性
                            // 開封済区分が0以外の場合：
                            // 非活性
                            if (f11011Dto.openedFlg === '0' && f11011Dto.messageLevelDiv === '2'){
                                $('#btn_confirm').show();
                            }else {
                                $('.content_div').css("height","auto");
                            }
                            // 2020/11/12 zhangminghao modify end
                            $(".hyper_link").click(function (e) {
                                window.location.href = $(this).text();
                            });
                        }
                        if (data.numCount){
                            vm.numCount = data.numCount;
                        }
                    }
                }
            });
        }
    }
});

// 2020/11/12 zhangminghao modify start
// 「確認しました」ボタン押下
function noticeConfirm() {
    $.ajax({
        url: ctxPath + '/student/F11011/noticeConfirm?messageId=' + param.messageId,
        type: 'POST',
        datatype: "json",
        success: function () {
            $('#btn_confirm').hide();
            $('.content_div').css("height","auto");
        }
    });
}
// 2020/11/12 zhangminghao modify end
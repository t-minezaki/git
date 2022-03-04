// var menHeight = window.screen.height - 44 - 58;
// $(".content_div").css("height",menHeight);
var param = getParam();
let guardReadingId = 0;
$(function () {
    $.ajax({
        url: ctxPath + '/guard/F30113/init',
        data:{
            id:param.noticeId
        },
        dataType:'json',
        type:'GET',
        async:true,
        success:function (data) {
            if (data.code == 0){
                if (data.noticeNews) {
                    // 保護者お知らせ閲覧状況
                    guardReadingId = data.noticeNews.id;
                    // お知らせ．お知らせタイトル
                    $(".title").text(data.noticeNews.noticeTitle);
                    //add at 2021/08/16 for V9.02 by NWT LiGX START
                    if (data.noticeNews.noticeTypeDiv == "3") {
                        if (data.noticeNews.reason) {
                            var lateCont =  "送信日 : " + data.updTime + "<br>" +
                                "遅刻・欠席時間 : " + data.tgtYmd + "<br>" +
                                "理由 : " + data.noticeNews.reason + "<br>" +
                                "内容 : " + "<br>" + data.noticeNews.bikou;
                            $(".description").html(lateCont);
                        }else {
                            var cont = decodeURIComponent(data.noticeNews.noticeCont);
                            $(".description").html(cont);
                        }

                    }else{
                        // お知らせ．お知らせ内容
                        var cont = decodeURIComponent(data.noticeNews.noticeCont);
                        // while (cont) {
                        //     var sub1 = cont.indexOf('<img');
                        //     if (sub1 != -1) {
                        //         var sub2 = cont.substring(sub1).indexOf('/>');
                        //         cont = cont.replace(cont.substring(sub1, sub2 + sub1 + 2), '');
                        //     } else {
                        //         break;
                        //     }
                        // }
                        $(".description").html(cont);
                    }
                    //add at 2021/08/16 for V9.02 by NWT LiGX END
                    //背景色
                    uParse('.content_div', {
                        rootPath: '../plugins/ueditor-1.4.3.3'
                    });
                    // お知らせ画像パス
                    // if (data.noticeNews.titleImgPath) {
                    //     $(".img").attr("src", data.noticeNews.titleImgPath);
                    // }
                    if (data.noticeNews.attachFilePath) {
                        //PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang start--
                        //2020/11/26 liyuhuan add start
                        //一致文字列
                        var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                        var re = new RegExp(rgx);
                        /* 2020/12/24 ITA-025 cuikailin add start */
                        var filePaths = data.noticeNews.attachFilePath.split(",");
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
                    $(".updateTime").text(data.updTime);

                    // 2020/11/25 modify zmh start
                    // 「お知らせレベル区分」が「2:重要」の場合、「確認しました」ボタンを表示する。
                    // そして、メッセージは[開封]ではありません
                    if (data.noticeNews.noticeLevelDiv === '2' && data.noticeNews.openedFlg === '0'){
                        $('#btn_confirm').show();
                    }
                    // 2020/11/25 modify zmh end
                    // 塾からの掲示板未読件数
                    if (data.count > 0) {
                        $(".count").text(data.count);
                        $(".count").show();
                    }
                    $(".hyper_link").click(function (e) {
                        window.location.href = $(this).text();
                    });
                }
            }
            $("#ifr_bottom").attr("src",ctxPath+"/common/F40004.html?id=F30112");
        }
    });
});
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
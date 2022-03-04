var url = window.location.href;
var param = getParam();
$(function () {
    $.ajax({
        url: ctxPath + '/guard/F30111/init',
        data: {
            id: param.noticeId,
            url: url
        },
        dataType: 'json',
        type: 'GET',
        async: true,
        success: function (data) {
            if (data.code == 0) {
                if (data.noticeNews) {
                    // お知らせ．お知らせタイトル
                    $(".title").text(data.noticeNews.noticeTitle);
                    // お知らせ．お知らせ内容
                    var cont = decodeURIComponent(data.noticeNews.noticeCont);
                    while (cont) {
                        var sub1 = cont.indexOf('<img');
                        if (sub1 != -1) {
                            var sub2 = cont.substring(sub1).indexOf('/>');
                            cont = cont.replace(cont.substring(sub1, sub2 + sub1 + 2), '');
                        } else {
                            break;
                        }
                    }
                    $(".description").html(cont);
                    //背景色
                    uParse('.content_div', {
                        rootPath: '../plugins/ueditor-1.4.3.3'
                    });
                    // お知らせ画像パス
                    if (data.noticeNews.titleImgPath) {
                        $(".img").attr("src", data.noticeNews.titleImgPath);
                    }
                    if (data.noticeNews.attachFilePath) {
                        var str = data.noticeNews.attachFilePath.substring(data.noticeNews.attachFilePath.lastIndexOf('\\') + 1);
                        str = str.slice(str.lastIndexOf("/") + 1);

                        function handleFileName(fileName) {
                            var filename = fileName.slice(fileName.lastIndexOf("\\") + 1);
                            filename = filename.slice(filename.lastIndexOf("/") + 1);
                            var newStr = "";
                            for (var i = 0; i < filename.length; i++) {
                                if (!(i >= filename.lastIndexOf(".") - 17 && i < filename.lastIndexOf("."))) {
                                    newStr += filename.charAt(i);
                                }
                            }
                            return newStr;
                        }
                        $(".div_files").html("<a href='"+ data.noticeNews.attachFilePath+"' download='"+ str +"'>"+ handleFileName(data.noticeNews.attachFilePath) +"</a>");
                    }
                    $(".updateTime").text(data.updTime);

                    // 塾からの掲示板未読件数
                    if (data.count > 0) {
                        $(".count").text(data.count);
                        $(".count").show();
                    }
                }
            }
        }
    });
})

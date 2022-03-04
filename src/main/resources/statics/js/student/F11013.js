var param = getParam();
var eventId ='';
$.ajax({
    url: ctxPath + '/student/F11013/init',
    type: 'GET',
    data: {
        eventId:param.eventId
    },
    async: true,
    datatype: 'json',
    success: function (data){

            if (data.unReadCount=="0"){
                $(".numCount").attr("hidden","hidden");
            } else {
                $(".numCount").html(data.unReadCount);
            }
            eventId = data.dto.eventId;
            var f11013Dto = data.dto;
            if(f11013Dto){
                // メッセージ．メッセージタイトル
                var title = decodeURIComponent(f11013Dto.eventTitle);
                $(".title").text(title);
                //メッセージ．メッセージ内容
                var cont = decodeURIComponent(f11013Dto.eventCont);
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
                // メッセージ．タイトル画像パス
                if (f11013Dto.titleImgPath) {
                    $(".img").attr("src", f11013Dto.titleImgPath);
                }else {
                    $(".div_img").css("display","none");
                }
                if (f11013Dto.attachFilePath) {
                    //PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang start--
                    //2020/11/26 liyuhuan add start
                    //一致文字列
                    var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                    var re = new RegExp(rgx);
                    var filePaths = f11013Dto.attachFilePath.split(",");
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
                $(".hyper_link").click(function (e) {
                    window.location.href = $(this).text();
                });
            }
            if (data.meg!="") {
            $(".btn").attr("hidden","hidden");
            $("#bottomMsg").html(data.meg).css("background-color","#F2F2F2")
        }
    }
});
 function toF11014() {
    window.location.href='./F11014.html?eventId='+eventId + "&flg=0";
}
var param = getParam();
var applyId = '';
$(document).ready(function () {
    $.ajax({
        url: ctxPath + '/guard/F30402/init',
        data:{
            id:param.eventId
        },
        dataType:'json',
        type:'GET',
        async:true,
        success:function (data) {
            if (data.code == 0){
                // 塾からのイベント情報．イベントタイトル
                if(data.eventNews){
                    applyId = data.eventNews.id;
                    $(".title").text(data.eventNews.eventTitle);
                    // 塾からのイベント情報．イベント内容
                    var cont = decodeURIComponent(data.eventNews.eventCont);
                    // while (cont){
                    //     var sub1 = cont.indexOf('<img');
                    //     if(sub1 != -1){
                    //         var sub2 = cont.substring(sub1).indexOf('/>');
                    //         cont = cont.replace(cont.substring(sub1 , sub2 + sub1 + 2),'');
                    //     }else{
                    //         break;
                    //     }
                    // }
                    $(".description").html(cont);
                    //背景色
                    uParse('.content_div', {
                        rootPath: '../plugins/ueditor-1.4.3.3'
                    });
                    //イベント画像パス
                    if(data.eventNews.titleImgPath){
                        $(".img").attr("src",data.eventNews.titleImgPath);
                    }
                    if(data.eventNews.attFilePath){
                        //2020/12/09 liguangxin add start
                        //一致文字列
                        var files = data.eventNews.attFilePath.split(',');
                        var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                        var regx = new RegExp(rgx);
                        var tag = '';
                        for(let i = 0; i < files.length; i++){
                            if (IsPicture(files[i])) {
                                const src = encodeURIComponent(files[i].replace(/\\/g, "/"));
                                tag +="<a id='showFile' onclick=previewImg('"+src+"') >"+ handleFileName(files[i]) +"</a><br>";
                            }else if (!regx.test(files[i].replace(/\\/g,"/"))){
                                tag +="<a id='showFile' onclick='pdfClick(this)' title='"+files[i]+"'>"+ handleFileName(files[i]) +"</a><br>";
                            }else {
                                tag +="<a onclick='pdfClick(this)' href='"+ files[i]+"' >"+ handleFileName(files[i]) +"</a><br>";
                            }
                            //PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang start--
                            //2020/12/09 ligungxin add end
                        }
                        $(".div_files").html(tag);
                    }
                    $(".hyper_link").click(function (e) {
                        window.location.href = $(this).text();
                    });
                }
                // $(".updateTime").text(data.updTime);

                // 塾からのイベント情報未読件数
                if (data.count1 > 0){
                    $(".count1").text(data.count1);
                    $(".count1").show();
                }
                // お知らせ未読カウント数
                // if(data.count1){
                //     $(".count1").text(data.count1);
                //     $(".count1").show();
                // }
                // 掲示板未読カウント数
                if(data.newsCount){
                    $(".newsCount").text(data.newsCount);
                    $(".newsCount").show();
                }
                if(data.msgx || data.msgx != ""){
                    $(".div_btnbox").hide();
                    $(".div_msgbox").html(data.msgx);
                    $(".div_msgbox").show();
                }else{
                    $(".div_btnbox").show();
                    $(".div_msgbox").hide();
                }
            }
            // 2021/1/7 huangxinliang modify start
            $('#iframe_top').attr('src', ctxPath + '/common/F40004-1.html');
            // 2021/1/7 huangxinliang modify end
            $("#ifr_bottom").attr("src",ctxPath+"/common/F40004.html?id=F30421");
        }
    });
});

function toF30405() {
    window.location.href = "F30405.html?eventId="+param.eventId+"&applyId=" + applyId + "&flg=0";
}
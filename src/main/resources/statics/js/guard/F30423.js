
var param = getParam();
$(function () {
    $.ajax({
        url: ctxPath + '/guard/F30423/init',
        data:{
            noticeId:param.noticeId
            // scmId:param.scmId
        },
        dataType:'json',
        type:'GET',
        async:true,
        success:function (data) {
            if (data.code == 0){
                if (data.f30423Dto) {
                    // お知らせ．お知らせタイトル
                    $(".title").text(data.f30423Dto.noticeTitle);
                    // お知らせ．お知らせ内容
                    var cont = data.f30423Dto.complimentCont;
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
                    // お知らせ画像パス
                    if (data.f30423Dto.codValue) {
                        $(".img").attr("src", data.f30423Dto.codValue);
                    }else {
                        $(".description").css("margin-top","0");
                    }
                }
            }
            // $('img').parent().css("text-align","center");
            $("#ifr_bottom").attr("src",ctxPath+"/common/F40004.html?id=F30423");
        }
    });
});
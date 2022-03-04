
param = getParam();
$.ajax({
    url: ctxPath + '/guard/F30420/init',
    type: 'GET',
    data: {
        noticeId:param.noticeId
    },
    async: true,
    datatype: 'json',
    success: function (data){
        if (data.code == 0) {
            var f30420Dto = data.f30420Dto;
            if(f30420Dto){
                // お知らせ．お知らせタイトル
                var title = decodeURIComponent(f30420Dto.noticeTitle);
                //お知らせ
                $("#title").text(title);
                // お知らせ．お知らせ内容
                var cont = decodeURIComponent(f30420Dto.noticeCont);
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
                //背景色
                uParse('.content_div', {
                    rootPath: '../plugins/ueditor-1.4.3.3'
                });
                // お知らせ画像パス
                // if (f30420Dto.titleImgPath) {
                //     $(".img").attr("src", f30420Dto.titleImgPath);
                // }
                if (f30420Dto.attachFilePath) {
                    //2020/11/26 liyuhuan add start
                    //一致文字列
                    var rgx= "(.xlsx|.xls|.csv|.pptx|.docx)$";
                    var re = new RegExp(rgx);
                    var filePaths = f30420Dto.attachFilePath.split(",");
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
                /* 2020/11/12 V9.0 cuikailin add start */
                var noticeLevelDiv = f30420Dto.noticeLevelDiv;
                if(noticeLevelDiv != null && noticeLevelDiv == '2' && f30420Dto.openedFlg == '0'){
                    $("#confirmContainer").show();
                }
                vm.grsId = f30420Dto.id;
                /* 2020/11/12 V9.0 cuikailin add end */
                $(".hyper_link").click(function (e) {
                    window.location.href = $(this).text();
                });
            }
        }
    }
});

/* 2020/11/12 V9.0 cuikailin add start */
var vm = new Vue({
    el: '#main',
    data: function (){
        return{
            grsId: -1
        }
    },
    methods: {
        confirm: function (){
            $.ajax({
                url: ctxPath + '/guard/F30420/confirm',
                type: 'POST',
                data: {
                    id: vm.grsId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.updateFlag == true) {
                        layer.confirm('配信情報を確認したことを送信しました。', {
                            btn: ['確認'],
                            title: "確認",
                            btn1: function () {
                                /* 2020/12/4 V9.0 cuikailin modify satrt */
                                //window.history.go(-1);
                                parent.layer.closeAll();
                                $("#confirmContainer").hide();
                                /* 2020/12/4 V9.0 cuikailin modify end */
                            }
                        });
                    }
                }
            })
        }
    }
})
/* 2020/11/12 V9.0 cuikailin add end */
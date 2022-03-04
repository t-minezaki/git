var menHeight = window.screen.height - 98 -41 - 36;
$(".content_div").css("height",menHeight)

var vm =new Vue({
    el: '.content',
    data: {
        //news content
        contents: [],
        // 塾からの掲示板未読件数
        count:"",
        // 記事一覧のオフセット (デフォルト30)
        offset:"30",
        // 画像なしのロゴ文字画像の取得
        imgPath:""
    },
    mounted:function () {
        this.getInfo();
    },
    methods: {
        // 画像が存在するかどうかを判断する
        changeStatus(img){
            if(img == null){
                return false;
            }else{
                return true;
            }
        }
        ,
        // ページジャンプ
        f30302init : function(content) {
            // id
            window.localStorage.setItem("id",content.id);
            // url
            window.localStorage.setItem("url",content.url);
            // 掲示板のタイトル
            window.localStorage.setItem("title",content.title);
            // 掲示板の画像
            window.localStorage.setItem("img",content.image_urls[0]);
            // 掲示板のコンテンツ
            window.localStorage.setItem("description",content.description);
            //掲示板情報の元サイト名
            window.localStorage.setItem("sourceName",content.sourceName);
            // 塾からの掲示板未読件数
            window.localStorage.setItem("count",vm.count);
            window.location=ctxPath + '/guard/F30302.html';
        },
        // 初期化
        getInfo: function () {
            $(".insert").hide();
            $.ajax({
                url: ctxPath + '/guard/F30301/init',
                data:{
                    url : window.location.href,
                    offset : 0
                },
                dataType:'json',
                type:'GET',
                async:true,
                success:function (data) {
                    if (data.code == 0){
                        if (data.imgPath) {
                            vm.imgPath = data.imgPath
                        }
                        if (data.contents) {
                            vm.contents = data.contents;
                        }
                        if (data.count) {
                            vm.count = data.count;
                        }
                        $(".insert").show();
                    }
                    if (data.count > 0){
                        $(".count").show();
                    }
                }
            })
        },
        // カメリオＡＰＩを呼び出し、次の３０件を追加し再表示する。
        insert: function () {
            $.ajax({
                url: ctxPath + '/guard/F30301/init',
                data:{
                    url : window.location.href,
                    offset:vm.offset
                },
                dataType:'json',
                type:'GET',
                async:true,
                success:function (data) {
                    if (data.code == 0){
                        vm.contents = vm.contents.concat(data.contents);
                        vm.offset = data.offset;
                        $(".insert").hide();
                    }
                }
            })
        }
    }
})

var page = '';
var vm = new Vue({
    el: '.main',
    data: {
        mstCodDEntityListLimit: [],
        length:'',
        page:''
    },
    mounted: function () {
        this.getInfo(0);
    },
    updated:function(){
        $(".pageBtn").each(function () {
            if (this.value == page){
                $(this).addClass("active");
            } else {
                $(this).removeClass("active");
            }
        })
    },
    methods: {
        //初期化
        getInfo: function (pageNum) {
            page = pageNum;
            $.ajax({
                url: ctxPath + '/pop/F21021/init',
                type: 'get',
                data: {
                    pageNum:pageNum + 1,
                    limit:9
                },
                success: function (data) {
                    vm.mstCodDEntityListLimit = data.mstCodDEntityListLimit;
                    vm.length = Math.ceil((data.mstCodDEntityList.length)/9);
                }
            });
        },
        to21022:function (codCd,codValue) {
            if (parent.$("#message")){
                parent.$("#message").hide();
            }
            parent.complimentDiv = codCd;
            parent.$(".stamp_img_div img").remove();
            parent.$(".stamp_img_div").append('<img src="'+codValue+'">');
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    }
});



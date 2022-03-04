var param=getParam();
var id = param.id;
var brandCd;
var vue = new Vue({
    el: "#v_If",
    data: {
        id : id
    },
    mounted: function(){
        this.pd();
        this.onclick();
        this.$nextTick(function(){
            if (getCookie("key") === 'PUSHAPI'){
                parent.$(".logout").css("display","none");
            }
        })
    },
    updated: function(){
        this.onclick();
    },
    methods:{
        pd: function () {
            $.ajax({
                url: ctxPath + '/common/F40012/init',
                dataType:'json',
                type:'GET',
                async:true,
                success:function (data) {
                    if (data.code == 0){
                        if (data.cou != 0) {
                            $(".haveMess").text(data.cou);
                            $(".haveMess").show();
                        }else {
                            $(".haveMess").hide();
                        }
                    }
                    isActive();
                }
            });
        },
        onclick:function () {
            $("li").click(function () {
                var index = $(this).index();
                var indexx = $(this).index()+1;
                var srcpng = '../img/bottomPngs7.0/btnpng' + indexx + 'fill.png';
                for ( var i = 0; i< $("li").length;i++){
                    var j = i+1;
                    $("li").eq(i).find("img").attr("src","../img/bottomPngs7.0/btnpng" + j + ".png")
                }
                $("li").eq(index).find('img').attr("src" ,srcpng);
                $("li").find("p").removeClass("green");
                $(this).find("p").addClass("green");
            });
        }
    }
});
function isActive() {

    $("li").find("p").removeClass("green");
    if(param.id === 'F11008'){
        for(var i=0;i< $("li").length;i++){
            if(i !== 0){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+".png");
            }else{
                $("li").eq(0).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+"fill.png");
                $("li").eq(0).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if(param.id === 'F11001' || param.id === 'F11002' || param.id === 'F11003' || param.id === 'F11004'){
        for (var i = 0; i < $("li").length; i++) {
            if (i != 1) {
                $("li").eq(i).find("img").eq(0).attr("src", "../img/bottomPngs7.0/btnpng" + (i + 1) + ".png");
            } else {
                $("li").eq(1).find("img").eq(0).attr("src", "../img/bottomPngs7.0/btnpng" + (i + 1) + "fill.png");
                $("li").eq(1).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if (param['id'] == 'F11010') {
        for (var i = 0; i < $("li").length; i++) {
            if (i != 2) {
                $("li").eq(i).find("img").eq(0).attr("src", "../img/bottomPngs7.0/btnpng" + (i + 1) + ".png");
            } else {
                $("li").eq(2).find("img").eq(0).attr("src", "../img/bottomPngs7.0/btnpng" + (i + 1) + "fill.png");
                $("li").eq(2).find("p").eq(0).addClass("green");
            }
        }
        return;
    }

    if(param['id']=='F11009'){
        for(var i=0;i< $("li").length;i++){
            if(i!=3){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+".png");
            }else{
                $("li").eq(3).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+"fill.png");
                $("li").eq(3).find("p").eq(0).addClass("green");
            }
        }
        return;
    }

    if(param['id']=='F11016'){
        for(var i=0;i< $("li").length;i++){
            if(i!=4){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+".png");
            }else{
                $("li").eq(4).find("img").eq(0).attr("src","../img/bottomPngs7.0/btnpng"+(i+1)+"fill.png");
                $("li").eq(4).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
}
$(function () {
    if (getCookie("key") === 'PUSHAPI'){
        $(".logout").css("display","none");
    }
})

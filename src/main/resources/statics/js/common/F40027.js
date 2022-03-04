var param=getParam();
var brandCd;
var vue = new Vue({
    el: "#v_If",
    data: {
        brandCd : '',
        cou: 0,
        orgAddId:''
    },
    mounted: function(){
        $.ajax({
            url: ctxPath + '/common/F40004/getOrg',
            type: 'GET',
            dataType: "json",
            success: function (data){
                vue.orgAddId = data.orgIdAdd;
            }
        });
        this.pd();
    },
    updated: function(){
        $("li").click(function () {
            var index = $(this).index();
            var indexx = $(this).index()+1;
            var srcpng = '../img/bottomPngs8.0/bottompng8.0_' + indexx + 'fill.png';
            for ( var i = 0; i< $("li").length;i++){
                var j = i+1;
                $("li").eq(i).find("img").attr("src","../img/bottomPngs8.0/bottompng8.0_" + j + ".png")
            }
            $("li").eq(index).find('img').attr("src" ,srcpng);
            $("li").find("p").removeClass("green");
            $(this).find("p").addClass("green");
        });
        isActive();
    },
    methods:{
        pd: function () {
            $.ajax({
                url: ctxPath + '/common/F40004/init',
                dataType:'json',
                type:'GET',
                async:true,
                success:function (data) {
                    if (data.code == 0){
                        if (data.cou != 0) {
                            vue.cou = data.cou;
                        }
                        $("#changeOrg").css("display", data.disa?"none":"block");
                        vue.brandCd = data.brandCd == ''?getCookie("brandcd"):data.brandCd;
                    }else if (data.code == 307) {
                        parent.window.location.href = ctxPath + data.msg;
                    }
                }
            });
        },
        displayCount: function () {
            if (this.cou > 0){
                return false;
            }else {
                return 'display: none;'
            }
        }
    }
});
function isActive() {

    $("li").find("p").removeClass("green");
    if(param.id === 'F30112' || param.id === 'F30419' || param.id === 'F30421'){
        for(var i=0;i< $("li").length;i++){
            if(i !== 0){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+".png");
            }else{
                $("li").eq(0).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+"fill.png");
                $("li").eq(0).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if(param['id']=='F30101'){
        for(var i=0;i< $("li").length;i++){
            if(i!=1){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+".png");
            }else{
                $("li").eq(1).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+"fill.png");
                $("li").eq(1).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if(param['id']=='F30411'){
        for(var i=0;i< $("li").length;i++){
            if(i!=2){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+".png");
            }else{
                $("li").eq(2).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+"fill.png");
                $("li").eq(2).find("p").eq(0).addClass("green");
            }
        }
        return;
    }

    if(param['id']=='F30406'){
        for(var i=0;i< $("li").length;i++){
            if(i!=3){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+".png");
            }else{
                $("li").eq(3).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+"fill.png");
                $("li").eq(3).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
    if(param['id']=='F30422'){
        for(var i=0;i< $("li").length;i++){
            if(i!=3){
                $("li").eq(i).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+".png");
            }else{
                $("li").eq(3).find("img").eq(0).attr("src","../img/bottomPngs8.0/bottompng8.0_"+(i+1)+"fill.png");
                $("li").eq(3).find("p").eq(0).addClass("green");
            }
        }
        return;
    }
}
function openUrl(url) {
    if (url == "logout") {
        parent.layer.confirm("ログアウトしますか？", {
            skin: (window.getCookie('brandcd') === vue.orgAddId) &&(window.parent.location.href.indexOf("student") < 0)?'layui-layer-molv out':'layui-layer-molv',
            btn: ['キャンセル', '確認'], title: "確認", btn2: function () {
                window.top.location.href = ctxPath + "/logout";
                return false;
            }
        });
    } else {
        parent.layer.confirm($.msg.MSGCOMN0004, {}, function () {
            window.top.location.href = ctxPath + "/student/" + url;
        });
    }
}
function toNotice() {
    switch (vue.brandCd) {
        case vue.orgAddId:
            parent.window.location.href = 'F30421.html';
            break;
        default:
            parent.window.location.href = 'F30112.html';
    }
}
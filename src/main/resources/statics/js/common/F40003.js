var param=getParam();
$(".tab_subject_s").find("li").removeClass("active");
$(".tab_subject_s").find("li").eq(5).removeClass("active");
if(param['id']=='F20002'){
    $(".tab_subject_s").find("li").eq(0).addClass("active");
}
if(param['id']=='F20003'){
    $(".tab_subject_s").find("li").eq(1).addClass("active");
}
if(param['id']=='F20006'){
    $(".tab_subject_s").find("li").eq(2).addClass("active");
}
if(param['id']=='F20008'||param['id']=='F20009'){
    $(".tab_subject_s").find("li").eq(3).addClass("active");
}
if(param['id']=='F20010'){
    $(".tab_subject_s").find("li").eq(4).addClass("active");
}

//アクセス許可のないリンクのグレースケール表示
$(".disable").parent().css("background-color", "#80808080");

$("a").click(function () {
    var url=$(this).attr("desc");
    if(param['id']=='F20003'&&!parent.getChangeStatus()){
        var index = parent.layer.confirm($.msg.MSGCOMN0052, {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            btn: ['キャンセル', '確認'],
            btn1: function () {
                parent.layer.close(index);
            },
            btn2: function () {
                window.parent.location.href=url;
                parent.layer.close(index);
            }
        });
    }else{
        window.parent.location.href=url;
    }

});
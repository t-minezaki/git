var vm = new Vue({
    el: "#app",
    data: {
        subjtList:[],
        freeBlockList:[],
        fixedBlockList:[],
        otherBlockList:[],
        allList:[]

    },
    mounted: function () {
    },
    updated: function(){
    },
    methods: {
        colorSelect:function (id, value,blockId,cod) {
            var id = id;
            var color = value;
            var blockId = blockId;
            var subjtDiv = cod;
            $.post(ctxPath + "/student/F10004/save",
                {
                    id: id,
                    color: color,
                    blockId:blockId,
                    subjtDiv:subjtDiv
                },function (data) {
                    if (data.code == 0) {
                        window.location.reload();

                    } else {
                        showMsg(data.msg);
                    }
                })
        }
    }
});
$(function () {

    $.get(ctxPath + "/student/F10004/init",
        function (data) {
            if (data.code == 0) {
                vm.subjtList = data.subjtList;
                vm.freeBlockList = data.freeBlockList;
                vm.fixedBlockList = data.fixedBlockList;
                vm.otherBlockList = data.otherBlockList;
            }else {
                showMsg(data.msg);
            }
        });

    // 「戻る」ボタン押下
    $("#btn_return").click(function () {

        // F10002_生徒Myページ画面へ遷移する。
        location.href = "F10002.html";
    });

});
var list = [];
function addList(list) {
    for (var i = 0; i <list.length ; i++) {
        vm.allList.push(list[i]);
    }
}

function colorPop(id, blockId, cod,color){
    var param={};
    param.id = id;
    param.blockId = blockId;
    param.cod = cod;
    param.color = color;
    var scrWidth = '230px'
    var scrHeight = '200px'
    var index = layer.open({
        id: 'f10004pop',
        type: 2,
        title: 'ブロック設定色',
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        area: [scrWidth, scrHeight],
        fixed: true,
        resize: false,
        content: ["../pop/F10004pop.html?" + $.param(param), 'no'],
        success: function (layero, index) {
        }
    });
}

window.onload = function (ev) {
    $("#iframe").contents().find("#mypage_img").css('width','50%');
}

// $(".color_btn").click(function () {
//     var scrWidth = $(window).width()*0.8;
//     var scrHeight = $(window).height()*1;
//     if(scrWidth > 340){
//         scrWidth = '340px'
//     }else{
//         scrWidth = scrWidth + 'px'
//     }
//     if(scrHeight > 530){
//         scrHeight = '530px'
//     }else{
//         scrHeight = scrHeight + 'px'
//     }
//     var index = layer.open({
//         id: 'f10004pop',
//         type: 2,
//         title: false,
//         shade: 0.1,
//         closeBtn: 0,
//         shadeClose: false,
//         area: [scrWidth, scrHeight],
//         fixed: true,
//         resize: false,
//         content: ["../pop/F10004pop.html?" + $.param(param), 'no'],
//         success: function (layero, index) {
//         }
//     });
// })
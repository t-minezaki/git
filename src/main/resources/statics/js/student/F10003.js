function parseUrl() {
    var url = location.href;
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}

var old;
var v = parseUrl();
var vm = new Vue({
    el: '#info',
    data: {
        subjtlist: [],
        crmschId: '',
        firstFlg: '',
        crmschLearnPrdId:'',
        schyDiv:'',
        stuNm:''
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {
        old = $(".f_books").serialize();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/student/F10003/getTextbChocInfo',
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    if (data.msg != "success") {
                        showMsg(data.msg);
                        $(".login_btn_sc_first").attr("disabled", "disabled");
                    } else {
                        //textbList
                        if (data.textbList) {
                            vm.subjtlist = data.textbList;
                            vm.crmschId = data.textbList[0].crmschId;
                        }
                        if(data.firstFlg){
                            vm.firstFlg = data.firstFlg;
                        }
                        if(data.crmschLearnPrdId){
                            vm.crmschLearnPrdId=data.crmschLearnPrdId;
                        }
                        if(data.schyDiv){
                            vm.schyDiv=data.schyDiv;
                        }
                        if(data.stuNm){
                            vm.stuNm=data.stuNm;
                        }
                    }
                }
            })
        }
    }
});

function updateChoc() {
    //var chocData = $(".f_books").serializeArray();
    var chocData;
    var chocLict=[];
    $("select").each(function () {
        chocData={};
        chocData.updateTimeStr=$(this).attr("updateStr");
        chocData.value=$(this).val();
        chocData.name=$(this).attr("name");
        chocLict.push(chocData);
    })
    $.post(ctxPath + '/student/F10003/updateChocTextb',
        {
            chocList: chocLict,
            crmschId: vm.crmschId,
            crmschLearnPrdId:vm.crmschLearnPrdId,
            schyDiv:vm.schyDiv
        }, function (data) {
            if (data.code == 0) {
                // var index = layer.alert(data.msg, {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     btn1: function () {
                        if (v['id'] == 'f10001') {
                            window.location.href = "./F10301.html"
                        }
                        else {
                            window.location.href = "./F10002.html"
                        }
                        // layer.close(index);
                    // }
                // });
            } else {
                showMsg(data.msg);
            }
        })
}

function cancelFn() {
    if (v['id'] == 'f10001') {
        window.location.href = "./F10001.html"
    }
    else {
        window.location.href = "./F10002.html"
    }
}

window.onload = function (ev) {
    $("#iframe").contents().find("#mypage_img").css('width','50%');
}
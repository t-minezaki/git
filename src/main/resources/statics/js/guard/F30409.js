var screenWidth = window.screen.width;
var param = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        stuList: [],
    },
    mounted: function () {
        this.getInfo(1);
        /* 2020/11/17 V9.0 cuikailin add start */
        $('.image-container').click(function () {
            var img = $(".image-container").find('img')[0];
            var index = $(img).attr('id').substring(5);
            $(".image-container").fadeOut();
            $(img).addClass('photo-image');
            $('#photoContainer' + index).html($(img));
        });
        /* 2020/11/17 V9.0 cuikailin add end */
    },
    methods: {
        //初期化
        getInfo: function (flg) {
            if (flg == 2) {
                $("#now").css("color", "#d3d3d3");
                $("#past").css("color", "#2e2e2e");
                $("#leftLine").css("border-bottom","3px solid #bababa");
                $("#rightLine").css("border-bottom","3px solid #8CC11F")
                // ipadの色を設定します
                if(768 <= screenWidth <= 1024){
                    $("#now").css("color", "#b6b6b6");
                }
            }
            if (flg == 1) {
                $("#past").css("color", "#d3d3d3");
                $("#now").css("color", "#2e2e2e");
                $("#rightLine").css("border-bottom","3px solid #bababa");
                $("#leftLine").css("border-bottom","3px solid #8CC11F")
                // ipadの色を設定します
                if(768 <= screenWidth <= 1024){
                    $("#past").css("color", "#b6b6b6");
                }
            }
            $.ajax({
                url: ctxPath + '/guard/F30409/init',
                type: 'GET',
                data: {
                    flg: flg
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        vm.stuList = data.stuList;
                    /* 2020/11/17 V9.0 cuikailin modify start */
                        // 2020/11/26 huangxinliang modify start
                        // if(data.stuList.length>0){
                            // for(var i=0;i<data.stuList.length;i++){
                            //     for(var j=0;j<data.stuList[i].qnlist.length;j++){
                            //     /* 2020/11/17 V9.0 cuikailin add start */
                            //         if(data.stuList[i].qnlist[j].questionName){
                            //             data.stuList[i].qnlist[j].questionName = data.stuList[i].qnlist[j].questionName.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{16}\x01?/g,"$&</br>").replace(/\x01/g,"");
                            //         }
                            //         if(data.stuList[i].qnlist[j].answerTypeDiv != 3){
                            //             if (data.stuList[i].qnlist[j].answerReltCont){
                            //                 data.stuList[i].qnlist[j].answerReltCont = data.stuList[i].qnlist[j].answerReltCont.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{20}\x01?/g,"$&</br>").replace(/\x01/g,"");
                            //             }
                            //         }
                            //     }
                            //     if(data.stuList[i].noteCont){
                            //         data.stuList[i].noteCont = data.stuList[i].noteCont.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{20}\x01?/g,"$&</br>").replace(/\x01/g,"");
                            //     }
                            //     /* 2020/11/23 V9.0 cuikailin modiyf end */
                            // }
                        // }
                        // 2020/11/26 huangxinliang modify end
                        var text ='';
                        var count = 1;
                        for(var i=0;i<vm.stuList.length;i++){
                            text = text+'<ul>' +
                                '<li style="overflow: hidden;">' +
                                '<span style="width: 1%;height: 50%;float: left"></span>' +
                                '<p style="margin-left: 2vw;font-weight: bold;height: 50%;font-size:15px;" class="text1">保護者面談</p>' +
                                '</li>'+
                                '<li class="text2" style="margin-left: 5vw;"><span style="width: 41%">生徒氏名</span><p style="margin-left: 5vw;">'+vm.stuList[i].stuNm+'</p></li>'+
                                '<li class="text2" style="margin-left: 5vw;"><span style="width: 41%">日時</span><p style="margin-left: 5vw;">'+vm.stuList[i].sgdStartDatime+'</p></li>'+
                                /* 2020/12/18 V9.0 cuikailin modify start */
                                '<li class="text2" style="margin-left: 5vw;"><span style="width: 41%">備考</span></li>'+
                                /* 2020/12/29 V9.0 cuikailin modify start */
                                '<li class="text2" style="margin-left: 0; width: 99%;"><textarea maxlength="500" rows="8" class="result_text" readonly="readonly">'+vm.stuList[i].noteCont+'</textarea></li>'
                                /* 2020/12/29 V9.0 cuikailin modify end */
                            if(vm.stuList[i].qnlist.length >0) {
                                text += '<li style="overflow: hidden;">' +
                                '<span style="width: 1%;height: 50%;float: left"></span>' +
                                '<p style="margin-left: 2vw;font-weight: bold;height: 50%;font-size:15px;" class="text1">アンケート内容</p>' +
                                '</li>'
                                for (var j = 0; j < vm.stuList[i].qnlist.length; j++) {
                                    // text = text+'<li class="text2" style="margin-left: 5vw;" ><span style="width: 32%">設問名'+(j+1)+'</span><p style="margin-left: 5vw; width: 140px;">'+vm.stuList[i].qnlist[j].questionName+'</p></li>'
                                    if (vm.stuList[i].qnlist[j].answerTypeDiv != 3) {
                                        text = text + '<li class="text2" style="margin-left:0; width: 100%; background: rgb(178, 222, 130);"><span style="margin-left:5vw; width: 92%">' + vm.stuList[i].qnlist[j].questionName + '</span></li>' +
                                            '<li class="text2" style="margin-left: 0; width: 97%;"><textarea maxlength="500" cols="50" rows="8" class="result_text" readonly="readonly">' + vm.stuList[i].qnlist[j].answerReltCont + '</textarea></li>'
                                    } else {
                                        var src = vm.stuList && vm.stuList[i].qnlist[j].answerReltCont ? vm.stuList[i].qnlist[j].answerReltCont : '';
                                        text += '<li class="text2" style="margin-left:0; width: 100%; background: rgb(178, 222, 130);"><span style="margin-left:5vw; width: 92%;">' + vm.stuList[i].qnlist[j].questionName + '</span></li>' +
                                            '<li class="text2" style="margin-left: 0; width: 100%;"><div id="photoContainer' + count + '" class="photo-container">' +
                                            '<img src="' + src + '" class="photo-image" onerror="this.src=\'../img/logo2.png\'" onclick="vm.showImg(' + count + ')" id="image' + count + '"/>' +
                                            '</div></li>'
                                        count++;
                                    }
                                    /* 2020/12/18 V9.0 cuikailin modify end */
                                }
                            }
                            if(vm.stuList[i].errorMsg!=null && vm.stuList[i].flg == 1 && vm.stuList[i].refType != '2'){
                                /* 2020/12/21 V9.0 cuikailin modify start */
                                text = text +'<li style="margin-left: 2vw;min-height: 15vw;color: red">'+vm.stuList[i].errorMsg+'</li>'
                                /* 2020/12/21 V9.0 cuikailin modify end */
                            }
                            /* 2020/12/29 V9.0 cuikailin modify start */
                            if((vm.stuList[i].errorMsg==null && vm.stuList[i].flg == 1) || (vm.stuList[i].flg == 1 && vm.stuList[i].refType == '2')){
                            /* 2020/12/29 V9.0 cuikailin modify end */
                                var a = "'";
                                text = text+'<div style="height: 25%;" class="btnDiv">' +
                                    '<button id="change" class="_btn" onclick="vm.change('+vm.stuList[i].eventId+','+vm.stuList[i].applyId+')">申込を変更</button>' +
                                    '<button id="cancel" class="_btn" onclick="vm.cancel('+vm.stuList[i].eventId+','+a+vm.stuList[i].stuId+a+','+a+vm.stuList[i].guardId+a+','+a+vm.stuList[i].refType+a+')">申込をキャンセル</button>' +
                                    '</div>'
                            }
                            text += '</ul>';
                        }
                        $(".content_div").html(text);
                    /* 2020/11/17 V9.0 cuikailin add end */
                    } else {
                        showMsg(data.msg);
                    }
                }
            })
        },
        //申込を変更するボタン押下時
        change: function (eventId,applyId) {
            window.location.href = "./F30405.html?eventId=" + eventId+"&&applyId="+applyId + "&flg=1";
        },
        //申込をキャンセルボタン押下時
        cancel: function (eventId,stuId, guardId, refType) {
            var object = {};
            object.eventId = eventId;
            object.stuId = stuId;
            object.guardId = guardId;
            /* 2020/12/18 V9.0 cuikailin add start */
            object.refType = refType;
            /* 2020/12/18 V9.0 cuikailin add end */
            window.location.href = "./F30410.html?" + $.param(object);
        },
        /* 2020/11/18 V9.0 cuikailin add start */
        showImg: function (index) {
            var container = $("#imageContainer");
            container.html($('#image' + index));
            $('#image' + index).removeClass('photo-image');
            container.fadeIn();
        }
        /* 2020/11/18 V9.0 cuikailin add end */
    }
});

//戻るボタン押下時
function back() {
    window.history.back();
}


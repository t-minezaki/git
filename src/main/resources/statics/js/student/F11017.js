var vue = new Vue({
    el: '#page',
    data: {
        f11017Dtos: [],
        flg: ''
    },
    mounted: function () {
        this.getInfo(1);
        /* 2020/11/18 V9.0 cuikailin add start */
        $('.image-container').click(function () {
            var img = $(".image-container").find('img')[0];
            var index = $(img).attr('id').substring(5);
            $(".image-container").fadeOut();
            $(img).addClass('photo-image');
            $('#photoContainer' + index).html($(img));
        });
        /* 2020/11/18 V9.0 cuikailin add end */
    },
    methods: {
        getInfo: function (flg) {
            if (flg == 2) {
                $("#now").css("background", "#E6E6E6");
                $("#past").css("background", "white");
            }
            if (flg == 1) {
                $("#now").css("background", "white");
                $("#past").css("background", "#E6E6E6");
            }
            $.ajax({
                url: ctxPath + '/student/F11017/init',
                type: 'GET',
                data: {
                    flg: flg
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.f11017Dtos) {
                            vue.stuList = data.f11017Dtos;
                            /* 2020/11/18 V9.0 cuikailin add start */
                            // 2020/11/26 huangxinliang modify start
                            // if(vue.stuList.length>0){
                                // for(var i=0;i<vue.stuList.length;i++){
                                    // for(var j=0;j<vue.stuList[i].qnlist.length;j++){
                                    //     vue.stuList[i].qnlist[j].questionName = vue.stuList[i].qnlist[j].questionName.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{16}\x01?/g,"$&</br>").replace(/\x01/g,"");
                                    //     if(vue.stuList[i].qnlist[j].answerTypeDiv != 3){
                                    //         vue.stuList[i].qnlist[j].answerReltCont = vue.stuList[i].qnlist[j].answerReltCont.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{24}\x01?/g,"$&</br>").replace(/\x01/g,"");
                                    //     }
                                    // }
                                    // vue.stuList[i].noteCont = vue.stuList[i].noteCont.replace(/[^\x00-\xff]/g,"$&\x01").replace(/.{24}\x01?/g,"$&</br>").replace(/\x01/g,"");
                                // }
                            // }
                            // 2020/11/26 huangxinliang modify end
                            var text ='';
                            var count = 1;
                            for(var i=0;i<vue.stuList.length;i++){
                                text = text+'<ul>' +
                                    '<li style="overflow: hidden;">' +
                                    '<span style="width: 1%;height: 50%;float: left"></span>' +
                                    '<p style="margin-left: 2vw;font-weight: bold;height: 50%;font-size:15px;" class="text1">生徒面談</p>' +
                                    '</li>'+
                                    '<li class="text2" style="margin-left: 5vw;"><span style="width: 33%">生徒氏名</span><p style="margin-left: 5vw;">'+vue.stuList[i].stuNm+'</p></li>'+
                                    '<li class="text2" style="margin-left: 5vw;"><span style="width: 33%">日時</span><p style="margin-left: 5vw;">'+vue.stuList[i].displayTime+'</p></li>'+
                                /* 2020/12/30 V9.0 cuikailin modify start */
                                    '<li class="text2" style="margin-left: 5vw;"><span style="width: 33%">備考</span></li>'+
                                    '<li class="text2" style="margin-left: 0; width: 99%;"><textarea maxlength="500" rows="8" class="result_text" readonly="readonly">'+vue.stuList[i].noteCont+'</textarea></li>'
                                if(vue.stuList[i].qnlist.length >0){
                                    text +='<li style="overflow: hidden;">' +
                                    '<span style="width: 1%;height: 50%;float: left"></span>'+
                                    '<p style="margin-left: 2vw;font-weight: bold;height: 50%;font-size:15px;" class="text1">アンケート内容</p>' +
                                    '</li>'
                                    for(var j=0;j<vue.stuList[i].qnlist.length;j++) {
                                        // text = text+'<li class="text2" style="margin-left: 5vw;" ><span style="width: 32%">設問名'+(j+1)+'</span><p style="margin-left: 5vw; width: 140px;">'+vue.stuList[i].qnlist[j].questionName+'</p></li>'
                                        if (vue.stuList[i].qnlist[j].answerTypeDiv != 3) {
                                            //text = text+'<li class="text2" style="margin-left: 5vw;"><span style="width: 33%">'+vue.stuList[i].qnlist[j].questionName+'</span><p style="margin-left: 5vw; width: 50vw;">'+vue.stuList[i].qnlist[j].answerReltCont+'</p></li>'
                                            text += '<li class="text2" style="margin-left:0; width: 100%; background: rgb(178, 222, 130);"><span style="margin-left:5vw; width: 92%;word-break: break-all;">' + vue.stuList[i].qnlist[j].questionName + '</span></li>' +
                                                '<li class="text2" style="margin-left: 0; width: 97%;"><textarea maxlength="500" cols="50" rows="8" class="result_text" readonly="readonly">' + vue.stuList[i].qnlist[j].answerReltCont + '</textarea></li>'
                                        } else {
                                            var src = vue.stuList && vue.stuList[i].qnlist[j].answerReltCont ? vue.stuList[i].qnlist[j].answerReltCont : '';
                                            text += '<li class="text2" style="margin-left:0; width: 100%; background: rgb(178, 222, 130);"><span style="margin-left:5vw; width: 92%;word-break: break-all;">' + vue.stuList[i].qnlist[j].questionName + '</span></li>' +
                                                '<li class="text2" style="margin-left: 0; width: 100%;"><div id="photoContainer' + count + '" class="photo-container">' +
                                                '<img src="' + src + '" class="photo-image" onerror="this.src=\'../img/logo2.png\'" onclick="vue.showImg(' + count + ')" id="image' + count + '"/>' +
                                                '</div></li>'
                                            count++;
                                        }
                                    }
                                }
                                if(vue.stuList[i].errorMsg!=null && vue.stuList[i].flg == 1 && vue.stuList[i].refType != '2'){
                                    text = text +'<li style="margin-left: 2vw;height: 15vw;color: red">'+vue.stuList[i].errorMsg+'</li>'
                                }
                                if((vue.stuList[i].errorMsg==null && vue.stuList[i].flg == 1) || (vue.stuList[i].flg == 1 && vue.stuList[i].refType == '2')){
                                /* 2020/12/30 V9.0 cuikailin modify end */
                                    var a = "'";
                                    text = text+'<div style="height: 25%;" class="btnDiv">' +
                                        '<button id="change" class="_btn" onclick="vue.change('+vue.stuList[i].eventId+','+vue.stuList[i].applyId+')">申込を変更</button>' +
                                        '<button id="cancel" class="_btn" onclick="vue.cancel('+vue.stuList[i].applyId+','+a+vue.stuList[i].refType+a+')">申込をキャンセル</button>' +
                                        '</div>'
                                }
                                text +='</ul>';
                            }
                            $(".div_content").html(text);
                            /* 2020/11/18 V9.0 cuikailin add end */
                        }
                        if (data.flg) {
                            vue.flg = data.flg;
                        }
                        if (vue.stuList.length == 0) {
                            if (vue.flg == 1) {
                                layer.alert($.format($.msg.MSGCOMN0017, "予定情報"));
                            } else {
                                layer.alert($.format($.msg.MSGCOMN0017, "過去の参加履歴"));
                            }
                        }
                    }
                }
            });
        },
        //申込を変更するボタン押下時
        change: function (eventId, applyId) {
            window.location.href = "./F11014.html?eventId=" + eventId + "&applyId=" + applyId + "&flg=1";
        },
        //申込をキャンセルボタン押下時
        cancel: function (applyId,refType) {
            var object = {};
            object.applyId = applyId;
            object.refType = refType;
            window.location.href = "./F11018.html?" + $.param(object);
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
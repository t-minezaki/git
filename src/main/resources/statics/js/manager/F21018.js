//パラメータを取得する
var param = getParam();
//vue
var vm = new Vue({
    el: '#content',
    data: {
        //ローマンサウンドプラグイン
        // kuroshiro: {},
        //生徒情報
        stuInfo: {},
        //保護者情報
        guardInfoList: [],
        base: ''
    },
    mounted: function () {
        if(getCookie("equipment") === "phone"){
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21018.css"}).appendTo("head");
        }else {
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21018-1.css"}).appendTo("head");
        }
        //初期化
        this.setUp();
        //情報を得る
        this.getInfo();
    },
    updated: function () {
        //曜日を設定します
        this.setUpDayweek();
    },
    methods: {
        setUp: function () {
            /* 2020/12/11 保守 cuikailin modify start */
            $('.textarea').height($(window).height() * 0.2);
            $('.div_header').height($(window).height() * 0.1);
            $('.name').css('margin-top', $(window).height() * 0.02);
            $('#content').css('margin-top', $(window).height() * 0.1);
            $('#content').height($(window).height() * 0.9 - 54);
            if($(window).width()<= 534){
                $('.checkbox-container').css('width','33%');
                $('#proxyLogin').height($(window).height() * 0.055);
            }else {
                $('.checkbox-container').css('width','14%');
                $('#proxyLogin').height($(window).height() * 0.045);
            }
            /* 2020/12/11 保守 cuikailin modify end */
        },
        //ローマ字に変換
        // convertToRomaji: function (kanaName) {
        //     this.kuroshiro.convert(kanaName, {mode: "normal", to: "romaji"}).then(function (result) {
        //         $('#englishNm').val(result.toUpperCase());
        //     });
        // },
        //画像ソースを取得
        getSrc: function (photPath) {
            if (photPath == null || photPath == '') {
                photPath = '../img/logo2.png';
            }
            var img = new Image();
            img.onload = function () {
                if (img.width >= img.height) {
                    $('#photo').css("height", "30vw").css("width", "").css("transform", "translate(-50%)").css("left", "50%");
                } else {
                    $('#photo').css("height", "").css("width", "30vw").css("transform", "translate(0, -25%)").css("left", 0).css("top","4vw");
                }
            };
            img.src = photPath;
            $('#photo').prop('src', photPath);
            return photPath;
        },
        //電話のURLを作成します
        getHref: function (tel) {
            return 'tel://' + tel;
        },
        //情報を得る
        getInfo: function () {
            $.ajax({
                url: ctxPath + "/manager/F21018/init",
                dataType: 'json',
                type: 'GET',
                data: {
                    stuId: param.stuId
                },
                success: function (data) {
                    if (data.code != 0) {
                        layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認']
                        });
                        return;
                    }
                    if (data.stuInfo) {
                        if (data.stuInfo.memoCont != null){
                            data.stuInfo.memoCont = decodeURIComponent(data.stuInfo.memoCont);
                        }
                        vm.stuInfo = data.stuInfo;
                        //MANAMIRU1-197 会員コード 会員番号の表示 20201217 modify yang start--
                        vm.stuInfo.birthd = vm.getBirthday(vm.stuInfo.birthd,"birthday");
                        vm.stuInfo.admissionDate = vm.getBirthday(vm.stuInfo.admissionDate,"admissionDate");
                    }
                    if (data.guardInfo) {
                        vm.guardInfoList = data.guardInfo;
                    }
                }
            });
        },
        //生年月日の変更
        getBirthday: function (birthDay,flg) {
            if (birthDay){
                birthDay = birthDay.replace(/-/g, '/');
                var nowDate = new Date();
                var date = new Date(birthDay);
                birthDay = birthDay.replace(/(\d{4})\/(\d{2})\/(\d{2})\s(\d{2}):(\d{2})\S*/, '$1/$2/$3');
                var yearDiff = nowDate.getFullYear() - date.getFullYear();
                var monthDiff = nowDate.getMonth() - date.getMonth();
                yearDiff = monthDiff >= 0 ? yearDiff : yearDiff - 1;
                monthDiff = monthDiff >= 0 ? monthDiff : 12 + monthDiff;
                if (flg === 'birthday') {
                    return birthDay + ' (' + yearDiff + '歳' + monthDiff + 'ヶ月)';
                }
                if (flg === 'admissionDate') {
                    return birthDay + '(入会から' + yearDiff + '年' + monthDiff + 'か月)';
                }
                //MANAMIRU1-197 会員コード 会員番号の表示　20201217 modify yang end--
            } else {
                return '';
            }
        },
        //英語名を取得
        // getEnglishNm: function (englishNm, stuKnNm) {
        //     if (englishNm == null || englishNm == '') {
        //         this.convertToRomaji(stuKnNm);
        //     } else {
        //         return englishNm;
        //     }
        // },
        //F21026ページにジャンプ
        toF21026: function () {
            window.location.href = './F21026.html?stuId=' + param.stuId;
        },
        //曜日を設定します
        setUpDayweek: function () {
            if (vm.stuInfo.dayweekDiv && vm.stuInfo.dayweekDiv != null) {
                $("input[name='dayweek']").each(function (i) {
                    var r = RegExp(i + 1);
                    if (r.exec(vm.stuInfo.dayweekDiv)) {
                        $(this).prop("checked", true);
                    } else {
                        $(this).prop("checked", false);
                    }
                });
            } else {
                $("input[name='dayweek']").each(function (i) {
                    $(this).prop("checked", true);
                });
            }
        },
        //変更された情報を送信する
        submit: function () {
            // var englishName = $('#englishNm').val();
            //英語名チェック
            // if (reg.test(englishName)){
            //     layer.confirm($.format($.msg.MSGCOMN0145, '英語'), {
            //         skin: 'layui-layer-molv',
            //         title: '確認',
            //         closeBtn: 0,
            //         anim: -1,
            //         btn: ['確認']
            //     });
            //     return;
            // }
            var memo = $("#memo").val();
            var dayweek = '';
            //選択したすべてのチェックボックスの値を配列に保存します
            $("input[name='dayweek']:checked").each(function (i) {
                dayweek = dayweek + $(this).val() + ',';
            });
            dayweek = dayweek.substring(0, dayweek.length - 1);
            var formData = new FormData();
            // if (document.getElementById("file").files[0]) {
            //     formData.append('file', vm.base);
            // } else {
            //     formData.append('file', '');
            // }
            formData.append('memo', memo);
            // formData.append('englishName', englishName);
            formData.append('dayweek', dayweek);
            formData.append('stuId', param.stuId);
            formData.append('updDatime', vm.stuInfo.updDatime);
            $.ajax({
                url: ctxPath + "/manager/F21018/update",
                dataType: 'json',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code==0){
                        window.location.href = ctxPath + '/manager/F21017.html';
                    } else {
                        var index = layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                layer.close(index);
                                window.location.href = ctxPath + '/manager/F21017.html';
                            }
                        });
                    }
                }
            });
        }
    }
});

//選択したアバターを表示
function changepic() {
    var f = document.getElementById('file').files[0];
    if (f) {
        var src = window.URL.createObjectURL(new Blob([f], {type: 'image/*'}));
        // 读取图片元数据：方向
        var orient;
        var image = new Image();
        EXIF.getData(f, function () {
            EXIF.getAllTags(this);
            orient = EXIF.getTag(f, 'Orientation') ? EXIF.getTag(f, 'Orientation') : 1;
            var base64 = '';
            var reader = new FileReader();
            var ratio;
            reader.onload = function () {
                image.src = src;
                image.onload = function () {
                    var canvas = document.createElement("canvas");
                    var ctx = canvas.getContext("2d");
                    if ((ratio = image.width * image.height / 4000000)>1) {
                        ratio = ratio * 5;
                        image.width /= ratio;
                        image.height /= ratio;
                    }else {
                        ratio = 1;
                    }
                    changeCanvas(canvas, image.width, image.height, orient);
                    // 调整图片方向问题
                    adjustImgOrientation(ctx, this, orient, image.width, image.height);
                    base64 = canvas.toDataURL("image/jpeg", 0.5);
                    vm.base = base64.split(',')[1];
                    // $('#photo').attr('src', base64).show();
                    vm.getSrc(base64);
                };

            };
            reader.readAsDataURL(f);
        });
    }
}

function adjustImgOrientation(ctx, img, orientation, width, height) {
    switch (orientation) {
        case 3:
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 6:
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 8:
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        case 2:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.drawImage(img, 0, 0, width, height);
            break;
        case 4:
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            ctx.rotate(180 * Math.PI / 180);
            ctx.drawImage(img, -width, -height, width, height);
            break;
        case 5:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(90 * Math.PI / 180);
            ctx.drawImage(img, 0, -height, width, height);
            break;
        case 7:
            ctx.translate(height, 0);
            ctx.scale(-1, 1);
            ctx.rotate(270 * Math.PI / 180);
            ctx.drawImage(img, -width, 0, width, height);
            break;
        default:
            ctx.drawImage(img, 0, 0, width, height);
    }
}
function changeCanvas(canvas,width,height,orientation) {
    switch (orientation) {
        case 6:
            canvas.width = height;
            canvas.height = width;
            break;
        case 8:
            canvas.width = height;
            canvas.height = width;
            break;
        case 5:
            canvas.width = height;
            canvas.height = width;
            break;
        case 7:
            canvas.width = height;
            canvas.height = width;
            break;
        default:
            canvas.width = width;
            canvas.height = height;
    }
}
/* 2020/12/11 保守 cuikailin add start */
window.onresize = function(){
    vm.setUp();
}
/* 2020/12/11 保守 cuikailin add end */

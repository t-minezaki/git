var vm = new Vue({
    el:"#page",
    data:{
        //生徒名
        stuKnNmu:'',
        //氏名
        stuNmu:'',
        //生年月日
        birthd:'',
        //保護者
        guardInfoList:'',
        //学年
        schy:'',
        //学生情報
        studentInfo:{},
        mstUsrEntity:'',
        base:''
    },
    mounted: function () {
        this.getInfo()
    },
    methods: {
        //画像ソースを取得
        // getSrc: function (photPath) {
        //     var img = new Image();
        //     img.onload = function () {
        //         if (img.width >= img.height) {
        //             // $('#photo').css("height", "43vw").css("width", "").css("top", 0).css("transform", "translate(-50%)").css("left", "50%");
        //         } else {
        //             // $('#photo').css("height", "").css("width", "43vw").css("top", "-50%").css("transform", "translate(-50%, 25%)").css("left", "50%");
        //         }
        //     };
        //     img.src = photPath;
        //     $('#photo').prop('src', photPath);
        //     return photPath;
        // },
        //初期化
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/guard/F30416/init',
                type: 'GET',
                data: {},
                dataType: 'json',
                success: function (data) {
                    if(data.code!=0) {
                        layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認']
                        })
                    }else {
                        vm.studentInfo = data.studentInfo;
                        vm.studentInfo.admissionDate = vm.getBirthday(data.studentInfo.admissionDate,"admissionDate");
                        vm.guardInfoList=data.guardInfoList;
                        vm.stuKnNmu = data.studentInfo.flnmKnNm + " " + data.studentInfo.flnmKnLnm ;
                        vm.stuNmu   = data.studentInfo.flnmNm + " " + data.studentInfo.flnmLnm;
                        vm.schy = data.schy;
                        vm.birthd = vm.getBirthday(vm.studentInfo.birthd,"birthday");
                        vm.mstUsrEntity = data.mstUsrEntity;
                        if (vm.studentInfo.photPath){
                            //画像ソースを取得
                            var img = new Image();
                            img.onload = function () {
                                if (img.width >= img.height) {
                                    // $('#photo').css("height", "43vw").css("width", "").css("top", 0).css("transform", "translate(-50%)").css("left", "50%");
                                } else {
                                    // $('#photo').css("height", "").css("width", "43vw").css("top", "-50%").css("transform", "translate(-50%, 25%)").css("left", "50%");
                                }
                            };
                            img.src = vm.studentInfo.photPath;
                            $('#photo').prop('src', vm.studentInfo.photPath);
                        }
                    }
                }
            })
        },
        //生年月日の変更
        getBirthday: function(birthDay,flg){
            if (birthDay) {
                birthDay = birthDay.replace(/-/g, '/');
                var nowDate = new Date();
                var date = new Date(birthDay);
                birthDay = birthDay.replace(/(\d{4})\/(\d{2})\/(\d{2})\s(\d{2}):(\d{2})\S*/,'$1/$2/$3');
                var yearDiff = nowDate.getFullYear() - date.getFullYear();
                var monthDiff = nowDate.getMonth() - date.getMonth();
                yearDiff = monthDiff >= 0 ? yearDiff : yearDiff - 1;
                monthDiff = monthDiff >= 0 ? monthDiff : 12 + monthDiff;
                if (flg === 'birthday') {
                    return birthDay + ' (' + yearDiff + '歳' + monthDiff + 'ヶ月)'
                }
                if (flg === 'admissionDate') {
                    return birthDay + '(入会から' + yearDiff + '年' + monthDiff + 'か月)';
                }
            }else {
                return '';
            }
        }
    }
});
//QRコード表示
function QrBtn(){
    var url = ctxPath + "/manager/F09002/qrcode?content=" + vm.studentInfo.qrCod;
    var img_infor = "<img style='width:200px !important;' src='" + url + "' />"+"<span class='qr_span'>" + vm.mstUsrEntity.orgId + "_" + vm.mstUsrEntity.afterUsrId + "</span>";
    layer.open({
        type: 1,
        closeBtn: 3,
        shade: 0.1,
        title: false,
        shadeClose: false,
        resize: false,
        area: [200+'px', 230+'px'],
        content: img_infor
    });
}

//選択したアバターを表示
function changepic() {
    f = document.getElementById('file').files[0];
    if (f) {
        var src = window.URL.createObjectURL(new Blob([f], {type: 'image/*'}));
        // 画像メタデータの読み取り：方向
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
                    if(navigator.userAgent.indexOf("iPhone") === -1 || navigator.userAgent.indexOf("iPad") || navigator.userAgent.indexOf("Mac")){
                        orient = 1;
                    }
                    changeCanvas(canvas, image.width, image.height, orient);

                    // newImage変換された画像ですか：
                    let newWidth=0,newHeight=0;
                    let imgRatio;
                    if (image.height>=image.width){
                        newHeight=image.width;
                        newWidth=image.width;
                        imgRatio= image.width / image.height;
                    }else{
                        newHeight=image.height;
                        newWidth=image.height;
                        imgRatio=image.height/image.width;
                    }
                    let newImage = cropImage(image, {
                        width:newWidth,
                        height:newHeight,
                    });

                    // 画像の向きの調整に関する問題
                    adjustImgOrientation(ctx, this, orient, image.width, image.height);
                    // base64 = canvas.toDataURL("image/png", 0.1);
                    base64=newImage;
                    vm.base = base64.split(',')[1];
                    // vm.getSrc(base64);
                    // $(".update").css("display","unset")
                    //写真更新
                    var formData = new FormData();
                    formData.append('time', vm.studentInfo.updDatime);
                    if (document.getElementById("file").files[0]) {
                        formData.append('file', vm.base);
                    } else {
                        formData.append('file', '');
                    }
                    $.ajax({
                        url: ctxPath + '/guard/F30416/update',
                        type: 'POST',
                        data:  formData,
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            $(".update").css("display","none");
                            if(data.code!=0) {
                                layer.confirm(data.msg, {
                                    skin: 'layui-layer-molv',
                                    title: '確認',
                                    closeBtn: 0,
                                    anim: -1,
                                    btn: ['確認'],
                                    btn1: function () {
                                        location.reload()
                                    }
                                })
                            }else {
                                // layer.confirm(data.msg, {
                                //     skin: 'layui-layer-molv',
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     anim: -1,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                location.reload()
                                // }
                                // })
                            }
                        }
                    })
                };
            };
            reader.readAsDataURL(f);
        });
    };


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
// センタークロップ
function cropImage(img, ops){
    // 画像の元のサイズ；
    let imgOriginWidth = img.width,
        imgOriginHeight = img.height;
    // 画像が変形しないようにするための画像のアスペクト比；
    /*let imgRatio;

    if (imgOriginHeight>=imgOriginWidth){
        imgRatio= imgOriginWidth / imgOriginHeight;
    }else{
        imgRatio=imgOriginHeight/imgOriginWidth;
    }*/

    // トリミング後の画像の幅と高さ。デフォルト値は元の画像の幅と高さです。；
    let imgCropedWidth = ops.width || imgOriginWidth,
        imgCropedHeight = ops.height || imgOriginHeight;
    // 開始座標点のオフセットを計算し、, ；
    let dx = (imgCropedWidth - imgOriginWidth) / 2,
        dy = (imgCropedHeight - imgOriginHeight) / 2;
    // let dx = ( imgOriginWidth- imgCropedWidth) / 2,
    //     dy = (imgCropedHeight - imgOriginHeight) / 2;

    // キャンバスを作成し、トリミングされた幅と高さにキャンバスを設定します；
    let cvs = document.createElement('canvas');
    let ctx = cvs.getContext('2d');
    cvs.width = imgCropedWidth;
    cvs.height = imgCropedHeight;
    // 絵を描いてエクスポートする；

    let imgRatio;
    if (imgOriginHeight>=imgOriginWidth){
        imgRatio= imgOriginWidth / imgOriginHeight;
        ctx.drawImage(img, dx, dy, imgCropedWidth, imgCropedWidth / imgRatio);
    }else{
        imgRatio=imgOriginHeight/imgOriginWidth;
        ctx.drawImage(img, dx, dy,  imgCropedWidth / imgRatio,imgCropedWidth);
    }
    // ctx.drawImage(img, dx, dy, imgCropedWidth, imgCropedWidth / imgRatio);
    return cvs.toDataURL('image/jpeg', 0.9);
}

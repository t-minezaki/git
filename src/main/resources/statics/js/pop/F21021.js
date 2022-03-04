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
        });
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
        to21020:function (codCd,codValue) {
            parent.vm.stampCd = codCd;
            parent.codValue = codValue;
            var intervalID = setInterval(function () {
                clearInterval(intervalID);
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }, 50);
        },
        /* 2021/08/16 manamiru1-756 cuikl add start */
        paging:function (event){
            let touchE = event.changedTouches[0];
            this.endPos = {
                x: touchE.pageX,
                y: touchE.pageY,
                timeStemp: new Date()
            };
            let direction = this.getDirection(this.startPos.x, this.startPos.y, this.endPos.x, this.endPos.y);
            let nowPage = document.getElementsByClassName("pageBtn active");
            if (nowPage && nowPage.length>0){
                let pageNum = parseInt(nowPage[0].value);
                if (direction == 4){
                    if (pageNum > 0){
                        vm.getInfo(pageNum-1);
                    }
                } else if(direction == 3) {
                    if (pageNum < vm.length-1){
                        vm.getInfo(pageNum+1);
                    }
                }
            }
        },
        getAngle:function(angx, angy) {
            return Math.atan2(angy, angx) * 180 / Math.PI;
        },
        getDirection:function(startx, starty, endx, endy) {
            var angx = endx - startx;
            var angy = endy - starty;
            var result = 0; //滑らなかった
                //スライド距離が短すぎる
            if (Math.abs(angx) < 2 && Math.abs(angy) < 2) {
                return result;
            }
            var angle = this.getAngle(angx, angy);
            if (angle >= -135 && angle <= -45) {
                result = 1; //up
            } else if (angle > 45 && angle < 135) {
                result = 2; //down
            } else if ((angle >= 135 && angle <= 180) || (angle >= -180 && angle < -135)) {
                result = 3; //left
            } else if (angle >= -45 && angle <= 45) {
                result = 4; //right
            }
            return result;
        },
        start:function (event) {
            let touchS = event.targetTouches[0]; //touchStart
            this.startPos = {
                x:touchS.pageX,
                y:touchS.pageY,
                time: new Date()
            }; //最初のtouchの座標値を取る
        },
        move:function (event) {
            //画面に複数のtouchがあったり、ページがスケーリングされている場合はmoveを実行しない
            if(event.targetTouches.length > 1 || event.scale && event.scale !== 1) return;
        }
        /* 2021/08/16 manamiru1-756 cuikl add end */
    }
});



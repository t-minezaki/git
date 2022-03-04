//パラメータを取得する
var params = getParam();
var vm = new Vue({
    el: '.content',
    data: {
        timerTm: 0,//最後の停止の合計時間
        perfYmd: '',
        perfLearnStartTime: '',
        perfLearnTm: 0,
        timer: '',//タイマー
        countDownDate: '',//タイマーが最後に開始された時間
        minText: '00',
        secondText: ': 00'
    },
    mounted: function () {
        this.setUp();
    },
    methods: {
        setUp: function () {
            //do init
        },
        //タイマーを開始
        start: function () {
            //画像1を非表示
            $('#frame1').fadeOut();
            //表示画面2
            $('#frame2').fadeIn();
            var date = new Date();
            this.perfYmd = date.format('Y-m-d');
            this.perfLearnStartTime = date.format('Y-m-d H:i:s');
            this.setTimer();
        },
        //タイマーを一時停止/再開
        pause: function () {
            if (this.timer !== -1){
                clearInterval(this.timer);
                this.timer = -1;
                this.timerTm += (new Date().getTime() - vm.countDownDate) / 1000;
                $('.btn_pause').html('開始').css('margin-top', '3vw');
            }else {
                this.setTimer();
                $('.btn_pause').html('一時停止').css('margin-top', '3vw').css();
            }
        },
        //タイマーを設定
        setTimer: function () {
            this.countDownDate = new Date().getTime();
            this.timer = setInterval(function () {
                var sumTm = vm.timerTm + (new Date().getTime() - vm.countDownDate) / 1000;
                var min = parseInt(sumTm / 60);
                var second = parseInt(sumTm % 60);
                var minText = min < 10 ? '0' + min : min;
                var secondText = second < 10 ? '0' + second : second;
                vm.minText = minText;
                vm.secondText = ': '+secondText;
            }, 50);
        },
        toF11001: function () {
            window.location.href = 'F11001.html';
        },
        toF11004: function () {
            window.location.href = 'F11004.html';
        },
        //タイマーをリセット
        reset: function () {
            this.countDownDate = new Date().getTime();
            this.timerTm = 0;
            this.minText = '00';
            this.secondText = ': 00';
        },
        //タイマーを終了
        end: function () {
            clearInterval(this.timer);
            if (this.timer !== -1) {
                this.timerTm += (new Date().getTime() - vm.countDownDate) / 1000;
            }
            this.perfLearnTm = Math.ceil(this.timerTm / (60 * 15)) * 15;
            //構成パラメータ
            var data = {
                id: params.id,
                timerTm: Math.ceil(this.timerTm),
                perfLearnTm: this.perfLearnTm,
                perfYmd: this.perfYmd,
                perfLearnStartTime: this.perfLearnStartTime
            };
            if (params.editFlag){
                data.editFlag = params.editFlag;
            }
            window.location.href = 'F11007.html?' + $.param(data);
        }
    }
});
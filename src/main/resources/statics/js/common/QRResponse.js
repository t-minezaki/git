var vm = new Vue({
    el: '#content',
    data: {
        json: '',
        downloadList: [],
        loginResult: ''
    },
    mounted: function(){
        this.json = '';
        this.downloadList = [];
    },
    methods: {
        checkUpdate: function () {
            var val = $('#sendMsg').val();
            $.ajax({
                type: "POST",
                url: ctxPath + '/QRAPI',
                contentType: "application/json",
                data: val,
                success: function(r){
                    vm.json = JSON.stringify(r);
                    vm.downloadList = r.downloadinfo;
                }
            });
        },
        download: function (url, token) {
            var link = url + '?token=' + token + '&time=' + new Date().getMilliseconds();
            var xhr = new XMLHttpRequest();
            xhr.open('GET', link, true);
            xhr.responseType = "blob";
            xhr.addEventListener("progress", function(ev) {
                // 下载中事件：计算下载进度
                var max   = ev.total;
                var value = ev.loaded;
                var width = value/max*100;
                vm.json = width + '%';
            });
            xhr.addEventListener("loadend", function(ev) {
                if(ev.srcElement.response.type !== 'application/json'){
                    alert("download success!")
                }
            });
            xhr.addEventListener("error", function(ev) {
                showMsg(ev.error.message);
            });
            xhr.onload = function () {
                if (this.status === 200) {
                    var blob = this.response;
                    if (this.response.type == 'application/json'){
                        var reader = new FileReader();
                        reader.readAsText(blob, 'utf-8');
                        reader.onload = function (e) {
                            vm.json = reader.result;
                        }
                    }else {
                        var a = document.createElement('a');
                        a.download = decodeURI(url.substring(url.lastIndexOf('/') + 1));
                        a.href = window.URL.createObjectURL(new Blob([blob], {type: 'application/zip'}));
                        a.click();
                        $(a).remove();
                        vm.json = 'ダウンロード成功！';
                    }
                }
            };
            xhr.send()
        },
        getHref: function (url, token) {
            return url + '?token=' + token;
        },
        login: function () {
            var val = $('#login').val();
            $.ajax({
                type: "POST",
                url: ctxPath + '/QRAPI',
                contentType: "application/json",
                data: val,
                success: function(r){
                    vm.loginResult = r;
                }
            });
        },
        updatePwd: function (){
            $.ajax({
                type: "GET",
                url: ctxPath + '/manager/BTGKA1004/updateFirstPassword',
                contentType: "application/json",
                success: function(data){
                    if (data.code === 0){
                        layer.alert('全システムの初期パスワードが更新しました。')
                    }else {
                        layer.alert(data.msg);
                    }
                }
            });
        }
    }
});

function reload() {
}
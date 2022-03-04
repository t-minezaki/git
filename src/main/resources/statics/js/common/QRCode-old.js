var vm = new Vue({
    el: '#content',
    data: {
        json1: '',
        json2: '',
        json3: '',
        token: ''
    },
    mounted: function(){
        this.token = '';
    },
    methods: {
        sendMsg: function (index) {
            if (index !==1 && vm.token === ''){
                alert("ログインしてください");
                return;
            }
            var val;
            if (index === 1){
                val = $('#sendMsg1').val();
            } else if (index === 2){
                $('#sendMsg2').val($('#sendMsg2').val().replace(/"token":"\S*"/, '"token":"' + vm.token + '"'));
                val = $('#sendMsg2').val();
            } else if (index === 3){
                val = $('#sendMsg3').val();
            }
            $.ajax({
                type: "POST",
                url: ctxPath + '/QRATAPI',
                contentType: "application/json",
                data: val,
                success: function(r){
                    if (index === 1){
                        vm.json1 = vm.format(JSON.stringify(r));
                    } else if (index === 2){
                        vm.json2 = vm.format(JSON.stringify(r));
                    } else if (index === 3){
                        vm.json3 = vm.format(JSON.stringify(r));
                    }
                    if (r.token){
                        if (index === 1){
                            $('#sendMsg2').val($('#sendMsg2').val().replace(/"token":"\S*"/, '"token":"' + r.token + '"'));
                        }
                        vm.token = r.token;
                        $('#sendMsg3').val($('#sendMsg3').val().replace(/"token":"\S*"/, '"token":"' + r.token + '"'));
                    }
                }
            });
        },
        format: function (text) {
            text = text.replace(/"([^"]*?)":/g, '\n     "$1":');
            return text.replace(/}/g, '\n}');
        }
    }
});

function debounce(fn, wait) {
    var timer = null;
    return function () {
        var context = this;
        var args = arguments;
        if (timer) {
            clearTimeout(timer);
            timer = null;
        }
        timer = setTimeout(function () {
            fn.apply(context, args)
        }, wait)
    }
}
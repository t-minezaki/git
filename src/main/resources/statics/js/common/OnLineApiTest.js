var vm = new Vue({
    el: '#content',
    data: {
        json1: ''

    },
    methods: {
        sendMsg: function (index) {
            var val = $('#sendMsg1').val();
            $.ajax({
                type: "POST",
                url: ctxPath + '/SamlDataSync',
                contentType: "application/json",
                data: val,
                success: function(r){
                    vm.json1 = vm.format(JSON.stringify(r));
                },
                error: function (r) {
                    vm.json1 = vm.format(JSON.stringify(r.responseJSON));
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
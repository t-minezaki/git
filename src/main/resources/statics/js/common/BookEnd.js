var vm = new Vue({
    el: '#content',
    data: {
        json: ''
    },
    mounted: function(){
        this.token = '';
    },
    methods: {
        test: function (index) {
            $.ajax({
                type: "GET",
                url: ctxPath + '/manager/' + index + '/test',
                contentType: "application/json",
                success: function(r){
                    vm.json = r;
                }
            });
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
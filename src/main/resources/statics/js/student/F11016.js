var vue = new Vue({
    el: '#page',
    data: {
        stuNm: ''
    },
    mounted: function () {
        this.init();
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/student/F11016/init',
                type: 'GET',
                success: function (data) {
                    if (data.stuNm) {
                        vue.stuNm = data.stuNm;
                    }
                }
            });
        }
    }
});
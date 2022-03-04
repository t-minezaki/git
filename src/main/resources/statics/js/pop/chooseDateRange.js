function parseUrl() {
    var url = decodeURI(location.href);
    var i = url.indexOf('?');
    if (i == -1) return;
    var querystr = url.substr(i + 1);
    var arr1 = querystr.split('&');
    var arr2 = new Object();
    for (i in arr1) {
        var ta = arr1[i].split('=');
        arr2[ta[0]] = ta[1];
    }
    return arr2;
}
var param = parseUrl();
var vm = new Vue({
    el: "#contentDiv",
    data: {
        rangeStartDay: '',
        today: ''
    },
    mounted: function () {
        this.rangeStartDay = decodeURIComponent(param['rangeStartDay']).replace(/-/g, '\/');
        this.today = decodeURIComponent(param['today']).replace(/-/g, '\/');
        this.setUp();
    },
    methods: {
        setUp: function(){
            var scrWidth = "40%";
            var scrHeight = "40%";
            laydate.render({
                elem: '#startDate',
                format: 'yyyy/MM/dd',
                value: new Date(this.rangeStartDay),
                eventElem: '#startPic',
                trigger: 'click',
                area: [scrWidth, scrHeight],
                max: this.today,
                done: function(value, date){
                    if (vm.rangeStartDay != value){
                        vm.rangeStartDay = value;
                    }
                }
            });
            laydate.render({
                elem: '#endDate',
                format: 'yyyy/MM/dd',
                value: new Date(this.today),
                eventElem: '#endPic',
                trigger: 'click',
                area: [scrWidth, scrHeight],
                max: this.today,
                done: function(value, date){
                    if (vm.today != value){
                        vm.today = value;
                    }
                }
            });
        }
    }
});
function callbackdata() {
    var row = {
        'rangeStartDay': vm.rangeStartDay,
        'today':vm.today
    }
    return row;
}
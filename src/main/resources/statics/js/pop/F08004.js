var vm = new Vue({
    el: '#app',
    data: {
        eventEntity: {},
        orgList:[]
    },
    computed: {},
    methods: {
    },
    mounted: function () {
    },
    updated: function () {

        $("#event_cont").html(vm.eventEntity.eventCont);
    }
});

var param = getParam();
var eventId = param.id;

$(function () {

    $.get(ctxPath + "/pop/F08004/init",
        {
            eventId: eventId
        },function (data) {
            if (data.code == 0) {
                vm.eventEntity = data.eventEntity;
                vm.eventEntity.eventCont = decodeURIComponent(vm.eventEntity.eventCont);
                vm.orgList = data.orgList;
            } else {
                showMsg(data.msg);
            }
        });
});
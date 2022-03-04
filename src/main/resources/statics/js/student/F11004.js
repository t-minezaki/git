
var vm = new Vue({
    el: "#page",
    data: {
        planList: []
    },
    mounted: function () {
        this.showPlan();
    },
    updated: function () {

    },
    methods: {
        showPlan: function () {
            $.ajax({
                url: ctxPath + '/student/F11004/init',
                type: 'get',
                datatype: 'json',
                data: {},
                success: function (data) {
                    if (data.code === 0) {
                        if(data.dto){
                            vm.planList = data.dto;
                            if(data.dto.length == 0){
                                layer.alert($.format($.msg.MSGCOMN0017, "計画情報"));
                            }
                        }
                    }
                }
            });
        },
        // 2020/11/23 zhangminghao modify start
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        toF11012: function(obj,type){
            var content = "";
            if (type == 1){
                window.localStorage.setItem("submitType", "1");
                content = "../pop/F11012.html?id="+0;
            } else {
                window.localStorage.setItem("submitType", "0");
                content = "../pop/F11012.html?id="+obj.id;
            }
            /* 2021/01/28 cuikailin MANAMIRU1-393 end */
            var srcWidth = "300px";
            var srcHeight = "140px";
            layer.open({
                id: 'F11012',
                type: 2,
                title: ['登録方法を選択してください','background-color:#FFFFFF !important;color:#3e3e3e;font-weight:bold'],
                shade: 0.1,
                closeBtn: 2,
                offset:['35%'],
                shadeClose: false,
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                /* 2021/01/28 cuikailin MANAMIRU1-393 start */
                // content: ["../pop/F11012.html?id="+obj.id],
                /* 2021/01/28 cuikailin MANAMIRU1-393 end */
                content:[content] ,
                success: function (layero, index) {
                },
                end: function () {
                    reload();
                }
            })
        },
        /* 2021/01/28 cuikailin MANAMIRU1-393 start */
        // toF11006(){
        //     window.localStorage.setItem("submitType", "1");
        //     parent.location.href= '../student/F11006.html'
        // // 2020/11/23 zhangminghao modify end
        // }
        /* 2021/01/28 cuikailin MANAMIRU1-393 end */
    }
});
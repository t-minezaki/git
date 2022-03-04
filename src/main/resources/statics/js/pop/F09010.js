// サブウィンドウインデックスを取得する
var index = parent.layer.getFrameIndex(window.name);
var vm = new Vue({
    el: '.content',
    data: {
        orgIdList:[],
        chosenOrgIdList: [],
        orgIds:[]
    },
    updated: function () {
    },
    mounted: function () {
        this.init();
        $("#toright").click(function () {
            // 2020/12/8 huangxinliang modify start
            var chosenOrgIdList = [];
            $("#select1 option:selected").each(function (){
                chosenOrgIdList.push($(this).val());
            });
            for(var i = vm.orgIdList.length - 1; i >= 0; i--){
                var org = vm.orgIdList[i];
                if (chosenOrgIdList.indexOf(org.orgId) >= 0){
                    vm.chosenOrgIdList.push(org);
                    vm.orgIdList.splice(i, 1);
                }
            }
            $("#select2 option:selected").attr("selected", false);
            $("#select1 option:selected").attr("selected", false);
            vm.orderList();
            // 2020/12/8 huangxinliang modify end
        });
        $("#toleft").click(function () {
            // 2020/12/8 huangxinliang modify start
            var chosenOrgIdList = [];
            $("#select2 option:selected").each(function (){
                chosenOrgIdList.push($(this).val());
            });
            for(var i = vm.chosenOrgIdList.length - 1; i >= 0; i--){
                var org = vm.chosenOrgIdList[i];
                if (chosenOrgIdList.indexOf(org.orgId) >= 0){
                    vm.orgIdList.push(org);
                    vm.chosenOrgIdList.splice(i, 1);
                }
            }
            $("#select2 option:selected").attr("selected", false);
            $("#select1 option:selected").attr("selected", false);
            vm.orderList();
            // 2020/12/8 huangxinliang modify end
        });
        //all add to right
        $("#allToRight").click(function () {
            // 2020/12/8 huangxinliang modify start
            for(var i = vm.orgIdList.length - 1; i >= 0; i--){
                var org = vm.orgIdList[i];
                vm.chosenOrgIdList.push(org);
                vm.orgIdList.splice(i, 1);
            }
            vm.orderList();
            // 2020/12/8 huangxinliang modify end
        });
        //all add to left
        $("#allToLeft").click(function () {
            // 2020/12/8 huangxinliang modify start
            for(var i = vm.chosenOrgIdList.length - 1; i >= 0; i--){
                var org = vm.chosenOrgIdList[i];
                vm.orgIdList.push(org);
                vm.chosenOrgIdList.splice(i, 1);
            }
            vm.orderList();
            // 2020/12/8 huangxinliang modify end
        });
    },
    methods: {
        //初期表示
        init: function () {
            $.ajax({
                url: ctxPath + '/pop/F09010/init',
                data: {},
                type: 'GET',
                success: function (data) {
                    if (data.code == 0){
                        if (data.orgIdList){
                            // 2020/12/7 huangxinliang modify start
                            parent.orgIdList.indexOf()
                            for(var i = 0; i < data.orgIdList.length; i++){
                                var org = data.orgIdList[i];
                                if (parent.orgIdList.indexOf(org.orgId) >= 0){
                                    vm.chosenOrgIdList.push(org);
                                }else {
                                    vm.orgIdList.push(org);
                                }
                            }
                            // 2020/12/7 huangxinliang modify end
                        }
                        if (data.orgIds){
                            vm.orgIds = data.orgIds;
                        }
                    }
                }
            })
        },
        //選択
        submit:function () {
            var orgIdListRight = [];
            $('#select2 option').each(function () {
                orgIdListRight.push($(this).val());
            });
            parent.orgIdList = orgIdListRight;
            parent.closeFlg = true;
            parent.layer.close(index);
        },
        // 2020/12/8 huangxinliang modify start
        orderList: function (){
            this.orgIdList.sort(this.sortFun);
            this.chosenOrgIdList.sort(this.sortFun);
        },
        sortFun: function (o1, o2){
            if (o1.level < o2.level){
                return -1;
            }else if (o1.level > o2.level){
                return 1;
            }else {
                var id1 = o1.orgId.replaceAll(' ', '').toLocaleUpperCase();
                var id2 = o2.orgId.replaceAll(' ', '').toLocaleUpperCase();
                if (id1 < id2){
                    return -1;
                }else if (id1 > id2){
                    return 1;
                }else{
                    return 0;
                }
            }
        }
        // 2020/12/8 huangxinliang modify end
    }
});

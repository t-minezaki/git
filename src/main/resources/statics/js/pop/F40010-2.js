Array.prototype.unique3 = function(){
    var res = [];
    var json = {};
    for(var i = 0; i < this.length; i++){
        if(!json[this[i]]){
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};

var param = getParam();

var vm = new Vue({
    el: "#main",
    data: {
        orgId: "",
        mstOrgEntityList: [],
        f40010DspDtoList: "",
        userListRight: [],
        stuAndGuard: []
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {

    },

    methods: {
        getInfo: function () {

            $.ajax({
                    url: ctxPath + '/manager/F40010/getOrgList',
                    type: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        if (data.mstOrgEntityList) {
                            vm.mstOrgEntityList = data.mstOrgEntityList;
                        }
                    }
                }
            )
        },
        getUserList: function () {

            var item = null;
            var obj = document.getElementsByName("choose");
            for (var i = 0; i < obj.length; i++) { //遍历Radio
                if (obj[i].checked) {
                    item = obj[i].value;
                }
            }

            var orgId = $("#orgId").val();
            $.ajax({
                url: ctxPath + '/manager/F40010/search',
                type: 'GET',
                data: {
                    orgId: orgId,
                    id: param.id,
                    roleDiv: item
                },
                datatype: 'json',
                success: function (data) {

                    if (item == null) {
                        var msg = "役割を選択する必要があります";
                        parent.layer.alert(msg);
                    } else {
                        if (data.code != 0) {
                            parent.layer.alert(data.msg);
                        } else {
                            vm.f40010DspDtoList = [];
                            for (var i = 0; i < data.f40010DspDtoList.length; i ++) {

                                var flag = false;
                                for (var j = 0; j < vm.userListRight.length; j ++) {

                                    if (data.f40010DspDtoList[i].userId == vm.userListRight[j].userId) {
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    vm.f40010DspDtoList.push(data.f40010DspDtoList[i]);
                                }
                            }
                        }

                    }

                }
            })
        }

    }

});

vm.userListRight = [];
for (var i = 0; i < parent.vm.rightList.length; i ++) {

    vm.userListRight.push(parent.vm.rightList[i]);
}

$(function () {

    Array.prototype.remove = function(val) {
        for (var i = 0; i < this.length; i ++) {

            if (this[i].userId == val.userId) {

                this.splice(i, 1);
            }
        }
    };

    // ＞＞ボタン押下時
    $("#right_all").click(function () {

        var options = $("#select_left option");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                userId: $(option).val(),
                name: $(option).text()
            };
            vm.userListRight.push(stuAndGuard);
            vm.f40010DspDtoList.remove(stuAndGuard);
        }
    });

    // ＞ボタン押下時
    $("#right").click(function () {

        var options = $("#select_left option:selected");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                userId: $(option).val(),
                name: $(option).text()
            };
            vm.userListRight.push(stuAndGuard);
            vm.f40010DspDtoList.remove(stuAndGuard);
        }
    });

    // ＜＜ボタン押下時
    $("#left_all").click(function () {

        var options = $("#select_right option");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                userId: $(option).val(),
                name: $(option).text()
            };
            vm.userListRight.remove(stuAndGuard);
            vm.f40010DspDtoList.push(stuAndGuard);
        }
    });

    // ＜ボタン押下時
    $("#left").click(function () {
        var rightSelected = [];
        var options = $("#select_right option:selected");
        for (var i = 0; i < options.length; i++) {
            var option = options[i];
            var stuAndGuard = {
                userId: $(option).val(),
                name: $(option).text()
            };
            vm.userListRight.remove(stuAndGuard);
            vm.f40010DspDtoList.push(stuAndGuard);
        }
    });

    var item = null;
    var obj = document.getElementsByName("age")
    for (var i = 0; i < obj.length; i++) { //遍历Radio
        if (obj[i].checked) {
            item = obj[i].value;
        }
    }

    $("#conf").click(function () {

            parent.vm.rightList = [];
            for (var i = 0; i < vm.userListRight.length; i ++) {

                parent.vm.rightList.push(vm.userListRight[i]);
            }
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    );

});

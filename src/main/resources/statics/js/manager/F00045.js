// $(function () {
//     var srcHeight = $(window).height() - ($(window).width() / 100) * 10;
//     $(".content").css("height", srcHeight)
// });
var param = getParam();
var guardId = "";
var upDtTm = "";
var adr = "";
var adr1 = "";
var adr2 = "";
var postcdMnum = "";
var postcdBnum = "";
var selGuardId = "";

$(function () {
    if (param.guardId) {
        guardId = param.guardId;
    }
});
var vm = new Vue({
    el: "#main",
    data: {
        showData: [],
        dataLen: '',
        style1: {
            background: 'grey',
            'pointer-events': 'none'
        },
        style2: {
            background: '#00a65a',
            'pointer-events': 'unset'
        },
        guardIdList: []
    },
    mounted: function () {
        this.init()
    },
    methods: {
        init: function () {
            $.ajax({
                url: ctxPath + '/manager/F00045/init',
                type: 'GET',
                data: {
                    stuId: param.stuId
                },
                success: function (data) {
                    $("#orgId").text(data.org.orgId);
                    $("#orgNm").text(data.org.orgNm);
                        vm.showData = data.showData;
                        vm.dataLen = data.showData.length;
                        for (var i = 0; i < vm.showData.length; i++) {
                            if (vm.showData[i].userId != null) {
                                vm.guardIdList.push(vm.showData[i].userId);
                            }
                            adr = "";
                            //保護者住所
                            if (vm.showData[i].postcdMnum) {
                                postcdMnum = vm.showData[i].postcdMnum;
                            }
                            if (vm.showData[i].postcdBnum) {
                                postcdBnum = vm.showData[i].postcdBnum;
                            }
                            if (vm.showData[i].adr1) {
                                adr1 = vm.showData[i].adr1;
                            }
                            if (vm.showData[i].adr2) {
                                adr2 = vm.showData[i].adr2;
                            }
                            adr = "";
                            if (!vm.showData[i].postcdMnum || !vm.showData[i].postcdBnum || vm.showData[i].postcdMnum.trim().length === 0 || vm.showData[i].postcdBnum.trim().length === 0) {
                                if (adr1 || (adr1).trim().length !== 0) {
                                    adr = adr.concat(adr1);
                                }
                            } else {
                                if (adr1 || (adr1).trim().length !== 0) {
                                    adr = adr.concat(postcdMnum + '-' + postcdBnum + ' ' + adr1);
                                } else {
                                    adr = adr.concat(postcdMnum + '-' + postcdBnum);
                                }
                            }
                            vm.showData[i].adr = adr;
                        }
                        //生徒
                        $("#stuId").val(data.showData[0].stuId);
                        $("#stuNm").val(data.showData[0].stuSei + ' ' + data.showData[0].stuMei);
                        $("#stuKnNm").val(data.showData[0].stuKnSei + ' ' + data.showData[0].stuKnMei);
                        $("#birthd").val(data.showData[0].birth);
                        $("#sex").val(data.showData[0].sex);
                        $("#schy").val(data.showData[0].schy);
                        upDtTm = data.showData[0].updDatm;
                    if (data.code != 0) {
                        showMsg(data.msg);
                    }
                }
            })
        },
        selectBtn: function (guardId) {
            selGuardId = guardId;
            layer.open({
                id: 'f00055',
                type: 2,
                title: false,
                shade: 0.1,
                closeBtn: 0,
                shadeClose: false,
                area: ['60%', '65%'],
                fixed: true,
                resize: false,
                content: ["../pop/F00055.html?roleDiv=3", 'no']
            })
        }
    }
})


function showF00055Data(params) {
    var usrId = params.split(',')[0]
    var reltnspDiv = params.split(',')[1]
    if (selGuardId && selGuardId != ''){
        if (usrId != selGuardId && vm.guardIdList.indexOf(usrId) !='-1'){
            showMsg($.format($.msg.MSGCOMN0144, "保護者"));
            return;
        }
        var newList= [];
        for (var i = 0; i < vm.showData.length; i++) {
            if (vm.showData[i].userId != selGuardId) {
                if (vm.showData[i].tuzukiGara == reltnspDiv) {
                    showMsg($.format($.msg.MSGCOMN0144, "保護者の続柄"));
                    return;
                }else {
                    newList.push(vm.showData[i]);
                }
            }
        }
        vm.showData = newList;
        vm.guardIdList[vm.guardIdList.indexOf(selGuardId)] = usrId;
    }else {
        for (var i = 0; i < vm.guardIdList.length; i++) {
            if (vm.guardIdList[i] == usrId) {
                showMsg($.format($.msg.MSGCOMN0144, "保護者"));
                return;
            }
        }
        for (var i = 0; i < vm.showData.length; i++) {
            if (vm.showData[i].tuzukiGara == reltnspDiv) {
                showMsg($.format($.msg.MSGCOMN0144, "保護者の続柄"));
                return;
            }
        }
        vm.guardIdList.push(usrId);
    }
    if (vm.guardIdList.length >= 5) {
        $("#btnDiv button").css("background", 'grey').css('pointer-events', 'none');
    }
    $.ajax({
        url: ctxPath + "/manager/F00045/select",
        type: "get",
        data: {
            guardId: usrId
        },
        success: function (data) {
            if (data.code === 0) {
                vm.showData.push(data.showData);
                for (var i = 0; i < vm.showData.length; i++) {
                    adr = "";
                    //保護者住所
                    if (vm.showData[i].postcdMnum) {
                        postcdMnum = vm.showData[i].postcdMnum;
                    }
                    if (vm.showData[i].postcdBnum) {
                        postcdBnum = vm.showData[i].postcdBnum;
                    }
                    if (vm.showData[i].adr1) {
                        adr1 = vm.showData[i].adr1;
                    }
                    if (vm.showData[i].adr2) {
                        adr2 = vm.showData[i].adr2;
                    }
                    adr = "";
                    if (!vm.showData[i].postcdMnum || !vm.showData[i].postcdBnum || vm.showData[i].postcdMnum.trim().length === 0 || vm.showData[i].postcdBnum.trim().length === 0) {
                        if (adr1 || (adr1 + adr2).trim().length !== 0) {
                            adr = adr.concat(adr1);
                        }
                    } else {
                        if (adr1 || (adr1 ).trim().length !== 0) {
                            adr = adr.concat(postcdMnum + '-' + postcdBnum + ' ' + adr1);
                        } else {
                            adr = adr.concat(postcdMnum + '-' + postcdBnum);
                        }
                    }
                    vm.showData[i].adr = adr;
                }
            }
        }
    })
}

function updateBtn() {
    if (vm.guardIdList.length == 0) {
        showMsg($.format($.msg.MSGCOMN0096, "保護者", "保護者追加"));
        return;
    }
    var index = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
        closeBtn: 0,
        title: '確認',
        btn: ["キャンセル", "確認"],
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F00045/update',
                type: 'GET',
                data: {
                    stuId: param.stuId,
                    guardId: JSON.stringify(vm.guardIdList),
                    updDatm: upDtTm
                },
                success: function (data) {
                    if (data.code === 0) {
                        // layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {
                        //     closeBtn: 0,
                        //     title: '確認',
                        //     btn: ["確認"],
                        //     btn1: function () {
                                window.location.href = "F00044.html";
                        //     }
                        // });
                    } else {
                        index = layer.close(index);
                        showMsg(data.msg);
                    }
                }
            })
        },
        btn1: function () {
            index = layer.close(index);
        }
    });
}
var param = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        org: '',
        orgList: [],
        orgId: '',
        upDatime : ''
    },
    updated: function () {

    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F00003/init',
                data: {
                    id: param.id
                },
                dataType: 'json',
                type: 'GET',
                success: function (data) {
                    if (data.org) {
                        vm.org = data.org;
                    }
                    if (data.orgList) {
                        vm.orgList = data.orgList;
                    }
                    if (data.orgId) {
                        vm.orgId = data.orgId;
                    }
                    vm.upDatime = data.upDatime;
                }
            });
        },
        isDisabled: function (org) {
            if (org == null || org == '') {
                return false;
            }
            return true;
        },
        //半角数字check
        isHalfAngle: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (48 > str.charCodeAt(i) || str.charCodeAt(i) > 57) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        //半角英数字
        isHalfAngleNumber: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (48 > str.charCodeAt(i) || (65 > str.charCodeAt(i) && str.charCodeAt(i) > 57) || (90 < str.charCodeAt(i) && str.charCodeAt(i) < 97) || str.charCodeAt(i) > 122) {
                    flg = false;
                    break;
                }
            }
            return flg
        },
        isHalf: function (str) {
            var flg = true;
            for (var i = 0; i < str.length; i++) {
                if (str.charCodeAt(i) > 256) {
                    flg = false;
                    break;
                }
            }
            return flg;
        },
        cancelFn: function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        getSelected: function (org, orgId, ob) {
            if (org != null && org != '') {
                if (org.upplevOrgId == ob.orgId) {
                    return "selected";
                } else {
                    return '';
                }
            } else {
                if (orgId == ob.orgId) {
                    return "selected";
                } else {
                    return '';
                }
            }
        },
        submitFn: function () {
            this.formCheck();
            if ($("#form1").valid() == false) {
                return;
            }
            var mgr_flg = '0';
            if ($("#mgr_flg").is(':checked')) {
                mgr_flg = '1';
            }
            $.ajax({
                url: ctxPath + '/pop/F00003/submit',
                data: {
                    id: param.id,
                    oldOrgId: vm.org.orgId,
                    orgId: $("#orgId").val(),
                    orgNm: $("#orgNm").val(),
                    upLevel: $("#upplevOrgId option:selected").attr("level"),
                    upplevOrgId: $("#upplevOrgId").val(),
                    mgrFlg: mgr_flg,
                    upTime :vm.upDatime
                },
                dataType: 'json',
                type: 'POST',
                async: true,
                success: function (data) {
                    if (data.code == 0) {
                        // parent.layer.alert(data.msg);
                        // 2020/12/7 huangxinliang modify start
                        top.orgSelectComponent.loadOrg();
                        // 2020/12/7 huangxinliang modify end
                        parent.searchFn();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    } else {
                        parent.layer.alert(data.msg);
                    }
                }
            });
        },
        formCheck: function () {
            $("#form1").validate({
                rules: {
                    orgId: {
                        /* 2020/11/26 V9.0 liguangxin add start */
                        alphaNumSymbol: true,
                        /* 2020/11/26 V9.0 liguangxin add end */
                        required: true,
                        maxlength: 20,
                        hankaku: '組織ID'

                    },
                    orgNm: {
                        required: true,
                        maxlength: 50
                    },
                    upplevOrgId: {
                        required: true
                    }
                },
                debug: false,
                onfocusout: false,
                onkeyup: false,
                messages: {
                    // 組織ＩＤ
                    orgId: {
                        /* 2020/11/26 V9.0 liguangxin add start */
                        alphaNumSymbol: $.format($.msg.MSGCOMD0004, "組織ＩＤ"),
                        /* 2020/11/26 V9.0 liguangxin add end */
                        required: $.format($.msg.MSGCOMD0001, "組織ＩＤ"),
                        maxlength: $.format($.msg.MSGCOMD0011, "組織ＩＤ", "20")
                    },
                    // 組織名
                    orgNm: {
                        required: $.format($.msg.MSGCOMD0001, "組織名"),
                        maxlength: $.format($.msg.MSGCOMD0011, "組織名", "50")
                    },
                    upplevOrgId: {
                        required: $.format($.msg.MSGCOMD0001, "上位組織"),
                    }
                },
                showErrors: function (errorMap, errorList) {
                    if (errorList.length != 0) {
                        parent.layer.alert(errorList[0].message);
                        return;
                    }
                }
            })
        }
    }
});
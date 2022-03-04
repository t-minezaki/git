var vue = new Vue({
    el: '.content',
    data: {
        orgId: '',
        orgNm: '',
        roleList: [],
        users: []
    },
    mounted: function () {
        this.getInitData();
        this.getObjList();
    },
    methods: {
        getInitData: function () {
            $.ajax({
                url: ctxPath + '/manager/F09023/init',
                type: 'get',
                success: function (data) {
                    if (data.code === 0) {
                        if (data.orgId) {
                            vue.orgId = data.orgId;
                        }
                        if (data.orgNm) {
                            vue.orgNm = getOrgName(data.orgNm);
                        }
                        if (data.roleList) {
                            vue.roleList = data.roleList;
                        }
                    }
                }
            });
        },
        getObjList: function () {
            $.jgrid.gridUnload('jqGrid');
            $("#jqGrid").jqGrid(
                {
                    datatype: "local",
                    colNames: ['利用者ログインＩＤ'/*, '組織共用キー'*/, ''],
                    colModel: [
                        {name: 'loginId', index: 'loginId', align: "center"},
                        // {name: 'commKey', index: 'commKey', align: "center"},
                        {name: 'del', index: 'del', align: "center",
                            formatter(cell, option, object) {
                                var roleDiv = $('input[name="usrRole"]:checked').val();
                                return "<button class='_delete' onclick='_remove(\""+object.loginId+"\", \""+roleDiv+"\")'>削除</button>";
                            }
                        }
                    ],
                    multiselect: false,
                    rowNum: 15,
                    viewrecords: true,
                    pager: "#jqGridPager"
                }
            );
            if (this.users || this.users.length == 0) {
                var localData = {page: 1, total: 2, records: "2", rows: this.users};
                localData.rows = this.users;
                localData.records = this.users.length;
                localData.total = (this.users.length % 15 == 0) ? (this.users.length / 15) : (Math.floor(this.users.length / 15) + 1);
                var reader = {
                    root: function (obj) {
                        return localData.rows;
                    },
                    page: function (obj) {
                        return localData.page;
                    },
                    total: function (obj) {
                        return localData.total;
                    },
                    records: function (obj) {
                        return localData.records;
                    }, repeatitems: false
                };
                $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
            }
        },
        addObj: function () {
            var user = {};
            var loginId = $("input[name='loginId']").val();

            if (!loginId || loginId === ''){
                showMsg($.format($.msg.MSGCOMD0001, "利用者ログインID"))
                return;
            }

            var roleDiv = $('input[name="usrRole"]:checked').val();
            user.loginId = loginId;
            user.roleDiv = roleDiv;
            $.ajax({
                url: ctxPath + '/manager/F09023/check',
                type: 'get',
                datatype: 'json',
                data: {
                    afterUsrId: loginId,
                    roleDiv: roleDiv
                },
                success: function (data) {
                    if (data.code === 0) {
                        for (var i = 0; i < vue.users.length; i++) {
                            if (vue.users[i].loginId == loginId && vue.users[i].roleDiv == roleDiv) {
                                showMsg($.format($.msg.MSGCOMN0010, "利用者ログインID", "同報対象一覧"));
                                return;
                            }
                        }
                        vue.users.push(user);
                        vue.getObjList();
                    } else {
                        showMsg(data.msg)
                    }
                }
            });
        },
        submit: function () {
            var roleDiv = $('input[name="usrRole"]:checked').val();
            var loginId = $("input[name='loginId']").val();
            if (!loginId || loginId === ''){
                showMsg($.format($.msg.MSGCOMD0001, "利用者ログインID"))
                return;
            }
            var msg = layer.confirm($.format($.msg.MSGCOMN0021, "登録"), {
                skin: 'layui-layer-molv',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['キャンセル','確認'],
                btn2: function () {
                    $.ajax({
                        url: ctxPath + '/manager/F09023/submit',
                        type: 'post',
                        datatype: 'json',
                        data: {
                            list: JSON.stringify(vue.users),
                            roleDiv: roleDiv
                        },
                        success: function (data) {
                            if (data.code == 0) {
                                // var msg1 = layer.confirm($.format($.msg.MSGCOMN0022, "登録"), {btn: ['確認']}, function () {
                                //     layer.close(msg1);
                                    vue.users = [];
                                    vue.getObjList();
                                    window.location.reload();
                                // });
                            } else {
                                layer.close(msg);
                                showMsg(data.msg);
                            }
                        }
                    });
                },
                btn1: function () {
                    layer.close(msg);
                }
            });
        },
        returnToF00041: function () {
            window.location.href = 'F00041.html';
        }
    }
});

Array.prototype.remove = function (loginId, roleDiv) {
    for (var i = 0; i < this.length; i++) {
        if (this[i].loginId == loginId && this[i].roleDiv == roleDiv) {
            this.splice(i, 1);
        }
    }
};

function _remove(loginId, roleDiv) {
    vue.users.remove(loginId, roleDiv);
    // vue.users.splice(obj,1);
    vue.getObjList()
}
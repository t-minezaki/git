var param = getParam();
var time = 0;
$(function () {
    if (param.roleDiv == '2') {
        document.getElementById("student").style.display = "none";
        document.getElementById("guard").style.display = "none";
        document.getElementById("student_list").style.display = "none";
        document.getElementById("guard_list").style.display = "none";
        $("#jqGrid_mentor").jqGrid({
            url: ctxPath + '/pop/F00055/mentorList',
            datatype: "json",
            colModel: [
                {
                    label: '選択', width: 80, sortable: false, formatter: function (cell, option, object) {
                        return "<input onclick='radio_select_function()' type='radio' id='" + object.id + "' name='single' value='" + object.usrId + "'>";
                    }
                },
                {
                    label: '先生ID',
                    name: 'mentorId',
                    index: 'mentor_id',
                    width: 80,
                    key: true,
                    sortable: false,
                    align: "center"
                },
                {label: '先生姓名', name: 'mentorNm', index: 'mentor_nm', width: 80, sortable: false, align: "center"},
            ],
            viewrecords: true,
            height: 385,
            rowNum: 30,
            //rowList: [10, 30, 50],
            //rownumbers: false,
            rownumWidth: 25,
            autowidth: true,
            // width: 1920,
            multiselect: false,
            singleselect: true,
            pager: "#jqGridPager_mentor",
            jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },
            onSelectRow: function () {
                var rowId = $("#jqGrid_mentor").jqGrid("getGridParam", "selrow");
                rowClick(rowId);
            },
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                $("#jqGrid_mentor").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            },
            loadComplete: function (data) {
                if (data.code != 0) {
                    showMsg(data.msg);
                } else {
                    if (data.mstOrgEntity) {
                        $("#orgId").text(data.mstOrgEntity.orgId);
                        $("#orgNm").text(data.mstOrgEntity.orgNm);
                    }
                }
            },
        });
    }
    if (param.roleDiv == '3') {
        document.getElementById("student").style.display = "none";
        document.getElementById("mentor").style.display = "none";
        document.getElementById("student_list").style.display = "none";
        document.getElementById("mentor_list").style.display = "none";
        $("#jqGrid_guard").jqGrid({
            url: ctxPath + '/pop/F00055/guardList',
            datatype: "json",
            colModel: [
                {
                    label: '選択', width: 80, sortable: false, formatter: function (cell, option, object) {
                        return "<input onclick='radio_select_function()' type='radio' id='" + object.id + "' name='single' value='" + object.usrId +','+ object.reltnspDiv +  "'>";
                    }
                },
                {
                    label: '保護者ID',
                    name: 'guardId',
                    index: 'guard_id',
                    width: 80,
                    key: true,
                    sortable: false,
                    align: "center"
                },
                {label: '保護者姓名', name: 'guardNm', index: 'guard_nm', width: 80, sortable: false, align: "center"},
                {label: 'メールアドレス', name: 'mailad', index: 'mailad', width: 80, sortable: false, align: "center"},
                {label: '続柄', name: 'reltnspDiv', index: 'reltnspDiv', width: 80, sortable: false, align: "center"}
            ],
            viewrecords: true,
            height: 385,
            rowNum: 30,
            //rowList: [10, 30, 50],
            //rownumbers: false,
            rownumWidth: 25,
            autowidth: true,
            // width: 1920,
            multiselect: false,
            singleselect: true,
            pager: "#jqGridPager_guard",
            jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },
            onSelectRow: function () {
                var rowId = $("#jqGrid_guard").jqGrid("getGridParam", "selrow");
                rowClick(rowId);
            },
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                $("#jqGrid_guard").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            },
            loadComplete: function (data) {
                if (data.code != 0) {
                    showMsg(data.msg);
                } else {
                    if (data.mstOrgEntity) {
                        $("#orgId").text(data.mstOrgEntity.orgId);
                        $("#orgNm").text(data.mstOrgEntity.orgNm);
                    }
                }
            },
        });
    }
    if (param.roleDiv == '4') {
        document.getElementById("guard").style.display = "none";
        document.getElementById("mentor").style.display = "none";
        document.getElementById("guard_list").style.display = "none";
        document.getElementById("mentor_list").style.display = "none";
        $("#jqGrid_stu").jqGrid({
            url: ctxPath + '/pop/F00055/stuList',
            datatype: "json",
            colModel: [
                {
                    label: '選択', width: 80, sortable: false, formatter: function (cell, option, object) {
                        return "<input onclick='radio_select_function()' type='radio' id='" + object.usrId + "' json_data='" + JSON.stringify(object) + "' name='single' value='" + object.usrId + "'>";
                    }
                },
                {label: '生徒ID', name: 'stuId', index: 'stu_id', width: 80, key: true, sortable: false, align: "center"},
                {label: '生徒姓名', name: 'studentNm', index: 'student_nm', width: 80, sortable: false, align: "center"},
                {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 80, sortable: false, align: "center"},
            ],
            viewrecords: true,
            height: 385,
            rowNum: 30,
            //rowList: [10, 30, 50],
            //rownumbers: false,
            rownumWidth: 25,
            autowidth: true,
            // width: 1920,
            multiselect: false,
            singleselect: true,
            pager: "#jqGridPager_stu",
            jsonReader: {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount"
            },
            prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },

            onSelectRow: function () {
                var rowId = $("#jqGrid_stu").jqGrid("getGridParam", "selrow");
                rowClick(rowId);
            },

            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
                $("#jqGrid_stu").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
            },
            loadComplete: function (data) {
                if (data.code != 0) {
                    showMsg(data.msg);
                } else {
                    if (data.mstOrgEntity) {
                        $("#orgId").text(data.mstOrgEntity.orgId);
                        $("#orgNm").text(data.mstOrgEntity.orgNm);
                    }
                    if (time == 0) {
                        var str = "<option value=''></option>";
                        for (var i = 0; i < data.mstCodDEntitySchyDivList.length; i++) {
                            var schyDiv = data.mstCodDEntitySchyDivList[i];
                            str += "<option value='" + schyDiv.codCd + "'>" + schyDiv.codValue + "</option>";
                        }
                        $("#schyDiv").html(str);
                        time++;
                    }
                }
            }
        });
    }
    $("#search_stu").click(function () {
        $("#select_btn").attr("disabled",true).css("background",'gray');
        $("#jqGrid_stu").jqGrid('setGridParam', {
            url: ctxPath + '/pop/F00055/stuList',
            datatype: "json",
            postData: {
                stuId: $("#stu_id_input").val(),
                studentNm: $("#stu_nm_input").val().replace(/(^\s*)|(\s*$)/g, ""),
                schyDiv: $("#schyDiv option:selected").val()

            },
            page: 1
        }).trigger("reloadGrid");
    });
    $("#search_guard").click(function () {
        $("#select_btn").attr("disabled",true).css("background",'gray');
        $("#jqGrid_guard").jqGrid('setGridParam', {
            url: ctxPath + '/pop/F00055/guardList',
            datatype: "json",
            postData: {
                guardId: $("#guard_id_input").val(),
                guardNm: $("#guard_nm_input").val().replace(/(^\s*)|(\s*$)/g, ""),
                mailad: $("#mail_addr_input").val().replace(/(^\s*)|(\s*$)/g, "")
            },
            page: 1
        }).trigger("reloadGrid");
    });
    $("#search_mentor").click(function () {
        $("#select_btn").attr("disabled",true).css("background",'gray');
        $("#jqGrid_mentor").jqGrid('setGridParam', {
            url: ctxPath + '/pop/F00055/mentorList',
            datatype: "json",
            postData: {
                mentorId: $("#mentor_id_input").val(),
                mentorNm: $("#mentor_nm_input").val().replace(/(^\s*)|(\s*$)/g, "")
            },
            page: 1
        }).trigger("reloadGrid");
    });

    $("#close_btn").click(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);

    });
    $("#select_btn").click(function () {
        // $('input[name="sex"]:checked').val();
        var str = $("input[name='single']:checked").val();
        if (str==='' || str === null){
            showMsg($.format($.msg.MSGCOMN0137, "ユーザー"))
            return;
        }
        if (param.id && param.id == 'F00054') {
            parent.vm.showF00055Data(str, param.roleDiv);
        } else {
            parent.showF00055Data(str, param.roleDiv);
        }
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

});

function rowClick(rowId) {
    document.getElementById(rowId).children[0].children[0].checked = true;
    document.getElementById("select_btn").disabled = false;
    $("#select_btn").css("color", "white").css("background", "#00a65a").css("border", "0").css("border-bottom","3px grey solid");
}

function radio_select_function() {
    var isChecked = $("input[name='single']:checked").val();
    if (isChecked != null) {
        document.getElementById("select_btn").disabled = false;
        $("#select_btn").css("color", "white").css("background", "#00a65a").css("border", "0").css("border-bottom","3px grey solid");
    }
}
$(function () {
    $(".ui-icon-seek-next,.ui-icon-seek-prev,.ui-icon-seek-first,.ui-icon-seek-end").click(function () {
        $("#select_btn").attr("disabled",true).css("background",'gray');
    });
});
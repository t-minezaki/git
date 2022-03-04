var param = getParam();
var eventId = param.eventId;
orgSelectDiv = param.orgSelectDiv;
var formartFlg = true;
var kuroshiro = null;
$(function () {
    $("#selectAll").click(function () {
        var flag = document.getElementById("selectAll").checked;
        if (flag) {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
            $("#schy").removeAttr("disabled").css("background-color", "white");
            // 2020/11/04 modify LiYuHuan
            $("#stuInput").removeAttr("disabled").css("background-color", "white");
            $("#stuIdtext").removeAttr("disabled").css("background-color", "white");
            $("#showPath").removeAttr("disabled").css("background-color", "white");
            $("#download").css("pointer-events", "unset");
            $("#file_btn").removeAttr("disabled");
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = treeObj.getCheckedNodes(true);
            if (nodes.length != 1) {
                $(".select2-search__field").attr("placeholder", "");
                $("#group").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            }
        } else {
            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
            $("#schy").val("");
            $("#schy").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            $("#group").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            $("#stuInput").val("");
            $("#stuIdtext").val("");
            $("#showPath")[0].value = "";
            $("#stuInput").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            $("#file_btn").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            $("#stuIdtext").attr("disabled", "disabled").css("background-color", "#EEEEEE");
            $("#showPath").css("background-color", "#EEEEEE");
            $("#download").css("pointer-events", "none");
            $("#btn_file").attr("disabled", "disabled");
        }
        onCheck();
    });
    $('#btn_file').change(function () {
        var str = $(this).val().substring($(this).val().lastIndexOf('\\') + 1);
        str = str.slice(str.lastIndexOf("/") + 1);
        $("#showPath").removeAttr("value");
        // $("#btn_file")[0].files[0] = null;
        // document.getElementById('btn_file').value = null;
        $("#showPath")[0].value = "";
        $("#showPath")[0].value = str;
    });
    $('#btn_file').click(function () {
        $("#showPath")[0].value = "";
        document.getElementById('btn_file').value = null;
    });
});

function onCheck() {
    //ztree オブジェクトを取得
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    nodes = treeObj.getCheckedNodes(true);
    //find parents' nodes
    var node = treeObj.getNodes();
    // find all nodes
    unCheckNodes = treeObj.transformToArray(node);
    var flg = document.getElementById("selectAll");
    if (unCheckNodes.length != nodes.length) {
        flg.checked = false;
    } else {
        flg.checked = true;
    }
    //組織のいずれかチェックされた場合のみ 非活性
    if (nodes.length == 0) {
        $("#schy").val("");
        $(".select2-search__field").attr("placeholder", "");
        $(".select2-selection__rendered").remove();
        $("#schy").attr("disabled", "disabled").css("background-color", "#EEEEEE");
        vm.groupName = "";
        // 2020/11/04 modify Lipeipei
        $("#stuInput").attr("disabled", "disabled").css("background-color", "#EEEEEE");
        $("#stuIdtext").attr("disabled", "disabled").css("background-color", "#EEEEEE");
        $("#file_btn").attr("disabled", "disabled").css("background-color", "#EEEEEE");
        $("#showPath").attr("disabled", "disabled").css("background-color", "#EEEEEE");
        $("#download").css("pointer-events", "none");
        $("#btn_file").attr("disabled", "disabled");
        $("#stuInput").val("");
        $("#stuIdtext").val("");
        $("#schy").val("");
        $("#showPath")[0].value = "";
    } else {
        $(".select2-selection__rendered").remove();
        if (nodes.length !== 1 && orgSelectDiv!='0') {
            $(".select2-search__field").attr("placeholder", "");
            $("#group").attr("disabled", true).css("background-color", "#EEEEEE");
        } else {
            $("#group").val("");
            $("#group").removeAttr("disabled").css("background-color", "white");
            var selectedNode = nodes[0].orgId;
            var formData = new FormData();
            formData.append("selectedNode", selectedNode);
            formData.append("orgFlag", vm.orgSelectDiv);
            getOrgList(formData);
        }
        $("#schy").removeAttr("disabled").css("background-color", "white");
        $("#stuInput").removeAttr("disabled").css("background-color", "white");
        $("#stuIdtext").removeAttr("disabled").css("background-color", "white");
        $("#showPath").removeAttr("disabled").css("background-color", "white");
        $("#file_btn").removeAttr("disabled").css("background-color", "white");
        $("#download").css("pointer-events", "unset");
        $("#btn_file").removeAttr("disabled");
    }
};

function getOrgList(formData) {
    $.ajax({
        type: "POST",
        url: ctxPath + '/pop/f04004/getOrgList',
        datatype: "json",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data.code == 0) {
                if (data.mstGrpEntityList) {
                    if (data.mstGrpEntityList.length == 0) {
                        vm.mstGrpEntityList = null;
                        vm.groupName = "";
                        $("#group").attr("disabled",true).css("background-color", "#EEEEEE");
                    } else {
                        $("#group").select2({
                            multiple: true,
                            // placeholder: vm.groupName
                            placeholder: "",
                            allowClear: false
                            // 2020/11/04 modify LiYuHuan end
                        });
                        $('.select2-hidden-accessible').attr('disabled', false);
                        vm.mstGrpEntityList = data.mstGrpEntityList;
                        vm.groupName = "";
                        for (var i = 0; i < vm.mstGrpEntityList.length; i++) {
                            if (i < vm.mstGrpEntityList.length - 1) {
                                vm.groupName += vm.mstGrpEntityList[i].grpNm + ',';
                            } else {
                                vm.groupName += vm.mstGrpEntityList[i].grpNm;
                            }
                        }
                        if (vm.groupName.length > 18) {
                            vm.groupName = vm.groupName.substring(0, 18) + "...";
                        }
                    }
                    // 2020/11/04 modify LiYuHuan
                    // $(".select2-search__field").attr("placeholder",vm.groupName);
                    $(".select2-search__field").attr("placeholder", "");
                }
            } else {
                parent.layer.alert(data.msg);
                vm.mstGrpEntityList = null;
                vm.groupName = "";
                $(".select2-search__field").attr("placeholder", "");
            }
        }
    });
}

var zNodes;
var nodes = [];
var unCheckNodes = [];
var stuList = [];
var returList = [];
var rightList = [];
var leftList = [];
var stuIdList = sessionStorage.getItem("stuIdList");
var allDeliverFlg = '';
// 2020/11/04 modify LiYuHuan start
var selectedNode = '';
var srcHeight = '';
var width = '';
srcHeight = $(window).height() * 0.158;
width = $(window).width() * 0.4;


var vm = new Vue({
    el: '.main',
    data: {
        org: '',
        schyList: [],
        mstGrpEntityList: [],
        groupName: '',
        nodesList: '',
        nodesListArray: [],
        valueList: [],
        selectedNode: '',
        orgSelectDiv: orgSelectDiv
    },
    mounted: function () {
        if (orgSelectDiv != 1) {
            $(".left").css("display", "none")
        }
        this.getInfo();
        $(".select2-selection__rendered").remove();
        $("#toright").click(function () {
            var ids = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
            var ids2 = $("#jqGridRight").getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                var rowData = $("#jqGrid").jqGrid('getRowData', ids[i]);
                $("#jqGridRight").addRowData(ids[i], rowData, "last");
            }
            $("#jqGridRight").jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
            for (var i = 0, l = ids.length; i < l; l--) {
                $("#jqGrid").jqGrid("delRowData", ids[l - 1]);
            }
            allcheck();
        });
        $("#toleft").click(function () {
            var ids = $("#jqGridRight").jqGrid('getGridParam', 'selarrrow');
            var ids2 = $("#jqGrid").getDataIDs();
            for (var i = 0; i < ids.length; i++) {
                var rowData = $("#jqGridRight").jqGrid('getRowData', ids[i]);
                $("#jqGrid").addRowData(ids[i], rowData, "last");
            }
            $("#jqGrid").jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
            for (var i = 0, l = ids.length; i < l; l--) {
                $("#jqGridRight").jqGrid("delRowData", ids[l - 1]);
            }
            allcheck();
        });
        //all add to right
        $("#allToRight").click(function () {
            allright()
        });
        //all add to left
        $("#allToLeft").click(function () {
            allleft()
        });
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/f04004/init',
                type: 'GET',
                data: {
                    selectedNode: selectedNode,
                    eventId: eventId,
                    stuIdList: stuIdList
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        if (data.stuEntity.length != 0) {
                            returList = data.stuEntity;
                            var localData = {page: 1, total: 2, records: "2", rows: returList};
                            localData.rows = returList;
                            localData.records = returList.length;
                            localData.total = returList.length;
                            if(!formartFlg){
                                allright();
                            }else{
                                kuroshiro = new Kuroshiro();
                                kuroshiro.init(new KuromojiAnalyzer({dictPath: ctxPath + "/plugins/kuroshiro-analyzer-kuromoji/node_modules/kuromoji/dict"}))
                                    .then(function () {
                                        formartFlg = false;
                                        for (var i = 0; i < localData.rows.length; i++) {
                                            $("#jqGrid").jqGrid('addRowData', localData.rows[i].stuId, localData.rows[i]);
                                        }
                                        allright();
                                    });
                            }

                        } else {
                            rightList = [];
                        }
                        if (data.org) {
                            vm.org = data.org;
                        }
                        if (data.schyList) {
                            vm.schyList = data.schyList;
                        }

                        if (data.orgIdList) {

                            zNodes = data.orgIdList;
                            if (zNodes != 1) {
                                $("#group").css("background-color", "#EEEEEE");
                            }
                            geneUnitTree(zNodes);
                            $.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
                        }
                        onCheck();
                    }
                    else {
                        rightList = [];
                        $("#stuInput").attr("readonly","readonly");
                        $("#stuIdtext").attr("readonly","readonly");
                        parent.layer.alert(data.msg);
                    }
                }
            });
            leftList = [];
            getGridRight();
            getGridLeft();
        },
        search: function () {
            getGridRight();
            // rightList = [];
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = treeObj.getCheckedNodes(true);
            $("#select1").find("option").remove();
            $("#select2").find("option").remove();
            if (nodes.length != 1) {
                vm.groupName = "";
                $(".select2-search__field").val("");
            }
            var obj = $("#jqGridRight");
            //グリッドテーブル内のすべてのROWID値を取得します
            var rowIds = obj.getDataIDs();
            var formData = new FormData();
            var groupList = [];
            formData.append('schy', $("#schy").val());
            $("#group option:selected").each(function () {
                groupList.push($(this).val());
            });
            formData.append('group', groupList);
            formData.append('rowIds', rowIds);
            for (var i = 0; i < nodes.length; i++) {
                if (i < nodes.length - 1)
                    vm.nodesList += nodes[i].orgId + ',';
                else {
                    vm.nodesList += nodes[i].orgId;
                }
            }
            var orgList = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
            var orgIdList = [];
            for (var i = 0; i < orgList.length; i++) {
                orgIdList.push(orgList[i].orgId);
            }
            // 2020/11/04 modify LiYuHuan start
            var stuId = $("#stuIdtext").val();
            var stuNm = $("#stuInput").val();
            formData.append('orgIdList', orgIdList);
            formData.append('stuId', stuId);
            formData.append('stuNm', stuNm);
            if ($("#showPath")[0].value != "") {
                var file = $("#btn_file")[0].files[0];
                formData.append('file', file);
            }
            formData.append("orgFlag", vm.orgSelectDiv);
            formData.append("eventId", eventId);
            formData.append("stuIdList", stuIdList);
            var error = false;
            $.ajax({
                type: "POST",
                url: ctxPath + '/pop/f04004/search',
                datatype: "json",
                data: formData,
                async: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data.code == 0) {
                        if (data.stuEntity != null) {
                            error = false;
                            returList = data.stuEntity;
                            getGridLeft();
                        }
                    } else {
                        error = true;
                        parent.layer.alert(data.msg);
                    }
                }
            });
            if (!error ) {
                // returList.push(returList[returList.length-1]);
                var localData = {page: 1, total: 2, records: "2", rows: returList};
                localData.rows = returList;
                localData.records = returList.length;
                localData.total = returList.length;
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
                if(!formartFlg){
                    $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
                    returList.pop(returList[returList.length - 1]);
                }else{
                    kuroshiro = new Kuroshiro();
                    kuroshiro.init(new KuromojiAnalyzer({dictPath: ctxPath + "/plugins/kuroshiro-analyzer-kuromoji/node_modules/kuromoji/dict"}))
                        .then(function () {
                            formartFlg = false;
                            $("#jqGrid").setGridParam({data: localData.rows, reader: reader}).trigger('reloadGrid');
                            returList.pop(returList[returList.length - 1]);
                        });
                }

            }
        }
        // 2020/11/04 modify LiYuHuan end
    }
});

// create ztree
function geneUnitTree(unitTrees) {
    $("#treeDemo").html(""); // clear
    var setting = {
        view: {
            selectedMulti: true,
            // expandAll: true,
            showIcon: false,
            expandSpeed: "fast"
            // open: true
        },
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "orgId",
                pIdKey: "upplevOrgId",
                rootPId: null
            },
            key: {
                name: "orgNmDisplay"
            }
        },
        callback: {
            onCheck: onCheck
        }
    };
    var treeNodes = unitTrees;
    $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes = treeObj.transformToArray(treeObj.getNodes());
    treeObj.expandNode(nodes[0],true);
}

$("#closeBtn").click(function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});
$("#chooseBtn").click(function () {
    var studentIdList = [];
    var returnDatas = [];
// 2020/11/04 modify LiYuHuan start
    var stuAndGuardList = []
    // 生徒IDリストを取得
    var arrayData = getJQAllData();
    for (var i = 0; i < arrayData.length; i++) {
        studentIdList.push(arrayData[i]['stuId']);
        returnDatas.push({
            "stuId": arrayData[i]['stuId'],
            "guardId": arrayData[i]['guardId'],
            "stuName": arrayData[i]['stuName'],
            "guardName": arrayData[i]['guardName'],
            "orgId": arrayData[i]['orgId'],
            "guardAfterId": arrayData[i]['guardAfterId'],
            "stuAfterId": arrayData[i]['stuAfterId']
        });
        stuAndGuardList.push({
            "stuId": arrayData[i]['stuId'],
            "guardId": arrayData[i]['guardId'],
            "stuName": arrayData[i]['stuName'],
            // NWT崔 manmiru1-726 2021/07/07 add start
            "orgId": arrayData[i]['orgId'],
            // NWT崔 manmiru1-726 2021/07/07 add end
            "stuNm": arrayData[i]['stuId'] + "," + arrayData[i]['stuName'],
            "guardName": arrayData[i]['guardName'],
            "schyDiv": arrayData[i]['schy'],
            "guardAfterId": arrayData[i]['guardAfterId'],
            "afterUsrId": arrayData[i]['stuAfterId'],
            "readFlg": arrayData[i]['readFlg']
        });
    }
    // 画面．追加対象エリアで一件でもないの場合
    var index = parent.layer.getFrameIndex(window.name);
    if (orgSelectDiv == '0') {
        if (stuAndGuardList.length == 0) {
            parent.layer.alert($.format($.msg.MSGCOMN0096, "配信先対象", "「＞　または　＞＞」"));
            return;
        } else {
            parent.layer.close(index);
            parent.vm.guardAndStudentList = stuAndGuardList;
            parent.closeFlg = true;
        }
    } else {
        if (studentIdList.length == 0) {
            parent.layer.alert($.format($.msg.MSGCOMN0096, "配信先対象", "「＞　または　＞＞」"));
            return;
        } else {

            //全体配信フラグ
            if (document.getElementById("selectAll").checked) {
                allDeliverFlg = '1';
            } else {
                allDeliverFlg = '0';
            }
            parent.vm.allDeliverFlg = allDeliverFlg;
            parent.vm.stuIdList = studentIdList;
            parent.returList = returnDatas;
            parent.closeFlg = true;
        }
    }
    parent.layer.close(index);
});

function getGridLeft() {
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid(
        {
            datatype: "local",
            colModel: [
                {label: '教室名', name: 'orgNm', index: 'orgNm', width: 50, sortable: true, align: "center"},
                {label: '生徒ID', name: 'stuAfterId', index: 'stuAfterId', width: 50, sortable: true, align: "center"},
                {
                    label: '学年',
                    name: 'schy',
                    index: 'schy',
                    width: 25,
                    sortable: true,
                    align: "center",
                    sorttype: function (cell, obj) {
                        for (var i = 0; i < vm.schyList.length; i ++) {
                            if (vm.schyList[i].codValue && vm.schyList[i].codValue === cell) {
                                return vm.schyList[i].sort;
                            }
                        }
                        return -1;
                    }
                },
                {
                    label: '生徒名',
                    name: 'stuName',
                    index: 'stuName',
                    width: 50,
                    sortable: true,
                    align: "center",
                    sorttype: function (cell, obj) {
                        if (obj.kana){
                            return obj.kana
                        } else {
                            return '';
                        }
                    }
                },
                {
                    label: '生徒id',
                    name: 'stuId',
                    index: 'stuId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true,
                    key: true,
                    firstsortorder:'desc'
                },
                {
                    label: 'orgId',
                    name: 'orgId',
                    index: 'orgId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardAfterId',
                    name: 'guardAfterId',
                    index: 'guardAfterId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardId',
                    name: 'guardId',
                    index: 'guardId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardName',
                    name: 'guardName',
                    index: 'guardName',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'readFlg',
                    name: 'readFlg',
                    index: 'readFlg',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'kanaName',
                    name: 'kanaName',
                    index: 'kanaName',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true,
                    formatter: function(cell, opiton, object) {
                        if (object.kana){
                            return object.kana;
                        } else {
                            kuroshiro.convert(object.stuName, {to: "katakana"})
                                .then(function (result) {
                                    object.kana = result;
                                    $('#' + object.stuId).find('td[aria-describedby=jqGrid_kanaName]').html(result);
                                });
                            return '';
                        }
                //
                    }
                }
            ],
            multiselect: true,
            rowNum: returList.length + 1,
            rownumbers: false,
            height: srcHeight,
            autowidth: false,
            width: width,
            sortable: true,
            scroll: false,
            sortorder: 'desc',
            // sortname: 'stuName',
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            },
            loadComplete: function (data) {
                $("div.ui-jqgrid-sortable").each(function (index, element) {
                    if (element.id == "jqgh_jqGrid_operation") {
                        return;
                    }
                    var asc = $(element).children().find("span[sort='desc']").first();
                    var span = $(element).children("span.s-ico").first();
                    var label = $(element).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(element).children("label").html("&#9650");
                    } else {
                        $(element).children("label").html("&#9660");
                    }
                });

                // ソートアイコン変換
                $("div.ui-jqgrid-sortable").bind("click", function () {
                    if (this.id == "jqgh_jqGrid_operation") {
                        return;
                    }
                    var asc = $(this).children().find("span[sort='desc']").first();
                    var span = $(this).children("span.s-ico").first();
                    var label = $(this).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(this).children("label").html("&#9650");
                    } else {
                        $(this).children("label").html("&#9660");
                    }
                });
                $(".ui-jqgrid-hdiv").bind("resize", function () {
                    $(this).parent(".ui-jqgrid").width($(this).width());
                });
                $(".topMessage").css("display", "none");
                // if (orgSelectDiv == '1') {
                //     $(".ui-jqgrid-hdiv.ui-state-default.ui-corner-top").css("height", "4vh");
                // } else {
                //     $(".ui-jqgrid-hdiv.ui-state-default.ui-corner-top").css("height", "6vh");
                // }
            }
        }
    );
}

function getGridRight() {
    // $.jgrid.gridUnload("jqGridRight");
    $("#jqGridRight").jqGrid(
        {
            datatype: "local",
            colModel: [
                {label: '教室名', name: 'orgNm', index: 'orgNm', width: 50, sortable: true, align: "center"},
                {label: '生徒ID', name: 'stuAfterId', index: 'stuAfterId', width: 50, sortable: true, align: "center"},
                {
                    label: '学年',
                    name: 'schy',
                    index: 'schy',
                    width: 25,
                    sortable: true,
                    align: "center",
                    sorttype: function (cell, obj) {
                        for (var i = 0; i < vm.schyList.length; i ++) {
                            if (vm.schyList[i].codValue && vm.schyList[i].codValue === cell) {
                                return vm.schyList[i].sort;
                            }
                        }
                        return -1;
                    }
                },
                {    label: '生徒名',
                     name: 'stuName',
                     index: 'stuName',
                     width: 50,
                     sortable: true,
                     align: "center",
                    sorttype: function (cell, obj) {
                        if (obj.kanaName){
                            return obj.kanaName
                        } else {
                            return '';
                        }
                    }
                },
                {
                    label: '生徒id',
                    name: 'stuId',
                    index: 'stuId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true,
                    key: true
                },
                {
                    label: 'orgId',
                    name: 'orgId',
                    index: 'orgId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardAfterId',
                    name: 'guardAfterId',
                    index: 'guardAfterId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardId',
                    name: 'guardId',
                    index: 'guardId',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'guardName',
                    name: 'guardName',
                    index: 'guardName',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'readFlg',
                    name: 'readFlg',
                    index: 'readFlg',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                },
                {
                    label: 'kanaName',
                    name: 'kanaName',
                    index: 'kanaName',
                    width: 40,
                    sortable: true,
                    align: "center",
                    hidden: true
                }
            ],
            multiselect: true,
            rowNum: returList.length + 1,
            resizable: false,
            rownumbers: false,
            height: srcHeight,
            width: width,
            autowidth: false,
            sortable: true,
            scroll: false,
            sortorder: 'desc',
            // sortname: 'stuName',
            gridComplete: function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            },
            loadComplete: function (data) {
                $("div.ui-jqgrid-sortable").each(function (index, element) {
                    if (element.id == "jqgh_jqGrid_operation") {
                        return;
                    }
                    var asc = $(element).children().find("span[sort='desc']").first();
                    var span = $(element).children("span.s-ico").first();
                    var label = $(element).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(element).children("label").html("&#9650");
                    } else {
                        $(element).children("label").html("&#9660");
                    }
                });

                // ソートアイコン変換
                $("div.ui-jqgrid-sortable").bind("click", function () {
                    if (this.id == "jqgh_jqGrid_operation") {
                        return;
                    }
                    var asc = $(this).children().find("span[sort='desc']").first();
                    var span = $(this).children("span.s-ico").first();
                    var label = $(this).children("label");
                    if (label.length <= 0) {
                        span.before("<label></label>");
                    }
                    if (asc.hasClass("ui-state-disabled")) {
                        $(this).children("label").html("&#9650");
                    } else {
                        $(this).children("label").html("&#9660");
                    }
                });
                // $(".ui-jqgrid-hdiv").bind("resize", function () {
                    // $(this).parent(".ui-jqgrid").width($(this).width());
                // });
                $(".topMessage").css("display", "none");
                // if (orgSelectDiv == '1') {
                //     $(".ui-jqgrid-hdiv.ui-state-default.ui-corner-top").css("height", "4vh");
                // } else {
                //     $(".ui-jqgrid-hdiv.ui-state-default.ui-corner-top").css("height", "6vh");
                // }
            }
        }
    );
}

function allcheck() {
    var dataLeft = $("#jqGrid").jqGrid("getRowData");
    var dataRight = $("#jqGridRight").jqGrid("getRowData");
    if (dataLeft == 0) {
        $("#cb_jqGrid")[0].checked = false;
    }
    if (dataRight == 0) {
        $("#cb_jqGridRight")[0].checked = false;
    }
}

function getTemplate() {
    if ($("#message") != null) {
        $("#message").hide();
    }
    $("#getTemplate input[name='div']").val($("input[name='one']:checked").val());
    $("#getTemplate").submit();
}

function getJQAllData() {
//グリッドオブジェクトを取得します
    var obj = $("#jqGridRight");
//グリッドテーブル内のすべてのROWID値を取得します
    var rowIds = obj.getDataIDs();
//行データを格納するために配列arrayDataコンテナを初期化します
    var arrayData = new Array();
    if (rowIds.length > 0) {
        for (var i = 0; i < rowIds.length; i++) {
            //rowData=obj.getRowData(rowid）;//ここでrowid = rowIds [i];
            arrayData.push(obj.getRowData(rowIds[i]));
        }
    }
    return arrayData;
}

function getJQAllDataLeft() {
//グリッドオブジェクトを取得します
    var obj = $("#jqGrid");
//グリッドテーブル内のすべてのROWID値を取得します
    var rowIds = obj.getDataIDs();
//行データを格納するために配列arrayDataコンテナを初期化します
    var arrayData = new Array();
    if (rowIds.length > 0) {
        for (var i = 0; i < rowIds.length; i++) {
            //rowData=obj.getRowData(rowid）;//ここでrowid = rowIds [i];
            arrayData.push(obj.getRowData(rowIds[i]));
        }
    }
    return arrayData;
}

// 2020/11/11 modify LiYuHuan end
function allleft() {
    var jqGrid = $("#jqGrid");
    var jqGrid2 = $("#jqGridRight");
    var ids2 = jqGrid2.getDataIDs();
    //すべての行のIDを取得する
    var ids = jqGrid.getDataIDs();
    for (var i = 0; i < ids2.length; i++) {
        var rowData = jqGrid2.jqGrid('getRowData', ids2[i]);
        jqGrid.jqGrid('addRowData', ids2[i], rowData, 'last');
    }
    jqGrid.jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
    jqGrid2.jqGrid('clearGridData');
    allcheck();
}

function allright() {
    var jqGrid = $("#jqGrid");
    var jqGrid2 = $("#jqGridRight");
    var ids2 = jqGrid2.getDataIDs();
    //すべての行のIDを取得する
    var ids = jqGrid.getDataIDs();
    for (var i = 0; i < ids.length; i++) {
        var rowData = jqGrid.jqGrid('getRowData', ids[i]);
        jqGrid2.jqGrid('addRowData', ids[i], rowData, 'last');
    }
    jqGrid2.jqGrid('setGridParam', {rowNum: ids.length + ids2.length}).trigger('reloadGrid');
    jqGrid.jqGrid('clearGridData');
    allcheck();
}
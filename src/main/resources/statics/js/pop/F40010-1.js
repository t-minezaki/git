var vm = new Vue({
    el: '#app',
    data: {
        mstOrgEntityList:[]
    },
    computed: {},
    methods: {
    },
    mounted: function () {
    },
    updated: function () {
    }
});
geneUnitTree(vm.orgIdList);

// create ztree
function geneUnitTree(unitTrees) {
    $("#treeDemo").html(""); // clear
    var setting = {
        view: {
            selectedMulti: true,
            expandAll: true,
            showIcon: false,
            expandSpeed: "fast",
            fontCss:{'color':'black','font-size':'1.5vw'}
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
                name: "orgNm"
            }
        },
        callback: {
            onCheck: function (e, treeId, treeNode) {

                setChildrenChecked(treeNode, treeNode.checked);
            }
        }
    };
    var treeNodes = unitTrees;
    $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    treeObj.expandAll(true);

    var nodes = treeObj.transformToArray(treeObj.getNodes());
    for (var i=0, len = nodes.length; i < len; i++) {
        if (parent.vm.CheckedOrgIdList.indexOf(nodes[i].orgId) >= 0) {
            nodes[i].checked = true;
            treeObj.updateNode(nodes[i]);
        }
    }
}

var treeObj = $.fn.zTree.getZTreeObj("treeDemo");

function setChildrenChecked(node, flg) {

    node.checked = flg;
    treeObj.updateNode(node);
    var childNodes = node.children;
    if (childNodes) {
        for (var i = 0; i < childNodes.length; i++) {

            setChildrenChecked(childNodes[i], flg);
        }
    }
}

var index = parent.layer.getFrameIndex(window.name);

$(function () {

    $("#selectAll").bind("click", function () {

        //ztree オブジェクトを取得
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        if ($(this).prop("checked")) {
            treeObj.checkAllNodes(true);
        }else{
            treeObj.checkAllNodes(false);
        }
    });

    $.get(ctxPath + "/manager/F40010/getOrgList", 
        function (data) {
            if (data.code == 0) {

                vm.mstOrgEntityList = data.mstOrgEntityList;
                geneUnitTree(data.mstOrgEntityList);
            }else{

                showMsg(data.msg);
            }
        });

    $("#btn_ok").bind("click", function () {

        var checkedNodes = treeObj.getCheckedNodes(true);
        var orgList = [];
        for (var i = 0; i < checkedNodes.length; i ++) {
            orgList.push(checkedNodes[i].orgId);
        }
        parent.layer.close(index);
        parent.vm.CheckedOrgIdList = orgList;
    });
});


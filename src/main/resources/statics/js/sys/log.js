$(function () {
    $("#jqGrid").jqGrid({
        url: '../../sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 30, key: true },
			{ label: 'ユーザー名', name: 'username', width: 50 },
			{ label: 'ユーザーの操作', name: 'operation', width: 70 },
			{ label: 'リクエストの方法', name: 'method', width: 150 },
			{ label: 'リクエストのパラメータ', name: 'params', width: 80 },
            { label: '実行時間(ミリ秒)', name: 'time', width: 80 },
			{ label: 'IPアドレス', name: 'ip', width: 70 },
			{ label: '作成時間', name: 'createDate', width: 90 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//gridのボトムすクロルバーを隠す
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});
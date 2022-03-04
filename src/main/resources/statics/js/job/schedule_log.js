$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/scheduleLog/list',
        datatype: "json",
        colModel: [			
            { label: 'ログID', name: 'logId', width: 50, key: true },
			{ label: 'バッチID', name: 'jobId', width: 50},
			{ label: 'バッチ', name: 'beanName', width: 60 },
			{ label: 'メソッド', name: 'methodName', width: 60 },
			{ label: 'パラメータ', name: 'params', width: 60 },
			{ label: '状態', name: 'status', width: 50, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-success">成功</span>' :
					'<span class="label label-danger pointer" onclick="vm.showError('+row.logId+')">失敗</span>';
			}},
			{ label: '時間(ミリ秒)', name: 'times', width: 70 },
			{ label: '実行開始時間', name: 'createTime', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50,100,200],
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
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			jobId: null
		}
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'jobId': vm.q.jobId},
                page:1 
            }).trigger("reloadGrid");
		},
		showError: function(logId) {
			$.get(baseURL + "sys/scheduleLog/info/"+logId, function(r){
				parent.layer.open({
				  title:'詳細情報',
				  closeBtn:0,
				  content: r.log.error
				});
			});
		},
		back: function (event) {
			history.go(-1);
		}
	}
});


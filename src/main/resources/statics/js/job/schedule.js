$(function () {
    $("#jqGrid").jqGrid({
        url: '../../sys/schedule/list',
        datatype: "json",
        colModel: [
            { label: 'バッチID', name: 'jobId', width: 60, key: true },
            { label: 'バッチ名', name: 'beanName', width: 100 },
            { label: 'メソッド', name: 'methodName', width: 100 },
            { label: 'パラメータ', name: 'params', width: 100 },
            { label: 'CRON式', name: 'cronExpression', width: 100 },
            { label: '備考', name: 'remark', width: 100 },
            { label: '状態', name: 'status', width: 60, formatter: function(value, options, row){
                return value === 0 ? 
                    '<span class="label label-success">正常</span>' : 
                    '<span class="label label-danger">一時停止</span>';
            }}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
            beanName: null
        },
        showList: true,
        title: null,
        schedule: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新規";
            vm.schedule = {};
        },
        update: function () {
            var jobId = getSelectedRow();
            if(jobId == null){
                return ;
            }
            
            $.get("../../sys/schedule/info/"+jobId, function(r){
                vm.showList = false;
                vm.title = "変更";
                vm.schedule = r.schedule;
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.schedule.jobId == null ? "../../sys/schedule/save" : "../../sys/schedule/update";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.schedule),
                success: function(r){
                    if(r.code === 0){
                        alert('登録処理は完了しました。', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }
            
            confirm('選択したレコードを削除してよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/schedule/delete",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('登録処理は完了しました。', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        pause: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }
            
            confirm('選択したレコードを一時停止してよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/schedule/pause",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('登録処理は完了しました。', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        resume: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }
            
            confirm('選択したレコードをリストアしてよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/schedule/resume",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('登録処理は完了しました。', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        runOnce: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }
            
            confirm('選択したレコードを実行てよろしいですか。', function(){
                $.ajax({
                    type: "POST",
                    url: "../../sys/schedule/run",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('登録処理は完了しました。', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'beanName': vm.q.beanName},
                page:page 
            }).trigger("reloadGrid");
        }
    }
});
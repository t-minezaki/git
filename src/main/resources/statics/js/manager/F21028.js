//前の画面からパラメーターを取得する
var params = getParam();
var sortname = 'afterUsrId';
var status = 'asc';
//テーブルの高さ
var height = 0;
//曜日リスト
var weeks = ['日', '月', '火', '水', '木', '金', '土'];
var closeFlg = false;
//columnパラメーター
var colModel = [
    {
        id: 'stuId',
        label: 'ID',
        name: 'stuId',
        index: 'stuId',
        width: 0,
        resizable: false,
        sortable: false,
        sorttype: 'text',
        align: "center",
        fixed: true,
        hidden: true
    },
    {
        label: 'ID',
        name: 'afterUsrId',
        index: 'afterUsrId',
        width: ($(window).width() * 0.09),
        resizable: false,
        sortable: false,
        sorttype: 'text',
        align: "center",
        fixed: true
    },
    {
        label: '氏名',
        name: 'stuNm',
        index: 'stuNm',
        width: ($(window).width() * 0.09),
        resizable: false,
        sortable: false,
        sorttype: 'text',
        align: "center",
        fixed: true,
        formatter: function (cell, colModel, obj) {
            return '<a href="./F21041.html?stuId=' + obj.stuId + '">' + cell + '</a>';
        }
    },
    {
        label: '学年',
        name: 'schy',
        index: 'schy',
        width: ($(window).width() * 0.09),
        resizable: false,
        sortable: false,
        sorttype: function (cell) {
            for (var schyDivsKey in vm.schyDivs) {
                if (vm.schyDivs[schyDivsKey].codValue === cell) {
                    return vm.schyDivs[schyDivsKey].sort;
                }
            }
        },
        align: "center",
        fixed: true
    }
];
//vue
var vm = new Vue({
    el: '#content',
    data: {
        isFirst: true,
        pageTurning: false,
        schyList: [],
        grpList: [],
        dateRange: '',
        date: '',
        stuIdList: '',
        standEntity: {},
        data: [],
        orgId: '',
        userId: '',
        schyDivs: [],
        flag:true,
        schyDiv:params.schyDiv,
        grpId:params.grpId,
        stuNm: decodeURI(params.stuNm === undefined?'':params.stuNm)
    },
    mounted: function () {
        if (params.inner !== "true") {
            sessionStorage.removeItem("stuIdList");
        }
        if (sessionStorage.getItem("stuIdList") === null || sessionStorage.getItem("firstLoad") === "true"){
            sessionStorage.setItem("firstLoad",true);
            this.pageTurning = false;
            this.flag = false;
            $("#dayView").addClass("this-view");
        }
        //初期化
        this.setUp();
        //データを取得する
        this.getResult();
    },
    methods: {
        setUp: function () {
            //「しきい値設定」ボタンクリックイベント
            $('#standSet').click(function () {
                var srcWidth = "750px";
                var srcHeight = "550px";
                layer.open({
                    id: 'F21031',
                    type: 2,
                    anim: 2,
                    skin: "layui-layer-myskin",
                    title: [" ", 'height:3vh'],
                    shade: 0.2,
                    closeBtn: 1,
                    shadeClose: false,
                    move: '.layui-layer-title',
                    area: [srcWidth, srcHeight],
                    fixed: true,
                    resize: false,
                    background: '#F0F0F0',
                    content: ["../pop/F21031.html?orgId=" + vm.orgId + "&userId=" + vm.userId, 'no'],
                    success: function (layero, index) {
                    }
                    , end: function () {
                        if (closeFlg) {
                            vm.isFirst = true;
                            vm.pageTurning = false;
                            vm.getResult();
                            closeFlg = false;
                        }
                    }
                });
            });
            //「週」ボタンクリックイベント
            $('#weekView').click(function () {
                sessionStorage.setItem("stuIdList",encodeURI(vm.stuIdList));
                window.location.href = "./F21029.html?inner=true" + "&schyDiv=" + $('#schySelect').val() + "&grpId=" + $('#grpSelect').val() + "&stuNm=" + encodeURI($('#stuNmInput').val());
            });
            //「月」ボタンクリックイベント
            $('#monthView').click(function () {
                sessionStorage.setItem("stuIdList",encodeURI(vm.stuIdList));
                window.location.href = "./F21030.html?inner=true" + "&schyDiv=" + $('#schySelect').val() + "&grpId=" + $('#grpSelect').val() + "&stuNm=" + encodeURI($('#stuNmInput').val());
            });
            //「学年」変更イベント
            $('#schySelect').change(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
            //「グループ」変更イベント
            $('#grpSelect').change(function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
            //「学徒名」入力イベント
            $('#stuNmInput').bind("input propertychange", function () {
                if ($("#message") != null) {
                    $("#message").hide();
                }
            });
            this.date = new Date();
            //日曜日の日付
            this.date.setDate(this.date.getDate() - this.date.getDay());
            //「来週」アイコンのイベントをクリック
            $('#nextWeek').click(function () {
                vm.isFirst = true;
                vm.pageTurning = true;
                vm.date.setDate(vm.date.getDate() + 7);
                vm.getResult();
            });
            //「先週」アイコンのイベントをクリック
            $('#preWeek').click(function () {
                vm.isFirst = true;
                vm.pageTurning = true;
                vm.date.setDate(vm.date.getDate() - 7);
                vm.getResult();
            });
            //「検索」ボタンクリックイベント
            $('#search').click(function () {
                sessionStorage.setItem("firstLoad",false);
                vm.stuNm = $('#stuNmInput').val();
                vm.flag = false;
                vm.isFirst = false;
                vm.getResult();
            });
            //前のページの学徒IDリスト
            if (sessionStorage.getItem("stuIdList")) {
                this.stuIdList = decodeURIComponent(sessionStorage.getItem("stuIdList"));
            }
            //テーブルの高さを計算する
            height = getRemainingHeight('body', 'one', 'div-select', 'div-reload') - $(window).height() * 0.18;
        },
        //テーブルをリセットしてデータを取得する
        getResult: function () {
            if ($("#message") != null) {
                $("#message").html('');
            }
            //colModelを生成
            var columns = [];
            columns.push(colModel[0], colModel[1], colModel[2], colModel[3]);
            //7日間
            for (var i = 0; i < 7; i++) {
                var date = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());
                //データのインデックスは、トラバーサルのインデックスの反対です
                var index = 6 - i;
                //日数の計算
                var day = new Date(date.setDate(date.getDate() + index));
                //colModel生成する
                columns.push({
                    label: day.format('m/d') + '(' + weeks[index] + ')',
                    name: 'day' + i,
                    index: 'day' + i,
                    width: ($(window).width() * 0.09),
                    resizable: false,
                    key: false,
                    sortable: false,
                    align: "center",
                    fixed: true,
                    sorttype:function (cell) {
                        if (cell === null){
                            return -999;
                        }
                        return cell;
                    },
                    //データをフォーマットする
                    formatter: function (cell, option, object) {
                        if (cell) {
                            var hour = parseInt(cell / 60);
                            var min = cell % 60;
                            hour = hour > 0 ? hour + '時間' : '';
                            min = min > 0 ? min + '分' : '';
                            return hour + min;
                        } else {
                            return "";
                        }
                    },
                    //スタイル設定
                    cellattr: function (rowId, val, rawObject, title, rdata) {
                        if (!rawObject[title.name] && rawObject[title.name] !== 0) {
                            // return "style='background-color:#fabf8f'";
                            return;
                        }
                        else if (vm.standEntity.perfStandDay3 && vm.standEntity.perfStandDay2 && vm.standEntity.perfStandDay1) {
                            if (rawObject[title.name] === -1) {
                                return "style='background-color:#FF0000'";
                            } else if (rawObject[title.name] > vm.standEntity.perfStandDay3) {
                                return "style='background-color:#9BC2E6'";
                            } else if (rawObject[title.name] > vm.standEntity.perfStandDay2) {
                                return "style='background-color:#DDEBF7'";
                            } else if (rawObject[title.name] > vm.standEntity.perfStandDay1) {
                                return "style='background-color:#FCE4D6'";
                            } else if (0 <= rawObject[title.name] <= vm.standEntity.perfStandDay1) {
                                return "style='background-color:#F4B084'";
                            }
                        } else {
                            if (rawObject[title.name] === -1) {
                                return "style='background-color:#FF0000'";
                            }
                            else if (rawObject[title.name] > 90) {
                                return "style='background-color:#9BC2E6'";
                            } else if (rawObject[title.name] > 60) {
                                return "style='background-color:#DDEBF7'";
                            } else if (rawObject[title.name] > 30) {
                                return "style='background-color:#FCE4D6'";
                            } else if (0 <= rawObject[title.name] <= 30) {
                                return "style='background-color:#F4B084'";
                            }
                        }
                    }
                });
                //日付範囲の設定
                if (i == 0) {
                    this.dateRange = day.format('Y/m/d') + ' ~ ' + this.date.format('Y/m/d');
                }
            }

            //jqGrid re-init
            $.jgrid.gridUnload("jqGrid");
            //jqGrid
            $('#jqGrid').jqGrid({
                datatype: 'local',
                colModel: columns,
                viewrecords: true,
                regional: 'cn',
                width:  ($(window).width() * 0.90 - 6),
                height: height,
                rowNum: 15,
                /* 2021/1/11 UT-048 cuikailin modify start */
                // rowList: 15,
                rowList: [15, 30, 50],
                /* 2021/1/11 UT-048 cuikailin modify end */
                rownumbers: false,
                sortable: false,
                // sortorder: 'desc',
                loadonce: true,
                rownumWidth: 25,
                autowidth: false,
                multiselect: false,
                pager:
                    "#jqGridPager",
                page: 1,
                gridComplete: function () {
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    $("#jqGrid").setGridWidth($(window).width() * 0.90);
                },
            });
            $($("div.ui-jqgrid-sortable")[0]).trigger("click");
            //データを取得する
            //学年区分
            var schyDiv = $('#schySelect').val();
            //グループID
            var grpId = $('#grpSelect').val();
            //学徒名
            var stuNm = $('#stuNmInput').val();
            //学徒IDリストを構成する
            // var stuIds = this.isFirst ? this.stuIdList : '';
            $.ajax({
                url: ctxPath + '/manager/F21028/getData',
                type: 'GET',
                dataType: "json",
                data: {
                    isFirst: this.isFirst,
                    pageTurning: this.pageTurning,
                    date: this.date.format('Y-m-d'),
                    schyDiv: schyDiv,
                    grpId: grpId,
                    stuNm: stuNm,
                    stuIds: this.stuIdList,
                    flag:this.flag
                },
                success: function (data) {
                    //グループデータを設定する
                    if (data.grpList) {
                        vm.grpList = data.grpList;
                        vm.grpList.unshift({grpId: '', grpNm: ''});
                    }
                    //学年データを設定する
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                        vm.schyList.unshift({grpId: '', grpNm: ''});
                    }
                    //フラグはすべてfalseに設定されます
                    vm.isFirst = false;
                    vm.pageTurning = false;
                    //異常な情報がある
                    if (data.code != 0) {
                        vm.data = [];
                        vm.stuIdList = '';
                        showMsg(data.msg);
                        return;
                    }
                    //標準エンティティクラス
                    if (data.standEntity) {
                        vm.standEntity = data.standEntity;
                    }
                    //行データの投入
                    if (data.lineData) {
                        vm.data = data.lineData;
                        vm.reload();
                    } else {
                        vm.data = [];
                    }
                    if (data.orgId) {
                        vm.orgId = data.orgId;
                    }
                    if (data.userId) {
                        vm.userId = data.userId;
                    }
                    if (data.schyDivs) {
                        vm.schyDivs = data.schyDivs;
                    }
                }
            });
        },
        reload: function () {
            var stuIdList = [];
            //配列を反復処理する
            for (var i = 0, len = this.data.length; i < len; i++) {
                //行データの構築
                var rowData = {
                    afterUsrId: this.data[i].afterUsrId,
                    stuNm: this.data[i].stuNm,
                    schy: this.data[i].schy,
                    stuId: this.data[i].stuId
                };
                //7日間
                for (var j = 0; j < 7; j++) {
                    rowData["day" + j] = this.data[i].day[j];
                }
                //生徒IDリスト
                stuIdList.push(this.data[i].stuId);
                //データ入力
                $('#jqGrid').jqGrid('addRowData', i + 1, rowData);
            }
            //オブジェクトから文字列
            this.stuIdList = JSON.stringify(stuIdList);
            //refresh jqGrid
            $('#jqGrid').trigger("reloadGrid");

            sortname = 'afterUsrId';
            status = 'asc';
            $("#jqGrid").setGridParam({sortname:sortname, sortorder: status})
                .trigger('reloadGrid');
            // 並べ替えアイコン
            $("div.ui-jqgrid-sortable").each(function (index, element) {

                // if (index > 1){
                //     return;
                // }

                var asc = $(element).children().find("span[sort='asc']").first();
                var span = $(element).children("span.s-ico").first();
                var label = $(element).children("label");
                if (label.length <= 0) {
                    span.before("<label></label>");
                }
                $($(".ui-jqgrid-sortable")[0]).children("label").html("&#9650").addClass("firstOne");
                if (asc.hasClass("ui-state-disabled")) {
                    $(element).children("label").html("&#9650");
                } else {
                    $(element).children("label").html("&#9660");
                }
            });

            // ソートアイコン変換
            $("div.ui-jqgrid-sortable").bind("click", function () {

                $(".ui-jqgrid-sortable").children("label").html("&#9650");
                var name1 = $(arguments[0].target).attr("id").split("_")[2];
                if (name1 === sortname ) {
                    status = status==='desc'?'asc':'desc';
                }else {
                    status = 'desc';
                    sortname = name1;
                }
                $("#jqGrid").setGridParam({sortname:sortname, sortorder: status})
                    .trigger('reloadGrid');

                var asc = $(this).children().find("span[sort='asc']").first();
                var span = $(this).children("span.s-ico").first();
                var label = $(this).children("label");
                if (label.length <= 0) {
                    span.before("<label></label>");
                }
                if (status === 'desc') {
                    $(this).children("label").html("&#9660");
                } else {
                    $(this).children("label").html("&#9650");
                }
            });
        }
    }
});
$("#dayView").click(function () {
    $(this).addClass("this-view");
    vm.flag = false;
    //初期化
    vm.setUp();
    //データを取得する
    vm.getResult();
});
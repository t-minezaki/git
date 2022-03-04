var isFirst = true;
height = $(window).height() * 0.45;
width = $(window).width() * 0.9;

function reload(name, loginId) {

    // リストをはずす
    $.jgrid.gridUnload("jqGrid");

    $("#jqGrid").jqGrid({
        url: ctxPath + '/manager/F09004/init',
        datatype: "json",
        postData: {
            params: JSON.stringify({
                name: name,
                loginId: loginId,
                isFirst: isFirst
            })
        },
        colModel: [
            {
                label: 'チェック', name: 'usrId', index: 'usrId', width: 120, sortable: false, align: "center",
                formatter(cell, option, object) {

                    return "<input type='checkbox' class='stu_check' value='" + object.stuId + "'/>";
                }
            },
            {label: '組織名', name: 'orgCd', index: 'orgCd', width: 200, sortable: false, align: "center"},
            {label: '生徒ID', name: 'afterUsrId', index: 'afterUsrId', width: 100, sortable: false, align: "center"},
            {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 200, sortable: false, align: "center"},
            {label: '学年', name: 'schyDiv', index: 'schyDiv', width: 150, sortable: false, align: "center"}

        ],
        viewrecords: true,
        rowNum: 999999,
        height:height,
        width:width,
        regional: 'jp',
        rownumbers: false,
        sortable: true,
        sortorder: 'asc',
        loadonce: true,
        multiselect: false,
        jsonReader:
            {
                root: "page.list",
                page: "page.currPage",
                total: "page.totalPage",
                records: "page.totalCount",
                orgId: "orgId",
                orgNm: "orgNm"
            }
        ,
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function (data) {
        }
        ,
        loadComplete: function (data) {

            // $("#jqGrid").setGridHeight($(window).height() * 0.45);
            // $("#jqGrid").setGridWidth($(window).width() * 0.9);

            if (!data.code || data.code == 0) {
            } else {

                // 状態コードが0でない場合は警告情報が表示される
                showMsg(data.msg);
            }
            if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
                if (window.location.href.indexOf("?mobile") < 0) {
                    try {
                        if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                            $('.stu_check').append('<style>.stu_check:checked::after{font-size:14px;position: relative;bottom: 16%;right: 8%;}</style>')
                        } else if (/iPad/i.test(navigator.userAgent)) {
                            $('.stu_check').append('<style>.stu_check:checked::after{font-size:12px;position: relative;bottom: 16%;right: 10%;}</style>')
                        } else {
                            // alert("other")
                        }
                    } catch (e) {
                    }
                } else {
                    // alert("456")
                }
            } else {
                if (/Macintosh/i.test(navigator.userAgent)){
                    $('.stu_check').append('<style>.stu_check:checked::after{font-size:12px;position: relative;bottom: 16%;right: 10%;}</style>')
                }
            }
        }
    });

    if (isFirst) {
        isFirst = false;
    }
    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}


var vm = new Vue({
    el: '#app',
    data: {
        pick: '',
    }
});
reload(null, null);

$(function () {
    var date = new Date();
    date = date.setDate(date.getDate() + 1);
    date = new Date(date);
    date.setHours(0);
    date.setMinutes(0);
    date.setSeconds(0);
    date.setTime(date.getTime() - (1000));
// laydate日時を設ける
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        max: new Date(date).format('Y-m-d H:i:s'),
        format: 'yyyy/MM/dd HH:mm',
        done: function (value) {
            $("#time_select").val(value)
        }, ready: function () {
            $(".layui-laydate .laydate-btns-confirm").html("選択");
        }
    });
    // jqGridはウィンドウサイズに合わせて適応する
    $(window).resize(function () {
        $("#jqGrid").setGridHeight($(window).height() * 0.3);
        $("#jqGrid").setGridWidth($(window).width() * 0.6);
    });

    $("#btn_search").bind("click", function () {

        reload($("#stu_nm").val(), $("#stu_loginId").val());
    });

    $("#btn_ok").bind("click", function () {

        var stuIdList = [];

        $(".stu_check:checked").each(function (index, element) {

            stuIdList.push($(element).val());
        });

        if (stuIdList.length <= 0) {
            // 2020/11/25 huangxinliang modify start
            showMsg($.format($.msg.MSGCOMN0175, "対象", "１人"))
            // 2020/11/25 huangxinliang modify end
        }

        if (!vm.pick || (vm.pick != 0 && vm.pick != 1)) {
            // 2020/11/25 huangxinliang modify start
            showMsg($.format($.msg.MSGCOMN0175, "ステータス", "１つ"))
            // 2020/11/25 huangxinliang modify end
        }
        // if (data.check) {
        //     let time = $("#time_select").val();
        //     if (time == null || time == ''){
        //         time = new Date().format('Y/m/d H:i')
        //     }
        // }
        //     }
        var time = $("#time_select").val();
        if (time == null || time == '') {
            time = new Date().format('Y/m/d H:i')
        }
        $.post(ctxPath + "/manager/F09004/check",
            {
                status: vm.pick,
                stuIdListStr: JSON.stringify(stuIdList),
                time : time
            }, function (data) {
                if (data.code == 0) {
                    if (data.check) {
                        // var time = $("#time_select").val();
                        // if (time == null || time == '') {
                        //     time = new Date().format('Y/m/d H:i')
                        // }
                        // 登校・下校が可能の場合
                        // 時間選択がブランクの場合、システム時間はF09005_代理入退登録確認画面に引き渡し、時間指定がありの場合、設定した時間はそのままF09005_代理入退登録確認画面に引き渡す。
                        location.href = "F09005.html?status=" + vm.pick + "&time=" + encodeURI(time) + "&stuIdList=" + encodeURI(JSON.stringify(stuIdList));

                        // var srcWidth = "600px";
                        // var srcHeight = "500px";
                        // layer.open({
                        //     id: 'F09005',
                        //     type: 2,
                        //     anim: 2,
                        //     skin: "layui-layer-myskin",
                        //     shade: 0.2,
                        //     closeBtn: 1,
                        //     shadeClose: false,
                        //     move: '.layui-layer-title',
                        //     area: [srcWidth, srcHeight],
                        //     resize: false,
                        //     content: [url, 'no'],
                        //     success: function (layero, index) {
                        //
                        //         $(".layui-layer-title").text("");
                        //     }
                        // });
                        // }
                    }
                } else {

                    showMsg(data.msg);
                }
            });
    });

});
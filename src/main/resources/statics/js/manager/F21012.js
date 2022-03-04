var param = JSON.parse(window.sessionStorage.getItem("params"));
/* 2020/11/27 V8.0 cuikailin add start */
if (param !== null) {
    var list = JSON.parse(decodeURIComponent(param.stuList).replace(/\"name\"/g, "\"stuName\""));
    var colModel = [
        {
            label: '生徒ID',
            name: 'afterUsrId',
            index: 'afterUsrId',
            width: 200,
            resizable: false,
            key: true,
            sortable: false,
            align: "center"
        },
        {
            label: '学年',
            name: 'schyDiv',
            index: 'schyDiv',
            width: 200,
            resizable: false,
            sortable: false,
            align: "center"
        },
        {
            label: '生徒名',
            name: 'stuName',
            index: 'stuName',
            width: 200,
            resizable: false,
            sortable: false,
            align: "center"
        },
        {
            label: '保護者名',
            name: 'guardName',
            index: 'guardName',
            width: 200,
            resizable: false,
            sortable: false,
            align: "center"
        }
    ];
    var height = 0;
    var vm = new Vue({
        el: '#content',
        data: {
            statusCD: '',
            statusValue: '',
            list: [],
            attendanceBookDate: "",
            startDate: '',
            endDate: '',
            date: '',
            stuIdList: [],
            hasGuiRepStuIdList: [],
            misGuiRepStuList: [],
            guidReprDeliverCd: '',
            a: '1'
        },
        mounted: function () {
            this.setUp();
        },
        methods: {
            setUp: function () {
                height = getRemainingHeight('body', 'one', 'statusRow', 'attendBookDateRow', 'rangeRow', 'btnRow', 'listTitle') - $(window).height() * 0.15 - 30;
                //param
                this.guidReprDeliverCd = decodeURIComponent(param.guidReprDeliverCd ? param.guidReprDeliverCd : '');
                this.statusCD = decodeURIComponent(param.pick);
                this.statusValue = decodeURIComponent(param.chooseText);
                this.list = list;
                this.a = decodeURIComponent(param.a);
                this.attendanceBookDate = decodeURIComponent(param.attendanceBookDate);
                this.startDate = decodeURIComponent(param.startDt);
                this.endDate = decodeURIComponent(param.endTime);
                this.stuIdList = list.reduce(function (carry, item) {
                    //  check if the item is actually an object and does contain the field
                    if (typeof item === 'object' && 'stuId' in item && (carry.indexOf(item['stuId']) == -1)) {
                        carry.push({stuId: item.stuId, codValue: item.codValue});
                    }
                    //  return the 'carry' (which is the list of matched field values)
                    return carry;
                }, []);
                this.date = this.attendanceBookDate;
                //layDate
                laydate.render({
                    elem: '#attendanceBookDate',
                    format: 'yyyy/MM/dd',
                    eventElem: '#dayPic',
                    trigger: 'click',
                    value: this.attendanceBookDate,
                    done: function (value, date) {
                        vm.date = value;
                    }
                });
                //jqGrid
                var srcHeight = $(window).height() - $(window).width() * 0.18 - 62 - ($(window).width() / 100) * 3.6;
                var srcWidth = $(window).width() * 0.7;
                $('#jqGrid').jqGrid({
                    datatype: 'local',
                    colModel: colModel,
                    viewRecords:
                        true,
                    width:
                    srcWidth,
                    height:
                    height,
                    rowNum:
                        30,
                    rowList:
                        [10, 30, 50],
                    rownumbers:
                        false,
                    rownumWidth:
                        25,
                    autowidth:
                        true,
                    multiselect:
                        false,
                    pager:
                        "#jqGridPager",
                    gridComplete: function () {
                        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                    }
                });
                for (var i = 0; i <= this.list.length; i++) {
                    $('#jqGrid').jqGrid('addRowData', i + 1, this.list[i]);
                }
                //refresh jqGrid
                if (list.length <= 10) {
                    $('#jqGridPager').css({"display": "none"});
                }
                $('#jqGrid').trigger("reloadGrid");
                setTargetControlHeight('.ui-jqgrid-bdiv', 'body', '.ui-jqgrid-hdiv .ui-state-default .ui-corner-top'
                    , '.one', '.statusContent .row', '#jqGridPager', '.AttendanceBookDateContent .row', '.dateRangeContent .row', '.btnContent .row'
                    , '.btnContent .row');
                /* 2020/12/7 V9.0 cuikailin modify start */
                if (this.a == '0' && (!param.againFlag || param.againFlag != 1)) {
                /* 2020/12/7 V9.0 cuikailin modify end */
                    $('#submit').attr('disabled', true).css({
                        "background-color": "#808080",
                        "border": "1px solid #808080"
                    });
                }
            }
        }
    });

    $(function () {
        $(window).resize(function () {
            $("#jqGrid").setGridWidth(460);
        });
        // alert(navigator.userAgent)
        if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
            if (window.location.href.indexOf("?mobile") < 0) {
                try {
                    if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {

                    } else if (/iPad/i.test(navigator.userAgent)) {
                        // alert(navigator.userAgent)
                        $(".input").css("height", "3vh").css("line-height", "0vh")
                    } else {
                        // alert("other")
                    }
                } catch (e) {
                }
            } else {
                // alert("456")
            }
        } else {
            if (/Macintosh/i.test(navigator.userAgent)) {
                $(".input").css("height", "3vh").css("line-height", "0vh")
            }
        }
    });

    function setTargetControlHeight(objId, containerId) {
        var length = arguments.length;
        var height = 0;
        for (var i = 2; i < length; i++) {
            height += $(arguments[i]).outerHeight(true);
        }
        var parentHeight = $(containerId).height();
        $(objId).height(parentHeight - height);
    }

//「戻す」ボタンを押す
    function toF21011() {
        param.flag = true;
        window.sessionStorage.setItem("params", JSON.stringify(param));
        location.href = 'F21011.html';
    }

    function toF21007() {
        // var params = {};
        // params.pageDiv = '1';
        // params.tgtYmd = vm.date;
        // var stuList = vm.misGuiRepStuList.reduce(function(carry, item) {
        //     //  check if the item is actually an object and does contain the field
        //     if (typeof item === 'object') {
        //         carry.push(item['stuId']);
        //     }
        //     //  return the 'carry' (which is the list of matched field values)
        //     return carry;
        // }, []);
        // params.stuList = JSON.stringify(stuList);
        // params.timesNum = '';
        // window.location.href = 'F21008.html?' + $.param(params);
        location.href = 'F21007.html';
        window.sessionStorage.removeItem("params");
    }

    function toF21010() {
        location.href = 'F21010.html';
        window.sessionStorage.removeItem("params");
    }

//「登録」を押す
    function submit() {
        $.ajax({
            url: ctxPath + '/manager/F21012/saveOrUpdate',
            type: 'GET',
            data: {
                guidReprDeliverCd: vm.guidReprDeliverCd,
                list: JSON.stringify(vm.stuIdList),
                date: vm.date,
                startDate: vm.startDate,
                endDate: vm.endDate,
                statusCD: vm.statusCD,
                noticeLevelDiv: param.noticeLevelDiv
            },
            success: function (data) {
                if (data.code != 0) {
                    var msg = parent.layer.confirm(data.msg, {
                        skin: 'layui-layer-molv',
                        title: '確認',
                        closeBtn: 0,
                        anim: -1,
                        btn: ['確認'],
                        btn1: function () {
                            parent.layer.close(msg);
                        }
                    });
                    return;
                }
                window.sessionStorage.removeItem("params");
                if (data.stuIdList) {
                    vm.hasGuiRepStuIdList = data.stuIdList;
                    //対象者のIDあるだが対応する指導報告書コードがない場合
                    vm.misGuiRepStuList = vm.list.reduce(function (carry, item) {
                        //  check if the item is actually an object and does contain the field
                        if (typeof item === 'object' && vm.hasGuiRepStuIdList.indexOf(item['stuId']) == -1) {
                            carry.push(item);
                        }
                        //  return the 'carry' (which is the list of matched field values)
                        return carry;
                    }, []);
                    var scrWidth = $(window).width();
                    var scrHeight = $(window).height();
                    if (scrWidth > 600) {
                        scrWidth = '600px'
                    } else {
                        scrWidth = scrWidth + 'px'
                    }
                    if (scrHeight > 500) {
                        scrHeight = '500px'
                    } else {
                        scrHeight = scrHeight + 'px'
                    }
                    var index = layer.open({
                        id: 'f21012pop',
                        type: 2,
                        title: '指導報告書データはありません',
                        shade: 0.1,
                        closeBtn: 0,
                        shadeClose: false,
                        area: [scrWidth, scrHeight],
                        fixed: true,
                        resize: false,
                        content: ["../pop/F21012pop.html", 'no'],
                        btn: ['対策1', '対策2'],
                        btn1: function () {
                            toF21011();
                        },
                        btn2: function () {
                            toF21007();
                        }
                    });
                } else {
                    // 2021/1/20 huangxinliang modify start
                    // if (vm.guidReprDeliverCd === '') {
                    toF21010();
                    // } else {
                    //     toF21011();
                    // }
                    // 2021/1/20 huangxinliang modify end
                }
            }
        })
    }
}else {
    location.href = 'F21011.html';
}
/* 2020/11/27 V8.0 cuikailin add end */
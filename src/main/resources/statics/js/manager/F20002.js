var weekArray = ['日', '月', '火', '水', '木', '金', '土'];
var old;
var flg=1;
var first=false;
Vue.directive('select2', {
    inserted(el) {
        $(el).on('select2:select', () => {
            const event = new Event('change', { bubbles: true, cancelable: true });
            el.dispatchEvent(event);
        });

        $(el).on('select2:unselect', () => {
            const event = new Event('change', {bubbles: true, cancelable: true})
            el.dispatchEvent(event)
        })
    },
});
var vm = new Vue({
    el: '#info',
    data: {
        stu: {},
        stuId: '',
        stuNm: '',
        schy: '',
        schNm: '',
        orgNm: '',
        subjtlist: [],
        arr: [],
        crmschId: '',
        crmschLearnPrdId: '',
        schyDiv: '',
        mentorNm:'',
        subjt :[]
    },
    mounted: function () {
        this.getInfo();
        this.getInit();
    },
    updated: function () {
        old = $(".f_books").serialize();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F20002/getStuInfo',
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    if (data.msg != "success") {
                        showMsg(data.msg);
                        $(".login_btn_F20002").attr("disabled", "disabled");
                    }
                    //stu
                    if (data.stu == null) {
                        return;
                    }
                    if (data.stu) {
                        vm.stu = data.stu;
                        vm.stu.resptyNm = decodeURIComponent(vm.stu.resptyNm === null ? '' : vm.stu.resptyNm);
                        vm.stu.studyCont = decodeURIComponent(vm.stu.studyCont === null ? '' : vm.stu.studyCont);
                        vm.stu.hopeJobNm = decodeURIComponent(vm.stu.hopeJobNm === null ? '' : vm.stu.hopeJobNm);
                        vm.stu.hopeUnityNm = decodeURIComponent(vm.stu.hopeUnityNm === null ? '' : vm.stu.hopeUnityNm);
                        vm.stu.hopeLearnSub = decodeURIComponent(vm.stu.hopeLearnSub === null ? '' : vm.stu.hopeLearnSub);
                        vm.stu.specCont = decodeURIComponent(vm.stu.specCont === null ? '' : vm.stu.specCont);
                    }
                    if (data.stu.stuId) {
                        first = true;
                        vm.stuId = data.stu.stuId;
                    }
                    if (data.stu.stuNm) {
                        vm.stuNm = data.stu.stuNm;
                    }
                    if (data.stu.schyDiv) {
                        vm.schy = data.stu.schy;
                    }
                    if (data.stu.schNm) {
                        vm.schNm = data.stu.schNm;
                    }
                    if (data.stu.orgNm) {
                        vm.orgNm = getOrgName(data.stu.orgNm);
                    }
                    if (data.stu.orgId) {
                        vm.crmschId = data.stu.orgId;
                    }
                    //textbList
                    if (data.textbList) {
                        vm.subjtlist = data.textbList;
                        vm.subjtlist.unshift({
                            subjtDiv: '',
                            codValue: '',
                            codValue2: '',
                            imgPath: '',
                            f20002TextListDtos: []
                        });
                    }
                    if (data.subjtList){
                        vm.subjt = data.subjtList;
                    }
                    if(data.crmschLearnPrdId){
                        vm.crmschLearnPrdId=data.crmschLearnPrdId;
                    }
                    if(data.schyDiv){
                        vm.subjtDiv=data.schyDiv;
                    }
                    if (data.mentorNm){
                        vm.mentorNm = data.mentorNm;
                    }
                    $(".selectSa").select2({
                        multiple: true,
                        placeholder: vm.groupName
                    });
                    $(".selectUnsa").select2({
                        multiple: true,
                        placeholder: vm.groupName
                    });
                },
                error: function () {
                    /*alert("error");*/
                },
                complete:function () {
                    jqGrid();
                }
            })
        },
        getInit: function () {
            jqGrid();
        }
    }
});

function updateChoc() {
    var chocData;
    var chocLict = [];
    vm.stu.goodSubjtDiv = $(".selectSa").val().join(',');
    vm.stu.nogoodSubjtDiv = $(".selectUnsa").val().join(',');
    $(".f_books").each(function () {
        chocData = {};
        chocData.updateTimeStr = $(this).attr("updateStr");
        chocData.value = $(this).val();
        chocData.name = $(this).attr("name");
        chocLict.push(chocData);
    });
    vm.stu.chocList = chocLict;
    vm.stu.crmschId = vm.crmschId;
    vm.stu.crmschLearnPrdId = vm.crmschLearnPrdId;
    vm.stu.resptyNm = vm.stu.resptyNm.trim();
    vm.stu.studyCont = vm.stu.studyCont.trim();
    vm.stu.hopeJobNm = vm.stu.hopeJobNm.trim();
    vm.stu.hopeUnityNm = vm.stu.hopeUnityNm.trim();
    vm.stu.hopeLearnSub = vm.stu.hopeLearnSub.trim();
    vm.stu.specCont = vm.stu.specCont.trim();
    $.post(ctxPath + '/manager/F20002/updateChocTextb',
        {
            bookDto: JSON.stringify(vm.stu)
        }, function (data) {
            if (data.code == 0) {
                // var index = layer.alert(data.msg, {
                //     skin: 'layui-layer-molv',
                //     title: '確認',
                //     closeBtn: 0,
                //     anim: -1,
                //     btn: ['確認'],
                //     btn1: function () {
                        window.location.href = "../manager/F20001.html";
                //         layer.close(index);
                //     }
                // });
            }else {
                showMsg(data.msg);
            }
        })
}

$("#backBtn").click(function () {
    window.location.href = 'F20001.html?stuId=' + vm.stuId;
});
/*2020/12/29 liyuhuan add start*/
function jqGrid() {
    var srcHeight = $(window).height() * 0.62;
    var srcWidth = $(window).width() * 0.39;
    if (first) {
        $.jgrid.gridUnload("jqGrid");
        $("#jqGrid").jqGrid({
                url: ctxPath + '/manager/F21041/talk',
                datatype: "json",
                postData: {
                    nowYear: new Date().getFullYear(),
                    flg: flg,
                    stuId: vm.stuId
                },
                colModel: [
                    {
                        label: ' ', name: 'talkDatime', index: 'talkDatime', width: 20, sortable: false, align: "left"
                        , formatter(cell, option, object) {
                            var getTime = new Date(object.talkDatime == null ? object.askDatime : object.talkDatime);
                            var a = weekArray[getTime.getDay()];
                            var time = getTime.getMonth() + 1 + "/" + getTime.getDate() + " " + "(" + a + ")";
                            return '<div style="border-bottom:1px #C6C6C6 !important;margin-left: 2vw;font-weight: bolder;">'+time+'</div>'
                        }
                    },
                    {
                        label: '面談タイトル', name: 'eventTitle', index: 'eventTitle', width: 150, sortable: false, align: "left"
                        ,
                        formatter(cell, option, object) {
                            if (object.talkDatime==null) {
                                return '<div onclick="infoPop('+object.id+')"  style="border-bottom:1px #C6C6C6  !important;font-weight: bolder;cursor: pointer;">'+object.askTitle+'</div>'
                            }else {
                                return '<div onclick="infoPop('+object.id+')" style="border-bottom:1px #C6C6C6  !important;font-weight: bolder;cursor: pointer;margin-left: 2vw !important;">'+object.eventTitle+'</div>'
                            }
                        }
                    }
                ],
                viewrecords: true,
                height: srcHeight,
                width: srcWidth,
                rowNum: flg == 1 ? 10 : 30,
                rowList: [10, 30, 50],
                rownumbers: false,
                rownumWidth: 25,
                multiselect: false,
                pager: "#jqGridPager",
                jsonReader:
                    {
                        root: "page.list",
                        page: "page.currPage",
                        total: "page.totalPage",
                        records: "page.totalCount"
                    }
                ,
                prmNames: {
                    page: "page",
                    rows: "limit"
                }
                ,
                gridComplete: function () {
                    if ($("#message") != null) {
                        $("#message").hide();
                    }
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
                ,
                loadComplete: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    }
                    if (flg==2){
                        $("#jqGrid tbody tr").find('td[aria-describedby="jqGrid_talkDatime"]').css("width", "21.8vw");
                    }
                },

            }
        );
    }

}
function F21060() {
    flg = 1;
    $(".ui-jqgrid .ui-jqgrid-btable").css("table-layout", "auto !important");
    $("#add").css("display", "none");
    $("#score").css("display", "contents");
    $("#askTalk").css("color", "#1B9848");
    $("#askAbout").css("color", "#C6C6C6");
    jqGrid();
}
function F21049() {
    flg = 2;
    $("#askAbout").css("color", "#1B9848");
    $("#askTalk").css("color", "#C6C6C6");
    $(".ui-jqgrid .ui-jqgrid-btable").css("table-layout", "fixed !important");
    $("#score").css("display", "none");
    $("#add").css("display", "inline");
    jqGrid();
}
function add() {
    var srcWidth = $(window).width() * 0.35 + "px";
    var srcHeight = $(window).height() * 0.75 + "px";
    layer.open({
        id: 'F21051',
        title:"問合わせ記録",
        type: 2,
        anim: 2,
        skin: "layui-layer-myskin",
        shade: 0.2,
        closeBtn: 0,
        shadeClose: false,
        move: '.layui-layer-title',
        area: [srcWidth, srcHeight],
        resize: false,
        content: ["../pop/F21050.html", 'no'],
        success: function (layero, index) {
        },
        end:function () {
        }
    })
}
function infoPop(id) {
    if(flg==1){
        layer.open({
            id: 'F21045-1',
            title:"面談記録",
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: ['60%', '75%'],
            resize: false,
            content: ["../pop/F21045.html?id=" + id, 'no'],
            success: function (layero, index) {
            },
            end:function () {
                jqGrid()
            }
        })
    }else {
        var srcWidth = $(window).width() * 0.5 + "px";
        var srcHeight = $(window).height() * 0.5 + "px";
        layer.open({
            id: 'F21051',
            title:"問い合わせ記録",
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            shade: 0.2,
            closeBtn: 1,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F21050.html?id=" + id  + "&stuId=" +vm.stuId, 'no'],
            success: function (layero, index) {
            },
            end:function () {
            }
        })
    }
}
/*2020/12/29 liyuhuan add end*/

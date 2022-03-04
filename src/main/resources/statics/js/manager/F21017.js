
var flg = false;
var page = 1;
var pageSize = 15;
/*add at 2021/08/10 for V9.02 by NWT wen START*/
var schoolYear = "";
var week = "";
var status = "";
/*add at 2021/08/10 for V9.02 by NWT wen END*/
$(function () {
    var isPageHide = false;
    window.addEventListener('pageshow', function () {
        if (isPageHide) {
            window.location.reload();
        }
    });
    window.addEventListener('pagehide', function () {
        isPageHide = true;
    });
    $("#btn1").click(function () {
        flg = false;
        $(this).addClass("active");
        $("#btn2").removeClass("active").css("border-left", "0");
        pageSize = 15;
        $(".insert").css("display", "block");
        vm.init(true);
    });
    $("#btn2").click(function () {
        flg = true;
        $(this).addClass("active");
        $("#btn1").removeClass("active");
        pageSize = 15;
        $(".insert").css("display", "block");
        vm.init(true);
    });
});
var vm = new Vue({
    el: '.content',
    data: {
        stuList: [],
        birthDayTxt: '',
        birthFlg: false,
        count: ''
        /*add at 2021/08/10 for V9.02 by NWT wen START*/
        , schoolYear: []
        , weekDay: []
        , status: []
        /*add at 2021/08/10 for V9.02 by NWT wen END*/
    },
    mounted: function () {
        if(getCookie("equipment") === "phone"){
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21017.css"}).appendTo("head");
        }else {
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21017-1.css"}).appendTo("head");
        }
        this.init(true);
    },
    methods: {
        init: function (initFlag) {
            /*add at 2021/08/10 for V9.02 by NWT wen START*/
            var params = {
                schoolYear: schoolYear,
                week: week,
                status: status,
                limit: pageSize
            }
            /*add at 2021/08/10 for V9.02 by NWT wen END*/
            $.ajax({
                url: ctxPath + '/manager/F21017/init',
                /*update at 2021/08/10 for V9.02 by NWT wen START*/
                type: 'POST',
                data: JSON.stringify(params),
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
                /*update at 2021/08/10 for V9.02 by NWT wen END*/
                success: function (data) {

                    if (data.code == 0) {
                        /*add at 2021/08/10 for V9.02 by NWT wen START*/
                        if (data.schoolYear) {
                            vm.schoolYear = data.schoolYear;
                        }
                        if (data.weekDay) {
                            vm.weekDay = data.weekDay;
                        }
                        if (data.status) {
                            vm.status = data.status;
                        }
                        /*add at 2021/08/10 for V9.02 by NWT wen END*/
                        var width = $(window).width();
                        var height = $(window).height();
                        var length = width > height?5:10;
                        for (var i = 0; i < data.stuList.length; i++) {
                            if (data.stuList[i].entryFlg == "0") {
                                data.stuList[i].absSts = '入室中';
                                data.stuList[i].backGround = '#FFE2EA';
                                data.stuList[i].border = getCookie("equipment") === "phone"?'#FFE2EA':'#FFE2EA';
                            } else if (data.stuList[i].entryFlg == "1") {
                                data.stuList[i].absSts = '退室済';
                                data.stuList[i].backGround = '#E5E5E5';
                                data.stuList[i].border = getCookie("equipment") === "phone"?'#E5E5E5':'#E5E5E5';
                            } else if (data.stuList[i].absSts && data.stuList[i].tgtYmd) {
                                var absDate = data.stuList[i].tgtYmd.replace(/(\d{4})-(\d{2})-(\d{2}) \S*/, '$1/$2/$3');
                                var nowDate = new Date();
                                nowDate = nowDate.Format('yyyy/MM/dd');
                                if (nowDate === absDate) {
                                    data.stuList[i].absSts = '本日遅刻・欠席連絡あり';
                                } else {
                                    data.stuList[i].absSts = '';
                                    data.stuList[i].tgtYmd = null;
                                }
                            } else {
                                data.stuList[i].absSts = '';
                                data.stuList[i].backGround = 'white';
                                data.stuList[i].border = getCookie("equipment") === "phone"?'white':'#CCCCCC';
                            }
                            data.stuList[i].style = 'height:12vw;transform:translate(-50%);left:50%;';
                            (function (arg) {
                                var img = new Image();
                                img.onload = function () {
                                    if (img.width >= img.height) {
                                        data.stuList[arg].style = "height:" + length + "vw;transform:translate(-50%);left:50%;"
                                    } else {
                                        data.stuList[arg].style = "width:" + length + "vw;transform:translate(0, -50%);left:0;top: 50%;"
                                    }
                                };
                                img.src = data.stuList[i].photPath;
                            })(i);
                        }
                        vm.count = data.count;
                    } else {
                        $(".insert").css("display", "none");
                        var index = layer.confirm(data.msg, {
                            skin: 'layui-layer-molv',
                            title: '確認',
                            closeBtn: 0,
                            anim: -1,
                            btn: ['確認'],
                            btn1: function () {
                                layer.close(index);
                            }
                        });
                    }
                    if (initFlag){
                        vm.stuList = data.stuList;
                    }else {
                        vm.stuList = vm.stuList.concat(data.stuList);
                    }
                    /*add at 2021/08/10 for V9.02 by NWT wen START*/
                    if (!vm.stuList || vm.stuList.length == 0) {
                        $(".insert").css("display", "none");
                    } else {
                        if (pageSize >= vm.count) {
                            $(".insert").css("display", "none");
                        } else {
                            $(".insert").css("display", "block");
                        }
                    }
                    /*add at 2021/08/10 for V9.02 by NWT wen END*/
                }
            });
        },
        toF21018: function (stuId, absSts) {
            window.location.href = '../manager/F21018.html?stuId=' + stuId + '&absSts=' + encodeURIComponent(absSts);
        },
        toF21020: function (stuId) {
            window.location.href = '../manager/F21020.html?stuId=' + stuId;
        },
        insert: function () {
            pageSize = pageSize + 15;
            vm.init(false);
        }
    }
});
/*add at 2021/08/10 for V9.02 by NWT wen START*/
function _filter() {
    schoolYear = $("#schoolYear").val() ? $("#schoolYear").val() : '';
    week = $("#week").val() ? $("#week").val() : '';
    status = $("#status").val() ? $("#status").val() : '';
    pageSize = 15;
    vm.init(true);
}
/*add at 2021/08/10 for V9.02 by NWT wen END*/
window.addEventListener("orientationchange", function() {
    var width = $(window).width();
    var height = $(window).height();
    if (width > height){
        for (var i= 0;i < vm.stuList.length;i++){
            vm.stuList[i].style = vm.stuList[i].style.replace("6","10");
        }
    } else {
        for (var i= 0;i < vm.stuList.length;i++){
            vm.stuList[i].style = vm.stuList[i].style.replace("10","6");
        }
    }

}, false);
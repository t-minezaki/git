var param = getParam();
$(function () {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {

                } else if (/iPad/i.test(navigator.userAgent)) {
                    // alert(123)
                    $(".number").css("width", "25%");
                    $(".btm").css("margin-right", "2vw");
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
            $(".number").css("width", "25%");
            $(".btm").css("margin-right", "2vw");
        }
    }
});
var value = '';
var vue = new Vue({
    el: ".content",
    data: {
        subjtList: [],
        absStsDivList: [],
        homeWkDivList: [],
        careDivList: [],
        lastTimeHwtDivList: [],
        schStsDivList: [],
        lectShapeDivList: [],
        absStsList: [],
        // add at 2021/08/12 for V9.02 by NWT wen START
        dataList: [],
        // add at 2021/08/12 for V9.02 by NWT wen END
        pageDiv: '',
        codCd: [],
        value:''

    },
    mounted: function () {
        this.pageDiv = param.pageDiv;
        if (param.pageDiv != 4) {
            this.goInfo();
        }
    },
    updated: function(){
        if (param.pageDiv == 1 && this.dataList.length > 0){
            this.resetLayout(this.dataList.length)
        }
        value = this.value;
    },
    methods: {
        resetLayout: function(sizeNumber){
            if (sizeNumber <= 7){
                $('.subjt').css("width", "35%").css("height","14vh").css("margin","2% calc(30% / 4)");
            }
        },
        goInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/f21006/init',
                type: 'GET',
                data: {
                    grpId: param.grpId,
                    pageDiv: param.pageDiv
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    // modify at 2021/08/12 for V9.02 by NWT wen START
                    if (data.code === 0) {
                        if (data.list) {
                            if (param.pageDiv === 8 && param.codCd) {
                                vue.codCd = param.codCd.split(",");
                                for (var i = 0; i < vue.codCd.length; i++) {
                                    for (var j = 0; j < data.list.length; j++) {
                                        if (vue.codCd[i] == data.list[j].codCd) {
                                            statusList.push(data.list[j].codValue2);
                                            codCds.push(vue.codCd[i]);
                                        }
                                    }
                                }
                                $("#lectShape").val(getArrStr(statusList))
                            }
                            vue.dataList = data.list;
                        }
                    }
                    // modify at 2021/08/12 for V9.02 by NWT wen END
                }
            })
        },

        backTo21008: function (codCd) {
            parent.vm.codCd = codCd;
            parent.vm.endFlg = true;
            if (codCd === '99'){
                parent.submitFlg = true;
                parent.vm.subjectName = $(".otherSubjt").val();
                parent.vm.codCd = codCd;
            }
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        backTo21009: function (codCd) {
            parent.vm.codCd = codCd;
            parent.vm.endFlg = true;
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

    }
});
var grade = '';

/* 2020/12/15 V9.0 cuikailin modify start */
function number(value) {
    if (value == undefined) {
        // var val = $("#number").val();
        grade = grade.substring(0, grade.length - 1);
        $("#numInput").val(grade);
    }
    else {
        grade = $("#numInput").val();
        grade = grade + value;
        if (grade > 100) {
            grade = '100';
        }
        $("#numInput").val(grade);
    }
}
function inputNumber() {
    grade = $("#numInput").val();
    if (grade > 100) {
        grade = '100';
    }
    $("#numInput").val(grade);
}
/* 2020/12/15 V9.0 cuikailin modify end */

function getArrStr(arr) {

    var retStr = "";
    if (arr.length > 0) {
        retStr = retStr + arr[0];
        for (var i = 1; i < arr.length; i++) {

            retStr = retStr + "," + arr[i];
        }
    }
    return retStr;
}

function submit() {
    if (grade !== '') {
        parent.vm.score = grade;
    }
    else {
        parent.vm.score = 0;
        parent.vm.codCd = codCds;
    }
    parent.vm.endFlg = true;
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

var statusList = [];
var i = '';
var status = '';
var codCds = [];
var arr = []

function add(value, codCd) {
    if (codCds.indexOf(codCd) == -1) {
        codCds.push(codCd);
        if (value == undefined) {

            $("#lectShape").val(getArrStr(statusList))
        } else {
            statusList.push(value);
        }
        $("#lectShape").val(getArrStr(statusList))
    } else {
        $("#lectShape").val(getArrStr(statusList));
    }
}

function remove() {
    var text = $("#lectShape").val();
    statusList.pop();
    codCds.pop();
    $("#lectShape").val(getArrStr(statusList));
}
function clear() {
    statusList = [];
    codCds = [];
    $("#lectShape").val(getArrStr(statusList));
}
function setEmpty() {
    parent.vm.codCd = '';
    parent.vm.endFlg = true;
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

var v = getParam();
var vm = new Vue({
    el: "#rrapp",
    data: {
        stu: '',
        gendrList: '',
        schyList: '',
        birthd: '',
        updateStr:'',
        schList:'',
        orgId:''
    },
    mounted: function () {
        this.showData();
    },
    methods: {
        isAngle:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(!(12543 >str.charCodeAt(i)&&str.charCodeAt(i)> 12352)){
                    flg=false;
                    break;
                }
            }
            return flg
        },
        showData: function () {
            $.ajax({
                url: ctxPath + "/student/F10001/getStuDataInfo",
                type: "get",
                datatype: "json",
                success: function (data) {
                    if(data.code!=0){
                        showMsg(data.msg);
                        return;
                    }
                    if (data.birthd) {
                        vm.birthd = data.birthd;
                    }
                    if (data.stuMst) {
                        vm.stu = data.stuMst;
                        if(data.stuMst.birthdayString!=undefined){
                            var y=parseInt(data.stuMst.birthdayString.split("-")[0]);
                            var m=parseInt(data.stuMst.birthdayString.split("-")[1]);
                            var d=parseInt(data.stuMst.birthdayString.split("-")[2]);
                            if (document.attachEvent){
                                window.attachEvent("onload", dateStart(y,m,d));}
                            else
                                window.addEventListener('load', dateStart(y,m,d), false);
                        }else{
                            if (document.attachEvent){
                                window.attachEvent("onload", dateStart(undefined,undefined,undefined));}
                            else
                                window.addEventListener('load', dateStart(undefined,undefined,undefined), false);
                        }

                    }
                    if (data.gendrList) {
                        vm.gendrList = data.gendrList;
                    }
                    if (data.schyList) {
                        vm.schyList = data.schyList;
                    }
                    if(data.updateStr){
                        vm.updateStr=data.updateStr;
                    }
                    if(data.schList){
                        vm.schList=data.schList;
                    }
                    if(data.orgId){
                        vm.orgId=data.orgId;
                    }
                    //????????????????????????
                    if (data.saf_modify_flg =="0"){
                        $("#update").css("visibility","hidden")
                    }
                }
            })
        },
        saveOrUpdate: function () {
            // //ID???????????????
            // if ($("input[name='stuId']").val() == "") {
            //     showMsg($.format($.msg.MSGCOMD0001, "ID"));
            //     return;
            // }
            // if ($("input[name='stuId']").val().length < 6) {
            //     showMsg($.format($.msg.MSGCOMD0016, "ID", "6"));
            //     return;
            // }

            //??????_??????????????????
            if ($("input[name='stuFnm']").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "??????_???"));
                return;
            }

            //??????????????????_??????????????????????????????????????????????????????
            if(!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='stuFnm']").val())){
                showMsg($.format($.msg.MSGCOMD0007,"??????_???"));
                $("#stuFnm").focus();
                return;
            }

            //??????????????????_??????????????????????????????????????????????????????
            if ($("input[name='stuLnm']").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "??????_???"));
                return;
            }

            //??????_????????????????????????
            if(!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='stuLnm']").val())){
                showMsg($.format($.msg.MSGCOMD0007,"??????_???"))
                $("#stuLnm").focus();
                return;
            }

            //??????_??????????????????
            if ($("input[name='flnmKnNm']").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "??????_?????????"));
                return;
            }

            //??????????????????_??????????????????????????????????????????????????????
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='flnmKnNm']").val())){
                showMsg($.format($.msg.MSGCOMD0007,"??????_?????????"));
                $("#flnmKnNm").focus();
                return;
            }

            //??????_??????????????????
            if ($("input[name='flnmKnLnm']").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "??????_?????????"));
                return;
            }

            //??????????????????_??????????????????????????????????????????????????????
            if (!/^[^\x01-\x7E\uFF61-\uFF9F]+$/.test($("input[name='flnmKnLnm']").val())){
                showMsg($.format($.msg.MSGCOMD0007,"??????_?????????"));
                $("#flnmKnLnm").focus();
                return;
            }

            //???????????????????????????
            if ($("#year option:selected").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "????????????_???"));
                return;
            }
            //???????????????????????????
            if ($("#month option:selected").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "????????????_???"));
                return;
            }
            //???????????????????????????
            if ($("#day option:selected").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "????????????_???"));
                return;
            }

            // //????????????_?????????????????????
            // if($("input[name='postcdMnum']").val()==""){
            //     showMsg($.format($.msg.MSGCOMD0001, "????????????_??????"));
            //     return;
            // }
            // if($("input[name='postcdMnum']").val().length<3){
            //     showMsg($.format($.msg.MSGCOMD0016, "????????????_??????","3"));
            //     return;
            // }
            // if(!/^\d{3}$/.test($("input[name='postcdMnum']").val())){
            //     showMsg($.format($.msg.MSGCOMD0006, "????????????_??????"));
            //     return;
            // }
            //
            // //????????????_?????????????????????
            // if($("input[name='postcdBnum']").val()==""){
            //     showMsg($.format($.msg.MSGCOMD0001, "????????????_??????"));
            //     return;
            // }
            // if($("input[name='postcdBnum']").val().length<4){
            //     showMsg($.format($.msg.MSGCOMD0016, "????????????_??????","4"));
            //     return;
            // }
            // if(!/^\d{4}$/.test($("input[name='postcdBnum']").val())){
            //     showMsg($.format($.msg.MSGCOMD0006, "????????????_??????"));
            //     return;
            // }
            //
            // //?????????????????????
            // if($("input[name='adr']").val()==""){
            //     showMsg($.format($.msg.MSGCOMD0001, "??????"));
            //     return;
            // }

            // //?????????????????????
            // if ($("#sch").val() == "") {
            //     showMsg($.format($.msg.MSGCOMD0001, "??????"));
            //     return;
            // }

            //?????????????????????
            if ($("#gendr option:selected").val() == "") {
                showMsg($.format($.msg.MSGCOMD0001, "??????"));
                return;
            }


            // //?????????????????????
            // if ($("#schy option:selected").val() == "") {
            //     showMsg($.format($.msg.MSGCOMD0001, "??????"));
            //     return;
            // }

            //?????????????????????
            var param = JSON.parse("{}");
            //?????????????????????id
            param.id = vm.stu.id;
            param.updateStr=vm.updateStr;
            // //????????????????????????id
            // param.usrId = $("input[name='usrId']").val();
            //??????_???
            param.stuFnm = $("input[name='stuFnm']").val();
            //??????_???
            param.stuLnm = $("input[name='stuLnm']").val();
            //??????_?????????
            param.flnmKnNm = $("input[name='flnmKnNm']").val();
            //??????_?????????
            param.flnmKnLnm = $("input[name='flnmKnLnm']").val();
            //????????????
            param.birthdayString = $("#year option:selected").val()+'-'+$("#month option:selected").val()+'-'+$("#day option:selected").val();
            //??????
            param.gendrDiv = $("#gendr option:selected").val();
            // //????????????_??????
            // param.postcdMnum = $("input[name='postcdMnum']").val();
            // //????????????_??????
            // param.postcdBnum = $("input[name='postcdBnum']").val();
            // //??????
            // param.adr = $("input[name='adr']").val();
            //??????
            param.schCd = $("#sch").val();
            // //????????????
            // param.schyDiv = $("#schy option:selected").val();
            $.ajax({
                type: "POST",
                url: ctxPath + "/student/F10001/update",
                contentType: "application/json",
                data: JSON.stringify(param),
                success: function (r) {
                    if (r.code == 0) {
                        // var index=layer.alert($.format($.msg.MSGCOMN0015,"????????????"),{
                        //     closeBtn:0,
                        //     btn:["??????"],
                        //     btn1:function () {
                                if (v.id != 'f10002') {
                                    window.location.href = "./F10003.html?id=f10001";
                                } else {
                                    window.location.href = "./F10002.html";
                                }
                        //         index=layer.close(index);
                        //     }
                        // });
                    } else {
                        showMsg(r.msg);
                    }
                }
            });
        },
        returnFn: function () {
            if (v.id == 'f10002') {
                window.location.href = "./F10002.html";
            } else {
                window.location.href = "../common/F40008.html";
            }
        }
    }

})
// F10002POP

var index = '';
$(".save_btn_left").click(function () {
    $(this).addClass("hasborder")
    $("#popId").removeClass("disNone")
    index = layer.open({
        id: 'f10002',
        type: 1,
        title: false,
        shade: 0.1,
        closeBtn: 0,
        shadeClose: false,
        fixed: true,
        area: ['270px', '150px'],
        content: $("#popId"),
        success: function () {
            $(".layui-layer").addClass("layui-chage");
        },
        end: function () {
            $("#popId").addClass("disNone");
        }
    });
});
$(".cancelFn").click(function () {
    $(".save_btn_left").removeClass("hasborder");
    $("#popId").addClass("disNone");
    layer.close(index);
})

function uploadpImg(file, imgNum, select) {
    selectflg = select;
    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            if (reader.readyState === 2) {
                document.getElementById("headImg").src = evt.target.result;
                $(".save_btn_left").removeClass("hasborder");
                $("#popId").addClass("disNone");
                layer.close(index);
            }
        }
        reader.readAsDataURL(file.files[0]);
        document.getElementsByName("pic")[0].files = file.files;
        document.getElementsByName("pic").value = file.value;
    }
}

function dateStart(y,n,d) {
    MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    //????????????????????????
    if(y==undefined){
        y = new Date().getFullYear();
    }
    for (var i = (y - 50); i < (y + 50); i++) //??????????????????50?????????50???
        document.date.year.options.add(new Option(" " + i, i));

    //???????????????????????????
    for (var i = 1; i < 13; i++)
        document.date.month.options.add(new Option(" " + i, i));

    document.date.year.value = y;
    if(n==undefined) {
        n = MonHead[new Date().getMonth()]
    }
    document.date.month.value =n;

    if (new Date().getMonth() == 1 && IsPinYear(y))
        n++;
    writeDay(MonHead[n-1]); //?????????????????????
    if(d==undefined) {
        document.date.day.value = new Date().getDate();
    }else{
        document.date.day.value = d;
    }
}



function selectYear(str) //???????????????????????????????????????????????????????????????????????????
{
    var monthvalue = document.date.month.options[document.date.month.selectedIndex].value;
    if (monthvalue == "") {
        var e = document.date.day;
        optionsClear(e);
        return;
    }
    var n = MonHead[monthvalue - 1];
    if (monthvalue == 2 && IsPinYear(str))
        n++;
    writeDay(n);
}

function selectMonth(str)   //????????????????????????????????????
{
    var yearvalue = document.date.year.options[document.date.year.selectedIndex].value;
    if (yearvalue == "") {
        var e = document.date.day;
        optionsClear(e);
        return;
    }
    var n = MonHead[str - 1];
    if (str == 2 && IsPinYear(yearvalue))
        n++;
    writeDay(n);
}

function writeDay(n)   //?????????????????????????????????
{
    var e = document.date.day;
    optionsClear(e);
    for (var i = 1; i < (n + 1); i++)
        e.options.add(new Option(" " + i, i));
}

function IsPinYear(year)//???????????????????????????
{
    return (0 == year % 4 && (year % 100 != 0 || year % 400 == 0));
}

function optionsClear(e) {
    e.options.length = 1;
}
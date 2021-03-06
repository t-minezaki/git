var param =new getParam();
var orgAddId = '';
$.ajax({
    url: ctxPath + '/common/F40004/getOrg',
    type: 'GET',
    dataType: "json",
    success: function (data){
        orgAddId = data.orgIdAdd;
        if (window.getCookie('brandcd') != orgAddId){
            $(".chooseChild").css("visibility","hidden")
        }
    }
});
var vm =new Vue({
    el:'.content',
    data:{
        codList:[],
        guard:[],
        updateTime:""
    },
    updated:function(){
    },
    mounted:function () {
        this.getInfo();
    },
    methods:{
        getInfo:function () {
            $.ajax({
                url: ctxPath + '/guard/f30001/init',
                type: 'GET',
                dataType: 'json',
                async: true,
                success:function (data) {
                    if (data.code == 0){
                        if (data.guard){
                            vm.guard = data.guard;
                            $('#flnmNm').val(data.guard.flnmNm);
                            $('#flnmLnm').val(data.guard.flnmLnm);
                            $('#flnmKnNm').val(data.guard.flnmKnNm);
                            $('#flnmKnLnm').val(data.guard.flnmKnLnm);
                            $('#telnum').val(data.guard.telnum);
                            $('#urgTelnum').val(data.guard.urgTelnum);
                            $('#email').html(data.guard.mailad);
                            if(data.guard.postcdMnum+data.guard.postcdBnum){
                                $('#postcd').val((data.guard.postcdMnum+data.guard.postcdBnum).trim(""));
                            }
                            $('#adr1').val(data.guard.adr1);
                            $('#adr2').val(data.guard.adr2);
                            window.id = data.guard.id;
                            vm.updateTime = data.updateTime;
                            if (data.safModifyFlg === '0') {
                                $('#search').css("visibility","hidden");
                                $('#confirm').css("visibility","hidden");
                                $('#submit').css("visibility","hidden");
                            }
                        }
                    }else {
                        showMsg(data.msg);
                    }
                    if(data.codList){
                        vm.codList = data.codList;
                    }
                }
            });
        },
        //??????check
        isHalfAngle:function(str){
            var flg=true;
            for(var i=0;i<str.length;i++){
                if(48>str.charCodeAt(i)||str.charCodeAt(i)>57){
                    flg=false;
                    break;
                }
            }
            return flg
        },
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
        searchFn:function (){
            // ????????????
            // ??????????????????????????????????????????????????????
            if($("#postcd").val() == ''){
                showMsg($.format($.msg.MSGCOMD0001,"????????????"));
                $("#postcd").focus();
                return;
            }
            // ???????????????????????????????????????????????????????????????
            if ($('input[id="postcd"]').val().length > 7 ){
                showMsg($.format($.msg.MSGCOMD0017,"????????????","7"));
                return;
            }
            // ?????????????????????????????????????????????????????????
            if ($('input[id="postcd"]').val().length < 5){
                showMsg($.format($.msg.MSGCOMD0016,"????????????","5"));
                return;
            }
            // ???????????????????????????????????????????????????????????????????????????
            if(this.isHalfAngle($('input[id="postcd"]').val()) == false){
                showMsg($.format($.msg.MSGCOMD0006,"????????????"));
                $("#postcd").focus();
                return;
            }
            $.ajax({
                url: ctxPath + '/guard/f30001/search',
                type: 'POST',
                data:{
                    postcd:$('input[id="postcd"]').val()
                },
                dataType: 'json',
                async: true,
                success:function (data){
                    if (data.code == 0){
                        $('#adr1').val(data.adr1);
                    }else {
                        $('#adr1').val('');
                        showMsg(data.msg);
                    }
                }
            })
        },
        cancelFn:function () {
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        },
        backFn:function(){
            if(param.flag==1){
                window.location.href="../common/F40008.html";
            }else{
                window.history.back();
            }
        },
        confirmFn:function () {
            // ??????????????????????????????????????????????????????????????????
            layer.open({
                id:'F40017',
                type:2,
                title: ['', 'display:none;'],
                shade: 0.1,
                // button:null,
                closeBtn: 0,
                shadeClose: false,
                move: '.layui-layer-title',
                area: ["100%","100%"],
                fixed: true,
                resize: false,
                background:'#F0F0F0',
                content:["../common/F40017.html"],
                end: function(){
                    $.ajax({
                        url: ctxPath + '/guard/f30001/init',
                        type: 'GET',
                        dataType: 'json',
                        async: true,
                        success:function (data) {
                            if (data.code == 0){
                                if (data.guard){
                                    $('#email').html(data.guard.mailad);
                                    vm.updateTime = data.updateTime;
                                }
                            }
                        }
                    });
                }
            })
        },
        submitFn:function () {
            formatCheck();
            if ($("#f30001Form").valid() == true ){
                $.ajax({
                    url: ctxPath + '/guard/f30001/submit',
                    data:{
                        id:window.id,
                        flnmNm:$('#flnmNm').val(),
                        flnmLnm:$('#flnmLnm').val(),
                        flnmKnNm:$('#flnmKnNm').val(),
                        flnmKnLnm:$('#flnmKnLnm').val(),
                        telnum:$('#telnum').val(),
                        urgTelnum: $('#urgTelnum').val(),
                        mailad:$('#email').html(),
                        postcdMnum:$('#postcd').val().substring(0,3),
                        postcdBnum:$('#postcd').val().substring(3,7),
                        adr1:$('#adr1').val(),
                        adr2:$('#adr2').val(),
                        reltnspDiv:$('#reltnspDiv').val(),
                        updateTime:vm.updateTime
                    },
                    dataType:'json',
                    type:'POST',
                    async:true,
                    success:function (data) {
                        if (data.code == 0) {
                            // layer.confirm($.format($.msg.MSGCOMN0015,"????????????"),{btn:["??????"]},function () {
                                if (param.flag == 1){
                                    if (data.f30001Dto.count == 1){
                                        window.open("../guard/F30101.html?stuId="+data.f30001Dto.stuId+"&orgId="+data.f30001Dto.orgId+"&crmschId="+data.crmschId,"_self");
                                    }
                                    else if (data.f30001Dto.count > 1) {
                                        window.open("../guard/F30002.html","_self");
                                    }else {
                                        layer.alert($.msg.MSGCOMN0102);
                                    }
                                }
                                else {
                                    window.history.go(-1);
                                }
                            // })
                        }
                        else {
                            layer.alert(data.msg);
                        }
                    }
                });
            }
        }
    }
});

$("#email").bind('input propertychange',function () {
    var summary = $(this).val().split("@")[0];
    var suffix = $(this).val().split("@")[1];
    if (summary.length > 30) {
        parent.layer.alert("????????????????????????????????????????????????")
        $(this).val($(this).val().substring(0, 30) + "@" + suffix)
    }
});

function formatCheck() {
    $("#f30001Form").validate({
        rules: {
            flnmNm:{
                required: true,
                maxlength:10
            },
            flnmLnm: {
                required: true,
                maxlength:10
            },
            flnmKnNm:{
                required: true,
                maxlength:10,
                zenkaku:true
            },
            flnmKnLnm:{
                required: true,
                maxlength:10,
                zenkaku:true
            },
            telnum: {
                minlength: 10,
                maxlength: 11,
                digits:true
            },
            urgTelnum: {
                maxlength:20,
                digits:true
            },
            email: {
                required: true,
                email:true,
                maxlength:60
            },
            postcd:{
                required: true,
                digits:true,
                minlength:5,
                maxlength:7
            },
            adr1:{
                required:true,
                maxlength:60
            },
            adr2:{
                maxlength:60
            }
        },
        debug:true,
        messages: {
            // ??????_???
            flnmNm: {
                required:$.format($.msg.MSGCOMD0001,"??????_???"),
                maxlength:$.format($.msg.MSGCOMD0011,"??????_???","10")
            },
            // ??????_???
            flnmLnm: {
                required:$.format($.msg.MSGCOMD0001,"??????_???"),
                maxlength:$.format($.msg.MSGCOMD0011,"??????_???","10")
            },
            // ??????_?????????
            flnmKnNm: {
                required:$.format($.msg.MSGCOMD0001,"??????_?????????"),
                maxlength:$.format($.msg.MSGCOMD0011,"??????_?????????","10"),
                zenkaku:$.format($.msg.MSGCOMD0007, "??????_?????????")
            },
            // ??????_?????????
            flnmKnLnm: {
                required:$.format($.msg.MSGCOMD0001,"??????_?????????"),
                maxlength:$.format($.msg.MSGCOMD0011,"??????_?????????","10"),
                zenkaku:$.format($.msg.MSGCOMD0007, "??????_?????????")
            },
            // ????????????
            telnum: {
                minlength: $.format($.msg.MSGCOMD0010, "????????????", "10", "11"),
                maxlength: $.format($.msg.MSGCOMD0010, "????????????", "10", "11"),
                digits:$.format($.msg.MSGCOMD0006,"????????????")
            },
            // ?????????
            email: {
                required:$.format($.msg.MSGCOMD0001,"?????????"),
                maxlength:$.format($.msg.MSGCOMD0011,"?????????","60"),
                email:$.format($.msg.MSGCOMD0018,"?????????")
            },
            // ??????
            postcd:{
                required:$.format($.msg.MSGCOMD0001,"????????????"),
                maxlength:$.format($.msg.MSGCOMD0017,"????????????","7"),
                minlength:$.format($.msg.MSGCOMD0016,"????????????","5"),
                digits:$.format($.msg.MSGCOMD0006,"????????????")
            },
            // ?????????
            adr1: {
                required:$.format($.msg.MSGCOMD0001,"?????????"),
                maxlength:$.format($.msg.MSGCOMD0011,"?????????","60"),
            },
            // ?????????
            adr2: {
                maxlength:$.format($.msg.MSGCOMD0011,"?????????","60"),
            }
        },
        showErrors:function(errorMap,errorList) {
            if (errorList.length != 0) {
                showMsg(errorList[0].message);
            }
        }
    });
}
var param = getParam();
// 期日フォーマット
function dateFmtYMD(val) {
    /* 2021/01/15 UT-146 cuikailin modify start */
    if(val){
        var date = new Date(val);
        Y = date.getFullYear() + '/';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
        D = date.getDate() > 9 ? date.getDate() + ' ' : '0' + date.getDate() + ' ';
        h = date.getHours() > 9 ? date.getHours() + ':' : '0' + date.getHours() + ":";
        m = date.getMinutes() > 9 ? date.getMinutes() + ':' : '0' + date.getMinutes() ;
        return Y + M + D ;
    }else {
        return "";
    }
}
// 期日フォーマット
function dateFmt(val) {
    if(val) {
        var date = new Date(val);
        Y = date.getFullYear() + '/';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
        D = date.getDate() > 9 ? date.getDate() + ' ' : '0' + date.getDate() + ' ';
        h = date.getHours() > 9 ? date.getHours() + ':' : '0' + date.getHours() + ":";
        m = date.getMinutes() > 9 ? date.getMinutes() + ':' : '0' + date.getMinutes();
        // s = date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds();
        return Y + M + D + h + m;
    }else {
        return "";
    }
    /* 2021/01/15 UT-146 cuikailin modify end */
}


$(function () {
    // laydate日時を設ける
    laydate.render({
        elem: '#timeOne',
        type: 'datetime',
        format: 'yyyy/MM/dd',
        done: function (value) {
            $("#readTimeStart").val(dateFmtYMD(value))
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeTwo',
        type: 'datetime',
        format: 'yyyy/MM/dd ',
        done: function (value) {
            $("#readTimeEnd").val(dateFmtYMD(value))
        }
    });
    // laydate日時を設ける
    laydate.render({
        elem: '#timeThree',
        type: 'datetime',
        format: 'yyyy/MM/dd ',
        done: function (value) {
            $("#replyTimeStart").val(dateFmtYMD(value))
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeFour',
        type: 'datetime',
        format: 'yyyy/MM/dd ',
        done: function (value) {
            $("#replyTimeEnd").val(dateFmtYMD(value))
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeFive',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm',
        done: function (value) {
            $("#applyDatimeStart").val(dateFmt(value))
        }
    });

    // laydate日時を設ける
    laydate.render({
        elem: '#timeSix',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm',
        done: function (value) {
            $("#applyDatimeEnd").val(dateFmt(value))
        }
    });

    // サブウィンドウインデックスを取得する
    var index = parent.layer.getFrameIndex(window.name);

    // 検索ボタン押下後
    $("#submitbtn").click(function () {
        var data = {};
        var flg = 0;
        data['guardName'] = $("#guardName").val().replace(" ",'');
        data['stuName'] = $("#stuName").val().replace(" ",'');
        data['codCd'] = $("#status").find("option:selected").attr("value");
        data['codKey'] = $("#status").find("option:selected").attr("data-key");
        if ($("#readTimeStart").val()) {
            data['readTimeStart'] = $("#readTimeStart").val()+ "00:00:00" ;
        }
        if ($("#readTimeEnd").val()){
            data['readTimeEnd'] = $("#readTimeEnd").val() + "23:59:59";
        }
        if($("#replyTimeStart").val()){
            data['replyTimeStart'] = $("#replyTimeStart").val()+ "00:00:00";
        }
        if ($("#replyTimeEnd").val()) {
            data['replyTimeEnd'] = $("#replyTimeEnd").val()+ "23:59:59";
        }
        if ($("#applyDatimeStart").val()){
            data['applyDatimeStart'] = $("#applyDatimeStart").val();
        }
        if ($("#applyDatimeEnd").val()){
            data['applyDatimeEnd'] = $("#applyDatimeEnd").val();
        }
        if ($("#roleName").val()){
            data['roleName'] = $("#roleName").val().replace(" ",'');
        }
        data['refType'] = vue.refType;
        parent.data = data;
        parent.flg = flg;
        parent.layer.close(index);
    });
// closeボタン押下後
    $("#close_btn").click(function () {
        parent.layer.close(index);
    });

});
var vue = new Vue({
    el: '#form1',
    data: {
        refType:'',
        statusList:[]
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/manager/F08011/getRefType',
                type: 'GET',
                data: {
                    eventId:param.eventId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        vue.refType = data.refType;
                        vue.statusList = data.statusList
                    }
                    else {
                        showMsg(data.msg)
                    }
                }
            })
        }
    }
})




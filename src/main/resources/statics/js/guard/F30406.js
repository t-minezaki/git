var param = getParam();
$(function () {
    $.get(ctxPath + '/guard/F30406/init',
        {
            eventId: "p11"
        },function (data) {
            if(data.code == 0){

                $("#guardNm").text(data.guardNm);
                var brand = getCookie("brandcd").toUpperCase()==='ONAIR'?getCookie("brandcd").toUpperCase():getCookie("brandcd");
                var dataBrand = data.brandCd.toUpperCase() ==='ONAIR'?data.brandCd.toUpperCase():data.brandCd;
                var brandCd = data.brandCd == ''?brand:dataBrand;
                var orgAddId = data.orgIdAdd.toUpperCase();
                if(brandCd == orgAddId){
                    $("#diffShow").hide();
                    $("#infoUp").attr("href","https://gonline-membership.gakken.jp/membership/mypage/?external_browser=1");
                }else{
                    $("#diffShow").show();
                    $("#infoUp").attr("href","F30001.html");
                }
            }else {
                showMsg(data.msg);
            }
        });
});

function toF30405() {
    window.location.href = "F30405.html?eventid="+param.eventId;
}
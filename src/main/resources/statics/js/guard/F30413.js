var param = getParam();
// guid_repr_id = 1;
// tgt_ymd = new Date("2019-12-09");
$.ajax({
    url: ctxPath + '/guard/F30413/init',
    type: 'GET',
    data: {
        noticeId: param.noticeId,
        deliverCd: param.deliverCd,
        guidReprId:param.guidReprId
    },
    async: true,
    datatype: 'json',
    success: function (data) {
        if (data.code == 0) {
            var dto = data.dto;
            var lsd = data.lsd;
            if (dto.absStsDiv == null || dto.absStsDiv == "") {
                // $("#asd").attr("style", "display:none;");
                $("#asd").remove();
            }
            else {
                $("#abs_sts_div").text(dto.absStsDiv);
            }
            if (dto.guidCont == null || dto.guidCont == "") {
                // $("#gc").attr("style", "display:none;");
                $("#gc").remove();
            }
            else {
                $("#guid_cont").text(dto.guidCont);
            }
            if (dto.hwkCont == null || dto.hwkCont == "") {
                // $("#hc").attr("style", "display:none;");
                $("#hc").remove();
            }
            else {
                $("#hwk_cont").text(dto.hwkCont);
            }
            if (dto.hwkDiv == null || dto.hwkDiv == "") {
                // $("#hd").attr("style", "display:none;");
                $("#hd").remove();
            }
            else {
                $("#hwk_div").text(dto.hwkDiv);
            }
            if (dto.lastHwkDiv == null || dto.lastHwkDiv == "") {
                // $("#ld").attr("style", "display:none;");
                $("#ld").remove();
            }
            else {
                $("#lasthwk_div").text(dto.lastHwkDiv);
            }
            if (dto.testUnitNm == null || dto.testUnitNm == "") {
                // $("#tun").attr("style", "display:none;");
                $("#tun").remove();
            }
            else {
                $("#test_unit_nm").text(dto.testUnitNm);
            }
            if (dto.testPoints == null || dto.testPoints == "") {
                // $("#tp").attr("style", "display:none;");
                $("#tp").remove();
            }
            else {
                $("#test_points").text(dto.testPoints);
            }
            if (dto.careDiv == null || dto.careDiv == "") {
                // $("#cd").attr("style", "display:none;");
                $("#cd").remove();
            }
            else {
                $("#care_div").text(dto.careDiv);
            }
            if (dto.schStsDiv == null || dto.schStsDiv == "") {
                // $("#ssd").attr("style", "display:none;");
                $("#ssd").remove();
            }
            else {
                $("#sch_sts_div").text(dto.schStsDiv);
            }
            if (lsd == null || lsd == "") {
                // $("#lsd").attr("style", "display:none;");
                $("#lsd").remove();
            }
            else {
                $("#lect_shape_div").text(lsd);
            }
            if (dto.concItemCont == null || dto.concItemCont == "") {
                // $("#cic").attr("style", "display:none;");
                $("#cic").remove();
            }
            else {
                $("#conc_item_cont").text(dto.concItemCont);
            }
            if (dto.useCont == null || dto.useCont == "") {
                // $("#uc").attr("style", "display:none;");
                $("#uc").remove();
            }
            else {
                $("#use_cont").text(dto.useCont);
            }
            //2020/11/11 huangxinliang modify start
            if (data.noticeLevelDiv){
                vm.importantDiv = data.noticeLevelDiv;
            }
            // 2020/11/25 huangxinliang modify start
            if (data.openedFlg){
                vm.openedFlg = data.openedFlg;
            }
            // 2020/11/25 huangxinliang modify end
            if (data.grasId){
                vm.grasId = data.grasId;
            }
            //2020/11/11 huangxinliang modify end
        }
        else {

        }
    }
});
//2020/11/11 huangxinliang add start
var vm = new Vue({
    el: '#main',
    data: function (){
        return{
            importantDiv: '0',
            // 2020/11/25 huangxinliang modify start
            openedFlg: '1',
            // 2020/11/25 huangxinliang modify end
            grasId: -1
        }
    },
    methods: {
        confirm: function (){
            $.ajax({
                url: ctxPath + '/guard/F30413/confirm',
                type: 'GET',
                data: {
                    grasId: vm.grasId
                },
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        // 2020/11/25 huangxinliang modify start
                        vm.openedFlg = '1';
                        var index = layer.confirm("配信情報を確認したことを送信しました。", {
                            btn: ['確認'],
                            title: "確認",
                            btn1: function () {
                                layer.close(index);
                            }
                        });
                        // 2020/11/25 huangxinliang modify end
                    }
                }
            })
        }
    }
})
//2020/11/11 huangxinliang add end
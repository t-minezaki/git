var param = getParam();
var time1 = 0;
var time2 = 0;
var vue = new Vue({
    el: '.content',
    data: {
        f03005Dto: '',
        mstUnitEntity: '',
        mstCodDEntitySchyDivList: '',
        mstCodDEntitySubjtDivList: '',
        mstOrgEntity: ''
    },
    mounted: function () {
        this.getInfo();
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F03005/init',
                type: 'GET',
                data: {
                    id: param.unitId,
                },
                async: true,
                datatype: 'json',
                success: function (data) {
                    if (data.mstOrgEntity) {
                        vue.mstOrgEntity = data.mstOrgEntity;
                        $("#orgId").text(vue.mstOrgEntity.orgId);
                        $("#orgNm").text(getOrgName(vue.mstOrgEntity.orgNm));
                    }
                    if (data.f03005Dto) {
                        vue.f03005Dto = data.f03005Dto;
                    }
                    if (param.unitId){
                        if (data.mstUnitEntity) {
                            vue.mstUnitEntity = data.mstUnitEntity;
                            $("#chapt_nm_input").val(vue.mstUnitEntity.chaptNm)
                            $("#sectn_nm_input").val(vue.mstUnitEntity.sectnNm)
                            $("#unit_nm_input").val(vue.mstUnitEntity.unitNm)
                            $("#unit_mst_cd_input").val(vue.mstUnitEntity.unitMstCd)
                        } else {
                            layer.alert($.format($.msg.MSGCOMN0017, "単元"));
                            return false;
                        }
                    }
                    if (data.mstCodDEntitySchyDivList.length != 0) {
                        vue.mstCodDEntitySchyDivList = data.mstCodDEntitySchyDivList;
                        if (time1 == 0) {
                            var str = "<option value=''></option>";
                            for (var i = 0; i < vue.mstCodDEntitySchyDivList.length; i++) {
                                str += "<option value='" + vue.mstCodDEntitySchyDivList[i].codCd + "'>" + vue.mstCodDEntitySchyDivList[i].codValue + "</option>";
                            }
                            $("#schy_div_input").html(str);
                            $("#schy_div_input option[value='" + vue.mstUnitEntity.schyDiv + "']").attr("selected", "selected");
                            time1++;
                        }
                    } else {
                        layer.alert($.format($.msg.MSGCOMN0017, "学年"));
                        return false;
                    }
                    if (data.mstCodDEntitySubjtDivList) {
                        vue.mstCodDEntitySubjtDivList = data.mstCodDEntitySubjtDivList;
                        if (time2 == 0) {
                            var str = "<option value=''></option>";
                            for (var i = 0; i < vue.mstCodDEntitySubjtDivList.length; i++) {
                                str += "<option value='" + vue.mstCodDEntitySubjtDivList[i].codCd + "'>" + vue.mstCodDEntitySubjtDivList[i].codValue + "</option>";
                            }
                            $("#subjt_div_input").html(str);
                            $("#subjt_div_input option[value='" + vue.mstUnitEntity.subjtDiv + "']").attr("selected", "selected");
                            time2++;
                        }
                    } else {
                        layer.alert($.format($.msg.MSGCOMN0017, "教科"));
                        return false;
                    }
                }
            })
        }
    }
});
$("#addBtn").click(function () {
    var id = param.id;
    var schyDiv = $("#schy_div_input option:selected").val();
    if (schyDiv == '') {
        layer.alert($.format($.msg.MSGCOMD0001, "学年"));
        return;
    }
    var subjtDiv = $("#subjt_div_input option:selected").val();
    if (subjtDiv == '') {
        layer.alert($.format($.msg.MSGCOMD0001, "教科"));
        return;
    }
    var chaptNm = $("#chapt_nm_input").val();
    if (chaptNm == '') {
        layer.alert($.format($.msg.MSGCOMD0001, "章名"));
        return;
    }
    if (chaptNm.length > 300) {
        layer.alert($.format($.msg.MSGCOMD0011, "章名", "300"));
        return;
    }
    var sectnNm = $("#sectn_nm_input").val();
    if (sectnNm.length > 300) {
        layer.alert($.format($.msg.MSGCOMD0011, "節名", "300"));
        return;
    }
    var unitNm = $("#unit_nm_input").val();
    if (unitNm == '') {
        layer.alert($.format($.msg.MSGCOMD0001, "単元名"));
        return;
    }
    if (unitNm.length > 300) {
        layer.alert($.format($.msg.MSGCOMD0001, "単元名", "300"));
        return;
    }
    var unitMstCd = $("#unit_mst_cd_input").val();
    if (unitMstCd.length > 20) {
        layer.alert($.format($.msg.MSGCOMD0011, "単元管理ＣＤ", "20"));
        return;
    }
    $.ajax({
        url: ctxPath + '/pop/F03005/Add',
        type: 'POST',
        data: {
            id: param.unitId,
            unitMstCd: unitMstCd,
            chaptNm: chaptNm,
            sectnNm: sectnNm,
            unitNm: unitNm,
            schyDiv: schyDiv,
            subjtDiv: subjtDiv,
            updateTimeStr: vue.f03005Dto.updateTimeStr
        },
        datatype: 'json',
        success: function (data) {
            if (data.code != 0) {
                parent.layer.alert(data.msg);
            } else {
                // var idx = layer.alert($.format($.msg.MSGCOMN0022, "登録"), {
                //     yes: function () {
                        parent.location.reload();
                //         parent.layer.close(idx);
                //     }
                // })
            }
        }
    })
});

$("#cancel").click(function () {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
});
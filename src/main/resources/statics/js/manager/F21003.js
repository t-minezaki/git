$(function () {
        reload();
});
var corrspdSts ='0';
$('input[type=radio][name=a]').change(function() {
    corrspdSts = this.value ;
    reload();
});
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "S+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "s": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function reload(){
    var srcHeight = $(window).height() * 0.6;
    var srcWidth = $(window).width() * 0.98;
    $.jgrid.gridUnload("jqGrid");
    $("#jqGrid").jqGrid({
            url: ctxPath + '/manager/F21003/init',
            datatype: "json",
            postData: {
                corrspdSts:corrspdSts
            },
            colModel: [
                {label: '生徒名', name: 'stuNm', index: 'stuNm', width: 15, sortable: false, align: "center"},
                {label: '学年', name: 'codValue', index: 'codValue', width: 15, sortable: false, align: "center"},
                {label: '保護者連絡日時', name: 'tgtYmd', index: 'tgtYmd', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                    var s = new Date(object.tgtYmd.replace(/-/g, '/')).Format("yyyy/MM/dd HH:mm");
                        return s;
                    }
                },

                {label: '保護者名', name: 'name', index: 'name', width: 15, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var name = object.flnmNm +" "+ object.flnmLnm;
                        return name;
                    }
                },
                {
                    label: '操作', name: 'operate', index: 'operate', width: 20, sortable: false, align: "center",
                    formatter(cell, option, object) {
                        var params={}
                        params.id = object.id;
                        params.tgtYmd = object.tgtYmd.substring(0,10);
                        params.mailad = object.mailad;
                        params.flnmNm = object.flnmNm;
                        params.flnmLnm = object.flnmLnm;
                        params.corrspdSts = object.corrspdSts;
                        params.noticeId = object.noticeId;
                        if (corrspdSts == 0|| corrspdSts==3){
                        return "<button onclick=toF21004('"+JSON.stringify(params)+"')> 確認連絡</button>"
                            + "&nbsp" + "&nbsp" + "&nbsp" + "" +
                            "<button value='"+object.corrspdSts+"' class='gridButton' onclick=change('" + object.id + "','" + object.corrspdSts + "')> 非表示</button>"
                        }else {
                            return "<button value='"+object.corrspdSts+"' class='gridButton' onclick=change('" + object.id + "','" + object.corrspdSts + "')> 未対応に戻る</button>"
                        }
                    }
                }
            ],
            viewrecords: true,
            height: srcHeight,
            width:srcWidth,
            rowNum: 30,
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
                for(var i=0;i<data.page.list.length;i++){
                    if (data.page.list[i].corrspdSts =="2" || data.page.list[i].corrspdSts =="3"){
                        // $("#"+data.page.list[i].id+"").css("background","#E5E5E5");
                    }
                }
                if (data.page.list =="") {
                    showMsg($.format($.msg.MSGCOMN0017, "遅刻・欠席連絡"))
                }
            },

        }
    );
    // jqgrid重載
    $('#jqGrid').trigger('reloadGrid');
}
function change(id,corrspdSts) {
    // 確認ダイアログをポップアップ表示する
    if (corrspdSts == "1" || corrspdSts == "2") {
    var index = layer.confirm($.format($.msg.MSGCOMN0131, "対応済を"), {
        skin: 'layui-layer-molv',
        title: '確認',
        closeBtn: 0,
        anim: -1,
        btn: ['キャンセル', '確認'],
        btn1: function () {
            layer.close(index);
        },
        btn2: function () {
            $.ajax({
                url: ctxPath + '/manager/F21003/change',
                type: 'GET',
                data: {
                    id: id,
                    corrspdSts:corrspdSts
                },
                dataType: "json",
                success: function (data) {
                    if (data.code != 0) {
                        showMsg(data.msg);
                    } else {
                        reload();
                    }
                }
            });
        }
    })}else {
    $.ajax({
        url:ctxPath+'/manager/F21003/change',
        type:'GET',
        data:{
            id:id,
            corrspdSts:corrspdSts
        },
        success: function (data) {
            if (data.code != 0) {
                showMsg(data.msg);
            } else {
                reload();
            }
        }
    })}
}
function toF21004(params) {
    var params1 = JSON.parse(params);
    params1.guardName = params1.flnmNm + ' ' + params1.flnmLnm;
    var srcWidth = "480px";
    var srcHeight = "320px";
    layer.open({
        id: 'F21016',
        type: 2,
        anim: 2,
        title: ['', 'display:none;'],
        skin: "layui-layer-myskin2",
        shade: 0.1,
        closeBtn: 1,
        shadeClose: false,
        move: '.layui-layer-title',
        area: [srcWidth, srcHeight],
        fixed: true,
        resize: false,
        background:'#F0F0F0',
        content: ["../pop/F21004.html?" + $.param(params1)],
        end: function () {
            reload();
        }
    })
}

$(function () {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    $(".radioDiv").css("margin-top", "0.2%");
                    $("input[type=\"radio\"].newtui-radio-one").append('<style>input[type="radio"].newtui-radio-one:checked::after{border: 2px solid' +
                        ' #AAA}</style>')
                } else if (/iPad/i.test(navigator.userAgent)) {
                    $(".radioDiv").css("margin-top", "0.8%");
                } else {
                    // alert("other")
                }
            }
            catch (e) {
            }
        } else {
            // alert("456")
        }
    } else {
        if (/Macintosh/i.test(navigator.userAgent)){
            $(".radioDiv").css("margin-top", "0.8%");
        }
    }

    // 「代理登録」ボタン押下
    $("#proxyBtn").click(function () {
        var srcWidth = $(window).width() * 0.4 + "px";
        var srcHeight = $(window).height() * 0.7 + "px";
        layer.open({
            id: 'F21016',
            type: 2,
            anim: 2,
            skin: "layui-layer-myskin",
            title: "",
            shade: 0.2,
            closeBtn: 0,
            shadeClose: false,
            move: '.layui-layer-title',
            area: [srcWidth, srcHeight],
            resize: false,
            content: ["../pop/F21016.html", 'no']
        });
    })
    // 「日程一覧へ」ボタン押下時
    $("#schedule").click(function () {
        window.location.href = 'F21005.html?';
    });
});
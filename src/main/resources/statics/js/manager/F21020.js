var page = 1;
var pageSize = 15;
var total = 0;
var param = getParam();
var width = $(window).width() * 0.96 + 18;
var srcHeight = ($(window).height() * 0.9 - 54) * 0.6 - 60 - 36;
$(function () {

});

var vm = new Vue({
    el: "#page",
    data: {
        stampCd: "",
        stuNm: "",
        offset: 0,
        codValue:''
    },
    mounted: function () {
        if(getCookie("equipment") === "phone"){
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21020.css"}).appendTo("head");
        }else {
            $("<link>").attr({ rel: "stylesheet",type: "text/css",href: ctxPath + "/css/manager/F21020-1.css"}).appendTo("head");
        }
        this.init();
        this.setUp();
        this.showNotice();
    },
    updated: function () {

    },
    methods: {
        init: function () {
            $("#jqGrid").jqGrid({
                    url: ctxPath + '/manager/F21020/list',
                    datatype: "json",
                    postData: {
                        stuId: param.stuId,
                        limit: pageSize
                    },
                    colModel: [
                        {label: '日付', name: 'complimentDt', index: 'complimentDt', width: 16, sortable: false, align: "center",
                            formatter(cell, option, object) {
                                var date = new Date(object.complimentDt.replace(/(\d{4})-(\d{2})-(\d{2})\s(\d{2}):(\d{2})\S*/,'$1/$2/$3'));
                                return date.Format("M/d");
                            }},
                        {
                            label: 'コメント', name: 'complimentCont', index: 'complimentCont', width: 52, sortable: false, align: "left",
                            formatter(cell, option, object) {
                                var codValue2 = object.codValue2 === null ? '' : '[' + object.codValue2 + ']';
                                var otherLine = codValue2 === '' ? '' : '</br>';
                                return '<p class="comment">' + codValue2 + otherLine + cell + '</p>';
                            }
                        },
                        {
                            label: 'スタンプ',
                            name: 'noticeTitle',
                            index: 'noticeTitle',
                            width: 16,
                            sortable: false,
                            align: "center",
                            formatter(cell, option, object) {
                                if (object.stamp) {
                                    return '<div class="stamp" style="text-align: center" onclick="vm.showImg(\'' + object.stampImg + '\')">〇</div>';
                                } else {
                                    return '<div class="stamp" style="text-align: center">―</div>';
                                }

                            }
                        },
                        {
                            label: '状態',
                            name: 'readingStsDiv',
                            index: 'readingStsDiv',
                            width: 16,
                            sortable: false,
                            align: "center"
                        }
                    ],
                    viewrecords: true,
                    regional: 'jp',
                    height: srcHeight,
                    width: width,
                    rowNum: pageSize,
                    // rowList: [5, 10, 20],
                    rownumbers: false,
                    // rownumWidth: 25,
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
                        rows: "limit",
                        order: "order"
                    }
                    ,
                    gridComplete: function () {
                        if ($("#message") != null) {
                            $("#message").hide();
                        }
                        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                        $(".ui-jqgrid-labels").find("th").eq(3).css("border-right", "0 none");
                        $(".ui-jqgrid-bdiv").append("<ul class=\"insert\" onclick=\"vm.insert()\">更に読み込む</ul>");
                        if (vm.offset > 0) {
                            $(".ui-jqgrid-bdiv").scrollTop(vm.offset);
                        }
                    }
                    ,
                    loadComplete: function (data) {
                        if (pageSize >= data.page.totalCount) {
                            $(".insert").css("display", "none");
                        }
                    }
                }
            );
        },
        showNotice: function () {
            $.ajax({
                url: ctxPath + '/manager/F21020/init',
                type: 'get',
                datatype: 'json',
                data: {
                    stuId: param.stuId
                },
                success: function (data) {
                    if (data.stuNm) {
                        vm.stuNm = data.stuNm;
                    }
                }
            });
        },
        toF21021: function () {
            var srcWidth = "315px";
            var srcHeight = "270px";
            var os = function () {
                var ua = navigator.userAgent,
                    isWindowsPhone = /(?:Windows Phone)/.test(ua),
                    isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
                    isAndroid = /(?:Android)/.test(ua),
                    isFireFox = /(?:Firefox)/.test(ua),
                    isChrome = /(?:Chrome|CriOS)/.test(ua),
                    isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isFireFox && /(?:Tablet)/.test(ua)),
                    isPhone = /(?:iPhone)/.test(ua) && !isTablet,
                    isPc = !isPhone && !isAndroid && !isSymbian;
                return {
                    isTablet: isTablet,
                    isPhone: isPhone,
                    isAndroid: isAndroid,
                    isPc: isPc
                };
            }();
            if (os.isTablet) {
                srcWidth = '550px';
                srcHeight = '400px';
            }
            layer.open({
                id: 'f21021',
                type: 2,
                title: '',
                shade: 0.1,
                closeBtn: 1,
                shadeClose: false,
                area: [srcWidth, srcHeight],
                fixed: true,
                resize: false,
                content: ["../pop/F21021.html"],
                success: function (layero, index) {
                },
                end: function () {
                    if (vm.stampCd == null || vm.stampCd === '') {
                        $('.svg').prop('src', ctxPath + '/img/smile_grey.png');
                    } else {
                        $('.svg').prop('src', ctxPath + '/img/change_pink_smile.png');
                    }
                    $(".stamp_img_div").find("img").each(function () {
                        $(this).remove();
                    })
                    if(getCookie("equipment") === "phone"){
                        $(".stamp_img_div").append('<img src="' + codValue + '">');
                        $(".imgDiv").css("display","none");
                    }else {
                        $(".imgDiv").find("img").attr("src",codValue);
                    }

                }
            });
        },
        setUp: function () {
            $('.image-container').click(function () {
                $(".image-container").fadeOut();
            });
        },
        showImg: function (src) {
            previewImg(src);
        },
        insert: function () {
            pageSize = pageSize + 15;
            vm.offset = $("#jqGrid").height() - $('.ui-jqgrid-bdiv').height() + $(".insert").outerHeight();
            $.jgrid.gridUnload("jqGrid");
            vm.init();
        }
    }
});
function previewImg(src) {
    var width ='170px';
    var height ='126px';
    var imgHtml = "<img id='preview' src='" + decodeURIComponent(src).replace(/\%20/g," ") + "' />";
    //弹出层
    layer.open({
        type: 1,
        closeBtn:1,
        shade: 0.8,
        offset: 'auto',
        area: [width,height],
        shadeClose: false,
        title: "",
        content: imgHtml,
        cancel: function () {
        }
    });
    return false;
}
$("#sendMail").click(function () {
    if ($(".cmt_in_div").find("textarea").eq(0).val().trim() === '' && (vm.stampCd == null || vm.stampCd === '')) {
        var idx = layer.confirm($.format($.msg.MSGCOMN0153, 'コメント', 'スタンプ'), {
            skin: 'layui-layer-molv',
            title: '確認',
            closeBtn: 0,
            anim: -1,
            btn: ['確認'],
            yes: function () {
                layer.close(idx);
            }
        });
        return;
    }
    //NWT 楊 2021/07/28 MANAMIRU1-711 Edit Start
    var comment =  $(".cmt_in_div").find("textarea").eq(0).val().trim();
    $.ajax({
        url: ctxPath + '/manager/F21020/sendMail',
        type: 'POST',
        data: {
            stuId: param.stuId,
            stampCd: vm.stampCd,
            comment:encodeURIComponent(comment)/*画面入力した内容エンコード*/
            //NWT 楊 2021/07/28 MANAMIRU1-711 Edit End
        },
        datatype: 'json',
        success: function (data) {
            if (data.code != 0) {
                var idx = layer.confirm(data.msg, {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        window.parent.location.reload();
                        layer.close(idx);
                    }
                });
            } else {
                var idx = layer.confirm("メッセージを送信しました", {
                    skin: 'layui-layer-molv',
                    title: '確認',
                    closeBtn: 0,
                    anim: -1,
                    btn: ['確認'],
                    yes: function () {
                        window.parent.location.reload();
                        layer.close(idx);
                    }
                });
            }
        }
    });
});
var WinHeight = $(window).height();
$(window).resize(function () {
    $('body').height(WinHeight);
});
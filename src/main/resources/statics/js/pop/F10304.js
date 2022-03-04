var index = parent.layer.getFrameIndex(window.name);
var v = getParam();
var vm = new Vue({
    el: '#app',
    data: {
        blockList: [],
        upBlock: {},
        blockDispyNm: '',
        blockPicDiv: 'schedule2.png'
    },
    mounted: function () {
        this.getInfo();
    },
    updated: function () {
        $(function () {
            //最初のものがデフォルトで選択されています
            $("#demo_select").find('option').eq(0).attr("selected", "selected");
            $('#demo_select').mobiscroll().select({
                mode: "scroller",
                display: "inline",
                lang: "zh",
                rows: 5
            });
        })
    },
    methods: {
        getInfo: function () {
            $.ajax({
                url: ctxPath + '/pop/F10304/init/' + v['blockId'],
                type: 'GET',
                datatype: 'json',
                success: function (data) {
                    if (data.code != 0) {
                        parent.layer.alert(data.msg);
                    }
                    if (data.blockList) {
                        vm.blockList = data.blockList;
                        vm.blockDispyNm = data.blockList[0].blockDispyNm;
                    }
                    if (data.upBlock) {
                        vm.upBlock = data.upBlock;
                        vm.blockPicDiv = data.upBlock.blockPicDiv;
                    }
                },
                error: function () {
                }
            })
        },
        submitFn: function () {
            var arr = $("#demo_select").find("option:selected");
            if ($("#demo_select").val() == 'その他') {
                window.location.href = "../pop/F10308.html?bigBlockId=" + v['blockId'];
            } else {
                $.ajax({
                    url: ctxPath + '/pop/F10304/submit',
                    data: {
                        'blockNm': $("#demo_select").val(),
                        'blockId': arr.attr("desc")
                    },
                    type: 'POST',
                    datatype: 'json',
                    success: function (data) {
                        if (data.code != 0) {
                            parent.layer.alert(data.msg);
                        } else {
                            var index = parent.layer.getFrameIndex(window.name);
                            // その他ブロックの登録した後、画面更新処理
                            parent.vm.otherBlock.push(data.blockDto);
                            parent.layer.close(index);
                        }
                    },
                    error: function () {
                    }
                })

            }
        },
        cancelFn: function () {
            parent.layer.close(index);
        }
    }
});

function selectChange() {
    $("#showright").html($("#demo_select").val());
}
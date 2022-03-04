/*2020/11/12 zhangminghao modify start*/
let params = getParam();
/**
 * データ選択ボックスの構成
 *
 * dataValue            コードCD
 * dataFieldName        コード値
 * selectedField        現在選択されている[コードCD]フィールド
 * defaultValue         デフォルトの[コードCD]
 * scrollDom            選択ボックスを表示するDOM
 * showDom              選択したアイテムの名前を示すDOM
 */
let dataShowConfig = {
    // 理解度
    learnLev: {
        dataValue: 'codCd',
        dataFieldName: 'codValue',
        selectedField: 'learnLevUnds',
        defaultValue: '3',
        scrollDom: '#learn_div',
        showDom: '#learnDiv'
    },
    // カテゴリ
    blockType: {
        dataValue: 'blockTypeDiv',
        dataFieldName: 'blockDispyNm',
        selectedField: 'blockTypeDiv',
        defaultValue: 'V1',
        scrollDom: '#block_type_div',
        showDom: '#blockType'
    },
    // 教科/科目
    subjtDiv: {
        dataValue: 'subjtDiv',
        dataFieldName: 'subjtNm',
        selectedField: 'subjtDiv',
        defaultValue: 'e1',
        scrollDom: '#subjt_div',
        showDom: '#subjtDiv'
    }
}
/*2020/11/12 zhangminghao modify end*/
let vm = new Vue({
    el: '.content',
    data: {
        one: {},
        memo: '',
        submitType: "0"
    },
    mounted: function () {
        this.setUp();
        let temp = window.localStorage.getItem("submitType")
        this.submitType = temp ? temp : '0';
    },
    methods: {
        setUp: function () {
            $.ajax({
                url: ctxPath + '/student/F11007/init',
                type: 'GET',
                data: {
                    // id: params.id
                    id: params.id
                },
                success: function (data) {
                    /*2020/11/12 zhangminghao modify start*/
                    if (data.code !== 0) {
                        layer.alert(data.msg);
                    }

                    if (data.one) {
                        vm.one = data.one;
                        if (data.one.blockDispyNm.indexOf(' ') > 0) {
                            vm.memo = data.one.blockDispyNm.substring(data.one.blockDispyNm.indexOf(' ') + 1);
                        }
                    }
                    // 2020/11/23 zhangminghao modify start
                    vm.one.timerTm = decodeURIComponent(params.timerTm);
                    vm.one.perfLearnTm = decodeURIComponent(params.perfLearnTm);
                    vm.one.perfYmd = decodeURIComponent(params.perfYmd);
                    vm.one.perfLearnStartTime = decodeURIComponent(params.perfLearnStartTime);
                    // 2020/11/23 zhangminghao modify end

                    if (data.colors) {
                        refreshToggleBox(dataShowConfig.learnLev, data.colors);
                    }
                    if (data.blockType) {
                        refreshToggleBox(dataShowConfig.blockType, data.blockType);
                    }
                    if (data.subjtDiv) {
                        refreshToggleBox(dataShowConfig.subjtDiv, data.subjtDiv);
                    }
                    /*2020/11/12 zhangminghao modify end*/
                }
            })
        },
        submit: function () {
            if (params.editFlag && params.editFlag === '1') {
                window.location.href = ctxPath + '/student/F11008.html';
                return;
            }
            this.saveOrUpdate();
        },
        /*2020/11/12 zhangminghao modify start*/
        blockTypeToggle: function () {
            $('.block_type_div').toggleClass('disNone');
        },
        subjtToggle: function () {
            $('.subjt_div').toggleClass('disNone');
        },
        learnToggle: function () {
            $('.choose-container').toggleClass('disNone');
        },
        /*2020/11/12 zhangminghao modify end*/
        toF11001: function () {
            window.location.href = 'F11001.html';
        },
        toF11004: function () {
            window.location.href = 'F11004.html';
        },
        update: function () {
            this.saveOrUpdate();
        },
        del: function () {
            //確認ダイアログをポップアップ表示する
            let index = layer.confirm($.format($.msg.MSGCOMN0021, "削除"), {
                title: '確認',
                closeBtn: 0,
                shadeClose: false,
                btn: ['キャンセル', '確認'],
                btn1: function () {
                    layer.close(index);
                },
                btn2: function () {
                    $.ajax({
                        url: ctxPath + "/student/F11007/delete",
                        type: 'POST',
                        data: {
                            weeklyPlanId: params.id
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data.code !== 0) {
                                showMsg(data.msg);
                            } else {
                                //登録完了確認メッセージを表示する。
                                // let index = layer.confirm($.format($.msg.MSGCOMN0022, "削除"), {
                                //     title: '確認',
                                //     closeBtn: 0,
                                //     shadeClose: false,
                                //     btn: ['確認'],
                                //     btn1: function () {
                                //         layer.close(index);
                                        window.location.reload();
                                //     }
                                // });
                            }
                        }
                    });
                }
            });
        },
        saveOrUpdate: function () {
            let memo = $('#memo').val();
            if (memo.length > 50){
                layer.alert($.format($.msg.MSGCOMD0017, "メモ", '50'));
                return;
            }
            // 2020/11/20 zhangminghao modify start
            if (vm.one.blockDispyNm === undefined){
                vm.one.blockDispyNm = $('#subjtDiv').html() + ' ';
            }
            // 2020/11/20 zhangminghao modify end
            if (vm.one.blockDispyNm.indexOf(' ') > 0){
                vm.one.blockDispyNm = vm.one.blockDispyNm.substring(0, vm.one.blockDispyNm.indexOf(' '))  + ' ' + memo.trim();
            }else {
                vm.one.blockDispyNm = vm.one.blockDispyNm + ' ' + memo.trim();
            }
            let text = '登録';
            if (params.editFlag){
                text = '更新';
            }
            let index = layer.confirm($.format($.msg.MSGCOMN0021, text), {
                closeBtn: 0,
                title: '確認',
                btn: ["キャンセル", "確認"],
                btn2: function () {
                    $.ajax({
                        url: ctxPath + '/student/F11007/submit',
                        type: 'GET',
                        data: {
                            // id: params.id
                            one: encodeURIComponent(JSON.stringify(vm.one))
                        },
                        success: function (data) {
                            // 2020/11/20 zhangminghao modify start
                            if (data.code === 0){
                                let index2 = layer.alert($.format($.msg.MSGCOMN0022, text), {
                                    closeBtn: 0,
                                    title: '確認',
                                    btn: ["確認"],
                                    btn1: function () {
                                        layer.close(index2);
                                        window.location.href = 'F11004.html'
                                    }
                                });
                                // 2020/11/20 zhangminghao modify end
                            }else {
                                layer.alert(data.msg);
                            }
                        }
                    });
                },
                btn1: function () {
                    index = layer.close(index);
                }
            });
        }
    }
});
/*2020/11/12 zhangminghao modify start*/
/**
 * デフォルトの選択を生成するための正規式
 */
function defaultSelectedRegex(selectedField, defaultValue) {
    let index;
    if (vm.one[selectedField] && !!vm.one[selectedField].trim()) {
        index = vm.one[selectedField];
    } else {
        index = defaultValue;
    }

    return new RegExp('<option value="(' + index + ')">')
}

/**
 * 選択ボックスをバインドし、
 * オプションが送信および変更されたときに選択されたコード値を取得します
 */
function bindToggleBox(scrollDom, dom, selectedField){
    $(scrollDom).mobiscroll().select({
        mode: "scroller",
        display: "inline",
        lang: "en",
        rows: 5,
        formatResult: function (value) {
            scrollSizeCommon("", $(dom));
            vm.one[selectedField] = value[0];
            let text = $(scrollDom + ' > option[value="' + value + '"]').html();
            $(dom).html(text);
        },
        onValueTap: function () {
            $(scrollDom).parent().toggleClass('disNone');
        },
        onMarkupReady: function (event) {
            scrollSizeCommon(event, "");
        }
    });
}

/**
 * オプションボックスを生成し、デフォルト値を選択します
 */
function generateToggleBox(data, dataValue, defaultSelectedRegex, dataFieldName) {
    let options = '';

    for (let d of data) {
        options += '<option value="' + d[dataValue] + '">' + d[dataFieldName] + '</option>';
    }

    return options.replace(defaultSelectedRegex, '<option value="$1" selected="selected">');
}

/**
 * 指定されたデータと[選択ボックス構成]は、指定された選択ボックスを生成します
 */
function refreshToggleBox(config, data) {
    let regExp = defaultSelectedRegex(config.selectedField, config.defaultValue);

    let options = generateToggleBox(data, config.dataValue, regExp, config.dataFieldName);
    $(config.scrollDom).html(options);

    bindToggleBox(config.scrollDom, config.showDom, config.selectedField)
}
/*2020/11/12 zhangminghao modify end*/
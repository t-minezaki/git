var csrfToken;
var ctxPath;

$.extend({
    msg: {
        'MSGCOMD0001': '{0}は入力必須項目です。',
        'MSGCOMD0002': '{0}は全角文字で入力してください。',
        'MSGCOMD0003': '{0}は半角文字で入力してください。',
        'MSGCOMD0004': '{0}は半角英数字で入力してください。',
        'MSGCOMD0005': '{0}は英大文字または数字で入力してください。',
        'MSGCOMD0006': '{0}は半角数字で入力してください。',
        'MSGCOMD0007': '{0}は全角カナ文字で入力してください。',
        'MSGCOMD0008': '{0}は半角カナ文字で入力してください。',
        'MSGCOMD0009': '{0}は{1}文字で入力してください。',
        'MSGCOMD0010': '{0}は{1}から{2}までの範囲で入力してください。',
        'MSGCOMD0011': '{0}は{1}文字以下で入力してください。',
        'MSGCOMD0012': '{0}は{1}文字で入力してください。',
        'MSGCOMD0013': '{0}は正しい日付で入力してください。',
        'MSGCOMD0014': '{0}は{1}から{2}までの範囲で入力してください。',
        'MSGCOMD0015': '{0}は整数部{1}桁、小数部{2}桁までの範囲で入力してください。',
        'MSGCOMD0016': '{0}は{1}文字以上で入力してください。',
        'MSGCOMD0017': '{0}は{1}文字以下で入力してください。',
        'MSGCOMD0018': '{0}の形式に誤りがあります。<br />半角英数字で入力されているか、空白が入っていないかなどご確認ください。<br />解消しない場合は「よくあるご質問」をご確認ください。',
        'MSGCOMD0019': '{0}は正しいクレジットカード番号を入力してください。',
        'MSGCOMD0020': '{0}は適切なURL形式で入力してください。',
        'MSGCOMD0021': '{0}行目のオリジナルCDに入力禁止文字"<x:raw>{1}</x:raw>"が含まれています。',
        'MSGCOMD0022': '{0}の入力形式が間違っています。',
        'MSGCOMN0001': 'ユーザＩＤおよびパスワードが間違っています。',
        'MSGCOMN0002': 'ユーザがロックされたので、管理者まで連絡してください。',
        'MSGCOMN0003': '当生徒が計画中のため、ただいま登録・更新・削除を止めて、しばらくお待ちください。',
        'MSGCOMN0004': '登録が完了していません。計画した予定をとうろくする場合は登録ボタンをタップしてください。',
        'MSGCOMN0005': '{0}を入力する場合、{1}を入力してください。',
        'MSGCOMN0006': '{0}が入力されていない場合、{1}を入力してください。',
        'MSGCOMN0007': '{0}は現在日以降の未来日を入力できません。正しい日付を入力してください。',
        'MSGCOMN0008': '{0}は上限日数{1}日以内で入力してください。',
        'MSGCOMN0009': '入力された{0}は{1}に存在しません。',
        'MSGCOMN0010': '入力された{0}は既に{1}に存在しています。',
        'MSGCOMN0011': '{0}を登録します。よろしいですか。',
        'MSGCOMN0012': '{0}を変更します。よろしいですか。',
        'MSGCOMN0013': '{0}を削除します。よろしいですか。',
        'MSGCOMN0014': '{0}の登録処理は完了しました。',
        'MSGCOMN0015': '{0}の変更処理は完了しました。',
        'MSGCOMN0016': '{0}の取消処理は完了しました。',
        'MSGCOMN0017': '{0}データがありません。',
        'MSGCOMN0018': '検索結果が上限件数{0}件を超えています。上限件数分のみを画面に表示します。',
        'MSGCOMN0019': '当データは他のユーザー更新中なので、リフレッシュしてください。',
        'MSGCOMN0020': 'ただいま、システム異常のため、システム管理者まで連絡してください。',
        'MSGCOMN0021': '{0}処理を行います。よろしいですか。',
        'MSGCOMN0022': '{0}処理が完了しました。',
        'MSGCOMN0023': '{0}と{1}は一致していません。',
        'MSGCOMN0024': '{0}は{1}以降の年月を入力してください。',
        'MSGCOMN0025': '{0}が{1}とは重複していますが、再度入力してください。',
        'MSGCOMN0026': '{0}を削除します。よろしいですか。',
        'MSGCOMN0027': '{0}の削除処理は完了しました。',
        'MSGCOMN0028': '{0}は少なくとも一つ選択してください。',
        'MSGCOMN0029': '{0}を選択しない場合、{1}を入力してください。',
        'MSGCOMN0030': '{0}を選択する場合、{1}が{2}とは重複しないてください。',
        'MSGCOMN0031': '{0}を選択する場合、{1}と{2}が違う時、{3}を入力しないてください。',
        'MSGCOMN0032': '検索上限件数の取得に失敗しました。',
        'MSGCOMN0033': '検索条件に該当する結果がありません。',
        'MSGCOMN0034': '指定された{0}は既に入力済みです。',
        'MSGCOMN0035': '{0}を取得できませんでした。',
        'MSGCOMN0036': '{0}の修正処理は完了しました。',
        'MSGCOMN0037': '{0},{1},{2}いずれかの項目に入力がある場合、{0},{1},{2}項目は入力必須です。',
        'MSGCOMN0038': '{0}のいずれか1項目以上に、開始日と終了日の両方を指定してください。',
        'MSGCOMN0039': '{0},{1}いずれかの項目に入力がある場合、{0},{1}項目は入力必須です。',
        'MSGCOMN0040': 'アップロードされたファイルの拡張子が{0}ではありません。',
        'MSGCOMN0041': '指定されたファイルにはデータが含まれていません。',
        'MSGCOMN0042': '指定された{0}が存在しません。',
        'MSGCOMN0043': 'ファイルサイズが{0}バイトを超えています。',
        'MSGCOMN0044': '単元{0}は生徒計画済みのため、再度調整したい場合、ウィークリープラン計画管理画面で計画取消後再調整してください。',
        'MSGCOMN0045': '{0}教科書既に計画済みのため、更新・削除できません。',
        'MSGCOMN0046': '{0}はすでに{1}に存在していますため、再度入力してください。',
        'MSGCOMN0047': '{0}を押下する場合、{1}を入力してください。',
        'MSGCOMN0048': '{0}は{1}以降の時間を入力してください。',
        'MSGCOMN0049': '{0}小分類既に計画済みのため、削除できません',
        'MSGCOMN0050': '{0}は少なくとも一つ入力してください。',
        'MSGCOMN0051': '登録が完了していません。設定を保存する場合はキャンセルをクリックの上、登録ボタンをクリックしてください。',
        'MSGCOMN0052': '設定を変更しているが登録をクリックしていない場合、変更内容は破棄されます。',
        'MSGCOMN0053': '{0}が選択している場合、{1}を選択しないてください。',
        'MSGCOMN0054': 'セッションタイムアウトが発生しました。再ログインしてください。',
        'MSGCOMN0055': '{0}の{1},{2}いずれかの項目に入力がある場合、{1},{2}項目は入力必須です。',
        'MSGCOMN0056': '登録した日時既に計画したが、再度入力してください。',
        'MSGCOMN0057': '{0}～{1}期間内、計画済みプランがあるため、登録できません、当期間の計画プランを削除後、再度登録してください。',
        'MSGCOMN0058': '旧パスワードが間違っています。',
        'MSGCOMN0059': '{0}は一致していません。',
        'MSGCOMN0060': '{0}を押下する場合、{1}と{2}のいずれか選択してください。',
        'MSGCOMN0061': '{0}既に計画済みのため、削除できません。',
        'MSGCOMN0062': '{0}は使用済みので、{1}を指定してください。',
        'MSGCOMN0063': '{0}行目の{1}が入力されていません。',
        'MSGCOMN0064': '{0}行目の　(A列)システム発番IDがDBに存在しません。',
        'MSGCOMN0065': '{0}行目の{1}がDBに存在しています。',
        'MSGCOMN0066': '{0}行目の{1}は既に登録されています。別のIDを入力してください。',
        'MSGCOMN0067': '{0}行の{1}全て入力してください。',
        'MSGCOMN0068': '{0}行の{1}がDBに存在しません。',
        'MSGCOMN0069': '下位の階層しか指定できません。現階層は{0}です',
        'MSGCOMN0070': '{0}がDBに存在しません。',
        'MSGCOMN0071': '{0}行の{1}が{2}と一致していません。',
        'MSGCOMN0072': '{0}行目の{1}が半角数字を入力してください。',
        'MSGCOMN0073': '{0}行目の{1}が{2}文字以下で入力してください。',
        'MSGCOMN0074': '{0}が指定されていません。',
        'MSGCOMN0075': '指定された{0}が存在しません。',
        'MSGCOMN0076': 'アップロードされたファイルの拡張子が{0}ではありません。',
        'MSGCOMN0077': 'ファイルサイズが{0}バイトを超えています。',
        'MSGCOMN0078': 'インポートファイルの形式が正しくありません。',
        'MSGCOMN0079': 'インポートファイル{0}行目データがDBへ登録エラー発生、データチェックをお願いします。',
        'MSGCOMN0080': 'ユーザＩＤおよびメールアドレスが間違っています。',
        'MSGCOMN0081': '{0}既に使用済みのため、更新・削除できません。',
        'MSGCOMN0082': '削除対象の組織IDは変更対象にすることはできないので、別の組織IDを入力してください。',
        'MSGCOMN0083': '入力した階層は上位組織IDの階層より下位階層しか入力できませんので、入力データの適当性をチェックしてください。',
        'MSGCOMN0084': '階層{0}以上では、入力した組織ＩＤ{1}が存在しません。',
        'MSGCOMN0085': '{0}行目の生徒-グループ関係が既に登録済みですので、該当行の内容を修正してください。',
        'MSGCOMN0086': 'ファイル数が{0}個を超えています。',
        'MSGCOMN0087': '{0}が指定されていません。{1}チェックボックスより、指定してください。',
        'MSGCOMN0088': '{0}の{1}が{2}を超えています。',
        'MSGCOMN0089': '解除連絡メール後で送信致しますので、メール内容より解除処理を続けましょう。',
        'MSGCOMN0090': '{0}既に利用されたため、再度入力してください。',
        'MSGCOMN0091': 'アップロードが失敗しました。',
        'MSGCOMN0092': '{0}は使用中のため、削除できません。',
        'MSGCOMN0093': '生徒保護者関係設定の後で、登録してください。',
        'MSGCOMN0094': 'メールを送信失敗',
        'MSGCOMN0095': '登録が完了していません、戻ってもよろしいですか。',
        'MSGCOMN0096': '{0}が指定されていません。{1}ボタンより、指定してください。',
        'MSGCOMN0097': '入力された{0}が重複するため、再度入力してください。',
        'MSGCOMN0098': '{0}行の{1}入力形式が間違っています。',
        'MSGCOMN0099': '{0}を押下する場合、{1}が選択必要です。',
        'MSGCOMN0100': '画像の幅が{0}を超えていますため、幅を調整した後、プレビューボタンより、再度確認してください。',
        'MSGCOMN0101': '教科書既に選択済みのため、{0}が更新できません。',
        'MSGCOMN0102': '生徒保護者が紐づけしていないため、ログインできません。',
        'MSGCOMN0103': '同一単元の同一時期が追加できないため、再編集してください。',
        'MSGCOMN0104': '{0}を選択する場合、{1}を入力してください。',
        'MSGCOMN0105': '{0}は今日以降の日付を指定してください。',
        'MSGCOMN0107': '{0}を選択する場合、{1}を入力してください。',
        'MSGCOMN0108': '申込期間が終了しています。<br />お手数お掛け致しますが<br />本イベントへの申込やお問い合わせは<br />直接教室へご連絡をお願い致します。',
        'MSGCOMN0109': '{0}既に公開されたため、削除できません。',
        'MSGCOMN0110': '{0}と{1}の間隔は{2}の倍数に設定してください。',
        'MSGCOMN0111': '{0}既に申込されたため、削除・変更できません。',
        'MSGCOMN0112': '申し込み開始日は{0}です。<br />{0}以降に申し込んでください。',
        'MSGCOMN0113': '本イベントは申込済です。<br />右下のメニューの「その他」→<br />「マイページ」の「面談・イベント予約確認」<br />からご覧ください。',
        'MSGCOMN0114': '本当に{0}されますがよろしいですか',
        'MSGCOMN0115': '{0}既に予約済みのため、登録できません。',
        'MSGCOMN0117': '変更可能期間が過ぎているため、変更・キャンセルの場合、直接教室へご連絡をお願い致します。',
        'MSGCOMN0118': '{0}メールが送信完了しました。',
        'MSGCOMN0116': '以下の対象にメールを送信します。よろしければメール送信をクリックしてください。',
        'MSGCOMN0119': 'この申込をキャンセルします一度キャンセルした申込は、もとに戻すことが出来ません。',
        'MSGCOMN0120': '{0}が指定されていません。',
        'MSGCOMN0121': '{0}は予約が入ったため、本画面での修正・再送信できません。',
        'MSGCOMN0122': 'この利用者IDは既に登録されています。別のIDを入力してください。',
        'MSGCOMN0123': '{0}データが登録されていません。',
        'MSGCOMN0124': '{0}は{1}以降の日付を指定してください。',
        'MSGCOMN0125': '３か月以内の期間を指定してください。',
        'MSGCOMN0126': 'いずれか機能の権限が付与されていません。',
        'MSGCOMN0127': '当会員は既に{0}しているか、前回{1}の登録がされていません。',
        'MSGCOMN0128': '入力された内容はまだ反映されておりません。<br/>内容を反映したい場合は「保存せず進む」ボタンをクリックしてください。',
        'MSGCOMN0129': '全員の｢{0}｣に以下の内容入力します、<br/>よろしいですか？',
        'MSGCOMN0130': '入力時間は｛5,10,15…｝など5分間隔にしてください。;',
        'MSGCOMN0131': '{0}未対応に戻します、よろしいですか。',
        'MSGCOMN0132': '保護者に確認連絡を通知しますが、よろしいですか。',
        'MSGCOMN0133': '該当遅刻・欠席連絡はもう確認しました。',
        'MSGCOMN0134': '生徒名を入力してください。',
        'MSGCOMN0135': '入力内容を放棄します、よろしいですか。',
        'MSGCOMN0136': '{0}の日付を指定してください。',
        'MSGCOMN0137': '{0}が指定していません。少なくともひとりを指定してください。',
        'MSGCOMN0138': '{0}が指定していません。少なくとも一つを指定してください。',
        'MSGCOMN0139': '先生機能既定値以外の機能が選択されました、取消してください。',
        'MSGCOMN0140': '該当生徒の下校日は前回の登校日と同じ日付を指定してください。',
        'MSGCOMN0141': '該当生徒のステータス間違っています、正しい{0}をも一回指定してください。',
        'MSGCOMN0142': 'データを{0}の{1}が失敗しました。',
        'MSGCOMN0143': '{0}を指定してください。',
        'MSGCOMN0144': '選択した{0}が画面に存在しています。',
        'MSGCOMN0145': '入力した英語氏名は｛0｝ではありません。',
        'MSGCOMN0146': '{0}を{1}します。よろしいですか。',
        'MSGCOMN0147': '{0}の入力時間は標準時間の最大値より大きなので、ご確認してください。',
        'MSGCOMN0148': '不合格ライン＜中間ライン＜合格ラインとなるように設定してください。',
        'MSGCOMN0149': 'すみません、先生は「その他」画面の利用<br/>権限がありません。',
        'MSGCOMN0150': '一部の生徒は関連付けの保護者いないため、該当生徒たちの保護者を設定してください。',
        'MSGCOMN0151': '該当生徒は保護者が設定されていません。<br/>運用管理の「ユーザー管理」から生徒と保護者を紐付けた後に操作してください。',
        'MSGCOMN0152': '{0}を選択してください。',
        'MSGCOMN0153': '{0}また{1}いずれの内容を入力してください。',
        'MSGCOMN0158': '赤色対象の方が該当イベント既に読みました、該当方達を除き、送信でよろしいですか。',
        'MSGCOMN0159': '該当面談記録が実施済でした、代理登録できません。',
        'MSGCOMN0160': '生徒先生関係設定の後で、登録してください。',
        'MSGCOMN0162': '{0}は英大文字、小文字と数字を入力してください。',
        'MSGCOMN0169': '追加したい組織{0}は選択済みです。',
        'MSGCOMN0170': '{0}は半角非0数字を入力してください。',
        'MSGCOMN0171': '選択された生徒の付与ポイントを「0」にリセットします。よろしいでしょうか。',
        'MSGCOMN0172': '存在しない生徒IDが{0}件あります。{1},存在していない件数, 存在していない生徒ID',
        'MSGCOMN0173': '{0}を確認したことを送信しました。',
        'MSGCOMN0174': '以下の対象を再通知します。よろしければ再通知をクリックしてください。',
        'MSGCOMN0175': '{0}は少なくとも{1}選択してください。',
        'MSGCOMN0176': '当生徒は既に{0}しているか、前回{1}の登録がされていません。',
        'MSGCOMN0177': '{0}を復元します。よろしいでしょうか。',
        'MSGCOMN0178': '表示件数が多すぎます。<br>検索したい教室の条件を入力してください。',
        'MSGCOMN0179': 'ポイントがマイナスになりますので、改めて設定した数値をご確認ください',
        'MSGCOMN0180': '{0}は半角整数を入力してください。',
        'MSGCOMN0182': '{0}が存在している。',
        'MSGCOMN0184': '{0}入力してください。',
        'MSGCOMN0186': '{0}が入力されていません。',
        'MSGCOMN0187': 'ご指定のユーザーは、IDとパスワードの更新ができません。',
        'MSGCOMN0189': 'お子さまのアカウントとなりますので、保護者さま用のアカウントにてログインしてください。'
        // add at 2021/09/06 for V9.02 by NWT wen START
        , 'MSGCOMN0192': '{0}を取得できませんでした、{1}が失敗しました。'
        // add at 2021/09/06 for V9.02 by NWT wen END
    },
    format: function (source, params) {
        if (arguments.length === 1) {
            return function () {
                var args = $.makeArray(arguments);
                args.unshift(source);
                return $.format.apply(this, args);
            };
        }
        if (params === undefined) {
            return source;
        }
        if (arguments.length > 2 && params.constructor !== Array) {
            params = $.makeArray(arguments).slice(1);
        }
        if (params.constructor !== Array) {
            params = [params];
        }
        $.each(params, function (i, n) {
            source = source.replace(new RegExp("\\{" + i + "\\}", "g"), function () {
                return n;
            });
        });
        return source;
    }
});

/**
 * ログインデバイスを取得
 * 2019/11/13-yang
 */
$(function getEquipment() {
    if (/AppleWebKit.*mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        $(".header_logo").css("width", "100px").css("height", "20px").css("margin-top", "10px");
    }
})

function getDay(dateStr) {
    if (dateStr == "") {

    } else {
        var day = new Date(dateStr).getDay(),
            text = "";
        switch (day) {
            case 0:
                text = "日";
                break;
            case 1:
                text = "月";
                break;
            case 2:
                text = "火";
                break;
            case 3:
                text = "水";
                break;
            case 4:
                text = "木";
                break;
            case 5:
                text = "金";
                break;
            case 6:
                text = "土";
                break;
        }
        return text;
    }
}

function scrollSizeCommon(event, num) {
    if (event) {
        $(event).find("div.dw-i").each(function (index, element) {
            if ($(this).parent().attr("aria-selected") && ($(this).parent().attr("aria-selected") == true || $(this).parent().attr("aria-selected") == 'true')) {
                $(this).css("font-size", "18px");
                if (index > 1 && index < $(event).find("div.dw-i").length - 2) {
                    $(event).find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index - 2).css("font-size", "12px");
                    $(event).find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == 1) {
                    $(event).find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == 0) {
                    $(event).find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == $(event).find("div.dw-i").length - 2) {
                    $(event).find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index - 2).css("font-size", "12px");
                }
                if (index == $(event).find("div.dw-i").length - 1) {
                    $(event).find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(event).find("div.dw-i").eq(index - 2).css("font-size", "12px");
                }
            }
        });
    }
    if (num) {
        $(num).next().find("div.dw-i").each(function (index, element) {
            if ($(this).parent().attr("aria-selected") && ($(this).parent().attr("aria-selected") == true || $(this).parent().attr("aria-selected") == 'true')) {
                $(this).css("font-size", "18px");
                if (index > 1 && index < $(num).next().find("div.dw-i").length - 2) {
                    $(num).next().find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index - 2).css("font-size", "12px");
                    $(num).next().find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == 1) {
                    $(num).next().find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == 0) {
                    $(num).next().find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index + 2).css("font-size", "12px");
                }
                if (index == $(num).next().find("div.dw-i").length - 2) {
                    $(num).next().find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index + 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index - 2).css("font-size", "12px");
                }
                if (index == $(num).next().find("div.dw-i").length - 1) {
                    $(num).next().find("div.dw-i").eq(index - 1).css("font-size", "15px");
                    $(num).next().find("div.dw-i").eq(index - 2).css("font-size", "12px");
                }
            }
        });
    }
}

/**
 * パラメータを取得する
 */
function getParam() {
    var param = {}
    var item = [];
    if (location.search == "") {
        return param;
    }

    var query = location.search.substring(1);
    var list = query.split('&');
    for (var i = 0; i < list.length; i++) {
        item = list[i].split('=');
        param[item[0]] = item[1];
    }

    return param;
}

function handleFileName(fileName) {

    var filename = fileName.slice(fileName.lastIndexOf("\\") + 1);
    filename = filename.slice(filename.lastIndexOf("/") + 1);
    var newStr = "";
    for (var i = 0; i < filename.length; i++) {
        if (!(i >= filename.lastIndexOf(".") - 17 && i < filename.lastIndexOf("."))) {
            newStr += filename.charAt(i);
        }
    }
    return newStr;
}

/**
 * メッセージを表示する
 */
function showMsg(message) {
    $(".topMessage").addClass("hasHeight");
    $(".topMessage").html(message);
    $(".topMessage").css("display", "block")
    setTimeout(function () {
        $(".topMessage").slideUp(1000)
        setTimeout(function () {
            $(".topMessage").removeClass("hasHeight");
            $(".topMessage").html("");
        }, 1000)
    }, 600 * 3000)
}

function popMsg(message) {
    var popMsg = layer.alert(message, {
        title: '確認',
        closeBtn: 0,
        btn: ["確認"],
        btn1: function () {
            popMsg = layer.close(popMsg);
        }
    });
}

function openUrl(url) {
    if (url == "logout") {
        layer.confirm("ログアウトしますか？", {
            btn: ['キャンセル', '確認'], title: "確認", btn2: function () {
                window.top.location.href = ctxPath + "/logout";
                return false;
            }
        });
    } else {
        layer.confirm($.msg.MSGCOMN0004, {}, function () {
            window.top.location.href = ctxPath + "/student/" + url;
        });
    }
}

// ajax
function getTopWinow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
}

(function ($) {
    var loadingLoop;
    $.ajaxSetup({
        beforeSend: function (XHR) {
            if ($("#message") != null) {
                $("#message").hide();
            }

            if (csrfToken) {
                XHR.setRequestHeader("csrfToken", csrfToken);
            }

            loadingLoop = layer.load(1, {
                shade: [0.5, '#CCC']
            });
        },
        complete: function (xhr, status) {
            layer.close(loadingLoop);
            var sessionStatus = xhr.getResponseHeader('session-status');
            if (sessionStatus == 'timeout') {
                var top = getTopWinow();
                layer.alert("セッションタイムアウトが発生しました。再ログインしてください。", function () {
                    top.location.href = ctxPath + '/login/' + getCookie("brandcd");
                });
            }
        },
        statusCode: {
            401: function (xhr) {
                var top = getTopWinow();
                layer.alert("セッションタイムアウトが発生しました。再ログインしてください。", function () {
                    top.location.href = ctxPath + '/login/' + getCookie("brandcd");
                });
            }
        }
    });

})(jQuery);
$(function () {
    /**
     * メッセージをクリアする
     */
    if (window.location.href.indexOf("login") < 0) {
        // $("input").keyup(function () {
        //     $("input[type=text]").css("background-color", "white");
        //     $("#message").hide();
        // });
        $("input").focusin(function () {
            if ($(this).attr('type') === 'text' || $(this).attr('type') === 'password') {
                $(this).css("background-color", "rgb(232, 240, 254)");
                $("#message").hide();
            }
        });
        $("input").focusout(function () {
            if ($(this).attr('type') === 'text' || $(this).attr('type') === 'password') {
                $(this).css("background-color", "white");
                $("#message").hide();
            }
        });
        // $("input").change(function () {
        //     $("input[type=text]").css("background-color", "white");
        //     $("#message").hide();
        // });
        $("select").change(function () {
            $("#message").hide();
            $("input").css("background-color", "white");
            $("input[type='radio']").css("background-color", "transparent");
            // $("input[type='radio']::after").css("background-color", "#FFF");
            $("body").append("<style>input[type = 'radio'].newtui-radio-one::after{background-color:#FFF}</style>");
        });
        if (getCookie("key") === 'PUSHAPI'){
            $(".logout").css("display","none");
            $(".img_loginout").css("display","none");
        }
    }
})

function storageTest(storage) {
    if (storage) {
        try {
            storage.setItem("test", "value");
            storage.removeItem("test");
            return false;
        } catch (e) {
            return false;
        }
    } else {
        return false;
    }
}

/**
 * Cookieを取得する
 */
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = document.cookie.length;
            }
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return "";
}

/**
 * author:hjx
 * demo: new Date().Format("yyyyMMdd")    new Date().Format("yyyy-MM-dd HH:mm:SS:ss")
 * @param fmt
 * @returns {*}
 * @constructor
 */
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

/**
 *<p>ターゲットコントロールの高さを設定する</p>
 *
 * @param objId         ターゲットID
 * @param containerId   ターゲットの親コンテナID
 * @param ...           ターゲットピアコントロールID
 */
function setTargetControlHeight(objId, containerId) {
    var length = arguments.length;
    var height = 0;
    for (var i = 2; i < length; i++) {
        height += $("#" + arguments[i]).outerHeight(true);
    }
    var parentHeight = $("#" + containerId).height();
    $("#" + objId).css("height", parentHeight - height);
}
/**
 *<p>残りの高さを取得</p>
 *
 * @param containerId   ターゲットの親コンテナID
 * @param ...           ターゲットピアコントロールID
 */
function getRemainingHeight(containerId) {
    var length = arguments.length;
    var height = 0;
    for (var i = 1; i < length; i++) {
        height += $("#" + arguments[i]).outerHeight(true);
    }
    var parentHeight = $("#" + containerId).height();
    return (parentHeight - height);
}

/**
 * マラミルからLEADへSSOを作成する。
 * 2020/03/10 yang
 */
function toUrl() {
    window.location.href='https://gsignup.learningpark.jp/enrollment/login/TC?external_browser=1';
    // window.location.href='https://gsignup.learningpark.jp/enrollment/login/TC?external_browser=1';
    // var mstCodDEntity;
    // var url;
    // var paramReqid;
    // var loginId;
    // var loginPw;
    // var corpCode;
    // var lang;
    // var role;
    // var usrId;
    // var usrPw;
    // var mstUsrFirtPwEntity;
    // $.ajax({
    //     url: ctxPath + '/toUrl',
    //     type: 'POST',
    //     success: function (data) {
    //         if (data.code == 0) {
    //             mstCodDEntity = data.mstCodDEntity;
    //             mstUsrFirtPwEntity = data.mstUsrFirtPwEntity;
    //             url = mstCodDEntity.codValue;
    //             paramReqid = mstCodDEntity.codValue2;
    //             loginId = mstCodDEntity.codValue3;
    //             loginPw = mstCodDEntity.codValue4;
    //             corpCode = mstCodDEntity.codValue5;
    //             lang = mstCodDEntity.codCont;
    //             role = mstUsrFirtPwEntity.roleDiv;
    //             usrId = mstUsrFirtPwEntity.usrId;
    //             usrPw = mstUsrFirtPwEntity.usrFstPassword;
    //             window.location.href = url + '?' + paramReqid + '&' + loginId + '=' + usrId + '&' + loginPw + '=' + usrPw + '&' + corpCode + '&' + lang;
    //         } else {
    //             layer.alert(data.msg);
    //         }
    //     }
    // })
}

/**
 *16進数の色の値のrgbを取得します。
 *
 * @param sColor
 * @returns {*}
 */
function colorRgb(sColor){
    //16進数の色値の正規表現
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    // 16進数の色であれば
    if (sColor && reg.test(sColor)) {
        if (sColor.length === 4) {
            var sColorNew = "#";
            for (var i=1; i<4; i+=1) {
                sColorNew += sColor.slice(i, i+1).concat(sColor.slice(i, i+1));
            }
            sColor = sColorNew;
        }
        //六桁の色の値を処理します。
        var sColorChange = [];
        for (var i = 1; i < 7; i += 2) {
            sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
        }
        return "" + sColorChange.join(",") + ",0.6";
    }
    return sColor;
}

/**
 * 電話番号のフォーマット
 * @param telnum
 * @returns {string}
 */
function getDisplayTelNumber(telnum) {
    if (typeof telnum !== "string") {
        return '';
    }
    return telnum.replace(/(\d{2,3})(\d{4})(\d{4})/, '$1-$2-$3');
}
/**
 * ブランド取得API
 *
 * @param orgId ログイン者に所属する組織（教室）コード
 * @param type
 */
function getBrandCd(orgId,type) {
    var params = {
        "orgId":orgId,
        "type":type
    };
    $.ajax({
        url:ctxPath + '/PUSHAPI',
        type: "POST",
        dataType:'json',
        contentType: "application/json",
        data:JSON.stringify(params),
        success:function (data) {
            if (data.code == 200){
                window.location.href = "../" + data.URL;
            }
        }
    })
}

function JumpType() {
    this.SELF = 'self';
    this.PARENT = 'parent';
    this.CLOSE = 'close';
}

var jumpType = new JumpType();

/**
 * 通知プッシュオブジェクト
 * @param type
 * @param url
 * @constructor
 */
function Jumper(type, url) {
    this.type = type;
    this.url = url;
    this.jump = function () {
        switch (this.type) {
            case jumpType.SELF:
                window.location.href = url;
                break;
            case jumpType.PARENT:
                parent.window.location.href = url;
                break;
            case jumpType.CLOSE:
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                window.parent.location.reload();
                break;
            default:
                return;
        }
    }
}
/**
 * メッセージ送信API(messageSend)
 * @param 送信開始時間
 */
function sendMessage(sendData,changePage) {
    var params = {
        "data":sendData,
        "type":'sendMessage'
    };
    $.ajax({
        url:ctxPath + '/PUSHAPI',
        type: "POST",
        dataType:'json',
        contentType: "application/json",
        data:JSON.stringify(params),
        success:function (data) {
            if (changePage) {
                // changePage();
                changePage.jump();
            }

        }
    })
}

/**
 * 送信失敗データの取得(errorDataGet)
 * @param 送信時間区間の開始時間
 * @param 送信時間区間の終了時間
 */
function getErrorData(sendTime,endTime,msgId){
    sendTime = new Date(sendTime);
    endTime = new Date(endTime);
    var params = {
        "startDateTime":sendTime.getTime(),
        "endDateTime":endTime.getTime(),
        "type":'getErrorData'
    };
    $.ajax({
        url:ctxPath + '/PUSHAPI',
        type: "POST",
        dataType:'json',
        contentType: "application/json",
        data:JSON.stringify(params),
        async : false,
        success:function (data) {
            if (data.code == 200){
                doInsert(sendTime,endTime,data,msgId);
            }
        }
    })
}

/**
 * 返却した送信失敗データをプッシュ失敗データに登録
 * @param 送信時間区間の開始時間
 * @param 送信時間区間の終了時間
 */
function doInsert(pubPlanStartDt,pubPlanEndDt,result,msgId) {
    var pushErrDatEntities = [];
    var list = [];
    if (result.data.errorDataList){
        var returnData = result.data.errorDataList.detail;
        for (var i = 0; i < returnData.length; i++) {
            list.push(JSON.parse(returnData[i].params).msgId);
        }
        var count = getRepeatNum(list);
        for (var i = 0; i < returnData.length; i++) {
            var pushErrDatEntity = {
                deviceId:returnData[i].deviceId,
                msgTypeDiv:JSON.parse(returnData[i].params).msgType,
                msgId:JSON.parse(returnData[i].params).msgId,
                errDatCnt:count[JSON.parse(returnData[i].params).msgId],
                sendStatTime:returnData[i].startTime,
                exceptionInfo:returnData[i].exceptionInfo,
                msgTitle:returnData[i].title,
                msgTxt:returnData[i].comment,
                priority:returnData[i].priority,
                sendId:returnData[i].receiverId,
                errCont:returnData[i].errCont,
                otherPara:returnData[i].otherPara,
                stuId:JSON.parse(returnData[i].priParams).stuId
            };
            pushErrDatEntities.push(pushErrDatEntity);
        }
    }
    if (result.code === 200){
        var params = {
            "result":JSON.stringify(pushErrDatEntities),
            "msgId":msgId,
            "type":'insert'
        };
        $.ajax({
            url:ctxPath + '/PUSHAPI',
            type: "POST",
            dataType:'json',
            contentType: "application/json",
            data:JSON.stringify(params),
            traditional: true,
            async:false,
            success:function (data) {

            }
        })
    }
}
function getRepeatNum(arr){
    return arr.reduce(function(prev,next){
        prev[next] = (prev[next] + 1) || 1;
        return prev;
    },{});
}

/**
 * 一致したすべての文字列を正規表現で置き換える
 * @param regString     正規表現文字列
 * @param replacement   置換文字列
 * @returns {string}    置き換えられた文字列
 */
String.prototype.replaceAll = function (regString, replacement){
    var reg = new RegExp(regString, 'g');
    return this.replace(reg, replacement);
};
/**
 * 組織名が長さを超えて表示修正されます
 * 2020/11/05
 * Modify yang
 */
function getOrgName(orgName) {
    // if (orgName!=undefined){
    //     orgName = orgName.length >= 15?orgName.substring(0,14) + '...':orgName;
    // }
    return orgName;
}
//2020/11/26 liyuhuan add start
function IsPicture(path) {
    {
        var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|.svg|"
        if(path.indexOf(".")>-1)
        {
            var p = path.lastIndexOf(".");
            var strPostfix=path.substring(p,path.length) + '|';
            strPostfix = strPostfix.toLowerCase();
            if(strFilter.indexOf(strPostfix)>-1)
            {
                return true;
            }
        }
        return false;
    }
}

function previewImg(src) {
    /* 2020/12/09 liguangxin add start*/
    var imgHtml = "<div class=\"box\" >\n" +
        "                <img id='preview' src='" + decodeURIComponent(src).replace(/\%20/g," ") + "' />" +
        "     </div>";
    img = new Image();
    img.src= decodeURIComponent(src).replace(/\%20/g," ");
    var loadingLoop = layer.load(1, {
        shade: [0.5, '#CCC']
    });
    img.onload = function () {
        if(img.complete){
            layer.close(loadingLoop);
        }
        var w = document.body.scrollWidth*0.8;
        var h = document.body.scrollHeight*0.8;
        var tempWidth;
        var tempHeight;
        if(img.naturalWidth/img.naturalHeight >= w/h){
            if (img.naturalWidth> w){
                tempWidth = w;
                tempHeight = (img.naturalHeight * w)/img.naturalWidth;
            } else{
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }else {
            if (img.naturalHeight>h){
                tempHeight = h;
                tempWidth = (img.naturalWidth * h)/img.naturalHeight;
            } else {
                tempWidth = img.naturalWidth;
                tempHeight = img.naturalHeight;
            }
        }
        var height = tempHeight + "px";
        var width = tempWidth + "px";
        layer.open({
            id:'previewBox',
            type: 1,
            offset: 'auto',
            closeBtn:1,
            area: [width,height],
            shadeClose: false,
            scrollbar: false,
            title: "",
            content: imgHtml,
            success: function (layero, index) {
                /* 2021/08/13 manamiru1-584 cuikl add start */
                $("#previewBox").css('border','1px solid #8f8f8f')
                /* 2021/08/13 manamiru1-584 cuikl add end */
                //NWT　楊　2021/08/18　MANAMIRU1-584　Edit　Start
                function point2D(x,y){
                    return {x : x,y : y};
                }
                var reqAnimationFrame = (function () {
                    return window[Hammer.prefixed(window, 'requestAnimationFrame')] || function (callback) {
                        window.setTimeout(callback, 1000 / 60);
                    };
                })();
                var tMatrix = [1,0,0,1,0,0]
                var startY = (document.body.scrollHeight - tempHeight) / 2;
                var initScale = 1;
                var el =  document.getElementById("preview");
                var mc = new Hammer.Manager(el)
                var ticking = false
                var poscenter = point2D(0,0);
                var duration = '';
                var lastTranslate = point2D(0,0);
                var lastcenter= point2D(el.offsetWidth/2,el.offsetWidth * (tempHeight/tempWidth) /2)
                var center = lastcenter
                mc.add(new Hammer.Pan({ threshold: 0, pointers: 1 }))
                mc.add(new Hammer.Pinch({ threshold: 0 }))
                mc.add( new Hammer.Tap({ event: 'doubletap', taps: 2 }) );
                mc.on("panmove", onPan);
                mc.on("panstart",onPanStart)
                mc.on("pinchmove", onPinch);
                mc.on("pinchstart",onPinchStart);
                mc.on("doubletap",onDoubleTap);

                function onPanStart(ev){
                    lastTranslate = point2D(tMatrix[4],tMatrix[5])
                }
                function onPan(ev){
                    duration = ''
                    el.className = ''
                    var nowScale = tMatrix[0];
                    var nMaxCrosswise = Math.abs(nowScale * tempWidth - tempWidth) / 2;
                    var nMaxPortrait = Math.abs(nowScale * tempHeight - tempHeight) / 2;
                    var offsetX = lastTranslate.x + ev.deltaX;
                    var offsetY = lastTranslate.y + ev.deltaY;
                    if (offsetX < 0){
                        offsetX =  Math.abs(offsetX) > nMaxCrosswise ? -nMaxCrosswise:offsetX;
                    }else{
                        offsetX =  Math.abs(offsetX) > nMaxCrosswise ? nMaxCrosswise:offsetX;
                    }
                    if (offsetY < 0){
                        offsetY =  Math.abs(offsetY) > nMaxPortrait ? -nMaxPortrait:offsetY;
                    }else{
                        offsetY =  Math.abs(offsetY) > nMaxPortrait ? nMaxPortrait:offsetY;
                    }
                    tMatrix[4] = offsetX
                    tMatrix[5] = offsetY
                    requestElementUpdate('onpan');
                }
                function onPinchStart(ev){
                    duration = '';
                    lastTranslate = point2D(tMatrix[4],tMatrix[5])
                    initScale = tMatrix[0] || 1;
                    poscenter = point2D(ev.center.x, ev.center.y)
                    lastcenter = point2D(center.x + lastTranslate.x,center.y + lastTranslate.y)
                    poscenter = point2D(ev.center.x - lastcenter.x, ev.center.y - lastcenter.y - startY)
                    requestElementUpdate('onpinchStart');
                }
                function onPinch(ev){
                    var nowScale = tMatrix[0] = tMatrix[3] = initScale * ev.scale;
                    var composscal = (1 - ev.scale)
                    //NWT　楊　2021/08/26　MANAMIRU1-767　Edit　Start
                    if (nowScale < 1){
                        tMatrix[0] = tMatrix[3] = 1;
                        tMatrix[4] = tMatrix[5] = 0;
                    }else {
                        tMatrix[4] = (1 - ev.scale) * poscenter.x + lastTranslate.x
                        tMatrix[5] = (1 - ev.scale) * poscenter.y + lastTranslate.y;
                    }
                    requestElementUpdate('onpinch');
                    onPan(ev);
                    //NWT　楊　2021/08/26　MANAMIRU1-767　Edit　End
                }
                function onDoubleTap(ev){
                    duration = ".3s ease all";
                    var nowScale = tMatrix[0];
                    if(nowScale != 1 || tMatrix[4]!= 0){
                        tMatrix[0] = tMatrix[3] = 1;
                        tMatrix[4] = tMatrix[5] = 0;
                    }else{
                        var pointer = ev.center
                        var scale = 2
                        tMatrix[0] = tMatrix[3] = scale
                        tMatrix[4] = (1 - scale) * ( pointer.x - center.x)
                        tMatrix[5] = (1 - scale) * ( pointer.y - startY - center.y)
                    }
                    requestElementUpdate('doubleTap');
                }

                function updateElementTransform() {
                    el.style.transition = duration
                    var tmp = tMatrix.join(',')
                    el.style.transform = 'matrix(' + tmp + ')';
                    ticking = false;
                }
                function requestElementUpdate() {
                    if(!ticking) {
                        reqAnimationFrame(updateElementTransform);
                        ticking = true;
                    }
                }
                requestElementUpdate();
                //NWT　楊　2021/08/18　MANAMIRU1-　Edit　End
            }
        });
    };
    return false;
    /* 2020/12/09 liguangxin add end*/
}
//2020/11/26 liyuhuan add end
function pdfClick() {
    // var path = decodeURIComponent($("#showFile").attr("href"));
    var path = window.location.protocol + '//' + location.host + ctxPath + $("#showFile").attr("title").substring(2,$("#showFile").attr("title").length).replace(/\\/g,"/");
    // window.location.href = path;
    path = encodeURIComponent(path).replace(/\%20/g, " ");
    layer.open({
        id: 'pdfpreview',
        type: 2,
        title: false,
        skin: 'layui-layer-molv123',
        shade: 0.1,
        closeBtn: 1,
        shadeClose: false,
        area: ['100%', '100%'],
        fixed: true,
        resize: false,
        content: [ctxPath + "/plugins/pdfjs-2.5.207-es5-dist/web/viewer.html?file=" + path],
        success: function (layero, index) {
            layero[0].childNodes[1].childNodes[0].removeAttribute('href');
            layero[0].childNodes[1].classList.add('cursorStyle');
            layero[0].childNodes[1].childNodes[0].classList.remove('layui-layer-close2');
            layero[0].childNodes[1].childNodes[0].classList.add('layui-layer-close1');
        },
        end: function () {
            $("#button#secondaryToolbarToggle").css("margin-right","7vw");
        }
    });
}
//PDFプレビューおよびlinkジャンプ 2020/11/23 modify yang end--

// 「確認しました」ボタン押下
function noticeConfirm() {
    $.ajax({
        url: ctxPath + '/guard/F30113/noticeConfirm?guardReadingId=' + guardReadingId,
        type: 'POST',
        datatype: "json",
        success: function () {
            let idx = layer.confirm('配信情報を確認したことを送信しました。', {
                skin: 'layui-layer-molv out',
                title: '確認',
                closeBtn: 0,
                anim: -1,
                btn: ['確認'],
                yes: function () {
                    // 2020/11/25 modify zmh start
                    // POP画面で「確認」押下時、親画面へ戻る。
                    $('#btn_confirm').hide()
                    layer.close(idx);
                    // 2020/11/25 modify zmh end
                }
            })
        }
    });
}

function getFileSuffix(file){
    let idx = file.name.lastIndexOf(".");
    if (idx != -1){
        let ext = file.name.substr(idx + 1).toUpperCase();
        return ext.toLowerCase();
    }else {
        return '';
    }
}


//PDFプレビューおよびlinkジャンプ 2020/11/23
function pdfClick(ele) {
    // var path = decodeURIComponent($("#showFile").attr("href"));
    var path = window.location.protocol + '//' + location.host + ctxPath + $(ele).attr("title").substring(2,$(ele).attr("title").length).replace(/\\/g,"/");
    // window.location.href = path;
    path = encodeURIComponent(path).replace(/\%20/g, " ");
    layer.open({
        id: 'pdfpreview',
        type: 2,
        title: false,
        skin: 'layui-layer-molv123',
        shade: 0.1,
        closeBtn: 1,
        shadeClose: false,
        area: ['100%', '100%'],
        fixed: true,
        resize: false,
        content: [ctxPath + "/plugins/pdfjs-2.5.207-es5-dist/web/viewer.html?file=" + path],
        success: function (layero, index) {
            layero[0].childNodes[1].childNodes[0].removeAttribute('href');
            layero[0].childNodes[1].classList.add('cursorStyle');
            layero[0].childNodes[1].childNodes[0].classList.remove('layui-layer-close2');
            layero[0].childNodes[1].childNodes[0].classList.add('layui-layer-close1');
        },
        end: function () {
            $("#button#secondaryToolbarToggle").css("margin-right","7vw");
        }
    });
}
/* 2021/08/13 manamiru1-584 cuikl delete start */
/* 2021/08/13 manamiru1-584 cuikl delete end */

//2020/11/26 liyuhuan add end
function IsPicture(path) {
    {
        var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|.svg|"
        if(path.indexOf(".")>-1)
        {
            var p = path.lastIndexOf(".");
            var strPostfix=path.substring(p,path.length) + '|';
            strPostfix = strPostfix.toLowerCase();
            if(strFilter.indexOf(strPostfix)>-1)
            {
                return true;
            }
        }
        return false;
    }
}
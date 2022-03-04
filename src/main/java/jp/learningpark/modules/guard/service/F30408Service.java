/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;


import jp.learningpark.modules.guard.dto.F30408Dto;

import java.util.List;

/**
 * <p>塾からのイベント情報詳細画面</p >
 */
public interface F30408Service {

    /**
     * 初期化  ページ
     * @param eventId イベントID
     * @return
     */
    List<F30408Dto> getEventNews(Integer eventId, String guardId);
}

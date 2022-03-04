/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/31 : wq: 新規<br />
 * @version 1.0
 */
public interface ConstraintService {

    Integer getMaxStuId();
    Integer getMaxGuardId();
    Integer getMaxMentorId();
    Integer getMaxManagerId();
}

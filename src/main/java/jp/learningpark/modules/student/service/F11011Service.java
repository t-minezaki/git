package jp.learningpark.modules.student.service;

import jp.learningpark.modules.student.dto.F11011Dto;

public interface F11011Service {

    F11011Dto init(Integer messageId);
    // 2020/11/12 zhangminghao modify start
    /**
     * 閲覧状況を更新する。
     */
    void noticeConfirm(Integer messageId);
    // 2020/11/12 zhangminghao modify end
}

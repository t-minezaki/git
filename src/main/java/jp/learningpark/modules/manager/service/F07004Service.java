/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F07004Dto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>教科追加・編集画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/04/04: xiong: 新規<br />
 * @version 1.0
 */
public interface F07004Service {

    /**
     * 登録ボタン押下
     * @param f07004Dto 画面内容
     * @param file1     画面．表示用画像
     * @param file2     画面．ボタン用画像
     * @return subjtInformation
     * @throws IOException
     */
    R setSubjtInfo(F07004Dto f07004Dto, MultipartFile file1, MultipartFile file2)throws IOException;
}

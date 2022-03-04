package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>08017 テンプレート新規編集画面Service</p >
 *
 * @author NWT : cuikailin <br />
 * 変更履歴 <br />
 * 2021/01/27 : cuikailin: 新規<br />
 * @version 1.0
 */
public interface F08025Service {
    /**
     * <p>IDによるテンプレートの取得</p>
     *
     * @param id テンプレート id
     * @return
     */
    R getTmplateById(Integer id);

    /**
     * <p>テンプレートの保存</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file          添付ファイルパス
     * @return
     * @throws IOException
     */
    R addTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePath) throws IOException;

    /**
     * <p>テンプレートの更新</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file          添付ファイルパス
     * @return
     */
    R updateTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePath) throws UnsupportedEncodingException;
}

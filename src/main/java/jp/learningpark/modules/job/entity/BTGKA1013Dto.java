package jp.learningpark.modules.job.entity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>作用描述</p>
 * <p>详细描述</p>
 * <p></p>
 *
 * @author NWT : LiGX <br />
 * 変更履歴 <br />
 * 2021/10/15 : LiGX: 新規<br />
 * @version 1.0
 */
public class BTGKA1013Dto implements Serializable {

    /**
     *  デバイスTOKEN管理．デバイスID
     */
    private Integer deviceId;

    /**
     *  デバイスTOKEN管理．更新日時
     */
    private Timestamp updDatime;

    /**
     *  デバイスIDを取得する
     *
     * @return  deviceId デバイスID
     */
    public Integer getDeviceId() {
        return deviceId;
    }
    /**
     * デバイスIDを設定する
     *
     * @param deviceId デバイスID
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    /**
     *  更新日時を取得する
     *
     * @return  updDatime 更新日時
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * 更新日時を設定する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
}

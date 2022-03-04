package jp.learningpark.framework.utils;

import jp.learningpark.framework.exception.RRException;

import java.text.SimpleDateFormat;

/**
 * <p>
 * https://github.com/souyunku/SnowFlake
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/25 ： NWT)hxl ： 新規作成
 * @date 2020/02/25 18:41
 */
public class SnowFlake {

    /**
     * シリアル番号が占める桁数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 最大シリアル番号
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * タイムスタンプによって左にシフトされたビット数
     */
    private final static long TIMESTMP_LEFT = SEQUENCE_BIT;

    /**
     * シリアル番号
     */
    private static long sequence = 0L;

    /**
     * 最後のタイムスタンプ
     */
    private static long lastStmp = -1L;

    /**
     * 次のIDを生成
     *
     * @return
     */
    public static synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //同じミリ秒で、シリアル番号が増加します
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同じミリ秒内のシーケンスの数が最大に達しました
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //シリアル番号は、異なるミリ秒で0に設定されます
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp) << TIMESTMP_LEFT      //タイムスタンプ部
                | sequence;                     //シリアル番号部
    }

    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private static long getNewstmp() {
        return System.currentTimeMillis();
    }

    /**
     * IDを時間に戻す
     *
     * @param id        ID
     * @param format    時間の形式
     * @return
     */
    public static String getDateFromID(Long id, String format) {
        try {
            Long id1 = id >> 12;
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.format(id1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException(e.getMessage());
        }
    }
}

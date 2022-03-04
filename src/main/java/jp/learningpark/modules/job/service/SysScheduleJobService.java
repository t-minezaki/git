/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.modules.job.entity.SysScheduleJobEntity;

import java.util.Map;

/**
 * スケジュールジョブ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
public interface SysScheduleJobService extends IService<SysScheduleJobEntity> {

    /**
     * ページ処理
     * @param params　ページパラメータ
     * @return １ページ
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 保存処理
     * @param scheduleJob　スケジュールジョブ
     */
    void createJob(SysScheduleJobEntity scheduleJob);

    /**
     * 更新処理
     * @param scheduleJob　スケジュールジョブ
     */
    void updateJob(SysScheduleJobEntity scheduleJob);
    
    /**
     * 削除処理
     * @param scheduleJob　スケジュールジョブ
     */
    void deleteBatch(Long[] jobIds);
    
    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);
    
    /**
     * 立即执行
     */
    void run(Long[] jobIds);
    
    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);
    
    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);

}

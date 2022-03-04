package jp.learningpark.framework.filter;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/03/10 ： NWT)hxl ： 新規作成
 * @date 2020/03/10 15:51
 */
@Component
@DependsOn("mstCodDDao")
public class QRResponseFilter implements Filter {

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * カウンター
     */
    private static AtomicInteger apiCount = new AtomicInteger(0);

    /**
     * カウンター
     */
    private static AtomicInteger downloadCount = new AtomicInteger(0);

    private static Pattern p = Pattern.compile("/download/\\S*.zip");

    private static final Logger log = LoggerFactory.getLogger(QRResponseFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /* 2021/09/15 manamiru1-772 cuikl del start */
        /* 2021/09/15 manamiru1-772 cuikl del end */
        if (StringUtils.equals(((HttpServletRequestWrapper) request).getServletPath(), "/QRAPI")){
            MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().select("cod_value").eq("del_flg", 0).eq("cod_key", "QR_USER_MAX_NUM").eq("cod_cd", "0"));
            int apiLimit = 300;
            if (mstCodDEntity != null){
                apiLimit = Integer.parseInt(mstCodDEntity.getCodValue());
            }
            //キャップチェック
            next(request, response, chain, apiCount, apiLimit);
        }else if (p.matcher(((HttpServletRequestWrapper) request).getServletPath()).matches()){
            MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("del_flg", 0).eq("cod_key", "QR_USER_MAX_NUM").eq("cod_cd", "0"));
            int downloadLimit = 200;
            if (mstCodDEntity != null){
                downloadLimit = Integer.parseInt(mstCodDEntity.getCodValue2());
            }
            //キャップチェック
            next(request, response, chain, downloadCount, downloadLimit);
        }
        else {
            chain.doFilter(request, response);
        }
    }

    /**
     * process the request
     *
     * @param request   request
     * @param response  response
     * @param chain     chain
     * @param count     count
     * @param limit     limit
     * @throws IOException
     * @throws ServletException
     */
    private void next(ServletRequest request, ServletResponse response, FilterChain chain, AtomicInteger count, int limit) throws IOException, ServletException {
        if (count.get() >= limit){
            //限界に達した
            log.info("---------------connect number is too much---------------");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("code",901);
            if (p.matcher(((HttpServletRequestWrapper)request).getServletPath()).matches()) {
                res.put("message", "システム障害防止のため、現在ダウンロードアクセス数を制限しています。\n" +
                        "大変恐れ入りますが、時間をおいて再度お試しください。比較的つながりやすい夜間や午前中の実施をおすすめします。\n" +
                        "※更新版のマナミル解答集は約400MBあります。");
            } else {
                res.put("message", "request上限あふれた。");
            }
            res.put("downloadinfo",null);
            PrintWriter out;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
        }else {
            //カウンターを増やす
            count.getAndIncrement();
            log.info("start---------------" + count + "---------------");
            try {
                chain.doFilter(request, response);
            }catch (Exception e){
                log.error(e.getMessage());
            }finally {
                //デクリメントカウンター
                count.getAndDecrement();
            }
            log.info("end---------------" + count + "---------------");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    public void setMstCodDDao(MstCodDDao mstCodDDao){
        this.mstCodDDao = mstCodDDao;
    }
}

package jp.learningpark.framework.freemaker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import jp.learningpark.framework.utils.HttpContextUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.framework.utils.StringUtils;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;

/**
 * Freemakerでパス取得メソッド
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2019/07/17  GAOLI: 新規
 * @version 1.0
 */
public class ResourceUrlMethod implements TemplateMethodModelEx  {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String path = StringUtils.defaultString(arguments.get(0));
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        ResourceUrlProvider provider = SpringContextUtils.getBean(ResourceUrlProvider.class);
        return HttpContextUtils.getHttpServletRequest().getContextPath() + provider.getForLookupPath(path);
    }
}
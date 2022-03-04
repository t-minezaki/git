package jp.learningpark.framework.dao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * 根据 ID 更新有值字段
 * </p>
 *
 * @author gl
 */
public class UpdateAllColumnById extends AbstractMethod {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public MappedStatement injectMappedStatement(final Class<?> mapperClass, final Class<?> modelClass, final TableInfo tableInfo) {
        final SqlMethod sqlMethod = SqlMethod.LOGIC_UPDATE_ALL_COLUMN_BY_ID;

        // 反射修改fieldFill值为update
        final List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        for (final TableFieldInfo tableFieldInfo : fieldList) {
            final Class<? extends TableFieldInfo> aClass = tableFieldInfo.getClass();
            try {
                final Field fieldFill = aClass.getDeclaredField("fieldFill");
                fieldFill.setAccessible(true);
                fieldFill.set(tableFieldInfo, FieldFill.UPDATE);
            } catch (final NoSuchFieldException e) {
                logger.error(e.getMessage());
            } catch (final IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        }

        final String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlSet(false, false, tableInfo, Constants.ENTITY_SPOT),
                tableInfo.getKeyColumn(), Constants.ENTITY_SPOT + tableInfo.getKeyProperty(),
                new StringBuilder("<if test=\"et instanceof java.util.Map\">").append("<if test=\"et.MP_OPTLOCK_VERSION_ORIGINAL!=null\">")
                        .append(" AND ${et.MP_OPTLOCK_VERSION_COLUMN}=#{et.MP_OPTLOCK_VERSION_ORIGINAL}").append("</if></if>"));
        final SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
package jp.learningpark.framework.validator.group;

import javax.validation.GroupSequence;

/**
 * グループ
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-15 23:15
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}

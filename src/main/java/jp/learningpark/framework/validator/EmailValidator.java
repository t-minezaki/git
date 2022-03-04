package jp.learningpark.framework.validator;

import jp.learningpark.framework.validator.annotation.MyEmail;
import org.hibernate.validator.internal.util.DomainNameUtil;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/17 ： NWT)hxl ： 新規作成
 * @date 2021/3/17 15:31
 */
public class EmailValidator implements ConstraintValidator<MyEmail, Object> {

    private static final Log LOG = LoggerFactory.make( MethodHandles.lookup() );

    private java.util.regex.Pattern pattern;

    private static final int MAX_LOCAL_PART_LENGTH = 64;

    private static final String LOCAL_PART_ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~\u0080-\uFFFF-]";
    private static final String LOCAL_PART_INSIDE_QUOTES_ATOM = "([a-z0-9!#$%&'*.(),<>\\[\\]:;  @+/=?^_`{|}~\u0080-\uFFFF-]|\\\\\\\\|\\\\\\\")";
    /**
     * Regular expression for the local part of an email address (everything before '@')
     */
    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile(
            "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" +
                    "(\\." + "(" + LOCAL_PART_ATOM + "+|\"" + LOCAL_PART_INSIDE_QUOTES_ATOM + "+\")" + ")*", CASE_INSENSITIVE
    );

    @Override
    public void initialize(MyEmail constraintAnnotation) {
        javax.validation.constraints.Pattern.Flag[] flags = constraintAnnotation.flags();
        int intFlag = 0;
        for ( javax.validation.constraints.Pattern.Flag flag : flags ) {
            intFlag = intFlag | flag.getValue();
        }

        // we only apply the regexp if there is one to apply
        if ( !".*".equals( constraintAnnotation.regexp() ) || constraintAnnotation.flags().length > 0 ) {
            try {
                pattern = java.util.regex.Pattern.compile( constraintAnnotation.regexp(), intFlag );
            }
            catch (PatternSyntaxException e) {
                throw LOG.getInvalidRegularExpressionException( e );
            }
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if ( value == null  || ((String)value).length() == 0 ) {
            return true;
        }

        boolean isValid;

        do {
            String stringValue = value.toString();
            int splitPosition = stringValue.lastIndexOf( '@' );
            if ( splitPosition < 0 ) {
                isValid =  false;
                break;
            }
            String localPart = stringValue.substring( 0, splitPosition );
            String domainPart = stringValue.substring( splitPosition + 1 );
            if ( !isValidEmailLocalPart( localPart ) ) {
                isValid =  false;
                break;
            }
            isValid =  DomainNameUtil.isValidEmailDomainAddress( domainPart );
        }while (false);

        if ( pattern == null || !isValid ) {
            return isValid;
        }

        Matcher m = pattern.matcher( (String)value );
        return m.matches();
    }

    private boolean isValidEmailLocalPart(String localPart) {
        if ( localPart.length() > MAX_LOCAL_PART_LENGTH ) {
            return false;
        }
        Matcher matcher = LOCAL_PART_PATTERN.matcher( localPart );
        return matcher.matches();
    }
}

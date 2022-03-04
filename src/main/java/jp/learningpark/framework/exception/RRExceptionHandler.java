package jp.learningpark.framework.exception;

import jp.learningpark.framework.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 例外ハンドラ
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        logger.error(e.getMessage());
        return r;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        R r = new R();
//        r.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
//        StringBuffer errMsg = new StringBuffer();
//        BindingResult bindingResult = e.getBindingResult();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            System.out.println(JSON.toJSONString(fieldError));
//
//            List<Object> args = new ArrayList<Object>();
//            for (Object obj : fieldError.getArguments()) {
//                if (obj instanceof DefaultMessageSourceResolvable) {
//                    DefaultMessageSourceResolvable resolvable = (DefaultMessageSourceResolvable) obj;
//                    args.add(MessageUtils.getMessage(fieldError.getObjectName() + "." + resolvable.getCode()));
//                } else {
//                    args.add(obj);
//                }
//            }
//            errMsg.append(MessageUtils.getMessage("errors." + fieldError.getCode(), args.toArray(new String[args.size()]))).append("<br>");
//        }
//        r.put("msg", errMsg.toString());
//        return r;
//    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error();
    }
}

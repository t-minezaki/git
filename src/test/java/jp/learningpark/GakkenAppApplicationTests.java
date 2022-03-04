package jp.learningpark;

import jp.learningpark.framework.utils.GoogleSheetApiUtils;
import jp.learningpark.modules.guard.service.impl.F30113ServiceImpl;
import jp.learningpark.modules.pop.controller.F21016Controller;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GakkenAppApplication.class)
public class GakkenAppApplicationTests {

    @Autowired
    private F21016Controller f21016Controller;

    public static void main (String... args){
        final String userId = "nwtjp2020@gmail.com";
        final String sheetId = "1w54M_a0K2wG7t7SvzBx7NIv6nquHXz2w_bKgJSYzNuw";
        final String range = "生徒基本情報!A2:P";

        List<List<Object>> values = new ArrayList<>();
        try {
            values = GoogleSheetApiUtils.googleSheet(userId, sheetId, range);
        } catch (Exception  ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString().split("\n")[0]);
            StringWriter stringWriter = new StringWriter();
            PrintWriter writer = new PrintWriter(stringWriter);
            ex.printStackTrace(writer);
            StringBuffer buffer = stringWriter.getBuffer();
            System.out.println(buffer.toString());
            ex.printStackTrace();
//            ex.getMessage();
        }
        for (List row : values) {
            System.out.printf("'%s' '%s' '%s' '%s' '%s'\n", row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
        }
    }

    @Test
    public void test() {
//        //创建ArrayList的Mock对象
//        F30113ServiceImpl mockList = mock(F30113ServiceImpl.class);
//        //pass
//        Assert.assertTrue(mockList instanceof F30113ServiceImpl);

//        f21016Controller.submit()
    }
}
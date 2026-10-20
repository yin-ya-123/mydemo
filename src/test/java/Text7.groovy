import cn.hutool.core.map.MapProxy;
import com.yin.MyDemoApplication;
import com.yin.mapper.TestMapper;
import com.yin.utils.Packaging.MapWrapper;
import groovy.transform.CompileStatic;
import groovy.transform.TypeCheckingMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yin
 * @date 2024-12-19
 * @Description 海泰模板病区复制
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
@CompileStatic(TypeCheckingMode.SKIP)
public class Text7 {

   @Autowired
   private TestMapper testMapper;


    @Test
    public void show(){
        List<Map<String, Object>> result = testMapper.selectList();
        System.out.println(result);
        if (!result.isEmpty()) {
            def userMap = result.get(0);
            String userId = userMap.USERID;
            println "userId:${userId}"
        }
    }

}

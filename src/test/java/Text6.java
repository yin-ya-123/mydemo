import com.alibaba.fastjson2.JSONObject;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.utils.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author yin
 * @date 2025-02-15
 * @Description
 */
public class Text6 {
    public static void main(String[] args) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject("{\"1\":\"2\"}");
        jsonObject.put("1","3");
        System.out.println(jsonObject.get("1"));
//        String path="E:\\文件信息保存\\2025-02-15\\2025-02-15TRANSLOG";
//        String path2="E:\\文件信息保存\\2025-02-15\\2025-02-15TRANSLOG\\all.txt";
//        File file = new File(path);
//        for (File listFile : file.listFiles()) {
//            String s = FileUtils.readFile(listFile.getAbsolutePath());
//            FileUtils.writeFile(path2,s, true);
//        }
    }

    @Test
    public void test() throws YException {
        List<String> datesBetween = ApplicationUtils.getDatesBetween("2024-01-01", "2025-01-01", "day");
        for (int i = 0; i < datesBetween.size()-1; i++) {
            System.out.print(datesBetween.get(i)+",");
        }
        System.out.println();
        for (int i = 0; i < datesBetween.size()-1; i++) {
            System.out.print(datesBetween.get(i+1)+",");
        }
    }
}

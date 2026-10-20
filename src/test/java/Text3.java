import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.OracleService;
import com.yin.service.impl.OracleServiceImpl2;
import com.yin.service.impl.OrderService;
import com.yin.utils.ConvertAllUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2024-11-12
 * @Description
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
public class Text3 {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OracleServiceImpl2 oracleServiceImpl2;
    @Resource
    private OracleService hisOracleServiceImpl;
    @Test
    public void test() {
//        orderService.doHandle();
    }

    @Test
    public void test1() {
        String sql=getSql2();
        List list = hisOracleServiceImpl.selectAll(sql);
        Map<String, String> list1 = getList();
//        System.out.println(list1.size());
        Map<String, String> list2 = new HashMap<>();
        JSONArray objects = ConvertAllUtils.convertJsonArray(list);
        for (Object object : objects) {
            JSONObject jsonObject = ConvertAllUtils.convertJson(object);
            String phone = jsonObject.getString("LXR_LXFS");
            String s = list1.get(phone);
            if(s==null){
                System.out.println(jsonObject.get("ZYLSH").toString()
                        +jsonObject.get("PATNAME").toString()
                        +jsonObject.get("LXR_LXFS").toString()
                );
            }else {
                list2.put(phone,s);
            }
        }
//        System.out.println(objects.toString());
//        Map<String, String> differences = findDifferences(list1, list2);
//
//        System.out.println("Differences: " + differences);
    }

    public Map<String,String> getList(){
        String path = "E:\\文件信息保存\\2024-11-18\\new 3.txt";
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("RequestData：")){
                    line=line.replace("RequestData：","");
                    // 处理每一行数据
//                    System.out.println(line);
                    JSONObject jsonObject = JSONObject.parseObject(line);
                    String phone1 = jsonObject.getString("mobileNumber");
                    stringStringHashMap.put(phone1,jsonObject.toString());
                }
            }
//            System.out.println(strings.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringStringHashMap;
    }
    public String getsql(){
        return "--每日费用\n" +
                "select b.zylsh,\n" +
                "       b.status_mc,\n" +
                "       b.room || '-' || b.zycwh || ' ' || b.pat_name as patname,\n" +
                "       round(fy.je, 2) zje,\n" +
                "       round(nvl(DQ_yjjze, 0), 2) yjje,\n" +
                "       round(nvl(DQ_yjjze, 0) - nvl(fy.je, 0) + nvl(b.ybysbxje, 0), 2) as ye,\n" +
                "       round(nvl(b.ybysbxje, 0), 2) as ybysbxje,\n" +
                "       b.zycwh cwh,\n" +
                "       b.ybxz brxz_mc,\n" +
                "       b.zy_cs,\n" +
                "       b.lxr_lxfs,\n" +
                "       nvl(b.kz_ed, 3000) qfed,\n" +
                "       b.ybysrq\n" +
                "  from his.v_inpat_info b,\n" +
                "       (select zylsh_id, sum(ssje) je\n" +
                "          from his.pat_inpat_order_cost\n" +
                "         where fy_status in ('2', '7')\n" +
                "           and trunc(jz_Rq) <= to_date('2024-11-13','yyyy-mm-dd')  \n" +
                "         group by zylsh_id) fy,\n" +
                "       (\n" +
                "       select abs(ye) - qfed qfje,\n" +
                "       t.\"ZYLSH\",\n" +
                "       t.\"PAT_NAME\",\n" +
                "       t.\"ZY_BQ_ID\",\n" +
                "       t.\"FYZE\",\n" +
                "       t.\"YJJZE\",\n" +
                "       t.\"YBYSBXJE\",\n" +
                "       t.\"QFED\",\n" +
                "       t.\"YE\"\n" +
                "  from (select zylsh,\n" +
                "               b.pat_name,\n" +
                "               q.zy_bq_id,\n" +
                "               c.je fyze,\n" +
                "               nvl(q.dq_yjjze, 0) yjjze,\n" +
                "               q.ybysbxje,\n" +
                "               decode(q.xz, '1', 45000, '2', 25000, 2500) qfed,\n" +
                "               nvl((\n" +
                "               SELECT sum(yj.yj_je) FROM HIS.OUTINPAT_ADVANCEPAY_RECORD yj where yj.yl_lsh=q.zylsh and trunc(yj.sk_rq) <= to_date('2024-11-13','yyyy-mm-dd')  \n" +
                "               ), 0) - c.je + q.ybysbxje ye\n" +
                "          from his.pat_inpat_info q,\n" +
                "               his.pat_info b,\n" +
                "               (select zylsh_id, sum(ssje) je\n" +
                "                  from his.pat_inpat_order_cost\n" +
                "                 where \n" +
                "                 trunc(jz_rq) <= to_date('2024-11-13','yyyy-mm-dd') \n" +
                "                   and fy_status in ('2', '7')\n" +
                "                 group by zylsh_id) c\n" +
                "         where (status in ('2', '3', '4', '8') or (status='5' and q.cy_jsrq > to_date('2024-11-13 09:00:00','yyyy-mm-dd hh24:mi:ss')))\n" +
                "           and q.pat_id = b.pat_id\n" +
                "           and q.zylsh = c.zylsh_id) t\n" +
                " where /*qfed<abs(ye) and*/\n" +
                " ye < 0 --and zylsh='8668'\n" +
                "       ) c\n" +
                " where b.status in ('2', '3', '6', '4', '8','5')\n" +
                "   and b.zyksmc not like '%测试%'\n" +
                "   and b.zylsh = c.ZYLSH\n" +
                "   --and b.zybqdm like :in_bqid\n" +
                "   and b.zylsh = fy.zylsh_id\n" +
                " order by b.zybqdm, b.zycwh\n" +
                " --状态   0 入院(开入院申请单);1.住院收费处确认; 2在院;3医生开出院;4护士执行出院;5出院结算;9作废;6转科中;7:医生取消住院;8正在结算\n";
    }

    /**
     * 当天欠费sql
     * @return
     */
    public String getSql2(){
        return "--每日费用\n" +
                "select b.zylsh,\n" +
                "       b.status_mc,\n" +
                "       b.room || '-' || b.zycwh || ' ' || b.pat_name as patname,\n" +
                "       round(fy.je, 2) zje,\n" +
                "       round(nvl(DQ_yjjze, 0), 2) yjje,\n" +
                "       round(nvl(DQ_yjjze, 0) - nvl(fy.je, 0) + nvl(b.ybysbxje, 0), 2) as ye,\n" +
                "       round(nvl(b.ybysbxje, 0), 2) as ybysbxje,\n" +
                "       b.zycwh cwh,\n" +
                "       b.ybxz brxz_mc,\n" +
                "       b.zy_cs,\n" +
                "       b.lxr_lxfs,\n" +
                "       nvl(b.kz_ed, 3000) qfed,\n" +
                "       b.ybysrq\n" +
                "  from his.v_inpat_info b,\n" +
                "       (select zylsh_id, sum(ssje) je\n" +
                "          from his.pat_inpat_order_cost\n" +
                "         where fy_status in ('2', '7')\n" +
                "           and trunc(jz_Rq) <= trunc(sysdate) - 1\n" +
                "         group by zylsh_id) fy,\n" +
                "       his.v_ybbr_fyts c\n" +
                " where b.status in ('2', '3', '6', '4', '8')\n" +
                "   and b.zyksmc not like '%测试%'\n" +
                "   and b.zylsh = c.ZYLSH\n" +
                "   and b.zybqdm like '%'\n" +
                "   and b.zylsh = fy.zylsh_id\n" +
                "   and b.pat_name not in ('刘林鑫','桂树华','富启标')\n" +
                " order by b.zybqdm, b.zycwh\n";
    }


    public static Map<String, String> findDifferences(Map<String, String> map1, Map<String, String> map2) {
        Map<String, String> differences = new HashMap<>();

        // Check for differences in map1
        for (Map.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!map2.containsKey(key) || !map2.get(key).equals(value)) {
                differences.put(key, value);
            }
        }

        // Check for differences in map2
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!map1.containsKey(key)) {
                differences.put(key, value);
            }
        }

        return differences;
    }

    @Test
    public void test3(){
        String sql="SELECT * FROM bdhis.ybglml";
        List list = oracleServiceImpl2.selectAll(sql);
        for (Object o : list) {
            System.out.println(JSONObject.toJSONString(o));
            break;
        }
    }
}

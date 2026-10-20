import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.pojo.qt.All;
import com.yin.service.OracleService;
import com.yin.service.impl.HisOracleServiceImpl;
import com.yin.service.impl.OracleServiceImpl;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yin
 * @date 2024-12-19
 * @Description 海泰模板病区复制
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
public class Text4 {
    @Resource
    private HisOracleServiceImpl hisOracleServiceImpl;
    @Resource
    private OracleService oracleServiceImpl2;
    @Resource
    private OracleService htOracleServiceImpl;

    @Resource
    private OracleService htCsOracleServiceImpl;


    public static void main(String[] args) {
        String path = "D:\\公司\\项目文件\\康复医院\\可以异地使用的慢病.txt";
        String s = FileUtils.readFile(path);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        Object o = JSONUtil.parseObj(JSONUtil.parseObj(jsonObject.get("data").toString()).get("pageBean").toString()).get("data");
        JSONArray objects = JSONUtil.parseArray(o);
        ExcelUtils<All> allExcelUtils = new ExcelUtils<>();
        ArrayList<All> alls = new ArrayList<>();
        for (Object object : objects) {
            String opspDiseCode = JSONUtil.parseObj(object.toString()).get("opspDiseCode").toString();
            String opspDiseName = JSONUtil.parseObj(object.toString()).get("opspDiseName").toString();
            All all = new All();
            all.setOne(opspDiseCode);
            all.setTwo(opspDiseName);
            alls.add(all);
        }
        allExcelUtils.writeExcel("D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2024-12-18\\可以异地结算的慢特病种.xlsx", new All(), alls);
    }

    @Test
    public void test() throws SQLException {
        List list = htCsOracleServiceImpl.selectAll(sql());
        String sql="insert into TEXT_CS(one,two)values(?,?)";
        for (Object o : list) {
            JSONObject jsonObject = ConvertAllUtils.convertJson2(o);
            oracleServiceImpl2.insertAllType(sql,jsonObject.get("TPL_DATA").toString(), ApplicationUtils.getNowDateTime());
        }
    }

    @Test
    public void htMb(){
        int index=1;
        List list = htOracleServiceImpl.selectAll(getSQLMb());
        System.out.println(list);
        for (Object o : list) {
            JSONObject jsonObject = JSONUtil.parseObj(o);
            System.out.println(jsonObject);
        }
    }

    /**
     * 获取科室模板分类信息
     * @return
     */
    public String getSQLMb(){
        return "SELECT * FROM TEXTTEMPLATE.TEXTTEMPLATE_TYPE C WHERE\n" +
                " C.OWNER_TYPE='bq'\n" +
                " AND C.OWNER_ID='BQ08'\n" +
                " AND C.TYPE_NAME NOT IN \n" +
                " (\n" +
                " SELECT C.TYPE_NAME FROM TEXTTEMPLATE.TEXTTEMPLATE_TYPE C WHERE C.OWNER_TYPE='bq' AND C.OWNER_ID='BQ99'\n" +
                " )";
    }
    /**
     * 获取科室模板分类信息对应的模板内容信息
     * @return
     */
    public String getSqlMbInfo(String typeFlow){
        return "SELECT \n" +
                "A.TEMPLATE_FLOW,\n" +
                "A.TEMPLATE_NAME,\n" +
                "A.ORDINAL,\n" +
                "A.TPL_DATA,\n" +
                "A.STATE_DESCRIPTION,\n" +
                "A.CREATOR,\n" +
                "A.CREATOR_NAME,\n" +
                "A.INOUT_FLAG,\n" +
                "A.TYPE_NAME,\n" +
                "A.CREATOR_TIME,\n" +
                "A.ACTION_CODE,\n" +
                "A.ACTION_NAMES\n" +
                " FROM \n" +
                "TEXTTEMPLATE.TEXTTEMPLATE_RC a\n" +
                "WHERE A.TYPE_FLOW ='"+typeFlow+"'";
    }

    public String getSqlInsertMb(JSONObject jsonObject){
        return "";
    }





    public String sql() {
        return "SELECT * FROM TEXTTEMPLATE.TEXTTEMPLATE_RC a";
    }

    public String insertSql(JSONObject jsonObject){
        return "INSERT INTO TEXTTEMPLATE.TEXTTEMPLATE_RC_MY(\n" +
                "       A.TEMPLATE_FLOW,\n" +
                "       A.TEMPLATE_NAME,\n" +
                "       A.TYPE_FLOW,\n" +
                "       A.OWNER_ID,\n" +
                "       A.OWNER_TYPE,\n" +
                "       A.ORDINAL,\n" +
                "       A.TPL_DATA,\n" +
                "       A.IS_XML,\n" +
                "       A.PY,\n" +
                "       A.STATE_DESCRIPTION,\n" +
                "       A.CREATOR,\n" +
                "       A.CREATOR_NAME,\n" +
                "       A.INOUT_FLAG,\n" +
                ")VALUES ("+
                jsonObject.get("TEMPLATE_FLOW").toString()+","+
                jsonObject.get("TEMPLATE_NAME").toString()+","+
                jsonObject.get("TYPE_FLOW").toString()+","+
                jsonObject.get("OWNER_ID").toString()+","+
                jsonObject.get("OWNER_TYPE").toString()+","+
                jsonObject.get("ORDINAL").toString()+","+
                jsonObject.get("TPL_DATA").toString()+","+
                jsonObject.get("IS_XML").toString()+","+
                jsonObject.get("PY").toString()+","+
                jsonObject.get("STATE_DESCRIPTION").toString()+","+
                jsonObject.get("CREATOR").toString()+","+
                jsonObject.get("CREATOR_NAME").toString()+","+
                (null!=jsonObject.get("INOUT_FLAG")?"''":jsonObject.get("INOUT_FLAG").toString())+
                ")";
    }



}

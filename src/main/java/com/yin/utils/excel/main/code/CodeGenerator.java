package com.yin.utils.excel.main.code;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.OracleService;
import com.yin.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 代码生成器，根据数据库表生成对应的Model、Mapper、Service、Controller
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CodeGenerator {

    private String path = "D:\\cs\\ssxjg\\sql.txt";
    private String packagePath = "D:\\cs\\模板2\\Template\\src\\main\\java\\com\\yin";
    private String packagePathVue = "D:\\cs\\接口\\template-vite-project\\src";

    private String LocalPath = "E:\\文件信息保存\\实体类创建";
    private String LocalPathPojo = packagePath + "\\pojo\\";
    private String LocalPathService = packagePath + "\\service\\";
    private String LocalPathMapper = packagePath + "\\mapper\\";
    private String LocalPathMapperXml = packagePath + "\\mapper\\xml\\";
    private String LocalPathController = packagePath + "\\controller\\";
    private String LocalPathJsApi = packagePathVue + "\\api\\";
    //D:\cs\新项目\MedicalInsurance\src\main\java\com\yin
    @Autowired
    private OracleService oracleServiceImpl2;

    @Test
    public void pojoGenerator() {
//        String sql="SELECT * FROM BDHIS.DIC_USER";
        String tableName = "error_log";
        String tableInfo = "简化的错误日志表，记录错误ID、类型、相关数据和问题描述";
        String dicUser = getSql(tableName);
        String newTableName = FirstStrUpp(HumpNaming2(tableName));
        List list = oracleServiceImpl2.selectAll(dicUser);
        if (list.size() > 0) {
            JSONArray objects = JSONArray.parseArray(JSONObject.toJSONString(list));
            generateClass(newTableName, objects, tableInfo, "dataflowdb");
//            String s = generateServiceImplForLambda("DictSysDictIonAry", objects, "数据字典信息");
//            System.out.println(s);
        } else {
            log.info("未查询到表结构信息");
        }
    }

    //测试
    @Test
    public void pojoGeneratorCs() {
//        String sql="SELECT * FROM BDHIS.DIC_USER";
        String tableName = "SJCJ_JKXXMXB";
        String tableInfo = "数据采集-接口信息主表";
        String dicUser = getSql(tableName);
        String newTableName = FirstStrUpp(HumpNaming2(tableName));
        List list = oracleServiceImpl2.selectAll(dicUser);
        if (list.size() > 0) {
            JSONArray objects = JSONArray.parseArray(JSONObject.toJSONString(list));
           CodeGeneratorMethod.generateTable(newTableName, objects, tableInfo, "log");
//            String s = generateServiceImplForLambda("DictSysDictIonAry", objects, "数据字典信息");
//            System.out.println(s);
        } else {
            log.info("未查询到表结构信息");
        }
    }

    @Test
    public void pojoGenerator2() {
//        String sql="SELECT * FROM BDHIS.DIC_USER";
        List list = oracleServiceImpl2.selectAll(getsql3());
        if (list.size() > 0) {
            JSONArray objects = JSONArray.parseArray(JSONObject.toJSONString(list));
            generateClassTwo("UserRolePojo", JSONObject.parseObject(objects.get(0).toString()));
        } else {
            log.info("未查询到表结构信息");
        }
    }

    public void generateClass(String className, JSONArray objects, String annotation) {
        String pojo = generatePojo(className, objects, annotation);
        String mapper = generateMapper(className, objects, annotation);
        String service = generateService(className, objects, annotation);
        String serviceImpl = generateServiceImpl(className, objects, annotation);
        String controller = generateController(className, objects, annotation);
        String vueApi = generateVueApi(className, objects, annotation);
        String pojoPath = LocalPath + File.separator + className + File.separator + className + "Pojo.java";
        String mapperPath = LocalPath + File.separator + className + File.separator + className + "Mapper.java";
        String servicePath = LocalPath + File.separator + className + File.separator + className + "Service.java";
        String serviceImplPath = LocalPath + File.separator + className + File.separator + className + "ServiceImpl.java";
        String controllerPath = LocalPath + File.separator + className + File.separator + className + "Controller.java";
        String vueApiPath = LocalPath + File.separator + className + File.separator + className + "Api.js";
        try {
            FileUtils.writeFile(pojoPath, pojo, false);
            FileUtils.writeFile(mapperPath, mapper, false);
            FileUtils.writeFile(servicePath, service, false);
            FileUtils.writeFile(serviceImplPath, serviceImpl, false);
            FileUtils.writeFile(controllerPath, controller, false);
            FileUtils.writeFile(vueApiPath, vueApi, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 主入口
     *
     * @param className  转换表名成首字符大写的驼峰命名
     * @param objects    读取数据库中的表结构信息，获取表类型、注释、字段名
     * @param annotation 表的说明注释
     * @param prefixPath 写入代码位置的前缀路径
     */
    public void generateClass(String className, JSONArray objects, String annotation, String prefixPath) {
        String pojo = CodeGeneratorMethod.generatePojo(className, objects, annotation, prefixPath);
        String mapper = CodeGeneratorMethod.generateMapper(className, objects, annotation, prefixPath);
        String mapperXml = CodeGeneratorMethod.generateMapperXml(className, objects, annotation, prefixPath);
        String service = CodeGeneratorMethod.generateService(className, objects, annotation, prefixPath);
        String serviceImpl = CodeGeneratorMethod.generateServiceImpl(className, objects, annotation, prefixPath);
        String controller = CodeGeneratorMethod.generateController(className, objects, annotation, prefixPath);
        String vueApi = CodeGeneratorMethod.generateVueApi(className, objects, annotation, prefixPath);
        String pojoPath = LocalPathPojo + prefixPath + File.separator + className + "Pojo.java";
        String mapperPath = LocalPathMapper + prefixPath + File.separator + className + "Mapper.java";
        String mapperXmlPath = LocalPathMapperXml + prefixPath + File.separator + className + "Mapper.xml";
        String servicePath = LocalPathService + prefixPath + File.separator + className + "Service.java";
        String serviceImplPath = LocalPathService + prefixPath + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        String controllerPath = LocalPathController + prefixPath + File.separator + className + "Controller.java";
        String vueApiPath = LocalPathJsApi + prefixPath + File.separator + className + "Api.js";
        try {
            FileUtils.writeFile(pojoPath, pojo, false);
            FileUtils.writeFile(mapperPath, mapper, false);
            FileUtils.writeFile(mapperXmlPath, mapperXml, false);
            FileUtils.writeFile(servicePath, service, false);
            FileUtils.writeFile(serviceImplPath, serviceImpl, false);
            FileUtils.writeFile(controllerPath, controller, false);
//            FileUtils.writeFile(vueApiPath, vueApi, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateClassTwo(String className, JSONObject object) {
        StringBuilder str = new StringBuilder();
        str.append("import lombok.Data;\n" + "import java.io.Serializable;\n" + "import com.baomidou.mybatisplus.annotation.TableField;\n" + "import com.baomidou.mybatisplus.annotation.TableName;\n" + "import lombok.EqualsAndHashCode;\n" + "import lombok.experimental.Accessors;\n" + "import java.time.LocalDateTime;\n");
        str.append("@EqualsAndHashCode(callSuper = false)\n").append("@Accessors(chain = true)\n").append("@Data\n").append(String.format("public class %s implements Serializable {\n", className)).append("private static final long serialVersionUID=1L;\n");
        for (String fieldName : object.keySet()) {
            str.append("@JsonProperty\n");
            //驼峰命名处理
            String[] s = fieldName.split("_");
            StringBuilder newFieldName = new StringBuilder();
            if (s.length > 0) {
                for (int i = 0; i < s.length; i++) {
                    String newStr = s[i].toLowerCase();
                    if (i == 0) {
                        newFieldName.append(newStr);
                    } else {
                        String upperCase = newStr.substring(0, 1).toUpperCase();
                        upperCase = upperCase + newStr.substring(1);
                        newFieldName.append(upperCase);
                    }
                }
            }
            str.append(String.format("private String %s;", newFieldName.toString())).append("\n");
        }
        str.append("}");
        System.out.println(str.toString());
    }


    public String generatePojo(String className, JSONArray objects, String annotation) {
        className = className + "Pojo";
        StringBuilder str = new StringBuilder();
        String owner = JSONObject.parseObject(objects.get(0).toString()).getString("OWNER");
        String tablename = JSONObject.parseObject(objects.get(0).toString()).getString("TABLENAME");
        str.append("\n/*\n").append(annotation).append("实体类\n").append("*/\n");
        str.append("import lombok.Data;\n" + "import java.io.Serializable;\n"
                + "import com.baomidou.mybatisplus.annotation.TableField;\n"
                + "import com.baomidou.mybatisplus.annotation.TableName;\n"
                + "import lombok.EqualsAndHashCode;\n"
                + "import lombok.experimental.Accessors;\n"
                + "import java.time.LocalDateTime;\n"
                //+ "import org.example.pojo.PagePojo;\n"
                + "import com.baomidou.mybatisplus.annotation.FieldFill;\n"
                + "import com.fasterxml.jackson.annotation.JsonProperty;\n");
        str.append("@EqualsAndHashCode(callSuper = false)\n").append("@Accessors(chain = true)\n").append("@Data\n").append(String.format("@TableName(\"%s\")\n", owner + "." + tablename)).append(String.format("public class %s implements Serializable {\n", className)).append("private static final long serialVersionUID=1L;\n");
        int index = 0;
        for (Object object : objects) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
            //判断类型
            String fieldType = jsonObject.getString("DATATYPE");
            String fieldName = jsonObject.getString("COLUMNNAME");
            String fieldSm = jsonObject.getString("COMMENTS");

            if (fieldType.equals("VARCHAR2") || fieldType.equals("CHAR")) {
                fieldType = "String ";
            } else if (fieldType.equals("NUMBER")) {
                fieldType = "Integer ";
            } else if (fieldType.equals("DATE")) {
                fieldType = "LocalDateTime ";
            }
            if (fieldSm != null) {
                str.append("/*\n").append(fieldSm).append("\n").append("*/\n");
            }
            if (fieldName.equals("UPDATE_TIME") || fieldName.equals("UPDATE_USER")) {
                str.append("@TableField(value = \"" + fieldName + "\",fill = FieldFill.UPDATE) \n");
            } else if (fieldName.equals("CREATE_USER") || fieldName.equals("CREATE_TIME")) {
                str.append("@TableField(value = \"" + fieldName + "\",fill = FieldFill.INSERT) \n");
            } else if (fieldName.equals("DEL_FLAG")) {
                // @TableLogic(delval = "1",value = "0")
                str.append(" @TableLogic(delval = \"1\",value = \"0\")");
            } else {
                if (index == 0) {
                    str.append("@TableId(\"" + fieldName + "\")\n");
                    index = 1;
                } else {
                    str.append("@TableField(\"" + fieldName + "\")\n");
                }

            }
            str.append("@JsonProperty\n");
            //驼峰命名处理
            String[] s = fieldName.split("_");
            StringBuilder newFieldName = new StringBuilder();
            if (s.length > 0) {
                for (int i = 0; i < s.length; i++) {
                    String newStr = s[i].toLowerCase();
                    if (i == 0) {
                        newFieldName.append(newStr);
                    } else {
                        String upperCase = newStr.substring(0, 1).toUpperCase();
                        upperCase = upperCase + newStr.substring(1);
                        newFieldName.append(upperCase);
                    }
                }
            }
            str.append(String.format("private %s %s;", fieldType, newFieldName.toString())).append("\n\n");
        }
        str.append("@TableField(exist = false)\n" + "    @JsonProperty\n" + "    private PagePojo pagePojo;\n");
        str.append("}");
        return str.toString();
    }

    public String generateMapper(String className, JSONArray objects, String annotation) {
        String classNameMapper = className + "Mapper";
        String classNamePojo = className + "Pojo";
        StringBuilder str = new StringBuilder();
        str.append("\n/*\n").append(annotation).append("mapper接口\n").append("*/\n");
        str.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" + "import org.apache.ibatis.annotations.Mapper;\n" + "import org.apache.ibatis.annotations.Param;\n" + "@Mapper\n");
        str.append("public interface ").append(classNameMapper).append(" extends BaseMapper<").append(classNamePojo).append(">{\n").append("}");
        return str.toString();
    }

    public String generateService(String className, JSONArray objects, String annotation) {
        String classNameService = className + "Service";
        String classNamePojo = className + "Pojo";
        String classNamePojoLow = FirstStrLow(classNamePojo);
        StringBuilder str = new StringBuilder();
        str.append("\n/*\n").append(annotation).append("service接口\n").append("*/\n");
        str.append("import com.baomidou.mybatisplus.extension.plugins.pagination.Page;\n" + "import com.baomidou.mybatisplus.extension.service.IService;\n");
        str.append("public interface ").append(classNameService).append(" extends IService<").append(classNamePojo).append(">{\n");
        str.append("//新增\n" + "    public Result<?> add").append(classNamePojo).append("(").append(classNamePojo).append(" ").append(classNamePojoLow).append(");\n");
        str.append("//删除\n" + "    public Result<?> delete").append(classNamePojo).append("(").append(classNamePojo).append(" ").append(classNamePojoLow).append(");\n");
        str.append("//修改\n" + "    public Result<?> update").append(classNamePojo).append("(").append(classNamePojo).append(" ").append(classNamePojoLow).append(");\n");
        str.append(" //查询\n" + "    public Result<List<").append(classNamePojo).append(">> get").append(classNamePojo).append("List( ").append(classNamePojo).append(" ").append(classNamePojoLow).append(");\n");
        str.append("//分页查询\n" + "    public Result<Page<").append(classNamePojo).append(">> get").append(classNamePojo).append("ListPage(").append(classNamePojo).append(" ").append(classNamePojoLow).append(");\n");
        str.append("}");
        return str.toString();
    }

    public String generateServiceImpl(String className, JSONArray objects, String annotation) {
        String classNameMapper = className + "Mapper";
        String classNameService = className + "Service";
        String classNameServiceImpl = className + "ServiceImpl";
        String classNamePojo = className + "Pojo";
        StringBuilder str = new StringBuilder();
        str.append("\n/*\n").append(annotation).append("serviceImpl接口\n").append("*/\n");
        str.append("import org.springframework.stereotype.Service;\n" + "import lombok.extern.slf4j.Slf4j;\n" + "import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n" + "@Service\n" + "@Slf4j\n");
        str.append("public class ").append(classNameServiceImpl).append(" extends ServiceImpl<")
                .append(classNameMapper).append(",").append(classNamePojo).append(">")
                .append(" implements ")
                .append(classNameService).append("{\n");
        str.append("@Autowired\n").append("private ").append(classNameMapper).append(" ").append(FirstStrLow(classNameMapper)).append(";");
        str.append("@Autowired\n").append("private ").append("ServiceUtils<")
                .append(classNamePojo).append("> serviceUtils;");
        String s = generateServiceImplForLambda(className, objects, annotation);
//        String s1 = generateServiceImplForVerify(className, objects, annotation);
        String s2 = generateServiceImplForVerifyS(className, objects, annotation);
        String s3 = generateServiceImplImpl(className, objects, annotation);
        str.append(s).append("\n");
//        str.append(s1).append("\n");
        str.append(s2).append("\n");
        str.append(s3).append("\n");
        str.append("}");
        return str.toString();
    }

    public String generateController(String className, JSONArray objects, String annotation) {
        String classNameService = className + "Service";
        String controllerName = className + "Controller";
        String classNamePojo = className + "Pojo";
        String classNamePojoLow = FirstStrLow(classNamePojo);
        StringBuilder str = new StringBuilder();
        str.append("\n/*\n").append(annotation).append("Controller接口\n").append("*/\n");
        str.append("@RestController\n" +
                "@RequestMapping(\"/" + FirstStrLow(className) + "\")");
        str.append("public class ").append(controllerName).append(" {");
        str.append("\n//").append(annotation).append("\n");
        str.append("@Autowired").append("\n");
        str.append("private ").append(classNameService).append(" ").append(FirstStrLow(classNameService)).append(";").append("\n");
        str.append("//新增\n").append("@PostMapping(\"add").append(className).append("\")").append("\n");
        String addMethod = "add" + classNamePojo;
        str.append("public Result<?> ").append(addMethod).append("(")
                .append("@RequestBody ")
                .append(classNamePojo).append(" ").append(classNamePojoLow).append("){\n")
                .append("return ").append(FirstStrLow(classNameService)).append(".").append(addMethod)
                .append("(").append(classNamePojoLow).append(");")
                .append("}\n")
        ;
        String deleteMethod = "delete" + classNamePojo;
        str.append("//删除\n").append("@PostMapping(\"delete").append(className).append("\")").append("\n");
        str.append(" public Result<?> ").append(deleteMethod).append("(")
                .append("@RequestBody ")
                .append(classNamePojo).append(" ").append(classNamePojoLow).append("){\n")
                .append("return ").append(FirstStrLow(classNameService)).append(".").append(deleteMethod)
                .append("(").append(classNamePojoLow).append(");")
                .append("}\n")
        ;
        String updateMethod = "update" + classNamePojo;
        str.append("//修改\n").append("@PostMapping(\"update").append(className).append("\")").append("\n");
        str.append("public Result<?> ").append(updateMethod).append("(")
                .append("@RequestBody ")
                .append(classNamePojo).append(" ").append(classNamePojoLow).append("){\n")
                .append("return ").append(FirstStrLow(classNameService)).append(".").append(updateMethod)
                .append("(").append(classNamePojoLow).append(");")
                .append("}\n")
        ;
        String selListMethod = "get" + classNamePojo + "List";
        str.append(" //查询\n").append("@PostMapping(\"get").append(className).append("List\")").append("\n");
        str.append("public Result<List<").append(classNamePojo).append(">> ")
                .append(selListMethod).append("( ")
                .append("@RequestBody ")
                .append(classNamePojo).append(" ")
                .append(classNamePojoLow).append("){\n")
                .append("return ").append(FirstStrLow(classNameService)).append(".").append(selListMethod)
                .append("(").append(classNamePojoLow).append(");")
                .append("}\n")
        ;
        String selPageMethod = "get" + classNamePojo + "ListPage";
        str.append("//分页查询\n").append("@PostMapping(\"get").append(className).append("ListPage\")").append("\n");
        str.append("public Result<Page<").append(classNamePojo).append(">> ").append(selPageMethod).append("(")
                .append("@RequestBody ")
                .append(classNamePojo).append(" ").append(classNamePojoLow).append("){\n")
                .append("return ").append(FirstStrLow(classNameService)).append(".").append(selPageMethod)
                .append("(").append(classNamePojoLow).append(");")
                .append("}\n");
        str.append("}");
        return str.toString();
    }

    /**
     * 生成条件方法
     *
     * @param className
     * @param objects
     * @param annotation
     * @return
     */
    public String generateServiceImplForLambda(String className, JSONArray objects, String annotation) {
        StringBuilder str = new StringBuilder();
        String classNamePojo = className + "Pojo";
        //MenuExist(MenuPojo menuPojo) {
        String lowNamePojo = FirstStrLow(className);
        str.append("\n/*\n").append(annotation).append("条件参数\n").append("*/\n");
        //menuPojoLambdaQueryWrapper
        str.append("public LambdaQueryWrapper<").append(classNamePojo).append("> ").append(lowNamePojo).append("LambdaQueryWrapper(").append(classNamePojo).append(" ").append(lowNamePojo).append(")\n").append("{\n");
        str.append(" LambdaQueryWrapper<").append(classNamePojo).append("> ").append(lowNamePojo).append("LambdaQueryWrapper = new LambdaQueryWrapper<>();\n");
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
            String fieldName = jsonObject.getString("COLUMNNAME");
            String newFieldName = FirstStrUpp(HumpNaming(fieldName));
            str.append("\n if (").append(lowNamePojo).append(".get").append(newFieldName).append("() != null ");
            if (!newFieldName.equals("CreateTime") && !newFieldName.equals("UpdateTime")) {
                str.append("&& !")
                        .append(lowNamePojo)
                        .append(".get")
                        .append(newFieldName)
                        .append("().isEmpty() ");

            }
            str.append(") {\n").append(lowNamePojo).append("LambdaQueryWrapper.eq(").append(classNamePojo).append("::get").append(newFieldName).append(",").append(lowNamePojo).append(".get").append(newFieldName).append("()").append(");");
            str.append("}\n");
        }
        str.append("return ");
        str.append(lowNamePojo).append("LambdaQueryWrapper;");
        str.append("\n}");
        return str.toString();
    }

    /**
     * 生成数据校验方法
     *
     * @param className
     * @param objects
     * @param annotation
     * @return
     */
    public String generateServiceImplForVerify(String className, JSONArray objects, String annotation) {
        StringBuilder str = new StringBuilder();
        String classNamePojo = className + "Pojo";
        //MenuExist(MenuPojo menuPojo) {
        String lowNamePojo = FirstStrLow(className);
        str.append("/**\n" +
                "     * " + annotation + "参数校验\n" +
                "     * @param " + lowNamePojo + " 实体\n" +
                "     * @param type 操作类型 0 新增 1 删除 2修改\n" +
                "     * @return 标志\n" +
                "     */");
        //menuPojoLambdaQueryWrapper
        str.append("public Boolean ").append(lowNamePojo).append("Verify (").append(classNamePojo).append(" ").append(lowNamePojo)
                .append(",SysEnum type")
                .append(")\n").append("{\n");
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
            String fieldName = jsonObject.getString("COLUMNNAME");
            String newFieldName = FirstStrUpp(HumpNaming(fieldName));
            str.append("\n if (").append(lowNamePojo).append(".get").append(newFieldName).append("() == null");
            if (i == 0) {
                str.append(" && (type.getSysCodeInt().equals(SysEnum.DELETE.getSysCodeInt()) || type.getSysCodeInt().equals(SysEnum.UPDATE.getSysCodeInt()))");
            }
            str.append(") {\n return false;\n");
            str.append("}\n");
        }
        str.append("return true;");
        str.append("\n}");
        return str.toString();
    }

    /**
     * 生成数据校验方法
     *
     * @param className
     * @param objects
     * @param annotation
     * @return
     */
    public String generateServiceImplForVerifyS(String className, JSONArray objects, String annotation) {
        StringBuilder str = new StringBuilder();
        String classNamePojo = className + "Pojo";
        //MenuExist(MenuPojo menuPojo) {
        String lowNamePojo = FirstStrLow(className);
        str.append("/**\n" +
                "     * " + annotation + "参数校验\n" +
                "     * @param " + lowNamePojo + " 实体\n" +
                "     * @param type 操作类型 0 新增 1 删除 2修改\n" +
                "     * @return 标志\n" +
                "     */");
        //menuPojoLambdaQueryWrapper
        str.append("public String ").append(lowNamePojo).append("VerifyS (").append(classNamePojo).append(" ").append(lowNamePojo)
                .append(",SysEnum type")
                .append(")\n").append("{\n");
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
            String fieldName = jsonObject.getString("COLUMNNAME");
            String fieldSm = jsonObject.getString("COMMENTS");
            String newFieldName = FirstStrUpp(HumpNaming(fieldName));
            if (!newFieldName.equals("CreateTime") && !newFieldName.equals("UpdateTime")) {
                str.append("\n if ((")
                        .append(lowNamePojo)
                        .append(".get").append(newFieldName)
                        .append("() == null");
                str.append(" || ")
                        .append(lowNamePojo)
                        .append(".get").append(newFieldName).append("()")
                        .append(".isEmpty())");
                if (i == 0) {
                    str.append(" && (type.getSysCodeInt().equals(SysEnum.DELETE.getSysCodeInt()) || type.getSysCodeInt().equals(SysEnum.UPDATE.getSysCodeInt()))");
                }
                str.append(") {\n return \"").append(fieldSm).append("为空\";\n");
                str.append("}\n");
            }
        }
        str.append("return ExceptionEnum.CODE_CZ.getResultCode();");
        str.append("\n}");
        return str.toString();
    }


    public String generateServiceImplImpl(String className, JSONArray objects, String annotation) {
        String classNameService = className + "Service";
        String classNamePojo = className + "Pojo";
        String classNameMapper = className + "Mapper";
        String classNamePojoLow = FirstStrLow(className);
        StringBuilder str = new StringBuilder();
        str.append("//新增\n" + "    public Result<?> add")
                .append(classNamePojo)
                .append("(")
                .append(classNamePojo)
                .append(" ")
                .append(FirstStrLow(classNamePojo))
                .append("){\n")
                .append("return serviceUtils.insert(")
                .append(FirstStrLow(classNameMapper)).append(",")
                .append(FirstStrLow(classNamePojo)).append(",")
                .append(classNamePojoLow).append("VerifyS(").append(FirstStrLow(classNamePojo)).append(",SysEnum.SAVE),")
                .append(classNamePojoLow).append("LambdaQueryWrapper(").append(FirstStrLow(classNamePojo)).append(")")
                .append(");\n}\n")
        ;
        str.append("//删除\n" + "    public Result<?> delete")
                .append(classNamePojo).append("(")
                .append(classNamePojo).append(" ")
                .append(FirstStrLow(classNamePojo))
                .append("){\n")
                .append("return serviceUtils.delete(")
                .append(FirstStrLow(classNameMapper)).append(",")
                .append(FirstStrLow(classNamePojo)).append(",")
                .append(classNamePojoLow).append("VerifyS(").append(FirstStrLow(classNamePojo)).append(",SysEnum.DELETE)")
                .append(");\n}\n")
        ;
        str.append("//修改\n" + "    public Result<?> update")
                .append(classNamePojo).append("(")
                .append(classNamePojo).append(" ")
                .append(FirstStrLow(classNamePojo))
                .append("){\n")
                .append("return serviceUtils.update(")
                .append(FirstStrLow(classNameMapper)).append(",")
                .append(FirstStrLow(classNamePojo)).append(",")
                .append(classNamePojoLow).append("VerifyS(").append(FirstStrLow(classNamePojo)).append(",SysEnum.UPDATE)")
                .append(");\n}\n")
        ;
        str.append(" //查询\n" + "    public Result<List<")
                .append(classNamePojo).append(">> get")
                .append(classNamePojo)
                .append("List( ")
                .append(classNamePojo)
                .append(" ").append(FirstStrLow(classNamePojo))
                .append("){\n")
                .append("return serviceUtils.selectList(")
                .append(FirstStrLow(classNameMapper)).append(",")
                .append(classNamePojoLow).append("LambdaQueryWrapper(")
                .append(FirstStrLow(classNamePojo)).append(")")
                .append(");\n}\n")
        ;
        str.append("//分页查询\n" + "    public Result<Page<")
                .append(classNamePojo).append(">> get")
                .append(classNamePojo)
                .append("ListPage(").append(classNamePojo).append(" ").append(FirstStrLow(classNamePojo))
                .append("){\n")
                .append("return serviceUtils.selectPage(")
                .append(FirstStrLow(classNameMapper)).append(",")
                .append(classNamePojo).append(".class,")
                .append(FirstStrLow(classNamePojo)).append(".getPagePojo(),")
                .append(classNamePojoLow).append("LambdaQueryWrapper(")
                .append(FirstStrLow(classNamePojo)).append(")")
                .append(");\n}\n")
        ;
        return str.toString();
    }

    /**
     * 生成vueApi文件内容
     *
     * @return
     */
    public String generateVueApi(String className, JSONArray objects, String annotation) {
        StringBuilder str = new StringBuilder();
        /**
         * import request from "../../utils/request.js";
         * const baseUrl = 'sysModule'
         * export default {
         *     //新增
         *     addSysModule(data) {
         *         return request({
         *             url: `${baseUrl}/addSysModule`,
         *             method: 'post',
         *             data: data
         *         })
         *     },
         * }
         */
        str.append("import request from \"../../utils/request.js\";\n")
                .append("const baseUrl = '").append(FirstStrLow(className)).append("'\n");
        str.append("export default {\n");
        String addMethod = "add" + className;
        str.append("\t//新增\n")
                .append(addMethod).append("(data){\n")
                .append("return request({\n")
                .append("url:`${baseUrl}/").append(addMethod).append("`,\n")
                .append("method: 'post',\n")
                .append("data: data,\n")
                .append("})},\n")
        ;
        String delMethod = "delete" + className;
        str.append("\t//删除\n")
                .append(delMethod).append("(data){\n")
                .append("return request({\n")
                .append("url:`${baseUrl}/").append(delMethod).append("`,\n")
                .append("method: 'post',\n")
                .append("data: data,\n")
                .append("})},\n")
        ;
        String updMethod = "update" + className;
        str.append("\t//修改\n")
                .append(updMethod).append("(data){\n")
                .append("return request({\n")
                .append("url:`${baseUrl}/").append(updMethod).append("`,\n")
                .append("method: 'post',\n")
                .append("data: data,\n")
                .append("})},\n")
        ;
        String selMethod = "get" + className + "List";
        str.append("\t//查询\n")
                .append(selMethod).append("(data){\n")
                .append("return request({\n")
                .append("url:`${baseUrl}/").append(selMethod).append("`,\n")
                .append("method: 'post',\n")
                .append("data: data,\n")
                .append("})},\n")
        ;
        String selMethodPage = "get" + className + "ListPage";
        str.append("\t//分页查询\n")
                .append(selMethodPage).append("(data){\n")
                .append("return request({\n")
                .append("url:`${baseUrl}/").append(selMethodPage).append("`,\n")
                .append("method: 'post',\n")
                .append("data: data,\n")
                .append("})},\n");
        str.append("}\n");
        return str.toString();
    }

    public String getSql(String name) {
        String sql = String.format("SELECT C.TABLE_NAME  tableName,\n" + "       C.COLUMN_NAME columnName,\n" + "       C.DATA_TYPE   dataType,\n" + "       C.CHAR_LENGTH CHARLENGTH,\n" + "       C.NULLABLE,\n" + "       D.COMMENTS,\n" + "       C.OWNER\n" + "  FROM ALL_TAB_COLUMNS C\n" + "  JOIN all_col_comments d\n" + "    ON C.COLUMN_NAME = D.COLUMN_NAME\n" + "   AND C.TABLE_NAME = D.TABLE_NAME\n" + " WHERE 1 = 1\n" + "AND C.TABLE_NAME = UPPER('%s')\n" + "order by c.COLUMN_ID ", name);
        return sql;
    }

    public String getSql2() {
        String sql = "SELECT\n" + "DISTINCT\n" + "A.USERKENNO \"keyno\",\n" + "A.ZGH \"zgh\",\n" + "A.XM \"xm\",\n" + "A.PASSWORD \"password\",\n" + "a.Roleid \"roleid\"\n" + "FROM " + "DIC_USER A\n" + "LEFT JOIN sys_role B ON A.ROLEID=B.id\n" + "WHERE 1=1 ";
        return sql;
    }

    public String getsql3() {
        String s = FileUtils.readFile(path);
        return s;
    }

    /**
     * 驼峰命名处理 去除空格
     *
     * @param fieldName
     * @return
     */
    public String HumpNaming(String fieldName) {
        //驼峰命名处理
        String[] s = fieldName.split("_");
        StringBuilder newFieldName = new StringBuilder();
        if (s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                String newStr = s[i].toLowerCase();
                if (i == 0) {
                    newFieldName.append(newStr);
                } else {
                    String upperCase = newStr.substring(0, 1).toUpperCase();
                    upperCase = upperCase + newStr.substring(1);
                    newFieldName.append(upperCase);
                }
            }
        }
        return newFieldName.toString();

    }

    /**
     * 首字母大写
     *
     * @param fieldName
     * @return
     */
    public String FirstStrUpp(String fieldName) {
        String firstName = fieldName.substring(0, 1).toUpperCase();
        String newName = fieldName.substring(1);
        return (firstName + newName).trim();
    }

    /**
     * 首字母小写
     *
     * @param fieldName
     * @return
     */
    public String FirstStrLow(String fieldName) {
        String firstName = fieldName.substring(0, 1).toLowerCase();
        String newName = fieldName.substring(1);
        return (firstName + newName).trim();
    }

    /**
     * 驼峰命名处理 去除空格
     *
     * @param fieldName
     * @return
     */
    public String HumpNaming2(String fieldName) {
        //驼峰命名处理
        String[] s = fieldName.split("_");
        StringBuilder newFieldName = new StringBuilder();
        if (s.length > 0) {
            for (int i = 0; i < s.length; i++) {
                String newStr = s[i].toLowerCase();
                if (i == 0) {
                    newFieldName.append(newStr);
                } else {
                    String upperCase = newStr.substring(0, 1).toUpperCase();
                    upperCase = upperCase + newStr.substring(1);
                    newFieldName.append(upperCase);
                }
            }
        }
        return newFieldName.toString().trim();
    }

}

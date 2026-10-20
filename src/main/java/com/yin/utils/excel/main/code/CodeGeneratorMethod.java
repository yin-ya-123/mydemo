package com.yin.utils.excel.main.code;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

/**
 * @author yin
 * @date 2024-12-26
 * @Description 具体实现方法
 */
public class CodeGeneratorMethod {
    //生成el-table
    public static void generateTable(String className, JSONArray objects, String annotation, String prefixPath) {
        for (Object object : objects) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
            //判断类型
            String fieldType = jsonObject.getString("DATATYPE");
            String fieldName = jsonObject.getString("COLUMNNAME");
            String fieldSm = jsonObject.getString("COMMENTS");
//             <el-table-column align="center" width="160" label="违规目录ID" prop="jsdjh"/>
            StringBuilder str = new StringBuilder();
            str.append("<el-table-column align=\"center\" width=\"160\"");
            str.append(" label=\"").append(fieldSm).append("\"");
            str.append(" prop=\"").append(HumpNaming(fieldName)).append("\"");
            str.append("/>");
            System.out.println(str.toString());
        }

    }

    //生成实体类
    public static String generatePojo(String className, JSONArray objects, String annotation, String prefixPath) {
        className = className + "Pojo";
        StringBuilder str = new StringBuilder();
        String owner = JSONObject.parseObject(objects.get(0).toString()).getString("OWNER");
        String tablename = JSONObject.parseObject(objects.get(0).toString()).getString("TABLENAME");
        str.append("\n/*\n").append(annotation).append("实体类\n").append("*/\n");
        str.append("package com.yin.pojo.").append(prefixPath).append(";\n");
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

    //生成mapper
    public static String generateMapper(String className, JSONArray objects, String annotation, String prefixPath) {
        String classNameMapper = className + "Mapper";
        String classNamePojo = className + "Pojo";
        StringBuilder str = new StringBuilder();
        str.append("package com.yin.mapper.").append(prefixPath).append(";\n");
        str.append("\n/*\n").append(annotation).append("mapper接口\n").append("*/\n");
        str.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" + "import org.apache.ibatis.annotations.Mapper;\n" + "import org.apache.ibatis.annotations.Param;\n" + "@Mapper\n");
        str.append("public interface ").append(classNameMapper).append(" extends BaseMapper<").append(classNamePojo).append(">{\n").append("}");
        return str.toString();
    }

    /**
     * 生成mapper.xml
     * @param className 类名
     * @param objects 字段
     * @param annotation 注释
     * @param prefixPath 前缀路径
     * @return
     */
    public static String generateMapperXml(String className, JSONArray objects, String annotation, String prefixPath) {
        String classNameMapper = className + "Mapper";
        String namespace = "com.yin.mapper." + prefixPath + "." + classNameMapper;
        StringBuilder str = new StringBuilder();
        str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n")
                .append("<mapper namespace=\"").append(namespace).append("\">\n");
        str.append("</mapper>");
        return str.toString();
    }

    //生成service
    public static String generateService(String className, JSONArray objects, String annotation, String prefixPath) {
        String classNameService = className + "Service";
        String classNamePojo = className + "Pojo";
        String classNamePojoLow = FirstStrLow(classNamePojo);
        StringBuilder str = new StringBuilder();
        str.append("package com.yin.service.").append(prefixPath).append(";\n");
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

    //生成serviceImpl
    public static String generateServiceImpl(String className, JSONArray objects, String annotation, String prefixPath) {
        String classNameMapper = className + "Mapper";
        String classNameService = className + "Service";
        String classNameServiceImpl = className + "ServiceImpl";
        String classNamePojo = className + "Pojo";
        StringBuilder str = new StringBuilder();
        str.append("package com.yin.service.").append(prefixPath).append(".impl").append(";\n");
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

    //生成controller
    public static String generateController(String className, JSONArray objects, String annotation, String prefixPath) {
        String classNameService = className + "Service";
        String controllerName = className + "Controller";
        String classNamePojo = className + "Pojo";
        String classNamePojoLow = FirstStrLow(classNamePojo);
        StringBuilder str = new StringBuilder();
        str.append("package com.yin.controller.").append(prefixPath).append(";\n");
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
    public static String generateServiceImplForLambda(String className, JSONArray objects, String annotation) {
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
    public static String generateServiceImplForVerify(String className, JSONArray objects, String annotation) {
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
    public static String generateServiceImplForVerifyS(String className, JSONArray objects, String annotation) {
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


    public static String generateServiceImplImpl(String className, JSONArray objects, String annotation) {
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
    public static String generateVueApi(String className, JSONArray objects, String annotation, String prefixPath) {
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

    /**
     * 驼峰命名处理
     *
     * @param fieldName
     * @return
     */
    public static String HumpNaming(String fieldName) {
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
    public static String FirstStrUpp(String fieldName) {
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
    public static String FirstStrLow(String fieldName) {
        String firstName = fieldName.substring(0, 1).toLowerCase();
        String newName = fieldName.substring(1);
        return (firstName + newName).trim();
    }

    /**
     * 驼峰命名处理
     *
     * @param fieldName
     * @return
     */
    public static String HumpNaming2(String fieldName) {
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

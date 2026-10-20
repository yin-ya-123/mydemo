package mybatis_plus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther: YIN
 * @DescriPtion:
 * @Date:2024-01-29/01/29/15:26
 */
public class Generator {
    String path = "D:\\cs\\ssxjg\\myDemo";
    public static void main (String[] args) {
        String path = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java";
        DataSourceConfig.Builder schema = new DataSourceConfig.Builder("jdbc:oracle:thin:@192.168.1.111:1521/HIS", "his", "DBa54321")
                //.schema("DICTMANAGE")//指定数据库用户
                ;
        FastAutoGenerator.create(schema)
                .globalConfig(builder -> {
                    builder.disableOpenDir() //
                            .author("YIN") //作者名
                            .outputDir(path) //指定输出目录
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder ->{
                    builder.parent("com.yin")
                            .moduleName("")
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, path+"/com/yin/mapper/xml")); //设置xml生成路径

                })
                .strategyConfig(builder -> {
                    builder.addInclude("GLOBAL_TEMP_TABLE_NAME_MB2,GLOBAL_TEMP_TABLE_NAME_MB3,GLOBAL_TEMP_TABLE_NAME_MB4") //设置需要映射的表名
                            .addTablePrefix("t_","c_"); //设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) //使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

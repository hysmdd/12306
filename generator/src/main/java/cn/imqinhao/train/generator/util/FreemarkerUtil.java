package cn.imqinhao.train.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author Martis
 * @create 2023-08-10 09:31:16
 */
public class FreemarkerUtil {

    static String ftlPath = "generator\\src\\main\\java\\cn\\imqinhao\\train\\generator\\ftl\\";
    static Template template;

    public static void initConfig(String ftlName) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_31));
        template = configuration.getTemplate(ftlName);
    }

    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        template.process(map, bufferedWriter);
        bufferedWriter.flush();
        fileWriter.close();
    }

}

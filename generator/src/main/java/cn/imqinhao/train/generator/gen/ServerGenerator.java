package cn.imqinhao.train.generator.gen;

import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Martis
 * @create 2023-08-10 09:44:59
 */
public class ServerGenerator {

    static String toPath = "generator/src/main/java/cn/imqinhao/train/generator/test/";
    static String pomPath = "generator/pom.xml";

    static {
        new File(toPath).mkdirs();
    }

    public static void main(String[] args) throws TemplateException, IOException, DocumentException {
        // FreemarkerUtil.initConfig("test.ftl");
        // HashMap<String, Object> param = new HashMap<>();
        // param.put("domain", "Test");
        // param.put("package", "test");
        // FreemarkerUtil.generator(toPath + "Test.java", param);
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<>();
        map .put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
    }

}

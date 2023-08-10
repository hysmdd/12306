package cn.imqinhao.train.generator.gen;

import cn.imqinhao.train.generator.util.FreemarkerUtil;
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

    static String servicePath = "[module]/src/main/java/cn/imqinhao/train/[module]/service/";
    static String pomPath = "generator/pom.xml";

    static {
        new File(servicePath).mkdirs();
    }

    public static void main(String[] args) throws TemplateException, IOException, DocumentException {
        // FreemarkerUtil.initConfig("test.ftl");
        // HashMap<String, Object> param = new HashMap<>();
        // param.put("domain", "Test");
        // param.put("package", "test");
        // FreemarkerUtil.generator(toPath + "Test.java", param);
        // 获取mybatis-generator
        String generatorPath = getGeneratorPath();
        String module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        System.out.println("module: " + module);
        servicePath = servicePath.replace("[module]", module);
        System.out.println("servicePath: " + servicePath);
        // 读取table节点
        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        System.out.println(table);
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());

        // 示例：表名 martis_test
        // Domain = MartisTest
        String Domain = domainObjectName.getText();
        // domain = martisTest
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        // do_main = martis-test
        String do_main = tableName.getText().replaceAll("_", "-");

        // 组装参数
        Map<String, Object> param = new HashMap<>();
        param.put("Domain", Domain);
        param.put("domain", do_main);
        param.put("do_main", do_main);
        System.out.println("组装参数：" + param);

        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(servicePath + Domain + "Service.java", param);
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<>();
        map .put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        return node.getText();
    }

}

package cn.imqinhao.train.generator.gen;

import cn.imqinhao.train.generator.util.DbUtil;
import cn.imqinhao.train.generator.util.Field;
import cn.imqinhao.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Martis
 * @create 2023-08-10 09:44:59
 */
public class ServerGenerator {

    static boolean readOnly = false;
    static String vuePath = "admin/src/views/main/";
    static String serverPath = "[module]/src/main/java/cn/imqinhao/train/[module]/";
    static String pomPath = "generator/pom.xml";
    static String module = "";

    public static void main(String[] args) throws Exception {
        // FreemarkerUtil.initConfig("test.ftl");
        // HashMap<String, Object> param = new HashMap<>();
        // param.put("domain", "Test");
        // param.put("package", "test");
        // FreemarkerUtil.generator(toPath + "Test.java", param);
        // 获取mybatis-generator
        String generatorPath = getGeneratorPath();
        module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        System.out.println("module: " + module);
        serverPath = serverPath.replace("[module]", module);
        System.out.println("servicePath: " + serverPath);

        // 读取table节点
        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        System.out.println(table);
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());

        // 为DbUtil设置数据源
        Node connectionURL = document.selectSingleNode("//@connectionURL");
        Node userId = document.selectSingleNode("//@userId");
        Node password = document.selectSingleNode("//@password");
        System.out.println("url: " + connectionURL.getText());
        System.out.println("user: " + userId.getText());
        System.out.println("password: " + password.getText());
        DbUtil.url = connectionURL.getText();
        DbUtil.user = userId.getText();
        DbUtil.password = password.getText();

        // 示例：表名 martis_test
        // Domain = MartisTest
        String Domain = domainObjectName.getText();
        // domain = martisTest
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        // do_main = martis-test
        String do_main = tableName.getText().replaceAll("_", "-");
        // 表中文名
        String tableNameCn = DbUtil.getTableComment(tableName.getText());
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName.getText());
        Set<String> typeSet = getJavaTypes(fieldList);

        // 组装参数
        Map<String, Object> param = new HashMap<>();
        param.put("module", module);
        param.put("Domain", Domain);
        param.put("domain", domain);
        param.put("do_main", do_main);
        param.put("tableNameCn", tableNameCn);
        param.put("fieldList", fieldList);
        param.put("typeSet", typeSet);
        param.put("readOnly", readOnly);
        System.out.println("组装参数：" + param);

        generate(Domain, param, "service", "service");
        generate(Domain, param, "controller/admin", "adminController");
        generate(Domain, param, "req", "saveReq");
        generate(Domain, param, "req", "queryReq");
        generate(Domain, param, "resp", "queryResp");
        generateVue(do_main, param);
    }

    /**
     * 获取所有的Java类型，使用Set去重
     * @author :Martis
     * @create :2023-08-10 14:50:00
     * @param fieldList
     * @return
     */
    public static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }

    public static void generateVue(String do_main, Map<String, Object> param) throws TemplateException, IOException {
        FreemarkerUtil.initConfig("vue.ftl");
        new File(vuePath + module).mkdirs();
        String fileName = vuePath + module + "/" + do_main + ".vue";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }

    private static void generate(String Domain, Map<String, Object> param, String packageName, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        String toPath = serverPath + packageName + "/";
        new File(toPath).mkdirs();
        String fileName = toPath + Domain + Target + ".java";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
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

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMakerTest {

    /**
     * 获取模板信息
     *
     * @param name 模板名
     * @return
     */
    public String getTemplate(String name, Map<String, Object> map) throws Exception {
        //通过freemarker Configuration读取相应的ftl
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        //设定去哪里读取相应的ftl模板文件，指定模板路径
        cfg.setClassForTemplateLoading(this.getClass(), "/freeMarker/template");

        //在模板文件目录中找到名称为name的文件
        Template template = cfg.getTemplate(name, "utf-8");
        StringWriter result = new StringWriter();
        template.process(map, result);
        return result.toString();
//        System.out.println(FreeMarkerTemplateUtils.processTemplateIntoString(template, map));
//        return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

    }

    public static void main(String[] args) {
        FreeMakerTest freemakerUtil = new FreeMakerTest();
        Map map = new HashMap();
        map.put("username", "AAA");
        map.put("candidate", "BBB");
        map.put("job", "java");
        map.put("date", "2018/06/07");
        map.put("description", "qwert");
        map.put("address", "天下名企汇");
        map.put("entrydate", "2018-06-20");
        map.put("attachmentUrl", "http://www.baidu.com");
        try {
            String template = freemakerUtil.getTemplate("mail.ftl", map);
            System.out.println(template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


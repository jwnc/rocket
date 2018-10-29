import com.wnc.string.PatternUtil;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OgnlTest {
    public static class User {
        private String username;

        public User(String username) {
            super();
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @Test
    public void a() throws OgnlException {
        Map<String, Object> root = new HashMap<String, Object>();
        Map context = Ognl.createDefaultContext(root);
        context.put("lucy", new User("lucy"));
        context.put("phone", 110);
        String phrase = "My name is #lucy.username, my phone is #phone.";
        parse(root, context, phrase);
    }

    private void parse(Map<String, Object> root, Map context, final String phrase) throws OgnlException {
        String tmp = phrase;
        System.out.println(tmp);
        while (true) {
            String exp = PatternUtil.getFirstPatternGroup(tmp, "#[a-zA-Z_\\d\\$\\.]+");
            if (StringUtils.isBlank(exp)) {
                break;
            }
            if (exp.endsWith(".")) {
                exp = exp.substring(0, exp.length() - 1);
            }
            Object expVal = Ognl.getValue(Ognl.parseExpression(exp), context, root);
            String preReplace = tmp;
            tmp = tmp.replaceFirst("(^.*?)(" + exp + ")(.*?$)", "$1" + expVal + "$3");
            System.out.println(tmp);
            if (tmp.equals(preReplace)) {
                break;
            }
        }
    }
}

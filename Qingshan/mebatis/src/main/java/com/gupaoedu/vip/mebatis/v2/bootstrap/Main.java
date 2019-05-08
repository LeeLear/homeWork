package com.gupaoedu.vip.mebatis.v2.bootstrap;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/5/6 16:41
 * @description：
 * @modified By：
 * @version: $
 */
public class Main {
    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");
        for (String s : resourceBundle.keySet()) {
            String value = resourceBundle.getString(s);
            String sql = value.split("--")[0];
            String pojo = value.split("--")[1];

            Pattern pattern =Pattern.compile("#\\{[^\\}]+\\}",// ^ 匹配输入字符串的开始位置，除非在方括号表达式中使用，此时它表示不接受该字符集合。也就是说，在{}之间，不能存在}
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);

            while (matcher.find()){
                String paramName = matcher.group();
                int n = matcher.groupCount();
                System.out.println(n);
                System.out.println(paramName);
            }

            System.out.println(sql+" ,"+pojo);
            System.out.println(s);
        }
    }
}

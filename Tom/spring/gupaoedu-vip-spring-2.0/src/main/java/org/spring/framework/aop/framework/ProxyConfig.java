package org.spring.framework.aop.framework;

import org.spring.framework.utils.StringUtils;
import sun.security.jca.GetInstance;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/4/24 9:38
 * @description：
 * @modified By：
 * @version: $
 */
public class ProxyConfig {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;

    private final String AOP_NAME= "spring-aop.properties";

    public final static ProxyConfig instance = new ProxyConfig();

    public ProxyConfig() {



        Properties properties = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("")) {
            properties.load(is);
            if (properties.containsKey(AOP_NAME)) {
                try (InputStream is2 = this.getClass().getClassLoader().getResourceAsStream(AOP_NAME)) {
                    properties.load(is2);
                    setPointCut(properties.getProperty("pointCut"));
                    setAspectClass(properties.getProperty("aspectClass"));
                    setAspectBefore(properties.getProperty("aspectBefore"));
                    setAspectAfter(properties.getProperty("aspectAfter"));
                    setAspectAfterThrow(properties.getProperty("aspectAfterThrow"));
                    setAspectAfterThrowingName(properties.getProperty("aspectAfterThrowingName"));
                    pointCut = pointCut
                            .replaceAll("\\.","\\\\.")
                            .replaceAll("\\\\.\\*",".*")
                            .replaceAll("\\(","\\\\(")
                            .replaceAll("\\)","\\\\)");
                }
            }else {
                System.out.println("并没有找到aop文件");
            }
        } catch (IOException e) {
            System.out.println("");
        }

    }


    public final ProxyConfig getInstance(){
        return instance;
    }

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectAfterThrowingName() {
        return aspectAfterThrowingName;
    }

    public void setAspectAfterThrowingName(String aspectAfterThrowingName) {
        this.aspectAfterThrowingName = aspectAfterThrowingName;
    }
}

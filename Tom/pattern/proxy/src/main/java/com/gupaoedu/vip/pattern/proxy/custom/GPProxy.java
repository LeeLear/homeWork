package com.gupaoedu.vip.pattern.proxy.custom;

import com.gupaoedu.vip.pattern.proxy.entity.MeiPo;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/16 13:09
 * @description：
 * @modified By：
 * @version: $
 */
public class GPProxy {
    public static final String ln="\r\n";
    public static Object newProxyInstance(GPClassLoader classLoader,Class<?>[] interfaces,GPInvocationHandler h){
        try {
            //1、动态生成源代码.java文件
            String src = generateSrc(interfaces);
            System.out.println(src);
            //2、Java文件输出到磁盘
            String filePath = GPProxy.class.getResource("").getPath();
            System.out.println(filePath);
            File f = new File(filePath+"$Proxy0.java");
            FileWriter fileWriter=new FileWriter(f);
            fileWriter.write(src);
            fileWriter.flush();
            fileWriter.close();
            //3、把生成的.java文件编译成.class文件
            JavaCompiler compiler= ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
            Iterable iterable = manager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task=compiler.getTask(null,manager,null,null,null,iterable);
            task.call();
            manager.close();

            //4、编译生成的.class文件加载到JVM中来
            Class proxyClass = classLoader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(GPInvocationHandler.class);
            f.delete();

            //5、返回字节码重组以后的新的代理对象
            return c.newInstance(h);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(GPProxy.class.getPackage()+";"+ln);
        for (Class<?> anInterface : interfaces) {
            stringBuilder.append("import "+anInterface.getName()+";"+ln);
        }

        stringBuilder.append("import java.lang.reflect.Method;" + ln);
        stringBuilder.append("public class $Proxy0 implements ");
        int n=interfaces.length;
        int i=0;
        for (Class<?> anInterface : interfaces) {
            i++;
            stringBuilder.append(anInterface.getSimpleName());
            if (i<n){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("{"+ln);
        stringBuilder.append("      GPInvocationHandler h;"+ln);
        stringBuilder.append("      public $Proxy0(GPInvocationHandler h){"+ln);
        stringBuilder.append("              this.h=h;"+ln);
        stringBuilder.append("      }"+ln);
        for (Class<?> anInterface : interfaces) {
            for (Method method : anInterface.getMethods()) {
                stringBuilder.append("      public "+method.getReturnType().getName()+" "+method.getName()+"(){"+ln);
                stringBuilder.append("              try{"+ln);
                stringBuilder.append("                      Method m ="+anInterface.getName()+".class.getMethod(\""+method.getName()+"\",new Class[]{});"+ln);
                stringBuilder.append("                      this.h.invoke(this,m,null);"+ln);
                stringBuilder.append("              }catch(Throwable e){"+ln);
                stringBuilder.append("                      e.printStackTrace();"+ln);
                stringBuilder.append("              }"+ln);
                stringBuilder.append("      }"+ln);

            }
        }
        stringBuilder.append("}"+ln);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(GPProxy.class.getPackage());
    }
}

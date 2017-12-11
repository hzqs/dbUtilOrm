package util;

import sun.net.www.protocol.jar.JarURLConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by hzq on 2017/12/7.
 *
 * 扫描文件
 */
public class ScanUtil {

      //获取当前项目的绝对路径
    private static final Set<String> classNames=new HashSet<String>();

    //获取指定包下面以及子包中所有的类
    public static Set<String> scan(String packageName){
        if(packageName==null){
            throw new RuntimeException("The path can not be null");
        }
        String packagePath=packageName.replace(".","/");
        ClassLoader loader=Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls=loader.getResources(packagePath);
            while(urls.hasMoreElements()){
                URL url=urls.nextElement();
                if("file".equals(url.getProtocol())){
                    scanFromDir(url.getPath(),packageName);
                }
                if("jar".equals(url.getProtocol())){
                    JarURLConnection connection=(JarURLConnection)url.openConnection();
                    scanFromJar(connection.getJarFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Resolve path error.", e);
        }
         return classNames;
    }

    //从项目文件获取某包下所有类
    private static void scanFromDir(String filePath,String packageName){
        File[] files=new File(filePath).listFiles();
        packageName=packageName+".";
        for(File childFile:files){
            if(childFile.isDirectory()){
                //递归
                scanFromDir(childFile.getPath(),packageName+childFile.getName());
            }else{
                String fileName=childFile.getName();
                if(fileName.endsWith(".class")){
                    if(packageName.charAt(0)=='.'){
                        packageName=packageName.substring(1,packageName.length());
                    }
                    String className=packageName+fileName.replace(".class","");
                   classNames.add(className);
                }
            }
        }
    }

    //扫描文件
    private static void scanFromJar(JarFile jarFile){
      Enumeration<JarEntry> files=jarFile.entries();
      while(files.hasMoreElements()){
          JarEntry entry=files.nextElement();
          if(entry.getName().endsWith(".class")){
              String className=entry.getName().replace("/",".").replace(".class","");
              classNames.add(className);
         }
      }
    }

    public static void main(String[] args) {
        Set<String> classNames=scan("dao");
        for(String className:classNames){
            System.out.print(className);
        }
    }











}

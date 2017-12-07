package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzq on 2017/12/7.
 *
 * 扫描文件
 */
public class ScanUtil {

      //获取当前项目的绝对路径
    private final static String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();//.replace("/","\\").substring(1);
     //定义集合存放类名
    private static List<String>list=new ArrayList<>();

    //根据记录输入的包名或类名扫描
    private static String gianUrl(String pathName){
       pathName=pathName.replace(".","\\");
        return pathName;
    }

    //通过这个方法进行扫描
    public static List<String> scan(String pathNames){
        String pathName=gianUrl(pathNames);
        readFile(path+pathName);
        return list;
    }

    //读取class文件信息
    private static void readFile(String path){
        File f=new File(path);
        File[] files=f.listFiles();
        if(files!=null){
            for(File file:files){
                //如果是文件就解析
                if(file.isFile()){
                //文件解析，路径
                    String className=resolveClass(file.getAbsolutePath());
                    list.add(className);
                }else{
                    //如果是目录,那么就执行递归
                    readFile(file.getAbsolutePath());
                }
            }
        }


    }

    //文件解析
    private static String resolveClass(String classPath){
            String className=classPath.substring(path.length(),classPath.length());
            className=className.replace(".class","");
            return className;
    }

    public static void main(String[] args) {
        System.out.println(path);
    }




}

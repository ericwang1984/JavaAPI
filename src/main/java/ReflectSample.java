import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by wanghuanxi on 30/04/2017.
 */
public class ReflectSample <T>{

    private String classpath = "";

    private String fieldList ="";

    private Map<Class,List<Method>>  reportMap = new HashMap<>();


    public String getValues(List<T> objects,Class clazz){

       List<Method> callList = reportMap.get(clazz);

        StringBuffer record = new StringBuffer();
        for (Object o :objects) {
            StringBuffer sb = new StringBuffer();
            callList.forEach(method -> {
                try {
                    String a  = (String)method.invoke(o);
                    sb.append(a);
                    sb.append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            record.append(sb.toString().substring(0,sb.toString().lastIndexOf(","))).append("/r/n");
        }

        return record.toString();
    }





    private void initGetMethodMap (String classpath,String fieldList){
        try {
            Class clazz = Class.forName(classpath);
            Field[] fields  = clazz.getDeclaredFields();
            List<Method> callMethods = new  LinkedList<Method>();

            String[] reportFields = fieldList.split(",");

            for (String s:reportFields) {

                for (Field f:fields){
                    if(f.getName().equals(s)){
                     Method sMethod =   clazz.getMethod(parGetName(s),null);
                        callMethods.add(sMethod);
                        break;

                    }
                }
            }

            reportMap.put(clazz,callMethods);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }



    /**
     * 拼接某属性的 get方法
     * @param fieldName
     * @return String
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
    /**
     * 拼接在某属性的 set方法
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
}


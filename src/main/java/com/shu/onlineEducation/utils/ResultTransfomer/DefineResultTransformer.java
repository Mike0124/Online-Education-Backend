package com.shu.onlineEducation.utils.ResultTransfomer;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.transform.ResultTransformer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 自定义hinernate结果转换类
 */
public class DefineResultTransformer<T> implements ResultTransformer, Serializable {
    private static final long serialVersionUID = 775781526148172513L;

    private Class<T> resultClazz;
    private BeanUtilsBean beanUtilsBean;

    public DefineResultTransformer(Class<T>  resultClass){
        this.resultClazz = resultClass;
        this.beanUtilsBean = BeanUtilsBean.getInstance();
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object result = null;

        Field[] fields = resultClazz.getDeclaredFields();
        try {
            result = resultClazz.newInstance();
            for(int i =0 ;i<aliases.length; i++){
                for(Field field : fields){
//   1、忽略大小写映射，如SQL查询字段为 "username" 可直接映射为Java实体类中userName属性
//   2、去掉"_" 映射，如SQL查询出的字段为 "user_name" 也可直接映射为Java实体类中userName属性
                    if(field.getName().equalsIgnoreCase(aliases[i].replaceAll("_",""))){
                        beanUtilsBean.setProperty(result,field.getName(),tuple[i]);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}

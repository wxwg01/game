package com.game.dictionary;

import com.game.bean.ClassCode;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : wanggang
 * @description : <一句话描述类的作用/>
 * @email : wb-wg471966@alibaba-inc.com
 * @date : 2019/5/17
 */
public class ClassCodeDictionary {
    private static Map<Integer, ClassCode> classCodeMap = new ConcurrentHashMap<>();

    public boolean register(int code,String desc,Class clazz){
        return register(code, desc, clazz,true);
    }

    public boolean register(int code,String desc,Class clazz,boolean cover){
        ClassCode classCode = classCodeMap.get(code);
        if(!cover && classCode!=null){
            return false;
        }
        classCode = new ClassCode(code, desc, clazz);
        classCodeMap.put(code, classCode);
        return true;
    }

    public static int getCodeByBean(Class bean) {
        Collection<ClassCode> classCodes = classCodeMap.values();
        for (ClassCode classCode : classCodes) {
            if (classCode.getClazz().getName().equals(bean.getName())) {
                return classCode.getCode();
            }
        }
        return 0;
    }

    public static Class getBeanByCode(int code) {
        Collection<ClassCode> classCodes = classCodeMap.values();
        for (ClassCode classCode : classCodes) {
            if (classCode.getCode() == code) {
                return classCode.getClazz();
            }
        }
        return null;
    }
}

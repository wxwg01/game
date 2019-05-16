package com.game.server.freemarker;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.Template;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/14 16:17
 * @Since 0.0.1
 */
public class JaveBeanTemplate {
    //生成bean
    public static void main(String[] args) throws Exception {
        //获取模板
        String basicPath = "D:\\workspace\\boxing\\src\\main\\resources\\ftl";
        String ftlName = "javabean.ftl";
        Template temp = FreeMarkerInit.getInstance().getDefinedTemplate(basicPath, ftlName);

        String filePath = "D:\\workspace\\boxing\\src\\main\\resources\\packet\\packet.xml";

        File f = new File(filePath);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        Element root = doc.getRootElement();
        String version = root.attribute("version").getText();

        Element poo;


        String filePackage = "com.wg.server.biz.entity";
        String outPutPath = "D:\\workspace\\boxing\\src\\main\\java\\com\\wg\\server\\biz\\entity\\";

        //获取父目录
        File fileParent = new File(outPutPath);
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录文件
            fileParent.mkdirs();
        }

        Map<String, String> refTypeMap = new HashMap<>();

        List<Map> mapList = new ArrayList<>();

        for (Iterator i = root.elementIterator("packet"); i.hasNext(); ) {
            poo = (Element) i.next();
            String className = poo.attribute("name").getText();
            String id = poo.attribute("id").getText();
            String des = poo.attribute("des").getText();
            String type = poo.attribute("type").getText();


            Map<String, Object> map = new HashMap<String, Object>();
            Integer t = null;
            if (Optional.ofNullable(type).isPresent()) {
                t = Integer.parseInt(type);
            }
            if (t != null && t == 0) {
                map.put("packageName", filePackage + ".client");
                map.put("outPutPath", outPutPath + "client\\");
                //获取父目录
                fileParent = new File(outPutPath + "client\\");
                //判断是否存在
                if (!fileParent.exists()) {
                    //创建父目录文件
                    fileParent.mkdirs();
                }
            } else {
                map.put("packageName", filePackage + ".server");
                map.put("outPutPath", outPutPath + "server\\");
                //获取父目录
                fileParent = new File(outPutPath + "server\\");
                //判断是否存在
                if (!fileParent.exists()) {
                    //创建父目录文件
                    fileParent.mkdirs();
                }
            }
            map.put("className", className);
            map.put("classDesc", des);
            map.put("classCode", id);

            List<Attribute> attr_list = new ArrayList<>();
            refTypeMap.put(className, filePackage + "." + className);

            Element foo;

            List<String> imports = new ArrayList<>();

            for (Iterator j = poo.elementIterator("field"); j.hasNext(); ) {
                foo = (Element) j.next();
                org.dom4j.Attribute att;
                String attType = (att = foo.attribute("type")) == null ? null : att.getText();
                String attName = (att = foo.attribute("name")) == null ? null : att.getText();
                String attDes = (att = foo.attribute("des")) == null ? "" : att.getText();
                String attRef = (att = foo.attribute("refType")) == null ? null : att.getText();

                if (attType.equals("string")) {
                    attType = "String";
                } else if (attType.equals("refArray")) {
                    attType = "List";
                    if (!imports.contains("java.util.List")) {
                        imports.add("java.util.List");
                    }
                }

                if (Optional.ofNullable(attRef).isPresent()) {
                    if (attType.equals("List")) {
                        attType = attType + "<" + attRef + ">";
                    } else {
                        attType = attRef;
                    }

                    if (refTypeMap.containsKey(attRef)) {
                        imports.add(refTypeMap.get(attRef));
                    } else if (!imports.contains("missing_" + attRef)) {
                        imports.add("missing_" + attRef);
                    }
                }
                attr_list.add(new Attribute(attType, attName, attDes));
            }


            map.put("imports", imports);
            map.put("attrs", attr_list);

            mapList.add(map);
        }

        for (Iterator i = root.elementIterator("ref"); i.hasNext(); ) {
            poo = (Element) i.next();
            String className = poo.attribute("name").getText();

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("packageName", filePackage + ".server");
            map.put("outPutPath", outPutPath + "server\\");
            //获取父目录
            fileParent = new File(outPutPath + "server\\");
            //判断是否存在
            if (!fileParent.exists()) {
                //创建父目录文件
                fileParent.mkdirs();
            }

            map.put("className", className);
            map.put("classDesc", "");
            map.put("classCode", "");

            List<Attribute> attr_list = new ArrayList<>();
            refTypeMap.put(className, filePackage + ".server" + "." + className);

            Element foo;

            List<String> imports = new ArrayList<>();

            for (Iterator j = poo.elementIterator("field"); j.hasNext(); ) {
                foo = (Element) j.next();
                org.dom4j.Attribute att;
                String attType = (att = foo.attribute("type")) == null ? null : att.getText();
                String attName = (att = foo.attribute("name")) == null ? null : att.getText();
                String attDes = (att = foo.attribute("des")) == null ? "" : att.getText();
                String attRef = (att = foo.attribute("refType")) == null ? null : att.getText();

                if (attType.equals("string")) {
                    attType = "String";
                } else if (attType.equals("refArray")) {
                    attType = "List";
                    if (!imports.contains("java.util.ArrayList")) {
                        imports.add("java.util.ArrayList");
                    }
                }

                if (Optional.ofNullable(attRef).isPresent()) {
                    attType = attRef;
                    if (refTypeMap.containsKey(attType)) {
                        imports.add(refTypeMap.get(attType));
                    } else if (!imports.contains("missing_" + attType)) {
                        imports.add("missing_" + attType);
                    }
                }
                attr_list.add(new Attribute(attType, attName, attDes));
            }


            map.put("imports", imports);
            map.put("attrs", attr_list);

            mapList.add(map);
        }

        mapList.forEach(it -> {
            List<String> list = (List<String>) it.get("imports");
            if (Optional.ofNullable(list).isPresent()) {
                list = list.stream().map(item -> {
                    if (item.startsWith("missing_")) {
                        String ref = Arrays.asList(item.split("_")).get(1);
                        String aa = refTypeMap.get(ref);
                        return aa;
                    } else {
                        return item;
                    }
                }).collect(Collectors.toList());
            }
            it.put("imports", list);

            String outPutName = (String) it.get("className");
            String outPutPath1 = (String) it.get("outPutPath");

            String fileType = ".java";
            String str = outPutPath1 + outPutName + fileType;


            System.out.println(JSONObject.toJSONString(it));

            File ignored = new File(str);


            if (!ignored.exists()) {
                try {
                    ignored.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ignored.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (
                    OutputStream fos = new FileOutputStream(ignored);
                    Writer out = new OutputStreamWriter(fos);
            ) {
                temp.process(it, out);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        createCHandel(mapList);
        createCEnum(mapList);
    }


    public static void createCHandel(List<Map> mapList) throws Exception {
        //获取模板
        String basicPath = "D:\\workspace\\boxing\\src\\main\\resources\\ftl";
        String ftlName = "handel.ftl";
        Template temp = FreeMarkerInit.getInstance().getDefinedTemplate(basicPath, ftlName);


        String packageName = "com.wg.server.biz.handler.test";
        String outPutPath1 = "D:\\workspace\\boxing\\src\\main\\java\\com\\wg\\server\\biz\\handler\\test\\";

//获取父目录
        File fileParent = new File(outPutPath1);
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录文件
            fileParent.mkdirs();
        }

        mapList.forEach(it -> {

            String bean = (String) it.get("className");
            if (bean == null || !bean.startsWith("C")) return;

            String fileType = ".java";
            String str = outPutPath1 + bean + "Handler" + fileType;
            it.put("packageName", packageName);
            it.put("bean", bean);

            System.out.println(JSONObject.toJSONString(it));

            File ignored = new File(str);


            if (!ignored.exists()) {
                try {
                    ignored.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ignored.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try (
                    OutputStream fos = new FileOutputStream(ignored);
                    Writer out = new OutputStreamWriter(fos);
            ) {
                temp.process(it, out);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


    public static void createCEnum(List<Map> mapList) throws Exception {
        //获取模板
        String basicPath = "D:\\workspace\\boxing\\src\\main\\resources\\ftl";
        String ftlName = "cenum.ftl";
        Template temp = FreeMarkerInit.getInstance().getDefinedTemplate(basicPath, ftlName);

        String outPutPath1 = "D:\\workspace\\boxing\\src\\main\\java\\com\\wg\\server\\biz\\constant\\test\\";

        //获取父目录
        File fileParent = new File(outPutPath1);
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录文件
            fileParent.mkdirs();
        }

        String packageName = "com.wg.server.biz.constant.test";


        List<Map> list = new ArrayList<>();
        List<String> listImport = new ArrayList<>();
        mapList.stream().filter(item -> {
            String bean = (String) item.get("className");

            if (bean == null || !bean.startsWith("C")) return false;
            return true;
        }).forEach(item -> {
            Map map = new HashMap();
            // ${className?capitalize}((short)${code},${des}, ${className}Handler.class,${className}.class)<#if (en?size == (en_index +1))>,<#else >;</#if>
            map.put("bean", item.get("className"));
            map.put("code", item.get("classCode"));
            map.put("des", item.get("classDesc"));
            list.add(map);


        });

        Map map = new HashMap();
        map.put("packageName", packageName);
        map.put("enums", list);

        String str = outPutPath1 + "CEnumValue.java";

        File ignored = new File(str);


        if (!ignored.exists()) {
            try {
                ignored.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ignored.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try (
                OutputStream fos = new FileOutputStream(ignored);
                Writer out = new OutputStreamWriter(fos);
        ) {
            temp.process(map, out);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

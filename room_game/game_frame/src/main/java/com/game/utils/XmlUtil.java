package com.game.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.io.InputStream;

public class XmlUtil {
//	private static XStream xstream;
//	static {
//		xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
//	}

    /**
     * @param inputXml
     * @param type
     * @return
     * @throws Exception
     * @Description: xml字符串转换为对象
     * @author leechenxiang
     * @date 2017年8月31日 下午4:52:13
     */
    public static Object xml2Object(String inputXml, Class<?> type) {
        if (null == inputXml || "".equals(inputXml)) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
//        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(type);
        xstream.autodetectAnnotations(true);
        return xstream.fromXML(inputXml);

       /* Object xmlObject = null;
        XStream xstream = new XStream();
        xstream.processAnnotations(type);
        xstream.autodetectAnnotations(true);
        xmlObject= xstream.fromXML(inputXml);
        return xmlObject;*/

    }

    public static Object xml2Object(String inputXml, Class<?> type,String alias) {
        if (null == inputXml || "".equals(inputXml)) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.processAnnotations(type);
        xstream.autodetectAnnotations(true);
        xstream.alias(alias, type);
        return xstream.fromXML(inputXml);


    }

    /**
     * @param inputStream
     * @param type
     * @return
     * @throws Exception
     * @Description: 从inputStream中读取对象
     * @author leechenxiang
     * @date 2017年8月31日 下午4:52:29
     */
    public static Object xml2Object(InputStream inputStream, Class<?> type) throws Exception {
        if (null == inputStream) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", type);
        return xstream.fromXML(inputStream, type);
    }

    /**
     * @param ro
     * @param types
     * @return
     * @throws Exception
     * @Description: 对象转换为xml字符串
     * @author leechenxiang
     * @date 2017年8月31日 下午4:52:45
     */
    public static String object2Xml(Object ro, Class<?> types) throws Exception {
        if (null == ro) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
//        xstream.alias("xml", types);
        xstream.processAnnotations(types);
        xstream.autodetectAnnotations(true);
        return xstream.toXML(ro);
    }

}

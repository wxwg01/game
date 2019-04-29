package com.game.domain.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.game.domain.pojo.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) throws Exception {
//		String xml = "<xml><appid><![CDATA[wx27ba4edf360825f0]]></appid>\r\n" +
//				"<bank_type><![CDATA[CFT]]></bank_type>\r\n" +
//				"<cash_fee><![CDATA[1]]></cash_fee>\r\n" +
//				"<fee_type><![CDATA[CNY]]></fee_type>\r\n" +
//				"<is_subscribe><![CDATA[Y]]></is_subscribe>\r\n" +
//				"<mch_id><![CDATA[1488079552]]></mch_id>\r\n" +
//				"<nonce_str><![CDATA[bedd615ea8bf403c9878a47619a37db2]]></nonce_str>\r\n" +
//				"<openid><![CDATA[obzI8wbPdqk46snp2u-Vbr4mtIy4]]></openid>\r\n" +
//				"<out_trade_no><![CDATA[170831BN11MFTKGC]]></out_trade_no>\r\n" +
//				"<result_code><![CDATA[SUCCESS]]></result_code>\r\n" +
//				"<return_code><![CDATA[SUCCESS]]></return_code>\r\n" +
//				"<sign><![CDATA[A5391D28AB0A7AAD2A04CA025ECFC9F8]]></sign>\r\n" +
//				"<time_end><![CDATA[20170831162215]]></time_end>\r\n" +
//				"<total_fee>1</total_fee>\r\n" +
//				"<trade_type><![CDATA[NATIVE]]></trade_type>\r\n" +
//				"<transaction_id><![CDATA[4009492001201708319326473865]]></transaction_id>\r\n" +
//				"</xml>";
//
//		xml = "<xml><appid><![CDATA[wx27ba4edf360825f0]]></appid><bank_type><![CDATA[CCB_DEBIT]]></bank_type><cash_fee><![CDATA[2]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1488079552]]></mch_id><nonce_str><![CDATA[b137e6dc58ca41f9a03ececef3737dd9]]></nonce_str><openid><![CDATA[obzI8wbPdqk46snp2u-Vbr4mtIy4]]></openid><out_trade_no><![CDATA[170831BXB06KDWX4]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[8556332D8BA236995B9F001A29F3527B]]></sign><time_end><![CDATA[20170831164115]]></time_end><total_fee>2</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4009492001201708319321871970]]></transaction_id></xml>";

//		String xml = "<xml><appid><![CDATA[wx27ba4edf360825f0]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1488079552]]></mch_id><nonce_str><![CDATA[6c13599fcd4543f1a6e89ca039d413bd]]></nonce_str><openid><![CDATA[obzI8wbPdqk46snp2u-Vbr4mtIy4]]></openid><out_trade_no><![CDATA[170831CC85T0204H]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[75E1E8C59A0D9E518F35F9B68ECAA4EF]]></sign><time_end><![CDATA[20170831172556]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[4009492001201708319329835501]]></transaction_id></xml>";
		/*List<Role> list = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			Role bean = new Role();
			bean.setId(1);
			bean.setHp(50);
			bean.setSkillId(1);
			bean.setMp(300);
			bean.setName("菜鸟");
			bean.setSkillId(0);
			list.add(bean);
		}*/
		/*Role bean = new Role();
		bean.setId(1);
		bean.setHp(50);
		bean.setSkillId(1);
		bean.setMp(300);
		bean.setName("菜鸟");
		bean.setSkillId(0);*/
		/*GameConfig bean = new GameConfig();

		String str = object2Xml(bean,GameConfig.class);
		System.out.println(str);*/
        /*String path = "D:\\workspace\\boxing\\server\\src\\main\\resources\\config\\role.xml";
        String str = FileUtil.readFile(path);
        RoleList list = (RoleList) xml2Object(str, RoleList.class);
        System.out.println(list.getRoles().size());
        list.getRoles().forEach(item -> {
            JSONObject jsonObject = JSONObject.parseObject(item.getProp());
//			System.out.println(jsonObject.toJSONString());
        });
        System.out.println(JSONObject.toJSONString(list));*/

        /*List<Skill> list = new ArrayList<>();
        for (int i = 1 ;i < 3; i++) {
            Skill bean = new Skill();
            bean.setId(i);
            bean.setName("技能"+i);
            bean.setBeHitAdd(1);
            bean.setBlockHitAdd(1);
            bean.setBlockHitMpAdd(1);
            bean.setHitAdd(2);
            bean.setInitTime(1);
            bean.setRound(3);
            list.add(bean);
        }

        SkillList skillList = new SkillList();
        skillList.setSkillList(list);

		String str = object2Xml(skillList, SkillList.class);*/

        List<Level> list = new ArrayList<>();
        for (int i = 1 ;i < 3; i++) {
            Level bean = new Level();
            bean.setLevel(i);
            bean.setExp(i);

            list.add(bean);
        }

        LevelList skillList = new LevelList();
        skillList.setLevels(list);

        String str = object2Xml(skillList, LevelList.class);

		System.out.println(str);

    }

}

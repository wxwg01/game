package com.game.domain.utils;

import com.game.domain.enums.SEnumValue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/7 12:17
 * @Since 0.0.1
 */
public class BytebufUtil {

    public static Object createObject(String clazzName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (clazzName.startsWith("class")) {
            clazzName = clazzName.substring("class".length()).trim();
        }
        Class<?> clazz = Class.forName(clazzName);
        return clazz.newInstance();
    }

    public static Object createObject(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doResultString(ByteBuf buf) {
        StringBuffer sb = new StringBuffer();
        while (buf.isReadable()) {
            String b = Integer.toHexString(0xFF & buf.readByte());
            if (b.length() == 1) b = "0" + b;
            sb.append(b);
        }
        return sb.toString().toUpperCase();
    }

    public static ByteBuf doReturnByteBuf(List<Object> objects) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        if (Optional.ofNullable(objects).isPresent()) {

            ByteBuf data = Unpooled.buffer();
            for (Object object : objects) {
                ByteBuf bf = objectToByteBuf(object);
                data.writeInt(bf.readableBytes() + 4);
                data.writeBytes(bf);
            }
            buf.writeInt(4 + 2 + data.readableBytes());
            buf.writeShort(objects.size());
            buf.writeBytes(data);
            System.out.println("send data length:" + buf.readableBytes());
        } else {
            buf.writeInt(4 + 2);
            buf.writeShort(0);
        }
        return buf;
    }

    public static ByteBuf objectToByteBuf(Object object) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
        ByteBuf buf = Unpooled.buffer();
        if (Optional.ofNullable(object).isPresent()) {//----begin
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            int code = SEnumValue.getCodeByBean(clz);

            if (code > 0) {
                buf.writeShort(code);
            }
//            System.out.println(JSONObject.toJSONString(fields));
            for (Field field : fields) {// --for() begin
                String type = field.getGenericType().toString();
                String name = field.getName();
                if (name.equals("serialVersionUID")) {
                    continue;
                }
                System.out.println(type + " " + name);

                // 如果类型是String
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    // 拿到该属性的gettet方法
                    /**
                     * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                     * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                     * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                     */
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));

                    String val = (String) m.invoke(object);// 调用getter方法获取属性值

                    if (Optional.ofNullable(val).isPresent()) {
                        buf.writeShort(val.length());
                        buf.writeBytes(val.getBytes());
                    } else {
                        buf.writeShort(0);
                    }

                }

                // 如果类型是Integer
                else if (type.equals("class java.lang.Integer") || type.equals("int")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Integer val = (Integer) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = 0;
                    }
                    buf.writeInt(val);

                }

   /*             // 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        System.out.println("Double type:" + val);
                    }

                }*/

                // 如果类型是Boolean 是封装类
                else if (type.equals("class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            name);
                    Boolean val = (Boolean) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = false;
                    }
                    buf.writeBoolean(val);

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                else if (type.equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            name);
                    Boolean val = (Boolean) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = false;
                    }
                    buf.writeBoolean(val);

                }
                /*// 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        System.out.println("Date type:" + val);
                    }

                }*/
                // 如果类型是Short
                else if (type.equals("class java.lang.Short") || type.equals("short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Short val = (Short) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = 0;
                    }
                    buf.writeShort(val);

                }
                // 如果类型是Long
                else if (type.equals("class java.lang.Long") || type.equals("long")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Long val = (Long) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = 0L;
                    }
                    buf.writeLong(val);

                } // 如果类型是Byte
                else if (type.equals("class java.lang.Byte") || type.equals("byte")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Byte val = (Byte) m.invoke(object);
                    if (!Optional.ofNullable(val).isPresent()) {
                        val = 0;
                    }
                    buf.writeByte(val);

                }
                // 如果还需要其他的类型请自己做扩展
                else if (type.startsWith("java.util.List")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    List val = (List) m.invoke(object);
                    ByteBuf bf = Unpooled.buffer();
                    if (Optional.ofNullable(val).isPresent()) {
                        bf.writeShort(val.size());
                        for (Object ob : val) {
                            bf.writeBytes(objectToByteBuf(ob));
                        }
                    } else {
                        bf.writeShort(0);
                    }
                    buf.writeBytes(bf);
                } else {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(name));
                    Object val = m.invoke(object);
                    if (Optional.ofNullable(val).isPresent()) {
                        buf.writeByte(1);
                        buf.writeBytes(objectToByteBuf(val));
                    } else {
                        buf.writeByte(0);
                    }

                }
            }
        }
        return buf;
    }


    public static Object byteBufToObject(ByteBuf buf, Object object, boolean hadCode, boolean isReturn) {
        try {
            if (buf.readableBytes() > 0) {//----begin
                if (isReturn && hadCode) buf.readInt();
                // 拿到该类
                Class<?> clz;
                if (!hadCode && Optional.ofNullable(object).isPresent()) {
                    clz = object.getClass();
                } else {
                    int code = buf.readInt();
                    clz = SEnumValue.getBeanByCode(code);
                    if (clz == null) {
                        return object;
                    }
                    object = createObject(clz);
                }
                // 获取实体类的所有属性，返回Field数组
                Field[] fields = clz.getDeclaredFields();

                for (Field field : fields) {// --for() begin
                    String type = field.getGenericType().toString();
                    Class[] parameterTypes = new Class[1];
                    parameterTypes[0] = field.getType();

                    String name = field.getName();

                    String setMethodName = "set" + getMethodName(name);
                    Method m = (Method) object.getClass().getMethod(setMethodName, parameterTypes);
                    // 如果类型是String
                    if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                        /**
                         * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                         * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                         * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                         */
                        int length = buf.readShort();
                        byte[] bytes = new byte[length];
                        buf.readBytes(bytes);
                        String data = new String(bytes, "UTF-8");
                        m.invoke(object, data);
                    }
                    // 如果类型是Integer
                    else if (type.equals("class java.lang.Integer") || type.equals("int")) {
                        int data = buf.readInt();
                        m.invoke(object, data);
                    }
                    // 如果类型是Boolean 是封装类
                    else if (type.equals("class java.lang.Boolean")) {
                        boolean data = buf.readBoolean();
                        m.invoke(object, data);
                    }
                    // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                    // 反射找不到getter的具体名
                    else if (type.equals("boolean")) {
                        setMethodName = name;
                        m = (Method) object.getClass().getMethod(setMethodName, parameterTypes);
                        boolean data = buf.readBoolean();
                        m.invoke(object, data);
                    } else if (type.equals("class java.lang.Short") || type.equals("short")) {
                        short data = buf.readShort();
                        m.invoke(object, data);
                    }
                    // 如果类型是Long
                    else if (type.equals("class java.lang.Long") || type.equals("long")) {
                        long data = buf.readLong();
                        m.invoke(object, data);
                    } else if (type.equals("class java.lang.Byte") || type.equals("byte")) {
                        byte data = buf.readByte();
                        m.invoke(object, data);
                    }
                    // 如果还需要其他的类型请自己做扩展
                    else if (type.equals("class java.util.List")) {
                        int length = buf.readShort();
                        List list = new ArrayList();
                        // 如果是泛型参数的类型
                        if (field.getGenericType() instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) field.getGenericType();
                            //得到泛型里的class类型对象
                            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];

                            for (int i = 0; i < length; i++) {
                                Object ob = byteBufToObject(buf, createObject(genericClazz), false, isReturn);
                                list.add(ob);
                            }
                        }
                        m.invoke(object, list);
                    } else if (!type.equals("class java.lang.Object")) {
                        if (isReturn) buf.readByte();
                        Object ob = byteBufToObject(buf, createObject(type), false, isReturn);
                        m.invoke(object, ob);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger().error(e.getMessage(), e);
        }
        return object;
    }


    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


    public static String getStrMsg(Object object) throws Exception {
        List list = new ArrayList();
        list.add(object);
        return BytebufUtil.doResultString(doReturnByteBuf(list));
    }


    public static void main(String[] args) throws Exception {

//        Object clazz = createObject("com.tiger.server.biz.entity.server.PlayData");
       /* SLogin sLogin =  new SLogin();
        sLogin.setUuid(UUID.randomUUID().toString());

        PlayData user = new PlayData();
        user.setUuid(sLogin.getUuid());
        user.setName("test");
        user.setExp(1000000000L);
        user.setLevel((short) 100);
        user.setSex((byte) 1);
        user.setGold(100000L);
        user.setMonmey(1000);

        BattleData round = new BattleData();
        round.setUuid(user.getUuid());
        round.setBattlenum(10);
        round.setAttackNum(11);
        round.setBlocknum(12);
        round.setWinnum(13);
        round.setXulinum(14);

        user.setBattleData(round);

        sLogin.setPlayData(user);

        List list = new ArrayList();
        list.add(sLogin);
        ByteBuf buf = doReturnByteBuf(list);
        hexLog(buf,buf.readerIndex());*/

//        String str = "00000021000000000000000027110009E8B4A6E58FB7E5908D0006313233343536";
//        String str = "00 00 00 AB 00 01 00 00 4E 21 00 24 63 38 39 30 34 30 37 32 2D 66 34 33 31 2D 34 37 36 33 2D 38 65 62 36 2D 63 36 35 30 63 61 37 61 30 37 30 63 00 24 63 38 39 30 34 30 37 32 2D 66 34 33 31 2D 34 37 36 33 2D 38 65 62 36 2D 63 36 35 30 63 61 37 61 30 37 30 63 00 04 74 65 73 74 01 00 64 00 00 00 00 3B 9A CA 00 00 00 00 00 00 01 86 A0 00 00 03 E8 00 24 63 38 39 30 34 30 37 32 2D 66 34 33 31 2D 34 37 36 33 2D 38 65 62 36 2D 63 36 35 30 63 61 37 61 30 37 30 63 00 00 00 0A 00 00 00 0D 00 00 00 0B 00 00 00 0C 00 00 00 0E";


//        ByteBuf buf = stringToByteBuf(str);
//        buf.skipBytes(4+2);
//        System.out.println(JSONObject.toJSONString(byteBufToObject(buf,null,true)));
        /*String str="03 EA 62 61 64 20 72 73 76 20 52 53 56 31 3A 20 66 61 6C 73 65 20 52 53 56 32 3A 20 74 72 75 65 20 52 53 56 33 3A 20 74 72 75 65";
        ByteBuf buf = stringToByteBuf(str);
        int index = buf.readerIndex();
        int size = buf.readableBytes();
        for (int i = 0; i < size; i++) {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            System.out.println(new String(bytes,"UTF-8"));

            buf.readerIndex(index);
            buf.skipBytes(i+1);
        }*/
        int i = 2;
        for (int j = 0; j < 100; j++) {
            System.out.println((int) (Math.random() * i));
        }


    }


    public static String hexLog(ByteBuf buf, int index) {
        buf.readerIndex(index);
        StringBuilder sb = new StringBuilder();
        while (buf.isReadable()) {
            sb.append(String.format("%02x", buf.readByte()).toUpperCase() + " ");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sb.append(" [" + sdf.format(TimeUtil.getUTCTime()) + " UTC ]");
        buf.readerIndex(index);
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static ByteBuf stringToByteBuf(String str) {
        str = str.replace(" ", "");

        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < str.length() / 2; i++) {
            buf.writeByte(Integer.parseInt(str.substring(i * 2, (i + 1) * 2), 16));
        }

        return buf;
    }

}

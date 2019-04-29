package ${packageName};

public enum CEnumValue{

<#-- 循环类型及属性 -->
<#list enums as en>
    ${en.bean?upper_case}((short)${en.code},"${en.des}", ${en.bean?trim}Handler.class,${en.bean?trim}.class) <#if (en?size == (en_index ))>;<#else >,</#if>
</#list>
	  
  private final short code;
  private final String desc;
  private final Class handler;
  private final Class bean;

  CEnumValue(short code,String desc ,Class handler,Class bean) {
    this.code = code;
    this.desc = desc;
    this.handler =handler;
    this.bean = bean;
  }

  public short getCode() {
    return this.code;
  }

  public String getDesc() {
    return this.desc;
  }

  public Class getHandler(){
    return this.handler;
  }

  public Class getBean(){
    return this.bean;
  }

  public static Class getBeanByCode(int code) {
    CEnumValue[] cEnumValues = values();
    for (CEnumValue cEnumValue : cEnumValues) {
      if (cEnumValue.getCode()==code) {
        return cEnumValue.getBean();
      }
    }
    return null;
  }

}
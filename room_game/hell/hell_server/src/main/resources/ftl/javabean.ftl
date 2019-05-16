package ${packageName};

<#list imports as im>
import ${im};
</#list>
/**
* ${classCode} : ${classDesc}
*/
public class ${className}{

<#-- 循环类型及属性 -->
<#list attrs as attr>
	  public ${attr.type} ${attr.name};// ${attr.desc}
</#list>

<#-- 循环生成set get方法
<#list attrs as attr>
public void set${attr.getSetName}(${attr.type} ${attr.name}) {
  this.${attr.name} = ${attr.name};
}
public ${attr.type} get${attr.name?cap_first}() {
  return ${attr.name};
}
</#list>-->
}
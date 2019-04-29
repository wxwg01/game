package ${packageName};

import com.tiger.base.concurrent.handler.AbstractHandler;
import com.tiger.base.message.IMessage;
import com.tiger.base.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 *  ${classDesc}
 *
 * @author wanggang
 * @date 2018/6/25 16:24
 * @since 1.0.0
 */
@Service("${bean?uncap_first}Handler")
public class ${bean?cap_first}Handler extends AbstractHandler
<IMessage, Session> {

  @Override
  public void doAction() {
    System.out.println("handel:${bean?cap_first}Handler");
  }
}
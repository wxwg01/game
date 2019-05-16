package com.game.server.freemarker;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/14 16:13
 * @Since 0.0.1
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;

public class FreeMarkerInit {

    private static FreeMarkerInit single = new FreeMarkerInit();

    private FreeMarkerInit() {
    }

    //静态工厂方法
    public static FreeMarkerInit getInstance() {
        return single;
    }

    public Template getDefinedTemplate(String basicPath, String templateName) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File(basicPath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg.getTemplate(templateName);
    }
}


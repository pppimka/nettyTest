package ua.palii.nettytest.server;

import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by VVV on 05.08.2015.
 */
public class FreemarkerConfig {

    private static final String PAGES_DIRECTORY = "src/main/resources/pages";

    private static final FreemarkerConfig instance = new FreemarkerConfig();

    private static final Version VERSION = new Version(2, 3, 20);

    private static final String ENCODING = "UTF-8";

    private Configuration cfg;

    private FreemarkerConfig() {
        cfg = new Configuration();
        try {
            File f = new File(PAGES_DIRECTORY);
            cfg.setDirectoryForTemplateLoading(f);
            cfg.setIncompatibleImprovements(VERSION);
            cfg.setDefaultEncoding(ENCODING);
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static FreemarkerConfig getInstance() {
        return instance;
    }

    public String getPage(String pageName) {
        StringWriter writer = new StringWriter();
        try {
            Template template = cfg.getTemplate(pageName, Locale.US, ENCODING, false);
            template.process(new HashMap(), writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.getBuffer().toString();
    }
}

package com.csii.tmt.core;

import com.csii.tmt.domain.AppConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class AppConfigLoader {

    private static AppConfig appConfig = AppConfig.getInstance();
    ;

    private static Log log = LogFactory.getLog(AppConfigLoader.class);

    public static void loadAppConfig() {
        Properties xmlProps = new Properties();
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.xml");
            xmlProps.loadFromXML(stream);
            String language = xmlProps.getProperty("Language");
            // log.info("Language=>" + language);
            appConfig.setLanguage(language.trim());

            String defaultIp = xmlProps.getProperty("DefaultIp");
            // log.info("DefaultId=>" + defaultIp);
            appConfig.setDefaultIp(defaultIp.trim());

            String defaultPort = xmlProps.getProperty("DefaultPort");
            // log.info("DefaultPort=>" + defaultPort);
            appConfig.setDefaultPort(defaultPort.trim());

            String defaultCharset = System.getProperty("file.encoding");
            //xmlProps.getProperty("DefaultCharset");
            // log.info("defaultCharset=>" + defaultCharset);
            appConfig.setDefaultCharset(defaultCharset.trim());

        } catch (InvalidPropertiesFormatException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        }
    }
}

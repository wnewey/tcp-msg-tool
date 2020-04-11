package com.csii.tmt.domain;

import com.csii.tmt.core.AppConfigLoader;

public class AppConfig {

    private String language;

    private String defaultIp;

    private String defaultPort;

    private String defaultCharset;

    private static AppConfig appConfig;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDefaultIp() {
        return defaultIp;
    }

    public void setDefaultIp(String defaultIp) {
        this.defaultIp = defaultIp;
    }

    public String getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(String defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    private AppConfig() {
    }

    public synchronized static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
            AppConfigLoader.loadAppConfig();
        }
        return appConfig;
    }
}

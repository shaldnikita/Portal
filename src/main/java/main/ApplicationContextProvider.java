package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware{
    private static final transient Logger log = LoggerFactory.getLogger(ApplicationContextProvider.class);

    private static ApplicationContext ctx = null;

    public static ApplicationContext getContext() {
        return ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        log.info("Init static context");
        ApplicationContextProvider.ctx = ctx;
    }
}

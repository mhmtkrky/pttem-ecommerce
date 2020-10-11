package service;

import org.springframework.context.ApplicationContext;

public class BeanConfigurater {
    private ApplicationContext applicationContext;
    private static BeanConfigurater self;
    private BeanConfigurater(){

    }
    public static BeanConfigurater init(ApplicationContext applicationContext){
        self = new BeanConfigurater();
        self.applicationContext = applicationContext;
        return self;
    }
    public static BeanConfigurater getInstance(){
        return self;
    }


    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

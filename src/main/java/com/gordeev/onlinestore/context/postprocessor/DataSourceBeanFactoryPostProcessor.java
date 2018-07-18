package com.gordeev.onlinestore.context.postprocessor;

import com.gordeev.applicationcontextlibrary.entity.BeanDefinition;
import com.gordeev.applicationcontextlibrary.postprocessor.BeanFactoryPostProcessor;
import com.gordeev.onlinestore.Main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DataSourceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitionList) {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
//        BasicDataSource dataSource = new BasicDataSource();
//        LOG.info("{}:{}", "JDBC_DATABASE_URL", dbUrl);

        if (dbUrl != null) {
            dbUrl += "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        } else {
            Properties properties = new Properties();
            try {
                properties.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
            } catch (IOException e) {
                e.printStackTrace();
//                LOG.info("bad file or properties in it");
            }
            dbUrl = properties.getProperty("url");
        }

//        dataSource.setUrl(dbUrl);
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            if ("dataSource".equals(beanDefinition.getId())){
                for (Map.Entry<String, String> dependency : beanDefinition.getDependencies().entrySet()) {
                    if (dependency.getKey().equals("url")){
                        dependency.setValue(dbUrl);
                        break;
                    }
                }
            }
        }

//        Context.register(DataSource.class, dataSource);

//        LOG.info("Main: got connection to database");
    }
}

package com.gordeev.onlinestore.context.postprocessor;

import com.gordeev.onlinestore.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;

import java.io.IOException;
import java.util.Properties;

public class PropertiesBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");

        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            LOG.error("Cannot load information from file application.properties", e);
        }

        if (dbUrl != null && !properties.isEmpty()) {
            dbUrl += "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
            properties.setProperty("url", dbUrl);
        }

        for (String beanDefinitionName : configurableListableBeanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);

            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {

                if (propertyValue.getValue() instanceof TypedStringValue) {
                    TypedStringValue value = (TypedStringValue) propertyValue.getValue();
                    String stringValue = value.getValue();

                    if (stringValue.startsWith("${")) {
                        String propertyFileKey = stringValue.substring("${".length(), stringValue.indexOf("}"));
                        String propertyFileValue = properties.getProperty(propertyFileKey);

                        value.setValue(propertyFileValue);
                    }
                }
            }
        }
    }
}

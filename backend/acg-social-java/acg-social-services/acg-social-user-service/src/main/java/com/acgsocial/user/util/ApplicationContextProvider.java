package com.acgsocial.user.util;

import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

  private static ApplicationContext context;

  public static <T> T bean(Class<T> beanType) {
    return context.getBean(beanType);
  }

  public static Object bean(String name) {
    return context.getBean(name);
  }

  @Override
  public void setApplicationContext(@SuppressWarnings("NullableProblems") ApplicationContext ac) {
    context = ac;
  }
}
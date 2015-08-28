package varontron.test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Properties;

import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

public class BasicAnnoTransformer implements IAnnotationTransformer2 {

  private Properties props = new Properties();
  private static final String PROPERTIES = "/basic.properties";
  private static final String DELIMITER  = ",";   
  
  public BasicAnnoTransformer() throws IOException {
    InputStream is = this.getClass().getResourceAsStream(PROPERTIES);
    this.props.load(is);
    is.close();
  }

  public void transform(ITestAnnotation arg0, Class arg1, Constructor arg2,
      Method arg3) {
    String name    = arg3.getName();
    String listStr = (String)this.props.get(name);
    if(listStr != null)
    {
      String[] list = listStr.split(DELIMITER);
      if(list != null && list.length > 0)
      {
        Annotation anno = arg3.getAnnotation(BasicAnno.class);
        if (anno != null)
        {
          changeAnnotationValue(anno,"list",list);
        }
      }
    }

  }

  public void transform(IDataProviderAnnotation arg0, Method arg1) {
    // TODO Auto-generated method stub

  }

  public void transform(IFactoryAnnotation arg0, Method arg1) {
    // TODO Auto-generated method stub

  }

  public void transform(IConfigurationAnnotation arg0, Class arg1,
      Constructor arg2, Method arg3) {
    // TODO Auto-generated method stub

  }
  
  public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue){
    Object handler = Proxy.getInvocationHandler(annotation);
    Field f;
    try {
        f = handler.getClass().getDeclaredField("memberValues");
    } 
    catch (NoSuchFieldException e)
    {
       throw new IllegalStateException(e);
    }
    catch (SecurityException e) 
    {
      throw new IllegalStateException(e);
    }
    f.setAccessible(true);
    Map<String, Object> memberValues;
    try {
        memberValues = (Map<String, Object>) f.get(handler);
    } 
    catch (IllegalArgumentException e) 
    {
      throw new IllegalStateException(e);
    }
    catch (IllegalAccessException e) 
    {
        throw new IllegalStateException(e);
    }
    Object oldValue = memberValues.get(key);
//    if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
//        throw new IllegalArgumentException();
//    }
    memberValues.put(key,newValue);
    return oldValue;
}

}

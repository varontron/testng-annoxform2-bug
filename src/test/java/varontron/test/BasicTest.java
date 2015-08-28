package varontron.test;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BasicTest {
  
  public BasicTest() {

  }
  
  @DataProvider (name="BasicProvider")
  public Object[][] dataProvider(Method method) {
    String[] params = method.getAnnotation(BasicAnno.class).list();
    Object[][] result = new Object[params.length][];
    for (int i=0;i<params.length;i++) {
      result[i] = new Object[] {params[i]};
    }
    return result;
  }
  
// If you un-comment the preTest code, the "transformed" annotation for aTest will be used 
// If you leave it commented, the "default" annotation for aTest will be used 
// The first test in the class always loses the transformed annotation
  
//  @Test (enabled=true,dataProvider="BasicProvider")
//  @BasicAnno (list={})
//  public void preTest(String param) {
//    System.out.println("preTest:" + param);
//  }

  
  @Test (enabled=true,dataProvider="BasicProvider")
  @BasicAnno (list={"default_aTestS1","default_aTestS2"})
  public void aTest(String param) {
    System.out.println("aTest: " + param);
  }
  
  @Test (enabled=true,dataProvider="BasicProvider")
  @BasicAnno (list={"default_bTestS1","default_bTestS2"})
  public void bTest(String param) {
    System.out.println("bTest: " + param);
  }

}

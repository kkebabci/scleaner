/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kk.scleaner.test.annotation;

import java.util.ArrayList;
import java.util.List;import kk.scleaner.cleaner.SCleaner;
import kk.scleaner.test.scannedpackage.ClassIncludesBooleanStatic;
import kk.scleaner.test.scannedpackage.ClassIncludesMultipleStatics;
import kk.scleaner.test.scannedpackage.ClassIncludesOneStatic;
import kk.scleaner.test.scannedpackage.ClassIncludesPrimitiveBooleanStatic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lenovo
 */
public class DestroyAtShutdownTest {
    
    private List<String> packageList;
    
    @Before
    public void setUp() {
        packageList = new ArrayList<>();
        packageList.add("kk.scleaner.test");
    }
    
    @Test
    public void test_class_annotated() {
        String time1 = ClassIncludesMultipleStatics.getTime1();
        String time2 = ClassIncludesMultipleStatics.getTime2();
        
        // Cleans statics
        SCleaner.clean(packageList);
        sleep(100);
        
        Assert.assertEquals(false,time1.equals(ClassIncludesMultipleStatics.getTime1()));
        Assert.assertEquals(false,time2.equals(ClassIncludesMultipleStatics.getTime2()));
    }
    
    @Test
    public void test_field_annotated() {
        String time1 = ClassIncludesOneStatic.getTime1();
       
        // Cleans statics
        SCleaner.clean(packageList);
        sleep(100);
        
        Assert.assertEquals(false,time1.equals(ClassIncludesOneStatic.getTime1()));
    }
    
    @Test
    public void test_primitive_field_annotated() {
        ClassIncludesPrimitiveBooleanStatic.initialize();
        
        SCleaner.clean(packageList);
        
        Assert.assertEquals(false, ClassIncludesPrimitiveBooleanStatic.getInitialized());
    }
    
    @Test
    public void test_boolean_object_field_annotated() {
        ClassIncludesBooleanStatic.initialize();
        
        SCleaner.clean(packageList);
        
        Assert.assertEquals(null, ClassIncludesBooleanStatic.getInitialized());
    }
    private void sleep(int i) {
       try {
           Thread.sleep(i);
       }catch(Exception ex) {
           
       }
    }
}

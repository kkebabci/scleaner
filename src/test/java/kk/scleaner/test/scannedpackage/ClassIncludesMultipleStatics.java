/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kk.scleaner.test.scannedpackage;

import kk.scleaner.annotation.DestroyAtShutdown;

/**
 *
 * @author lenovo
 */
@DestroyAtShutdown
public class ClassIncludesMultipleStatics {
    
    public static String time1 = null;
    
    private static String time2 = null;
    
    
    public static synchronized String getTime1() {
       if(time1 == null) {
           time1 = String.valueOf(System.currentTimeMillis());
       }    
       return time1;
    }
    
    public static synchronized String getTime2() {
       if(time2 == null) {
           time2 = String.valueOf(System.currentTimeMillis());
       }    
       return time2;
    } 
}

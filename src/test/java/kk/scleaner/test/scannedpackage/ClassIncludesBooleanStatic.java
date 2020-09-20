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
public class ClassIncludesBooleanStatic {
    
    @DestroyAtShutdown(booleanObjectSetNull = true)
    private static Boolean initialized=null;
    
    public static void initialize() {
        initialized = true;
    }
    
    public  static Boolean getInitialized() {
        return initialized;
    }
}

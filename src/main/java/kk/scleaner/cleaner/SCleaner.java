/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kk.scleaner.cleaner;

import kk.scleaner.annotation.finder.DestroyAtShutdownFinder;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.Modifier;
import kk.scleaner.annotation.DestroyAtShutdown;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 *
 * @author kkebabci
 */
public class SCleaner {

    public static void clean(List<String> packageList) {

        for (String packageToClean : packageList) {
            Reflections ref = new Reflections(packageToClean, new FieldAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());
            //@DestroyAtContextShutdown classes
            Set<Class<?>> classesToClean = DestroyAtShutdownFinder.findClasses(ref, packageToClean);
            classesToClean.forEach((classToClean) -> {
                DestroyAtShutdown ds = classToClean.getAnnotation(DestroyAtShutdown.class);
                findStaticFieldsAndDestroy(classToClean, ds.booleanObjectSetNull() ? null : ds.defaultBooleanValue());
            });

            //@DestroyAtContextShutdown fields
            Set<Field> fieldsToClean = DestroyAtShutdownFinder.findFields(ref, packageToClean);
            fieldsToClean.forEach((fieldToClean) -> destroyField(fieldToClean, false));
        }
    }

    private static void destroyField(Field field, Boolean defaultBooleanValue) {
        if (Modifier.isStatic(field.getModifiers())) {
            try {
                boolean accesable = field.isAccessible();
                field.setAccessible(true);

                if (field.getType().equals(Boolean.TYPE) || field.getType().equals(java.lang.Boolean.class)) {
                    Boolean value = defaultBooleanValue;
                    
                    DestroyAtShutdown ds = field.getAnnotation(DestroyAtShutdown.class);
                    if (ds != null) {
                        value = ds.booleanObjectSetNull() ? null : ds.defaultBooleanValue();
                    } 
                    
                    field.set(null, value);
                } else {
                    field.set(null, null);
                }

                field.setAccessible(accesable);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(SCleaner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void findStaticFieldsAndDestroy(Class<?> classToClean, boolean defaultBooleanValue) {

        Field[] fieldList = classToClean.getDeclaredFields();

        for (Field field : fieldList) {
            destroyField(field, defaultBooleanValue);
        }
    }
}

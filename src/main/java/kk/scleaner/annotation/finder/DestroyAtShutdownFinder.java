/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kk.scleaner.annotation.finder;

import java.lang.reflect.Field;
import java.util.Set;
import org.reflections.Reflections;
import kk.scleaner.annotation.DestroyAtShutdown;

/**
 *
 * @author lenovo
 */
public class DestroyAtShutdownFinder {

    public static Set<Class<?>> findClasses(Reflections ref, String packageName) {
        return ref.getTypesAnnotatedWith(DestroyAtShutdown.class);
    }

    public static Set<Field> findFields(Reflections ref, String packageName) {
        return ref.getFieldsAnnotatedWith(DestroyAtShutdown.class);
    }
}

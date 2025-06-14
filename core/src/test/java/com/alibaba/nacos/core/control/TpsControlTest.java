/*
 *
 * Copyright 1999-2021 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.alibaba.nacos.core.control;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for TpsControl annotation.
 *
 * @author liuzunfei
 */
class TpsControlTest {
    
    @Test
    void testAnnotationPresence() {
        assertTrue(TpsControl.class.isAnnotation());
        
        Retention retention = TpsControl.class.getAnnotation(Retention.class);
        assertNotNull(retention);
        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }
    
    @Test
    void testAnnotationMethods() throws Exception {
        Method nameMethod = TpsControl.class.getMethod("name");
        Method pointNameMethod = TpsControl.class.getMethod("pointName");
        
        assertNotNull(nameMethod);
        assertNotNull(pointNameMethod);
        
        assertEquals(String.class, nameMethod.getReturnType());
        assertEquals(String.class, pointNameMethod.getReturnType());
        
        // Test default value for name method
        assertEquals("", nameMethod.getDefaultValue());
        
        // pointName method should not have a default value
        assertEquals(null, pointNameMethod.getDefaultValue());
    }
    
    @Test
    void testAnnotationUsage() {
        // Test that the annotation can be applied to methods
        Method[] methods = TestClass.class.getDeclaredMethods();
        boolean foundAnnotation = false;
        
        for (Method method : methods) {
            if (method.isAnnotationPresent(TpsControl.class)) {
                foundAnnotation = true;
                TpsControl annotation = method.getAnnotation(TpsControl.class);
                assertEquals("testMethod", annotation.name());
                assertEquals("test.point", annotation.pointName());
                break;
            }
        }
        
        assertTrue(foundAnnotation, "TpsControl annotation should be found on test method");
    }
    
    // Test class to verify annotation usage
    private static class TestClass {
        
        @TpsControl(name = "testMethod", pointName = "test.point")
        public void testMethod() {
            // Test method with TpsControl annotation
        }
        
        @TpsControl(pointName = "another.point")
        public void anotherMethod() {
            // Test method with TpsControl annotation using default name
        }
    }
    
    @Test
    void testDefaultNameValue() throws Exception {
        Method anotherMethod = TestClass.class.getDeclaredMethod("anotherMethod");
        TpsControl annotation = anotherMethod.getAnnotation(TpsControl.class);
        
        assertNotNull(annotation);
        assertEquals("", annotation.name()); // Default value
        assertEquals("another.point", annotation.pointName());
    }
    
    @Test
    void testCustomNameValue() throws Exception {
        Method testMethod = TestClass.class.getDeclaredMethod("testMethod");
        TpsControl annotation = testMethod.getAnnotation(TpsControl.class);
        
        assertNotNull(annotation);
        assertEquals("testMethod", annotation.name());
        assertEquals("test.point", annotation.pointName());
    }
}
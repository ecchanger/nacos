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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for TpsControlConfig.
 *
 * @author liuzunfei
 */
class TpsControlConfigTest {
    
    @Test
    void testIsTpsControlEnabled() {
        // Test that TPS control is enabled by default
        assertTrue(TpsControlConfig.isTpsControlEnabled());
    }
    
    @Test
    void testIsTpsControlEnabledConsistency() {
        // Test that multiple calls return the same result
        boolean firstCall = TpsControlConfig.isTpsControlEnabled();
        boolean secondCall = TpsControlConfig.isTpsControlEnabled();
        
        assertTrue(firstCall);
        assertTrue(secondCall);
        assertTrue(firstCall == secondCall);
    }
    
    @Test
    void testStaticMethodBehavior() {
        // Test that the static method can be called without instantiation
        assertTrue(TpsControlConfig.isTpsControlEnabled());
        
        // Verify we can call it multiple times
        for (int i = 0; i < 10; i++) {
            assertTrue(TpsControlConfig.isTpsControlEnabled());
        }
    }
}
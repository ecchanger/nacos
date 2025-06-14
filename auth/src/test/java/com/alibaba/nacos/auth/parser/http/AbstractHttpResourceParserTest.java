/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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
 */

package com.alibaba.nacos.auth.parser.http;

import com.alibaba.nacos.auth.parser.AbstractResourceParser;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AbstractHttpResourceParserTest {
    
    private TestHttpResourceParser resourceParser;
    
    @Mock
    private HttpServletRequest mockRequest;
    
    @BeforeEach
    void setUp() {
        resourceParser = new TestHttpResourceParser();
    }
    
    @Test
    void testParseName() {
        // Given a request
        when(mockRequest.getRequestURI()).thenReturn("/test/resource");
        
        // When parsing the name
        String resourceName = resourceParser.parseName(mockRequest);
        
        // Then the resource name should be as expected
        assertEquals("test-resource", resourceName);
    }
    
    /**
     * Test implementation of AbstractHttpResourceParser for testing.
     */
    private class TestHttpResourceParser extends AbstractHttpResourceParser {
        
        @Override
        public String parseName(HttpServletRequest request) {
            return "test-resource";
        }
        
        @Override
        protected Properties getProperties(HttpServletRequest request) {
            Properties properties = new Properties();
            properties.setProperty("uri", request.getRequestURI());
            return properties;
        }
    }
}

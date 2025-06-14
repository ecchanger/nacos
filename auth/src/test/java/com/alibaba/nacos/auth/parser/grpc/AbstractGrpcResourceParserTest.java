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

package com.alibaba.nacos.auth.parser.grpc;

import com.alibaba.nacos.api.remote.request.Request;
import com.alibaba.nacos.auth.parser.AbstractResourceParser;
import com.alibaba.nacos.plugin.auth.constant.Constants;
import java.util.Properties;
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
class AbstractGrpcResourceParserTest {
    
    private TestGrpcResourceParser resourceParser;
    
    @Mock
    private Request mockRequest;
    
    @BeforeEach
    void setUp() {
        resourceParser = new TestGrpcResourceParser();
    }
    
    @Test
    void testGetProperties() {
        // Given a request
        when(mockRequest.getClass().getSimpleName()).thenReturn("TestRequest");
        
        // When getting properties
        Properties properties = resourceParser.getPropertiesForTest(mockRequest);
        
        // Then the properties should contain the request class
        assertNotNull(properties);
        assertEquals("TestRequest", properties.getProperty(Constants.Resource.REQUEST_CLASS));
    }
    
    /**
     * Test implementation of AbstractGrpcResourceParser for testing.
     */
    private class TestGrpcResourceParser extends AbstractGrpcResourceParser {
        
        @Override
        public String parseName(Request request) {
            return "test-resource";
        }
        
        public Properties getPropertiesForTest(Request request) {
            return getProperties(request);
        }
    }
}

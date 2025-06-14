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

package com.alibaba.nacos.auth.parser;

import com.alibaba.nacos.auth.annotation.Secured;
import com.alibaba.nacos.plugin.auth.api.Resource;
import com.alibaba.nacos.plugin.auth.constant.Constants;
import com.alibaba.nacos.plugin.auth.constant.SignType;
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
class AbstractResourceParserTest {
    
    private TestResourceParser resourceParser;
    
    @Mock
    private Secured secured;
    
    @BeforeEach
    void setUp() {
        resourceParser = new TestResourceParser();
        when(secured.action()).thenReturn(Constants.ActionTypes.READ);
        when(secured.signType()).thenReturn(SignType.DEFAULT);
        when(secured.tags()).thenReturn(new String[]{"tag1", "tag2"});
    }
    
    @Test
    void testParse() {
        // When parsing a request
        Resource resource = resourceParser.parse("testRequest", secured);
        
        // Then the resource should be correctly constructed
        assertNotNull(resource);
        assertEquals("test-namespace", resource.getNamespaceId());
        assertEquals("test-group", resource.getGroup());
        assertEquals("test-resource", resource.getName());
        assertEquals(SignType.DEFAULT, resource.getType());
        
        // And properties should contain action and tags
        Properties properties = resource.getProperties();
        assertEquals(Constants.ActionTypes.READ.toString(), properties.getProperty(Constants.Resource.ACTION));
        assertEquals("tag1", properties.getProperty("tag1"));
        assertEquals("tag2", properties.getProperty("tag2"));
    }
    
    @Test
    void testInjectTagsToProperties() {
        // Given properties and secured annotation
        Properties properties = new Properties();
        
        // When injecting tags
        resourceParser.injectTagsToPropertiesForTest(properties, secured);
        
        // Then properties should contain the tags
        assertEquals("tag1", properties.getProperty("tag1"));
        assertEquals("tag2", properties.getProperty("tag2"));
    }
    
    /**
     * Test implementation of AbstractResourceParser for testing.
     */
    private class TestResourceParser extends AbstractResourceParser<String> {
        
        @Override
        protected String getNamespaceId(String request) {
            return "test-namespace";
        }
        
        @Override
        protected String getGroup(String request) {
            return "test-group";
        }
        
        @Override
        protected String getResourceName(String request) {
            return "test-resource";
        }
        
        @Override
        protected Properties getProperties(String request) {
            Properties properties = new Properties();
            properties.setProperty("request", request);
            return properties;
        }
        
        public void injectTagsToPropertiesForTest(Properties properties, Secured secured) {
            injectTagsToProperties(properties, secured);
        }
    }
}

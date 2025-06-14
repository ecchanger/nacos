/*
 * Copyright 1999-2023 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.common.paramcheck;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test for ParamInfo.
 *
 * @author zhuoguang
 */
class ParamInfoTest {
    
    @Test
    void testGettersAndSetters() {
        ParamInfo paramInfo = new ParamInfo();
        
        // Test namespaceShowName
        assertNull(paramInfo.getNamespaceShowName());
        paramInfo.setNamespaceShowName("test-namespace");
        assertEquals("test-namespace", paramInfo.getNamespaceShowName());
        
        // Test namespaceId
        assertNull(paramInfo.getNamespaceId());
        paramInfo.setNamespaceId("test-namespace-id");
        assertEquals("test-namespace-id", paramInfo.getNamespaceId());
        
        // Test dataId
        assertNull(paramInfo.getDataId());
        paramInfo.setDataId("test-data-id");
        assertEquals("test-data-id", paramInfo.getDataId());
        
        // Test serviceName
        assertNull(paramInfo.getServiceName());
        paramInfo.setServiceName("test-service");
        assertEquals("test-service", paramInfo.getServiceName());
        
        // Test group
        assertNull(paramInfo.getGroup());
        paramInfo.setGroup("test-group");
        assertEquals("test-group", paramInfo.getGroup());
        
        // Test cluster
        assertNull(paramInfo.getCluster());
        paramInfo.setCluster("test-cluster");
        assertEquals("test-cluster", paramInfo.getCluster());
        
        // Test clusters
        assertNull(paramInfo.getClusters());
        paramInfo.setClusters("cluster1,cluster2");
        assertEquals("cluster1,cluster2", paramInfo.getClusters());
        
        // Test ip
        assertNull(paramInfo.getIp());
        paramInfo.setIp("192.168.1.1");
        assertEquals("192.168.1.1", paramInfo.getIp());
        
        // Test port
        assertNull(paramInfo.getPort());
        paramInfo.setPort("8080");
        assertEquals("8080", paramInfo.getPort());
        
        // Test metadata
        assertNull(paramInfo.getMetadata());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        paramInfo.setMetadata(metadata);
        assertEquals(metadata, paramInfo.getMetadata());
        assertEquals("value1", paramInfo.getMetadata().get("key1"));
        assertEquals("value2", paramInfo.getMetadata().get("key2"));
    }
    
    @Test
    void testNullValues() {
        ParamInfo paramInfo = new ParamInfo();
        
        // Set all values to null
        paramInfo.setNamespaceShowName(null);
        paramInfo.setNamespaceId(null);
        paramInfo.setDataId(null);
        paramInfo.setServiceName(null);
        paramInfo.setGroup(null);
        paramInfo.setCluster(null);
        paramInfo.setClusters(null);
        paramInfo.setIp(null);
        paramInfo.setPort(null);
        paramInfo.setMetadata(null);
        
        // Verify all values are null
        assertNull(paramInfo.getNamespaceShowName());
        assertNull(paramInfo.getNamespaceId());
        assertNull(paramInfo.getDataId());
        assertNull(paramInfo.getServiceName());
        assertNull(paramInfo.getGroup());
        assertNull(paramInfo.getCluster());
        assertNull(paramInfo.getClusters());
        assertNull(paramInfo.getIp());
        assertNull(paramInfo.getPort());
        assertNull(paramInfo.getMetadata());
    }
    
    @Test
    void testEmptyValues() {
        ParamInfo paramInfo = new ParamInfo();
        
        // Set all values to empty strings
        paramInfo.setNamespaceShowName("");
        paramInfo.setNamespaceId("");
        paramInfo.setDataId("");
        paramInfo.setServiceName("");
        paramInfo.setGroup("");
        paramInfo.setCluster("");
        paramInfo.setClusters("");
        paramInfo.setIp("");
        paramInfo.setPort("");
        paramInfo.setMetadata(new HashMap<>());
        
        // Verify all values are empty
        assertEquals("", paramInfo.getNamespaceShowName());
        assertEquals("", paramInfo.getNamespaceId());
        assertEquals("", paramInfo.getDataId());
        assertEquals("", paramInfo.getServiceName());
        assertEquals("", paramInfo.getGroup());
        assertEquals("", paramInfo.getCluster());
        assertEquals("", paramInfo.getClusters());
        assertEquals("", paramInfo.getIp());
        assertEquals("", paramInfo.getPort());
        assertEquals(0, paramInfo.getMetadata().size());
    }
}
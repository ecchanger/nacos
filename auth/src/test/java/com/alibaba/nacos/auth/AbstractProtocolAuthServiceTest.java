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

package com.alibaba.nacos.auth;

import com.alibaba.nacos.auth.annotation.Secured;
import com.alibaba.nacos.auth.config.AuthConfigs;
import com.alibaba.nacos.auth.parser.ResourceParser;
import com.alibaba.nacos.auth.serveridentity.ServerIdentity;
import com.alibaba.nacos.auth.serveridentity.ServerIdentityChecker;
import com.alibaba.nacos.auth.serveridentity.ServerIdentityCheckerHolder;
import com.alibaba.nacos.auth.serveridentity.ServerIdentityResult;
import com.alibaba.nacos.plugin.auth.api.IdentityContext;
import com.alibaba.nacos.plugin.auth.api.Permission;
import com.alibaba.nacos.plugin.auth.api.Resource;
import com.alibaba.nacos.plugin.auth.constant.Constants;
import com.alibaba.nacos.plugin.auth.constant.SignType;
import com.alibaba.nacos.plugin.auth.exception.AccessException;
import com.alibaba.nacos.plugin.auth.spi.server.AuthPluginManager;
import com.alibaba.nacos.plugin.auth.spi.server.AuthPluginService;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AbstractProtocolAuthServiceTest {
    
    private TestProtocolAuthService protocolAuthService;
    
    @Mock
    private AuthConfigs authConfigs;
    
    @Mock
    private ServerIdentityChecker serverIdentityChecker;
    
    @Mock
    private ServerIdentityCheckerHolder serverIdentityCheckerHolder;
    
    @Mock
    private Secured secured;
    
    @Mock
    private AuthPluginManager authPluginManager;
    
    @Mock
    private AuthPluginService authPluginService;
    
    private MockedStatic<ServerIdentityCheckerHolder> serverIdentityCheckerHolderMockedStatic;
    
    private MockedStatic<AuthPluginManager> authPluginManagerMockedStatic;
    
    @BeforeEach
    void setUp() {
        serverIdentityCheckerHolderMockedStatic = Mockito.mockStatic(ServerIdentityCheckerHolder.class);
        serverIdentityCheckerHolderMockedStatic.when(ServerIdentityCheckerHolder::getInstance).thenReturn(serverIdentityCheckerHolder);
        when(serverIdentityCheckerHolder.getChecker()).thenReturn(serverIdentityChecker);
        
        authPluginManagerMockedStatic = Mockito.mockStatic(AuthPluginManager.class);
        authPluginManagerMockedStatic.when(AuthPluginManager::getInstance).thenReturn(authPluginManager);
        
        protocolAuthService = new TestProtocolAuthService(authConfigs);
    }
    
    @Test
    void testInitialize() {
        // When initializing
        protocolAuthService.initialize();
        
        // Then the checker should be initialized with auth configs
        Mockito.verify(serverIdentityChecker).init(authConfigs);
    }
    
    @Test
    void testEnableAuthWhenPluginFound() {
        // Given
        when(authConfigs.getNacosAuthSystemType()).thenReturn("test");
        when(authPluginManager.findAuthServiceSpiImpl("test")).thenReturn(Optional.of(authPluginService));
        when(secured.action()).thenReturn(Constants.ActionTypes.READ);
        when(secured.signType()).thenReturn(SignType.DEFAULT);
        when(authPluginService.enableAuth(Constants.ActionTypes.READ, SignType.DEFAULT)).thenReturn(true);
        
        // When
        boolean result = protocolAuthService.enableAuth(secured);
        
        // Then
        assertTrue(result);
    }
    
    @Test
    void testEnableAuthWhenPluginNotFound() {
        // Given
        when(authConfigs.getNacosAuthSystemType()).thenReturn("test");
        when(authPluginManager.findAuthServiceSpiImpl("test")).thenReturn(Optional.empty());
        
        // When
        boolean result = protocolAuthService.enableAuth(secured);
        
        // Then
        assertFalse(result);
    }
    
    @Test
    void testParseSpecifiedResource() {
        // Given
        when(secured.tags()).thenReturn(new String[]{"tag1", "tag2"});
        when(secured.resource()).thenReturn("test-resource");
        
        // When
        Resource resource = protocolAuthService.parseSpecifiedResource(secured);
        
        // Then
        assertNotNull(resource);
        assertEquals("test-resource", resource.getName());
        assertEquals(SignType.SPECIFIED, resource.getType());
        assertEquals("tag1", resource.getProperties().getProperty("tag1"));
        assertEquals("tag2", resource.getProperties().getProperty("tag2"));
    }
    
    @Test
    void testCheckServerIdentityWithInvalidIdentity() {
        // Given
        when(authConfigs.getServerIdentityKey()).thenReturn("");
        when(authConfigs.getServerIdentityValue()).thenReturn("");
        
        // When
        ServerIdentityResult result = protocolAuthService.checkServerIdentity("request", secured);
        
        // Then
        assertFalse(result.isSuccess());
    }
    
    @Test
    void testCheckServerIdentityWithValidIdentity() {
        // Given
        when(authConfigs.getServerIdentityKey()).thenReturn("key");
        when(authConfigs.getServerIdentityValue()).thenReturn("value");
        ServerIdentityResult expectedResult = ServerIdentityResult.success();
        when(serverIdentityChecker.check(any(ServerIdentity.class), eq(secured))).thenReturn(expectedResult);
        
        // When
        ServerIdentityResult result = protocolAuthService.checkServerIdentity("request", secured);
        
        // Then
        assertEquals(expectedResult, result);
    }
    
    /**
     * Test implementation of AbstractProtocolAuthService.
     */
    private static class TestProtocolAuthService extends AbstractProtocolAuthService<String> {
        
        TestProtocolAuthService(AuthConfigs authConfigs) {
            super(authConfigs);
        }
        
        @Override
        protected ServerIdentity parseServerIdentity(String request) {
            return new ServerIdentity("key", "value");
        }
        
        @Override
        public Resource parseResource(String request, Secured secured) {
            return Resource.EMPTY_RESOURCE;
        }
    }
}

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

package com.alibaba.nacos.common.tls;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for TlsSystemConfig.
 *
 * @author wangwei
 */
class TlsSystemConfigTest {
    
    @Test
    void testConstants() {
        assertEquals("tls.test", TlsSystemConfig.TLS_TEST_MODE_ENABLE);
        assertEquals("tls.enable", TlsSystemConfig.TLS_ENABLE);
        assertEquals("tls.client.authServer", TlsSystemConfig.CLIENT_AUTH);
        assertEquals("tls.client.keyPath", TlsSystemConfig.CLIENT_KEYPATH);
        assertEquals("tls.client.keyPassword", TlsSystemConfig.CLIENT_KEYPASSWORD);
        assertEquals("tls.client.certPath", TlsSystemConfig.CLIENT_CERTPATH);
        assertEquals("tls.client.trustCertPath", TlsSystemConfig.CLIENT_TRUST_CERT);
        assertEquals("tls.server.authClient", TlsSystemConfig.SERVER_AUTH);
        assertEquals("tls.server.keyPath", TlsSystemConfig.SERVER_KEYPATH);
        assertEquals("tls.server.keyPassword", TlsSystemConfig.SERVER_KEYPASSWORD);
        assertEquals("tls.server.certPath", TlsSystemConfig.SERVER_CERTPATH);
        assertEquals("tls.server.trustCertPath", TlsSystemConfig.SERVER_TRUST_CERT);
        assertEquals("checkIntervalTlsFile", TlsSystemConfig.CHECK_INTERVAL);
    }
}
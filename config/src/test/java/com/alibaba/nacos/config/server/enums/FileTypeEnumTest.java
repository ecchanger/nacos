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

package com.alibaba.nacos.config.server.enums;

import com.alibaba.nacos.common.http.param.MediaType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for FileTypeEnum.
 *
 * @author klw
 */
class FileTypeEnumTest {
    
    @Test
    void testEnumValues() {
        // Test all enum values exist
        assertNotNull(FileTypeEnum.YML);
        assertNotNull(FileTypeEnum.YAML);
        assertNotNull(FileTypeEnum.TXT);
        assertNotNull(FileTypeEnum.TEXT);
        assertNotNull(FileTypeEnum.JSON);
        assertNotNull(FileTypeEnum.XML);
        assertNotNull(FileTypeEnum.HTM);
        assertNotNull(FileTypeEnum.HTML);
        assertNotNull(FileTypeEnum.PROPERTIES);
    }
    
    @Test
    void testFileTypes() {
        assertEquals("yaml", FileTypeEnum.YML.getFileType());
        assertEquals("yaml", FileTypeEnum.YAML.getFileType());
        assertEquals("text", FileTypeEnum.TXT.getFileType());
        assertEquals("text", FileTypeEnum.TEXT.getFileType());
        assertEquals("json", FileTypeEnum.JSON.getFileType());
        assertEquals("xml", FileTypeEnum.XML.getFileType());
        assertEquals("html", FileTypeEnum.HTM.getFileType());
        assertEquals("html", FileTypeEnum.HTML.getFileType());
        assertEquals("properties", FileTypeEnum.PROPERTIES.getFileType());
    }
    
    @Test
    void testContentTypes() {
        assertEquals(MediaType.TEXT_PLAIN, FileTypeEnum.YML.getContentType());
        assertEquals(MediaType.TEXT_PLAIN, FileTypeEnum.YAML.getContentType());
        assertEquals(MediaType.TEXT_PLAIN, FileTypeEnum.TXT.getContentType());
        assertEquals(MediaType.TEXT_PLAIN, FileTypeEnum.TEXT.getContentType());
        assertEquals(MediaType.APPLICATION_JSON, FileTypeEnum.JSON.getContentType());
        assertEquals(MediaType.APPLICATION_XML, FileTypeEnum.XML.getContentType());
        assertEquals(MediaType.TEXT_HTML, FileTypeEnum.HTM.getContentType());
        assertEquals(MediaType.TEXT_HTML, FileTypeEnum.HTML.getContentType());
        assertEquals(MediaType.TEXT_PLAIN, FileTypeEnum.PROPERTIES.getContentType());
    }
    
    @Test
    void testGetFileTypeEnumByFileExtensionOrFileType() {
        // Test exact matches (case insensitive)
        assertEquals(FileTypeEnum.YML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("yml"));
        assertEquals(FileTypeEnum.YML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("YML"));
        assertEquals(FileTypeEnum.YAML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("yaml"));
        assertEquals(FileTypeEnum.YAML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("YAML"));
        assertEquals(FileTypeEnum.TXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("txt"));
        assertEquals(FileTypeEnum.TXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("TXT"));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("text"));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("TEXT"));
        assertEquals(FileTypeEnum.JSON, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("json"));
        assertEquals(FileTypeEnum.JSON, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("JSON"));
        assertEquals(FileTypeEnum.XML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("xml"));
        assertEquals(FileTypeEnum.XML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("XML"));
        assertEquals(FileTypeEnum.HTM, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("htm"));
        assertEquals(FileTypeEnum.HTM, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("HTM"));
        assertEquals(FileTypeEnum.HTML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("html"));
        assertEquals(FileTypeEnum.HTML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("HTML"));
        assertEquals(FileTypeEnum.PROPERTIES, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("properties"));
        assertEquals(FileTypeEnum.PROPERTIES, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("PROPERTIES"));
    }
    
    @Test
    void testGetFileTypeEnumByFileExtensionOrFileTypeWithSpaces() {
        // Test with leading/trailing spaces
        assertEquals(FileTypeEnum.JSON, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType(" json "));
        assertEquals(FileTypeEnum.XML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("  xml  "));
        assertEquals(FileTypeEnum.YML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("\tyml\t"));
    }
    
    @Test
    void testGetFileTypeEnumByFileExtensionOrFileTypeWithMixedCase() {
        // Test mixed case
        assertEquals(FileTypeEnum.JSON, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("Json"));
        assertEquals(FileTypeEnum.XML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("Xml"));
        assertEquals(FileTypeEnum.HTML, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("Html"));
        assertEquals(FileTypeEnum.PROPERTIES, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("Properties"));
    }
    
    @Test
    void testGetFileTypeEnumByFileExtensionOrFileTypeDefaultValue() {
        // Test unknown extensions return TEXT
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("unknown"));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("pdf"));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("doc"));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("exe"));
    }
    
    @Test
    void testGetFileTypeEnumByFileExtensionOrFileTypeNullAndEmpty() {
        // Test null and empty values
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType(null));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType(""));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("   "));
        assertEquals(FileTypeEnum.TEXT, FileTypeEnum.getFileTypeEnumByFileExtensionOrFileType("\t\n"));
    }
    
    @Test
    void testEnumCount() {
        // Test that we have the expected number of enum values
        FileTypeEnum[] values = FileTypeEnum.values();
        assertEquals(9, values.length);
    }
    
    @Test
    void testEnumNames() {
        // Test enum names
        assertEquals("YML", FileTypeEnum.YML.name());
        assertEquals("YAML", FileTypeEnum.YAML.name());
        assertEquals("TXT", FileTypeEnum.TXT.name());
        assertEquals("TEXT", FileTypeEnum.TEXT.name());
        assertEquals("JSON", FileTypeEnum.JSON.name());
        assertEquals("XML", FileTypeEnum.XML.name());
        assertEquals("HTM", FileTypeEnum.HTM.name());
        assertEquals("HTML", FileTypeEnum.HTML.name());
        assertEquals("PROPERTIES", FileTypeEnum.PROPERTIES.name());
    }
    
    @Test
    void testValueOf() {
        // Test valueOf method
        assertEquals(FileTypeEnum.YML, FileTypeEnum.valueOf("YML"));
        assertEquals(FileTypeEnum.YAML, FileTypeEnum.valueOf("YAML"));
        assertEquals(FileTypeEnum.JSON, FileTypeEnum.valueOf("JSON"));
        assertEquals(FileTypeEnum.XML, FileTypeEnum.valueOf("XML"));
        assertEquals(FileTypeEnum.PROPERTIES, FileTypeEnum.valueOf("PROPERTIES"));
    }
    
    @Test
    void testYamlVariants() {
        // Test that both YML and YAML have the same file type
        assertEquals(FileTypeEnum.YML.getFileType(), FileTypeEnum.YAML.getFileType());
        assertEquals(FileTypeEnum.YML.getContentType(), FileTypeEnum.YAML.getContentType());
    }
    
    @Test
    void testTextVariants() {
        // Test that both TXT and TEXT have the same file type
        assertEquals(FileTypeEnum.TXT.getFileType(), FileTypeEnum.TEXT.getFileType());
        assertEquals(FileTypeEnum.TXT.getContentType(), FileTypeEnum.TEXT.getContentType());
    }
    
    @Test
    void testHtmlVariants() {
        // Test that both HTM and HTML have the same file type
        assertEquals(FileTypeEnum.HTM.getFileType(), FileTypeEnum.HTML.getFileType());
        assertEquals(FileTypeEnum.HTM.getContentType(), FileTypeEnum.HTML.getContentType());
    }
}
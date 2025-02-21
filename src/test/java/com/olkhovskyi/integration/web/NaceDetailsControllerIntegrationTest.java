
package com.olkhovskyi.integration.web;

import com.olkhovskyi.service.NaceDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NaceDetailsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NaceDetailsService naceDetailsService;

    @Test
    public void testPutNaceDetailsFromCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "Worksheet_C.csv",
                MediaType.TEXT_PLAIN_VALUE,
                getClass().getResourceAsStream("/Worksheet_C.csv")
        );

        mockMvc.perform(multipart("/v1/api/nace/putNaceDetails/csv")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
    @Test
    public void testPutNaceDetailsFromEmptyCsv() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "empty.csv",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/v1/api/nace/putNaceDetails/csv")
                        .file(emptyFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutNaceDetailsFromInvalidCsv() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile(
                "file",
                "invalid.csv",
                MediaType.TEXT_PLAIN_VALUE,
                getClass().getResourceAsStream("/invalid.csv")
        );

        mockMvc.perform(multipart("/v1/api/nace/putNaceDetails/csv")
                        .file(invalidFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }
}

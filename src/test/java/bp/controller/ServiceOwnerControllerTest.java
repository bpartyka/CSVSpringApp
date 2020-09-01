package bp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@SpringBootTest
class ServiceOwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        byte[] bArray = Files.readAllBytes(Paths.get("src/test/resources/file.csv"));
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file.csv", "applications/csv", bArray);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/v1/import/csv")
                        .file(multipartFile);

        mockMvc.perform(builder);
    }

    @Test
    public void shouldReturnIdOfRemovedRecord() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/v1/owners/1");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("1"));

    }

    @Test
    public void shouldReturnNotFoundWhenIdNotExists() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/v1/owners/11");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
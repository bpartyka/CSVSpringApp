package bp.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.nio.file.Files;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@SpringBootTest
class UploadCSVControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldUploadFileToDb() throws Exception {
        byte[] bArray = Files.readAllBytes(Paths.get("src/test/resources/file.csv"));
        MockMultipartFile multipartFile = new MockMultipartFile("file", "file.csv", "applications/csv", bArray);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/v1/import/csv")
                        .file(multipartFile);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(6)));
    }

    @Test
    public void shouldThrowExceptionWhenFileIsInvalid() throws Exception {
        byte[] bArray = Files.readAllBytes(Paths.get("src/test/resources/invalidFile.csv"));
        MockMultipartFile multipartFile = new MockMultipartFile("file", "invalidFile.csv", "applications/csv", bArray);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/v1/import/csv")
                        .file(multipartFile);


        Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        });
    }
}

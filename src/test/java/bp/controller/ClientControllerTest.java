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
class ClientControllerTest {

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
    public void shouldReturnOneRecord() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/v1/clients/1");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"id\":1,\"name\":\"Name1\",\"description\":\"Desc\",\"updatedTime\":\"2020-08-31T19:26:51.588+00:00\"}"));

    }

    @Test
    public void shouldReturnNotFoundWhenGivenIdNotExists() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/v1/clients/9");

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void shouldReturnRecordsInGivenDateRange() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/v1/clients/getByTime?timeFrom=1597518892000&timeTo=1600197292000&page=0&size=5");

        String result = "{\"content\":[{\"id\":1,\"name\":\"Name1\",\"description\":\"Desc\",\"updatedTime\":\"2020-08-31T19:26:51.588+00:00\"},{\"id\":5,\"name\":\"Name5\",\"description\":null,\"updatedTime\":\"2020-08-31T19:14:52.000+00:00\"}],\"pageable\":{\"sort\":{\"unsorted\":true,\"sorted\":false,\"empty\":true},\"offset\":0,\"pageNumber\":0,\"pageSize\":5,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalPages\":1,\"totalElements\":2,\"number\":0,\"size\":5,\"sort\":{\"unsorted\":true,\"sorted\":false,\"empty\":true},\"numberOfElements\":2,\"first\":true,\"empty\":false}";
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(result));

    }
}

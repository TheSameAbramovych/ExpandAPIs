package bohdan.abramovych.expandapis;

import bohdan.abramovych.expandapis.infra.controller.dto.ProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExpandApIsApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = "USER")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Save Product - Valid Request")
    void testSaveProductValidRequest() throws Exception {
        ProductDTO productDTO = createSampleProductDTO();

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Save Product - Invalid Request")
    void testSaveProductInvalidRequest() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get Products - Successful")
    void testGetProductsSuccessful() throws Exception {
        mockMvc.perform(get("/products/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Get Products - Nonexistent Table")
    void testGetProductsNonexistentTable() throws Exception {
        mockMvc.perform(get("/products/nonexistentTable"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Products - Invalid Method")
    void testGetProductsInvalidMethod() throws Exception {
        mockMvc.perform(post("/products/all"))
                .andExpect(status().isMethodNotAllowed());
    }

    private static ProductDTO createSampleProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTable("products");

        List<JsonNode> records = List.of(
                createSampleRecord("03-01-2023", "11111", "Test Inventory 1", "20", "Paid"),
                createSampleRecord("03-01-2023", "11111", "Test Inventory 2", "20", "Paid")
        );

        productDTO.setRecords(records);
        return productDTO;
    }

    private static JsonNode createSampleRecord(String entryDate, String itemCode, String itemName, String itemQuantity, String status) {
        String json = String.format("{\"entryDate\": \"%s\", \"itemCode\": \"%s\", \"itemName\": \"%s\", \"itemQuantity\": \"%s\", \"status\": \"%s\"}",
                entryDate, itemCode, itemName, itemQuantity, status);

        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JsonNode from sample data", e);
        }
    }
}
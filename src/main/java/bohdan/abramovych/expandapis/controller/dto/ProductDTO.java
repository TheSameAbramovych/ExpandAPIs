package bohdan.abramovych.expandapis.controller.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
@Data
public class ProductDTO {
    String table;
    List<JsonNode> records;
}

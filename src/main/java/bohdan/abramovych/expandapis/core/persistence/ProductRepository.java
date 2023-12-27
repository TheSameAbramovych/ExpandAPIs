package bohdan.abramovych.expandapis.core.persistence;

import bohdan.abramovych.expandapis.infra.controller.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

    void createTableIfNotExists(ProductDTO productDTO);

    List<Map<String, Object>> getAll(String tableName);
}

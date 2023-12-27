package bohdan.abramovych.expandapis.infra.persistence;

import bohdan.abramovych.expandapis.core.persistence.ProductRepository;
import bohdan.abramovych.expandapis.infra.controller.dto.ProductDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRepositoryMySql implements ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    ObjectMapper mapper = new ObjectMapper();

    private static final String COLUMN_TEMPLATE = "%s VARCHAR(255)";
    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE IF NOT EXISTS %s (id INT AUTO_INCREMENT PRIMARY KEY, %s)";
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SELECT_ALL_QUERY_TEMPLATE = "SELECT * FROM %s";

    @Override
    @Transactional
    public void createTableIfNotExists(ProductDTO productDTO) {
        String tableName = productDTO.getTable();
        List<JsonNode> jsonNodes = productDTO.getRecords();

        jsonNodes.forEach(node -> {
            Map<String, String> fields = mapper.convertValue(node, Map.class);
            createTableIfNotExists(tableName, fields);
            insertDataIntoTable(tableName, fields);
        });
    }

    @Override
    public List<Map<String, Object>> getAll(String tableName) {
        String selectAllQuery = String.format(SELECT_ALL_QUERY_TEMPLATE, tableName);
        return entityManager.createNativeQuery(selectAllQuery, Map.class).getResultList();
    }

    private void createTableIfNotExists(String tableName, Map<String, String> fields) {
        String columns = fields.keySet().stream()
                .map(this::toSnakeCase)
                .map(column -> String.format(COLUMN_TEMPLATE, column))
                .collect(Collectors.joining(", "));

        String createTableQuery = String.format(CREATE_TABLE_TEMPLATE, tableName, columns);
        entityManager.createNativeQuery(createTableQuery).executeUpdate();
    }

    private void insertDataIntoTable(String tableName, Map<String, String> fields) {
        String columnNames = fields.keySet().stream()
                .map(this::toSnakeCase)
                .collect(Collectors.joining(", "));

        String columnValues = fields.values().stream().map(n -> "'" + n + "'").collect(Collectors.joining(", "));
        String insertQuery = String.format(INSERT_QUERY_TEMPLATE, tableName, columnNames, columnValues);
        entityManager.createNativeQuery(insertQuery).executeUpdate();
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}


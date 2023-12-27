package bohdan.abramovych.expandapis.infra.controller;

import bohdan.abramovych.expandapis.core.persistence.ProductRepository;
import bohdan.abramovych.expandapis.infra.controller.dto.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductsController {

    ProductRepository productRepo;

    @PostMapping("/add")
    public void saveProduct(@RequestBody ProductDTO productDTO) {
        productRepo.createTableIfNotExists(productDTO);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getProducts() {
        return productRepo.getAll("products");
    }
}
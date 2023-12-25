package bohdan.abramovych.expandapis.controller;

import bohdan.abramovych.expandapis.controller.dto.ProductDTO;
import bohdan.abramovych.expandapis.repo.ProductRepo;
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
    ProductRepo productRepo;

    @PostMapping("/add")
    public void saveProduct(@RequestBody ProductDTO productDTO) {
        productRepo.createTableIfNotExists(productDTO);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getProducts() {
        return productRepo.getAll("products");
    }
}
package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AddColourRequest;
import peaksoft.dto.request.ProductInnerPageResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;
import peaksoft.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductAPI {
    private final ProductService productService;

    @Secured("ADMIN")
    @GetMapping("/getAll")
    public List<ProductResponse> findAll() {
        return productService.findAllProducts();
    }

    @Secured("ADMIN")
    @PostMapping("/save/{loginID}")
    public SimpleResponse save(@PathVariable Long loginID,
                               @RequestParam Category category,
                               @RequestBody ProductRequest productRequest) {
        return productService.save(loginID, category, productRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{productId}")
    public SimpleResponse delete(@PathVariable Long productId) {
        return productService.delete(productId);
    }

    @Secured({"ADMIN"})
    @PostMapping("/addColors/{loginID}/{productID}")
    public SimpleResponse addColours(@RequestBody AddColourRequest addColourRequest,
                                     @PathVariable Long loginID,
                                     @PathVariable Long productID
    ) {
        return productService.addColours(loginID, productID, addColourRequest);

    }

    @Secured({"ADMIN","CLIENT"})
    @PutMapping("/addFav/{loginId}/{productId}")
    public SimpleResponse addOrRemoveFavorite(@PathVariable Long loginId, @PathVariable Long productId) {
        return productService.addOrRemoveFav(loginId, productId);
    }

    @Secured({"ADMIN","CLIENT"})
    @GetMapping("/getAllFavProd/{loginId}")
    public List<ProductResponse> findFav(@PathVariable Long loginId) {
        return productService.findAllFavProducts(loginId);
    }

    @Secured({"ADMIN","CLIENT"})
    @GetMapping("prodFindById/{productId}")
    public ProductInnerPageResponse findById(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @Secured({"ADMIN","CLIENT"})
    @PutMapping("/addNewProdForBasket/{userId}/{prodId}")
    public SimpleResponse addNewProdForBasket(@PathVariable Long userId, @PathVariable Long prodId) {
        return productService.addProdForBasket(userId, prodId);
    }

    @Secured({"ADMIN","CLIENT"})
    @DeleteMapping("/deleteProdInBasket/{userId}/{prodId}")
    public SimpleResponse deleteProdInBasket(@PathVariable Long userId, @PathVariable Long prodId) {
        return productService.deleteProdInBasket(userId, prodId);
    }

    @Secured({"ADMIN","CLIENT"})
    @GetMapping("/totalSumm/{userId}")
    public Long getTotalSum(@PathVariable Long userId) {
        return productService.getTotalSum(userId);
    }
    @Secured({"ADMIN"})
    @PutMapping("/update/{prodId}")
    public SimpleResponse update(@RequestBody ProductRequest productRequest,@RequestParam Category category,@PathVariable Long prodId){
        return productService.update(category,productRequest,prodId);
    }
    @Secured({"CLIENT"})
    @DeleteMapping("/byProd")
    public SimpleResponse byMyAllProds(){
        return productService.byProd();
    }
}

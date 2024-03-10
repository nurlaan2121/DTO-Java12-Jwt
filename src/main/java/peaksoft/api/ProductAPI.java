package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AddColourRequest;
import peaksoft.dto.request.ProductInnerPageResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;
import peaksoft.service.ProductService;

import java.util.List;



/**
 * @author Mukhammed Asantegin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductAPI {
    private final ProductService productService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findAllProducts();
    }

    @Secured("ADMIN")
    @PostMapping("/{loginID}")
    public SimpleResponse save(@PathVariable Long loginID,
                               @RequestParam Category category,
                               @RequestBody ProductRequest productRequest) {
      return   productService.save(loginID, category, productRequest);
    }
    @Secured("ADMIN")
    @DeleteMapping("/{productId}")
    public SimpleResponse delete(@PathVariable Long productId){
        return productService.delete(productId);
    }

    @Secured({"ADMIN"})
    @PostMapping("/{loginID}/{productID}")
    public SimpleResponse addColours(@RequestBody AddColourRequest addColourRequest,
                                     @PathVariable Long loginID,
                                     @PathVariable Long productID
                                     ){
        return productService.addColours(loginID, productID, addColourRequest);

    }

    @PutMapping("/{loginId}/{productId}")
    public SimpleResponse addOrRemoveFavorite(@PathVariable Long loginId, @PathVariable Long productId){
        return productService.addOrRemoveFav(loginId, productId);
    }


    @GetMapping("/favorite/{loginId}")
    public List<ProductResponse> findFav(@PathVariable Long loginId){
        return productService.findAllFavProducts(loginId);
    }

    @GetMapping("/{productId}")
    public ProductInnerPageResponse findById(@PathVariable Long productId){
        return productService.findById(productId);
    }
    @PutMapping("/addNewProdForBasket/{userId}/{prodId}")
    public SimpleResponse addNewProdForBasket(@PathVariable Long userId,@PathVariable Long prodId){
        return productService.addProdForBasket(userId,prodId);
    }
    @DeleteMapping("/deleteProdInBasket/{userId}/{prodId}")
    public SimpleResponse deleteProdInBasket(@PathVariable Long userId,@PathVariable Long prodId){
        return productService.deleteProdInBasket(userId,prodId);
    }
    @GetMapping("/totalSumm/{userId}")
    public Long getTotalSum(@PathVariable Long userId){
        return productService.getTotalSum(userId);
    }
}

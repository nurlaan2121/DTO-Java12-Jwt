package peaksoft.service;

import peaksoft.dto.request.AddColourRequest;
import peaksoft.dto.request.ProductInnerPageResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;

import java.util.List;

/**
 * @author Mukhammed Asantegin
 */
public interface ProductService {
    SimpleResponse save(Long loginID, Category category, ProductRequest productRequest);

    SimpleResponse addColours(Long loginID, Long productID, AddColourRequest addColourRequest);

    List<ProductResponse> findAllProducts();

    SimpleResponse addOrRemoveFav(Long loginId, Long productId);

    List<ProductResponse> findAllFavProducts(Long loginId);

    ProductInnerPageResponse findById(Long productId);
}

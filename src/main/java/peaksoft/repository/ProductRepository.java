package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.request.ProductInnerPageResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.enums.Size;
import peaksoft.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new peaksoft.dto.response.ProductResponse(p.image, p.name, p.price) from Product p")
    List<ProductResponse> findAllProducts();

    @Query("""
            select new peaksoft.dto.response.ProductResponse(p.image, p.name, p.price)
            from User u
            join u.favoriteProducts p
            where u.id = :loginId
            """)
    List<ProductResponse> findAllFavProducts(Long loginId);

    @Query("""
            select new peaksoft.dto.request.ProductInnerPageResponse
            (
            p.image,
            p.name,
            p.price)
            from Product  p 
            where p.id = ?1
            """)
    ProductInnerPageResponse findProudctById(Long productId);

    @Query("""
            select p.sizes from 
            Product p where p.id = ?1
            """)
    List<Size> getSizes(Long productId);
}
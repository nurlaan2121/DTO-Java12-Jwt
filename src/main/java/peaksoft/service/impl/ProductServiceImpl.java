package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.AddColourRequest;
import peaksoft.dto.request.ProductInnerPageResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;
import peaksoft.enums.Role;
import peaksoft.enums.Size;
import peaksoft.model.Product;
import peaksoft.model.User;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Mukhammed Asantegin
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final UserRepository userRepository;

    private void checkLoginUser(Long loginID) {
        User loginUser = userRepository.findById(loginID).orElseThrow(() -> new NoSuchElementException("User with id: " + loginID + "not found"));
        if (!loginUser.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Forbidden 403");
        }
    }

    @Override
    public SimpleResponse save(Long loginID, Category category, ProductRequest productRequest) {
        checkLoginUser(loginID);
        System.out.println("productRequest.getName() = " + productRequest.getName());
        Product buildProduct = productRequest.build();
        buildProduct.setCategory(category);
        productRepo.save(buildProduct);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved product with name: " + buildProduct.getName());
    }

    @Override
    public SimpleResponse delete(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NoSuchElementException("Product with id: " + productId + "not found"));
        String name = product.getName();
        productRepo.delete(product);
        return new SimpleResponse(HttpStatus.OK, "Success deleted product: " + name);
    }

    @Override
    @Transactional
    public SimpleResponse addProdForBasket(Long userId, Long prodId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id: " + userId + "not found"));
        if (user.getRole().equals(Role.ADMIN)) throw new RuntimeException("Forbidden 403");
        Product product = productRepo.findById(prodId).orElseThrow(() -> new NoSuchElementException("Product with id: " + prodId + "not found"));
        user.getBasketProducts().add(product);
        product.setQuantity(product.getQuantity() - 1);
        return new SimpleResponse(HttpStatus.OK, "Product with name:   " + product.getName() + "   success add basket");
    }

    @Override
    @Transactional
    public SimpleResponse deleteProdInBasket(Long userId, Long prodId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id: " + userId + "not found"));
        if (user.getRole().equals(Role.ADMIN)) throw new RuntimeException("Forbidden 403");
        Product product = productRepo.findById(prodId).orElseThrow(() -> new NoSuchElementException("Product with id: " + prodId + "not found"));
        for (int i = 0; i < user.getBasketProducts().size(); i++) {
            if (user.getBasketProducts().get(i).getId().equals(prodId)) {
                user.getBasketProducts().remove(i);
                product.setQuantity(product.getQuantity() + 1);
                return new SimpleResponse(HttpStatus.OK, "Product with name:  " + product.getName() + " success deleted in your basket !");
            }
        }
        throw new NoSuchElementException("Product with name: " + product.getName() + " is  not your basket!");
    }

    @Override
    public Long getTotalSum(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id : " + userId + " Not found"));
        if (user.getRole().equals(Role.ADMIN)) throw new RuntimeException("Forbidden 403");
        if (!user.getBasketProducts().isEmpty()) {
            BigDecimal summ = user.getBasketProducts().get(0).getPrice();
            for (int i = 1; i < user.getBasketProducts().size(); i++) {
                summ.add(user.getBasketProducts().get(i).getPrice());
            }
            return summ.longValue();
        }
        return 0L;
    }

    @Override
    @Transactional
    public SimpleResponse addColours(Long loginID, Long productID, AddColourRequest addColourRequest) {
        checkLoginUser(loginID);
        Product product = productRepo.findById(productID).orElseThrow(() -> new NoSuchElementException("Product with id: " + productID + "not found"));
        for (String colour : addColourRequest.colours()) {
            product.addColour(colour);
        }
        return new SimpleResponse(HttpStatus.OK, "Success add colours");

    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepo.findAllProducts();

    }

    @Override
    @Transactional
    public SimpleResponse addOrRemoveFav(Long loginId, Long productId) {
        User loginUser = userRepository.findById(loginId).orElseThrow(() -> new NoSuchElementException("User with id: " + loginId + "not found"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new NoSuchElementException("Product with id: " + productId + "not found"));
        if (loginUser.getRole().equals(Role.ADMIN)) throw new RuntimeException("Forbidden 403");
        if (loginUser.getFavoriteProducts().contains(product)) {
            loginUser.getFavoriteProducts().remove(product);
            return SimpleResponse.builder().httpStatus(HttpStatus.ACCEPTED).message("Product removed from favorite").build();
        }
        loginUser.getFavoriteProducts().add(product);
        return SimpleResponse.builder().httpStatus(HttpStatus.ACCEPTED).message("Product added to favorite").build();
    }

    @Override
    public List<ProductResponse> findAllFavProducts(Long loginId) {
        return productRepo.findAllFavProducts(loginId);
    }

    @Override
    public ProductInnerPageResponse findById(Long productId) {
        ProductInnerPageResponse product = productRepo.findProudctById(productId);
        product.setSizes(productRepo.getSizes(productId));
        return product;

    }

}

package com.edu.shopservice.repository;

import com.edu.shopservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("""
                UPDATE Product p
                   SET p.availableStock = p.availableStock - :delta
                 WHERE p.id = :id AND p.availableStock >= :delta
            """)
    int decreaseStock(@Param("id") Long id,
                      @Param("delta") int delta);


    @Modifying
    @Query("""
                 UPDATE Product p
                    SET p.price = :newPrice
                  WHERE p.id = :id
            """)
    int updatePrice(@Param("id") Long id, @Param("newPrice") BigDecimal newPrice);

}



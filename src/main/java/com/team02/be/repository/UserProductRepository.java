package com.team02.be.repository;
import com.team02.be.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    // (:param IS NULL → 파라미터 없으면 조건 무시) 패턴
    @Query("SELECT p FROM Product p WHERE " +
            "(:isDecaf IS NULL OR p.isDecaf = :isDecaf) AND " +
            "(:roastingLevel IS NULL OR p.roastingLevel = :roastingLevel) AND " +
            "(:acidity IS NULL OR p.acidity = :acidity)")
    List<Product> findByFilters(
            @Param("isDecaf") Boolean isDecaf,
            @Param("roastingLevel") Product.RoastingLevel roastingLevel,
            @Param("acidity") Boolean acidity
    );
}

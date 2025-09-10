package com.devsaadeh.dscatalog.repositories;

import com.devsaadeh.dscatalog.entities.Product;
import com.devsaadeh.dscatalog.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(nativeQuery = true, value = """
            select distinct p.id, p.name
            from tb_product as p
            inner join tb_product_category as pc on p.id = pc.product_id
            inner join tb_category as c on c.id =pc.category_id
            where lower(p.name) like LOWER(CONCAT('%' ,:name, '%'))
            and (:categoryIDs IS NULL OR c.id in :categoryIDs )
            """, countQuery = """
            select count(*) from (
            select distinct p.id, p.name
            from tb_product as p
            inner join tb_product_category as pc on p.id = pc.product_id
            inner join tb_category as c on c.id =pc.category_id
            where lower(p.name) like LOWER(CONCAT('%' ,:name, '%'))
            and (:categoryIDs IS NULL OR c.id in :categoryIDs )
             ) AS tb_result
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIDs, String name, Pageable pageable);

    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productIds ")
    List<Product> searchProductsWithCategories(List<Long> productIds);
}

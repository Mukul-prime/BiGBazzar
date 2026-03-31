package com.example.BigBazzarServer.DAO;

import com.example.BigBazzarServer.Model.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductImageDAO extends JpaRepository<ProductImages , Long> {
    List<ProductImages> findByProductProductId(int productId);
}

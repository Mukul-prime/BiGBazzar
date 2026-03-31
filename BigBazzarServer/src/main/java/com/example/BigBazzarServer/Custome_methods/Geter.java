package com.example.BigBazzarServer.Custome_methods;

import com.example.BigBazzarServer.DAO.ProductImageDAO;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.ProductImages;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Component
public class Geter {

    @Autowired
    private  ProductImageDAO productImageDAO;

    public  List<String> getproductimages(Product product){
        List<ProductImages> as =
                productImageDAO.findByProductProductId(product.getProductId());

        List<String> ans = new ArrayList<>();

        for(ProductImages p : as){
            if(p.getImage()!=null){
                ans.add(Base64.getEncoder().encodeToString(p.getImage()));
            }
        }
        return ans;
    }
}

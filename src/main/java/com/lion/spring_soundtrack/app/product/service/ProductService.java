package com.lion.spring_soundtrack.app.product.service;

import com.lion.spring_soundtrack.app.product.entity.Product;
import com.lion.spring_soundtrack.app.product.repository.ProductRepository;
import com.lion.spring_soundtrack.app.song.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Dictionary;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Song song, String subject, int price) {
         Product product = Product.builder()
                 .subject(subject)
                 .song(song)
                 .author(song.getAuthor())
                 .price(price)
                 .build();

         return productRepository.save(product);
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void modify(Product product, String subject, int price) {
        product.setSubject(subject);
        product.setPrice(price);
    }

}

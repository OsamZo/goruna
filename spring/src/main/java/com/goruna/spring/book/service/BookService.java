package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 예약 생성하기
    @Transactional
    public void createBook(Long productSeq, Long userSeq, int bookQty) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        Product product = productRepository.findById(productSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.PRODUCT_NOT_FOUND));

        // 해당 상품의 남은 수량
        int productQty = product.getProductQty();

        // 예약하려는 수량이 남은 수량보다 많다면 에러 발생, 그렇지 않다면 재고 변경
        if (bookQty <= productQty) {
            product.updateProductQty(productQty - bookQty);
            productRepository.save(product);
        } else {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        Book book = Book.builder()
                .user(user)
                .product(product)
                .bookQty(bookQty)
                .build();

        bookRepository.save(book);
    }

    // 회원별 예약 리스트 가져오기
    public List<BookListReadResDTO> readBookListByUserSeq(Long userSeq) {
        List<Book> books = bookRepository.findByUserUserSeq(userSeq);
        return books.stream()
                .map(book -> {
                    BookListReadResDTO bookListReadResDTO = modelMapper.map(book, BookListReadResDTO.class);
                    bookListReadResDTO.setShopName(book.getProduct().getShop().getShopName());
                    bookListReadResDTO.setShopAddress(book.getProduct().getShop().getShopAddress());
                    bookListReadResDTO.setShopImgUrl(book.getProduct().getShop().getShopImgUrl());
                    bookListReadResDTO.setIsBookCancelled(book.getIsBookCancelled());
                    return bookListReadResDTO;
                })
                .collect(Collectors.toList());
    }

    // 예약 취소하기
    @Transactional
    public void cancelBook(Long bookSeq) {
        Book book = bookRepository.findById(bookSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        Product product = productRepository.findById(book.getProduct().getProductSeq())
                        .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        book.cancelBook();
        product.updateProductQty(product.getProductQty() + book.getBookQty());

        bookRepository.save(book);
    }
}

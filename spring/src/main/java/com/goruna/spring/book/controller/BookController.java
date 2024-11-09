package com.goruna.spring.book.controller;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.service.BookService;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "book", description = "예약 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 예약 신청
    @PostMapping("/product/{productSeq}")
    @Operation(summary = "예약 신청", description = "예약을 신청합니다.")
    public ApiResponse<?> createBook(
            @PathVariable(value = "productSeq") Long productSeq,
            @RequestParam Long userSeq,
            @RequestParam int bookQty
    ) {
        bookService.createBook(productSeq, userSeq, bookQty);
        return ResponseUtil.successResponse("예약이 성공적으로 생성되었습니다.").getBody();
    }

    // 예약 취소
    @PutMapping("/book/{bookSeq}")
    @Operation(summary="예약 취소", description="예약을 취소합니다.")
    public ApiResponse<?> cancelBook(@PathVariable(value="bookSeq") Long bookSeq) {
        bookService.cancelBook(bookSeq);
        return ResponseUtil.successResponse("예약이 성공적으로 취소되었습니다.").getBody();
    }

    // 회원 예약 내역 조회
    @GetMapping("/user/{userSeq}/book")
    @Operation(summary = "회원 예약 내역 조회", description = "회원 예약 내역을 조회합니다.")
    public ApiResponse<?> getBookListByUserSeq(@PathVariable(value = "userSeq") Long userSeq) {
        List<BookListReadResDTO> bookListReadResDTOs = bookService.readBookListByUserSeq(userSeq);
        return ResponseUtil.successResponse("회원 예약 내역 조회가 성공적으로 완료되었습니다.", bookListReadResDTOs).getBody();
    }
}
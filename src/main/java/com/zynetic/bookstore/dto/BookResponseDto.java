package com.zynetic.bookstore.dto;

import com.zynetic.bookstore.enums.BookCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class BookResponseDto {
    private UUID bookId;
    private String title;
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    private String price;

    private Date publishDate;
}

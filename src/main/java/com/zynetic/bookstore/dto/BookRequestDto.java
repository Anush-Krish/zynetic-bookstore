package com.zynetic.bookstore.dto;

import com.zynetic.bookstore.enums.BookCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class BookRequestDto {

    @NonNull
    private String title;
    @NonNull
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    private String price;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date publishDate;
}

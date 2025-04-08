package com.zynetic.bookstore.entity;

import com.zynetic.bookstore.enums.BookCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "books")
@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookId;
    @NonNull
    private String title;
    @NonNull
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    private String price;

    private Date publishDate;

    //rating
}

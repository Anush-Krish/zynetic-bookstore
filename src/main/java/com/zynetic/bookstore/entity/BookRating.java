package com.zynetic.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "book_ratings")
@Getter
@Setter
public class BookRating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ratingId;

    private Integer rating;
    private UUID bookId;

    private UUID userId;
}

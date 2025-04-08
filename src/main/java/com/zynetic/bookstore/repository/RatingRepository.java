package com.zynetic.bookstore.repository;

import com.zynetic.bookstore.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<BookRating, UUID> {
    List<BookRating> findAllByBookId(UUID bookId);
}

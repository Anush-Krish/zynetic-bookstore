package com.zynetic.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RatingDto {
    private UUID ratingId;

    private Integer rating;
    private UUID bookId;

    private UUID userId;
}

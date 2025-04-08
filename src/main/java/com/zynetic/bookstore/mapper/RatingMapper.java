package com.zynetic.bookstore.mapper;

import com.zynetic.bookstore.dto.RatingDto;
import com.zynetic.bookstore.entity.BookRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {
    RatingDto toDto(BookRating rating);
    BookRating toEntity(RatingDto ratingDto);
}

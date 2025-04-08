package com.zynetic.bookstore.mapper;

import com.zynetic.bookstore.dto.BookRequestDto;
import com.zynetic.bookstore.dto.BookResponseDto;
import com.zynetic.bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    Book toEntity(BookRequestDto requestDto);

    BookResponseDto toResponseDto(Book book);

    List<BookResponseDto> toResponseDtoList(List<Book> bookList);

    void updateEntityFromDto(BookRequestDto dto, @MappingTarget Book book);

}

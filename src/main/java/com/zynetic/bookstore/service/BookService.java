package com.zynetic.bookstore.service;

import com.zynetic.bookstore.dto.BookRequestDto;
import com.zynetic.bookstore.dto.BookResponseDto;
import com.zynetic.bookstore.dto.RatingDto;
import com.zynetic.bookstore.entity.Book;
import com.zynetic.bookstore.entity.BookRating;
import com.zynetic.bookstore.mapper.BookMapper;
import com.zynetic.bookstore.mapper.RatingMapper;
import com.zynetic.bookstore.repository.BookRepository;
import com.zynetic.bookstore.repository.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;

    public BookResponseDto createBook(BookRequestDto requestDto) {
        try {
            //handled null check in request dto itself.
            Book book = bookMapper.toEntity(requestDto);

            Book savedBook = bookRepository.save(book);
            return bookMapper.toResponseDto(savedBook);
        } catch (Exception e) {
            log.error("Error creating book{}", e.getMessage());
            throw new RuntimeException("Book creation failed");
        }
    }

    public BookResponseDto getBookById(UUID bookId) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    BookResponseDto dto = bookMapper.toResponseDto(book);
                    populateAverageRating(dto);
                    return dto;
                })
                .orElseThrow(() -> {
                    log.error("Book with bookId {} does not exist.", bookId);
                    return new RuntimeException("Book does not exist.");
                });
    }

    public List<BookResponseDto> getAllBooksPaginated(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Book> books = bookRepository.findAllBy(pageable);
            List<BookResponseDto> responseDtos = bookMapper.toResponseDtoList(books);
            populateAverageRating(responseDtos);
            return responseDtos;

        } catch (Exception e) {
            log.error("Error in fetching all books{}", e.getMessage());
            throw new RuntimeException("Error fetching all books");
        }
    }

    @Transactional
    public BookResponseDto updateBook(UUID bookId, BookRequestDto requestDto) {
        try {
            Book existingBook = bookRepository.findById(bookId)
                    .orElseThrow(() -> {
                        log.error("Book with bookId {} does not exist.", bookId);
                        return new RuntimeException("Book not found for update.");
                    });

            bookMapper.updateEntityFromDto(requestDto, existingBook);

            Book updatedBook = bookRepository.save(existingBook);
            return bookMapper.toResponseDto(updatedBook);
        } catch (Exception e) {
            log.error("Error updating bookId {}: {}", bookId, e.getMessage());
            throw new RuntimeException("Error updating book.");
        }
    }


    @Transactional
    public void deleteBook(UUID bookId) {
        try {
            bookRepository.deleteById(bookId);
            log.info("Book with bookId{} deleted successful.", bookId);
        } catch (Exception e) {
            log.error("Error in deleting bookId{}, :{}", bookId, e.getMessage());
            throw new RuntimeException("Error in deleting book.");
        }
    }

    public RatingDto createRating(RatingDto ratingDto) {
        BookRating rating = ratingMapper.toEntity(ratingDto);
        rating.setUserId(rating.getUserId());//todo impl common util to extract userid from jwt
        BookRating savedRating = ratingRepository.save(rating);
        return ratingMapper.toDto(savedRating);
    }

    private void populateAverageRating(BookResponseDto bookResponseDto) {
        List<BookRating> ratings = ratingRepository.findAllByBookId(bookResponseDto.getBookId());
        if (!ratings.isEmpty()) {
            double average = ratings.stream()
                    .mapToInt(BookRating::getRating)
                    .average()
                    .orElse(0.0);
            bookResponseDto.setAverageRating(average);
        } else {
            bookResponseDto.setAverageRating(0.0);
        }
    }

    private void populateAverageRating(List<BookResponseDto> bookResponseDtoList) {
        for (BookResponseDto dto : bookResponseDtoList) {
            populateAverageRating(dto);
        }
    }

    public List<BookResponseDto> filterBooks(String author, String category, String title, Double minRating, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Book> books = bookRepository.searchBooks(author, category, title, pageable);
        List<BookResponseDto> dtos = bookMapper.toResponseDtoList(books);
        populateAverageRating(dtos);

        if (minRating != null) {
            dtos = dtos.stream()
                    .filter(dto -> dto.getAverageRating() >= minRating)
                    .toList();
        }

        return dtos;
    }



}

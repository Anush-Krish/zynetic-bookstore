package com.zynetic.bookstore.service;

import com.zynetic.bookstore.dto.BookRequestDto;
import com.zynetic.bookstore.dto.BookResponseDto;
import com.zynetic.bookstore.entity.Book;
import com.zynetic.bookstore.mapper.BookMapper;
import com.zynetic.bookstore.repository.BookRepository;
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
                .map(bookMapper::toResponseDto)
                .orElseThrow(() -> {
                    log.error("Book with bookId {} does not exist.", bookId);
                    return new RuntimeException("Book does not exist.");
                });
    }

    public List<BookResponseDto> getAllBooksPaginated(Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Book> bookResponseDtoList = bookRepository.findAllBy(pageable);
            return bookMapper.toResponseDtoList(bookResponseDtoList);
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

}

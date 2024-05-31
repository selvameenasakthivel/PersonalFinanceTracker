package com.example.FinanceTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.FinanceTracker.dto.BookDTO;
import com.example.FinanceTracker.model.Book;
import com.example.FinanceTracker.repository.BookRepository;
import com.example.FinanceTracker.serviceImpl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private BookServiceImpl bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllBooks() {
		List<Book> books = Arrays.asList(new Book(), new Book());
		when(bookRepository.findAll()).thenReturn(books);
		when(objectMapper.convertValue(any(), eq(BookDTO.class))).thenReturn(new BookDTO());
		List<BookDTO> result = bookService.getAllBooks();
		assertEquals(2, result.size());
		verify(bookRepository, times(1)).findAll();
		verify(objectMapper, times(2)).convertValue(any(), eq(BookDTO.class));
	}

	@Test
	void testAddBook() {
		BookDTO bookDTO = new BookDTO();
		Book book = new Book();
		when(objectMapper.convertValue(bookDTO, Book.class)).thenReturn(book);
		when(bookRepository.save(book)).thenReturn(book);
		when(objectMapper.convertValue(book, BookDTO.class)).thenReturn(bookDTO);
		BookDTO result = bookService.addBook(bookDTO);
		assertEquals(bookDTO, result);
		verify(objectMapper, times(1)).convertValue(bookDTO, Book.class);
		verify(bookRepository, times(1)).save(book);
		verify(objectMapper, times(1)).convertValue(book, BookDTO.class);
	}

	@Test
	void testSearchBooks() {
		String bookName = "Test Book";
		List<Book> books = Arrays.asList(new Book(), new Book());
		when(bookRepository.findBybookNameContainingIgnoreCase(bookName)).thenReturn(books);
		when(objectMapper.convertValue(any(), eq(BookDTO.class))).thenReturn(new BookDTO());
		List<BookDTO> result = bookService.searchBooks(bookName);
		assertEquals(2, result.size());
		verify(bookRepository, times(1)).findBybookNameContainingIgnoreCase(bookName);
		verify(objectMapper, times(2)).convertValue(any(), eq(BookDTO.class));
	}
}

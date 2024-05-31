package com.example.FinanceTracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.FinanceTracker.dto.BookDTO;
import com.example.FinanceTracker.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

	@Mock
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateBook() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setBookName("Sample Book");
		when(bookService.addBook(bookDTO)).thenReturn(bookDTO);
		ResponseEntity<BookDTO> responseEntity = bookController.createBook(bookDTO);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(bookDTO, responseEntity.getBody());
		verify(bookService, times(1)).addBook(bookDTO);
	}

	@Test
	void testGetAllBooks() {
		BookDTO book1 = new BookDTO();
		book1.setBookName("Book 1");
		BookDTO book2 = new BookDTO();
		book2.setBookName("Book 2");
		List<BookDTO> bookList = Arrays.asList(book1, book2);
		when(bookService.getAllBooks()).thenReturn(bookList);
		ResponseEntity<List<BookDTO>> responseEntity = bookController.getAllBooks();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookList, responseEntity.getBody());
		verify(bookService, times(1)).getAllBooks();
	}

	@Test
	void testSearchBooks() {
		String bookName = "Sample";
		BookDTO book1 = new BookDTO();
		book1.setBookName("Sample Book 1");
		BookDTO book2 = new BookDTO();
		book2.setBookName("Sample Book 2");
		List<BookDTO> bookList = Arrays.asList(book1, book2);
		when(bookService.searchBooks(bookName)).thenReturn(bookList);
		ResponseEntity<List<BookDTO>> responseEntity = bookController.searchBooks(bookName);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(bookList, responseEntity.getBody());
		verify(bookService, times(1)).searchBooks(bookName);
	}
}

package com.example.FinanceTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceTracker.dto.BookDTO;
import com.example.FinanceTracker.model.Book;
import com.example.FinanceTracker.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book) {
		BookDTO createdBook = bookService.addBook(book);
		return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		List<BookDTO> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<BookDTO>> searchBooks(@RequestParam(required = false) String bookName) {
		List<BookDTO> books = bookService.searchBooks(bookName);
		return ResponseEntity.ok(books);
	}
}

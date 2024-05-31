package com.example.FinanceTracker.service;

import java.util.List;

import com.example.FinanceTracker.dto.BookDTO;
import com.example.FinanceTracker.model.Book;

public interface BookService {

	List<BookDTO> getAllBooks();

	BookDTO addBook(BookDTO book);

	List<BookDTO> searchBooks(String bookName);

}

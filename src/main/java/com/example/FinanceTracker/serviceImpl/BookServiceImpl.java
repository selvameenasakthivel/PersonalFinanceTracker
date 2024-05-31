package com.example.FinanceTracker.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceTracker.dto.BookDTO;
import com.example.FinanceTracker.model.Book;
import com.example.FinanceTracker.repository.BookRepository;
import com.example.FinanceTracker.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	ObjectMapper mapper;

	@Override
	public List<BookDTO> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books.stream().map(book -> mapper.convertValue(book, BookDTO.class)).collect(Collectors.toList());
	}

	@Override
	public BookDTO addBook(BookDTO bookDTO) {
		Book book = mapper.convertValue(bookDTO, Book.class);
		Book savedBook = bookRepository.save(book);
		return mapper.convertValue(savedBook, BookDTO.class);
	}

	@Override
	public List<BookDTO> searchBooks(String bookName) {
		List<Book> books = bookRepository.findBybookNameContainingIgnoreCase(bookName);
		return books.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private BookDTO mapToDTO(Book book) {
		return mapper.convertValue(book, BookDTO.class);
	}
}

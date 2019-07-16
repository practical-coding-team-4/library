package kr.ac.ajou.library.service;

import kr.ac.ajou.library.domain.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LibraryService {
    Map<Book, Integer> library = new HashMap<>();

    public void addBook(Book book) {
        checkInput(book.getName());
        checkInput(book.getAuthor());
        checkInput(book.getIsbn());

        int quantity = 0;
        for(Book registered : library.keySet()) {
            if(registered == book) {
                quantity = library.get(book);
                break;
            }
        }
        library.put(book, quantity + 1);
    }

    private void checkInput(String tmp) {
        if (tmp == null || tmp.trim().isEmpty())
            throw new RuntimeException();
    }
}

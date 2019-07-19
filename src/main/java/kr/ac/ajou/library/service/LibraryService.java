package kr.ac.ajou.library.service;

import kr.ac.ajou.library.domain.Book;
import kr.ac.ajou.library.domain.BookStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

@Service
public class LibraryService {
    Map<Book, BookStatus> library = new HashMap<>();

    public Book addBook(Book book) {
        checkInput(book.getName());
        checkInput(book.getAuthor());
        checkInput(book.getIsbn());

        if(library.get(book) == null) {
            BookStatus bookStatus = new BookStatus();
            bookStatus.setQuantity(1);
            bookStatus.setAvailable(1);
            library.put(book, bookStatus);
        }else{
            int quantity = library.get(book).getQuantity();
            int availalbe = library.get(book).getAvailable();
            library.get(book).setQuantity(quantity + 1);
            library.get(book).setAvailable(availalbe + 1);
            library.put(book, library.get(book));
        }
        return book;
    }

    private void checkInput(String tmp) {
        if (tmp == null || tmp.trim().isEmpty())
            throw new RuntimeException();
    }

    public String searchBook(String bookName){
        String searchResult ="";

        for(Book book: library.keySet()) {
            if(book.getName().contains(bookName)){
                searchResult += book.getName();
                searchResult += "/ ";
                searchResult += book.getAuthor();
                searchResult += "/ ";
                searchResult += book.getIsbn();
                searchResult += "\n";
            }
        }
        if(searchResult==""){
            searchResult = "검색 결과 없음.";
        }
        return searchResult;
    }


    public Book loanBook(Book book){
        /*
        checkInput(book.getName());
        checkInput(book.getAuthor());
        checkInput(book.getIsbn());
        */
        if(library.get(book) == null) {
            System.out.println("해당 책 없음");
            return null;
        }

        int available = library.get(book).getAvailable();
        if(available == 0){
            System.out.println("책이 모두 대여중 입니다.");
            return null;
        }
        library.get(book).setAvailable(available - 1);
        System.out.println("해당 책 대여에 성공했습니다.");
        return book;
    }

    public void returnBook(Book book){
        /*
        checkInput(book.getName());
        checkInput(book.getAuthor());
        checkInput(book.getIsbn());
        */
        if(library.get(book) == null) {
            System.out.println("해당 책 없음");
            return;
        }

        int available = library.get(book).getAvailable() + 1;

        if(available <= library.get(book).getQuantity()){
            System.out.println("해당 책 반납에 성공했습니다.");
            library.get(book).setAvailable(available);
        }else{
            throw new RuntimeException();
        }
    }
}

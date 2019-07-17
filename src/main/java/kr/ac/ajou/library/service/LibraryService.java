package kr.ac.ajou.library.service;

import kr.ac.ajou.library.domain.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LibraryService {
    Map<Book, Integer> library = new HashMap<>();

    public void addBook(Book book) {
        checkInput(book.getName());
        checkInput(book.getAuthor());
        checkInput(book.getIsbn());
        Integer quantity = library.get(book);

        if(quantity == null)
            quantity = 0;

        library.put(book, quantity + 1);
    }

    private void checkInput(String tmp) {
        if (tmp == null || tmp.trim().isEmpty())
            throw new RuntimeException();
    }

    public void searchBook(String bookName){
        checkInput(bookName);
        boolean searchFlag = false;
        for(Book book: library.keySet()) {
            if(book.getName().contains(bookName)){
                System.out.println(book.getName()+
                        ", 저자 : "+book.getAuthor()+
                        ", ISBN : "+book.getIsbn()+"\n");
                searchFlag = true;
            }
        }
        if(searchFlag == false)
            System.out.println("검색 결과 없음");
    }
}

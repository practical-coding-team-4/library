package kr.ac.ajou.library.service;

import kr.ac.ajou.library.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

//@RunWith(SpringJUnit4ClassRunner.class)
public class LibraryServiceTest {
    // @Autowired
    // LibraryService libraryService;

    @InjectMocks
    private LibraryService mockLibraryService;

    LibraryService libraryService = new LibraryService();
    @Before
    public void emptyBookList() {
        libraryService.library.clear();
    }

    @Test
    public void testAddBookSizeIncrease() {
        Book book1 = Book.builder().name("Foo").author("Bob").isbn("12345").build();
        Book book2 = Book.builder().name("Bar").author("Alice").isbn("67890").build();
        libraryService.addBook(book1);
        assertThat(libraryService.library.keySet(), hasSize(1));
        libraryService.addBook(book2);
        assertThat(libraryService.library.keySet(), hasSize(2));
    }

    @Test(expected=RuntimeException.class)
    public void testAddBookThrowExceptionEmptyName() {
        Book book = Book.builder().name("").author("Bob").isbn("12345").build();
        libraryService.addBook(book);

    }
    @Test(expected=RuntimeException.class)
    public void testAddBookThrowExceptionEmptyAuthor() {
        Book book = Book.builder().name("Foo").author("").isbn("12345").build();
        libraryService.addBook(book);
    }
    @Test(expected=RuntimeException.class)
    public void testAddBookThrowExceptionEmptyIsbn() {
        Book book = Book.builder().name("Foo").author("Bob").isbn("").build();
        libraryService.addBook(book);
    }
    @Test(expected=RuntimeException.class)
    public void testAddBookThrowExceptionWhiteSpace() {
        Book book = Book.builder().name("Foo").author("Bob").isbn("  ").build();
        libraryService.addBook(book);
    }
    @Test(expected=RuntimeException.class)
    public void testAddBookThrowExceptionNullInput() {
        Book book = Book.builder().name("Foo").author("Bob").build();
        libraryService.addBook(book);
    }
    @Test
    public void testAddBookDuplicateBookIncrementSize() {
        Book book = Book.builder().name("Foo").author("Bob").isbn("12345").build();
        libraryService.addBook(book);
        assertThat(libraryService.library.keySet(), hasSize(1));
        libraryService.addBook(book);
        assertThat(libraryService.library.keySet(), hasSize(1));
    }
    @Test
    public void testSearchBook_InputFullBookName(){
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();
        libraryService.addBook(book);
        String result = libraryService.searchBook("Harry Potter and the Sorcerer's Stone");
        System.out.println(result);
        assertThat(result, is("Harry Potter and the Sorcerer's Stone/ J.K.R/ 12345\n"));
    }
    @Test
    public void testSearchBook_InputKeywordOfBookName(){
        Book book2 = Book.builder().name("Harry Potter and the Chamber of Secrets").author("J.K.R").isbn("12346").build();
        Book book3 = Book.builder().name("Harry Potter And The Prisoner Of Azkaban").author("J.K.R").isbn("12347").build();
        libraryService.addBook(book2);
        libraryService.addBook(book3);
        String result = libraryService.searchBook("Harry Potter");
        System.out.println(result);
        assertThat(result,is(
                "Harry Potter and the Chamber of Secrets/ J.K.R/ 12346\n" +
                        "Harry Potter And The Prisoner Of Azkaban/ J.K.R/ 12347\n"));
    }
    @Test
    public void testSearchBook_InputEmptyBookName(){
        String result = libraryService.searchBook("");
        System.out.println(result);
        assertThat(result,is("검색 결과 없음."));
    }
    @Test
    public void testSearchBook_InputNonExistBookName(){
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();
        libraryService.addBook(book);
        String result = libraryService.searchBook("Aladdin");
        System.out.println(result);
        assertThat(result,is("검색 결과 없음."));
    }
    @Test(timeout = 5000)
    public void testSearchBook_SearchShouldRunIn5sec(){
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();
        libraryService.addBook(book);
        libraryService.searchBook("Harry Potter");
    }

    @Test
    public void validReturnCase(){
        libraryService = mock(LibraryService.class);
        Random random = new Random();
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();

        int randomNum_adding = random.nextInt(10);
        for(int i = 0; i < randomNum_adding; i++){
            libraryService.addBook(book);
        }

        int randomNum_loaning = getRandom(random, randomNum_adding);
        for(int i = 0; i < randomNum_loaning; i++){
            libraryService.loanBook(book);
        }

        int randomNum_returning = getRandom(random, randomNum_loaning);
        for(int i = 0; i < randomNum_returning; i++){
            libraryService.returnBook(book);
        }

        Mockito.verify(libraryService, atMost(randomNum_loaning)).returnBook(book);
    }
    private int getRandom(Random random, int r1) {
        //random.nextInt(a)일 때, a에 0이 들어가면 오류가 나기 때문에 만든 함수입니다.
        int R2;
        if (r1 == 0) {
            R2 = 0;
        } else {
            R2 = random.nextInt(r1);
        }
        return R2;
    }

}

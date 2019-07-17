package kr.ac.ajou.library.service;

import kr.ac.ajou.library.domain.Book;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// @RunWith(SpringJUnit4ClassRunner.class)
public class LibraryServiceTest {
    // @Autowired
    // LibraryService libraryService;
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
        libraryService.searchBook("Harry Potter and the Sorcerer's Stone");
    }
    @Test
    public void testSearchBook_InputKeywordOfBookName(){
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();
        Book book2 = Book.builder().name("Harry Potter and the Chamber of Secrets").author("J.K.R").isbn("12346").build();
        Book book3 = Book.builder().name("Harry Potter And The Prisoner Of Azkaban").author("J.K.R").isbn("12347").build();
        Book book4 = Book.builder().name("Harry Potter and the Goblet of Fire").author("J.K.R").isbn("12348").build();
        libraryService.addBook(book);
        libraryService.addBook(book2);
        libraryService.addBook(book3);
        libraryService.addBook(book4);
        libraryService.searchBook("Harry Potter");
    }
    @Test(expected=RuntimeException.class)
    public void testSearchBook_ThrowExceptionEmptyBookName(){
        libraryService.searchBook("");
    }
    @Test(timeout = 5000)
    public void testSearchBook_SearchShouldRunIn5sec(){
        Book book = Book.builder().name("Harry Potter and the Sorcerer's Stone").author("J.K.R").isbn("12345").build();
        libraryService.addBook(book);
        libraryService.searchBook("Harry Potter");
    }



}

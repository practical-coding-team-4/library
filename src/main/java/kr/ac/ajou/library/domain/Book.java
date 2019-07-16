package kr.ac.ajou.library.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    String name;
    String author;
    String isbn;
}

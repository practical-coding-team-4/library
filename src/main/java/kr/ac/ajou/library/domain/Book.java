package kr.ac.ajou.library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Book {
    String name;
    String author;
    String isbn;
}

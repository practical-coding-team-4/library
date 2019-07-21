package kr.ac.ajou.library.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class BookStatus {
    int available;
    int quantity;
}

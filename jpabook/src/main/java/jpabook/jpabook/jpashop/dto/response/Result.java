package jpabook.jpabook.jpashop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Result<T>{

    private int count;
    private T data;
}

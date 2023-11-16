package com.lbg.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event<T> {

    private T data;

    public static <T> Event<T> of(T data) {
        return new Event<>(data);
    }
}

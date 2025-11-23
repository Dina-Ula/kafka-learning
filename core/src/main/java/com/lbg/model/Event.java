package com.lbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event<T> {

    private String eventName;

    private T data;

    Event(T data) {
        this.data = data;
    }

    public static <T> Event<T> of(T data) {
        return new Event<>(data);
    }
}

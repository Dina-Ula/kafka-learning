package com.lbg.model;

import lombok.Data;

@Data
public class Event<T> {
    private T data;
}

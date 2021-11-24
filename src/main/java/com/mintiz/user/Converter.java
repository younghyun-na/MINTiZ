package com.mintiz.user;

public interface Converter<S, T>{
    T convert(S source);
}
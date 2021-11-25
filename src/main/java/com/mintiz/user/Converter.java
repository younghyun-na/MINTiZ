package com.mintiz.user;

public interface Converter<S, T>{  // S가 변환하기 전
    T convert(S source);
}
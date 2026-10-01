package com.klinik.antrian.util;

public interface Antrian<T> {
    void tambahAntrian(T item);
    T panggilBerikutnya();
    boolean isEmpty();
    int ukuran();
}
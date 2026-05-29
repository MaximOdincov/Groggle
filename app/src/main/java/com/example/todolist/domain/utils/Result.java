package com.example.todolist.domain.utils;

public class Result<T> {
    private final T data;
    private final Throwable error;
    private final boolean success;

    private Result(T data, Throwable error, boolean success) {
        this.data = data;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, true);
    }

    public static <T> Result<T> failure(Throwable error) {
        return new Result<>(null, error, false);
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }
}

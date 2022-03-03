package com.osenov.rickandmorty.util.network

data class Result<out T>(val status: Status, val data: T?, val error: Error?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): Result<T> {
            return Result(Status.ERROR, null, error, message)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null, null)
        }
    }

}
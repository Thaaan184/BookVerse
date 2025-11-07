package com.app.bookverse.api;

import com.app.bookverse.model.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("books")
    Call<List<Book>> getBooks();

    @GET("books/{id}")
    Call<Book> getBookById(@Path("id") int id);
}
package com.app.bookverse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.app.bookverse.R;
import com.app.bookverse.api.ApiService;
import com.app.bookverse.api.RetrofitClient;
import com.app.bookverse.databinding.FragmentProductDetailBinding;
import com.app.bookverse.model.Book;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailFragment extends BottomSheetDialogFragment {
    private FragmentProductDetailBinding binding;
    private static final String ARG_ID = "id";

    public static BookDetailFragment newInstance(int bookId) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        int bookId = getArguments().getInt(ARG_ID);
        fetchBookDetail(bookId);
        binding.btnBuy.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    private void fetchBookDetail(int id) {
        ApiService api = RetrofitClient.getClient(requireContext()).create(ApiService.class);
        api.getBookById(id).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Book book = response.body().get(0);
                    binding.txtName.setText(book.getTitle());
                    binding.txtAuthor.setText(book.getAuthor());
                    binding.txtPrice.setText("Rp " + book.getPrice());
                    binding.txtDesc.setText(book.getDescription());
                    binding.ratingBar.setRating(book.getRating());
                    Glide.with(getContext())
                            .load("https://yourproject.supabase.co/storage/v1/object/public/" + book.getCover()) // Gunakan path langsung dari cover
                            .placeholder(R.drawable.placeholder)
                            .into(binding.imgProduct);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
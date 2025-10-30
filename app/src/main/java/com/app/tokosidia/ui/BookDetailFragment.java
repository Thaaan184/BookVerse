// Lokasi: com/app/tokosidia/ui/BookDetailFragment.java
package com.app.tokosidia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.tokosidia.R;
import com.app.tokosidia.model.Book;
// Import ViewBinding
import com.app.tokosidia.databinding.FragmentProductDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BookDetailFragment extends BottomSheetDialogFragment {

    // Gunakan ViewBinding
    private FragmentProductDetailBinding binding;

    private static final String ARG_NAME = "name";
    private static final String ARG_AUTHOR = "author"; // Ditambahkan
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESC = "desc";
    private static final String ARG_RATING = "rating";
    private static final String ARG_IMAGE = "image";

    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, book.getTitle());
        args.putString(ARG_AUTHOR, book.getAuthor()); // Ditambahkan
        args.putDouble(ARG_PRICE, book.getPrice());
        args.putString(ARG_DESC, book.getDescription());
        args.putFloat(ARG_RATING, book.getRating());
        args.putInt(ARG_IMAGE, book.getImageResId());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate menggunakan ViewBinding
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        Bundle args = getArguments();
        if (args != null) {
            // Akses view melalui binding
            binding.txtName.setText(args.getString(ARG_NAME));
            binding.txtAuthor.setText(args.getString(ARG_AUTHOR)); // Ditambahkan
            binding.txtPrice.setText("Rp " + args.getDouble(ARG_PRICE));
            binding.txtDesc.setText(args.getString(ARG_DESC));
            binding.ratingBar.setRating(args.getFloat(ARG_RATING));
            binding.imgProduct.setImageResource(args.getInt(ARG_IMAGE));
        }

        binding.btnBuy.setOnClickListener(v -> dismiss());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Hindari memory leak
    }
}
package com.app.bookverse.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bookverse.R;
import com.app.bookverse.adapter.BookAdapter;
import com.app.bookverse.api.ApiService;
import com.app.bookverse.api.RetrofitClient;
import com.app.bookverse.databinding.FragmentProductListBinding;
import com.app.bookverse.model.Book;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListFragment extends Fragment {
    private static final String TAG = "BookListFragment";
    private FragmentProductListBinding binding;
    private List<Book> bookList = new ArrayList<>();
    private int currentViewType = BookAdapter.TYPE_LIST;

    public BookListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        fetchBooks();
        return binding.getRoot();
    }

    private void fetchBooks() {
        ApiService api = RetrofitClient.getClient(requireContext()).create(ApiService.class);
        api.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = response.body();
                    setRecycler(binding.recyclerView, currentViewType);
                } else {
                    Log.e(TAG, "Response Error: " + response.code() + " - " + response.message());
                    Toast.makeText(getContext(), "Gagal fetch data: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(TAG, "Failure: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Koneksi gagal: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecycler(RecyclerView recyclerView, int viewType) {
        currentViewType = viewType;
        RecyclerView.LayoutManager layoutManager;
        if (viewType == BookAdapter.TYPE_GRID) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BookAdapter(getContext(), bookList, viewType));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_view_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_list) {
            setRecycler(binding.recyclerView, BookAdapter.TYPE_LIST);
            Toast.makeText(getContext(), "Tampilan List", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_grid) {
            setRecycler(binding.recyclerView, BookAdapter.TYPE_GRID);
            Toast.makeText(getContext(), "Tampilan Grid", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_card) {
            setRecycler(binding.recyclerView, BookAdapter.TYPE_CARD);
            Toast.makeText(getContext(), "Tampilan Card", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_filter) {
            showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        String[] options = {"Harga < 100K", "Harga ≥ 100K", "Semua Buku"};
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Filter Buku")
                .setItems(options, (dialog, which) -> {
                    List<Book> filteredList = new ArrayList<>();
                    switch (which) {
                        case 0:
                            for (Book b : bookList) if (b.getPrice() < 100000) filteredList.add(b);
                            break;
                        case 1:
                            for (Book b : bookList) if (b.getPrice() >= 100000) filteredList.add(b);
                            break;
                        default:
                            filteredList = new ArrayList<>(bookList);
                    }
                    binding.recyclerView.setAdapter(new BookAdapter(getContext(), filteredList, currentViewType));
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
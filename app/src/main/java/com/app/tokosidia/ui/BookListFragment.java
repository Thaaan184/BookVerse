package com.app.tokosidia.ui;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tokosidia.R;
import com.app.tokosidia.adapter.BookAdapter;
import com.app.tokosidia.model.Book;
// Import ViewBinding
import com.app.tokosidia.databinding.FragmentProductListBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {

    // Gunakan ViewBinding
    private FragmentProductListBinding binding;
    private List<Book> bookList;
    private int currentViewType = BookAdapter.TYPE_LIST;

    // Hapus referensi Toolbar

    public BookListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Beri tahu Fragment bahwa ia memiliki menu untuk ditambahkan ke Toolbar Activity
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate menggunakan ViewBinding
        binding = FragmentProductListBinding.inflate(inflater, container, false);

        // Hapus semua setup Toolbar dari sini
        // ((androidx.appcompat.app.AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        initBooks();
        // Gunakan binding.recyclerView
        setRecycler(binding.recyclerView, BookAdapter.TYPE_LIST);

        return binding.getRoot();
    }

    private void initBooks() {
        bookList = new ArrayList<>();
        bookList.add(new Book("Laskar Pelangi", "Andrea Hirata", 95000, "Novel inspiratif tentang perjuangan anak-anak Belitung.", 4.8f, R.drawable.laskarpelangi));
        bookList.add(new Book("Bumi Manusia", "Pramoedya A. Toer", 120000, "Kisah Minke di era kolonial Hindia Belanda.", 4.9f, R.drawable.bumimanusia));
        bookList.add(new Book("Atomic Habits", "James Clear", 150000, "Membangun kebiasaan baik dan menghilangkan kebiasaan buruk.", 4.7f, R.drawable.atomichabbits));
        bookList.add(new Book("Cantik Itu Luka", "Eka Kurniawan", 110000, "Sebuah novel dengan balutan sejarah dan magis.", 4.6f, R.drawable.cantikituluka));
        bookList.add(new Book("Filosofi Teras", "Henry Manampiring", 85000, "Pengenalan filsafat Stoa untuk mental yang tangguh.", 4.5f, R.drawable.filosofiteras));

        bookList.add(new Book("Si Juki The Movie: Panitia Hari Akhir", "Faza Meonk", 75000, "Komedi satir dengan pesan nasionalisme, tanggung jawab, dan kerja sama.", 4.6f, R.drawable.juki));

        bookList.add(new Book("Negeri 5 Menara", "Ahmad Fuadi", 95000, "Kisah enam santri dengan mimpi besar dari pesantren kecil di pinggiran tanah air.", 4.7f, R.drawable.menara));
        bookList.add(new Book("Sang Pemimpi", "Andrea Hirata", 98000, "Lanjutan Laskar Pelangi tentang mimpi, persahabatan, dan perjuangan hidup.", 4.8f, R.drawable.pemimpi));
        bookList.add(new Book("Norwegian Wood", "Haruki Murakami", 130000, "Kisah cinta, kehilangan, dan kedewasaan yang penuh refleksi emosional.", 4.6f, R.drawable.norwegian));
        bookList.add(new Book("The Alchemist", "Paulo Coelho", 125000, "Perjalanan spiritual mencari makna hidup dan takdir sejati.", 4.9f, R.drawable.alchemist));
        bookList.add(new Book("Komik One Piece Vol. 1", "Eiichiro Oda", 60000, "Petualangan Luffy mencari kebebasan dan arti sejati dari impian.", 4.8f, R.drawable.onepis));
        bookList.add(new Book("Man’s Search for Meaning", "Viktor E. Frankl", 135000, "Kisah nyata tentang menemukan makna hidup di tengah penderitaan.", 4.9f, R.drawable.man));
        bookList.add(new Book("Komik Naruto Vol. 1", "Masashi Kishimoto", 55000, "Cerita tentang tekad, kerja keras, dan semangat pantang menyerah.", 4.7f, R.drawable.nartoh));
        bookList.add(new Book("Komik Doraemon: Petualangan di Masa Depan", "Fujiko F. Fujio", 50000, "Cerita ringan tapi sarat pesan tentang persahabatan dan tanggung jawab.", 4.6f, R.drawable.doraemon));
        bookList.add(new Book("Rich Dad Poor Dad", "Robert T. Kiyosaki", 140000, "Pelajaran penting tentang pola pikir keuangan dan kebebasan finansial.", 4.8f, R.drawable.rich));
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
        // Inflate menu ke Toolbar milik Activity
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
                            for (Book p : bookList) if (p.getPrice() < 100000) filteredList.add(p);
                            break;
                        case 1:
                            for (Book p : bookList) if (p.getPrice() >= 100000) filteredList.add(p);
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
        // Hindari memory leak dengan set binding ke null
        binding = null;
    }
}
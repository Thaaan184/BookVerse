package com.app.tokosidia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tokosidia.R;
// Import model dan fragment yang baru
import com.app.tokosidia.model.Book;
import com.app.tokosidia.ui.BookDetailFragment;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    // Konstanta ini tetap sama, merujuk ke file Anda
    public static final int TYPE_LIST = 0;
    public static final int TYPE_GRID = 1;
    public static final int TYPE_CARD = 2;

    private Context context;
    private List<Book> bookList; // Diganti dari Product ke Book
    private int viewType;

    public BookAdapter(Context context, List<Book> bookList, int viewType) {
        this.context = context;
        this.bookList = bookList;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        // Logika ini tetap sama, menggunakan layout XML yang Anda sediakan
        if (this.viewType == TYPE_GRID) {
            layout = R.layout.item_product_grid;
        } else if (this.viewType == TYPE_CARD) {
            layout = R.layout.item_product_card;
        } else {
            layout = R.layout.item_product_list;
        }

        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position); // Diganti ke Book

        // Layout Anda (item_product_...) memiliki 'txtName'
        // Kita isi 'txtName' dengan judul buku
        holder.txtName.setText(book.getTitle());
        holder.txtPrice.setText("Rp " + book.getPrice());
        holder.imgProduct.setImageResource(book.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            // Mengarahkan ke BookDetailFragment
            BookDetailFragment detailFragment = BookDetailFragment.newInstance(book);
            detailFragment.show(((androidx.fragment.app.FragmentActivity) context)
                    .getSupportFragmentManager(), "BookDetail");
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size(); // Diganti ke bookList
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imgProduct;

        // ViewHolder tetap sama, me-mapping ke ID di XML Anda
        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
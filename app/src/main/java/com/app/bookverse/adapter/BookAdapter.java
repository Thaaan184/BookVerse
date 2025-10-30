// Lokasi: com/app/tokosidia/adapter/BookAdapter.java
package com.app.bookverse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bookverse.R;
import com.app.bookverse.model.Book;
import com.app.bookverse.ui.BookDetailFragment;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    public static final int TYPE_LIST = 0;
    public static final int TYPE_GRID = 1;
    public static final int TYPE_CARD = 2;

    private Context context;
    private List<Book> bookList;
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
        // Logika ini sudah benar, menggunakan nama layout Anda
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
        Book book = bookList.get(position);

        // Mengisi 'txtName' di XML dengan Judul Buku
        holder.txtName.setText(book.getTitle());
        holder.txtPrice.setText("Rp " + book.getPrice());
        holder.imgProduct.setImageResource(book.getImageResId());

        // (Layout item list/grid/card Anda tidak punya txtAuthor, jadi kita tidak set di sini)

        holder.itemView.setOnClickListener(v -> {
            BookDetailFragment detailFragment = BookDetailFragment.newInstance(book);
            detailFragment.show(((androidx.fragment.app.FragmentActivity) context)
                    .getSupportFragmentManager(), "BookDetail");
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imgProduct;

        // ID ini (txtName, txtPrice, imgProduct) harus ada di 
        // ketiga file layout item Anda (item_product_list, grid, card)
        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
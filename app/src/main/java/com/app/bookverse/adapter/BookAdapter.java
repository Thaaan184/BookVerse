package com.app.bookverse.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.app.bookverse.R;
import com.app.bookverse.model.Book;
import com.app.bookverse.ui.BookDetailFragment;
import com.bumptech.glide.Glide;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    public static final int TYPE_LIST = 0;
    public static final int TYPE_GRID = 1;
    public static final int TYPE_CARD = 2;

    private Context context;
    private List<Book> bookList;
    private int viewType;
    private String storageBaseUrl; // base dari values

    public BookAdapter(Context context, List<Book> bookList, int viewType) {
        this.context = context;
        this.bookList = bookList;
        this.viewType = viewType;

        // Ambil base url dari resources (values/api.xml)
        this.storageBaseUrl = ContextCompat.getString(context, R.string.supabase_storage_base);
        if (!this.storageBaseUrl.endsWith("/")) {
            this.storageBaseUrl = this.storageBaseUrl + "/";
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
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

        holder.txtName.setText(book.getTitle() != null ? book.getTitle() : "-");
        double price = book.getPrice();
        holder.txtPrice.setText("Rp " + String.format("%,.0f", price));

        String cover = book.getCover();
        String imageUrl = null;

        if (!TextUtils.isEmpty(cover)) {
            cover = cover.trim();

            // Jika sudah full URL (http/https), gunakan langsung
            if (cover.startsWith("http://") || cover.startsWith("https://")) {
                imageUrl = cover;
            } else {
                // Jika ada prefix uploads/ atau hanya filename, hapus "uploads/" jika ada
                String fileName = cover;
                if (fileName.startsWith("uploads/")) {
                    fileName = fileName.substring("uploads/".length());
                }
                // Gabungkan dengan base storage
                imageUrl = storageBaseUrl + fileName;
            }
        }

        // Gunakan Glide dengan fallback placeholder
        Glide.with(context)
                .load(imageUrl) // jika imageUrl null, Glide akan menampilkan placeholder
                .placeholder(R.drawable.placeholder) // pastikan drawable ada
                .error(R.drawable.placeholder)
                .into(holder.imgProduct);

        holder.itemView.setOnClickListener(v -> {
            BookDetailFragment detailFragment = BookDetailFragment.newInstance(book.getId());
            detailFragment.show(((androidx.fragment.app.FragmentActivity) context)
                    .getSupportFragmentManager(), "BookDetail");
        });
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;
        ImageView imgProduct;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}

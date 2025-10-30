package com.app.tokosidia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.tokosidia.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProductDetailFragment extends BottomSheetDialogFragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_PRICE = "price";
    private static final String ARG_DESC = "desc";
    private static final String ARG_RATING = "rating";
    private static final String ARG_IMAGE = "image";

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, product.getName());
        args.putDouble(ARG_PRICE, product.getPrice());
        args.putString(ARG_DESC, product.getDescription());
        args.putFloat(ARG_RATING, product.getRating());
        args.putInt(ARG_IMAGE, product.getImageResId());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ImageView imgProduct = view.findViewById(R.id.imgProduct);
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtPrice = view.findViewById(R.id.txtPrice);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        Button btnBuy = view.findViewById(R.id.btnBuy);

        Bundle args = getArguments();
        if (args != null) {
            txtName.setText(args.getString(ARG_NAME));
            txtPrice.setText("Rp " + args.getDouble(ARG_PRICE));
            txtDesc.setText(args.getString(ARG_DESC));
            ratingBar.setRating(args.getFloat(ARG_RATING));
            imgProduct.setImageResource(args.getInt(ARG_IMAGE));
        }

        btnBuy.setOnClickListener(v -> dismiss());

        return view;
    }
}

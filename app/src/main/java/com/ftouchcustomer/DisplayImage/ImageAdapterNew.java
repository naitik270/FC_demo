package com.ftouchcustomer.DisplayImage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.ImageEdit.ImageController;
import com.ftouchcustomer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapterNew extends RecyclerView.Adapter<ImageAdapterNew.ViewHolder> {


    Context context;
    List<String> imagePaths;
    private ImageController imageController;

    String _path = "";
    OnItemClickListenerClsOrder mOnItemClickListener;

    public ImageAdapterNew(Context context, ImageController imageController,
                           List<String> imagePaths) {
        this.context = context;
        this.imageController = imageController;
        this.imagePaths = imagePaths;
        Log.d("--path--", "imagePaths_Adapter: " + imagePaths);
    }


    public void setOnClickImg(OnItemClickListenerClsOrder mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListenerClsOrder {
        void OnClick(String clsImgPath, int position);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_image_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String imagePath = imagePaths.get(position);

        Picasso.get()
                .load(imagePath)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.Bind(imagePath, mOnItemClickListener, position);
    }

    public void changePath(String _path) {
        this._path = _path;
        if (imagePaths.size() > 0) {
            imageController.setImgByURL(_path);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView iv_remove;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
            iv_remove = itemView.findViewById(R.id.iv_remove);

            if (ClsGlobal.imgPreviewMode.equalsIgnoreCase("Preview")) {
                iv_remove.setVisibility(View.GONE);
            } else {
                iv_remove.setVisibility(View.VISIBLE);
            }
        }

        void Bind(final String uri, final OnItemClickListenerClsOrder mOnItemClickListener,
                  final int position) {
            imageView.setOnClickListener(v -> {

                mOnItemClickListener.OnClick(uri, position);
            });
        }

    }

}
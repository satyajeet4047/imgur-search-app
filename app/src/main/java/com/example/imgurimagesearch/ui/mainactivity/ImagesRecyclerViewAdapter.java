package com.example.imgurimagesearch.ui.mainactivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgurimagesearch.data.network.networkmodel.ImageDataResponse;
import com.example.imgurimagesearch.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewAdapter.MovieViewHolder> {


    private final String TAG = ImagesRecyclerViewAdapter.class.getSimpleName();

    private List<ImageDataResponse> imageResponseList;
    private MainActivity context;


    ImagesRecyclerViewAdapter(MainActivity context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
            holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return imageResponseList== null? 0 : imageResponseList.size();
    }



    void setList(List<ImageDataResponse> list){
        this.imageResponseList = list;
        notifyDataSetChanged();
    }



    void clear() {
        if(imageResponseList != null) {
            int size = imageResponseList.size();
            imageResponseList.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    private ImageDataResponse getItem(int position) {
        return imageResponseList.get(position);
    }



    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.imgur_thumbnail) ImageView imageView;
        @BindView(R.id.image_progress) ProgressBar progressBar;

        MovieViewHolder(View v) {
            super(v);
           ButterKnife.bind(this,v);
        }

        void bind(int position) {
            ImageDataResponse item = getItem(position);
            setClickListener(item);
            if ("image/gif".equals(item.getType()) && item.getMp4() != null){
                Log.i(TAG,"Gif type");
            } else {
                loadImage(item.getLink());
            }

        }

        private void setClickListener(ImageDataResponse imageDataResponse) {
            itemView.setTag(imageDataResponse);
            itemView.setOnClickListener(this);
        }

        void loadImage(final String url) {


            Picasso.with(imageView.getContext())
                    .load(url)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            //..image loaded from cache
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(imageView.getContext())
                                    .load(url)
                                    .error(R.drawable.ic_error_red_24dp)
                                    .into(imageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            //... image loaded from online
                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });

        }

        @Override
        public void onClick(View view) {
            if (null != context) {

                    context.onImageClick((ImageDataResponse) view.getTag());
                }
            }
        }


    public interface OnImageClickListener {

         void onImageClick(ImageDataResponse imageDataResponse);
    }

}

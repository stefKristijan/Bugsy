package hr.ferit.kstefancic.bugsy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kristijan on 25.4.2017..
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public static final String LINK = "link";
    List<News> mNews;
    Context mContext;

    public NewsAdapter(List<News> Newss, Context context){
        this.mNews=Newss;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.news_item,parent,false);
        ViewHolder newsViewHolder = new ViewHolder(newsView);
        return newsViewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final News aNews = this.mNews.get(position);
        holder.tvCategory.setText(aNews.getCategory());
        holder.tvPubDate.setText(aNews.getPubDate());
        holder.tvTitle.setText(aNews.getTitle());
        holder.tvDescription.setText(aNews.getDescription());
        Picasso.with(mContext)
                .load(aNews.getNewsImage())
                .fit()
                .centerCrop()
                .into(holder.ivImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK",aNews.getLink());
                Intent intent = new Intent();
                Uri uri = Uri.parse(aNews.getLink());
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvPubDate, tvCategory;
        ImageView ivImage;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
            this.ivImage = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            this.tvDescription= (TextView) itemView.findViewById(R.id.tvDescription);
            this.tvPubDate= (TextView) itemView.findViewById(R.id.tvPubDate);
        }
    }
}
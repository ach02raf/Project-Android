package com.example.blog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.BlogPage;
import com.example.blog.R;
import com.example.blog.model.Blog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestBlogAdapter  extends RecyclerView.Adapter<BestBlogAdapter.RecyclerViewHolder> {


    Context context ;
    static List<Blog> BestBlogList ;
    private Uri uri;

    public BestBlogAdapter(Context context, List<Blog> bestBlogList) {
        this.context = context;
        BestBlogList = bestBlogList;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.best_blog_item,parent , false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.Blog_name.setText(BestBlogList.get(position).getBlog_name());
        holder.Blog_text.setText(BestBlogList.get(position).getBlog_text());
        holder.Date.setText(BestBlogList.get(position).getDate());
        Blog listblog = BestBlogList.get(position );
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i = new Intent(context, BlogPage.class);

                i.putExtra("blog", listblog);
                context.startActivity(i);

            }
        });

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("images").child(BestBlogList.get(position).getBlog_name());
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                //do something with downloadurl
                System.out.println(downloadUrl);

                Picasso.get().load(downloadUrl).into(holder.blogimg);


            }
        });




    }

    @Override
    public int getItemCount() {
        return BestBlogList.size();
    }

    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView blogimg ;
        TextView Blog_name ,Date ,Blog_text ;
        Button btn ;
        Context context ;


        public RecyclerViewHolder(@NonNull View itemView){

            super(itemView);

            blogimg = itemView.findViewById(R.id.blogimg);
            Blog_name = itemView.findViewById(R.id.Blog_name);
            Date = itemView.findViewById(R.id.Date);
            Blog_text = itemView.findViewById(R.id.Blog_text);
            btn = itemView.findViewById(R.id.button);




        }


    }
}

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
import com.example.blog.Home;
import com.example.blog.R;
import com.example.blog.model.Blog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter< RecentsAdapter.RecyclerViewHolder> {
   Context context ;
   static List<Blog> blogList ;

    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView blogimg ;
        TextView  Blog_name ,Date ,Blog_text ;
        Button btn ;
        Context context ;

        public RecyclerViewHolder(@NonNull View itemView){

            super(itemView);

            blogimg = itemView.findViewById(R.id.blogimg);
            Blog_name = itemView.findViewById(R.id.Blog_name);
            Date = itemView.findViewById(R.id.Date);
            Blog_text = itemView.findViewById(R.id.Blog_text);
            btn = itemView.findViewById(R.id.BtnRecent);

            context = itemView.getContext();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    Blog listblog = blogList.get(mPosition  );

                    System.out.println(listblog.getBlog_name());
                    Intent i = new Intent(context, BlogPage.class);
/*

                    Blog blog = new Blog();

                    blog.setBlog_name(listblog.getBlog_name());
                    blog.setBlog_text(listblog.getBlog_text());
                    blog.setDate(listblog.getDate());
                    blog.setImageURL(listblog.getImageURL());
                    blog.setCateg(listblog.getCateg());*/

                    i.putExtra("blog", listblog);
                    context.startActivity(i);

                }
            });



        }


    }


    public RecentsAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_blog_item,parent , false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.Blog_name.setText(blogList.get(position).getBlog_name());
        holder.Blog_text.setText(blogList.get(position).getBlog_text());
        holder.Date.setText(blogList.get(position).getDate());
       // holder.blogimg.setImageResource(blogList.get(position).getImageURL());


        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("images").child(blogList.get(position).getBlog_name());
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
        return blogList.size() ;
    }



}

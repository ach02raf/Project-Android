package com.example.blog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.Home;
import com.example.blog.R;
import com.example.blog.model.Blog;
import com.example.blog.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViweHolder> {


    Context context ;
    List<Category> categoryList ;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // parent.getContext  ==>  peut remplacer context
        View view = LayoutInflater.from(context).inflate(R.layout.catgory_row_item , parent, false);

        return new CategoryViweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViweHolder holder, int position) {
        holder.cat_name.setText(categoryList.get(position).getName());
      //  holder.imgCat.setImageResource(categoryList.get(position).getImageURL());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViweHolder extends RecyclerView.ViewHolder{
        ImageView imgCat ;
        TextView cat_name ;
        public CategoryViweHolder(@NonNull View itemView) {
            super(itemView);
            imgCat = itemView.findViewById(R.id.imgCat);
            cat_name = itemView.findViewById(R.id.cat_name);

        }
    }
}

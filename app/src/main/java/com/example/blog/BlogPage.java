package com.example.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blog.model.Blog;
import com.example.blog.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BlogPage extends AppCompatActivity {

    ImageView imggBlog ;
    TextView blogName ,blogText ,blogAuter ,blogDate ;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = getIntent().getParcelableExtra("user");


        setContentView(R.layout.activity_blog_page);

        Blog blog = getIntent().getParcelableExtra("blog");
        System.out.println(blog.getBlog_name() + " " + blog.getBlog_text());

        imggBlog = findViewById(R.id.imgBlog);
        blogName = findViewById(R.id.blogName);
        blogText = findViewById(R.id.blogText);
        //    blogAuter= findViewById(R.id.blogAuter);
        blogDate = findViewById(R.id.blogDate);
        Button delet = findViewById(R.id.delet);


        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();


                DatabaseReference myRef = database.getReference("Blogs");

                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            Toast toast = Toast.makeText(BlogPage.this, "Please Try again later", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            //  Log.d("firebase test", String.valueOf(task.getResult().getValue().getClass()));

                            HashMap maps = (HashMap) task.getResult().getValue();
                            Iterator hmIterator = maps.entrySet().iterator();


                            boolean test = false;
                            while (hmIterator.hasNext() & !test) {

                                Map.Entry mapElement = (Map.Entry) hmIterator.next();

                                System.out.println(mapElement.getKey());
                                HashMap vl = (HashMap) mapElement.getValue();


                                if (vl.get("blog_name").equals(blog.getBlog_name())) {
                                    DatabaseReference mPostReference = myRef.child(String.valueOf(mapElement.getKey()));
                                    mPostReference.removeValue();
                                    Toast.makeText(BlogPage.this, "This blog is deleted", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            }

                        }

                    }
                });
            }
        });

        //  imggBlog.setImageResource(blog.getImageURL());
        blogName.setText(blog.getBlog_name());
        blogText.setText(blog.getBlog_text());

        blogDate.setText(blog.getDate());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference dateRef = storageRef.child("images").child(blog.getBlog_name());
        dateRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri downloadUrl) {
                //do something with downloadurl
                System.out.println(downloadUrl);

                Picasso.get().load(downloadUrl).into(imggBlog);


            }
        });
    }
    }


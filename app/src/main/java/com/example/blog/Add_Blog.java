package com.example.blog;

import static com.example.blog.R.id.*;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blog.R.id;
import com.example.blog.databinding.ActivityMainBinding;
import com.example.blog.model.Blog;
import com.example.blog.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Add_Blog extends AppCompatActivity {


    Uri imageIURI ;
    StorageReference storageReference ;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);

        User user = getIntent().getParcelableExtra("user");




        Button addbtn = findViewById(R.id.addbtn);
        Button imgbtn = findViewById(R.id.imgbtn);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();

            }
        });


        Button cancsalbtn = findViewById(cancalbtn);
        EditText BlogName = findViewById(R.id.BlogName);
        EditText BlogText = findViewById(R.id.BlogText);

        cancsalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                String Name = BlogName.getText().toString();
                String Text = BlogText.getText().toString();
                if (Name.isEmpty() || Text.isEmpty()) {


                    Toast.makeText(Add_Blog.this, "Plese Entre all the Filde", Toast.LENGTH_SHORT).show();
                } else {



                    FirebaseDatabase database = FirebaseDatabase.getInstance();


                    DatabaseReference myRef = database.getReference("Blogs");

                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                                Toast toast = Toast.makeText( Add_Blog.this , "Please Try again later", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                //  Log.d("firebase test", String.valueOf(task.getResult().getValue().getClass()));

                                HashMap maps = (HashMap) task.getResult().getValue();
                                Iterator hmIterator = maps.entrySet().iterator();


                                boolean test = false;
                                while (hmIterator.hasNext() & !test) {

                                    Map.Entry mapElement = (Map.Entry)hmIterator.next();

                                    System.out.println(mapElement.getValue() );
                                    HashMap vl = (HashMap) mapElement.getValue() ;


                                    if ( vl.get("blog_name").equals(Name)){
                                         Toast.makeText( Add_Blog.this , "this Blog Name is used ! try somthing else", Toast.LENGTH_LONG).show();

                                        test = true;
                                    }
                                }

                                if (!test){


                                    uplodeBlog();





                                }
                            }
                        }


                    });











                }

            }

        });

    }

    private void uplodeBlog() {

         progressDialog = new ProgressDialog(this);
         progressDialog.setTitle(" Uploading image ");
         progressDialog.show();
        EditText BlogText = findViewById(R.id.BlogText);
        EditText BlogName = findViewById(R.id.BlogName);
        String ImgName = BlogName.getText().toString();
        // Create a Cloud Storage reference from the app
        // Create a reference to 'images/'
        storageReference = FirebaseStorage.getInstance().getReference("images/"+ImgName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference("Blogs");

        storageReference.putFile(imageIURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (progressDialog.isShowing())progressDialog.dismiss();

                        String Name = BlogName.getText().toString();
                        String Text = BlogText.getText().toString();

                        Blog blog = new Blog() ;

                        blog.setBlog_text(Text);
                        blog.setBlog_name(Name);

                        blog.setCateg("test");
                        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy.MM.dd");
                        blog.setDate(String.valueOf( ft.format(new Date())));

                        myRef.push().setValue(blog);
                        Toast.makeText( Add_Blog.this, "Blog Added", Toast.LENGTH_LONG).show();

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())progressDialog.dismiss();

                        Toast.makeText(Add_Blog.this, "Failed to uplod image", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void SelectImage() {

        Intent intent = new Intent() ;
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , 100);

    }

    protected  void  onActivityResult(int requestCode , int resultCode , Intent data ) {

        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 100 && data != null && data.getData() != null ){

            imageIURI = data.getData();
             ImageView img = findViewById(R.id.imageuri);
             img.setImageURI(imageIURI);


        }

    }
}
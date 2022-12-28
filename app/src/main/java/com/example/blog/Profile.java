package com.example.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Profile extends AppCompatActivity {

    DrawerLayout drawerlayout ;
    private StorageReference storageReference;
    private Uri imageIURI;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user = getIntent().getParcelableExtra("user");
        TextView userName = findViewById(R.id.username);


        drawerlayout = findViewById(R.id.drawerlayout);



        userName.setText(user.getEmail()) ;


        EditText Email = findViewById(R.id.userEmail);
        Email.setText(user.getPhone()) ;

        EditText Phone = findViewById(R.id.UserPhone);
        Phone.setText(user.getName()) ;

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("user");
        StorageReference storgeprofilePic = FirebaseStorage.getInstance().getReference().child("Profile pic");

        ImageView imgProfile = findViewById(R.id.profile);
        Button savabtn = findViewById(R.id.savabtn);

        savabtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();


                DatabaseReference myRef = database.getReference("user");

                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            Toast toast = Toast.makeText( Profile.this , "Please Try again later", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            //  Log.d("firebase test", String.valueOf(task.getResult().getValue().getClass()));

                            HashMap maps = (HashMap) task.getResult().getValue();
                            Iterator hmIterator = maps.entrySet().iterator();


                            boolean test = false;
                            while (hmIterator.hasNext() & !test) {

                                Map.Entry mapElement = (Map.Entry)hmIterator.next();

                                System.out.println(mapElement.getKey() );
                                HashMap vl = (HashMap) mapElement.getValue() ;


                                if ( vl.get("email").equals(user.getPhone())){

                                    myRef.child(String.valueOf(mapElement.getKey())).child("email").setValue(Email.getText());
                                    myRef.child(String.valueOf(mapElement.getKey())).child("phone").setValue(Phone.getText());

                                    Toast.makeText(Profile.this, "everything is upDated", Toast.LENGTH_SHORT).show();
                               //     finish();

                                }
                            }

                        }

                    }
                });
            }});


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectImage();
            }
        });





    }

    private void uplodeProfileImg() {
    }


    private void updateProfile() {

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


        DatabaseReference myRef = database.getReference("User");

        storageReference.putFile(imageIURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (progressDialog.isShowing())progressDialog.dismiss();

                     /*   String Name = BlogName.getText().toString();
                        String Text = BlogText.getText().toString();

                        Blog blog = new Blog() ;

                        blog.setBlog_text(Text);
                        blog.setBlog_name(Name);

                        blog.setCateg("test");
                        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy.MM.dd");
                        blog.setDate(String.valueOf( ft.format(new Date())));

                       */// myRef.push().setValue(blog);
                     //   Toast.makeText( Add_Blog.this, "Blog Added", Toast.LENGTH_LONG).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())progressDialog.dismiss();

                       // Toast.makeText(Add_Blog.this, "Failed to uplod image", Toast.LENGTH_SHORT).show();
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
             ImageView img = findViewById(R.id.profile);
            img.setImageURI(imageIURI);


        }

    }


    public void  ClickMenu(View view){
        //Open dramer
        openDrawer(drawerlayout);
    }

    private void openDrawer(DrawerLayout drawerlayout) {
        // Open drawer layout
        drawerlayout.openDrawer(GravityCompat.START);
    }

    public void  ClickLogo(View view){
        //Recreate activity
        Home.CloseDrawer(drawerlayout);
    }



    public void  ClickHome(View view){
        //Recreate activity
        Home.redirectActivity(this , Home.class);
    }



    public void  ClickProfile (View view){
        //Redirect activity to dashbord
        Home.redirectActivity(this , Profile.class);

    }


    public void  ClickAdboutUS (View view){
        //Redirect activity to dashbord
        Home.redirectActivity(this , Profile.class);

    }

    public void  LogOut (View view){
        //Redirect activity to dashbord
        Home.redirectActivity(this , Profile.class);

    }


}
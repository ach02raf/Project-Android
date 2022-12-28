package com.example.blog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blog.Adapters.BestBlogAdapter;
import com.example.blog.Adapters.CategoryAdapter;
import com.example.blog.Adapters.RecentsAdapter;
import com.example.blog.model.Blog;
import com.example.blog.model.Category;
import com.example.blog.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {
    static User user;
    ActionBar actionBar;

    RecyclerView recent_blog ;
    RecyclerView Catory ;
    RecyclerView BestBlogs ;
    RecentsAdapter recentsAdapter ;
    CategoryAdapter categoryAdapter ;
    BestBlogAdapter bestBlogAdapter ;
    TextView nameuser ;
    DrawerLayout drawerlayout ;
    //Bitmap bitmap;

    NavigationView navigationView ;
    FloatingActionButton floatbtn ;
    Animation rotateOpen ,rotateClose ;
    boolean isOpen = false ;

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*-------------------- get user from intent  ----------------*/
        user = getIntent().getParcelableExtra("user");

        // System.out.println( user.getPhone()  +"  "+ user.getEmail() +"  "+ user.getName() );

        nameuser = findViewById(R.id.nameuser);
        nameuser.setText(user.getPhone() );


        /*---------------------   add blog   -------------------------*/
        LinearLayout addblog = findViewById(R.id.AddBlog);
        addblog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // starting background task to update product
                Intent fp=new Intent(getApplicationContext(),Add_Blog.class);
                fp.putExtra("user", user);
                startActivity(fp);
            }
        });



        /*-----------------     Animation pour floating Button -------------------------*/
        floatbtn = (FloatingActionButton) findViewById(R.id.floatbtn);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);




        /*-----------------     floating Button              -------------------------*/

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                Toast.makeText(Home.this, "floatiopn button", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });

        /*---------------      Menu       --------------*/




        drawerlayout = findViewById(R.id.drawerlayout);





/*-------------  check the role        -----------*/
        if ( user.getRole().equals("user")   ){

            // remove add blog and add categrory
            LinearLayout addbloglyout = findViewById(R.id.addblogl);
            floatbtn = findViewById(R.id.floatbtn);


            addbloglyout.setVisibility(View.GONE);
            floatbtn.setVisibility(View.GONE);




        }








        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference BlogRef = rootRef.child("Blogs");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Blog> blogList = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap vl = (HashMap) ds.getValue() ;
                    Blog  blog =  new Blog(vl.get("blog_name").toString(),vl.get("blog_text").toString() ,vl.get("date").toString()  , "voyagte"  );
                    StorageReference storageRef =   FirebaseStorage.getInstance().getReference();
                    blogList.add(blog);


                }
                Log.d("TAG", String.valueOf(blogList));
                setBestBlog(blogList);


                setRecentRecycler(blogList);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        BlogRef.addListenerForSingleValueEvent(eventListener);





        List<Category> CategoryList = new ArrayList<>();
        CategoryList.add(new Category("Food"  ) );
        CategoryList.add(new Category("Bussnec") );
        CategoryList.add(new Category("Music" ));
        CategoryList.add(new Category("Music" ));
        CategoryList.add(new Category("Music" ));


        setCagrory(CategoryList);


/*
        List<Blog> BestBlogList = new ArrayList<>();

         System.out.println(BestBlogList.size());
      */
    }

    /*-----------------               menu          -----------------------------------------*/


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
        CloseDrawer(drawerlayout);
    }

    public static void CloseDrawer(DrawerLayout drawerlayout) {
        // Close drawer layout
        //check condition
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerlayout.closeDrawer(GravityCompat.START);
        }
    }


    public void  ClickHome(View view){
        //Recreate activity
        recreate();
    }


    public  void  ClickDashboard(View view){
        //Redirect activity to dashbord
        redirectActivity(this , Home.class);
    }


    public void  ClickProfile (View view){
        //Redirect activity to dashbord
        redirectActivity(this , Profile.class);
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }


    public void  ClickAdboutUS (View view){
        //Redirect activity to dashbord
        redirectActivity(this , AboutUs.class);
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }

    public void  LogOut (View view){
        //Redirect activity to dashbord
        redirectActivity(this , Profile.class);
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }

    public static   void redirectActivity(Activity activity, Class aclass) {
        //Initialize intent
        Intent intent = new Intent(activity ,aclass );
        //Set flag

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("user", user);
        //Start activity
        activity.startActivity(intent);

    }



    /*-----------------     floating Button animation               -------------------------*/


    private void animateFab() {
        if (isOpen){
            floatbtn.startAnimation(rotateOpen);
            isOpen = false ;
        }else {

            floatbtn.startAnimation(rotateClose);
            isOpen = true ;
        }
    }

    /*--------------------  openDialog to creat a blog        --------------------*/

    public void openDialog(){


        DialogAddBlog dialogAddBlog = new DialogAddBlog();
        dialogAddBlog.setCancelable(false);

        dialogAddBlog.show(getSupportFragmentManager(),"Add blog Dialog");



    }

    private void setRecentRecycler(List<Blog> recentsBlogList){
        recent_blog = findViewById(R.id.recent_blog);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.HORIZONTAL ,false);
        recent_blog.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this , recentsBlogList);
        recent_blog.setAdapter(recentsAdapter);
    }

    private void setCagrory(List<Category> categoryList){
        Catory = findViewById(R.id.Catory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.HORIZONTAL ,false);

        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this ,2); ======> pour etre virticle
        Catory.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this  , categoryList);
        Catory.setAdapter(categoryAdapter);
    }


    private void setBestBlog(List<Blog> BestBlogList){
        BestBlogs = findViewById(R.id.BestBlogs);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this , RecyclerView.HORIZONTAL ,false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this ,1);
        BestBlogs.setLayoutManager(layoutManager);
        bestBlogAdapter = new BestBlogAdapter(this  , BestBlogList);
        BestBlogs.setAdapter(bestBlogAdapter);
    }






}
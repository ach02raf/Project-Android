package com.example.blog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blog.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SinginFragment extends Fragment {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-z._-]+\\.+[a-z]+" ;
    EditText email;
    EditText pass;
    Button btn ;
    TextView text ;

    float v = 0 ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_singin ,container ,false);

        email = root.findViewById(R.id.Email_singin);
        pass = root.findViewById(R.id.password_singin);
        btn = root.findViewById(R.id.button_singin);
        text = root.findViewById(R.id.textView);




        email.setTranslationX(800);
        pass.setTranslationX(800);
        btn.setTranslationX(800);
        text.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        btn.setAlpha(v);
        text.setAlpha(v);


        email.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        btn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        text.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailTXT = email.getText().toString();
                final String passTXT = pass.getText().toString();

                if (  emailTXT.isEmpty() ||   passTXT.isEmpty() ){
                    Toast toast = Toast.makeText( SinginFragment.this.getContext() , "Please enter all information", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (!emailTXT.matches(emailPattern)){
                    Toast toast = Toast.makeText( SinginFragment.this.getContext() , "Please check your adress email", Toast.LENGTH_SHORT);
                    toast.show();
                }else {



                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    User user = new User() ;



                    DatabaseReference myRef = database.getReference("user");

                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                                Toast toast = Toast.makeText( SinginFragment.this.getContext() , "Please csdssheck your adress email", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                Log.d("firebase test", String.valueOf(task.getResult().getValue().getClass()));
                                HashMap maps = (HashMap) task.getResult().getValue();



                                Iterator hmIterator = maps.entrySet().iterator();


                                boolean test = false;
                                while (hmIterator.hasNext() & !test) {

                                    Map.Entry mapElement = (Map.Entry)hmIterator.next();

                                    System.out.println(mapElement.getValue() );
                                    HashMap vl = (HashMap) mapElement.getValue() ;


                                    if ( vl.get("email").equals(emailTXT) && vl.get("password").equals(passTXT) ){
                                        Toast toast = Toast.makeText( SinginFragment.this.getContext() , "this Email already exist", Toast.LENGTH_SHORT);
                                        toast.show();
                                        user.setEmail(emailTXT);
                                        user.setPassword(passTXT);
                                        user.setName(vl.get("name").toString());
                                        user.setPhone(vl.get("phone").toString());
                                        user.setRole(vl.get("role").toString());


                                        test = true;
                                    }
                                }

                                if (!test){

                                    Toast toast = Toast.makeText(SinginFragment.this.getContext(), "Email or password incorect ", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else {
                                    Intent intent = new Intent(SinginFragment.this.requireContext() , Home.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);
                                }

                            }
                        }


                    });



                }
            }
        });

   return root ;
    }
}
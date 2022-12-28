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

public class sinupFragment extends Fragment {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-z._-]+\\.+[a-z]+" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sinup ,container ,false);


        EditText name = (EditText)root.findViewById(R.id.name);
        EditText email = (EditText) root.findViewById(R.id.Email);
        EditText phone = (EditText) root.findViewById(R.id.phne);
        EditText pass = (EditText) root.findViewById(R.id.password);
        Button btn = (Button) root.findViewById(R.id.singup);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameTXT = name.getText().toString();
                final String emailTXT = email.getText().toString();
                final String phoneTXT = phone.getText().toString();
                final String passTXT = pass.getText().toString();

                if (nameTXT.isEmpty() || emailTXT.isEmpty() || phoneTXT.isEmpty() || passTXT.isEmpty() ){
                    Toast toast = Toast.makeText( sinupFragment.this.getContext() , "Please enter all information", Toast.LENGTH_SHORT);
                    toast.show();
                }else if (!emailTXT.matches(emailPattern)){
                    Toast toast = Toast.makeText( sinupFragment.this.getContext() , "Please check your adress email", Toast.LENGTH_SHORT);
                    toast.show();
                }else {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    User user = new User() ;

                            user.setPhone(phoneTXT);
                            user.setEmail(emailTXT);
                            user.setPassword(passTXT);
                            user.setName(nameTXT);
                            user.setRole("user");

                    DatabaseReference myRef = database.getReference("user");

                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                                Toast toast = Toast.makeText( sinupFragment.this.getContext() , "Please csdssheck your adress email", Toast.LENGTH_SHORT);
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

                                    System.out.println( "email : " + vl.get("email") );
                                    System.out.println(vl.get("email").equals(emailTXT));

                                    if ( vl.get("email").equals(emailTXT)){
                                        Toast toast = Toast.makeText( sinupFragment.this.getContext() , "this Email already exist", Toast.LENGTH_SHORT);
                                        toast.show();
                                        test = true;
                                    }
                                }

                                if (!test){
                                    myRef.push().setValue(user);
                                    Toast toast = Toast.makeText(sinupFragment.this.getContext(), "user added", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(sinupFragment.this.requireContext() , Home.class);
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
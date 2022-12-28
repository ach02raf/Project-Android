package com.example.blog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.blog.model.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DialogAddBlog extends AppCompatDialogFragment {

    public Object setPositiveButton;
    private EditText editTextBlogtext ;
    private EditText editTextBlogTitre ;
    private Button btnSelectImg ;
    ProgressDialog progressDialog ;

    Uri imageIURI ;
    StorageReference storageReference ;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       // return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) ;
        LayoutInflater inflater = getActivity().getLayoutInflater() ;
        View view = inflater.inflate(R.layout.layout_dialog_add_blog , null);

        builder.setView(view)
                .setCancelable(true)
                .setTitle("Add new Blog")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Post",   new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editTextBlogTitre =  view.findViewById(R.id.editTextBlogTitre);
                    String BlogName = editTextBlogTitre.getText().toString();
                        if (BlogName.isEmpty() ){


                            Toast.makeText(getContext(), "Plese Entre all the Filde", Toast.LENGTH_LONG).show();

                        }else {


                        //    uplodeBlog();

                        }
                    }
                })
                ;











        return builder.create() ;

    }




}

package com.example.emergency;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL= 1;
    private EditText mEditTextNumber;


    private ExpandableListView expandableListView;
    private CustomAdapter customAdapter;
    List<String> listDataHeader;
    HashMap<String,List<String>> listDataChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepare();
        expandableListView =(ExpandableListView)findViewById(R.id.ex1);
        customAdapter = new CustomAdapter(this,listDataHeader,listDataChild);
        expandableListView.setAdapter(customAdapter);
        mEditTextNumber =findViewById(R.id.num);
        ImageView imageCall = findViewById(R.id.image_call);
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhonecall();

            }
        });
    }




    public void makePhonecall(){

        String number = mEditTextNumber.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
              String dial="tel:"+number;
              startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
    }else{
            Toast.makeText(MainActivity.this,"enter phone number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode == REQUEST_CALL){
           if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
               makePhonecall();
           }else{
               Toast.makeText(this,"permission Denied",Toast.LENGTH_SHORT).show();

           }

       }
    }

    public void prepare(){

        String[] headerString= getResources().getStringArray(R.array.header);
        String[] childString= getResources().getStringArray(R.array.child);
        listDataHeader =new ArrayList<>();
        listDataChild =new HashMap<>();
        for(int i=0; i<headerString.length; i++){
            listDataHeader.add(headerString[i]);
            List<String> child =new ArrayList<>();
            child.add(childString[i]);
            listDataChild.put(listDataHeader.get(i),child);
        }
    }
}

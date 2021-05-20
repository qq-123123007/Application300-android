package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class findpage extends AppCompatActivity {

    String searchtext = "";
    String language = "E";
    private JSONObject data = new JSONObject();
    Bitmap setnull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpage);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            searchtext = bundle.getString("searchtext");
        }

        cklan();
        setlan();
        setdata();
        setdata1();
        setdata2();
    }

    private void setdata(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdata");
        setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b1);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key="ok";
                if (snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        if((ds.child("name").getValue().toString()+ds.child("cName").getValue().toString()).contains(searchtext)){
                            String gettype = ds.child("shoptype").getValue().toString();
                            String getid = ds.child("id").getValue().toString();
                            Object getback = ds.child("icon").getValue();
                            String backgrand = String.valueOf(getback);
                            String getname = "";
                            if(language.equals("E")) {
                                getname = ds.child("name").getValue().toString();
                            }else if(language.equals("C")){
                                getname = ds.child("cName").getValue().toString();
                            }
                            View.OnClickListener button_click = new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(findpage.this, shoppageuser.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", getid);
                                    bundle.putString("type", gettype);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            };
                            View linearLayout1 = findViewById(R.id.mainpage);
                            ImageView addbutton1 = new ImageView(getBaseContext());
                            addbutton1.setId(0);
                            addbutton1.setTag(getid);
                            if (backgrand.equals("null") == false) {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(StringToBitMap(backgrand), 500, 500, false));
                            } else {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(setnull, 500, 500, false));
                            }
                            addbutton1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton1.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout1).addView(addbutton1);

                            View linearLayout = findViewById(R.id.mainpage);
                            TextView addbutton = new TextView(getBaseContext());
                            addbutton.setText(getname);
                            addbutton.setId(0);
                            addbutton.setTextSize(25);
                            addbutton.setTag(getid);
                            addbutton.setGravity(Gravity.CENTER);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);

                            linearLayout = findViewById(R.id.mainpage);
                            addbutton = new TextView(getBaseContext());
                            addbutton.setBackgroundColor(Color.parseColor("#000000"));
                            addbutton.setHeight(2);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);
                        }
                    }
                    //datashop.remove(0);
                    //text.setText(datashop.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setdata1(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdatam");
        setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b1);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key="ok";
                if (snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        if((ds.child("name").getValue().toString()+ds.child("cName").getValue().toString()).contains(searchtext)){
                            String gettype = ds.child("shoptype").getValue().toString();
                            String getid = ds.child("id").getValue().toString();
                            Object getback = ds.child("icon").getValue();
                            String backgrand = String.valueOf(getback);
                            String getname = "";
                            if(language.equals("E")) {
                                getname = ds.child("name").getValue().toString();
                            }else if(language.equals("C")){
                                getname = ds.child("cName").getValue().toString();
                            }
                            View.OnClickListener button_click = new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(findpage.this, shoppageuser.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", getid);
                                    bundle.putString("type", gettype);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            };
                            View linearLayout1 = findViewById(R.id.mainpage);
                            ImageView addbutton1 = new ImageView(getBaseContext());
                            addbutton1.setId(0);
                            addbutton1.setTag(getid);
                            if (backgrand.equals("null") == false) {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(StringToBitMap(backgrand), 500, 500, false));
                            } else {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(setnull, 500, 500, false));
                            }
                            addbutton1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton1.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout1).addView(addbutton1);

                            View linearLayout = findViewById(R.id.mainpage);
                            TextView addbutton = new TextView(getBaseContext());
                            addbutton.setText(getname);
                            addbutton.setId(0);
                            addbutton.setTextSize(25);
                            addbutton.setTag(getid);
                            addbutton.setGravity(Gravity.CENTER);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);

                            linearLayout = findViewById(R.id.mainpage);
                            addbutton = new TextView(getBaseContext());
                            addbutton.setBackgroundColor(Color.parseColor("#000000"));
                            addbutton.setHeight(2);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);
                        }
                    }
                    //datashop.remove(0);
                    //text.setText(datashop.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setdata2(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdatap");
        setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b1);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key="ok";
                if (snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        if((ds.child("name").getValue().toString()+ds.child("cName").getValue().toString()).contains(searchtext)){
                            String gettype = ds.child("shoptype").getValue().toString();
                            String getid = ds.child("id").getValue().toString();
                            Object getback = ds.child("icon").getValue();
                            String backgrand = String.valueOf(getback);
                            String getname = "";
                            if(language.equals("E")) {
                                getname = ds.child("name").getValue().toString();
                            }else if(language.equals("C")){
                                getname = ds.child("cName").getValue().toString();
                            }
                            View.OnClickListener button_click = new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(findpage.this, shoppageuser.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", getid);
                                    bundle.putString("type", gettype);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            };
                            View linearLayout1 = findViewById(R.id.mainpage);
                            ImageView addbutton1 = new ImageView(getBaseContext());
                            addbutton1.setId(0);
                            addbutton1.setTag(getid);
                            if (backgrand.equals("null") == false) {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(StringToBitMap(backgrand), 500, 500, false));
                            } else {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(setnull, 500, 500, false));
                            }
                            addbutton1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton1.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout1).addView(addbutton1);

                            View linearLayout = findViewById(R.id.mainpage);
                            TextView addbutton = new TextView(getBaseContext());
                            addbutton.setText(getname);
                            addbutton.setId(0);
                            addbutton.setTextSize(25);
                            addbutton.setTag(getid);
                            addbutton.setGravity(Gravity.CENTER);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);

                            linearLayout = findViewById(R.id.mainpage);
                            addbutton = new TextView(getBaseContext());
                            addbutton.setBackgroundColor(Color.parseColor("#000000"));
                            addbutton.setHeight(2);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);
                        }
                    }
                    //datashop.remove(0);
                    //text.setText(datashop.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){

        }else if(language.equals("C")){

        }
    }

    public void cklan(){
        try {
            InputStream in = openFileInput(NOTES);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String readline = reader.readLine().toString();
                //text.setText(data.toString());
                tmp.close();
                reader.close();
                in.close();

                data = new JSONObject(readline);

                language = data.getString("language");
                //Toast.makeText(getApplicationContext(), language+" data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception rr) {}
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
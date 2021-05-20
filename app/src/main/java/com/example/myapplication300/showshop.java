package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class showshop extends AppCompatActivity {

    TextView text,firstname,title1,title2;
    ImageView first;
    private DatabaseReference mDatabase;

    String language = "E";
    private JSONObject data = new JSONObject();
    boolean firstck = false;
    String gettype = "";
    String getdata="";
    ArrayList<String> datashop = new ArrayList<String>();
    ArrayList<String> datashopchange = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showshop);

        text = (TextView)findViewById(R.id.text);
        first = (ImageView)findViewById(R.id.first);
        firstname = (TextView)findViewById(R.id.firstname);
        title1 = (TextView)findViewById(R.id.title1);
        title2 = (TextView)findViewById(R.id.title2);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            gettype = bundle.getString("type");
        }
        cklan();
        setlan();
        //Toast.makeText(getApplicationContext(), gettype, Toast.LENGTH_SHORT).show();
        if(gettype.equals("restaurant")){
            gettype = "Restaurant";
            go("r");
        }else if(gettype.equals("museum")){
            gettype = "Exhibition Museum";
            go("m");
        }else if(gettype.equals("park")){
            gettype = "Amusement Park";
            go("p");
        }

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("shopdata")
//                //.whereEqualTo("capital", true)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            getdata += task.getResult()+" ";
//                        }
//                    }
//                });
//        text.setText(getdata);

//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("send").child("text").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                text.setText(task.getResult().getValue().toString());
//            }
//        });


//        while (datashop.size()==0){
//            int get = Integer.parseInt(datashop.size()+"");
//            datashopchange.add(datashop.get(get));
//            datashop.remove(get);
//            datashopchange.add(get+"");
//        }
//        text.setText(datashopchange.toString());

        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showshop.this.finish();
            }
        });
    }

    private void go(String type){
        String settype="";
        DatabaseReference reference;
        Bitmap setnull;
        if(type.equals("r")) {
            reference = FirebaseDatabase.getInstance().getReference("shopdata");
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b1);
            settype = "Restaurant";
        }else if(type.equals("m")) {
            reference = FirebaseDatabase.getInstance().getReference("shopdatam");
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b2);
            settype = "Exhibition Museum";
        }else {
            reference = FirebaseDatabase.getInstance().getReference("shopdatap");
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b5);
            settype = "Amusement Park";
        }
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key="ok";
                if (snapshot.exists()){
                    int choose = (int)(Math.random()*((int)snapshot.getChildrenCount()));
                    int num = 0;
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        //key += ds.getKey()+" ";
                        if (choose==num){
                            firstck = true;
                        }
                        num++;
                        String getid = ds.child("id").getValue().toString();
                        Object getback = ds.child("icon").getValue();
                        String backgrand = String.valueOf(getback);
                        String getname = "";
                        if(language.equals("E")) {
                            getname = ds.child("name").getValue().toString();
                        }else if(language.equals("C")){
                            getname = ds.child("cName").getValue().toString();
                        }
                        if(firstck) {
                            firstname.setText(getname);
                            if (backgrand.equals("null") == false) {
                                first.setImageBitmap(StringToBitMap(backgrand));
                            }else {
                                first.setImageBitmap(setnull);
                            }
                            first.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(showshop.this, shoppageuser.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", getid);
                                    bundle.putString("type", gettype);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            firstname.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(showshop.this, shoppage.class);
                                    intent.putExtra("id", getid);
                                    startActivity(intent);
                                }
                            });
                            firstck = false;
                        }else {
                            View.OnClickListener button_click = new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(showshop.this, shoppageuser.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id", getid);
                                    bundle.putString("type", gettype);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            };
                            View linearLayout = findViewById(R.id.othertext);
                            TextView addbutton = new TextView(getBaseContext());
                            addbutton.setText(getname);
                            addbutton.setId(0);
                            addbutton.setTextSize(25);
                            addbutton.setTag(getid);
                            addbutton.setWidth(500);
                            addbutton.setGravity(Gravity.CENTER);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton);

                            View linearLayout1 = findViewById(R.id.otherimg);
                            ImageView addbutton1 = new ImageView(getBaseContext());
                            addbutton1.setId(0);
                            addbutton1.setTag(getid);
                            if (backgrand.equals("null") == false) {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(StringToBitMap(backgrand), 500, 500, false));
                            } else {
                                addbutton1.setImageBitmap(Bitmap.createScaledBitmap(setnull, 500, 500, false));
                            }
                            addbutton1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton1.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout1).addView(addbutton1);
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){
            if(gettype.equals("restaurant")){
                title1.setText("Restaurant");
            }else if(gettype.equals("museum")){
                title1.setText("Exhibition Museum");
            }else if(gettype.equals("park")){
                title1.setText("Amusement Park");
            }
        }else if(language.equals("C")){
            if(gettype.equals("restaurant")){
                title1.setText("餐廳");
            }else if(gettype.equals("museum")){
                title1.setText("展覽博物館");
            }else if(gettype.equals("park")){
                title1.setText("遊樂園");
            }
            title2.setText("其他");
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
}
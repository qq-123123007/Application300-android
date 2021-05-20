package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class MainActivity extends AppCompatActivity {

    Button registered,enter,sa,restaurant,museum,park,home,news,setting,find;
    LinearLayout mainpage,login,getsetting;
    EditText userid,password;
    TextView setlanguage,settingtitle,logintitle;
    ScrollView newview;

    private JSONObject data = new JSONObject();
    String language = "E";
    boolean wroung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newview = (ScrollView)findViewById(R.id.newview);
        find = (Button)findViewById(R.id.find);
        registered = (Button)findViewById(R.id.registered);
        enter = (Button)findViewById(R.id.enter);
        park = (Button)findViewById(R.id.park);
        museum = (Button)findViewById(R.id.museum);
        restaurant = (Button)findViewById(R.id.restaurant);
        sa = (Button)findViewById(R.id.sa);
        mainpage = (LinearLayout)findViewById(R.id.mainpage);
        getsetting = (LinearLayout)findViewById(R.id.getsetting);
        login = (LinearLayout)findViewById(R.id.login);
        news = (Button)findViewById(R.id.news);
        home = (Button)findViewById(R.id.home);
        setting = (Button)findViewById(R.id.setting);
        setlanguage = (TextView)findViewById(R.id.setlanguage);
        settingtitle = (TextView)findViewById(R.id.settingtitle);
        logintitle = (TextView)findViewById(R.id.logintitle);
        userid = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);

        cklan();
        setlan();

//        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
//        Toast.makeText(getApplicationContext(), timeStamp, Toast.LENGTH_SHORT).show();

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.change,null);
                customizeDialog.setView(dialogView);
                if(language.equals("E")) {
                    customizeDialog.setTitle("Find");
                }else if(language.equals("C")){
                    customizeDialog.setTitle("搜尋");
                }
                final AlertDialog dialog = customizeDialog.create();
                final EditText text = (EditText)dialogView.findViewById(R.id.text);
                final TextView back = (TextView)dialogView.findViewById(R.id.back);
                final TextView add = (TextView)dialogView.findViewById(R.id.add);
                if(language.equals("C")){
                    back.setText("返回");
                    add.setText("搜尋");
                }else if(language.equals("E")){
                    add.setText("Find");
                }

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(text.getText().toString().equals("")==false) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, findpage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("searchtext", text.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });

                dialog.show();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, letter.class);
                startActivity(intent);
            }
        });
        setlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.languagepage,null);
                customizeDialog.setView(dialogView);
                final AlertDialog dialog = customizeDialog.create();

                final TextView goe = (TextView)dialogView.findViewById(R.id.goe);
                final TextView goc = (TextView)dialogView.findViewById(R.id.goc);

                if(language.equals("E")){
                    goe.setTextColor(Color.parseColor("#00ff00"));
                }else  if(language.equals("C")){
                    goc.setTextColor(Color.parseColor("#00ff00"));
                }

                goc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "C";
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
                            }
                            data.put("language","C");
                            //Toast.makeText(getApplicationContext(), "new update", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}
                        try{
                            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(NOTES,0));
                            out.write(data.toString());
                            out.close();
                        }catch (IOException e){}
                        setlan();
                        dialog.dismiss();
                    }
                });
                goe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        language = "E";
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
                            }
                            data.put("language","E");
                            //Toast.makeText(getApplicationContext(), "new update", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}
                        try{
                            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(NOTES,0));
                            out.write(data.toString());
                            out.close();
                        }catch (IOException e){}
                        setlan();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, showshop.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "restaurant");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, showshop.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "park");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, showshop.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "museum");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid.getText().toString().equals("")||password.getText().toString().equals("")){
                    if (language.equals("E")) {
                        Toast.makeText(getApplicationContext(), "Input Not Full", Toast.LENGTH_SHORT).show();
                    }else if(language.equals("C")){
                        Toast.makeText(getApplicationContext(), "輸入未完", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    cklogin();
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newview.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                getsetting.setVisibility(View.GONE);
            }
        });
        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newview.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getsetting.setVisibility(View.VISIBLE);
                newview.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
            }
        });
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),registered.class);
                startActivity(intent);
            }
        });
    }

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){
            setlanguage.setText("Language");
            sa.setText("Shop Account");
            park.setText("Amusement Park");
            museum.setText("Exhibition Museum");
            restaurant.setText("Restaurant");
            settingtitle.setText("Setting");
            logintitle.setText("Login");
            registered.setText("Registered");
            enter.setText("Enter");
            userid.setHint("ID");
            password.setHint("Password");
            find.setText("Find Shop");
        }else if(language.equals("C")){
            setlanguage.setText("語言");
            sa.setText("店鋪帳號");
            park.setText("遊樂園");
            museum.setText("展覽博物館");
            restaurant.setText("餐廳");
            settingtitle.setText("設定");
            logintitle.setText("登入");
            registered.setText("註冊");
            enter.setText("確定");
            userid.setHint("序號");
            password.setHint("密碼");
            find.setText("查詢店舖");
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
        } catch (Exception rr) {
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
                }
                data.put("language","E");
                data.put("love","");
                //Toast.makeText(getApplicationContext(), "new update", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                this.finish();
            }
            try{
                OutputStreamWriter out = new OutputStreamWriter(openFileOutput(NOTES,0));
                out.write(data.toString());
                out.close();
            }catch (IOException e){}
        }
    }

    private void cklogin(){
        wroung = false;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdata");
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("shopdatam");
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("shopdatap");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Object rs = dataSnapshot.getValue();
                    String getpassword = "";
                    Object rs = dataSnapshot.child(userid.getText().toString()).child("password").getValue();
                    getpassword = String.valueOf(rs);
                    String gettype = "";
                    Object rs1 = dataSnapshot.child(userid.getText().toString()).child("shoptype").getValue();
                    gettype = String.valueOf(rs1);
                    if (getpassword.equals(password.getText().toString())) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, shoppage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", userid.getText().toString());
                        bundle.putString("type", gettype);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }else {
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    //Object rs = dataSnapshot.getValue();
                                    String getpassword = "";
                                    Object rs = dataSnapshot.child(userid.getText().toString()).child("password").getValue();
                                    getpassword = String.valueOf(rs);
                                    String gettype = "";
                                    Object rs1 = dataSnapshot.child(userid.getText().toString()).child("shoptype").getValue();
                                    gettype = String.valueOf(rs1);
                                    if(getpassword.equals(password.getText().toString())){
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this, shoppage.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", userid.getText().toString());
                                        bundle.putString("type", gettype);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        MainActivity.this.finish();
                                    }else {
                                        reference2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    //Object rs = dataSnapshot.getValue();
                                                    String getpassword = "";
                                                    Object rs = dataSnapshot.child(userid.getText().toString()).child("password").getValue();
                                                    getpassword = String.valueOf(rs);
                                                    String gettype = "";
                                                    Object rs1 = dataSnapshot.child(userid.getText().toString()).child("shoptype").getValue();
                                                    gettype = String.valueOf(rs1);
                                                    if(getpassword.equals(password.getText().toString())){
                                                        Intent intent = new Intent();
                                                        intent.setClass(MainActivity.this, shoppage.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("id", userid.getText().toString());
                                                        bundle.putString("type", gettype);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        MainActivity.this.finish();
                                                    }else {
                                                        if (language.equals("E")) {
                                                            Toast.makeText(getApplicationContext(), "ID or Password Not Correct", Toast.LENGTH_SHORT).show();
                                                        }else if(language.equals("C")){
                                                            Toast.makeText(getApplicationContext(), "序號或密碼不正確", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
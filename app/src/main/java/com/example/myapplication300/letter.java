package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.ArrayList;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class letter extends AppCompatActivity {

    TextView title;

    String language = "E";
    private JSONObject data = new JSONObject();
    ArrayList<String> lovearray = new ArrayList<String>();
    String getlove = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        title = (TextView)findViewById(R.id.title);

        cklan();
        setlan();
        genlove();

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letter.this.finish();
            }
        });
    }

    private void genlove(){
        if(getlove.equals("")==false){
            String getlovearry[] = getlove.split(" ");
//            String a="ok ";
//            a += getlovearry[0];
            for (int i=0;i<getlovearry.length;i++){

            }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("send");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String key="ok";
                    if (snapshot.exists()){
                        for(DataSnapshot ds : snapshot.getChildren()){//DataSnapshot ds : snapshot.getChildren())
                            String getid = ds.child("id").getValue().toString();
                            String getmessage = ds.child("message").getValue().toString();
                            String getname="";
                            String getcname="";
                            if(language.equals("E")){
                                getname = ds.child("name").getValue().toString();
                            }else if(language.equals("C")){
                                getname = ds.child("cname").getValue().toString();
                            }

                            View.OnClickListener button_click = new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    final AlertDialog.Builder normalDialog =
                                            new AlertDialog.Builder(letter.this);
                                    normalDialog.setTitle(v.getTag().toString());
                                    normalDialog.setMessage(getmessage);
                                    String settext1 = "關閉";
                                    if (language.equals("E")){
                                        settext1 = "Close";
                                    }
                                    normalDialog.setNegativeButton(settext1,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //...To-do
                                                }
                                            });
                                    // 显示
                                    normalDialog.show();
                                }
                            };
                            View linearLayout = findViewById(R.id.output);

                            TextView addbutton1 = new TextView(getBaseContext());
                            addbutton1.setHeight(2);
                            addbutton1.setBackgroundColor(Color.parseColor("#000000"));
                            addbutton1.setTag(getid);
                            addbutton1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) linearLayout).addView(addbutton1,0);

                            TextView addbutton = new TextView(getBaseContext());
                            addbutton.setText(ds.getKey().substring(0,8)+"     "+getname);
                            addbutton.setId(0);
                            addbutton.setTextSize(25);
                            addbutton.setTag(getname);
                            addbutton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            addbutton.setOnClickListener(button_click);
                            ((LinearLayout) linearLayout).addView(addbutton,0);
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
    }

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){

        }else if(language.equals("C")){
            title.setText("信件");
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

                getlove = data.getString("love");
                language = data.getString("language");
                //Toast.makeText(getApplicationContext(), language+" data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception rr) {}
    }
}
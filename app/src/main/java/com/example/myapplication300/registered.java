package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.auth.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class registered extends AppCompatActivity {

    String language = "E";
    String shoptype = "Restaurant";
    private JSONObject data = new JSONObject();

    TextView registeredtitle,stext;
    RadioButton a,b,c;
    LinearLayout repage,spage;
    EditText address,password,repassword,name,caddress,cname;
    Button renter;
    ArrayList<String> text = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        address = (EditText)findViewById(R.id.address);
        password = (EditText)findViewById(R.id.password);
        renter = (Button) findViewById(R.id.renter);
        repassword = (EditText)findViewById(R.id.repassword);
        name = (EditText)findViewById(R.id.name);
        cname = (EditText)findViewById(R.id.cname);
        caddress = (EditText)findViewById(R.id.caddress);
        registeredtitle = (TextView)findViewById(R.id.registeredtitle);
        spage = (LinearLayout)findViewById(R.id.spage);
        repage = (LinearLayout)findViewById(R.id.repage);
        stext = (TextView)findViewById(R.id.stext);
        a = (RadioButton)findViewById(R.id.a);
        b = (RadioButton)findViewById(R.id.b);
        c = (RadioButton)findViewById(R.id.c);

        cklan();
        setlan();
        //final DatabaseReference myRef2=myRef.child("data01");

        //firebace_select();
        //firebace_add(myRef);

        registeredtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered.this.finish();
            }
        });
        renter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(caddress.getText().toString().equals("")||cname.getText().toString().equals("")||address.getText().toString().equals("")||password.getText().toString().equals("")||name.getText().toString().equals("")||repassword.getText().toString().equals("")){
                    if (language.equals("E")) {
                        Toast.makeText(getApplicationContext(), "Input Empty", Toast.LENGTH_SHORT).show();
                    }else if(language.equals("C")){
                        Toast.makeText(getApplicationContext(), "輸入未完", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (repassword.getText().toString().equals(password.getText().toString())){
                        firebace_add(name.getText().toString(),cname.getText().toString(),address.getText().toString(),caddress.getText().toString(),password.getText().toString());
                    }else {
                        if (language.equals("E")) {
                            Toast.makeText(getApplicationContext(), "Password Not Match!", Toast.LENGTH_SHORT).show();
                        }else if(language.equals("C")){
                            Toast.makeText(getApplicationContext(), "密碼不匹配！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void ronclick(View view){
        shoptype = ""+view.getTag();
    }

    private void firebace_select(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdata");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key="ok";
                if (dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        key += ds.getKey()+" ";
                    }
                    name.setText(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ///////////////////////
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shopdata");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String key="ok";
//                if (snapshot.exists()){
//                    for(DataSnapshot ds : snapshot.getChildren()) {
//                        //key += ds.getKey()+" ";
//                        String get = ds.child("id").getValue().toString();
//                        datashop.add(get);
//                    }
//                    //datashop.remove(0);
//                    text.setText(datashop.toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void firebace_add(String name,String cname,String address,String caddress,String password) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("shopdata");
        if(shoptype.equals("Exhibition Museum")){
            myRef = FirebaseDatabase.getInstance().getReference("shopdatam");
        }
        if(shoptype.equals("Amusement Park")){
            myRef = FirebaseDatabase.getInstance().getReference("shopdatap");
        }
//        myRef.child("ABCD").setValue("12334");
        String id = myRef.push().getKey();

        ContactsInfo contact1 = new ContactsInfo(id, name, cname, shoptype, address, caddress, password);
        //ContactsInfo contact2 = new ContactsInfo(id,"Ray","male",20);
        //ContactsInfo contact3 = new ContactsInfo("May","female",2);

        //將contact1放人子目錄 /ex1/1
        myRef.child(id).setValue(contact1);
        //將contact1放人子目錄 /ex1/2
        //myRef.child("2").setValue(contact2);
        //將contact1放人子目錄 /ex1/3
        //myRef.child("3").child("age").setValue(50);
        repage.setVisibility(View.GONE);
        if (language.equals("E")) {
            stext.setText("Please write down your information\n\n" + "ID: " + id + "\n" + "English Name: " + name + "\n" + "Chinese name: " + cname + "\n" + "Types: " + shoptype + "\n" + "English Address: " + address + "\n" + "Chinese address: " + caddress + "\n" + "Password: " + password);
        }else if(language.equals("C")){
            if(shoptype.equals("Restaurant")){
                shoptype = "餐廳";
            }else if(shoptype.equals("Exhibition Museum")){
                shoptype = "展览博物馆";
            }else if(shoptype.equals("Amusement Park")){
                shoptype = "遊樂園";
            }
            stext.setText("請記下您的信息\n\n"+"序號: "+id+"\n"+"英文名字: "+name+"\n"+"中文名字: "+cname+"\n"+"類型: "+shoptype+"\n"+"英文地址: "+address+"\n"+"中文地址: "+caddress+"\n"+"密碼: "+password);
        }
    }

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){
            registeredtitle.setText("Registered");
            a.setText("Restaurant");
            b.setText("Exhibition Museum");
            c.setText("Amusement Park");
            password.setHint("Password");
            repassword.setHint("Re-Password");
            renter.setText("Enter");
        }else if(language.equals("C")){
            name.setHint("英文名字");
            cname.setHint("中文名字");
            address.setHint("英文地址");
            caddress.setHint("中文地址");
            registeredtitle.setText("注冊");
            a.setText("餐廳");
            b.setText("展览博物馆");
            c.setText("遊樂園");
            password.setHint("密碼");
            repassword.setHint("重復密碼");
            renter.setText("確定");
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
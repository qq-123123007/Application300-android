package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class shoppageuser extends AppCompatActivity {

    ImageView showimg;
    Button attention;
    TextView title1,showid,showname,title3,showaddress,title4,introduction,text;

    String language = "E";
    private JSONObject data = new JSONObject();
    String id = "";
    String type = "";
    String getlove = "";
    Bitmap setnull;
    boolean islove = false;
    ArrayList<String> lovearray = new ArrayList<String>();

    String name = "";
    String address = "";
    String backgrand = "";
    String Eintroduction = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppageuser);

        title1 = (TextView)findViewById(R.id.title1);
        title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);
        showid = (TextView)findViewById(R.id.showid);
        showname = (TextView)findViewById(R.id.showname);
        showaddress = (TextView)findViewById(R.id.showaddress);
        introduction = (TextView)findViewById(R.id.introduction);
        text = (TextView)findViewById(R.id.text);
        showimg = (ImageView)findViewById(R.id.showimg);
        attention = (Button)findViewById(R.id.attention);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            type = bundle.getString("type");
            showid.setText(id);
        }

        cklan();
        setlan();
        setdata();
        setlove();

        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islove){
                    islove = false;
                    attention.setBackgroundColor(Color.parseColor("#00ff00"));
                    if(language.equals("E")) {
                        attention.setText("Following");
                    }else if(language.equals("C")){
                        attention.setText("關注");
                    }
                    loveadelete();
                }else {
                    islove = true;
                    attention.setBackgroundColor(Color.parseColor("#00ffff"));
                    if(language.equals("E")) {
                        attention.setText("Followed");
                    }else if(language.equals("C")){
                        attention.setText("已關注");
                    }
                    loveadd();
                }
            }
        });
        showname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppageuser.this.finish();
            }
        });

        showaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gomap(address);
            }
        });
    }

    public void gomap(String getaddress){
        String adderess = getaddress;
// get address in string for used location for the map

        /* get latitude and longitude from the adderress */

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(adderess, 5);
            if (addresses.size() > 0)
            {
                Double lat = (double) (addresses.get(0).getLatitude());
                Double lon = (double) (addresses.get(0).getLongitude());

                //Log.d("lat-long", "" + lat + "......." + lon);
                final LatLng user = new LatLng(lat, lon);
                /*used marker for show the location */
                //Toast.makeText(getApplicationContext(), lat+" "+lon, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(shoppageuser.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", lat);
                bundle.putDouble("lon", lon);
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setlove(){
        if(getlove.equals("")==false){
            String getlovearry[] = getlove.split(" ");
//            String a="ok ";
//            a += getlovearry[0];
            for (int i=0;i<getlovearry.length;i++){
                lovearray.add(getlovearry[i]);
                if (getlovearry[i].equals(id)){
                    islove = true;
                    attention.setBackgroundColor(Color.parseColor("#00ffff"));
                    if(language.equals("E")) {
                        attention.setText("Followed");
                    }else if(language.equals("C")){
                        attention.setText("已關注");
                    }
                }
            }
            //text.setText(a);
        }
    }

    private void loveadd(){
        getlove = getlove+" "+id;
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
            data.put("love",getlove);
            Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
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

    private void loveadelete(){
        getlove="";
        for(int i=0;i<lovearray.size();i++){
            if (lovearray.get(i).equals(id)==false){
                getlove += lovearray.get(i)+" ";
            }
        }
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
            data.put("love",getlove);
            Toast.makeText(getApplicationContext(), "Unsubscribe", Toast.LENGTH_SHORT).show();
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

    private void setdata(){
        DatabaseReference reference;
        if(type.equals("Restaurant")){
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b1);
            reference = FirebaseDatabase.getInstance().getReference("shopdata");
        }else if(type.equals("Exhibition Museum")){
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b2);
            reference = FirebaseDatabase.getInstance().getReference("shopdatam");
        }else {
            setnull = BitmapFactory.decodeResource(getResources(),R.drawable.b5);
            reference = FirebaseDatabase.getInstance().getReference("shopdatap");
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //Object rs = dataSnapshot.getValue();
                    Object rs = "";
                    if(language.equals("E")) {
                        rs = dataSnapshot.child(id).child("name").getValue();
                        name = String.valueOf(rs);
                        showname.setText(name);
                        rs = dataSnapshot.child(id).child("address").getValue();
                        address = String.valueOf(rs);
                        showaddress.setText(address);
                    }else if(language.equals("C")){
                        rs = dataSnapshot.child(id).child("cName").getValue();
                        name = String.valueOf(rs);
                        showname.setText(name);
                        rs = dataSnapshot.child(id).child("caddress").getValue();
                        address = String.valueOf(rs);
                        showaddress.setText(address);
                    }
                    rs = dataSnapshot.child(id).child("backgrand").getValue();
                    backgrand = String.valueOf(rs);
                    //Toast.makeText(getApplicationContext(), backgrand, Toast.LENGTH_SHORT).show();
                    if (backgrand.equals("null") == false) {
                        showimg.setImageBitmap(StringToBitMap(backgrand));
                    }else {
                        showimg.setImageBitmap(setnull);
                    }
                    rs = dataSnapshot.child(id).child("Eintroduction").getValue();
                    Eintroduction = String.valueOf(rs);
                    introduction.setText(Eintroduction);
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
            title1.setText("序號");
            title3.setText("地址");
            title4.setText("簡介");
            attention.setText("關注");
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
                getlove = data.getString("love");
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
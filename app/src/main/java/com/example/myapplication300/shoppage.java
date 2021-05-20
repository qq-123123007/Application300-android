package com.example.myapplication300;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.provider.ContactsContract.Intents.Insert.NOTES;

public class shoppage extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String id = "";
    String type = "";
    String language = "E";
    private JSONObject data = new JSONObject();
    ImageView showimg,showicon;
    TextView showid,showname,showcname,showaddress,showcaddress,showshoptype;
    TextView title0,title1,title2,title3,title4,title5,title6,title7,title8;
    Button showbutton,showbuttonicon,mail;
    TextView introduction;

    Bitmap imgb=null,imgi=null;
    String name = "";
    String cname = "";
    String address = "";
    String caddress = "";
    String shoptype = "";
    String backgrand = "";
    String icon = "";
    String Eintroduction = "";
    String Cintroduction = "";
    String setimgwhere = "icon";
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppage);

        showimg = (ImageView)findViewById(R.id.showimg);
        showicon = (ImageView)findViewById(R.id.showicon);
        showname = (TextView)findViewById(R.id.showname);
        showcname = (TextView)findViewById(R.id.showcname);
        showaddress = (TextView)findViewById(R.id.showaddress);
        showcaddress = (TextView)findViewById(R.id.showcaddress);
        showshoptype = (TextView)findViewById(R.id.showshoptype);
        showid = (TextView)findViewById(R.id.showid);
        showbutton = (Button)findViewById(R.id.showbutton);
        showbuttonicon = (Button)findViewById(R.id.showbuttonicon);
        title0 = (TextView)findViewById(R.id.title0);
        title1 = (TextView)findViewById(R.id.title1);
        title2 = (TextView)findViewById(R.id.title2);
        title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);
        title5 = (TextView)findViewById(R.id.title5);
        title6 = (TextView)findViewById(R.id.title6);
        title7 = (TextView)findViewById(R.id.title7);
        title8 = (TextView)findViewById(R.id.title8);
        introduction = (TextView)findViewById(R.id.introduction);
        mail = (Button)findViewById(R.id.mail);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            type = bundle.getString("type");
        }
        //Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

        setdata();
        cklan();
        setlan();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(shoppage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            golocation();
        }else {
            ActivityCompat.requestPermissions(shoppage.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(shoppage.this);
                final View dialogView = LayoutInflater.from(shoppage.this).inflate(R.layout.change,null);
                customizeDialog.setView(dialogView);
                if(language.equals("E")) {
                    customizeDialog.setTitle("Introduction");
                }else if(language.equals("C")){
                    customizeDialog.setTitle("簡介");
                }
                final AlertDialog dialog = customizeDialog.create();
                final EditText text = (EditText)dialogView.findViewById(R.id.text);
                final TextView back = (TextView)dialogView.findViewById(R.id.back);
                final TextView add = (TextView)dialogView.findViewById(R.id.add);
                if(language.equals("C")){
                    back.setText("返回");
                    add.setText("傳送");
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
                            sendIntroduction(text.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder customizeDialog = new AlertDialog.Builder(shoppage.this);
                final View dialogView = LayoutInflater.from(shoppage.this).inflate(R.layout.change,null);
                customizeDialog.setView(dialogView);
                if(language.equals("E")) {
                    customizeDialog.setTitle("Message");
                }else if(language.equals("C")){
                    customizeDialog.setTitle("信息");
                }
                final AlertDialog dialog = customizeDialog.create();
                final EditText text = (EditText)dialogView.findViewById(R.id.text);
                final TextView back = (TextView)dialogView.findViewById(R.id.back);
                final TextView add = (TextView)dialogView.findViewById(R.id.add);
                if(language.equals("C")){
                    back.setText("返回");
                    add.setText("傳送");
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
                            sendmessage(text.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });
        showaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gomap(address);
            }
        });
        showcaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gomap(caddress);
            }
        });
        showbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                setimgwhere = "backgrand";
            }
        });
        showimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                setimgwhere = "backgrand";
            }
        });
        showbuttonicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                setimgwhere = "icon";
            }
        });
        showicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                setimgwhere = "icon";
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void golocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null){
                    try {
                        Geocoder geocoder = new Geocoder(shoppage.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        //showid.setText(addresses.get(0).getAddressLine(0));
                    }catch (IOException e){}
                }
            }
        });
    }

    public void updataEinfromation(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("shopdata");
        myRef.child(id).child("Eintroduction").setValue(introduction.getText().toString());
    }

    public void sendmessage(String message){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("send");
        String sendid = myRef.push().getKey();
        myRef.child(timeStamp+sendid).child("message").setValue(message);
        myRef.child(timeStamp+sendid).child("id").setValue(id);
        myRef.child(timeStamp+sendid).child("name").setValue(name);
        myRef.child(timeStamp+sendid).child("cname").setValue(cname);

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String key="ok";
//                if (dataSnapshot.exists()){
//                    String sendid = (dataSnapshot.getChildrenCount()+1)+"";
//                    myRef.child(sendid).child("message").setValue(message);
//                    myRef.child(sendid).child("id").setValue(id);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void sendIntroduction(String message){
        DatabaseReference reference;
        if(type.equals("Restaurant")){
            reference = FirebaseDatabase.getInstance().getReference("shopdata");
        }else if(type.equals("Exhibition Museum")){
            reference = FirebaseDatabase.getInstance().getReference("shopdatam");
        }else {
            reference = FirebaseDatabase.getInstance().getReference("shopdatap");
        }
        reference.child(id).child("Eintroduction").setValue(message);
        shoppage.this.finish();
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
                intent.setClass(shoppage.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", lat);
                bundle.putDouble("lon", lon);
                if(language.equals("C")){
                    bundle.putString("name", cname);
                }else if(language.equals("E")){
                    bundle.putString("name", name);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setdata(){
        DatabaseReference reference;
        if(type.equals("Restaurant")){
            reference = FirebaseDatabase.getInstance().getReference("shopdata");
        }else if(type.equals("Exhibition Museum")){
            reference = FirebaseDatabase.getInstance().getReference("shopdatam");
        }else {
            reference = FirebaseDatabase.getInstance().getReference("shopdatap");
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //Object rs = dataSnapshot.getValue();
                    showid.setText(id);
                    Object rs = dataSnapshot.child(id).child("name").getValue();
                    name = String.valueOf(rs);
                    showname.setText(name);
                    rs = dataSnapshot.child(id).child("cName").getValue();
                    cname = String.valueOf(rs);
                    showcname.setText(cname);
                    rs = dataSnapshot.child(id).child("address").getValue();
                    address = String.valueOf(rs);
                    showaddress.setText(address);
                    rs = dataSnapshot.child(id).child("caddress").getValue();
                    caddress = String.valueOf(rs);
                    showcaddress.setText(caddress);
                    rs = dataSnapshot.child(id).child("shoptype").getValue();
                    shoptype = String.valueOf(rs);
                    showshoptype.setText(shoptype);
                    rs = dataSnapshot.child(id).child("backgrand").getValue();
                    backgrand = String.valueOf(rs);
                    if(backgrand.equals("null")==false){
                        showbutton.setVisibility(View.GONE);
                        showimg.setImageBitmap(StringToBitMap(backgrand));
                    }
                    rs = dataSnapshot.child(id).child("icon").getValue();
                    icon = String.valueOf(rs);
                    if(icon.equals("null")==false){
                        showbuttonicon.setVisibility(View.GONE);
                        showicon.setImageBitmap(StringToBitMap(icon));
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

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            if(setimgwhere.equals("backgrand")) {
                showimg.setImageURI(imageUri);
                ViewTreeObserver vto = showimg.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        try {
                            showbutton.setVisibility(View.GONE);
                            imgb = createViewBitmap(showimg);
                            String getimg = BitMapToString(imgb);
                            //firebace_addback(getimg);
                            showimg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            firebace_addback(getimg);
                            shoppage.this.finish();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else if(setimgwhere.equals("icon")){
                showicon.setImageURI(imageUri);
                ViewTreeObserver vto = showicon.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        try {
                            showbutton.setVisibility(View.GONE);
                            imgi = createViewBitmap(showicon);
                            String getimg = BitMapToString(imgi);
                            //firebace_addback(getimg);
                            showicon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            firebace_addback(getimg);
                            shoppage.this.finish();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private void firebace_addback(String back) {
        DatabaseReference myRef;
        if(type.equals("Restaurant")){
            myRef = FirebaseDatabase.getInstance().getReference("shopdata");
        }else if(type.equals("Exhibition Museum")){
            myRef = FirebaseDatabase.getInstance().getReference("shopdatam");
        }else {
            myRef = FirebaseDatabase.getInstance().getReference("shopdatap");
        }
        if(setimgwhere.equals("backgrand")) {
            myRef.child(id).child("backgrand").setValue(back);
        }else if(setimgwhere.equals("icon")) {
            myRef.child(id).child("icon").setValue(back);
        }
    }

    public void setlan(){
        //Toast.makeText(getApplicationContext(), language, Toast.LENGTH_SHORT).show();
        if(language.equals("E")){

        }else if(language.equals("C")){
            title0.setText("圖標圖像");
            title1.setText("背景圖片");
            title2.setText("英文名字");
            title3.setText("序號");
            title4.setText("中文名字");
            title5.setText("英文地址");
            title6.setText("中文地址");
            title7.setText("店舖類型");
            title8.setText("簡介(點擊作更改)");
            mail.setText("傳送信息");
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap1 = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        v.draw(canvas);
        return bitmap1;
    }
}
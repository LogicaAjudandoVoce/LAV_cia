package com.example.telas_v1.activitys.postagemcliente;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.telas_v1.R;
import com.example.telas_v1.models.Postagem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MapNewPostActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng loc;
    private Postagem postagem = new Postagem();
    private EditText txtLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_new_post);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtLoc = findViewById(R.id.edtLoc);

        Places.initialize(MapNewPostActivity.this, "AIzaSyBjAlJo8Wlr1w007qWeRw8kXNCBEoJ0pJg");
        txtLoc.setFocusable(false);
        txtLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                , fieldList).build(MapNewPostActivity.this);

                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        validarAcesso();
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                postagem.setLatitude(latLng.latitude);
                postagem.setLongitude(latLng.longitude);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && resultCode==RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            txtLoc.setText(place.getAddress());
            mMap.clear();
            loc = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            mMap.addMarker(new MarkerOptions().position(loc));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
        }
    }

    public void addLoc(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapNewPostActivity.this);
        builder.setTitle("Você tem certeza?").setMessage("Adicinar essa localização?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MapNewPostActivity.this, NovaPostagemClienteActivity.class);
                        intent.putExtra("loc", postagem);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Cancelar", null).create().show();
    }

    private void validarAcesso(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapNewPostActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            LatLng sydney = new LatLng(-3.1345645,-59.9814487);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        }
    }
}
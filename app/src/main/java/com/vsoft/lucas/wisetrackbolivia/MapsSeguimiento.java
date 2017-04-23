package com.vsoft.lucas.wisetrackbolivia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vsoft.lucas.wisetrackbolivia.app.AppController;
import com.vsoft.lucas.wisetrackbolivia.utils.Const;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsSeguimiento extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private String TAG = MapsSeguimiento.class.getSimpleName();
    public String jsonResponse;

    private String tag_json_arry = "jarray_req";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_seguimiento);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Pintar();
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        //{
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //}

        /*
        LatLng sydney = new LatLng(-17.767706, -63.115123);
        final Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in GabrielSentra").icon(BitmapDescriptorFactory.fromResource(R.mipmap.auto_verde_este)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Add a marker in Sydney and move the camera
        */
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void Pintar() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMap.clear();
                        //INICIO JsonArrayRequest
                        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Log.d(TAG, response.toString());

                                        try {
                                            // Parsing json array response
                                            // loop through each json object
                                            jsonResponse = "";
                                            for (int i = 0; i < response.length(); i++) {

                                                JSONObject trama = (JSONObject) response.get(i);

                                                String asimut = trama.getString("Asimut");
                                                String EstadoGPS = trama.getString("EstadoGPS");
                                                String EstadoMotor = trama.getString("EstadoMotor");
                                                String EstadoPuerta = trama.getString("EstadoPuerta");
                                                String FechaGPS = trama.getString("FechaGPS");
                                                String ID = trama.getString("ID");
                                                String IDButton = trama.getString("IDButton");
                                                String IMEI = trama.getString("IMEI");
                                                String Kilometraje = trama.getString("Kilometraje");
                                                String Latitud = trama.getString("Latitud");
                                                String Longitud = trama.getString("Longitud");
                                                String NIT = trama.getString("NIT");
                                                String Nombre = trama.getString("Nombre");
                                                String Nro = trama.getString("Nro");
                                                String NroPlaca = trama.getString("NroPlaca");
                                                String Temperatura = trama.getString("Temperatura");
                                                String Velocidad = trama.getString("Velocidad");
                                                String VoltajeBateria = trama.getString("VoltajeBateria");
                                                String direcciones = trama.getString("direcciones");
                                                String tipov = trama.getString("tipov");

/*
                                jsonResponse += "asimut: " + asimut + "\n";
                                jsonResponse += "EstadoGPS: " + EstadoGPS + "\n";
                                jsonResponse += "EstadoMotor: " + EstadoMotor + "\n";
                                jsonResponse += "EstadoPuerta: " + EstadoPuerta + "\n";
                                jsonResponse += "FechaGPS: " + FechaGPS + "\n";
                                jsonResponse += "ID: " + ID + "\n";
                                jsonResponse += "IDButton: " + IDButton + "\n";
                                jsonResponse += "IMEI: " + IMEI + "\n";
                                jsonResponse += "Kilometraje: " + Kilometraje + "\n";
                                jsonResponse += "Latitud: " + Latitud + "\n";
                                jsonResponse += "Longitud: " + Longitud + "\n";
                                jsonResponse += "NIT: " + NIT + "\n";
                                jsonResponse += "Nombre: " + Nombre + "\n";
                                jsonResponse += "Nro: " + Nro + "\n";
                                jsonResponse += "NroPlaca: " + NroPlaca + "\n";
                                jsonResponse += "Temperatura: " + Temperatura + "\n";
                                jsonResponse += "Velocidad: " + Velocidad + "\n";
                                jsonResponse += "VoltajeBateria: " + VoltajeBateria + "\n";
                                jsonResponse += "direcciones: " + direcciones + "\n";
                                jsonResponse += "tipov: " + tipov + "\n\n\n";
*/

                                                mMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud")))
                                                        .title("Placa: " + NroPlaca)
                                                        .snippet("Fecha: " + FechaGPS)
                                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.auto_verde_este)));


                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(),
                                                    "Error: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                //hideProgressDialog();
                            }
                        });

                        // Adding request to request queue
                        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

                        // Cancelling request
                        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

                        //FIN de JsonArrayRequest

                    }
                });
            }
        }, 0, 20000);
    }


    private void makeJsonArryReq() {
        //showProgressDialog();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }, 0, 20000);
    }

}

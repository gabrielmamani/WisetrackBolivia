package com.vsoft.lucas.wisetrackbolivia;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vsoft.lucas.wisetrackbolivia.app.AppController;
import com.vsoft.lucas.wisetrackbolivia.utils.Const;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MapsSeguimiento extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener {

    private GoogleMap mMap;
    private String TAG = MapsSeguimiento.class.getSimpleName();
    public String jsonResponse;
    private Marker mSelectedMarker;

    private static int[] resource_auto_verde = {
            R.mipmap.auto_verde_este,
            R.mipmap.auto_verde_noreste,
            R.mipmap.auto_verde_noroeste,
            R.mipmap.auto_verde_norte,
            R.mipmap.auto_verde_oeste,
            R.mipmap.auto_verde_sur,
            R.mipmap.auto_verde_sureste,
            R.mipmap.auto_verde_suroeste
    };

    private static int[] resource_auto_amarillo = {
            R.mipmap.auto_amarillo_este,
            R.mipmap.auto_amarillo_noreste,
            R.mipmap.auto_amarillo_noroeste,
            R.mipmap.auto_amarillo_norte,
            R.mipmap.auto_amarillo_oeste,
            R.mipmap.auto_amarillo_sur,
            R.mipmap.auto_amarillo_sureste,
            R.mipmap.auto_amarillo_suroeste
    };

    private static int[] resource_auto_rojo = {
            R.mipmap.auto_rojo_este,
            R.mipmap.auto_rojo_noreste,
            R.mipmap.auto_rojo_noroeste,
            R.mipmap.auto_rojo_norte,
            R.mipmap.auto_rojo_oeste,
            R.mipmap.auto_rojo_sur,
            R.mipmap.auto_rojo_sureste,
            R.mipmap.auto_rojo_suroeste
    };

    private static int[] resource_auto_azul = {
            R.mipmap.auto_azul_este,
            R.mipmap.auto_azul_noreste,
            R.mipmap.auto_azul_noroeste,
            R.mipmap.auto_azul_norte,
            R.mipmap.auto_azul_oeste,
            R.mipmap.auto_azul_sur,
            R.mipmap.auto_azul_sureste,
            R.mipmap.auto_azul_suroeste
    };

    private static int[] resource_auto_celeste = {
            R.mipmap.auto_celeste_este,
            R.mipmap.auto_celeste_noreste,
            R.mipmap.auto_celeste_noroeste,
            R.mipmap.auto_celeste_norte,
            R.mipmap.auto_celeste_oeste,
            R.mipmap.auto_celeste_sur,
            R.mipmap.auto_celeste_sureste,
            R.mipmap.auto_celeste_suroeste
    };

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

        mMap.getUiSettings().setZoomControlsEnabled(true);

        Pintar();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowCloseListener(this);

        LatLng latLng = new LatLng(-17.128210, -64.714407);
        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(latLng, 5);
        mMap.moveCamera(cameraUpdateFactory);



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
        mSelectedMarker = marker;
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

                                                String resultado = "FechaGPS: " + FechaGPS;
                                                float rasimut = Float.parseFloat(asimut);
                                                int restadomotor = Integer.parseInt(EstadoMotor);

                                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                Date date = new Date();
                                                try{
                                                    date = format.parse(FechaGPS);
                                                    Log.e("FechaGPS", date.toString());
                                                }catch (ParseException e){
                                                    e.printStackTrace();
                                                }

                                                int nro = ObtenerEstadoMovil(rasimut, restadomotor, date);

                                                /*
                                                mMap.addMarker(new MarkerOptions()
                                                        .position(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud")))
                                                        .title("Placa: " + NroPlaca)
                                                        .snippet(resultado)
                                                        .icon(BitmapDescriptorFactory.fromResource(nro)));
                                                        */

                                                if (NroPlaca.equals("4217-UBN")) {
                                                    mMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud")))
                                                            .title("Placa: " + NroPlaca)
                                                            .snippet(resultado)
                                                            .icon(BitmapDescriptorFactory.fromResource(nro)));

                                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud"))));

                                                } else {
                                                    mMap.addMarker(new MarkerOptions()
                                                            .position(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud")))
                                                            .title("Placa: " + NroPlaca)
                                                            .snippet(resultado)
                                                            .icon(BitmapDescriptorFactory.fromResource(nro)));
                                                }


                                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(trama.getDouble("Latitud"), trama.getDouble("Longitud"))));

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

    public int ObtenerEstadoMovil(float result, int restadomotor, Date fechagps)
    {
        int resultfinal = 0;
        int valor = 0;
        if (result >= 338) {
            valor = 3; // resource_image_auto[3]; //norte
        }
        if(result <= 23){
            valor = 3; //resource_image_auto[3]; //norte
        }
        if(result > 23 && result < 68){
            valor = 1; // resource_image_auto[1]; //noreste
        }
        if(result >= 68 && result < 113){
            valor = 0; //resource_image_auto[0]; //este
        }
        if(result >= 113 && result < 158){
            valor = 6; //resource_image_auto[6]; //sureste
        }
        if(result >= 158 && result < 203){
            valor = 5; //resource_image_auto[5]; //sur
        }
        if(result >= 203 && result < 248){
            valor = 7; //resource_image_auto[7]; //suroeste
        }
        if(result >= 248 && result < 293){
            valor =4; // resource_image_auto[4]; //oeste
        }
        if(result >= 293 && result < 338){
            valor = 2; //resource_image_auto[2]; //noroeste
        }

        Date newDate = new Date();
        long diff = newDate.getTime() - fechagps.getTime();
        //long minutos = (diff/(1000*60))%60;
        long minutos = (diff/(1000*60));
        if(minutos >= 60){
            resultfinal = resource_auto_rojo[valor];
        }
        if(minutos >= 30 && minutos < 60){
            resultfinal= resource_auto_amarillo[valor];
        }
        if(minutos < 30){
            switch (restadomotor){
                case 11:
                    resultfinal = resource_auto_azul[valor];
                    break;
                case 12:
                    resultfinal = resource_auto_celeste[valor];
                    break;
                case 21:
                    resultfinal = resource_auto_verde[valor];
                    break;
                case 22:
                    resultfinal = resource_auto_verde[valor];
                    break;
                case 41:
                    resultfinal = resource_auto_azul[valor];
                    break;
                case 42:
                    resultfinal = resource_auto_celeste[valor];
                    break;
                case 1:
                    resultfinal = resource_auto_verde[valor];
                    break;
                case 0:
                    resultfinal = resource_auto_azul[valor];
                    break;
                default:
                    resultfinal = resource_auto_verde[valor];
                    break;
            }
        }
        return resultfinal;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        Toast.makeText(this, "Info Window long click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        //Toast.makeText(this, "Close Info Window", Toast.LENGTH_SHORT).show();
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            //render(marker, mWindow);
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;
            badge = R.mipmap.auto_verde_este;

            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));

            if (title != null) {
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                //snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }

    }

/*
    public void onClearMap(View view) {
        mMap.clear();
    }

    public void onResetMap(View view) {
        mMap.clear();
        //Pintar();
    }
*/

}

//gm
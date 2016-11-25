package com.example.mike.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import static com.example.mike.myapplication.R.id.location;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private boolean wifiConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver receiver = MyBroadcastReceiver.createMyBroadcastReceiver(this);
        LocationListener locationListener = new MyLocationListener(this);
        setWifiButtonMessage(this.getBaseContext());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void startCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    public void updateLocation(Address address, float distanciaALondres) {
        String addressString = address.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = address.getLocality();
        String state = address.getAdminArea();
        String country = address.getCountryName();
        String postalCode = address.getPostalCode();
        String knownName = address.getFeatureName(); // Only if available else return NULL

        String locationString = "Your current location:\naddress: " + addressString +
                "\ncity: " + city +
                "\nstate: " + state +
                "\ncountry: " + country +
                "\npost code: " + postalCode +
                "\nknown name: " + knownName +
                "\n distancia a Londres: " + (distanciaALondres / 1000) + "km" + ", " + (distanciaALondres / 1600) + " miles";
        TextView textView = (TextView) findViewById(location);
        textView.setText(locationString);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void setWifiButtonMessage(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getActiveNetworkInfo();
        String message = "";
        if ( null != mWifi && mWifi.getType() == ConnectivityManager.TYPE_WIFI && mWifi.isConnectedOrConnecting()) {
            message = "Apagar Wifi";
            wifiConnected = true;
        } else {
            message = "Encender Wifi";
            wifiConnected = false;
        }
        Button textView = (Button) findViewById(R.id.wifiButton);
        textView.setText(message);
    }

    public void toggleWifi(View view) {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if (!wifiConnected) {
            Button textView = (Button) findViewById(R.id.wifiButton);
            textView.setText("encendiendo...");
        }
        wifiManager.setWifiEnabled(wifiConnected ? false : true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.capturedPhoto);
            imageView.setImageBitmap(imageBitmap);
        }
    }

}

package biu.ex4.danevy.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import biu.ex4.danevy.R;

public class MainActivity extends AppCompatActivity {

    private static final String INTERNET_PERM = Manifest.permission.INTERNET;
    private static final int INTERNET_PERM_CODE = 2;

    private Button connectButton;
    private TextView ipText, portText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.connectButton = findViewById(R.id.connect);
        this.ipText = findViewById(R.id.ipText);
        this.portText = findViewById(R.id.portText);

        // move to the joystick when clicked
        connectButton.setOnClickListener(new OnConnect());

        // behave according to whether internet permission was granted
        boolean hasInternet = getInternetPermission()
                == PackageManager.PERMISSION_GRANTED;
        onInternetPermissionChange(hasInternet);
    }

    private class OnConnect implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String ip = ipText.getText().toString();
            int port = Integer.parseInt(ipText.getText().toString());

            Intent moveIntent = new Intent(MainActivity.this, JoystickActivity.class);
            moveIntent.putExtra("ip", ip);
            moveIntent.putExtra("port", port);
            startActivity(moveIntent);
        }
    }

    private int getInternetPermission() {
        return ContextCompat
                .checkSelfPermission(this, INTERNET_PERM);
    }

    private void requestInternetPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] { INTERNET_PERM }, INTERNET_PERM_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == INTERNET_PERM_CODE) {
            boolean hasPermission = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;

            onInternetPermissionChange(hasPermission);
        }
    }

    private void onInternetPermissionChange(boolean hasInternet) {
        connectButton.setEnabled(hasInternet);

        if (!hasInternet) {
            requestInternetPermission();
        }
    }
}

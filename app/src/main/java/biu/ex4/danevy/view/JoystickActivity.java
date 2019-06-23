package biu.ex4.danevy.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import biu.ex4.danevy.R;
import biu.ex4.danevy.model.ClientModel;
import biu.ex4.danevy.model.FlightClientModel;
import biu.ex4.danevy.model.ThreadedClientModel;
import biu.ex4.danevy.view.joystick.Joystick;
import biu.ex4.danevy.view.joystick.OnJoystickValueChange;


public class JoystickActivity extends AppCompatActivity {

    private Joystick joystick;
    private ClientModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
            String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port", 25565);

        joystick = findViewById(R.id.joystick);

        initConnection(ip, port);
    }

    private void initConnection(String ip, int port) {
        // create and connect model
        model = new ThreadedClientModel(new FlightClientModel());
        model.connect(ip, port);

        // send requests to change values
        joystick.setValueChangeListener((aileron, elevator) -> {
            model.set(ClientModel.AILERON_PATH, aileron);
            model.set(ClientModel.ELEVATOR_PATH, elevator);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.disconnect();
    }
}

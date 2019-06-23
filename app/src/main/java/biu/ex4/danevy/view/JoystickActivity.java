package biu.ex4.danevy.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import biu.ex4.danevy.R;
import biu.ex4.danevy.view.joystick.Joystick;
import biu.ex4.danevy.view.joystick.OnJoystickValueChange;


public class JoystickActivity extends AppCompatActivity {

    private class Notified implements OnJoystickValueChange {

        @Override
        public void onChanged(float x, float y) {
            Log.i("vc", "x=" + x + ", y=" + y);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Joystick joy = findViewById(R.id.joystick);
        joy.setValueChangeListener(new Notified());
    }
}

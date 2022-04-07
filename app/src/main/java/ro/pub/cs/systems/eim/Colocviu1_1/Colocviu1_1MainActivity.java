package ro.pub.cs.systems.eim.Colocviu1_1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.stream.Stream;

public class Colocviu1_1MainActivity extends AppCompatActivity {

    private static class MyServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("SERVICE",
                    intent.getAction() + " " + intent.getStringExtra(Constants.DATA));
        }
    }

    private static final String TIMES_PRESSED = "TIMES_PRESSED";

    private int timesPressed;
    private MyServiceReceiver receiver;
    private IntentFilter receiveFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu_1_1main);

        if (savedInstanceState != null) {
            timesPressed = savedInstanceState.getInt(TIMES_PRESSED);
        } else {
            timesPressed = 0;
        }

        receiver = new MyServiceReceiver();
        receiveFilter = new IntentFilter();
        receiveFilter.addAction(Constants.ACTION);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TIMES_PRESSED, timesPressed);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            String text = "";
            switch (resultCode) {
                case Activity.RESULT_OK:
                    text = "Register";
                    break;
                case Activity.RESULT_CANCELED:
                    text = "Cancel";
                    break;
            }
            Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show();
        }
    }

    public void onSecondActivityButtonClick(View view) {
        EditText editText = findViewById(R.id.presses);
        Intent intent = new Intent(this, Colocviu1_1SecondaryActivity.class);
        intent.putExtra(Constants.PRESS_LIST, editText.getText().toString());
        timesPressed = 0;
        editText.setText("");
        startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
    }

    public void onDirButtonClick(View view) {
        EditText editText = findViewById(R.id.presses);
        String text = editText.getText().toString();
        switch (view.getId()) {
            case R.id.northButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("North");
                break;
            case R.id.southButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("South");
                break;
            case R.id.eastButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("East");
                break;
            case R.id.westButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("West");
                break;
        }
        ++timesPressed;
        Log.d("NUMBER", String.valueOf(timesPressed));
        checkStartService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, receiveFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, Colocviu1_1Service.class));
    }

    private void checkStartService() {
        if (timesPressed == Constants.START_SERVICE_THRESHOLD) {
            EditText editText;
            Intent intent = new Intent(this, Colocviu1_1Service.class);

            editText = (EditText) findViewById(R.id.presses);
            intent.putExtra(Constants.PRESS_LIST, editText.getText().toString());

            startService(intent);
        }
    }
}
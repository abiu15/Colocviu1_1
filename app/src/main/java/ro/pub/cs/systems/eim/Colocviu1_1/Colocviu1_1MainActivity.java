package ro.pub.cs.systems.eim.Colocviu1_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Colocviu1_1MainActivity extends AppCompatActivity {

    private static final String TIMES_PRESSED = "TIMES_PRESSED";

    int timesPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Colocviu_1_1main);

        if (savedInstanceState != null) {
            timesPressed = savedInstanceState.getInt(TIMES_PRESSED);
        } else {
            timesPressed = 0;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TIMES_PRESSED, timesPressed);
    }

    public void onSecondActivityButtonClick(View view) {

    }

    public void onDirButtonClick(View view) {
        EditText editText = findViewById(R.id.presses);
        String text = editText.getText().toString();
        switch (view.getId()) {
            case R.id.northButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("North");
            case R.id.southButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("South");
            case R.id.eastButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("East");
            case R.id.westButton:
                if (!text.isEmpty())
                    editText.append(",");
                editText.append("West");
        }
        ++timesPressed;
    }
}
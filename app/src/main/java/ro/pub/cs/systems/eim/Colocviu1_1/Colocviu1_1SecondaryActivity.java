package ro.pub.cs.systems.eim.Colocviu1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Colocviu1_1SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu_1_1_secondary);

        ((EditText) findViewById(R.id.presses)).setText(
                getIntent().getExtras().getString(Constants.PRESS_LIST));
    }

    public void onButtunClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.registerButton:
                setResult(RESULT_OK, intent);
                break;
            case R.id.cancelButton:
                setResult(RESULT_CANCELED, intent);
                break;
        }
        finish();
    }
}
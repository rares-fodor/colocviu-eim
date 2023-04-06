package ro.pub.cs.systems.eim.colocviu2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ro.pub.cs.systems.eim.colocviu2023.Constants.Constants;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TERMS)) {
            String value = intent.getExtras().getString(Constants.TERMS);
            int total = 0;

            String[] tokens = value.split(" \\+ ");
            for (String token : tokens) {
                total += Integer.parseInt(token);
            }

            intent.putExtra(Constants.RETURN_DATA, total);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
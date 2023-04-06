package ro.pub.cs.systems.eim.colocviu2023;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.colocviu2023.Constants.Constants;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button computeButton;

    private EditText nextTermEditText;
    private EditText resultEditText;

    private boolean hasBeenModified;
    private String output;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (data != null && data.getExtras().containsKey(Constants.RETURN_DATA)) {
                    Toast toast = Toast.makeText(this,
                            String.valueOf(data.getExtras().getInt(Constants.RETURN_DATA)),
                            Toast.LENGTH_SHORT);
                    toast.show();
                    output = String.valueOf(data.getExtras().getInt(Constants.RETURN_DATA));
                }
            }
    );

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.add_button:
                    String term = nextTermEditText.getText().toString();
                    String partial = resultEditText.getText().toString();
                    if (!term.equals("")) {
                        if (partial.equals("")) {
                            partial = term;
                        } else {
                            partial = partial + " + " + term;
                        }
                        hasBeenModified = true;
                    } else {
                        hasBeenModified = false;
                    }
                    resultEditText.setText(partial);
                    break;
                case R.id.compute_button:
                    if (hasBeenModified) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.putExtra(Constants.TERMS, resultEditText.getText().toString());
                        launcher.launch(intent);
                        hasBeenModified = false;
                    } else {
                        if (!output.equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), output,
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hasBeenModified = false;

        addButton = findViewById(R.id.add_button);
        computeButton = findViewById(R.id.compute_button);

        addButton.setOnClickListener(buttonClickListener);
        computeButton.setOnClickListener(buttonClickListener);

        nextTermEditText = findViewById(R.id.next_term_edit);
        resultEditText = findViewById(R.id.result_edit_text);

    }


}
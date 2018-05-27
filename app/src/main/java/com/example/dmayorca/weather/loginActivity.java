package com.example.dmayorca.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {


    public EditText editText3=null;
    public EditText editText4=null;
    private Button btnLogin =null;
    public int counter=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText3.getText().toString().equals("admin") &&
                        editText4.getText().toString().equals("admin")) {

                    Toast.makeText(getApplicationContext(), "Connect with app",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    startActivityForResult(intent, 0);

                }else{

                    Toast.makeText(getApplicationContext(), "Wrong " +
                            "Credentials",Toast.LENGTH_SHORT).show();

                    /*stateLogin.setVisibility(View.VISIBLE);
                    stateLogin.setBackgroundColor(Color.RED);
                    counter--;
                    stateLogin.setText(Integer.toString(counter));*/

                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                    }

                }
            }
        });



    }

    public void fcn_info(View view) {

        Toast.makeText(getApplicationContext(), "Connect with info",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, infoActivity.class);
        startActivityForResult(intent, 0);
    }

    public void fcn_autores(View view) {

        Toast.makeText(getApplicationContext(), "Connect with autors",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, autorsActivity.class);
        startActivityForResult(intent, 0);
    }
}

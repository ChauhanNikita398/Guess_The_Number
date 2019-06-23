package com.example.lucktrial;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int attempts = 0;
    final int maxAttempts = 4;
    Random randGen = new Random();
    int ranNum;
    ProgressBar mProgressBar;



    final int PROGRESS_BAR_INCREMENT=(int)Math.ceil(100.0/(maxAttempts +1));
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textResponse = (TextView) findViewById(R.id.txtResponse);
        final TextView guessText = (TextView) findViewById(R.id.txtAnswer);
        final EditText userGuess = (EditText) findViewById(R.id.editText);
        mProgressBar=findViewById(R.id.progressBar);


        Button pressMe = (Button) findViewById(R.id.button);

        randGen = new Random();
        ranNum = randGen.nextInt(100);
        pressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (++attempts > maxAttempts) {
                    textResponse.setText("");
                    userGuess.setText("");

                    final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);

                    alert.setTitle("Trial Over");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                        }
                    });
                    alert.setNegativeButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            PackageManager packageManager= getApplicationContext().getPackageManager();
                            Intent intent=packageManager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
                            ComponentName componentName=intent.getComponent();
                            Intent mainIntent=Intent.makeRestartActivityTask(componentName);
                            getApplicationContext().startActivity(mainIntent);


                        }
                    });
                    alert.show();
                }
                else {
                    String randText = "";

                    int userNumber = Integer.parseInt(userGuess.getText().toString());

                    if (userNumber > 100) {
                        guessText.setText("Please guess between 1 and 100");
                    } else if (userNumber < ranNum) {
                        guessText.setText("Too low.Try again!");
                        guessText.setBackgroundColor(Color.RED);
                    } else if (userNumber > ranNum) {
                        guessText.setText("Too High.Try again!");
                        guessText.setBackgroundColor(Color.RED);
                    } else if (userNumber == ranNum) {
                        ranNum = randGen.nextInt(100);
                        guessText.setText("You did it!");
                        guessText.setBackgroundColor(Color.WHITE);

                        textResponse.setText("");

                        userGuess.setText("");

                        final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);

                        alert.setTitle("Trial Over");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        alert.setNegativeButton("Try again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                PackageManager packageManager = getApplicationContext().getPackageManager();
                                Intent intent = packageManager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
                                ComponentName componentName = intent.getComponent();
                                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                                getApplicationContext().startActivity(mainIntent);
                            }
                        });
                        alert.show();
                    }
                    randText = Integer.toString(ranNum);
                    textResponse.setText("");

                    userGuess.setText("");

                }
               mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);

            }

            ;


        });

    }
}

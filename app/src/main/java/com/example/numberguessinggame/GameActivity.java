package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean twoDigits, threeDigits, fourDigits;
    Random r = new Random();
    int random;
    int remainingRight = 10;

    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewHint = findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewRight = findViewById(R.id.textViewRight);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        editTextGuess = findViewById(R.id.editTextGuess);


        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);

        if(twoDigits)
        {
            random = r.nextInt(90)+10;
        }
        if(threeDigits)
        {
            random = r.nextInt(900)+100;
        }
        if(fourDigits)
        {
            random = r.nextInt(9000)+1000;
        }
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editTextGuess.getText().toString();
                if(guess.equals(""))
                {
                    Toast.makeText(GameActivity.this, "Введи число", Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingRight--;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Твое последние предположение: "+guess);
                    textViewRight.setText("У тебя осталось попыток "+remainingRight);

                    if(random == userGuess)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Угадайка!");
                        builder.setCancelable(false);
                        builder.setMessage("Веерно! Как ты это сделал? Я загадывал " + random
                        +"\n\n Ты отгадал мое число с "+ userAttempts + " попыток!" +
                                "\n\n Вот все твои предположения "+guessesList+
                        "\n\nБудешь еще играть?");
                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();

                    }
                    if(random < userGuess)
                    {
                        textViewHint.setText("Мое число меньше");

                    }
                    if(random > userGuess)
                    {
                        textViewHint.setText("Мое число больше");
                    }
                    if(remainingRight == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Угадайка!");
                        builder.setCancelable(false);
                        builder.setMessage("Слабак! Ты проиграл! Я загадывал " + random
                                +"\n\n Ты не отгадал мое число с "+ userAttempts + " попыток!" +
                                "\n\n Вот все твои предположения "+guessesList+
                                "\n\nБудешь еще играть?");
                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    editTextGuess.setText("");
                }
            }
        });
    }
}
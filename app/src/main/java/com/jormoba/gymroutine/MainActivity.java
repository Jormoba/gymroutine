package com.jormoba.gymroutine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jormoba.gymroutine.ui.main.FragmentLista;
import com.jormoba.gymroutine.ui.main.SectionsPagerAdapter;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILIS = 30000;
    private TextView textViewCountDown;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILIS;
    private MediaPlayer mediaPlayer;
    private Button buttonStartPause, buttonReset, buttonAddTime;

    private Button buttonAdd;
    private Spinner spinner;

    String usuario;
    String [] clientes = {"Pepe", "Juan", "Andrés"};

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = "Test";

        // DECLARAR SPINNER Y PONER ADAPTADOR
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, clientes);
        spinner.setAdapter(adapter);

        // CARGAR SPINNER DEFAULT
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        spinner.setSelection(sharedPreferences.getInt("cliente_int", 0));
        usuario = spinner.getSelectedItem().toString();

        // GUARDAR SPINNER DEFAULT
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cliente_string", clientes[i]);
                editor.putInt("cliente_int", i);
                editor.commit();

                // VIEWPAGER Y TABS
                sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager());
                viewPager = findViewById(R.id.view_pager);
                viewPager.setAdapter(sectionsPagerAdapter);
                tabs = findViewById(R.id.tabs);
                tabs.setupWithViewPager(viewPager);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // VIEWPAGER Y TABS
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // BOTÓN DE AÑADIR EJERCICIO PARA ENTRENADOR
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddEjercicio.class);
                int item = viewPager.getCurrentItem();
                String dia;

                switch (item){
                    case 0: dia = "lunes"; break;
                    case 1: dia = "martes"; break;
                    case 2: dia = "miercoles"; break;
                    case 3: dia = "jueves"; break;
                    case 4: dia = "viernes"; break;
                    case 5: dia = "sabado"; break;
                    default: dia = "lunes"; break;
                }
                i.putExtra("dia",dia);
                i.putExtra("usuario",usuario);
                startActivity(i);
            }
        });

        //Contdown
        textViewCountDown = (TextView) findViewById(R.id.textView);
        buttonStartPause = (Button) findViewById(R.id.button1);
        buttonReset = (Button) findViewById(R.id.button2);
        buttonAddTime = (Button) findViewById(R.id.button3);

        mediaPlayer = MediaPlayer.create(this, R.raw.popcorn);

        buttonReset.setVisibility(View.GONE);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        buttonAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTime();
            }
        });

        updateCountDownText();
    }

    private void startTimer (){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartPause.setText("Start");
                buttonStartPause.setVisibility(View.GONE);
                buttonReset.setVisibility(View.VISIBLE);

                mediaPlayer.start();
            }
        }.start();

        timerRunning = true;
        buttonStartPause.setText("Pausar");
        buttonReset.setVisibility(View.GONE);
    }

    private void pauseTimer (){
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText("Iniciar");
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer (){
        timeLeftInMillis = START_TIME_IN_MILIS;
        updateCountDownText();
        buttonReset.setVisibility(View.GONE);
        buttonStartPause.setVisibility(View.VISIBLE);
    }

    private void addTime(){
        timeLeftInMillis = timeLeftInMillis + 5000;
        updateCountDownText();
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeLeftFormatted);
    }

}
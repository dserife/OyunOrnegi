package com.example.oyunornegi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageView1,
            imageView2,
            imageView3,
            imageView4,
            imageView5,
            imageView6,
            imageView7,
            imageView8,
            imageView9;

    ImageView[] imageArray;

    TextView tvTime,
            tvSkor;

    int skor;

    Handler handler;
    Runnable runabble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        tvTime=findViewById(R.id.tvTime);
        tvSkor=findViewById(R.id.tvSkor);

        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);

        imageArray = new ImageView[] {
                imageView1,
                imageView2,
                imageView3,
                imageView4,
                imageView5,
                imageView6,
                imageView7,
                imageView8,
                imageView9
        };

        hideImage();

        skor=0;

        new CountDownTimer(20000,1000) {
            //zamanlayıcıyı başlatır.
            @Override
            public void onTick(long millisUntilFinished) {

                tvTime.setText("Süre : "+ millisUntilFinished/1000);

            }

            //zaman bitince ne olacağını belirleriz.
            @Override
            public void onFinish() {

                //süre bitti uyarısı yazdırdık ve image nesnelerini gizledik.
                tvTime.setText("SÜRE BİTTİ!");
                handler.removeCallbacks(runabble);
                for (ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                alertDialog.setTitle("Tekrar oynamak ister misiniz?");
                alertDialog.setMessage("Tekrar oynamak istediğinize emin misiniz ?");

                alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //restart
                       //tekrar oynamak ister misiniz sorusuna evet dendiğinde oyun baştan başlatılacak
                        // yani main activtiy yi intent nesnesiyle çağırıyoruz.

                        Intent intent=getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                //tekrar oynamak ister misiniz sorusuna hayır dendiğinde olacaklar.
                alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(),"OYUN BİTTİ!",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.show();

            }
        }.start();

    }

    //resme her tıklandığında skoru 1 arttıracak metot.
    public void skoruArttir(View view){

        skor++; //skorun değerini bir attırır.

        tvSkor.setText("Skore : "+skor);

    }

    //random olarak image nesnelerini gösteren metot.
    public void hideImage(){

        handler= new Handler();

        //aynı kodu beli bir periyot içerisinde tekrarlayabilmek için
        //runnable sınıfı kullanıyoruz, handler ile birlikte.
        runabble= new Runnable() {
            @Override
            public void run() {

                //image nesnelerini gizleyen kod.
                for (ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i =random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,700);
            }
        };

        handler.post(runabble);
    }
}

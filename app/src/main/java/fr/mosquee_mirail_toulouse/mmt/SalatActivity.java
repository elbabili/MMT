package fr.mosquee_mirail_toulouse.mmt;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalatActivity extends AppCompatActivity {
    private TextView txtEditFajr;
    private TextView txtEditDohor;
    private TextView txtEditAsr;
    private TextView txtEditMaghreb;
    private TextView txtEditIchaa;

    private String hourFajr;
    private String hourDohor;
    private String hourAsr;
    private String hourMaghreb;
    private String hourIchaa;

    private EditText txtEdit;
    private TextView txtEditTimer;

 /* @Override
    public void onStart() { System.out.println("onStart de SalatActivity"); super.onStart(); }
    @Override
    public void onPause() { System.out.println("onPause de SalatActivity"); super.onPause(); }
    @Override
    public void onStop() { System.out.println("onStop de SalatActivity"); super.onStop(); }
    @Override
    public void onRestart() {  System.out.println("onRestart de SalatActivity"); super.onRestart(); }
    @Override
    public void onDestroy() { System.out.println("onDestroy de SalatActivity"); super.onDestroy(); }*/
    @Override
    public void onResume() {
        //System.out.println("onResume de SalatActivity");
        //refreshUI();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate de SalatActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salat);
        refreshUI();
        setTitle("Horaires de prières");
    }

    private void refreshUI(){
        //txtEdit = (TextView) findViewById(R.id.textView4);
        txtEditTimer = (TextView) findViewById(R.id.timer);

        txtEditFajr = (TextView) findViewById(R.id.editFajr);
        txtEditDohor = (TextView) findViewById(R.id.editDohor);
        txtEditAsr = (TextView) findViewById(R.id.editAsr);
        txtEditMaghreb = (TextView) findViewById(R.id.editMaghreb);
        txtEditIchaa = (TextView) findViewById(R.id.editIchaa);

        txtEdit = (EditText) findViewById(R.id.editText);
        txtEdit.setKeyListener(null);
        Spanned sp = Html.fromHtml(explainPrayer());
        txtEdit.setText(sp);

        hourFajr = txtEditFajr.getText().toString();
        hourDohor = txtEditDohor.getText().toString();
        hourAsr = txtEditAsr.getText().toString();
        hourMaghreb = txtEditMaghreb.getText().toString();
        hourIchaa = txtEditIchaa.getText().toString();

        Date date = new Date();     // date et heure actuelle
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");     //formatage qui nous interesse
        String hourCurrent = f.format(date);    //conversion en chaine

        Date dteNow = null, dteFajr = null, dteDohor = null, dteAsr = null, dteMaghreb = null, dteIchaa = null, dteTomFajr = null;
        try {
            dteNow = f.parse(hourCurrent);

            dteFajr = f.parse(hourFajr);
            dteDohor = f.parse(hourDohor);
            dteAsr = f.parse(hourAsr);
            dteMaghreb = f.parse(hourMaghreb);
            dteIchaa = f.parse(hourIchaa);

            //dteTomFajr = f.parse(hourTomorowFajr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeNow = dteNow.getTime();

        long timeFajr = dteFajr.getTime();
        long timeDohor = dteDohor.getTime();
        long timeAsr = dteAsr.getTime();
        long timeMaghreb = dteMaghreb.getTime();
        long timeIchaa = dteIchaa.getTime();

        long timeTomorowFajr = PrayTime.getTomorowFajrTime();

        long timeRest = 0;      // temps restant avant la prochaine prière !

        if( timeNow > timeFajr ){   //prière fajr passée
            if(timeNow > timeDohor){
                if(timeNow > timeAsr){
                    if(timeNow > timeMaghreb){
                        if(timeNow > timeIchaa){
                            //txtEdit.setText(" la prochaine prière est Fajr, il reste :");
                            timeRest = timeTomorowFajr - timeNow;
                        }
                        else {
                            //txtEdit.setText(" la prochaine prière est Ichaa, il reste :");
                            timeRest = timeIchaa - timeNow;
                        }
                    }
                    else {
                        //txtEdit.setText(" la prochaine prière est Maghreb, il reste :");
                        timeRest = timeMaghreb - timeNow;
                    }
                }
                else {
                    //txtEdit.setText(" la prochaine prière est Asr, il reste :");
                    timeRest = timeAsr - timeNow;
                }
            }
            else {
                //txtEdit.setText(" la prochaine prière est Dohor, il reste :");
                timeRest = timeDohor - timeNow;
            }
        }
        else {
            //txtEdit.setText(" la prochaine prière est Fajr");
            timeRest = timeTomorowFajr - timeNow;
        }

        new CountDownTimer(timeRest, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int days = (int) ((millisUntilFinished / 1000) / 86400);
                int hours = (int) (((millisUntilFinished / 1000)
                        - (days * 86400)) / 3600);
                int minutes = (int) (((millisUntilFinished / 1000)
                        - (days * 86400) - (hours * 3600)) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String countdown = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                txtEditTimer.setText(countdown);
                //countdownTimer.setText(countdown);
            }
            @Override
            public void onFinish() {
                txtEditTimer.setVisibility(View.GONE);
                //txtEditTimer.setText("IT'S HERE!");
                //Comment faire pour qu'il recommece à la fin d'une prière ?
            }
        }.start();
    }

    private String explainPrayer(){
        InputStream inputStream = getResources().openRawResource(R.raw.salat);
        try {
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String str;
                StringBuilder buf = new StringBuilder();

                while ((str = reader.readLine()) != null) {     buf.append(str + "<br />");   }

                reader.close();
                inputStream.close();
                return buf.toString();
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG);
        }
        return null;
    }
}

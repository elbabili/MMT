package fr.mosquee_mirail_toulouse.mmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import fr.mosquee_mirail_toulouse.mmt.DataBase.Notification;

import static fr.mosquee_mirail_toulouse.mmt.MainActivity.mainDataBase;

public class DjanazaActivity extends AppCompatActivity {

    private EditText editTextDjanaza;
    private TextView txtViewDjanaza;
    private ImageButton imgBtnWhatsApp;

    private String msgDjanaza = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_djanaza);

        setTitle("Salat Djanaza");

        txtViewDjanaza = (TextView) findViewById(R.id.txtViewDjanaza);
        txtViewDjanaza.setText(getDjanaza());

        editTextDjanaza = (EditText) findViewById(R.id.editTxtDjanaza);
        editTextDjanaza.setKeyListener(null);
        Spanned sp = Html.fromHtml(explainDjanaza());
        editTextDjanaza.setText(sp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.djanaza_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.djanazaMenu :
                shareDjanaza();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDjanaza(){
        if(mainDataBase != null) {
            ArrayList<Notification> notifs = mainDataBase.getNotifs();
            if (notifs.size() > 0) {
                Iterator<Notification> it = notifs.iterator();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();     // date du jour
                String strJour = formatter.format(date);

                while (it.hasNext()){
                    Notification notif = it.next();     // notif courante
                    String strNotif = notif.getWhen();
                    Date dateNotif = null, dateJour = null;
                    try {
                        dateNotif = formatter.parse(strNotif);
                        dateJour = formatter.parse(strJour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(dateJour.after(dateNotif)){
                        mainDataBase.deleteNotif(notif.getNotifID());    // suppression de cette notif
                    }
                    else {
                        if(msgDjanaza == null){ msgDjanaza = ""; }
                        else msgDjanaza += "\n";
                        msgDjanaza += notif.getWhat();
                    }
                }
                //if(msgDjanaza == null)     msgDjanaza = "pas de salat djanaza actuellement";
            }
            else {
                msgDjanaza = "pas de salat djanaza de prévue";
            }
        }
        return msgDjanaza;
    }

    public String explainDjanaza(){
        InputStream inputStream = getResources().openRawResource(R.raw.djanaza);
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

    public void shareDjanaza(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msgDjanaza);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Envoyé avec :"));
    }
}

package fr.mosquee_mirail_toulouse.mmt;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import fr.mosquee_mirail_toulouse.mmt.DataBase.Article;
import fr.mosquee_mirail_toulouse.mmt.DataBase.MyCustomAdapter;
import fr.mosquee_mirail_toulouse.mmt.Rss.RssItem;
import fr.mosquee_mirail_toulouse.mmt.Rss.RssReader;

import static fr.mosquee_mirail_toulouse.mmt.MainActivity.mainDataBase;

public class NewsActivity extends Activity implements View.OnClickListener {
    private ListView mList;
    private ArrayList<Article>    articles = null;
    private MyCustomAdapter myCustomAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mList = (ListView) findViewById(R.id.list);

        //On met un écouteur d'évènement sur notre listView
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // à partir de la position, je lance une webview dans une nouvelle activité, à condition d'avoir des éléments dans mes articles
                if(articles.size() > 0){
                    Article artCurrent = articles.get(position);
                    //tester s'il y a une connexion internet + ouvrir une webview en consultation uniquement
                    Intent intent = new Intent(NewsActivity.this,WebActivity.class);
                    intent.putExtra("LINK_ART",artCurrent.getLink());
                    startActivity(intent);
                }
            }
        });

        Button btn = (Button)findViewById(R.id.btnUpdate);
        btn.setOnClickListener(this);

        refreshUI();       //s'il existe une occurence en base, à etudier !    +   si pas de connexion reseaux

        Log.i("UIN","nous sommes dans la methode create de la fenetre NewsActivity");
    }

    private void refreshUI(){
        articles = mainDataBase.getArticles();        //s'il y en a, récupération de tous les articles en bdd
        // que faire si la base est vide, on affiche rien
        myCustomAdapter = new MyCustomAdapter(this, R.layout.article_details, articles);  //gestion affichage du listView
        mList.setAdapter(myCustomAdapter);

        //myCustomAdapter.refresh();

        //myCustomAdapter.clear();
        //myCustomAdapter.addAll(mList);
        myCustomAdapter.notifyDataSetChanged();
    }

    public void onClick(View view){
        if (view.getId() == R.id.btnUpdate) {
            updateUI();
        }
    }

    public void onDestroy(Bundle savedInstanceState) {
        //adbh.close();
    }

    private void updateUI() {        //récupérer le dernier flux et mettre à jour sa base de donné  ---> trouver un moyen de ne pas récupérer les mêmes flux
        try {
            new task().execute("http://www.mosquee-mirail-toulouse.fr/feed/");
            //comment j'évite les doublons ou les mêmes informations ?
        }
        catch (Exception e) {     //revoir gestion des exceptions pour BDD
            Log.v("Error Open RSS", e + "");
            Log.i("RSS","Erreur sur ouverture Flux RSS");
        }
        //return true;    //si mise à jour ok sinon message ...
    }

    private class task extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream stream = connection.getInputStream();

                RssReader rssReader = new RssReader(stream);
                Iterator<Article> it = articles.iterator();

                for (RssItem item : rssReader.getItems()) {
                    // avant d'inserer un article je dois verifier s'il existe ---> comparaison pour l'instant uniquement sur le titre
                    String title = item.getTitle();
                    boolean bool = false;

                    while (it.hasNext()){
                        if(it.next().getTitle().equals(title)) {
                            bool = true;
                            break;
                        }
                    }
                    if (bool == false)  mainDataBase.insertArticle(title,item.getDate(),item.getDescription(),item.getLink());
                }
                Log.i("BDD","Apres insertion Flux Rss, affichage !");

            } catch (Exception e) {     //revoir gestion des exceptions pour BDD
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                refreshUI();
            } catch (Exception e) {
                Log.v("Error refreshUI onEx", e + "");
            }
        }
    }
}

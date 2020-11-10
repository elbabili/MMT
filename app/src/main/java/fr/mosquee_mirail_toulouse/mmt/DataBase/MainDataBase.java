package fr.mosquee_mirail_toulouse.mmt.DataBase;

/**
 * Created by moham on 18/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MainDataBase extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MMT.db";        //nom base de donnée
    public static final String ITEMS_TABLE_NAME = "t_items";     //table des articles
    public static final String NOTIF_TABLE_NAME = "t_notifs";     //table des notifications

    public static final String ITEM_ID = "item_id";
    public static final String ITEM_TITLE = "i_title";          //titre d'un article
    public static final String ITEM_DATE = "i_date";            //date de publication
    public static final String ITEM = "i_item";                 //contenu de l'article
    public static final String ITEM_LINK = "i_link";                 //lien vers l'article


    public static final String NOTIF_ID = "notif_id";
    public static final String NOTIF_WHAT = "n_what";           //Type de notif
    public static final String NOTIF_WHERE = "n_where";         //Lieu
    public static final String NOTIF_WHEN = "i_when";           //date

    public MainDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " +           ITEMS_TABLE_NAME +
                "(item_id integer primary key AUTOINCREMENT NOT NULL,"+
                ITEM_TITLE + " Text," +
                ITEM_DATE  + " Text,"+
                ITEM       + " Text,"+
                ITEM_LINK  + " Text)"
        );
        db.execSQL("create table  " +           NOTIF_TABLE_NAME +
                "(notif_id integer primary key AUTOINCREMENT NOT NULL,"+
                NOTIF_WHAT + " Text," +
                NOTIF_WHERE +  " Text,"+
                NOTIF_WHEN + " Text)"
        );
        //Log.i("BDD","nous venons de créer la base de donnée");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME);
        onCreate(db);
    }

    public boolean insertArticle(String title, String date,String item/*description*/, String link) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_TITLE, title);
        contentValues.put(ITEM_DATE, date);        //revoir insertion d'une date
        contentValues.put(ITEM, item);
        contentValues.put(ITEM_LINK, link);

        db.insert(ITEMS_TABLE_NAME, null, contentValues);
        Log.i("BDD","nous venons d'insérer un article dans la base de donnée");
        return true;
    }

    public boolean insertNotif(String what, String where,String when) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIF_WHAT, what);
        contentValues.put(NOTIF_WHERE, where);        //revoir insertion d'une date
        contentValues.put(NOTIF_WHEN, when);

        db.insert(NOTIF_TABLE_NAME, null, contentValues);
        Log.i("BDD","nous venons d'insérer un article dans la base de donnée");
        return true;
    }

    public ArrayList<Article> getArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Article> articles= new ArrayList<Article>();
        Cursor result = db.rawQuery("select * from "+ITEMS_TABLE_NAME , null);
        while(result.moveToNext()){
            articles.add( new Article(result.getString(result.getColumnIndex(ITEM_TITLE)),
                                      result.getString(result.getColumnIndex(ITEM_DATE)),
                                      result.getString(result.getColumnIndex(ITEM)),
                                      result.getString(result.getColumnIndex(ITEM_LINK))
            ));
            /*articles.add(new Article(result.getString(result.getColumnIndex(ITEM))));*/
        }
        Log.i("BDD","nous sommes dans getData()");
        return articles;
    }

    public ArrayList<Notification> getNotifs() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Notification> notifs = new ArrayList<Notification>();
        Cursor result = db.rawQuery("select * from " + NOTIF_TABLE_NAME, null);
        while (result.moveToNext()) {
            notifs.add(new Notification(
                    Integer.parseInt(result.getString(0)),
                    result.getString(result.getColumnIndex(NOTIF_WHAT)),
                    result.getString(result.getColumnIndex(NOTIF_WHERE)),
                    result.getString(result.getColumnIndex(NOTIF_WHEN))));
        }
        Log.i("BDD", "nous sommes dans getData()");
        return notifs;
    }

    public boolean updateArticle(int id, String title, String date, String item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("i_title", title);
        contentValues.put("i_date", date);        //revoir insertion d'une date
        contentValues.put("i_item", item);

        db.update(ITEMS_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteArticle(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ITEMS_TABLE_NAME,
                "item_id = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteNotif(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NOTIF_TABLE_NAME,
                "notif_id = ? ",
                new String[]{Integer.toString(id)});
    }
}
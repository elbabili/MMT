package fr.mosquee_mirail_toulouse.mmt.DataBase;

/**
 * Created by moham on 23/10/2017.
 */

public class Article {

    private String title;
    private String date;
    private String item;



    private String link;

    public Article(String titre, String dte, String art, String link){
        setTitle(titre);
        setDate(dte);
        setItem(art);
        setLink(link);

    }

    public Article(String art){
        setItem(art);
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}

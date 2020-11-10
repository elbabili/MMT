package fr.mosquee_mirail_toulouse.mmt.Rss;

public class RssItem {
    String title;
    String description;
    String link;
    String imageUrl;

    String date;

    public RssItem(){
    }

    public RssItem(String title, String description, String link, String date){
        setTitle(title);
        setDescription(description);
        setLink(link);
        setDate(date);
    }

    public String getDate(){ return date;}
    public void setDate(String dte){ date = dte ;}

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}

package fr.mosquee_mirail_toulouse.mmt.DataBase;

/**
 * Created by moham on 13/12/2017.
 */

public class Notification {

    private int notifID;    // er : clé primaire
    private String what;    // ex : salat djanaza
    private String where;   // ex : Mosquée Basso
    private String when;    // ex : Demain Dohor

    public Notification(int notifID, String what, String where, String when) {
        setNotifID(notifID);
        setWhat(what);
        setWhere(where);
        setWhen(when);
    }

    public int getNotifID() { return notifID;  }
    public void setNotifID(int notifID) {  this.notifID = notifID;  }

    public String getWhat() {
        return what;
    }
    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }
    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhen() {
        return when;
    }
    public void setWhen(String when) {  this.when = when;  }
}


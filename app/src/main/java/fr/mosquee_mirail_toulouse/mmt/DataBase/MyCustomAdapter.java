package fr.mosquee_mirail_toulouse.mmt.DataBase;

/**
 * Created by moham on 23/10/2017.
 */

import android.content.Context;
import android.text.Html;

import android.text.Spanned;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.mosquee_mirail_toulouse.mmt.R;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class MyCustomAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Article> articles;

    public MyCustomAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);
        this.context = context;
        setArticles(objects);
    }

    private class ViewHolder {
        TextView itemTitle;
        TextView itemDate;
        TextView item;
    }

    public void setArticles(ArrayList objects){
        articles = objects;
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.article_details, null);

            holder = new ViewHolder();
            //holder.itemTitle = (TextView) convertView.findViewById(R.id.title);
            //holder.itemDate = (TextView) convertView.findViewById(R.id.date);
            holder.item     =(TextView)convertView.findViewById(R.id.item);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Article individualArt = articles.get(position);

        String str = shapeString(individualArt.getTitle(),individualArt.getDate(),individualArt.getItem());

        holder.item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kaaba, 0, 0, 0);
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.item.setText(Html.fromHtml(str, FROM_HTML_MODE_LEGACY));
        } else {
            holder.item.setText(Html.fromHtml(str));
        }*/
        Spanned sp = Html.fromHtml(str);
        holder.item.setText(sp);

        //holder.item.setText(Jsoup.parse(str).text());
        return convertView;
    }

    private String shapeString(String title, String dte, String item){
        String str = "";
        str += "<html> <b> <font color=\"#8D3457\">" + title.toUpperCase() + "</font> </b>  </html>" + "<br />";      // TITRE
        str +=  "<html> <font color=\"#94A834\">" + dte.substring(0,16) + "</font>  </html>" + "<br />";     // date
        str += item;                            //Contenu
        return str;
    }
}
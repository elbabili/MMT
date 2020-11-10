package fr.mosquee_mirail_toulouse.mmt.Rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class RssReader {
    private InputStream stream;
    private XmlPullParserFactory xmlFactoryObject;

    private List<RssItem> rssItemList;
    private RssItem currentItem = null;

    private String title, link, description, date;

    public RssReader(InputStream rssUrl) {
        stream = rssUrl;
        rssItemList = new ArrayList<RssItem>();
    }

    public List<RssItem> getItems() throws XmlPullParserException, IOException {
        xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myParser = xmlFactoryObject.newPullParser();

        myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        myParser.setInput(stream, null);

        parseXML(myParser);
        stream.close();

        return rssItemList;
    }

    public void parseXML(XmlPullParser myParser) {
        int event;
        String[] result = new String[5];
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG :
                        if (name.equals("item")) {
                            //currentItem = new RssItem();
                        } else if (name.equals("title")) {
                            title = myParser.nextText().toString();

                        } else if (name.equals("link")) {
                            link = myParser.nextText().toString();

                        } else if (name.equals("description")) {
                            description = myParser.nextText().toString();

                        } else if (name.equals("pubDate")) {
                            date = myParser.nextText().toString();
                        }
                        break;

                    /*case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;*/

                    case XmlPullParser.END_TAG:
                        if (name.equals("item")){
                            currentItem = new RssItem(title,description,link,date);
                            rssItemList.add(currentItem);
                        }
                        break;
                }
                event = myParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package fr.mosquee_mirail_toulouse.mmt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PrayerFragment extends Fragment {

    private TextView txtEditFajr;
    private TextView txtEditChourouk;
    private TextView txtEditDohor;
    private TextView txtEditAsr;
    private TextView txtEditMaghreb;
    private TextView txtEditIchaa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prayer,container,false);

        txtEditFajr = (TextView) rootView.findViewById(R.id.editFajr);
        txtEditChourouk = (TextView) rootView.findViewById(R.id.editChourouk);
        txtEditDohor = (TextView) rootView.findViewById(R.id.editDohor);
        txtEditAsr = (TextView) rootView.findViewById(R.id.editAsr);
        txtEditMaghreb = (TextView) rootView.findViewById(R.id.editMaghreb);
        txtEditIchaa = (TextView) rootView.findViewById(R.id.editIchaa);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        ArrayList<String> prayerTimes = PrayTime.DisplayPrayer(cal);
        int size = prayerTimes.size();

        for (int i = 0; i < size ; i++) {
            switch(i){
                case 0 : txtEditFajr.setText(prayerTimes.get(i));
                    break;

                case 1 : txtEditChourouk.setText(prayerTimes.get(i));
                    break;

                case 2 : txtEditDohor.setText(prayerTimes.get(i));
                    break;

                case 3 : txtEditAsr.setText(prayerTimes.get(i));
                    break;

                case 4 : //sunset non traité ici => coucher de soleil !
                    break;

                case 5 : txtEditMaghreb.setText(prayerTimes.get(i));
                    break;

                case 6 : txtEditIchaa.setText(prayerTimes.get(i));
                    break;

                default: System.out.println("on ne devrait pas arriver ici");       //revoir
            }
        }
        return rootView;
    }
}

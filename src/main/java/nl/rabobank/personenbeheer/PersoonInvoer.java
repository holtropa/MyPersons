package nl.rabobank.personenbeheer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.text.CollationKey;
import java.time.LocalDate;
import java.util.*;

import static java.util.Collections.sort;


class PersoonInvoer {


    static List<Persoon> personen = new ArrayList();



    PersoonInvoer() {

    }

    Persoon addPersoon(Persoon persoon) {
        if (persoon != null) {
            personen.add(persoon);
        }
        return persoon;
    }

    static int bepaalMaxId() {
        int maxid = 0;

        for (Persoon persoon : personen) {
            int id = persoon.getId();
            if (maxid <= persoon.getId()) {
                maxid = ++id;
            }
        }
        return maxid;
    }

    static List<Persoon> sortByAchterNaamAscending(List<Persoon> personen) {
        List<Persoon> list = new ArrayList<>(personen);
//        sort(personen, new Comparator<Persoon>() {
        personen.sort(new Comparator<Persoon>() {
            @Override
            public int compare(Persoon persoon1, Persoon persoon2) {
                return persoon1.getAchterNaam().compareTo(persoon2.getAchterNaam());
            }
        });
        return list;
    }
}






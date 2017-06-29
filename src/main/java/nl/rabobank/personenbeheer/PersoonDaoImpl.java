package nl.rabobank.personenbeheer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersoonDaoImpl implements PersoonDao {

    //list is working as a database
    List<Persoon> Persoons;

    public PersoonDaoImpl(){
        Persoons = new ArrayList<Persoon>();
        Persoon Persoon1 = new Persoon("Anne", LocalDate.of(1965, 12, 12));
        Persoon Persoon2 = new Persoon("Piet", LocalDate.of(1965, 12, 12));
        Persoons.add(Persoon1);
        Persoons.add(Persoon2);
    }
    //   @Override
    public void deletePersoon(Persoon Persoon) {
        Persoons.remove(Persoon.getId());
        System.out.println("Persoon: IdNo " + Persoon.getId() + ", deleted from database");
    }

    //retrive list of Persoons from the database
//    @Override
    public List<Persoon> getAllPersonen() {
        return Persoons;
    }

    //   @Override
    public Persoon getPersoon(int rollNo) {
        return Persoons.get(rollNo);
    }

    //   @Override
    public void updatePersoon(Persoon Persoon) {
        Persoons.get(Persoon.getId()).setAchterNaam(Persoon.getAchterNaam());
        System.out.println("Persoon: IdNo " + Persoon.getId() + ", updated in the database");
    }
}


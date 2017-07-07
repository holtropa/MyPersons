package nl.rabobank.personenbeheer;

import java.util.List;

public interface PersoonDao {
    public  List<Persoon> getAllPersonen();
    public Persoon getPersoon(int id);
    public Persoon updatePersoon(Persoon persoon);
    public void deletePersoon(Persoon persoon);
}

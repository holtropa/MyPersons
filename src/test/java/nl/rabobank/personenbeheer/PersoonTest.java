package nl.rabobank.personenbeheer;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static nl.rabobank.personenbeheer.PersoonDaoImpl.personen;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PersoonTest {

    @Test
    public void it_should_return_an_existing_person() {
        Persoon persoon = new Persoon("Anne", LocalDate.of(1965, 12, 12));
        PersoonInvoer invoer = new PersoonInvoer();
        Persoon vindPersoon = invoer.addPersoon(persoon);
        vindPersoon.compareTo(persoon);

        assertThat(vindPersoon).isNotNull();
    }

    @Test
    public void it_should_return_the_first_person() {
        Persoon persoon = new Persoon("Piet", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon);
        Persoon persoon2 = new Persoon("Anne", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon2);
        Persoon persoon3 = new Persoon("Siert", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon3);
        PersoonInvoer.sortByAchterNaamAscending(PersoonInvoer.personen);

        assertThat(PersoonInvoer.personen.get(0).getAchterNaam().contains("Anne"));
        assertEquals(1,PersoonInvoer.personen.get(0).getId());
    }

    @Test
    public void write_a_person_to_dbase() {
        WritePersoonToDbase writePersoonToDbase = new WritePersoonToDbase();
        Persoon persoon = new Persoon("Piet", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon);
        int itId=persoon.getId();
        writePersoonToDbase.storePersoon(persoon);
        int zoekId = writePersoonToDbase.selectPersoon(persoon);
        assertEquals(zoekId,PersoonInvoer.personen.get(0).getId());
        writePersoonToDbase.removePersoon(persoon);
    }

    @Test
    public void write_a_person_to_dbase_via_DAO() {

        Persoon persoon = new Persoon("Anika", LocalDate.of(1966, 11, 11));
        PersoonInvoer invoer = new PersoonInvoer();
        Persoon newPersoon = invoer.addPersoon(persoon);

        PersoonDaoImpl persoonDaoImpl = new PersoonDaoImpl();

        int newId = persoon.getId();
        Persoon zoekPersoon = persoonDaoImpl.getPersoonOpNaam("Anika");

        assertEquals(newPersoon.getAchterNaam(), zoekPersoon.getAchterNaam());

        persoonDaoImpl.updatePersoon(persoon);

        Persoon zoekupdatepersoon = persoonDaoImpl.getPersoon(newId);

        assertEquals("Utrecht", zoekupdatepersoon.getPlaatsNaam());

        persoonDaoImpl.deletePersoon(zoekupdatepersoon);

        Persoon vindDeletedPersoon = persoonDaoImpl.getPersoon(newId);

        assertNull(vindDeletedPersoon.getAchterNaam());

    }

    @Test
    public void write_a_personen_list_from_dbase_via_DAO() {

       PersoonDaoImpl persoonDaoImpl = new PersoonDaoImpl();
       List<Persoon> list = new ArrayList<>(persoonDaoImpl.getAllPersonen());
        for (Persoon persoon : list) {
            System.out.println("Dit is een persoons record: " + persoon.toString());
        }

        assertNotNull(list.get(0));

    }
}


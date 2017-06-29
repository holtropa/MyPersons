package nl.rabobank.personenbeheer;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PersoonTest {

    @Test
    public void it_should_return_an_existing_person() {
        Persoon persoon = new Persoon();
        persoon.setAchterNaam("Anne");
        persoon.setGeboorteDat(1965, 12, 12);
        PersoonInvoer invoer = new PersoonInvoer();
        Persoon vindPersoon = invoer.addPersoon(persoon);//
        vindPersoon.compareTo(persoon);

     ///   assertThat(vindPersoon).isNotNull();
    }

    @Test
    public void it_should_return_the_first_person() {
        Persoon persoon = new Persoon("Piet", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon);
        Persoon persoon2 = new Persoon("Anne", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon2);
        Persoon persoon3 = new Persoon("Henk", LocalDate.of(1965, 12, 12));
        PersoonInvoer.personen.add(persoon3);
        //

        PersoonInvoer.sortByAchterNaamAscending(PersoonInvoer.personen);

        assertThat(PersoonInvoer.personen.get(0).getAchterNaam().contains("Anne"));
        assertEquals(1,PersoonInvoer.personen.get(0).getId());
    }
}


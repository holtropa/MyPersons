package nl.rabobank.personenbeheer;

public class DaoPatternDemo {
    public static void main(String[] args) {
        PersoonDao PersoonDao = new PersoonDaoImpl();

        //print all Persoons
        for (Persoon Persoon : PersoonDao.getAllPersonen()) {
            System.out.println("Persoon: [IdNo : " + Persoon.getId() + ", Achternaam : " + Persoon.getAchterNaam() + " ]");
        }


        //update Persoon
        Persoon Persoon =PersoonDao.getAllPersonen().get(0);
        Persoon.setAchterNaam("Michael");
        PersoonDao.updatePersoon(Persoon);

        //get the Persoon
        PersoonDao.getPersoon(0);
        System.out.println("Persoon: [IdNo : " + Persoon.getId() + ", Achternaam : " + Persoon.getAchterNaam() + " ]");
    }
}
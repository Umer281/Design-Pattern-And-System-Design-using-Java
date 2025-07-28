public class Main {
    public static void main(String[] args) {
        Pizza margerattta = new BasePizza();
        System.out.println(margerattta.getDescription());
        System.out.println(margerattta.getCost());
        margerattta = new CheezDecorator(margerattta);
        System.out.println(margerattta.getCost());
        margerattta = new CornDecorator(margerattta);
        margerattta = new PepproniDecorator(margerattta);
        System.out.println(margerattta.getCost());

    }
}
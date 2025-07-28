public class BasePizza implements Pizza {

    @Override
    public String getDescription() {
        return "Base Pizza";
    }

    @Override
    public double getCost() {
        return 100.0;
    }
}

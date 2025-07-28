public class PepproniDecorator extends ToppingDecorator{


    PepproniDecorator(Pizza pizza){
        super(pizza);
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + "Pepricorn pizza";
    }
}

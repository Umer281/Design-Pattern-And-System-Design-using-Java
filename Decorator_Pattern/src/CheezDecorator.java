public class CheezDecorator extends ToppingDecorator {


    CheezDecorator(Pizza pizza){
        super(pizza);
    }


    public String getDescription(){
        return pizza.getDescription() + "Cheese topping";
    }

    public double getCost(){
        return pizza.getCost() + 10.0;
    }


}

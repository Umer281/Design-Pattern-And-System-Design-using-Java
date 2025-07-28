public class CornDecorator extends ToppingDecorator {

  CornDecorator(Pizza pizza) {
       super(pizza);
  }

    public String getDescription (){
        return pizza.getDescription() + "Corn topping";
    }

    public double getCost(){
        return pizza.getCost() + 12.0;
    }

}

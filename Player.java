import java.util.UUID;

public class Player{
  
  private String name;
  private double cash;
  private UUID   id = UUID.randomUUID();
  
  public Player(String name, double cash){
    this.name = name;
    this.cash = cash;
  }
  
  public double bet(double amount){
    this.cash -= amount;
    return amount;
  }
  public String getName() {
	  return name;
  }
  public double getCash() {
	  return cash;
  }
}
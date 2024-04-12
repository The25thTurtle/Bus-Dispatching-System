import java.util.Scanner;
import java.util.ArrayList;

public class Assignment2 {
  public static void main(String []args) {
    Scanner input = new Scanner
    (System.in);

    Dispatcher dispatcher = new Dispatcher();
    
    System.out.println("Bus Dispatching System");
    System.out.println("");
    
    while (true) {
      System.out.println("1. Add bus");
      System.out.println("2. Add Person to bus");
      System.out.println("3. Remove bus");
      System.out.println("4. Remove person");
      System.out.println("5. List passengers");
      System.out.println("6. List busses");
      System.out.println("7. Requeue bus");
      System.out.println("8. Transfer person");
      System.out.println("9. Dispatch bus");
      System.out.println("0. Exit");

      int choice = input.nextInt();

      if (choice != 0) {
        if (choice == 1) {
          Bus bus1 = new Bus();
          int position1 = dispatcher.addBus(bus1);
          System.out.println("Bus " + bus1.getId() + " added to position " + position1);
        }
        else if (choice == 2) {
          System.out.print("Enter bus id: ");
          int busId = input.nextInt();

          Bus bus = dispatcher.findBus(busId);

          if (bus == null) {
            System.out.println("No bus with id " + busId);
          }
          else {
            System.out.print("Enter person's name: ");
            String name = input.next();

            Person person = new Person(name);
            bus.addPerson(person);

            System.out.println(name + " has been added to bus " + busId);
          }
        }
        else if (choice == 3) {
          System.out.print("Enter bus id: ");
          int busId = input.nextInt();

          Bus removedBus = dispatcher.findBus(busId);

          if (removedBus != null) {
            dispatcher.removeBus(busId);
            System.out.println("Bus " + removedBus.getId() + " removed");
          } 
          else {
            System.out.println("No bus with id " + busId);
          }
        }
        else if (choice == 4) {
          System.out.print("Enter bus id: ");
          int busId = input.nextInt();
          Bus bus = dispatcher.findBus(busId);
          
          if (bus != null) {
            System.out.print("Enter person's name: ");
            String name = input.next();

            Person removedPerson = bus.findPerson(name);

            if (removedPerson != null) {
              bus.removePerson(removedPerson);
              System.out.println(name + " has been removed from bus " + busId);
            } 
            else {
              System.out.println("No such person found in bus " + busId);
            }
          } 
          else {
            System.out.println("No bus with id " + busId);
          }
        }
        else if (choice == 5) {
          System.out.print("Enter bus id: ");
          int busId = input.nextInt();

          Bus bus = dispatcher.findBus(busId);

          if (bus != null) {
            System.out.println("Bus " + busId);
            System.out.println(bus.getPassengers());
          }
          else {
            System.out.println("No bus with id " + busId);
          }
        }
        else if (choice == 6) {
          System.out.println("BUS QUEUE");
          System.out.println(dispatcher.toString());
        }
        else if (choice == 7) {
          System.out.print("Enter bus id: ");
          int busIdRequeue = input.nextInt();

          Bus busRequeue = dispatcher.findBus(busIdRequeue);

          if (busRequeue == null) {
            System.out.println("No bus with ID " + busIdRequeue);
          } 
          else {
            System.out.print("Enter new bus position: ");
            int newPosition = input.nextInt();

            int Actual_Position = dispatcher.addBus(busRequeue, newPosition);

            System.out.println("Bus " + busIdRequeue + " added to position " + Actual_Position);
    }

        }
        else if (choice == 8) {
          System.out.print("Enter person's name: ");
          String Name = input.next();
          System.out.print("Enter id of bus 1: ");
          int StartBusId = input.nextInt();
          System.out.print("Enter id of bus 2: ");
          int EndBusId = input.nextInt();

          Bus StartBus = dispatcher.findBus(StartBusId);
          Bus EndBus = dispatcher.findBus(EndBusId);

          if (StartBus == null) {
            System.out.println("No bus with ID " + StartBusId);
          } 
          else if (EndBus == null) {
            System.out.println("No bus with ID " + EndBusId);
          } 
          else {
            Person person = StartBus.findPerson(Name);
            if (person == null) {
            System.out.println("No person named " + Name);
              } 
            else {
              boolean transferSuccess = Bus.transferPerson(StartBus, EndBus, person);
              if (transferSuccess) {
                System.out.println("Person transferred successfully.");
            } else {
                System.out.println("Person transfer failed.");
            }
        }
    }
}
        }
        else if (choice == 9) {
          Bus dispatchedBus = dispatcher.dispatchBus();
          if (dispatchedBus != null) {
            System.out.println("Bus " + dispatchedBus.getId() + " has been dispatched.");
          } 
          else {
            System.out.println("Bus queue is empty");
          }
        } // Some reason it doesn't want to dispatch the bus for me I tried to fix but can't get it right.
          
        else {
          System.out.println("Shutting down.");
          input.close();
          System.exit(0);
      }
    }
  }
}

class Person {
  private String name;
  private int ticketNumber;
  private static int nextTicketNumber = 1;

  public Person () {
    this.name = "NO NAME";
    this.ticketNumber = nextTicketNumber;
    nextTicketNumber++;
  }

  public Person ( String name) {
    this.name = name;
    this.ticketNumber = nextTicketNumber;
    nextTicketNumber++;
  }
  public String getName () {
    return name;
  }

  public String toString () {
    return "" +ticketNumber+ "\t" +name;
  }
}

class Bus {
  private ArrayList<Person> passengers = new ArrayList<>();
  private static int nextId = 0;
  private int busId;

  public Bus () {
    this.busId = nextId;
    nextId++;
    this.passengers = new ArrayList<>();
  }

  public int getId () {
    return busId;
  }

  public void addPerson (Person person) {
    passengers.add(person);
  }

  public boolean removePerson (Person person) {
    return passengers.remove(person);
  }

  public Person findPerson (String name) {
    for (Person person  : passengers) {
      if (person.getName().equals(name)) {
        return person;
      }
    }
    return null;
  }
  
  public static boolean transferPerson (Bus bus1, Bus bus2, Person person) {
    if (bus1.removePerson(person)) {
      bus2.addPerson(person);
      return true;
    }
    else {
      return false;
    }
  }
  
  public String getPassengers () {
    StringBuilder result = new StringBuilder();
    for (Person person : passengers) {
      result.append(person.toString()).append("\n");
    }
    return result.toString();
  }

  public String toString () {
    return String.valueOf(busId);
  }
}

class Dispatcher {
  public ArrayList<Bus> busQueue = new ArrayList<>();

  public Dispatcher() {
        busQueue = new ArrayList<>();
    }

  public int addBus (Bus bus) {
    busQueue.add(bus);
    int position = busQueue.size() - 1;
    return position;
  }

  public int addBus (Bus bus, int position) {
    int Actual_Position;

    if (position < 0 || position > busQueue.size()) {
      busQueue.add(bus);
      Actual_Position = busQueue.size() - 1;
    }
    else {
      busQueue.add(position, bus);
      Actual_Position = position;
    }
    return Actual_Position;
  }

  public Bus findBus (int id) {
    for (Bus bus : busQueue) {
      if (bus.getId() == id) {
        return bus;
      }
    }
    return null;
  }

  public Bus removeBus (int id) {
    for (int i = 0; i < busQueue.size(); i++) {
      Bus bus = busQueue.get(i);
      if (bus.getId() == id) {
        busQueue.remove(i);
        return bus;
      }
    }
    return null;
  }

  public Bus dispatchBus () {
    if (busQueue.isEmpty()) {
      return null;
    }
    else {
      return busQueue.remove(0);
    }
  }

  public String toString () {
    StringBuilder result = new StringBuilder();
    for (Bus bus : busQueue) {
      result.append(bus.toString()).append("\n");
    }
    return result.toString();
  }
}

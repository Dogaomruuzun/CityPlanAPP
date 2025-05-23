package observer;
//observrable
public interface Subject {
    void registerObserver(Observer o); //Adds a new observer to the system.
    void removeObserver(Observer o); //Removes a previously added observer from the system.
    void notifyObservers(); //update method
}

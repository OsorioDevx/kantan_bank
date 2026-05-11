/*INTERFACE: OPERABLE
Starting the development from the interface-first approach, IntelliJ will not complain if you structure the project this way.

What is this interface ---------------------------------------
This is not a graphical interface; it is part of the Object-Oriented Programming concepts of inheritance and polymorphism.
An interface defines a contract: “every class implementing this interface MUST provide these methods.”
Unlike an abstract class, an interface does not store state (attributes) and can be implemented by multiple classes.
It represents capabilities or behaviors: “Can this account perform banking operations?”

Why use it in this project? ----------------------------------------------
Because it guarantees that EVERY account type will support operations such as deposit, withdrawal, and account statements. Any class implementing the interface is required to fulfill these rules.
The interface also enables polymorphism: all account types can be treated simply as Operable, without needing to know their concrete implementation.

* */

public interface Operable {
    /* Deposits a value into the account.
     * @param value primitive double (must be non-negative)
     * @return true if the deposit was successful, false otherwise
     */
    boolean deposit(double value);

    /*
     * Withdraws a value from the account.
     * @param value the amount to withdraw
     * @return true if the withdrawal was successful, false otherwise
     */
    boolean withdraw(double value);

    /*
     * Transfers a value to another account.
     * @param value -> the amount to transfer
     * @param destination reference to another Account object
     * @return true if the transfer was successful, false otherwise
     */
    boolean transfer(double value, Account destination);

    /*
     * Shows the complete account statement.
     * All interface methods are implicitly public and abstract.
     */
    void showStatement();
}

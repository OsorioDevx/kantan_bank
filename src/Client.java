/*CLASS: CLIENT
Concepts applied:
--> Simple concrete class (no inheritance or abstraction)
--> Encapsulation implemented through private attributes
--> Custom constructor
--> Primitive type: int (`age`)
--> Reference types: String (`name`, `cpf`)
--> Overridden `toString()` method
*        */




public class Client {
    /*/*
     * Below are the PRIVATE attributes mentioned in the class header ----------
     * String is a non-primitive (reference) type because it is a class, not a primitive data type.
     */
    private String name;
    private String cpf;

    /* Now, int is a primitive type because its value is stored directly in memory. */
    private int age;

    /* CUSTOM CONSTRUCTOR ----------
     * Why use a parameterized constructor?
     * -> It guarantees that no Customer object can be created without a name, CPF, and age
     * -> The object is instantiated already complete and in a valid state because these required fields are enforced at creation time*/
    public Client(String name, String cpf, int age) {
        this.name  = name;   // 'this' diferencia o atributo do parâmetro
        this.cpf   = cpf;
        this.age = age;
    }

    /* Getter methods only, no setters, because the client data should not change */
    public String getName()  { return name; }
    public String getCpf()   { return cpf; }
    public int getAge() { return age; }

    /*toString()
   /*
    * @Override: informs the compiler that this method overrides
    * a method from the superclass (Object, the root class of all Java classes).*/
    @Override
    public String toString() {
        return String.format("Cliente: %-20s / CPF: %s / Idade: %d anos",
                name, cpf, age);
    }

}

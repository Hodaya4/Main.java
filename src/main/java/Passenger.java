public class Passenger {
    private int passengerId;
    private int survived;
    private int pClass;
    private String name;
    private String sex;
    private int age;
    private int sibSp;
    private int parch;
    private String ticket;
    private float fare;
    private String cabin;
    private char embarked;

    public Passenger(int passengerId, int survived, int pClass, String name, String sex, int age,
                     int sibSp, int parch, String ticket, float fare, String cabin, char embarked) {
        this.passengerId = passengerId;
        this.survived = survived;
        this.pClass = pClass;
        this.name = getFormattedName(name);
        this.sex = sex;
        this.age = age;
        this.sibSp = sibSp;
        this.parch = parch;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
    }

    private static String getFormattedName(String name) {
        String[] nameArray = name.split(" ");
        String lastName = nameArray[0].substring(0,nameArray[0].length() - 1);
        String firstName = "";
        for (int i = 2; i < nameArray.length; i++) {
            firstName = firstName.concat(" " + nameArray[i]);
        }
        String fullName = firstName.concat(" " + lastName);
        return fullName;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", survived=" + survived +
                ", pClass=" + pClass +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", sibSp=" + sibSp +
                ", parch=" + parch +
                ", ticket='" + ticket + '\'' +
                ", fare=" + fare +
                ", cabin='" + cabin + '\'' +
                ", embarked=" + embarked +
                '}';
    }
}

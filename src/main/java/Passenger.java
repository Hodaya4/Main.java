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


    public boolean passengerInRange(String minRange, String maxRange) {
        System.out.println("try");
        try {
            if (maxRange.equals(null) && minRange.equals(null)) {
                return true;
            }
            int min = Constants.MIN;
            int max = Constants.MAX;
            if (!minRange.equals("")) {
                min = Integer.parseInt(minRange);
            }
            if (!maxRange.equals("")) {
                max = Integer.parseInt(maxRange);
            }
            return (this.passengerId <= max && this.passengerId >= min);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean containsName(String name) {
        System.out.println("try");
        try {
            if (name.equals(null)) {
                return true;
            }
            return (this.name.contains(name));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sibSPMatch(String num) {
        System.out.println("try");
        try {
            if (num.equals(null)) {
                return true;
            }
            int num2 = Integer.parseInt(num);
            return this.sibSp == num2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean parchMatch(String num) {
        System.out.println("try");
        try {
            if (num.equals(null)) {
                return true;
            }
            int num2 = Integer.parseInt(num);
            return this.parch == num2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean ticketNumMatch(String num) {
        System.out.println("try");
        try {
            if (num.equals(null)) {
                return true;
            }
            int num2 = Integer.parseInt(num);
            String temp = "";
            for (int i = 0; i < this.ticket.length(); i++) {
                if (Character.isDigit(this.ticket.charAt(i))) {
                    temp += this.ticket.charAt(i);
                }
            }
            int ticketNum = Integer.parseInt(temp);
            return ticketNum == num2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean ticketFareMatch(String min, String max) {
        System.out.println("try");
        try {
            if (min.equals(null) && max.equals(null)) {
                return true;
            }
            float minimum = Float.parseFloat(min);
            float maximum = Float.parseFloat(max);
            return this.fare >= minimum && this.fare <= maximum;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean cabinMatch(String cabin) {
        System.out.println("try");
        try {
            if (cabin.equals(null)) {
                return true;
            }
            String temp = "";
            for (int i = 0; i < this.cabin.length(); i++) {
                if (Character.isDigit(this.cabin.charAt(i))) {
                    temp += this.cabin.charAt(i);
                }
            }
            int cabinNum = Integer.parseInt(temp);
            return cabinNum == Integer.parseInt(cabin);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean filterClass(String pClass) {
        System.out.println("try");
        if (pClass.equals(null)) {
            return true;
        }
        switch (pClass) {
            case Constants.ALL:
                return true;
            case Constants.FIRST:
                if (this.pClass == Constants.FIRST_INT) {
                    return true;
                } break;
            case Constants.SECOND:
                if (this.pClass == Constants.SECOND_INT) {
                    return true;
                } break;
            case Constants.THIRD:
                if (this.pClass == Constants.THIRD_INT) {
                    return true;
                } break;
        } return false;
    }

    public boolean filterSex(String sex) {
        System.out.println("try");
        try {
            if (sex.equals(null)) {
                return true;
            }
            return sex.equals(Constants.ALL) || sex.equals(this.sex);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean filterEmbarked(String embarked) {
        System.out.println("try");
        if (embarked.equals(null)) {
            return true;
        }
        String thisEmbarked = this.embarked + "";
        return embarked.equals(Constants.ALL) || embarked.equals(thisEmbarked);
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

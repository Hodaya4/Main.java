import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainPanel extends JPanel {
    private JComboBox<String> classComboBox;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> embarkedComboBox;
    private String[] valuesOfText;
    private String valueClass;
    private String valueSex;
    private String valueEmbarked;
    private int fileNumber = 0;


    public MainPanel (int x, int y, int width, int height) {
        this.setLayout(null);
        this.setBounds(x, y + Constants.MARGIN_FROM_TOP, width, height);


        File file = new File(Constants.PATH_TO_DATA_FILE); //this is the path to the data file
        ArrayList<Passenger> passengerData = new ArrayList<>();
        try {

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    passengerData.add(convert(line));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.classComboBox = new JComboBox<>(Constants.CLASS_OPTIONS);
        this.classComboBox.setBounds(Constants.LABEL_WIDTH + 1, y,
                Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.classComboBox);

        this.sexComboBox = new JComboBox<>(Constants.SEX_OPTIONS);
        this.sexComboBox.setBounds(Constants.LABEL_WIDTH + 1, y + Constants.MARGIN_Y,
                Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.sexComboBox);

        this.embarkedComboBox = new JComboBox<>(Constants.EMBARKED_OPTIONS);
        this.embarkedComboBox.setBounds(Constants.LABEL_WIDTH + 1, this.sexComboBox.getY() + Constants.MARGIN_Y,
                Constants.COMBO_BOX_WIDTH, Constants.COMBO_BOX_HEIGHT);
        this.add(this.embarkedComboBox);

        JTextField[] textFields = new JTextField[Constants.TEXT_FIELDS];

        int textField_y = 3 * Constants.MARGIN_Y;
        for (int i = 0; i < Constants.TEXT_FIELDS; i++) {
            JTextField jTextField = new JTextField();
            jTextField.setBounds(Constants.TEXT_FIELD_X, textField_y, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
            this.add(jTextField);
            textFields[i] = jTextField;
            textField_y += Constants.MARGIN_Y;
        }

        String[] textLabels = {"Passenger Class:", "Passenger Sex:", "Embarked:", "Passenger Num Range, Min:", "Passenger Num Range, Max:",
                "Passenger Name:", "Siblings/Spouses:", "Children/Parents:", "Ticket Number:", "Ticket Fare, Min:", "Ticket Fare, Max:", "Cabin:"};

        for (int j = 0; j < textLabels.length; j++) {
            JLabel jLabel = new JLabel(textLabels[j]);
            jLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(jLabel);
            y += Constants.MARGIN_Y;
        }

        JButton filter = new JButton("Filter");
        filter.setBounds(Constants.FILTER_X, Constants.FILTER_Y, Constants.FILTER_WIDTH, Constants.FILTER_HEIGHT);
        this.add(filter);

        JLabel result = new JLabel();
        result.setBounds(Constants.RESULT_X, Constants.RESULT_Y, Constants.RESULT_WIDTH, Constants.RESULT_HEIGHT);
        Font resFont = new Font("resFont", Font.ITALIC, 16);
        result.setFont(resFont);
        this.add(result);

        //String valueNumRangeMin = "";
        //String valueNumRangeMax = "";
        //String valueName = "";
        //String valueSibSP = "";
        //String valueParch = "";
        //String valueTicketNum = "";
        //String valueTicketFareMin = "";
        //String valueTicketFareMax = "";
        //String valueCabin = "";
        this.valuesOfText = new String[9];

        filter.addActionListener((e) -> {
            this.valueClass = this.classComboBox.getSelectedItem().toString();
            this.valueSex = this.sexComboBox.getSelectedItem().toString();
            this.valueEmbarked = this.embarkedComboBox.getSelectedItem().toString();
            for (int i = 0; i < textFields.length; i++) {
                this.valuesOfText[i] = textFields[i].getText();
            }
            List<Passenger> filteredList = filterResult(passengerData);
            List<Passenger> survivedList = filteredList.stream().filter(passenger -> passenger.getSurvived() == Constants.SURVIVED)
                    .collect(Collectors.toList());
            int deceased = filteredList.size() - survivedList.size();

            result.setText("Total rows: " + filteredList.size() + " ( " + survivedList.size() + " survived, " + deceased + " did not )");

            try {
                String nameFile = "C:\\Users\\Hodaya\\Desktop\\titanic-master\\src\\main\\resources\\fileNumber.txt";
                String nameNum = readFromFile(nameFile);
                System.out.println(nameNum);
                int name = Integer.parseInt(nameNum);
                name++;
                nameNum = name + "";
                File newFile = new File(nameFile);
                newFile.delete();
                writeToFile(nameFile, nameNum);
                String path = new String("C:\\Users\\Hodaya\\Desktop\\titanic-master\\fileWriter\\" + nameNum + ".csv");
                String columns = "PassengerId,Survived,Pclass,Name,Sex,Age,SibSp,Parch,Ticket,Fare,Cabin,Embarked";

                for (Passenger passenger : filteredList) {
                    String line = passenger.toString();
                    writeToFile(line, path);


                }

                //writeToFile(, path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            System.out.println(filteredList.size());

        });

        this.classComboBox.addActionListener((e) -> {
            this.valueClass = classComboBox.getSelectedItem().toString();
        });
        this.sexComboBox.addActionListener((e) -> {
            this.valueSex = sexComboBox.getSelectedItem().toString();
        });
        this.embarkedComboBox.addActionListener((e) -> {
            this.valueEmbarked = embarkedComboBox.getSelectedItem().toString();
        });



    }


    public List<Passenger> filterResult(ArrayList<Passenger> passengerData) {
        return passengerData.stream().filter(passenger -> passenger.passengerInRange(this.valuesOfText[0], this.valuesOfText[1])).filter(passenger
                -> passenger.containsName(this.valuesOfText[2])).filter(passenger -> passenger.sibSPMatch(this.valuesOfText[3])).filter(passenger ->
                passenger.parchMatch(this.valuesOfText[4])).filter(passenger -> passenger.ticketNumMatch(this.valuesOfText[5])).filter(passenger ->
                passenger.ticketFareMatch(this.valuesOfText[6], this.valuesOfText[7])).filter(passenger ->
                passenger.cabinMatch(this.valuesOfText[8])).filter(passenger -> passenger.filterClass(this.valueClass)).filter(passenger ->
                passenger.filterSex(this.valueSex)).filter(passenger -> passenger.filterEmbarked(this.valueEmbarked)).collect(Collectors.toList());
        //List<Passenger> idRangeFilter = passengerData.stream().filter(passenger -> passenger.passengerInRange(this.valuesOfText[0], this.valuesOfText[1])).collect(Collectors.toList());
        //List<Passenger> nameFilter = idRangeFilter.stream().filter(passenger -> passenger.containsName(this.valuesOfText[2])).collect(Collectors.toList());
        //List<Passenger> sibsFilter = nameFilter.stream().filter(passenger -> passenger.sibSPMatch(this.valuesOfText[3])).collect(Collectors.toList());
        //List<Passenger> parchFilter = sibsFilter.stream().filter(passenger -> passenger.parchMatch(this.valuesOfText[4])).collect(Collectors.toList());
        //List<Passenger> ticketNumFilter = parchFilter.stream().filter(passenger -> passenger.ticketNumMatch(this.valuesOfText[5])).collect(Collectors.toList());
        //List<Passenger> ticketFareFilter = ticketNumFilter.stream().filter(passenger -> passenger.ticketFareMatch(this.valuesOfText[6], this.valuesOfText[7])).collect(Collectors.toList());
        //List<Passenger> cabinFilter = ticketFareFilter.stream().filter(passenger -> passenger.cabinMatch(this.valuesOfText[8])).collect(Collectors.toList());
        //List<Passenger> sexFilter = cabinFilter.stream().filter(passenger -> passenger.filterSex(this.valueSex)).collect(Collectors.toList());
        //List<Passenger> embarkedFilter = sexFilter.stream().filter(passenger -> passenger.filterEmbarked(this.valueEmbarked)).collect(Collectors.toList());
        //List<Passenger> classFilter = embarkedFilter.stream().filter(passenger -> passenger.filterClass(this.valueClass)).collect(Collectors.toList());


        //return classFilter;


    }


    public static void writeToFile(String text, String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readFromFile(String path) {
        String text = null;
        try {
            if (new File(path).exists()) {
                Scanner scanner = new Scanner(new File(path));
                if (scanner.hasNextLine()) {
                    text = scanner.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }


    private static Passenger convert(String data) {
        String[] dataToArr = data.split(",");
        int PassengerId = Integer.parseInt(dataToArr[0]);
        int survived = Integer.parseInt(dataToArr[1]);
        int pClass = Integer.parseInt(dataToArr[2]);
        String name = (dataToArr[3].concat(dataToArr[4]));
        String sex = (dataToArr[5]);
        int age = Constants.EMPTY;
        try {
            age = Integer.parseInt(dataToArr[6]);
        } catch (Exception e) {
        }
        int sibSp = Integer.parseInt(dataToArr[7]);
        int parch = Integer.parseInt(dataToArr[8]);
        String ticket = (dataToArr[9]);
        float fare = Float.parseFloat(dataToArr[10]);
        String cabin = (dataToArr[11]);
        char embarked = ' ';
        try {
            embarked = (dataToArr[12].charAt(0));
        } catch (Exception e) {
        }

        Passenger passenger = new Passenger(PassengerId, survived, pClass, name, sex,
                age, sibSp, parch, ticket, fare, cabin, embarked);

        return passenger;
    }

}
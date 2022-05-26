import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainPanel extends JPanel {
    private JComboBox<String> classComboBox;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> embarkedComboBox;
    private static final int EMPTY = 0;


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

        int textField_y = 3 * Constants.MARGIN_Y;
        for (int i = 0; i < Constants.TEXT_FIELDS; i++) {
            JTextField jTextField = new JTextField();
            jTextField.setBounds(Constants.TEXT_FIELD_X, textField_y, Constants.TEXT_FIELD_WIDTH, Constants.TEXT_FIELD_HEIGHT);
            this.add(jTextField);
            textField_y += Constants.MARGIN_Y;
        }

        String[] textLabels = {"Passenger Class:", "Passenger Sex:", "Embarked:", "Passenger Number Range:", "Passenger Name:",
                "Siblings/Spouses:", "Children/Parents:", "Ticket Number:", "Ticket Fare:", "Cabin:"};

        for (int j = 0; j < textLabels.length; j++) {
            JLabel jLabel = new JLabel(textLabels[j]);
            jLabel.setBounds(x + Constants.MARGIN_FROM_LEFT, y, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
            this.add(jLabel);
            y += Constants.MARGIN_Y;
        }

        this.classComboBox.addActionListener((e) -> {
            //String value = classComboBox.getSelectedItem().toString();



        });



    }

    
    private static Passenger convert(String data) {
        String[] dataToArr = data.split(",");
        int PassengerId = Integer.parseInt(dataToArr[0]);
        int survived = Integer.parseInt(dataToArr[1]);
        int pClass = Integer.parseInt(dataToArr[2]);
        String name = (dataToArr[3].concat(dataToArr[4]));
        String sex = (dataToArr[5]);
        int age = EMPTY;
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

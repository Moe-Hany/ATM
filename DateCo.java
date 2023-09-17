import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class DateCo extends JPanel {
    private JComboBox<Integer> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;

    public DateCo() {
        setLayout(new FlowLayout());

        // Day ComboBox
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }

        // Month ComboBox
        monthComboBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });

        // Year ComboBox
        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= currentYear - 100; i--) {
            yearComboBox.addItem(i);
        }

        // Remove the background image
        setOpaque(false);

        // Add the components to the panel
        add(dayComboBox);
        add(monthComboBox);
        add(yearComboBox);
    }

    public Date getSelectedDate() {
        int day = (int) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex();
        int year = (int) yearComboBox.getSelectedItem();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);

        return calendar.getTime();
    }
    

}

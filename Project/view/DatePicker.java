package view;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DatePicker extends JDialog {
    private String selectedDate = "";
    
    private JComboBox<String> cmbMonth;
    private JComboBox<Integer> cmbYear;
    private JPanel pnlCalendar;
    
    private JButton[] days = new JButton[49];

    public DatePicker(JFrame parent) {
        super(parent, "Select Date of Birth", true);
        setSize(450, 400); 
        setLocationRelativeTo(parent);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        String[] months = {
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        };
        cmbMonth = new JComboBox<>(months);
        cmbMonth.setFont(new Font("Arial", Font.PLAIN, 14));
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = 1900;
        int totalYears = currentYear - startYear + 1;
        
        Integer[] years = new Integer[totalYears];
        for (int i = 0; i < totalYears; i++) {
            years[i] = startYear + i;
        }
        
        cmbYear = new JComboBox<>(years);
        cmbYear.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbYear.setSelectedItem(currentYear); 
      
        cmbMonth.addActionListener(e -> displayDate());
        cmbYear.addActionListener(e -> displayDate());

        header.add(new JLabel("Month:"));
        header.add(cmbMonth);
        header.add(new JLabel("Year:"));
        header.add(cmbYear);
        
        add(header, BorderLayout.NORTH);

        pnlCalendar = new JPanel(new GridLayout(7, 7, 5, 5)); 
        pnlCalendar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] dayHeaders = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String h : dayHeaders) {
            JLabel lbl = new JLabel(h, SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.BOLD, 12));
            lbl.setForeground(Color.BLUE);
            pnlCalendar.add(lbl);
        }

        for (int i = 0; i < 42; i++) {
            final int selection = i;
            days[i] = new JButton();
            days[i].setFocusPainted(false);
            days[i].setBackground(Color.WHITE);
            days[i].setFont(new Font("Arial", Font.PLAIN, 12));
            
            days[i].addActionListener(e -> {
                String day = days[selection].getActionCommand();
                if (!day.equals("")) {
                    int selMonth = cmbMonth.getSelectedIndex() + 1;
                    int selYear = (Integer) cmbYear.getSelectedItem();
                    
                    selectedDate = String.format("%02d-%02d-%d", selMonth, Integer.parseInt(day), selYear);
                    dispose(); 
                }
            });
            pnlCalendar.add(days[i]);
        }
        add(pnlCalendar, BorderLayout.CENTER);

        cmbMonth.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        displayDate();
    }

    public void displayDate() {
        int month = cmbMonth.getSelectedIndex();
        int year = (Integer) cmbYear.getSelectedItem();
        
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 42; i++) {
            days[i].setText("");
            days[i].setEnabled(false);
            days[i].setBackground(new Color(240, 240, 240)); 
        }

        for (int i = 1; i <= numberOfDays; i++) {
            int index = (i + startDay - 2); 
            if(index >= 0 && index < 42) {
                days[index].setText("" + i);
                days[index].setEnabled(true);
                days[index].setBackground(Color.WHITE);
            }
        }
        pnlCalendar.repaint();
    }

    public String setPickedDate() {
        setVisible(true); 
        return selectedDate;
    }
}
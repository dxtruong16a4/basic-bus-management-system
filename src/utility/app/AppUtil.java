package utility.app;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;

import com.toedter.calendar.JDateChooser;

import controller.*;

public class AppUtil {
    public static AppUtil instance = null;
    ComponentManager componentManager = null;
    AppTranslator translator = null;

    public static AppUtil getInstance() {
        if (instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }

    private AppUtil() {
        componentManager = ComponentManager.getInstance();
        translator = AppTranslator.getInstance(new java.util.Locale("en"));
    }

    public AppTranslator getAppTranslator() {
        return translator;
    }

    private JLabel addLabelComponent(JPanel panel, String id, String text, GridBagConstraints gbc) {
        if (panel == null) return null;
        JLabel label = new JLabel(text);
        label.setMinimumSize(new Dimension(100, 30));
        label.setPreferredSize(new Dimension(100, 30));
        label.setHorizontalAlignment(JLabel.LEFT);
        panel.add(label, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "label", label);
        } else {
            componentManager.addComponent("label", label);
        }
        return label;
    }

    private JTextField addTextFieldComponent(JPanel panel, String id, GridBagConstraints gbc) {
        if (panel == null) return null;
        JTextField textField = new JTextField(24);
        panel.add(textField, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "textfield", textField);
        } else {
            componentManager.addComponent("textfield", textField);
        }
        return textField;
    }

    private JComboBox<String> addComboBoxComponent(JPanel panel, String id, String[] items, GridBagConstraints gbc) {
        if (panel == null || items == null) return null;
        JComboBox<String> comboBox = new JComboBox<>();
        for (String item : items) comboBox.addItem(item);
        comboBox.setPreferredSize(new Dimension(275, 30));
        comboBox.setEditable(true);
        panel.add(comboBox, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "combobox", comboBox);
        } else {
            componentManager.addComponent("combobox", comboBox);
        }
        return comboBox;
    }

    private JSpinner addSpinnerComponent(JPanel panel, String id, int min, int max, GridBagConstraints gbc) {
        if (panel == null) return null;
        SpinnerModel model = new SpinnerNumberModel(0, min, max, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(275, 30));
        panel.add(spinner, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "spinner", spinner);
        } else {
            componentManager.addComponent("spinner", spinner);
        }
        return spinner;
    }

    private void addRadioButtons(JPanel panel, String id, Consumer<JRadioButton> falseRef, Consumer<JRadioButton> trueRef, GridBagConstraints gbc) {
        if (panel == null || id == null || id.isEmpty()) return;

        String trueId = id + "_true";
        String falseId = id + "_false";

        JRadioButton trueButton = new JRadioButton("True");
        JRadioButton falseButton = new JRadioButton("False");
        falseButton.setSelected(true);

        trueButton.setName(trueId);
        falseButton.setName(falseId);

        ButtonGroup group = new ButtonGroup();
        group.add(trueButton);
        group.add(falseButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        radioPanel.add(trueButton);
        radioPanel.add(falseButton);

        trueRef.accept(trueButton);
        falseRef.accept(falseButton);

        panel.add(radioPanel, gbc);

        componentManager.addComponentWithId(trueId, "truebutton", trueButton);
        componentManager.addComponentWithId(falseId, "falsebutton", falseButton);
        componentManager.addComponentWithId(id, "radiobuttons", trueButton);
    }

    private JDateChooser addCalendarComponent(JPanel panel, String id, GridBagConstraints gbc) {
        if (panel == null) return null;
        JDateChooser calendar = new JDateChooser();
        calendar.setPreferredSize(new Dimension(275, 30));
        calendar.setDateFormatString("yyyy-MM-dd");
        panel.add(calendar, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "calendar", calendar);
        } else {
            componentManager.addComponent("calendar", calendar);
        }
        return calendar;
    }

    private JDateChooser addTimestampComponent(JPanel panel, String id, GridBagConstraints gbc) {
        if (panel == null) return null;
        JDateChooser timestamp = new JDateChooser();
        timestamp.setPreferredSize(new Dimension(275, 30));
        timestamp.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        panel.add(timestamp, gbc);
        if (id != null && !id.isEmpty()) {
            componentManager.addComponentWithId(id, "timestamp", timestamp);
        } else {
            componentManager.addComponent("timestamp", timestamp);
        }
        return timestamp;
    }

    public Component addComponentByName(JPanel panel, String id, String componentName, GridBagConstraints gbc) {
        if (panel == null || componentName == null) return null;
        switch (componentName.toLowerCase()) {
            case "label":
                return addLabelComponent(panel, id, "Label", gbc);
            case "textfield":
                return addTextFieldComponent(panel, id, gbc);
            case "combobox":
                return addComboBoxComponent(panel, id, new String[]{}, gbc);
            case "spinner_int":
                return addSpinnerComponent(panel, id, Integer.MIN_VALUE, Integer.MAX_VALUE, gbc);
            case "spinner_float":
                SpinnerModel floatModel = new SpinnerNumberModel(0.0, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1);
                JSpinner floatSpinner = new JSpinner(floatModel);
                floatSpinner.setPreferredSize(new Dimension(275, 30));
                panel.add(floatSpinner, gbc);
                if (id != null && !id.isEmpty()) {
                    componentManager.addComponentWithId(id, "spinner", floatSpinner);
                } else {
                    componentManager.addComponent("spinner", floatSpinner);
                }
                return floatSpinner;
            case "spinner":
                return addSpinnerComponent(panel, id, 0, 10000000, gbc);
            case "radiobuttons":
                JRadioButton[] trueBtn = new JRadioButton[1], falseBtn = new JRadioButton[1];
                addRadioButtons(panel, id, b -> falseBtn[0] = b, b -> trueBtn[0] = b, gbc);
                return trueBtn[0];
            case "calendar":
                return addCalendarComponent(panel, id, gbc);
            case "timestamp":
                return addTimestampComponent(panel, id, gbc);
            default:
                return null;
        }
    }

    public String mapTypeToComponent(String dataType) {
        if (dataType.equals("boolean") || dataType.equals("bit") || dataType.equals("tinyint(1)")) return "radiobuttons";
        if (dataType.contains("float") || dataType.contains("double") || dataType.contains("decimal")) return "spinner_float";
        if (dataType.matches(".*(int|tinyint|smallint|bigint).*") && !dataType.equals("tinyint(1)")) return "spinner_int";
        if (dataType.contains("char") || dataType.contains("text")) return "textfield";
        if (dataType.contains("enum")) return "combobox";
        if (dataType.contains("timestamp")) return "timestamp";
        if (dataType.contains("date")) return "calendar";
        return "textfield";
    }

    public int addComponentsByDataTypes(JPanel panel, GridBagConstraints gbc, Map<String, String> columnTypes, String headerPrefix,
                                        Map<String, String[]> idComboBoxData, String primaryKeyField) {
        int line = 0;
        gbc.gridy = 0;
        boolean isFirstField = true;

        // Get all controllers for foreign key data
        BookingController bookingController = BookingController.getInstance();
        BookingDetailController bookingDetailController = BookingDetailController.getInstance();
        BusOperatorController busOperatorController = BusOperatorController.getInstance();
        BusController busController = BusController.getInstance();
        RouteController routeController = RouteController.getInstance();
        ScheduleController scheduleController = ScheduleController.getInstance();
        FareController fareController = FareController.getInstance();
        SeatController seatController = SeatController.getInstance();
        UserController userController = UserController.getInstance();

        for (Map.Entry<String, String> entry : columnTypes.entrySet()) {
            String columnKey = entry.getKey();
            String columnName = translator.translate(headerPrefix + columnKey);
            String dataType = entry.getValue().toLowerCase();

            gbc.gridx = 0;
            addLabelComponent(panel, columnKey, columnName, gbc);

            String componentName;

            if (isFirstField) {
                componentName = "textfield";
                isFirstField = false;
            } else if (columnKey.toLowerCase().contains("id") && !columnKey.equals(primaryKeyField)) {
                componentName = "combobox";
            } else if (idComboBoxData != null && idComboBoxData.containsKey(columnName)) {
                componentName = "combobox";
            } else {
                componentName = mapTypeToComponent(dataType);
            }

            gbc.gridx = 1;
            if (componentName.equals("combobox")) {
                String[] comboData = null;
                if (dataType.contains("enum")) {
                    int start = dataType.indexOf("(");
                    int end = dataType.lastIndexOf(")");
                    if (start != -1 && end != -1 && end > start) {
                        String enums = dataType.substring(start + 1, end);
                        enums = enums.replace("'", "");
                        String[] enumValues = enums.split(",");
                        for (int i = 0; i < enumValues.length; i++) {
                            enumValues[i] = enumValues[i].trim();
                        }
                        comboData = enumValues;
                    }
                } else if (idComboBoxData != null && idComboBoxData.containsKey(columnName)) {
                    comboData = idComboBoxData.get(columnName);
                } else {
                    switch (columnKey.toLowerCase()) {
                        case "booking_id":
                            comboData = bookingDetailController.getAllBookingDetails().stream()
                                .map(booking -> booking.getBookingId() + " - " + booking.getPassengerName())
                                .toArray(String[]::new);
                            break;
                        case "booking_detail_id":
                            comboData = bookingController.getAllBookings().stream()
                                .map(booking -> booking.getBookingId() + " - " + booking.getBookingDate())
                                .toArray(String[]::new);
                            break;
                        case "bus_id":
                            comboData = busController.getAllBuses().stream()
                                .map(bus -> bus.getBusId() + " - " + bus.getBusName())
                                .toArray(String[]::new);
                            break;
                        case "operator_id":
                            comboData = busOperatorController.getAllBusOperators().stream()
                                .map(operator -> operator.getOperatorId() + " - " + operator.getOperatorName())
                                .toArray(String[]::new);
                            break;                        
                        case "fare_id":
                            comboData = fareController.getAllFares().stream()
                                .map(fare -> fare.getFareId() + " - $" + fare.getBaseFare())
                                .toArray(String[]::new);
                            break;
                        case "route_id":
                            comboData = routeController.getAllRoutes().stream()
                                .map(route -> route.getRouteId() + " - " + route.getOriginCity() + " to " + route.getDestinationCity())
                                .toArray(String[]::new);
                            break;
                        case "schedule_id":
                            comboData = scheduleController.getAllSchedules().stream()
                                .map(schedule -> schedule.getScheduleId() + " - " + schedule.getDepartureTime())
                                .toArray(String[]::new);
                            break;
                        case "seat_id":
                            comboData = seatController.getAllSeats().stream()
                                .map(seat -> seat.getSeatId() + " - " + seat.getSeatNumber())
                                .toArray(String[]::new);
                            break;
                        case "user_id":
                            comboData = userController.getAllUsers().stream()
                                .map(user -> user.getUserId() + " - " + user.getUsername())
                                .toArray(String[]::new);
                            break;
                        default:
                            System.out.println("No matching case for: " + columnKey);
                    }
                }
                if (comboData != null && comboData.length > 0) {
                    addComboBoxComponent(panel, columnKey, comboData, gbc);
                } else {
                    addComboBoxComponent(panel, columnKey, new String[]{}, gbc);
                }
            } else {
                addComponentByName(panel, columnKey, componentName, gbc);
            }

            gbc.gridy++;
            line++;
        }

        return line;
    }

    public Map<String, Object> getInputData(JPanel panel, Map<String, String> columnTypes) {
        Map<String, Object> inputData = new HashMap<>();
        
        for (Map.Entry<String, String> entry : columnTypes.entrySet()) {
            String columnKey = entry.getKey();
            String dataType = entry.getValue().toLowerCase();
            
            Component component = componentManager.getComponentById(columnKey);
            if (component == null) continue;
            
            if (component instanceof JTextField) {
                String value = ((JTextField) component).getText();
                inputData.put(columnKey, convertValue(value, dataType));
            } else if (component instanceof JComboBox) {
                String value = ((JComboBox<?>) component).getSelectedItem().toString();
                if (value.contains(" - ")) {
                    value = value.split(" - ")[0];
                }
                inputData.put(columnKey, convertValue(value, dataType));
            } else if (component instanceof JSpinner) {
                Object value = ((JSpinner) component).getValue();
                inputData.put(columnKey, value);
            } else if (component instanceof JRadioButton) {
                boolean value = ((JRadioButton) component).isSelected();
                inputData.put(columnKey, value);
            } else if (component instanceof JDateChooser) {
                java.util.Date value = ((JDateChooser) component).getDate();
                inputData.put(columnKey, value);
            }
        }
        return inputData;
    }
    
    private Object convertValue(String value, String dataType) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            if (dataType.contains("int")) {
                return Integer.parseInt(value);
            } else if (dataType.contains("decimal") || dataType.contains("float") || dataType.contains("double")) {
                return Double.parseDouble(value);
            } else if (dataType.contains("date") || dataType.contains("time") || dataType.contains("timestamp")) {
                if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return java.sql.Date.valueOf(value);
                } else {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date utilDate = sdf.parse(value);
                        return new java.sql.Date(utilDate.getTime());
                    } catch (Exception ex) {
                        return value;
                    }
                }
            } else if (dataType.equals("boolean") || dataType.equals("bit") || dataType.equals("tinyint(1)")) {
                return Boolean.parseBoolean(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void fillInputData(JPanel panel, Map<String, Object> inputData) {
        if (panel == null || inputData == null) return;

        for (Map.Entry<String, Object> entry : inputData.entrySet()) {
            String columnKey = entry.getKey();
            Object value = entry.getValue();

            Component component = componentManager.getComponentById(columnKey);
            if (component == null) continue;

            if (component instanceof JTextField) {
                ((JTextField) component).setText(value != null ? value.toString() : "");
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setSelectedItem(value != null ? value.toString() : "");
            } else if (component instanceof JSpinner) {
                ((JSpinner) component).setValue(value != null ? value : 0);
            } else if (component instanceof JRadioButton) {
                ((JRadioButton) component).setSelected(value != null && Boolean.parseBoolean(value.toString()));
            } else if (component instanceof JDateChooser) {
                if (value instanceof java.util.Date) {
                    ((JDateChooser) component).setDate((java.util.Date) value);
                } else if (value instanceof java.sql.Date) {
                    ((JDateChooser) component).setDate(new java.util.Date(((java.sql.Date) value).getTime()));
                }
            }
        }
    }
}

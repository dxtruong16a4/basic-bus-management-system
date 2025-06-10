package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import utility.tableloader.*;

import utility.Suggestion.SuggestionFileUtil;
import utility.Suggestion.SuggestionPopUp;
import utility.app.AppTranslator;
import utility.app.AppUtil;
import utility.app.ComponentManager;
import utility.db.DbConstants;
import dao.DAO;
import controller.*;

public class Manager extends javax.swing.JFrame {
    private String currentAction = null;
    private int lineCount = 0;

    private int currentPage = 1;
    private int pageSize = 10;
    private int totalRows = 0;
    private int totalPages = 1;

    public static Manager instance = null;
    private AppUtil appUtil = null;
    private AppTranslator translator = null;
    private ComponentManager componentManager = null;
    private SuggestionPopUp searchSuggestionPopUp = null;

    // Controller
    private BookingController bookingController = null;
    private BookingDetailController bookingDetailController = null;
    private BusController busController = null;
    private BusOperatorController busOperatorController = null;
    private FareController fareController = null; 
    private RouteController routeController = null;
    private ScheduleController scheduleController = null;
    private SeatController seatController = null;

    // Table Loader
    private BookingTableLoader bookingTableLoader;
    private BookingDetailTableLoader bookingDetailTableLoader;
    private BusTableLoader busTableLoader;
    private BusOperatorTableLoader busOperatorTableLoader;
    private FareTableLoader fareTableLoader;
    private RouteTableLoader routeTableLoader;
    private ScheduleTableLoader scheduleTableLoader;
    private SeatTableLoader seatTableLoader;

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public Manager() {
        appUtil = AppUtil.getInstance();
        translator = appUtil.getAppTranslator();
        componentManager = ComponentManager.getInstance();
        initComponents();
        initController();
        initTableLoader();        
        loadCbMode();
        setLocationRelativeTo(null);
    }

    private void initController() {
        bookingController = BookingController.getInstance();
        bookingDetailController = BookingDetailController.getInstance();
        busController = BusController.getInstance();
        busOperatorController = BusOperatorController.getInstance();
        fareController = FareController.getInstance();
        routeController = RouteController.getInstance();
        scheduleController = ScheduleController.getInstance();
        seatController = SeatController.getInstance();
    }

    private void initTableLoader() {
        bookingTableLoader = new BookingTableLoader(bookingController, translator);
        bookingDetailTableLoader = new BookingDetailTableLoader(bookingDetailController, translator);
        busTableLoader = new BusTableLoader(busController, translator);
        busOperatorTableLoader = new BusOperatorTableLoader(busOperatorController, translator);
        fareTableLoader = new FareTableLoader(fareController, translator);
        routeTableLoader = new RouteTableLoader(routeController, translator);
        scheduleTableLoader = new ScheduleTableLoader(scheduleController, translator);
        seatTableLoader = new SeatTableLoader(seatController, translator);
    }

    private void loadCbMode() {
        List<String> currentDBMode = DAO.getAllTableNames();
        List<String> hiddenDBMode = List.of(
            DbConstants.HIDDEN1,
            DbConstants.HIDDEN2,
            DbConstants.HIDDEN3,
            DbConstants.HIDDEN4
        );
        if (currentDBMode != null) {
            currentDBMode.removeAll(hiddenDBMode);
            if (currentDBMode.isEmpty()) {
                System.out.println("No modes available.");
                return;
            }
            cbMode.removeAllItems();
            for (String mode : currentDBMode) {
                String translatedMode = translator.translate("db.table." + mode);
                cbMode.addItem(translatedMode);
            }
        } else {
            System.out.println("No modes available.");
        }
    }

    private void loadCbSearch(String mode) {
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for search.");
            return;
        }
        cbSearch.removeAllItems();
        if (mode.equals(translator.translate("db.table.bookings"))) {
            Map<String, String> searchOptions = bookingController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("booking.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            Map<String, String> searchOptions = bookingDetailController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("booking_detail.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            Map<String, String> searchOptions = busController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("bus.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            Map<String, String> searchOptions = busOperatorController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("bus_operator.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            Map<String, String> searchOptions = routeController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("route.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            Map<String, String> searchOptions = scheduleController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("schedule.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            Map<String, String> searchOptions = fareController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("fare.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            Map<String, String> searchOptions = seatController.getColumnDataTypes();        
            for (String columnName : searchOptions.keySet()) {
                String translatedColumnName = translator.translate("seat.header." + columnName);
                cbSearch.addItem(translatedColumnName);
            }
        } else {
            System.out.println("Search options not available for this mode: " + mode);
        }
    }

    private void createInputPanel(String mode) {
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for input panel.");
            return;
        }

        lineCount = 0;
        pnInput.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        Map<String, String> columnTypes = null;
        String headerPrefix = "";
        String primaryKey = "";
        Map<String, String[]> idComboBoxData = null;
        if (mode.equals(translator.translate("db.table.bookings"))) {
            columnTypes = bookingController.getColumnDataTypes();
            headerPrefix = "booking.header.";
            primaryKey = "booking_id";
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            columnTypes = bookingDetailController.getColumnDataTypes();
            headerPrefix = "booking_detail.header.";
            primaryKey = "booking_detail_id";
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            columnTypes = busController.getColumnDataTypes();
            headerPrefix = "bus.header.";
            primaryKey = "bus_id";
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            columnTypes = busOperatorController.getColumnDataTypes();
            headerPrefix = "bus_operator.header.";
            primaryKey = "operator_id";
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            columnTypes = fareController.getColumnDataTypes();
            headerPrefix = "fare.header.";
            primaryKey = "fare_id";
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            columnTypes = routeController.getColumnDataTypes();
            headerPrefix = "route.header.";
            primaryKey = "route_id";
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            columnTypes = scheduleController.getColumnDataTypes();
            headerPrefix = "schedule.header.";
            primaryKey = "schedule_id";
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            columnTypes = seatController.getColumnDataTypes();
            headerPrefix = "seat.header.";
            primaryKey = "seat_id";
        } else {
            System.out.println("Input panel not available for this mode: " + mode);
            return;
        }

        lineCount = appUtil.addComponentsByDataTypes(pnInput, gbc, columnTypes, headerPrefix, idComboBoxData, primaryKey);
    }

    private void updateButtonColors() {
        btnInsert.setBackground("insert".equals(currentAction) ? 
            new java.awt.Color(102, 102, 255) : new java.awt.Color(255, 255, 255));
        btnUpdate.setBackground("update".equals(currentAction) ? 
            new java.awt.Color(102, 102, 255) : new java.awt.Color(255, 255, 255));
        btnDelete.setBackground("delete".equals(currentAction) ? 
            new java.awt.Color(102, 102, 255) : new java.awt.Color(255, 255, 255));
    }

    private void showInputPanel(boolean show) {        
        updateButtonColors();
        pnInput.setPreferredSize(new Dimension(700, lineCount * 40));
        pnInput.setVisible(show);
    }

    private void setTableHeader(String mode) {
        if (mode == null || mode.isEmpty()) {
            return;
        }
        loadTable(mode);
    }   

    private void loadTable(String mode) {
        tbDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for loading table.");
            return;
        }
        int offset = (currentPage - 1) * pageSize;
        if (mode.equals(translator.translate("db.table.bookings"))) {
            List<?> all = bookingController.getAllBookings();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            bookingTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            List<?> all = bookingDetailController.getAllBookingDetails();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            bookingDetailTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            List<?> all = busController.getAllBuses();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            busTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            List<?> all = busOperatorController.getAllBusOperators();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            busOperatorTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            List<?> all = routeController.getAllRoutes();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            routeTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            List<?> all = scheduleController.getAllSchedules();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            scheduleTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            List<?> all = fareController.getAllFares();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            fareTableLoader.load(tbDetail, offset, pageSize);
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            List<?> all = seatController.getAllSeats();
            totalRows = all.size();
            totalPages = (int) Math.ceil((double) totalRows / pageSize);
            seatTableLoader.load(tbDetail, offset, pageSize);
        } else {
            System.out.println("Unknown mode: " + mode);
        }
        lbPageId.setText(currentPage + "/" + totalPages);
    }

    private void loadDataBy(String searchBy, String searchValue) {
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for loading data.");
            return;
        }
        if (searchBy == null || searchBy.isEmpty() || searchValue == null || searchValue.isEmpty()) {
            System.out.println("No search criteria provided.");
            return;
        }
        int offset = (currentPage - 1) * pageSize;
        if (mode.equals(translator.translate("db.table.bookings"))) {
            bookingTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.booking_details"))) {
            bookingDetailTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.buses"))) {
            busTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            busOperatorTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.routes"))) {
            routeTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.schedules"))) {
            scheduleTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.fares"))) {
            fareTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else if (mode.equals(translator.translate("db.table.seats"))) {
            seatTableLoader.loadBy(tbDetail, searchBy, searchValue, offset, pageSize);
        }
        else {
            System.out.println("Unknown mode: " + mode);
        }
    }

    private Map<String, String> getColumnTypesByMode(String mode) {
        if (mode.equals(translator.translate("db.table.bookings"))) {
            return bookingController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            return bookingDetailController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            return busController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            return busOperatorController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            return fareController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            return routeController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            return scheduleController.getColumnDataTypes();
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            return seatController.getColumnDataTypes();
        }
        return null;
    }

    private String getRealColumnKey(String columnName, String mode, Map<String, String> columnTypes) {
        if (columnTypes == null) return columnName;
        String keyPrefix = mode.toLowerCase().replace("db.table.", "").replace(" ", "_");
        for (String key : columnTypes.keySet()) {
            String display = translator.translate(keyPrefix + ".header." + key);
            if (display.equals(columnName) || key.equalsIgnoreCase(columnName)) {
                return key;
            }
        }
        return columnName;
    }

    private void fillComponentValue(java.awt.Component comp, Object value, String realColumnKey) {
        if (comp instanceof javax.swing.JTextField) {
            ((javax.swing.JTextField) comp).setText(value != null ? value.toString() : "");
        } else if (comp instanceof javax.swing.JComboBox) {
            ((javax.swing.JComboBox<?>) comp).setSelectedItem(value);
        } else if (comp instanceof javax.swing.JSpinner) {
            if (value instanceof Number) {
                ((javax.swing.JSpinner) comp).setValue(value);
            } else {
                try {
                    ((javax.swing.JSpinner) comp).setValue(Double.parseDouble(value.toString()));
                } catch (Exception ex) {}
            }
        } else if (comp instanceof javax.swing.JRadioButton) {
            boolean selected = false;
            if (value instanceof Boolean) selected = (Boolean) value;
            else if (value != null) selected = value.toString().equalsIgnoreCase("true") || value.toString().equals("1");
            JRadioButton trueBtn = (JRadioButton) componentManager.getComponentById(realColumnKey + "_true");
            JRadioButton falseBtn = (JRadioButton) componentManager.getComponentById(realColumnKey + "_false");
            if (trueBtn != null)
            {trueBtn.setSelected(selected);}
            if (falseBtn != null)
            {falseBtn.setSelected(!selected);}
        } else if (comp instanceof com.toedter.calendar.JDateChooser) {
            try {
                if (value != null && !value.toString().isEmpty()) {
                    java.util.Date date = null;
                    if (value instanceof java.util.Date) date = (java.util.Date) value;
                    else {
                        String v = value.toString();
                        if (v.length() > 10) v = v.substring(0, 19);
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(v.length() > 10 ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd");
                        date = sdf.parse(v);
                    }
                    ((com.toedter.calendar.JDateChooser) comp).setDate(date);
                } else {
                    ((com.toedter.calendar.JDateChooser) comp).setDate(null);
                }
            } catch (Exception ex) {}
        }
    }

    private boolean insert() {
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for insertion.");
            return false;
        }
        if (mode.equals(translator.translate("db.table.bookings"))) {
            return bookingController.addBooking(appUtil.getInputData(pnInput, bookingController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            return bookingDetailController.addBookingDetail(appUtil.getInputData(pnInput, bookingDetailController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            return busController.addBus(appUtil.getInputData(pnInput, busController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            return busOperatorController.addBusOperator(appUtil.getInputData(pnInput, busOperatorController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            return fareController.addFare(appUtil.getInputData(pnInput, fareController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            return routeController.addRoute(appUtil.getInputData(pnInput, routeController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            return scheduleController.addSchedule(appUtil.getInputData(pnInput, scheduleController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            return seatController.addSeat(appUtil.getInputData(pnInput, seatController.getColumnDataTypes()));
        }
        System.out.println("Insert operation not available for this mode: " + mode);
        return false;
    }

    private boolean update() {
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for update.");
            return false;
        }
        if (mode.equals(translator.translate("db.table.bookings"))) {
            return bookingController.updateBooking(appUtil.getInputData(pnInput, bookingController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            return bookingDetailController.updateBookingDetail(appUtil.getInputData(pnInput, bookingDetailController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            return busController.updateBus(appUtil.getInputData(pnInput, busController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            return busOperatorController.updateBusOperator(appUtil.getInputData(pnInput, busOperatorController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            return fareController.updateFare(appUtil.getInputData(pnInput, fareController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            return routeController.updateRoute(appUtil.getInputData(pnInput, routeController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            return scheduleController.updateSchedule(appUtil.getInputData(pnInput, scheduleController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            return seatController.updateSeat(appUtil.getInputData(pnInput, seatController.getColumnDataTypes()));
        }
        System.out.println("Update operation not available for this mode: " + mode);
        return false;
    }

    private boolean delete() {
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for deletion.");
            return false;
        }
        if (mode.equals(translator.translate("db.table.bookings"))) {
            return bookingController.deleteBooking(appUtil.getInputData(pnInput, bookingController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.booking_details"))) {
            return bookingDetailController.deleteBookingDetail(appUtil.getInputData(pnInput, bookingDetailController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.buses"))) {
            return busController.deleteBus(appUtil.getInputData(pnInput, busController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.bus_operators"))) {
            return busOperatorController.deleteBusOperator(appUtil.getInputData(pnInput, busOperatorController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.fares"))) {
            return fareController.deleteFare(appUtil.getInputData(pnInput, fareController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.routes"))) {
            return routeController.deleteRoute(appUtil.getInputData(pnInput, routeController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.schedules"))) {
            return scheduleController.deleteSchedule(appUtil.getInputData(pnInput, scheduleController.getColumnDataTypes()));
        } else if (mode.equals(translator.translate("db.table.seats"))) {
            return seatController.deleteSeat(appUtil.getInputData(pnInput, seatController.getColumnDataTypes()));
        }
        System.out.println("Delete operation not available for this mode: " + mode);
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnManager = new javax.swing.JScrollPane();
        pnWorkspace = new javax.swing.JPanel();
        pnManagerMode = new javax.swing.JPanel();
        lbMode = new javax.swing.JLabel();
        cbMode = new javax.swing.JComboBox<>();
        pnSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        cbSearch = new javax.swing.JComboBox<>();
        tfSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        pnData = new javax.swing.JPanel();
        pnAction = new javax.swing.JPanel();
        btnLoad = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnInput = new javax.swing.JPanel();
        pnTable = new javax.swing.JPanel();
        spnTable = new javax.swing.JScrollPane();
        tbDetail = new javax.swing.JTable();
        pnPage = new javax.swing.JPanel();
        btnPre5 = new javax.swing.JButton();
        btnPre1 = new javax.swing.JButton();
        lbPageId = new javax.swing.JLabel();
        btnNext1 = new javax.swing.JButton();
        btnNext5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manager");
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(1200, 900));
        setPreferredSize(new java.awt.Dimension(1200, 900));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pnManager.setMaximumSize(new java.awt.Dimension(1920, 1080));
        pnManager.setMinimumSize(new java.awt.Dimension(1180, 780));
        pnManager.setPreferredSize(new java.awt.Dimension(1180, 780));

        pnWorkspace.setMinimumSize(new java.awt.Dimension(720, 702));
        pnWorkspace.setLayout(new java.awt.GridBagLayout());

        pnManagerMode.setMinimumSize(new java.awt.Dimension(700, 40));
        pnManagerMode.setPreferredSize(new java.awt.Dimension(700, 40));
        pnManagerMode.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));

        lbMode.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbMode.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbMode.setText("Manager:");
        lbMode.setMaximumSize(new java.awt.Dimension(200, 32));
        lbMode.setMinimumSize(new java.awt.Dimension(200, 32));
        lbMode.setPreferredSize(new java.awt.Dimension(200, 32));
        pnManagerMode.add(lbMode);

        cbMode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbMode.setMaximumSize(new java.awt.Dimension(200, 40));
        cbMode.setMinimumSize(new java.awt.Dimension(200, 40));
        cbMode.setPreferredSize(new java.awt.Dimension(200, 40));
        cbMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModeActionPerformed(evt);
            }
        });
        pnManagerMode.add(cbMode);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        pnWorkspace.add(pnManagerMode, gridBagConstraints);

        pnSearch.setPreferredSize(new java.awt.Dimension(720, 50));
        pnSearch.setLayout(new java.awt.GridBagLayout());

        lbSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSearch.setText("Search by");
        lbSearch.setMaximumSize(new java.awt.Dimension(100, 32));
        lbSearch.setMinimumSize(new java.awt.Dimension(100, 32));
        lbSearch.setName(""); // NOI18N
        lbSearch.setPreferredSize(new java.awt.Dimension(100, 32));
        pnSearch.add(lbSearch, new java.awt.GridBagConstraints());

        cbSearch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cbSearch.setMaximumSize(new java.awt.Dimension(120, 32));
        cbSearch.setMinimumSize(new java.awt.Dimension(120, 32));
        cbSearch.setPreferredSize(new java.awt.Dimension(120, 32));
        pnSearch.add(cbSearch, new java.awt.GridBagConstraints());

        tfSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tfSearch.setMaximumSize(new java.awt.Dimension(300, 32));
        tfSearch.setMinimumSize(new java.awt.Dimension(300, 32));
        tfSearch.setName(""); // NOI18N
        tfSearch.setPreferredSize(new java.awt.Dimension(300, 32));
        tfSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfSearchMouseClicked(evt);
            }
        });
        pnSearch.add(tfSearch, new java.awt.GridBagConstraints());

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setMaximumSize(new java.awt.Dimension(100, 32));
        btnSearch.setMinimumSize(new java.awt.Dimension(100, 32));
        btnSearch.setPreferredSize(new java.awt.Dimension(100, 32));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnSearch.add(btnSearch, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        pnWorkspace.add(pnSearch, gridBagConstraints);

        pnData.setMinimumSize(new java.awt.Dimension(700, 80));
        pnData.setLayout(new java.awt.GridBagLayout());

        pnAction.setMinimumSize(new java.awt.Dimension(700, 42));
        pnAction.setPreferredSize(new java.awt.Dimension(700, 42));
        pnAction.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        btnLoad.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLoad.setText("Load");
        btnLoad.setMaximumSize(new java.awt.Dimension(100, 32));
        btnLoad.setMinimumSize(new java.awt.Dimension(100, 32));
        btnLoad.setPreferredSize(new java.awt.Dimension(100, 32));
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        pnAction.add(btnLoad);

        btnInsert.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.setMaximumSize(new java.awt.Dimension(100, 32));
        btnInsert.setMinimumSize(new java.awt.Dimension(100, 32));
        btnInsert.setPreferredSize(new java.awt.Dimension(100, 32));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnAction.add(btnInsert);

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setMaximumSize(new java.awt.Dimension(100, 32));
        btnUpdate.setMinimumSize(new java.awt.Dimension(100, 32));
        btnUpdate.setPreferredSize(new java.awt.Dimension(100, 32));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnAction.add(btnUpdate);

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(new java.awt.Dimension(100, 32));
        btnDelete.setMinimumSize(new java.awt.Dimension(100, 32));
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 32));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnAction.add(btnDelete);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        pnData.add(pnAction, gridBagConstraints);

        pnInput.setMinimumSize(new java.awt.Dimension(0, 0));
        pnInput.setPreferredSize(new java.awt.Dimension(0, 0));
        pnInput.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        pnData.add(pnInput, gridBagConstraints);

        pnTable.setMinimumSize(new java.awt.Dimension(700, 10));
        pnTable.setPreferredSize(new java.awt.Dimension(1100, 280));

        spnTable.setPreferredSize(new java.awt.Dimension(1100, 270));

        tbDetail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        tbDetail.setMinimumSize(new java.awt.Dimension(680, 240));
        tbDetail.setPreferredSize(new java.awt.Dimension(680, 240));
        tbDetail.setRowHeight(24);
        tbDetail.setShowGrid(true);
        tbDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailMouseClicked(evt);
            }
        });
        spnTable.setViewportView(tbDetail);

        pnTable.add(spnTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnData.add(pnTable, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        pnWorkspace.add(pnData, gridBagConstraints);

        pnPage.setMaximumSize(new java.awt.Dimension(720, 50));
        pnPage.setMinimumSize(new java.awt.Dimension(720, 50));
        pnPage.setPreferredSize(new java.awt.Dimension(720, 50));
        pnPage.setLayout(new java.awt.GridBagLayout());

        btnPre5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pre5.png"))); // NOI18N
        btnPre5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnPre5.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPre5.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPre5.setPreferredSize(new java.awt.Dimension(32, 32));
        btnPre5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPre5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        pnPage.add(btnPre5, gridBagConstraints);

        btnPre1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pre1.png"))); // NOI18N
        btnPre1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnPre1.setMaximumSize(new java.awt.Dimension(32, 32));
        btnPre1.setMinimumSize(new java.awt.Dimension(32, 32));
        btnPre1.setPreferredSize(new java.awt.Dimension(32, 32));
        btnPre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPre1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        pnPage.add(btnPre1, gridBagConstraints);

        lbPageId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbPageId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPageId.setText("0/0");
        lbPageId.setMaximumSize(new java.awt.Dimension(100, 32));
        lbPageId.setMinimumSize(new java.awt.Dimension(100, 32));
        lbPageId.setPreferredSize(new java.awt.Dimension(100, 32));
        pnPage.add(lbPageId, new java.awt.GridBagConstraints());

        btnNext1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/next1.png"))); // NOI18N
        btnNext1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNext1.setMaximumSize(new java.awt.Dimension(32, 32));
        btnNext1.setMinimumSize(new java.awt.Dimension(32, 32));
        btnNext1.setPreferredSize(new java.awt.Dimension(32, 32));
        btnNext1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        pnPage.add(btnNext1, gridBagConstraints);

        btnNext5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/next5.png"))); // NOI18N
        btnNext5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnNext5.setMaximumSize(new java.awt.Dimension(32, 32));
        btnNext5.setMinimumSize(new java.awt.Dimension(32, 32));
        btnNext5.setPreferredSize(new java.awt.Dimension(32, 32));
        btnNext5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        pnPage.add(btnNext5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        pnWorkspace.add(pnPage, gridBagConstraints);

        pnManager.setViewportView(pnWorkspace);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(pnManager, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModeActionPerformed
        String selectedMode = (String) cbMode.getSelectedItem();
        if (selectedMode != null && !selectedMode.isEmpty()) {      
            currentPage = 1;
            loadCbSearch(selectedMode);
            setTableHeader(selectedMode);
            currentAction = null;            
            createInputPanel(selectedMode);
            showInputPanel(false);
        } else {
            System.out.println("No mode selected.");
        }
    }//GEN-LAST:event_cbModeActionPerformed

    private void tfSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfSearchMouseClicked
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) return;
        try {
            String suggestionFile = "src/suggestions.txt";
            List<String> suggestions = new ArrayList<>(SuggestionFileUtil.readSuggestions(suggestionFile, mode));
            if (searchSuggestionPopUp == null) {
                searchSuggestionPopUp = new SuggestionPopUp(tfSearch, suggestions);
            } else {
                searchSuggestionPopUp.setSuggestions(suggestions);
            }
            searchSuggestionPopUp.showPopup();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tfSearchMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        currentAction = null;
        showInputPanel(false);
        String searchBy = (String) cbSearch.getSelectedItem();
        String searchValue = tfSearch.getText().trim();
        String mode = (String) cbMode.getSelectedItem();
        if (searchBy != null && !searchBy.isEmpty() && !searchValue.isEmpty()) {
            loadDataBy(searchBy, searchValue);
            try {
                String suggestionFile = "src/suggestions.txt";
                List<String> suggestions = new ArrayList<>(SuggestionFileUtil.readSuggestions(suggestionFile, mode));
                if (!suggestions.contains(searchValue)) {
                    suggestions.add(searchValue);
                    SuggestionFileUtil.writeSuggestions(suggestionFile, mode, suggestions);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, translator.translate("message.search.criteria.empty"),
                translator.translate("message.title.error"), JOptionPane.ERROR_MESSAGE);
        }
        if (searchSuggestionPopUp != null) {
            searchSuggestionPopUp.hidePopup();
        }
        tfSearch.setText("");
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        loadTable((String) cbMode.getSelectedItem());
        currentAction = null;
        showInputPanel(false);
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        if ("insert".equals(currentAction)) {
            currentAction = null;
            showInputPanel(false);
            if (insert()) {
                JOptionPane.showMessageDialog(this, translator.translate("message.insert.success"), translator.translate("message.title.success"), JOptionPane.INFORMATION_MESSAGE);
                loadTable((String) cbMode.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, translator.translate("message.insert.failure"), translator.translate("message.title.failure"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            currentAction = "insert";
            showInputPanel(true);
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if ("update".equals(currentAction)) {          
            currentAction = null;
            showInputPanel(false);
            if (update()) {
                JOptionPane.showMessageDialog(this, translator.translate("message.update.success"), translator.translate("message.title.success"), JOptionPane.INFORMATION_MESSAGE);
                loadTable((String) cbMode.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, translator.translate("message.update.failure"), translator.translate("message.title.failure"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            currentAction = "update";
            showInputPanel(true);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if ("delete".equals(currentAction)) {
            currentAction = null;
            showInputPanel(false);
            if (delete()) {
                JOptionPane.showMessageDialog(this, translator.translate("message.delete.success"), translator.translate("message.title.success"), JOptionPane.INFORMATION_MESSAGE);
                loadTable((String) cbMode.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, translator.translate("message.delete.failure"), translator.translate("message.title.failure"), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            currentAction = "delete";
            showInputPanel(true);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailMouseClicked
        int selectedRow = tbDetail.getSelectedRow();
        if (selectedRow < 0) {
            System.out.println("No row selected for editing.");
            return;
        }
        String mode = (String) cbMode.getSelectedItem();
        if (mode == null || mode.isEmpty()) {
            System.out.println("No mode selected for editing.");
            return;
        }
        currentAction = "update";
        showInputPanel(true);
        javax.swing.table.TableModel model = tbDetail.getModel();
        Map<String, String> columnTypes = getColumnTypesByMode(mode);
        for (int col = 0; col < model.getColumnCount(); col++) {
            String columnName = model.getColumnName(col);
            Object value = model.getValueAt(selectedRow, col);
            String realColumnKey = getRealColumnKey(columnName, mode, columnTypes);
            java.awt.Component comp = componentManager.getComponentById(realColumnKey);
            if (comp != null) fillComponentValue(comp, value, realColumnKey);
        }
    }//GEN-LAST:event_tbDetailMouseClicked

    private void btnPre5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPre5ActionPerformed
        if (currentPage > 5) {
            currentPage -= 5;
        } else {
            currentPage = 1;
        }
        loadTable((String) cbMode.getSelectedItem());
    }//GEN-LAST:event_btnPre5ActionPerformed

    private void btnPre1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPre1ActionPerformed
        if (currentPage > 1) {
            currentPage--;
            loadTable((String) cbMode.getSelectedItem());
        }
    }//GEN-LAST:event_btnPre1ActionPerformed

    private void btnNext1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext1ActionPerformed
        if (currentPage < totalPages) {
            currentPage++;
            loadTable((String) cbMode.getSelectedItem());
        }
    }//GEN-LAST:event_btnNext1ActionPerformed

    private void btnNext5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext5ActionPerformed
        if (currentPage + 5 <= totalPages) {
            currentPage += 5;
        } else {
            currentPage = totalPages;
        }
        loadTable((String) cbMode.getSelectedItem());
    }//GEN-LAST:event_btnNext5ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // close all controllers
        try {
            if (bookingController != null) bookingController.getDbConnect().Close();
            if (bookingDetailController != null) bookingDetailController.getDbConnect().Close();
            if (busController != null) busController.getDbConnect().Close();
            if (busOperatorController != null) busOperatorController.getDbConnect().Close();
            if (fareController != null) fareController.getDbConnect().Close();
            if (routeController != null) routeController.getDbConnect().Close();
            if (scheduleController != null) scheduleController.getDbConnect().Close();
            if (seatController != null) seatController.getDbConnect().Close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new Manager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnNext1;
    private javax.swing.JButton btnNext5;
    private javax.swing.JButton btnPre1;
    private javax.swing.JButton btnPre5;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbMode;
    private javax.swing.JComboBox<String> cbSearch;
    private javax.swing.JLabel lbMode;
    private javax.swing.JLabel lbPageId;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JPanel pnAction;
    private javax.swing.JPanel pnData;
    private javax.swing.JPanel pnInput;
    private javax.swing.JScrollPane pnManager;
    private javax.swing.JPanel pnManagerMode;
    private javax.swing.JPanel pnPage;
    private javax.swing.JPanel pnSearch;
    private javax.swing.JPanel pnTable;
    private javax.swing.JPanel pnWorkspace;
    private javax.swing.JScrollPane spnTable;
    private javax.swing.JTable tbDetail;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration//GEN-END:variables
}
package helper;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DialogUtil {
    private static final Map<String, JDialog> dialogMap = new HashMap<>();

    public static void showConfirmDialog(String key, String title, String message, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
            if (callback != null) {
                callback.onOkClicked();
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        dialogMap.put(key, dialog);
    }

    public static void showInsertBusDialog(String key, String title, String message, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JTextField tfBusId = new JTextField(15);
        JTextField tfBusCompany = new JTextField(15);
        JTextField tfBusLisencePlate = new JTextField(15);
        JTextField tfBusRoute = new JTextField(15);
        JTextField tfBusPlan = new JTextField(15);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã xe:"));
        inputPanel.add(tfBusId);
        inputPanel.add(new JLabel("Hãng xe:"));
        inputPanel.add(tfBusCompany);
        inputPanel.add(new JLabel("Biển sổ:"));
        inputPanel.add(tfBusLisencePlate);
        inputPanel.add(new JLabel("Tuyến:"));
        inputPanel.add(tfBusRoute);
        inputPanel.add(new JLabel("Lịch trình:"));
        inputPanel.add(tfBusPlan);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (tfBusCompany.getText().trim().isEmpty() ||
                tfBusLisencePlate.getText().trim().isEmpty() ||
                tfBusRoute.getText().trim().isEmpty() ||
                tfBusPlan.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dialog.dispose();
            if (callback != null) {
                callback.onInsertBusClicked(
                    tfBusId.getText(),
                    tfBusCompany.getText(),
                    tfBusLisencePlate.getText(),
                    tfBusRoute.getText(),
                    tfBusPlan.getText()
                );
            }
        });

        JButton cancelButton = new JButton("Huỷ");
        cancelButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
        dialogMap.put(key, dialog);
    }
    
    // public static void showUpdateBusDialog(String key, String title, String message, Bus bus, DialogCallback callback) {
    //     if (dialogMap.containsKey(key)) {
    //         dialogMap.get(key).dispose();
    //     }

    //     JDialog dialog = new JDialog();
    //     dialog.setTitle(title);
    //     dialog.setModal(true);
    //     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    //     dialog.setSize(400, 300);
    //     dialog.setLocationRelativeTo(null);

    //     JTextField tfBusId = new JTextField(bus.getBusId(), 15);
    //     tfBusId.setEditable(false);
    //     JTextField tfBusCompany = new JTextField(bus.getBusCompany(), 15);
    //     JTextField tfBusLisencePlate = new JTextField(bus.getBusLisencePlate(), 15);
    //     JTextField tfBusRoute = new JTextField(bus.getBusRoute(), 15);
    //     JTextField tfBusPlan = new JTextField(bus.getBusPlan(), 15);

    //     JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 8));
    //     inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //     inputPanel.add(new JLabel("Mã xe:"));
    //     inputPanel.add(tfBusId);
    //     inputPanel.add(new JLabel("Hãng xe:"));
    //     inputPanel.add(tfBusCompany);
    //     inputPanel.add(new JLabel("Biển sổ:"));
    //     inputPanel.add(tfBusLisencePlate);
    //     inputPanel.add(new JLabel("Tuyến:"));
    //     inputPanel.add(tfBusRoute);
    //     inputPanel.add(new JLabel("Lịch trình:"));
    //     inputPanel.add(tfBusPlan);

    //     JButton okButton = new JButton("OK");
    //     okButton.addActionListener(e -> {
    //         if (tfBusCompany.getText().trim().isEmpty() ||
    //             tfBusLisencePlate.getText().trim().isEmpty() ||
    //             tfBusRoute.getText().trim().isEmpty() ||
    //             tfBusPlan.getText().trim().isEmpty()) {
    //             JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }
    //         dialog.dispose();
    //         if (callback != null) {
    //             callback.onUpdateBusClicked(
    //                 tfBusId.getText(),
    //                 tfBusCompany.getText(),
    //                 tfBusLisencePlate.getText(),
    //                 tfBusRoute.getText(),
    //                 tfBusPlan.getText()
    //             );
    //         }
    //     });
    //     JButton cancelButton = new JButton("Huỷ");
    //     cancelButton.addActionListener(e -> dialog.dispose());
    //     JPanel buttonPanel = new JPanel();
    //     buttonPanel.add(okButton);
    //     buttonPanel.add(cancelButton);
    //     JPanel mainPanel = new JPanel(new BorderLayout());
    //     mainPanel.add(inputPanel, BorderLayout.CENTER);
    //     mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    //     dialog.setContentPane(mainPanel);
    //     dialog.setVisible(true);
    //     dialogMap.put(key, dialog);
    // }
    
    public static void showDeleteBusDialog(String key, String title, String message, String busId, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
            if (callback != null) {
                callback.onDeleteBusClicked(busId);
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        dialogMap.put(key, dialog);
    }

    public static void showInsertDriverDialog(String key, String title, String message, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JTextField tfDriverId = new JTextField(15);
        JTextField tfBusId = new JTextField(15);
        JTextField tfDriverName = new JTextField(15);
        JTextField tfDriverPhone = new JTextField(15);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã tài xế:"));
        inputPanel.add(tfDriverId);
        inputPanel.add(new JLabel("Mã xe:"));
        inputPanel.add(tfBusId);
        inputPanel.add(new JLabel("Tên tài xế:"));
        inputPanel.add(tfDriverName);
        inputPanel.add(new JLabel("Số điện thoại:"));
        inputPanel.add(tfDriverPhone);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (tfDriverId.getText().trim().isEmpty() ||
                tfBusId.getText().trim().isEmpty() ||
                tfDriverName.getText().trim().isEmpty() ||
                tfDriverPhone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dialog.dispose();
            if (callback != null) {
                callback.onInsertDriverClicked(
                    tfDriverId.getText(),
                    tfBusId.getText(),
                    tfDriverName.getText(),
                    tfDriverPhone.getText()
                );
            }
        });

        JButton cancelButton = new JButton("Huỷ");  
        cancelButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);

        dialogMap.put(key, dialog);
    }

    // public static void showUpdateDriverDialog(String key, String title, String message, Driver driver, DialogCallback callback) {
    //     if (dialogMap.containsKey(key)) {
    //         dialogMap.get(key).dispose();
    //     }

    //     JDialog dialog = new JDialog();
    //     dialog.setTitle(title);
    //     dialog.setModal(true);
    //     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    //     dialog.setSize(400, 300);
    //     dialog.setLocationRelativeTo(null);

    //     JTextField tfDriverId = new JTextField(driver.getBusId(), 15);
    //     tfDriverId.setEditable(false);
    //     JTextField tfBusId = new JTextField(driver.getBusId(), 15);
    //     tfBusId.setEditable(false);
    //     JTextField tfDriverName = new JTextField(driver.getDriverName(), 15);
    //     JTextField tfDriverPhone = new JTextField(driver.getDriverPhone(), 15);

    //     JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 8));
    //     inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //     inputPanel.add(new JLabel("Mã tài xế:"));
    //     inputPanel.add(tfDriverId);
    //     inputPanel.add(new JLabel("Mã xe:"));
    //     inputPanel.add(tfBusId);
    //     inputPanel.add(new JLabel("Tên tài xế:"));
    //     inputPanel.add(tfDriverName);
    //     inputPanel.add(new JLabel("Số điện thoại:"));
    //     inputPanel.add(tfDriverPhone);

    //     JButton okButton = new JButton("OK");
    //     okButton.addActionListener(e -> {
    //         if (tfDriverId.getText().trim().isEmpty() ||
    //             tfBusId.getText().trim().isEmpty() ||
    //             tfDriverName.getText().trim().isEmpty() ||
    //             tfDriverPhone.getText().trim().isEmpty()) {
    //             JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }
    //         dialog.dispose();
    //         if (callback != null) {
    //             callback.onUpdateDriverClicked(
    //                 tfDriverId.getText(),
    //                 tfBusId.getText(),
    //                 tfDriverName.getText(),
    //                 tfDriverPhone.getText()
    //             );
    //         }
    //     });

    //     JButton cancelButton = new JButton("Huỷ");
    //     cancelButton.addActionListener(e -> dialog.dispose());
        
    //     JPanel buttonPanel = new JPanel();
    //     buttonPanel.add(okButton);
    //     buttonPanel.add(cancelButton);
    //     JPanel mainPanel = new JPanel(new BorderLayout());
    //     mainPanel.add(inputPanel, BorderLayout.CENTER);
    //     mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    //     dialog.setContentPane(mainPanel);
    //     dialog.setVisible(true);

    //     dialogMap.put(key, dialog);
    // }

    public static void showDeleteDriverDialog(String key, String title, String message, String driverId, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
            if (callback != null) {
                callback.onDeleteDriverClicked(driverId);
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        dialogMap.put(key, dialog);
    }

    public static void showInsertPlanDialog(String key, String title, String message, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);

        JTextField tfPlanId = new JTextField(15);
        JTextField tfBusId = new JTextField(15);
        JTextField tfDriverId = new JTextField(15);
        JTextField tfArriveTime = new JTextField(15);
        JTextField tfStatus = new JTextField(15);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã lịch trình:"));
        inputPanel.add(tfPlanId);
        inputPanel.add(new JLabel("Mã xe:"));
        inputPanel.add(tfBusId);
        inputPanel.add(new JLabel("Mã tài xế:"));
        inputPanel.add(tfDriverId);
        inputPanel.add(new JLabel("Thời gian đến:"));
        inputPanel.add(tfArriveTime);
        inputPanel.add(new JLabel("Trạng thái:"));
        inputPanel.add(tfStatus);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (tfPlanId.getText().trim().isEmpty() ||
                tfBusId.getText().trim().isEmpty() ||
                tfDriverId.getText().trim().isEmpty() ||
                tfArriveTime.getText().trim().isEmpty() ||
                tfStatus.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dialog.dispose();
            if (callback != null) {
                callback.onInsertPlanClicked(
                    tfPlanId.getText(),
                    tfBusId.getText(),
                    tfDriverId.getText(),
                    tfArriveTime.getText(),
                    tfStatus.getText()
                );
            }
        });

        JButton cancelButton = new JButton("Huỷ");
        cancelButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
        dialog.setVisible(true);
        dialogMap.put(key, dialog);
    }

    // public static void showUpdatePlanDialog(String key, String title, String message, Plan plan, DialogCallback callback) {
    //     if (dialogMap.containsKey(key)) {
    //         dialogMap.get(key).dispose();
    //     }

    //     JDialog dialog = new JDialog();
    //     dialog.setTitle(title);
    //     dialog.setModal(true);
    //     dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    //     dialog.setSize(400, 300);
    //     dialog.setLocationRelativeTo(null);

    //     JTextField tfPlanId = new JTextField(plan.getBusId(), 15);
    //     tfPlanId.setEditable(false);
    //     JTextField tfBusId = new JTextField(plan.getBusId(), 15);
    //     tfBusId.setEditable(false);
    //     JTextField tfDriverId = new JTextField(plan.getDriverId(), 15);
    //     tfDriverId.setEditable(false);
    //     JTextField tfArriveTime = new JTextField(plan.getArriveTime(), 15);
    //     JTextField tfStatus = new JTextField(plan.getStatus(), 15);

    //     JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 8));
    //     inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //     inputPanel.add(new JLabel("Mã lịch trình:"));
    //     inputPanel.add(tfPlanId);
    //     inputPanel.add(new JLabel("Mã xe:"));
    //     inputPanel.add(tfBusId);
    //     inputPanel.add(new JLabel("Mã tài xế:"));
    //     inputPanel.add(tfDriverId);
    //     inputPanel.add(new JLabel("Thời gian đến:"));
    //     inputPanel.add(tfArriveTime);
    //     inputPanel.add(new JLabel("Trạng thái:"));
    //     inputPanel.add(tfStatus);

    //     JButton okButton = new JButton("OK");
    //     okButton.addActionListener(e -> {
    //         if (tfPlanId.getText().trim().isEmpty() ||
    //             tfBusId.getText().trim().isEmpty() ||
    //             tfDriverId.getText().trim().isEmpty() ||
    //             tfArriveTime.getText().trim().isEmpty() ||
    //             tfStatus.getText().trim().isEmpty()) {
    //             JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Error", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }
    //         dialog.dispose();
    //         if (callback != null) {
    //             callback.onUpdatePlanClicked(
    //                 tfPlanId.getText(),
    //                 tfBusId.getText(),
    //                 tfDriverId.getText(),
    //                 tfArriveTime.getText(),
    //                 tfStatus.getText()
    //             );
    //         }
    //     });
    //     JButton cancelButton = new JButton("Huỷ");
    //     cancelButton.addActionListener(e -> dialog.dispose());
    //     JPanel buttonPanel = new JPanel();
    //     buttonPanel.add(okButton);
    //     buttonPanel.add(cancelButton);
    //     JPanel mainPanel = new JPanel(new BorderLayout());
    //     mainPanel.add(inputPanel, BorderLayout.CENTER);
    //     mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    //     dialog.setContentPane(mainPanel);
    //     dialog.setVisible(true);
    //     dialogMap.put(key, dialog);
    // }

    public static void showDeletePlanDialog(String key, String title, String message, String planId, DialogCallback callback) {
        if (dialogMap.containsKey(key)) {
            dialogMap.get(key).dispose();
        }

        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            dialog.dispose();
            if (callback != null) {
                callback.onDeletePlanClicked(planId);
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        dialogMap.put(key, dialog);
    }

    public static void showConfirmDialog(String key, String title, String message) {
        showConfirmDialog(key, title, message, null);
    }

    /*
     * Usage example:
     * DialogUtil.showDialog("exampleKey", "Example Title", "This is an example message.", new DialogCallbackAdapter() {
     *   @Override
     *   public void onOkClicked() {
     *       System.out.println("OK clicked");
     *  }
     * 
     */
}
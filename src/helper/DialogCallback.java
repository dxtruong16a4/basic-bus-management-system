package helper;

public interface DialogCallback {
    void onOkClicked();
    void onCancelClicked();
    // Callback methods for insert dialogs
    void onInsertBusClicked(String busId, String busCompany, String busLicensePlate, String busRoute, String busPlan);
    void onInsertDriverClicked(String driverId, String busId, String driverName, String driverPhone);
    void onInsertPlanClicked(String planId, String busId, String driverId, String arriveTime, String status);
    // Callback methods for update dialogs
    void onUpdateBusClicked(String busId, String busCompany, String busLicensePlate, String busRoute, String busPlan);
    void onUpdateDriverClicked(String driverId, String busId, String driverName, String driverPhone);
    void onUpdatePlanClicked(String planId, String busId, String driverId, String arriveTime, String status);
    // Callback methods for delete dialogs
    void onDeleteBusClicked(String busId);
    void onDeleteDriverClicked(String driverId);
    void onDeletePlanClicked(String planId);
}
package helper;

public abstract class DialogCallbackAdapter implements DialogCallback {
    @Override public void onOkClicked() {}
    @Override public void onCancelClicked() {}
    @Override public void onInsertBusClicked(String busId, String busCompany, String busLicensePlate, String busRoute, String busPlan) {}
    @Override public void onInsertDriverClicked(String driverId, String busId, String driverName, String driverPhone) {}
    @Override public void onInsertPlanClicked(String planId, String busId, String driverId, String arriveTime, String status) {}
    @Override public void onUpdateBusClicked(String busId, String busCompany, String busLicensePlate, String busRoute, String busPlan) {}
    @Override public void onUpdateDriverClicked(String driverId, String busId, String driverName, String driverPhone) {}
    @Override public void onUpdatePlanClicked(String planId, String busId, String driverId, String arriveTime, String status) {}
    @Override public void onDeleteBusClicked(String busId) {}
    @Override public void onDeleteDriverClicked(String driverId) {}
    @Override public void onDeletePlanClicked(String planId) {}
}
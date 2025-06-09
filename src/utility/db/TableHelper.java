package utility.db;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class TableHelper {
    public static void loadTable(JTable table, List<Map<String, Object>> data, Map<String, String> columnHeaderMap) {
        DefaultTableModel model = new DefaultTableModel();

        if (data == null || data.isEmpty()) {
            table.setModel(model);
            return;
        }

        List<String> keys;
        if (columnHeaderMap != null && !columnHeaderMap.isEmpty()) {
            keys = new ArrayList<>(columnHeaderMap.keySet());
            for (String key : keys) {
                model.addColumn(columnHeaderMap.get(key));
            }

            for (Map<String, Object> row : data) {
                List<Object> rowData = new ArrayList<>();
                for (String key : keys) {
                    rowData.add(row.get(key));
                }
                model.addRow(rowData.toArray());
            }
        } else {
            Map<String, Object> firstRow = data.get(0);
            keys = new ArrayList<>(firstRow.keySet());
            for (String key : keys) {
                model.addColumn(key);
            }
            for (Map<String, Object> row : data) {
                List<Object> rowData = new ArrayList<>();
                for (String key : keys) {
                    rowData.add(row.get(key));
                }
                model.addRow(rowData.toArray());
            }
        }

        table.setModel(model);
    }

    /**
     * Usage:
     *  Map<String, String> headerMap = new HashMap<>();
        headerMap.put("maxe", "Mã Xe");
        headerMap.put("hangxe", "Hãng Xe");
        headerMap.put("bienso", "Biển Số");
        headerMap.put("tuyen", "Tuyến");
        headerMap.put("lich", "Lịch Trình");
        List<Map<String, Object>> data = busController.SelectQuery(null, null);
        TableHelper.loadTable(jTable1, data, headerMap);
     * 
     */
}

package utility;

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
            // Lấy keys theo thứ tự từ columnHeaderMap
            keys = new ArrayList<>(columnHeaderMap.keySet());
            // Thêm header cột theo thứ tự từ columnHeaderMap
            for (String key : keys) {
                model.addColumn(columnHeaderMap.get(key));
            }

            // Thêm dữ liệu vào các cột theo thứ tự của keys
            for (Map<String, Object> row : data) {
                List<Object> rowData = new ArrayList<>();
                for (String key : keys) {
                    rowData.add(row.get(key));
                }
                model.addRow(rowData.toArray());
            }
        } else {
            // Trường hợp không có columnHeaderMap, lấy keys từ hàng đầu tiên (thứ tự có thể không đảm bảo)
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

/*
 * http://stackoverflow.com/questions/11328451/setting-jcheckbox-invisible-in-jtable
 * answered by Guillaume Polet
 */

package mathhonorsociety;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class TestTable {
    
    private ArrayList<Integer> count = new ArrayList<Integer>();

    public class CheckboxCellRenderer extends JPanel implements TableCellRenderer {

        private JCheckBox checkBox;

        public CheckboxCellRenderer() {
            super(new FlowLayout(FlowLayout.CENTER, 0, 0));
            setOpaque(false);
            checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            checkBox.setHorizontalAlignment(JCheckBox.CENTER);
            add(checkBox);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Object valueInColumn1 = table.getValueAt(row, 0);
            checkBox.setVisible(MainOperations.getMemberList().get(row).tShirt());
            checkBox.setSelected(value instanceof Boolean && (Boolean) value);
            return this;
        }
    }

    private JFrame f;
    private JTable table;

    private class MyObject {
        private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        private String value;

        private boolean selected;
        

        public MyObject(String value) {
            this.value = value;
        }

        public PropertyChangeSupport getPropertyChangeSupport() {
            return propertyChangeSupport;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
            propertyChangeSupport.firePropertyChange("value", null, value);
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            if (this.selected != selected) {
                this.selected = selected;
                propertyChangeSupport.firePropertyChange("selected", !selected, selected);   
            }
        }
        
        public void setSelected(boolean selected, int row) {
            if (this.selected != selected) {
                this.selected = selected;
                propertyChangeSupport.firePropertyChange("selected", !selected, selected);
                
                //count.set(row, count.get(row) + 1);
                int shirtPoints = MainOperations.getMemberList().get(row).getMeetingPointList().get(MainOperations.getMemberList().get(row).getMeetingPointList().size()-1);             
                if (count.get(row)%2 == 0)
                {
                    MainOperations.getMemberList().get(row).changeMeetingPoints(MainOperations.getMemberList().get(row).getMeetingPointList().size()-1, shirtPoints + MainOperations.getPointsPerShirt());
                    count.set(row, count.get(row) + 1);
                }
                else 
                {
                    MainOperations.getMemberList().get(row).changeMeetingPoints(MainOperations.getMemberList().get(row).getMeetingPointList().size()-1, shirtPoints - MainOperations.getPointsPerShirt());
                    count.set(row, count.get(row) + 1);
                }
                
                System.out.println(count.get(row));
                System.out.println(MainOperations.getMemberList().get(row).getMeetingPointList().get(MainOperations.getMemberList().get(row).getMeetingPointList().size()-1));             

            }
        }

    }

    protected void initUI() {
        List<MyObject> objects = new ArrayList<TestTable.MyObject>();
        for (int i = 0; i < MainOperations.getNumberOfMembers(); i++) {
            MyObject object = new MyObject(MainOperations.getMemberList().get(i).getLastName() + ", " + MainOperations.getMemberList().get(i).getFirstName());
            count.add(0);
            objects.add(object);
        }
        table = new JTable(new MyTableModel(objects));
        table.setRowHeight(20);
        table.getColumnModel().getColumn(1).setCellRenderer(new CheckboxCellRenderer());
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JScrollPane(table), BorderLayout.CENTER);
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);

    }

    public class MyTableModel extends AbstractTableModel implements PropertyChangeListener {

        private List<MyObject> objects;

        public MyTableModel(List<MyObject> objects) {
            super();
            this.objects = objects;
            for (MyObject object : objects) {
                object.getPropertyChangeSupport().addPropertyChangeListener(this);
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() instanceof MyObject) {
                int index = objects.indexOf(evt.getSource());
                fireTableRowsUpdated(index, index);
            }
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return objects.size();
        }

        public MyObject getValueAt(int row) {
            return objects.get(row);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
            case 0:
                return getValueAt(rowIndex).getValue();
            case 1:
                return getValueAt(rowIndex).isSelected();
            }
            return null;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value), rowIndex);  
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1 && getValueAt(rowIndex).getValue() != null;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            switch (column) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
            }
            return Object.class;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
            case 0:
                return "Member";
            case 1:
                return "Selected";
            }
            return null;
        }

    }
    
    public JFrame getJFrame()
    {
        return f;
    }
    
    public void closeWindow()
    {
        f.setVisible(false);
        f.dispose();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TestTable().initUI();
            }
        });
    }

}
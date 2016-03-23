/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mathhonorsociety;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ry543
 */
public class TableCheckBox extends AbstractCellEditor implements TableCellEditor, TableCellRenderer
{
	private static final long serialVersionUID = 5647725208335645741L;

	public interface TableCheckBoxPressedHandler
    {
        /**
         * Called when the button is pressed.
         * @param row The row in which the button is in the table.
         * @param column The column the button is in in the table.
         */
        void onButtonPress(int row, int column);
    }

    private List<TableCheckBoxPressedHandler> handlers;
    private Hashtable<Integer, JCheckBox> checkboxes;

    public TableCheckBox()
    {
        handlers = new ArrayList<TableCheckBoxPressedHandler>();
        checkboxes = new Hashtable<Integer, JCheckBox>();
    }

    /**
     * Add a slide callback handler
     * @param handler
     */
    public void addHandler(TableCheckBoxPressedHandler handler)
    {
        if (handlers != null)
        {
            handlers.add(handler);
        }
    }

    /**
     * Remove a slide callback handler
     * @param handler
     */
    public void removeHandler(TableCheckBoxPressedHandler handler)
    {
        if (handlers != null)
        {
            handlers.remove(handler);
        }
    }


    /**
     * Removes the component at that row index
     * @param row The row index which was just removed
     */
    public void removeRow(int row)
    {
    	if(checkboxes.containsKey(row))
        {
            checkboxes.remove(row);
        }
    }

    /**
     * Moves the component at oldRow index to newRow index
     * @param oldRow The old row index
     * @param newRow THe new row index
     */
    public void moveRow(int oldRow, int newRow)
    {
    	if(checkboxes.containsKey(oldRow))
        {
    		JCheckBox checkbox = checkboxes.remove(oldRow);
    		checkboxes.put(newRow, checkbox);
        }
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, final int row, final int column)
    {
        JCheckBox checkbox = null;
        if(checkboxes.containsKey(row))
        {
            checkbox = checkboxes.get(row);
        }
        else
        {
            checkbox = new JCheckBox();
           /* if(value != null && value instanceof String)
            {
                button.setText((String)value);
            } */
            checkbox.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    if(handlers != null)
                    {
                        for(TableCheckBoxPressedHandler handler : handlers)
                        {
                            handler.onButtonPress(row, column);
                        }
                    }
                }
            });
            checkboxes.put(row, checkbox);
        }

        return checkbox;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column)
    {
        JCheckBox checkbox = null;
        if(checkboxes.containsKey(row))
        {
            checkbox = checkboxes.get(row);
        }
        else
        {
            checkbox = new JCheckBox();
           /* if(value != null && value instanceof String)
            {
                button.setText((String)value);
            } */

            checkboxes.put(row, checkbox);
        }

        return checkbox;
    }

    public void setButtonText(int row, String text)
    {
        JCheckBox checkbox = null;
        if(checkboxes.containsKey(row))
        {
            checkbox = checkboxes.get(row);
            checkbox.setText(text);
        }
    }
    
    public void setVisibilityOfBoxes()
    {
        for (int row = 0; row < MainOperations.getNumberOfMembers(); row++)
        {
            JCheckBox checkbox = checkboxes.get(row);
            String name = (String)MainClass.getMenuWindow1().getEmbedding().getTable().getModel().getValueAt(row, 0);
            String nameLast = name.substring(0, name.indexOf(","));
            String nameFirst = name.substring(name.indexOf(",")+2);
            int memberIndex = MainOperations.findIndexOfMember(nameFirst, nameLast);
            if (!(MainOperations.getMemberList().get(memberIndex).tShirt()))
            {
                checkbox.setVisible(false);
            }
        }
        
    }

    public Object getCellEditorValue()
    {
        return null;
    }

    public void dispose()
    {
        if (handlers != null)
        {
            handlers.clear();
        }
    }
}


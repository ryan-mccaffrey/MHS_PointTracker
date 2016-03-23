package mathhonorsociety;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import mathhonorsociety.MainClass;
//import mathhonorsociety.MainOperations;
//import mathhonorsociety.TableCheckBox;

/**
 * A demo illustrating the embedding of a slider and a button control into a
 * JTable
 * <p/>
 * Copyright (C) 2010 by Ilya Volodarsky
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
public class SwingEmbedding extends JFrame 
{
	private static final long serialVersionUID = 6788933562312584275L;
        private int memberIndex = 0;
        private ArrayList<Integer> count = new ArrayList<Integer>();
       // private ArrayList<Integer> count2 = new ArrayList<Integer>();
        private TableCheckBox checkboxes2;

	@SuppressWarnings({ "unchecked", "serial" })
	public SwingEmbedding()
	{
		initComponents();
		
		// important to set the columns that will have embedded elements as "Editable"
		DefaultTableModel model = new DefaultTableModel(new String [] {"Member", "Attendance", /*"T-Shirt"*/ }, 0) 
		{
	            Class[] types = new Class[]
	            {
	                 java.lang.String.class, java.lang.String.class/*, java.lang.String.class*/
	            };

	            public Class getColumnClass(int columnIndex)
	            {
	                return types[columnIndex];
	            }

	            boolean[] canEdit = new boolean [] {
	                false, true/*, true*/
	            };

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
		};
		table.setModel(model);
		
		// sets the table columns to use the button/slider renderer and editor component
        TableColumn checkboxColumn = table.getColumnModel().getColumn(1);
        
        TableCheckBox checkboxes = new TableCheckBox();
        checkboxes.addHandler(new TableCheckBox.TableCheckBoxPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) 
			{
				String studentName = (String)table.getValueAt(row, 0);
                                String lastName1 = studentName.substring(0, studentName.indexOf(","));
                                String firstName1 = studentName.substring(studentName.indexOf(",")+2);
                                int index1 = MainOperations.findIndexOfMember(firstName1, lastName1);
                                int datePoints = MainOperations.getMemberList().get(index1).getMeetingPointList().get(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1);
                                if (count.get(row)%2 == 0)
                                {
                                    MainOperations.getMemberList().get(index1).changeMeetingPoints(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1, datePoints + MainOperations.getPointsPerMeeting());
                                    count.set(row, count.get(row) + 1);
                                }
                                else
                                {
                                    MainOperations.getMemberList().get(index1).changeMeetingPoints(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1, datePoints - MainOperations.getPointsPerMeeting());
                                    count.set(row, count.get(row) + 1);
                                }
                                System.out.println(MainOperations.getMemberList().get(index1).getMeetingPointList().get(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1));
			}
		});

		// set the column's renderer and editor as the control
		checkboxColumn.setCellRenderer(checkboxes);
		checkboxColumn.setCellEditor(checkboxes);
        
/*	TableColumn checkboxColumn2 = table.getColumnModel().getColumn(2);

        checkboxes2 = new TableCheckBox();
        checkboxes2.addHandler(new TableCheckBox.TableCheckBoxPressedHandler() {
			
			@Override
			public void onButtonPress(int row, int column) {
				String studentName = (String)table.getValueAt(row, 0);
                                String lastName1 = studentName.substring(0, studentName.indexOf(","));
                                String firstName1 = studentName.substring(studentName.indexOf(",")+2);
                                int index1 = MainOperations.findIndexOfMember(firstName1, lastName1);
                                int datePoints = MainOperations.getMemberList().get(index1).getMeetingPointList().get(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1);
                                if (count2.get(row)%2 == 0)
                                {
                                    MainOperations.getMemberList().get(index1).changePoints(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1, datePoints + MainOperations.getPointsPerShirt());
                                    count2.set(row, count2.get(row) + 1);
                                }
                                else
                                {
                                    MainOperations.getMemberList().get(index1).changePoints(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1, datePoints - MainOperations.getPointsPerShirt());
                                    count2.set(row, count2.get(row) + 1);
                                }
                                System.out.println(MainOperations.getMemberList().get(index1).getMeetingPointList().get(MainOperations.getMemberList().get(index1).getMeetingPointList().size()-1));
				
			}
		}); 
        
        checkboxColumn2.setCellRenderer(checkboxes2);
        checkboxColumn2.setCellEditor(checkboxes2);
  */      
        for (int i = 0; i < MainOperations.getNumberOfMembers(); i++)
                {
                    this.addElement();
                    count.add(0);
                   // count2.add(0);
                    memberIndex++;
                }
	}

	public void addElement()
        {
		DefaultTableModel model = (DefaultTableModel) table.getModel();	
		model.addRow(new Object[] {MainOperations.getMemberList().get(memberIndex).getLastName() + ", " + MainOperations.getMemberList().get(memberIndex).getFirstName(), ""});
	}

	/**
	 * Initializes components, code generated by NetBeans GUI editor
	 */
	private void initComponents() 
	{
		setTitle("Add Meeting Points");
		
		jScrollPane1 = new javax.swing.JScrollPane();
		table = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jScrollPane1.setViewportView(table);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300,
				Short.MAX_VALUE));

		pack();
	}
        
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable table;
        
        public javax.swing.JTable getTable()
        {
            return table;
        }
        
        public int getMemberIndex()
        {
            return memberIndex;
        }
               

	public static void main(String[] args) 
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		SwingEmbedding embedding = new SwingEmbedding();
		embedding.setVisible(true);
		
               /* for (int i = 0; i < MainOperations.getNumberOfMembers(); i++)
                {
                    embedding.addElement();
                    memberIndex++;
                } */
		
	}

}

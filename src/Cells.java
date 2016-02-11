import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;

public class Cells {
		private JPanel panel_main;
		private int[][] tableIntCells;
		private JLabel[][] tablicaJLabel;
				
		public static Cells cells = new Cells();
		public static Cells getInstance(){
			if(cells != null) return cells;
			else return new Cells();
		}
		private Cells(){
		}

		public JPanel getPanel_main() {
			return panel_main;
		}

		public void setPanel_main(JPanel panel_main) {
			this.panel_main = panel_main;
		}

		public int[][] getTableIntCells() {
			return tableIntCells;
		}

		public void setTableIntCells(int[][] tableIntCells) {
			this.tableIntCells = tableIntCells;
		}

		public JLabel[][] getTablicaJLabel() {
			return tablicaJLabel;
		}

		public void setTablicaJLabel(JLabel[][] tablicaJLabel) {
			this.tablicaJLabel = tablicaJLabel;
		}
		public void makeAllLabelsWhite(JSpinner spinner_size_of_table, JPanel panel_main){
			this.panel_main = panel_main;
			int size = (int) spinner_size_of_table.getValue();
			panel_main.removeAll();
			panel_main.setLayout(new GridLayout(size, size));
			
			tablicaJLabel = new JLabel[size][size];
			tableIntCells = new int[size][size];
			
			for(int i = 0; i < size; i++){
	    		for(int j = 0; j < size; j++){
	    			JLabel label = new JLabel();
	    			label.setSize(15, 15);
	    			label.setBackground(Color.WHITE);
	    			label.setOpaque(true);
					Border border = BorderFactory.createLineBorder(Color.black, 1);
			        label.setBorder(border);
			        tablicaJLabel[i][j] = label;
					panel_main.add(label);
					tableIntCells[i][j] = 0;
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel label = (JLabel) e.getSource();
							if(GUI.rdbtnLangtonsAnt.isSelected()){
								switch (label.getText()) {
									case "": label.setText("N"); break;
									case "N": label.setText("E"); break;
									case "E": label.setText("S"); break;
									case "S": label.setText("W"); break;
									case "W": label.setText("N"); break;
								} 
								label.setFont(new Font("Tunga", Font.PLAIN, 16));
								label.setBackground(Color.GREEN);
							}
							else{
								if(label.getBackground().equals(Color.WHITE)){
									label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
									label.setBackground(Color.BLACK);
								}
								else{
									label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
									label.setBackground(Color.WHITE);
								}	
							}
									        
						}
					});
	    		}
			}
			panel_main.repaint();
			panel_main.validate();
		}
		void makeArrayIntFromArrayJLabel(){
			int size = tablicaJLabel[0].length;
			for(int i = 0; i < size; i++){
	    		for(int j = 0; j < size; j++){
	    			if(tablicaJLabel[i][j].getBackground().equals(Color.BLACK))
	    				tableIntCells[i][j] = 1;
	    			else tableIntCells[i][j] = 0;
	    		}
			}
		}
		
		void showCells(int[][] tableCells, JLabel[][] tablicaJLabel,JPanel panel_main){
			int size = tableCells.length;
			for(int i = 0; i < size; i++){
	    		for(int j = 0; j < size; j++){
	    			if(tableCells[i][j] == 1){
	    				tablicaJLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    				tablicaJLabel[i][j].setBackground(Color.BLACK);
	    			}
	    			else{
	    				tablicaJLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    				tablicaJLabel[i][j].setBackground(Color.WHITE);
	    				}
	    		}
			}
			panel_main.repaint();
			panel_main.validate();
		}
	
		public void randomGenerateCells(JSpinner spinner_size_of_table, JSpinner spinner_probability_of_dead_cells, JPanel panel_main){
			Random rand = new Random();
			int size = (int) spinner_size_of_table.getValue();
			int p = (int) spinner_probability_of_dead_cells.getValue();
			makeAllLabelsWhite(spinner_size_of_table, panel_main);
			for(int i = 0; i < size; i++){
	    		for(int j = 0; j < size; j++){
	    			if(rand.nextInt(100) < p)
	    				tablicaJLabel[i][j].setBackground(Color.black);
	    		}
			}
			panel_main.repaint();
			panel_main.validate();
		}	
		
		public void startGameOfLife(){
			makeArrayIntFromArrayJLabel();
			int size = tableIntCells.length;
			int[][] tab1 = new int[size][size];
//			System.arraycopy(tableIntCells, 0, tab1, 0, size);
			
			for(int i = 0; i < size; i++){
	    		for(int j = 0; j < size; j++){
	    			int s = neighborhoodCells(i,j,size,tableIntCells);
	    			
	    			if(tableIntCells[i][j] == 1){
	    				if(s == 2 || s== 3){
		    				tab1[i][j] = 1;
	    				}
	    				else{
	    					tab1[i][j] = 0;
	    				}
	    			}
	    			else{
	    				if(s >= 3 ){
		    				tab1[i][j] = 1;
	    				}
	    				else{
	    					tab1[i][j] = 0;
	    				}
	    			}
	    		}
			}	
			System.arraycopy(tab1, 0, tableIntCells, 0, tableIntCells.length);
			showCells(tableIntCells, tablicaJLabel, panel_main);	
			panel_main.repaint();
			panel_main.validate();
		}
		
		private int neighborhoodCells(int i, int j, int size, int[][] tab){
			if(i==0 && j==0)
				{int temp =  
				 tab[i+1][j]+tab[i][j+1]+tab[i+1][j+1];return temp;
				}
			if(i==size-1 && j==0)
				{int temp =  
				 tab[i-1][j]+tab[i][j+1]+tab[i-1][j+1];return temp;
				}
			if(i==size-1 && j==size-1)
				{int temp =  
				 tab[i-1][j]+tab[i][j-1]+tab[i-1][j-1];return temp;
				}
			if(i==0 && j==size-1)
				{int temp =  
				 tab[i+1][j]+tab[i][j-1]+tab[i+1][j-1];return temp;
				}
			
			if(j==0)
				{int temp =  
				 tab[i-1][j]+tab[i+1][j]+tab[i][j+1]+tab[i-1][j+1]+tab[i+1][j+1];return temp;
				}
			if(i==size-1)
				{int temp =  
				 tab[i][j-1]+tab[i][j+1]+tab[i-1][j]+tab[i-1][j-1]+tab[i-1][j+1];return temp;
				}
			if(j==size-1)
				{int temp =  
				 tab[i+1][j]+tab[i-1][j]+tab[i][j-1]+tab[i+1][j-1]+tab[i-1][j-1];return temp;
				}
			if(i==0)
				{int temp =  
				 tab[i][j-1]+tab[i][j+1]+tab[i+1][j]+tab[i+1][j-1]+tab[i+1][j+1];return temp;
				}
			int temp =  
			 tab[i-1][j-1]+tab[i-1][j]+tab[i-1][j+1]+tab[i][j+1]+tab[i+1][j+1]+tab[i+1][j]+tab[i+1][j-1]+tab[i][j-1];return temp;
		}
}


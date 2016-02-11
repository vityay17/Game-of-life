import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JFrame{
	private JPanel contentPane;
	static GUI frame;
	private Cells cells = Cells.getInstance(); 
	private GameofLife gameofLife;
	static JRadioButton rdbtnTheGameOf;
	static JRadioButton rdbtnLangtonsAnt;
	
	boolean isGameOfLife = true,
			isAntLangthon = false;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 956, 718);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 751, 658);
		contentPane.add(scrollPane);
		
		JPanel panel_main = new JPanel();
		scrollPane.setViewportView(panel_main);
		
		JSpinner spinner_size_of_table = new JSpinner();
		spinner_size_of_table.setModel(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(1)));
		
		JLabel lblParameters = new JLabel("Parameters:");
		lblParameters.setFont(new Font("Tunga", Font.PLAIN, 20));
		lblParameters.setBounds(771, 71, 94, 17);
		contentPane.add(lblParameters);
		
		JLabel lbl_Size = new JLabel("Size:");
		lbl_Size.setLabelFor(spinner_size_of_table);
		lbl_Size.setFont(new Font("Tunga", Font.PLAIN, 20));
		lbl_Size.setBounds(771, 113, 45, 17);
		contentPane.add(lbl_Size);
		
		spinner_size_of_table.setBounds(815, 99, 118, 32);
		contentPane.add(spinner_size_of_table);
		
		JSpinner spinner_probability_of_dead_cells = new JSpinner();
		spinner_probability_of_dead_cells.setToolTipText("The probability of dead cells");
		spinner_probability_of_dead_cells.setModel(new SpinnerNumberModel(50, 0, 100, 10));
		spinner_probability_of_dead_cells.setBounds(791, 142, 142, 32);
		contentPane.add(spinner_probability_of_dead_cells);
		
		JLabel lblP = new JLabel("P:");
		lblP.setFont(new Font("Tunga", Font.PLAIN, 20));
		lblP.setBounds(771, 156, 22, 17);
		contentPane.add(lblP);
		lblP.setLabelFor(spinner_probability_of_dead_cells);
		
		JButton btn_Start_simulation = new JButton("Start simulation");
		
		btn_Start_simulation.setFont(new Font("Tunga", Font.PLAIN, 20));
		btn_Start_simulation.setBounds(771, 185, 162, 32);
		contentPane.add(btn_Start_simulation);
		
		JButton btn_1_Step = new JButton("1 step");
		
		btn_1_Step.setFont(new Font("Tunga", Font.PLAIN, 20));
		btn_1_Step.setBounds(771, 228, 162, 32);
		contentPane.add(btn_1_Step);
		
		JButton btn_Stop_simulation = new JButton("Stop");
		
		btn_Stop_simulation.setToolTipText("Stop simulation");
		btn_Stop_simulation.setForeground(Color.BLACK);
		btn_Stop_simulation.setBackground(Color.RED);
		btn_Stop_simulation.setFont(new Font("Tunga", Font.PLAIN, 20));
		btn_Stop_simulation.setBounds(771, 271, 162, 32);
		contentPane.add(btn_Stop_simulation);
		
		JButton btn_Random = new JButton("Random");
		btn_Random.setFont(new Font("Tunga", Font.PLAIN, 20));
		btn_Random.setBounds(771, 314, 162, 32);
		contentPane.add(btn_Random);

		rdbtnLangtonsAnt = new JRadioButton("Langtons Ant");
		rdbtnLangtonsAnt.setFont(new Font("Tunga", Font.PLAIN, 20));
		rdbtnLangtonsAnt.setBounds(767, 11, 162, 25);
		contentPane.add(rdbtnLangtonsAnt);
		
		rdbtnTheGameOf = new JRadioButton("The game of life");
		rdbtnTheGameOf.setFont(new Font("Tunga", Font.PLAIN, 20));
		rdbtnTheGameOf.setBounds(767, 39, 162, 25);
		contentPane.add(rdbtnTheGameOf);
		
		rdbtnTheGameOf.setSelected(true);
		cells.makeAllLabelsWhite(spinner_size_of_table, panel_main);
		
		spinner_size_of_table.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cells.makeAllLabelsWhite(spinner_size_of_table, panel_main);
			}
		});
		
		btn_Random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cells.randomGenerateCells(spinner_size_of_table, spinner_probability_of_dead_cells, panel_main);
			}
		});
		
		btn_Start_simulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Start_simulation.setEnabled(false);
				btn_Stop_simulation.setEnabled(true);
				if(rdbtnTheGameOf.isSelected())
					(gameofLife =  new GameofLife()).execute();
			}
		});
		
		btn_Stop_simulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Start_simulation.setEnabled(true);
				btn_Stop_simulation.setEnabled(false);
				gameofLife.cancel(true);
				gameofLife = null;
			}
		});
		
		btn_1_Step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnTheGameOf.isSelected())
					cells.startGameOfLife();
			}
		});	
		
		rdbtnTheGameOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnLangtonsAnt.setSelected(false);
			}
		});
		
		rdbtnLangtonsAnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnTheGameOf.setSelected(false);
			}
		});
	}	

	private class GameofLife extends SwingWorker<Void, Void>{

		@Override
		protected Void doInBackground() throws Exception {
			Cells cells = Cells.getInstance();
			while (!isCancelled()){
				cells.startGameOfLife();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}
		}
			return null;
		
		}
	}
}

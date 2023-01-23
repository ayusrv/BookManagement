package BookManagement;

import java.awt.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

//import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EtchedBorder;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
//		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			//Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Creating a connection
			
			String url = "jdbc:mysql://localhost/book";
			String username = "root";
			String password = "admin";
			con = DriverManager.getConnection(url,username,password);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void table_load()
	{
		try
		{
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			while(rs.next()) {
				String name = rs.getString(2);
				String price = rs.getString(4);
				System.out.print(name);
				System.out.print(price);
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(new Color(0, 0, 51));
		frame.setForeground(new Color(0, 0, 51));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Study\\study material\\project\\OIP.jpg"));
		frame.getContentPane().setBackground(new Color(51, 204, 204));
		frame.setBounds(100, 100, 806, 545);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(321, 11, 146, 74);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 204, 153));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Regisration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 102, 51)));
		panel.setBounds(32, 90, 378, 218);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 45, 92, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 94, 92, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 147, 65, 14);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBackground(Color.WHITE);
		txtbname.setBounds(132, 44, 218, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(132, 93, 218, 20);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(132, 146, 218, 20);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(new Color(51, 204, 153));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price;
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				try {
					pst = con.prepareStatement("insert into book (name,edition,price) values (?,?,?)");
					pst.setString(1,bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record added!!!");
					table_load();
					
					//This will empty the text box after the record has been added
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					
					//This will focus your mouse on the bname box or automatically select the bname
					txtbname.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(32, 319, 98, 52);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEdit = new JButton("Exit");
		btnEdit.setBackground(new Color(51, 204, 153));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnEdit.setBounds(174, 319, 98, 52);
		frame.getContentPane().add(btnEdit);
		
		JButton btnExit = new JButton("Clear");
		btnExit.setBackground(new Color(51, 204, 153));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//This will empty the text box after the record has been added
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				
				//This will focus your mouse on the bname box or automatically select the bname
				txtbname.requestFocus();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnExit.setBounds(312, 319, 98, 52);
		frame.getContentPane().add(btnExit);
		
		table = new JTable();
		table.setBackground(new Color(255, 128, 255));
		table.setBounds(655, 271, -199, -163);
		frame.getContentPane().add(table);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(438, 96, 331, 278);
		frame.getContentPane().add(scrollPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(new Color(51, 204, 153));
		scrollPane.setViewportView(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 204, 153));
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 102, 51)));
		panel_1.setBounds(32, 395, 378, 74);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtbid = new JTextField();
		txtbid.setBackground(Color.WHITE);
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String id = txtbid.getText();
				try {
					pst = con.prepareStatement("select name,edition,price from book where id = ?");
					pst.setString(1,id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true) {
						String name = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
						
					}
					
					else {
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(132, 25, 210, 27);
		panel_1.add(txtbid);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("BookID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(21, 23, 332, 26);
		panel_1.add(lblNewLabel_1_1_1);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(new Color(51, 204, 153));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,edition,price,bid;
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtbid.getText();
				
				try {
					pst = con.prepareStatement("update book set name=?,edition=?,price=? where id=?");
					pst.setString(1,bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!!");
//					table_load();
					
					//This will empty the text box after the record has been added
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					
					//This will focus your mouse on the bname box or automatically select the bname
					txtbname.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnUpdate.setBounds(448, 391, 138, 52);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(51, 204, 153));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				
				bid = txtbid.getText();
				
				try {
					pst = con.prepareStatement("delete from book where id=?");
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!!!");
//					table_load();
					
					//This will empty the text box after the record has been added
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					
					//This will focus your mouse on the bname box or automatically select the bname
					txtbname.requestFocus();
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		btnDelete.setBounds(631, 391, 138, 52);
		frame.getContentPane().add(btnDelete);
	}
}

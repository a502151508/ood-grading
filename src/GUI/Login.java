package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class Login{
 
    public static void main(String[] args){
        Login gui = new Login();
        gui.init();
    }
    public void init(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(); 
        JLabel lblPassword = new JLabel("Instructor Code:");
        JTextField txtPassword = new JTextField(20);
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new LoginListener());


        panel.add(lblPassword);
		panel.add(txtPassword);     
		panel.add(btnLogin);    
        frame.getContentPane().add(BorderLayout.CENTER,panel);
 
 
 
        frame.setSize(300,300);
        frame.setVisible(true);
 
 
    }
 
    public class LoginListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
             
            JOptionPane.showMessageDialog(null,"Login Successfull");                    
        }
    }
   
}
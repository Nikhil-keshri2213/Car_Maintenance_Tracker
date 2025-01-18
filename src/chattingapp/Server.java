package chattingapp;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;

public class Server implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.setSize(450,700);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLayout(null);
        
        JPanel pl = new JPanel();
        //pl.setBackground(new Color(7,94,84));
        pl.setBackground(Color.BLACK);
        pl.setBounds(0,0, 450, 50);
        pl.setLayout(null);
        f.add(pl);
        
        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("icons/close-fill.png"));
        Image img = ic.getImage().getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        ImageIcon ic2 = new ImageIcon(img);
        
        JLabel close = new JLabel(ic2);
        close.setBounds(5, 15, 25, 25);
        pl.add(close);
        
        close.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        
        ImageIcon icp = new ImageIcon(ClassLoader.getSystemResource("icons/user-fill1.png"));
        Image imgPro = icp.getImage().getScaledInstance(35, 35, Image.SCALE_AREA_AVERAGING);
        ImageIcon ic2p = new ImageIcon(imgPro);
        
        
        JLabel profile = new JLabel(ic2p);
        profile.setBounds(40, 10, 35, 35);
        pl.add(profile);
        
        
        JLabel name = new JLabel("John");
        name.setBounds(100,10,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD, 20));
        pl.add(name);
        
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(100,25,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.PLAIN, 14));
        pl.add(status);
        
        
        a1 = new JPanel();
        a1.setBounds(5,55,425,560);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5,620,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        f.add(text);
        
        JButton send = new JButton("SEND");
        send.setBounds(320,620,110,40);
        send.setBackground(Color.BLACK );
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        send.addActionListener(this);
        f.add(send);
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try{
            String output = text.getText();
            //JLabel out = new JLabel(output);

            JPanel p1 = formatLable(output);
            //p1.add(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p1, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(output);
            text.setText(null);

            f.repaint();
            f.invalidate();
            f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLable(String s){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
//        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+s+"</html>");
        JLabel output = new JLabel(s);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(Color.GRAY);
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String arg[]){
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLable(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch(Exception e){
        
        }
    }
}
 
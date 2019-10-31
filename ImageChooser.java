import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

public class ImageChooser extends JPanel implements ActionListener{
    private JFileChooser fileChooser;
    private JFrame f, f2;
    protected JFrame f1;
    private JMenu m;
    private JMenuBar mb;
    private JMenuItem i, l;
    private File file;
    private int returnVal;
    ImageIcon MyImage, MyImage1;
    private JTextArea ta;
    private JTextField t, y;
    private JCheckBox cb1, cb2;
    private JLabel label, lab1, lab2, lab3, lab4, lab5, lab6;
    private JButton b1, b2, b3, b4;
    private JPanel pan, pan1;
    private JRadioButton rb1, rb2;
    private BufferedImage buffered;
    

    public ImageChooser(){
        fileChooser = new JFileChooser();
        f = new JFrame("Image Chooser Application");
        f1 = new JFrame("Option Chooser");
        f2 = new JFrame("Encode or Decode");
        i = new JMenuItem("Open Image");
        l = new JMenuItem("Encoded Image");
        m = new JMenu("File");
        t = new JTextField();
        y = new JTextField();
        mb = new JMenuBar();
        label = new JLabel();
        lab1 = new JLabel("Enter Text to be stored:");
        lab3 = new JLabel("Q) Hide or Un-Hide?");
        lab3.setForeground(Color.white);
        lab4 = new JLabel("Encoded Picture");
        lab5 = new JLabel();
        lab6 = new JLabel("Image Steganography");
        lab6.setForeground(Color.white);
        lab6.setBounds(180, 20, 400, 120);
        lab6.setFont(new Font("Calibri", Font.BOLD , 28));
        ta= new JTextArea();
        pan = new JPanel();
        pan1 = new JPanel();
        b1 = new JButton("Encode");
        b2= new JButton("Decode");
        b3 = new JButton("Encode");
        b4 = new JButton("Decode");
        lab2 = new JLabel("Q) Enter method you want to use for steganography:");
        lab2.setForeground(Color.white);
        lab2.setBounds(50, 150, 300, 30);
        rb1 = new JRadioButton("DCT");
        rb2 = new JRadioButton("LSB");
        cb1 = new JCheckBox("Hide");
        cb2 = new JCheckBox("Un-Hide");
        cb1.addActionListener(this);
        cb2.addActionListener(this);
        rb1.setBounds(50, 190, 100, 30);
        rb2.setBounds(50, 240, 100, 30);
        rb1.addActionListener(this);
        rb2.addActionListener(this);
        lab3.setBounds(430, 150, 150, 30);
        cb1.setBounds(450, 190, 100, 30);
        cb2.setBounds(450, 240, 100, 30);
        b3.setBounds(20, 220, 100, 30);
        f1.add(lab2); f1.add(rb1); f1.add(rb2);
        f1.add(lab3); f1.add(cb1); f1.add(cb2);
        f1.add(lab6);
        f1.setSize(650, 450);
        f1.getContentPane().setBackground(Color.black);
        f1.setLayout(new BorderLayout());
        f1.setVisible(true);
        f1.setDefaultCloseOperation(f1.HIDE_ON_CLOSE);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(rb1.isSelected() && cb1.isSelected())
        {

            t.setBounds(20, 50, 200, 40);
            label.setBounds(30,200,350,250);
            lab4.setBounds(450, 130, 250, 70);
            lab4.setFont(new Font("Courier New", Font.BOLD, 20));
            lab5.setBounds(450,200,350,250);
            lab1.setBounds(20, 10, 200, 30);
            b3.setBounds(30, 100, 100, 40);
            mb.setBounds(0,0,50,30);
            i.addActionListener(this);
            l.addActionListener(this);
            m.add(i); m.add(l);
            mb.add(m); f.add(b3);
            f.add(mb); f.add(lab1);f.add(t);
            f.add(lab4); f.add(lab5);
            f.add(label); f.add(pan);
            f.setJMenuBar(mb);

            if(e.getSource() == i)
            {
                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    label.setIcon(ResizeImage(path));
                }
                File f4=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    DCTSteganography dct=new DCTSteganography(f4);
                    FileOutputStream fs=new FileOutputStream("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\keys.bin");
                    ObjectOutputStream os=new ObjectOutputStream(fs);
                    dct.hide(t.getText(),os);
                    int pix[]=dct.constructPixels();
                   dct.writeImage(new File("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\hassan.png"), pix);
                    os.close();
                    fs.close();
                } catch (IOException ex) {
                    System.out.println("ERRoR" + ex.toString());
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
            if(e.getSource() == l)
            {

                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    lab5.setIcon(resizeImage(path));
                }
            }
            f.getContentPane().setBackground(Color.CYAN);
            f.setSize(1000, 600);
            f.setVisible(true);
            f.setLayout(null);
            f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        }
        if(rb2.isSelected() && cb1.isSelected())
        {
            t.setBounds(20, 50, 200, 40);
            label.setBounds(30,200,350,250);
            lab4.setBounds(450, 130, 250, 70);
            lab4.setFont(new Font("Courier New", Font.BOLD, 20));
            lab5.setBounds(450,200,350,250);
            lab1.setBounds(20, 10, 200, 30);
            b3.setBounds(30, 100, 100, 40);
            mb.setBounds(0,0,50,30);
            i.addActionListener(this);
            l.addActionListener(this);
            m.add(i); m.add(l);
            mb.add(m); f.add(b3);
            f.add(mb); f.add(lab1);f.add(t);
            f.add(lab4); f.add(lab5);
            f.add(label); f.add(pan);
            f.setJMenuBar(mb);
            if(e.getSource() == i)
            {
                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    label.setIcon(ResizeImage(path));
                }
                File f6=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    LSB dct=new LSB(f6);
                    FileOutputStream fs=new FileOutputStream("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\keys.bin");
                    ObjectOutputStream os=new ObjectOutputStream(fs);
                    dct.Hide(t.getText());
                    int pix[]=dct.constructPixels();
                    dct.writeImage(new File("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\output.png"), pix);
                    os.close();
                    fs.close();
                } catch (IOException ex) {
                    System.out.println("ERRoR"+ex.toString());
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
            if(e.getSource() == l)
            {

                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    lab5.setIcon(resizeImage(path));
                }
            }
            f.getContentPane().setBackground(Color.CYAN);
            f.setSize(1000, 600);
            f.setVisible(true);
            f.setLayout(null);
            f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        }
        if(rb2.isSelected() && cb2.isSelected())
        {
            b3.setBounds(30, 150, 100, 40);
            b3.setText("Decode");
            t.setBounds(20, 100, 200, 40);
            lab1.setText("Decoded Text:");
            label.setBounds(30,200,350,250);
            lab1.setBounds(20, 30, 200, 50);
            l.addActionListener(this);
            lab1.setFont(new Font("Calibri", Font.BOLD, 16));
            mb.setBounds(0,0,50,30);
            i.addActionListener(this);
            m.add(i);// m.add(l);
            mb.add(m); f.add(b3);
            f.add(mb); f.add(lab1);f.add(t); //f.add(b1);
            f.add(label); f.add(pan);
            f.setJMenuBar(mb);
            if(e.getSource() == i)
            {
                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    label.setIcon(ResizeImage(path));
                }
                File f6=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    LSB dct=new LSB(f6);
                    StringBuilder sb=new StringBuilder();
                    FileInputStream fs=new FileInputStream("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\keys.bin");
                    ObjectInputStream is=new ObjectInputStream(fs);

                    dct.unHide(sb);
                    t.setText(""+sb);
                } catch (IOException ex) {
                    System.err.println("ERROR"+" "+ex.getMessage());
                    System.exit(0);
                }

            }
            f.getContentPane().setBackground(Color.CYAN);
            f.setSize(600, 600);
            f.setVisible(true);
            f.setLayout(null);
            f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        }
        if(rb1.isSelected() && cb2.isSelected())
        {
            b3.setBounds(30, 150, 100, 40);
            b3.setText("Decode");
            t.setBounds(20, 100, 200, 40);
            label.setBounds(30,200,350,250);
            lab1.setBounds(20, 30, 200, 60);
            lab1.setText("Text Decoded");
            lab1.setFont(new Font("Calibri", Font.BOLD, 16));
            mb.setBounds(0,0,50,30);
            i.addActionListener(this);
            l.addActionListener(this);
            m.add(i); //m.add(l);
            mb.add(m); f.add(b3);
            f.add(mb); f.add(lab1);f.add(t);
            f.add(label); f.add(pan);
            f.setJMenuBar(mb);
            if(e.getSource() == i)
            {
                fileChooser = new JFileChooser();
                returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == fileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    label.setIcon(ResizeImage(path));
                    //pan.add(label);
                }
                File f5=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    DCTSteganography dct=new DCTSteganography(f5);
                    StringBuilder sb=new StringBuilder();
                    FileInputStream fs=new FileInputStream("C:\\Users\\Hassan Shahzad\\Pictures\\Lambourghini Huracan\\keys.bin");
                    ObjectInputStream is=new ObjectInputStream(fs);
                    dct.unHide(is, sb);
                    t.setText(""+ sb);
                } catch (IOException ex) {
                    System.err.println("ERROR"+" "+ex.getMessage());
                    System.exit(0);
                }
            }
            f.getContentPane().setBackground(Color.CYAN);
            f.setSize(600, 600);
            f.setVisible(true);
            f.setLayout(null);
            f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        }


    }


    public ImageIcon resizeImage(String ImagePath)
    {
        MyImage1 = new ImageIcon(ImagePath);
        Image img1 = MyImage1.getImage();
        Image newImg1 = img1.getScaledInstance(lab5.getWidth(), lab5.getHeight(), Image.SCALE_SMOOTH );
        ImageIcon image = new ImageIcon(newImg1);
        return image;
    }

    public ImageIcon ResizeImage(String ImagePath)
    {
        MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH );
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }



}
package RGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class howtoplay extends JFrame {
	Image Howplay;
	private Clip cilp;
	public howtoplay() {
		super("HOW TO PLAY"); //Ÿ��Ʋ
		Howplay = Toolkit.getDefaultToolkit().createImage("..//im//���ӹ��.png");
		// TODO Auto-generated constructor stub
		JPanel jPanel = new JPanel();
		 setSize(720, 760);
		 
		JButton btn2 = new JButton("TO MAIN");
		setLayout(null);
		btn2.setBounds(480, 620, 140, 40);
		btn2.setBackground(Color.BLACK);
		btn2.setForeground(Color.WHITE);
		btn2.setFont(new Font("TimesRaman",Font.ITALIC,13));
       
		add(btn2);
	    add(jPanel);

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //ȭ�� �߾ӿ� ����
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
	
        btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				playSound("src/audio/��ư.wav",false);
				 new Partmain();
		         setVisible(false);
			}
		});
	    
	}
	public void playSound(String pathName, boolean isLoop) {
		try {
			cilp = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			cilp.open(audioStream);
			cilp.start();
			if(isLoop) {
				cilp.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(Howplay,0,0,this);	
		g.setFont(new Font("TimesRaman",Font.BOLD ,20));
		g.setColor(Color.BLACK);
		g.drawString("RACOON JUMP GAME", 32, 80); //����
		
		g.drawString("�ʷϻ� ������ ��� ���� �ö󰡴� ����", 50, 140); 
		g.drawString("���� ���� �ö󰡸� ������ �ø����� ", 50, 180); 
		g.drawString("�ö󰥼��� ������ 1���� �����մϴ� ", 50, 220); 
		g.drawString("Ư���� ������ ��� ������ �߰��� �÷������� ", 50, 260); 
		g.drawString("������������ ������ ���� �ٰ� +10��", 50, 300); 
		g.drawString("��ֹ��� ������ -10�� ", 50, 340); 
		}
	
	
	
}

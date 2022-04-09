package GamePage;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inGame.Raccoon_ingame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MainScreen extends JFrame{
	
	private Clip cilp;
	Image raconback;
	public MainScreen() {
		super("JUMP GAME");
		raconback = Toolkit.getDefaultToolkit().getImage(""
				+ "..//Game//gameimage//main_background.png"); 

		    JPanel jPanel = new JPanel();
		    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    setLayout(null);
		    JButton btn1 = new JButton("PLAY GAME"); // �����ư
		    btn1.setBounds(450, 360, 140, 40);
		    btn1.setBackground(Color.WHITE);
		    btn1.setForeground(Color.BLUE);
		    btn1.setFont(new Font("TimesRaman",Font.ITALIC,15));
		    
		  
		    JButton btn2 = new JButton("HOW TO PLAY?"); // ���۹�ư
		    btn2.setBounds(450, 430, 140, 40);
		    btn2.setBackground(Color.WHITE);
		    btn2.setForeground(Color.BLUE);
		    btn2.setFont(new Font("TimesRaman",Font.ITALIC,13));
		    
		    
		    JButton btn3 = new JButton("CLOSE"); // �����ư
		    btn3.setBounds(450, 500, 140, 40);
		    btn3.setBackground(Color.WHITE);
		    btn3.setForeground(Color.BLUE);
		    btn3.setFont(new Font("TimesRaman",Font.ITALIC,15));
		    
		    
		    setSize(720, 760); //â ũ�� ����
		    add(btn1);         
		    add(btn2);
		    add(btn3);
		    add(jPanel);
		    
		    Dimension frameSize = getSize();
		    Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		    setLocation((windowSize.width - frameSize.width) / 2, //ȭ�� �߾ӿ� ����
		            (windowSize.height - frameSize.height) / 2); 
		    
		    setVisible(true);
		    
		    btn1.addActionListener(new ActionListener() { //bt1 -> ���ӽ���
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					playSound("src/audio/button.wav",false);
					new Raccoon_ingame();
					JFrame w = new JFrame("play"); //â �̸�
					w.setResizable(false);
					w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					w.add(new Raccoon_ingame());
					w.pack();
					w.setLocationRelativeTo(null);
					w.setVisible(true);
					setVisible(false);
					
				}
			});
 
		    btn2.addActionListener(new ActionListener() { //bt2 -> ���ӹ��
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	playSound("src/audio/button.wav",false);
		            new HowtoplayScreen();
		            setVisible(false); // â �Ⱥ��̰� �ϱ� 
		        }
		    });
		    
		    btn3.addActionListener(new ActionListener() { //bt3 -> ��������			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					playSound("src/audio/button.wav",false);
					System.exit(0);
				}
			});	
	}
	
	public void playSound(String pathName, boolean isLoop) { //�Ҹ� ����� ���� playSound����
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
	public void paint(Graphics g) {  // ����ȭ�� ���� ����
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(raconback,0,0,this);	
		g.setFont(new Font("TimesRaman",Font.BOLD ,32));
		g.setColor(Color.BLUE);
		g.drawString("___________________", 60, 180);
		g.setColor(Color.WHITE);
		g.drawString("RACOON JUMP GAME", 60, 220); 
		g.setColor(Color.BLUE);
		g.drawString("___________________", 60, 230);
	}
	
	
}

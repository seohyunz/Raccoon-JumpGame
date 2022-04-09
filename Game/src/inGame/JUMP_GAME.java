package inGame;

import javax.swing.*;
import javax.swing.text.AbstractDocument.BranchElement;

import RGame.Partmain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.midi.Patch;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.Collections;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class POS{ //����,����
	int x, y;
}
class APOS{ //Ư������
	int x2, y2;
}
class BPOS{ //Ư������2
	int x3, y3;
}
 
public class JUMP_GAME extends JPanel implements Runnable,KeyListener{ 
	
	
	 int WIDTH = 550; 
	 int HEIGHT = 900;
	 

	 boolean isRunning;
	 Thread thread;
	 BufferedImage view, background, floor, animal, Rfloor,Ofloor,Over;
	//���, ����, ���� ����
		
		
	 
	/*int lastIndex=0;
	String rankingnum;
	int rrrr=0; ��ŷ*/
	
	
	
	 int score = 0;
	 int check=0;
	
	
	
	
	POS[] pos; //����
	APOS[] apos; //����2
	BPOS[] bpos; //����3
	int firstscore=0;
	int x = 100, y = 100, h = 180;  //�����ϴ°�,����
	int x2 = 80, y2 = 80, h2 = 160; 
	int x3 = 50, y3 = 50, h3 = 150;	
	
	ArrayList<Integer>list = new ArrayList<Integer>();
	
	double dy =0;	
	boolean right, left;
	
	
	public JUMP_GAME() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT)); //â����
		addKeyListener(this); //Ű �̺�Ʈ
		
	}
	@Override
	public void addNotify() { //ó�� �׸��� �׸�
		// TODO Auto-generated method stub
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
	}
	
	public void start() throws IOException {//����  ����ó�� ����,�̹���
			//���� �̹���
			view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			floor = ImageIO.read(getClass().getResource("floor3 (2).png"));
		//	Rfloor= ImageIO.read(getClass().getResource("������2.png"));
			Ofloor = ImageIO.read(getClass().getResource("��ֹ�.png"));
			background = ImageIO.read(getClass().getResource("backk.png"));
			animal = ImageIO.read(getClass().getResource("racoon.png"));
			Over= ImageIO.read(getClass().getResource("ground2.png"));
			
			pos = new POS[20];//���� ���� ���� 
			for(int i=0; i<10; i++) {
				pos[i] =new POS();
				pos[i].x = new Random().nextInt(450); 
				pos[i].y = new Random().nextInt(900);
				
			}
			
			apos = new APOS[20];//����2 ���� ���� 
			for(int i=0; i<10; i++) { 
			  apos[i] =new APOS();
			  apos[i].x2 = new Random().nextInt(410); 
			  apos[i].y2 = new Random().nextInt(900);
		 }		
			
			bpos = new BPOS[20];//����3 ���� ���� 
			for(int i=0; i<10; i++) { 
				bpos[i] =new BPOS();
				bpos[i].x3 = new Random().nextInt(410); 
				bpos[i].y3 = new Random().nextInt(900);
		 }		
		
	}
	
	
	public void update() {//������ �����ϴ� �͵� 
		if(right) { //4�� ������
			x+=4;
		}
		else if(left) {
			x-=4;
		}
		
		dy += 0.2; //�������� �ӵ�
		y +=dy;
		
		if(y>920) { //y(������)930(â ��)�� ������ ��������
			check=1;
		}
		
			
		if(y<h) {
			for(int i=0; i<10; i++) { //ȭ�� �̾�����
				y=h;
				pos[i].y = pos[i].y-(int)dy;
				apos[i].y2 = apos[i].y2-(int)dy;
				bpos[i].y3 = bpos[i].y3-(int)dy;
				
				if(pos[i].y>900) {
					pos[i].y=0;//����
					pos[i].x = new Random().nextInt(650); //������ ���Ӱ� ����� �ֱ�		
					score+=1; //�ö󰥼��� ���� ����
					apos[i].y2=0; //����2
					apos[i].x2 = new Random().nextInt(650);
					bpos[i].y3=0; //����3
					bpos[i].x3 = new Random().nextInt(650);		
				}
			}
		}
		
			//vBar[i].y > HEIGHT + 10
		for(int i=0; i<10; i++) {  //���ǿ� ����� Ȯ���ϱ�
			if((x+50 >pos[i].x)&&(x+20<pos[i].x+68)
					&&(y+70>pos[i].y)&&(y+70<pos[i].y+14)&&(dy>0)) {
				playSound("src/audio/�����Ҹ�.wav",false);
				
				dy=-15;
			}
			//10���߰��� 
		/*	else if((x+35 >apos[i].x2)&&(x+20<apos[i].x2+60)
					&&(y+75>apos[i].y2)&&(y+75<apos[i].y2+14)&&(dy>0)) {
				playSound("src/audio/�����Ҹ�2.wav",false);
				score+=10;
				dy=-20;	
			}
			*/
			//-30
			else if((x+40 >bpos[i].x3)&&(x+20<bpos[i].x3+50)
					&&(y+55>bpos[i].y3)&&(y+55<bpos[i].y3+14)&&(dy>0)) {
				
				score= score-10;
				dy=-11;	
				
			}
			
		}	
			
	}
	
	private Clip cilp;
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

	
	public void draw() throws IOException {//�̹��� �׸���
		Graphics2D g2 = (Graphics2D)view.getGraphics();
		g2.drawImage(background, 0, 0, WIDTH, HEIGHT, null); //����
		g2.drawImage(animal, x, y, animal.getWidth(), animal.getHeight(), null);
		g2.setColor(Color.WHITE); 
		g2.setFont(new Font("TimesRaman", Font.BOLD,27));
		g2.drawString("score :"+score, 50,20);	
		for(int i=0; i<9; i++) { //���� ����
			g2.drawImage(floor, pos[i].x, pos[i].y, floor.getWidth(), floor.getHeight(), null);	
		}
		/*for(int i=0; i<1; i++) { //Ư������2 ����
			g2.drawImage(Rfloor, apos[i].x2, apos[i].y2, Rfloor.getWidth(), Rfloor.getHeight(), null);
		}
		*/
		for(int i=0; i<3; i++) { //Ư������3 ����
			g2.drawImage(Ofloor, bpos[i].x3, bpos[i].y3, Ofloor.getWidth(), Ofloor.getHeight(), null);
		}
		
		
	
		
		
		if(check==1) {//������	
			playSound("src/audio/����Ҹ�.wav",false);
			
		/*	File file = new File("E:/java_����/test.txt"); //��° score
			File file2 = new File("E:/java_����/test2.txt"); //��°  score �ű��
			
			FileOutputStream output = new FileOutputStream(file,true);
			FileOutputStream output2 = new FileOutputStream(file2,true);
			
			String score1 = String.valueOf(score+"\r\n");//file���� ����
			output.write(score1.getBytes());
			output.close();
			*/
			//file�� �������� ����
		/*	BufferedReader reader =null; //������ ���ϱ�
			try {
				reader = new BufferedReader(new FileReader(file));
				String next, line = reader.readLine();
				  for (boolean first = true, last = (line == null);
						  !last; first = false, line = next) {
		                last = ((next = reader.readLine()) == null);
		                if (last) {
		                	lastIndex=Integer.parseInt(line); //������ ������ ���������� ����(�ֱ�����)
		                	
		                }   
				  }	
			} finally {
	            if (reader != null) try { reader.close(); } catch (IOException e) {}
	        }
			
			
			list.add(lastIndex);
			Collections.sort(list);
			int index1=list.size();
			int firstindex=0;
			//���� �ֱ������� ���� �������� �������� ũ��
			
			 if(firstindex>lastIndex) { 
				    JOptionPane aa=new JOptionPane();
				    aa.showMessageDialog(null,"�����մϴ� �ű�� �޼�!!");   
				    rankingnum = String.valueOf(lastIndex+"\r\n"); //�ű�����Ͽ�rankingnum����
				    output2.write(rankingnum.getBytes());
				    output2.close();	
			  } 	
			*/
			/*	BufferedReader reader2 =null; //������ ���ϱ�
				try {
					reader2 = new BufferedReader(new FileReader(file2));
					String next2, line2 = reader2.readLine();
					  for (boolean first = true, last = (line2 == null);
							  !last; first = false, line2 = next2) {
			                last = ((next2 = reader2.readLine()) == null);
			                if (last) {
			                	rrrr=Integer.parseInt(line2);
			                }   
					  }	
				} finally {
		            if (reader2 != null) try { reader2.close(); } catch (IOException e) {}
		      }
			 */
			JPanel jPanel = new JPanel();
			JButton btn = new JButton("��������");
			g2.drawImage(Over, 0, 0, WIDTH, HEIGHT, null);
			g2.setColor(Color.RED); 
			g2.setFont(new Font("TimesRaman", Font.BOLD,35));
			g2.drawString("GAME OVER",30, 580);
			g2.setColor(Color.BLACK); 
			g2.drawString("score(������):"+score, 30,630);
			g2.setColor(Color.BLUE);
			//g2.drawString("���� ������� :"+rrrr, 30,680);
			g2.setFont(new Font("TimesRaman", Font.BOLD,15));
			setLayout(null);
			btn.setBounds(320, 800, 100, 40);
			btn.setBackground(Color.WHITE);
			btn.setForeground(Color.BLACK);
			btn.setFont(new Font("TimesRaman",Font.ITALIC,13));
			add(btn);
			add(jPanel);
			btn.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					new Partmain(); 
			        setVisible(false);
				}
			});	
		}
		
		Graphics g =getGraphics();
		g.drawImage(view, 0, 0, WIDTH, HEIGHT, null); //���
		g.dispose();
	
		
	}
	
	
	@Override
	public void run() { //���� ���� ��
		// TODO Auto-generated method stub
		try {
			requestFocus();
			start();
			while(isRunning) {
				update();
				draw();
				Thread.sleep(1000/68);//���� �ð�
				if(check==1) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {//ŰŸ�� Ȯ��
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
	}
	
	
}
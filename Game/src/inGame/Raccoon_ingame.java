package inGame;

import javax.swing.*;
import javax.swing.text.AbstractDocument.BranchElement;

import GamePage.MainScreen;

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

//발판위치
class POS{ //발판
	int x, y;
}
class R_POS{  //무지개발판
	int x2, y2;
}
class D_POS{ //장애물 발판
	int x3, y3;
}
 
public class Raccoon_ingame extends JPanel implements Runnable,KeyListener{ 
	int WIDTH = 550; 
	int HEIGHT = 900;
	boolean isRunning;
	Thread thread;
	BufferedImage view, background,floor,raccoon,rainbowfloor,dangerousfloor,end;
	int score = 0;
	int check=0;	
	POS[] pos; 
	R_POS[] rpos; 
	D_POS[] dpos; 
	int x = 100, y = 100, h = 180;  //점프하는 위치,라쿤위치
	int x2 = 80, y2 = 80, h2 = 160; 
	int x3 = 50, y3 = 50, h3 = 150;	
	double dy =0; //점프 높이
	boolean right, left;
	
	public Raccoon_ingame() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		addKeyListener(this); 	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
	}
	
	public void start() throws IOException {//변수 이미지 
			view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			floor = ImageIO.read(getClass().getResource("normal_floor.png"));
			rainbowfloor= ImageIO.read(getClass().getResource("rainbow_floor.png"));
			dangerousfloor = ImageIO.read(getClass().getResource("dangerous_floor.png"));
			background = ImageIO.read(getClass().getResource("ingame_background.png"));
			raccoon = ImageIO.read(getClass().getResource("raccoon.png"));
			end= ImageIO.read(getClass().getResource("gameover_background.png"));
			//발판 랜덤 생성 
			pos = new POS[20];
			for(int i=0; i<10; i++) {
				pos[i] =new POS();
				pos[i].x = new Random().nextInt(450); 
				pos[i].y = new Random().nextInt(900);		
			}
			
			rpos = new R_POS[20];
			for(int i=0; i<10; i++) { 
				rpos[i] =new R_POS();
				rpos[i].x2 = new Random().nextInt(410); 
				rpos[i].y2 = new Random().nextInt(900);
			}		
			
			dpos = new D_POS[20];
			for(int i=0; i<10; i++) { 
				dpos[i] =new D_POS();
				dpos[i].x3 = new Random().nextInt(410); 
				dpos[i].y3 = new Random().nextInt(900);
		 }				
	}
	
	public void update() {//게임중 업데이트 해야할것
		if(right) { //4씩 움직임
			x+=4;
		}
		else if(left) {
			x-=4;
		}
		dy += 0.2; //떨어지는 속도
		y +=dy;
		
		if(y>920) { //y(동물이)920(창 끝)에 닿으면-> check=1(게임종료로 넘어가기)
			check=1;
		}
			
		if(y<h) {//화면 이어지기
			for(int i=0; i<10; i++) { 
				y=h;
				pos[i].y = pos[i].y-(int)dy;
				rpos[i].y2 = rpos[i].y2-(int)dy;
				dpos[i].y3 = dpos[i].y3-(int)dy;
				
				if(pos[i].y>900) {
					pos[i].y=0;
					pos[i].x = new Random().nextInt(650); //위에서 새롭게 만들어 주기		
					score+=1; //올라갈수록 점수 증가
					rpos[i].y2=0; 
					rpos[i].x2 = new Random().nextInt(650);
					dpos[i].y3=0; 
					dpos[i].x3 = new Random().nextInt(650);		
				}
			}
		}
		
		for(int i=0; i<10; i++) {  //발판에 닿는지 확인하기
			if((x+50 >pos[i].x)&&(x+20<pos[i].x+68)
					&&(y+70>pos[i].y)&&(y+70<pos[i].y+14)&&(dy>0)) {
				playSound("src/audio/jump.wav",false);		
				dy=-15;
			}
	
			else if((x+35 >rpos[i].x2)&&(x+20<rpos[i].x2+60)
					&&(y+75>rpos[i].y2)&&(y+75<rpos[i].y2+14)&&(dy>0)) {
				playSound("src/audio/jump2.wav",false);
				score+=10;
				dy=-20;	
			}

			else if((x+40 >dpos[i].x3)&&(x+20<dpos[i].x3+50)
					&&(y+55>dpos[i].y3)&&(y+55<dpos[i].y3+14)&&(dy>0)) {	
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
	
	public void draw() throws IOException {//이미지 그리기
		Graphics2D g2 = (Graphics2D)view.getGraphics();
		g2.drawImage(background, 0, 0, WIDTH, HEIGHT, null); 
		g2.drawImage(raccoon, x, y, raccoon.getWidth(), raccoon.getHeight(), null);
		g2.setColor(Color.WHITE); 
		g2.setFont(new Font("TimesRaman", Font.BOLD,27));
		g2.drawString("score :"+score, 50,20);	
		for(int i=0; i<9; i++) {
			g2.drawImage(floor, pos[i].x, pos[i].y, floor.getWidth(), floor.getHeight(), null);	
		}
		for(int i=0; i<1; i++) {
			g2.drawImage(rainbowfloor, rpos[i].x2, rpos[i].y2, rainbowfloor.getWidth(), rainbowfloor.getHeight(), null);
		}
		for(int i=0; i<3; i++) {
			g2.drawImage(dangerousfloor, dpos[i].x3, dpos[i].y3, dangerousfloor.getWidth(), dangerousfloor.getHeight(), null);
		}
		
		//gameover 화면
		if(check==1) {
			playSound("src/audio/end.wav",false);
			JPanel jPanel = new JPanel();
			JButton btn = new JButton("메인으로");
			g2.drawImage(end, 0, 0, WIDTH, HEIGHT, null);
			g2.setColor(Color.RED); 
			g2.setFont(new Font("TimesRaman", Font.BOLD,35));
			g2.drawString("GAME OVER",30, 580);
			g2.setColor(Color.BLACK); 
			g2.drawString("score:"+score, 30,630);
			g2.setColor(Color.BLUE);
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
					playSound("src/audio/button.wav",false);
					new MainScreen(); 
			        setVisible(false);
				}
			});	
		}
		Graphics g =getGraphics();
		g.drawImage(view, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();	
	}
	
	@Override
	public void run() { //게임 실행 중
		// TODO Auto-generated method stub
		try {
			requestFocus();
			start();
			while(isRunning) {
				update();
				draw();
				Thread.sleep(1000/68); //점프 시간
				if(check==1) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	@Override //키타입 확인
	public void keyTyped(KeyEvent e) {}
	
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

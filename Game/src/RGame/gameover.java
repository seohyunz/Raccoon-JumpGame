package RGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import inGame.JUMP_GAME;


public class gameover extends JFrame { //메인으로 돌아가기버튼, 점수 확인, GAMEOVER메시지 띄우기
	Image over;
	JUMP_GAME score;

	public gameover(int score) {
		super("GAME OVER");  
		over = Toolkit.getDefaultToolkit().createImage("..//im//images (1).png");
		JPanel jPanel = new JPanel();
		setSize(400, 450);
		
		JButton btn3 = new JButton("TO MAIN");
		setLayout(null);
		btn3.setBounds(220, 350, 120, 35);
		btn3.setBackground(Color.BLACK);
		btn3.setForeground(Color.WHITE);
		btn3.setFont(new Font("TimesRaman",Font.ITALIC,13));
		
		add(btn3);
		add(jPanel);

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
	
        
        btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 new Partmain();
		         setVisible(false);
			}
		});
	    
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(over,0,0,this);	
		g.setFont(new Font("TimesRaman",Font.BOLD ,35));
		g.setColor(Color.BLACK);
		g.drawString("GAME OVER", 40, 260); 
		g.drawString("score : "+score, 40, 290);
		
		}
	
}


		
		 
	
	 
	
	
	
	


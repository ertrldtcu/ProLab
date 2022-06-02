import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class shadowedJLabel extends JLabel {
	
	private JLabel shadow = new JLabel("");
	
	public shadowedJLabel() {
		this("");
	}
	
	public shadowedJLabel(String text) {
		super.setText(text);
		shadow.setText(text); 
		shadow.setForeground(new Color(255,255,255));
		super.setForeground(new Color(0,0,0));
		add(shadow);
	}
	
	@Override
	public void setText(String text) {
		if (shadow != null) {
			super.setText(text);
			shadow.setText(text);
		}
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		if (shadow != null) {
			super.setBounds(x, y, width, height);
			shadow.setBounds(-2, -2, width, height);
		}
	}
	
	@Override
	public void setFont(Font f) {
		if (shadow != null) {
			super.setFont(f);
			shadow.setFont(f);
		}
	}
	
	@Override
	public void setForeground(Color c) {
		if(shadow != null) {
			shadow.setForeground(c);
		}
			
	}
	
	@Override
	public void setHorizontalAlignment(int aligment) {
		if (shadow != null) {
			super.setHorizontalAlignment(aligment);
			shadow.setHorizontalAlignment(aligment);
		}
	}
	
	private static final long serialVersionUID = 1L;
	
}

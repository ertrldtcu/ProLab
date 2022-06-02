import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JLabel;

public class Sporcu extends JLabel {

	// variables
	protected Oyuncu sahip;
	protected String sporcuIsim = new String();
	protected String sporcuTakim = new String();
	protected boolean kartKullanildiMi = false;
	
	protected shadowedJLabel isimLabel = new shadowedJLabel("");
	protected shadowedJLabel takimLabel = new shadowedJLabel("");
	protected shadowedJLabel ozelliklerLabel = new shadowedJLabel("");
	
	protected boolean isShowable = true;
	protected boolean isSelectable = true;
	protected boolean hasBrightened = false;
	protected Timer moveTimer;
		
	// constructors
	public Sporcu() {
		this("",""); 
	}
	
	public Sporcu(String sporcuIsim) {
		this(sporcuIsim,"");
	}
	public Sporcu(String sporcuIsim, String sporcuTakim) {
		
		setOpaque(true);
		this.addMouseListener(ML);
		setSize(128,192);
		
		this.sporcuIsim = sporcuIsim;
		isimLabel.setText("<html><p align=\"center\">" + sporcuIsim + "</p></html>");
		isimLabel.setBounds(3,0,122,80);
		isimLabel.setHorizontalAlignment(JLabel.CENTER);
		isimLabel.setFont(new Font(Font.DIALOG, Font.BOLD,  22));
		add(isimLabel);
		
		this.sporcuTakim = sporcuTakim;
		takimLabel.setText("<html><p align=\"center\">" + sporcuTakim + "</p></html>");
		takimLabel.setBounds(3,80,122,48);
		takimLabel.setHorizontalAlignment(JLabel.CENTER);
		takimLabel.setFont(new Font(Font.DIALOG, Font.PLAIN,  16));
		add(takimLabel);
		
		ozelliklerLabel.setText("<html><p align=\"center\">" + "??<br/>??<br/>??" + "</p></html>");
		ozelliklerLabel.setBounds(3,128,122,64);
		ozelliklerLabel.setHorizontalAlignment(JLabel.CENTER);
		ozelliklerLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
		add(ozelliklerLabel);
	}
	
	// setters and getters
	public String getSporcuIsim() {
		return sporcuIsim;
	}

	public void setSporcuIsim(String isim) {
		sporcuIsim = isim;
		isimLabel.setText("<html><p align=\"center\">" + sporcuIsim + "</p></html>");
	}

	public String getSporcuTakim() {
		return sporcuTakim;
	}

	public void setSporcuTakim(String takim) {
		sporcuTakim = takim;
		takimLabel.setText("<html><p align=\"center\">" + sporcuTakim + "</p></html>");
	}
	
	public boolean getKartKullanildiMi() {
		return kartKullanildiMi;
	}

	public void setKartKullanildiMi(boolean state) {
		kartKullanildiMi = state;
	}
	
	public boolean getShowable() {
		return this.isShowable;
	}
	
	public void setShowable(boolean showable) {
		isShowable = showable;
		if(showable) {
			isimLabel.setVisible(true);
			takimLabel.setVisible(true);
			ozelliklerLabel.setVisible(true);
		}
		else {
			isimLabel.setVisible(false);
			takimLabel.setVisible(false);
			ozelliklerLabel.setVisible(false);
		}
	}
	
	public boolean getSelectable() {
		return isSelectable;
	}

	public void setSelectable(boolean selectable) {
		isSelectable = selectable;
	}
	
	public void moveTo( int x, int y ) {
		if (moveTimer != null) {
			moveTimer.cancel();
		}
		moveTimer = new Timer();
		moveTimer.schedule(new TimerTask() {
			float t = 0.0f;
			public void run() {
				setLocation( (int) ((getX() + x) / 2), (int) ((getY() + y) / 2) );
				t += 0.1f;
				if (t >= 1.0f) {
					setLocation(x,y);
					moveTimer.cancel();
					moveTimer = null;
				}
			}
		},0,25);
	}
	
	public void brighter () {
		if (!hasBrightened) {
			setBackground(getBackground().brighter());
			hasBrightened = true;
		}
	}
	
	public void darker ( ) {
		if (hasBrightened) {
			setBackground(getBackground().darker());
			hasBrightened = false;
		}
	}
	
	protected static final MouseListener ML = new MouseListener() {
		public void mouseEntered(MouseEvent e) {
			Sporcu parent = (Sporcu) (e.getSource());
			if (parent.getShowable() && parent.getSelectable()) {
				Point p = parent.getLocation();
				parent.brighter();
				parent.setLocation(p.x, p.y-7);
			}
		}
		public void mouseExited(MouseEvent e) {
			Sporcu parent = (Sporcu) (e.getSource());
			if (parent.getShowable() && parent.getSelectable()) {
				Point p = parent.getLocation();
				parent.darker();
				parent.setLocation(p.x, p.y+7);
			}
		}
		public void mouseClicked(MouseEvent e){}; public void mousePressed(MouseEvent e){}; public void mouseReleased(MouseEvent e){}
	};
	
	private static final long serialVersionUID = 1L;
	
}

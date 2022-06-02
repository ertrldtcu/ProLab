import java.awt.Color;
import javax.swing.ImageIcon;

public class Basketbolcu extends Sporcu {
	
	// variables
	protected int ikilik, ucluk, serbestAtis;
	
	// constuctors
	public Basketbolcu() {
		this("","",0,0,0);
	}
	
	public Basketbolcu(String basketbolcuAdi) {
		this(basketbolcuAdi,"",0,0,0);
	}
	
	public Basketbolcu(String basketbolcuAdi, String basketbolcuTakim) {
		this(basketbolcuAdi,basketbolcuTakim,0,0,0);
	}
	
	public Basketbolcu(String basketbolcuAdi, String basketbolcuTakim, int ikilik, int ucluk, int serbestAtis) {
		super(basketbolcuAdi,basketbolcuTakim);
		this.ikilik = ikilik;
		this.ucluk = ucluk;
		this.serbestAtis = serbestAtis;
		setIcon(new ImageIcon(getClass().getResource("img/basketballer.png")));
		ozelliklerLabel.setText("<html><p align=\"center\">Ýkilik: " + ikilik + "<br/>Üçlük: " + ucluk + "<br/>Serbest Atýþ: " + serbestAtis + "</p></html>");
		setBackground(new Color(178,140,112));
	}

	// setters and getters
	public String getBasketbolcuAdi() {
		return super.sporcuIsim; 
	}

	public void setBasketbolcuAdi(String basketbolcuAdi) {
		super.setSporcuIsim(basketbolcuAdi);
	}

	public String getBasketbolcuTakim() {
		return super.sporcuTakim;
	}

	public void setBasketbolcuTakim(String basketbolcuTakim) {
		super.setSporcuTakim(basketbolcuTakim);
	}

	public int getIkilik() {
		return ikilik;
	}

	public void setIkilik(int ikilik) {
		this.ikilik = ikilik;
		ozelliklerLabel.setText("<html><p align=\"center\">Ýkilik: " + ikilik + "<br/>Üçlük: " + ucluk + "<br/>Serbest Atýþ: " + serbestAtis + "</p></html>");
	}

	public int getUcluk() {
		return ucluk;
	}

	public void setUcluk(int ucluk) {
		this.ucluk = ucluk;
		ozelliklerLabel.setText("<html><p align=\"center\">Ýkilik: " + ikilik + "<br/>Üçlük: " + ucluk + "<br/>Serbest Atýþ: " + serbestAtis + "</p></html>");
	}

	public int getSerbestAtis() {
		return serbestAtis;
	}

	public void setSerbestAtis(int serbestAtis) {
		this.serbestAtis = serbestAtis;
		ozelliklerLabel.setText("<html><p align=\"center\">Ýkilik: " + ikilik + "<br/>Üçlük: " + ucluk + "<br/>Serbest Atýþ: " + serbestAtis + "</p></html>");
	}
	
	private static final long serialVersionUID = 1L;
	
}

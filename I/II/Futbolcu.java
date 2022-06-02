import java.awt.Color;

import javax.swing.ImageIcon;

public class Futbolcu extends Sporcu {
	
	// variables
	protected int penalti, serbestAtis, kaleciKarsiKarsiya;
	
	// constuctors
	public Futbolcu() {
		this("","",0,0,0);
	}
	
	public Futbolcu(String futbolcuAdi) {
		this(futbolcuAdi,"",0,0,0);
	}
	
	public Futbolcu(String futbolcuAdi, String futbolcuTakim) {
		this(futbolcuAdi,futbolcuTakim,0,0,0);
	}
	
	public Futbolcu(String futbolcuAdi, String futbolcuTakim, int penalti, int serbestAtis, int kaleciKarsiKarsiya) {
		super(futbolcuAdi,futbolcuTakim);
		this.penalti = penalti;
		this.serbestAtis = serbestAtis;
		this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
		setIcon(new ImageIcon(getClass().getResource("img/footballer.png")));
		ozelliklerLabel.setText("<html><p align=\"center\">Penaltý: " + penalti + "<br/>Serbest Atýþ: " + serbestAtis + "<br/>Kaleci Karþýlýklý: " + kaleciKarsiKarsiya + "</p></html>");
		setBackground(new Color(112,140,178));
	}


	// setters and getters
	public String getFutbolcuAdi() {
		return super.sporcuIsim;
	}

	public void setFutbolcuAdi(String futbolcuAdi) {
		super.setSporcuIsim(futbolcuAdi);
	}

	public String getFutbolcuTakim() {
		return super.sporcuTakim;
	}

	public void setFutbolcuTakim(String futbolcuTakim) {
		super.setSporcuTakim(futbolcuTakim);
	}

	public int getPenalti() {
		return penalti;
	}

	public void setPenalti(int penalti) {
		this.penalti = penalti;
		ozelliklerLabel.setText("<html><p align=\"center\">Penaltý: " + penalti + "<br/>Serbest Atýþ: " + serbestAtis + "<br/>Kaleci Karþýlýklý: " + kaleciKarsiKarsiya + "</p></html>");
	}

	public int getSerbestAtis() {
		return serbestAtis; 
	}

	public void setSerbestAtis(int serbestAtis) {
		this.serbestAtis = serbestAtis;
		ozelliklerLabel.setText("<html><p align=\"center\">Penaltý: " + penalti + "<br/>Serbest Atýþ: " + serbestAtis + "<br/>Kaleci Karþýlýklý: " + kaleciKarsiKarsiya + "</p></html>");
	}

	public int getKaleciKarsiKarsiya() {
		return kaleciKarsiKarsiya;
	}

	public void setKaleciKarsiKarsiya(int kaleciKarsiKarsiya) {
		this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
		ozelliklerLabel.setText("<html><p align=\"center\">Penaltý: " + penalti + "<br/>Serbest Atýþ: " + serbestAtis + "<br/>Kaleci Karþýlýklý: " + kaleciKarsiKarsiya + "</p></html>");
	}

	private static final long serialVersionUID = 1L;
	
}

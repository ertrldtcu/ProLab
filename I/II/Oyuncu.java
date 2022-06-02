import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Oyuncu extends shadowedJLabel {
	
	// variables
	protected String oyuncuID = new String("");
	protected String oyuncuAdi = new String("");
	protected int Skor = 0;
	protected ArrayList<Sporcu> kartListesi = new ArrayList<Sporcu>(); 
	protected Sporcu sonSecilen;
	protected Test gui;
	
	// constuctors
	public Oyuncu() {};
	
	public Oyuncu(String oyuncuID) {
		this.oyuncuID = oyuncuID;
	}
	
	public Oyuncu(String oyuncuID, String oyuncuAdi) {
		this.oyuncuID = oyuncuID;
		this.oyuncuAdi = oyuncuAdi;
	}
	
	public Oyuncu(String oyuncuID, String oyuncuAdi, int Skor) {
		this.oyuncuID = oyuncuID;
		this.oyuncuAdi = oyuncuAdi;
		this.Skor = Skor;
		
		setIcon(new ImageIcon(getClass().getResource("img/namebg.png")));
		setText(oyuncuAdi + ": " + Skor);
		setHorizontalAlignment(JLabel.CENTER);
		setHorizontalTextPosition(JLabel.CENTER);
		setFont(new Font(Font.DIALOG, Font.BOLD,  24));
		setBackground(new Color(140,178,140));
		setOpaque(true);
		setSize(512,42);
	}
	
	// methods
	public void kartSec(Class<?> c) {} // override
	 
	public void kartEkle(Sporcu s) {
		kartListesi.add(s);
		s.sahip = this;
		if (this.getClass() == Bilgisayar.class) {
			s.setShowable(false);
		}
	}
	
	public int getKartSayisi() { // oyuncudaki kart sayýsýný verir
		return kartListesi.size();
	}

	public int getKartSayisi(Class<?> c) {// verilen sýnýf ile ayný sýnýfta olan ve kullanýlmamýþ kart sayýsýný verir
		int counter = 0;
		for(Sporcu card: kartListesi) {
			if (card.getClass() == c && !(card.getKartKullanildiMi()) ) 
				counter++;
		}
		return counter;
	}
	
	public int getKullanilmamisKartSayisi() { // kullanýlmamýþ kart sayýsýný verir
		int counter = 0;
		for(Sporcu card: kartListesi) {
			if ( !(card.getKartKullanildiMi()) ) 
				counter++;
		}
		return counter;
	}
	
	public Sporcu kartGetir(int i) {
		if (i < kartListesi.size()) 
			return kartListesi.get(i);
		return null;
	}
	
	// setters and getters
	public String getOyuncuID() {
		return oyuncuID;
	}

	public void setOyuncuID(String oyuncuID) {
		this.oyuncuID = oyuncuID;
	}

	public String getOyuncuAdi() {
		return oyuncuAdi;
	}

	public void setOyuncuAdi(String oyuncuAdi) {
		this.oyuncuAdi = oyuncuAdi;
		setText(oyuncuAdi + ": " + Skor);
	}

	public int getSkor() {
		return Skor;
	}

	public void setSkor(int skor) {
		Skor = skor;
		setText(oyuncuAdi + ": " + Skor);
	}

	public void setGUI(Test gui) {
		this.gui = gui;
	}
	
	private static final long serialVersionUID = 1L;
	
}

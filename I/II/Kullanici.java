import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Kullanici extends Oyuncu {

	static final MouseListener ML = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Sporcu current = (Sporcu)(e.getSource());
			current.setKartKullanildiMi(true);
			current.brighter();
			(current.sahip).sonSecilen = current;
			for(int i=0; i < current.sahip.kartListesi.size(); i++) {
				current.sahip.kartListesi.get(i).setSelectable(false);
				current.sahip.kartListesi.get(i).removeMouseListener(ML);
			} 
			current.sahip.gui.kartSecildi(current.sahip,current);
		}
		public void mouseEntered(MouseEvent e){}; public void mouseExited(MouseEvent e){}; public void mousePressed(MouseEvent e){}; public void mouseReleased(MouseEvent e){};
	};
	
	// constuctors
	public Kullanici() {
		super();
	}
	
	public Kullanici(String oyuncuID) {
		super(oyuncuID);
	}
	
	public Kullanici(String oyuncuID, String oyuncuAdi) {
		super(oyuncuID,oyuncuAdi);
	}
	
	public Kullanici(String oyuncuID, String oyuncuAdi, int Skor) {
		super(oyuncuID,oyuncuAdi,Skor);
	}
	
	// methods
	@Override
	public void kartSec(Class<?> c) {
		for(int i=0; i < kartListesi.size(); i++) {
			Sporcu current = kartListesi.get(i);
			current.setSelectable(false);
			current.removeMouseListener(ML);
			if (current.getClass() == c && !(current.getKartKullanildiMi())) { 
				current.setSelectable(true);
				current.addMouseListener(ML);
			}
		}
	}

	private static final long serialVersionUID = 1L;
	
}

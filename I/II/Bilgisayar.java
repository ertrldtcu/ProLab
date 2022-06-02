public class Bilgisayar extends Oyuncu {

	// constuctors
	public Bilgisayar() {
		super();
	}
	
	public Bilgisayar(String oyuncuID) {
		super(oyuncuID);
	}
	
	public Bilgisayar(String oyuncuID, String oyuncuAdi) {
		super(oyuncuID,oyuncuAdi);
	}
	
	public Bilgisayar(String oyuncuID, String oyuncuAdi, int Skor) {
		super(oyuncuID,oyuncuAdi,Skor);
	}
	 
	// methods
	@Override
	public void kartSec(Class<?> c) {
		int rand = (int) (Math.random()*getKartSayisi(c));
		int counter = 0;
		for(int i=0; i<kartListesi.size(); i++) {
			Sporcu current = kartListesi.get(i);
			if (current.getClass() == c && !(current.getKartKullanildiMi())) {
				if (counter == rand) {
					current.setKartKullanildiMi(true);
					current.setShowable(true);
					current.setSelectable(false);
					current.brighter();
					(current.sahip).sonSecilen = current;
					gui.kartSecildi(current.sahip,current);
					break;
				}
				counter++;
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
	
}

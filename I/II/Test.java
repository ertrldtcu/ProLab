import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private shadowedJLabel message = new shadowedJLabel("");
	static Oyuncu players[] = { new Kullanici("1", "", 0), new Bilgisayar("2", "Bilgisayar", 0) };
	Basketbolcu[] basketballPlayers = {
		// isim , takım , ikilik , üçlük , serbest atış
		new Basketbolcu("LeBron James", "Los Angeles Lakers", 88, 80, 70),
		new Basketbolcu("Kawhi Leonard", "Los Angeles Clippers", 92, 81, 89),
		new Basketbolcu("James Harden", "Houston Rockets", 87, 84, 86),
		new Basketbolcu("Anthony Davis", "Los Angeles Lakers", 85, 79, 85),
		new Basketbolcu("Kevin Durant", "Brooklyn Nets", 98, 86, 89),
		new Basketbolcu("Stephen Curry", "Golden State Warriors", 91, 99, 92),
		new Basketbolcu("Luka Dončić", "Dallas Mavericks", 83, 80, 75),
		new Basketbolcu("Damian Lillard", "Portland Trail Blazers", 90, 89, 89),
	};
	Futbolcu[] footballPlayers = {
		// penaltı , frikik, kaleciyle karşı karşıya
		new Futbolcu("Neymar JR", "PSG", 85, 93, 91),
		new Futbolcu("Kylian Mbappe", "PSG", 92, 77, 88),
		new Futbolcu("Cristiano Ronaldo", "Juventus", 99, 85, 90),
		new Futbolcu("Lionel Messi", "Barcelona", 84, 98, 97),
		new Futbolcu("Kevin De Bruyne", "Manchester City", 92, 96, 88),
		new Futbolcu("Luka Modric", "Real Madrid", 83, 80, 88),
		new Futbolcu("Alexander Arnold", "Liverpool", 73, 84, 68),
		new Futbolcu("Harry Kane", "Tottenham", 90, 65, 92),
	};

	protected boolean mustSelect = true; // true basketbolcu, false futbolcu

	public Test() {
		setTitle("Sporcu Oyunu - Programlama II");
		setSize(1600, 900);
		setLocation(160, 90);
		setFocusable(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		message.setBounds(0, 380, 1600, 100);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font(Font.DIALOG, Font.PLAIN, 28));
		message.setForeground(new Color(255, 255, 255));
		add(message);
	}

	public void createGameboard() {
		add(players[0]);
		players[0].setLocation(544, 594);
		players[0].setGUI(this);
		add(players[1]);
		players[1].setLocation(544, 214);
		players[1].setGUI(this);

		for (Basketbolcu basketbolcu : basketballPlayers) {
			int player = (int) (Math.random() * 2);
			add(basketbolcu);
			if (players[player].getKartSayisi(Basketbolcu.class) < 4) {
				players[player].kartEkle(basketbolcu);
			} else {
				players[player * (-1) + 1].kartEkle(basketbolcu);
			}
		}

		for (Futbolcu futbolcu : footballPlayers) {
			int player = (int) (Math.random() * 2);
			add(futbolcu);
			if (players[player].getKartSayisi(Futbolcu.class) < 4) {
				players[player].kartEkle(futbolcu);
			} else {
				players[player * (-1) + 1].kartEkle(futbolcu);
			}
		}

		relocationCards();
	}

	public void relocationCards() {
		for (int i = 0; i < 2; i++) {
			int counter = 0;
			for (Sporcu card : players[i].kartListesi) {
				if (!(card.getKartKullanildiMi())) {
					card.moveTo(800 - players[i].getKullanilmamisKartSayisi() * 69 + counter * 138, 650 - i * 640);
					counter++;
				}
			}
		}
	}

	public void kartSectir(Oyuncu oyuncu) {
		// son kartları ekran dışına taşı
		if (oyuncu == players[0] && oyuncu.sonSecilen != null) {
			players[0].sonSecilen.darker();
			players[0].sonSecilen.moveTo(1600, 354);
			players[1].sonSecilen.setShowable(false);
			players[1].sonSecilen.darker();
			players[1].sonSecilen.moveTo(-130, 354);
		}

		// seçme sırası kullanıcıdaysa ve 1 kartı kalmışsa
		if (oyuncu == players[0] && oyuncu.getKullanilmamisKartSayisi() == 1) {
			for (Sporcu card : players[0].kartListesi) {
				if ( !(card.getKartKullanildiMi()) ) {
					for (Sporcu card2 : players[1].kartListesi) {
						if (!(card2.getKartKullanildiMi())) {
							if (card.getClass() == Basketbolcu.class) {
								if (	((Basketbolcu) (card)).getIkilik() == ((Basketbolcu) (card2)).getIkilik()
										&& ((Basketbolcu) (card)).getUcluk() == ((Basketbolcu) (card2)).getUcluk()
										&& ((Basketbolcu) (card)).getSerbestAtis() == ((Basketbolcu) (card2)).getSerbestAtis())
								{
									card2.setKartKullanildiMi(true);
									card.setKartKullanildiMi(true);
								}
							}
							else {
								if (	((Futbolcu) (card)).getPenalti() == ((Futbolcu) (card2)).getPenalti()
										&& ((Futbolcu) (card)).getKaleciKarsiKarsiya() == ((Futbolcu) (card2)).getKaleciKarsiKarsiya()
										&& ((Futbolcu) (card)).getSerbestAtis() == ((Futbolcu) (card2)).getSerbestAtis())
								{
									card2.setKartKullanildiMi(true);
									card.setKartKullanildiMi(true);
								}
							}
							break;
						}
					}
				}
			}
		}

		// oyuncunun kartı yoksa
		if (oyuncu == players[0] && oyuncu.getKullanilmamisKartSayisi() == 0) {
			for (int i = 0; i < 2; i++) { // tüm kartları ekranın dışına taşı
				for (Sporcu sporcu : players[i].kartListesi) {
					sporcu.moveTo(1600 - i * 1730, 354); // eğer kullanıcıysa sağa, bilgisayarsa sola
				}
			}

			if (players[1].getSkor() > players[0].getSkor())
				message.setText("<html><p align=\"center\">Oyun bitti.<br/>" + players[1].getOyuncuAdi() + " Kazandı !</p></html>");
			else if (players[1].getSkor() < players[0].getSkor())
				message.setText("<html><p align=\"center\">Oyun bitti.<br/>" + players[0].getOyuncuAdi() + " Kazandı !</p></html>");
			else
				message.setText("<html><p align=\"center\">Oyun bitti.<br/>Berabere !</p></html>");

			return;
		}


		// normal kart seçme kısmı
		Class<?> shouldSelect = Basketbolcu.class;
		if (!mustSelect) {
			shouldSelect = Futbolcu.class;
		}

		if (oyuncu.getKullanilmamisKartSayisi() > 0 && oyuncu.getKartSayisi(shouldSelect) > 0) {
			message.setText("Lütfen bir " + shouldSelect.getName() + " seçiniz.");
			if (oyuncu == players[0] && oyuncu.getKartSayisi(shouldSelect) == 1) {
				for (Sporcu card : oyuncu.kartListesi) {
					if (card.getClass() == shouldSelect && !(card.getKartKullanildiMi())) {
						oyuncu.sonSecilen = card;
						card.setKartKullanildiMi(true);
						card.setBackground(card.getBackground().brighter());
						kartSecildi(players[0], card);
						break;
					}
				}
			} else {
				oyuncu.kartSec(shouldSelect);
			}
		} else {
			mustSelect = !mustSelect;
			kartSectir(oyuncu);
		}

	}

	public void kartSecildi(Oyuncu secenOyuncu, Sporcu secilenKart) {
		Timer anim = new Timer();
		if (secenOyuncu == players[0]) {
			secilenKart.moveTo(1200, 354);
			anim.schedule(new TimerTask() {
				public void run() {
					kartSectir((Oyuncu) players[1]);
				}
			}, 500);
		} else if (secenOyuncu == players[1]) {
			secilenKart.moveTo(272, 354);
			anim.schedule(new TimerTask() {
				@Override
				public void run() {
					int randProp = (int) (Math.random() * 3);
					if (secilenKart.getClass() == Basketbolcu.class) {
						Basketbolcu oyuncununKarti = (Basketbolcu) (players[0].sonSecilen);
						Basketbolcu bilgisayarinKarti = (Basketbolcu) secilenKart;
						if (randProp == 0) {
							if (oyuncununKarti.getIkilik() < bilgisayarinKarti.getIkilik()) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncununKarti.getIkilik() > bilgisayarinKarti.getIkilik()) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\">Karşılaştırılacak Özellik: İkilik<br/>"
									+ bilgisayarinKarti.getSporcuIsim() + " " + bilgisayarinKarti.getIkilik() + " - "
									+ oyuncununKarti.getIkilik() + " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						} else if (randProp == 1) {
							if (oyuncununKarti.getUcluk() < bilgisayarinKarti.getUcluk()) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncununKarti.getUcluk() > bilgisayarinKarti.getUcluk()) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\">Karşılaştırılacak Özellik: Üçlük<br/>"
									+ bilgisayarinKarti.getSporcuIsim() + " " + bilgisayarinKarti.getUcluk() + " - "
									+ oyuncununKarti.getUcluk() + " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						} else {
							if (oyuncununKarti.getSerbestAtis() < bilgisayarinKarti.getSerbestAtis()) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncununKarti.getSerbestAtis() > bilgisayarinKarti.getSerbestAtis()) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\">Karşılaştırılacak Özellik: Serbest Atış<br/>" + bilgisayarinKarti.getSporcuIsim()
									+ " " + bilgisayarinKarti.getSerbestAtis() + " - " + oyuncununKarti.getSerbestAtis()
									+ " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						}
					} else {
						Futbolcu oyuncununKarti = (Futbolcu) (players[0].sonSecilen);
						Futbolcu bilgisayarinKarti = (Futbolcu) secilenKart;
						if (randProp == 0) {
							int oyuncuPenalti = oyuncununKarti.getPenalti();
							int bilgisayarPenalti = bilgisayarinKarti.getPenalti();
							if (oyuncuPenalti < bilgisayarPenalti) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncuPenalti > bilgisayarPenalti) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\">Karşılaştırılacak Özellik: Penaltı<br/>"
									+ bilgisayarinKarti.getSporcuIsim() + " " + bilgisayarPenalti + " - "
									+ oyuncuPenalti + " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						} else if (randProp == 1) {
							int oyuncuSerbest = oyuncununKarti.getSerbestAtis();
							int bilgisayarSerbest = bilgisayarinKarti.getSerbestAtis();
							if (oyuncuSerbest < bilgisayarSerbest) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncuSerbest > bilgisayarSerbest) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\"Karşılaştırılacak Özellik: Serbest Atış<br/>"
										+ bilgisayarinKarti.getSporcuIsim() + " " + bilgisayarSerbest + " - "
										+ oyuncuSerbest + " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						} else {
							int oyuncuKaleci = oyuncununKarti.getKaleciKarsiKarsiya();
							int bilgisayarKaleci = bilgisayarinKarti.getKaleciKarsiKarsiya();
							if (oyuncuKaleci < bilgisayarKaleci) {
								players[1].setSkor(players[1].getSkor() + 10);
							} else if (oyuncuKaleci > bilgisayarKaleci) {
								players[0].setSkor(players[0].getSkor() + 10);
							} else {
								oyuncununKarti.setKartKullanildiMi(false);
								bilgisayarinKarti.setKartKullanildiMi(false);
							}
							message.setText("<html><p align=\"center\">Karşılaştırılacak Özellik: Kaleciyle Karşı Karşıya<br/>"
									+ bilgisayarinKarti.getSporcuIsim() + " " + bilgisayarKaleci + " - " + oyuncuKaleci
									+ " " + oyuncununKarti.getSporcuIsim() + "</p></html>");
						}
					}

					this.cancel();
					anim.schedule(new TimerTask() {
						public void run() {
							mustSelect = !mustSelect;
							kartSectir((Oyuncu) players[0]);
							relocationCards();
						}
					}, 5000);

				}
			}, 300);
		}

	}

	public static void main(String args[]) {
		Test gui = new Test();

		gui.setLayout(null); // konumlandırma ve boyutlandırma için
		gui.getContentPane().setBackground(new Color(40, 40, 40));

		gui.message.setText("Lütfen adınızı giriniz");

		JTextField oyuncuAdi = new JTextField();
		oyuncuAdi.setBounds(600, 480, 270, 30);
		oyuncuAdi.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		oyuncuAdi.setForeground(new Color(0, 0, 0));
		gui.add(oyuncuAdi);

		JButton onaylaButton = new JButton("Onayla");
		onaylaButton.setOpaque(true);
		onaylaButton.setBounds(880, 480, 120, 30);
		onaylaButton.setBackground(new Color(175, 175, 175));
		onaylaButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		gui.add(onaylaButton);

		onaylaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onaylaButton.setVisible(false);
				oyuncuAdi.setVisible(false);
				gui.remove(onaylaButton);
				gui.remove(oyuncuAdi);
				gui.message.setText("Oyun başlatılıyor.");

				Timer startTimer = new Timer();
				startTimer.schedule(new TimerTask() {
					int counter = 0;

					public void run() {
						if (counter < 40) {
							String c = "◷";
							if (counter % 4 == 0)
								c = "◶";
							else if (counter % 4 == 1)
								c = "◵";
							else if (counter % 4 == 2)
								c = "◴";
							gui.message.setText(c + " Oyun yükleniyor " + c);
						} else {
							players[0].setOyuncuAdi(oyuncuAdi.getText());
							gui.createGameboard();
							gui.kartSectir((Oyuncu) players[0]);
							startTimer.cancel();
						}
						counter++;
					}
				}, 0, 75);
			}
		});

		gui.setVisible(true);

	}
}

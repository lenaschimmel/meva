

// DONTEDIT This file has been copied from /home/lena/workspaceNeu/checkin.module/meva/src/de/gmino/checkin/shared/domain/Shop.java.

// This warning may apply even when the original file contained a message that explicitly allows editing.

// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.ShopGen;
import de.gmino.geobase.shared.domain.Address;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.geobase.shared.domain.LatLon;
import de.gmino.meva.shared.Util;

public class Shop extends ShopGen {
	private static String[] shopTitles = { "Aldi", "Lidl", "Penny",
			"Tante Rudolf", "Swootchi", "Mc Pommes", "Döniri Grill",
			"Friseursalon Kopf-verdreht", "Lebensmittel Kalle",
			"Pommesbude am Eck", "Bierstube", "Wurst-Peter GmbH",
			"Tüdelü - Geschenkeladen", "Fit im Alter - für coole Omis",
			"Phonecentre 5000", "Medien und mehr",
			"Um Gottes Willen - Die atheistische Bibelgruppe",
			"Teppichmeister", "Softapps & mobilewebzz GmbH",
			"Frühstücksclub 1877 e.V.", "McBubbleTeaDrink YPK",
			"Servicepartner Fröhde", "1-€uroshop" };
	private static String[] slogans = { "Jetzt neu!", "Nur bei uns!",
			"Das schafft kein anderer!", "Blöd, wer woander hin geht.",
			"Billig, billiger, wir!", "Wir haben Stil.",
			"Wir können auch ohne flotten Werbespruch.",
			"Die besten in der Stadt.", "Wir machen jeden Kram mit.",
			"Uns gibt's nichtmal im Traum.",
			"Einfach nur Super. Mehr kann man da nicht sagen.", "Kaufen!",
			"Jetzt Stammkunde werden.", "Yeah!", "Was will man mehr?",
			"Wir reparieren auch.", "Freundliche Bedienung inclusive.",
			"Nichts für schwache Nerven :)",
			"Kommst du einmal, kommst du immer.",
			"Unsere Werbetexter sind unterbezahlt und unmotiviert.",
			"WO GROSSBUCHSTABEN NOCH COOL SIND!!!!1", "Yippieyippiejäih!",
			"110% Qualität.", "Billiger kost nix.",
			"Wir schließen! Alles mus raus! 78% Rabatt auf Vorjahreswahre.",
			"Montags ist Pizzatag.",
			"Keine halben Sachen - nur halbe Preise :D ;)",
			"Wie cool ist das denn?!", "Nur Originalteile.", "Alles vegan.",
			"BIO zum Discount-Preis.",
			"Hier kauf der Weihnachtsmann seine Ostereier." };

	// Constructors
	public Shop(long id) {
		super(id);
	}

	public Shop(long id, boolean ready, LatLon location, String facebookId,
			String scanCode, String title, String description, ImageUrl logo,
			Address shopAddress, Address billingAddress, ShopAdmin admin,
			Coupon currentCoupon) {
		super(id, ready, (de.gmino.geobase.shared.domain.LatLon) location,
				facebookId, scanCode, title, description,
				(de.gmino.geobase.shared.domain.ImageUrl) logo,
				(de.gmino.geobase.shared.domain.Address) shopAddress,
				(de.gmino.geobase.shared.domain.Address) billingAddress,
				(de.gmino.checkin.shared.domain.ShopAdmin) admin,
				currentCoupon);
	}

	public void fillWithRandomData() {
		location = LatLon.getWithRandomData();
		facebookId = (long) (Math.random() * 999999999) + "";
		scanCode = "";
		for (int i = 0; i < 8; i++)
			if (Math.random() < 0.722)
				scanCode += (char)('a' + (char) (Math.random() * 25));
			else
				scanCode += (char)('0' + (char) (Math.random() * 9));
		title = Util.randomElementFrom(shopTitles);
		description = Util.randomElementFrom(slogans) + " "
				+ Util.randomElementFrom(slogans) + " "
				+ Util.randomElementFrom(slogans);
		logo = new ImageUrl("");
		logo.fillWithRandomData();
		shopAddress = new Address("", "", "", "", "", "");
		shopAddress.fillWithRandomData();
		billingAddress = new Address("", "", "", "", "", "");
		billingAddress.fillWithRandomData();
	}

}

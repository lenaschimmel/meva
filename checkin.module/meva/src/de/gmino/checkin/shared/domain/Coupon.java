// You may edit this file. It has been generated, but it will NOT be overwritten by Meva.
// To regenerate this file, delete it and run Meva again.

package de.gmino.checkin.shared.domain;

// gmino stuff
import de.gmino.checkin.shared.domain.gen.CouponGen;
import de.gmino.geobase.shared.domain.Duration;
import de.gmino.geobase.shared.domain.ImageUrl;
import de.gmino.meva.shared.Util;

public class Coupon extends CouponGen {

	static String[] couponTexts = { "%1 gratis!|Kaufe ein %1 und bekomme das zweite gratis dazu!", "%p% Rabatt|Auf alle %1 jetzt %p% Rabatt",
			"%1 zum Preis von %2|Kaufe ein %1 und zahle dafür nur so viel, als wäre es ein %2.", "10 zum Preis von 8|Kaufe 10 %1 zum Preis von 8 %1.",
			"%1 gratis dazu|Beim Kauf eines %2 gibt es %1 gratis dazu.", "Super-Spar-Kombo|%1 und %2 zusammen für %p% weniger." };
	static String[] products = { "Handy", "App", "Brötchen", "Pott Kaffee", "Kraftfahrzeug", "Pizza", "Waschmaschine", "Drucker", "Stück Käse", "Taschenfederkernmatratze",
			"Lamm-Döner mit extra scharf", "Massage", "Traumfänger", "Ohrloch", "Schildkröte", "Haushaltswarenset 470-teilig", "Kreuzfahrt" };

	// Constructors
	public Coupon(long id) {
		super(id);
	}

	public Coupon(long id, boolean ready, Shop shop, String title, String description, ImageUrl image, Duration duration) {
		super(id, ready, (de.gmino.checkin.shared.domain.Shop) shop, title, description, (de.gmino.geobase.shared.domain.ImageUrl) image, (de.gmino.geobase.shared.domain.Duration) duration);
	}

	public void fillWithRandomData() {
		String base = Util.randomElementFrom(couponTexts);
		base = base.replaceAll("%1", Util.randomElementFrom(products)).replaceAll("%2", Util.randomElementFrom(products)).replaceAll("%p", (int) (Math.random() * 80 + 10) + "");
		String[] parts = base.split("\\|");
		System.out.println("Base: " + base);
		System.out.println("Parts: " + parts);
		for (String part : parts)
			System.out.println("Parts[?]: " + part);
		title = parts[0];
		description = parts[1];
		image = new ImageUrl("");
		image.fillWithRandomData();
		duration = new Duration((long) (Math.random() * Duration.MONTH));
	}
}

/*
Idriftsættelse
ant -q; rsync -a dist/* deltagere.html gmail-adgangskode.txt  javabog.dk:DistribueredeSystemer/

 */
package brugerautorisation;

import brugerautorisation.data.Bruger;
import brugerautorisation.data.Brugerdatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hovedprogrammet på serveren
 * @author j
 */
public class BenytBrugerdatabase {

	public static void main(String[] args) throws IOException {

		Brugerdatabase db = Brugerdatabase.getInstans();
		System.out.println("\nDer er "+db.brugere.size()+" brugere i databasen");

		brugerautorisation.transport.soap.Brugeradminserver.main(null);

		Scanner scanner = new Scanner(System.in); // opret scanner-objekt

		while (true) try {
			System.out.println();
			System.out.println("1 Udskriv brugere");
			System.out.println("2 Generer kommasepareret fil med brugere");
			//System.out.println("2 Start RMI server");
			//System.out.println("3 Start SOAP server");
			System.out.println("4 Send mail til alle brugere, der ikke har ændret deres kode endnu");
			System.out.println("9 Gem databasen og stop programmet");
			System.out.print("Skriv valg: ");
			int valg = scanner.nextInt();
			scanner.nextLine();
			if (valg==1) {
				for (Bruger b : db.brugere) {
					System.out.println(Diverse.toString(b));
				}
			} else
			if (valg==2) {
				for (Bruger b : db.brugere) {
					System.out.println(Diverse.tilCsvLinje(b));
				}
			} else
			if (valg==3) {
			} else
			if (valg==4) {
				ArrayList<Bruger> mglBru = new ArrayList<>();
				for (Bruger b : db.brugere) {
					if (b.sidstAktiv > 0) continue;
					mglBru.add(b);
				}
				System.out.println("Der er "+mglBru.size()+" brugere, der mangler at skifte deres kode.");
				System.out.println("Det er: "+mglBru);
				System.out.println("Skriv en linje med forklarende tekst");
				String forklarendeTekst = scanner.nextLine();
				System.out.println("Er du SIKKER på at du vil sende "+forklarendeTekst+" til "+mglBru.size()+" brugere?");
				System.out.print("Skriv JA: ");
				String accept = scanner.nextLine().trim();
				if (!accept.equals("JA")) {
					System.out.println("Afbrudt med "+accept);
					continue;
				}

				for (Bruger b : mglBru) {
					Diverse.sendMail("DIST: Din adgangskode skal skiftes",
							"Kære "+b.fornavn+"\n\nDu skal skifte adgangskoden som en del af kurset i Distribuerede Systemer."
							+"\n\nDit brugernavn er "+b.brugernavn+" og din midlertidige adgangskode er: "+b.adgangskode
							+"\n\nSe hvordan du skifter koden på https://docs.google.com/document/d/1ZtbPbPrEKwSu32-SSmtcSWSQaeFid8YQI5FpI35Jkb0/edit?usp=sharing \n"
							+"\n\n"+forklarendeTekst
							+"\n\nBesked sendt p.v.a. underviseren - Jacob Nordfalk (jacno@dtu.dk)",
							b.email);
					Thread.sleep(1000);
				}
			} else
			if (valg==9) {
				break;
			} else {
				System.out.println("Ulovligt valg");
			}
		} catch (Throwable t) { t.printStackTrace(); scanner.nextLine(); }

		//db.gemTilFil();
		System.out.println("Afslutter programmet... ");
		db.gemTilFil(true);
		System.exit(0);
	}

}

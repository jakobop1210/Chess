# Beksrivelse av prosjektet
Jeg skal lage sjakk, der målet er å etterligne et ekte sjakkspill så mye som mulig.
Når man åpner appen kan man spille mot en venn, og man starter spillet ved å trykke på brikken man lyst til å flytte først. 
Hver gang man trykker på en brikke vil alle lovlige trekk bli synlige ved at rutene får en ny farge. Målet med spillet
er å vinne ved å sette motstanderen i sjakkmatt, eller at motstanderen gir opp. Jeg har bestemt meg for å ikke imnplementere spesialregler i spillet som rokade, passant, patt, trekkgjentakelser og maks trekk uten at brikke blir tatt. Jeg skal heller ikke impelmentere noen tidsbegrensning i spillet. Alt dette er for å unnga og gjøre prosjektet for omfattende, da det allerede er ganske krevende. 

# Grunnklasser
- Piece.java: Piece skal være klassen som oppretter brikkene i spillet. Klassen skal inneholde navn og farge på brikken. Jeg skal
              muligens også lage en egen klasse for hver brikketype, som arver fra Piece klassen, men dette er noe jeg ikke har 
              bestemt meg for enda. Inne i Piece klassen, skal det ved hjelp av å få inn brikkeType, posisjon og brett beregnes
              lovlige trekk for den brikken.
- Board.java: Board klassen skal opprette et sjakkbrett ved hjelp av nested arrays som er 8x8 lange. Klassen skal også legge til alle 
              brikkene slik at den sender ut brettet med brikkene oppstilt slik de er på starten av hvert spill.
- Game.java: Game klassen skal være klassen som har kontroll på hvert spill man spiller. Denne klassen skal ta mye i bruk de andre klassene
             for å opprette brettet, hente ut brikker og beregne gyldige trekk. Game klassen skal i tillegg ha kontroll på hvem sin tur det er, 
             i tillegg til å ha metoder for å beregne sjakk og sjakkmatt, og da si hvem som vinner. 

# Filbehandling
Jeg tenker å implementere filbehandling ved å lagre spillet man spiller. Dette skal gjøre ved å lese av boardklassen, slik at hvis man lukker appen og senere går inn igjen, kan man fortsette på det samme spillet.

# Testing
I testing delen tenker jeg å teste forskjellige stillinger som kan oppstå i spillet. Her skal det sjekkes for at alle brikkene skal kun kunne flytte til lovlige ruter, sjekke for sjakk og sjakkmatt eksempler, hvis en spiller prøver å flytte når det ikke er hans tur, i tillegg til hva som skjer hvis man prøver å flytte ut av brettet. Dette skal gjøre ved å sende inn ulike typer boards som jeg skal laget for å teste en spesifikk del. 


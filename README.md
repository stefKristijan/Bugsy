# Bugsy
get RSS Feed from bug.hr


Bugsy je aplikacija koja s Bug.hr dohva�a vijesti u XML formatu i prikazuje ih na korisni�kom su�elju. Kako bi prikaz vijesti bio �itljiv i pregledan bilo je potrebno parsirati podatke u odgovaraju�i oblik kako bi se spremili u obliku objekata klase News. Dohva�anje podataka se izvodi na posebnoj niti pomo�u klase AsyncTask. Vijesti se prikazuju u obliku kartica (CardView) u RecyclerView-u. Dodatna mogu�nost aplikacije je spremanje odabranih vijesti u lokalnu bazu podataka da ih korisnik mo�e dohvatiti u bilo koje vrijeme. 

Za dohva�anje podataka, tj. uspostavljanja HTTP veze sa stranicom bug.hr koristi se metoda getData() unutar klase RssFeed koja naslje�uje klasu AsyncTask. Metoda getData() se poziva unutar preoptere�ene metode doInBackground(), a ona uspostavlja HTTP konekciju i �alje zahtjev za dohva�anje podataka. Za prihva�anje podataka koristi se objekt klase InputStream koji se predaje metodi parse() klase ParseXml. 

U klasi ParseXml se na temelju InputStream objekta dobiveni podaci parsiraju i spremaju u obliku News objekta. Cijela pri�a se doga�a u metodi parse(InputStream) koja koristi objekte klasa XmlPullParserFactory i XmlPullParser za parsiranje podataka. Metodom getEventType() objekta XmlPullParser klase se uz pomo� switch-a odre�uje �to se doga�a s podacima, ukoliko getEventType() vra�a START_TAG koji je jednak XML elementu "item" zna�i da se radi o novim vijestima i stvara se novi objekt klase News. Ukoliko metoda getEventType() vra�a TEXT, dohva�a se tekst unutar pro�itanog XML atributa i sprema se u String, a na temelju vra�ene vrijednosti END_TAG metode getEventType() se postavljaju atributi objekta klase News na pro�itan tekst. Metodom next() XmlPullParser-a se dohva�a sljede�i XML element.

Nakon dohva�anja svih vijesti poziva se metoda onPostExecute() klase RssFeed iz koje se poziva metoda setUI() klase MainActivity. U MainActivity-u se nakon izvo�enja niti tj. vra�anja liste objekata News sve kategorije postavljaju u objekt klase Spinner pomo�u kojeg se odabiru kategorije koje �e se prikazati u RecyclerView-u. Nakon toga se postavlja RecyclerView kojem se uz listu objekata klase News i objekta klase Context �alje i boolean varijabla koja ozna�ava koji ViewHolder �e se koristiti. Ukoliko je boolean varijabla jednaka "false" tada se kreira ViewHolder koji ne sadr�i checkBox-ove, a ukoliko se proslijedi "true" kreira se ViewHolder koji uz lijevi rub ekrana prikazuje checkBox-ove. Potreba za kori�tenjem dva ViewHolder-a se pojavila zbog vi�estrukog odabira objekata iz RecyclerView-a. Prilikom dugog klika na bilo koji element RecyclerView-a kreira se ViewHolder s checkBox-ovima koje je mogu�e ozna�iti, a pritiskom na FloatingActionButton za spremanje u bazu, odabrani objekti se spremaju u lokalnu bazu podataka. Kako bi se saznalo koji objekti su ozna�eni bilo je potrebno u klasu News dodati i boolean atribut koji govori je li objekt ozna�en ili nije. 

U aplikaciji je dostupno i "pull to refresh" osvje�avanje vijesti pomo�u widget-a SwipeRefreshLayout. Funkcionalnost je postignuta vrlo lako, nakon instanciranja objekta potrebno je postaviti metodu setOnRefreshListener() u kojoj se odredi �to aplikacija treba napraviti prilikom osvje�avanja (ponovno u�itavanje podataka s bug.hr). Prilikom aktiviranja osvje�avanja pojavi se "progressBar" koji je nakon u�itavanja podataka potrebno maknuti, a to se radi metodom setRefreshing(false).
Lokalna baza podataka radi na istom principu kao baza podataka prikazana na vje�bama.

Kori�teni resursi:
http://stackoverflow.com/questions/42534954/how-to-change-layout-for-all-items-in-recyclerview --> Rje�enje su�elja koje ima dva RecyclerView-a

http://stackoverflow.com/questions/32159724/scroll-to-top-in-recyclerview-with-linearlayoutmanager --> vra�anje na vrh liste

https://www.youtube.com/watch?v=1clUga356ms --> XMLPullParser

https://developer.android.com/training/basics/network-ops/xml.html#instantiate

https://www.youtube.com/watch?v=2lcBx4KVUVk --> pull to refresh

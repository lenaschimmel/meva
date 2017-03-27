# Überblick
__Meva ist ein Java-basierts Framework für GWT, Android & iOS. Es ist ca. 2013 im StartUp _Greenmobile Innovations UG_ entstanden und wurde dort für interne Projekte genutzt.__

_Ob es jemals weiter entwickelt wird, ist fraglich. Es folgt die alte Dokumentation von damals, die hier und da ein paar unverständliche Referenzen enthalten könnte._

# Alte Dokumentation
Meva ist ein komplexes System, das uns hoffentlich in naher Zukunft die Arbeit erleichtern wird. Um es effektiv zu benutzen, sind allerdings einige Grundkenntnisse nötig, die in diesem Dokument vermittelt werden.

Dieses Dokument soll einen guten Überblick geben. Es wird (hoffentlich) gelegentlich aktualisiert, aber (ebenfalls hoffentlich) in Zukunft nicht viel Umfangreicher. Genauere Details zu bestimmten Themen werden dann stattdessen in weiteren Unterartikeln vertieft.

## Zielsetzung

Meva soll uns ermöglichen, komplexe Softwarelandschaften auf Dauer aufzubauen und zu pflegen. Dabei geht es um viele verschiedene Produkte mit teils ähnlichen und teils verschiedenen Funktionen, die aus Apps (Android, iOS, etc.), Webinterfaces, Datenbanken, Backends, Desktop-Software, etc. bestehen. 

Die Grundprinzipien von Meva sind:
 * Nutze so viel Java, wie möglich
 * Benutze Code auf mehreren Plattformen
 * Benutze Code in mehreren Projekten
 * Halte so viele Optionen für die Zukunft offen, wie möglich
 * Biete ein einheitliches Programmiermodell, an das man sich gewöhnen kann
 * Nehme dem Entwickler so viel Arbeit wie möglich ab, insbesondere bei Serveranfragen und bei der Datenspeicherung sowie beim komplexen Threadgedöns auf Android und GWT

Meva ist nicht "Die Plattform" (im Sinne von Lenas etwas komischer Definition, auch bekannt als "Kommunikationsplattform") und Meva ist nicht "Cubenet". Derzeit baut Meva auf völlig anderen Prinzipien, kann aber trotzdem *manches* von dem, was Plattform und Cubenet konnten oder können sollten. Meva ist so erweiterbar, dass es später vielleicht sogar *alles* das können wird, was Plattform und Cubenet können sollten. Außerdem hilft uns Meva, das zu bauen, was als "Appbaukasten", "Softwareplattform" oder "Geoframework" bekannt war, oder einfach als "Plattform" nach der umfangreicheren Definition. Konkret entsteht diese Grundlage gerade als ein Meva-Modul mit dem Namen "geobase".

## Codegenerierung

Meva generiert Java-Code, den wir somit nicht mehr von Hand schreiben müssen. Es handelt sich dabei um die so genannten Domänenklassen, welche also die Dinge und Sachverhalte repräsentieren, um die es in unserer jeweiligen Anwedungen geht. Dazu wird rein deklarativ definiert, welche Felder die Klassen haben sollen, was derzeit in einer JSON-Notation statfindet. Daraus generiert Meva Klassen mit den folgenden Standardfunktionaltiäten:

 * Felder
 * Getter-Methoden
 * Setter-Methoden
 * Konstruktoren bzw. Zugriff über Facotry-Klassen
 * Serialisierung und Desrialisierung in verschiedenen Formaten (nicht jedes auf jeder Plattform verfügbar)
   * Binär
   * JSON
   * SQL
 * Tabellenschema in SQL
 * Identifizierung von Entitäten mittels IDs
 * Lazy-Loading von Entitäten (ermöglichen eingeschränktes Arbeiten mit Entitäten, die eigentlich noch gar nicht da sind)
 * Code für das einfache Ableiten von Klassen mit manuell geschriebenem Code

Die Vorteile des generierten Codes sind:
 
 * Einheitliche Schnittstellen und Verhaltensweisen
 * Kann zentral um neue Features erweitert werden, die dann automatisch für alle Klassen verfügbar werden
 * Weniger Fehleranfällig, da jeder Fehler nur einmal im Generator gefixt werden muss
 * Weniger Arbeit - somit können ein paar Deluxe-Features eingebaut werden, deren Pflege sonst viel zu arbeitsintensiv wäre
 * Generiertes befindet sich in eigenen Dateien - die von Hand geschriebenen Klassen enthalten daher nur den wirklich interessanten Code und bleiben übersichtlicher

Generierte Klassen sollen auf keinen Fall von Hand bearbeitet werden. Man erkennt sie an vielen Merkmaken:

 * Klassenname endet auf "...Gen"
 * Package endet auf ".gen"
 * Warnhinweis am Anfang der Datei
 * Je nach Plattform liegen sie auch in einem eigenen Source-Ordner: gen bzw. mevagen. (GWT unterstüzt nur einen gemeinsamen Sourceordner für generierten und maneullen Code.)

## Module

Ein Modul ist eine hochrangige Organisationseinheit bei Meva. Ein Modul kann einem Produkt, einer Anwendung, einer Bibliothek, etc. entsprechen und besteht fast immer aus mehreren Projekten (eins je Zielplattform).

Ein Modul kann von beliebig vielen anderen Modulen abhängen / erben, wobei aber keine Kreise entstehen drüfen.

Im Dateisystem gilt folgende Konvention: Im Workspace liegen Ordner mit dem Namen "namedesmoduls.module", und darin unterordner mit dem Namen verschiedener Über-Plattformen:
 * meva
 * android
 * gwt

Dabei hat das meva-Projekt die Besonderheit, dass hier neue Klassen definiert werden können. Andererseits hat das meva-Projekt keine konkrete Ausführungsplattform, sondern dient nur als Basis für davon abgeleitete Projekte. (Technisch gesehen hat es Java SE als Ausführungsplattform. Sollten wirklich einmal Java-SE-Programme damit erzeugt werden, würde aber vermutlich ein neuer Projekttyp hinzugefügt werden anstatt die Anwendung im meva-Projekt zu erstellen.)

Es gibt jedoch mehr Unter-Platformen, die nicht stets ihr eigenes Projekt erhalten:

 * shared
 * android
 * client
 * server

Insbesondere enthalten gwt-Projekte drei Varianten des Codes, nämlich shared, client und server.

## Codeduplikation

Meva generiert nicht nur Code, es kopiert auch automatisiert Code von einem Modul zum anderen (entlang der Modulabhängigkeiten) und innerhalb eines Moduls vom meva-Projekt zu den anderen Plattformprojekten. Dabei kann sowohl manuell geschriebener, als auch generierter Code kopiert werden, und auch Code, der bereits eine Kopie ist. Beim Kopieren ändert sich weder der Name einer Klasse oder Datei, noch das Package. Kopierte Dateien zu erkennen ist daher nicht ganz so einfach, wie generierten Code zu erkennen. Dennoch gibt es mehrere Anhaltspunkte:

 * Warnung am Anfang der Datei
 * Der packagename enthält einen anderen Modul- und/oder Plattform bezeichner als das Projekt, in dem sich die Datei befindet.
 * Auf manchen Plattformen wird ein anderer Source-Ordner namens "cpy" anstatt "src" für duplizierten Code verwendet (nicht bei gwt)

## Datenmodell

Meva folgt vielen Empfhlungen des Domain Driven Design, unter anderem auch der Unterteilung zwischen Entitäten und Werten, englisch entities und values. Dies ist eine Unterscheidung, die manch eine Programmiersprache bereits auf Sprachebene enthält, was bei Java aber nicht der Fall ist. Diese Unterscheidung einzuführen ist dabei keine Erweiterung der Sprache, sondern streng genommen eine Einschränkung, da viele Varianten, die weder Wert noch Entität sind, ausgeschlossen werden. 

Die wichtigsten Eigenschaften einer Entität:
 * Jede Entitäteninstanz wird durch eine ID gekennzeichnet
 * Pro Entitätenklasse ist eine ID global eindeutig, bezeichnet also global (auf jedem Rechner / Gerät) dieselbe Entität
 * Entitäten sind veränderbar (ihre Eigenschaften können mit Settern gesetzt werden)
 * Entitäten können als Zeile in einer Datenbank gespeichert werden, wobei es eine Tabelle/Relation je Entitätenklasse gibt
 * Anwendungscode erzeugt Entitäten nicht selbst per Kontruktor, sondern erhält die Instanzen von Fabriken (factories)
 * Auf ein Entitätenobjekt können beliebig viele Variablen verweisen, aber es wird nicht kopiert (pass by reference)
 * Entitäten verweisen ggf. auf andere Entitäten, aber sie können keine Entitäten "enthalten" bzw. nicht in anderen Entitäten "enthalten sein"

Im Gegensatz dazu haben Werte folgende eigenschaften:
 * Werte haben keine eigenen Identität
 * Werte sind nach der Konstruktion unveränderbar (immutable)
 * Werte können prinzipiell dupliziert werden bzw. per Konstruktor erzeugt
 * Jede Entität hat ihre eigenen Werte, selbst wenn die Werte gleichen Inhalt haben
 * Werte können aus anderen Werten zusammengesetzt sein
 * Auf Werte wird nicht verweisen, sondern sie werden übergeben (pass by value)
 * Entitäten können Werte enthalten, Werte können auf Entitäten verweisen

## Id-Handling und Anfragen

Eine Id ist eine Zahl und hat für sich genommen keine Eindeutigkeit. Nur in Verbindung mit dem Klassennamen ist sie eindeutig und kann auf eine exisiterende Entität verweisen oder nicht. Anwendungscode sollte so wenig wie möglich mit Ids umgehen und wenn möglich dierekt mit den Entitäten-Objekten. Intern wird hingegen sehr viel mit den Ids gearbeitet, sei es bei der Speicherung, Übertragung oder Anfrage von Entitäten, oder bei den Verweisen von Entitäten untereinander. In den meisten Fällen bestehen Abstraktionen, um die Anwendungslogik von diesen Details zu trennen.

Aus Anwendungssicht bestehen die meisten Anfragen aus einer Selektion (oft in Form eines speziellen Werteobjektes) und die Antwort aus einer (ggf. leeren) Menge von Entitäten gleichartigen Typs.

*(hier fehlt noch was)*

## Blockierende Methodenaufrufe - wünsch dir ein Problem!

Einige Methoden, weche als Rückgabewert eine Entität haben, können Prinzipbedingt nicht immer sofort einen sinnvollen Rückgabewert liefern. Es könnte nämlich sein, dass dazu eine Anfrage an einen Server oder eine Datenbank nötig ist. Dies wiederum könnte längere Zeit in Anspruch nehmen (man sagt dann, der Aufruf "blockiert") oder sogar komplett Fehlschlagen.

Sowohl unter Android als auch in GWT-Clients müssen besondere Vorsichtsmaßnahmen ergriffen werden, wenn solche eine Methode aufgerufen wird. Logischerweise ist es nicht möglich, die Methode so zu schreiben, dass sie nie Probleme bereitet, aber man könnte sie auf verschiedene Weisen schreiben, um sich wenigstens auszusuchen, welche Art von Problem man bevorzugt. Meva geht noch ein Stück weiter, denn hier hat der Aufrufer einer Methode teilweise die Wahl, welches Problem ihm am liebsten ist, wenn es denn schon welche geben muss. Manche dieser Alternativen laufen hinaus auf "Wenn schon, geb mir das Problem sofort und zwar hart!" und andere eher auf "Verschone mich jetzt mit dem Problem, dafür nerve mich später noch mehr damit" (mehr zu letzterem auch im Abschnitt "Lazy loading").

Diese Wünsch-dir-ein-Problem-Methoden erkennt man daran, dass man ihnen ein Enum vom Typ ReturnEntityPolicy übergeben muss. Die Werte haben folgende Bedeutungen:

 * RETURN_NULL: Entweder, es wird eine vollfunktionsfähige (geladene) Entität zurückgegeben, oder null. Der Aufruf blockt nicht und macht keinen Netzwerkzugriff. Kann manchmal Arbeit ersparen, wenn man eh schon Rücksicht auf Null-Verweise nimmt.
 * RETURN_UNLOADED: Es wird sofort ein Entitätenobjekt zurückgegeben. Wenn möglich, ist dieses auch bereits geladen, es kann aber auch sein, dass die Werte noch nicht geladen sind. Ein späterer Aufruf einer Getter-Methode der Entität kann dann also erneut zu Problemen führen. Dies ist das weiter unten erläuterte "Lazy Loading".
 * THROW_EXCEPTION: Entweder es kommt eine geladene Entität zurück, oder die Methode returnt gar nicht, sondern wirft sofort eine EntityNotReadyException. Diese ist eine Runtime-Exception, das heißt, Eclipse wird einen nicht daran erinnern, dafür ein throw-catch zu basteln. Wenn man daran nicht von selbst denkt, dann kann diese Exception das gesamte Programm beenden.
 * BLOCK_ALWAYS und BLOCK_IF_NEEDED: Bei diesen Parametern muss man damit rechnen, dass die Methode eventull eine Weile lang blockt und/oder Netzwerkzugriffe macht. Der Lohn dafür ist, dass man in jedem Fall eine geladene Entität als Rückgabe erhält und keine halben Sachen. Sollte das doch einmal nicht funktionieren, setzt es eine dicke Exception.

Beim Aufruf einer Methode gibt es keinen Unterschied zwischen BLOCK_ALWAYS und BLOCK_IF_NEEDED. Die genannten Enums werden aber aber auch bei Requests verwendet, die kein einzelner Methodenaufruf sind, sondern über Request-Klassen mit Callback-Methoden funktionieren. Wenn man diese mit BLOCK_IF_NEEDED baut, *kann* es sein, dass die Anfrage in einem Hintergrundthread ausgeführt werden, und manche Callbacks aus eben diesem aufgerufen werden, andere hingegen aus dem Ui-Thread (wenn es denn einen solchen gibt, und wenn man ihn auch benutzt hat... es gibt also Ausnahmen). Das ist also eine möglichkeit der asynchronen Benachrichtigung. Andererseits *kann* der Request auch ein paar Vereinfachungen treffen, wenn nämlich alle benötigten Daten schon im Speicher sind, und die Callbacks alle synchron ( = sofort) im selben Thread aufrufen. Das beschleunigt den Programmablauf, macht die Sache aber auch schwerer vorherzusehen. Wenn bei solch einem Request stattdessen BLOCK_ALWAYS übergeben wird, wartet er in jedem Fall und liefert das Ergebnis auch dann asynchron, wenn es schon im Speicher lag.

## Lazy Loading

Das Einfache vorweg: Wenn man ein Wert-Objekt (value) erhält, dann hat es schon seine endgültigen Inhalte. Anders ginge es auch gar nicht, denn ein Wertobjekt ist stets immutable und kann sich somit nie ändern. Wert-Objekte haben also kein Lazy Loading!

Entitäten hingegen können sich fast jederzeit ändern, und zwar aus verschiedensten Gründen. Der, welcher einem am häufigsten Begenen wird, ist, dass man eine Entität angefragt hat und das System bereits weiß, welche Entität man haben will ( = es kennt schon den Typen und die Id) aber noch nicht, welche konkreten Inhalte die Entität hat. Das System kann dann eine "ungeladene" Entität zurück liefern. Die kann man sich in eine Variable packen, man kann andere Entitäten und Werte auf sie zeigen lassen, etc. Beim Versuch, einen Getter auf solch einer Entität aufzurufen, kann es aber wieder Probleme geben. Aus Gründen stehen hier aber weniger alternativen zur Auswahl. Die Getter fragen daher einfach "alwaysThrowIfNotReady" - übergibt man hier true, erhält man im Problemfall eine EntityNotReadyException. Übergibt man stattdessen false - dann blockiert die Methode eventuell und gibt dann einen Wert zurück, oder aber sie wirft trotzdem eine Exception.

Zur Abwehr dieser Probleme sind verschiedene Möglichkeiten denkbar:
 * Bei der Beschaffung der Entität nutzt man irgendeine return-policy, die eben nicht RETURN_UNLOADED ist, denn nur so kommt man überhaupt an ungeladene Entitäten.
 * Man prüft vorher mit entity.isReady() ob die Entität fertig geladen ist. (den dazu passenden Setter "setReady" sollten nur Menschen benutzen, die wissen, was dann passiert. Solche Menschen leben aber noch nicht.)
 * Man ruft entity.load() auf. Dies blockiert, bis die Entität geladen ist.
 * Man nutz entity.callWhenReady() um eine Callbackmethode aufrufen zu lassen, wenn die Entität fertig geladen ist. Das kann natürlich sofort der Fall sein, und dann kommt wieder der oben genannte Untereschied zwischen BLOCK_ALWAYS und BLOCK_IF_NEEDED zum Tragen.
 * (Später wird es auch noch eine spezielle Klasse geben, um Callbacks aufrufen zu lassen, wenn eine beliebige Menge von Entitäten komplett geladen ist. Ähnlicher Code ist schon im IdEntityRequest vorhanden.)

Eine Besonderheit sind übrigens Getter-Methoden von Entitäten, die wiederum Entitäten zurück geben. Diese bekommen zwei zusätzliche Parameter, da ja auch zwei beteligte Entiäte Probleme machen können:
 * Das boolean "alwaysThrowIfNotReady" gibt an, was passieren soll, wenn die Entität deren Methode man aufruft nicht bereit ist.
 * Die ReturnEntityPolicy gibt ab, was passiert, wenn zwar die vorliegende Entität bereit ist, aber nicht jene, die man sich zurück geben lassen will.

## Observer

Um es kurz zu sagen: Meva hat derzeit noch keine echten Observer.

Mit "Observer" wird ein Entwurfsmuster bezeichnet, bei dem beliebige Objekte sich benachrichtigen lassen können, wenn beliebige andere Objekte sich ändern. Damit werden bestimmte flexible Programmierweisen möglich. Observer haben immer auch etwas mit Callbacks zu tun - Callbacks sind aber nicht immer ein Zeichen für das Vorhandensein des Observer-Patterns. Meva hat zwar schon ein paar Callbacks, die einem "Bescheid sagen" wenn ein Request fertig ist und/oder wenn Entitäten fertig geladen sind. Meva kann aber derzeit noch nicht "Bescheid sagen", wenn sich eine Entiät verändert hat.

Diese Funktionalität soll aber in Zukunft kommen, da sie extrem vielseitig einsetzbar ist. In der sagenumwobenen "Plattform" war diese Funktion auch bereits enthalten und eines der Alleinstellungsmerkmale. Eine einfache Observer-Variante lässt sich schnell in Code einbauen. Ein wirklich tolles Observer-System kann einem nicht nur Bescheid sagen, dass sich irgendwo irgendwas geändert hat, sondern sagt einem ganz konkret, welche Eigenschaft welcher Entität sich von welchem Ausgangszustand zu welchem Endzustand geändert hat, und auch nur, wenn die Änderung gewissen Filterkriterien entspricht, die man vorher festgelegt hat.

Ein solches System ist extram praktisch, aber so komplex, dass es wohl nur umsetzbar ist, wenn ein Codegenerator im Einsatz ist, und selbst dann ist es nicht einfach. Da Meva ein Codegenerator ist, stehen die Chancen hoch, dass sowas später mal kommt. Aber das wird nicht all zu bald geschehen. Bis dahin vermisst hoffentlich niemand die Funktionalität zu sehr!

## n:m-Relationen in Meva

Wie auch bei 1:n kommt die RelationCollection oder ähnliches zum Einsatz. Dabei haben beide Entitäen eine solche. Wird in einer der beiden etwas entfernt oder hinzugefügt, so geschieht dies auch bei der anderen.

Bisher war es so, dass im Speicher und in der Json-Kommunikation beide Beziehungsrichtungen übertragen wurden, aber die RelationCollection nicht ins SQL serialisiert wurde. Bei der Deserialisierung hingegen wird ein Join über die Tabellen der Einzel- und Mehrfachentitäten vorgenommen, um die Collection zu befüllen.

Bei n:m-Relationen ist eine weitere Tabelle unumgänglich - allerdings auch nur genau eine Tabelle je Entitätenpaar. Spätestens bei der Benennung dieser Tabelle muss eine eindeutige Reihung zwischen beiden bestehen. Auch der programminterne Relationsname muss Teil des Tabellennamens sein, da zwischen den gleichen Entitäten mehrere n:m-Relationen bestehen können.

Die Re	quests müssen prinzipiell nicht viel anders aussehen. Allerdings stellen sich - eigentlich auch schon bei 1:n - Integritätsfragen. Aber die Json-Darstellung bleibt eben.

Die prinzipielle Operation auf diesen Relationen ist: entferne ein Tupel oder füge es hinzu. Das geschieht dann in beiden Objekten im Ram symmetrisch und wirkt sich auf das Json beider aus. Sobald eines gespeichert wird, muss die Relationstabelle angepasst werden, d.h. es müssen alle bekannten Tupel hinzugefügt werden (unter Ignoranz der schon vorhandenen), aber vorallem auch alle gelöscht werden, die nicht mehr vorhanden sind. Letzteres geht wohl nur über eine Mengenoperation mit dem Stand der DB oder mit dem alten Stand im Ram. In dem Moment, wo eine Entität ins SQL serealisiert wird, gibt es aber keine Anlage mehr im Ram über den zuvor serialisierten Stand - zumindest noch nicht. Ein DELETE WHERE NOT IN könnte helfen.

In 1:n-Relationen setzt die RelationCollection bereits die Einzelreferenz im anderen Objekt, auf den eigenen Wert oder auf null. Das ist in gewisser Weise ein Sonderfall von hinzufügen und entfernen und könnte daher über eine einheitliche API geschehen. Somit wäre dann auch nur eine RelationCollection nötig, egal ob 1:n oder n:m.

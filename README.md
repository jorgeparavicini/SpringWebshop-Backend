<br />
<div style="text-align: center">
    <img src="resources/img/logo.png" alt="Logo" width="80" height="80">
    <h1 style="margin-top: 0">Spring Technik</h1>
    <p>
        Ein Technologiewebshop für das Java Enterprise Edition Modul
    </p>
    <a href="https://git.ffhs.ch/jorge.paravicini/jee_webshop"><strong>Zum Backend</strong></a>
    <br />
    <a href="https://git.ffhs.ch/jorge.paravicini/jee_webshop_frontend"><strong>Zum Frontend</strong></a>
</div>

---

## Inhalt

1. [Einführung](#einführung)
   1. [Anforderungen](#anforderungen)
   2. [Architektur](#architektur)
2. [Installation](#installation)
3. [Statusberichte](#statusberichte)
    1. [Statusbericht 1](#statusbericht-1)

---

## Einführung <a name="einführung"></a>

Das Ziel dieses Projektes ist es, ein Webshop zu erstellen. In diesem Webshop werden vor allem PC Komponenten und 
andere technologische Geräte verkauft.

### Anforderungen <a name="anforderungen"></a>

In diesem Abschnitt werden die Anforderungen für das Endprodukt des Webshops spezifiziert. 
Die Bedingung an das Projekt ist, dass das Backend mit JEE oder einem ähnlichen Produkt entwickelt wird.
Als Alternative für Jakarta wird Spring Boot verwendet.

Folgende Features sollen im Endprodukt vorhanden sein:

* User Management
  * Sign up 
  * Login / Logout 
  * Profil aktualisieren 
* Inhalte 
  * Sortiment Übersicht 
  * Bestsellers die Täglich / wöchentlich ändern 
  * Detailansicht für einzelne Produkte 
  * Persönlicher Einkaufswagen 
  * Wunschliste / Merkliste 
  * Reviews und Kommentare für Produkte 
  * Bestellen mit Lieferadresse aus Konto vorausgefüllt, falls angemeldet 
* Admin 
  * Ein Admin panel um alle Produkte zu verwalten 
  * Permission- und User verwaltung 
  * Kann Rezensionen löschen (Falls sie nicht den TOS folgen)
  * Backend Endpoints müssen entsprechend beschützt werden, dass nur Admins diese ausführen können. Ansonsten sollte eine Fehlermeldung mit Status 403 Forbidden zurückgegeben werden.


### Architektur <a name="architektur"></a>

Der Webshop wird folgendermassen aufgebaut:

![Grobe Architektur](resources/img/Grobe Architektur.png)

Das Frontend, welches in Angular entwickelt wird, kommuniziert mit dem Backend anhand einer REST-API.
Das Backend kommuniziert mit einer MySQL Datenbank, um die Requests der Benutzer zu bearbeiten.

Eine Anforderung ist, dass nur Admins neue Produkte erstellen können.
Eine weitere ist, dass Benutzer Rezensionen schreiben und editieren können.
Damit diese Anforderungen erfüllt werden können, braucht es ein Identity Management.
In diesem Fall wird auth0 als provider verwendet.
Das Frontend kann dem Backend ein Bearer Token in dem `Authorization` Header mitgeben, um beschützte Endpoints auszuführen.

Falls das Backend eine Anfrage erhält für einen beschützten Endpoint ohne `Authorization` Header, 
dann wird ein `401 Unauthorized` Fehler zurückgegeben. Falls ein `Authorization` Header mitgegeben wurde, 
jedoch dieser Falsch ist, oder nicht genügend Rechte hat, um diesen Endpoint auszuführen, 
dann wird ein `403 Forbidden` Fehler zurückgegeben.

### Backend

TO-DO: Entwurf Backend

### Frontend

TO-DO: Entwurf Frontend

## Installation <a name="installation"></a>

TO-DO: Erste Dockerfile wurde erstellt. Überprüfen, ob diese korrekt funktioniert und Dokumentation ergänzen.

## Statusberichte <a name="statusberichte"></a>

### Statusbericht 28.09.2022 <a name="statusbericht-1"></a>

In dem ersten Arbeitsschritt wurde klar definiert, was die Endapplikation unterstützen muss und welche Technologien 
verwendet werden sollen. Zusätzlich wurden drei Projekte erstellt. 
Das erste Projekt ist das Frontend, welches in Angular geschrieben wird. Da ich Angular schon etwas kenne, kann ich
mich auf das Entwickeln einer guten Architektur und auf das Backend fokussieren. 
Das zweite Projekt ist das Spring Boot Backend. Spring wurde über Jakarta ausgewählt, da in meiner Erfahrung 
Spring beliebter ist und daher bessere Dokumentationen gefunden werden können.
Schlussendlich wurde noch ein Projekt in Auth0 erstellt, welches das User-Management übernehmen wird. 
Zusätzlich zu den Projekten wurde für das Front- und Backend ein GitLab Repository erstellt welche im Header dieses
Dokumentes verlinkt sind.

Um die Dokumentation zu vereinfachen, wurde im Backend Swagger installiert. Dies ermöglicht die Endpoints im Code
zu dokumentieren und im Browser danach anzusehen. Hier ist ein Beispiel wie die Dokumentation für das Backend aussehen wird.

![An example of the final Swagger Documentation](resources/img/swagger doc.png)

Als Nächstes wurde eine Architektur für das Backend entworfen, welche noch verfeinert werden muss und im nächsten
Statusbericht hinzugefügt wird. Aber in Kürze, die Architektur wird dem MVC (Model-View-Controller) Architektur pattern 
folgen. Es wird ein Model geben, welche die Entities der Datenbank repräsentieren, eine View, welche die Schnittstelle
zu den Endnutzern darstellen. Schlussendlich gibt es noch die Controller, welche die Anfragen der Benutzer empfange, 
sie verarbeiten, und die verarbeiteten Models wieder zurückgibt. 

Schlussendlich wurde das Backend initialisiert, sodass Kotlin unterstützt wird und alle benötigten Abhängigkeiten
installiert sind. In diesem Zustand ist das Backend bereit entwickelt zu werden, sobald die genauen Schnittstellen 
entworfen sind.

Als Seitennote warum MySql verwendet wird anstatt PostgreSQL. Es wurden schon ein par kleine Controllers entwickelt, um
sicherzustellen, dass die Architektur funktioniert. Dabei wurde observiert, dass wenn man die Datenbank seeded, also
vordefinierte Daten in die Datenbank ladet, dann wird der interne ID-Counter von PostgreSQL nicht erhöht. Das bedeutet,
dass wenn man 5 Einträge vordefiniert, und danach einen neuen Eintrag in dieselbe Tabelle einfügen möchte, dann gibt 
es einen ID-Konflikt. Dieser Fehler existiert in MySQL nicht, da der ID-Counter auch erhöht wird, wenn man Einträge mit
expliziten IDs hinzufügt.

Die nächsten Schritte werden sein:
* Schnittstelle zwischen Front- und Backend definieren
* Architektur des Backends vervollständigen und dokumentieren
* Autorisierung und Authentifizierung im Backend mit dem Auth0 Server verbinden
* Definierte Schnittstellen im Backend umsetzen und Dokumentieren

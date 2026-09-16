# Automatische Tests und Testing-Tools

# Inhaltsverzeichnis
- [Automatische Tests und Testing-Tools](#automatische-tests-und-testing-tools)
- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Automation Testing – Lösung](#automation-testing--lösung)
    - [Ausgangslage](#ausgangslage)
  - [Übung 1 – REST-Schnittstelle automatisiert testen](#übung-1--rest-schnittstelle-automatisiert-testen)
    - [#Gewähltes Werkzeug](#gewähltes-werkzeug)
    - [Testfälle](#testfälle)
    - [Beispiel](#beispiel)
    - [Ausführen](#ausführen)
  - [Übung 2 – Angular End-To-End Test](#übung-2--angular-end-to-end-test)
    - [Gewähltes Werkzeug](#gewähltes-werkzeug-1)
    - [Testfälle](#testfälle-1)
      - [Studentenliste anzeigen](#studentenliste-anzeigen)
      - [Student über das GUI erstellen](#student-über-das-gui-erstellen)
    - [Installation](#installation)
    - [Ausführen](#ausführen-1)
  - [Übung 3 – Performance Testing mit JMeter](#übung-3--performance-testing-mit-jmeter)
  - [Gewähltes Werkzeug](#gewähltes-werkzeug-2)
    - [Aufbau des Tests](#aufbau-des-tests)
    - [Installation unter macOS](#installation-unter-macos)
    - [GUI starten](#gui-starten)
  - [Ausführung über die Kommandozeile](#ausführung-über-die-kommandozeile)
    - [Wichtige Messwerte in JMeter](#wichtige-messwerte-in-jmeter)
      - [Samples](#samples)
      - [Average](#average)
      - [Min / Max](#min--max)
      - [Error %](#error-)
      - [Throughput](#throughput)
      - [Percentiles](#percentiles)
    - [Erkenntnisse](#erkenntnisse)
  - [Vergleich der eingesetzten Tools](#vergleich-der-eingesetzten-tools)


## Automation Testing – Lösung

### Ausgangslage

Die Anwendung besteht aus einem **Spring-Boot-Backend** und einem **Angular-Frontend**.

- Backend: `http://localhost:8081`
- REST-Endpunkt: `http://localhost:8081/students`
- Frontend: `http://localhost:4200`

Das Backend stellt momentan zwei REST-Operationen bereit:

- `GET /students` – alle Studierenden laden
- `POST /students` – einen neuen Studenten speichern

## Übung 1 – REST-Schnittstelle automatisiert testen

### #Gewähltes Werkzeug

Für die automatisierten REST-Tests verwende ich **JUnit 5 mit `TestRestTemplate`**.

Der Test startet Spring Boot auf einem zufälligen freien Port und sendet echte HTTP-Requests an das Backend. Dadurch teste ich nicht nur eine einzelne Methode, sondern die REST-Schnittstelle inklusive Controller, JSON-Verarbeitung, Spring-Konfiguration und H2-Datenbank.

Die Tests befinden sich unter:

```text
src/test/java/ch/tbz/m450/testing/tools/controller/StudentRestApiTest.java
```

### Testfälle

| Test | Erwartetes Resultat |
|---|---|
| `GET /students` | HTTP-Erfolg, JSON-Antwort und vorhandene Studierende werden zurückgegeben |
| `POST /students` | Neuer Student wird gespeichert und ist anschliessend über `GET /students` auffindbar |
| unbekannter Endpunkt | HTTP 404 wird zurückgegeben |

### Beispiel

```java
@Test
void postStudentCreatesStudentThatCanBeReadAgain() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, String> body = Map.of(
            "name", "Charlie",
            "email", "charlie@tbz.ch"
    );

    ResponseEntity<Void> postResponse = restTemplate.postForEntity(
            "/students",
            new HttpEntity<>(body, headers),
            Void.class
    );

    assertTrue(postResponse.getStatusCode().is2xxSuccessful());
}
```

### Ausführen

Im Projektordner:

```bash
mvn test
```

Damit werden die Tests automatisch ausgeführt.

## Übung 2 – Angular End-To-End Test

### Gewähltes Werkzeug

Für den automatisierten Browser-Test verwende ich **Playwright**.

Playwright steuert einen echten Browser und führt die Anwendung aus Sicht eines Benutzers aus. Dadurch kann ich beispielsweise Navigation, Formulare und die Kommunikation zwischen Angular und Spring Boot gemeinsam testen.

Die E2E-Tests befinden sich unter:

```text
src/main/js/my-app/e2e/students.spec.ts
```

Die Konfiguration befindet sich in:

```text
src/main/js/my-app/playwright.config.ts
```

### Testfälle

#### Studentenliste anzeigen

Der Browser öffnet `/students` und kontrolliert:

- die Tabelle ist sichtbar
- ein vom Backend angelegter Student wird angezeigt
- die zugehörige E-Mail-Adresse wird angezeigt

#### Student über das GUI erstellen

Der Browser führt automatisch folgende Schritte aus:

1. `/addstudents` öffnen
2. prüfen, dass `Submit` ohne Eingabe deaktiviert ist
3. Name eingeben
4. E-Mail eingeben
5. `Submit` anklicken
6. Weiterleitung auf `/students` prüfen
7. prüfen, ob der neue Student in der Tabelle erscheint

### Installation

Im Angular-Projekt:

```bash
cd src/main/js/my-app
npm install
npx playwright install chromium
```

### Ausführen

```bash
npm run e2e
```

Die `playwright.config.ts` startet bei Bedarf automatisch:

- Spring Boot auf Port `8081`
- Angular auf Port `4200`
- Chromium für die eigentlichen Tests

Für eine visuelle Ausführung kann ich verwenden:

```bash
npm run e2e:ui
```

## Übung 3 – Performance Testing mit JMeter

## Gewähltes Werkzeug

Für den Belastungstest des Backends verwende ich **Apache JMeter**.

JMeter kann viele virtuelle Benutzer erzeugen und dadurch messen, wie sich eine Schnittstelle unter höherer Last verhält.

Der vorbereitete Testplan befindet sich unter:

```text
performance/student-api-load-test.jmx
```

### Aufbau des Tests

Der Test belastet:

```text
GET http://localhost:8081/students
```

Konfiguration:

| Einstellung | Wert |
|---|---:|
| Virtuelle Benutzer / Threads | 25 |
| Ramp-Up-Zeit | 5 Sekunden |
| Durchläufe pro Benutzer | 20 |
| Maximale Requests bei vollständiger Ausführung | 500 |
| Erwarteter HTTP-Status | 200 |
| Connect Timeout | 5 Sekunden |
| Response Timeout | 5 Sekunden |

Die 25 Benutzer werden innerhalb von 5 Sekunden gestartet. Jeder Benutzer führt 20 Requests aus. Dadurch werden insgesamt bis zu 500 Requests an die REST-Schnittstelle gesendet.

### Installation unter macOS

```bash
brew install jmeter
```

### GUI starten

```bash
jmeter
```

Danach kann die Datei

```text
performance/student-api-load-test.jmx
```

in JMeter geöffnet und gestartet werden.

## Ausführung über die Kommandozeile

Zuerst muss das Spring-Boot-Backend laufen:

```bash
mvn spring-boot:run
```

Danach kann der Performance-Test ausgeführt werden:

```bash
jmeter -n \
  -t performance/student-api-load-test.jmx \
  -l performance/results.jtl \
  -e \
  -o performance/report
```

Der HTML-Bericht befindet sich anschliessend unter:

```text
performance/report/index.html
```

> Das Verzeichnis `performance/report` darf beim Erstellen des Reports noch nicht existieren bzw. muss leer sein.

### Wichtige Messwerte in JMeter

#### Samples

Die Anzahl der ausgeführten Requests.

#### Average

Die durchschnittliche Antwortzeit aller Requests.

#### Min / Max

Die schnellste beziehungsweise langsamste gemessene Antwortzeit.

#### Error %

Der prozentuale Anteil fehlgeschlagener Requests. Bei einem erfolgreichen Test sollte dieser Wert möglichst bei `0 %` liegen.

#### Throughput

Zeigt, wie viele Requests das System innerhalb einer bestimmten Zeit verarbeiten konnte. Damit kann ich beurteilen, wie stark das Backend belastbar ist.

#### Percentiles

Percentiles zeigen, wie schnell ein bestimmter Anteil der Requests verarbeitet wurde. Beispielsweise bedeutet das 95. Percentile, dass 95 % der Requests höchstens diese Antwortzeit benötigt haben.

### Erkenntnisse

Mit JMeter kann ich nicht nur überprüfen, **ob** die REST-Schnittstelle funktioniert, sondern auch, wie sie sich bei vielen gleichzeitigen Zugriffen verhält.

Im Gegensatz zu einem normalen funktionalen Test liegt der Schwerpunkt beim Performance-Test auf Eigenschaften wie:

- Antwortzeit
- Durchsatz
- Fehlerrate
- Verhalten bei steigender Last
- Stabilität des Backends

Die Last lässt sich einfach erhöhen, indem ich beispielsweise die Anzahl Threads oder die Anzahl Durchläufe im Testplan erhöhe. 

## Vergleich der eingesetzten Tools

| Übung | Tool | Testart | Was wird getestet? |
|---|---|---|---|
| 1 | JUnit 5 + TestRestTemplate | automatisierter REST-/Integrationstest | Spring-Boot-REST-Schnittstelle |
| 2 | Playwright | End-To-End / GUI-Test | Angular + Browser + Backend |
| 3 | JMeter | Performance-/Load-Test | Verhalten des Backends unter Last |

Die drei Varianten testen damit unterschiedliche Ebenen der Anwendung. Der REST-Test ist schnell und gezielt, der Playwright-Test überprüft einen vollständigen Benutzerablauf und JMeter untersucht nicht-funktionale Eigenschaften wie Performance und Belastbarkeit.

# Teststrategien

## Inhaltsverzeichnis
- [Teststrategien](#teststrategien)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Übung 1](#übung-1)
    - [Abstrakte Testfälle](#abstrakte-testfälle)
    - [Konkrete Testfälle](#konkrete-testfälle)
  - [Übung 2](#übung-2)
    - [Funktionale Black-Box Testfälle](#funktionale-black-box-testfälle)
    - [Einordnung der Testfälle](#einordnung-der-testfälle)
  - [Übung 3](#übung-3)
    - [Black-Box Testfälle](#black-box-testfälle)
    - [Besonders wichtige Grenz- und Fehlerfälle](#besonders-wichtige-grenz--und-fehlerfälle)
    - [White-Box Testfälle](#white-box-testfälle)
      - [`Account.deposit()`](#accountdeposit)
      - [`Account.withdraw()`](#accountwithdraw)
      - [`Bank.createAccount()`](#bankcreateaccount)
      - [`Bank.getAccount()`](#bankgetaccount)
      - [`Bank.deleteAccount()`](#bankdeleteaccount)
      - [`Counter.convertCurrency()`](#counterconvertcurrency)
    - [Verbesserungen und Best Practices](#verbesserungen-und-best-practices)
      - [1. Keine `double`-Werte für Geld verwenden](#1-keine-double-werte-für-geld-verwenden)
      - [2. Negative Beträge verhindern](#2-negative-beträge-verhindern)
      - [3. Verantwortlichkeiten besser trennen](#3-verantwortlichkeiten-besser-trennen)
      - [4. API-Key nicht direkt im Sourcecode speichern](#4-api-key-nicht-direkt-im-sourcecode-speichern)
      - [5. Keine zu allgemeinen Exceptions abfangen](#5-keine-zu-allgemeinen-exceptions-abfangen)
      - [6. Eingaben vor `substring()` überprüfen](#6-eingaben-vor-substring-überprüfen)
      - [7. Währungsumrechnung auslagern](#7-währungsumrechnung-auslagern)
      - [8. Namen verständlich und einheitlich wählen](#8-namen-verständlich-und-einheitlich-wählen)
    - [Fazit](#fazit)

## Übung 1

### Abstrakte Testfälle

Bei den abstrakten Testfällen verwende ich keine konkreten Preise, sondern Bedingungen mit logischen Operatoren.

| ID | Kaufpreis                             |         Erwarteter Rabatt |
| -- | ------------------------------------- | ------------------------: |
| A1 | Preis < 15'000 CHF                    |                       0 % |
| A2 | Preis >= 15'000 CHF und <= 20'000 CHF |                       5 % |
| A3 | Preis > 20'000 CHF und < 25'000 CHF   |                       7 % |
| A4 | Preis > 25'000 CHF                    |                     8.5 % |
| A5 | Preis = 25'000 CHF                    | Nicht eindeutig definiert |

Bei einem Kaufpreis von genau **25'000 CHF** ist die Anforderung nicht eindeutig. Es steht nur, dass unter 25'000 CHF 7 % Rabatt und darüber 8.5 % Rabatt gewährt werden. Deshalb müsste geklärt werden, welcher Rabatt bei genau 25'000 CHF gilt.

### Konkrete Testfälle

Bei den konkreten Testfällen verwende ich feste Eingabewerte. Ich habe bewusst Werte direkt an den Grenzen gewählt, da dort besonders häufig Fehler auftreten können.

| ID | Eingabe Kaufpreis |         Erwarteter Rabatt |
| -- | ----------------: | ------------------------: |
| K1 |        14'999 CHF |                       0 % |
| K2 |        15'000 CHF |                       5 % |
| K3 |        20'000 CHF |                       5 % |
| K4 |        20'001 CHF |                       7 % |
| K5 |        24'999 CHF |                       7 % |
| K6 |        25'000 CHF | Nicht eindeutig definiert |
| K7 |        25'001 CHF |                     8.5 % |

## Übung 2

Für diese Aufgabe verwende ich als Beispiel eine Webseite für Autovermietung. Ich teste dabei nur Funktionen, die ich als Benutzer auf der Webseite verwenden kann.

Da es sich um **Black-Box Tests** handelt, kenne und berücksichtige ich den internen Programmcode nicht. Ich überprüfe nur, ob bestimmte Eingaben zum erwarteten Ergebnis führen.

### Funktionale Black-Box Testfälle

| ID | Beschreibung                                                                                              | Erwartetes Resultat                                                                                  | Effektives Resultat | Status | Mögliche Ursache                                                                    |
| -- | --------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- | ------------------- | ------ | ----------------------------------------------------------------------------------- |
| 1  | Ich suche mit einem gültigen Abholort sowie einem gültigen Abhol- und Rückgabedatum nach einem Mietwagen. | Die verfügbaren Fahrzeuge für den ausgewählten Zeitraum werden angezeigt.                            | Resultat wie erwartet | Erfolgreich  | Kein Fehler               |
| 2  | Ich wähle ein Rückgabedatum, das vor dem Abholdatum liegt.                                                | Die Suche wird verhindert oder es wird eine verständliche Fehlermeldung angezeigt.                   | Beim Auswahl auf ein Datum welches in der Vergangenheit liegt wird automatisch mit dem Startdatum versehen | Erfolgreich  | Kein Fehler                                    |
| 3  | Ich wähle ein verfügbares Fahrzeug aus den Suchergebnissen aus.                                           | Das ausgewählte Fahrzeug wird mit dem korrekten Preis und den Buchungsinformationen angezeigt.       | Resultat wie erwartet | Erfolgreich  | Kein Fehler                         |
| 4  | Ich füge eine zusätzliche Option wie einen Zusatzfahrer oder einen Kindersitz hinzu.                      | Die zusätzliche Option wird zur Buchung hinzugefügt und der Gesamtpreis wird entsprechend angepasst. | Resultat wie erwartet | Erfolgreich  | Kein Fehler |
| 5  | Ich führe eine vollständige Buchung (ohne Zahlung) mit gültigen Kundendaten durch.                                       | Ich werde ohne Fehler erfolgreich zur Zahlungsmethode weitergeleitet  | Resultat wie erwartet | Erfolgreich  | Kein Fehler |

### Einordnung der Testfälle

Bei meinen Testfällen handelt es sich um **funktionale Black-Box Tests**. Ich teste Funktionen, die mir als Benutzer auf der Webseite zur Verfügung stehen. Dabei ist mir nicht bekannt, wie diese Funktionen intern programmiert wurden. Ich überprüfe lediglich meine Eingaben und die daraus resultierenden Ausgaben beziehungsweise Reaktionen der Webseite.

## Übung 3

### Black-Box Testfälle

Bei den Black-Box Tests betrachte ich die Software aus Sicht eines Benutzers. Ich kenne dabei zwar für diese Aufgabe den Sourcecode, berücksichtige ihn bei der eigentlichen Durchführung der Black-Box Tests jedoch nicht. Ich überprüfe nur meine Eingaben und die daraus entstehenden Ergebnisse.

| ID  | Beschreibung                      | Eingabe / Durchführung                                                  | Erwartetes Resultat                                                                                        | Effektives Resultat | Status |
| --- | --------------------------------- | ----------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- | ------------------- | ------ |
| B1  | Alle Konten anzeigen              | Im Hauptmenü `a` eingeben                                               | Alle vorhandenen Konten werden mit Kontonummer, Name und Währung angezeigt.                                | Resultat wie erwartet | Erfolgreich  |
| B2  | Bestehendes Konto auswählen       | Eine gültige Kontonummer eingeben                                       | Das ausgewählte Konto und dessen Informationen werden angezeigt.                                           | Resultat wie erwartet | Erfolgreich   |
| B3  | Nicht vorhandenes Konto auswählen | Eine ungültige Kontonummer eingeben                                     | Es wird eine verständliche Fehlermeldung angezeigt und die Anwendung läuft weiter.                         | Resultat wie erwartet | Erfolgreich  |
| B4  | Geld einzahlen                    | Ein Konto auswählen und einen positiven Betrag einzahlen                | Der Betrag wird dem aktuellen Kontostand hinzugefügt.                                                      | Resultat wie erwartet | Erfolgreich  |
| B5  | Geld abheben                      | Einen Betrag abheben, der kleiner als der Kontostand ist                | Der Betrag wird vom Kontostand abgezogen.                                                                  | Resultat wie erwartet | Erfolgreich  |
| B6  | Zu viel Geld abheben              | Einen Betrag eingeben, der höher als der Kontostand ist                 | Die Auszahlung wird verhindert und der Kontostand bleibt unverändert.                                      | Resultat wie erwartet | Erfolgreich  |
| B7  | Negativen Betrag einzahlen        | Zum Beispiel `-100` einzahlen                                           | Die Eingabe sollte abgelehnt werden und der Kontostand darf sich nicht verändern.                          | Resultat wie erwartet | Erfolgreich  |
| B8  | Negativen Betrag abheben          | Zum Beispiel `-100` abheben                                             | Die Eingabe sollte abgelehnt werden und der Kontostand darf sich nicht verändern.                          | Resultat wie erwartet | Erfolgreich  |
| B9  | Geld auf anderes Konto überweisen | Zwei verschiedene Konten auswählen und einen gültigen Betrag überweisen | Der Betrag wird beim ersten Konto abgezogen und beim Zielkonto hinzugefügt.                                | Resultat wie erwartet | Erfolgreich  |
| B10 | Auf dasselbe Konto überweisen     | Das aktuell verwendete Konto als Ziel auswählen                         | Die Überweisung wird verhindert und eine Fehlermeldung wird angezeigt.                                     | Resultat wie erwartet | Erfolgreich  |
| B11 | Konto erstellen                   | Nachname und gültige Währung wie `CHF` eingeben                         | Ein neues Konto wird erstellt und erhält eine neue Kontonummer.                                            | Resultat wie erwartet | Erfolgreich |
| B12 | Ungültige Währung eingeben        | Zum Beispiel `ABC` als Währung eingeben                                 | Die Anwendung sollte die unbekannte Währung ablehnen oder eine verständliche Meldung anzeigen.             | Resultat wie erwartet | Erfolgreich  |
| B13 | Konto löschen                     | Ein Konto auswählen, löschen und mit `j` bestätigen                     | Das Konto wird entfernt und kann danach nicht mehr ausgewählt werden.                                      | Resultat wie erwartet | Erfolgreich |
| B14 | Löschen abbrechen                 | Ein Konto löschen und mit `n` antworten                                 | Das Löschen wird abgebrochen und das Konto bleibt bestehen.                                                | Resultat wie erwartet | Erfolgreich  |
| B15 | Ungültige Menüeingabe             | Zum Beispiel `x` eingeben                                               | Die Eingabe wird abgelehnt und das Menü erneut angezeigt.                                                  | Resultat wie erwartet | Erfolgreich  |
| B16 | Leere Eingabe                     | Bei einer Menüabfrage nur Enter drücken                                 | Die Anwendung sollte die Eingabe ablehnen und nicht abstürzen.                                             | Resultat wie erwartet | Erfolgreich |
| B17 | Wechselkurs abfragen              | Zum Beispiel `CHF USD` eingeben                                         | Der aktuelle Wechselkurs wird ausgegeben oder bei einem Fehler erscheint eine verständliche Fehlermeldung. | Resultat wie erwartet | Erfolgreich  |

### Besonders wichtige Grenz- und Fehlerfälle

Ich würde insbesondere die Testfälle mit **negativen Geldbeträgen** testen. Bei einer Banksoftware darf ein negativer Einzahlungs- oder Auszahlungsbetrag nicht akzeptiert werden.

Auch leere Eingaben sind wichtig. Die Anwendung sollte bei falschen Benutzereingaben nicht abstürzen, sondern eine entsprechende Meldung anzeigen.

### White-Box Testfälle

Bei White-Box Tests kenne ich den internen Aufbau der Software und kann gezielt einzelne Methoden sowie unterschiedliche Codepfade testen.

#### `Account.deposit()`

Diese Methode eignet sich gut für einen White-Box Test.

Mögliche Testfälle:

| Test             |                           Eingabe |     Erwartetes Ergebnis |
| ---------------- | --------------------------------: | ----------------------: |
| Normaler Betrag  |  Kontostand 1'000, Einzahlung 500 |        Kontostand 1'500 |
| Betrag 0         |    Kontostand 1'000, Einzahlung 0 |        Kontostand 1'000 |
| Negativer Betrag | Kontostand 1'000, Einzahlung -500 | Sollte abgelehnt werden |


#### `Account.withdraw()`

Bei dieser Methode gibt es verschiedene Codepfade, weshalb sie sich besonders gut für White-Box Tests eignet.

| Test                    | Ausgangslage |   Eingabe | Erwartetes Ergebnis                  |
| ----------------------- | -----------: | --------: | ------------------------------------ |
| Genügend Guthaben       |    1'000 CHF |   500 CHF | `true`, neuer Kontostand 500 CHF     |
| Genau gesamtes Guthaben |    1'000 CHF | 1'000 CHF | `true`, neuer Kontostand 0 CHF       |
| Zu wenig Guthaben       |    1'000 CHF | 1'500 CHF | `false`, Kontostand bleibt 1'000 CHF |
| Negativer Betrag        |    1'000 CHF |  -500 CHF | Sollte abgelehnt werden              |


#### `Bank.createAccount()`

Hier kann ich überprüfen, ob ein neues Konto korrekt erstellt und zur Liste der Bank hinzugefügt wird.

Mögliche Tests:

* Konto wird mit dem richtigen Namen erstellt.
* Konto besitzt die richtige Währung.
* Der Startkontostand stimmt.
* Das neue Konto erhält eine ID.
* Die Anzahl der Konten erhöht sich um eins.

#### `Bank.getAccount()`

Hier gibt es zwei wichtige Codepfade:

* Das Konto wird gefunden.
* Das Konto existiert nicht.

| Test                  | Eingabe                     | Erwartetes Ergebnis                |
| --------------------- | --------------------------- | ---------------------------------- |
| Existierende ID       | ID eines vorhandenen Kontos | Das entsprechende `Account`-Objekt |
| Nicht existierende ID | z. B. `999`                 | `null`                             |

#### `Bank.deleteAccount()`

Hier würde ich überprüfen:

* Das Konto wird tatsächlich aus der Liste entfernt.
* Die Anzahl der Konten reduziert sich um eins.
* Das gelöschte Konto kann über `getAccount()` nicht mehr gefunden werden.

#### `Counter.convertCurrency()`

Auch die Währungsumrechnung wäre ein guter Kandidat für White-Box Tests, da die Methode verschiedene Bedingungen enthält.

Beispiele:

* USD → CHF
* USD → EUR
* CHF → USD
* Nicht implementierte Kombination wie EUR → CHF

Für jede Bedingung kann gezielt überprüft werden, ob der richtige Codepfad ausgeführt wird.

### Verbesserungen und Best Practices

Beim Durchsehen des Codes sind mir mehrere Punkte aufgefallen, die ich verbessern würde.

#### 1. Keine `double`-Werte für Geld verwenden

Für Geldbeträge würde ich nicht `double`, sondern beispielsweise `BigDecimal` verwenden.

Bei `double` können durch die binäre Darstellung von Dezimalzahlen Rundungsfehler entstehen. Bei Finanzanwendungen sollte mit Geldbeträgen möglichst exakt gerechnet werden.

#### 2. Negative Beträge verhindern

Bei Einzahlungen und Auszahlungen sollte überprüft werden, ob der Betrag grösser als `0` ist.

Beispielsweise sollte Folgendes nicht möglich sein:

```java
account.deposit(-100);
account.withdraw(-100);
```

Eine entsprechende Prüfung könnte bereits direkt in der `Account`-Klasse stattfinden.

#### 3. Verantwortlichkeiten besser trennen

Die Klasse `Counter` übernimmt momentan sehr viele unterschiedliche Aufgaben:

* Benutzereingaben
* Menüausgabe
* Überweisungen
* Validierung
* Währungsumrechnung
* API-Abfragen

Ich würde diese Verantwortlichkeiten auf mehrere Klassen oder Services aufteilen.

Zum Beispiel:

```text
Counter
    → Benutzerinteraktion

AccountService
    → Einzahlen
    → Abheben
    → Überweisen

ExchangeRateService
    → Wechselkurse

InputValidator
    → Eingaben validieren
```

Dadurch werden die einzelnen Komponenten übersichtlicher und einfacher testbar.

#### 4. API-Key nicht direkt im Sourcecode speichern

Der API-Key für den Wechselkurs ist direkt im Java-Code hinterlegt.

API-Keys sollten nicht im Git-Repository gespeichert werden. Ich würde den Key beispielsweise über eine Umgebungsvariable oder eine Konfigurationsdatei laden, welche nicht eingecheckt wird.

#### 5. Keine zu allgemeinen Exceptions abfangen

Im Code wird an mehreren Stellen folgendes verwendet:

```java
catch (Exception e)
```

Besser wäre es, nur die Exceptions abzufangen, die tatsächlich erwartet werden.

Zum Beispiel:

```java
catch (NumberFormatException e)
```

Dadurch werden unerwartete Programmierfehler nicht versehentlich versteckt.

#### 6. Eingaben vor `substring()` überprüfen

Bei manchen Benutzereingaben wird direkt auf das erste Zeichen zugegriffen.

Beispielsweise:

```java
input = input.substring(0, 1);
```

Wenn der Benutzer einfach Enter drückt und somit einen leeren String eingibt, kann dies zu einem Fehler führen.

Vorher sollte deshalb überprüft werden:

```java
if (input.isEmpty()) {
    // Fehlermeldung
}
```

#### 7. Währungsumrechnung auslagern

Die Umrechnungskurse sind teilweise direkt im Code hinterlegt.

Ich würde die Währungsumrechnung in einen eigenen Service auslagern und für alle unterstützten Währungen einheitlich behandeln.

Dadurch könnte die Logik unabhängig von der Benutzeroberfläche getestet werden.

#### 8. Namen verständlich und einheitlich wählen

Einige Bezeichnungen könnten verbessert werden.

Beispielsweise:

```java
AccountExeption
```

würde ich zu

```java
AccountException
```

ändern.

Auch Methoden und Variablen sollten möglichst eindeutig beschreiben, welche Aufgabe sie erfüllen.

### Fazit

Die Banksoftware bietet mehrere gute Möglichkeiten für Black-Box und White-Box Tests.

Bei den Black-Box Tests würde ich hauptsächlich die Funktionen testen, die ein Benutzer über das Konsolenmenü ausführen kann. Dazu gehören Konten erstellen, Geld einzahlen und abheben, Überweisungen, Konten löschen und Wechselkurse abfragen.

Bei den White-Box Tests würde ich mich vor allem auf Methoden wie `deposit()`, `withdraw()`, `createAccount()`, `getAccount()`, `deleteAccount()` und die Währungsumrechnung konzentrieren.

Die wichtigsten Verbesserungen sehe ich bei der Validierung von Geldbeträgen, der Verwendung eines geeigneten Datentyps für Geld, der Trennung der Verantwortlichkeiten sowie beim sicheren Umgang mit dem API-Key.

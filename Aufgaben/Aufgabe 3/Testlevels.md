# Testlevels

# Inhaltsverzeichnis
- [Testlevels](#testlevels)
- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Aufgabe 1 - Simpler Rechner](#aufgabe-1---simpler-rechner)
  - [Aufgabe 2 – JUnit Zusammenfassung](#aufgabe-2--junit-zusammenfassung)
    - [Was ist JUnit?](#was-ist-junit)
    - [Wichtige JUnit Features](#wichtige-junit-features)
      - [`@Test`](#test)
      - [`assertEquals()`](#assertequals)
      - [`assertNotEquals()`](#assertnotequals)
      - [`assertTrue()`](#asserttrue)
      - [`assertFalse()`](#assertfalse)
      - [`assertNull()`](#assertnull)
      - [`assertNotNull()`](#assertnotnull)
      - [`assertThrows()`](#assertthrows)
    - [Setup und Cleanup](#setup-und-cleanup)
      - [`@BeforeEach`](#beforeeach)
      - [`@AfterEach`](#aftereach)
      - [`@BeforeAll`](#beforeall)
      - [`@AfterAll`](#afterall)
    - [`@DisplayName`](#displayname)
    - [`@Disabled`](#disabled)
    - [Parametrisierte Tests](#parametrisierte-tests)
      - [`@ParameterizedTest`](#parameterizedtest)
      - [`@ValueSource`](#valuesource)
      - [`@CsvSource`](#csvsource)
    - [`assertAll()`](#assertall)
    - [Aufbau eines Unit-Tests](#aufbau-eines-unit-tests)
      - [Arrange](#arrange)
      - [Act](#act)
      - [Assert](#assert)
    - [Eigenschaften guter Unit-Tests](#eigenschaften-guter-unit-tests)
    - [Vorteile von Unit-Tests](#vorteile-von-unit-tests)
    - [Unit-Tests und White-Box Testing](#unit-tests-und-white-box-testing)
    - [Testlevels](#testlevels-1)
    - [Tests ausführen](#tests-ausführen)
      - [In der Entwicklungsumgebung](#in-der-entwicklungsumgebung)
      - [Mit Maven](#mit-maven)
    - [Fazit](#fazit)
    - [Referenz](#referenz)
  - [Aufgabe 3 – Banken Simulation](#aufgabe-3--banken-simulation)
    - [Überblick](#überblick)
    - [Klassenübersicht](#klassenübersicht)
    - [`Bank`](#bank)
      - [Konten erstellen](#konten-erstellen)
      - [Sparkonto](#sparkonto)
      - [Promo-Jugendsparkonto](#promo-jugendsparkonto)
      - [Lohnkonto](#lohnkonto)
      - [Weitere Funktionen der Bank](#weitere-funktionen-der-bank)
    - [`Account`](#account)
      - [Attribute](#attribute)
      - [`id`](#id)
      - [`balance`](#balance)
      - [`bookings`](#bookings)
    - [Einzahlen](#einzahlen)
    - [Abheben](#abheben)
    - [Reihenfolge der Transaktionen](#reihenfolge-der-transaktionen)
    - [`SavingsAccount`](#savingsaccount)
    - [`SalaryAccount`](#salaryaccount)
    - [`PromoYouthSavingsAccount`](#promoyouthsavingsaccount)
    - [`Booking`](#booking)
      - [`date`](#date)
      - [`amount`](#amount)
      - [`print()`](#print)
    - [`BankUtils`](#bankutils)
      - [`formatBankDate()`](#formatbankdate)
      - [`formatAmount()`](#formatamount)
    - [Kontoauszüge](#kontoauszüge)
      - [Gesamter Kontoauszug](#gesamter-kontoauszug)
      - [Monatlicher Kontoauszug](#monatlicher-kontoauszug)
    - [Comparator-Klassen](#comparator-klassen)
      - [`AccountBalanceComparator`](#accountbalancecomparator)
      - [`AccountInverseBalanceComparator`](#accountinversebalancecomparator)
    - [Beziehungen zwischen den Klassen](#beziehungen-zwischen-den-klassen)
      - [Vererbung](#vererbung)
      - [Bedeutung](#bedeutung)
    - [Beziehung zwischen `Bank` und `Account`](#beziehung-zwischen-bank-und-account)
    - [Beziehung zwischen `Account` und `Booking`](#beziehung-zwischen-account-und-booking)
    - [Beziehung zu `BankUtils`](#beziehung-zu-bankutils)
    - [Ablauf einer Einzahlung](#ablauf-einer-einzahlung)
    - [Ablauf einer Auszahlung](#ablauf-einer-auszahlung)
    - [`Main`](#main)
    - [Zusammenfassung der Zusammenhänge](#zusammenfassung-der-zusammenhänge)
      - [Wichtigste Punkte](#wichtigste-punkte)
  - [Aufgabe 4 - Unit-Tests implementieren](#aufgabe-4---unit-tests-implementieren)


## Aufgabe 1 - Simpler Rechner
Die Klassen Calculator.java und CalculatorTest.java sind direkt in diesem Repo aufzufinden. Die Tests wurden mit Maven direkt in IntelliJ ausgeführt. Das Resultat sieht wie Folgt aus.

![Resultat Tests](/Aufgaben/Aufgabe%203/Images/Test-Results.png)

## Aufgabe 2 – JUnit Zusammenfassung

### Was ist JUnit?

JUnit ist ein Framework für automatisierte Tests in Java.

Ich verwende JUnit hauptsächlich für Unit-Tests. Dabei teste ich einzelne Klassen oder Methoden isoliert und überprüfe, ob sie das erwartete Verhalten zeigen.

Unit-Tests werden in der Regel von Entwicklern geschrieben und ausgeführt. Sie gehören zum ersten Testlevel und sind normalerweise White-Box Tests.

### Wichtige JUnit Features

#### `@Test`

Mit `@Test` markiere ich eine Methode als Testfall.

```java
@Test
void addTest() {
    assertEquals(10, calculator.add(5, 5));
}
```

JUnit erkennt dadurch, dass diese Methode beim Ausführen der Tests getestet werden soll.

#### `assertEquals()`

Mit `assertEquals()` überprüfe ich, ob das tatsächliche Ergebnis dem erwarteten Ergebnis entspricht.

```java
@Test
void addTest() {
    double result = calculator.add(5, 5);

    assertEquals(10, result);
}
```

Wenn das Ergebnis nicht `10` ist, schlägt der Test fehl.

#### `assertNotEquals()`

Mit `assertNotEquals()` überprüfe ich, ob zwei Werte unterschiedlich sind.

```java
@Test
void resultShouldNotBeWrong() {
    assertNotEquals(20, calculator.add(5, 5));
}
```

#### `assertTrue()`

Mit `assertTrue()` überprüfe ich, ob eine Bedingung `true` ergibt.

```java
@Test
void resultShouldBePositive() {
    double result = calculator.add(5, 5);

    assertTrue(result > 0);
}
```

#### `assertFalse()`

Mit `assertFalse()` überprüfe ich, ob eine Bedingung `false` ergibt.

```java
@Test
void resultShouldNotBeNegative() {
    double result = calculator.add(5, 5);

    assertFalse(result < 0);
}
```

#### `assertNull()`

Mit `assertNull()` überprüfe ich, ob ein Wert `null` ist.

```java
@Test
void accountShouldNotExist() {
    Account account = bank.getAccount(999);

    assertNull(account);
}
```

#### `assertNotNull()`

Mit `assertNotNull()` überprüfe ich, ob ein Objekt existiert.

```java
@Test
void accountShouldExist() {
    Account account = bank.createAccount("Muster", Currency.CHF, 1000);

    assertNotNull(account);
}
```

#### `assertThrows()`

Mit `assertThrows()` überprüfe ich, ob bei einer bestimmten Aktion eine erwartete Exception ausgelöst wird.

```java
@Test
void divisionByZeroShouldThrowException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.divide(10, 0)
    );
}
```

Dieser Test ist erfolgreich, wenn beim Dividieren durch `0` eine `IllegalArgumentException` ausgelöst wird.

### Setup und Cleanup

JUnit bietet verschiedene Annotations, mit denen Code automatisch vor oder nach Tests ausgeführt werden kann.

#### `@BeforeEach`

Eine Methode mit `@BeforeEach` wird vor jedem einzelnen Test ausgeführt.

```java
private Calculator calculator;

@BeforeEach
void setUp() {
    calculator = new Calculator();
}
```

Dadurch erhält jeder Test ein neues `Calculator`-Objekt.

Dies hilft dabei, die Tests unabhängig voneinander zu halten.

#### `@AfterEach`

Eine Methode mit `@AfterEach` wird nach jedem einzelnen Test ausgeführt.

```java
@AfterEach
void tearDown() {
    calculator = null;
}
```

Dies kann zum Beispiel verwendet werden, um Ressourcen nach einem Test wieder freizugeben.

#### `@BeforeAll`

Eine Methode mit `@BeforeAll` wird einmal vor allen Tests einer Testklasse ausgeführt.

```java
@BeforeAll
static void beforeAll() {
    System.out.println("Tests werden gestartet");
}
```

Diese Annotation eignet sich für Vorbereitungen, die nur einmal durchgeführt werden müssen.

#### `@AfterAll`

Eine Methode mit `@AfterAll` wird einmal ausgeführt, nachdem alle Tests einer Testklasse abgeschlossen wurden.

```java
@AfterAll
static void afterAll() {
    System.out.println("Tests wurden beendet");
}
```

### `@DisplayName`

Mit `@DisplayName` kann ich einem Test einen verständlichen Namen geben.

```java
@Test
@DisplayName("Zwei positive Zahlen werden korrekt addiert")
void addPositiveNumbers() {
    assertEquals(10, calculator.add(5, 5));
}
```

Dadurch ist in der Testausgabe besser ersichtlich, was genau getestet wird.

### `@Disabled`

Mit `@Disabled` kann ich einen Test vorübergehend deaktivieren.

```java
@Test
@Disabled
void unfinishedTest() {
    // Dieser Test wird aktuell nicht ausgeführt.
}
```

Dies kann sinnvoll sein, wenn eine Funktion noch nicht fertig implementiert wurde.

Ein deaktivierter Test sollte jedoch nicht dauerhaft ohne Grund bestehen bleiben.

### Parametrisierte Tests

Mit parametrisierten Tests kann ich denselben Test mit verschiedenen Eingabewerten ausführen.

Dadurch muss ich für ähnliche Testfälle nicht mehrere einzelne Methoden erstellen.

#### `@ParameterizedTest`

Ein parametrisierter Test wird mit `@ParameterizedTest` gekennzeichnet.

```java
@ParameterizedTest
@ValueSource(ints = {1, 5, 10, 100})
void numberShouldBePositive(int number) {
    assertTrue(number > 0);
}
```

Dieser Test wird viermal ausgeführt:

* mit `1`
* mit `5`
* mit `10`
* mit `100`

#### `@ValueSource`

Mit `@ValueSource` kann ich einem parametrisierten Test mehrere einzelne Werte übergeben.

```java
@ParameterizedTest
@ValueSource(doubles = {1.0, 5.0, 10.0})
void positiveNumbers(double number) {
    assertTrue(number > 0);
}
```

#### `@CsvSource`

Mit `@CsvSource` kann ich mehrere Eingabewerte und erwartete Resultate definieren.

```java
@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "5, 5, 10",
    "-5, 5, 0",
    "10, -2, 8"
})
void additionTest(double number1, double number2, double expected) {
    assertEquals(expected, calculator.add(number1, number2));
}
```

Jede Zeile stellt dabei einen eigenen Testfall dar.

### `assertAll()`

Mit `assertAll()` kann ich mehrere Assertions innerhalb eines Tests zusammenfassen.

```java
@Test
void accountTest() {
    Account account = new Account("Muster", Currency.CHF, 1000);

    assertAll(
        () -> assertEquals("Muster", account.getUserLastName()),
        () -> assertEquals(Currency.CHF, account.getCurrency()),
        () -> assertEquals(1000, account.getBalance())
    );
}
```

Der Vorteil ist, dass alle Assertions ausgewertet werden und nicht bereits nach der ersten fehlgeschlagenen Prüfung abgebrochen wird.

### Aufbau eines Unit-Tests

Für Unit-Tests kann ich das Prinzip **Arrange – Act – Assert** verwenden.

#### Arrange

Ich bereite die benötigten Daten und Objekte vor.

```java
Calculator calculator = new Calculator();
```

#### Act

Ich führe die Methode aus, die ich testen möchte.

```java
double result = calculator.add(5, 10);
```

#### Assert

Ich überprüfe das Ergebnis.

```java
assertEquals(15, result);
```

Ein kompletter Test sieht beispielsweise so aus:

```java
@Test
void addTest() {

    // Arrange
    Calculator calculator = new Calculator();

    // Act
    double result = calculator.add(5, 10);

    // Assert
    assertEquals(15, result);
}
```

### Eigenschaften guter Unit-Tests

Gute Unit-Tests sollten folgende Eigenschaften besitzen:

* Tests sind voneinander unabhängig.
* Ein Test überprüft möglichst genau eine Eigenschaft.
* Tests sind vollständig automatisiert.
* Tests sind schnell ausführbar.
* Tests sind wiederholbar und liefern immer dasselbe Ergebnis.
* Tests sind leicht verständlich und möglichst kurz.
* Testcode sollte eine ähnliche Codequalität wie der Produktivcode besitzen.
* Es sollte relevanter Code getestet werden.
* Unit-Tests sollten Refactorings unterstützen.

### Vorteile von Unit-Tests

Unit-Tests helfen mir dabei:

* Fehler früh zu erkennen
* bestehende Funktionen abzusichern
* versehentliche Änderungen schnell zu erkennen
* Refactorings sicherer durchzuführen
* das Verhalten einzelner Methoden zu dokumentieren
* Tests automatisiert und wiederholt auszuführen

Wenn ich später Änderungen an einer Methode vornehme, kann ich die Tests erneut starten und überprüfen, ob die bisherige Funktionalität weiterhin korrekt funktioniert.

### Unit-Tests und White-Box Testing

Unit-Tests sind normalerweise White-Box Tests.

Das bedeutet, dass ich den Sourcecode kenne und gezielt bestimmte Methoden und Codepfade testen kann.

Beispiel:

```java
if (balance >= amount) {
    balance -= amount;
    return true;
} else {
    return false;
}
```

Hier würde ich verschiedene Fälle testen:

1. Der Kontostand ist grösser als der Betrag.
2. Der Kontostand entspricht genau dem Betrag.
3. Der Kontostand ist kleiner als der Betrag.

Dadurch kann ich die verschiedenen Codepfade gezielt überprüfen.

### Testlevels

| Testlevel           | Beschreibung                                                                                | Typisch zuständig |
| ------------------- | ------------------------------------------------------------------------------------------- | ----------------- |
| Unit Testing        | Einzelne Methoden oder Klassen werden isoliert getestet                                     | Entwickler        |
| Component Testing   | Mehrere Komponenten werden gemeinsam getestet, externe Abhängigkeiten werden häufig gemockt | Entwickler        |
| Integration Testing | Zusammenspiel mit echten Schnittstellen wie Datenbanken oder APIs wird getestet             | Entwickler / QA   |
| System Testing      | Die komplette Software wird als Ganzes getestet                                             | QA / Tester       |
| Acceptance Testing  | Es wird überprüft, ob das System die Anforderungen des Kunden erfüllt                       | Kunde / Business  |

Die verschiedenen Testlevels bauen aufeinander auf.

Unit-Tests sind dabei die kleinsten und normalerweise am schnellsten ausführbaren Tests. Spätere Testlevels werden zunehmend komplexer und testen grössere Teile der Anwendung.

### Tests ausführen

#### In der Entwicklungsumgebung

JUnit-Tests können direkt über die Entwicklungsumgebung ausgeführt werden.

Zum Beispiel in IntelliJ IDEA:

1. Testklasse öffnen
2. Rechtsklick auf die Testklasse
3. `Run 'CalculatorTest'` auswählen

Alternativ kann auch ein einzelner Test ausgeführt werden.

#### Mit Maven

Alle Tests eines Maven-Projekts können über die Kommandozeile ausgeführt werden:

```bash
mvn test
```

Maven sucht dabei nach den Tests im Verzeichnis:

```text
src/test/java
```

Bei erfolgreichen Tests wird am Ende beispielsweise folgende Ausgabe angezeigt:

```text
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

### Fazit

JUnit ermöglicht mir, automatisierte Tests für Java-Anwendungen zu erstellen.

Zu den wichtigsten Features gehören für mich:

* `@Test`
* `@BeforeEach`
* `@AfterEach`
* `@BeforeAll`
* `@AfterAll`
* `@DisplayName`
* `@Disabled`
* `@ParameterizedTest`
* `@ValueSource`
* `@CsvSource`
* `assertEquals()`
* `assertNotEquals()`
* `assertTrue()`
* `assertFalse()`
* `assertNull()`
* `assertNotNull()`
* `assertThrows()`
* `assertAll()`

Mit diesen Funktionen kann ich normale Fälle, Grenzfälle und Fehlerfälle automatisiert testen.

### Referenz

Als Referenz verwende ich die offizielle JUnit-Dokumentation:

[JUnit 5 User Guide](https://docs.junit.org/5/user-guide/)

## Aufgabe 3 – Banken Simulation

### Überblick

Die Software simuliert eine einfache Bank mit verschiedenen Kontoarten.

Die wichtigsten Funktionen sind:

- Konten erstellen
- Geld einzahlen
- Geld abheben
- Kontostand abfragen
- Buchungen speichern
- Kontoauszüge ausgeben
- Konten nach Kontostand sortieren
- verschiedene Regeln je nach Kontoart anwenden

Die zentrale Klasse ist `Bank`. Die verschiedenen Kontotypen basieren auf der abstrakten Klasse `Account`.

### Klassenübersicht

| Klasse | Aufgabe |
|---|---|
| `Bank` | Verwaltet alle Konten und bietet Funktionen zum Erstellen, Einzahlen, Abheben und Anzeigen von Konten |
| `Account` | Abstrakte Basisklasse für alle Kontotypen |
| `SavingsAccount` | Sparkonto, bei dem nicht mehr Geld abgehoben werden kann als vorhanden ist |
| `SalaryAccount` | Lohnkonto mit einer Kreditlimite |
| `PromoYouthSavingsAccount` | Jugendsparkonto mit 1 % Bonus bei Einzahlungen |
| `Booking` | Repräsentiert eine einzelne Buchung auf einem Konto |
| `BankUtils` | Hilfsklasse zum Formatieren von Geldbeträgen und Datumsangaben |
| `AccountBalanceComparator` | Sortiert Konten nach Kontostand absteigend |
| `AccountInverseBalanceComparator` | Sortiert Konten nach Kontostand aufsteigend |
| `Main` | Einstiegspunkt der Anwendung |

### `Bank`

Die Klasse `Bank` verwaltet die verschiedenen Konten.

Die Konten werden in einer `TreeMap` gespeichert:

```java
private TreeMap<String, Account> accounts;
```

Als Schlüssel wird die Kontonummer verwendet und als Wert das entsprechende `Account`-Objekt.

Beim Erstellen einer neuen Bank beginnt die Vergabe der Kontonummern bei:

```text
1000
```

#### Konten erstellen

Die Bank kann drei verschiedene Kontoarten erstellen.

#### Sparkonto

```java
createSavingsAccount()
```

Die Kontonummer beginnt mit:

```text
S-
```

Beispiel:

```text
S-1000
```

#### Promo-Jugendsparkonto

```java
createPromoYouthSavingsAccount()
```

Die Kontonummer beginnt mit:

```text
Y-
```

Beispiel:

```text
Y-1001
```

#### Lohnkonto

```java
createSalaryAccount(long creditLimit)
```

Die Kontonummer beginnt mit:

```text
P-
```

Beispiel:

```text
P-1002
```

Beim Lohnkonto wird zusätzlich eine Kreditlimite angegeben.

Die Kreditlimite muss als negative Zahl angegeben werden.

Beispiel:

```java
bank.createSalaryAccount(-10000);
```

#### Weitere Funktionen der Bank

Die Klasse `Bank` bietet unter anderem folgende Methoden:

```java
getBalance()
```

Gibt den gesamten Kontostand der Bank zurück.

```java
getBalance(String id)
```

Gibt den Kontostand eines bestimmten Kontos zurück.

Existiert das Konto nicht, wird `0` zurückgegeben.

```java
deposit(String id, int date, long amount)
```

Zahlt Geld auf ein bestimmtes Konto ein.

Die Bank sucht zuerst anhand der Kontonummer nach dem entsprechenden Konto und ruft danach dessen `deposit()`-Methode auf.

```java
withdraw(String id, int date, long amount)
```

Hebt Geld von einem bestimmten Konto ab.

Auch hier wird zuerst das Konto gesucht und danach die `withdraw()`-Methode des Kontos verwendet

```java
print(String id)
```

Gibt den gesamten Kontoauszug eines Kontos aus.

```java
print(String id, int year, int month)
```

Gibt nur den Kontoauszug eines bestimmten Monats aus.

```java
printTop5()
```

Gibt die fünf Konten mit dem höchsten Kontostand aus.

```java
printBottom5()
```

Gibt die fünf Konten mit dem niedrigsten Kontostand aus.

### `Account`

`Account` ist die abstrakte Basisklasse aller Konten.

```java
public abstract class Account
```

Die Klassen

* `SavingsAccount`
* `SalaryAccount`

erben direkt von `Account`.

`PromoYouthSavingsAccount` erbt wiederum von `SavingsAccount`.

#### Attribute

Ein Konto besitzt:

```java
private String id;
private long balance;
private ArrayList<Booking> bookings;
```

#### `id`

Enthält die Kontonummer.

Beispiel:

```text
S-1000
```

#### `balance`

Enthält den aktuellen Kontostand.

Der Betrag wird intern als `long` gespeichert.

#### `bookings`

Enthält alle Buchungen des Kontos.

Bei jeder erfolgreichen Einzahlung oder Auszahlung wird ein neues `Booking`-Objekt erstellt.

### Einzahlen

Die grundlegende Einzahlung wird in `Account` durchgeführt:

```java
deposit(int date, long amount)
```

Dabei wird überprüft:

* Der Betrag darf nicht negativ sein.
* Das Buchungsdatum muss gültig sein.
* Der Kontostand wird erhöht.
* Eine neue Buchung wird gespeichert.

Eine Einzahlung wird als positiver Betrag gespeichert.

### Abheben

Die grundlegende Auszahlung wird ebenfalls in `Account` durchgeführt:

```java
withdraw(int date, long amount)
```

Dabei wird überprüft:

* Der Betrag darf nicht negativ sein.
* Das Buchungsdatum muss gültig sein.
* Der Kontostand wird reduziert.
* Eine neue Buchung wird gespeichert.

Eine Auszahlung wird als negativer Betrag in der Buchung gespeichert.

### Reihenfolge der Transaktionen

Mit

```java
canTransact(int date)
```

wird überprüft, ob eine neue Buchung durchgeführt werden darf.

Wenn noch keine Buchung existiert, ist jede Transaktion möglich.

Existieren bereits Buchungen, muss das Datum der neuen Transaktion mindestens gleich gross wie das Datum der letzten Buchung sein.

Dadurch wird verhindert, dass nachträglich eine ältere Buchung eingefügt wird.

Beispiel:

```text
Letzte Buchung: Tag 100
Neue Buchung:   Tag 105
→ erlaubt
```

```text
Letzte Buchung: Tag 100
Neue Buchung:   Tag 90
→ nicht erlaubt
```

### `SavingsAccount`

`SavingsAccount` erbt von `Account`.

```text
Account
   ↑
SavingsAccount
```

Das Sparkonto überschreibt die Methode:

```java
withdraw()
```

Beim Sparkonto darf nur so viel Geld abgehoben werden, wie auf dem Konto vorhanden ist.

Beispiel:

```text
Kontostand: 1000
Auszahlung: 800
→ erlaubt
```

```text
Kontostand: 1000
Auszahlung: 1200
→ nicht erlaubt
```

Ein Sparkonto kann dadurch keinen negativen Kontostand erhalten.

### `SalaryAccount`

`SalaryAccount` erbt ebenfalls direkt von `Account`.

```text
Account
   ↑
SalaryAccount
```

Ein Lohnkonto besitzt zusätzlich:

```java
private long creditLimit;
```

Die Kreditlimite bestimmt, wie weit das Konto ins Minus gehen darf.

Beispiel:

```text
Kontostand: 1000
Kreditlimite: -2000
Auszahlung: 2500

Neuer Kontostand: -1500
→ erlaubt
```

Bei einer Auszahlung von `3500` wäre der neue Kontostand:

```text
-2500
```

Da damit die Kreditlimite von `-2000` unterschritten wird, wird die Auszahlung abgelehnt.

### `PromoYouthSavingsAccount`

Das `PromoYouthSavingsAccount` erbt von `SavingsAccount`.

```text
Account
   ↑
SavingsAccount
   ↑
PromoYouthSavingsAccount
```

Dadurch übernimmt es auch die Regeln des Sparkontos.

Zusätzlich wird die Methode

```java
deposit()
```

überschrieben.

Bei jeder Einzahlung wird ein Bonus von **1 %** berechnet.

Im Code:

```java
long bonus = amount / 100;
```

Anschliessend wird der ursprüngliche Betrag zusammen mit dem Bonus eingezahlt.

Beispiel:

```text
Einzahlung: 10'000
Bonus:         100
----------------
Gutschrift: 10'100
```

### `Booking`

Die Klasse `Booking` repräsentiert eine einzelne Buchung.

Eine Buchung besitzt:

```java
private int date;
private long amount;
```

#### `date`

Das Datum wird nicht als normales Java-Datum gespeichert, sondern als Anzahl Banktage seit dem 01.01.1970.

Dabei verwendet das Programm ein vereinfachtes Datumsmodell:

* 1 Jahr = 360 Tage
* 1 Monat = 30 Tage

#### `amount`

Enthält den Betrag der Buchung.

Einzahlungen werden positiv gespeichert:

```text
+1000
```

Auszahlungen werden negativ gespeichert:

```text
-1000
```

#### `print()`

Mit

```java
print(long balance)
```

wird eine einzelne Buchungszeile ausgegeben.

Dafür verwendet `Booking` die Methoden der Klasse `BankUtils`.

### `BankUtils`

`BankUtils` ist eine Hilfsklasse.

Sie enthält hauptsächlich Methoden zur Formatierung.

#### `formatBankDate()`

```java
formatBankDate(int date)
```

Wandelt das interne Bankdatum in eine lesbare Darstellung um.

Beispiel:

```text
01.01.1970
```

Das verwendete Datumsmodell besteht aus:

```text
360 Tagen pro Jahr
30 Tagen pro Monat
```

#### `formatAmount()`

```java
formatAmount(long amount)
```

Formatiert einen Geldbetrag für die Ausgabe.

Dadurch können die Beträge beispielsweise korrekt auf einem Kontoauszug dargestellt werden.

### Kontoauszüge

Die Klasse `Account` kann zwei Arten von Kontoauszügen ausgeben.

#### Gesamter Kontoauszug

```java
print()
```

Es werden alle Buchungen des Kontos ausgegeben.

Die Ausgabe enthält:

```text
Datum
Betrag
Saldo
```

#### Monatlicher Kontoauszug

```java
print(int year, int month)
```

Hier werden nur die Buchungen des angegebenen Monats ausgegeben.

Beispiel:

```java
account.print(2025, 5);
```

Damit werden die Buchungen für Mai 2025 ausgegeben.

### Comparator-Klassen

Für die Sortierung der Konten existieren zwei Comparator-Klassen.

#### `AccountBalanceComparator`

Diese Klasse sortiert die Konten nach ihrem Kontostand absteigend.

```text
5000
3000
1000
-500
```

Sie wird für

```java
printTop5()
```

verwendet.

#### `AccountInverseBalanceComparator`

Diese Klasse sortiert die Konten in umgekehrter Richtung.

```text
-500
1000
3000
5000
```

Sie wird für

```java
printBottom5()
```

verwendet.

### Beziehungen zwischen den Klassen

#### Vererbung

Die Vererbung der Kontoarten sieht folgendermassen aus:

```text
             Account
             /     \
            /       \
 SavingsAccount    SalaryAccount
       |
       |
PromoYouthSavingsAccount
```

#### Bedeutung

* `Account` enthält die grundlegenden Funktionen eines Kontos.
* `SavingsAccount` erweitert diese Funktionen um die Regel, dass das Konto nicht überzogen werden darf.
* `SalaryAccount` erweitert `Account` um eine Kreditlimite.
* `PromoYouthSavingsAccount` erweitert das Sparkonto um einen Bonus bei Einzahlungen.

### Beziehung zwischen `Bank` und `Account`

Eine `Bank` verwaltet mehrere `Account`-Objekte.

```text
Bank
 |
 | verwaltet
 ↓
Account
```

Die Konten werden in folgender Datenstruktur gespeichert:

```java
TreeMap<String, Account>
```

Dadurch können die Konten über ihre Kontonummer gefunden werden.

### Beziehung zwischen `Account` und `Booking`

Ein Konto kann mehrere Buchungen besitzen.

```text
Account
 |
 | 1
 |
 | *
 ↓
Booking
```

Eine Einzahlung oder Auszahlung erzeugt jeweils eine neue Buchung.

Beispiel:

```text
Account S-1000

├── Booking: +1000
├── Booking: -200
├── Booking: +500
└── Booking: -100
```

### Beziehung zu `BankUtils`

`Booking` verwendet `BankUtils`, um die Daten für die Ausgabe zu formatieren.

```text
Booking
   |
   | verwendet
   ↓
BankUtils
```

Dabei werden insbesondere folgende Methoden verwendet:

```java
BankUtils.formatBankDate()
BankUtils.formatAmount()
```

### Ablauf einer Einzahlung

Eine Einzahlung über die Bank läuft ungefähr folgendermassen ab:

```text
Bank.deposit()
      |
      ↓
Konto anhand der ID suchen
      |
      ↓
Account.deposit()
      |
      ├── Betrag überprüfen
      ├── Datum überprüfen
      ├── Kontostand erhöhen
      |
      ↓
neues Booking erstellen
```

Bei einem `PromoYouthSavingsAccount` wird vorher zusätzlich der 1-%-Bonus berechnet.

### Ablauf einer Auszahlung

Eine Auszahlung läuft folgendermassen ab:

```text
Bank.withdraw()
      |
      ↓
Konto anhand der ID suchen
      |
      ↓
withdraw() der jeweiligen Kontoart
      |
      ├── SavingsAccount
      │     → genügend Guthaben?
      │
      └── SalaryAccount
            → Kreditlimite eingehalten?
      |
      ↓
Account.withdraw()
      |
      ├── Betrag überprüfen
      ├── Datum überprüfen
      ├── Kontostand reduzieren
      |
      ↓
neues Booking mit negativem Betrag
```

Dadurch kann jede Kontoart eigene Regeln für eine Auszahlung definieren, während die allgemeine Buchungslogik in `Account` bleibt.

### `Main`

Die Klasse `Main` dient als Einstiegspunkt der Anwendung.

Dort wird zuerst eine Bank erstellt:

```java
Bank ubs = new Bank();
```

Anschliessend werden beispielhaft Konten erstellt:

```java
ubs.createPromoYouthSavingsAccount();
ubs.createSalaryAccount(12000);
```

Beim `SalaryAccount` fällt auf, dass laut Implementierung die Kreditlimite negativ sein muss.

Da hier `12000` als positive Zahl übergeben wird, wird dieses Lohnkonto nicht erstellt und die Methode gibt `null` zurück.

Korrekt wäre beispielsweise:

```java
ubs.createSalaryAccount(-12000);
```

### Zusammenfassung der Zusammenhänge

```text
Bank
 |
 | verwaltet mehrere
 ↓
Account
 |
 ├── SavingsAccount
 |       |
 |       └── PromoYouthSavingsAccount
 |
 └── SalaryAccount

Account
 |
 | besitzt mehrere
 ↓
Booking
 |
 | verwendet
 ↓
BankUtils


Bank
 |
 ├── AccountBalanceComparator
 |      → Top 5
 |
 └── AccountInverseBalanceComparator
        → Bottom 5
```

#### Wichtigste Punkte

* `Bank` verwaltet alle Konten.
* `Account` ist die gemeinsame abstrakte Basisklasse.
* Jedes Konto besitzt eine ID, einen Kontostand und mehrere Buchungen.
* `SavingsAccount` verhindert das Überziehen des Kontos.
* `SalaryAccount` erlaubt das Überziehen bis zu einer festgelegten Kreditlimite.
* `PromoYouthSavingsAccount` gibt bei jeder Einzahlung 1 % Bonus.
* Jede erfolgreiche Transaktion erzeugt ein `Booking`.
* Buchungen müssen chronologisch durchgeführt werden.
* `Booking` speichert Datum und Betrag einer Transaktion.
* `BankUtils` formatiert Datum und Geldbeträge für die Ausgabe.
* Comparator-Klassen werden verwendet, um die höchsten und niedrigsten Kontostände zu bestimmen.

## Aufgabe 4 - Unit-Tests implementieren
Ich habe alle Tests angepasst, welche auch in diesem Repo unter Aufgabe 3/Banking aufzufinden sind und habe diese mit der Code-Coverage laufen lassen. Das entsprechende Resultat wäre dann dieses:

![Code Coverage](/Aufgaben/Aufgabe%203/Images/Code-Coverage.png)

![Test Results 2](/Aufgaben/Aufgabe%203/Images/Test-Results-2.png)
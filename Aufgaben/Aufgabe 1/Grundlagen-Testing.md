# Grundlagen zu Testing und Testing in Vorgehensmodelle

## Inhaltsverzeichnis
- [Grundlagen zu Testing und Testing in Vorgehensmodelle](#grundlagen-zu-testing-und-testing-in-vorgehensmodelle)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Aufgabe 1](#aufgabe-1)
  - [Aufgabe 2](#aufgabe-2)
  - [Aufgabe 3](#aufgabe-3)
  - [Aufgabe 3 - Bonus](#aufgabe-3---bonus)
    - [Fehler 1](#fehler-1)
    - [Fehler 2](#fehler-2)


## Aufgabe 1
**Komponententest**: Einzelne Methoden oder Klassen werden mit verschiedenen Eingaben getestet und das Ergebnis mit dem erwarteten Ergebnis verglichen.

**Integrationstest**: Es wird getestet, ob mehrere Komponenten, z. B. Frontend, Backend und Datenbank, korrekt miteinander funktionieren.

**Systemtest**: Die komplette Anwendung wird mit realistischen Abläufen getestet, beispielsweise ein vollständiger Bestellvorgang.

**Abnahmetest**: Der Kunde testet, ob die fertige Software seinen Anforderungen entspricht und akzeptiert werden kann.

## Aufgabe 2
**SW-Fehler**: Eine Rabattberechnung liefert 95 CHF, obwohl gemäss Anforderungen 90 CHF herauskommen müssten.

**SW-Mangel**: Der Preis wird korrekt berechnet, aber im GUI mit der falschen Währung dargestellt.

**Hoher Schaden**: Eine medizinische Software berechnet eine falsche Medikamentendosis, wodurch ein Patient gefährdet werden kann.

## Aufgabe 3
Für die Methode calculatePrice() wurde ein einfacher Testtreiber erstellt. Dieser ruft die Methode mit verschiedenen Eingabewerten auf und vergleicht das berechnete Resultat mit dem erwarteten Preis.

Getestet wurden unter anderem folgende Fälle:

| Test            | Extras | Händlerrabatt | Erwarteter Preis |
| --------------- | -----: | ------------: | ---------------: |
| Keine Rabatte   |      0 |           0 % |           35'000 |
| Händlerrabatt   |      2 |           5 % |           33'500 |
| Grenze 3 Extras |      3 |           5 % |           33'200 |
| Zwischenwert    |      4 |           5 % |           33'200 |
| Grenze 5 Extras |      5 |           5 % |           33'050 |

Dabei wurden besonders die Grenzwerte 3 und 5 Extras getestet, da sich dort die Rabattstufe ändern soll. Solche unterschiedlichen und gezielt gewählten Testfälle helfen dabei, Fehler im Programm zu erkennen.

Der Testtreiber vergleicht jeweils den erwarteten Wert mit dem tatsächlich berechneten Wert und gibt aus, ob der Test erfolgreich war.

Beispiel:

5 Extras
Erwartet: 33050
Erhalten: 33200
FEHLER

Dadurch ist erkennbar, dass die Preisberechnung bei bestimmten Eingaben nicht korrekt funktioniert.

## Aufgabe 3 - Bonus

### Fehler 1
Der erste Fehler liegt in der Reihenfolge der Bedingungen:

```java
if (extras >= 3)
    addon_discount = 10;
else if (extras >= 5)
    addon_discount = 15;
```

Bei 5 oder mehr Extras ist bereits extras >= 3 wahr. Deshalb wird immer ein Rabatt von 10 % gesetzt und die zweite Bedingung mit 15 % wird nie erreicht.

Die Bedingungen müssen deshalb umgedreht werden:

```java
if (extras >= 5)
    addon_discount = 15;
else if (extras >= 3)
    addon_discount = 10;
else
    addon_discount = 0;
```

### Fehler 2
```java
if (discount > addon_discount)
    addon_discount = discount;
```
Laut Aufgabenstellung gilt der Händlerrabatt nur für den Grundpreis und der Zubehörrabatt nur für das Zubehör. Daher sollte der Händlerrabatt discount den Zubehörrabatt nicht verändern.

Diese Zeilen sollten also entfernt werden.

Korrigierte Version
```java
if (extras >= 5)
    addon_discount = 15;
else if (extras >= 3)
    addon_discount = 10;
else
    addon_discount = 0;

result = baseprice / 100.0 * (100 - discount)
        + specialprice
        + extraprice / 100.0 * (100 - addon_discount);
```
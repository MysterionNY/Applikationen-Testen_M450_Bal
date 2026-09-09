# Schnittstellen

# Inhaltsverzeichnis
- [Schnittstellen](#schnittstellen)
- [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Verweise](#verweise)
  - [Aufgabe 1](#aufgabe-1)
    - [Getestete Klassen](#getestete-klassen)
    - [Test Double im Service](#test-double-im-service)
    - [`@BeforeEach`](#beforeeach)
    - [Comparator](#comparator)
  - [Aufgabe 2](#aufgabe-2)

## Verweise
Diese Übung erfordert keine extra Dokumentation, jedoch möchte ich mit dieser File auf die Test-Klassen verweisen welche ich entwickelt habe:
| # | Klasse | Datei |
|---|-------|-------|
| 1 | Address Controller Test | [AddressControllerTest](/Aufgaben/Aufgabe%204/Addressbook/controller/AddressControllerTest.java) |
| 2 | Address Test | [AddressTest](/Aufgaben/Aufgabe%204/Addressbook/repository/AddressTest.java) |
| 3 | Address Service Test| [AddressServiceTest](/Aufgaben/Aufgabe%204/Addressbook/service/AddressServiceTest.java) |
| 4 | Address Comparator Test | [AddressComparatorTest](/Aufgaben/Aufgabe%204/Addressbook/util/AddressComparatorTest.java) |
| 5 | Addressbook Application Test | [AddressbookApplicationTest](/Aufgaben/Aufgabe%204/Addressbook/AddressbookApplicationTest.java) |

## Aufgabe 1

### Getestete Klassen

- `Address`
- `AddressService`
- `AddressController`
- `AddressComparator`
- `AddressbookApplication` über einen Context-Test

`AddressRepository` ist ein Spring-Data-Interface ohne eigene Implementierungslogik. Im `AddressServiceTest` wird es deshalb mit Mockito gemockt, anstatt für den Unit-Test eine echte H2-Datenbank zu verwenden.

### Test Double im Service

Für den Service verwende ich ein Mock des `AddressRepository`:

```java
@Mock
private AddressRepository addressRepository;

@InjectMocks
private AddressService addressService;
```

Mit `when(...).thenReturn(...)` definiere ich die Rückgabe des Repository-Mocks:

```java
when(addressRepository.findById(1)).thenReturn(Optional.of(address1));
```

Mit `verify(...)` überprüfe ich anschliessend, ob der Service das Repository wie erwartet aufgerufen hat:

```java
verify(addressRepository).findById(1);
```

Dadurch benötigt dieser Test keine laufende H2-Datenbank und testet nur die Logik des `AddressService`.

### `@BeforeEach`

Die Testdaten werden mit `@BeforeEach` vor jedem Test neu erstellt. Dadurch sind die Tests voneinander unabhängig.

```java
@BeforeEach
void setUp() {
    address1 = new Address(1, "Mert", "Bal", "0791111111", new Date(1_000));
}
```

### Comparator

Die vorherige Implementierung

```java
return -1;
```

war fehlerhaft, weil damit jedes erste Objekt unabhängig von seinen Werten als kleiner als das zweite Objekt behandelt wurde.

Der Comparator sortiert standardmässig nach:

1. Nachname
2. Vorname
3. ID

Damit ist die Reihenfolge auch dann eindeutig, wenn zwei Personen denselben Nachnamen haben.

## Aufgabe 2

Der `AddressComparator` wurde erweitert, sodass gezielt nach verschiedenen Attributen sortiert werden kann.

Unterstützt werden:

- `ID`
- `FIRSTNAME`
- `LASTNAME`
- `PHONENUMBER`
- `REGISTRATION_DATE`

Beispiel für eine Sortierung nach Vorname:

```java
addresses.sort(new AddressComparator(AddressComparator.SortField.FIRSTNAME));
```

Beispiel für eine Sortierung nach Registrierungsdatum:

```java
addresses.sort(new AddressComparator(AddressComparator.SortField.REGISTRATION_DATE));
```

Es können auch mehrere Attribute als Sortierreihenfolge angegeben werden:

```java
addresses.sort(new AddressComparator(
        AddressComparator.SortField.LASTNAME,
        AddressComparator.SortField.FIRSTNAME,
        AddressComparator.SortField.ID
));
```

Die zusätzliche Funktionalität wird in `AddressComparatorTest` getestet.
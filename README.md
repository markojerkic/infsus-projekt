# Pokretanje

> Potrebno je imati instaliran Docker i Java 17.

```bash
./mvnw spring-boot:run
```

## Testovi

Testovi se pokreću s naredbom:

```bash
./mvnw test
```

Integracijski testovi pokreću dodatni Docker container s PostgreSQL bazom podataka koja nema volumena.

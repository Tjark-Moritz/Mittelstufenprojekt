# Mittelstufenprojekt LF12

Dieses Projekt umfasst ein Schichtenplansystem, welches automatisiert Schichtpläne generieren kann, sowie Interaktionen zwischen Mitarbeitern zulässt, da diese ihnen ungelegene Schichten mit anderen Mitarbeiter über ein Tauschsystem wechseln können. Des Weiteren können auch administrative Tätigkeiten, wie z.B. das Genehmigen von Urlaubsanträgen über diese Platform stattfinden.

### Login-Daten für Beispiel Account:
```
Schichtenadmin: abt_k:shift123
Schichtuser: decker_p:shift123 / schmid_j:shift123
```

### Relevante Links
```
Swagger für Backend: Localhost:8090/swagger
Keycloak admin panel: Localhost:8089 | Login: admin:admin
Postgres DB Ports: Localhost:5432 (Shiftplanner PostgreDB) / Localhost:5433 (Keycloak PostgreDB)
```

## Wie startet man das Projekt
 
### 1. Backend starten
```bash
cd /shift_backend/docker/local
docker compose up
```
### 2. Frontend starten
```bash
cd /shiftFrontend/docker
docker compose up
```

## Wie stoppt man das Projekt

### Backend stoppen
```bash
cd shift_backend/docker/local
docker compose down
```

### Frontend stoppen
```bash
cd /shiftFrontend/docker
docker compose down
```





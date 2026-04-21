## PostgreSQL container initialization

```bash
docker run --name volunteer-hours-postgres \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=volunteer_hours \
    -p 55432:5432 \
    -d postgres:latest
```
FROM postgres:9.4

COPY scripts/00-create-complaints-schema.sql /docker-entrypoint-initdb.d/
COPY scripts/01-complaints-schema.sql /docker-entrypoint-initdb.d/

FROM mysql:8.0
COPY ./schema.sql /docker-entrypoint-initdb.d/01_schema.sql
COPY ./data.sql /docker-entrypoint-initdb.d/02_data.sql
ENV MYSQL_ROOT_PASSWORD=pancake
ENV MYSQL_DATABASE=myappdatabase
EXPOSE 3306

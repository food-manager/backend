# Food manager

To run required db, run the following command:

```
docker run -p 3307:3306 -d --name food-manager --env MARIADB_USER=food-manager --env MARIADB_PASSWORD=dij621293 --env MARIADB_ROOT_PASSWORD=secret_password  mariadb:latest
```
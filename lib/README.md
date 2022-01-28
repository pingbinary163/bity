# oracle
https://www.oracle.com/database/technologies/appdev/jdbc-ucp-21-3-downloads.html

# microsoft sqlserver
https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15

# steps
https://gist.github.com/stuartsierra/3062743

```
lein deploy [repo] [artifact] [version] [jar-path]

eg:
lein deploy project local/bar 1.0.0 bar.jar
then
[local/bar "1.0.0"]
```

so,
```
lein deploy project local/oracle 21.3.0 lib/ojdbc8.jar
lein deploy project local/mssql 9.4.1 lib/mssql-jdbc-9.4.1.jre11.jar
lein deps
```

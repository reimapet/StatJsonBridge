INSERT INTO users (username, `password`) VALUES ('user', 'pass');

INSERT INTO input_data_sources (user_id, name, title, jdbc_url, jdbc_username, jdbc_password) VALUES (1, 'datasource', 'Data Source', 'jdbc:mysql://localhost:3306/employees', 'root', 'foo');

INSERT INTO input_queries (user_id, name, sql_query) VALUES (1, 'query', 'SELECT DATE_FORMAT(birth_date, ''%Y-%m'') AS dts, SUM(CASE WHEN gender = ''M'' THEN 1 ELSE 0 END) AS males, SUM(CASE WHEN gender = ''F'' THEN 1 ELSE 0 END) AS females FROM employees GROUP BY dts ORDER BY dts DESC');

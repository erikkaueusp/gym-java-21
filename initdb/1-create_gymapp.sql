
SELECT 'CREATE DATABASE gymapp WITH OWNER postgres'
WHERE NOT EXISTS (
  SELECT FROM pg_database WHERE datname = 'gymapp'
)
\gexec

-- Výchozí data pro demo (Postman). Po startu aplikace jsou v DB 2 uživatelé a 2 nářadí.
-- Sloupec user_role (ne "role") kvůli kompatibilitě H2 i PostgreSQL.
INSERT INTO users (name, user_role) VALUES ('Jan Novak', 'CUSTOMER');
INSERT INTO users (name, user_role) VALUES ('Marie Zamestnankyne', 'EMPLOYEE');
INSERT INTO tools (name, daily_price_czk, late_fee_per_day) VALUES ('Kladivo', 100, 50);
INSERT INTO tools (name, daily_price_czk, late_fee_per_day) VALUES ('Sroubovak', 80, 40);

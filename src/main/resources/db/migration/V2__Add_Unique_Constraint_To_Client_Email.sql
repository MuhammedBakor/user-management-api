ALTER TABLE usert
    ADD CONSTRAINT client_email_unique UNIQUE (email);
ALTER TABLE client
    ADD CONSTRAINT client_email_unique UNIQUE (email);
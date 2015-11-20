ALTER TABLE books
   ADD COLUMN is_valid boolean NOT NULL DEFAULT false;
UPDATE books SET is_valid = true WHERE description LIKE 'GOOD%';


CREATE TABLE public.invalid_pages
(
   id bigint NOT NULL,
   book_id bigint NOT NULL,
   page_number integer NOT NULL,
   comment text,
   CONSTRAINT idx PRIMARY KEY (id),
   CONSTRAINT books_idx FOREIGN KEY (book_id) REFERENCES books (book_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT book_page_idx UNIQUE (book_id, page_number)
)
WITH (
  OIDS = FALSE
)
;

-- Table: roles

-- DROP TABLE roles;

CREATE TABLE roles
(
  role_id text NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (role_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE roles
  OWNER TO postgres;

-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  user_id bigint NOT NULL DEFAULT nextval('user_id_seq'::regclass),
  user_name text NOT NULL,
  password text,
  CONSTRAINT users_pkey PRIMARY KEY (user_id),
  CONSTRAINT users_user_name_key UNIQUE (user_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

-- Table: user_roles

-- DROP TABLE user_roles;

CREATE TABLE user_roles
(
  user_id bigint,
  role text,
  CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role)
      REFERENCES roles (role_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES users (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_roles_user_id_role_id_key UNIQUE (user_id, role)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_roles
  OWNER TO postgres;


INSERT INTO roles(
            role_id)
    VALUES ('ROLE_USER');
INSERT INTO roles(
            role_id)
    VALUES ('ROLE_ADMIN');

CREATE TABLE APP_USER (
    id CHAR(100) NOT NULL,
    username CHAR(100) NOT NULL,
    email CHAR(100) NOT null,
    status CHAR(1) NOT NULL
);

ALTER TABLE APP_USER ADD CONSTRAINT user_pk PRIMARY KEY ( id );

CREATE TABLE USER_PASSWORD (
    id CHAR(100) NOT NULL,
    user_id CHAR(100) NOT NULL,
    password CHAR(200) NOT NULL,
    status CHAR(1) NOT NULL,
    failed_login_times INTEGER NOT NULL,
    last_login_date_time DATE,
    created_date_time DATE NOT NULL
);

ALTER TABLE USER_PASSWORD ADD CONSTRAINT user_password_pk PRIMARY KEY ( id );
ALTER TABLE USER_PASSWORD ADD CONSTRAINT user_password_reference_app_user FOREIGN KEY (user_id) references app_user(id);



CREATE TABLE IF NOT EXISTS users(
                                        user_id BIGSERIAL   PRIMARY KEY,
                                        chat_id VARCHAR(50) NOT NULL,
                                        user_name VARCHAR(50),
                                        email     VARCHAR(255),
                                        sex       TEXT CHECK (sex IN ('MALE', 'FEMALE')),
                                        weight    INT,
                                        date_of_birth DATE,
                                        locale VARCHAR (20),
                                        UNIQUE(chat_id)
);

CREATE TABLE IF NOT EXISTS food_type(
                                        id BIGSERIAL    PRIMARY KEY,
                                        name VARCHAR(1000) NOT NULL

);

CREATE TABLE IF NOT EXISTS meal(
                                        id BIGSERIAL  PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        time_of_meal TIMESTAMP NOT NULL,
                                        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS meal_food(
                                        id BIGSERIAL PRIMARY KEY,
                                        meal_id BIGINT NOT NULL,
                                        product_id BIGINT NOT NULL,
                                        quantity INT NOT NULL,
                                        FOREIGN KEY (meal_id) REFERENCES meal(id) ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS dish(
                                       id BIGSERIAL  PRIMARY KEY,
                                       user_id BIGINT,
                                       name VARCHAR(100) NOT NULL,
                                       products BIGINT[] NOT NULL,
                                       portions BIGINT[] NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products(
                                       id BIGSERIAL  PRIMARY KEY,
                                       name VARCHAR(100) NOT NULL,
                                       type_id BIGINT NOT NULL,
                                       gram SERIAL NOT NULL,
                                       protein FLOAT NOT NULL,
                                       fat FLOAT NOT NULL,
                                       carbohydrates FLOAT NOT NULL,
                                       calories FLOAT NOT NULL,
                                       FOREIGN KEY (type_id) REFERENCES food_type(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sessions_messages(
                                                id BIGSERIAL  PRIMARY KEY,
                                                user_id BIGINT NOT NULL,
                                                message_id BIGINT NOT NULL,
                                                message VARCHAR(1000) NOT NULL,
                                                response VARCHAR(1000) NOT NULL,
                                                state VARCHAR(1000) NOT NULL,
                                                failure VARCHAR(1000) NOT NULL,
                                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS import_history(
                                                id BIGSERIAL  PRIMARY KEY,
                                                version TIMESTAMP NOT NULL,
                                                type VARCHAR(255) NOT NULL,
                                                sha VARCHAR(255) NOT NULL,
                                                body BYTEA NOT NULL,
                                                status VARCHAR(255) NOT NULL

);


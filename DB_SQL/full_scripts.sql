-- USE master
-- GO

-- ALTER DATABASE audiosn SET SINGLE_USER WITH ROLLBACK IMMEDIATE
-- GO
-- DROP DATABASE audiosn
-- GO

CREATE DATABASE audiosn
GO

USE audiosn
GO

-- User
CREATE TABLE tbl_user (
    col_username VARCHAR(30),
    col_password VARCHAR(100) NOT NULL,
    col_active BIT DEFAULT 1 NOT NULL,

    PRIMARY KEY(col_username)
)
GO

-- User's Info
CREATE TABLE tbl_user_info (
    col_username VARCHAR(30) REFERENCES tbl_user(col_username),
    col_display_name NVARCHAR(100) NULL,
    col_avatar VARCHAR(500) NULL,
    col_email VARCHAR(100) NULL,

    PRIMARY KEY(col_username)
)
GO

-- Role
CREATE TABLE tbl_role (
    col_id INT IDENTITY(1,1),
    col_role_name VARCHAR(50) NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- User's role
CREATE TABLE tbl_user_role (
    col_id INT IDENTITY(1,1),
    col_username VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_role_id INT REFERENCES tbl_role(col_id) NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- Category
CREATE TABLE tbl_category (
    -- col_id INT IDENTITY(1,1),
    col_category_name VARCHAR(30)

    PRIMARY KEY(col_category_name)
)
GO

-- Article
CREATE TABLE tbl_article (
    col_id INT IDENTITY(1,1),
    col_title NVARCHAR(500) NOT NULL,
    col_description NTEXT NULL,
    col_content NTEXT NOT NULL,
    col_audio_content VARCHAR(500) NULL,
    col_date_created DATE NOT NULL,
    col_time_created TIME NOT NULL,
    col_author VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_url NVARCHAR(500) UNIQUE NOT NULL,
    col_category VARCHAR(30) REFERENCES tbl_category(col_category_name) NOT NULL,
    col_thumbnail_url NVARCHAR(500) NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- Comment
CREATE TABLE tbl_comment (
    col_id INT IDENTITY(1,1),
    col_author VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_article_id INT REFERENCES tbl_article(col_id) NOT NULL,
    col_date DATE NOT NULL,
    col_time TIME NOT NULL,
    col_content NTEXT NOT NULL,
    col_audio_content VARCHAR(500) NULL,

    PRIMARY KEY(col_id)
)
GO

-- User's vote state
CREATE TABLE tbl_user_vote_state (
    col_id INT IDENTITY(1,1),
    col_article_id INT REFERENCES tbl_article(col_id) NOT NULL,
    col_username VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_vote_state INT DEFAULT 0 NOT NULL, -- 0: none | 1: up | -1: down
    
    UNIQUE(col_article_id, col_username),

    PRIMARY KEY(col_id)
)
GO

-- Article's interactive score
CREATE TABLE tbl_article_interaction (
    col_article_id INT REFERENCES tbl_article(col_id),
    col_comment_score FLOAT NOT NULL,
    col_vote_score FLOAT NOT NULL,

    PRIMARY KEY(col_article_id)
)
GO

-- Article's report
CREATE TABLE tbl_article_report (
    col_id INT IDENTITY(1,1),
    col_article_id INT REFERENCES tbl_article(col_id) NOT NULL,
    col_author VARCHAR(30) REFERENCES tbl_user(col_username) NOT NULL,
    col_date DATE NOT NULL,
    col_time TIME NOT NULL,
    col_content NTEXT NOT NULL,

    PRIMARY KEY(col_id)
)
GO

-- Init data
INSERT INTO tbl_user (col_username, col_password, col_active) VALUES ('admin', '$2a$10$Oq2/BUGgxCaeKjcWEnpiyOyh0aGOeltqUM9Db44cRJvlfX9npXmzy', 1)
GO

INSERT INTO tbl_role (col_role_name) VALUES ('admin')
GO
INSERT INTO tbl_role (col_role_name) VALUES ('mod')
GO
INSERT INTO tbl_role (col_role_name) VALUES ('norm')
GO

INSERT INTO tbl_user_role (col_username, col_role_id) VALUES ('admin', 1)
GO

INSERT INTO tbl_category (col_category_name) VALUES ('front-end')
GO

INSERT INTO tbl_category (col_category_name) VALUES ('back-end')
GO

INSERT INTO tbl_category (col_category_name) VALUES ('ios')
GO

INSERT INTO tbl_category (col_category_name) VALUES ('android')
GO

INSERT INTO tbl_category (col_category_name) VALUES ('tips-tricks')
GO

INSERT INTO tbl_category (col_category_name) VALUES ('english')
GO
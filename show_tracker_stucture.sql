drop database if exists TV_show_tracker;

create database TV_show_tracker;
use TV_show_tracker;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL unique,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    CHECK (CHAR_LENGTH(username) BETWEEN 5 AND 20),
    CHECK (CHAR_LENGTH(password_hash) >= 8),
    PRIMARY KEY (user_id)
);

create table tv_shows(
show_id int auto_increment,
name varchar(255) NOT NULL,
episodes int NOT NULL,
discription TEXT,
primary key(show_id)
);

create table show_tracker(
    show_id int,
    user_id int,
    episodes_watched int,
    rating INT CHECK (rating BETWEEN 1 AND 5),
     status ENUM('Plan to Watch', 'Currently Watching', 'Finished')
     DEFAULT 'Plan to Watch',
    primary key (show_id,user_id),
    foreign key(show_id) references tv_shows(show_id) ON DELETE CASCADE,
    foreign Key(user_id) references users(user_id) ON DELETE CASCADE
);

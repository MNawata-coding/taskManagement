CREATE TABLE task ( 
    id int NOT NULL AUTO_INCREMENT,
    task_name varchar(20), -- タスク名
    create_date_time DATETIME,  -- 作成日時
     primary key(id));
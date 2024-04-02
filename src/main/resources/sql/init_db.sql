CREATE TABLE Student
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARACHAR(255),
    username VARCHAR(255)
);
CREATE TABLE Lesson(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    student_id INT,
    is_student_present BOOLEAN,
    mark FLOAT,
    date DATE,
    FOREIGN KEY (student_id) REFERENCES Student(id)
);
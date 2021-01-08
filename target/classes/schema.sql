create table university (
    id int primary key,
    name varchar not null unique,
    short_name varchar(20) not null unique,
    foundation_year int not null
);

create table faculty (
     faculty_id int primary key,
     name varchar not null,
     university_id int not null,
     constraint faculty_university_id_fkey foreign key (university_id) references university(id)
);

create table department (
    id serial primary key,
    name varchar not null,
    faculty_id int not null,
    constraint department_faculty_id_fkey foreign key (faculty_id) references faculty(faculty_id)
);

create table student_group (
    group_key varchar(50) primary key,
    department_id int not null,
    constraint student_group_department_id_fkey foreign key (department_id) references department(id)
);

create table student (
    student_card int primary key,
    last_name varchar not null,
    first_name varchar not null,
    birthday date not null,
    group_key varchar not null,
    constraint student_group_key_fkey foreign key (group_key) references student_group(group_key)
);

create table subject (
    id int primary key,
    subject varchar not null,
    count_of_hours int not null
);

create table subject_info (
    subject_id int primary key,
    description text not null,
    room_number int,
    constraint subject_info_id_fkey foreign key (subject_id) references subject(id)
);

create table faculty_subject (
    faculty_id int not null,
    subject_id int not null,
    primary key (faculty_id, subject_id),
    foreign key (faculty_id) references faculty(faculty_id),
    foreign key (subject_id) references subject(id)
);


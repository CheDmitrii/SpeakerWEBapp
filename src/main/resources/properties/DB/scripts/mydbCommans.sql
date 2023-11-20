create database speaker_db;


create table Language (
    idLanguage smallserial primary key,
    LanguageName VARCHAR(200) not null);



create table Genre (
    idGenre smallserial primary key,
    GenreName VARCHAR(200) not null);



create table Label (
    idLabel serial primary key,
    LabelName VARCHAR(300) not null);



create table Country (
    idCountry smallserial primary key,
    CountryName VARCHAR(200) not null);



create table Limitation (
    idLimitation serial primary key,
    LimName VARCHAR(200) not null,
    LimVal integer not null);



create table Album (
    idAlbum bigserial primary key,
    AlbumName VARCHAR(300) not null,
    AlbumPhoto VARCHAR(250) default '/path/to/file',
    idLabel serial not null,
    CONSTRAINT fk_album_label
        FOREIGN KEY (idLabel) REFERENCES Label (idLabel)
            ON DELETE SET NULL
            ON UPDATE CASCADE);




create table Song (
    idSong bigserial PRIMARY KEY,
    SongName VARCHAR(200) not null,
    LimVal integer not null,
    idGenre smallint,
    idAlbum biting not null,
    idLanguage smallint not null,
    CONSTRAINT fk_song_genre
        FOREIGN KEY (idGenre) REFERENCES Genre (idGenre),
    CONSTRAINT fk_song_language
        FOREIGN KEY (idLanguage) REFERENCES Language (idLanguage));



-- ALTER TABLE Song
--     ADD CONSTRAINT
--         fk_song_album FOREIGN KEY (idAlbum) REFERENCES Album (idAlbum)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE;
--
-- alter table song DROP CONSTRAINT fk_song_album;




-- in country table we should create row with id=0 and name equals 'Earth planet'

CREATE TABLE Singer (
    idSinger bigserial PRIMARY KEY,
    SingerName VARCHAR(200) not null,
    SingerBithday date not null,
    idCountry smallint not null default 0,
    CONSTRAINT fk_singer_country
        FOREIGN KEY (idCountry) REFERENCES Country (idCountry)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE);



CREATE TABLE Album_Singer (
    idAlbum biting,
    idSinger bigint,
    CONSTRAINT fk_album_singer_singer
        FOREIGN KEY (idSinger) REFERENCES Singer(idSinger)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_album_singer_album
        FOREIGN KEY (idAlbum) REFERENCES Album(idAlbum)
            ON DELETE CASCADE
            ON UPDATE CASCADE);



create table Subscribe (
    idSubscribe serial PRIMARY KEY,
    SubscribeName VARCHAR(200) not null,
    SubscribeCost NUMERIC(10,2) not null default 0.00);



create table "User" (
    UserName VARCHAR(100) PRIMARY KEY,
    idUser bigserial UNIQUE,
    FirstName VARCHAR(200) not null,
    SecondName VARCHAR(200) not null,
    Password VARCHAR(200) not null,
    UserBithday date not null,
    idSubscribe smallint DEFAULT 1 not null,
    sale NUMERIC(10, 2) not null default 0.00,
    idLimitation smallint DEFAULT 0 not null,
    CONSTRAINT fk_user_subscribe
        FOREIGN KEY (idSubscribe) REFERENCES Subscribe(idSubscribe)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE,
    CONSTRAINT fk_user_limitation
        FOREIGN KEY (idLimitation) REFERENCES Limitation(idLimitation)
            ON DELETE SET DEFAULT
            ON UPDATE CASCADE);



-- in my realisation id don't have bytea column but you can store data like lites instead of just path to file

create table SongSource (
    idSong biting PRIMARY KEY,
    SongText text not null,
    SongSource bytea not null,
    CONSTRAINT link_song_songsource
        FOREIGN KEY (idSong) REFERENCES Song (idSong)
            ON DELETE CASCADE
            ON UPDATE CASCADE);



create table Library (
    idSong biting not null,
    idUser biting not null,
    CONSTRAINT fk_library_user
        FOREIGN KEY (idUser) REFERENCES "User" (idUser)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_library_song
        FOREIGN KEY (idSong) REFERENCES Song (idSong)
            ON DELETE CASCADE
            ON UPDATE CASCADE);



create table song_singer(
    idSong bigint not null,
    idSinger bigint not null,
    isMain BOOLEAN default false,
    CONSTRAINT fk_song_singer_song
        FOREIGN KEY (idSong) REFERENCES Song (idSong)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_song_singer_singer
        FOREIGN KEY (idSinger) REFERENCES Singer(idSinger)
            ON DELETE CASCADE
            ON UPDATE CASCADE);
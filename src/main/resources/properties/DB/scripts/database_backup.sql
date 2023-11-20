--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Homebrew)
-- Dumped by pg_dump version 14.9 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: change_first_name(integer, character varying); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.change_first_name(IN id integer, IN newname character varying)
    LANGUAGE plpgsql
    AS $_$
DECLARE
begin
    if newname !~* '[0-9\^\-\\\!\@\#\$\%\&\*\(\)\{\}\[\],.<>?:''"|]' THEN
        UPDATE "User"
        SET firstname=newname
        WHERE iduser=id;
        COMMIT;
    end if;
end;
$_$;


ALTER PROCEDURE public.change_first_name(IN id integer, IN newname character varying) OWNER TO speaker_user;

--
-- Name: PROCEDURE change_first_name(IN id integer, IN newname character varying); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.change_first_name(IN id integer, IN newname character varying) IS 'This procedure change first name of user to valid new first name by iduser.';


--
-- Name: change_limitation(integer, integer); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.change_limitation(IN id integer, IN newlimitation integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
begin
    if EXISTS(SELECT idlimitation FROM limitation WHERE idlimitation=newlimitation) THEN
        UPDATE "User"
        SET idlimitation=newlimitation
        WHERE iduser=id;
        COMMIT;
    end if;
end;
$$;


ALTER PROCEDURE public.change_limitation(IN id integer, IN newlimitation integer) OWNER TO speaker_user;

--
-- Name: PROCEDURE change_limitation(IN id integer, IN newlimitation integer); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.change_limitation(IN id integer, IN newlimitation integer) IS 'This procedure set new id limitation for user by iduser.';


--
-- Name: change_password(integer, character varying); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.change_password(IN id integer, IN newpassword character varying)
    LANGUAGE plpgsql
    AS $$
    DECLARE
    begin
        if EXISTS(SELECT iduser FROM "User" WHERE iduser=id) THEN
            UPDATE "User"
            SET password=newpassword
            WHERE iduser=id;
            COMMIT;
        end if;
    end;
$$;


ALTER PROCEDURE public.change_password(IN id integer, IN newpassword character varying) OWNER TO speaker_user;

--
-- Name: PROCEDURE change_password(IN id integer, IN newpassword character varying); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.change_password(IN id integer, IN newpassword character varying) IS 'This procedure change password of user by iduser.';


--
-- Name: change_second_name(integer, character varying); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.change_second_name(IN id integer, IN newname character varying)
    LANGUAGE plpgsql
    AS $_$
DECLARE
begin
    if newname !~* '[0-9\^\-\\\!\@\#\$\%\&\*\(\)\{\}\[\],.<>?:''"|]' THEN
        UPDATE "User"
        SET secondname=newname
        WHERE iduser=id;
        COMMIT;
    end if;
end;
$_$;


ALTER PROCEDURE public.change_second_name(IN id integer, IN newname character varying) OWNER TO speaker_user;

--
-- Name: PROCEDURE change_second_name(IN id integer, IN newname character varying); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.change_second_name(IN id integer, IN newname character varying) IS 'This procedure change second name of user to valid new second name by iduser.';


--
-- Name: change_subscribe(integer, character varying); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.change_subscribe(IN id integer, IN newsubscribe character varying)
    LANGUAGE plpgsql
    AS $$
    DECLARE
        idsub INTEGER;
    begin
        if EXISTS(SELECT idsubscribe INTO idsub FROM subscribe WHERE subscribename=newsubscribe) AND
           EXISTS(SELECT iduser FROM "User" WHERE iduser=id) THEN

            UPDATE "User"
            SET idsubscribe=idsub
            WHERE iduser=id;
            COMMIT;
        end if;
    end;$$;


ALTER PROCEDURE public.change_subscribe(IN id integer, IN newsubscribe character varying) OWNER TO speaker_user;

--
-- Name: PROCEDURE change_subscribe(IN id integer, IN newsubscribe character varying); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.change_subscribe(IN id integer, IN newsubscribe character varying) IS 'This procedure change subscribe of user.';


--
-- Name: how_added(integer); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.how_added(id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    result integer;
begin

    result = 0;
    if EXISTS(SELECT iduser FROM "User" WHERE iduser=id) THEN
        SELECT sum(additions) INTO result FROM (SELECT library.idsong, count(*) as additions FROM library
                                                JOIN song s on s.idsong = library.idsong
                                                JOIN song_singer ss on s.idsong = ss.idsong
                                                WHERE ss.idsinger=id
                                                GROUP BY library.idsong) as adds;
    end if;
    return result;
end;
$$;


ALTER FUNCTION public.how_added(id integer) OWNER TO speaker_user;

--
-- Name: FUNCTION how_added(id integer); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.how_added(id integer) IS 'This function returns the number of songs added by the artist.';


--
-- Name: how_similar(integer, integer); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.how_similar(first integer, second integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
DECLARE
    songs numeric;
    similars numeric;
begin
    SELECT count(*) INTO songs FROM library
    WHERE iduser=first;
    SELECT count(*) INTO similars FROM (SELECT idsong FROM library WHERE iduser=first) AS f
    JOIN (SELECT idsong FROM library WHERE iduser=second) AS s ON f.idsong=s.idsong;

    return (similars / songs)::numeric(5, 2) * 100;
end;
$$;


ALTER FUNCTION public.how_similar(first integer, second integer) OWNER TO speaker_user;

--
-- Name: FUNCTION how_similar(first integer, second integer); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.how_similar(first integer, second integer) IS 'This function return as a percentage how similar the playlists of two users are(similar songs / songs of first).';


--
-- Name: how_singels(integer); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.how_singels(id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    result integer;
begin
    result = 0;
    if EXISTS(SELECT iduser FROM "User" WHERE iduser=id) THEN
        SELECT count(*) INTO result FROM song_singer
        WHERE idsinger=id AND idsong NOT IN (SELECT idsong FROM song_singer
                                             WHERE idsinger=id AND ismain=false);
    end if;

    return result;
end;
$$;


ALTER FUNCTION public.how_singels(id integer) OWNER TO speaker_user;

--
-- Name: FUNCTION how_singels(id integer); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.how_singels(id integer) IS 'This function return the number of songs that sing by a single artist.';


--
-- Name: is_exist(character varying); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.is_exist(name character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
begin
     if EXISTS(SELECT "User".iduser  FROM "User"
                WHERE "User".username ~* name) THEN
         RETURN TRUE;
     end if;
    RETURN FALSE;
end;
$$;


ALTER FUNCTION public.is_exist(name character varying) OWNER TO speaker_user;

--
-- Name: FUNCTION is_exist(name character varying); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.is_exist(name character varying) IS 'This function return true if username is already taken.';


--
-- Name: set_sale(integer, integer); Type: PROCEDURE; Schema: public; Owner: speaker_user
--

CREATE PROCEDURE public.set_sale(IN id integer, IN newsale integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
begin
    if newsale <= 100 THEN
        UPDATE "User"
        SET sale=newsale::numeric(10, 2)
        WHERE iduser=id;
        COMMIT;
    end if;
end;
$$;


ALTER PROCEDURE public.set_sale(IN id integer, IN newsale integer) OWNER TO speaker_user;

--
-- Name: PROCEDURE set_sale(IN id integer, IN newsale integer); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON PROCEDURE public.set_sale(IN id integer, IN newsale integer) IS 'This procedure set sale for user as a percentage.';


--
-- Name: trigger_check_for_add_music(); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.trigger_check_for_add_music() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    userVal integer;
    songVal integer;
begin
    SELECT limval INTO userVal FROM limitation
    JOIN "User" U on limitation.idlimitation = U.idlimitation
    WHERE U.iduser=NEW.iduser;
    SELECT limval INTO songVal FROM song
    WHERE idsong=NEW.idsong;
    if songVal > userVal THEN
        RAISE EXCEPTION 'Your limitation don''t allow you add music.' ;
    end if;
    RETURN NEW;
end;
$$;


ALTER FUNCTION public.trigger_check_for_add_music() OWNER TO speaker_user;

--
-- Name: FUNCTION trigger_check_for_add_music(); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.trigger_check_for_add_music() IS 'This function executes by trigger named "check_add_music" of library table and determinate add music or doesn''t.';


--
-- Name: trigger_check_music_for_user(); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.trigger_check_music_for_user() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    isbiger integer;
begin
    SELECT count(*) INTO isbiger FROM limitation l2, song s
    JOIN library l on s.idsong = l.idsong
    JOIN "User" U on U.iduser = l.iduser
    WHERE U.iduser=NEW.iduser AND
          l2.idlimitation=NEW.idlimitation AND
          l2.limval<s.limval;

    if isbiger > 0 THEN
        DELETE FROM library
        USING song, limitation
        WHERE library.iduser=NEW.iduser AND
              song.idsong=library.idsong AND
              NEW.idlimitation=limitation.idlimitation AND
              song.limval>limitation.limval;
    end if;
    RETURN NEW;
end;
$$;


ALTER FUNCTION public.trigger_check_music_for_user() OWNER TO speaker_user;

--
-- Name: FUNCTION trigger_check_music_for_user(); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.trigger_check_music_for_user() IS 'This function executes by trigger named "check_valid_limitation" of User table and deletes sons from library whose limitation value is greater than user''s.';


--
-- Name: trigger_check_update_password(); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.trigger_check_update_password() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
begin
    if length(NEW.password) < 6 THEN
        RAISE EXCEPTION 'password should be longer or equals that 6 characters';
    end if;
    RETURN NEW;
end;
$$;


ALTER FUNCTION public.trigger_check_update_password() OWNER TO speaker_user;

--
-- Name: FUNCTION trigger_check_update_password(); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.trigger_check_update_password() IS 'This function executes by trigger named "check_update_password" of User table and checks that update be correct.';


--
-- Name: trigger_insert_user(); Type: FUNCTION; Schema: public; Owner: speaker_user
--

CREATE FUNCTION public.trigger_insert_user() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
begin
    if extract(YEAR FROM age(NEW.userbithday)) < 14 THEN
        RAISE EXCEPTION 'age can''t be lower 14';
    end if;
    RETURN NEW;
end;
$$;


ALTER FUNCTION public.trigger_insert_user() OWNER TO speaker_user;

--
-- Name: FUNCTION trigger_insert_user(); Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON FUNCTION public.trigger_insert_user() IS 'This function executes by trigger named "check_insert_user" of User table and checks that age of creating user be greater or equals 14 years.';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: User; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public."User" (
    username character varying(100) NOT NULL,
    firstname character varying(200) NOT NULL,
    secondname character varying(200) NOT NULL,
    userbithday date NOT NULL,
    idsubscribe smallint DEFAULT 1 NOT NULL,
    idlimitation smallint DEFAULT 0 NOT NULL,
    password character varying(200) NOT NULL,
    iduser bigint NOT NULL,
    sale numeric(10,2) DEFAULT 0.00
);


ALTER TABLE public."User" OWNER TO speaker_user;

--
-- Name: User_iduser_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public."User_iduser_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."User_iduser_seq" OWNER TO speaker_user;

--
-- Name: User_iduser_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public."User_iduser_seq" OWNED BY public."User".iduser;


--
-- Name: album; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.album (
    idalbum bigint NOT NULL,
    albumname character varying(300) NOT NULL,
    idlabel integer NOT NULL,
    albumphoto character varying(250) DEFAULT '/path/to/default/photo'::character varying
);


ALTER TABLE public.album OWNER TO speaker_user;

--
-- Name: album_idalbum_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.album_idalbum_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.album_idalbum_seq OWNER TO speaker_user;

--
-- Name: album_idalbum_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.album_idalbum_seq OWNED BY public.album.idalbum;


--
-- Name: album_singer; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.album_singer (
    idalbum bigint NOT NULL,
    idsinger bigint NOT NULL
);


ALTER TABLE public.album_singer OWNER TO speaker_user;

--
-- Name: country; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.country (
    idcountry smallint NOT NULL,
    countryname character varying(200) NOT NULL
);


ALTER TABLE public.country OWNER TO speaker_user;

--
-- Name: country_idcountry_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.country_idcountry_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.country_idcountry_seq OWNER TO speaker_user;

--
-- Name: country_idcountry_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.country_idcountry_seq OWNED BY public.country.idcountry;


--
-- Name: genre; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.genre (
    idgenre smallint NOT NULL,
    genrename character varying(200) NOT NULL
);


ALTER TABLE public.genre OWNER TO speaker_user;

--
-- Name: genre_idgenre_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.genre_idgenre_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genre_idgenre_seq OWNER TO speaker_user;

--
-- Name: genre_idgenre_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.genre_idgenre_seq OWNED BY public.genre.idgenre;


--
-- Name: label; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.label (
    idlabel integer NOT NULL,
    labelname character varying(300) NOT NULL
);


ALTER TABLE public.label OWNER TO speaker_user;

--
-- Name: label_idlabel_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.label_idlabel_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.label_idlabel_seq OWNER TO speaker_user;

--
-- Name: label_idlabel_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.label_idlabel_seq OWNED BY public.label.idlabel;


--
-- Name: language; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.language (
    idlanguage smallint NOT NULL,
    languagename character varying(200) NOT NULL
);


ALTER TABLE public.language OWNER TO speaker_user;

--
-- Name: language_idlanguage_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.language_idlanguage_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.language_idlanguage_seq OWNER TO speaker_user;

--
-- Name: language_idlanguage_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.language_idlanguage_seq OWNED BY public.language.idlanguage;


--
-- Name: library; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.library (
    idsong bigint NOT NULL,
    iduser bigint NOT NULL
);


ALTER TABLE public.library OWNER TO speaker_user;

--
-- Name: limitation; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.limitation (
    idlimitation integer NOT NULL,
    limname character varying(200) NOT NULL,
    limval integer NOT NULL
);


ALTER TABLE public.limitation OWNER TO speaker_user;

--
-- Name: limitation_idlimitation_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.limitation_idlimitation_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.limitation_idlimitation_seq OWNER TO speaker_user;

--
-- Name: limitation_idlimitation_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.limitation_idlimitation_seq OWNED BY public.limitation.idlimitation;


--
-- Name: singer; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.singer (
    idsinger bigint NOT NULL,
    singername character varying(200) NOT NULL,
    singerbithday date NOT NULL,
    idcountry smallint DEFAULT 0 NOT NULL
);


ALTER TABLE public.singer OWNER TO speaker_user;

--
-- Name: singer_idsinger_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.singer_idsinger_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.singer_idsinger_seq OWNER TO speaker_user;

--
-- Name: singer_idsinger_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.singer_idsinger_seq OWNED BY public.singer.idsinger;


--
-- Name: song; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.song (
    idsong bigint NOT NULL,
    songname character varying(200) NOT NULL,
    limval integer NOT NULL,
    idgenre smallint NOT NULL,
    idalbum bigint NOT NULL,
    idlanguage smallint NOT NULL
);


ALTER TABLE public.song OWNER TO speaker_user;

--
-- Name: song_idsong_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.song_idsong_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.song_idsong_seq OWNER TO speaker_user;

--
-- Name: song_idsong_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.song_idsong_seq OWNED BY public.song.idsong;


--
-- Name: song_singer; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.song_singer (
    idsong bigint NOT NULL,
    idsinger bigint NOT NULL,
    ismain boolean
);


ALTER TABLE public.song_singer OWNER TO speaker_user;

--
-- Name: songsource; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.songsource (
    idsong bigint NOT NULL,
    songtext text NOT NULL,
    songpath text NOT NULL
);


ALTER TABLE public.songsource OWNER TO speaker_user;

--
-- Name: subscribe; Type: TABLE; Schema: public; Owner: speaker_user
--

CREATE TABLE public.subscribe (
    idsubscribe integer NOT NULL,
    subscribename character varying(200) NOT NULL,
    subscribecost numeric(10,2) DEFAULT 0.00 NOT NULL
);


ALTER TABLE public.subscribe OWNER TO speaker_user;

--
-- Name: subscribe_idsubscribe_seq; Type: SEQUENCE; Schema: public; Owner: speaker_user
--

CREATE SEQUENCE public.subscribe_idsubscribe_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subscribe_idsubscribe_seq OWNER TO speaker_user;

--
-- Name: subscribe_idsubscribe_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: speaker_user
--

ALTER SEQUENCE public.subscribe_idsubscribe_seq OWNED BY public.subscribe.idsubscribe;


--
-- Name: User iduser; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public."User" ALTER COLUMN iduser SET DEFAULT nextval('public."User_iduser_seq"'::regclass);


--
-- Name: album idalbum; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.album ALTER COLUMN idalbum SET DEFAULT nextval('public.album_idalbum_seq'::regclass);


--
-- Name: country idcountry; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.country ALTER COLUMN idcountry SET DEFAULT nextval('public.country_idcountry_seq'::regclass);


--
-- Name: genre idgenre; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.genre ALTER COLUMN idgenre SET DEFAULT nextval('public.genre_idgenre_seq'::regclass);


--
-- Name: label idlabel; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.label ALTER COLUMN idlabel SET DEFAULT nextval('public.label_idlabel_seq'::regclass);


--
-- Name: language idlanguage; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.language ALTER COLUMN idlanguage SET DEFAULT nextval('public.language_idlanguage_seq'::regclass);


--
-- Name: limitation idlimitation; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.limitation ALTER COLUMN idlimitation SET DEFAULT nextval('public.limitation_idlimitation_seq'::regclass);


--
-- Name: singer idsinger; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.singer ALTER COLUMN idsinger SET DEFAULT nextval('public.singer_idsinger_seq'::regclass);


--
-- Name: song idsong; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song ALTER COLUMN idsong SET DEFAULT nextval('public.song_idsong_seq'::regclass);


--
-- Name: subscribe idsubscribe; Type: DEFAULT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.subscribe ALTER COLUMN idsubscribe SET DEFAULT nextval('public.subscribe_idsubscribe_seq'::regclass);


--
-- Name: User User_iduser_key; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_iduser_key" UNIQUE (iduser);


--
-- Name: User User_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (username);


--
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (idalbum);


--
-- Name: country country_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.country
    ADD CONSTRAINT country_pkey PRIMARY KEY (idcountry);


--
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (idgenre);


--
-- Name: label label_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.label
    ADD CONSTRAINT label_pkey PRIMARY KEY (idlabel);


--
-- Name: language language_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.language
    ADD CONSTRAINT language_pkey PRIMARY KEY (idlanguage);


--
-- Name: limitation limitation_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.limitation
    ADD CONSTRAINT limitation_pkey PRIMARY KEY (idlimitation);


--
-- Name: singer singer_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.singer
    ADD CONSTRAINT singer_pkey PRIMARY KEY (idsinger);


--
-- Name: song song_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song
    ADD CONSTRAINT song_pkey PRIMARY KEY (idsong);


--
-- Name: songsource songsource_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.songsource
    ADD CONSTRAINT songsource_pkey PRIMARY KEY (idsong);


--
-- Name: subscribe subscribe_pkey; Type: CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.subscribe
    ADD CONSTRAINT subscribe_pkey PRIMARY KEY (idsubscribe);


--
-- Name: library check_add_music; Type: TRIGGER; Schema: public; Owner: speaker_user
--

CREATE TRIGGER check_add_music BEFORE INSERT ON public.library FOR EACH ROW EXECUTE FUNCTION public.trigger_check_for_add_music();


--
-- Name: TRIGGER check_add_music ON library; Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON TRIGGER check_add_music ON public.library IS 'This trigger doesn''t allow add new row in table where limitation value of song is greater then user''s (add music in user''s library).';


--
-- Name: User check_insert_user; Type: TRIGGER; Schema: public; Owner: speaker_user
--

CREATE TRIGGER check_insert_user BEFORE INSERT ON public."User" FOR EACH ROW EXECUTE FUNCTION public.trigger_insert_user();


--
-- Name: TRIGGER check_insert_user ON "User"; Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON TRIGGER check_insert_user ON public."User" IS 'This trigger doesn''t allow create user older 14 years.';


--
-- Name: User check_update_password; Type: TRIGGER; Schema: public; Owner: speaker_user
--

CREATE TRIGGER check_update_password BEFORE UPDATE OF password ON public."User" FOR EACH ROW EXECUTE FUNCTION public.trigger_check_update_password();


--
-- Name: TRIGGER check_update_password ON "User"; Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON TRIGGER check_update_password ON public."User" IS 'This trigger doesn''t allow create password with length lower 6 character.';


--
-- Name: User check_valid_limitation; Type: TRIGGER; Schema: public; Owner: speaker_user
--

CREATE TRIGGER check_valid_limitation AFTER UPDATE OF idlimitation ON public."User" FOR EACH ROW EXECUTE FUNCTION public.trigger_check_music_for_user();


--
-- Name: TRIGGER check_valid_limitation ON "User"; Type: COMMENT; Schema: public; Owner: speaker_user
--

COMMENT ON TRIGGER check_valid_limitation ON public."User" IS 'This trigger find and delete all songs from user''s library whose limitation value is greater than user''s limitation.';


--
-- Name: album fk_album_label; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT fk_album_label FOREIGN KEY (idlabel) REFERENCES public.label(idlabel) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: album_singer fk_album_singer_album; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.album_singer
    ADD CONSTRAINT fk_album_singer_album FOREIGN KEY (idalbum) REFERENCES public.album(idalbum) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: album_singer fk_album_singer_singer; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.album_singer
    ADD CONSTRAINT fk_album_singer_singer FOREIGN KEY (idsinger) REFERENCES public.singer(idsinger) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: library fk_library_song; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.library
    ADD CONSTRAINT fk_library_song FOREIGN KEY (idsong) REFERENCES public.song(idsong) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: library fk_library_user; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.library
    ADD CONSTRAINT fk_library_user FOREIGN KEY (iduser) REFERENCES public."User"(iduser) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: singer fk_singer_country; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.singer
    ADD CONSTRAINT fk_singer_country FOREIGN KEY (idcountry) REFERENCES public.country(idcountry) ON UPDATE CASCADE ON DELETE SET DEFAULT;


--
-- Name: song fk_song_genre; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song
    ADD CONSTRAINT fk_song_genre FOREIGN KEY (idgenre) REFERENCES public.genre(idgenre);


--
-- Name: song fk_song_language; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song
    ADD CONSTRAINT fk_song_language FOREIGN KEY (idlanguage) REFERENCES public.language(idlanguage);


--
-- Name: song_singer fk_song_singer_singer; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song_singer
    ADD CONSTRAINT fk_song_singer_singer FOREIGN KEY (idsinger) REFERENCES public.singer(idsinger) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: song_singer fk_song_singer_song; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.song_singer
    ADD CONSTRAINT fk_song_singer_song FOREIGN KEY (idsong) REFERENCES public.song(idsong) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: User fk_user_limitation; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT fk_user_limitation FOREIGN KEY (idlimitation) REFERENCES public.limitation(idlimitation) ON UPDATE CASCADE ON DELETE SET DEFAULT;


--
-- Name: User fk_user_subscribe; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public."User"
    ADD CONSTRAINT fk_user_subscribe FOREIGN KEY (idsubscribe) REFERENCES public.subscribe(idsubscribe) ON UPDATE CASCADE ON DELETE SET DEFAULT;


--
-- Name: songsource link_song_songsource; Type: FK CONSTRAINT; Schema: public; Owner: speaker_user
--

ALTER TABLE ONLY public.songsource
    ADD CONSTRAINT link_song_songsource FOREIGN KEY (idsong) REFERENCES public.song(idsong) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--


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
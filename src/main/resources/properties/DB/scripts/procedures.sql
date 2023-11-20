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
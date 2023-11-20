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

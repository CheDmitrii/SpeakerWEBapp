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
